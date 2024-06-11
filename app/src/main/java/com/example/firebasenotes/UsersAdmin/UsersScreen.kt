package com.example.firebasenotes.UsersAdmin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.firebasenotes.R
import com.example.firebasenotes.Viaticos.DataViaticos


@Composable
fun listUsers(usersViewModel: UsersViewModel = viewModel(), navController: NavController) {
    val listUser = usersViewModel.stateUsers.value
    LazyColumn {
        items(listUser) { user ->
            val navigateToUserDetail: () -> Unit = {
                navController.navigate("detalleUser/${user.nombres}/${user.apellidos}/${user.empresa}/${user.email}/${user.puedeFacturar}/${user.usuarioHabilitado}/${user.typeId}")
            }
            ComponentList(use = user, navigateToUserDetail)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentList(use : DataViaticos, navigateToUserDetail: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { navigateToUserDetail.invoke() } // Aquí invocamos la función
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Mostrar la imagen de la empresa
            // Supongamos que tienes las imágenes de las empresas en tu carpeta 'res/drawable'
            val imagen = when (use.empresa) {
                "Isita" -> R.drawable.isita2
                "Verifigas" -> R.drawable.verifi
                else -> R.drawable.est // Imagen por defecto en caso de que la empresa no coincida
            }

            // Muestra la imagen de la empresa
            Image(
                painter = painterResource(imagen),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(11.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(2f)) {
            Text(
                text = "Nombre: ${use.nombres} ${use.apellidos}",
                style = TextStyle(fontSize = 12.sp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Correo: ${use.email}",
                style = TextStyle(fontSize = 12.sp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "usuarioHabilitado: ${use.usuarioHabilitado}",
                style = TextStyle(fontSize = 12.sp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "PuedeFacutras: ${use.puedeFacturar}",
                style = TextStyle(fontSize = 12.sp)
            )
        }

    }
}
}

