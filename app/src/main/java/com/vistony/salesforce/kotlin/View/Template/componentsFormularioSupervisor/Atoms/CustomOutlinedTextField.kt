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
fun CustomOutlinedTextField(
    labelText: String,
    value: String,
    onValueChange: (String) -> Unit,
    validator: (String) -> Boolean = { true },
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int  // Parámetro para el número máximo de caracteres
) {
    OutlinedTextField(
        value = value.filter { !it.isSurrogate() }.take(maxLength),  // Asegurarse de no exceder el límite
        onValueChange = { newValue ->
            val filteredValue = newValue.filter { !it.isSurrogate() }.take(maxLength)  // Filtra y asegura el límite
            if (validator(filteredValue)) {
                onValueChange(filteredValue)
            }
        },
        label = { Text(labelText, Modifier.padding(bottom = if (labelText.length > 24) 24.dp else 4.dp).padding(end = 8.dp), color = Color.Black) },
        modifier = modifier.padding(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF0957C3),
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = Color.Gray
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        //colors = Color.Blue,
        keyboardActions = KeyboardActions(onDone = { /* Optional: Handle done button press */ })
    )
}

