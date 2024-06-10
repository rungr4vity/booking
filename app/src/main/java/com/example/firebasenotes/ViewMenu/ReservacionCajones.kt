package com.example.firebasenotes.ViewMenu

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
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
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.firebasenotes.models.horariosModel
import com.example.firebasenotes.ui.theme.FirebasenotesTheme
import com.example.firebasenotes.viewModels.LoginViewModel
import com.example.firebasenotes.viewModels.NotesViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale

class ReservacionCajones : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginVM : LoginViewModel by viewModels()
        val noteVM : NotesViewModel by viewModels()

        val mContext: Context
        mContext = getApplicationContext();

        setContent {
            FirebasenotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReservacionCajones_extension(
                        loginVM,
                        mContext,
                        "",
                        "",
                        "",
                        "",
                        false,""
                    )
                }
            }
        }
    }

}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservacionCajones_extension(
    loginVM: LoginViewModel = LoginViewModel(),
    context: Context,
    nombre: String,
    company: String,
    cajon: String,
    piso: String,
    esEspecial: Boolean,
    idEstacionamiento: String
) {

    @Composable
    fun ComposableLifecycle(
        lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
        onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit
    ) {

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { source, event ->
                onEvent(source, event)
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }

    val TAG = "SecondScreen"
    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                Log.d(TAG, "onCreate")



            }
            Lifecycle.Event.ON_START -> {
                Log.d(TAG, "On Start")
            }
            Lifecycle.Event.ON_RESUME -> {
                Log.d(TAG, "On Resume")
            }
            Lifecycle.Event.ON_PAUSE -> {
                Log.d(TAG, "On Pause")
            }
            Lifecycle.Event.ON_STOP -> {
                Log.d(TAG, "On Stop")
            }
            Lifecycle.Event.ON_DESTROY -> {
                Log.d(TAG, "On Destroy")
            }
            else -> {}
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

// here

        var dayOfyear: Int = 0
        var state = rememberDatePickerState()
        var data = state.selectedDateMillis ?: System.currentTimeMillis()
        var suma = 0

        var today = LocalDate.now()
        var dayOfYear = today.dayOfYear

        var data_listado = System.currentTimeMillis()
        var suma_listado = 0



        data?.let {
            val defaultZoneId = ZoneId.systemDefault()
            Log.d("zoneID",defaultZoneId.toString())
            //UTC
            val localDate = Instant.ofEpochMilli(it).atZone(ZoneId.of("GMT")).toLocalDate()
            //Text(text = "Fecha: ${localDate.dayOfMonth}/${localDate.month}/${localDate.year}")


            //suma_listado = (localDate.dayOfMonth + localDate.month.value + localDate.year)

            //loginVM.getData(localDate.dayOfYear,localDate.year,idEstacionamiento)
            Log.d("data_listado",data_listado.toString())
        }














        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val initialDate = dateFormat.format(calendar.time)
        var text3 by remember { mutableStateOf(initialDate) }
        var showDatePicker by remember { mutableStateOf(false) }
        var date by remember { mutableStateOf(initialDate) }
        var dayOfYear_ by remember { mutableStateOf(calendar.get(Calendar.DAY_OF_YEAR)) }
        var year by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }

        loginVM.getData(dayOfYear_,year,idEstacionamiento)

        Text(
            text = "Confirmar reservación: $nombre", fontWeight = FontWeight.Bold, fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )

        // Add a Row containing the IconButton and Text Field 3
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(Icons.Filled.CalendarToday, contentDescription = "Pick a Date")
            }
            androidx.compose.material.OutlinedTextField(
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
                android.app.DatePickerDialog(
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























        //val opsHorarios_listado: List<horariosModel> by loginVM.horarios.observeAsState(listOf())
        //var menHorarios_listado by remember { mutableStateOf(opsHorarios_listado) }



        val emailfrom = Firebase.auth.currentUser?.email ?: "No user"
        var turno by remember { mutableStateOf(0) }
        var idEstacionamientoMutable by remember { mutableStateOf(idEstacionamiento) }

        var menHorarios by remember { mutableStateOf("Seleccionar su horarios") }
        var menEmpresa by remember { mutableStateOf(company) }
        var menEspacios by remember { mutableStateOf(cajon) }
        var emailPrueba by remember { mutableStateOf(emailfrom) }

        var expansion_empresa by remember { mutableStateOf(false) }
        var expansion_Horarios by remember { mutableStateOf(false) }
        var expansion_Espacios by remember { mutableStateOf(false) }



//        data?.let {
//
//            val localDate = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
//            dayOfyear = localDate.dayOfYear
//            Text(text = "Fecha: ${localDate.dayOfMonth}/${localDate.month}/${localDate.year}")
//            suma = (localDate.dayOfMonth + localDate.month.value + localDate.year)
//
//            //loginVM.getData(dayOfyear,localDate.year,idEstacionamientoMutable)
//
//            loginVM.getData(149,2024,idEstacionamientoMutable)
//        }



        val opsHorarios_old: List<horariosModel> by loginVM.horarios.observeAsState(listOf())
        val opsHorarios: MutableMap<Int,String> by loginVM.horarios_datePicker.observeAsState(mutableMapOf())


        val opsEmpresa = listOf("Isita", "Verifigas")
        val opsEspacios = listOf("441", "443","167","316 (P.Aguirre)","310 (A.Garza)")


        var showDialog by remember {
            mutableStateOf(false)
        }




//        Text(text = "Usuario:  $emailfrom", modifier = Modifier
//            .padding(10.dp)
//            .fillMaxWidth())
        Text(text = "Espacio $cajon - Piso $piso", modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth())



//        OutlinedTextField(value = emailPrueba, onValueChange = {},
//            modifier = Modifier
//                .fillMaxWidth())

//        OutlinedTextField(value = "443", onValueChange = {},
//            modifier = Modifier
//                .fillMaxWidth())

//        ExposedDropdownMenuBox(expanded = expansion_empresa, onExpandedChange = {expansion_empresa = !expansion_empresa} ) {
//            OutlinedTextField(
//                value = menEmpresa,
//                onValueChange = {  },//cambia el valor
//                label = { Text("Compañia") },
//                readOnly = false,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .menuAnchor(),
//                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expansion_empresa)}
//            )
//            ExposedDropdownMenu(expanded = expansion_empresa, onDismissRequest = { expansion_empresa= false }) {
//                opsEmpresa.forEach { option ->
//                    DropdownMenuItem(text = { Text(option)}, onClick = {
//                        menEmpresa = option
//                        expansion_empresa = false
//                    },
//                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
//                    )
//                }
//            }
//        }






//        ExposedDropdownMenuBox(expanded = expansion_Espacios, onExpandedChange = {expansion_Espacios = !expansion_Espacios} ) {
//            OutlinedTextField(
//                value = menEspacios,
//                onValueChange = {  },
//                label = { Text("Espacios") },
//                readOnly = false,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .menuAnchor(),
//                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expansion_Espacios)}
//            )
//            ExposedDropdownMenu(expanded = expansion_Espacios, onDismissRequest = { expansion_Espacios = false }) {
//                opsEspacios.forEach { option ->
//                    DropdownMenuItem(text = { Text(option)}, onClick = {
//                        menEspacios = option
//                        expansion_Espacios= false
//                    },
//                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
//                    )
//                }
//            }
//        }











//        Button(shape = RoundedCornerShape(5.dp),onClick = {
//            showDialog = true },
//            colors = ButtonDefaults.buttonColors( Color(0xFF800000)),
//            modifier = Modifier
//            .fillMaxWidth()
//            .padding(5.dp)) {
//            Text(text = "Calendario",
//                color = Color.White)
//        }

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
        }






        ExposedDropdownMenuBox(expanded = expansion_Horarios , onExpandedChange = {expansion_Horarios  = !expansion_Horarios } ) {
            OutlinedTextField(

                value = menHorarios,
                onValueChange = {  },
                label = { Text("Horario") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .padding(top = 20.dp,start = 10.dp,end = 10.dp),

                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expansion_Horarios )}
            )


//            ExposedDropdownMenu(expanded = expanded,onDismissRequest = { expanded = false },
//                properties = PopupProperties(focusable = false)) {
//                options.forEach { selectionOption ->
//                    DropdownMenuItem(
//                        text = { Text(selectionOption) },
//                        onClick = {selectedOptionText = selectionOption expanded = false }
//                    ) }}


            ExposedDropdownMenu(expanded = expansion_Horarios , onDismissRequest = { expansion_Horarios  = false }) {


                opsHorarios.forEach { option ->
                    DropdownMenuItem(text = { Text(option.value)}, onClick = {
                        turno = option.key
                        menHorarios = option.value
                        expansion_Horarios  = false
                    },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        Text(text = "Especial:  ${if(esEspecial) "Si" else "No"}", modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth())


//        opsHorarios.forEach {
//            Text(text = it.nombre,
//                fontWeight = FontWeight.Medium, fontSize = 15.sp, color = Color.Blue,
//                modifier = Modifier.padding(10.dp),
//            )
//        }

        Button(shape = RoundedCornerShape(5.dp),onClick = {
            //coding
            val localFecha = Instant.ofEpochMilli(data ?: 0).atZone(ZoneId.of("MST")).toLocalDate()
            val idUsuario = Firebase.auth.currentUser?.uid ?: ""

            var hoy = LocalDate.now()
            var dayOfYear_hoy = hoy.dayOfYear

           loginVM.saveSpace(context,year,dayOfYear_,"",idEstacionamientoMutable,idUsuario,turno) {
            }

        },
            colors = ButtonDefaults.buttonColors( Color(0xFF800000)),
            modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)) {
            Text(text = "Aceptar reservacion",color = Color.White)

        }




    }




}
