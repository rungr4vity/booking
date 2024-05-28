package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

suspend fun FireStoreCajonData(): MutableList<DataDrawer> {
    val db = FirebaseFirestore.getInstance()
    val cajon = mutableListOf<DataDrawer>()
    val querySnapshot = db.collection("Estacionamientos").get().await()
    querySnapshot.query
    for (document in querySnapshot.documents) {
        val cajones = document.toObject(DataDrawer::class.java)
        cajones?.let {
            cajon.add(it)
        }
    }
    return cajon
}

class DrawerViewModel : ViewModel() {
    val stateDrawer = mutableStateOf<List<DataDrawer>>(emptyList())

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            stateDrawer.value =
                com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.FireStoreCajonData()
        }
    }

    fun insertarDatos(
        numo_cajon: Int, nombre_cajon: String, piso_edificio: String,
        selectedOptionText: String, descripcion: String,
        imagenEstacionamiento: String, esEspecial : Boolean?=false
    ) {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            val data = hashMapOf(
                "numero" to numo_cajon,
                "nombre" to nombre_cajon.trim(),
                "piso" to piso_edificio.trim(),
                "empresa" to selectedOptionText.trim(),
                "descripcion" to descripcion.trim(),
                "imagenEstacionamiento" to imagenEstacionamiento.trim(),
                "esEspecial" to esEspecial
            )
            try {
                val documentRef = db.collection("Estacionamientos").add(data).await()
                val id = documentRef.id
                db.collection("Estacionamientos").document(id).update("id", id).await()
            } catch (e: Exception) {

            }
        }
    }}
class DeleteDrawerViewModel : ViewModel() {
    val delete = mutableStateOf<List<DataDrawer>>(emptyList())

    init {
        deleteData()
    }

    private fun deleteData() {
        viewModelScope.launch {

                val newData = FireStoreCajonData()
                delete.value = newData

        }
    }

    fun deleteData(id: String) {
        viewModelScope.launch {

                val db = FirebaseFirestore.getInstance()
                db.collection("Estacionamientos").document(id).delete().await()
                deleteData() // función para actualizar la lista después de eliminar

        }
    }
}
