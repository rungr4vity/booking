package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.models.users
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


suspend fun FireStoreCajonData(): MutableList<DataDrawer> {
    val db = FirebaseFirestore.getInstance()
    val cajonList = mutableListOf<DataDrawer>()

    try {
        val querySnapshot = db.collection("Estacionamientos").get().await()
        for (document in querySnapshot.documents) {
            val numero = document.getLong("numero") ?: 0L
            val nombre = document.getString("nombre") ?: ""
            val piso = document.getString("piso") ?: ""
            val empresa = document.getString("empresa") ?: ""
            val esEspecial = document.getBoolean("esEspecial") ?: false

            cajonList.add(
                DataDrawer(
                    numero = numero.toInt(),
                    nombre = nombre,
                    piso = piso,
                    id = document.id,
                    empresa = empresa,
                    esEspecial = esEspecial
                )
            )
        }
    } catch (e: Exception) {
        Log.e("FirestoreError", "Error fetching data: ${e.message}")
    }
    return cajonList
}


data class users_short(
    val nombres: String,
    val apellidos: String,
    val documentId: String
)

suspend fun getUsuarios_(): MutableList<users_short> {
    val db = FirebaseFirestore.getInstance()
    val usersList = mutableListOf<users_short>()

    try {
        val querySnapshot = db.collection("Usuarios").get().await()
        for (document in querySnapshot.documents) {

            val nombres = document.getString("nombres") ?: ""
            val apellidos = document.getString("apellidos") ?: ""
            val documentId = document.id

            usersList.add(
                users_short(
                    nombres = nombres,
                    apellidos = apellidos,
                    documentId = documentId
                )
            )
        }
    } catch (e: Exception) {
        Log.e("FirestoreError", "Error fetching data users short: ${e.message}")
    }
    return usersList
}


class DrawerViewModel() : ViewModel() {
    val stateDrawer = mutableStateOf<List<DataDrawer>>(emptyList())
    val usuarios_short = mutableStateOf<List<users_short>>(emptyList())

    init {
        getData()
        getUsuarios()
    }

    private fun getUsuarios() {
        viewModelScope.launch {
            usuarios_short.value =
                com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.getUsuarios_()
        }
    }

    private fun getData() {
        viewModelScope.launch {
            stateDrawer.value =
                com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.FireStoreCajonData()
        }
    }

    fun insertarDatos(context: Context,
                      numo_cajon: Int,
                      nombre_cajon: String,
                      piso_edificio: String,
                      selectedOptionText: String,
                      esEspecial : Boolean,
                      perteneceA: String
    ){

        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()

            val data = hashMapOf(
                "numero" to numo_cajon,
                "nombre" to nombre_cajon.trim(),
                "piso" to piso_edificio.trim(),
                "empresa" to selectedOptionText.trim(),
                "esEspecial" to esEspecial,
                "id" to "",
                "perteneceA" to perteneceA
            )
            try {
                val documentRef = db.collection("Estacionamientos").add(data).await()
                Toast.makeText(context, "Datos insertados correctamente", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Log.e("Error",e.message.toString())
            }
        }
    }}
class DeleteDrawerViewModel : ViewModel() {
    val delete = mutableStateOf<List<DataDrawer>>(emptyList())

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            delete.value = FireStoreCajonData()
        }
    }

    fun deleteData(id: String) {
        viewModelScope.launch {
            try {
                val db = FirebaseFirestore.getInstance()
                db.collection("Estacionamientos").document(id).delete().await()
                loadData() // Refrescar los datos después de eliminar
            } catch (e: Exception) {
                Log.e("FirestoreError", "Error deleting data: ${e.message}")
            }
        }
    }
}
