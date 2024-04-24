package com.example.firebasenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class InputActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //LoginScreen()

        }
    }
}
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    var selectedOptionText  by remember { mutableStateOf("Isita") }
    var expanded  by remember { mutableStateOf(false) }
    val options = listOf("Isita", "Verifigas")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = com.google.android.gms.base.R.drawable.common_full_open_on_phone),
            contentDescription = "Logo",
            modifier = Modifier.size(240.dp)
        )
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("Nombres") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Apellidos") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {expanded = !expanded} ) {
            OutlinedTextField(
                value = selectedOptionText,
                onValueChange = {  },
                label = { Text("Compañia") },
                readOnly = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)}
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    options.forEach { option ->
                        DropdownMenuItem(text = { Text(option)}, onClick = {
                            selectedOptionText = option
                            expanded = false
                        },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword },modifier = Modifier.height(25.dp)) {
                    Icon(
                        painter = if (showPassword) painterResource(id = R.drawable.ic_launcher_foreground) else painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = if (showPassword) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { /* Realizar registro */ }),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Realizar registro */ },
            enabled = isValid(firstName, lastName, email, password),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
        Spacer(modifier = Modifier.height(16.dp)) // Spacer para mantener el botón visible aunque el teclado esté activo
    }
}


private fun isValid(firstName: String, lastName: String, email: String, password: String): Boolean {
    val emailPattern = Regex("^\\S+@\\S+\\.\\S+\$")
    return firstName.isNotBlank() && lastName.isNotBlank() &&
            email.matches(emailPattern) && password.length >= 8
}


@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var canLogin by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = com.google.android.gms.base.R.drawable.common_full_open_on_phone),
            contentDescription = "Logo",
            modifier = Modifier.size(240.dp)
        )
        Spacer(modifier = Modifier.height(0.dp))
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                checkLoginEnabled(username, password, rememberMe) { canLogin = it }
            },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                checkLoginEnabled(username, password, rememberMe) { canLogin = it }
            },
            label = { Text("Contraseña") },
            visualTransformation = if (!passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }, modifier = Modifier.height(25.dp)) {
                    Icon(
                        painter = painterResource(id = if (passwordVisible) R.drawable.ic_launcher_foreground else R.drawable.ic_launcher_foreground),
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = {
                        rememberMe = it
                        checkLoginEnabled(username, password, rememberMe) { canLogin = it }
                    }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Recordar credenciales")
            }
            /*TextButton(onClick = { /* Olvidaste tu contraseña? */ }) {
                Text("Olvidaste tu contraseña?")
            }*/
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Realizar inicio de sesión */ },
            enabled = canLogin,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Iniciar sesión")
        }
    }
}
private fun checkLoginEnabled(
    username: String,
    password: String,
    rememberMe: Boolean,
    onUpdate: (Boolean) -> Unit
) {
    val canLogin = username.length >5 && password.length>7
    onUpdate(canLogin)
}