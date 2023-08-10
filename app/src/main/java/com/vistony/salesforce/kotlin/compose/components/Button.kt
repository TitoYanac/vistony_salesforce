package com.vistony.salesforce.kotlin.compose.components

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.compose.theme.BlueVistony
import com.vistony.salesforce.kotlin.compose.theme.Shapes
import com.vistony.salesforce.kotlin.data.DetailDispatchSheet
import com.vistony.salesforce.kotlin.utilities.ResourceToImageBitmap

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