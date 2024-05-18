package com.example.firebasenotes.viewModels


import android.content.Context
import kotlinx.coroutines.delay
import android.util.Log
import android.widget.Toast

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

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

    var state by mutableStateOf(mainState())
        private set

    private val auth : FirebaseAuth = Firebase.auth

    fun performCompoundQuery() {
        val db = FirebaseFirestore.getInstance()

        val startTime = Timestamp.now() // Assume this is the starting timestamp
        //val endTime = Timestamp.now() // Assume this is the ending timestamp

        // Perform compound query
        db.collection("reservacionCajones")

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
    fun signInWithEmailAndPassword(context: Context, email: String, password: String, nombre: String, apellidos: String, empresa: String, typeid: Int, onSuccess: () -> Unit) {

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
                                userID = userid_,
                                nombre = nombre,
                                apellidos = apellidos,
                                email = email,
                                empresa = empresa,
                                id = userid_,
                                typeid = typeid,
                                token = token ?: ""
                            ).toMap()

                            FirebaseFirestore.getInstance().collection("users")
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

    fun login(email:String , password:String, onSuccess: () -> Unit){
        viewModelScope.launch {
            try {



                state = state.copy(isLoading = true)
                delay(1000)
                //general.getHorarios()
                var horariosActual = general.getHorariosHoy(2047,441)
                var originales = mutableListOf(
                    general.Companion.horarios2(1, "9:00 a.m - 1:00 p.m"),
                    general.Companion.horarios2(2, "1:00 p.m - 6:00 p.m"),
                    general.Companion.horarios2(3, "6:00 p.m - 9:00 p.m")
                )
                val filtered = originales.filterNot { it in horariosActual }

                for(i in filtered){
                    Log.d("HORARIO", "ERROR:${i}")
                }



                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){
                            onSuccess()

                        }else{
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

                FirebaseFirestore.getInstance().collection("reservacionCajones").document(document)
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