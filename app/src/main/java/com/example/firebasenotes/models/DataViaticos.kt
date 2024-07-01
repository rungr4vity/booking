package com.example.firebasenotes.models

data class DataViaticos(
    val apellidos: String?="",
    val contrasena: String = "",
    val email: String?="",
    val empresa: String?="",
    val esAdmin: Boolean ?=false,
    val id: String="",
    val nombres: String="",
    val puedeFacturar: Boolean ?=false,
    val tieneViajeActivo: Boolean ?=false,
    val typeId: Int?=0,
    val usuarioHabilitado: Boolean?=true,
    val userID: String ="",
    val token: String ?=""
)


