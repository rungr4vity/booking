//package com.example.firebasenotes.UsersAdmin
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.DropdownMenuItem
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.ExposedDropdownMenuBox
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.Button
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ExposedDropdownMenuDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.example.firebasenotes.Viaticos.DataViaticos
//import com.example.firebasenotes.ViewMenu.Mipefil.DDViewModel
//import com.example.firebasenotes.pruebass.DataTypeId
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//
//
//@SuppressLint("StateFlowValueCalledInComposition")
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun detalleUser(
//    navController: NavController,
//    nombres: String,
//    apellidos: String,
//    empresa: String,
//    email: String,
//    puedeFacturar: Boolean,
//    usuarioHabilitado: Boolean,
//    usersViewModel: UsersViewModel = viewModel(),
//    typedViewModel: TypedViewModel = viewModel()
//) {
//
//    val expandedFacturas = remember { mutableStateOf(false) }
//    val opcionesFac = listOf("true", "false")
//    val seleccionFac = remember { mutableStateOf(puedeFacturar.toString()) }
//
//    val expandedUsuario = remember { mutableStateOf(false) }
//    val opcionesUsuario = listOf("true", "false")
//    val seleccionUsuario = remember { mutableStateOf(usuarioHabilitado.toString()) }
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        item {
//            Icon(
//                imageVector = Icons.Default.Person,
//                contentDescription = "",
//                modifier = Modifier.size(100.dp) // Tamaño ajustado
//            )
//            TextField(
//                value = "$nombres $apellidos",
//                onValueChange = { }
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            TextField(
//                value = empresa,
//                onValueChange = { }
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            TextField(
//                value = email,
//                onValueChange = { }
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            Spacer(modifier = Modifier.height(20.dp))
//
//            ExposedDropdownMenuBox(
//                expanded = expandedFacturas.value,
//                onExpandedChange = { expandedFacturas.value = it },
//                modifier = Modifier.padding(horizontal = 40.dp)
//            ) {
//                OutlinedTextField(
//                    value = seleccionFac.value,
//                    onValueChange = { seleccionFac.value = it },
//                    readOnly = false,
//                    label = { Text("Puede Facturar") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                DropdownMenu(
//                    expanded = expandedFacturas.value,
//                    onDismissRequest = { expandedFacturas.value = false }) {
//                    opcionesFac.forEach { opcion ->
//                        DropdownMenuItem(
//                            onClick = {
//                                seleccionFac.value = opcion
//                                expandedFacturas.value = false
//                            }
//                        ) {
//                            Text(opcion)
//                        }
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            ExposedDropdownMenuBox(
//                expanded = expandedUsuario.value,
//                onExpandedChange = { expandedUsuario.value = it },
//                modifier = Modifier.padding(horizontal = 40.dp)
//            ) {
//                OutlinedTextField(
//                    value = seleccionUsuario.value,
//                    onValueChange = { seleccionUsuario.value = it },
//                    readOnly = false,
//                    label = { Text("Usuario Habilitado") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                DropdownMenu(
//                    expanded = expandedUsuario.value,
//                    onDismissRequest = { expandedUsuario.value = false }) {
//                    opcionesUsuario.forEach { opcion ->
//                        DropdownMenuItem(
//                            onClick = {
//                                seleccionUsuario.value = opcion
//                                expandedUsuario.value = false
//                            }
//                        ) {
//                            Text(opcion)
//                        }
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//            TypedDropdownMenu(dataList = when (val typedDataState = typedViewModel.typedData.value) {
//                is TypedDataState.Success -> typedDataState.dataList
//                else -> emptyList() // Maneja el estado de carga o error como prefieras
//            })
//
//            Button(onClick = {
//                val userData = DataViaticos(
//                    nombres = nombres,
//                    apellidos = apellidos,
//                    empresa = empresa,
//                    email = email,
//                    puedeFacturar = seleccionFac.value.toBoolean(),
//                    usuarioHabilitado = seleccionUsuario.value.toBoolean()
//                )
//                usersViewModel.updateUserData(userData)
//            }) {
//                Text("Actualizar")
//            }
//
//
//        }
//    }}
//
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun TypedDropdownMenu(dataList: List<DataTypeId>) {
//    val expanded = remember { mutableStateOf(false) }
//    val selectedDescripcion = remember { mutableStateOf("") }
//
//    ExposedDropdownMenuBox(
//        expanded = expanded.value,
//        onExpandedChange = { expanded.value = it },
//        modifier = Modifier.padding(horizontal = 40.dp)
//    ) {
//        androidx.compose.material.OutlinedTextField(
//            value = selectedDescripcion.value,
//            onValueChange = { selectedDescripcion.value = it },
//            readOnly = false,
//            label = { androidx.compose.material.Text("Seleccione una opción") },
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
