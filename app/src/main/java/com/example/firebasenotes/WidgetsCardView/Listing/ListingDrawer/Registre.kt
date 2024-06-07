package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Registre(){
    Scaffold() {
        ComponentRegistrar()
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ComponentRegistrar(drawerViewModel: DrawerViewModel = viewModel()) {
    var  nombre_cajon by remember { mutableStateOf("") }
    var  num_cajon by remember { mutableStateOf("") }
    var piso_edificio by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var esEspecial by remember { mutableStateOf("") }
    var img by remember { mutableStateOf("") }

    var selectedOptionText  by remember { mutableStateOf("Empresa") }
    var expanded  by remember { mutableStateOf(false) }
    val options = listOf("Isita", "Verifigas")

    var context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        TextField(
            value =  nombre_cajon,
            onValueChange = {  nombre_cajon = it },
            label = { Text("Nombre") }
            ,modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
        )
Spacer(modifier = Modifier.padding(5.dp))
        TextField(
            value =  num_cajon,
            onValueChange = {  num_cajon = it },
            label = { Text("Numero") }
            ,modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        TextField(
            value =  piso_edificio,
            onValueChange = {  piso_edificio = it },
            label = { Text("Piso") }
            ,modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        androidx.compose.material3.ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = selectedOptionText,
                onValueChange = { },
                label = { androidx.compose.material3.Text("Compañia") },
                readOnly = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { androidx.compose.material3.Text(option) },
                        onClick = {
                        selectedOptionText = option
                        expanded = false
                    },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(140.dp))

        Button(
            onClick = { drawerViewModel.insertarDatos(context,num_cajon.toInt(),nombre_cajon ,piso_edificio,selectedOptionText
            ,esEspecial = false)
                      num_cajon = ""
                      nombre_cajon = ""
                      piso_edificio = ""
                      selectedOptionText = "Empresa"
                      },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            colors = ButtonDefaults.buttonColors( Color(0xFF800000))

        ) {
            Text("Insertar Datos", color =Color.White)
        }
    }
}

