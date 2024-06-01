package com.example.firebasenotes.Oficinas

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.DataAreas
import com.example.firebasenotes.models.horariosModel
import com.example.firebasenotes.models.oficinasDTO
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


suspend fun _ReservacionOficinasDia(ano:Int,dia:Int,idArea:String) : MutableList <oficinasDTO>{
    val db = FirebaseFirestore.getInstance()
    val reservacionOficinas = mutableListOf<oficinasDTO>()

    val querySnapshot = db.collection("ReservacionOficina")
        .whereEqualTo("dia", dia)
        .whereEqualTo("ano", ano)
        .whereEqualTo("idArea", idArea)
        .get().await()
    querySnapshot.query

    for (document in querySnapshot.documents){
        val ar = document.toObject(oficinasDTO::class.java)

        ar?.let {
            reservacionOficinas.add(it)
        }
    }
    return reservacionOficinas
}


class OficinasViewModel:ViewModel() {

    private var _horariosOficinas = MutableLiveData<MutableList<oficinasDTO>>(mutableListOf())
    val horariosOficinas: LiveData<MutableList<oficinasDTO>> get() = _horariosOficinas






    val stateOficina = mutableStateOf<List<oficinasDTO>>(emptyList())
    fun timeToSeconds(ttime: Int): Int {




        // Extract hours, minutes, and seconds from the TTime digit
        val hours = ttime / 10000
        val minutes = (ttime / 100) % 100
        val seconds = ttime % 100

        // Convert hours, minutes, and seconds to total seconds
        val totalSeconds = hours * 3600 + minutes * 60 + seconds

        return totalSeconds
    }

    fun reservacionOficina(
        context: Context,
        ano:Int,
        dia:Int,
        horaInicial:String,
        horafinal:String,
        id:String,
        idArea:String,
        idUsuario:String) {



        val horaInicio = horaInicial.split(":")[0].toInt()
        val minutosInicio = horaInicial.split(":")[1].toInt()

        val horaFin = horafinal.split(":")[0].toInt()
        val minutosFin = horafinal.split(":")[1].toInt()


        //var _inicialInt = horaInicial.replace(":",".")
        //var _finalInt = horafinal.replace(":",".")

        val inicialInt = (horaInicio.toInt() * 60) + minutosInicio
        val finalInt = (horaFin.toInt() * 60) + minutosFin


        val oficina = oficinasDTO(
            ano,
            dia,
            inicialInt,
            finalInt,
            id,
            idArea,
            idUsuario)

        viewModelScope.launch {
                        try {
                            FirebaseFirestore.getInstance().collection("ReservacionOficina")
                                .document()
                                .set(oficina)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Reservación generada", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "error al generar reservación", Toast.LENGTH_SHORT).show()
                                }

                        }catch (e:Exception){

                        }
        } // end launch


    }// end function

    fun ReservacionOficinasDia(ano:Int,dia:Int,idArea:String) {

        viewModelScope.launch {
           // stateOficina.value =
            _horariosOficinas.value = _ReservacionOficinasDia(ano,dia,idArea)
        }

    }

}