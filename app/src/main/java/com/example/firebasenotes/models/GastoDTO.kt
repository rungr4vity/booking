package com.example.firebasenotes.models

import java.util.Date

data class GastoDTO (
    var claveProdServ: Int=0,
    var comentario: String= "",
    var descripcion: String= "",
    var emisorNombre: String= "",
    var emisorRfc: String = "",
    var emisorRegimen: String= "",
    var esDeducible: Boolean = false,
    var fecha: String = "",
    var folio: String = "",
    var formaPago: Int=0,
    var idUsuario: String= "",
    var idViaje: String= "",
    var imagen: String= "",
    var importe: String= "",
    var impuesto: String = "",
    var lugarExpedicion: String = "",
    var metodoPago: String= "",
    var moneda: String= "",
    var pdf: String = "",
    var subTotal: Double=0.0,
    var total: String= "",
    var xml: String = "",
)