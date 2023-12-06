package com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vistony.salesforce.kotlin.Model.ApiResponse
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Molecules.CampoTextoDisabled
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Molecules.RenderTipoSalidaCheckboxes

@Composable
fun SectionDatosPrincipales(apiResponse: MutableState<ApiResponse?>, onApiResponseChange: (ApiResponse) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .border(
                1.dp,
                Color(0xFF0957C3),
                RoundedCornerShape(6.dp)
            )
            .shadow(2.dp, shape = RoundedCornerShape(4.dp))
            .padding(20.dp)
    ) {
        apiResponse.value?.datosPrincipales?.numInforme?.let { CampoTextoDisabled("N° Informe:", it) }
        apiResponse.value?.datosPrincipales?.fechaHoy?.let { CampoTextoDisabled("Fecha Hoy:", it) }
        apiResponse.value?.datosPrincipales?.nombreSupervisor?.let {
            CampoTextoDisabled("Nombre Supervisor:", it)
        }
        apiResponse.value?.datosPrincipales?.nombreVendedor?.let {
            CampoTextoDisabled("Nombre Vendedor:", it)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(
                text = "Tipo de salida realizada:",
                style = TextStyle(
                    fontSize = 14.sp, // Adjust the font size as needed
                    fontWeight = FontWeight.Bold, // You can customize the text style
                    color = Color.Black // Customize the text color
                )
            )

        }

        apiResponse.value?.datosPrincipales?.tipoSalida?.let {
            RenderTipoSalidaCheckboxes(it) { index, updatedTipoSalida ->

                // Paso 1: Obtener la lista original de tipo de salida.
                val originalTipoSalidaList = apiResponse.value!!.datosPrincipales?.tipoSalida

                // Paso 2: Crear una lista mutable para poder editarla y actualizar la entrada correspondiente.
                val updatedTipoSalidaList = originalTipoSalidaList?.toMutableList().apply {
                    this?.set(index, updatedTipoSalida)
                }

                // Paso 3: Obtener los datos principales originales.
                val originalDatosPrincipales = apiResponse.value!!.datosPrincipales

                // Paso 4: Crear una copia de los datos principales con la lista de tipo de salida actualizada.
                val updatedDatosPrincipales = originalDatosPrincipales?.copy(tipoSalida = updatedTipoSalidaList)

                // Paso 5: Obtener la respuesta API original.

                // Paso 6: Crear una copia de la respuesta API con los datos principales actualizados.
                val updatedApiResponse = apiResponse.value!!.copy(datosPrincipales = updatedDatosPrincipales)

                // Paso 7: Invocar el callback con la respuesta API actualizada.
                onApiResponseChange(updatedApiResponse)
            }
        }


    }

}
