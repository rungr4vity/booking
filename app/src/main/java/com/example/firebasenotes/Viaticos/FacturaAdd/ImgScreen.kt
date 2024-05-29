import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebasenotes.models.GastoDTO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.util.Date

@Preview
@Composable
fun UploadImageScreen() {


    var bytearray by remember { mutableStateOf<ByteArray?>(null) }


    var selectedImage by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(uri)
            selectedImage = android.graphics.BitmapFactory.decodeStream(inputStream)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = {
                if (selectedImage != null) {
                    uploadImageToFirebaseStorage(selectedImage!!, context)
                } else {
                    // Si no se ha seleccionado ninguna imagen, muestra un mensaje
                    Toast.makeText(context, "Por favor selecciona una imagen", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
            , colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text("Subir Imagen")
        }
        // Botón para seleccionar una imagen
        Button(
            onClick = { getContent.launch("image/*") }, colors = ButtonDefaults.buttonColors(Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Seleccionar Imagen")
        }

        // Vista previa de la imagen seleccionada
        selectedImage?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
        }

        // Mostrar la URL de la imagen subida
        imageUrl?.let {
            Text(
                text = "URL de la imagen subida:\n $it",
                fontSize = 16.sp
            )
        }
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
            Log.d("Firebase_gasto", "DocumentSnapshot added with ID: ${it.id}")
        }
        .addOnFailureListener { e ->
            Log.w("Firebase", "Error adding document", e)
        }
}




private fun uploadImageToFirebaseStorage(image: android.graphics.Bitmap, context: android.content.Context) {
    val storageRef = Firebase.storage.reference
    val imagesRef = storageRef.child("images/${System.currentTimeMillis()}.jpg")


    // =================================================================================================
    val baos = ByteArrayOutputStream()
    image.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, baos)
    val data = baos.toByteArray()
    // =================================================================================================

    val uploadTask = imagesRef.putBytes(data)
    uploadTask.addOnSuccessListener { taskSnapshot ->
        taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->

            val imageUrl = uri.toString()

            AltaGastoNoDeducible(
                context,
                false,
                Date().toString(),
                "",
                "",
                "",
                "",
                imageUrl)



            Log.d("Firebase", "URL de la imagen subida: $imageUrl")
            Toast.makeText(context, "Imagen subida con éxito", Toast.LENGTH_SHORT).show()
            // Actualiza el estado de la URL de la imagen subida
            imageUrl.let {
                // Hacer algo con la URL, como mostrarla en la interfaz de usuario
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(context, "Error al obtener la URL de la imagen", Toast.LENGTH_SHORT)
                .show()
            Log.e("Firebase", "Error al obtener la URL de la imagen", exception)
        }
    }}