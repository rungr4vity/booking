package com.example.firebasenotes.WidgetsCardView.Listing.ListAreas

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
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

    fun InsertDatosArea(capacidadDpersona:String,nombreArea:String,mobiliaria:String,descripcion:String){
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            val date = hashMapOf(
                    "capacidadDpersonas" to capacidadDpersona,
                    "nombreArea" to nombreArea,
                    "mobiliaria" to mobiliaria,
                    "descripcion" to descripcion

                    )
            db.collection("areas").add(date).await()
        }
    }

}