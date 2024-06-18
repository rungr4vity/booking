package com.example.firebasenotes.views.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebasenotes.R
import com.example.firebasenotes.viewModels.LoginViewModel
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginView(navController: NavController, loginVM: LoginViewModel) {
    var state = loginVM.state
    var context = LocalContext.current
    var showPassword by remember { mutableStateOf(value = false) }
    var password by remember { mutableStateOf("") }


    Scaffold(
        Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ) {



        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.dp, end = 5.dp)
            ) {
                // homer@op.com
                // 12345678

                // luis.montemayor@isita.com.mx
                // 1412918Luis
                Image(
                    painter = painterResource(id = R.drawable.isita3), // Reemplaza 'your_image' con el nombre de tu imagen
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 20.dp)
                )

                var email_shared = loginVM.getLoginEmail(context) ?: ""
                Log.d("email_shared", email_shared)

//                var email by remember { mutableStateOf("") }
//                var password by remember { mutableStateOf("") }
//                var email by remember { mutableStateOf(email_shared) }
//                var password by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("francisco.perez@isita.com.mx") }
                var password by remember { mutableStateOf("12345678") }

                Text(text = "Inicio de sesión", Modifier.padding(bottom = 20.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "e-mail") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                )
                Spacer(modifier = Modifier.padding(bottom = 10.dp))
//                OutlinedTextField(
//                    value = password,
//                    onValueChange = { password = it },
//                    label = { Text(text = "contraseña") },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 15.dp),
//                    visualTransformation = PasswordVisualTransformation(),
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//
//                )
                PasswordTextField(
                    label = "Password",
                    password = password,
                    onPasswordChange = { password = it }
                )



                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                Button(
                    onClick = {
                        loginVM.login(context, email, password) {
                            navController.navigate("Home")
                        }
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    colors = ButtonDefaults.buttonColors( Color(0xFF800000))
                ) {
                    Text(text = "Entrar",
                        color = Color.White)
                }
            }
        }
    }
}

@Composable
fun PasswordTextField(
    label: String,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = null)
            }
        }
    )
}

@Composable
fun PasswordField(password:String,onTextFieldChanged:(String)-> Unit) {
    var showPassword by remember { mutableStateOf(value = false) }

    TextField(
        value = password,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),

        placeholder = { Text(text = "Password")},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            if (showPassword) {
                IconButton(onClick = { showPassword = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "hide_password"
                    )
                }
            } else {
                IconButton(
                    onClick = { showPassword = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "hide_password"
                    )
                }
            }
        },

        colors = TextFieldDefaults.colors(
            //setting the text field background when it is focused
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,

            //setting the text field background when it is unfocused or initial state
            unfocusedContainerColor = Color.Transparent,

            //setting the text field background when it is disabled
            disabledContainerColor = Color.Transparent,
        ),
    )
}

@Composable
fun loadingCircle(viewModel: LoginViewModel) {
    TODO("Not yet implemented")
    var state = viewModel.state
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
