package com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Atoms

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            if (newValue.all { it.isDigit() } && validator(newValue)) {  // Añadido el validador
                onValueChange(newValue)
            }
        },
        label = { Text(labelText, Modifier.padding(horizontal = 4.dp)) },
        modifier = modifier.padding(16.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,  // Asegurándose de que el tipo de teclado es Number
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { /* Optional: Handle done button press */ })
    )
}