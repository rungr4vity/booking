package com.example.firebasenotes.UsersAdmin

import android.annotation.SuppressLint
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp@OptIn(ExperimentalMaterialApi::class)

@Preview
@Composable
fun DropdownTextFieldExample() {
    var expanded by remember { mutableStateOf(false) }
    var rol by remember { mutableStateOf("Usuario") }
    var valor by remember { mutableStateOf(2) }
    val selectedDescripcion = remember { mutableStateOf("Seleccione una opción") }

    // Mapa que asocia cada opción con su valor entero
    val rolMap = mapOf(
        "Administrador" to 1,
        "Usuario" to 2,
        "Otro" to 3
    )

    Column(modifier = Modifier.fillMaxSize()) {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
            OutlinedTextField(value = selectedDescripcion.value, onValueChange = {})
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                rolMap.keys.forEach { opc ->
                    DropdownMenuItem(
                        onClick = {
                            selectedDescripcion.value = opc
                            rol = opc
                            valor = rolMap[opc] ?: 0 // Obtener el valor correspondiente al rol seleccionado
                            expanded = false
                        }
                    ) {
                        Text(text = opc)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Caja de texto para mostrar el tipo de usuario y su valor correspondiente
        Text(text = "Tipo de usuario: $rol - Valor: $valor")
    }
}
