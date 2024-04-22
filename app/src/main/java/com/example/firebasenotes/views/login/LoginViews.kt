package com.example.firebasenotes.views.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebasenotes.viewModels.LoginViewModel
import com.example.firebasenotes.viewModels.NotesViewModel

@Composable
fun LoginView(navController: NavController,loginVM:LoginViewModel){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        OutlinedTextField(value = email, onValueChange = {email = it}, label = {Text(text ="Email")}
        , modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp))

        OutlinedTextField(value = password, onValueChange = {password = it}, label = {Text(text ="Password")}
            , modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        
        
        Button(onClick = {
                         loginVM.login(email,password){
                             navController.navigate("Home")
                         }
        },
            modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp)) {
            Text(text = "Entrar")
        }


    }

}