package com.example.firebasenotes.Oficinas

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.AreaViewModel

@Composable
fun EditarOficinas(
    idArea: String,
    capacidad : String,
    descripcion: String,
    id: String,
    mobilaria: String,
    nombre : String,
    areaViewModel: AreaViewModel = viewModel()
) {
    var nombreText by remember { mutableStateOf(nombre) }
    var descripcionText by remember { mutableStateOf(descripcion) }
    var capacidadText by remember { mutableStateOf(capacidad) }

    Column(modifier = Modifier.padding(16.dp)) {
        // Campo para editar el nombre
        OutlinedTextField(
            value = nombreText,
            onValueChange = { nombreText = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para editar la descripción
        OutlinedTextField(
            value = descripcionText,
            onValueChange = { descripcionText = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para editar la capacidad
        OutlinedTextField(
            value = capacidadText,
            onValueChange = { capacidadText = it },
            label = { Text("Capacidad") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        val Context = LocalContext.current
        // Botón de guardar cambios
        Button(
            onClick = {
                areaViewModel.actualizarOficina(
                    idArea = idArea,
                    nombre = nombreText,
                    descripcion = descripcionText,
                    capacidad = capacidadText,
                    mobilaria = mobilaria,
                    id = id
                )

                Toast.makeText(Context, "Cambios guardados", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color(0xFF800000))
        ) {
            Text("Guardar cambios", color = Color.White)
        }
    }
}
