package com.vistony.salesforce.kotlin.View.Molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.vistony.salesforce.kotlin.View.Atoms.TextLabel
import com.vistony.salesforce.kotlin.View.components.Spinner

@Composable
fun SpinnerView(
    tittle:String
    ,list: List<String>
    ,currentSelected: MutableState<String>
)
{
    Column() {
        TextLabel(text=tittle)
        Spinner(
            items = list,
            selectedItem = currentSelected.value,
            onItemSelected = { item ->
                currentSelected.value = item
            }
        )
    }
}