package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
            suma = (localDate.dayOfMonth + localDate.month.value + localDate.year)
        }
        val opsHorarios: List<horariosModel> by viewModel.horarios.observeAsState(listOf())
        var menHorarios by remember { mutableStateOf(opsHorarios) }

        Column(modifier = Modifier
            .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = com.example.firebasenotes.R.drawable.ofi2), // Reemplaza 'your_image' con el nombre de tu imagen
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterHorizontally)


            )
            Spacer(modifier = Modifier.size(30.dp))
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

                var esEspecial_string = if (esEspecial) "Si" else "No"
                //Text(text = "Pertenece a: $esEspecial_string", modifier = Modifier.padding(10.dp))
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
            Spacer(modifier = Modifier.size(50.dp))
                Button(
                    onClick = {
                        if(!esEspecial){
                            navController.navigate("ReservacionCajones_extension/${nombre}/${company}/ ${cajon}/ ${piso}/${esEspecial}/${idEstacionamiento}",
                                navOptions { // Use the Kotlin DSL for building NavOptions
                                    anim {
                                        enter = R.animator.fade_in
                                        exit = R.animator.fade_out
                                    }
                                })
                        } else {
                            Toast.makeText(
                                context,
                                "Cajón especial no disponible",
                                Toast.LENGTH_LONG
                            ).show()
                        }




                        },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF800000))
                ) {
                    Text(text = "Reservar ${cajon}", color = Color.White)
                } // end button

                if (esEspecial) {
                    Text(text = "Cajón especial *", modifier = Modifier.padding(10.dp))
                } else {
                    Text(text = "Cajón normal", modifier = Modifier.padding(10.dp))
                }
            }
        }

    }
