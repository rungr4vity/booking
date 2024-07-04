package com.example.firebasenotes.presentation.view

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.firebasenotes.oficinas.viewmodels.AreaViewModel

import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun EditarOficinas(
    idArea: String,
    capacidad: String,
    descripcion: String,
    id: String,
    mobilaria: String,
    nombre: String,
    imagen: String,
    areaViewModel: AreaViewModel = viewModel()
) {
    var nombreText by remember { mutableStateOf(nombre) }
    var mobText by remember { mutableStateOf(mobilaria) }
    var capacidadText by remember { mutableStateOf(capacidad) }
    val Context = LocalContext.current
    val ejemplo = imagen.toString().trim()
    var imageUri by remember { mutableStateOf<Uri?>(Uri.parse(ejemplo)) }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
                println()
            } else {
                imageUri = Uri.parse(ejemplo)
            }
        }
    )


    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        imageUri?.let {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUri)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp)


            )
            // inside pic
            //Text(text = "Selected image: $imagen")
        }
        Spacer(modifier = Modifier.height(16.dp))

        androidx.compose.material.Button(
            onClick = {
                Toast.makeText(Context, "Imagen seleccionada", Toast.LENGTH_SHORT).show()

                launcher.launch("image/*")

            },
            shape = RoundedCornerShape(5.dp),
            colors = androidx.compose.material.ButtonDefaults.buttonColors(Color(0xFF800000)),

            ) {
            androidx.compose.material.Text(text = "Seleccionar imagen", color = Color.White)
        }

//            androidx.compose.material.Button(modifier= Modifier.padding(start = 10.dp),
//                onClick = {
//                    var imagen_jpg = ""
//                    val encodedUrl = URLDecoder.decode(imagen, StandardCharsets.UTF_8.toString())
//                    var decode = encodedUrl.split("/")
//
//                    decode.forEach {
//                        if(it.contains(".jpg")) {
//                            imagen_jpg = it
//                        }
//                    }
//
//                    var ultima = imagen_jpg.split("?")
//
//                    if(ultima.size > 1) {
//                        imagen_jpg = ultima[0]
//                    }
////                    viewModel.updatePhoto(context,imagen_jpg,imageUri ?: Uri.EMPTY,idEstacionamianto)
//                    areaViewModel.updatePhoto(context,imagen_jpg,imageUri ?: Uri.EMPTY,idArea)
//                },
//                shape = RoundedCornerShape(5.dp),
//                colors = androidx.compose.material.ButtonDefaults.buttonColors( Color(0xFF800000)),
//
//
//                ) {
//                androidx.compose.material.Text(text = "Actualizar",color = Color.White)
//            }

    Spacer(modifier = Modifier.height(16.dp))
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
            value = mobText,
            onValueChange = { mobText = it },
            label = { Text("Mobiliaria") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para editar la capacidad
        OutlinedTextField(
            value = capacidadText,
            onValueChange = { capacidadText = it },
            label = { Text("Capacidad de personas") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Botón de guardar cambios
        Button(
            onClick = {
                areaViewModel.actualizarOficina(
                    idArea = idArea,
                    nombre = nombreText,
                    descripcion = descripcion,
                    capacidad = capacidadText,
                    mobilaria = mobText,
                    id = id
                )

                Toast.makeText(Context, "Cambios guardados", Toast.LENGTH_SHORT).show()

                // Check if a new image has been selected
                if (imageUri != Uri.parse(imagen.trim())) {
                    var imagen_jpg = ""
                    val encodedUrl = URLDecoder.decode(imagen, StandardCharsets.UTF_8.toString())
                    var decode = encodedUrl.split("/")

                    decode.forEach {
                        if (it.contains(".jpg")) {
                            imagen_jpg = it
                        }
                    }

                    var ultima = imagen_jpg.split("?")

                    if (ultima.size > 1) {
                        imagen_jpg = ultima[0]
                    }

                    areaViewModel.updatePhoto(context, imagen_jpg, imageUri ?: Uri.EMPTY, idArea)
                }
            },

            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF800000))
        ) {
            Text("Guardar cambios", color = Color.White)
        }

    }
}
