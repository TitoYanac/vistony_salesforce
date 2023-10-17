package com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Organisms

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.kotlin.View.Pages.ApiResponse
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Atoms.CustomOutlinedTextField
import com.vistony.salesforce.kotlin.View.components.Spinner


@Composable
fun SectionFormulario(
    apiResponse: MutableState<ApiResponse>,
    onApiResponseChange: (ApiResponse) -> Unit
) {
    // Se establece un estado local para gestionar la API response dentro del composable.
    //var localApiResponse: MutableState<ApiResponse> = remember { mutableStateOf(apiResponse) }

    // Se inicializa automáticamente las respuestas al valor predeterminado al principio.
    LaunchedEffect(key1 = apiResponse) { // Cambio de localApiResponse a apiResponse
        val updatedFormulario = apiResponse.value.formulario?.map { preguntaRespuesta ->
            preguntaRespuesta.copy(respuesta = preguntaRespuesta.opciones.first().valor)
        }
        val updatedApiResponse = apiResponse.value.copy(formulario = updatedFormulario)
        //localApiResponse.value = updatedApiResponse
        onApiResponseChange(updatedApiResponse) // Notifica al componente padre sobre el cambio.
    }

    // Se utiliza 'localApiResponse' en lugar de 'apiResponse' en el resto del composable.

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color(0xFF1565C0), RoundedCornerShape(6.dp))
            .shadow(2.dp, shape = RoundedCornerShape(4.dp))
            .padding(20.dp)
    ) {
        apiResponse.value.formulario?.forEachIndexed { index, preguntaRespuesta ->
            preguntaRespuesta.pregunta?.let {
                Row {
                    Text(
                        text = "${index + 1}. ",
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            var expanded by remember { mutableStateOf(false) }
            var selectedOption by remember {
                mutableStateOf(preguntaRespuesta.opciones.first())
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Respuesta:",
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.width(120.dp)
                )
                /*val currentSelectionSpinner = remember {
                    mutableStateOf("SELECCIONAR OPCION")
                }
                var opcionList: MutableList<String> = mutableListOf()
                for (i in 0 until preguntaRespuesta.opciones.size) {
                    opcionList.add(preguntaRespuesta.opciones.get(i).opcion!!)
                }
                Spinner(
                        items = opcionList,
                        selectedItem = currentSelectionSpinner.value,
                        onItemSelected = { item ->
                            currentSelectionSpinner.value = item
                        }
                )*/

                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Gray)
                        .shadow(1.dp)
                        .background(MaterialTheme.colors.surface)
                        .fillMaxHeight()
                        .clickable { expanded = true }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .wrapContentHeight(Alignment.CenterVertically),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        selectedOption.opcion?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.body1
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 120.dp)
                ) {
                    preguntaRespuesta.opciones.forEach { opcion ->
                        val preguntaIndex = apiResponse.value.formulario?.indexOf(preguntaRespuesta) ?: -1
                        DropdownMenuItem(
                            onClick = {
                                if (preguntaIndex != -1) {
                                    //apiResponse.value.formulario!!.get(preguntaIndex).copy(respuesta = opcion.valor)
                                    //apiResponse.value.copy(formulario = )
                                    val updatedPreguntaRespuesta = preguntaRespuesta.copy(respuesta = opcion.valor)
                                    Log.e("testrespuesta: ", "updatedPreguntaRespuesta: ${updatedPreguntaRespuesta}")
                                    val updatedFormulario = apiResponse.value.formulario!!.toMutableList().apply {
                                        set(preguntaIndex, updatedPreguntaRespuesta)
                                    }
                                    Log.e("testrespuesta: ", "updatedFormulario: ${updatedFormulario}")
                                    val updatedApiResponse = apiResponse.value.copy(formulario = updatedFormulario)
                                    Log.e("testrespuesta: ", "updatedApiResponse: ${updatedApiResponse}")

                                    //localApiResponse = updatedApiResponse
                                    onApiResponseChange(updatedApiResponse)
                                    //apiResponse.value
                                }

                                selectedOption = opcion
                                expanded = false
                            }
                        ) {
                            opcion.opcion?.let {

                                //Log.e("testrespuesta: ", "updatedPreguntaRespuesta: ${it}")
                                //apiResponse.value.formulario!!.get(preguntaIndex).respuesta=it
                                Text(text = it) }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        var comentario by remember { mutableStateOf(apiResponse.value.comentario ?: "") }

        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedTextField(
            labelText = "Comentario Adicional",
            value = comentario,
            onValueChange = { newValue ->
                comentario = newValue
                val updatedApiResponse = apiResponse.value?.copy(comentario = newValue)
                if (updatedApiResponse != null) {
                    //onApiResponseChange(updatedApiResponse)
                }
            }
        )


        Spacer(modifier = Modifier.height(16.dp))
    }
}


