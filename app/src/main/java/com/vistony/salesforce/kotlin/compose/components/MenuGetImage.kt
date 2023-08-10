package com.vistony.salesforce.kotlin.compose.components

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.utilities.ResourceToImageBitmap

@Composable
fun MenuGetImage(
description:String,
attachStatus:Boolean,
captureStatus:Boolean,
seeStatus:Boolean,
context: Context,
activity: Activity,
resultBitmap: (Bitmap) -> Unit,
//openDialogShowImage: MutableState<Boolean?>,
resultopenDialogShowImage: (Boolean) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.selectableGroup()
    ) {
        Row(modifier = Modifier
            .align(Alignment.Start)
        ) {
            Text(text = description, color = Color.Gray)
        }
        Surface(
            shape = RoundedCornerShape(4.dp),
            elevation = 2.dp,
            modifier = Modifier
                .selectable(
                    selected = expanded,
                    onClick = { expanded = !expanded }
                )
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 10.dp, 0.dp)
        ) {
            Column() {
                Row(modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
                ) {

                    if(attachStatus)
                    {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f).clickable {  })
                        {
                            ItemGetImage(
                                ResourceToImageBitmap(R.drawable.ic_baseline_attach_file_24),
                                "Adjuntar"
                            )
                        }
                    }
                    if(captureStatus)
                    {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f))
                        {
                            CaptureImage(
                                resultBitmap = { result ->
                                    resultBitmap(result)
                                },
                                context,
                                activity
                            )

                        }
                    }
                    if(seeStatus)
                    {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f)
                            .clickable {
                                //openDialogShowImage.value=true
                                resultopenDialogShowImage(true)
                            })
                        {
                            ItemGetImage(
                                ResourceToImageBitmap(R.drawable.ic_baseline_visibility_24_black),
                                "Ver"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemGetImage(
    imageBitmap:ImageBitmap,
    item:String
){
    Box(contentAlignment = Alignment.Center,
        //modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
    )
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                bitmap = imageBitmap,
                contentDescription = "Image",
                modifier = Modifier
            )
            Text(text = item, color = Color.Gray)
        }
    }
}

@Composable
fun ItemGetImageCapture(
    imageBitmap:ImageBitmap,
    item:String,
    startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
){
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.clickable {
            startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
    )
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                bitmap = imageBitmap,
                contentDescription = "Image",
                modifier = Modifier
            )
            Text(text = item, color = Color.Gray)
        }
    }
}
