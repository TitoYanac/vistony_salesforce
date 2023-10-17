package com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Organisms

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.kotlin.View.Pages.ApiResponse
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Atoms.CustomOutlinedNumericField
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Atoms.CustomOutlinedTextField
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Molecules.CheckboxLabel
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Molecules.TimePickerRow


@Composable
fun SectionDatosVisita(apiResponse: MutableState<ApiResponse>, onApiResponseChange: (ApiResponse) -> Unit)
{
    Log.e("test", "SectionDatosVisita: ${apiResponse.value.datosVisita}")
    //var localApiResponse by remember { mutableStateOf(apiResponse) }
    var zona by remember { mutableStateOf(apiResponse.value.datosVisita?.zona ?: "") }


    var observacionZona by remember { mutableStateOf(apiResponse.value.datosVisita?.observacionZona ?: "") }
    var mostrarObservaciones by remember { mutableStateOf(false) }
    var horaInicio by remember {
        mutableStateOf(apiResponse.value.datosVisita?.horaInicio?.ifBlank { "00:00 AM" } ?: "00:00 AM")
    }
    var horaFin by remember {
        mutableStateOf(apiResponse.value.datosVisita?.horaFin?.ifBlank { "00:00 PM" } ?: "00:00 PM")
    }

    var clientesNuevos by remember { mutableStateOf(apiResponse.value.datosVisita?.clientesNuevos ?: "") }
    var clientesEmpadronados by remember { mutableStateOf(
        apiResponse.value.datosVisita?.clientesEmpadronados ?: "") }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color(0xFF1565C0), RoundedCornerShape(6.dp))
            .shadow(2.dp, shape = RoundedCornerShape(4.dp))
            .padding(20.dp)
    )  {

        CustomOutlinedTextField(
            labelText = "Zona",
            value = zona,
            onValueChange = { newValue ->
                zona = newValue
                val updatedDatosVisita = apiResponse.value.datosVisita?.copy(zona = newValue)
                val updatedApiResponse = apiResponse.value.copy(datosVisita = updatedDatosVisita)
                //apiResponse.value = updatedApiResponse
                onApiResponseChange(updatedApiResponse)
            }
        )



        CheckboxLabel(mostrarObservaciones, "¿Tienes observaciones?") {
            mostrarObservaciones = it
        }

        if (mostrarObservaciones) {

            CustomOutlinedTextField(
                labelText = "Observaciones del recorrido",
                value = observacionZona,
                onValueChange = { newValue ->
                    observacionZona = newValue
                    val updatedDatosVisita = apiResponse.value.datosVisita?.copy(observacionZona = newValue)
                    val updatedApiResponse = apiResponse.value.copy(datosVisita = updatedDatosVisita)
                    //apiResponse.value = updatedApiResponse
                    onApiResponseChange(updatedApiResponse)
                }
            )


        }

        TimePickerRow("Hora de inicio", horaInicio) { newValue ->
            horaInicio = newValue
            Log.e("test", "horaInicio: $horaInicio")
            var updatedDatosVisita = apiResponse.value.datosVisita?.copy(horaInicio = newValue)
            var updatedApiResponse = apiResponse.value.copy(datosVisita = updatedDatosVisita)
            onApiResponseChange(updatedApiResponse)
        }
        TimePickerRow("Hora fin", horaFin) { newValue ->
            Log.e("test", "horaFin: $horaFin")
            horaFin = newValue
            var updatedDatosVisita = apiResponse.value.datosVisita?.copy(horaFin = newValue)
            var updatedApiResponse = apiResponse.value.copy(datosVisita = updatedDatosVisita)
            onApiResponseChange(updatedApiResponse)
        }

        CustomOutlinedNumericField(
            labelText = "Clientes nuevos:",
            value = clientesNuevos,
            onValueChange = { newValue ->
                clientesNuevos = newValue
                val updatedDatosVisita = apiResponse.value.datosVisita?.copy(clientesNuevos = newValue)
                val updatedApiResponse = apiResponse.value.copy(datosVisita = updatedDatosVisita)
                //apiResponse.value = updatedApiResponse
                onApiResponseChange(updatedApiResponse)
            }
        )
        CustomOutlinedNumericField(
            labelText = "Clientes empadronados:",
            value = clientesEmpadronados,
            onValueChange = { newValue ->
                clientesEmpadronados = newValue
                val updatedDatosVisita = apiResponse.value.datosVisita?.copy(clientesEmpadronados = newValue)
                val updatedApiResponse = apiResponse.value.copy(datosVisita = updatedDatosVisita)
                //localApiResponse = updatedApiResponse
                onApiResponseChange(updatedApiResponse)
            }
        )
    }
}




