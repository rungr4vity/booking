package com.example.firebasenotes.Cards

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
import androidx.compose.material.icons.filled.Face
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
fun DataScreen(
    dataViewModel: DataViewModel = viewModel()
) {
    val users = dataViewModel.state.value

    LazyColumn {
        items(users) { user ->
            UserCard(user = user)
        }
    }
}
@Composable
fun UserCard(user: About) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SimIcon()
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = user.nombre ?: "", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = user.email, style = TextStyle(fontSize = 16.sp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = user.empresa, style = TextStyle(fontSize = 16.sp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Cajon : 243 ${user.userID}", style = TextStyle(fontSize = 16.sp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Btn_Eliminar()
        }
    }
}
@Composable
fun SimIcon(){
    Icon(imageVector = Icons.Default.Face, contentDescription = "",
        modifier = Modifier.size(80.dp))
}

@Composable
fun Btn_Eliminar (){
    Button(onClick = { /*TODO*/ }) {
        Text(text = "Liberar")
    }
}