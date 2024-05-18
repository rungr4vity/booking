package com.example.firebasenotes.models

import com.google.firebase.Timestamp

data class viajesDTO (
    val id: String,
    val motivo: String,
    val fechaInicioval : Timestamp,
    val fechaFinval : Timestamp,
    val clienteval : String,
    val idUsuarioval : String,
    val destinoval : String,

    )