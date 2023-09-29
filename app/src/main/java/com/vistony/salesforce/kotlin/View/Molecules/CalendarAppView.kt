package com.vistony.salesforce.kotlin.View.Molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.vistony.salesforce.kotlin.View.Atoms.CalendarApp
import com.vistony.salesforce.kotlin.View.Atoms.TextLabel
import com.vistony.salesforce.kotlin.View.components.Spinner

@Composable
fun CalendarAppView(
    tittle:String,
    DateApp:MutableState<String>
)
{
    Column() {
        TextLabel(text=tittle)
        CalendarApp(
            DateApp = DateApp
        )
    }
}