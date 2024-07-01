package com.example.firebasenotes.viewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.firebasenotes.models.Data
import com.example.firebasenotes.models.DataReservations
import com.example.firebasenotes.models.DataTurnos
import com.example.firebasenotes.models.DataDrawer
import com.google.firebase.firestore.FirebaseFirestore

class SharedPreferencesManager(private val context: Context) {
    private val sharedPref = context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)

    fun saveUserData(userData: Data) {
        with(sharedPref.edit()) {
            putString("apellidos", userData.apellidos)
            putString("contrasena", userData.contrasena)
            putString("email", userData.email)
            putString("empresa", userData.empresa)
            putBoolean("esAdmin", userData.esAdmin ?: false)
            putString("id", userData.id)
            putString("nombres", userData.nombres)
            putBoolean("puedeFacturar", userData.puedeFacturar ?: true)
            putBoolean("tieneViajeActivo", userData.tieneViajeActivo ?: true)
            putInt("typeId", userData.typeId ?: 0)
            putBoolean("usuarioHabilitado", userData.usuarioHabilitado ?: true)
            putString("userID", userData.userID)
            putString("token", userData.token)
            apply()
        }
    }

    fun getUserData(): Data {
        return Data(
            sharedPref.getString("apellidos", "") ?: "",
            sharedPref.getString("contrasena", "") ?: "",
            sharedPref.getString("email", "") ?: "",
            sharedPref.getString("empresa", "") ?: "",
            sharedPref.getBoolean("esAdmin", false),
            sharedPref.getString("id", "") ?: "",
            sharedPref.getString("nombres", "") ?: "",
            sharedPref.getBoolean("puedeFacturar", true),
            sharedPref.getBoolean("tieneViajeActivo", true),
            sharedPref.getInt("typeId", 0),
            sharedPref.getBoolean("usuarioHabilitado", true),
            sharedPref.getString("userID", "") ?: "",
            sharedPref.getString("token", "") ?: ""
        )
    }

    fun updateUserData(userData: Data) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("Usuarios").document(userData.email)

        docRef.update(
            "nombres", userData.nombres,
            "apellidos", userData.apellidos,
            "email", userData.email,
            // otros campos que desees actualizar
        )
            .addOnSuccessListener {
                Log.d(TAG, "Datos actualizados correctamente en Firebase")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error al actualizar los datos en Firebase", e)
            }
    }
}

class UnifiedFirebaseManager(private val context: Context) {
    private val sharedPref = context.getSharedPreferences("unified_pref", Context.MODE_PRIVATE)
    private val db = FirebaseFirestore.getInstance()

    // Obtener datos de la colección "Estacionamiento"
     fun FireStoreCajonData(callback: (DataDrawer) -> Unit) {
        db.collection("Estacionamientos").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
//                    val estacionamientoData = document.toObject(DataReservations::class.java)
                    val estacionamientoData = DataDrawer(
                        numero = document.getString("numero").toString().toInt() ?: 0,
                        nombre = document.getString("nombre") ?: "",
                        piso = document.getString("piso") ?: "",
                        id = document.id,
                        empresa = document.getString("empresa") ?: "",

                    )
                    callback(estacionamientoData)
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores
            }
    }


    // Obtener datos de la colección "ReservacionEstacionamiento"
    fun getReservacionEstacionamientoData(callback: (DataReservations) -> Unit) {
        db.collection("ReservacionEstacionamiento").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
//                    val reservacionData = document.toObject(DataReservations::class.java)
                    val reservacionData = DataReservations(
                        ano = document.getString("ano").toString().toInt() ?: 0,
                        dia = document.getString("dia").toString().toInt() ?: 0,
                        id = document.id,
                        idEstacionamiento = document.getString("idEstacionamiento") ?: "",
                        idUsuario = document.getString("idUsuario") ?: "",
                        turno = document.getString("turno").toString().toInt() ?: 0
                    )
                    callback(reservacionData)
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores
            }
    }

    // Obtener datos de la colección "Turnos"
    fun getTurnosData(callback: (DataTurnos) -> Unit) {
        db.collection("TurnosEstacionamiento").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val turnosData = document.toObject(DataTurnos::class.java)
                    callback(turnosData)
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores
            }
    }



    // Guardar los datos combinados en SharedPreferences
    fun saveUnifiedData(estacionamientoData: DataDrawer?, reservacionData: DataReservations?, turnosData: DataTurnos?) {
        with(sharedPref.edit()) {
            estacionamientoData?.let {
                putInt("estacionamiento_numero", it.numero ?: 0)
                putString("estacionamiento_nombre", it.nombre)
                putString("estacionamiento_piso", it.piso)
                putString("estacionamiento_id", it.id)
                putString("estacionamiento_empresa", it.empresa)
                putBoolean("estacionamiento_esEspecial", it.esEspecial ?: false)
            }

            reservacionData?.let {
                putString("reservacion_ano", it.ano.toString())
                putString("reservacion_id", it.id)
                putString("reservacion_idUsuario", it.idUsuario)
                putString("reservacion_Estacionamiento", it.idEstacionamiento)
                putString("reservacion_dia", it.dia.toString())
                putString("reservacion_turno", it.turno.toString())

            }
            turnosData?.let {
                putString("turnos_id", it.id.toString())
                putString("turno_turno", it.turno.toString())

            }
            apply()
        }
    }
}



