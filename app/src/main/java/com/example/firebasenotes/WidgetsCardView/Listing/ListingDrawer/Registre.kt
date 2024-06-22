package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer


import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.MutableState


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Registre(){
    Scaffold() {
        ComponentRegistrar()
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ComponentRegistrar(drawerViewModel: DrawerViewModel = viewModel()) {
    var  nombre_cajon by remember { mutableStateOf("") }
    var  num_cajon by remember { mutableStateOf("") }
    var piso_edificio by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var esEspecial by remember { mutableStateOf("") }
    var img by remember { mutableStateOf("") }

    var selectedOptionText  by remember { mutableStateOf("Empresa") }
    var expanded  by remember { mutableStateOf(false) }
    val options = listOf("Isita", "Verifigas")

    var context = LocalContext.current





    var expanded_user  by remember { mutableStateOf(false) }
    val checkedState: MutableState<Boolean> = remember { mutableStateOf(true) }
    val perteneceA = drawerViewModel.usuarios_short.value
    var selected_perteneceText  by remember { mutableStateOf("Asignado a") }
    var selected_pertenece_id  by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        TextField(
            value =  nombre_cajon,
            onValueChange = {  nombre_cajon = it },
            label = { Text("Nombre") }
            ,modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        TextField(
            value =  num_cajon,
            onValueChange = {  num_cajon = it },
            label = { Text("Numero") }
            ,modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        TextField(
            value =  piso_edificio,
            onValueChange = {  piso_edificio = it },
            label = { Text("Piso") }
            ,modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
        )
        var imageUri by remember { mutableStateOf<Uri?>(null) }
        var selectedImage by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
        val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {

                imageUri = uri
                //val inputStream = context.contentResolver.openInputStream(uri)
                //selectedImage = BitmapFactory.decodeStream(inputStream)

            }
        }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            imageUri = uri
        }

        Spacer(modifier = Modifier.padding(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = " ",
                modifier = Modifier.clickable {
                    launcher.launch("image/*")
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Adjunta Imagen")

            if (imageUri != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Imagen cargada",
                    tint = Color.Green
                )

            }
        }

        Spacer(modifier = Modifier.padding(5.dp))
        androidx.compose.material3.ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = selectedOptionText,
                onValueChange = { },
                label = { androidx.compose.material3.Text("Compañia") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { androidx.compose.material3.Text(option) },
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
            Text(text = "Estacionamiento especial",modifier = Modifier.padding(start = 5.dp,end=5.dp))
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
        }// end if(checkedState.value)
        Spacer(modifier = Modifier.padding(10.dp))

        val pertenece:() -> String  = {
            if(checkedState.value){
                selected_pertenece_id
            } else {
                ""
            }
        }


        Button(
            shape = RoundedCornerShape(5.dp),
            onClick = {

                if (imageUri == null) {
                    Toast.makeText(context, "No se ha seleccionado una imagen", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                drawerViewModel.insertarDatos(
                context,
                num_cajon.toInt(),
                nombre_cajon ,
                piso_edificio,
                selectedOptionText,
                    checkedState.value,
                    selected_pertenece_id,imageUri ?: Uri.EMPTY)


                      num_cajon = ""
                      nombre_cajon = ""
                      piso_edificio = ""
                      selectedOptionText = "Empresa"
                      selected_perteneceText = "Asignado a"
                      imageUri = Uri.EMPTY
                      },

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors( Color(0xFF800000))

        ) {
            Text("Agregar estacionamiento", color = Color.White)
        }
    }
}

