package com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Atoms

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@Composable
fun CustomOutlinedNumericField(
    labelText: String,
    value: String,
    onValueChange: (String) -> Unit,
    validator: (String) -> Boolean = { true },  // Añadido un validador genérico por defecto que siempre devuelve true
    modifier: Modifier = Modifier  // Permite pasar modificadores externamente
    ,status: Boolean = true
    ,keyboardType:KeyboardType= KeyboardType.Number
) {
    val sanitizedValue = value?.takeIf { it.all { char -> char.isDigit() } }
    val displayValue = sanitizedValue ?: "0"

    OutlinedTextField(
        enabled=status,
        //value = value,
        value = displayValue,
        onValueChange = { newValue ->
            // Filtra los caracteres no numéricos
            val sanitizedValue = newValue.filter { it.isDigit() }

            val finalValue = when {
                // Si el valor es vacío, cambiar a "0"
                sanitizedValue.isEmpty() -> "0"
                // Si el valor es mayor que cero, eliminar los ceros a la izquierda
                sanitizedValue.toIntOrNull() ?: 0 > 0 -> sanitizedValue.trimStart('0')
                // En otros casos, mantener el valor ingresado
                else -> sanitizedValue
            }

            // Verifica si el valor es válido antes de actualizar
            if (validator(finalValue)) {
                onValueChange(finalValue)
            }
        },
        label = { Text(labelText,Modifier.padding(horizontal = 4.dp)) },
        modifier = modifier.padding(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF0957C3),
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = Color.Gray,
            focusedLabelColor = Color.Black,
            disabledLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,  // Asegurándose de que el tipo de teclado es Number
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { /* Optional: Handle done button press */ })
    )
}

@Composable
fun CustomOutlinedNumericFieldNormal(
    labelText: String,
    value: String,
    onValueChange: (String) -> Unit,
    validator: (String) -> Boolean = { true }, // Validador genérico por defecto
    modifier: Modifier = Modifier,             // Permite pasar modificadores externamente
    status: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Number
) {
    OutlinedTextField(
        enabled = status,
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        label = { Text(labelText, Modifier.padding(horizontal = 4.dp)) },
        modifier = modifier.padding(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF0957C3),
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = Color.Gray,
            focusedLabelColor = Color.Black,
            disabledLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { /* Optional: Handle done button press */ })
    )
}
