package com.example.firebasenotes.WidgetsCardView.Listing.ListReservations


data class DataReservations( //Datos Reservas
    var ano : Int=0,
    var dia : Int=0,
    var id : String="",
    var idEstacionamiento : String="",
    var idUsuario : String="",
    var turno : Int=0,
)
data class DataDrawer( //Datos Estacionamiento
    val numero: Int = 0,
    val nombre: String = "",
    val piso: String = "",
    val id: String = "",
    val empresa: String = "",
    val esEspecial: Boolean? = false
)

data class DataTurnos(
    val id: Int =0,
    val turno: String ="",
)


