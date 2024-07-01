package com.example.firebasenotes.presentation.viewModel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.firebasenotes.models.DataDrawerDTO

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

class EstacionamientosViewModel: ViewModel() {

    private val _estacionamientos = MutableStateFlow<DataDrawerDTO>(DataDrawerDTO())
    val estacionamientos: StateFlow<DataDrawerDTO> = _estacionamientos

    fun updatePhoto(context:Context,imagen: String,updatedImage: Uri,estacionamientoId: String,onSuccess: (String) -> Unit) {

        val storageReference = FirebaseStorage.getInstance().reference
        val desertRef = storageReference.child("images/estacionamientos/$imagen")

        desertRef.delete().addOnSuccessListener {

            val storage = FirebaseStorage.getInstance().reference
            val imageRef = storage.child("images/estacionamientos/${UUID.randomUUID()}.jpg")
            imageRef.putFile(updatedImage)
                .addOnSuccessListener {

                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()
                        var instance = FirebaseFirestore.getInstance()

                        val dto = hashMapOf(
                            "imagen" to downloadUrl
                        )

                        instance.collection("Estacionamientos").document(estacionamientoId)
                            .update(dto as Map<String, Any>).addOnSuccessListener {
                                //Toast.makeText(context, "Imagen actualizada", Toast.LENGTH_SHORT).show()
                                onSuccess("ok")
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Error al agregar gasto No deducible : ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }

                }.addOnFailureListener {
                    Toast.makeText(context, "Error al actualizar la imagen", Toast.LENGTH_SHORT).show()
                }

        }.addOnFailureListener {
            // Uh-oh, an error occurred!
            Toast.makeText(context, "Error al eliminar la imagen", Toast.LENGTH_SHORT).show()
        }

    }

    fun updateInfo(context:Context,estacionamientoId:String,nombre: String,numero: String,piso: String,esEspecial: Boolean,
                   perteneceA: String) {

                        var instance = FirebaseFirestore.getInstance()

                        val dto = hashMapOf(
                            "nombre" to nombre,
                            "numero" to numero.toInt(),
                            "piso" to piso,
                            "esEspecial" to esEspecial,
                            "perteneceA" to perteneceA
                        )

                        instance.collection("Estacionamientos").document(estacionamientoId)
                            .update(dto as Map<String, Any>).addOnSuccessListener {
                                Toast.makeText(context, "Info actualizada", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Error al actualizar la info: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
    }

    fun getData(idEstacionamiento: String, onSuccess: (DataDrawerDTO) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val estacionamientoRef = db.collection("Estacionamientos").document(idEstacionamiento)

        estacionamientoRef.get().addOnSuccessListener { document ->
            val estacionamiento = document.toObject(DataDrawerDTO::class.java)
            _estacionamientos.value = estacionamiento ?: DataDrawerDTO()

            estacionamiento?.let { onSuccess(it) }
        }.addOnFailureListener { exception ->
            // Handle errors here
        }
    }




}