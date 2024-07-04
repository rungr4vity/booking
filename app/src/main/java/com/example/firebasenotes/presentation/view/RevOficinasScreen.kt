// RevoficinasScreen.kt
package com.example.firebasenotes.presentation.view

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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.firebasenotes.models.DataOfi
import com.example.firebasenotes.models.RevoficinasDTO
import com.example.firebasenotes.utils.ValidacionesHora
import com.example.firebasenotes.estacionamientos.viewmodels.RevoficinasViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun RevoficinasScreen() {
    var revoficinas by remember { mutableStateOf(emptyList<RevoficinasDTO>()) }
    val viewModel = remember { RevoficinasViewModel() }
    val idUsuario = FirebaseAuth.getInstance().currentUser?.uid
    var showDialog by remember { mutableStateOf(false) }
    var revoficinaToDelete by remember { mutableStateOf<RevoficinasDTO?>(null) }

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
                // Mostrar el diálogo de confirmación antes de eliminar
                revoficinaToDelete = revoficina
                showDialog = true
            }
        }
    }

    // Diálogo de confirmación para eliminar
    if (showDialog) {
        revoficinaToDelete?.let { revoficina ->
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    revoficinaToDelete = null
                },
                title = { Text("Confirmación") },
                text = { Text("¿Estás seguro que deseas eliminar esta reserva?") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteData(revoficina.id)
                            revoficinas = revoficinas.filter { it.id != revoficina.id }
                            showDialog = false
                            revoficinaToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF800000))
                    ) {
                        Text("Eliminar",color = Color.White)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false
                            revoficinaToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF800000))

                    ) {
                        Text("Cancelar",color = Color.White)
                    }
                }
            )
        }
    }
}

@Composable
fun RevoficinaCard(revoficina: RevoficinasDTO, onDelete: () -> Unit) {
    var dataOfi by remember { mutableStateOf<DataOfi?>(null) }
    val viewModel = remember { RevoficinasViewModel() }

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
//            Image(
//                painter = painterResource(id = R.drawable.ofi),
//                contentDescription = "Logo",
//                modifier = Modifier
//                    .size(140.dp)
//                    .padding(horizontal = 2.dp)
//            )
            dataOfi?.let { oficina ->
                Image(
                    painter = rememberAsyncImagePainter(oficina.imageUrl),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(140.dp)
                        .padding(horizontal = 2.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                dataOfi?.let { oficina ->
                    Text(
                        text = "Nombre: ${oficina.nombre}",
                        style = MaterialTheme.typography.body1
                    )
                }
                Text(
                    text = "Día:  ${ValidacionesHora.dayOfYearToDayAndMonth(revoficina.dia, revoficina.ano)} ${revoficina.ano}",
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

            IconButton(
                onClick = {
                    onDelete()
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Red
                )
            }
        }
    }
}