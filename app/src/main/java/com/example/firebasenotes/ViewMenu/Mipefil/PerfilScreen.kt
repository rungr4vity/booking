package com.example.firebasenotes.ViewMenu.Mipefil

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PerfilScreen(ddViewModel: DDViewModel = viewModel(), navController: NavController) {
    val userData = ddViewModel.state.value
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "",
                modifier = Modifier.size(190.dp)
            )
            Text(text = "Nombre:   ${userData.nombres}")
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "Apellido:   ${userData.apellidos}")
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "Email:   ${userData.email}")
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "Empresa:   ${userData.empresa}")
            Spacer(modifier = Modifier.size(100.dp))
            BTN(navController = navController)
        }
    }
}

@Composable
fun BTN(navController: NavController){
    Button(onClick = { navController.navigate("Actualizar")},) {
        Text(text = "MODIFICAR")
    }
}
