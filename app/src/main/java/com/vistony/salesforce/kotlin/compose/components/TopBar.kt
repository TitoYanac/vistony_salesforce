package com.vistony.salesforce.kotlin.compose.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.kotlin.compose.theme.BlueVistony

@Composable
fun TopBarTitle(title: String = "") {
    Column{
        TopAppBar(
            modifier= Modifier.background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BlueVistony,
                        BlueVistony
                    )
                )),
            title = {
                Text(
                    text = title,
                    color= Color.White
                )
            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )
    }
}


