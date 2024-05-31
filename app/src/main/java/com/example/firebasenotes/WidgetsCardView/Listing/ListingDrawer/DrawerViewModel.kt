package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
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

    try {
        for (document in querySnapshot.documents) {

            println()
            var numero = document.data?.get("numero") as Long

            cajon.add(
                DataDrawer(
                    numero.toInt(),
                document.data?.get("nombre") as String,
                document.data?.get("piso") as String,
                document.id,
                document.data?.get("empresa") as String,
                document.data?.get("esEspecial") as Boolean))

        }
    }catch (e:Exception){
        Log.e("Error",e.message.toString())
    }
    return cajon
}

class DrawerViewModel() : ViewModel() {
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

    fun insertarDatos(context: Context,
        numo_cajon: Int, nombre_cajon: String, piso_edificio: String,
        selectedOptionText: String
        ,esEspecial : Boolean?=false
    ){

        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()

            val data = hashMapOf(
                "numero" to numo_cajon,
                "nombre" to nombre_cajon.trim(),
                "piso" to piso_edificio.trim(),
                "empresa" to selectedOptionText.trim(),
                "esEspecial" to esEspecial,
                "id" to ""
            )
            try {
                val documentRef = db.collection("Estacionamientos").add(data).await()
                Toast.makeText(context, "Datos insertados correctamente", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Log.e("Error",e.message.toString())
            }
        }
    }}
class DeleteDrawerViewModel(context: Context) : ViewModel() {
    val delete = mutableStateOf<List<DataDrawer>>(emptyList())

    init {
        deleteData(context)
    }

    private fun deleteData(context: Context) {
        viewModelScope.launch {

                val newData = FireStoreCajonData()
                delete.value = newData

        }
    }

    fun deleteData(context: Context, id: String) {
        viewModelScope.launch {

                val db = FirebaseFirestore.getInstance()
                db.collection("Estacionamientos").document(id).delete().await()
                deleteData(context) // función para actualizar la lista después de eliminar

        }
    }
}
