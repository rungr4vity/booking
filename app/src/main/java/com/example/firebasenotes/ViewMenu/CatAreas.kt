package com.example.firebasenotes.ViewMenu

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.DrawerScreen
import com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.Registre

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CatArea(navController: NavController){
    Scaffold {

        DrawerScreen(navController = navController)
    }
}