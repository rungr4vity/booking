package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.WidgetsCardView.Data
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

suspend fun FireStoreCajonData(): MutableList<DataDrawer> {
    val db = FirebaseFirestore.getInstance()
    val cajon = mutableListOf<DataDrawer>()
    val querySnapshot = db.collection("parking").get().await()
    querySnapshot.query
    for (document in querySnapshot.documents) {
        val cajones = document.toObject(DataDrawer::class.java)
        cajones?.let {
            cajon.add(it)
        }
    }
    return cajon
}

class DrawerViewModel : ViewModel() {
    val stateDrawer = mutableStateOf<List<DataDrawer>>(emptyList())

    init {
        getData()
    }
    private fun getData() {
        viewModelScope.launch {
            stateDrawer.value =
                com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.FireStoreCajonData()
        }
    }



}