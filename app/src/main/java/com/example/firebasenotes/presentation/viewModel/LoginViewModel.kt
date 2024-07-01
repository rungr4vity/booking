package com.example.firebasenotes.presentation.viewModel


import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.delay
import android.util.Log
import android.widget.Toast

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.models.horariosModel

import com.example.firebasenotes.models.mainState
import com.example.firebasenotes.models.spaces
import com.example.firebasenotes.models.users

import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import kotlin.Exception

class LoginViewModel:ViewModel(){

//    private val _horarios_dto = MutableLiveData<MutableList<horariosDTO>>(mutableListOf())
//    val horarios_dto: LiveData<MutableList<horariosDTO>> get() = _horarios_dto



    private val _horarios = MutableLiveData<MutableList<horariosModel>>(mutableListOf())
    val horarios: LiveData<MutableList<horariosModel>> get() = _horarios



    private val _datePicker = MutableLiveData<MutableMap<Int,String>>()
    val horarios_datePicker: LiveData<MutableMap<Int,String>> get() = _datePicker



    var state by mutableStateOf(mainState())
        private set

    private val auth : FirebaseAuth = Firebase.auth



    fun getData(dia:Int,ano:Int,idEstacionamiento:String) {
        viewModelScope.launch() {
            EliminarDisponibilidad(getHorarios(ano,dia,idEstacionamiento))
        }
    }
   fun getData_old(dia:Int,ano:Int,idEstacionamiento:String){

       // me trae lo que esta disponible
       val updatedList = _horarios.value ?: mutableListOf()

       viewModelScope.launch {
           val today = LocalDate.now()
           val dayOfYear = today.dayOfYear

           var filtered = emptyList<horariosModel>()

           var horariosActual = getHorarios(ano,dia,idEstacionamiento)

           var originales = mutableListOf(
                  horariosModel(0, "Matutino 7Am - 12Pm"),
                  horariosModel(1, "Vespertino 12Am - 5Pm"),
                  horariosModel(2, "Nocturno 5Am - 10Pm"),
                  //horariosModel(3, "")
           )

           //high order function
           // boiling code
           filtered = originales.filter { it in horariosActual }

           filtered.forEach {
               updatedList.add(
                   horariosModel(it.valor,it.nombre)
               )
               _horarios.value = updatedList
           }


       }


   }

    fun performCompoundQuery() {
        val db = FirebaseFirestore.getInstance()

        val startTime = Timestamp.now() // Assume this is the starting timestamp
        //val endTime = Timestamp.now() // Assume this is the ending timestamp

        // Perform compound query
        db.collection("ReservacionCajones")

            .whereGreaterThan("fecha",2046)
            //.whereGreaterThanOrEqualTo("fecha", 2046)
            //.whereLessThanOrEqualTo("timestamp_field", endTime)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Handle retrieved documents
                    println("${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                // Handle errors
                println("Error getting documents: $exception")
            }
    }
    fun signInWithEmailAndPassword(context: Context,
                                   email: String,
                                   password: String,
                                   nombre: String,
                                   apellidos: String,
                                   empresa: String,
                                   typeId: Int,
                                   puedeFacturar: Boolean,
                                   usuarioHabilitado:Boolean,
                                   tieneViajeActivo:Boolean,
                                   esAdmin:Boolean,
                                   contrasena: String,
                                   onSuccess: () -> Unit) {



                                    //        val id: String?,
                                    //        val userID: String,
                                    //        val nombres: String,
                                    //        val apellidos: String,
                                    //        val contrasena: String,
                                    //        val email: String,
                                    //        val empresa: String,
                                    //        val typeId: Int,
                                    //        val token: String,
                                    //        val puedeFacturar: Boolean,
                                    //        val usuarioHabilitado:Boolean,
                                    //        val tieneViajeActivo:Boolean,
                                    //        val esAdmin:Boolean
        viewModelScope.launch {
            try {


                var token: String? = FirebaseMessaging.getInstance().token.await()!!

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, navigate to next screen
                            // You can handle navigation or any other action here

                            var userid_ = task.result.user?.uid.toString()

                            val user = users(
                                apellidos = apellidos,
                                contrasena = contrasena,
                                email = email.lowercase(),
                                empresa = empresa,
                                esAdmin = esAdmin,
                                id = userid_,
                                nombres = nombre,
                                puedeFacturar = puedeFacturar,
                                tieneViajeActivo = tieneViajeActivo,
                                typeId = typeId,
                                token = token ?: "",
                                usuarioHabilitado = usuarioHabilitado,
                            ).toMap()

                            FirebaseFirestore.getInstance().collection("Usuarios").document(userid_).set(user)
                                .addOnCompleteListener{
                                    Toast.makeText(context, "Usuario agregado - Bienvenido", Toast.LENGTH_SHORT).show()

                                    //Toast.makeText(context, "Sign in successful!", Toast.LENGTH_LONG).show()
                                }
                                .addOnFailureListener{
                                    Toast.makeText(context, "Failed user add", Toast.LENGTH_SHORT).show()
                                }


                            onSuccess()

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("signInWithEmail:failure", task.exception)
                            // You can show an error message to the user
                        }
                    }


            } catch (e:Exception){
                Log.d("ERROR", "ERROR:${e.localizedMessage}")
            }
        }

    }


    suspend fun DataFromUsers(email: String,context:Context ) : MutableList <users>{
        val db = FirebaseFirestore.getInstance()
        var usuarios = mutableListOf<users>()
        val sharedPreferences = context.getSharedPreferences("shared_usuario", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()


        try {
            //var firebaseAuthId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            val querySnapshot = db.collection("Usuarios")
                .whereEqualTo("email", email)
                .get().await()
            querySnapshot.query


            for (document in querySnapshot.documents){

//                Log.d("users_data", document.data?.get("usuarioHabilitado").toString())
//                editor.putString("empresa", document.data?.get("empresa").toString())
//                editor.apply()
//                Log.d("empresa", "empresa:${document.data?.get("empresa").toString()}")

                usuarios = mutableListOf(
                    users(document.data?.get("apellidos").toString(),
                        document.data?.get("contrasena").toString(),
                        document.data?.get("email").toString(),
                        document.data?.get("empresa").toString(),
                        document.data?.get("esAdmin").toString().toBoolean(),
                        document.data?.get("id").toString(),
                        document.data?.get("nombres").toString(),
                        document.data?.get("puedeFacturar").toString().toBoolean(),
                        document.data?.get("tieneViajeActivo").toString().toBoolean(),
                        document.data?.get("token").toString(),
                        document.data?.get("typeId").toString().toInt(),
                        document.data?.get("usuarioHabilitado").toString().toBoolean())
                )

               //val ar = document.toObject(users::class.java)
//                ar?.let {
//                    usuarios.add(it)
//                }


            }
        }catch (e:Exception){
            Log.d("ERROR_from_users_model", "ERROR:${e.localizedMessage}")
        }



        return usuarios
    }


        fun getLoginEmail(context: Context): String? {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            return sharedPreferences.getString("login_email", null)
        }



//    fun getAll(ano:Int,dia:Int) {
//        viewModelScope.launch {
//            _horarios_dto.value = getHorarios_year_day(ano,dia)
//        }
//
//        println()
//    }


//    suspend fun getHorarios_year_day(dia:Int,ano:Int)
//    :MutableList <horariosDTO> {
//
//        var horarios = mutableListOf<horariosDTO>()
//        val db = FirebaseFirestore.getInstance()
//
//        val usuarios = db.collection("ReservacionEstacionamiento")
//
//            .whereEqualTo("ano", ano)
//            .whereEqualTo("dia",dia)
//            .get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//
//
//                    //Log.d("HORARIO_count", "ERROR:${document.data?.get("turno").toString().toInt()}")
//                    horarios.add(
//                        horariosDTO(
//                            document.data?.get("turno").toString().toInt(),
//
//                            when(document.data?.get("turno").toString().trim().toInt()){
//                                0 -> "7:00 am - 12 pm".trim()
//                                1 -> "12:00 am - 5  pm".trim()
//                                2 -> "5:00 pm - 9:00 pm".trim()
//                                else -> "Todo el día"
//                            },
//
//                            document.data?.get("idEstacionamiento").toString()
//
//                        )
//                    )
//                }
//
//            }.addOnFailureListener {
//                Log.d("ERROR en getHorariosHoy", "ERROR:${it.localizedMessage}")
//            }.await()
//
//        return horarios
//    }

    // funcion que me regresa los registros de la base de datos
    suspend fun getHorarios(ano:Int,dia:Int,idEstacionamiento: String):MutableList <horariosModel> {
        var horarios = mutableListOf<horariosModel>()

        val db = FirebaseFirestore.getInstance()



        val usuarios = db.collection("ReservacionEstacionamiento")

            .whereEqualTo("ano", ano)
            .whereEqualTo("dia",dia)
            .whereEqualTo("idEstacionamiento",idEstacionamiento.trim())

            .get()
            .addOnSuccessListener { documents ->

                for (document in documents) {

                    //Log.d("HORARIO_count", "ERROR:${document.data?.get("turno").toString().toInt()}")
                    horarios.add(
                        horariosModel(
                            document.data?.get("turno").toString().toInt(),

                            when(document.data?.get("turno").toString().trim().toInt()){
                                0 -> "Matutino 7Am - 12Pm".trim()
                                1 -> "Vespertino 12Am - 5Pm".trim()
                                2 -> "Nocturno 5Am - 10Pm".trim()
                                else -> ""
                            }

                        )
                    )
                }

            }.addOnFailureListener {
                Log.d("ERROR en getHorariosHoy", "ERROR:${it.localizedMessage}")
            }.await()

        return horarios
    }

    suspend fun EliminarDisponibilidad(reservaciones:MutableList<horariosModel>): Unit {


        var copiadiccionarioDeTurnos:MutableMap<Int,String> = mutableMapOf<Int,String>(
            0 to "Matutino 7Am - 12Pm",
            1 to "Vespertino 12Am - 5Pm",
            2 to "Nocturno 5Am - 10Pm",
            3 to "Todo el día",
        )

        var updatedList = _datePicker.value ?: emptyMap<Int,String>().toMutableMap()

        var matutino:String = ""
        var vespertino:String = ""
        var nocturno:String = ""


        for(i in reservaciones){
            copiadiccionarioDeTurnos.remove(i.valor)
            when(i.valor) {
                0 -> {
                    matutino = "Red"
                }
                1 -> {
                    vespertino = "Red"
                }
                2 -> {
                    nocturno = "Red"
                }
                3 -> {
                    // limpia la copia
                    matutino = "Red"
                    vespertino = "Red"
                    nocturno = "Red"
                    copiadiccionarioDeTurnos.clear()
                }
                else -> {
                    Log.d("else_EliminarDisponibilidad","break")
                }
            }

            if (copiadiccionarioDeTurnos.count() < 4){
                copiadiccionarioDeTurnos.remove(3)
            }
        }



        //updatedList = copiaDiccionarioTurnos
        //_datePicker.postValue(updatedList)

        _datePicker.value = copiadiccionarioDeTurnos

    }

    private fun llenarDiccionario(copiaDiccionarioTurnos:MutableMap<Int,String>) {
        TODO("Not yet implemented")
        _datePicker.value = copiaDiccionarioTurnos

    }


    fun login(context: Context,email:String , password:String, onSuccess: () -> Unit){
        viewModelScope.launch {
            try {

                state = state.copy(isLoading = true)
                delay(1000)

                //general.getHorarios()

                var usuariosLit = DataFromUsers(email,context)
                var usuarioHabilitado = usuariosLit[0].usuarioHabilitado.toString().toBoolean()



                auth.signInWithEmailAndPassword(email.trim().uppercase(),password.trim())
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){

                            if(!usuarioHabilitado) {
                                Toast.makeText(
                                    context,
                                    "Usuario no habilitado,Favor de contactar al administrador",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            else{
                                val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                editor.putString("login_email", email)
                                editor.apply()

                                onSuccess()
                            }
                        }else{
                            // leer el modelo de datos y validar eAdmin = true caso contrario desplegar un mensaje de error
                            // (favor de contactar al administrador)
                            //Log.d("Error en firebase","Favor de contactar al administrador")




                            Toast.makeText(context, "Usuario o contraseña incorrectas", Toast.LENGTH_SHORT).show()
                            Log.d("Error en firebase","usuario y contraseña incorrectas")
                        }


                    }
            }catch (e:Exception){
                Toast.makeText(context, "Usuario o contraseña incorrectas", Toast.LENGTH_SHORT).show()
                Log.d("ERROR", "ERROR:${e.localizedMessage}")
            }


            state = state.copy(isLoading = false)
        }
    }

    fun saveSpace(
        context: Context,
        ano: Int,
        dia:  Int,
        id: String,
        idEstacionamiento: String,
        idUsuario: String,
        turno: Int,
        onSuccess: () -> Unit) {



//        var espacioInt:Int = 0
//        try {
//            espacioInt = espacio.trim().toInt()
//        }catch (e:Exception){
//            Log.d("ERROR", "ERROR:${e.localizedMessage}")
//        }


//        id = espacio.toString() + day.toString() + year.toString(),
//        email = email,
//        horario = horario,
//        espacio = espacioInt,
//        company = company,
//        day = day,
//        year = year,
//        fecha = year.toInt() + day.toInt(),
//        turno = turno,


        val space = spaces(
             ano = ano,
             dia = dia,
             id = id,
             idEstacionamiento = idEstacionamiento,
             idUsuario = idUsuario,
             turno = turno,
        )

        var str= ""

        viewModelScope.launch {
            try {

                FirebaseFirestore.getInstance().collection("ReservacionEstacionamiento")
                    .document()
                    .set(space)
                    .addOnSuccessListener {
//                        Toast.makeText(context, "Estacionamiento agregado", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "error al agregar estadcionamiento", Toast.LENGTH_SHORT).show()
                    }

            } catch (e:Exception){
                Log.d("ERROR", "ERROR:${e.localizedMessage}")
            }
        }
    } // function


}