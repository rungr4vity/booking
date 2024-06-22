package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer


import android.content.Context
import android.net.Uri

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateEstacionamiento (
    viewModel: EstacionamientosViewModel = EstacionamientosViewModel(),
    navController: NavController,
    context: Context,
    idEstacionamianto: String,
    imagen: String,
    nombre: String,
    numero: String,
    company: String,
    drawerViewModel: DrawerViewModel = viewModel(),
//    piso: String,
//    esEspecial: Boolean

) {

        var expanded_user  by remember { mutableStateOf(false) }
        val checkedState: MutableState<Boolean> = remember { mutableStateOf(true) }
        val perteneceA = drawerViewModel.usuarios_short.value
        var selected_perteneceText  by remember { mutableStateOf("Asignado a") }
        var selected_pertenece_id  by remember { mutableStateOf("") }


        var selectedOptionText  by remember { mutableStateOf(company) }
        var expanded  by remember { mutableStateOf(false) }
        val options = listOf("Isita", "Verifigas")

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

Row() {
    Icon(
        imageVector = Icons.Default.AddCircle,
        contentDescription = "Crop Icon",
        modifier = Modifier
            .size(24.dp)
            .clickable {
                // Handle the click event here
                launcher.launch("image/*")
            },
        tint = Color.Black // Adjust the color as needed
    )
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
                .size(100.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Crop
        )
        // inside pic
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
            colors = ButtonDefaults.buttonColors( Color(0xFF4CBB17)),
        ) {
            Text(text = "Cambiar",color = Color.White)
        }

    }



}

            Row(Modifier.fillMaxWidth().padding(5.dp)) {

//                Button(onClick = { launcher.launch("image/*") },
//                    shape = RoundedCornerShape(5.dp),
//                    colors = ButtonDefaults.buttonColors( Color(0xFF800000)),
//                    ) {
//                    Text(text = "Seleccionar imagen",color = Color.White)
//                }

            }


            Spacer(modifier = Modifier.padding(5.dp))
            TextField(
                value =  nombre,
                onValueChange = {  nombre = it },
                label = { androidx.compose.material.Text("Nombre") }
                ,modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
            )

            Spacer(modifier = Modifier.padding(5.dp))
            TextField(
                value =  numero,
                onValueChange = {  numero = it },
                label = { androidx.compose.material.Text("Num.") }
                ,modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
            )


            Spacer(modifier = Modifier.padding(5.dp))
            androidx.compose.material3.ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = selectedOptionText,
                    onValueChange = { },
                    label = { Text(text="Compañia") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option) },
                            onClick = {
                                selectedOptionText = option
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(5.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material.Text(
                    text = "Estacionamiento especial",
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                )
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it }
                )
                //Text(text = if (checkedState.value) "Especial" else "No Especial")
            }
            Spacer(modifier = Modifier.padding(5.dp))
            if(checkedState.value) {
                androidx.compose.material3.ExposedDropdownMenuBox(
                    expanded = expanded_user,
                    onExpandedChange = { expanded_user = !expanded_user }) {
                    OutlinedTextField(
                        value = selected_perteneceText,
                        onValueChange = { },
                        label = { androidx.compose.material3.Text("Asignado a") },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded_user) }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded_user,
                        onDismissRequest = { expanded_user = false }) {
                        perteneceA.forEach { option ->
                            DropdownMenuItem(
                                text = { androidx.compose.material3.Text(option.nombres) },
                                onClick = {
                                    selected_pertenece_id = option.documentId
                                    selected_perteneceText = option.nombres
                                    expanded_user = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                } // end drop down
            }














            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
               //println(imagen_jpg)
                viewModel.updateInfo(context,idEstacionamianto,nombre,numero)

            },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                colors = ButtonDefaults.buttonColors( Color(0xFF800000)),

            ) {
                Text(text = "Actualizar",color = Color.White)
            }
        }
    }



