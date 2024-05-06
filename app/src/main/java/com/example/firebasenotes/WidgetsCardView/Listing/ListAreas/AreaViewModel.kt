package com.example.firebasenotes.WidgetsCardView.Listing.ListAreas

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

suspend fun DataFromArea() : MutableList <DataAreas>{
    val db = FirebaseFirestore.getInstance()
    val areas = mutableListOf<DataAreas>()
    val querySnapshot = db.collection("areas").get().await()
    querySnapshot.query


    for (document in querySnapshot.documents){
        val ar = document.toObject(DataAreas::class.java)
        ar?.let {
            areas.add(it)
        }
    }
    return areas
}

class AreaViewModel : ViewModel(){
    val statearea = mutableStateOf<List<DataAreas>>(emptyList())
    init {
        getData()
    }
    private fun getData() {
        viewModelScope.launch {
           statearea.value = com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.DataFromArea()
        }
    }



}