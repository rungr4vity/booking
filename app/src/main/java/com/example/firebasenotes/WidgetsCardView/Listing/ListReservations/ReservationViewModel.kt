import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.WidgetsCardView.Listing.ListReservations.DataDrawer
import com.example.firebasenotes.WidgetsCardView.Listing.ListReservations.DataReservations
import com.example.firebasenotes.WidgetsCardView.Listing.ListReservations.DataTurnos
import kotlinx.coroutines.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class ReservationState(
    val estacionamientoData: List<DataDrawer> = emptyList(),
    val reservacionData: List<DataReservations> = emptyList(),
    val turnosData: List<DataTurnos> = emptyList()
)

class ReservationViewModel : ViewModel() {
    private val _reservationState = MutableStateFlow(ReservationState())
    val reservationState: StateFlow<ReservationState> = _reservationState

    private val db = FirebaseFirestore.getInstance()

    init {
        fetchUnifiedData()
    }

    fun fetchUnifiedData() {
        viewModelScope.launch {
            try {
                val estacionamientoData = fetchEstacionamientoData()
                val reservacionData = fetchReservacionEstacionamientoData()
                val turnosData = fetchTurnosData()
                _reservationState.value = ReservationState(
                    estacionamientoData = estacionamientoData,
                    reservacionData = reservacionData,
                    turnosData = turnosData
                )
            } catch (e: Exception) {
                Log.e("Error al obtener los datos:", e.message.toString())
            }
        }
    }

    private suspend fun fetchEstacionamientoData(): List<DataDrawer> {
        val estacionamientoList = mutableListOf<DataDrawer>()
        val documents = db.collection("Estacionamientos").get().await()
        for (document in documents) {
            val data = document.toObject(DataDrawer::class.java)
            estacionamientoList.add(data)
        }
        return estacionamientoList
    }

    private suspend fun fetchReservacionEstacionamientoData(): List<DataReservations> {
        val reservacionList = mutableListOf<DataReservations>()
        val documents = db.collection("ReservacionEstacionamiento").get().await()
        for (document in documents) {
            val data = document.toObject(DataReservations::class.java)
            data.id = document.id // Obtener el valor de 'id' del documento
            reservacionList.add(data)
        }
        return reservacionList
    }

    private suspend fun fetchTurnosData(): List<DataTurnos> {
        val turnosList = mutableListOf<DataTurnos>()
        val documents = db.collection("TurnosEstacionamiento").get().await()
        for (document in documents) {
            val data = document.toObject(DataTurnos::class.java)
            turnosList.add(data)
        }
        return turnosList
    }

    fun deleteReservacionEstacionamiento(id: String, context: Context) {
        viewModelScope.launch {
            db.collection("ReservacionEstacionamiento").document(id).delete().await()
            fetchUnifiedData()
        }
    }
}
