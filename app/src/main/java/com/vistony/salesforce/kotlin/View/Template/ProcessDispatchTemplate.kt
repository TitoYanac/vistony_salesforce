package com.vistony.salesforce.kotlin.View.Template

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Model.*
import com.vistony.salesforce.kotlin.Utilities.ConvertStatusDispatch
import com.vistony.salesforce.kotlin.Utilities.Geolocation
import com.vistony.salesforce.kotlin.Utilities.SaveImageStatusDispatch
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Atoms.TextLabel
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.View.Molecules.ButtonViewSurface
import com.vistony.salesforce.kotlin.View.Molecules.SpinnerView
import com.vistony.salesforce.kotlin.View.components.ButtonView
import com.vistony.salesforce.kotlin.View.components.MenuGetImage

@Composable
fun ProcessDispatchTemplate(
    detailDispatchSheet: DetailDispatchSheet,
    bitMapMutable: MutableState<Bitmap?>,
    openDialogShowImage: MutableState<Boolean?>,
    context: Context,
    openDialogEditCommentary: MutableState<Boolean?>,
    tittleDialogPhoto: MutableState<String>,
){
    val statusDispatchRepository : StatusDispatchRepository = StatusDispatchRepository()
    val statusDispatchViewModel: StatusDispatchViewModel = viewModel(
        factory = StatusDispatchViewModel.StatusDispatchViewModelFactory(
            statusDispatchRepository,
            context
        )
    )
    val typeDispatchRepository: TypeDispatchRepository = TypeDispatchRepository()
    val typeDispatchViewModel: TypeDispatchViewModel = viewModel(
        factory = TypeDispatchViewModel.TypeDispatchViewModelFactory(
            SesionEntity.imei,
            context,
            typeDispatchRepository
        )
    )
    val reasonDispatchRepository: ReasonDispatchRepository = ReasonDispatchRepository()
    val reasonDispatchViewModel: ReasonDispatchViewModel = viewModel(
        factory = ReasonDispatchViewModel.ReasonDispatchViewModelFactory(
            reasonDispatchRepository, context
        )
    )
    val activity = LocalContext.current as Activity
    var reasonDispatchResponse = reasonDispatchViewModel.result_get.collectAsState()
    var reasonDispatchList: MutableList<String> = mutableListOf()
    reasonDispatchViewModel.getReasonDispatch()
    var typeDispatchResponse = typeDispatchViewModel.result_get.collectAsState()
    var typeDispatchList: MutableList<String> = mutableListOf()
    typeDispatchViewModel.getTypeDispatch(context)

    Log.e(
        "REOS",
        "BottomSheet-ProcessStatusDispatch-typeDispatchResponse.value.data!!.size: " + typeDispatchResponse.value.data!!.size
    )
    for (i in 0 until typeDispatchResponse.value.data!!.size) {
        Log.e(
            "REOS",
            "BottomSheet-ProcessStatusDispatch-typeDispatchResponse.value.data!!.get(i).typedispatch.toString(): " + typeDispatchResponse.value.data!!.get(
                i
            ).typedispatch.toString()
        )
        typeDispatchList.add(typeDispatchResponse.value.data!!.get(i).typedispatch_id+"-"+typeDispatchResponse.value.data!!.get(i).typedispatch)

    }

    for (i in 0 until reasonDispatchResponse.value.data!!.size) {
        Log.e(
            "REOS",
            "BottomSheet-ProcessStatusDispatch-reasonDispatchResponse.value.data!!.get(i).reasonDispatchResponse.toString(): " + reasonDispatchResponse.value.data!!.get(
                i
            ).reasondispatch.toString()
        )
        reasonDispatchList.add(reasonDispatchResponse.value.data!!.get(i).reasondispatch_id+"-"+reasonDispatchResponse.value.data!!.get(i).reasondispatch)
    }

    //val currentSelection = remember { mutableStateOf(typeDispatchList.find { it.toUpperCase() == typeDispatchList.toString().toUpperCase() } ?: typeDispatchList.first()) }
    val currentSelectionSpinner1 = remember {
        mutableStateOf("SELECCIONAR TIPO")
    }
    val currentSelectionSpinner2 = remember {
        mutableStateOf("SELECCIONAR MOTIVO")
    }


    Column() {
        var expanded by remember { mutableStateOf(true) }
        Surface(
            //modifier = Modifier.fillMaxHeight(),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(10.dp))
            {
                Row(
                    modifier = Modifier
                        //.padding(10.dp, 5.dp, 10.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    //Spacer(modifier = Modifier.width(30.dp))
                    TableCell(text = "PROCESO DE DESPACHO", weight = 1f,title = true, textAlign = TextAlign.Center)
                    //TableCell(text = detailDispatchSheet.nombrecliente.toString(), weight = 1f,title = true)
                    /*Text(
                        detailDispatchSheet.nombrecliente.toString(),
                        modifier = Modifier.padding(0.dp, 5.dp, 10.dp, 0.dp)
                        ,
                        color = Color.Black,
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Bold
                    )*/
                }
                AnimatedVisibility(
                    visible = expanded,
                    enter = expandIn(),
                    exit = shrinkOut()
                ) {
                    Column()
                    {
                        ProcessDispatch(
                            detailDispatchSheet,
                            bitMapMutable,
                            openDialogShowImage,
                            context,
                            statusDispatchViewModel,
                            openDialogEditCommentary,
                            tittleDialogPhoto,
                            activity,
                            currentSelectionSpinner1,
                            currentSelectionSpinner2,
                            typeDispatchList,
                            reasonDispatchList
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProcessDispatch(
    detailDispatchSheet: DetailDispatchSheet,
    bitMapMutable: MutableState<Bitmap?>,
    openDialogShowImage: MutableState<Boolean?>,
    appContext: Context,
    statusDispatchViewModel: StatusDispatchViewModel,
    openDialogEditCommentary: MutableState<Boolean?>,
    tittleDialogPhoto: MutableState<String>,
    activity:Activity,
    currentSelectionSpinner1:MutableState<String>,
    currentSelectionSpinner2:MutableState<String>,
    typeDispatchList: MutableList<String>,
    reasonDispatchList: MutableList<String>
) {

    val bitmapLocale = remember { mutableStateOf<Bitmap?>(null) }
    val bitmapDelivery = remember { mutableStateOf<Bitmap?>(null) }
    val geoLocalizacion = Geolocation(appContext)
    var latitud by remember { mutableStateOf(0.0) }
    var longitud by remember { mutableStateOf(0.0) }
    Log.e(
        "REOS",
        "BottomSheet-ProcessStatusDispatch-latitud: " + latitud
    )
    Log.e(
        "REOS",
        "BottomSheet-ProcessStatusDispatch-longitud: " + longitud
    )

    geoLocalizacion.obtenerUbicacionActual(
        onSuccess = { location ->
            latitud=location.latitude
            longitud=location.longitude
        },
        onFailure = {
            // No se pudo obtener la ubicación actual del usuario
        }
    )


    var expanded by remember { mutableStateOf(true) }


    Column() {

    }
    AnimatedVisibility(
        visible = expanded,
        enter = expandIn(),
        exit = shrinkOut()
    )
    {
        /*Card(
            modifier = Modifier, elevation = 4.dp
        )
        {*/
            Column(modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp))
            {
                Row(
                    modifier = Modifier
                        .padding(0.dp, 5.dp, 10.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.weight(0.5f)
                    ) {
                        TextLabel(text ="Cliente" )
                        Row() {
                            TableCell(text = detailDispatchSheet.nombrecliente.toString(), weight = 1f,title = true)
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(0.dp, 5.dp, 10.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.weight(0.5f)
                    ) {
                        TextLabel(text ="Entrega" )
                        Row() {
                            TableCell(text = detailDispatchSheet.entrega.toString(), weight = 1f,title = true)
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        //.padding(0.dp, 5.dp, 10.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        //horizontalAlignment = Alignment.Start,
                       // modifier = Modifier.weight(0.5f)
                    ) {

                        SpinnerView(
                            "Tipo"
                            ,typeDispatchList
                            ,currentSelectionSpinner1
                        )
                    }
                }
                if(currentSelectionSpinner1.value !in setOf("E-ENTREGADO","SELECCIONAR TIPO"))
                {
                    Row(
                        modifier = Modifier
                            //.padding(0.dp, 5.dp, 10.dp, 0.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                              //horizontalAlignment = Alignment.Start,
                            //modifier = Modifier.weight(0.5f)
                        ) {

                            SpinnerView(
                                "Motivo"
                                ,reasonDispatchList
                                ,currentSelectionSpinner2
                          )
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.Center)
                {
                    MenuGetImage(
                        "Foto Local", false, true, true,
                        appContext,
                        activity,
                        resultBitmap = { result ->
                            //bitmapState.value = result
                            bitmapLocale.value = result
                            //openDialogShowImage.value=true
                        },
                        resultopenDialogShowImage = { result ->
                            //bitmapState.value = result
                            if (bitmapLocale.value != null) {
                                openDialogShowImage.value = result
                                bitMapMutable.value = bitmapLocale.value
                                tittleDialogPhoto.value = "Foto Local"
                            } else {
                                Toast.makeText(
                                    appContext,
                                    "Debe tomar una foto para poder ser mostrada",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            //openDialogShowImage.value=true
                        },
                        detailDispatchSheet.entrega.toString(),
                        "L"
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    MenuGetImage(
                        "Foto Guia", false, true, true,
                        appContext,
                        activity,
                        resultBitmap = { result ->
                            //bitmapStateDelivery.value = result
                            bitmapDelivery.value = result
                            //openDialogShowImage.value=true
                        },
                        resultopenDialogShowImage = { result ->
                            //bitmapState.value = result
                            if (bitmapDelivery.value != null) {
                                openDialogShowImage.value = result
                                bitMapMutable.value = bitmapDelivery.value
                                tittleDialogPhoto.value = "Foto Local"
                            } else {
                                Toast.makeText(
                                    appContext,
                                    "Debe tomar una foto para poder ser mostrada",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            //openDialogShowImage.value=true
                        },
                        detailDispatchSheet.entrega.toString(),
                        "G"
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Row()
                {
                    ButtonViewSurface(
                        tittle = "Comentario",
                        description = "Comentario",
                        OnClick = { openDialogEditCommentary.value=true },
                        status = true,
                        IconActive = false,
                        context=appContext,
                        backGroundColor = Color.White,
                        textColor = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row()
                {
                    Row()
                    {
                        ButtonView(
                            description = "Cerrar",
                            OnClick = {},
                            status = true,
                            IconActive = false,
                            context=appContext,
                            backGroundColor = BlueVistony,
                            textColor = Color.White
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        ButtonView(
                            description = "Aceptar",
                            OnClick = { } ,
                            status = true,
                            IconActive = false,
                            context=appContext,
                            backGroundColor = BlueVistony,
                            textColor = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Row()
                    {
                        Box(
                            modifier = Modifier
                                //.size(200.dp)
                                .weight(1f)
                                .height(50.dp)
                                .fillMaxWidth()
                                .background(Color.Gray, RoundedCornerShape(16.dp))
                                .clickable { expanded = true }
                            ,
                            contentAlignment = Alignment.Center

                        ) {
                            Row()
                            {
                                Icon(
                                    ImageVector.vectorResource(R.drawable.ic_arrow_back_white_24dp),
                                    tint = Color.White,
                                    contentDescription = null
                                )
                                Text(
                                    text = "Anterior",
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    //modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clickable {
                                    var typeDispatch_id: String = ""
                                    var typeDispatch: String = ""
                                    var reasonDispatch_id: String = ""
                                    var reasonDispatch: String = ""
                                    val elementsTypeDispatch =
                                        currentSelectionSpinner1.value.split("-", limit = 2)
                                    for (i in 0 until elementsTypeDispatch.size) {
                                        when (i) {
                                            0 -> {
                                                typeDispatch_id = elementsTypeDispatch.get(i)
                                            }
                                            1 -> {
                                                typeDispatch = elementsTypeDispatch.get(i)
                                            }
                                        }
                                    }
                                    val elementsReasonDispatch =
                                        currentSelectionSpinner2.value.split("-", limit = 2)
                                    for (i in 0 until elementsReasonDispatch.size) {
                                        when (i) {
                                            0 -> {
                                                reasonDispatch_id =
                                                    elementsReasonDispatch.get(i)
                                            }
                                            1 -> {
                                                reasonDispatch = elementsReasonDispatch.get(i)
                                            }
                                        }
                                    }
                                    ConvertStatusDispatch(
                                        typeDispatch_id,
                                        reasonDispatch_id,
                                        "",
                                        SaveImageStatusDispatch(
                                            appContext,
                                            bitmapDelivery.value,
                                            detailDispatchSheet.entrega,
                                            "G"
                                        ).toString(),
                                        latitud.toString(),
                                        longitud.toString(),
                                        detailDispatchSheet,
                                        SaveImageStatusDispatch(
                                            appContext,
                                            bitmapLocale.value,
                                            detailDispatchSheet.entrega,
                                            "L"
                                        ).toString(),
                                        appContext,
                                        typeDispatch,
                                        reasonDispatch,
                                        statusDispatchViewModel
                                    )
                                    //statusDispatchViewModel.sendAPIStatusDispatch(appContext)
                                    //statusDispatchViewModel.sendAPIPhotoStatusDispatch(appContext)
                                }
                                .weight(1f)
                                .height(50.dp)
                                .fillMaxWidth()
                                .background(BlueVistony, RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Row()
                            {
                                Text(
                                    text = "Guardar",
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    //modifier = Modifier.align(Alignment.Center)
                                )
                                Icon(
                                    ImageVector.vectorResource(R.drawable.ic_baseline_save_white_24),
                                    tint = Color.White,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        //}
    }
}

