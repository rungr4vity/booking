package com.example.firebasenotes.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.firebasenotes.RegisterScreen
import com.example.firebasenotes.viewModel.LoginViewModel

@Composable
fun RegisterView(navController: NavController, loginVM: LoginViewModel){
    RegisterScreen(navController)
}