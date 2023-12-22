package com.vistony.salesforce.kotlin.View.Template.componentsFormularioSupervisor.Molecules

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.kotlin.Model.FormularioSupervisorViewModel
import com.vistony.salesforce.kotlin.Model.TipoSalida

@Composable
fun RenderTipoSalidaCheckboxes(formularioSupervisorViewModel: FormularioSupervisorViewModel) {
    val apiResponse = formularioSupervisorViewModel.resultAPI.collectAsState()
    val opciones: List<TipoSalida> = apiResponse.value.Data?.datosPrincipales?.tipoSalida ?: emptyList()
    Log.e("jesusdebug1", "RenderTipoSalidaCheckboxes-tipoSalida: $opciones")
    if(opciones.isNotEmpty()){
        opciones.forEachIndexed { index, tipoSalida ->
            Log.e("jesusdebug1", "RenderTipoSalidaCheckboxes-tipoSalida: ${tipoSalida.opcion}")
            Log.e("jesusdebug1", "RenderTipoSalidaCheckboxes-tipoSalida: ${tipoSalida.marcado}")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val checked = remember { mutableStateOf(tipoSalida.marcado ?: false) }
                Checkbox(
                    checked = checked.value,
                    onCheckedChange = { isChecked ->
                        checked.value = isChecked
                        formularioSupervisorViewModel.actualizarMarcadoTipoSalida(index, isChecked)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF0957C3),
                        uncheckedColor = Color.Gray,
                        checkmarkColor = Color.White,
                        disabledIndeterminateColor = Color.Gray
                    )
                )
                Text(text = tipoSalida.opcion ?: "", color = Color.Black)
            }
        }
    }else{
        Text("No hay datos")
    }
}
