package com.example.firebasenotes.utils

import android.content.ContentValues
import android.util.Log
import com.example.firebasenotes.models.viajeDTO
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

interface viajesInterface {

    companion object{
        suspend fun getViajes():List<viajeDTO> {

            var allData = mutableListOf<Any>()

            val db = FirebaseFirestore.getInstance()
            val usuarios = db.collection("Viajes")
                //.whereGreaterThan("fecha", fecha)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    }

                }.addOnFailureListener {
                    Log.d("ERROR", "ERROR:${it.localizedMessage}")
                }.await()

           return allData as List<viajeDTO>
        }
    }

}