package com.example.firebasenotes.Viaticos

data class DataViaticos(
    val apellidos : String?="",
    val contrasena : String = "",
    val email : String?="",
    val empresa : String?="",
    val esAdmin : Boolean ?=false,
    val id : String="",
    val nombres : String="",
    val puedeFacturar : Boolean ?=true,
    val tieneViajeActivo : Boolean ?=true,
    val typeId: Int=0,
    val usuarioHabilitado: Boolean?=true,
    val userID : String ="",
    val token : String =""
)


