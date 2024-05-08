package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.google.firebase.components.ComponentRegistrar
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


    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value =  nombre_cajon,
            onValueChange = {  nombre_cajon = it },
            label = { Text("Nombre") }
            ,modifier = Modifier.fillMaxWidth().padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
        )

        TextField(
            value =  num_cajon,
            onValueChange = {  num_cajon = it },
            label = { Text("Numero") }
            ,modifier = Modifier.fillMaxWidth().padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
        )
        TextField(
            value =  piso_edificio,
            onValueChange = {  piso_edificio = it },
            label = { Text("Piso") }
            ,modifier = Modifier.fillMaxWidth().padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
        )



        Button(
            onClick = { drawerViewModel.insertarDatos( num_cajon,nombre_cajon ,piso_edificio) },
            modifier = Modifier.padding(top = 16.dp),

        ) {
            Text("Insertar Datos")
        }
    }
}

