package com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CheckboxLabel(checked: Boolean, label: String, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF0957C3),
                uncheckedColor = Color.Gray,
                checkmarkColor = Color.White,
                disabledIndeterminateColor = Color.Gray
            )
        )
        Text(label, color = Color.Black)  // Aquí es donde se debió realizar la corrección.
    }
}

