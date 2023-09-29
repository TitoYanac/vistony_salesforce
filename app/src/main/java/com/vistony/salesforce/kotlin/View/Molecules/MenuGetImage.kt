package com.vistony.salesforce.kotlin.View.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.Controller.Adapters.StatusDispatchDialog
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Utilities.ResourceToImageBitmap
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import com.vistony.salesforce.kotlin.Utilities.*
import com.vistony.salesforce.kotlin.View.Atoms.TextLabel

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
delivery: String,
type: String
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
       // modifier = Modifier.selectableGroup()
    ) {
        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
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
                //.fillMaxWidth()
                //.padding(0.dp, 0.dp, 10.dp, 0.dp)
        ) {
            Column() {
                Row(modifier = Modifier
                    .padding(20.dp,10.dp)
                    .align(Alignment.CenterHorizontally)
                ) {

                    if(attachStatus)
                    {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier
                            //.weight(1f)
                            .clickable { })
                        {
                            ItemGetImage(
                                ResourceToImageBitmap(R.drawable.ic_baseline_attach_file_24),
                                "Adjuntar"
                            )
                        }
                    }
                    if(captureStatus)
                    {
                        //Spacer(modifier = Modifier.width(10.dp))
                        Box(contentAlignment = Alignment.Center,
                            //modifier = Modifier.weight(1f)
                        )
                        {
                            CaptureImage(
                                resultBitmap = { result ->
                                    resultBitmap(result)
                                },
                                context,
                                activity,
                                delivery,
                                type
                            )

                        }
                    }
                    if(seeStatus)
                    {
                        Spacer(modifier = Modifier.width(40.dp))
                        Box(contentAlignment = Alignment.Center, modifier = Modifier
                            //.weight(1f)
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
    Box(
        contentAlignment = Alignment.Center,
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
    context: Context,
    activity: Activity,
    tittle:String,
    type: String,
    resultFile: (File) -> Unit,
) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    // Crea el File
    // Crea el File
    var photoFile: File? = null

    photoFile = createImageFile1(tittle+ "_" + getDate(), type,activity)

    if (photoFile != null) {
        Log.e("REOS", "statusDispatchRepository-->FotoLocal-->photoFile != null")
        var photoURI: Uri? = null
        when (BuildConfig.FLAVOR) {
            "ecuador" -> photoURI = FileProvider.getUriForFile(
                context,
                "com.vistony.salesforce.ecuador",
                photoFile
            )
            "peru", "marruecos" -> photoURI =
                FileProvider.getUriForFile(context, "com.vistony.salesforce.peru", photoFile)
            "espania" -> photoURI = FileProvider.getUriForFile(
                context,
                "com.vistony.salesforce.espania",
                photoFile
            )
            "perurofalab" -> photoURI = FileProvider.getUriForFile(
                context,
                "com.vistony.salesforce.perurofalab",
                photoFile
            )
            "bolivia" -> photoURI = FileProvider.getUriForFile(
                context,
                "com.vistony.salesforce.bolivia",
                photoFile
            )
            "paraguay" -> photoURI = FileProvider.getUriForFile(
                context,
                "com.vistony.salesforce.paraguay",
                photoFile
            )
            "chile" -> photoURI =
                FileProvider.getUriForFile(context, "com.vistony.salesforce.chile", photoFile)
        }
        resultFile(photoFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.clickable {
                startForResult.launch(intent)
            }
        )
        {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Image",
                    modifier = Modifier
                )
                TextLabel(text = item)
            }
        }
    }
}

