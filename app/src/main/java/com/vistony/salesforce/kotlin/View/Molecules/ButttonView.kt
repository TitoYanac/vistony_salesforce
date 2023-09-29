package com.vistony.salesforce.kotlin.View.Molecules

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.vistony.salesforce.kotlin.View.Atoms.TextLabel
import com.vistony.salesforce.kotlin.View.components.ButtonSurface

@Composable
fun ButtonViewSurface(
    tittle:String
    ,description:String
    , OnClick:() ->Unit
    , status: Boolean=false
    , IconActive:Boolean=false
    , context: Context
    , backGroundColor: Color = Color.Unspecified
    , textColor: Color = Color.Unspecified,
) {

    Column() {
        TextLabel(text=tittle)
        Row(){
            ButtonSurface(
                description
                ,OnClick
                ,status
                ,IconActive
                ,context
                ,backGroundColor
                ,textColor
            )
        }
    }
}