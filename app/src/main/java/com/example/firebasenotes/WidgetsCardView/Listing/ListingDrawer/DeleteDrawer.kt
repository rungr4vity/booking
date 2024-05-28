package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun DeleteDrawer(deleteDrawerViewModel: DeleteDrawerViewModel= viewModel(),navController: NavController){
    val drawer = deleteDrawerViewModel.delete.value
    LazyColumn {
        items(drawer) {cajones->
            ComponentDrawer(cajones = cajones, navController = navController, deleteDrawerViewModel = deleteDrawerViewModel)

        }
    }
}

@Composable
fun ComponentDrawer(cajones: DataDrawer,
                    deleteDrawerViewModel: DeleteDrawerViewModel= viewModel(),
                    navController: NavController){
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                modifier = Modifier
                    .size(80.dp)
                    .padding(11.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = "Nombre: ${cajones.nombre}",
                    style = TextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Descripción: ${cajones.descripcion}",
                    style = TextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Piso: ${cajones.piso}",
                    style = TextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "NumeroSSSS: ${cajones.numero}",
                    style = TextStyle(fontSize = 12.sp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = { deleteDrawerViewModel.deleteData(cajones.id) },
                modifier = Modifier.align(Alignment.CenterVertically)

            ){
                Icon(imageVector = Icons.Default.Delete, contentDescription = "", tint = Color.Red)
            }
        }
    }
}