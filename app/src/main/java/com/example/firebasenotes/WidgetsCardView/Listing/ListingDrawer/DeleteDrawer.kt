package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.firebasenotes.R

@Composable
fun DeleteDrawer(navController: NavController) {
    val deleteDrawerViewModel: DeleteDrawerViewModel = viewModel()
    val drawer = deleteDrawerViewModel.delete.value
    val context = LocalContext.current

    LazyColumn {
        items(drawer) { cajones ->
            DrawerCard(context, cajones, deleteDrawerViewModel, navController)
        }
    }
}

@Composable
fun DrawerCard(
    context: Context,
    cajones: DataDrawer,
    deleteDrawerViewModel: DeleteDrawerViewModel,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Image(painter = painterResource(id = R.drawable.est), contentDescription ="" ,
              modifier = Modifier.size(100.dp))
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = "Nombre: ${cajones.nombre}",
                    style = TextStyle(fontSize = 12.sp)
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Piso: ${cajones.piso}",
                    style = TextStyle(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Numero: ${cajones.numero}",
                    style = TextStyle(fontSize = 12.sp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = {  },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.Red)
            }
        }
    }
}