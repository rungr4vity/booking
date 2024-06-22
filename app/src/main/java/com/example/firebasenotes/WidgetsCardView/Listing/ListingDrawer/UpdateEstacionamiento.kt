package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer


import android.content.Context
import android.net.Uri
import android.view.View.OnClickListener
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.example.firebasenotes.estacionamientos.EstacionamientosViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun UpdateEstacionamiento (
    viewModel: EstacionamientosViewModel = EstacionamientosViewModel(),
    navController: NavController,
    context: Context,
    idEstacionamianto: String,
    imagen: String,
    nombre: String,
    numero: String,
//    company: String,

//    piso: String,
//    esEspecial: Boolean

) {


        var nombre by remember { mutableStateOf(nombre) }
        var numero by remember { mutableStateOf(numero) }
        var text3 by remember { mutableStateOf("") }
        var imagen_original = imagen
        var imageUri by remember { mutableStateOf<Uri?>(Uri.parse(imagen)) }

        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            contract = GetContent(),
            onResult = { uri: Uri? ->
                if (uri != null) {
                    imageUri = uri
                } else {
                    imageUri = Uri.parse(imagen)
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


                imageUri?.let {

                    Image(

                        painter = rememberImagePainter(
                            ImageRequest.Builder(context)
                                .data(it)
                                .size(Size.ORIGINAL)
                                .transformations(CircleCropTransformation())
                                .build()
                        ),

                        contentDescription = null,
                        modifier = Modifier
                            .size(128.dp)
                            .padding(bottom = 16.dp),
                        contentScale = ContentScale.Crop
                    )
                    // inside pic
                    //Text(text = "Selected image: $imagen")
                }


            Row(Modifier.fillMaxWidth().padding(5.dp)) {
                Button(onClick = { launcher.launch("image/*") },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors( Color(0xFF800000)),
                    ) {
                    Text(text = "Seleccionar imagen",color = Color.White)
                }

                Button(modifier= Modifier.padding(start = 10.dp),
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
                    viewModel.updatePhoto(context,imagen_jpg,imageUri ?: Uri.EMPTY,idEstacionamianto)
                },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors( Color(0xFF800000)),


                ) {
                    Text(text = "Actualizar",color = Color.White)
                }
            }



            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Nombre")
            BasicTextField(
                value = nombre,
                onValueChange = { nombre = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    if (nombre.isEmpty()) {
                        Text(nombre)
                    }
                    innerTextField()
                }
            )

            Text(text = "Numero")
            BasicTextField(
                value = numero,
                onValueChange = { numero = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    if (numero.isEmpty()) {
                        Text(numero)
                    }
                    innerTextField()
                }
            )

//            BasicTextField(
//                value = text3,
//                onValueChange = { text3 = it },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp),
//                decorationBox = { innerTextField ->
//                    if (text3.isEmpty()) {
//                        Text("Enter Text 3", color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f))
//                    }
//                    innerTextField()
//                }
//            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {


               //println(imagen_jpg)
                viewModel.updateInfo(context,idEstacionamianto,nombre,numero)

            },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors( Color(0xFF800000)),

            ) {
                Text(text = "Actualizar",color = Color.White)
            }
        }
    }



