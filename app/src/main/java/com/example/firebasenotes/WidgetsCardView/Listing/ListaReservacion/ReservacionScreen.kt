import ReservacionEstacionamientoViewModel
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.firebasenotes.R
import com.example.firebasenotes.WidgetsCardView.Listing.ListReservations.DataDrawerDTO
import com.example.firebasenotes.WidgetsCardView.Listing.ListReservations.DataReservations
import com.example.firebasenotes.WidgetsCardView.Listing.ListReservations.DataTurnos
import com.example.firebasenotes.utils.ValidacionesHora
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ReservacionEstacionamientoScreen(viewModel: ReservacionEstacionamientoViewModel = ReservacionEstacionamientoViewModel()) {
    var reservaciones by remember { mutableStateOf(emptyList<DataReservations>()) }
    val idUsuario = FirebaseAuth.getInstance().currentUser?.uid
    var showDialog by remember { mutableStateOf(false) }
    var reservaToDelete by remember { mutableStateOf<DataReservations?>(null) }

    LaunchedEffect(Unit) {
        idUsuario?.let { userId ->
            viewModel.getReservacionesEstacionamiento(userId) { reservacionesList ->
                reservaciones = reservacionesList
            }
        }
    }

    LazyColumn {
        items(reservaciones) { reserva ->
            ReservacionEstacionamientoCard(
                reserva = reserva,
                onDelete = {
                    // Mostrar la alerta de confirmación antes de eliminar
                    reservaToDelete = reserva
                    showDialog = true
                }
            )
        }
    }

    reservaToDelete?.let { reserva ->
        AlertDialogExample(
            reserva = reserva,
            onConfirm = {
                viewModel.deleteReservacion(reserva.id) {
                    reservaciones = reservaciones.filter { it.id != reserva.id }
                }
                showDialog = false
                reservaToDelete = null
            },
            dismissDialog = {
                showDialog = false
                reservaToDelete = null
            }
        )
    }
}

@Composable
fun ReservacionEstacionamientoCard(
    reserva: DataReservations,
    onDelete: () -> Unit
) {
    var estacionamiento by remember { mutableStateOf<DataDrawerDTO?>(null) }
    var turnosDisponibles by remember { mutableStateOf<List<DataTurnos>>(emptyList()) }
    val viewModel = remember { ReservacionEstacionamientoViewModel() }

    LaunchedEffect(Unit) {
        viewModel.getEstacionamiento(reserva.idEstacionamiento) { est ->
            estacionamiento = est
        }
        viewModel.getTurnosDisponibles { turnos ->
            turnosDisponibles = turnos
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
                painter = rememberAsyncImagePainter(estacionamiento?.imagen),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(140.dp)
                    .padding(horizontal = 2.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Nombre: ${estacionamiento?.nombre ?: "Cargando..."}",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "Día: ${ValidacionesHora.dayOfYearToDayAndMonth(reserva.dia, reserva.ano)} ${reserva.ano}",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "Turno: ${turnosDisponibles.find { it.id == reserva.turno }?.turno ?: "Cargando..."}",
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

@Composable
fun AlertDialogExample(
    reserva: DataReservations,
    onConfirm: () -> Unit,
    dismissDialog: () -> Unit
) {
    androidx.compose.material.AlertDialog(
        onDismissRequest = dismissDialog,
        title = { Text("Confirmación") },
        text = { Text("¿Estás seguro que deseas eliminar esta reserva?") },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    dismissDialog()
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF800000))
            ) {
                Text("Confirmar", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = { dismissDialog() },
                colors = ButtonDefaults.buttonColors(Color(0xFF800000))

            ) {
                Text("Cancelar",color = Color.White)
            }
        }
    )
}
