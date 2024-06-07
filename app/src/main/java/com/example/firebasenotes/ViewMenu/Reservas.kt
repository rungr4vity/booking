package com.example.firebasenotes.ViewMenu

import ReservationScreen
import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MiReservas(){
    Scaffold {
        ReservationScreen()
    }
}