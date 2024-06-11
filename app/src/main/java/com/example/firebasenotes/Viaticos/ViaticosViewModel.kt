package com.example.firebasenotes.Viaticos

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.models.GastoDTO
import com.example.firebasenotes.models.viajeDTO
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale

class ViaticosViewModel: ViewModel(){

    private val _gastosTotales = MutableLiveData(0.0)
    val gastosTotales: LiveData<Double> get() = _gastosTotales
    val viaticos = mutableStateOf(DataViaticos())
    val viajes = mutableStateOf(viajeDTO())

    // no entra al dar back de la app,solo desde menu en iOS es viewWillAppear

    init {
            getData()
    }

        fun backButton() {
            viewModelScope.launch {
               getData()
            }
        }


        private fun getData() {
        viewModelScope.launch {

            try {
                val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
                if (!currentUserEmail.isNullOrEmpty()) {
                    viaticos.value = DataUser(currentUserEmail)
                    viajes.value = obtenerUltimoViaje()
                    //gastosTotales.value = obtenerGastos(viajes.value.id).map { it.total.toDouble() }.sum() }
                    _gastosTotales.value = obtenerGastos(viajes.value.id).map { it.total.toDouble() }.sum() }
            }catch (e: Exception){
                Log.e("Error_getData",e.message.toString())
            }
        } }

        suspend fun DataUser(email: String): DataViaticos {
            val db = FirebaseFirestore.getInstance()
            val querySnapshot = db.collection("Usuarios")
                .whereEqualTo("email", email)
                .get()
                .await()
            return if (!querySnapshot.isEmpty) {
                val result = querySnapshot.documents[0].toObject(DataViaticos::class.java)
                result ?: DataViaticos()
            } else {
                DataViaticos()
            }
        }

        fun stringToTimestamp(dateString: String, format: String = "yyyy-MM-dd"): Timestamp? {
            return try {
                // Define the date format you expect in the string
                val sdf = SimpleDateFormat(format, Locale.getDefault())
                // Parse the string into a Date object
                val date = sdf.parse(dateString)
                // Check if the date is null, return null if it is
                date?.let { Timestamp(date) }
            } catch (e: Exception) {
                // Handle the exception if parsing fails
                e.printStackTrace()
                null
            }
        }

        suspend fun obtenerGastos(idViaje: String): List<GastoDTO> {

            return try {

                val db = FirebaseFirestore.getInstance()
                //val idUsuario = FirebaseAuth.getInstance().currentUser?.uid
                val gastos = mutableListOf<GastoDTO>()

                val querySnapshot = db.collection("Gastos")
                    .whereEqualTo("idViaje", idViaje)
                    .get()
                    .await()

                querySnapshot.documents.forEach {
                    gastos.add(
                        GastoDTO(
                            0, "", "", "", "", "", false, "", "",
                            0, "", "", "", "", "", "", "", "", "", 0.0,
                            it.get("total").toString(), ""
                        )

                    )

                }


                //            return querySnapshot.documents.map {
//
//                GastoDTO(Timestamp.now().toDate(),0,0.0,"","","","","",
//                    "","","",0,"","","",
//                    it.get("importe").toString(),false,"","","")
//            }


                return gastos.toList()


            } catch (e: Exception) {
                emptyList<GastoDTO>()
            }
        }

        suspend fun obtenerUltimoViaje(): viajeDTO {
            val db = FirebaseFirestore.getInstance()
            val idUsuario = FirebaseAuth.getInstance().currentUser?.uid
            var dto: viajeDTO? = null

            val querySnapshot = db.collection("Viajes")
                .whereEqualTo("idUsuario", idUsuario?.trim())
                .whereEqualTo("activo", true)
                .get()
                .await()
            //return if (!querySnapshot.isEmpty) {
            return try {

                val result = querySnapshot.documents.forEach {
println()
                    dto = viajeDTO(
                        true,
                        it.get("cliente").toString(),
                        it.get("destino").toString(),
                        stringToTimestamp(it.get("fechaFin").toString()) ?: Timestamp.now(),
                        Timestamp.now(),
                        it.id,
                        it.get("idUsuario").toString(),
                        it.get("motivo").toString(),
                        it.get("presupuesto").toString().toDouble()
                    )
                    print (dto)
                }

                Log.d("viaje_result", result.toString())
                println()
                //val result = querySnapshot.documents[0].toObject(viajeDTO::class.java)

                return dto ?: viajeDTO()

            } catch (e: Exception) {
                viajeDTO()
                //Log.e("Error_obtenerUltimoViaje",e.message.toString())
            }


            //result ?: viajeDTO()
//        } else {
//            viajeDTO()
            //}
        }



} // en class