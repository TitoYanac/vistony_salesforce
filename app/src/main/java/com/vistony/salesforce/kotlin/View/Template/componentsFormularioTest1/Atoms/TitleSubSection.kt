package com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Atoms

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun TitleSubSection(label: String,) {
    Text(
        text = label,
        style = MaterialTheme.typography.h6,
        modifier = Modifier
            .padding(10.dp) // Ajuste en el padding
    )
}