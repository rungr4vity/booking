package com.example.firebasenotes.Viaticos

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.firebasenotes.UsersAdmin.UsersViewModel
import java.util.Date
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ViaticosScreen(
    viaticosViewModel: ViaticosViewModel = viewModel(),
    navController: NavController,
    usersViewModel: UsersViewModel = viewModel() // Agrega esta línea
) {
    val gastosTotales: Double by viaticosViewModel.gastosTotales.observeAsState(0.0)

    LaunchedEffect(Unit) {
        viaticosViewModel.backButton()
    }

    val viaticos = viaticosViewModel.viaticos.value
    val viajes = viaticosViewModel.viajes.value

    var mensajeVisible by remember { mutableStateOf(false) }
    var confirmarCierre by remember { mutableStateOf(false) }

    if (viaticos.puedeFacturar == true) {
        Scaffold {
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
                Spacer(modifier = Modifier.height(40.dp))
                Text(text = "Destino: ${viajes.destino}", fontSize = 13.sp, color = Color.Gray)
                Text(text = "Cliente: ${viajes.cliente}", fontSize = 13.sp)

                Spacer(modifier = Modifier.height(255.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (viaticos.tieneViajeActivo == true) {
                        Button(
                            onClick = {
                                mensajeVisible = true
                            },
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
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 16.dp, vertical = 16.dp),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.Bottom
//                        ) {
//                            Box(
//                                modifier = Modifier.weight(1.8f),
//                                contentAlignment = Alignment.BottomStart
//                            ) {
//                                Text(
//                                    "Cerrar Viaje",
//                                    modifier = Modifier.clickable { mensajeVisible = false },
//                                    color = Color.Blue,
//                                    textDecoration = TextDecoration.Underline
//                                )
//                            }
//
//                            Box(
//                                modifier = Modifier.weight(8f),
//                                contentAlignment = Alignment.BottomEnd
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Default.AddCircle,
//                                    contentDescription = "",
//                                    modifier = Modifier
//                                        .size(70.dp)
//                                        .clickable { navController.navigate("viaticosAdd/{viajeId}") }
//                                )
//                            }
//                        }


                        if (mensajeVisible) {
                            Snackbar(
                                action = {
                                    Button(onClick = {
                                        confirmarCierre = true
                                        mensajeVisible = false
                                    }) {
                                        Text("Aceptar")
                                    }
                                },
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text("¿Desea cerrar el viaje?")
                            }
                        }

                        if (confirmarCierre) {
                            LaunchedEffect(Unit) {
                                usersViewModel.updateTieneViajeActivo(false)
                                confirmarCierre = false // Reset state after confirmation
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.height(100.dp))
                        Button(
                            onClick = { navController.navigate("Crear viaje") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp),
                            colors = ButtonDefaults.buttonColors( Color(0xFF800000))
                        ) {
                            Text("Crear Viaje")
                        }
                    }
                }
            }
        }
    } else {
        Descr()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Descr(viaticosViewModel: ViaticosViewModel = viewModel()) {
    val viaticos = viaticosViewModel.viaticos.value
    Scaffold {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "¡Buen Día!, Bienvenido", fontSize = 13.sp)
            Text(text = "Hoy es: ", fontSize = 11.sp, color = Color.Gray)
            Text(text = "${viaticos.nombres} ${viaticos.apellidos}", fontSize = 13.sp)
            Spacer(modifier = Modifier.height(240.dp))
            Text(text = "Usted no tiene viajes activos")
        }
    }
}

