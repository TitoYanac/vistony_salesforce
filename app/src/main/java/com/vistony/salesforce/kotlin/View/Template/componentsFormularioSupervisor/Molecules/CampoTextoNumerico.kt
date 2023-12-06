package com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CampoTextoNumerico(label: String, initialValor: String) {
    val backgroundColor = Color.LightGray

    // Usa un estado mutable para el valor del TextField
    var valor by remember { mutableStateOf(initialValor) }

    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .background(backgroundColor, RoundedCornerShape(8.dp))
                .height(48.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = valor,
                onValueChange = { nuevoValor ->
                    // Validación para asegurar que solo se aceptan números
                    if (nuevoValor.all { it.isDigit() }) {
                        valor = nuevoValor
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { /* Implementación del teclado al terminar */ }),
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                enabled = true
            )
        }
    }
}

