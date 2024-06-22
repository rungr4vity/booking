package com.example.firebasenotes.Oficinas

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.AreaViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun EditarOficinas(
    idArea: String,
    capacidad : String,
    descripcion: String,
    id: String,
    mobilaria: String,
    nombre : String,
    imagen : String,
    areaViewModel: AreaViewModel = viewModel()
) {
    var nombreText by remember { mutableStateOf(nombre) }
    var descripcionText by remember { mutableStateOf(descripcion) }
    var capacidadText by remember { mutableStateOf(capacidad) }
    var imageUri by remember { mutableStateOf<Uri?>(Uri.parse(imagen)) }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
            } else {
                imageUri = Uri.parse(imagen)
            }
        }
    )

    Column(modifier = Modifier.padding(16.dp)) {
        imageUri?.let {

            AsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp)
            )
            // inside pic
            //Text(text = "Selected image: $imagen")
        }


        Row(Modifier.fillMaxWidth().padding(5.dp)) {
            androidx.compose.material.Button(onClick = { launcher.launch("image/*") },
                shape = RoundedCornerShape(5.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors( Color(0xFF800000)),
            ) {
                androidx.compose.material.Text(text = "Seleccionar imagen",color = Color.White)
            }

            androidx.compose.material.Button(modifier= Modifier.padding(start = 10.dp),
                onClick = {
                    var imagen_jpg = ""
                    val encodedUrl = URLDecoder.decode(imagen, StandardCharsets.UTF_8.toString())
                    var decode = encodedUrl.split("/")

                    decode.forEach {
                        if(it.contains(".jpg")) {
                            imagen_jpg = it
                        }
                    }

                    var ultima = imagen_jpg.split("?")

                    if(ultima.size > 1) {
                        imagen_jpg = ultima[0]
                    }
//                    viewModel.updatePhoto(context,imagen_jpg,imageUri ?: Uri.EMPTY,idEstacionamianto)
                    areaViewModel.updatePhoto(context,imagen_jpg,imageUri ?: Uri.EMPTY,idArea)
                },
                shape = RoundedCornerShape(5.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors( Color(0xFF800000)),


                ) {
                androidx.compose.material.Text(text = "Actualizar",color = Color.White)
            }
        }

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
