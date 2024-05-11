package com.example.firebasenotes.viewModels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.models.spaces
import com.example.firebasenotes.models.users
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.Exception

class LoginViewModel:ViewModel(){

    private val auth : FirebaseAuth = Firebase.auth


    // Firebase authentication function
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
        }
    }



    fun saveSpace(
        context: Context,
        email: String,
        company: String, horario: String, espacio: String,
        day: Int,
        month:Int,
        year: Int,
        onSuccess: () -> Unit) {


        val space = spaces(
            id = espacio + day.toString() + month.toString() + year.toString(),
            email = email,
            horario = horario,
            espacio = espacio,
            company = company,
            day = day,
            month = month,
            year = year,
        )

        viewModelScope.launch {

            try {

                FirebaseFirestore.getInstance().collection("reservacionCajones")
                    .add(space)
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