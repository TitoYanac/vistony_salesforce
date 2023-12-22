package com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Organisms

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.kotlin.Model.FormularioSupervisorViewModel


@Composable
fun TileExpansionBodyFormulario(formularioSupervisorViewModel: FormularioSupervisorViewModel) {
    Log.e("jesusdebug", "TileExpansionBodyFormulario")
    val resultAPI   = formularioSupervisorViewModel.resultAPI.collectAsState()
    Log.e("jesusdebug", "resultAPI")
    if(resultAPI.value.Data == null) return
    val apiResponse = resultAPI.value.Data!!
    if(apiResponse.datosVisita == null) return
    val formulario  = apiResponse.formulario!!


    Log.e("jesusdebug", "mostrando preguntas")

    Card(modifier = Modifier
        .background(color = Color.White)
        .padding(horizontal = 8.dp, vertical = 12.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ){
            formulario.forEachIndexed { index, preguntaActual ->
                val pregunta: String = preguntaActual.pregunta ?: "(vacio)"
                Row {
                    Text(
                        text = "${index + 1}. ",
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = pregunta,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))


                val isDropdownOpen = remember { mutableStateOf(false) }
                val labelRespuesta = remember { mutableStateOf(preguntaActual.respuesta ?: "") }
                val valueRespuesta = remember { mutableStateOf(preguntaActual.valor ?: "") }

                val optionsList = preguntaActual.opciones
                if (labelRespuesta.value == "") {
                    labelRespuesta.value = optionsList.get(0).opcion ?: ""
                    valueRespuesta.value = optionsList.get(0).valor ?: ""
                    Log.e(
                        "jesusdebug",
                        "cargando respuesta :${labelRespuesta.value} ${valueRespuesta.value}"
                    )
                    formularioSupervisorViewModel.updateRespuesta(
                        index,
                        optionsList.get(0).opcion,
                        optionsList.get(0).valor
                    )
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

                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Gray)
                            .shadow(1.dp)
                            .background(MaterialTheme.colors.surface)
                            .fillMaxHeight()
                            .clickable { isDropdownOpen.value = !(isDropdownOpen.value) }
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .wrapContentHeight(Alignment.CenterVertically),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = labelRespuesta.value,
                                style = MaterialTheme.typography.body1,
                                color = Color.Gray
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = isDropdownOpen.value,
                        onDismissRequest = { isDropdownOpen.value = false },
                        modifier = Modifier
                            .wrapContentWidth(Alignment.CenterHorizontally)  // Ajusta el ancho al contenido
                            .padding(horizontal = 16.dp)
                    ) {
                        optionsList.forEach { opcion ->
                            DropdownMenuItem(
                                onClick = {
                                    formularioSupervisorViewModel.updateRespuesta(
                                        index,
                                        opcion.opcion,
                                        opcion.valor
                                    )

                                    labelRespuesta.value = opcion.opcion ?: ""
                                    valueRespuesta.value = opcion.valor ?: ""
                                    isDropdownOpen.value = false
                                }
                            ) {
                                opcion.opcion?.let {

                                    //Log.e("testrespuesta: ", "updatedPreguntaRespuesta: ${it}")
                                    //apiResponse.value.formulario!!.get(preguntaIndex).respuesta=it
                                    Text(text = it)
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }


            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

