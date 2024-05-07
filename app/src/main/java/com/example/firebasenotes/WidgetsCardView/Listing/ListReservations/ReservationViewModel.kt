package com.example.firebasenotes.WidgetsCardView.Listing.ListReservations

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.DataDrawer
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
            statereservation.value = com.example.firebasenotes.WidgetsCardView.Listing.ListReservations.DataFromReservation()
        }
    }



}


suspend fun DataFromReservation(): MutableList<DataReservations> {
    val db = FirebaseFirestore.getInstance()
    val reservas = mutableListOf<DataReservations>()
    val querySnapshot = db.collection("spaces").get().await()
    querySnapshot.query


    for(document in querySnapshot.documents){
        val reserv = document.toObject(DataReservations::class.java)
        reserv?.let {
            reservas.add(it)
        }
    }
    return reservas
}

