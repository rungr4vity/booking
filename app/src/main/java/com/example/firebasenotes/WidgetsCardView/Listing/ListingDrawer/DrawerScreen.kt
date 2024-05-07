package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
@Preview
@Composable
fun DrawerScreen(drawerViewModel: DrawerViewModel = viewModel()){
    val cajones = drawerViewModel.stateDrawer.value
    LazyColumn {
        items(cajones){cajon->
            ComponentDrawer(cajon = cajon)
        }
    }
}

@Composable
fun ComponentDrawer(cajon : DataDrawer){
    Card (modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxWidth()){
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text =cajon.nombre )
            Text(text =cajon.descripcion)
        }
    }
}