package com.vistony.salesforce.kotlin.View.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.View.Atoms.theme.RedVistony

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Editext(
    status: Boolean,
    text: MutableState<String>,
    placeholder:String,
    label:String,
    painter:Painter,
    keyboardType:KeyboardType
){
    val keyboardController = LocalSoftwareKeyboardController.current
    val maxCharacters = 254 // Establece el límite máximo de caracteres

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(10.dp).fillMaxWidth()
    ) {
        OutlinedTextField(
            enabled= status,
            singleLine=false,
            value = text.value,
            onValueChange =
            {
                if (it.length <= maxCharacters)
                {
                    text.value = it
                }
            },
            placeholder = {
                Text(text = placeholder)
            },
            label = { Text(label) },
            trailingIcon = { Icon(painter = painter
            //painterResource(id = R.drawable.ic_insert_comment_black_24dp)
                , contentDescription = null, tint = RedVistony) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType,imeAction = ImeAction.Go ),
            keyboardActions = KeyboardActions(
                onGo = {
                    keyboardController?.hide()
                       },
            ),
                modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "${text.value.length}/$maxCharacters",
            style = MaterialTheme.typography.caption,
            color = if (text.value.length > maxCharacters) Color.Red else Color.Black,
            modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp,0.dp)
        )
    }
}