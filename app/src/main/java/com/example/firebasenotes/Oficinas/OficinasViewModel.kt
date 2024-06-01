package com.example.firebasenotes.Oficinas

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.DataAreas
import com.example.firebasenotes.models.oficinasDTO
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class OficinasViewModel:ViewModel() {




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
        ano:String,
        dia:String,
        horaInicial:String,
        horafinal:String,
        id:String,
        idArea:String,
        idUsuario:String) {

        var _id = {
            if(id == "" || id == null){
                "N/A"
            }else{
                id
            }
        }

        var _inicialInt = horaInicial.replace(":",".")
        var _finalInt = horafinal.replace(":",".")

        var inicialInt = _inicialInt.toDouble() * 60
        var finalInt = _finalInt.toDouble() * 60

        println()
        val oficina = oficinasDTO(ano,dia,inicialInt.toString(),finalInt.toString(),_id.toString(),idArea,idUsuario)

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


    }

}