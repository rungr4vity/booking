package com.example.firebasenotes.ViewMenu

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.firebasenotes.WidgetsCardView.DScreen
import com.example.firebasenotes.WidgetsCardView.DViewModel
import com.example.firebasenotes.WidgetsCardView.Listing.ListReservations.ReservationScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MiReservas(){
    Scaffold {
        ReservationScreen()
    }
}