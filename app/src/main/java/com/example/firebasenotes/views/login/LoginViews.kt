package com.example.firebasenotes.views.login

import android.content.ContentValues.TAG
import android.credentials.CredentialManager
import android.credentials.GetCredentialRequest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebasenotes.viewModels.LoginViewModel
import com.example.firebasenotes.viewModels.NotesViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.rpc.context.AttributeContext
import com.google.rpc.context.AttributeContext.Resource
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID
import java.util.concurrent.Flow

@Composable
fun LoginView(navController: NavController,loginVM:LoginViewModel){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        var email by remember { mutableStateOf("tospaces7@gmail.com") }
        var password by remember { mutableStateOf("12345678") }

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)) {
            Text(text = "Entrar")
        }


    }

}
