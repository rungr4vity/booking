package com.example.firebasenotes.UsersAdmin

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.firebasenotes.Viaticos.DataViaticos

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun detalleUser(
    navController: NavController,
    nombres: String,
    apellidos: String,
    empresa: String,
    email: String,
    puedeFacturar: Boolean,
    usuarioHabilitado: Boolean,
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
    val selectedTipoUsuario = remember { mutableStateOf("Seleccione un tipo") }
    val selectedType = remember { mutableStateOf(2) }
    val expandedType = remember { mutableStateOf(false) }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 40.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))

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

                Spacer(modifier = Modifier.height(20.dp))

                //DropdownMenu Para seleccionar tipo de factura

                val expandedFacturas = remember { mutableStateOf(false) }
                val opcionesFac = listOf("Puede facturar", "No puede facturar")
                val seleccionFac = remember { mutableStateOf("Seleccionar opción") }

                ExposedDropdownMenuBox(
                    expanded = expandedFacturas.value,
                    onExpandedChange = { expandedFacturas.value = it },
                    modifier = Modifier.padding(horizontal = 40.dp)
                ) {
                    OutlinedTextField(
                        value = seleccionFac.value,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Facturas") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedFacturas.value,
                        onDismissRequest = { expandedFacturas.value = false }) {
                        opcionesFac.forEach { opcion ->
                            DropdownMenuItem(
                                onClick = {
                                    seleccionFac.value = opcion
                                    expandedFacturas.value = false
                                }
                            ) {
                                Text(opcion)
                            }
                        }
                    }
                }

// Agregar la lógica if-else para establecer el valor booleano interno
                if (seleccionFac.value == "Puede facturar") {
                    seleccionFac.value = true.toString()
                } else if (seleccionFac.value == "No puede facturar") {
                    seleccionFac.value = false.toString()
                }


                Spacer(modifier = Modifier.height(20.dp))

                //DropdownMenu Para seleccionar tipo de usuario

                ExposedDropdownMenuBox(
                    expanded = expandedUsuario.value,
                    onExpandedChange = { expandedUsuario.value = it },
                    modifier = Modifier.padding(horizontal = 40.dp)
                ) {
                    OutlinedTextField(
                        value = seleccionUsuario.value,
                        onValueChange = { seleccionFac.value },
                        readOnly = false,
                        label = { androidx.compose.material.Text("Esta habilitado") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    DropdownMenu(
                        expanded = expandedUsuario.value,
                        onDismissRequest = { expandedUsuario.value = false }) {
                        opcionesUsuario.forEach { opcion ->
                            DropdownMenuItem(
                                onClick = {
                                    seleccionUsuario.value = opcion
                                    expandedUsuario.value = false
                                }
                            ) {
                                Text(opcion)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                ExposedDropdownMenuBox(
                    expanded = expandedType.value,
                    onExpandedChange = { expandedType.value = it },
                    modifier = Modifier.padding(horizontal = 40.dp)
                ) {
                    OutlinedTextField(
                        value = selectedTipoUsuario.value,
                        onValueChange = { selectedTipoUsuario.value = it },
                        readOnly = false,
                        label = { androidx.compose.material.Text("Tipo de Usuario") },
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
                Spacer(modifier = Modifier.height(60.dp))
                Button(onClick = {
                    val userData = DataViaticos(
                        nombres = nombres,
                        apellidos = apellidos,
                        empresa = empresa,
                        email = email,
                        puedeFacturar = seleccionFac.value.toBoolean(),
                        usuarioHabilitado = seleccionUsuario.value.toBoolean(),
                        typeId = selectedType.value
                    )
                    usersViewModel.updateUserData(userData) // Llamar a la función para actualizar los datos
                }) {
                    Text("Actualizar")
                }

            }
        }
    }
}

//
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun TypedDropdownMenu(dataList: List<DataTypeId>) {
//    val expanded = remember { mutableStateOf(false) }
//    val selectedDescripcion = remember { mutableStateOf("Seleccione una opción") }
//
//    ExposedDropdownMenuBox(
//        expanded = expanded.value,
//        onExpandedChange = { expanded.value = it },
//        modifier = Modifier.padding(horizontal = 40.dp)
//    ) {
//        OutlinedTextField(
//            value = selectedDescripcion.value,
//            onValueChange = { selectedDescripcion.value = it },
//            readOnly = false,
//            label = { androidx.compose.material.Text("Tipo de Usuario") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        androidx.compose.material.DropdownMenu(
//            expanded = expanded.value,
//            onDismissRequest = { expanded.value = false }
//        ) {
//            dataList.forEach { data ->
//                DropdownMenuItem(
//                    onClick = {
//                        selectedDescripcion.value = data.descripcion
//                        expanded.value = false
//                    }
//                ) {
//                    androidx.compose.material.Text(data.descripcion)
//                }
//            }
//        }
//    }
//}
//
//sealed class TypedDataState {
//    object Loading : TypedDataState()
//    data class Success(val dataList: List<DataTypeId>) : TypedDataState()
//    data class Error(val message: String) : TypedDataState()
//}
//
//class TypedViewModel : ViewModel() {
//    private val firestore = FirebaseFirestore.getInstance()
//
//    private val _typedData = MutableStateFlow<TypedDataState>(TypedDataState.Loading)
//    val typedData: StateFlow<TypedDataState> = _typedData
//
//    init {
//        fetchTypedData()
//    }
//
//    private fun fetchTypedData() {
//        firestore.collection("TypeId")
//            .get()
//            .addOnSuccessListener { result ->
//                val dataList = mutableListOf<DataTypeId>()
//                for (document in result) {
//                    val descripcion = document.getString("descripcion") ?: ""
//                    val typeId = document.getLong("typeId")?.toInt() ?: 0
//                    dataList.add(DataTypeId(descripcion, typeId))
//                }
//                _typedData.value = TypedDataState.Success(dataList)
//            }
//            .addOnFailureListener { exception ->
//                _typedData.value = TypedDataState.Error(exception.message ?: "Error desconocido")
//            }
//    }
//
//    fun updateTypeId(documentId: String, newTypeId: Int) {
//        firestore.collection("TypeId").document(documentId)
//            .update("typeId", newTypeId)
//            .addOnSuccessListener {
//                // Handle success if needed
//            }
//            .addOnFailureListener { exception ->
//                // Handle failure if needed
//            }
//    }
//}
