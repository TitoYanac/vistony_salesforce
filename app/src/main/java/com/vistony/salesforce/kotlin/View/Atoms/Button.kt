package com.vistony.salesforce.kotlin.View.components

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Atoms.TextLabel
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.View.Atoms.theme.RedVistony
import com.vistony.salesforce.kotlin.View.Atoms.theme.Typography

@Composable
fun buttonGetStatus(
    status:Boolean,
    description:String,
    imageVector: ImageVector,
    //resultStatus(status:Boolean) --> Unit
    ResultStatus: (status:Boolean) -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
            Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .clickable {
                    ResultStatus(status.not())
                }
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(200.dp, 50.dp)
                        .background(BlueVistony, CircleShape)
                    , contentAlignment = Alignment.Center
                ) {
                    Row() {
                        Text(
                            text = description,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            imageVector = imageVector,
                            tint = Color.White,
                            contentDescription = null
                        )
                    }

                }

            }
        }

    }
}


@Composable
fun buttonImage(
    //status:Boolean,
    description:String,
    //imageVector: ImageVector,
    //resultStatus(status:Boolean) --> Unit
    //ResultStatus: (status:Boolean) -> Unit
    startForResult: ManagedActivityResultLauncher<Intent,ActivityResult>,
    imageBitmap:MutableState<ImageBitmap?>
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier
        //    .fillMaxWidth()
            .padding(10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
            Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .clickable {
                    // ResultStatus(status.not())
                    startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                }
        )

        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = description, color = Color.Gray)
                Box(
                    modifier = Modifier
                        //.size(200.dp, 50.dp)
                        .background(BlueVistony, CircleShape)
                    , contentAlignment = Alignment.Center
                ) {
                    Column() {
                        if(imageBitmap.value!=null)
                        {
                            Image(
                                //bitmap = ResourceToImageBitmap(R.drawable.ic_add_a_photo_black_24dp),
                                bitmap = imageBitmap.value!!,
                                contentDescription = "Image",
                                modifier = Modifier
                                    //.fillMaxWidth()
                                    .width(150.dp)
                                    .height(100.dp)//.size(400.dp,100.dp)
                                    .padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun RowScope.ButtonView(
    description:String
    ,OnClick:() ->Unit
    ,status: Boolean=false
    ,IconActive:Boolean=false
    ,context: Context
    ,backGroundColor: Color = Color.Unspecified
    ,textColor: Color = Color.Unspecified,
) {
        Box(
            modifier = Modifier
                //.size(200.dp)
                .weight(1f)
                .height(50.dp)
                //.fillMaxWidth()

                .background(
                    backGroundColor, RoundedCornerShape(4.dp)
                )
                .clickable {
                    if (status) {
                        OnClick()
                    } else {
                        Toast
                            .makeText(
                                context,
                                "El Boton se encuenta deshabilitado",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }
                },
            contentAlignment = Alignment.Center,


        ) {
            Row()
            {
                if (IconActive) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.ic_arrow_back_white_24dp),
                        tint = Color.White,
                        contentDescription = null
                    )
                }
                TableCell(
                    text = description,
                    color = textColor,
                    title = true,
                    weight = 1f,
                    textAlign = TextAlign.Center
                )
            }
        }
}

@Composable
fun ButtonSurface(
    description:String
    ,OnClick:() ->Unit
    ,status: Boolean=false
    ,IconActive:Boolean=false
    ,context: Context
    ,backGroundColor: Color = Color.Unspecified
    ,textColor: Color = Color.Unspecified
    ,height: Dp = 50.dp // Altura predeterminada de 50 DP

) {
    Column(
        modifier = Modifier.selectableGroup()
    ) {
        Surface(
            border = BorderStroke(2.dp, RedVistony), // Agregar un borde rojo
            contentColor= backGroundColor,
            color = backGroundColor,
            shape = RoundedCornerShape(4.dp),
            elevation = 2.dp,
            modifier = Modifier
                .selectable(
                    selected = true,
                    onClick =
                    {
                        if (status) {
                            OnClick()
                        } else {
                            Toast
                                .makeText(
                                    context,
                                    "El Boton se encuenta deshabilitado",
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        }
                    }
                )
                .height(height)
        ) {
            Row(modifier = Modifier
                .padding(10.dp)
                //.background(backGroundColor)
            )
            {
                if (IconActive) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.ic_arrow_back_white_24dp),
                        tint = Color.White,
                        contentDescription = null
                    )
                }
                TableCell(
                    text = description,
                    color = textColor,
                    title = true,
                    weight = 1f,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ButtonCircle(
    OnClick:() ->Unit
    ,size: DpSize = DpSize(40.dp,40.dp)
    ,color: Color= RedVistony
    ,roundedCornerShape: RoundedCornerShape= CircleShape
    ,content: @Composable () -> Unit
){
    Box(
        modifier = Modifier
            .size(size)
            .background(color, roundedCornerShape)
            .clickable { OnClick() }
        ,
        contentAlignment = Alignment.Center
    ) {
        content.invoke()
    }
}