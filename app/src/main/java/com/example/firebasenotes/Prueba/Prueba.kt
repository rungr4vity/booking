package com.example.firebasenotes.Prueba

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DDViaticos(navController: NavController) {
    var fecha by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var emisor by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val menuItems = listOf("Deducible", "No deducible")
    var selectedItem by remember { mutableStateOf("Tipo de Gasto") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box() {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Tipo de Gasto") },
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                menuItems.forEach { menuItem ->
                    DropdownMenuItem(onClick = {
                        selectedItem = menuItem
                        expanded = false
                    }) {
                        Text(text = menuItem)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (selectedItem == "Deducible") {
            Column(Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.AddCircle, contentDescription = " ")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Adjunta XML")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.AddCircle, contentDescription = " ")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Adjunta Pdf")
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = monto,
                    onValueChange = { monto = it },
                    label = { Text("Monto") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = emisor,
                    onValueChange = { emisor = it },
                    label = { Text("Emisor") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                btn_EnviarGasto()
            }
        } else if (selectedItem == "No deducible") {
            Column(Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.AddCircle, contentDescription = " "
                    , modifier = Modifier.clickable {
                        navController.navigate("imgViaticos")
                        })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Adjunta Imagen")
                }
                Spacer(modifier = Modifier.height(24.dp))

                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = "Fecha",
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = "Monto",
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = "Descripción",
                    onValueChange = {  },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = "Emisor",
                    onValueChange = {  },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                btn_EnviarGasto()
            }
        }
    }
}

@Composable
fun btn_EnviarGasto() {
    Button(
        onClick = { /* Acción que deseas realizar al hacer clic en el botón */ },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
    ) {
        Text(text = "Enviar Gastos", color = Color.White)
    }
}
