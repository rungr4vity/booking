package com.example.firebasenotes.models

import com.google.firebase.Timestamp
import com.google.type.DateTime
import java.time.LocalDateTime

data class spaces (

//    val id: String,
//    val email:String,
//    val company: String,
//    val horario: String,
//    val espacio: Int,
//    val day: Int,
//    val year: Int,
//    val fecha: Int,
//    val turno: Int

    val ano: Int,
    val dia: Int,
    val id: String,
    val idEstacionamiento:String,
    val idUsuario: String,
    val turno: Int,
    )