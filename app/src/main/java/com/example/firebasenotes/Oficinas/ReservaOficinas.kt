package com.example.firebasenotes.Oficinas

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.ComponentAreas
import com.example.firebasenotes.models.TimeRange
import com.example.firebasenotes.models.horariosModel
import com.example.firebasenotes.models.oficinasDTO
import com.example.firebasenotes.ui.theme.FirebasenotesTheme
import com.example.firebasenotes.utils.ValidacionesHora
import com.example.firebasenotes.utils.ValidacionesHora.minutesToHour
import com.example.firebasenotes.utils.ValidacionesHora.validateAllBookings
import com.example.firebasenotes.viewModels.LoginViewModel
import com.example.firebasenotes.viewModels.NotesViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.time.delay
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId



import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import android.app.DatePickerDialog

import androidx.compose.foundation.layout.*

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import java.util.*
import java.text.SimpleDateFormat

import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Timer


class ReservaOficinas : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val oficinasVM : OficinasViewModel by viewModels()


        val mContext: Context
        mContext = getApplicationContext();

        setContent {
            FirebasenotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReservaOficinas_extension(
                        navController = rememberNavController() ,
                        context = mContext,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        )


                }
            }
        }
    }

}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaOficinas_extension(
    navController: NavController,
    context: Context,
    capacidad: String,
    descripcion: String,
    id: String,
    mobilaria: String,
    nombre: String,
    idArea: String,
    //viewModel: OficinasViewModel = OficinasViewModel()
) {
    //val stateOficinas = viewModel.stateOficina.value










    val viewModel = OficinasViewModel()
    val oficinasHorarios: List<oficinasDTO> by viewModel.horariosOficinas.observeAsState(listOf())


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {


        var state = rememberDatePickerState()
        var data = state.selectedDateMillis ?: System.currentTimeMillis()

        val today = LocalDate.now()
        val dayOfYear = today.dayOfYear
        var localDate = LocalDate.now()

        data?.let {
            localDate = Instant.ofEpochMilli(it).atZone(ZoneId.of("GMT")).toLocalDate()
            //Text(text = "Fecha: ${localDate.dayOfMonth}/${localDate.month}/${localDate.year}")
        }



        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val initialDate = dateFormat.format(calendar.time)
        var text3 by remember { mutableStateOf(initialDate) }
        var showDatePicker by remember { mutableStateOf(false) }
        var date by remember { mutableStateOf(initialDate) }
        var dayOfYear_ by remember { mutableStateOf(calendar.get(Calendar.DAY_OF_YEAR)) }
        var year by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
        // Add a Row containing the IconButton and Text Field 3
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(Icons.Filled.CalendarToday, contentDescription = "Pick a Date")
            }
            OutlinedTextField(
                readOnly = true,
                value = text3,
                onValueChange = { text3 = it },
                label = { Text("Fecha") },
                modifier = Modifier.weight(1f).padding(10.dp)

            )
        }


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






        viewModel.ReservacionOficinasDia(year,dayOfYear_,idArea)



        //viewModel.ReservacionOficinasDia(localDate.year,localDate.dayOfYear,idArea)
        //Log.d("data_listadoOficinas",oficinasHorarios.toString())

        val emailfrom = Firebase.auth.currentUser?.email ?: "No user"

        var textoInicio by rememberSaveable { mutableStateOf("") }
        var textoFin by rememberSaveable { mutableStateOf("") }

        var showDialog by remember { mutableStateOf(false) }

//        Text(
//            text = "Confirmar reservación: ", fontWeight = FontWeight.Bold, fontSize = 20.sp,
//            modifier = Modifier.padding(10.dp)
//        )

        Text(text = "Usuario:  $emailfrom", modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth())
        Text(text = "$nombre", modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth())
        Text(text = "Capacidad: $capacidad", modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth())
        Text(text = "$descripcion", modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth())
//        Text(text = "idArea $idArea", modifier = Modifier
//            .padding(10.dp)
//            .fillMaxWidth())


//        Button(shape = RoundedCornerShape(5.dp),
//            colors = ButtonDefaults.buttonColors( Color(0xFF800000)),
//            onClick = {
//
//            textoInicio = ""
//            textoFin = ""
//            showDialog = true
//                                                          },modifier = Modifier
//            .fillMaxWidth()
//            .padding(5.dp)) {
//            Text(text = "Calendario",color = Color.White)
//        }

        val timePicker = TimePickerDialog(
            context,
            {

                    timePicker, hourSelect, minuteSelect ->
                textoInicio = "$hourSelect:$minuteSelect"
            }, 0, 0, true
        )

        val timePickerFin = TimePickerDialog(
            context,
            {
                    timePicker, hourSelect, minuteSelect ->
                textoFin = "$hourSelect:$minuteSelect"
            }, 0, 0, true
        )

//        Button(shape = RoundedCornerShape(5.dp),
//            colors = ButtonDefaults.buttonColors( Color(0xFF800000)),
//            onClick = {
//                viewModel.ReservacionOficinasDia(localDate.year,localDate.dayOfYear,idArea)
//                timePicker.show()
//                      },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(5.dp)) {
//            Text(text = "Inicio: $textoInicio",color = Color.White, modifier = Modifier
//                .padding(10.dp)
//                .fillMaxWidth())
//        }

//        Button(shape = RoundedCornerShape(5.dp),
//            colors = ButtonDefaults.buttonColors( Color(0xFF800000)),
//            onClick = {
//                timePickerFin.show()
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(5.dp)) {
//
//            Text(text = "Fin: $textoFin",color = Color.White, modifier = Modifier
//                .padding(10.dp)
//                .fillMaxWidth())
//        }




        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(

            value = textoInicio,
            onValueChange = {  },
            label = { Text("Inicio") },
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp,end = 10.dp),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {

                    //viewModel.ReservacionOficinasDia(localDate.year,localDate.dayOfYear,idArea)
                    viewModel.ReservacionOficinasDia(year,dayOfYear_,idArea)
                    timePicker.show()

                }) {
                    Icon(Icons.Default.Timer, contentDescription = "Select End Time")
                }
            }
        )


        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(

            value = textoFin,
            onValueChange = {  },
            label = { Text("Fin") },
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp,end = 10.dp),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    timePickerFin.show()
                }) {
                    Icon(Icons.Default.Timer, contentDescription = "Select End Time")
                }
            }
        )






















        Button(shape = RoundedCornerShape(5.dp)

            ,onClick = {


                if (textoInicio.isEmpty() || textoFin.isEmpty()) {
                    Toast.makeText(context, "Debe seleccionar una hora de inicio y fin", Toast.LENGTH_LONG).show()
                } else {


                //Log.d("data_listadoOficinas", oficinasHorarios.toString())

//                    val range1 = TimeRange(430, 490) //7:10-8:10
//                    val range2 = TimeRange(475, 520) //7:55-8:40
//                    println("Range1 and Range2 overlap: ${doRangesOverlap(range1, range2)}") // se empalma SI

                    val hour1 = textoInicio.split(":")
                    val hour2 = textoFin.split(":")

                    // ok
                    var horariosReservados = mutableListOf<TimeRange>()
                    oficinasHorarios.forEach{
                        horariosReservados.add(TimeRange(it.horaInicial,it.horafinal))
                    }

                    var horarioRequerido = TimeRange(
                        hour1[0].toInt() * 60 + hour1[1].toInt(),
                        hour2[0].toInt() * 60 + hour2[1].toInt())

                    var uno = minutesToHour(hour1[0].toInt() * 60 + hour1[1].toInt())
                    var dos = minutesToHour(hour2[0].toInt() * 60 + hour2[1].toInt())

                    println()


                    //cuando es falso no se puede agendar
                    var counter = validateAllBookings(horarioRequerido,horariosReservados)
                    //Log.d("counter",counter.toString())





                    val idUsuario = Firebase.auth.currentUser?.uid ?: ""

                //val hoy = LocalDate.now()
                //val dayOfYear_hoy = hoy.dayOfYear

                // example
                // print(isValidTimeRange(Time(8, 9), Time(8, 10)))



                val isValid = ValidacionesHora.isValidTimeRange(
                    ValidacionesHora.Time(hour1[0].toInt(), hour1[1].toInt()),
                    ValidacionesHora.Time(hour2[0].toInt(), hour2[1].toInt())
                )

                if (isValid && counter) {
            viewModel.reservacionOficina(
                context,
                year,
                dayOfYear_,
                textoInicio,
                textoFin,
                "",
                idArea,
                idUsuario
            )


                    textoInicio = ""
                    textoFin = ""



                    //Toast.makeText(context, "Adelante", Toast.LENGTH_LONG).show()
                } else {
                    if(!isValid){
                        Toast.makeText(context, "Rango de hora invalido", Toast.LENGTH_LONG).show()
                    }
                    if(!counter) {
                        Toast.makeText(context, "Horario no disponible", Toast.LENGTH_LONG).show()
                    }

                }







            }
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
            colors = ButtonDefaults.buttonColors( Color(0xFF800000)),

        ) {
            Text(text = "Confirmar reservacion",color = Color.White)
        }



        if(showDialog) {
            DatePickerDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {

                    Button(onClick = {
                        showDialog = false
                    }) {
                        Text(text = "Seleccionar fecha")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        showDialog = false
                    }) {
                        Text(text = "Cancelar")
                    }
                })
            {
                DatePicker(state = state)
            }
        }// end show dialog

        } // column





}
