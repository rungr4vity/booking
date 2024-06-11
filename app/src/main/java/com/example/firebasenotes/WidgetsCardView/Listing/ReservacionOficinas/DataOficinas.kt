package com.example.firebasenotes.WidgetsCardView.Listing.ReservacionOficinas

data class RevoficinasDTO(
    val ano: Int = 0,
    val dia:Int = 0,
    val horaInicial : Int = 0,
    val horafinal: Int = 0,
    var id: String = "",
    val idArea: String = "",
    val idUsuario: String = "",
)

data class DataOfi(
    val capacidad: String="",
    val descripcion: String="",
    var id : String="",
    val mobilaria: String="",
    val nombre: String="",
)
