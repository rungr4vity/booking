package com.example.firebasenotes.models

data class users(

    val id: String?,
    val userID: String,
    val nombre: String,
    val apellidos: String,
    val email: String,
    val empresa: String,
    val typeid: Int,
    val token: String
) {
    fun toMap():MutableMap<String,Any> {
        return mutableMapOf(
            "userID" to this.userID,
            "nombre" to this.nombre,
            "apellidos" to this.apellidos,
            "email" to this.email,
            "empresa" to this.empresa,
            "typeid" to this.typeid,
            "token" to this.token,
        )
    }
}