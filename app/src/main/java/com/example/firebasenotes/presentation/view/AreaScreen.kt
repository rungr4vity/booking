package com.example.firebasenotes.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.firebasenotes.oficinas.viewmodels.AreaViewModel
import com.example.firebasenotes.models.DataAreas
import com.example.firebasenotes.presentation.viewModel.DDViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun AreaScreen (
    areaViewModel: AreaViewModel = viewModel(),
    navController: NavController,
    ddViewModel: DDViewModel = viewModel()
) {
//    val are = areaViewModel.statearea.value
    val are : List<DataAreas> by areaViewModel.statearea.observeAsState(listOf())
    val userData = ddViewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
    LazyColumn {
        items(are) { areas ->
            ComponentAreas(ar = areas, navController = navController)
        }
    }

        LaunchedEffect(Unit) {
            areaViewModel.getDataInfo()
        }

if(userData.typeId == 0){
    FloatingActionButton(
        onClick = {
            navController.navigate("Alta de area")
        },
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp),
        containerColor =  Color(0xFF800000)
    ) {
        androidx.compose.material.Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
    }
}

    }
}
@Composable
fun ComponentAreas(
    ar: DataAreas,
    navController: NavController
) {
    val isDarkMode = isSystemInDarkTheme()
    CompositionLocalProvider(LocalContentColor provides if (isDarkMode) Color.White else Color.Black) {

        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
                // Mostrar la imagen asociada al área
                if (ar.imageUrl.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(ar.imageUrl), // Usa una función rememberImagePainter para cargar la imagen desde la URL
                        contentDescription = "Image",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(vertical = 10.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(2f)) {
                    Text(
                        text = "Nombre: ${ar.nombre}",
                        style = TextStyle(fontSize = 15.sp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Capacidad Pers.: ${ar.capacidad}",
                        style = TextStyle(fontSize = 15.sp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Mobiliaria: ${ar.mobilaria}",
                        style = TextStyle(fontSize = 15.sp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Navegación al detalle
                Text(
                    text = "Detalle",
                    modifier = Modifier
                        .clickable {
                            val encodedUrl =
                                URLEncoder.encode(ar.imageUrl, StandardCharsets.UTF_8.toString())
                            navController.navigate("DetalleOficinas/${ar.capacidad}/${ar.descripcion}/NA/${ar.mobilaria}/${ar.nombre}/${ar.id}/${encodedUrl}")
                        }
                        .align(Alignment.CenterVertically)
                        .padding(end = 16.dp),
                    color = if (isDarkMode) Color.White else Color.Blue
                )
            }
        }
    }
}
