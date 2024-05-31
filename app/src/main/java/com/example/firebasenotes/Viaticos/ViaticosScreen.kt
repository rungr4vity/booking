package com.example.firebasenotes.Viaticos

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ViaticosScreen(viaticosViewModel: ViaticosViewModel = viewModel(),navController: NavController) {

    val gastosTotales: Double by viaticosViewModel.gastosTotales.observeAsState(0.0)

    LaunchedEffect(Unit) {
        viaticosViewModel.backButton()
    }

//    BackHandler {
//       gastos = viaticosViewModel.gastosTotales.value
//    }

    //var gastosTotales = viaticosViewModel.gastosTotales.value
    val viaticos = viaticosViewModel.viaticos.value
    val viajes = viaticosViewModel.viajes.value
    var MensajeVisible by remember { mutableStateOf(false)}

         //false // original
        if (viaticos.puedeFacturar == true) {
        Scaffold() {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "¡Buen Día!, Bienvenido", fontSize = 13.sp)
                Text(text = "Hoy es: ${Date().toString()}", fontSize = 11.sp, color = Color.Gray)
                Text(text = "${viaticos.nombres} ${viaticos.apellidos}", fontSize = 13.sp)
                Text(text = "Desde: ${viajes.fechaInicio.toDate()}", fontSize = 13.sp)
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (viaticos.tieneViajeActivo == true) {

                        Card(modifier = Modifier.size(120.dp)) {
                            Column(
                                modifier = Modifier.padding(13.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Presupuesto", fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(40.dp))
                                Text(text = viajes.presupuesto.dec().toString(), fontSize = 18.sp)
                            }
                        }
                        Card(modifier = Modifier.size(120.dp)) {
                            Column(
                                modifier = Modifier.padding(13.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Saldo", fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(40.dp))
                                Text(text = "$ ${gastosTotales.dec()}", fontSize = 18.sp)
                            }
                        }
                    }
                }
                Text(text = "Destino: ${viajes.destino}", fontSize = 13.sp, color = Color.Gray)
                Text(text = "Cliente: ${viajes.cliente}", fontSize = 13.sp)

                Spacer(modifier = Modifier.height(205.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (viaticos.tieneViajeActivo == true) {
                        Button(
                            onClick = { MensajeVisible = true  },
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Text("Cerrar Viaje")
                        }
                        Button(
                            onClick = {
                                navController.navigate("viaticosAdd/${viajes.id}")
                                      },
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Text("Agregar Gasto")
                        }

                        if (MensajeVisible) {
                            Snackbar(
                                action = {
                                    Button(onClick = { MensajeVisible = false }) {
                                        Text("Aceptar")
                                    }
                                },
                                modifier = Modifier
                            ) {
                                Text("¿Desea cerrar el viaje?")
                            }

                        }
                    } else {
                        Button(
                            onClick = {  },
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Text("Abrir Viaje")
                        }

                    } // fin del else
                }
            }
        }
    } else {
        // Si el usuario no puede facturar, no mostramos ninguna funcionalidad
        Descr()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Descr(viaticosViewModel: ViaticosViewModel= viewModel()){
    val viaticos = viaticosViewModel.viaticos.value
    Scaffold() {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "¡Buen Día!, Bienvenido ${viaticos.nombres} ${viaticos.apellidos}", fontSize = 13.sp)
            Text(text = "Hoy es: ${Date().toString()}", fontSize = 11.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(240.dp))
            Text(text = "No se encontro ningun viaje activo")
        }
    }
}
