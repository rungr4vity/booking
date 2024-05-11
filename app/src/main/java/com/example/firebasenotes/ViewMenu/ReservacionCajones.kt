package com.example.firebasenotes.ViewMenu

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.firebasenotes.ui.theme.FirebasenotesTheme
import com.example.firebasenotes.viewModels.LoginViewModel
import com.example.firebasenotes.viewModels.NotesViewModel
import java.time.Instant
import java.time.ZoneId
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
                    ReservacionCajones_extension(loginVM,mContext)
                }
            }
        }
    }

}





@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservacionCajones_extension(loginVM: LoginViewModel, context: Context) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

        val VM = LoginViewModel()
        var menHorarios by remember { mutableStateOf("Seleccionar su horarios") }
        var menEmpresa by remember { mutableStateOf("Isita") }
        var menEspacios by remember { mutableStateOf("Seleccionar su espacio") }
        var emailPrueba by remember { mutableStateOf("admin@ut.com") }

        var expansion_empresa by remember { mutableStateOf(false) }
        var expansion_Horarios by remember { mutableStateOf(false) }
        var expansion_Espacios by remember { mutableStateOf(false) }

        val opsHorarios = listOf("9:00 a.m - 1:00 p.m", "1:00 p.m - 6:00 p.m", "Todo el dia")
        val opsEmpresa = listOf("Isita", "Verifigas")
        val opsEspacios = listOf("441", "443","167","316 (P.Aguirre)","310 (A.Garza)")
        var state = rememberDatePickerState()
        var showDialog by remember {
            mutableStateOf(false)
        }

        OutlinedTextField(value = emailPrueba, onValueChange = {},
            modifier = Modifier
                .fillMaxWidth())

        OutlinedTextField(value = "443", onValueChange = {},
            modifier = Modifier
                .fillMaxWidth())

        ExposedDropdownMenuBox(expanded = expansion_empresa, onExpandedChange = {expansion_empresa = !expansion_empresa} ) {
            OutlinedTextField(
                value = menEmpresa,
                onValueChange = {  },//cambia el valor
                label = { Text("Compañia") },
                readOnly = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expansion_empresa)}
            )
            ExposedDropdownMenu(expanded = expansion_empresa, onDismissRequest = { expansion_empresa= false }) {
                opsEmpresa.forEach { option ->
                    DropdownMenuItem(text = { Text(option)}, onClick = {
                        menEmpresa = option
                        expansion_empresa = false
                    },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
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
                    .menuAnchor(),

                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expansion_Horarios )}
            )
            ExposedDropdownMenu(expanded = expansion_Horarios , onDismissRequest = { expansion_Horarios  = false }) {
                opsHorarios.forEach { option ->
                    DropdownMenuItem(text = { Text(option)}, onClick = {
                        menHorarios = option
                        expansion_Horarios  = false
                    },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

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

        Button(onClick = {
            showDialog = true }) {
            Text(text = "Calendario")
        }

        if(showDialog) {
            DatePickerDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {

                    Button(onClick = { showDialog = false }) {
                        Text(text = "Seleccionar fecha")
                    }
                })
            {
                DatePicker(state = state)
            }
        }


        var data = state.selectedDateMillis
        data?.let {
            val localDate = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
            Text(text = "Fecha: ${localDate.dayOfMonth}/${localDate.month}/${localDate.year}")
        }

        Button(onClick = {
            //coding
            val localFecha = Instant.ofEpochMilli(data ?: 0).atZone(ZoneId.of("UTC")).toLocalDate()
            loginVM.saveSpace(context,emailPrueba,menEmpresa,menHorarios,menEspacios,localFecha.dayOfMonth,localFecha.monthValue,localFecha.year) {

            }
        }) {
            Text(text = "Aceptar")

        }
    }


}
