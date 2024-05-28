package com.example.firebasenotes.models

data class users(
    val apellidos: String = "",
    val contrasena: String = "",
    val email: String = "",
    val empresa: String = "",
    val esAdmin:Boolean = false,
    val id: String,
    val nombres: String = "",
    val puedeFacturar: Boolean = false,
    val tieneViajeActivo:Boolean = false,
    val token: String = "",
    val typeId: Int = 0,
    val usuarioHabilitado:Boolean = false,



) {
    fun toMap():MutableMap<String,Any> {
        return mutableMapOf(
            "nombres" to this.nombres,
            "apellidos" to this.apellidos,
            "contrasena" to this.contrasena,
            "email" to this.email,
            "empresa" to this.empresa,
            "typeId" to this.typeId,
            "token" to this.token,
            "puedeFacturar" to this.puedeFacturar,
            "usuarioHabilitado" to this.usuarioHabilitado,
            "tieneViajeActivo" to this.tieneViajeActivo,
            "esAdmin" to this.esAdmin,
            "id" to this.id,
        )
    }
}