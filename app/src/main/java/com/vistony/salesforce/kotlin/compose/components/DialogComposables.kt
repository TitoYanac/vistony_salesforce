package com.vistony.salesforce.kotlin.compose.components

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.data.DetailDispatchSheet
import com.vistony.salesforce.kotlin.data.VisitSection
import com.vistony.salesforce.kotlin.data.VisitSectionRepository
import com.vistony.salesforce.kotlin.data.VisitSectionViewModel
import com.vistony.salesforce.kotlin.utilities.Geolocation
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun CompleteDialogContent(
    title: String,
    dialogState: MutableState<Boolean>,
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
private fun TitleAndButton(title: String, dialogState: MutableState<Boolean>) {
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
private fun BottomButtons(successButtonText: String, dialogState: MutableState<Boolean>) {
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
fun DialogShowImage(
    title: String?,
    desc: String?,
    onDismiss: () -> Unit,
    bitMap_: MutableState<Bitmap?>
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {

        Box(
            modifier = Modifier
                .height(600.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.height(90.dp))
                Box(
                    modifier = Modifier
                        .height(920.dp)
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
                        //Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = title!!.toUpperCase(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .fillMaxWidth(),
                            color = Color.Black,
                            style = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Bold
                        )
                        /*Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = desc!!,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                        )
                        Spacer(modifier = Modifier.height(24.dp))*/
                        Image(
                            //painter = painterResource(id = R.mipmap.logo),
                            bitmap = bitMap_.value!!.asImageBitmap(),
                            contentDescription = "Google Maps", // decorative
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                //Set Image size to 40 dp
                                .size(400.dp)
                                //.align(Alignment.TopCenter)
                                .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(){
                            /*Button(
                                onClick =
                                {

                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    text = "Aceptar",
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))*/
                            Button(
                                onClick = onDismiss,
                                //colors = ButtonDefaults.buttonColors(Colors = MaterialTheme.colors.primary),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    text = "Cerrar",
                                    color = Color.White
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

