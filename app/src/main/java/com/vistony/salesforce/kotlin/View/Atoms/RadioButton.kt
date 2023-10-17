package com.vistony.salesforce.kotlin.View.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.kotlin.View.Atoms.theme.RedVistony

@Composable
fun RadioBtn(
    list: List<String>
    ,currentSelected: MutableState<String>
){

    Log.e(
        "REOS",
        "BottomSheet-RadioBtn-currentSelection.value" +currentSelected.value
    )
    RadioGroup(
        modifier = Modifier.fillMaxWidth(),
        items = list,
        selection = currentSelected.value,
        onItemClick = { clickedItem ->
            currentSelected.value = clickedItem
        },
        true
    )
}


@Composable
private fun RadioGroup(
    modifier: Modifier,
    items: List<String>,
    selection: String,
    onItemClick: ((String) -> Unit),
    statusEnable:Boolean
) {
    Column(modifier = modifier) {
        items.forEach { item ->
            LabelledRadioButton(
                modifier = Modifier.fillMaxWidth(),
                label = item,
                selected = item == selection,
                onClick = {
                    onItemClick(item)
                },
                statusEnable
            )
        }
    }
}


@Composable
private fun LabelledRadioButton(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: (() -> Unit)?,
    enabled: Boolean,
    colors: RadioButtonColors = RadioButtonDefaults.colors()
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            enabled = enabled,
            //colors = colors
            colors = RadioButtonDefaults.colors(
                selectedColor = RedVistony, // Color cuando está seleccionado
                unselectedColor = Color.Gray, // Color cuando no está seleccionado
                disabledColor = Color.Gray, // Color cuando está deshabilitado
                //have = Color.Green // Color al pasar el ratón por encima
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = label,
            fontWeight =
            if(selected==true)
            {
                FontWeight.Bold
            }else {
                FontWeight.Normal
            },
            style = MaterialTheme.typography.body1.merge(),
            modifier = Modifier.padding(start = 3.dp),
        )
    }
}