package com.example.firebasenotes.view

import com.google.firebase.Timestamp

data class MotivosViaje(
    val motivo: String = "",
    val id: String = ""
)

data class Clientes(
    val cliente: String = "",
    val id: String = ""
)

data class viaje (

    val activo: Boolean ?= false,
    val cliente: String = "",
    val destino : String = "",
    val fechaFin : Timestamp = Timestamp.now(),
    val fechaInicio : Timestamp = Timestamp.now(),
    val id: String = "",
    val idUsuario : String = "",
    val motivo: String= "",
    val presupuesto : String = "",

    )