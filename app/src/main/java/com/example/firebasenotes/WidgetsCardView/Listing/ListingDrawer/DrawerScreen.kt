package com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.firebasenotes.R
import com.example.firebasenotes.ViewMenu.Mipefil.DDViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.example.firebasenotes.UsersAdmin.UsersViewModel
import com.example.firebasenotes.models.horariosDTO
import com.example.firebasenotes.models.horariosModel
import com.example.firebasenotes.viewModels.LoginViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun DrawerScreen(drawerViewModel: DrawerViewModel = viewModel(), navController: NavController,
                 ddViewModel: DDViewModel = viewModel()

) {
    //val context = LocalContext.current
    //var sharedPreferences = context.getSharedPreferences("shared_usuario", Context.MODE_PRIVATE)
    //var myempresa = "Verifigas"
    //sharedPreferences.getString("empresa", "Verifigas")


    val cajones = drawerViewModel.stateDrawer.value
    val userData = ddViewModel.state.value
    //val empresa = userData.empresa
    //val user = usersViewModel.stateUsers.value

    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val initialDate = dateFormat.format(calendar.time)
    var text3 by remember { mutableStateOf(initialDate) }
    var showDatePicker by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf(initialDate) }
    var dayOfYear_ by remember { mutableStateOf(calendar.get(Calendar.DAY_OF_YEAR)) }
    var year by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }

    var expansion_Horarios by remember { mutableStateOf(false) }
    var menHorarios by remember { mutableStateOf("Selecciona horario") }


    //val opsHorarios: List<horariosModel> by loginVM.horarios.observeAsState(listOf())
    val opsHorarios: List<horariosDTO> by drawerViewModel.horarios_dto.observeAsState(listOf())
    Log.d("opsHorarios",opsHorarios.toString())


    val context = LocalContext.current
    if (showDatePicker) {
        Log.d("showDatePicker",showDatePicker.toString())

        LaunchedEffect(Unit) {
            DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDay ->
                    calendar.set(selectedYear, selectedMonth, selectedDay)
                    date = dateFormat.format(calendar.time)
                    text3 = date  // Update text3 with the selected date
                    dayOfYear_ = calendar.get(Calendar.DAY_OF_YEAR)
                    year = selectedYear
                    showDatePicker = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)

            ).show()
        }

        showDatePicker = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {



        Row(
            //verticalAlignment = Alignment.CenterVertically,
            //horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            IconButton(onClick = {


                //var sharedPreferences = context.getSharedPreferences("shared_usuario", Context.MODE_PRIVATE)
                //val myempresa = sharedPreferences.getString("empresa", null)


                menHorarios = "Selecciona horario"
                drawerViewModel.getAll(dayOfYear_,year)
                drawerViewModel.reload()
                showDatePicker = true


            }) {
                Icon(Icons.Filled.DateRange, contentDescription = "Pick a Date")
            }
            androidx.compose.material.OutlinedTextField(
                readOnly = true,
                value = text3,
                onValueChange = {
                    text3 = it

                    var dia = dayOfYear_
                    var anio = year



                    menHorarios = "Selecciona horario"
                    drawerViewModel.getAll(dayOfYear_,year)
                    drawerViewModel.reload()




                                },
                label = { androidx.compose.material.Text("Fecha") },
                modifier = Modifier
                    //.weight(1f)
                    .padding(8.dp)

            )
        }
        Row() {
            val opsHorarios: MutableMap<Int,String> = mutableMapOf(
                0 to "7:00 am - 12 pm",
                1 to "12:00 am - 5  pm",
                2 to "5:00 pm - 9:00 pm",
                3 to "Todo el día")

            IconButton(onClick = {  }) {
                Icon(Icons.Filled.Timer, contentDescription = "Pick a Date")
            }
            ExposedDropdownMenuBox(expanded = expansion_Horarios , onExpandedChange = {expansion_Horarios  = !expansion_Horarios } ) {
                OutlinedTextField(
                    value = menHorarios,
                    onValueChange = {



                    },
                    label = { androidx.compose.material.Text("Horarios") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .padding(top = 1.dp, start = 5.dp, end = 8.dp, bottom = 12.dp),
                    //trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expansion_Horarios) }
                )


                ExposedDropdownMenu(
                    expanded = expansion_Horarios,
                    onDismissRequest = { expansion_Horarios = false }) {
                    opsHorarios.forEach { option ->
                        DropdownMenuItem(text = { Text(option.value) }, onClick = {

                            menHorarios = option.value
                            expansion_Horarios = false
                            drawerViewModel.getAll(dayOfYear_,year)
                            drawerViewModel.reload()
                        },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }

        androidx.compose.material3.Button(
            shape = RoundedCornerShape(5.dp), onClick = {


                var dia = dayOfYear_
                var anio = year

                drawerViewModel.getAll(dayOfYear_,year)
                drawerViewModel.reload()


                // cajones //all  (idEstacionaminto)
                // opsHorarios //reservados (idEstacionamiento,valor,texto)
                var todos = cajones
                var reservados = opsHorarios


                println()
                //cajones.filter { it.id in opsHorarios.map { it.idEstacionamiento } }
                //var final = cajones.filterNot { it.id in opsHorarios }


                //Log.d("myempresa",myempresa.toString())


                cajones.forEach { cajon ->
                    if (menHorarios != "Selecciona horario") {

                        if (opsHorarios.isNotEmpty()) {
                            if (cajon.id in opsHorarios.map { it.idEstacionamiento }) {
                                var valor = opsHorarios.find { it.nombre == menHorarios }
                                if (valor != null) {
                                    //Toast.makeText(context, "El cajon ${cajon.numero} esta reservado", Toast.LENGTH_SHORT).show()
                                } else {
                                    //Toast.makeText(context, "El cajon ${cajon.numero} no esta reservado", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }



            },
            colors = ButtonDefaults.buttonColors(Color(0xFF800000)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            androidx.compose.material.Text(text = "Buscar", color = Color.White)
        }


        CompositionLocalProvider(LocalContentColor provides Color.Black) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    try {
                        items(cajones) { cajon ->

                            if (menHorarios != "Selecciona horario") {


                            // logica
                            if (opsHorarios.isNotEmpty()) {




                                if (cajon.id in opsHorarios.map { it.idEstacionamiento }) {
                                    var valor = opsHorarios.find { it.nombre == menHorarios }
                                    var todoDia = opsHorarios.find { it.nombre == "Todo el día" }



                                    if (valor != null || todoDia != null) {
                                        //Toast.makeText(context, "El cajon ${cajon.numero} esta reservado", Toast.LENGTH_SHORT).show()
                                        //remove
                                    } else {

                                        if(menHorarios == "Todo el día" && opsHorarios.isNotEmpty()){

                                        } else {
                                            ComponentDrawer(
                                                cajon = cajon,
                                                navController = navController
                                            )
                                        }




                                        //Toast.makeText(context, "El cajon ${cajon.numero} no esta reservado", Toast.LENGTH_SHORT).show()
                                    }

                                }
                            } else {
                                ComponentDrawer(cajon = cajon, navController = navController)
                            }
                        }

                        }
                    } catch (e: Exception) {
                        Log.e("ErrorLazy", e.toString())
                    }
                }
                if (userData.typeId == 0) {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate("Alta de Cajon")
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp),
                        containerColor =  Color(0xFF800000)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                    }
                }

            } // end box
        }


    }

    }








    @Composable
fun ComponentDrawer(
    cajon: DataDrawer,
    navController: NavController
) {
    val isDarkMode = isSystemInDarkTheme()

    CompositionLocalProvider(LocalContentColor provides if (isDarkMode) Color.White else Color.Black) {
        Card(
            modifier = Modifier
                .clickable {

                    navController.navigate("DetalleCajon/${cajon.nombre}/${cajon.empresa}/${cajon.numero.toString()}/${cajon.piso}/${cajon.esEspecial}/${cajon.id}",
                        navOptions { // Use the Kotlin DSL for building NavOptions
                            anim {
                                enter = android.R.animator.fade_in
                                exit = android.R.animator.fade_out
                            }
                        }
                    )
                }
                .padding(horizontal = 6.dp, vertical = 6.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 10.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.est), // Reemplaza 'your_image' con el nombre de tu imagen
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(100.dp)
//                        .padding(horizontal = 2.dp)

                )
                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(2f)) {
                    Text(
                        text = "Nombre: ${cajon.nombre}",
                        style = TextStyle(fontSize = 12.sp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Empresa: ${cajon.empresa}",
                        style = TextStyle(fontSize = 12.sp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Cajon: ${cajon.numero.toString()}",
                        style = TextStyle(fontSize = 12.sp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Piso: ${cajon.piso}",
                        style = TextStyle(fontSize = 12.sp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Detalle",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 12.dp),
                    color = if (isDarkMode) Color.White else Color.Blue // Cambiar el color del texto a blanco en modo oscuro
                )
            }
        }
    }

}



