package com.example.firebasenotes.views.login

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.firebasenotes.RegisterScreen
import com.example.firebasenotes.viewModels.LoginViewModel

@Composable
fun RegisterView(navController: NavController, loginVM: LoginViewModel){
    RegisterScreen(navController)
}