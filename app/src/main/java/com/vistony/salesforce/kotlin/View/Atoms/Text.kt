package com.vistony.salesforce.kotlin.View.Atoms

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.vistony.salesforce.kotlin.View.Atoms.theme.Typography

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    title:Boolean=false,
    color: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Center,
    fontSise: TextUnit = 15.sp
) {
        if (title) {
            Text(
                text = text,
                modifier =
                Modifier
                    .weight(weight)
                    .padding(2.dp),
                fontSize = fontSise,
                //fontWeight = FontWeight.Bold,
                color = color,
                textAlign = textAlign,
                style = Typography.h5,
            )
        } else {
            Text(
                text = text,
                modifier = Modifier
                    .weight(weight)
                    .padding(2.dp),
                color = color,
                fontSize = fontSise,
                textAlign = textAlign, style = Typography.body1
            )
        }
}

@Composable
fun TableCellBody(
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Left
) {
        Text(
            text = text,
            //modifier = Modifier.padding(2.dp),
            color = color,
            textAlign = textAlign, style = Typography.body1
        )
}

@Composable
fun TextLabel(
    text: String,
    textAlign: TextAlign = TextAlign.Left
) {
    Text(
        text = text,
        //modifier = Modifier.padding(2.dp),
        color = Color.Gray,
        textAlign = textAlign,
        fontFamily = FontFamily.Default,
        //fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
}

@Composable
fun Cell(
    text: String,
    title:Boolean=false,
    color: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Center,
    fontSise: TextUnit = 15.sp
) {
    if (title) {
        Text(
            text = text,
            /*modifier =
            Modifier
                //.weight(weight)
                .padding(2.dp),*/
            fontSize = fontSise,
            //fontWeight = FontWeight.Bold,
            color = color,
            textAlign = textAlign,
            style = Typography.h5,
        )
    } else {
        Text(
            text = text,
            /*modifier = Modifier
                //.weight(weight)
                .padding(2.dp),*/
            color = color,
            fontSize = fontSise,
            textAlign = textAlign, style = Typography.body1
        )
    }
}
