package com.example.firebasenotes.Viaticos.Viaje

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.ViewMenu.Mipefil.Data
import com.example.firebasenotes.WidgetsCardView.Listing.ListReservations.DataReservations
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ViajeViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _motivosViaje = MutableStateFlow<List<MotivosViaje>>(emptyList())
    val motivosViaje: StateFlow<List<MotivosViaje>> = _motivosViaje

    private val _clientes = MutableStateFlow<List<Clientes>>(emptyList())
    val clientes: StateFlow<List<Clientes>> = _clientes

    init {
        fetchMotivosViaje()
        fetchClientes()
    }

    private fun fetchMotivosViaje() {
        viewModelScope.launch {
            val motivos =
                db.collection("MotivosViaje").get().await().toObjects(MotivosViaje::class.java)
            _motivosViaje.value = motivos
        }
    }

    private fun fetchClientes() {
        viewModelScope.launch {
            val clients = db.collection("Clientes").get().await().toObjects(Clientes::class.java)
            _clientes.value = clients
        }
    }

    fun GuardarViaje(viaje: viaje) {
        viewModelScope.launch {
            val userEmail = auth.currentUser?.email
            if (userEmail != null) {
                // Obtener el ID del usuario basado en el email
                val userIdQuerySnapshot = db.collection("Usuarios")
                    .whereEqualTo("email", userEmail)
                    .get()
                    .await()

                val userId = userIdQuerySnapshot.documents.firstOrNull()?.id

                if (userId != null) {
                    val viajeConUsuario = viaje.copy(idUsuario = userId)
                    db.collection("Viajes").add(viajeConUsuario).await()
                }
            }
        }
    }
    fun cerrarViaje(viajeId: String) {
        val viajeRef = FirebaseFirestore.getInstance().collection("Viajes").document(viajeId)
        viajeRef.update("activo", false)
            .addOnSuccessListener {

            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error updating document", e)
            }
    }
}