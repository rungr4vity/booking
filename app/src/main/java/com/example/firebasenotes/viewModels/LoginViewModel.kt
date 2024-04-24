package com.example.firebasenotes.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.rpc.context.AttributeContext.Resource
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.concurrent.Flow

class LoginViewModel:ViewModel(){

    private val auth : FirebaseAuth = Firebase.auth

    fun login(email:String , password:String, onSuccess: () -> Unit){
        viewModelScope.launch {
            try {
auth.signInWithEmailAndPassword(email,password)
    .addOnCompleteListener{task ->
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

}