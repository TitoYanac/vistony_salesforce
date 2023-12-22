package com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vistony.salesforce.kotlin.Model.FormularioSupervisorViewModel
import com.vistony.salesforce.kotlin.Model.TipoSalida
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Molecules.CampoTextoDisabled
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Molecules.RenderTipoSalidaCheckboxes

@Composable
fun TileExpansionBodyPrincipal(formularioSupervisorViewModel: FormularioSupervisorViewModel) {
    val resultAPI         = formularioSupervisorViewModel.resultAPI.collectAsState()
    if(resultAPI.value.Data == null) return
    val apiResponse       = resultAPI.value.Data!!
    if(apiResponse.datosPrincipales == null) return
    val datosPrincipales  = apiResponse.datosPrincipales!!
    val numInforme      : String     = datosPrincipales.numInforme?:""
    val fechaHoy        : String     = datosPrincipales.fechaHoy?:""
    val nombreSupervisor: String     = datosPrincipales.nombreSupervisor?:""
    val nombreVendedor  : String     = datosPrincipales.nombreVendedor?:""
    val tipoSalida: List<TipoSalida> = datosPrincipales.tipoSalida?: emptyList()
    Card(modifier = Modifier
        .background(color = Color.White)
        .padding(horizontal = 8.dp, vertical = 12.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            CampoTextoDisabled("N° Informe:", numInforme ?: "")
            CampoTextoDisabled("Fecha Hoy:", fechaHoy ?: "")
            CampoTextoDisabled("Nombre Supervisor:", nombreSupervisor ?: "")
            CampoTextoDisabled("Nombre Vendedor:", nombreVendedor ?: "")
            Spacer(modifier = Modifier.height(16.dp))
            Row (modifier = Modifier.fillMaxWidth()){
                Text(
                    text = "Tipo de salida realizada:",
                    style = TextStyle(
                        fontSize = 14.sp, // Adjust the font size as needed
                        fontWeight = FontWeight.Bold, // You can customize the text style
                        color = Color.Black // Customize the text color
                    )
                )
            }

            if (tipoSalida.isNotEmpty()) {
                RenderTipoSalidaCheckboxes(formularioSupervisorViewModel= formularioSupervisorViewModel)
            }
        }
    }

}
