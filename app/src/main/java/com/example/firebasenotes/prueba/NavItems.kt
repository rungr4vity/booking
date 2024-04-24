package com.example.feature_appbar.navigation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItems(
    val label: String ,

    val route : String,

)

val listOfNavItems = listOf(

    NavItems(label = "Reservar Sitio",
        route = Screens.Menu.name
    ),
    NavItems(label = "Mis Reservas",
        route = Screens.Registro.name,

    )
)