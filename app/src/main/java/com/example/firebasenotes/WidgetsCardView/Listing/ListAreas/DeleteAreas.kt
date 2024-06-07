package com.example.firebasenotes.WidgetsCardView.Listing.ListAreas

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebasenotes.R


@SuppressLint("SuspiciousIndentation")
@Composable
fun ModAreaEliminar(areaViewModelDOS: AreaViewModelDOS, navController: NavController){
    val are = areaViewModelDOS.stateareaa.value
        LazyColumn {
            items(are) { areas ->
                ComponenteModArea(areas = areas, navController = navController, areaViewModelDOS = areaViewModelDOS)
            }
        }
    }


@Composable
fun ComponenteModArea(areas: DataAreas,navController: NavController,
                      areaViewModelDOS: AreaViewModelDOS
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ofi), // Reemplaza 'your_image' con el nombre de tu imagen
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding( horizontal = 2.dp)


            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = "Nombre de área: ${areas.nombre}",
                    style = TextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Descripción: ${areas.descripcion}",
                    style = TextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Capacidad de personas: ${areas.capacidad}",
                    style = TextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Mobiliaria: ${areas.mobilaria}",
                    style = TextStyle(fontSize = 12.sp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            onClick = { areaViewModelDOS.deleteArea(areas.id) },
                modifier = Modifier.align(Alignment.CenterVertically)

        ){
Icon(imageVector = Icons.Default.Delete, contentDescription = "", tint = Color.Red)
        }
        }
    }
}