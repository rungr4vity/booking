package com.example.firebasenotes.Oficinas
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
fun DetalleOficinas(
    navController: NavController,
    context: Context,
    capacidad: String,
    descripcion: String,
    id: String,
    mobilaria: String,
    nombre: String,
    idArea: String,
    viewModel: OficinasViewModel = OficinasViewModel()
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
        //val opsHorarios: List<horariosModel> by viewModel.horarios.observeAsState(listOf())
        //var menHorarios by remember { mutableStateOf(opsHorarios) }

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
                Text(text = " $nombre", modifier = Modifier.padding(10.dp))
                Text(text = "Capacidad: ${capacidad}", modifier = Modifier.padding(10.dp))
                Text(text = "$descripcion", modifier = Modifier.padding(10.dp))
                Text(
                    text = "Disponible",
                    fontWeight = FontWeight.Medium, fontSize = 15.sp, color = Color.Blue,
                    modifier = Modifier.padding(10.dp)
                )

                Button(
                    onClick = {
                        navController.navigate("ReservaOficinas_extension/${capacidad}/${descripcion}/${id}/ ${mobilaria}/ ${nombre}/ ${idArea}",
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
                    Text(text = "Reservar ${nombre}")
                } // end button


            }
        }

    }
}