package com.example.firebasenotes.ViewMenu.Mipefil


import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class DDViewModel : ViewModel() {
    val state = mutableStateOf(Data())
    private var documentId: String? = null

    init {
        try {
            getData()
        }catch (e: Exception){
            Log.d("TAG", "error")
        }

    }

    private fun getData() {
        viewModelScope.launch {
            val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
            if (!currentUserEmail.isNullOrEmpty()) {
                val userData = getUserData(currentUserEmail)
                state.value = userData.first
                documentId = userData.second
            }
        }
    }

    private suspend fun getUserData(email: String): Pair<Data, String?> {
        val db = FirebaseFirestore.getInstance()
        val querySnapshot = db.collection("Usuarios")
            .whereEqualTo("email", email)
            .get()
            .await()

        return if (!querySnapshot.isEmpty) {
            val document = querySnapshot.documents[0]
            val result = document.toObject(Data::class.java)
            Pair(result ?: throw IllegalStateException("El usuario con el correo electrónico $email no tiene datos asociados."), document.id)
        } else {
            throw IllegalStateException("No se encontró ningún usuario con el correo electrónico $email.")
        }
    }

    fun updateUserData(updatedUserData: Data) {
        state.value = updatedUserData
    }

    fun updateUserDataInFirebase() {
        viewModelScope.launch {
            try {
                val userData = state.value
                val db = FirebaseFirestore.getInstance()
                val docId = documentId ?: throw IllegalStateException("No se pudo encontrar el ID del documento del usuario.")

                val docRef = db.collection("Usuarios").document(docId)

                docRef.update(
                    "nombres", userData.nombres,
                    "apellidos", userData.apellidos,
                    "email", userData.email,
                    "contrasena", userData.contrasena,
                    "empresa", userData.empresa
                    // otros campos que desees actualizar
                ).await()

                Log.d(TAG, "Datos actualizados correctamente en Firebase")
            } catch (e: Exception) {
                Log.w(TAG, "Error al actualizar los datos en Firebase", e)
            }
        }
    }
}