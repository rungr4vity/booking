package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.firebasenotes.R
import com.example.firebasenotes.ViewMenu.Mipefil.DDViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun DrawerScreen(drawerViewModel: DrawerViewModel = viewModel(), navController: NavController,
                 ddViewModel: DDViewModel = viewModel()) {
    val cajones = drawerViewModel.stateDrawer.value
    val userData = ddViewModel.state.value
    CompositionLocalProvider(LocalContentColor provides Color.Black) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            try {
                items(cajones) { cajon ->
                    ComponentDrawer(cajon = cajon, navController = navController)
                }
            } catch (e: Exception) {
                Log.e("ErrorLazy", e.toString())
            }
        }
        if (userData.typeId == 0) {
            FloatingActionButton(
                onClick = {
                    navController.navigate("Alta de Cajon")
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor =  Color(0xFF800000)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            }
        }

    }
}}
@Composable
fun ComponentDrawer(
    cajon: DataDrawer,
    navController: NavController
) {
    val isDarkMode = isSystemInDarkTheme()

    CompositionLocalProvider(LocalContentColor provides if (isDarkMode) Color.White else Color.Black) {
        Card(
            modifier = Modifier
                .clickable {

                    navController.navigate("DetalleCajon/${cajon.nombre}/${cajon.empresa}/${cajon.numero.toString()}/${cajon.piso}/${cajon.esEspecial}/${cajon.id}",
                        navOptions { // Use the Kotlin DSL for building NavOptions
                            anim {
                                enter = android.R.animator.fade_in
                                exit = android.R.animator.fade_out
                            }
                        }
                    )
                }
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.est), // Reemplaza 'your_image' con el nombre de tu imagen
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(100.dp)
//                        .padding(horizontal = 2.dp)

                )


                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(2f)) {
                    Text(
                        text = "Nombre: ${cajon.nombre}",
                        style = TextStyle(fontSize = 12.sp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Empresa: ${cajon.empresa}",
                        style = TextStyle(fontSize = 12.sp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Cajon: ${cajon.numero.toString()}",
                        style = TextStyle(fontSize = 12.sp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Piso: ${cajon.piso}",
                        style = TextStyle(fontSize = 12.sp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Detalle",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 16.dp),
                    color = if (isDarkMode) Color.White else Color.Blue // Cambiar el color del texto a blanco en modo oscuro
                )
            }
        }
    }

}
