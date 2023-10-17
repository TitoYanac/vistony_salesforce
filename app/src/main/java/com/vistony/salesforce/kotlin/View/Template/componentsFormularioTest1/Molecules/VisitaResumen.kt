package com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vistony.salesforce.kotlin.View.Pages.ApiResponse
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Atoms.TitleSubSection


@Composable
fun VisitaResumen(label: String,apiResponse: ApiResponse, onApiResponseChange: (ApiResponse) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        item {
            Column {
                apiResponse.datosVisita?.resumen?.get(0)?.tipo?.let { TitleSubSection(label = it) }
                CampoTextoNumerico(label = "cantidad", initialValor = "0")
                CampoTextoNumerico(label = "monto", initialValor = "0")
            }
        }
        item {
            Column {
                apiResponse.datosVisita?.resumen?.get(1)?.tipo?.let { TitleSubSection(label = it) }
                CampoTextoNumerico(label = "cantidad", initialValor = "0")
                CampoTextoNumerico(label = "monto", initialValor = "0")
            }
        }
        item {
            Column {
                apiResponse.datosVisita?.resumen?.get(2)?.tipo?.let { TitleSubSection(label = it) }
                CampoTextoNumerico(label = "cantidad", initialValor = "0")
                CampoTextoNumerico(label = "monto", initialValor = "0")
            }
        }
    }
}

@Composable
fun ResumenColumn(
    title: String,
    items: List<String>,
    onItemChange: (Int, String) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = title, fontWeight = FontWeight.Bold)
        items.forEachIndexed { index, item ->
            TextField(
                value = item,
                onValueChange = { onItemChange(index, it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textStyle = TextStyle(fontSize = 16.sp)
            )
        }
    }
}

