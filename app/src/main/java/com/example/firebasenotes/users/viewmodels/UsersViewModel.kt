package com.example.firebasenotes.users.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.models.DataViaticos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UsersViewModel : ViewModel() {
    val stateUsers = mutableStateOf<List<DataViaticos>>(emptyList())

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            stateUsers.value = getDataFromFirebase()
        }
    }

    suspend fun getDataFromFirebase(): List<DataViaticos> {
        val db = FirebaseFirestore.getInstance()
        val users = mutableListOf<DataViaticos>()
        val querySnapshot = db.collection("Usuarios").get().await()

        for (document in querySnapshot.documents) {
            val user = document.toObject(DataViaticos::class.java)
            user?.let {
                users.add(it)
            }
        }
        return users
    }

    fun updateUserData(userData: DataViaticos) {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            val currentUserEmail = userData.email
            if (!currentUserEmail.isNullOrEmpty()) {
                val querySnapshot = db.collection("Usuarios")
                    .whereEqualTo("email", currentUserEmail)
                    .get()
                    .await()

                if (!querySnapshot.isEmpty) {
                    val docId = querySnapshot.documents[0].id
                    db.collection("Usuarios").document(docId).update(
                        mapOf(
                            "puedeFacturar" to userData.puedeFacturar,
                            "usuarioHabilitado" to userData.usuarioHabilitado,
                            "typeId" to userData.typeId,
                            "tieneViajeActivo" to userData.tieneViajeActivo
                        )
                    )
                }
            }
        }
    }

    fun updateTieneViajeActivo(value: Boolean) {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            val userEmail = FirebaseAuth.getInstance().currentUser?.email

            if (userEmail != null) {
                val querySnapshot = db.collection("Usuarios")
                    .whereEqualTo("email", userEmail)
                    .get()
                    .await()

                if (!querySnapshot.isEmpty) {
                    val docId = querySnapshot.documents[0].id
                    db.collection("Usuarios").document(docId).update("tieneViajeActivo", value).await()
                }
            }
        }
    }

}
