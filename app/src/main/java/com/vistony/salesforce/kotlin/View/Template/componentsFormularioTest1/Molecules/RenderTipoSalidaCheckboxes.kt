package com.vistony.salesforce.kotlin.View.Template.componentsFormularioTest1.Molecules

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.vistony.salesforce.kotlin.View.Pages.TipoSalida

@Composable
fun RenderTipoSalidaCheckboxes(
    tipoSalidaList: List<TipoSalida>,
    onTipoSalidaChange: (Int, TipoSalida) -> Unit
) {
    tipoSalidaList.forEachIndexed { index, tipoSalida ->
        var estadoCheck by remember { mutableStateOf(tipoSalida.marcado) }
        tipoSalida.opcion?.let {
            estadoCheck?.let { it1 ->
                CheckboxLabel(it1, it) { isChecked ->
                    estadoCheck = isChecked

                    val updatedTipoSalida = tipoSalida.copy(marcado = isChecked)
                    onTipoSalidaChange(index, updatedTipoSalida)
                }
            }
        }
    }
}
