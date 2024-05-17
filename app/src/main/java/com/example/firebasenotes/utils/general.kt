package com.example.firebasenotes.utils

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

import com.google.firebase.Timestamp

interface general {


    companion object {

        data class horarios2(
            val valor: Int,
            val nombre: String
        )

        // me trae todas las reservaciones mayores a hoy
        suspend fun getHorarios() {

            val lista = Constantes.horarios
            var allUsers = mutableListOf<Any>()
            val db = FirebaseFirestore.getInstance()
            val usuarios = db.collection("reservacionCajones").whereGreaterThan("fecha", 2045).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    }

                }.addOnFailureListener {
                Log.d("ERROR", "ERROR:${it.localizedMessage}")
            }.await()

            var str: String = ""
        }

        suspend fun getHorariosHoy():MutableList<horarios2> {

            val lista = Constantes.horarios
            var horarios: MutableList<horarios2> = mutableListOf()

            val db = FirebaseFirestore.getInstance()
            val usuarios = db.collection("reservacionCajones")
                .whereEqualTo("fecha", 2047)
                .whereEqualTo("espacio",441)

                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                        Log.d(ContentValues.TAG, "${document.get("horario").toString()}")
                        horarios = mutableListOf(
                            horarios2(1,document.get("horario").toString())
                        )
                    }

                }.addOnFailureListener {
                    Log.d("ERROR", "ERROR:${it.localizedMessage}")
                }.await()

            return horarios

        }

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