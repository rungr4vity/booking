package com.example.firebasenotes.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.firebasenotes.viewModel.UsersViewModel
import com.example.firebasenotes.models.DataViaticos

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun detalleUser(
    navController: NavController,
    nombres: String,
    apellidos: String,
    empresa: String,
    email: String,
    puedeFacturar: Boolean,
    usuarioHabilitado: Boolean,
    typeId: Int,
    usersViewModel: UsersViewModel = viewModel(),
//    typedViewModel: TypedViewModel = viewModel()
) {
    val expandedUsuario = remember { mutableStateOf(false) }
    val opcionesUsuario = listOf("true", "false")
    val seleccionUsuario = remember { mutableStateOf("Usuario Habilitado") }

    val tiposUsuario = listOf(
        "Normal",
        "SubAdministrador",
        "Administrador"
    ) // Agrega los tipos de usuario que necesites

    var title = ""

    title = when (typeId) {
        0 -> "Administrador"
        1 -> "SubAdministrador"
        else -> "Normal"
    }


    val selectedTipoUsuario = remember { mutableStateOf(title) }
    val selectedType = remember { mutableStateOf(typeId.toInt()) }
    val expandedType = remember { mutableStateOf(false) }
    CompositionLocalProvider(LocalContentColor provides Color.Black) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 90.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "",
                    modifier = Modifier.size(100.dp) // Tamaño ajustado
                )
                Text(
                    text = "$nombres $apellidos",
                    modifier = Modifier.padding(top = 16.dp),
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = email,
                    modifier = Modifier.padding(top = 8.dp),
                    style = TextStyle(fontSize = 14.sp)
                )
                Text(
                    text = empresa,
                    modifier = Modifier.padding(top = 8.dp),
                    style = TextStyle(fontSize = 14.sp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                //DropdownMenu Para seleccionar tipo de factura

                var expandedFacturas by remember { mutableStateOf(false) }
                var opcionesFac = listOf("true", "false")
                var selectedOptionText by remember { mutableStateOf(puedeFacturar.toString()) }



                androidx.compose.material3.ExposedDropdownMenuBox(
                    expanded = expandedFacturas,
                    onExpandedChange = { expandedFacturas = !expandedFacturas },
                    modifier = Modifier.padding(horizontal = 40.dp)) {
                    OutlinedTextField(
                        value = selectedOptionText,
                        onValueChange = { },
                        label = { Text("Facturas",color = Color.Black) },
                        readOnly = false,
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFacturas) }
                    )
                    ExposedDropdownMenu(
                        expanded = expandedFacturas,
                        onDismissRequest = { expandedFacturas = false }) {
                        opcionesFac.forEach { option ->
                            androidx.compose.material3.DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedOptionText = option
                                    expandedFacturas = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
// Agregar la lógica if-else para establecer el valor booleano interno


                Spacer(modifier = Modifier.height(10.dp))

                //DropdownMenu Para seleccionar tipo de usuario
                var expandedUsuario by remember { mutableStateOf(false) }
                var opcionesUsa = listOf("true", "false")
                var selectedOption by remember { mutableStateOf(usuarioHabilitado.toString()) }

                androidx.compose.material3.ExposedDropdownMenuBox(
                    expanded = expandedUsuario,
                    onExpandedChange = { expandedUsuario = !expandedUsuario },
                    modifier = Modifier.padding(horizontal = 40.dp)) {
                    OutlinedTextField(
                        value = selectedOption,
                        onValueChange = { },
                        label = { Text("Habilitado",color = Color.Black) },
                        readOnly = false,
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedUsuario) }
                    )
                    ExposedDropdownMenu(
                        expanded = expandedUsuario,
                        onDismissRequest = { expandedUsuario = false }) {
                        opcionesUsa.forEach { option ->
                            androidx.compose.material3.DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedOption = option
                                    expandedUsuario = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                ExposedDropdownMenuBox(
                    expanded = expandedType.value,
                    onExpandedChange = { expandedType.value = it },
                    modifier = Modifier.padding(horizontal = 40.dp)
                ) {
                    OutlinedTextField(
                        value = selectedTipoUsuario.value,
                        onValueChange = { selectedTipoUsuario.value = it },
                        readOnly = false,
                        label = { androidx.compose.material.Text("Tipo de Usuario", color = Color.Black) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedType.value,
                        onDismissRequest = { expandedType.value = false }) {
                        tiposUsuario.forEach { tipo ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedTipoUsuario.value = tipo
                                    expandedType.value = false
                                    // Actualizar selectedType según el tipo de usuario seleccionado
                                    selectedType.value = when (tipo) {
                                        "SubAdministrador" -> 1 // Asignar el typeId correspondiente
                                        "Administrador" -> 0
                                        else -> 2
                                    }
                                }
                            ) {
                                Text(tipo)
                            }
                        }
                    }
                }
//            TypedDropdownMenu(dataList = when (val typedDataState = typedViewModel.typedData.value) {
//                is TypedDataState.Success -> typedDataState.dataList
//                else -> emptyList() // Maneja el estado de carga o error como prefieras
//            })
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = {
                        val userData = DataViaticos(
                            nombres = nombres,
                            apellidos = apellidos,
                            empresa = empresa,
                            email = email,
                            puedeFacturar = selectedOptionText.toBoolean(),
                            usuarioHabilitado = selectedOption.toBoolean(),
                            typeId = selectedType.value
                        )
                        usersViewModel.updateUserData(userData) // Llamar a la función para actualizar los datos
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF800000)
                    )
                ) {

                    Text("Actualizar", color = Color.White)
                }

            }
        }
    }}
}

