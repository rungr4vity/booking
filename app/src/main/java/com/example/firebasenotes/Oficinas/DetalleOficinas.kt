package com.example.firebasenotes.Oficinas

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.firebasenotes.ViewMenu.Mipefil.DDViewModel
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.AreaViewModelDOS

import com.example.firebasenotes.models.oficinasDTO

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetalleOficinas(
    navController: NavController,
    context: Context,
    capacidad: String,
    descripcion: String,
    id: String,
    mobilaria: String,
    nombre: String,
    idArea: String,
    viewModel: OficinasViewModel = OficinasViewModel(),
    deleteViewModel: AreaViewModelDOS = AreaViewModelDOS(),
    ddViewModel: DDViewModel = viewModel()
){

    // first comment
    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

        ,

        ) {


//
        val today = LocalDate.now()
        val dayOfYear = today.dayOfYear
        val userData = ddViewModel.state.value
        val data = System.currentTimeMillis()
        var suma = 0

//        data?.let {
//            val localDate = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
//            suma = (localDate.dayOfMonth + localDate.month.value + localDate.year)
////            viewModel.ReservacionOficinasDia(localDate.year, localDate.dayOfYear,idArea)
//
//        }
        viewModel.ReservacionOficinasDia(today.year, today.dayOfYear,idArea)
        val oficinasHorarios: List<oficinasDTO> by viewModel.horariosOficinas.observeAsState(listOf())

        //val opsHorarios: List<horariosModel> by viewModel.horarios.observeAsState(listOf())
        //var menHorarios by remember { mutableStateOf(opsHorarios) }


        Column(modifier = Modifier
            .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = com.example.firebasenotes.R.drawable.ofi2), // Reemplaza 'your_image' con el nombre de tu imagen
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterHorizontally)


            )
            Spacer(modifier = Modifier.size(30.dp))
                Text(
                    text = "Detalle", fontWeight = FontWeight.Bold, fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )

                Text(
                    text = "Fecha ${LocalDate.now()}", fontWeight = FontWeight.Thin, fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Text(text = " $nombre", modifier = Modifier.padding(10.dp))
                Text(text = "Capacidad: ${capacidad}", modifier = Modifier.padding(10.dp))
                Text(text = "$descripcion", modifier = Modifier.padding(10.dp))


                Text(
                    text = "Reservado: ",
                    fontWeight = FontWeight.Medium, fontSize = 15.sp, color = Color.Blue,
                    modifier = Modifier.padding(10.dp)
                )
                oficinasHorarios.forEach {
                    Text(text = "Horario: ${String.format("%.2f",(it.horaInicial.toFloat() / 60))}  " +
                            ": ${String.format("%.2f",(it.horafinal.toFloat() / 60))}",
                        fontWeight = FontWeight.Medium, fontSize = 15.sp, color = Color.Blue,
                        modifier = Modifier.padding(10.dp))

                }
            Spacer(modifier = Modifier.size(60.dp))

            if(userData.typeId == 0){
           Button(onClick = {  deleteViewModel.deleteArea(idArea)}
               ,
               modifier = Modifier
                   .padding(horizontal = 19.dp)
                   .align(Alignment.End),
               colors = ButtonDefaults.buttonColors(Color(0xFF800000))
            ) {
                Text(text = "Eliminar",color = Color.White)
            }
            }

                Button(
                    onClick = {
                        navController.navigate("ReservaOficinas_extension/${capacidad}/${descripcion}/${id}/ ${mobilaria}/ ${nombre}/ ${idArea}",
                            navOptions { // Use the Kotlin DSL for building NavOptions
                                anim {
                                    enter = R.animator.fade_in
                                    exit = R.animator.fade_out
                                }
                            })
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF800000))
                ) {
                    Text(text = "Reservar ${nombre}", color = Color.White)
                } // end button



        }

    }




}



