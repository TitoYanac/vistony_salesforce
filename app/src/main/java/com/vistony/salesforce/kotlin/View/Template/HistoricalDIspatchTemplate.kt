package com.vistony.salesforce.kotlin.View.Template

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.View.ContainerDispatchView
import com.vistony.salesforce.kotlin.Model.CollectionDetail
import com.vistony.salesforce.kotlin.Model.CollectionDetailRepository
import com.vistony.salesforce.kotlin.Model.CollectionDetailViewModel
import com.vistony.salesforce.kotlin.Model.CollectionHeadRepository
import com.vistony.salesforce.kotlin.Model.CollectionHeadViewModel
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheet
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetEntity
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetRepository
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetUI
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetViewModel
import com.vistony.salesforce.kotlin.Utilities.CollectionReceipPDF
import com.vistony.salesforce.kotlin.Utilities.getDate
import com.vistony.salesforce.kotlin.Utilities.sendSMS
import com.vistony.salesforce.kotlin.View.Atoms.CalendarApp
import com.vistony.salesforce.kotlin.View.Atoms.Cell
import com.vistony.salesforce.kotlin.View.Atoms.SwitchExample
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Atoms.TextLabel
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.View.Atoms.theme.RedVistony
import com.vistony.salesforce.kotlin.View.Orgsnisms.contentVisitDriver
import com.vistony.salesforce.kotlin.View.components.ButtonCircle
import com.vistony.salesforce.kotlin.View.components.DialogView
import com.vistony.salesforce.kotlin.View.components.DispatchSheetTemplate
import com.vistony.salesforce.kotlin.View.components.Editext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoricalDispatchTemplate()
{
    var DateApp: MutableState<String> = remember { mutableStateOf(getDate()!!) }
    val appContext = LocalContext.current
    var quantity: MutableState<String> = remember {  mutableStateOf("0")}
    var amount: MutableState<String> = remember {  mutableStateOf("0")}
    var showTabsExample by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalContext.current as LifecycleOwner

    Scaffold(
            topBar = {
            },
            bottomBar = {
                //BottomBar(appContext,quantity,amount)
            }
    )
    {
        Column {
            Row() {
                //Consulta de fecha de cobranza
                DateQueryDispatch(DateApp)
                {
                    showTabsExample = !showTabsExample
                }
            }
            Row (modifier= Modifier
                    .padding(0.dp, 0.dp)
                    .background(BlueVistony)
                    .height(30.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ){
                TableCell(text = "DESPACHOS", color = Color.White, title = true, weight = 1f)
            }
            if (showTabsExample) {
                    TabsExample(DateApp,appContext,lifecycleOwner)
            }
        }
    }
}

@Composable
fun DateQueryDispatch(
        DateApp: MutableState<String>,
        onClick: ()->Unit
)
{
    Column(
            //modifier= Modifier.padding(10.dp)
    ) {
        TextLabel(text = "Elegir una fecha para su consulta")
        Row() {
            Column(modifier = Modifier.weight(1f)) {
                //CalendarAppView(tittle = "Elegir una fecha para su consulta", DateApp = DateApp)
                CalendarApp(
                        DateApp = DateApp
                )
            }
            //Spacer(modifier = Modifier.width(5.dp))
            Column(modifier = Modifier.width(40.dp)) {
                ButtonCircle(
                        OnClick = {
                            onClick()
                        }, roundedCornerShape = RoundedCornerShape(0.dp)
                ) {
                    Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_search_white_24dp),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                            //tint = if ( stepsStatus.get(index) == "Y") BlueVistony else Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun TabsExample(
        DateApp: MutableState<String>,
        appContext:Context,
        lifecycleOwner: LifecycleOwner
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    Column(
            modifier = Modifier
                    .fillMaxSize()
            //.padding(16.dp)
    ) {
        TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp),
                contentColor = Color.White
        ) {
            Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
            ) {
                Text("Pendientes")
            }
            Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
            ) {
                Text("Exitosos")
            }
            Tab(
                    selected = selectedTabIndex == 2,
                    onClick = { selectedTabIndex = 2 },
                    modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
            ) {
                Text("Fallidos")
            }
        }

        //Spacer(modifier = Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> TabContent(DateApp,appContext,"P",lifecycleOwner)
            1 -> TabContent(DateApp,appContext,"E",lifecycleOwner)
            2 -> TabContent(DateApp,appContext,"F",lifecycleOwner)
        }
    }
}

@Composable
fun TabContent(
        DateApp: MutableState<String>,
        appContext:Context,
        type:String,
        lifecycleOwner:LifecycleOwner

) {
    Log.e("REOS", "HistoricalDispatchTemplate-TabContent-type: " + type)
    val detailDispatchSheetRepository = DetailDispatchSheetRepository(appContext)
    val detailDispatchSheetViewModel: DetailDispatchSheetViewModel = viewModel(
            factory = DetailDispatchSheetViewModel.DetailDispatchSheetViewModelFactory(
                    detailDispatchSheetRepository,
                    appContext,
                    SesionEntity.imei
            )
    )
    detailDispatchSheetViewModel?.getStateDetailDispatchSheet(
            DateApp.value,
            type
    )
    var detailDispatchSheetDB=detailDispatchSheetViewModel.result.collectAsState()
    /*var detailDispatchSheetDB= MutableLiveData<DetailDispatchSheetEntity>()

    detailDispatchSheetViewModel.resultDB.observeForever { newResultGet ->
        // Actualizar el valor de _invoices cuando haya cambios
        detailDispatchSheetDB.value = newResultGet
        Log.e("REOS", "HistoricalDispatchTemplate-TabContent-detailDispatchSheetDB.value: " + detailDispatchSheetDB.value)
    }*/

    when (detailDispatchSheetDB.value?.Status) {
        "Y" -> {
            CardHistoricalDispatch(detailDispatchSheetDB.value!!.UI,appContext)
            //detailDispatchSheetViewModel.resetDetailDispatchSheet()
        }
    }
}


private suspend fun loadImageFromUrl(url: String): ImageBitmap? {
    Log.e("REOS", "HistoricalDispatchTemplate-loadImageFromUrl-url: " + url)
    return try {
        withContext(Dispatchers.IO) {
            var connection = URL(url).openConnection() as? HttpURLConnection
            connection?.instanceFollowRedirects = false
            connection?.connect()

            val responseCode = connection?.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream: InputStream = connection!!.inputStream
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

                return@withContext bitmap.asImageBitmap()
            } else if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                val newUrl = connection?.getHeaderField("Location")
                if (!newUrl.isNullOrBlank()) {
                    // Si hay una nueva ubicación, intenta cargar la imagen desde la nueva URL
                    return@withContext loadImageFromUrl(newUrl)
                }
            }

            Log.e("REOS", "HistoricalDispatchTemplate-loadImageFromUrl-connection failed. Response code: $responseCode")
            return@withContext null
        }
    } catch (e: Exception) {
        // Manejar errores aquí
        e.printStackTrace()
        Log.e("REOS", "HistoricalDispatchTemplate-loadImageFromUrl-error: " + e)
        return null
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun CardHistoricalDispatch(
    listDetailDispatchSheet :List<DetailDispatchSheetUI>,
    context: Context
){
    var quantity: MutableState<String> = remember {  mutableStateOf("0")}
    var amount: MutableState<String> = remember {  mutableStateOf("0")}
    quantity.value=listDetailDispatchSheet.size.toString()
    var amountTemp:Float=0f
    listDetailDispatchSheet.forEach{ detail->
        amountTemp += detail.saldo!!.toFloat()
    }
    amount.value=amountTemp.toString()
    val openDialogShowImage:MutableState<Boolean?> = remember { mutableStateOf(false) }
    val urlDialog:MutableState<String> = remember { mutableStateOf("") }
    val openDialogObservation:MutableState<Boolean?> = remember { mutableStateOf(false) }
    val observationDinamic:MutableState<String> = remember { mutableStateOf("" ) }
    val observationTittle:MutableState<String> = remember { mutableStateOf("" ) }
    if(openDialogShowImage.value!!)
    {
        val imageBitmapURL:MutableState<ImageBitmap?> = remember { mutableStateOf(null)}
        Log.e("REOS", "HistoricalDispatchTemplate-CardHistoricalDispatch-openDialogShowImage.value: " +openDialogShowImage.value)
        Log.e("REOS", "HistoricalDispatchTemplate-CardHistoricalDispatch-urlDialog.value: " +urlDialog.value)
        if (urlDialog.value!=null)

            LaunchedEffect(urlDialog.value!!) {
                //val bitmap:MutableState<ImageBitmap?> = remember { mutableStateOf(loadImageFromUrl(urlDialog.value!!)) }
                imageBitmapURL.value=loadImageFromUrl(urlDialog.value!!)
                Log.e("REOS", "HistoricalDispatchTemplate-CardHistoricalDispatch-imageBitmapURL.value: " +imageBitmapURL.value)
            }

        if(imageBitmapURL.value!=null)
        {
            DialogView(
                tittle = "Foto", subtittle = "", onClickCancel = {
                    openDialogShowImage.value = false
                }, onClickAccept = {
                    openDialogShowImage.value = false
                }, statusButtonAccept = false, statusButtonIcon = false, context = context
            ) {
                Image(
                    bitmap = imageBitmapURL.value!!,
                    contentDescription = "Google Maps", // decorative
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        //Set Image size to 40 dp
                        //.size(400.dp)
                        //.align(Alignment.TopCenter)
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                )

            }
        }
    }

    if(openDialogObservation.value!!)
    {
        DialogView(
            tittle = observationTittle.value
            , subtittle = if(observationDinamic.value.equals("")){"No se registro observación"}else{observationDinamic.value}
            ,onClickCancel = {
                openDialogObservation.value = false
            }
            ,onClickAccept = {
                openDialogObservation.value = false
            }
            ,statusButtonAccept = false
            ,statusButtonIcon = false
            ,context=context
        ){

        }
    }

    Scaffold(
            topBar = {
            },
            bottomBar = {
                BottomBar(context,quantity,amount)
            }
    )
    {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
        ) {
            // Contenido de LazyColumn
            Box(
                    modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
            )
            {
                LazyColumn(
                        modifier = Modifier
                                //.weight(1f)
                                .fillMaxWidth()
                ) {
                    itemsIndexed(listDetailDispatchSheet) { index, line ->
                        val openDialogShowImage:MutableState<Boolean?> = remember { mutableStateOf(false) }
                        val urlDialog:MutableState<String?> = remember { mutableStateOf("") }
                        var isCheckedReceive: MutableState<Boolean> = remember { mutableStateOf(false) }
                        //isCheckedQR.value = line.QRStatus.equals("Y")
                        isCheckedReceive.value = line.chk_statusServerDispatch.equals("Y")
                        if(openDialogShowImage.value!!)
                        {
                            val imageBitmapURL:MutableState<ImageBitmap?> = remember { mutableStateOf(null)}
                            Log.e("REOS", "HistoricalDispatchTemplate-CardHistoricalDispatch-openDialogShowImage.value: " +openDialogShowImage.value)
                            Log.e("REOS", "HistoricalDispatchTemplate-CardHistoricalDispatch-urlDialog.value: " +urlDialog.value)
                            if (urlDialog.value!=null)

                                LaunchedEffect(urlDialog.value!!) {
                                    //val bitmap:MutableState<ImageBitmap?> = remember { mutableStateOf(loadImageFromUrl(urlDialog.value!!)) }
                                    imageBitmapURL.value=loadImageFromUrl(urlDialog.value!!)
                                    Log.e("REOS", "HistoricalDispatchTemplate-CardHistoricalDispatch-imageBitmapURL.value: " +imageBitmapURL.value)
                                }

                            if(imageBitmapURL.value!=null)
                            {
                                DialogView(
                                    tittle = "Foto", subtittle = "", onClickCancel = {
                                        openDialogShowImage.value = false
                                    }, onClickAccept = {
                                        openDialogShowImage.value = false
                                    }, statusButtonAccept = false, statusButtonIcon = false, context = context
                                ) {
                                    Image(
                                        bitmap = imageBitmapURL.value!!,
                                        contentDescription = "Google Maps", // decorative
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            //Set Image size to 40 dp
                                            //.size(400.dp)
                                            //.align(Alignment.TopCenter)
                                            .padding(0.dp, 10.dp, 0.dp, 0.dp)
                                    )

                                }
                            }
                        }

                        Card(
                                elevation = 4.dp, modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                        {
                            Column(
                                    modifier = Modifier
                                            .padding(15.dp)
                                            .fillMaxWidth()
                            ) {
                                Row(
                                        //horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.fillMaxWidth()
                                ) {
                                    TableCell(text = line.nombrecliente.toString(), weight = 1f, title = true)
                                }
                                Divider()
                                Row(
                                        modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                            horizontalAlignment = Alignment.Start,
                                            modifier = Modifier.weight(0.3f)
                                    ) {
                                        Cell(
                                                text = context.getString(R.string.lbl_delivery),
                                                title = false,
                                                Color.Gray
                                        )
                                        Cell(
                                                text = context.getString(R.string.type),
                                                title = false,
                                                Color.Gray
                                        )
                                        if(!line.estado.equals("ENTREGADO"))
                                        {
                                            Cell(
                                                    text = context.getString(R.string.reasons),
                                                    title = false,
                                                    Color.Gray
                                            )
                                        }
                                        Cell(
                                                text = context.getString(R.string.driver),
                                                title = false,
                                                Color.Gray
                                        )
                                        Cell(
                                                text = context.getString(R.string.balance),
                                                title = false,
                                                Color.Gray
                                        )
                                        Cell(
                                                text = context.getString(R.string.phone),
                                                title = false,
                                                Color.Gray
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
                                    }
                                    Column(
                                            horizontalAlignment = Alignment.Start,
                                            modifier = Modifier.weight(0.7f)
                                    ) {
                                        Cell(
                                                text = line.entrega.toString(),
                                                title = true
                                        )
                                        Cell(
                                                text = line.estado.toString(),
                                                title = true
                                        )
                                        if(!line.estado.equals("ENTREGADO"))
                                        {
                                            Cell(
                                                    text = line.motivo.toString(),
                                                    title = true,
                                            )
                                        }
                                        Cell(
                                                text = "",
                                                title = true
                                        )
                                        Cell(
                                                text = Convert.currencyForView(line.saldo.toString()),
                                                title = true
                                        )
                                        Cell(
                                                text = "",
                                                title = true
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
                                    }
                                }

                                Divider()
                                Row(
                                    //horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(0.3f)
                                    ) {
                                        Cell(
                                            text = "Foto Local",
                                            title = false,
                                            Color.Gray
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Row {
                                            Icon(
                                                imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_warehouse_24_black),
                                                contentDescription = null,
                                                tint = Color.Red,
                                                modifier = Modifier.clickable {
                                                    openDialogShowImage.value = true
                                                    urlDialog.value = line.fotolocal
                                                }
                                            )
                                        }
                                    }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(0.3f)
                                    ) {
                                        Cell(
                                            text = "Foto Guia",
                                            title = false,
                                            Color.Gray
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Row {
                                            Icon(
                                                imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_receipt_24_black),
                                                contentDescription = null,
                                                tint = Color.Red,
                                                modifier = Modifier.clickable {
                                                    openDialogShowImage.value = true
                                                    urlDialog.value = line.fotoguia
                                                }
                                            )
                                        }
                                    }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(0.3f)
                                    ) {
                                        Cell(
                                            text = "Observación",
                                            title = false,
                                            Color.Gray
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Row {
                                            Icon(
                                                imageVector = ImageVector.vectorResource(R.drawable.baseline_preview_24),
                                                contentDescription = null,
                                                tint = Color.Red,
                                                modifier = Modifier.clickable {
                                                    observationTittle.value = "Observación"
                                                    openDialogObservation.value = true
                                                    observationDinamic.value =
                                                        line.comentariodespacho.toString()
                                                }
                                            )
                                        }
                                    }

                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Divider()
                                Row(
                                    //horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(0.3f)
                                    ) {
                                        Cell(
                                            text = "Recepción",
                                            title = false,
                                            Color.Gray
                                        )
                                        //Spacer(modifier = Modifier.height(10.dp))
                                        SwitchExample(
                                            isChecked = isCheckedReceive,
                                            enable = false
                                        )
                                    }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(0.3f)
                                    ) {
                                        Cell(
                                            text = "Mensaje",
                                            title = false,
                                            Color.Gray
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Row {
                                            Icon(
                                                imageVector = ImageVector.vectorResource(R.drawable.ic_insert_comment_black_24dp),
                                                contentDescription = null,
                                                tint = Color.Red,
                                                modifier = Modifier.clickable {
                                                    observationTittle.value="Mensaje"
                                                    openDialogObservation.value=true
                                                    observationDinamic.value=line.messageServerDispatch.toString()
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}