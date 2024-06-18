package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasenotes.ViewMenu.Mipefil.DDViewModel
import com.example.firebasenotes.models.horariosDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


suspend fun FireStoreCajonData(): MutableList<DataDrawer> {
    val db = FirebaseFirestore.getInstance()
    val cajonList = mutableListOf<DataDrawer>()


    var retorna = ""
    val empresa2 = db.collection("Usuarios")
        .whereEqualTo("email", FirebaseAuth.getInstance().currentUser?.email)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                retorna = document.data?.get("empresa").toString()
            }
        }.addOnFailureListener {
            Log.d("ERROR en getEmpresa", "ERROR:${it.localizedMessage}")
        }
        .await()

    try {
        val querySnapshot = db.collection("Estacionamientos")
            .whereEqualTo("empresa", retorna)
            .get().await()
        for (document in querySnapshot.documents) {
            val numero = document.getLong("numero") ?: 0L
            val nombre = document.getString("nombre") ?: ""
            val piso = document.getString("piso") ?: ""
            val empresa = document.getString("empresa") ?: ""
            val esEspecial = document.getBoolean("esEspecial") ?: false

            cajonList.add(
                DataDrawer(
                    numero = numero.toInt(),
                    nombre = nombre,
                    piso = piso,
                    id = document.id,
                    empresa = empresa,
                    esEspecial = esEspecial
                )
            )
        }
    } catch (e: Exception) {
        Log.e("FirestoreError", "Error fetching data: ${e.message}")
    }
    return cajonList
}

data class users_short(
    val nombres: String,
    val apellidos: String,
    val documentId: String
)

suspend fun getUsuarios_(): MutableList<users_short> {
    val db = FirebaseFirestore.getInstance()
    val usersList = mutableListOf<users_short>()

    try {
        val querySnapshot = db.collection("Usuarios").get().await()
        for (document in querySnapshot.documents) {

            val nombres = document.getString("nombres") ?: ""
            val apellidos = document.getString("apellidos") ?: ""
            val documentId = document.id

            usersList.add(
                users_short(
                    nombres = nombres,
                    apellidos = apellidos,
                    documentId = documentId
                )
            )
        }
    } catch (e: Exception) {
        Log.e("FirestoreError", "Error fetching data users short: ${e.message}")
    }
    return usersList
}


class DrawerViewModel() : ViewModel() {
    val stateDrawer = mutableStateOf<List<DataDrawer>>(emptyList())
    val usuarios_short = mutableStateOf<List<users_short>>(emptyList())
    private val _horarios_dto = MutableLiveData<MutableList<horariosDTO>>(mutableListOf())
    val horarios_dto: LiveData<MutableList<horariosDTO>> get() = _horarios_dto
    var empresa = mutableStateOf("")
    lateinit var context: Context




    init {
        try {
            //getEmpresa()
            getData()
            getUsuarios()
        }catch (e:Exception){
            Log.e("Error_drawer",e.message.toString())
        }

    }

    fun reload() {
        viewModelScope.launch {
            getData()
        }
    }

    fun getAll(ano:Int,dia:Int) {
        viewModelScope.launch {
            _horarios_dto.value = getHorarios_year_day(ano,dia)
        }

    }

    fun getEmpresa() {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        empresa.value =  sharedPreferences.getString("empresa", "") ?: ""
        var ojo  = ""
    }




    suspend fun getEmpresa_(): String {
        var retorna = ""
        val db = FirebaseFirestore.getInstance()
        val empresa = db.collection("Usuarios")
            .whereEqualTo("email", FirebaseAuth.getInstance().currentUser?.email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    retorna = document.data?.get("empresa").toString()
                }
            }.addOnFailureListener {
                Log.d("ERROR en getEmpresa", "ERROR:${it.localizedMessage}")
            }
            .await()

        return retorna
    }
    suspend fun getHorarios_year_day(dia:Int,ano:Int)
            :MutableList <horariosDTO> {

        var horarios = mutableListOf<horariosDTO>()
        val db = FirebaseFirestore.getInstance()

        val usuarios = db.collection("ReservacionEstacionamiento")

            .whereEqualTo("ano", ano)
            .whereEqualTo("dia",dia)

            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {


                    //Log.d("HORARIO_count", "ERROR:${document.data?.get("turno").toString().toInt()}")
                    horarios.add(
                        horariosDTO(
                            document.data?.get("turno").toString().toInt(),

                            when(document.data?.get("turno").toString().trim().toInt()){
                                0 -> "7:00 am - 12 pm".trim()
                                1 -> "12:00 am - 5  pm".trim()
                                2 -> "5:00 pm - 9:00 pm".trim()
                                else -> "Todo el día"
                            },

                            document.data?.get("idEstacionamiento").toString()

                        )
                    )
                }

            }.addOnFailureListener {
                Log.d("ERROR en getHorariosHoy", "ERROR:${it.localizedMessage}")
            }.await()

        return horarios
    }



    private fun getUsuarios() {
        viewModelScope.launch {
            usuarios_short.value =
                com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.getUsuarios_()
        }
    }

    private fun getData() {

        viewModelScope.launch {


            stateDrawer.value =
                com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.FireStoreCajonData()
        }
    }

    fun insertarDatos(context: Context,
                      numo_cajon: Int,
                      nombre_cajon: String,
                      piso_edificio: String,
                      selectedOptionText: String,
                      esEspecial : Boolean,
                      perteneceA: String
    ){

        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()

            val data = hashMapOf(
                "numero" to numo_cajon,
                "nombre" to nombre_cajon.trim(),
                "piso" to piso_edificio.trim(),
                "empresa" to selectedOptionText.trim(),
                "esEspecial" to esEspecial,
                "id" to "",
                "perteneceA" to perteneceA
            )
            try {
                val documentRef = db.collection("Estacionamientos").add(data).await()
                Toast.makeText(context, "Datos insertados correctamente", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Log.e("Error",e.message.toString())
            }
        }
    }}
class DeleteDrawerViewModel : ViewModel() {
    val delete = mutableStateOf<List<DataDrawer>>(emptyList())

    init {
        loadData()

    }

    private fun loadData() {
        viewModelScope.launch {
            delete.value = FireStoreCajonData()
        }
    }

    fun deleteData(id: String) {
        viewModelScope.launch {
            try {
                val db = FirebaseFirestore.getInstance()
                db.collection("Estacionamientos").document(id).delete().await()
                loadData() // Refrescar los datos después de eliminar
            } catch (e: Exception) {
                Log.e("FirestoreError", "Error deleting data: ${e.message}")
            }
        }
    }
}
