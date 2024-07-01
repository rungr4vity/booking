package com.example.firebasenotes.presentation.viewModel
import androidx.lifecycle.ViewModel
import com.example.firebasenotes.models.DataDrawerDTO
import com.example.firebasenotes.models.DataReservations
import com.example.firebasenotes.models.DataTurnos
import com.google.firebase.firestore.FirebaseFirestore

class ReservacionEstacionamientoViewModel : ViewModel() {

    fun getReservacionesEstacionamiento(idUsuario: String, onSuccess: (List<DataReservations>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val reservacionesRef = db.collection("ReservacionEstacionamiento")
            .whereEqualTo("idUsuario", idUsuario)

        reservacionesRef.get().addOnSuccessListener { result ->
            val reservacionesList = mutableListOf<DataReservations>()
            for (document in result.documents) {
                val reserva = document.toObject(DataReservations::class.java)
                reserva?.let {
                    it.id = document.id
                    reservacionesList.add(it)
                }
            }
            onSuccess(reservacionesList)
        }.addOnFailureListener { exception ->
            // Handle errors here
        }
    }

    fun getEstacionamiento(idEstacionamiento: String, onSuccess: (DataDrawerDTO) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val estacionamientoRef = db.collection("Estacionamientos").document(idEstacionamiento)

        estacionamientoRef.get().addOnSuccessListener { document ->
            val estacionamiento = document.toObject(DataDrawerDTO::class.java)
            estacionamiento?.let { onSuccess(it) }
        }.addOnFailureListener { exception ->
            // Handle errors here
        }
    }

    fun getTurnosDisponibles(onSuccess: (List<DataTurnos>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val turnosRef = db.collection("TurnosEstacionamiento")

        turnosRef.get().addOnSuccessListener { result ->
            val turnosList = mutableListOf<DataTurnos>()
            for (document in result.documents) {
                val turno = document.toObject(DataTurnos::class.java)
                turno?.let { turnosList.add(it) }
            }
            onSuccess(turnosList)
        }.addOnFailureListener { exception ->
            // Handle errors here
        }
    }

    fun deleteReservacion(idReservacion: String, onSuccess: () -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val reservacionRef = db.collection("ReservacionEstacionamiento").document(idReservacion)

        reservacionRef.delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                // Handle errors here
            }
    }
}
