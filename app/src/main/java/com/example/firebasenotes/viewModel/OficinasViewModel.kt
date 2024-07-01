package com.example.firebasenotes.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.models.DataAreas
import com.example.firebasenotes.models.oficinasDTO
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID


suspend fun _ReservacionOficinasDia(ano:Int,dia:Int,idArea:String) : MutableList <oficinasDTO>{
    val db = FirebaseFirestore.getInstance()
    val reservacionOficinas = mutableListOf<oficinasDTO>()

    val querySnapshot = db.collection("ReservacionOficina")
        .whereEqualTo("dia", dia)
        .whereEqualTo("ano", ano)
        .whereEqualTo("idArea", idArea.trim())
        .get().await()
    querySnapshot.query

    for (document in querySnapshot.documents){
        val ar = document.toObject(oficinasDTO::class.java)

        ar?.let {
            reservacionOficinas.add(it)
        }
    }
    return reservacionOficinas
}

class OficinasViewModel(

):ViewModel() {



    private var _horariosOficinas = MutableLiveData<MutableList<oficinasDTO>>(mutableListOf())
    val horariosOficinas: LiveData<MutableList<oficinasDTO>> get() = _horariosOficinas

    val stateOficina = mutableStateOf<List<oficinasDTO>>(emptyList())


    // Flow
    private var _flow_image: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)
    val flow_image get() = _flow_image

    fun setImageUri(uri: Uri) {
        _flow_image.value = uri
    }

    fun reservacionOficina(
        context: Context,
        ano:Int,
        dia:Int,
        horaInicial:String,
        horafinal:String,
        id:String,
        idArea:String,
        idUsuario:String) {



        val horaInicio = horaInicial.split(":")[0].toInt()
        val minutosInicio = horaInicial.split(":")[1].toInt()

        val horaFin = horafinal.split(":")[0].toInt()
        val minutosFin = horafinal.split(":")[1].toInt()

        //var _inicialInt = horaInicial.replace(":",".")
        //var _finalInt = horafinal.replace(":",".")

        val inicialInt = (horaInicio.toInt() * 60) + minutosInicio
        val finalInt = (horaFin.toInt() * 60) + minutosFin


        val oficina = oficinasDTO(
            ano,
            dia,
            inicialInt,
            finalInt,
            id,
            idArea.trim(),
            idUsuario.trim())

        viewModelScope.launch {
                        try {
                            FirebaseFirestore.getInstance().collection("ReservacionOficina")
                                .document()
                                .set(oficina)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Reservación generada", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "error al generar reservación", Toast.LENGTH_SHORT).show()
                                }

                        }catch (e:Exception){

                        }
        } // end launch


    }// end function

    fun ReservacionOficinasDia(ano:Int,dia:Int,idArea:String) {

        viewModelScope.launch {
           // stateOficina.value =
            _horariosOficinas.value = _ReservacionOficinasDia(ano,dia,idArea)
            println("stateOficina ${stateOficina.value}")
            delay(2000)
        }

    }

}

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

    val statearea_ = MutableLiveData<MutableList<DataAreas>>(mutableListOf())
    val statearea:LiveData<MutableList<DataAreas>>  get () = statearea_


    init {
//        getData()

    }

    private val _officeDetails = MutableStateFlow<DataAreas?>(null)
    val officeDetails: StateFlow<DataAreas?> = _officeDetails


    fun fetchOfficeDetails(idArea: String) {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            db.collection("Oficinas").document(idArea)
                .get()
                .addOnSuccessListener { document ->
                    val office = document.toObject(DataAreas::class.java)
                    _officeDetails.value = office
                }
                .addOnFailureListener { e ->
                    // Manejar errores aquí
                    _officeDetails.value = null
                }
        }
    }
    fun getDataInfo(){
        viewModelScope.launch {
            getData()
        }
    }
    private fun getData() {
        viewModelScope.launch {
//           statearea.value = com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.DataFromArea()
            val areas = DataFromArea() // Obtener datos desde Firestore
            statearea_.value = areas
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
//                                Toast.makeText(context, "Imagen actualizada", Toast.LENGTH_SHORT).show()
//
                            }
                            .addOnFailureListener { e ->
//                                Toast.makeText(context, "Error al implementar imagen oficina : ${e.message}", Toast.LENGTH_SHORT).show()
//
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
                "nombre" to nombre.trim(),
                "descripcion" to descripcion.trim(),
                "capacidad" to capacidad.trim(),
                "mobilaria" to mobilaria.trim(),
            )
        ).addOnSuccessListener {
            Log.d("ActualizarOficina", "Oficina actualizada con éxito")
        }.addOnFailureListener { e ->
            Log.e("ActualizarOficina", "Error al actualizar la oficina", e)
        }
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
                "capacidad" to capacidad.trim(),
                "descripcion" to descripcion.trim(),
                "mobilaria" to mobiliaria.trim(),
                "nombre" to nombre.trim(),
                "imageUrl" to imageUrl,
                "id" to id


            )
            db.collection("Oficinas").add(date)


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
