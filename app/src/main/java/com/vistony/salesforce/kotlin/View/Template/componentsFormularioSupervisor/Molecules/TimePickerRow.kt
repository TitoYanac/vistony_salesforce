package com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.RowScopeInstance.weight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TimePickerRow(
    label: String,
    selectedTime: String,
    onTimeChange: (String) -> Unit
) {


    val timeParts = selectedTime.split(":", " ")
    val initialHour = timeParts.getOrNull(0)?.takeIf { it.isNotBlank() }?.padStart(2, '0') ?: "00"
    val initialMinute = timeParts.getOrNull(1)?.takeIf { it.isNotBlank() }?.padStart(2, '0') ?: "00"
    //val initialPeriod = timeParts.getOrNull(2) ?: "AM"


    var hour by remember { mutableStateOf(initialHour) }
    var minute by remember { mutableStateOf(initialMinute) }
    //var period by remember { mutableStateOf(initialPeriod) }


    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text(label)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = hour,
                onValueChange = {
                    // Permitir borrar o si es un número en el rango adecuado
                    if (it.isEmpty() || (it.all { char -> char.isDigit() } && it.toInt() in 0..23)) {
                        hour = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { /* Handle next action */ }),
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF0957C3),
                    unfocusedBorderColor = Color.Gray,
                    disabledBorderColor = Color.Gray,
                    focusedLabelColor = Color.Black,
                    disabledLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black
                ),
                label = {
                    Text("Hora")
                }
            )
            Text(":", modifier = Modifier.padding(horizontal = 4.dp))
            OutlinedTextField(
                value = minute,
                onValueChange = {
                    // Permitir borrar o si es un número en el rango adecuado
                    if (it.isEmpty() || (it.all { char -> char.isDigit() } && it.toInt() in 0..59)) {
                        minute = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { /* Handle next action */ }),
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF0957C3),
                    unfocusedBorderColor = Color.Gray,
                    disabledBorderColor = Color.Gray,
                    focusedLabelColor = Color.Black,
                    disabledLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black
                ),
                label = {
                    Text("Minutos")
                }
            )
            /*Text(" ", modifier = Modifier.padding(horizontal = 4.dp))
            Button(
                onClick = {
                    period = if (period == "AM") "PM" else "AM"
                },
                modifier = Modifier
                    .width(80.dp)
                    .height(60.dp)
                    .padding(top = 14.dp)// Asegurando que el botón tenga la misma altura que los campos de texto
            ) {
                Text(period)
            }*/
        }
    }

    // Verificar si la hora y los minutos son válidos y activar onTimeChange
    LaunchedEffect(hour, minute) {
        val formattedHour = hour.trimStart('0').padStart(2, '0')
        val formattedMinute = minute.trimStart('0').padStart(2, '0')

        onTimeChange("$formattedHour:$formattedMinute ")
    }
}