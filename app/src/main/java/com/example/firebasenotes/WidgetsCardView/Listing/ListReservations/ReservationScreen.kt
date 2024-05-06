package com.example.firebasenotes.WidgetsCardView.Listing.ListReservations

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
@Preview
@Composable
fun ReservationScreen(reservationViewModel: ReservationViewModel = viewModel()){
    val reservar =reservationViewModel.statereservation.value
    LazyColumn {
        items(reservar){ reserv ->
            ComponentReservation(reserv = reserv)

        }
    }
}

@Composable
fun ComponentReservation(reserv : DataReservations ){
    Card {
        Text(text = reserv.nombre)
    }

}