package com.example.firebasenotes.WidgetsCardView.Listing.ReservacionOficinas

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore



class RevoficinasViewModel : ViewModel() {

    fun getRevoficinas(idUsuario: String, onSuccess: (List<RevoficinasDTO>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val reservacionOficinaRef = db.collection("ReservacionOficina")
            .whereEqualTo("idUsuario", idUsuario)

        reservacionOficinaRef.get().addOnSuccessListener { result ->
            val revoficinasList = mutableListOf<RevoficinasDTO>()
            for (document in result.documents) {
                val revoficina = document.toObject(RevoficinasDTO::class.java)
                revoficina?.let {
                    it.id = document.id
                    revoficinasList.add(it)
                }
            }
            onSuccess(revoficinasList)
        }.addOnFailureListener { exception ->
            // Manejar errores aquí
        }
    }

    fun getDataOficina(idOficina: String, onSuccess: (DataOfi) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val oficinaRef = db.collection("Oficinas").document(idOficina)

        oficinaRef.get().addOnSuccessListener { document ->
            val dataOfi = document.toObject(DataOfi::class.java)
            dataOfi?.let { onSuccess(it) }
        }.addOnFailureListener { exception ->
            // Manejar errores aquí
        }
    }

    fun deleteData(idReservacion: String) {
        val db = FirebaseFirestore.getInstance()
        val reservacionRef = db.collection("ReservacionOficina").document(idReservacion)

        reservacionRef.delete()
            .addOnSuccessListener {
                // Éxito al eliminar
            }
            .addOnFailureListener { exception ->
                // Manejar errores aquí
            }
    }
}
