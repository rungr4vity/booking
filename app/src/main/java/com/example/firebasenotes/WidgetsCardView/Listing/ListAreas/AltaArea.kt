package com.example.firebasenotes.WidgetsCardView.Listing.ListAreas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun AltaArea(areaViewModel: AreaViewModel = viewModel(), navController: NavController) {
    var desc by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var capacidad by remember { mutableStateOf("") }
    var mobiliaria by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        LimitedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = "Nombre sala",
            maxLength = MAX_LENGTH_NOMBRE,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 5.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LimitedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = "Descripción",
            maxLength = MAX_LENGTH_DESC,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 5.dp),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        LimitedTextField(
            value = capacidad,
            onValueChange = { capacidad = it },
            label = "Cantidad de personas",
            maxLength = MAX_LENGTH_CAPACIDAD,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 5.dp),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        LimitedTextField(
            value = mobiliaria,
            onValueChange = { mobiliaria = it },
            label = "Mobiliario",
            maxLength = MAX_LENGTH_MOBILIARIA,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 5.dp),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(250.dp))
        Button(
            onClick = {
                if (nombre.isNotEmpty() && desc.isNotEmpty() && capacidad.isNotEmpty() && mobiliaria.isNotEmpty()) {
                    areaViewModel.InsertDatosArea(capacidad, nombre, mobiliaria, desc)
                    capacidad = ""
                    nombre = ""
                    mobiliaria = ""
                    desc = ""
                }
            },
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().height(45.dp),
            enabled = nombre.isNotEmpty() && desc.isNotEmpty() && capacidad.isNotEmpty() && mobiliaria.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(Color(0xFF800000))
        ) {
            Text("Crear área", color = Color.White)
        }
    }
}

private const val MAX_LENGTH_NOMBRE = 25
private const val MAX_LENGTH_DESC = 30
private const val MAX_LENGTH_CAPACIDAD = 3
private const val MAX_LENGTH_MOBILIARIA = 20

@Composable
fun LimitedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    maxLength: Int,
    modifier: Modifier = Modifier,
    maxLines: Int = 1
) {
    val trimmedValue = if (value.length > maxLength) {
        value.substring(0, maxLength)
    } else {
        value
    }

    TextField(
        value = trimmedValue,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
        label = { Text(label) },
        modifier = modifier,
        maxLines = maxLines
    )
}
