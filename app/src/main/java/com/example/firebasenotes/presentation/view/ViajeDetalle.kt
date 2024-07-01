package com.example.firebasenotes.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ViajeDetalle(motivo: String) {



    Column(modifier = Modifier.fillMaxWidth()) {

        Card(modifier = Modifier.padding(10.dp).fillMaxWidth()) {

            Text(text = "Viaticos", fontWeight = FontWeight.Medium, fontSize = 20.sp,
                modifier = Modifier.padding(all = 10.dp).fillMaxWidth())

            Text(text = motivo , fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(all = 10.dp).fillMaxWidth())

            Text(text = "SIGMA", fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(all = 10.dp).fillMaxWidth())

            TextField(shape = RoundedCornerShape(5.dp),value = "Presupuesto", onValueChange = {},
                modifier = Modifier.padding(all = 10.dp).fillMaxWidth())

            TextField(shape = RoundedCornerShape(5.dp),value = "Destino", onValueChange = {},
                modifier = Modifier.padding(all = 10.dp).fillMaxWidth())


            Button(shape = RoundedCornerShape(5.dp), onClick = { /*TODO*/ },
                modifier = Modifier.padding(top = 50.dp, start = 10.dp, end = 10.dp).fillMaxWidth()) {
                Text(text = "Crear viaje")
            }
        } // end card

    }

}