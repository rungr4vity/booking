package com.example.firebasenotes.ViewMenu.Mipefil

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasenotes.R
import com.example.firebasenotes.SharedPreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ActualizarPerfil(ddViewModel: DDViewModel = viewModel(), sharedPreferencesManager: SharedPreferencesManager) {
    val userData by ddViewModel.state
    var extende by remember { mutableStateOf(false) }
    val menu = listOf("Isita", "Verifigas")


    CompositionLocalProvider(
        LocalContentColor provides Color.Black,
        content = {
        // Aquí se aplica el tema oscuro
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.isita3), // Reemplaza 'your_image' con el nombre de tu imagen
                contentDescription = "Logo",
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 20.dp)
            )
            TextField(
                singleLine = true,
                value = userData.nombres,
                onValueChange = {
                    if (it.length <= 15) {

                        ddViewModel.updateUserData(userData.copy(nombres = it))
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.size(18.dp))
            TextField(
                singleLine = true,
                value = userData.apellidos,
                onValueChange = {
                    if (it.length <= 15) {
                        ddViewModel.updateUserData(userData.copy(apellidos = it))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.size(18.dp))
            TextField(
                singleLine = true,
                readOnly = true,
                value = userData.email,
                onValueChange = { ddViewModel.updateUserData(userData.copy(email = it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
//        Spacer(modifier = Modifier.size(18.dp))
//        TextField(
//            value = userData.contrasena,
//            onValueChange = { ddViewModel.updateUserData(userData.copy(contrasena = it)) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp)
//        )
            Spacer(modifier = Modifier.size(18.dp))

            val isDarkMode = isSystemInDarkTheme()
            CompositionLocalProvider(LocalContentColor provides if (isDarkMode) Color.White else Color.Black) {
                ExposedDropdownMenuBox(
                    expanded = extende,
                    onExpandedChange = { extende = !extende },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = userData.empresa,
                        onValueChange = { ddViewModel.updateUserData(userData.copy(empresa = it)) },
                        readOnly = false,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = extende) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = extende,
                        onDismissRequest = { extende = false },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        menu.forEach { opcion ->
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                                text = { Text(text = opcion, color = Color.Black) }, // Aquí se cambia el color a negro
                                onClick = {
                                    ddViewModel.updateUserData(userData.copy(empresa = opcion))
                                    extende = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.size(50.dp))

            Button(
                onClick = {
                    ddViewModel.updateUserDataInFirebase()
                    sharedPreferencesManager.updateUserData(userData)
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF800000))
            ) {
                Text(text = "Actualizar", color = Color.White)
            }
        }
    })
}