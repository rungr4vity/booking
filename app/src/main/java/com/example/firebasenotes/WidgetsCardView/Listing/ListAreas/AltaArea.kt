package com.example.firebasenotes.WidgetsCardView.Listing.ListAreas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun AltaArea(areaViewModel: AreaViewModel = viewModel(),navController: NavController) {

    var desc by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var capacidad by remember { mutableStateOf("") }
    var mobiliaria by remember { mutableStateOf("") }



    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally ) {

        TextField(value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
            ,modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp))

        TextField(value = desc, onValueChange = { desc = it },
            label = { Text("Descripcion") }
            ,modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp))


        TextField(value = capacidad, onValueChange = { capacidad = it },
            label = { Text("Capacidad de personas ") }
            ,modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp))

        TextField(value = mobiliaria, onValueChange = { mobiliaria = it },
            label = { Text("Moobiliaria") }
            ,modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp))

        Button(
            onClick = { areaViewModel.InsertDatosArea(capacidad, nombre,mobiliaria,desc) },
            modifier = Modifier.padding(top = 16.dp),

            ) {
            androidx.compose.material.Text("Insertar Datos")
        }
    }

}
