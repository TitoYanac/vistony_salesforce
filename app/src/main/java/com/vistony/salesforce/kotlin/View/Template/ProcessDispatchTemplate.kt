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
import com.vistony.salesforce.kotlin.View.Atoms.theme.RedVistony
import com.vistony.salesforce.kotlin.View.Molecules.ButtonViewSurface
import com.vistony.salesforce.kotlin.View.Molecules.SpinnerView
import com.vistony.salesforce.kotlin.View.components.ButtonView
import com.vistony.salesforce.kotlin.View.components.MenuGetImage

@Composable
fun ProcessDispatchTemplate(
    detailDispatchSheet: DetailDispatchSheetUI,
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
            SesionEntity.imei, reasonDispatchRepository, context
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
    /*val currentSelectionSpinner1 :MutableState<String> = remember {
        mutableStateOf("SELECCIONAR TIPO")
    }
    val currentSelectionSpinner2 :MutableState<String> = remember {
        mutableStateOf("SELECCIONAR MOTIVO")
    }*/


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
                    Spacer(modifier = Modifier.height(10.dp))
                    TableCell(text = "PROCESO DE DESPACHO", weight = 1f,title = true, textAlign = TextAlign.Center)
                }
                Divider()
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
                            //currentSelectionSpinner1,
                            //currentSelectionSpinner2,
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
    detailDispatchSheet: DetailDispatchSheetUI,
    bitMapMutable: MutableState<Bitmap?>,
    openDialogShowImage: MutableState<Boolean?>,
    appContext: Context,
    statusDispatchViewModel: StatusDispatchViewModel,
    openDialogEditCommentary: MutableState<Boolean?>,
    tittleDialogPhoto: MutableState<String>,
    activity:Activity,
    //currentSelectionSpinner1:MutableState<String>,
    //currentSelectionSpinner2:MutableState<String>,
    typeDispatchList: MutableList<String>,
    reasonDispatchList: MutableList<String>
) {

    val bitmapLocale = remember { mutableStateOf<Bitmap?>(null) }
    val bitmapDelivery = remember { mutableStateOf<Bitmap?>(null) }
    val geoLocalizacion = Geolocation(appContext)
    var latitud by remember { mutableStateOf(0.0) }
    var longitud by remember { mutableStateOf(0.0) }
    var errorsaveby: String by remember { mutableStateOf<String>("") }
    var statusbuttonsave: Boolean by remember { mutableStateOf<Boolean>(true) }
    var colorbackgroundbuttonsave: Color by remember { mutableStateOf<Color>(RedVistony) }
    val currentSelectionSpinner1 :MutableState<String> = remember {
        mutableStateOf("")
    }
    val currentSelectionSpinner2 :MutableState<String> = remember {
        mutableStateOf("")
    }

    LaunchedEffect(typeDispatchList) {
        // Este bloque se ejecutará cada vez que typeDispatchList cambie
        currentSelectionSpinner1.value = "SELECCIONAR TIPO"
        currentSelectionSpinner2.value = "SELECCIONAR MOTIVO"
    }



    //var typeDispatchList: MutableList<String> = mutableListOf()
    Log.e("REOS", "BottomSheet-ProcessStatusDispatch-latitud: " + latitud)
    Log.e("REOS", "BottomSheet-ProcessStatusDispatch-longitud: " + longitud)
    Log.e("REOS", "BottomSheet-ProcessStatusDispatch-currentSelectionSpinner1: " + currentSelectionSpinner1.value)
    Log.e("REOS", "BottomSheet-ProcessStatusDispatch-currentSelectionSpinner2: " + currentSelectionSpinner2.value)



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
                            "Tipo (*)"
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
                                "Motivo (*)"
                                ,reasonDispatchList
                                ,currentSelectionSpinner2
                          )
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.Center)
                {
                    MenuGetImage(
                        "Foto Local (*)", false, true, true,
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
                        "Foto Guia (*)", false, true, true,
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
                        tittle = "Comentario (Opcional)",
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
                    TableCell(text = errorsaveby, weight =1f ,title = true, textAlign = TextAlign.Left, color = RedVistony)
                }
                Row()
                {
                    Row()
                    {
                        /*ButtonView(
                            description = "Cerrar",
                            OnClick = {expanded = true},
                            status = true,
                            IconActive = false,
                            context=appContext,
                            backGroundColor = RedVistony,
                            textColor = Color.White
                        )
                        Spacer(modifier = Modifier.width(10.dp))*/
                        ButtonView(
                            description = "Guardar",
                            OnClick = {

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
                                if(currentSelectionSpinner1.value.equals("SELECCIONAR TIPO"))
                                {
                                    errorsaveby="Importante: Debe seleccionar un tipo de despacho"
                                }else
                                {
                                    if(!currentSelectionSpinner1.value.equals("E-ENTREGADO")&&currentSelectionSpinner2.value.equals("SELECCIONAR MOTIVO"))
                                    {
                                        errorsaveby="Importante: Debe seleccionar un motivo de despacho"
                                    }
                                    else
                                    {
                                        if(bitmapLocale.value==null)
                                        {
                                            errorsaveby="Importante: Debe tomar foto del local del cliente"
                                        }else
                                        {
                                            if(bitmapDelivery.value==null)
                                            {
                                                errorsaveby="Importante: Debe tomar foto de la guia del cliente"
                                            }
                                            else {
                                                errorsaveby=""
                                                statusbuttonsave=false
                                                colorbackgroundbuttonsave=Color.Gray
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
                                            }
                                        }

                                    }
                                }
                            } ,
                            status = statusbuttonsave,
                            IconActive = false,
                            context=appContext,
                            backGroundColor = colorbackgroundbuttonsave,
                            textColor = Color.White
                        )
                    }
                }
                Row()
                {
                    TableCell(text = "(*) son campos obligatorios", weight =1f ,title = false, textAlign = TextAlign.Left)
                }
            }
        //}
    }
}

