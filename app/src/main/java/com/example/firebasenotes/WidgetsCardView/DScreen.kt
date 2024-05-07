package com.example.firebasenotes.WidgetsCardView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Preview
@Composable
fun DScreen(dViewModel: DViewModel = viewModel()) {
    val spaces = dViewModel.statee.value

    LazyColumn {
        items(spaces) { space ->
            ComponentCard(space = space)
        }
    }
}
@Composable
fun ComponentCard(space: Data) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "${space.day}/${space.month}/${space.year}",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .align(Alignment.End)
                .padding(5.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically, // Alinea verticalmente los elementos
            modifier = Modifier.padding(16.dp)
        ) {
            SimIcon()
            Spacer(modifier = Modifier.width(16.dp)) // Añade un espacio entre el icono y la columna
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "${space.nombre} ${space.Apellidos}",style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = space.email, style = TextStyle(fontSize = 16.sp))
                Text(text = space.company, style = TextStyle(fontSize = 16.sp))
                Text(text = space.espacio, style = TextStyle(fontSize = 16.sp))

            }

            Spacer(modifier = Modifier.width(6.dp)) // Añade un espacio al final

        }
        Button(onClick = { /*TODO*/ }, modifier = Modifier
            .align(Alignment.End)
            .padding(7.dp)) {
            Text(text = "Liberar")
        }
    }
}


@Composable
fun SimIcon(){
    Icon(imageVector = Icons.Default.DateRange, contentDescription = "",
        modifier = Modifier.size(80.dp))
}

