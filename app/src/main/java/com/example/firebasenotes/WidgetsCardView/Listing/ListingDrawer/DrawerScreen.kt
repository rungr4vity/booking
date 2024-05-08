package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun DrawerScreen(drawerViewModel: DrawerViewModel = viewModel(), navController: NavController){
    val cajones = drawerViewModel.stateDrawer.value
    LazyColumn {
        items(cajones){cajon->
            ComponentDrawer(cajon = cajon, onDeleteClicked = {}, navController = navController)
        }
    }
}

@Composable
fun ComponentDrawer(
    cajon: DataDrawer, onDeleteClicked: () -> Unit,
    navController: NavController
){

    Card (modifier = Modifier
        .clickable {
            navController.navigate("Mis reservas")
        }
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxWidth(),
    ){
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text =cajon.Nombre)
            //Text(text =cajon.Piso)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Cajon: ${cajon.Piso}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Piso 12")
            Text(text = cajon.Numero)
        }
    }
}