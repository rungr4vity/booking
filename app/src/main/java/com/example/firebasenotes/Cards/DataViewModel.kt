package com.example.firebasenotes.Cards

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
class DataViewModel : ViewModel() {
    val state = mutableStateOf<List<About>>(emptyList())

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            state.value = getDataFromFireStore()
        }
    }
}

suspend fun getDataFromFireStore(): List<About> {
    val db = FirebaseFirestore.getInstance()
    val users = mutableListOf<About>()

    try {
        val querySnapshot = db.collection("users").get().await()
        querySnapshot.query
        for (document in querySnapshot.documents) {
            val user = document.toObject(About::class.java)
            user?.let {
                users.add(it)
            }
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("Error", "getDATA")
    }
    return users
}

