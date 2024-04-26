package com.example.firebasenotes.cards

import com.example.firebasenotes.R

object DataProvider {
    val dpersonal =
        Personals(
            nombre = "ADMIN",
            fecha = "",
            email = "admin@ut.com",
            desc = "",
            perImageId = R.drawable.img
        )

    val personalList = listOf(
        dpersonal,

        Personals(  nombre = "Erick",
            fecha = "",
            email = "erick@gmail.com",
            desc = "",
            perImageId = R.drawable.img
    ))
}