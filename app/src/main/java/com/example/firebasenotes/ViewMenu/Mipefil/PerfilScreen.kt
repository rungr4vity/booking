package com.example.firebasenotes.ViewMenu.Mipefil

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PerfilScreen(userData: Data, navController: NavController) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.size(80.dp))
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "",
                modifier = Modifier.size(190.dp)
            )
            Text(text = "Nombre: ${userData.nombres} ${userData.apellidos}", fontSize = 18.sp, textAlign = TextAlign.End)
            Text(text = "Email: ${userData.email}", fontSize = 18.sp, textAlign = TextAlign.End)
            Text(text = "Empresa: ${userData.empresa}", fontSize = 18.sp, textAlign = TextAlign.End)
            Text(text = "Contraseña: ${userData.contrasena}", fontSize = 18.sp, textAlign = TextAlign.End)
            Spacer(modifier = Modifier.size(32.dp))
Row() {
    Text(
        text = "Mis reservaciones:",
        modifier = Modifier.clickable { navController.navigate("Mis reservas") },
        color = Color.Black
    )
Spacer(modifier = Modifier.size(10.dp))
    Text(
        text = "Estacionamiento",
        modifier = Modifier.clickable { navController.navigate("Mis reservas") },
        color = Color.Blue,
        textDecoration = TextDecoration.Underline
    )
    Spacer(modifier = Modifier.size(10.dp))
    Text(
        text = "Oficina",
        modifier = Modifier.clickable { navController.navigate("Mis reservasOficina") },
        color = Color.Blue,
        textDecoration = TextDecoration.Underline
    )
}
            Spacer(modifier = Modifier.size(5.dp))
            BTN(navController = navController)
        }
    }
}

@Composable
fun BTN(navController: NavController){
    Button(onClick = { navController.navigate("Actualizar")},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        colors = ButtonDefaults.buttonColors( Color(0xFF800000))
    ) {
        Text(text = "Modificar")
    }
}
