package com.vistony.salesforce.kotlin.View.Atoms

import android.widget.Switch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony

@Composable
fun SwitchExample(
    isChecked:MutableState<Boolean>,
    enable:Boolean=true
) {
    //var isChecked by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
        ,verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Switch(
            colors =  SwitchDefaults.colors(
                checkedThumbColor = BlueVistony, // Color del pulgar cuando está activo
                checkedTrackColor = BlueVistony.copy(alpha = 0.5f), // Color de la pista cuando está activo
                uncheckedThumbColor = Color.Gray, // Color del pulgar cuando está inactivo
                uncheckedTrackColor = Color.Gray.copy(alpha = 0.5f) // Color de la pista cuando está inactivo
            ),
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value=it
                              },
                enabled = enable
                , modifier = Modifier.clickable {  }
        )
    }
}