package com.example.firebasenotes.WidgetsCardView.Listing.ListReservations

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.DataDrawer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ReservationViewModel : ViewModel() {
    val statereservation = mutableStateOf<List<DataReservations>>(emptyList())

    init {
        getData()
    }
    private fun getData() {
        viewModelScope.launch {
            try {
                statereservation.value = com.example.firebasenotes.WidgetsCardView.Listing.ListReservations.DataFromReservation()
            }catch (e:Exception){
                Log.e("Error en listado de reservas", e.toString())
            }

        }
    }
}


suspend fun DataFromReservation(): MutableList<DataReservations> {
    val db = FirebaseFirestore.getInstance()
    val reservas = mutableListOf<DataReservations>()
    val email = FirebaseAuth.getInstance().currentUser?.email
    val querySnapshot = db.collection("ReservacionCajones")
        .whereEqualTo("email", email)
        .get().await()
    querySnapshot.query


    for(document in querySnapshot.documents){
        val reserv = document.toObject(DataReservations::class.java)
        reserv?.let {
            reservas.add(it)
        }
    }
    return reservas
}