package com.example.firebasenotes.presentation.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.firebasenotes.RegisterScreen
import com.example.firebasenotes.login.viewmodels.LoginViewModel

@Composable
fun RegisterView(navController: NavController, loginVM: LoginViewModel){
    RegisterScreen(navController)
}