package com.example.firebasenotes.utils

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

import com.google.firebase.Timestamp

interface general {


    companion object {




//        suspend fun getHorarios(fecha: Int):MutableList<horarios2> {
//
//            val lista = Constantes.horarios
//            var allUsers: MutableList<horarios2> = mutableListOf<horarios2>()
//                //mutableListOf<Any>()
//            val db = FirebaseFirestore.getInstance()
//            val usuarios = db.collection("reservacionCajones").whereGreaterThan("fecha", fecha).get()
//                .addOnSuccessListener { documents ->
//                    for (document in documents) {
//                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
//
//                        allUsers = mutableListOf(
//                            horarios2(document.get("id").toString().toInt(),document.get("horario").toString())
//                        )
//
//                    }
//
//                }.addOnFailureListener {
//                Log.d("ERROR", "ERROR:${it.localizedMessage}")
//            }.await()
//
//            var str: String = ""
//            return allUsers
//        }


        fun performCompoundQuery() {
            val db = FirebaseFirestore.getInstance()

            val startTime = Timestamp.now() // Assume this is the starting timestamp
            val endTime = Timestamp.now() // Assume this is the ending timestamp

            // Perform compound query
            db.collection("your_collection")
                .whereGreaterThanOrEqualTo("timestamp_field", startTime)
                .whereLessThanOrEqualTo("timestamp_field", endTime)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        // Handle retrieved documents
                        println("${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle errors
                    println("Error getting documents: $exception")
                }
        }

        data class horarios(
            val valor: Int,
            val nombre: String
        )

        //val numbers = listOf(1, 2, 3, 4, 5)
        //
        //// Filter out numbers that are not in a specific list


        //val numbersToKeep = listOf(2, 4)
        //val filteredNumbers = numbers.filterNot { it in numbersToKeep }
        //
        //println(filteredNumbers) // Output: [1, 3, 5]


        var originales = mutableListOf(
            horarios(1, "9 am"),
            horarios(2, "12 pm"),
            horarios(3, "9 pm")
        )

        var data = mutableListOf(
            horarios(1, "9 am"),
            horarios(2, "12 pm")
        )


        val filtered = originales.filterNot { it in data }

    }
}