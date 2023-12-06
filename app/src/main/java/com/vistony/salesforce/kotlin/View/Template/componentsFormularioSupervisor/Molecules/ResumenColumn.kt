package com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Molecules

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp



@Composable
fun ResumenColumn(title: String, items: List<String>) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .padding(8.dp)
            .border(1.dp, Color.Black)
            .shadow(1.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        items.forEach { item ->
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}

