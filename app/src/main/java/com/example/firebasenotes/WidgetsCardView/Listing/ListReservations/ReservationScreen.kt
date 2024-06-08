import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasenotes.WidgetsCardView.Listing.ListReservations.DataReservations
import com.example.firebasenotes.WidgetsCardView.Listing.ListReservations.DataTurnos
//import com.example.firebasenotes.WidgetsCardView.Listing.ListaReservacion.Estacionamiento

@Preview
@Composable
fun ReservationScreen(reservationViewModel: ReservationViewModel = viewModel()) {
    val reservationState by reservationViewModel.reservationState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        reservationViewModel.fetchUnifiedData()
    }

    LazyColumn {
        items(reservationState.reservacionData) { reserv ->
            val estacionamiento = reservationState.estacionamientoData.find { it.id == reserv.idEstacionamiento }
            val turno = reservationState.turnosData.find { it.id == reserv.turno }
            ComponentReservation(
                reserv = reserv,
//                estacionamiento =estacionamiento ,
                turno = turno
            ) {
                reservationViewModel.deleteReservacionEstacionamiento(reserv.id, context)
            }
        }
    }
}
@Composable
fun ComponentReservation(
    reserv: DataReservations,
//    estacionamiento: Estacionamiento?,
    turno: DataTurnos?,
    onDeleteClicked: () -> Unit,
) {
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
//                estacionamiento?.let {
//                    Text(text = "Estacionamiento: ${it.numero}", style = TextStyle(fontSize = 15.sp))
//                }
                turno?.let {
                    Text(text = "Turno: ${it.turno}", style = TextStyle(fontSize = 15.sp))
                }
                Text(text = "Fecha: ${reserv.ano}", style = TextStyle(fontSize = 15.sp))

            }
            Spacer(modifier = Modifier.width(6.dp))
            IconButton(onClick = onDeleteClicked) {
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
            }
        }
    }
}
