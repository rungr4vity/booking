package com.example.firebasenotes.models

import java.util.Date

data class GastoDTO (
    var fechaDelGasto: Date,
    var formaPago: Int,
    var subTotal: Double,
    var moneda: String,
    var metodoPago: String,
    var emisorNombre: String,
    var emisorRegimen: String,
    var emisorRfc: String,
    var receptorNombre: String,
    var receptorRegimen: String,
    var receptorRfc: String,
    var claveProdServ: Int,
    var descripcion: String,
    var valorUnitario: String,
    var importe: String,
    var comentario: String,
    var esDeducible: Boolean,
    var pdf: String,
    var xml: String,
    var imagen: String,
)