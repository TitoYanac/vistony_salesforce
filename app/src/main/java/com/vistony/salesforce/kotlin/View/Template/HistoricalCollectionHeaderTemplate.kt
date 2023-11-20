package com.vistony.salesforce.kotlin.View.Template

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Controller.Utilitario.Induvis
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Model.CollectionDetail
import com.vistony.salesforce.kotlin.Model.CollectionDetailRepository
import com.vistony.salesforce.kotlin.Model.CollectionDetailViewModel
import com.vistony.salesforce.kotlin.Model.CollectionHead
import com.vistony.salesforce.kotlin.Model.CollectionHeadRepository
import com.vistony.salesforce.kotlin.Model.CollectionHeadViewModel
import com.vistony.salesforce.kotlin.Utilities.CollectionReceipPDF
import com.vistony.salesforce.kotlin.Utilities.ConvertDateSAPaUserDate
import com.vistony.salesforce.kotlin.Utilities.getDate
import com.vistony.salesforce.kotlin.Utilities.sendSMS
import com.vistony.salesforce.kotlin.View.Atoms.CalendarApp
import com.vistony.salesforce.kotlin.View.Atoms.Cell
import com.vistony.salesforce.kotlin.View.Atoms.SwitchExample
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Atoms.TextLabel
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.View.components.ButtonCircle
import com.vistony.salesforce.kotlin.View.components.DialogView
import com.vistony.salesforce.kotlin.View.components.Editext

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoricalCollectionHeaderTemplate()
{
    var DateApp: MutableState<String> = remember { mutableStateOf(getDate()!!) }
    val appContext = LocalContext.current
    val collectionHeadRepository : CollectionHeadRepository = CollectionHeadRepository(appContext)
    val collectionHeadViewModel: CollectionHeadViewModel = viewModel(
            factory = CollectionHeadViewModel.CollectionHeadViewModelFactory(
                    appContext,
                    collectionHeadRepository
            )
    )
    var historicalCollectionHead=collectionHeadViewModel.result_DB.collectAsState()
    var quantity: MutableState<String> = remember {  mutableStateOf("0") }
    var amount: MutableState<String> = remember {  mutableStateOf("0") }
    var DateApp1: MutableState<String> = remember { mutableStateOf(getDate()!!) }

    val collectionDetailRepository : CollectionDetailRepository = CollectionDetailRepository(appContext)
    val collectionDetailViewModel: CollectionDetailViewModel = viewModel(
            factory = CollectionDetailViewModel.CollectionDetailViewModelFactory(
                    SesionEntity.imei,
                    appContext,
                    collectionDetailRepository
            )
    )

    Scaffold(
            topBar = {
            },
            bottomBar = {BottomBar(appContext,quantity,amount)}
    )
    {
        Column {
            Row() {
                Column(modifier = Modifier.weight(1f)) {
                    //Consulta de fecha de cobranza
                    DateQueryDeposi(DateApp)
                }
                Spacer(modifier = Modifier.width(5.dp))
                Column(modifier = Modifier.weight(1f)) {
                    //Consulta de fecha de cobranza
                    DateQueryDeposi(DateApp1)
                }
                Spacer(modifier = Modifier.width(5.dp))
                Column(modifier = Modifier.width(40.dp)) {
                    ButtonCircle(
                            OnClick = {
                                collectionHeadViewModel.getCollectionHeadForDate(
                                        DateApp.value,DateApp1.value
                                )
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
            Row (modifier= Modifier
                    .padding(0.dp, 0.dp)
                    .background(BlueVistony)
                    .height(30.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ){
                TableCell(text = "DEPOSITOS", color = Color.White, title = true, weight = 1f)
            }
            when(historicalCollectionHead.value.Status)
            {

                "Y"->{
                    var amountTemp:Float=0f
                    CardHistoricalCollectionHead(historicalCollectionHead.value.data,appContext,collectionHeadViewModel,collectionDetailViewModel)

                    quantity.value=historicalCollectionHead.value.data.size.toString()
                    historicalCollectionHead.value.data.forEach{ detail->
                        amountTemp += detail.AmountDeposit.toFloat()
                    }
                    amount.value=amountTemp.toString()
                }
            }

        }
    }
}

@Composable
fun DateQueryDeposi(
        DateApp: MutableState<String>,
)
{
    Column(
            //modifier= Modifier.padding(10.dp)
    ) {
        Row() {
            Column(modifier = Modifier.weight(1f)) {
                //CalendarAppView(tittle = "Elegir una fecha para su consulta", DateApp = DateApp)
                CalendarApp(
                        DateApp = DateApp
                )
            }

        }
    }
}

@Composable
fun CardHistoricalCollectionHead(
        listCollectionHead:List<CollectionHead>,
        context: Context,
        collectionHeadViewModel: CollectionHeadViewModel,
        collectionDetailViewModel:CollectionDetailViewModel
){
    val openDialogShowDialog:MutableState<Boolean?> = remember { mutableStateOf(false) }
    val messageDialog:MutableState<String> = remember { mutableStateOf("") }

    val openDialogSensSMS:MutableState<Boolean?> = remember { mutableStateOf(false) }
    var DialogEditResultSMS :MutableState<String> = remember { mutableStateOf("0") }
    val activity = LocalContext.current as Activity
    val collectionDetailSMS:MutableState<CollectionDetail?> = remember { mutableStateOf(null) }
    //Dialog cancel
    val openDialogCancelDeposit:MutableState<Boolean?> = remember { mutableStateOf(false) }
    var DialogCancelDepositResult :MutableState<String> = remember { mutableStateOf("0") }
    var DialogCancelDepositCode :MutableState<String> = remember { mutableStateOf("0") }
    var DialogCancelDepositDate :MutableState<String> = remember { mutableStateOf("0") }

    if(openDialogShowDialog.value!!)
    {
        DialogView(
                tittle = "Mensaje"
                , subtittle = ""
                ,onClickCancel = {
            openDialogShowDialog.value = false
        }
                ,onClickAccept = {
            openDialogShowDialog.value = false
        }
                ,statusButtonAccept = false
                ,statusButtonIcon = false
                ,context=context
        ){
            Row {
                TableCell(text = messageDialog.value, weight =1f , title = true)
            }

        }
    }

    if(openDialogSensSMS.value!!)
    {
        DialogView(
                "SMS"
                ,""
                ,onClickCancel = {
            openDialogSensSMS.value = false
        }
                ,onClickAccept = {
            sendSMS(
                    DialogEditResultSMS.value,
                    context,
                    activity,
                    collectionDetailSMS.value
            )
        }
                ,statusButtonAccept = true
                ,statusButtonIcon = false
                ,context=context
        ){
            Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                        horizontalAlignment = Alignment.Start,
                        modifier=Modifier.weight(0.5f)
                ) {
                    Text(text = "Enviar SMS", fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
            ) {
                Editext(
                        status = true,
                        text= DialogEditResultSMS,
                        "Ingresa el numero telefonico",
                        "Telefono",
                        painterResource(id = R.drawable.ic_baseline_numbers_24),
                        KeyboardType.Number
                )
            }
        }
    }

    if(openDialogCancelDeposit.value!!)
    {
        DialogView(
                tittle = "Anular"
                , subtittle = "Ingrese el comentario"
                ,onClickCancel = {
            openDialogCancelDeposit.value = false
        }
                ,onClickAccept = {
            collectionHeadViewModel.updateCancelCollectionHead(
                    DialogCancelDepositCode.value,
                    DialogCancelDepositDate.value,
                    DialogCancelDepositResult.value)
            collectionDetailViewModel.getCollectionDetailForDeposit(DialogCancelDepositCode.value)
            collectionDetailViewModel.sendAPICancelDepositCollectionDetail()
            collectionHeadViewModel.sendAPICancelCollectionHead()

            openDialogCancelDeposit.value = false
        }
                ,statusButtonAccept = true
                ,statusButtonIcon = false
                ,context=context
        ){
            Editext(
                    status = true,
                    text= DialogCancelDepositResult,
                    "Ingresa el comentario",
                    "Comentario",
                    painterResource(id = R.drawable.ic_baseline_numbers_24),
                    KeyboardType.Text
            )
        }
    }

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
                itemsIndexed(listCollectionHead) { index, line ->
                    var expanded by remember { mutableStateOf(true) }
                    // Tu código aquí
                    var isCheckedQR: MutableState<Boolean> = remember { mutableStateOf(false) }
                    var isCheckedReceive: MutableState<Boolean> = remember { mutableStateOf(false) }
                    //isCheckedQR.value = line.QRStatus.equals("Y")
                    isCheckedReceive.value = line.APIStatus.equals("Y")
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

                            AnimatedVisibility(
                                    visible = expanded,
                                    enter = expandIn(),
                                    exit = shrinkOut()
                            ) {
                                Row(
                                        //horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.fillMaxWidth()
                                ) {
                                    TableCell(text = line.Deposit, weight = 1f, title = true)
                                    Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.baseline_chevron_right_24),
                                            contentDescription = null,
                                            tint = Color.Red,
                                            modifier = Modifier.clickable {expanded=expanded.not()}
                                    )
                                }
                            }
                            AnimatedVisibility(
                                    visible = expanded.not(),
                                    enter = expandIn(),
                                    exit = shrinkOut()
                            ) {
                                Row(
                                        //horizontalArrangement = Arrangement.Start,
                                        modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.baseline_chevron_left_24),
                                            contentDescription = null,
                                            tint = Color.Red,
                                            modifier = Modifier.clickable {
                                                expanded=expanded.not()

                                            }
                                    )
                                    Column(modifier = Modifier.weight(1f).align(Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                                imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_cancel_24_white),
                                                contentDescription = null,
                                                tint = Color.Red,
                                                modifier = Modifier.clickable {
                                                    openDialogCancelDeposit.value=true
                                                    DialogCancelDepositCode.value=line.Deposit
                                                    DialogCancelDepositDate.value=line.Date
                                                }
                                        )
                                    }
                                }
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
                                            text = context.getString(R.string.banks) ,
                                            title = false,
                                            Color.Gray
                                    )
                                    Cell(
                                            text = context.getString(R.string.amount) ,
                                            title = false,
                                            Color.Gray
                                    )
                                    Cell(
                                            text = context.getString(R.string.status),
                                            title = false,
                                            Color.Gray
                                    )
                                    Cell(
                                            text = context.getString(R.string.date),
                                            title = false,
                                            Color.Gray
                                    )
                                    Cell(
                                            text = context.getString(R.string.type),
                                            title = false,
                                            Color.Gray
                                    )
                                    Cell(
                                            text = context.getString(R.string.deferred),
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
                                            text = line.BankID,
                                            title = true
                                    )
                                    Cell(
                                            text = Convert.currencyForView(line.AmountDeposit),
                                            title = true,
                                    )
                                    Cell(
                                            text = if(line.Status.equals("P")){"Pendiente"}else if(line.Status.equals("C")){"Conciliado"}else if(line.Status.equals("A")){"Anulado"}else{"Manual"},
                                            title = true
                                    )
                                    Cell(
                                            text = ConvertDateSAPaUserDate(line.Date)!!,
                                            title = true
                                    )
                                    Cell(
                                            text = if(line.IncomeType.equals("DE")){"Deposito"}else{ "Cheque"},
                                            title = true
                                    )
                                    Cell(
                                            text = ConvertDateSAPaUserDate(line.DeferredDate)!!,
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

                                /*Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(0.3f)
                                ) {
                                    Cell(
                                            text = "QR",
                                            title = false,
                                            Color.Gray
                                    )
                                    SwitchExample(
                                            isChecked = isCheckedQR,
                                            enable = false
                                    )
                                }*/
                                Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(0.3f)
                                ) {
                                    Cell(
                                            text = "Recibido",
                                            title = false,
                                            Color.Gray
                                    )
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
                                                imageVector = ImageVector.vectorResource(R.drawable.baseline_preview_24),
                                                contentDescription = null,
                                                tint = Color.Red,
                                                modifier = Modifier.clickable {
                                                    openDialogShowDialog.value=true
                                                    messageDialog.value=line.APIMessage }
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