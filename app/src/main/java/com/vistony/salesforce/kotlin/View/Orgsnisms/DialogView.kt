package com.vistony.salesforce.kotlin.View.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Utilities.sendSMS
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.View.Atoms.theme.RedVistony


@Composable
fun CompleteDialogContent(
    title: String,
    dialogState: MutableState<Boolean?>,
    successButtonText: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth(1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TitleAndButton(title, dialogState)
            AddBody(content)
            BottomButtons(successButtonText, dialogState = dialogState)
        }
    }
}

@Composable
private fun TitleAndButton(title: String, dialogState: MutableState<Boolean?>) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 24.sp)
            IconButton(modifier = Modifier.then(Modifier.size(24.dp)),
                onClick = {
                    dialogState.value = false
                }) {
                Icon(
                    Icons.Filled.Close,
                    "contentDescription"
                )
            }
        }
        Divider(color = Color.DarkGray, thickness = 1.dp)
    }
}

@Composable
private fun BottomButtons(successButtonText: String, dialogState: MutableState<Boolean?>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxWidth(1f)
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(100.dp)
                .padding(end = 5.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Cancel", fontSize = 20.sp)
        }
        Button(
            onClick = {
                dialogState.value = false
            },
            modifier = Modifier.width(100.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = successButtonText, fontSize = 20.sp)
        }

    }
}

@Composable
private fun AddBody(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .padding(20.dp)
    ) {
        content()
    }
}

@Composable
fun DialogView(
    tittle: String,
    subtittle:String,
    onClickCancel: () -> Unit,
    onClickAccept: () -> Unit,
    statusButtonAccept:Boolean,
    statusButtonIcon:Boolean,
    context: Context,
    content: @Composable () -> Unit,
) {
    Log.e("REOS", "DialogView-DialogView-tittle: " +tittle)
    Log.e("REOS", "DialogView-DialogView-subtittle: " +subtittle)
    Dialog(
        onDismissRequest = onClickCancel
    ) {

        Box(
            modifier = Modifier
                //.height(400.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.height(90.dp))
                Box(
                    modifier = Modifier
                        //.height(920.dp)
                        .background(
                            //color = MaterialTheme.colorScheme.onPrimary,
                            color = MaterialTheme.colors.onPrimary,
                            shape = RoundedCornerShape(25.dp, 10.dp, 25.dp, 10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Row() {
                            TableCell(text= tittle!!,color=Color.Black, title = true, weight = 1f, textAlign = TextAlign.Center)
                        }
                        if(!subtittle.equals(""))
                        {
                            Spacer(modifier = Modifier.height(5.dp))
                            Row() {
                                TableCell(
                                    text = subtittle!!,
                                    color = Color.Gray,
                                    title = false,
                                    weight = 1f,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        content.invoke()
                        Spacer(modifier = Modifier.height(4.dp))
                        Row() {
                            ButtonView(
                                description = "Cerrar",
                                OnClick = onClickCancel,
                                status = true,
                                IconActive = statusButtonIcon,
                                context=context,
                                backGroundColor = RedVistony,
                                textColor = Color.White
                            )
                            if(statusButtonAccept)
                            {
                                Spacer(modifier = Modifier.width(10.dp))
                                ButtonView(
                                    description = "Aceptar",
                                    OnClick = onClickAccept,
                                    status = true,
                                    IconActive = statusButtonIcon,
                                    context=context,
                                    backGroundColor = RedVistony,
                                    textColor = Color.White
                                )
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .clip(
                        RoundedCornerShape(
                            topEndPercent = 50,
                            bottomStartPercent = 50, topStartPercent = 50, bottomEndPercent = 50
                        )
                    )
                    .background(
                        Color.White
                    )
                    .align(Alignment.TopCenter)
            )

            Image(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = "Google Maps", // decorative
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    //Set Image size to 40 dp
                    .size(120.dp)
                    .align(Alignment.TopCenter)
                    .padding(0.dp, 20.dp, 0.dp, 0.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PruebaDialog(
    title: String?="Message",
    desc: String?="Your Message",
    onDismiss: () -> Unit,
    DialogOpen: Boolean
) {
    val contextForToast = LocalContext.current.applicationContext

    var dialogOpen by remember {
        //mutableStateOf(false)
        mutableStateOf(DialogOpen)
    }
    if (dialogOpen)
    {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {

            Box(
                modifier = Modifier
                    .height(460.dp)
            )
            {
                Column(

                )
                {
                    Spacer(modifier = Modifier.height(150.dp))

                    Box(
                        modifier =
                        Modifier.background(
                            //color = MaterialTheme.colorScheme.onPrimary,
                            color = MaterialTheme.colors.onPrimary,
                            shape = RoundedCornerShape(25.dp, 10.dp, 25.dp, 10.dp)
                        )

                    )
                    {
                        //Column
                        Column(
                            modifier = Modifier.align(Alignment.Center)
                        )
                        {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier
                                    //.fillMaxHeight(1f)
                                    .fillMaxWidth(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Box() {
                                    Text(
                                        text = "MAPA DE NAVEGACION",
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Black
                                        )
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    //.fillMaxHeight(1f)
                                    .fillMaxWidth(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Box() {
                                    Text(
                                        text = "Elija el mapa de navegacion:",
                                        fontSize = 18.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier
                                    //.fillMaxHeight(1f)
                                    .fillMaxWidth(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.SpaceBetween


                                ) {
                                    Image(
                                        painter = painterResource(id = R.mipmap.waze_logo),
                                        contentDescription = "Waze", // decorative
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            //Set Image size to 40 dp
                                            .size(80.dp)
                                            .align(Alignment.CenterHorizontally)
                                            //Clip Image to be shaped as a circle
                                            //.clip(CircleShape)
                                            //.border(1.5.dp, MaterialTheme.colors.secondary, CircleShape
                                            //)
                                            .clip(
                                                RoundedCornerShape(
                                                    topEndPercent = 50,
                                                    bottomStartPercent = 50,
                                                    topStartPercent = 50,
                                                    bottomEndPercent = 50
                                                )
                                            )
                                            .clickable(
                                                enabled = true,
                                                onClick = {
                                                    val openURL =
                                                        Intent(Intent.ACTION_VIEW)
                                                    /*openURL.data = Uri.parse(
                                                        //"https://waze.com/ul?q=-11.863631,-77.054802&ll=-11.857904,-77.028424&navigate=yes"
                                                        "https://waze.com/ul?q=" + latitude + "," + longitude + "&ll=" + latitudeClient + "," + longitudeClient + "&navigate=yes"
                                                    )

                                                    Log.e(
                                                        "REOS",
                                                        "SomeScreen-DialogScreenOne()-url.waze:" + "https://waze.com/ul?q=" + latitude + "," + longitude + "&ll=" + latitudeClient + "," + longitudeClient + "&navigate=yes"
                                                    )*/
                                                    //var menuview:MenuView =MenuView()
                                                    /*startActivity(contextForToast, openURL,
                                                            Bundle()
                                                        )*/
                                                    //menuview.startActivity(openURL)
                                                    //Fragment.startActivity(openURL)
                                                    openURL.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                    contextForToast.startActivity(openURL)
                                                    Toast
                                                        .makeText(
                                                            contextForToast,
                                                            "Waze",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                })


                                    )
                                    Text(
                                        text = "Waze",
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 18.sp,

                                            )
                                    )


                                }
                                Column(
                                    modifier = Modifier.weight(1f),


                                    ) {
                                    Image(
                                        painter = painterResource(id = R.mipmap.google_map_icon_412x412),
                                        contentDescription = "Google Maps", // decorative
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier

                                            //Set Image size to 40 dp
                                            .size(80.dp)
                                            .align(Alignment.CenterHorizontally)
                                            .clip(
                                                RoundedCornerShape(
                                                    topEndPercent = 50,
                                                    bottomStartPercent = 50,
                                                    topStartPercent = 50,
                                                    bottomEndPercent = 50
                                                )
                                            )
                                            .clickable(
                                                enabled = true,
                                                onClick = {
                                                   /* val gmmIntentUri =
                                                        //Uri.parse("google.navigation:q=-11.857904,-77.028424&")
                                                        Uri.parse("google.navigation:q=" + latitudeClient + "," + longitudeClient)
                                                    Log.e(
                                                        "REOS",
                                                        "SomeScreen-DialogScreenOne()-url.googlemaps:" + "google.navigation:q=" + latitudeClient + "," + longitudeClient
                                                    )
                                                    val mapIntent =
                                                        Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                                    mapIntent.setPackage("com.google.android.apps.maps")
                                                    //var menuView:MenuView= MenuView()
                                                    //menuView.startActivity(mapIntent)
                                                    mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                    //startActivity(contextForToast, mapIntent,Bundle())
                                                    contextForToast.startActivity(mapIntent)*/
                                                })
                                    )


                                    Text(
                                        text = "Google Maps",
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 18.sp,

                                            )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier
                                    //.fillMaxHeight(1f)
                                    .fillMaxWidth(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {

                                Button(
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                                    onClick = {
                                        dialogOpen=false
                                        onDismiss()
                                    }) {
                                    Text(text = "Cerrar", fontSize = 18.sp, color = Color.White)
                                }

                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                        //Column
                    }
                }
                //////////////Box
                HeaderImage(
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.TopCenter)
                        .clickable {},
                    "Geolocation"
                )

            }
        }
    }
}

