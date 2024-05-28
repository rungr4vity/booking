package com.example.firebasenotes.ViewMenu.Mipefil

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ActualizarPerfil(ddViewModel: DDViewModel = viewModel()) {
    val userData = ddViewModel.state.value
    var extende by remember { mutableStateOf(false) }
    var menu = listOf("Isita", "Verifigas")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "",
            modifier = Modifier.size(100.dp)
        )
        TextField(
            value = userData.nombres,
            onValueChange = { ddViewModel.state.value = userData.copy(nombres = it) }
        )
        Spacer(modifier = Modifier.size(10.dp))
        TextField(
            value = userData.apellidos,
            onValueChange = { ddViewModel.state.value = userData.copy(apellidos = it) }
        )
        Spacer(modifier = Modifier.size(10.dp))
        TextField(
            value = userData.email,
            onValueChange = { ddViewModel.state.value = userData.copy(email = it) }
        )
        Spacer(modifier = Modifier.size(10.dp))

        ExposedDropdownMenuBox(
            expanded = extende,
            onExpandedChange = { extende = !extende }
        ) {
            OutlinedTextField(
                value = userData.empresa,
                onValueChange = { ddViewModel.state.value = userData.copy(empresa = it) },
                readOnly = false,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = extende) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            )
            ExposedDropdownMenu(expanded = extende, onDismissRequest = { extende = false }) {
                menu.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(text = opcion) },
                        onClick = {
                            ddViewModel.state.value = userData.copy(empresa = opcion)
                            extende = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(8.dp))

        BTN_Actualizar(ddViewModel)
    }
}

@Composable
fun BTN_Actualizar(ddViewModel: DDViewModel) {
    Button(onClick = { updateDataInFirebase(ddViewModel.state.value) }) {
        Text(text = "Actualizar")
    }
}

fun updateDataInFirebase(data: Data) {
    val db = FirebaseFirestore.getInstance()
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
    if (!currentUserEmail.isNullOrEmpty()) {
        db.collection("Usuarios")
            .whereEqualTo("email", currentUserEmail)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val docId = querySnapshot.documents[0].id
                    db.collection("Usuarios").document(docId).set(data)

                }
            }

    } else {
        Log.d(TAG, "Current user email is null or empty")
    }
}
