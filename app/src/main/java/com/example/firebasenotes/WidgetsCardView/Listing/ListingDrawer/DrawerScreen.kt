package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun DrawerScreen(drawerViewModel: DrawerViewModel = viewModel(), navController: NavController) {
    val cajones = drawerViewModel.stateDrawer.value
    LazyColumn {
        items(cajones) { cajon ->
            ComponentDrawer(cajon = cajon, navController = navController)
        }
    }
}

@Composable
fun ComponentDrawer(
    cajon: DataDrawer,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .clickable {
                navController.navigate( "DetalleCajon")
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "",
                modifier = Modifier
                    .size(80.dp)
                    .padding(11.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = "Nombre: ${cajon.Nombre}",
                    style = TextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Empresa: ${cajon.Empresa}",
                    style = TextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Cajon: ${cajon.Numero}",
                    style = TextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Piso: ${cajon.Piso}",
                    style = TextStyle(fontSize = 12.sp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Detalle",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp), color = Color.Blue
            )
        }
    }
}
