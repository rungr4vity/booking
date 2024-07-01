package com.example.firebasenotes.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasenotes.viewModel.UsersViewModel
import com.example.firebasenotes.viewModel.ViajeViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MotivosYClientesScreen(viewModel: ViajeViewModel = viewModel(), usersViewModel: UsersViewModel = viewModel()) {
    val motivosViaje by viewModel.motivosViaje.collectAsState()
    val clientes by viewModel.clientes.collectAsState()
    var selectedMotivo by remember { mutableStateOf("") }
    var selectedCliente by remember { mutableStateOf("") }
    var presupuesto by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            ExposedDropdownMenuBoxMotivos(motivosViaje, selectedMotivo) { selectedMotivo = it }
            Spacer(modifier = Modifier.height(10.dp))
            ExposedDropdownMenuBoxClientes(clientes, selectedCliente) { selectedCliente = it }
            Spacer(modifier = Modifier.height(10.dp))
            UserInputFields(presupuesto, destino, { presupuesto = it }, { destino = it })
            Spacer(modifier = Modifier.height(310.dp))
            Button(
                onClick = {
                    viewModel.GuardarViaje(
                        viaje(
                            motivo = selectedMotivo,
                            cliente = selectedCliente,
                            presupuesto = presupuesto,
                            destino = destino,
                            activo = true
                        )
                    )
                    usersViewModel.updateTieneViajeActivo(true)
                    selectedMotivo = ""
                    selectedCliente = ""
                    presupuesto = ""
                    destino = ""
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Viaje creado")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF800000)
                )
            ) {
                Text(text = "Crear viaje", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownMenuBoxMotivos(motivosViaje: List<MotivosViaje>, selectedMotivo: String, onMotivoSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedMotivo,
            onValueChange = { onMotivoSelected(it) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            label = { Text("Motivo de viaje") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            motivosViaje.forEach { motivo ->
                DropdownMenuItem(onClick = {
                    onMotivoSelected(motivo.motivo)
                    expanded = false
                }) {
                    Text(text = motivo.motivo)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownMenuBoxClientes(clientes: List<Clientes>, selectedCliente: String, onClienteSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedCliente,
            onValueChange = { onClienteSelected(it) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            label = { Text("Cliente") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            clientes.forEach { cliente ->
                DropdownMenuItem(onClick = {
                    onClienteSelected(cliente.cliente)
                    expanded = false
                }) {
                    Text(text = cliente.cliente)
                }
            }
        }
    }
}

@Composable
fun UserInputFields(
    presupuesto: String,
    destino: String,
    onPresupuestoChange: (String) -> Unit,
    onDestinoChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = presupuesto,
            onValueChange = onPresupuestoChange,
            label = { Text("Presupuesto") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = destino,
            onValueChange = onDestinoChange,
            label = { Text("Destino") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun crearViaje() {
    MotivosYClientesScreen()
}
