package com.vistony.salesforce.kotlin.View.Molecules

import android.util.Log
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
    Log.e("REOS", "SpinnerView-SpinnerView-tittle: " +tittle)
    Log.e("REOS", "SpinnerView-SpinnerView-currentSelected: " +currentSelected.value)
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