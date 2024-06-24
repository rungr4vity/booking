package com.example.firebasenotes.WidgetsCardView.Listing.ListAreas

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

suspend fun DataFromArea() : MutableList <DataAreas>{
    val db = FirebaseFirestore.getInstance()

    val areas = mutableListOf<DataAreas>()
    val querySnapshot = db.collection("Oficinas").get().await()
    querySnapshot.query


    for (document in querySnapshot.documents){
        var ar = document.toObject(DataAreas::class.java)
        ar?.id = document.id

        ar?.let {

            areas.add(it)
        }
    }
    return areas
}

class AreaViewModel : ViewModel(){
    private val _variable = MutableStateFlow<String>("Inicial")
    val uiState: StateFlow<String> = _variable

    val statearea = mutableStateOf<List<DataAreas>>(emptyList())
    init {
        getData()

    }
    private fun getData() {
        viewModelScope.launch {
           statearea.value = com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.DataFromArea()
        }
    }

    fun imgArea(idArea: String){
        val imageUrlState = MutableStateFlow<Uri?>(null)

        Firebase.firestore.collection("Oficinas").document(idArea)
            .get()
            .addOnSuccessListener { document ->

                val imageUrl = document.getString("imageUrl")
                imageUrl?.let {
//                    imageUrlState.value = Uri.parse(it)
                    _variable.value = it

                }
            }

    }
    fun updatePhoto(context: Context, imagen: String, updatedImage: Uri, oficinaId: String) {

        val storageReference = FirebaseStorage.getInstance().reference
        val desertRef = storageReference.child("images/oficinas/$imagen")

        desertRef.delete().addOnSuccessListener {

            val storage = FirebaseStorage.getInstance().reference
            val imageRef = storage.child("images/oficinas/${UUID.randomUUID()}.jpg")
            imageRef.putFile(updatedImage)
                .addOnSuccessListener {

                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()
                        var instance = FirebaseFirestore.getInstance()

                        val dto = hashMapOf(

                            "imageUrl" to downloadUrl
                        )

                        instance.collection("Oficinas").document(oficinaId)
                            .update(dto as Map<String, Any>).addOnSuccessListener {
                                Toast.makeText(context, "Imagen actualizada", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Error al implementar imagen oficina : ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }

                }.addOnFailureListener {
                    Toast.makeText(context, "Error al actualizar la imagen", Toast.LENGTH_SHORT).show()
                }

        }.addOnFailureListener {
            // Uh-oh, an error occurred!
            Toast.makeText(context, "Error al eliminar la imagen", Toast.LENGTH_SHORT).show()
        }

    }
    private suspend fun getImageUrlFromFirestore(documentId: String): String? {
        val firestore = Firebase.firestore
        return try {
            val document = firestore.collection("Oficinas").document(documentId).get().await()
            document.getString("imageUrl")
        } catch (e: Exception) {
            // Manejar errores aquí, como falta de conexión o errores de Firestore
            null
        }
    }


    // Función para actualizar la oficina en Firestore
    fun actualizarOficina(
        idArea: String,
        nombre: String,
        descripcion: String,
        capacidad: String,
        mobilaria: String,
        id: String
    ) {
        val db = FirebaseFirestore.getInstance()
        val oficinasRef = db.collection("Oficinas").document(idArea)



        oficinasRef.update(
            mapOf(
                "nombre" to nombre,
                "descripcion" to descripcion,
                "capacidad" to capacidad,
                "mobilaria" to mobilaria
        )
        ).addOnSuccessListener {
            Log.d("ActualizarOficina", "Oficina actualizada con éxito")
        }.addOnFailureListener { e ->
            Log.e("ActualizarOficina", "Error al actualizar la oficina", e)
        }
    }
    fun fetchOfficeImages(idArea: String): List<DataAreas> {
        val db = Firebase.firestore
        val officeList = mutableListOf<DataAreas>()

        db.collection("Oficinas")
            .whereEqualTo("idArea", idArea)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val imageUrl = document.getString("imageUrl")
                    if (imageUrl != null) {
                        officeList.add(DataAreas(idArea, imageUrl))
                    }
                }

            }
            .addOnFailureListener { exception ->
                // Manejo de errores
            }

        return officeList
    }

    //CLASEE PARA AGREGAR REGISTRO
    fun InsertDatosArea(
        capacidad: String,
        nombre: String,
        mobiliaria: String,
        descripcion: String,
        imageUrl: String,
        id: String = ""
    ) {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            val date = hashMapOf(
                "capacidad" to capacidad,
                "descripcion" to descripcion,
                "mobilaria" to mobiliaria,
                "nombre" to nombre,
                "imageUrl" to imageUrl,
                "id" to id


            )
            db.collection("Oficinas").add(date)

            // Agregar un nuevo documento con un ID
//            db.collection("Oficinas").add(date).addOnSuccessListener { documentReference ->
//                // Obtener el ID generado
//                val id = documentReference.id
////                db.collection("Oficinas").document(id).update("id", id) }.await()
        }
    }
}

//CLASEE PARA ELIMINAR REGISTRO
class AreaViewModelDOS : ViewModel() {
    val stateareaa = mutableStateOf<List<DataAreas>>(emptyList())

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            stateareaa.value = DataFromArea()
        }
    }

    fun deleteArea(idArea: String) {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            db.collection("Oficinas").document(idArea).delete().await()
            stateareaa.value = DataFromArea()
        }
    }


}
