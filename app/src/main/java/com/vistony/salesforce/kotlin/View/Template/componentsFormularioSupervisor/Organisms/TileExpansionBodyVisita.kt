package com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Organisms

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.kotlin.Model.FormularioSupervisorViewModel
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Atoms.CustomOutlinedNumericField
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Atoms.CustomOutlinedNumericFieldNormal
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Atoms.CustomOutlinedTextField
import com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Molecules.TimePickerRow


@Composable
fun TileExpansionBodyVisita(formularioSupervisorViewModel: FormularioSupervisorViewModel) {
    val resultAPI         = formularioSupervisorViewModel.resultAPI.collectAsState()
    if(resultAPI.value.Data == null) return
    val apiResponse       = resultAPI.value.Data!!
    if(apiResponse.datosVisita == null) return
    val datosVisita  = apiResponse.datosVisita!!

    val horaInicio          : MutableState<String>     = remember { mutableStateOf(datosVisita.horaInicio?:"00:00 AM") }
    val horaFin             : MutableState<String>     = remember { mutableStateOf(datosVisita.horaFin?:"00:00 PM") }
    val observacionZona     : MutableState<String>     = remember { mutableStateOf(datosVisita.observacionZona?:"") }
    val clientesNuevos      : MutableState<String>     = remember { mutableStateOf(datosVisita.clientesNuevos?:"") }
    val clientesEmpadronados: MutableState<String>     = remember { mutableStateOf(datosVisita.clientesEmpadronados?:"") }
    val zona                : MutableState<String>     = remember { mutableStateOf(datosVisita.zona?:"") }
    val resumen = datosVisita.resumen

    Card(modifier = Modifier
        .background(color = Color.White)
        .padding(horizontal = 8.dp, vertical = 12.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {

            CustomOutlinedTextField(
                labelText = "Zona",
                value = zona.value,
                maxLength = 99,
                onValueChange = { newValue ->
                    zona.value = newValue
                    formularioSupervisorViewModel.updateZona(zona.value)
                }
            )
            CustomOutlinedTextField(
                labelText = "Comentarios (Opcional)",
                value = observacionZona.value,
                maxLength = 254,
                onValueChange = { newValue ->
                    observacionZona.value = newValue
                    formularioSupervisorViewModel.updateObservacionZona(observacionZona.value)
                }
            )

            TimePickerRow("Hora de inicio", horaInicio.value) { newValue ->
                horaInicio.value = newValue
                formularioSupervisorViewModel.updateHoraInicio(horaInicio.value)
            }
            TimePickerRow("Hora fin", horaFin.value) { newValue ->
                Log.e("test", "horaFin: $horaFin")
                horaFin.value = newValue
                formularioSupervisorViewModel.updateHoraFin(horaFin.value)
            }

            CustomOutlinedNumericField(
                labelText = "Clientes nuevos:",
                value = clientesNuevos.value,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newValue ->
                    clientesNuevos.value = newValue
                    formularioSupervisorViewModel.updateClientesNuevos(clientesNuevos.value)
                }
            )
            CustomOutlinedNumericField(
                labelText = "Clientes empadronados:",
                value = clientesEmpadronados.value,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newValue ->
                    clientesEmpadronados.value = newValue
                    formularioSupervisorViewModel.updateClientesEmpadronados(clientesEmpadronados.value)
                }
            )

            Row(
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                    .fillMaxWidth()
            ) {
                TableCell(
                    "Resumen de visitas",
                    textAlign = TextAlign.Center,
                    weight = 1f,
                    title = true
                )
            }

            resumen.forEachIndexed { index, elemento ->
                val title = elemento.tipo ?: "Sin tipo"
                val cantidad = elemento.cantidad ?: "0"
                val monto = elemento.monto ?: "0.00"
                Row(
                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    TableCell(
                        title.uppercase(),
                        textAlign = TextAlign.Left,
                        weight = 1f,
                        title = false
                    )
                }
                Row() {
                    Column(modifier = Modifier.weight(1f)) {
                        CustomOutlinedNumericField(
                            labelText = cantidad.uppercase(),
                            status = false,
                            value = cantidad,
                            onValueChange = {}
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {

                        CustomOutlinedNumericFieldNormal(
                            status = false,
                            labelText = monto.uppercase(),
                            value = monto,
                            onValueChange = {},
                            keyboardType = KeyboardType.Decimal
                        )

                    }
                }
            }

            val comentario = remember { mutableStateOf(apiResponse.comentario ?: "") }
            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                labelText = "Comentarios del Trabajo de Campo Realizado",
                value = comentario.value,
                maxLength = 254,
                onValueChange = { newValue ->
                    comentario.value = newValue
                    formularioSupervisorViewModel.updateComentario(comentario.value)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}



