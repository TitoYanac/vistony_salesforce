package com.vistony.salesforce.kotlin.View.Template

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Model.CollectionDetail
import com.vistony.salesforce.kotlin.Model.CollectionDetailRepository
import com.vistony.salesforce.kotlin.Model.CollectionDetailViewModel
import com.vistony.salesforce.kotlin.Model.Notification
import com.vistony.salesforce.kotlin.Utilities.getDate
import com.vistony.salesforce.kotlin.View.Atoms.CalendarApp
import com.vistony.salesforce.kotlin.View.Atoms.Cell
import com.vistony.salesforce.kotlin.View.Atoms.SwitchExample
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Atoms.TextLabel
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.View.Pages.ApiResponse
import com.vistony.salesforce.kotlin.View.components.ButtonCircle
import com.vistony.salesforce.kotlin.View.components.DialogView
import com.vistony.salesforce.kotlin.View.components.contexto

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoricalCollectionDetailTemplate()
{
    var DateApp: MutableState<String> = remember { mutableStateOf(getDate()!!) }
    val appContext = LocalContext.current
    val collectionDetailRepository : CollectionDetailRepository = CollectionDetailRepository(appContext)
    val collectionDetailViewModel: CollectionDetailViewModel = viewModel(
            factory = CollectionDetailViewModel.CollectionDetailViewModelFactory(
                    SesionEntity.imei,
                    appContext,
                    collectionDetailRepository
            )
    )
    var historicalCollectionDetail=collectionDetailViewModel.result_get_DB.collectAsState()
    var quantity: MutableState<String> = remember {  mutableStateOf("0")}
    var amount: MutableState<String> = remember {  mutableStateOf("0")}
    Scaffold(
            topBar = {
            },
            bottomBar = {BottomBar(appContext,quantity,amount)}
    )
    {
        Column {
            Row() {
                //Consulta de fecha de cobranza
                DateQuery(DateApp,collectionDetailViewModel)
            }
            Row (modifier= Modifier
                    .padding(0.dp,0.dp)
                    .background(BlueVistony)
                    .height(30.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ){
                TableCell(text = "RECIBOS", color = Color.White, title = true, weight = 1f)
            }
            when(historicalCollectionDetail.value.Status)
            {

                "Y"->{
                    var amountTemp:Float=0f
                    CardHistoricalCollectionDetail(historicalCollectionDetail.value.data,appContext)

                    quantity.value=historicalCollectionDetail.value.data.size.toString()
                    historicalCollectionDetail.value.data.forEach{ detail->
                        amountTemp += detail.AmountCharged.toFloat()
                    }
                    amount.value=amountTemp.toString()
                }
            }

        }
    }
}

@Composable
fun BottomBar(
        context:Context,
        quantity:MutableState<String>,
        amount:MutableState<String>
)
{
    Row (modifier= Modifier
            //.padding(10.dp)
            .background(BlueVistony)
            //.height(20.dp)
            .fillMaxWidth()
    ){
        Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                        .weight(0.5f)
                        .padding(10.dp, 0.dp)

        ) {
            Cell(
                    text = context.getString(R.string.quantity) ,
                    title = false,
                    color = Color.White,
                    fontSise = 20.sp
            )
            Cell(
                    text = context.getString(R.string.amount) ,
                    title = false,
                    color = Color.White,
                    fontSise = 20.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
        Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                        .weight(0.5f)
                        .padding(10.dp, 0.dp)
        ) {
            Cell(
                    text = quantity.value,
                    title = true,
                    color = Color.White,
                    fontSise = 20.sp
            )
            Cell(
                    text = Convert.currencyForView(amount.value) ,
                    title = true,
                    color = Color.White,
                    fontSise = 20.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun DateQuery(
        DateApp: MutableState<String>,
        collectionDetailViewModel: CollectionDetailViewModel
)
{
    Column(modifier= Modifier.padding(10.dp)) {
        TextLabel(text = "Elegir una fecha para su consulta")
        Row() {
            Column(modifier = Modifier.weight(1f)) {
                //CalendarAppView(tittle = "Elegir una fecha para su consulta", DateApp = DateApp)
                CalendarApp(
                        DateApp = DateApp
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Column(modifier = Modifier.weight(0.10f)) {
                ButtonCircle(
                        OnClick = {
                            collectionDetailViewModel.getCollectionDetailForDate(
                                    DateApp.value
                            )
                        }, roundedCornerShape = RoundedCornerShape(4.dp)
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
fun CardHistoricalCollectionDetail(
        listCollectionDetail:List<CollectionDetail>,
        context: Context
){
    val openDialogShowDialog:MutableState<Boolean?> = remember { mutableStateOf(false) }
    val messageDialog:MutableState<String> = remember { mutableStateOf("") }
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
                itemsIndexed(listCollectionDetail) { index, line ->
                    // Tu código aquí
                    var isCheckedQR: MutableState<Boolean> = remember { mutableStateOf(false) }
                    var isCheckedReceive: MutableState<Boolean> = remember { mutableStateOf(false) }
                    isCheckedQR.value = line.QRStatus.equals("Y")
                    isCheckedReceive.value = line.StatusSendAPI.equals("Y")
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
                                TableCell(text = line.CardName, weight = 1f,title = true)

                            }
                            Row(
                                    modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                        horizontalAlignment = Alignment.Start,
                                        modifier = Modifier.weight(0.3f)
                                ) {
                                    Cell(
                                            text = context.getString(R.string.menu_deposito) ,
                                            title = false,
                                            Color.Gray
                                    )
                                    Cell(
                                            text = context.getString(R.string.receip) ,
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
                                            text = context.getString(R.string.documents),
                                            title = false,
                                            Color.Gray
                                    )
                                    Cell(
                                            text = context.getString(R.string.type_collection),
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
                                            text = line.Deposit,
                                            title = true
                                    )
                                    Cell(
                                            text = line.Receip,
                                            title = true
                                    )
                                    Cell(
                                            text = Convert.currencyForView(line.AmountCharged),
                                            title = true,
                                    )
                                    Cell(
                                            text = line.Status,
                                            title = true
                                    )
                                    Cell(
                                            text = line.LegalNumber,
                                            title = true
                                    )
                                    Cell(
                                            text = line.U_VIS_Type,
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
                                            text = "QR",
                                            title = false,
                                            Color.Gray
                                    )
                                    SwitchExample(
                                            isChecked = isCheckedQR,
                                            enable = false
                                    )
                                }
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
                                                imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_sms_black_24),
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