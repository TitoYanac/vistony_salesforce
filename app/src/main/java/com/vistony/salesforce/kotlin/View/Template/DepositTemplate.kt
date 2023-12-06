package com.vistony.salesforce.kotlin.View.Template

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.DatePicker
import android.widget.ScrollView
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Controller.Utilitario.Induvis
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Model.*
import com.vistony.salesforce.kotlin.Utilities.CollectionReceipPDF
import com.vistony.salesforce.kotlin.Utilities.ConvertDateSAPaUserDate
import com.vistony.salesforce.kotlin.Utilities.CreateCollectionHead
import com.vistony.salesforce.kotlin.Utilities.getDate
import com.vistony.salesforce.kotlin.Utilities.getDateCurrent
import com.vistony.salesforce.kotlin.Utilities.sendSMS
import com.vistony.salesforce.kotlin.View.Atoms.*
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.View.Atoms.theme.RedVistony
import com.vistony.salesforce.kotlin.View.Molecules.*
import com.vistony.salesforce.kotlin.View.Pages.text1
import com.vistony.salesforce.kotlin.View.components.*
import kotlinx.coroutines.flow.collect
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContentDeposit()
{
    val appContext = LocalContext.current
    val collectionDetailRepository : CollectionDetailRepository = CollectionDetailRepository(appContext)
    val collectionDetailViewModel: CollectionDetailViewModel = viewModel(
        factory = CollectionDetailViewModel.CollectionDetailViewModelFactory(
            SesionEntity.imei,
            appContext,
            //lifecycleOwner,
            collectionDetailRepository
        )
    )
    collectionDetailViewModel.getCountCollectionDetail()
    var collectionDetailDB=collectionDetailViewModel.result_get_DB.collectAsState()
    var collectionDetailAPI=collectionDetailViewModel.result_get_API.collectAsState()
    var collectionlist:MutableState<List<CollectionDetail>> = remember { mutableStateOf( emptyList())}
    val openDialog:MutableState<Boolean?> = remember { mutableStateOf(false) }
    val bankRepository : BankRepository = BankRepository()
    val bankViewModel: BankViewModel = viewModel(
        factory = BankViewModel.BankViewModelFactory(
            bankRepository,
            appContext,
            SesionEntity.imei,
        )
    )
    bankViewModel.getAddBanks()
    val headerDispatchSheetRepository : HeaderDispatchSheetRepository = HeaderDispatchSheetRepository()
    val headerDispatchSheetViewModel: HeaderDispatchSheetViewModel = viewModel(
            factory = HeaderDispatchSheetViewModel.HeaderDispatchSheetViewModelFactory(
                    headerDispatchSheetRepository,
                    appContext,
                    SesionEntity.imei
            )
    )
    val amountDeposit:MutableState<String> = remember { mutableStateOf("0") }
    //Valida que existan recibos en la base de datos local y en caso de estar vacio, consulta la API
    when (collectionDetailDB.value.Status) {
        "Y" -> {
            if (collectionDetailDB.value.Count.equals("0"))
            {
                collectionDetailViewModel.getAPICollectionDetail(SesionEntity.usuario_id,"PD")
            }
        }
    }

    //Recibe estado de la API
    when (collectionDetailAPI.value.Status) {
        "Y" -> {
            if (collectionDetailDB.value.data.size>0)
            {
                Toast.makeText(appContext,"Se cargaron "+collectionDetailDB.value.data.size+" elementos", Toast.LENGTH_SHORT).show()
            }
        }
    }
    var statusBoolean: MutableState<Boolean> = remember { mutableStateOf(true) }
    Log.e("REOS", "DepositTemplate-ContentDeposit-statusBoolean.value: " + statusBoolean.value)
    val activity = LocalContext.current as Activity
    val typeCollection:MutableState<String> = remember { mutableStateOf("")}
    val collectionHeadRepository : CollectionHeadRepository = CollectionHeadRepository(appContext)
    val collectionHeadViewModel: CollectionHeadViewModel = viewModel(
            factory = CollectionHeadViewModel.CollectionHeadViewModelFactory(
                    appContext,
                    collectionHeadRepository
            )
    )
    collectionHeadViewModel.sendAPICollectionHead()
    collectionDetailViewModel.SendAPICollectionDetail(appContext, SesionEntity.compania_id,SesionEntity.usuario_id)
    collectionDetailViewModel.sendAPIUpdateDepositCollectionDetail()
    var totalQuantity: MutableState<String> = remember {  mutableStateOf("0")}
    var selectedQuantity: MutableState<String> = remember {  mutableStateOf("0")}
    var amount: MutableState<String> = remember {  mutableStateOf("0")}

    Scaffold(
        topBar = {
        },
        bottomBar = {BottomBarDeposit(appContext,totalQuantity,selectedQuantity,amount)}
    )
    {
        Column()
        {
            StageOneDeposit(
                collectionDetailViewModel,
                statusBoolean,
                amountDeposit,
                typeCollection,
                totalQuantity,
                selectedQuantity,
                amount,
                onSelectCollection = { collection ->
                    collectionlist.value = collection
                    openDialog.value = true
                    Log.e(
                        "REOS",
                        "DepositTemplate-ContentDeposit-collectionlist" + collectionlist
                    )
                }
            )

            if (openDialog.value!!) {
                StageTwoDeposit(
                    appContext,
                    bankViewModel,
                    statusBoolean,
                    activity,
                    headerDispatchSheetViewModel,
                    amountDeposit,
                    typeCollection,
                    collectionHeadViewModel,
                    collectionlist,
                    collectionDetailViewModel
                )
            }
        }
    }
}

@Composable
fun StageOneDeposit(
    collectionDetailViewModel:CollectionDetailViewModel,
    statusBoolean: MutableState<Boolean>,
    amountDeposit: MutableState<String>,
    typeCollection: MutableState<String>,
    totalQuantity: MutableState<String>,
    selectedQuantity: MutableState<String>,
    amount: MutableState<String>,
    onSelectCollection: (List<CollectionDetail> ) -> Unit
) {

    var DateApp: MutableState<String> = remember { mutableStateOf(getDate()!!) }
    //var expanded by remember { mutableStateOf(statusBoolean.value) }

    Log.e("REOS", "DepositTemplate-StageOneDeposit-DateApp.value: " + DateApp.value)

    var collectionDetail = collectionDetailViewModel.result_pending_deposit.collectAsState()
    Log.e("REOS", "DepositTemplate-StageOneDeposit-collectionDetail: " + collectionDetail)
    val typeDepositList = listOf( "Todos","Cobranza Ordinaria","Deposito Directo")
    val currentSelectionSpinner = remember {
        mutableStateOf("Todos")
    }
    typeCollection.value=currentSelectionSpinner.value

    CardView(
        cardtTittle =
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ButtonCircle(OnClick = {}, color = BlueVistony) {
                    Text(
                        text = "1",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    ) }
                TableCell(text = "SELECCION DE DOCUMENTOS", weight =1f ,title = true, textAlign = TextAlign.Center)
            }
        }
        , cardContent =
        {
            AnimatedVisibility(
                visible = statusBoolean.value,
                enter = expandIn(),
                exit = shrinkOut()
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                Column() {
                    TextLabel(text = "Elegir una fecha para su consulta")
                    Row() {
                        Column(modifier = Modifier.weight(0.90f)) {
                            //CalendarAppView(tittle = "Elegir una fecha para su consulta", DateApp = DateApp)
                            CalendarApp(
                                DateApp = DateApp
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Column(modifier = Modifier.weight(0.10f)) {
                            ButtonCircle(
                                OnClick = {
                                    collectionDetailViewModel.getCollectionDetailPendingDeposit(
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
            Column() {
                    when (collectionDetail.value.Status) {
                        "Y" -> {
                            var QuantitySelected by remember { mutableStateOf(0) }
                            var AmountSelected by remember { mutableStateOf(0f) }
                            Spacer(modifier = Modifier.height(5.dp))
                            Column(
                                modifier = Modifier
                                    //.fillMaxSize()
                                    //.padding(10.dp)
                            ) {

                                val filteredData = remember(currentSelectionSpinner) {
                                    derivedStateOf {
                                        if (currentSelectionSpinner.value == "Todos") {
                                            collectionDetail.value.data
                                        } else {
                                            collectionDetail.value.data.filter { it.U_VIS_Type == currentSelectionSpinner.value }
                                        }
                                    }
                                }
                                Row(modifier = Modifier.padding(0.dp,0.dp)){
                                    Column(modifier = Modifier.weight(0.8f)) {
                                        Spinner(
                                            items = typeDepositList,
                                            selectedItem = currentSelectionSpinner.value,
                                            onItemSelected = { item ->
                                                currentSelectionSpinner.value = item
                                            }
                                        )
                                    }
                                    /*Spacer(modifier = Modifier.width(5.dp))

                                    Column(modifier = Modifier.weight(0.20f)) {
                                        ButtonCircle(
                                            size = DpSize(45.dp,45.dp),
                                            OnClick = {
                                                for (i in 0 until filteredData.value.size)
                                                {
                                                    filteredData.value.get(i).StatusSelection=true
                                                }
                                            }, roundedCornerShape = RoundedCornerShape(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_check_box_white_24),
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier
                                                //tint = if ( stepsStatus.get(index) == "Y") BlueVistony else Color.Gray
                                            )
                                        }
                                    }*/
                                    /*Spacer(modifier = Modifier.width(5.dp))
                                    Column(modifier = Modifier.weight(0.15f)) {
                                        ButtonCircle(
                                            size = DpSize(45.dp,45.dp),
                                            OnClick = {
                                                onSelectCollection(filteredData.value)
                                                statusBoolean.value = statusBoolean.value.not()
                                            }, roundedCornerShape = RoundedCornerShape(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = ImageVector.vectorResource(R.drawable.ic_menu_send),
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier
                                                //tint = if ( stepsStatus.get(index) == "Y") BlueVistony else Color.Gray
                                            )
                                        }
                                    }*/
                                }
                                    Box(
                                        modifier = Modifier
                                           // .weight(1f)
                                        //.fillMaxWidth()
                                    )
                                    {
                                        if(statusBoolean.value)
                                        {
                                            LazyColumn(
                                                modifier = Modifier
                                                //.weight(1f)
                                                //.fillMaxWidth()
                                            ) {
                                                itemsIndexed(
                                                    //collectionDetail.value.data
                                                    filteredData.value
                                                ) { index, line ->
                                                    Card(
                                                        elevation = 4.dp, modifier = Modifier
                                                            //.fillMaxWidth()
                                                            .padding(10.dp)
                                                    )
                                                    {
                                                        Log.e(
                                                            "REOS",
                                                            "DepositTemplate-StageOneDeposit-line.StatusSelection" + line.StatusSelection
                                                        )
                                                        //line.StatusSelection
                                                        var isChecked: MutableState<Boolean> =
                                                            remember { mutableStateOf(line.StatusSelection) }
                                                        var expandedCard by remember {
                                                            mutableStateOf(
                                                                false
                                                            )
                                                        }

                                                        if (isChecked.value != line.StatusSelection) {
                                                            line.StatusSelection = isChecked.value
                                                            QuantitySelected = 0
                                                            AmountSelected = 0f
                                                            for (i in 0 until filteredData.value!!.size) {
                                                                if (filteredData.value.get(
                                                                        i
                                                                    ).StatusSelection
                                                                ) {
                                                                    QuantitySelected++
                                                                    AmountSelected =
                                                                        AmountSelected + filteredData.value.get(
                                                                            i
                                                                        ).AmountCharged.toFloat()
                                                                    amountDeposit.value=AmountSelected.toString()
                                                                }
                                                            }
                                                        }
                                                        Column(
                                                            modifier = Modifier
                                                                .padding(15.dp)
                                                            //.fillMaxWidth()
                                                        ) {

                                                            Row(
                                                                modifier = Modifier
                                                                    .clickable {
                                                                        expandedCard =
                                                                            expandedCard.not()
                                                                    }
                                                            ) {
                                                                TableCell(
                                                                    text = ""+line.CardName,
                                                                    title = true,
                                                                    textAlign = TextAlign.Center,
                                                                    weight = 1f
                                                                )
                                                            }
                                                            Row() {
                                                                TableCell(
                                                                    text = "Tipo",
                                                                    title = false,
                                                                    textAlign = TextAlign.Start,
                                                                    weight = 0.2f,
                                                                    color = Color.Gray
                                                                )
                                                                Spacer(modifier = Modifier.width(10.dp))
                                                                TableCell(
                                                                    text = line.U_VIS_Type!!,
                                                                    title = true,
                                                                    textAlign = TextAlign.Start,
                                                                    weight = 0.3f
                                                                )
                                                                Spacer(modifier = Modifier.width(10.dp))
                                                                TableCell(
                                                                    text = "Depositar?",
                                                                    title = false,
                                                                    textAlign = TextAlign.Start,
                                                                    weight = 0.3f,
                                                                    color = Color.Gray
                                                                )
                                                                Spacer(modifier = Modifier.width(10.dp))
                                                                SwitchExample(
                                                                    isChecked = isChecked,
                                                                )
                                                                Log.e(
                                                                    "REOS",
                                                                    "DepositTemplate-StageOneDeposit-index" + index
                                                                )
                                                                Log.e(
                                                                    "REOS",
                                                                    "DepositTemplate-StageOneDeposit-SwitchExample-isChecked.value" + isChecked.value
                                                                )
                                                            }
                                                            Row(
                                                                //horizontalArrangement = Arrangement.Start,
                                                                modifier = Modifier.fillMaxWidth()
                                                            ) {
                                                                Column(
                                                                    horizontalAlignment = Alignment.End,
                                                                    modifier = Modifier.weight(0.5f)
                                                                ) {
                                                                    Cell(
                                                                        text = "Recibo",
                                                                        title = false,
                                                                        Color.Gray
                                                                    )
                                                                    Cell(
                                                                        text = line.Receip.toString(),
                                                                        title = true,
                                                                        textAlign = TextAlign.End
                                                                    )
                                                                }
                                                                Spacer(modifier = Modifier.width(10.dp))
                                                                Column(
                                                                    horizontalAlignment = Alignment.End,
                                                                    modifier = Modifier.weight(0.5f)
                                                                )
                                                                {
                                                                    Cell(
                                                                        text = "Cobranza",
                                                                        title = false,
                                                                        Color.Gray
                                                                    )
                                                                    Cell(
                                                                        text = Convert.currencyForView(
                                                                            line.AmountCharged
                                                                        ),
                                                                        title = true,
                                                                        textAlign = TextAlign.End
                                                                    )
                                                                }
                                                            }
                                                            //Cell(text = line.U_VIS_Type!!, title = true)
                                                            AnimatedVisibility(
                                                                visible = expandedCard,
                                                                enter = expandIn(),
                                                                exit = shrinkOut()
                                                            )
                                                            {
                                                                Spacer(modifier = Modifier.height(5.dp))
                                                                Divider()
                                                                Row(
                                                                    //horizontalArrangement = Arrangement.Start,
                                                                    modifier = Modifier.fillMaxWidth()
                                                                ) {
                                                                    Column(
                                                                        horizontalAlignment = Alignment.End,
                                                                        modifier = Modifier.weight(
                                                                            0.5f
                                                                        )
                                                                    ) {
                                                                        Cell(
                                                                            text = "Importe",
                                                                            title = false,
                                                                            Color.Gray
                                                                        )
                                                                        Cell(
                                                                            text = Convert.currencyForView(
                                                                                line.DocTotal
                                                                            ),
                                                                            title = true,
                                                                            textAlign = TextAlign.End
                                                                        )
                                                                        Spacer(
                                                                            modifier = Modifier.height(
                                                                                5.dp
                                                                            )
                                                                        )
                                                                        Cell(
                                                                            text = "Saldo",
                                                                            title = false,
                                                                            Color.Gray
                                                                        )
                                                                        Cell(
                                                                            text = Convert.currencyForView(
                                                                                line.Balance
                                                                            ),
                                                                            title = true,
                                                                            textAlign = TextAlign.End
                                                                        )
                                                                    }
                                                                    Spacer(
                                                                        modifier = Modifier.width(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    Column(
                                                                        horizontalAlignment = Alignment.End,
                                                                        modifier = Modifier.weight(
                                                                            0.5f
                                                                        )
                                                                    ) {
                                                                        Cell(
                                                                            text = "Documento",
                                                                            title = false,
                                                                            Color.Gray
                                                                        )
                                                                        Cell(
                                                                            text = line.LegalNumber!!,
                                                                            title = true,
                                                                            textAlign = TextAlign.End
                                                                        )
                                                                        Spacer(
                                                                            modifier = Modifier.height(
                                                                                5.dp
                                                                            )
                                                                        )
                                                                        Cell(
                                                                            text = "Nuevo Saldo",
                                                                            title = false,
                                                                            Color.Gray
                                                                        )
                                                                        Cell(
                                                                            text = Convert.currencyForView(
                                                                                line.NewBalance
                                                                            ),
                                                                            title = true,
                                                                            textAlign = TextAlign.End
                                                                        )
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    Spacer(modifier = Modifier.height(20.dp))
                                                }
                                            }
                                            //FloatingButtonMenuPreview()
                                            /*Box(
                                                modifier = Modifier.fillMaxSize(),
                                                contentAlignment = Alignment.TopStart
                                            )
                                            {
                                                ButtonCircle(
                                                    OnClick = {
                                                        //onSelectCollection(filteredData.value)
                                                        //statusBoolean.value = statusBoolean.value.not()
                                                        for (i in 0 until filteredData.value.size)
                                                        {
                                                            filteredData.value.get(i).StatusSelection=true
                                                        }
                                                    },
                                                    size = DpSize(40.dp, 40.dp),
                                                    color = Color.Red
                                                )
                                                {
                                                    Icon(
                                                        imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_check_box_red_24),
                                                        //ImageVector.vectorResource(if (statusList.get(index) == "Y"){R.drawable.ic_check}else{R.drawable.ic_baseline_close_24_white}),
                                                        contentDescription = null,
                                                        tint = Color.White,
                                                        modifier = Modifier.align(Alignment.Center)
                                                        //tint = if ( stepsStatus.get(index) == "Y") BlueVistony else Color.Gray
                                                    )
                                                }
                                            }*/
                                        }
                                    }
                                //}
                                // Información que siempre aparecerá en la parte inferior de la pantalla
                                totalQuantity.value="${
                                    if (filteredData.value.isNullOrEmpty()) {
                                        0
                                    } else {
                                        filteredData.value.size
                                    }
                                }"
                                selectedQuantity.value=QuantitySelected.toString()
                                amount.value = AmountSelected.toString()
                                /*Column(
                                    modifier = Modifier
                                        //.fillMaxWidth()
                                        .background(BlueVistony, RoundedCornerShape(4.dp))
                                    //.padding(16.dp)
                                ) {
                                    Row() {
                                        TableCell(
                                            text = "Cantidad documentos: ",
                                            weight = 1f,
                                            title = true,
                                            color = Color.White,
                                            textAlign = TextAlign.Start
                                        )
                                        TableCell(
                                            text = "${
                                                if (filteredData.value.isNullOrEmpty()) {
                                                    0
                                                } else {
                                                    filteredData.value.size
                                                }
                                            }",
                                            weight = 1f,
                                            title = true,
                                            textAlign = TextAlign.End,
                                            color = Color.White,
                                        )
                                    }

                                    Row() {
                                        TableCell(
                                            text = "Cantidad seleccionada: ",
                                            weight = 1f,
                                            title = true,
                                            textAlign = TextAlign.Start,
                                            color = Color.White,
                                        )
                                        TableCell(
                                            text = QuantitySelected.toString(),
                                            weight = 1f,
                                            title = true,
                                            textAlign = TextAlign.End,
                                            color = Color.White,
                                        )
                                    }

                                    Row() {
                                        TableCell(
                                            text = "Monto seleccionado: ",
                                            weight = 1f,
                                            title = true,
                                            textAlign = TextAlign.Start,
                                            color = Color.White,
                                        )
                                        TableCell(
                                            text = AmountSelected.toString(),
                                            weight = 1f,
                                            title = true,
                                            textAlign = TextAlign.End,
                                            color = Color.White,
                                        )
                                    }
                                }*/
                                AnimatedVisibility(
                                    visible = statusBoolean.value,
                                    enter = expandIn(),
                                    exit = shrinkOut()
                                )
                                {
                                    Column() {
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Row() {

                                            ButtonView(
                                                description = "Anterior",
                                                OnClick = { /*TODO*/ },
                                                context = contexto,
                                                backGroundColor = Color.Gray,
                                                textColor = Color.White
                                            )
                                            Spacer(modifier = Modifier.width(20.dp))
                                            ButtonView(
                                                description = "Siguente",
                                                OnClick = {
                                                    //ExpandableInvoices()
                                                    onSelectCollection(filteredData.value)
                                                    statusBoolean.value = statusBoolean.value.not()
                                                },
                                                context = contexto,
                                                backGroundColor = RedVistony,
                                                textColor = Color.White,
                                                status = true
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            //}
        }
         ,cardBottom =
         {
         }
     )
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun StageTwoDeposit(
    context:Context,
    bankViewModel: BankViewModel,
    statusBoolean: MutableState<Boolean>,
    activity:Activity,
    headerDispatchSheetViewModel: HeaderDispatchSheetViewModel,
    amountDeposit: MutableState<String>,
    typeCollection: MutableState<String>,
    collectionHeadViewModel:CollectionHeadViewModel,
    collectionDetailList:MutableState<List<CollectionDetail>>,
    collectionDetailViewModel:CollectionDetailViewModel
)
{
    var DateApp: MutableState<String> = remember { mutableStateOf(getDate()!!) }
    var DialogEditStatus by remember { mutableStateOf(true) }
    var DialogEditResult :MutableState<String> = remember { mutableStateOf("0") }
    var DialogShowEditText by remember { mutableStateOf(false) }
    bankViewModel.getBanks()
    var banks=bankViewModel.resultDB.collectAsState()
    var banksList: MutableList<String> = mutableListOf()
    val currentSelectionSpinner = remember {
        mutableStateOf("SELECCIONAR BANCO")
    }
    val typeDepositList = listOf( "DE-DEPOSITO","CH-CHEQUE")
    val currentSelectionSpinner2 = remember { mutableStateOf("SELECCIONAR TIPO DEPOSITO") }
    var DialogShowCaptureImage by remember { mutableStateOf(false) }
    val bitmapLocale = remember { mutableStateOf<Bitmap?>(null) }
    val openDialogShowDispatch:MutableState<Boolean?> = remember { mutableStateOf(false) }
    val tittleDialogDispatch:MutableState<String> = remember { mutableStateOf("Codigo Despacho") }
    val currentDispatchSelected = remember { mutableStateOf("SELECCIONAR CODIGO DEPOSITO") }
    val headerDispatch=headerDispatchSheetViewModel.resultDB.collectAsState()
    var headerDispatchlist: MutableList<String> = mutableListOf()
    var colorButtonAfter by remember{ mutableStateOf(Color.Red)}
    var colorButtonSave by remember{ mutableStateOf(Color.Red)}
    var statusButtonSave by remember{ mutableStateOf(true)}
    var colorButtonCapture by remember{ mutableStateOf(Color.Red)}
    var statusButtonCapture by remember{ mutableStateOf(true)}
    var colorButtonSync by remember{ mutableStateOf(Color.Red)}
    var statusButtonSync by remember{ mutableStateOf(true)}
    when (banks.value.Status)
    {
        "Y"->{
            for (i in 0 until banks.value.Data!!.size) {
                banksList.add(banks.value.Data!!.get(i).BankId+"-"+banks.value.Data!!.get(i).BankName)
            }
        }
    }

    when(headerDispatch.value.status)
    {
        "Y"->{
            for (i in 0 until headerDispatch.value.data!!.size) {
                headerDispatchlist.add(headerDispatch.value.data!!.get(i).control_id.toString())
            }
        }
    }

    Log.e("REOS", "DepositTemplate-StageTwoDeposit-collectionDetailList:" + collectionDetailList)

    if(DialogShowEditText)
    {

        DialogView(
            "N° Operación", "",
            onClickCancel = {
                DialogShowEditText = false
            }, onClickAccept = {
                DialogShowEditText = false
            }, statusButtonAccept = true, statusButtonIcon = false, context = context
        ) {
            Editext(
                DialogEditStatus,
                DialogEditResult,
                "Ingrese N° Operacion",
                "N° de Operacion",
                painterResource(id = R.drawable.ic_baseline_numbers_24),
                KeyboardType.Number
            )
        }
    }

    if(DialogShowCaptureImage)
    {
        /*CaptureImage(
                resultBitmap = { result ->
                    bitmapLocale.value=result
                },
                context,
                activity,
                "DEP",
                "G"
        )*/
        /*CaptureImageSave(context,activity,"DEP", getDateCurrent()!!,
                resultBitmap = { result ->
            bitmapLocale.value=result
                               }
                ,)*/
        CaptureImageAndRetrieveBitmap(
                activity = activity,
                context,
                "DEP",
                getDateCurrent()!!


        ) { bitmap ->
            bitmapLocale.value = bitmap
        }
    }

    if(openDialogShowDispatch.value!!)
    {

        DialogView(
                tittle = tittleDialogDispatch.value
                , subtittle = ""
                ,onClickCancel = {
            openDialogShowDispatch.value = false
        }
                ,onClickAccept = {
            //openDialogShowDispatch.value = false
            DialogEditResult.value=currentDispatchSelected.value
            openDialogShowDispatch.value = false

        }
                ,statusButtonAccept = true
                ,statusButtonIcon = false
                ,context=context
        ){
            Column() {
                TextLabel(text = "Elegir una fecha para su consulta")
                Row() {
                    Column(modifier = Modifier.weight(1f)) {
                        //CalendarAppView(tittle = "Elegir una fecha para su consulta", DateApp = DateApp)
                        CalendarApp(
                                DateApp = DateApp
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(modifier = Modifier.weight(0.15f)) {
                        ButtonCircle(
                                OnClick = {
                                    /*collectionDetailViewModel.getCollectionDetailPendingDeposit(
                                            DateApp.value
                                    )*/
                                    headerDispatchSheetViewModel.getMasterDispatchSheetDB(DateApp.value)
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
                Spacer(modifier = Modifier.height(5.dp))
                if(headerDispatchlist.size>0)
                {
                    SpinnerView(
                            "Seleccione el codigo de deposito: ", headerDispatchlist, currentDispatchSelected
                    )
                }
            }
        }
    }


    CardView(
        cardtTittle =
        {
            Column(
                modifier = Modifier.padding(10.dp)
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ButtonCircle(OnClick = {}, color = BlueVistony) {
                        Text(
                            text = "2",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    }
                    TableCell(
                        text = "REGISTRO DE DEPOSITO",
                        weight = 1f,
                        title = true,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        , cardContent =
        {
            AnimatedVisibility(
                visible = statusBoolean.value.not(),
                enter = expandIn(),
                exit = shrinkOut()
            ) {
                Column() {
                    Row() {
                        TableCell(
                            text = "N° Operacion",
                            weight = 1f,
                            color = Color.Gray,
                            title = false,
                            textAlign = TextAlign.Start
                        )
                    }
                    Row() {
                        Column(modifier = Modifier.weight(0.85f)) {
                            ButtonSurface(
                                description = DialogEditResult.value,
                                OnClick = { },
                                status = true,
                                IconActive = false,
                                context = context,
                                backGroundColor = Color.White,
                                textColor = Color.Black,
                                height = 45.dp
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Column(modifier = Modifier.weight(0.15f)) {
                            ButtonCircle(
                                size = DpSize(45.dp, 45.dp),
                                OnClick = {
                                    //collectionDetailViewModel.getCollectionDetailPendingDeposit(
                                    //DateApp.value
                                    DialogShowEditText = true
                                    //)
                                }, roundedCornerShape = RoundedCornerShape(4.dp)
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_edit_24_white),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                    //tint = if ( stepsStatus.get(index) == "Y") BlueVistony else Color.Gray
                                )
                            }
                        }
                    }
                    Row() {
                        SpinnerView(
                            "Seleccione el banco: ", banksList, currentSelectionSpinner
                        )
                    }
                    Row() {
                        SpinnerView(
                            "Seleccione el tipo de deposito: ",
                            typeDepositList,
                            currentSelectionSpinner2
                        )
                    }
                    if (currentSelectionSpinner2.value.equals("CH-CHEQUE")) {
                        TextLabel(text = "Elegir una fecha para el cheque")
                        CalendarApp(
                            DateApp = DateApp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(0.5f)
                        ) {
                            /*ButtonCircle(
                                OnClick = {
                                   // DialogShowCaptureImage=true

                                },
                                size = DpSize(50.dp, 50.dp),
                                //color = colorButtonSave.value,
                                color = BlueVistony,
                                roundedCornerShape = CircleShape
                            )
                            {
                                Icon(
                                    ImageVector.vectorResource(R.drawable.ic_baseline_camera_alt_black_24),
                                    tint = Color.White,
                                    contentDescription = null
                                )
                            }*/

                            CaptureImageCircle(
                                    resultBitmap = { result ->
                                        if(statusButtonCapture)
                                        {
                                            bitmapLocale.value=result
                                        }else{
                                            Toast.makeText(context,"El boton esta deshabilitado",Toast.LENGTH_LONG).show()
                                        }
                                    },
                                    context,
                                    activity,
                                    "DEP",
                                    "G",
                                color = colorButtonCapture
                            )
                            Text(
                                text = "Capturar",
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                //modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(0.5f)
                        ) {
                            ButtonCircle(
                                OnClick = {
                                    if(statusButtonSync) {
                                        openDialogShowDispatch.value=true
                                    }else{
                                        Toast.makeText(context,"El boton esta deshabilitado",Toast.LENGTH_LONG).show()
                                    }
                                },
                                size = DpSize(50.dp, 50.dp),
                                //color = colorButtonSave.value,
                                color = colorButtonSync,
                                roundedCornerShape = CircleShape
                            )
                            {
                                Icon(
                                    ImageVector.vectorResource(R.drawable.ic_baseline_electric_rickshaw_24),
                                    tint = Color.White,
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = "Vincular",
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                //modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(0.5f)
                            //.align(Alignment.CenterVertically)
                        ) {
                            ButtonCircle(
                                OnClick = {
                                    if(statusButtonSave) {
                                        var collectionHead=CreateCollectionHead(
                                            context,
                                            amountDeposit.value,
                                            DialogEditResult.value,
                                            DateApp.value,
                                            currentSelectionSpinner2.value,
                                            currentSelectionSpinner.value,
                                            typeCollection.value
                                        )
                                        collectionHeadViewModel.addCollectionHead(collectionHead)
                                        collectionHeadViewModel.sendAPICollectionHead()
                                        collectionDetailViewModel.updateDepositCollectionDetail(collectionDetailList.value,DialogEditResult.value,currentSelectionSpinner.value)
                                        collectionDetailViewModel.sendAPIUpdateDepositCollectionDetail()
                                        Toast.makeText(context,"Deposito Registrado Correctamente", Toast.LENGTH_SHORT).show()
                                        colorButtonAfter=Color.Gray
                                        colorButtonSave=Color.Gray
                                        statusButtonSave=false
                                        colorButtonCapture=Color.Gray
                                        statusButtonCapture=false
                                        colorButtonSync=Color.Gray
                                        statusButtonSync=false
                                    }
                                },
                                size = DpSize(50.dp, 50.dp),
                                //color = colorButtonPrint,
                                color = colorButtonSave,
                                roundedCornerShape = CircleShape
                            )
                            {
                                Icon(
                                    ImageVector.vectorResource(R.drawable.ic_save_black_24dp),
                                    tint = Color.White,
                                    contentDescription = null
                                )
                            }
                            Text(
                                text = "Guardar",
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                //modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }

        }
        ,   cardBottom =
        {
            AnimatedVisibility(
                visible = statusBoolean.value.not(),
                enter = expandIn(),
                exit = shrinkOut()
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                Row() {

                    ButtonView(
                        description = "Anterior",
                        OnClick = { statusBoolean.value = statusBoolean.value.not() },
                        context = contexto,
                        backGroundColor = colorButtonAfter,
                        textColor = Color.White,
                        status = true
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    ButtonView(
                        description = "Siguente",
                        OnClick = {
                            //ExpandableInvoices()
                            //expanded = false
                        },
                        context = contexto,
                        backGroundColor = Color.Gray,
                        textColor = Color.White,
                        //status = true
                    )
                }
            }
        }
    )
}


@Composable
fun FloatingButtonMenu() {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        /*contentAlignment = if (isMenuExpanded) {
            Alignment.TopEnd
        } else {
            Alignment.BottomEnd
        }*/
                contentAlignment=Alignment.BottomStart
        ,
    ) {
        Column {
            if (isMenuExpanded) {
                // Aquí puedes agregar los botones del menú
                ExtendedFloatingActionButton(
                    onClick = {
                        // Acción cuando se hace clic en el primer botón del menú
                    },
                    icon = {
                        /*Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar"
                        )*/
                        /*Box(
                            modifier = Modifier
                                .size(60.dp) // Ajusta el tamaño del icono según tus necesidades
                                .padding(8.dp), // Ajusta el relleno del icono según tus necesidades
                            contentAlignment = Alignment.Center
                        ) {
                            // El icono está centrado en un contenedor
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Agregar"
                            )
                        }*/
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(Color.Red, CircleShape)
                                .clickable { }
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Agregar"
                            )
                        }
                    },
                    text = {
                        //Text("Agregar")
                        /*Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar"
                        )*/
                           },
                    /*shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
                    modifier =
                    Modifier.padding(16.dp).clip(CircleShape)
                            .size(50.dp) // E // Aplicar una forma circular al botón*/
                    shape = CircleShape, // Utiliza CircleShape para la forma circular
                    modifier = Modifier
                        .padding(16.dp)
                        .size(50.dp) // Asegúrate de que el tamaño sea igual en ancho y alto
                )
            }

            // Botón principal que expande o contrae el menú
            ExtendedFloatingActionButton(
                onClick = {
                    isMenuExpanded = !isMenuExpanded
                },
                icon = {
                    /*Icon(
                        imageVector = if (isMenuExpanded) Icons.Default.Add else Icons.Default.Close,
                        contentDescription = if (isMenuExpanded) "Expandir" else "Contraer"
                    )*/
                    /*Box(
                        modifier = Modifier
                            .size(60.dp) // Ajusta el tamaño del icono según tus necesidades
                            .padding(8.dp), // Ajusta el relleno del icono según tus necesidades
                        contentAlignment = Alignment.Center
                    ) {
                        // El icono está centrado en un contenedor
                        Icon(
                            imageVector = if (isMenuExpanded) Icons.Default.Add else Icons.Default.Close,
                            contentDescription = if (isMenuExpanded) "Expandir" else "Contraer"
                        )
                    }*/
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color.Red, CircleShape)
                            .clickable { }
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isMenuExpanded) Icons.Default.Add else Icons.Default.Close,
                            contentDescription = if (isMenuExpanded) "Expandir" else "Contraer"
                        )
                    }
                },
                text = {
                    //Text(if (isMenuExpanded) "Cerrar" else "Abrir")
                    /*Icon(
                    imageVector = if (isMenuExpanded) Icons.Default.Add else Icons.Default.Close,
                    contentDescription = if (isMenuExpanded) "Expandir" else "Contraer"
                )*/
                       },
                /*text = {
                    Text(if (isMenuExpanded) "Cerrar" else "Abrir")
                },*/
                backgroundColor = if (isMenuExpanded) BlueVistony else Color.Red,
                //shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 100)),
                /*modifier = Modifier.padding(16.dp).clip(CircleShape)
                    .size(50.dp) // E // Aplicar una forma circular al botón*/
                // Aplicar una forma circular al botón
                shape = CircleShape, // Utiliza CircleShape para la forma circular
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp) // Asegúrate de que el tamaño sea igual en ancho y alto
            )
        }
    }
}

@Preview
@Composable
fun FloatingButtonMenuPreview() {
    FloatingButtonMenu()
}

@Composable
fun BottomBarDeposit(
    context:Context,
    totalQuantity:MutableState<String>,
    selectionQuantity:MutableState<String>,
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
                .weight(0.7f)
                .padding(10.dp, 0.dp)

        ) {
            Cell(
                text = "Cantidad Total" ,
                title = false,
                color = Color.White,
                fontSise = 20.sp
            )
            Cell(
                text = "Cantidad Seleccionada" ,
                title = false,
                color = Color.White,
                fontSise = 20.sp
            )
            Cell(
                text = "Monto Seleccionado" ,
                title = false,
                color = Color.White,
                fontSise = 20.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .weight(0.3f)
                .padding(10.dp, 0.dp)
        ) {
            Cell(
                text = totalQuantity.value,
                title = true,
                color = Color.White,
                fontSise = 20.sp
            )
            Cell(
                text = selectionQuantity.value,
                title = true,
                color = Color.White,
                fontSise = 20.sp
            )
            Cell(
                text = Convert.currencyForView(if(amount.value==null){"0"}else{amount.value}) ,
                title = true,
                color = Color.White,
                fontSise = 20.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}