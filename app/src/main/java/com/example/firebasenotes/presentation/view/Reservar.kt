package com.example.firebasenotes.presentation.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Reservar() {

    MyReservas()
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyReservas() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        var menHorarios by remember { mutableStateOf("Seleccionar su horarios") }
        var menEmpresa by remember { mutableStateOf("Isita") }
        var menEspacios by remember { mutableStateOf("Seleccionar su espacio") }
        var emailPrueba by remember { mutableStateOf("admin@ut.com") }

        var expansion_empresa by remember { mutableStateOf(false) }
        var expansion_Horarios by remember { mutableStateOf(false) }
        var expansion_Espacios by remember { mutableStateOf(false) }

        val opsHorarios = listOf("9:00 a.m - 1:00 p.m", "1:00 p.m - 6:00 p.m", "Todo el dia")
        val opsEmpresa = listOf("Isita", "Verifigas")
        val opsEspacios = listOf("441", "443","167","316 (P.Aguirre)","310 (A.Garza)")

        OutlinedTextField(value = emailPrueba, onValueChange = {},
            modifier = Modifier
                .fillMaxWidth())


        ExposedDropdownMenuBox(expanded = expansion_empresa, onExpandedChange = {expansion_empresa = !expansion_empresa} ) {
            OutlinedTextField(
                value = menEmpresa,
                onValueChange = {  },//cambia el valor
                label = { Text("Compañia") },
                readOnly = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expansion_empresa)}
            )
            ExposedDropdownMenu(expanded = expansion_empresa, onDismissRequest = { expansion_empresa= false }) {
                opsEmpresa.forEach { option ->
                    DropdownMenuItem(text = { Text(option)}, onClick = {
                        menEmpresa = option
                        expansion_empresa = false
                    },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        ExposedDropdownMenuBox(expanded = expansion_Horarios , onExpandedChange = {expansion_Horarios  = !expansion_Horarios } ) {
            OutlinedTextField(
                value = menHorarios,
                onValueChange = {  },
                label = { Text("Horario") },
                readOnly = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expansion_Horarios )}
            )
            ExposedDropdownMenu(expanded = expansion_Horarios , onDismissRequest = { expansion_Horarios  = false }) {
                opsHorarios.forEach { option ->
                    DropdownMenuItem(text = { Text(option)}, onClick = {
                        menHorarios = option
                        expansion_Horarios  = false
                    },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }


        ExposedDropdownMenuBox(expanded = expansion_Espacios, onExpandedChange = {expansion_Espacios = !expansion_Espacios} ) {
            OutlinedTextField(
                value = menEspacios,
                onValueChange = {  },
                label = { Text("Espacios") },
                readOnly = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expansion_Espacios)}
            )
            ExposedDropdownMenu(expanded = expansion_Espacios, onDismissRequest = { expansion_Espacios = false }) {
                opsEspacios.forEach { option ->
                    DropdownMenuItem(text = { Text(option)}, onClick = {
                        menEspacios = option
                        expansion_Espacios= false
                    },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Aceptar")

        }


    }


}

