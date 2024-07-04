package com.example.firebasenotes.navigation

import App
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebasenotes.login.viewmodels.LoginViewModel
import com.example.firebasenotes.presentation.viewModel.NotesViewModel
import com.example.firebasenotes.presentation.view.TabsViews


@Composable
fun NavManager(loginVM: LoginViewModel, notesVM: NotesViewModel){
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