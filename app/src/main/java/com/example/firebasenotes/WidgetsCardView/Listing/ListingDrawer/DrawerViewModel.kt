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
    val querySnapshot = db.collection("cajones").get().await()
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
        numo_cajon: String, nombre_cajon: String, piso_edificio: String,
        selectedOptionText: String, descripcion: String,
        imagenEstacionamiento: String, esEspecial : Boolean
    ) {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            val data = hashMapOf(
                "numero" to numo_cajon,
                "nombre" to nombre_cajon,
                "piso" to piso_edificio,
                "empresa" to selectedOptionText,
                "descripcion" to descripcion,
                "imagenEstacionamiento" to imagenEstacionamiento,
                "esEspecial" to esEspecial
            )
            try {
                val documentRef = db.collection("cajones").add(data).await()
                val id = documentRef.id
                db.collection("cajones").document(id).update("id", id).await()
            } catch (e: Exception) {

            }
        }
    }}