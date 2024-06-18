package com.example.firebasenotes.ViewMenu

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firebasenotes.R
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.font.FontWeight
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun MiPerfil() {
    Scaffold {
        val isDarkMode = isSystemInDarkTheme()
        CompositionLocalProvider(LocalContentColor provides if (isDarkMode) Color.White else Color.Black) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hola: ${Firebase.auth.currentUser?.email ?: ""}", fontWeight = FontWeight.Medium, color =if (isDarkMode) Color.White else Color.Black, modifier = Modifier.padding(top = 15.dp))

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Image(
                    painter = painterResource(id = R.drawable.img_1),
                    contentDescription = "",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 15.dp))
            }





//            Icon(
//
//                imageVector = Icons.Default.Home, modifier = Modifier.size(100.dp),
//                contentDescription = "", // Cambia el tamaño según lo desees
//            )
        }
    }
}}