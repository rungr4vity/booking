package com.example.firebasenotes.WidgetsCardView.Listing.ListReservations

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "${reserv.day}/${reserv.month}/${reserv.year}",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .align(Alignment.End)
                .padding(5.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically, // Alinea verticalmente los elementos
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp)) // Añade un espacio entre el icono y la columna
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = reserv.email, style = TextStyle(fontSize = 15.sp),fontWeight = FontWeight.Bold,modifier = Modifier.padding(bottom = 20.dp))
                //Text(text = "Detalle" , style = TextStyle(fontSize = 15.sp),modifier = Modifier.padding(bottom = 10.dp))
                Text(text = reserv.company ?: "", style = TextStyle(fontSize = 15.sp))
                Text(text = "Estacionamiento:  ${reserv.espacio.toString()}", style = TextStyle(fontSize = 15.sp))
                Text(text = "Horario:  ${reserv.horario.toString()}",
                    fontWeight = FontWeight.Medium, fontSize = 15.sp, color = Color.Blue,
                    modifier = Modifier.padding(top = 10.dp))
            }

            Spacer(modifier = Modifier.width(6.dp)) // Añade un espacio al final

        }}}