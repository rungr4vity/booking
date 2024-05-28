package com.example.firebasenotes.navigation

import App
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebasenotes.viewModels.LoginViewModel
import com.example.firebasenotes.viewModels.NotesViewModel
import com.example.firebasenotes.views.login.TabsViews


@Composable
fun NavManager(loginVM:LoginViewModel, notesVM:NotesViewModel){
val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Login" ){
        composable("Login"){
            TabsViews(navController,loginVM)
        }
            composable("Home"){
                App()
            }

    }
}