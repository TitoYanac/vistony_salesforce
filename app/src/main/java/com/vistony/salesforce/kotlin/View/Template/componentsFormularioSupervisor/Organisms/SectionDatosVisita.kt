package com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Organisms

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.kotlin.Model.ApiResponse
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Atoms.CustomOutlinedNumericField
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Atoms.CustomOutlinedNumericFieldNormal
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Atoms.CustomOutlinedTextField
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Molecules.TimePickerRow


@Composable
fun SectionDatosVisita(apiResponse: MutableState<ApiResponse?>, onApiResponseChange: (ApiResponse) -> Unit)
{
    Log.e("test", "SectionDatosVisita: ${apiResponse.value?.datosVisita}")
    var zona by remember { mutableStateOf(apiResponse.value?.datosVisita?.zona ?: "") }


    var observacionZona by remember { mutableStateOf(apiResponse.value?.datosVisita?.observacionZona ?: "") }
    var horaInicio by remember {
        mutableStateOf(apiResponse.value?.datosVisita?.horaInicio?.ifBlank { "00:00 AM" } ?: "00:00 AM")
    }
    var horaFin by remember {
        mutableStateOf(apiResponse.value?.datosVisita?.horaFin?.ifBlank { "00:00 PM" } ?: "00:00 PM")
    }

    var clientesNuevos by remember { mutableStateOf(apiResponse.value?.datosVisita?.clientesNuevos ?: "") }
    var clientesEmpadronados by remember { mutableStateOf(
        apiResponse.value?.datosVisita?.clientesEmpadronados ?: "") }


    Column(
        modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(1.dp, Color(0xFF0957C3), RoundedCornerShape(6.dp))
                .shadow(2.dp, shape = RoundedCornerShape(4.dp))
                .padding(20.dp)
    )  {

        CustomOutlinedTextField(
            labelText = "Zona",
            value = zona,
            maxLength = 99,
            onValueChange = { newValue ->
                zona = newValue
                val updatedDatosVisita = apiResponse.value?.datosVisita?.copy(zona = newValue)
                val updatedApiResponse = apiResponse.value?.copy(datosVisita = updatedDatosVisita)
                //apiResponse.value = updatedApiResponse
                if (updatedApiResponse != null) {
                    onApiResponseChange(updatedApiResponse)
                }
            }
        )


        if (true) {

            CustomOutlinedTextField(
                labelText = "Comentarios (Opcional)",
                value = observacionZona,
                maxLength = 254,
                onValueChange = { newValue ->
                    observacionZona = newValue
                    val updatedDatosVisita = apiResponse.value?.datosVisita?.copy(observacionZona = newValue)
                    val updatedApiResponse = apiResponse.value?.copy(datosVisita = updatedDatosVisita)
                    //apiResponse.value = updatedApiResponse
                    if (updatedApiResponse != null) {
                        onApiResponseChange(updatedApiResponse)
                    }
                }
            )


        }

        TimePickerRow("Hora de inicio", horaInicio) { newValue ->
            horaInicio = newValue
            Log.e("test", "horaInicio: $horaInicio")
            var updatedDatosVisita = apiResponse.value?.datosVisita?.copy(horaInicio = newValue)
            var updatedApiResponse = apiResponse.value?.copy(datosVisita = updatedDatosVisita)
            if (updatedApiResponse != null) {
                onApiResponseChange(updatedApiResponse)
            }
        }
        TimePickerRow("Hora fin", horaFin) { newValue ->
            Log.e("test", "horaFin: $horaFin")
            horaFin = newValue
            var updatedDatosVisita = apiResponse.value?.datosVisita?.copy(horaFin = newValue)
            var updatedApiResponse = apiResponse.value?.copy(datosVisita = updatedDatosVisita)
            if (updatedApiResponse != null) {
                onApiResponseChange(updatedApiResponse)
            }
        }

        CustomOutlinedNumericField(
            labelText = "Clientes nuevos:",
            value = clientesNuevos,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { newValue ->
                clientesNuevos = newValue
                val updatedDatosVisita = apiResponse.value?.datosVisita?.copy(clientesNuevos = newValue)
                val updatedApiResponse = apiResponse.value?.copy(datosVisita = updatedDatosVisita)
                //apiResponse.value = updatedApiResponse
                if (updatedApiResponse != null) {
                    onApiResponseChange(updatedApiResponse)
                }
            }
        )
        CustomOutlinedNumericField(
            labelText = "Clientes empadronados:",
            value = clientesEmpadronados,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { newValue ->
                clientesEmpadronados = newValue
                val updatedDatosVisita = apiResponse.value?.datosVisita?.copy(clientesEmpadronados = newValue)
                val updatedApiResponse = apiResponse.value?.copy(datosVisita = updatedDatosVisita)
                //localApiResponse = updatedApiResponse
                if (updatedApiResponse != null) {
                    onApiResponseChange(updatedApiResponse)
                }
            }
        )

        Row(
                modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp)
        ) {
            TableCell("Resumen de visitas", textAlign = TextAlign.Center, weight = 1f, title = true)
        }
        Log.e("test", "apiResponse.value.datosVisita?.resumen: ${apiResponse.value?.datosVisita?.resumen}")
            apiResponse.value?.datosVisita?.resumen!!.forEach  { elemento ->
                var cantidad by remember { mutableStateOf(elemento.cantidad ?: "0") }
                var monto by remember { mutableStateOf(elemento.monto ?: "0.00") }
                Row(
                        modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp)
                ) {
                    var label:String=""
                    if(elemento.tipo!!.equals("pedidos"))
                    {
                        label="Pedidos"
                    }else if(elemento.tipo!!.equals("cobranza"))
                    {
                        label="Cobranzas"
                    }else if(elemento.tipo!!.equals("visita"))
                    {
                        label="Visitas"
                    }
                    TableCell(label, textAlign = TextAlign.Left, weight = 1f, title = false)
                }
                Row() {
                    Column(modifier = Modifier.weight(1f)){
                        CustomOutlinedNumericField(
                                labelText = "Cantidad",
                            status = false,
                                value = cantidad,
                                onValueChange = { newValue ->
                                    cantidad=newValue
                                    elemento.cantidad=cantidad
                                }
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {

                        CustomOutlinedNumericFieldNormal(
                            status = false,
                                labelText = "Monto",
                                value = monto,
                                onValueChange = { newValue ->
                                    monto=newValue
                                    elemento.monto=monto
                                },
                                keyboardType = KeyboardType.Decimal
                        )

                    }
                }
            }

        var comentario by remember { mutableStateOf(apiResponse.value?.comentario ?: "") }
        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedTextField(
            labelText = "Comentarios del Trabajo de Campo Realizado",
            value = comentario,
            maxLength = 254,
            onValueChange = { newValue ->
                comentario = newValue
                val updatedApiResponse = apiResponse.value?.copy(comentario = newValue)
                if (updatedApiResponse != null) {
                    onApiResponseChange(updatedApiResponse)
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}




