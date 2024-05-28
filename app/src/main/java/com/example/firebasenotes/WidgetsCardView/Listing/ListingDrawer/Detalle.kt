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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.firebasenotes.models.horariosModel
import com.example.firebasenotes.viewModels.LoginViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


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
    idEstacionamiento: String,
    viewModel: LoginViewModel = LoginViewModel()
){
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

        ,

    ) {
        val today = LocalDate.now()
        val dayOfYear = today.dayOfYear

        var data = System.currentTimeMillis()
        var suma = 0




















        data?.let {
            val localDate = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
            //Text(text = "Fecha: ${localDate.dayOfMonth}/${localDate.month}/${localDate.year}")
            suma = (localDate.dayOfMonth + localDate.month.value + localDate.year)

            //viewModel.getData(dayOfYear,localDate.year,idEstacionamiento)
        }


        val opsHorarios: List<horariosModel> by viewModel.horarios.observeAsState(listOf())
        var menHorarios by remember { mutableStateOf(opsHorarios) }





















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
                Text(text = "id: $idEstacionamiento", modifier = Modifier.padding(10.dp))



                Text(
                    text = "Disponible",
                    fontWeight = FontWeight.Medium, fontSize = 15.sp, color = Color.Blue,
                    modifier = Modifier.padding(10.dp)
                )












                menHorarios.forEach {
                    Text(text = it.nombre,
                        fontWeight = FontWeight.Medium, fontSize = 15.sp, color = Color.Blue,
                        modifier = Modifier.padding(10.dp),
                        )
                }




















                Button(
                    onClick = {




                        navController.navigate("ReservacionCajones_extension/${nombre}/${company}/ ${cajon}/ ${piso}/${esEspecial}/${idEstacionamiento}",
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
                } // end button

                if (esEspecial) {
                    Text(text = "Cajón especial *", modifier = Modifier.padding(10.dp))
                } else {
                    Text(text = "Cajón normal", modifier = Modifier.padding(10.dp))
                }
            }
        }

    }
}