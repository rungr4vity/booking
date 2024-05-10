package com.example.firebasenotes.WidgetsCardView.Listing.ListAreas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
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
import com.google.firebase.components.Component


@Composable
fun AreaScreen (areaViewModel: AreaViewModel = viewModel(), navController: NavController){
    val are = areaViewModel.statearea.value

    LazyColumn {
        items(are){areas ->
            ComponentAreas(ar = areas , navController = navController)
        }
    }
}

@Composable
fun ComponentAreas( ar:DataAreas  ,  navController: NavController
) {
    Card(
        modifier = Modifier
            .clickable {
                navController.navigate( "DetalleAlta")
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material.Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                modifier = Modifier
                    .size(80.dp)
                    .padding(11.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = "Nombre de area: ${ar.nombreArea}",
                    style = TextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Descripcion: ${ar.descripcion}",
                    style = TextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Capacidad de personas: ${ar.capacidadDpersonas}",
                    style = TextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Mobiliaria: ${ar.mobiliaria}",
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
