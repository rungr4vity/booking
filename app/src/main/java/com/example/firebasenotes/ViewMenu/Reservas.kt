package com.example.firebasenotes.ViewMenu

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.firebasenotes.WidgetsCardView.DScreen
import com.example.firebasenotes.WidgetsCardView.DViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MiReservas(){
    Scaffold {
        Text(text = "Mis Reservas")
        var vm = DViewModel()
        DScreen(vm)
    }
}