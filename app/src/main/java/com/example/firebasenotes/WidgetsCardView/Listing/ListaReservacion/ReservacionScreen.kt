import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasenotes.R
import com.example.firebasenotes.WidgetsCardView.Listing.ListaReservacion.DataReservations
import com.example.firebasenotes.WidgetsCardView.Listing.ListaReservacion.DataTurnos
import com.example.firebasenotes.WidgetsCardView.Listing.ListaReservacion.Estacionamiento
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Reservacion(
    estacionamientoViewModel: EstacionamientoViewModel = viewModel(),
    turnoViewModel: TurnoViewModel = viewModel(),
    reservaViewModel: ReservaViewModel = viewModel()
) {
    val estacionamientos by estacionamientoViewModel.reservaciones.collectAsState()
    val turnos by turnoViewModel.turnos.collectAsState()
    val reservas by reservaViewModel.estacionamientos.collectAsState()

    Scaffold(

    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            items(estacionamientos.size) { index ->
                val estacionamiento = estacionamientos[index]
                val reserva = reservas.getOrNull(index)
                val turno = turnos.getOrNull(index)

                CombinedItem(
                    estacionamiento = estacionamiento,
                    turno = turno,
                    reserva = reserva,
                    onDeleteClicked = {

                    }
                )
            }
        }
    }
}@Composable
fun CombinedItem(
    estacionamiento: Estacionamiento,
    turno: DataTurnos?,
    reserva: DataReservations?,
    onDeleteClicked: () -> Unit
) {
    val estacionamientoViewModel: EstacionamientoViewModel = viewModel()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.est),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding(horizontal = 8.dp)
            )

            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "${estacionamiento.nombre} No: ${estacionamiento.numero}")
                turno?.let {
                    Text(text = "Turno: ${it.turno}")
                }
                Text(text = "Piso: ${estacionamiento.piso}")
                Text(text = "Empresa: ${estacionamiento.empresa}")
            }
            Spacer(modifier = Modifier.weight(1f)) // Espacio flexible para empujar el IconButton hacia la derecha

            IconButton(
                onClick ={
                    estacionamientoViewModel.eliminarEstacionamiento(estacionamiento)
                }
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
