package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasenotes.WidgetsCardView.Data
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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

    var id: Int = 0

    fun insertarDatos(num_cajon: String, nombre_cajon: String, piso_edificio: String) {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            val data = hashMapOf(
                "Numero " to num_cajon,
                "Nombre" to nombre_cajon,
                "id" to id,
                "Piso" to piso_edificio,
            )

            try {
                val parkingRef = db.collection("cajones")
                parkingRef.add(data).await()
                id++
            } catch (e: Exception) {

            }
        }
    }



}
