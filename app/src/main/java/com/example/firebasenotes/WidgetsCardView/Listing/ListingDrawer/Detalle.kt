package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebasenotes.ViewMenu.ReservacionCajones

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Detalle(navController: NavController,context: Context){
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

        ,

    ) {
        Column(modifier = Modifier
            .padding(10.dp)
            ) {

            Card(
                onClick = { /*TODO*/ }

            ) {


                Text(
                    text = "Detalle", fontWeight = FontWeight.Bold, fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )

                Text(text = "Homero Rmz - ISITA", modifier = Modifier.padding(10.dp))
                Text(text = "Cajon: 443", modifier = Modifier.padding(10.dp))
                Text(text = "Piso 3", modifier = Modifier.padding(10.dp))
                Text(text = "Nota: Cajón especial", modifier = Modifier.padding(10.dp))

                Text(
                    text = "Disponible",
                    fontWeight = FontWeight.Medium, fontSize = 15.sp, color = Color.Blue,
                    modifier = Modifier.padding(10.dp)
                )

                Button(
                    onClick = {
                        navController.navigate("ReservacionCajones_extension")
                        //
                        //
                        //var context = LocalContext.current
                        //context.startActivity(Intent(context, ReservacionCajones::class.java))
                        },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()

                ) {
                    Text(text = "Reservar")
                }
            }
        }

    }
}