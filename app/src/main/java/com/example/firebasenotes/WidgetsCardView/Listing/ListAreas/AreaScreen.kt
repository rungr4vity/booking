package com.example.firebasenotes.WidgetsCardView.Listing.ListAreas

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.firebasenotes.R
import com.example.firebasenotes.ViewMenu.Mipefil.DDViewModel
import com.google.firebase.components.Component
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun AreaScreen (
    areaViewModel: AreaViewModel = viewModel(),
    navController: NavController,
    ddViewModel: DDViewModel = viewModel()
) {
    val are = areaViewModel.statearea.value
    val userData = ddViewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
    LazyColumn {
        items(are) { areas ->
            ComponentAreas(ar = areas, navController = navController)
        }
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
                        painter = rememberImagePainter(ar.imageUrl), // Usa una función rememberImagePainter para cargar la imagen desde la URL
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
                        text = "${ar.nombre}",
                        style = TextStyle(fontSize = 15.sp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Cap. : ${ar.capacidad}",
                        style = TextStyle(fontSize = 15.sp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Mob.: ${ar.mobilaria}",
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
