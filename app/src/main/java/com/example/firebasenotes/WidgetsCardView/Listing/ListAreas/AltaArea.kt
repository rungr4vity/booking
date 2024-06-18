package com.example.firebasenotes.WidgetsCardView.Listing.ListAreas

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.firebasenotes.R
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.util.UUID


@Composable
fun AltaArea(areaViewModel: AreaViewModel = viewModel(), navController: NavController) {
    var desc by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var capacidad by remember { mutableStateOf("") }
    var mobiliaria by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri.toString()
    }
    Spacer(modifier = Modifier.height(20.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LimitedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = "Nombre sala",
            maxLength = MAX_LENGTH_NOMBRE,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 5.dp)
        )
        LimitedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = "Descripción",
            maxLength = MAX_LENGTH_DESC,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 5.dp),
            maxLines = 1
        )
        LimitedTextField(
            value = capacidad,
            onValueChange = { capacidad = it },
            label = "Cantidad de personas",
            maxLength = MAX_LENGTH_CAPACIDAD,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 5.dp),
            maxLines = 1
        )
        LimitedTextField(
            value = mobiliaria,
            onValueChange = { mobiliaria = it },
            label = "Mobiliario",
            maxLength = MAX_LENGTH_MOBILIARIA,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 5.dp),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(20.dp))
// Espacio para mostrar la imagen seleccionada
        selectedImageUri?.let { uri ->
            Image(
                painter = rememberImagePainter(uri), // Placeholder o imagen predeterminada
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(150.dp),
                contentScale = ContentScale.Crop
            )
        }
Spacer(modifier = Modifier.height(10.dp))
        // Botón para seleccionar imagen
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier
                .padding(horizontal = 80.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF800000))

            ) {
            Text("Seleccionar Imagen", color = Color.White)
        }

        Spacer(modifier = Modifier.height(80.dp))

        val Context = LocalContext.current
        Button(
            shape = RoundedCornerShape(5.dp),
            onClick = {

                Toast.makeText(Context, "Creando área...", Toast.LENGTH_SHORT).show()
                if (nombre.isNotEmpty() && desc.isNotEmpty() && capacidad.isNotEmpty() && mobiliaria.isNotEmpty() && selectedImageUri != null) {

                    // Subir imagen a Firebase Storage
                    val storageRef = Firebase.storage.reference
                    val uuid = UUID.randomUUID().toString()
                    val imageRef = storageRef.child("images/$uuid")
                    val uploadTask = imageRef.putFile(selectedImageUri!!.toUri())

                    uploadTask.addOnSuccessListener {
                        // Obtener URL de descarga de la imagen
                        imageRef.downloadUrl.addOnSuccessListener { uri ->

                            // Insertar datos del área con la URL de la imagen en Firestore
                            areaViewModel.InsertDatosArea(capacidad, nombre, mobiliaria, desc, uri.toString())


                            // Limpiar campos después de insertar
                            capacidad = ""
                            nombre = ""
                            mobiliaria = ""
                            desc = ""
                            selectedImageUri = null
                        }

                    }.addOnFailureListener {
                        // Manejar errores si falla la carga de la imagen
                    }
                }
            },
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
                .height(45.dp),
            enabled = nombre.isNotEmpty() && desc.isNotEmpty() && capacidad.isNotEmpty() && mobiliaria.isNotEmpty() && selectedImageUri != null,
            colors = ButtonDefaults.buttonColors(Color(0xFF800000))
        ) {
            Text("Crear área", color = Color.White)
        }
    }
}

private const val MAX_LENGTH_NOMBRE = 25
private const val MAX_LENGTH_DESC = 30
private const val MAX_LENGTH_CAPACIDAD = 3
private const val MAX_LENGTH_MOBILIARIA = 20

@Composable
fun LimitedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    maxLength: Int,
    modifier: Modifier = Modifier,
    maxLines: Int = 1
) {
    val trimmedValue = if (value.length > maxLength) {
        value.substring(0, maxLength)
    } else {
        value
    }

    TextField(
        value = trimmedValue,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
        label = { Text(label) },
        modifier = modifier,
        maxLines = maxLines
    )
}

@Preview
@Composable
fun AltaAreaPreview() {
    val context = LocalContext.current
    AltaArea(areaViewModel = AreaViewModel(), navController = NavController(context))
}