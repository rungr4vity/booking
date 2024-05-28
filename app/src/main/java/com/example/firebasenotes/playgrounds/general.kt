package com.example.firebasenotes.playgrounds

import java.time.LocalDate

fun main () {

    val today = LocalDate.now()
    val dayOfYear = today.dayOfYear


    println(dayOfYear.toString())
}