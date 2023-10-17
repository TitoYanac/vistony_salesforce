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
fun CustomOutlinedTextField(
    labelText: String,
    value: String,
    onValueChange: (String) -> Unit,
    validator: (String) -> Boolean = { true },  // Añadido un validador genérico por defecto que siempre devuelve true
    modifier: Modifier = Modifier,  // Permite pasar modificadores externamente
    keyboardType: KeyboardType = KeyboardType.Text  // Añadido el tipo de teclado como parámetro
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (validator(newValue)) {  // Utilizando el validador
                onValueChange(newValue)
            }
        },
        label = { Text(labelText, Modifier.padding(horizontal = 4.dp)) },
        modifier = modifier.padding(16.dp),  // Uso del modificador externo
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,  // Uso del tipo de teclado pasado como parámetro
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { /* Optional: Handle done button press */ })
    )
}

