package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import android.R
import android.annotation.SuppressLint
import android.content.Context

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.firebasenotes.viewModels.LoginViewModel
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Detalle(
    navController: NavController,
    context: Context,
    nombre: String,
    company: String,
    cajon: String,
    piso: String,
    esEspecial: Boolean,
    viewModel: LoginViewModel = LoginViewModel()
){
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

                Text(
                    text = "Fecha ${LocalDate.now()}", fontWeight = FontWeight.Thin, fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )

                Text(text = " $nombre  - $company", modifier = Modifier.padding(10.dp))
                Text(text = "Cajon: ${cajon}", modifier = Modifier.padding(10.dp))
                Text(text = "Piso: $piso", modifier = Modifier.padding(10.dp))

                if (esEspecial) {
                    Text(text = "Cajón especial *", modifier = Modifier.padding(10.dp))
                } else {
                    Text(text = "Cajón normal", modifier = Modifier.padding(10.dp))
                }

                Text(
                    text = "Disponible *",
                    fontWeight = FontWeight.Medium, fontSize = 15.sp, color = Color.Blue,
                    modifier = Modifier.padding(10.dp)
                )

                Button(
                    onClick = {




                        navController.navigate("ReservacionCajones_extension/${nombre}/${company}/ ${cajon}/ ${piso}/${esEspecial}",
                            navOptions { // Use the Kotlin DSL for building NavOptions
                            anim {
                                enter = R.animator.fade_in
                                exit = R.animator.fade_out
                            }

                        })
                        },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()

                ) {
                    Text(text = "Reservar ${cajon}")
                }
            }
        }

    }
}