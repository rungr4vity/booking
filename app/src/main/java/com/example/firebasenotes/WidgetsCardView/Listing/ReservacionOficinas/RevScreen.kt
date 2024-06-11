// RevoficinasScreen.kt
package com.example.firebasenotes.WidgetsCardView.Listing.ReservacionOficinas

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.firebasenotes.R
import com.example.firebasenotes.utils.ValidacionesHora
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RevoficinasScreen() {
    var revoficinas by remember { mutableStateOf(emptyList<RevoficinasDTO>()) }
    val viewModel = RevoficinasViewModel()
    val idUsuario = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(Unit) {
        idUsuario?.let { userId ->
            viewModel.getRevoficinas(userId) { revoficinasList ->
                revoficinas = revoficinasList
            }
        }
    }

    LazyColumn {
        items(revoficinas) { revoficina ->
            RevoficinaCard(revoficina = revoficina) {
                // Aquí actualizamos la lista después de eliminar un registro
                if (idUsuario != null) {
                    viewModel.getRevoficinas(idUsuario) { updatedRevoficinas ->
                        revoficinas = updatedRevoficinas
                    }
                }
            }
        }
    }
}

@Composable
fun RevoficinaCard(revoficina: RevoficinasDTO, onDelete: () -> Unit) {
    var dataOfi by remember { mutableStateOf<DataOfi?>(null) }
    val viewModel = RevoficinasViewModel()

    LaunchedEffect(Unit) {
        viewModel.getDataOficina(revoficina.idArea) { oficina ->
            dataOfi = oficina
        }
    }

    Card(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ofi), // Reemplaza 'your_image' con el nombre de tu imagen
                contentDescription = "Logo",
                modifier = Modifier
                    .size(140.dp)
                    .padding( horizontal = 2.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
                    .padding(horizontal = 8.dp)
            ) {

            dataOfi?.let { oficina ->
                Text(
                    text = "Nombre: ${oficina.nombre}",
                    style = MaterialTheme.typography.body1
                )

            Text(
                text = "Día:  ${ValidacionesHora.dayOfYearToDayAndMonth(revoficina.dia,revoficina.ano)+" "+revoficina.ano}",
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Hora Inicial: ${ValidacionesHora.minutesToHour(revoficina.horaInicial)}",
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Hora Final: ${ValidacionesHora.minutesToHour(revoficina.horafinal)}",
                style = MaterialTheme.typography.body1
            )

            }

        }
            IconButton(
                onClick = {
                    viewModel.deleteData(revoficina.id)
                    onDelete() // Aquí llamamos a la función de callback para actualizar la lista
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Red
                )
            }
    }
}}
