import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.WidgetsCardView.Listing.ListaReservacion.DataReservations
import com.example.firebasenotes.WidgetsCardView.Listing.ListaReservacion.DataTurnos
import com.example.firebasenotes.WidgetsCardView.Listing.ListaReservacion.Estacionamiento
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class  EstacionamientoViewModel : ViewModel() {
    private val _reservaciones = MutableStateFlow<List<Estacionamiento>>(emptyList())
    val reservaciones: StateFlow<List<Estacionamiento>> = _reservaciones

    private val db = FirebaseFirestore.getInstance()

    init {
        fetchReservaciones()
    }

    private fun fetchReservaciones() {
        viewModelScope.launch {
            db.collection("Estacionamientos")
                .get()
                .addOnSuccessListener { result ->
                    val lista = result.map { document ->
                        document.toObject(Estacionamiento::class.java)
                    }
                    _reservaciones.value = lista
                }
                .addOnFailureListener { exception ->
                    // Manejar error
                }
        }
    }


}

class TurnoViewModel : ViewModel() {
    private val _turnos = MutableStateFlow<List<DataTurnos>>(emptyList())
    val turnos: StateFlow<List<DataTurnos>> = _turnos

    private val db = FirebaseFirestore.getInstance()

    init {
        fetchTurnos()
    }

    private fun fetchTurnos() {
        viewModelScope.launch {
            db.collection("TurnosEstacionamiento")
                .get()
                .addOnSuccessListener { result ->
                    val lista = result.map { document ->
                        document.toObject(DataTurnos::class.java)
                    }
                    _turnos.value = lista
                }
                .addOnFailureListener { exception ->
                    // Manejar error
                }
        }
    }
}

class  ReservaViewModel : ViewModel() {
    private val _estacionamientos = MutableStateFlow<List<DataReservations>>(emptyList())
    val estacionamientos: StateFlow<List<DataReservations>> = _estacionamientos

    private val db = FirebaseFirestore.getInstance()

    init {
        fetchReservaciones()
    }

    private fun fetchReservaciones() {
        viewModelScope.launch {
            db.collection("ReservacionEstacionamiento")
                .get()
                .addOnSuccessListener { result ->
                    val lista = result.map { document ->
                        document.toObject(DataReservations::class.java)
                    }
                    _estacionamientos.value = lista
                }
                .addOnFailureListener { exception ->
                    // Manejar error
                }
        }
    }
    fun deleteReservacionEstacionamientoCollection() {
        db.collection("ReservacionEstacionamiento")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    document.reference.delete()
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores aquí
            }
    }

}
