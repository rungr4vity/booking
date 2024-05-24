package com.example.firebasenotes.Viaticos

import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
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


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ViaticosScreen(viaticosViewModel: ViaticosViewModel = viewModel(),navController: NavController) {
    val viaticos = viaticosViewModel.viaticos.value
    var MensajeVisible by remember { mutableStateOf(false)}

        if (viaticos.puedeFacturar == true) {
        Scaffold() {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "¡Buen Día!, Bienvenido", fontSize = 13.sp)
                Text(text = "Hoy es: 20/05/2024 ", fontSize = 11.sp, color = Color.Gray)
                Text(text = "${viaticos.nombres} ${viaticos.apellidos}", fontSize = 13.sp)
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (viaticos.tieneViajeActivo == true) {

                        Card(modifier = Modifier.size(120.dp)) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Presupuesto", fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(48.dp))
                                Text(text = "$1323.23", fontSize = 13.sp)
                            }
                        }
                        Card(modifier = Modifier.size(120.dp)) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Saldo", fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(48.dp))
                                Text(text = "$0", fontSize = 13.sp)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(205.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (viaticos.tieneViajeActivo == true) {
                        Button(
                            onClick = { MensajeVisible= true  },
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Text("Cerrar Viaje")
                        }
                        Button(
                            onClick = { navController.navigate("viaticosAdd")  },
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

                    }
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
            Text(text = "¡Buen Día!, Bienvenido", fontSize = 13.sp)
            Text(text = "Hoy es: 17/05/2024 ", fontSize = 11.sp, color = Color.Gray)
            Text(text = "${viaticos.nombres} ${viaticos.apellidos}", fontSize = 13.sp)
            Spacer(modifier = Modifier.height(240.dp))
            Text(text = "AQUI DEBE DE IR CARDS Y BOTONES")
        }
    }
}
