package com.vistony.salesforce.kotlin.View.Molecules

import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheet
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetUI
import com.vistony.salesforce.kotlin.Utilities.Geolocation
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.View.Atoms.theme.Typography
import com.vistony.salesforce.kotlin.View.Template.BottomSheetScreen
import com.vistony.salesforce.kotlin.View.Template.ProcessCollectionTemplate
import com.vistony.salesforce.kotlin.View.Template.SheetLayout
import com.vistony.salesforce.kotlin.View.components.ButtonCircle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class
)
@Composable
fun StatusAdvanceDispatch(
    icons:String,
    InfoDialog: (detailDispatchSheet: DetailDispatchSheetUI) -> Unit,
    InfoDialogEnd: (detailDispatchSheet: DetailDispatchSheetUI) -> Unit,
    list: DetailDispatchSheetUI,
    context1: Context,
    formProcessCollection:(detailDispatchSheet: DetailDispatchSheetUI) -> Unit,
    formProcessStatusDispatch:(detailDispatchSheet: DetailDispatchSheetUI) -> Unit,
)
{

    when (icons) {
        "Entrada" -> {
            InfoDialog(list)
        }
        "Despacho" -> {
            formProcessStatusDispatch(list)
        }

        "Cobranza" -> {
            formProcessCollection(list)
            //openSheet(
            /*var modal = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, confirmStateChange = {false})
            val scope = rememberCoroutineScope()
            var currentBottomSheet: BottomSheetScreen? by remember { mutableStateOf(null) }
            val closeSheet: () -> Unit = { scope.launch {
                modal.hide()
                //modal==null
            }}

            ModalBottomSheetLayout(
                sheetState = modal,
                sheetContent = {
                    Box(modifier = Modifier.defaultMinSize(minHeight = 1.dp)) {
                        currentBottomSheet?.let { currentSheet ->
                            SheetLayout(currentSheet, closeSheet,showIconClose=true)
                        }
                    }
                }
            ) {
                val openSheet: (BottomSheetScreen) -> Unit = {
                    scope.launch {
                        currentBottomSheet = it
                        modal.animateTo(ModalBottomSheetValue.Expanded)
                    }
                }

                openSheet(
                    BottomSheetScreen.collectionDetailBottom(
                        //objDistpatch.nombrecliente.toString()
                        //,invoiceViewModel
                        list
                    )
                )
            }*/
            //ProcessCollectionTemplate(list)
        }
        "Salida" -> {
            InfoDialogEnd(list)
        }
    }
    Log.e(
        "REOS",
        "Composables-StatusIcons-Final"
    )
}

@Composable
fun HorizontalStepView(
    statusTittleIcon: List<Pair<String, ImageVector>>,
    statusList: Array<String?>,
    /*numberStatus: Array<String>,
    context: Context,
    InfoDialog: (status:String) -> Unit,
    InfoDialogEnd: (status:String) -> Unit,
    list: DetailDispatchSheet,
    formProcessCollection: (cliente_id:String) -> Unit,
    formProcessStatusDispatch: (list: DetailDispatchSheet) -> Unit,*/
    OnClick:(result:String)-> Unit,
    progress:Float,
    StatusIcon:Boolean,

    ) {

    Box {
        LinearProgressIndicator(
            progress = progress
            ,
            color = BlueVistony,
            backgroundColor = Color.Gray, // el color de fondo siempre será gris
            //progress = 0.3f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp, 70.dp, 45.dp, 0.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp, 10.dp, 0.dp)
        ) {

            statusTittleIcon.forEachIndexed { index, step ->
                var color:MutableState<Color> = remember { mutableStateOf(Color.Gray)}
                if (statusList.get(index) == "Y"){color.value= BlueVistony}else{color.value=Color.Gray}
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    if(StatusIcon)
                    {
                        IconButton(
                            onClick = {
                                OnClick(step.first)
                                //showDialog.value=true
                            },

                            ){
                            Icon(
                                imageVector = step.second,
                                contentDescription = null,
                                modifier =
                                Modifier
                                    .size(30.dp),
                                tint = color.value,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box {
                        ButtonCircle(
                            OnClick={},
                            size = DpSize(30.dp,30.dp),
                            color=color.value
                        )
                        {
                            Icon(
                                imageVector = ImageVector.vectorResource(if (statusList.get(index) == "Y"){R.drawable.ic_check}else{R.drawable.ic_baseline_close_24_white}),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                                //tint = if ( stepsStatus.get(index) == "Y") BlueVistony else Color.Gray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        //text = step,
                        text = step.first,
                        fontWeight = FontWeight.Bold,
                        color =color.value,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun HorizontalStepView2(
    statusTittleIcon: List<Pair<String, ImageVector>>,
    statusList: Array<String?>,
    OnClick:(result:String)-> Unit,
    progress:Float,
    StatusIcon:Boolean,

    ) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(10.dp)
    ) {
        LinearProgressIndicator(
            progress = progress
            ,
            color = BlueVistony,
            backgroundColor = Color.Gray, // el color de fondo siempre será gris
            //progress = 0.3f,
            modifier = Modifier
                .fillMaxWidth()
                //.padding(40.dp, 70.dp, 45.dp, 0.dp)
        )
        Row(
            //horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                //.padding(10.dp, 10.dp, 10.dp, 0.dp)
        ) {

            statusTittleIcon.forEachIndexed { index, step ->
                var color:MutableState<Color> = remember { mutableStateOf(Color.Gray)}
                if (statusList.get(index) == "Y"){color.value= BlueVistony}else{color.value=Color.Gray}
                Log.e(
                    "REOS",
                    "StatusAdvanceDispatch-HorizontalStepView2-index: " +index
                )
                Log.e(
                    "REOS",
                    "StatusAdvanceDispatch-HorizontalStepView2-statusTittleIcon.size: " +statusTittleIcon.size
                )
                Column(
                    horizontalAlignment = if(index==0){Alignment.Start}else if(index==(statusTittleIcon.size-1)){Alignment.End} else{Alignment.CenterHorizontally} ,
                    modifier = Modifier.weight(1f)
                ) {
                    if(StatusIcon)
                    {
                        IconButton(
                            onClick = {
                                OnClick(step.first)
                                //showDialog.value=true
                            },

                            ){
                            Icon(
                                imageVector = step.second,
                                contentDescription = null,
                                modifier =
                                Modifier
                                    .size(30.dp),
                                tint = color.value,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box {
                        ButtonCircle(
                            OnClick={},
                            size = DpSize(30.dp,30.dp),
                            color=color.value
                        )
                        {
                            Icon(
                                imageVector = ImageVector.vectorResource(if (statusList.get(index) == "Y"){R.drawable.ic_check}else{R.drawable.ic_baseline_close_24_white}),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                                //tint = if ( stepsStatus.get(index) == "Y") BlueVistony else Color.Gray
                            )
                        }
                    }
                    if(StatusIcon) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            //text = step,
                            text = step.first,
                            fontWeight = FontWeight.Bold,
                            color = color.value,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardDispatch(
    list: DetailDispatchSheetUI,
    context: Context,
    formProcessCollection: (list: DetailDispatchSheetUI) -> Unit,
    formProcessStatusDispatch: (list: DetailDispatchSheetUI) -> Unit,
    listelementsinvoice: List<Pair<String, String>>,
    //steps: List<Pair<String, ImageVector>>,
    //numberStatus: Array<String>,
    stepsStatus: Array<String?>,
    openVisitDriver: MutableState<Boolean>,
    openDialogMapNavigation: MutableState<Boolean>,
    latitud: MutableState<String>,
    longitud: MutableState<String>,
    statusvisitDispatch: (list: DetailDispatchSheetUI) -> Unit,
){
    Log.e(
        "REOS",
        "DispatchSheetTemplate-CardDispatch-list.statusvisitend: "+list.statusvisitend
    )
    Log.e(
        "REOS",
        "VisitSectionViewModel-addVisitSection-list.cliente_id: "+list.cliente_id
    )
    Card(
        elevation = 10.dp,
        //onClick = { },
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth()
            .clickable {
                Log.e("REOS", "DispatchSheet-CardDispatch-list: "+list) }
        ,
    )
    {
        Column()
        {
            Row(Modifier.padding(10.dp,10.dp,10.dp,0.dp))
            {
                ButtonCircle(OnClick = {}, color = BlueVistony)
                {
                    Text(
                        text = list.item_id.toString(),
                        fontSize = 13.sp,
                        //fontWeight = FontWeight.Bold,
                        color = Color.White,
                        style = Typography.h5,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(270.dp))
                ButtonCircle(OnClick =
                { val geoLocalizacion = Geolocation(context)

                    geoLocalizacion.obtenerUbicacionActual(
                        onSuccess = { location ->
                            latitud.value = location.latitude.toString()
                            longitud.value = location.longitude.toString()
                            openDialogMapNavigation.value = true
                        },
                        onFailure = {
                            // No se pudo obtener la ubicación actual del usuario
                        }
                    )
                }
                )
                {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_directions_24),
                        contentDescription = null,
                        tint = Color.White,
                        //modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
            Column(modifier = Modifier.padding(10.dp,0.dp,10.dp,0.dp)) {
                Row {
                    Box(modifier = Modifier.weight(1f)) {
                        Row() {
                            TableCell (text ="Razon Social", weight = 1f ,title = false,color = Color.Gray, fontSise = 13.sp, textAlign = TextAlign.Left)
                        }
                    }
                    Box(modifier = Modifier.weight(3f)) {
                        Row() {
                            TableCell (text =list.nombrecliente.toString(), weight = 1f ,title = true, fontSise = 13.sp, textAlign = TextAlign.Left)
                        }
                    }
                }
                Row {
                    Box(modifier = Modifier.weight(1f)) {
                        Row() {
                            TableCell (text ="Dirección", weight = 1f ,title = false,color = Color.Gray, fontSise = 13.sp, textAlign = TextAlign.Left)
                        }
                    }
                    Box(modifier = Modifier.weight(3f)) {
                        Row() {
                            TableCell (text =list.direccion.toString(), weight = 1f ,title = true, fontSise = 13.sp, textAlign = TextAlign.Left)
                        }
                    }
                }
            }
            Column()
            {
                val showDialog = remember { mutableStateOf(false) }
                val resultValue:MutableState<String> = remember { mutableStateOf("") }

                if (showDialog.value)
                {
                    StatusAdvanceDispatch(
                        resultValue.value,
                        //InfoDialog,
                        {
                            //openVisitDriver.value = true
                            list -> statusvisitDispatch(list)
                        },
                        {
                            //openVisitDriver.value = true
                                list -> statusvisitDispatch(list)
                        },
                        list,
                        context,
                        //formProcessCollection = formProcessCollection(list.cliente_id)
                        { clienteId -> formProcessCollection(list) }
                        //{formProcessCollection(index, list)}
                        , {list -> formProcessStatusDispatch(list)}
                    )
                    showDialog.value = false
                }
                val steps = listOf(
                    Pair("Entrada", Icons.Filled.Home),
                    Pair(
                        "Despacho",
                        ImageVector.vectorResource(R.drawable.ic_baseline_local_shipping_black_24)
                    ),
                    Pair(
                        "Cobranza",
                        ImageVector.vectorResource(R.drawable.ic_local_atm_black_24dp)
                    ),
                    Pair("Salida", Icons.Filled.ExitToApp)
                )
                var statusvisitstart:String=""
                var statusupdatedispatch:String=""
                var statuscollection:String=""
                var statusvisitend:String=""
                var progress:Float=0F
                steps.forEachIndexed { index, step ->
                    if(index==0)
                    {
                        statusvisitstart=stepsStatus.get(index).toString()
                    }
                    if(index==1)
                    {
                        statusupdatedispatch=stepsStatus.get(index).toString()
                    }
                    if(index==2)
                    {
                        statuscollection=stepsStatus.get(index).toString()
                    }
                    if(index==3)
                    {
                        statusvisitend=stepsStatus.get(index).toString()
                    }
                }

                if(statusvisitstart.equals("Y")&&statusupdatedispatch.equals("Y")&&
                    statuscollection.equals("N")&&statusvisitend.equals("N"))
                    progress=0.03f
                else if(statusvisitstart.equals("Y")&&statusupdatedispatch.equals("Y")&&
                    statuscollection.equals("Y")&&statusvisitend.equals("N"))
                    progress=0.06f
                else if(statusvisitstart.equals("Y")&&statusupdatedispatch.equals("Y")&&
                    statuscollection.equals("Y")&&statusvisitend.equals("Y"))
                    progress=1.00f
                else if(statusvisitstart.equals("Y")&&statusupdatedispatch.equals("Y")&&
                    statuscollection.equals("N")&&statusvisitend.equals("Y"))
                    progress=1.00f

                ExpandableCard("Detalle",listelementsinvoice)

                HorizontalStepView(
                    statusTittleIcon = steps
                    , statusList = stepsStatus
                    , OnClick = {
                            result ->
                        resultValue.value=result
                        showDialog.value=true
                    },
                    progress,
                    true
                )


            }
        }
    }
}