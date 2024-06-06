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
fun AltaArea(areaViewModel: AreaViewModel = viewModel(),navController: NavController) {

    var desc by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var capacidad by remember { mutableStateOf("") }
    var mobiliaria by remember { mutableStateOf("") }



    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally ) {
Spacer(modifier = Modifier.height(16.dp))
        TextField(value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre sala") }
            ,modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp))
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = desc, onValueChange = { desc = it },
            label = { Text("Descripcion") }
            ,modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp))
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = capacidad, onValueChange = { capacidad = it },
            label = { Text("Cantidad de personas ") }
            ,modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp))
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = mobiliaria, onValueChange = { mobiliaria = it },
            label = { Text("Mobiliario") }
            ,modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp))
        Spacer(modifier = Modifier.height(250.dp))
        Button(
            onClick = {
                areaViewModel.InsertDatosArea(capacidad, nombre,mobiliaria,desc)
                capacidad=""
                nombre=""
                mobiliaria=""
                desc=""
                      },

            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().size(45.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF800000))

            ) {
            androidx.compose.material.Text("Crear area", color = Color.White)
        }
    }

}
