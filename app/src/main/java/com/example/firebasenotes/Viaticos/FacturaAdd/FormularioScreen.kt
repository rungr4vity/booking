package com.example.firebasenotes.Viaticos.FacturaAdd

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebasenotes.bill.toXml
import com.example.firebasenotes.models.GastoDTO
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.UUID

@Composable
fun DDViaticos(navController: NavController,viajeId: String) {


    println()
    var fecha by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var emisor by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var expanded by remember { mutableStateOf(false) }
    val menuItems = listOf("Deducible", "No deducible")
    var selectedItem by remember { mutableStateOf("Tipo de Gasto") }
    var context = LocalContext.current
    var xmlUri by remember { mutableStateOf<Uri?>(null) }
    var pdfUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        pdfUri = uri
        //Toast.makeText(context, "PDF added to memory", Toast.LENGTH_SHORT).show()
    }


    var selectedFileName by remember { mutableStateOf("No file selected") }
    var fileContent by remember { mutableStateOf("") }
    var gastoDTO_mutable by remember { mutableStateOf(GastoDTO()) }



    var mystring by remember { mutableStateOf("") }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            uri?.let {
                xmlUri = uri
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    fileContent = reader.readText()
                    selectedFileName = uri.path ?: "Unknown file"
                    mystring = fileContent.toString()

                }
            }
        }
    )
    var showSnackbar by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Tipo de Gasto") },
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                menuItems.forEach { menuItem ->
                    DropdownMenuItem(onClick = {
                        selectedItem = menuItem
                        expanded = false
                    }) {
                        Text(text = menuItem)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (selectedItem == "No deducible") {
            Column(Modifier.fillMaxWidth()) {
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

                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = monto,
                    onValueChange = { monto = it },
                    label = { Text("Monto") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = emisor,
                    onValueChange = { emisor = it },
                    label = { Text("Emisor") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                btn_EnviarGasto(context,monto,viajeId,imageUri) {
                    showSnackbar = true
                }
            }
        } else if (selectedItem == "Deducible") {




















            Column(Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.AddCircle, contentDescription = " ",

                        modifier = Modifier.clickable {

                        filePickerLauncher.launch(arrayOf("text/xml"))
                        })


                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Adjunta XML")
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.AddCircle, contentDescription = " ",

                        modifier = Modifier.clickable {
                            pdfLauncher.launch("application/pdf")

                    })

                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Adjunta Pdf")
                }




























                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = "Fecha",
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = "Monto",
                    onValueChange = { },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = "Descripción",
                    onValueChange = {  },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = "Emisor",
                    onValueChange = {  },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))






                if (selectedItem == "Deducible") {
                    btn_EnviarGastoDeducible(context,monto,viajeId,pdfUri, xmlUri,mystring) {
                        showSnackbar = true
                    }
                }else{
                    btn_EnviarGasto(context,monto,viajeId,imageUri) {
                        showSnackbar = true
                    }
                }







            }
        }

        if (showSnackbar) {
            Snackbar(
                action = {
                    Button(onClick = { showSnackbar = false }) {
                        Text("Cerrar")
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Imagen subida exitosamente")
            }
        }
    }
}

@Composable
fun btn_EnviarGasto(context:Context,monto:String,viajeId:String, imageUri: Uri?, onUploadSuccess: () -> Unit) {
    Button(
        onClick = {
            imageUri?.let {
                uploadImageToFirebase(context,monto,viajeId, imageUri = it, onUploadSuccess)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
    ) {
        Text(text = "Enviar Gastos", color = Color.White)
    }
}
@Composable
fun btn_EnviarGastoDeducible(context:Context,monto:String,viajeId:String,pdfUri: Uri?, xmlUri: Uri?,mystring:String, onUploadSuccess: () -> Unit) {
    Button(
        onClick = {
            if (xmlUri == null) {
                Toast.makeText(context, "No se ha seleccionado un XML", Toast.LENGTH_SHORT).show()
                return@Button
            }
            if (pdfUri == null) {
                Toast.makeText(context, "No se ha seleccionado un PDF", Toast.LENGTH_SHORT).show()
                return@Button
            }

            var idPdf = ""
            pdfUri?.let {

                val storageReference = FirebaseStorage.getInstance().reference
                val idUsuario = FirebaseAuth.getInstance().currentUser?.uid
                val pdfRef = storageReference.child("pdfs/${UUID.randomUUID()}.pdf")

                pdfRef.putFile(pdfUri)
                    .addOnSuccessListener {
                        pdfRef.downloadUrl.addOnSuccessListener { uri ->
                            val downloadUrl = uri.toString()
                            idPdf = downloadUrl



                            xmlUri?.let {
                                uploadXMLToFirebase(context,xmlUri,idPdf,viajeId,monto, mystring, onUploadSuccess){
                                    Toast.makeText(context, "Se agrego gasto deducible", Toast.LENGTH_SHORT).show()
                                }
                            }




                        }
                    }
                    .addOnFailureListener {

                    }

            }






        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
    ) {
        Text(text = "Enviar Gastos", color = Color.White)
    }
}

fun AltaGastoDeducible(context:Context,gastoDTO: GastoDTO): Unit {

    var instance = FirebaseFirestore.getInstance()

    instance.collection("Gastos")
        .add(gastoDTO).addOnSuccessListener {
            Toast.makeText(context, "Gasto deducible agregado!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Error al agregar gasto deducible : ${e.message}", Toast.LENGTH_SHORT).show()
        }


}
fun uploadXMLToFirebase(
    context: Context,
    xmlUri: Uri,
    pdf: String,
    viajeId: String,
    monto: String,
    mystring: String,
    onUploadSuccess: () -> Unit,
    function: () -> Unit
): Unit {
    val storageReference = FirebaseStorage.getInstance().reference
    val idUsuario = FirebaseAuth.getInstance().currentUser?.uid
    val xmlRef = storageReference.child("xmls/${UUID.randomUUID()}.xml")

    xmlRef.putFile(xmlUri)
        .addOnSuccessListener {
            xmlRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()


                var gastodto = toXml.toReadXML(
                    uri.path.toString(),
                    mystring,
                    idUsuario ?: "",
                    viajeId,"",
                    "",pdf,
                    downloadUrl,"",
                    "from_Android",
                    "")

                AltaGastoDeducible(
                    context,
                    gastodto
                )
                onUploadSuccess()
            }
        }
        .addOnFailureListener {
            Toast.makeText(context, "Error al cargar XML gasto deducible", Toast.LENGTH_SHORT).show()
        }
}
fun AltaGastoNoDeducible(context: Context, esDeducible: Boolean, fecha:String, total:String, descripcion:String,
                         emisorNombre:String, imagen:String, idViaje:String): Unit {

    var gastoDTo: GastoDTO = GastoDTO()

    gastoDTo.esDeducible = esDeducible
    gastoDTo.fecha = fecha
    gastoDTo.total = total
    gastoDTo.descripcion = descripcion
    gastoDTo.emisorNombre = emisorNombre
    gastoDTo.imagen = imagen
    gastoDTo.idViaje = idViaje


    var instance = FirebaseFirestore.getInstance()

    instance.collection("Gastos")
        .add(gastoDTo).addOnSuccessListener {
            Toast.makeText(context, "Alta Gasto No Deducible", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Error al agregar gasto No deducible : ${e.message}", Toast.LENGTH_SHORT).show()
        }
}
fun uploadImageToFirebase(context: Context,monto:String,viajeId: String, imageUri: Uri, onUploadSuccess: () -> Unit) {
    val storageReference = FirebaseStorage.getInstance().reference
    val imageRef = storageReference.child("images/${UUID.randomUUID()}.jpg")

    imageRef.putFile(imageUri)
        .addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()


                AltaGastoNoDeducible(context,
                    esDeducible = false,
                    fecha = "",
                    total = monto,
                    descripcion = "",
                    emisorNombre = "",
                    imagen = downloadUrl,
                    idViaje = viajeId)


                onUploadSuccess()
            }
        }
        .addOnFailureListener {
            // Manejo de errores si la carga falla
        }
}