package com.example.firebasenotes.WidgetsCardView.Listing.ListAreas

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.components.Component

@Preview
@Composable
fun AreaScreen (areaViewModel: AreaViewModel = viewModel()){
    val are = areaViewModel.statearea.value

    LazyColumn {
        items(are){areas ->
            ComponentAreas(ar = areas)
        }
    }
}

@Composable
fun ComponentAreas( ar:DataAreas){
    Card (modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxWidth()){
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
        Text(text = ar.nombre)
            Text(text = ar.descripcion)
        }
    }
}