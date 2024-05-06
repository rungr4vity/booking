package com.example.firebasenotes.WidgetsCardView

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DViewModel : ViewModel() {
    val statee = mutableStateOf<List<Data>>(emptyList())

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            statee.value = com.example.firebasenotes.WidgetsCardView.DataFrom()
        }
    }
}


    suspend fun DataFrom(): MutableList<Data> {
        val db = FirebaseFirestore.getInstance()
        val spaces = mutableListOf<Data>()
        val querySnapshot = db.collection("spaces").get().await()
        querySnapshot.query
        for (document in querySnapshot.documents) {
            val space = document.toObject(Data::class.java)
            space?.let {
                spaces.add(it)
            }
        }
        return spaces
    }
