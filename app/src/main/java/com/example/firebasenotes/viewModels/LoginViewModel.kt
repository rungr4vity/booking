package com.example.firebasenotes.viewModels


import android.content.ContentValues
import android.content.Context
import kotlinx.coroutines.delay
import android.util.Log
import android.widget.Toast

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.DataAreas
import com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.DataDrawer
import com.example.firebasenotes.models.horariosModel

import com.example.firebasenotes.models.mainState
import com.example.firebasenotes.models.spaces
import com.example.firebasenotes.models.users
import com.example.firebasenotes.utils.general

import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.type.DateTime
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.Exception

class LoginViewModel:ViewModel(){


    private val _horarios = MutableLiveData<MutableList<String>>(mutableListOf())
    val horarios: LiveData<MutableList<String>> get() = _horarios









    var state by mutableStateOf(mainState())
        private set

    private val auth : FirebaseAuth = Firebase.auth

   fun getData(fecha:Int,espacio:Int){

       println()
       val updatedList = _horarios.value ?: mutableListOf()


       viewModelScope.launch {

           var filtered = emptyList<horariosModel>()
           var horariosActual = getHorariosHoy(fecha,espacio)
           var originales = mutableListOf(
                  horariosModel(1, "9:00 a.m - 2:00 p.m"),
                  horariosModel(2, "1:00 p.m - 6:00 p.m"),
                  horariosModel(3, "6:00 p.m - 9:00 p.m")
           )

           filtered = originales.filterNot { it in horariosActual }

           filtered.forEach {
               updatedList.add(it.nombre)
               _horarios.value = updatedList
           }


//           getHorariosHoy(2052,445).forEach {
//               updatedList.add(it.nombre)
//               _horarios.value = updatedList
//           }



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
                                email = email,
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

                            FirebaseFirestore.getInstance().collection("Usuarios")
                                .add(user)
                                .addOnCompleteListener{
                                    Toast.makeText(context, "Usuario agregado", Toast.LENGTH_SHORT).show()

                                    Toast.makeText(context, "Sign in successful!", Toast.LENGTH_LONG).show()
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


    suspend fun DataFromUsers(email: String) : MutableList <users>{
        val db = FirebaseFirestore.getInstance()
        var usuarios = mutableListOf<users>()

        try {
            //var firebaseAuthId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            val querySnapshot = db.collection("Usuarios")
                .whereEqualTo("email", email)
                .get().await()
            querySnapshot.query


            for (document in querySnapshot.documents){

                Log.d("users_data", document.data?.get("usuarioHabilitado").toString())

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

//    fun getAvailableSpaces(fechaHoy: Int,espacio: Int): List<general.Companion.horarios2> {
//
//        var filtered = emptyList<general.Companion.horarios2>()
//
//        viewModelScope.launch {
//
//            //2047 //441
//            var horariosActual = general.getHorariosHoy(fechaHoy,espacio)
//
//            println(horariosActual)
//
//            var originales = mutableListOf(
//                general.Companion.horarios2(1, "9:00 a.m - 1:00 p.m"),
//                general.Companion.horarios2(2, "1:00 p.m - 6:00 p.m"),
//                general.Companion.horarios2(3, "6:00 p.m - 9:00 p.m")
//            )
//
//            if(horariosActual.isEmpty()){
//                filtered = mutableListOf(
//                    general.Companion.horarios2(1, "9:00 a.m - 1:00 p.m"),
//                    general.Companion.horarios2(2, "1:00 p.m - 6:00 p.m"),
//                    general.Companion.horarios2(3, "6:00 p.m - 9:00 p.m")
//                )
//
//            }else {
//                filtered = originales.filterNot { it in horariosActual }
//            }
//
//
//            for(i in filtered){
//                Log.d("HORARIO", "ERROR:${i}")
//            }
//
//        }
//
//        return filtered
//    }

    suspend fun getHorariosHoy(fecha: Int,espacio: Int):MutableList <horariosModel> {
        var horarios = mutableListOf<horariosModel>()
        val db = FirebaseFirestore.getInstance()
        val usuarios = db.collection("ReservacionCajones")
            .whereEqualTo("fecha", fecha)
            .whereEqualTo("espacio",espacio)

            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {

                    horarios = mutableListOf(
                        horariosModel(1,
                            document.data?.get("horario").toString()
                        )
                    )
                }

            }.addOnFailureListener {
                Log.d("ERROR en getHorariosHoy", "ERROR:${it.localizedMessage}")
            }.await()

        return horarios
    }

    fun login(context: Context,email:String , password:String, onSuccess: () -> Unit){


        viewModelScope.launch {
            try {

                state = state.copy(isLoading = true)
                delay(1000)

                //general.getHorarios()

                var usuariosLit = DataFromUsers(email)
                println(usuariosLit)
                var usuarioHabilitado = usuariosLit[0].usuarioHabilitado.toString().toBoolean()



                auth.signInWithEmailAndPassword(email,password)

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
                                onSuccess()
                            }


                        }else{


                            // leer el modelo de datos y validar eAdmin = true caso contrario desplegar un mensaje de error
                            // (favor de contactar al administrador)
                            //Log.d("Error en firebase","Favor de contactar al administrador")

                            Log.d("Error en firebase","usuario y contraseña incorrectas")
                        }
                    }
            }catch (e:Exception){
                Log.d("ERROR", "ERROR:${e.localizedMessage}")
            }


            state = state.copy(isLoading = false)
        }
    }

    fun saveSpace(
        context: Context,
        email: String,
        company: String,
        horario: String,
        espacio: String,
        day: Int,
        month:Int,
        year: Int,
        onSuccess: () -> Unit) {


        val document = espacio.toString() + day.toString() + month.toString() + year.toString()

        val dateTimeString = year.toString() + "-" + month.toString() + "-" + day.toString()

        //val timest = Timestamp.
        //val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        //val dateTime = LocalDateTime.parse(dateTimeString, formatter)
        var espacioInt:Int = 0
        try {
            espacioInt = espacio.trim().toInt()
        }catch (e:Exception){
            Log.d("ERROR", "ERROR:${e.localizedMessage}")
        }


        val space = spaces(
            id = espacio.toString() + day.toString() + month.toString() + year.toString(),
            email = email,
            horario = horario,
            espacio = espacioInt,
            company = company,
            day = day,
            month = month,
            year = year,
            fecha = year.toInt() + month.toInt()  + day.toInt()
        )

        var str= ""

        viewModelScope.launch {
            try {

                FirebaseFirestore.getInstance().collection("ReservacionCajones").document(document)
                    .set(space)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Parking agregado", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "error al agregar espacio", Toast.LENGTH_SHORT).show()
                    }

            } catch (e:Exception){
                Log.d("ERROR", "ERROR:${e.localizedMessage}")
            }
        }
    } // function


}