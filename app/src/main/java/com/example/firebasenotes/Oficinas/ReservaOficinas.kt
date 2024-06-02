package com.example.firebasenotes.Oficinas

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.example.firebasenotes.models.horariosModel
import com.example.firebasenotes.models.oficinasDTO
import com.example.firebasenotes.ui.theme.FirebasenotesTheme
import com.example.firebasenotes.viewModels.LoginViewModel
import com.example.firebasenotes.viewModels.NotesViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

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

        Log.d("entramos","OK")

        var state = rememberDatePickerState()
        var data = state.selectedDateMillis ?: System.currentTimeMillis()

        val today = LocalDate.now()
        val dayOfYear = today.dayOfYear
        var localDate = LocalDate.now()

        data?.let {
            localDate = Instant.ofEpochMilli(it).atZone(ZoneId.of("GMT")).toLocalDate()
            Text(text = "Fecha: ${localDate.dayOfMonth}/${localDate.month}/${localDate.year}")
        }

        //viewModel.ReservacionOficinasDia(localDate.year,localDate.dayOfYear,idArea)
        Log.d("data_listadoOficinas",oficinasHorarios.toString())

        val emailfrom = Firebase.auth.currentUser?.email ?: "No user"

        var textoInicio by rememberSaveable { mutableStateOf("") }
        var textoFin by rememberSaveable { mutableStateOf("") }

        var showDialog by remember { mutableStateOf(false) }

        Text(
            text = "Confirmar reservación: ", fontWeight = FontWeight.Bold, fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )

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
        Text(text = "idArea $idArea", modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth())


        Button(shape = RoundedCornerShape(5.dp),onClick = {
            showDialog = true },modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)) {
            Text(text = "Calendario")
        }

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

        Button(shape = RoundedCornerShape(5.dp),
            onClick = {
                timePicker.show()
                      },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {
            Text(text = "Inicio: $textoInicio", modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth())
        }

        Button(shape = RoundedCornerShape(5.dp),
            onClick = {
                timePickerFin.show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {

            Text(text = "Fin: $textoFin", modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth())
        }

        Button(shape = RoundedCornerShape(5.dp)

            ,onClick = {
            val idUsuario = Firebase.auth.currentUser?.uid ?: ""

            //val hoy = LocalDate.now()
            //val dayOfYear_hoy = hoy.dayOfYear

            viewModel.reservacionOficina(
                context,
                localDate.year,
                localDate.dayOfYear,
                textoInicio,
                textoFin,
                "",
                idArea,
                idUsuario
            )

        }, modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)

        ) {
            Text(text = "Confirmar reservacion")
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