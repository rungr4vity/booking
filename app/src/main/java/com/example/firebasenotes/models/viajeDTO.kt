package com.example.firebasenotes.models

import android.icu.text.DecimalFormat
import com.google.firebase.Timestamp

data class viajeDTO (

    val activo: Boolean = false,
    val cliente: String = "",
    val destino : String = "",
    val fechaFin : Timestamp= Timestamp.now(),
    val fechaInicio : Timestamp= Timestamp.now(),
    val id: String= "",
    val idUsuario : String= "",
    val motivo: String= "",
    val presupuesto : Double= 0.0,

    )