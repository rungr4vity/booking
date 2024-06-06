package com.example.firebasenotes.ViewMenu.Mipefil


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DDViewModel : ViewModel() {
    val state = mutableStateOf(Data())

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
            if (!currentUserEmail.isNullOrEmpty()) {
                state.value = getUserData(currentUserEmail)
            } else {

            }
        }
    }

    private suspend fun getUserData(email: String): Data {
        val db = FirebaseFirestore.getInstance()
        val querySnapshot = db.collection("Usuarios")
            .whereEqualTo("email", email)
            .get()
            .await()

        return if (!querySnapshot.isEmpty) {
            val result = querySnapshot.documents[0].toObject(Data::class.java)
            result ?: throw IllegalStateException("El usuario con el correo electrónico $email no tiene datos asociados.")
        } else {
            throw IllegalStateException("No se encontró ningún usuario con el correo electrónico $email.")
        }
    }

}