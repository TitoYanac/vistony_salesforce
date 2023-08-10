package com.vistony.salesforce.kotlin.compose.components

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Controller.Utilitario.Induvis
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.compose.DialogMain
import com.vistony.salesforce.kotlin.compose.MyUI
import com.vistony.salesforce.kotlin.compose.theme.BlueVistony
import com.vistony.salesforce.kotlin.compose.theme.RedVistony
import com.vistony.salesforce.kotlin.compose.theme.Shapes
import com.vistony.salesforce.kotlin.data.*
import com.vistony.salesforce.kotlin.utilities.*
import io.sentry.protocol.App
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun SheetLayout(currentScreen: BottomSheetScreen,onCloseBottomSheet :()->Unit,showIconClose:Boolean=true) {
    BottomSheetWithCloseDialog(onCloseBottomSheet,showIconClose=showIconClose){
        when(currentScreen){
            is BottomSheetScreen.collectionDetailBottom ->
                Expandable(cliente_id=currentScreen.cliente_id
                    //,Imei=currentScreen.Imei,appContext=currentScreen.appContext,lifecycleOwner=currentScreen.lifecycleOwner,invoicesRepository=currentScreen.invoicesRepository
                        ,invoiceViewModel=currentScreen.invoiceViewModel
                )
            is BottomSheetScreen.processStatusDispatch ->
                ExpandableProcessStatusDispatch(
                    detailDispatchSheet=currentScreen.detailDispatchSheet,
                    bitMapMutable=currentScreen.bitMapMutable,
                    openDialogShowImage=currentScreen.openDialogShowImage,
                    context=currentScreen.context
                )
        }
    }
    Log.e(
        "REOS",
        "BottomSheet-SheetLayout-fin"
    )
}


sealed class BottomSheetScreen() {
    class collectionDetailBottom(val cliente_id: String,val invoiceViewModel: InvoicesViewModel):BottomSheetScreen()
    class processStatusDispatch(
        val detailDispatchSheet: DetailDispatchSheet,
        var bitMapMutable: MutableState<Bitmap?>,
        var openDialogShowImage: MutableState<Boolean?>,
        var context: Context
    ):BottomSheetScreen()
}

@Composable
fun BottomSheetWithCloseDialog(onClosePressed: () -> Unit, closeButtonColor: Color = Color.Gray, showIconClose:Boolean, content: @Composable() () -> Unit){
    Box{
        content()
        if(showIconClose){
            IconButton(
                onClick = onClosePressed,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp, end = 10.dp, start = 16.dp, bottom = 16.dp)
                    .size(29.dp)
            ) {
                Icon(Icons.Filled.Close, tint = closeButtonColor, contentDescription = null)
            }
            //bottomSheetVisible=false
        }
    }
}

@Composable
private fun InvoicesList(
    invoiceViewModel: InvoicesViewModel
    ,cliente_id: String
    ,expandableCollectionProcess: (invoices:Invoices?) -> Unit
    ,statusEnable: Boolean
    ,invoicelegalnum:String
    ,onBooleanValueChanged: (Boolean) -> Unit

){
    var invoices = invoiceViewModel.invoices.collectAsState()
    var invoicesobj:Invoices?=null
    //val selectedLines = remember { mutableStateListOf<Boolean>() }
    //var isSelected by remember { mutableStateOf(false) }
    val selectedLineIndex = remember { mutableStateOf(-1) }
    Column(modifier = Modifier.padding(top = 0.dp, bottom = 0.dp)) {
        Divider()

        LazyColumn(
            modifier = Modifier
                //.fillMaxWidth()
                //.padding(0.dp,0.dp,0.dp,100.dp)
                .weight(1f),
            contentPadding = PaddingValues(10.dp)
        ) {
            itemsIndexed(invoices.value ) { index,line ->

                Log.e(
                    "REOS",
                    "BottomSheet-InvoicesList-line.isSelected"+line.isSelected
                )
                /*if (index >= selectedLines.size) {
                    selectedLines.add(false)
                }*/
                Card(

                    elevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        //.backgroundcolor(cardColor)
                        //.background(if (isSelected) Color.Blue else Color.White)
                        .clickable {
                            //line.isSelected = !line.isSelected
                            //selectedLines[index] = !selectedLines[index]
                            selectedLineIndex.value = index
                            invoicesobj = line
                            Log.e(
                                "REOS",
                                "BottomSheet-InvoicesList-line.isSelected.clickable" + line.isSelected
                            )
                        },
                    //backgroundColor = if (selectedLines[index]) Color.LightGray else Color.White,
                    backgroundColor = if (selectedLineIndex.value == index)
                                        Color.LightGray

                                        else Color.White,
                ){
                    //invoices.value = invoiceList
                    //invoices.value = invoiceList

                    Column(modifier = Modifier
                        .padding(10.dp)){
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(1f)
                            ) {
                                Text(text = "Documento", color = Color.Gray )
                                Text(text = "${line.nroFactura}",fontWeight = FontWeight.Bold)
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.5f)
                            ) {
                                Text(text = "Fecha Emisión", color = Color.Gray)
                                Text(text = "${Induvis.getDate(BuildConfig.FLAVOR,line.fechaEmision)  }", fontWeight = FontWeight.Bold)
                            }

                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.5f)
                            ) {
                                Text(text = "Fecha Vencimiento", color = Color.Gray)
                                Text(text = "${Induvis.getDate(BuildConfig.FLAVOR,line.fechaVencimiento)}", fontWeight = FontWeight.Bold)
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.5f)
                            ) {
                                Text(text = "Importe", color = Color.Gray)
                                Text(text = "${Convert.currencyForView(line.importeFactura)}", fontWeight = FontWeight.Bold)
                            }
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.5f)
                            ) {
                                Text(text = "Saldo", color = Color.Gray)
                                Text(text = "${Convert.currencyForView(line.saldo)}", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier.padding(0.dp,0.dp,0.dp,20.dp)
        )
        {
            IconButton(
                onClick = {

                },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(5.dp))
                    .background(BlueVistony)
                , enabled = false
                ,
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
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {

                    expandableCollectionProcess(invoicesobj)
                    onBooleanValueChanged(!statusEnable)
                    //invoicelegalnum=invoicesobj?.nroFactura.toString()
                    Log.e(
                        "REOS",
                        "BottomSheet-InvoicesList-line.isSelected.clickable.invoicesobj" +invoicesobj
                    )
                   // invoicesobj?.let { expandableCollectionProcess(it) }
                          },
                //colors = ButtonDefaults.buttonColors(Colors = MaterialTheme.colors.primary),
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(5.dp))
                    .background(BlueVistony)
                // , enabled = false
            ) {
                Row()
                {

                    Text(
                        text = "Siguiente",
                        color = Color.White
                    )
                    Icon(
                        ImageVector.vectorResource(R.drawable.ic_baseline_arrow_forward_24),
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            }
        }
    }
}


@Composable
fun Expandable(
    invoiceViewModel: InvoicesViewModel
    ,cliente_id: String
){
    val showDialog = remember { mutableStateOf(false) }
    val showDialogCollection = remember { mutableStateOf(false) }
    //val invoicesState = remember { mutableStateOf<Invoices?>(null) } // Variable mutable para almacenar el objeto invoices
    val selectedInvoices = remember { mutableStateOf<Invoices?>(null) }
    val typesCollectionlist = listOf( "Cobranza Ordinaria", "Deposito Directo","Pago POS", "Cobro Vendedor", "Pago Adelantado")
    val currentSelection = remember { mutableStateOf(typesCollectionlist.find { it.toUpperCase() == typesCollectionlist.toString().toUpperCase() } ?: typesCollectionlist.first()) }
    val openDialog = remember { mutableStateOf(false) }


    Column() {
        Text(
            text = cliente_id,
            modifier = Modifier.padding(start = 20.dp, bottom = 10.dp, end = 50.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        ExpandableTypeInvoices(
            invoiceViewModel
            ,cliente_id
            ,ExpandableInvoices = { showDialog.value = true }
            ,typesCollectionlist
            ,currentSelection
        )

        Log.e(
            "REOS",
            "BottomSheet-Expandable.currentSelection.value" +currentSelection.value
        )

        if (showDialog.value) {
            ExpandableInvoices(
            invoiceViewModel
            ,cliente_id
            ) { invoices ->
                selectedInvoices.value = invoices as Invoices?
                showDialog.value = true
            }
        }
        //var invoices: Invoices? =null

        /*if (showDialogCollection.value) {
            ExpandableCollectionProcess(
                invoices =
            )
        }*/
        val invoices = selectedInvoices.value
        if (invoices != null) {
            ExpandableCollectionProcess(
                invoices = invoices,
                cliente_id,
                currentSelection.value,
                InfoDialog = { openDialog.value = true },
            )
        }
        if (openDialog.value)
        {
            MyUI(onDismiss = {
                openDialog.value = false
            })
        }



        var progress:Float=0.6f
        LinearProgressIndicator(
            progress = progress
            ,
            color = BlueVistony,
            backgroundColor = Color.Gray, // el color de fondo siempre será gris
            //progress = 0.3f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp, 0.dp, 20.dp)
        )
    }
}

@Composable
fun ExpandableTypeInvoices(
    invoiceViewModel: InvoicesViewModel
    ,cliente_id: String
    ,ExpandableInvoices: () -> Unit
    ,listTypeCollecion: List<String>
    ,currentSelected:MutableState<String>
) {
    Column(modifier = Modifier.padding(top = 0.dp, bottom = 0.dp))
    {
        var expanded by remember { mutableStateOf(false) }
        Card(
            //modifier = Modifier
                //.clickable { expanded = !expanded },
            //.padding(20.dp),
            elevation = 4.dp
        ) {

            Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
            {
                Row(
                    modifier = Modifier
                        .padding(0.dp, 5.dp, 10.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(BlueVistony, CircleShape)
                    ) {
                        Text(
                            text = "1",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        "SELECCIONE TIPO DE FACTURA:",
                        modifier = Modifier.padding(0.dp, 5.dp, 10.dp, 0.dp),
                        color = Color.Black,
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector =
                        if (expanded) {
                            ImageVector.vectorResource(R.drawable.ic_baseline_arrow_drop_up_24_black)
                        } else {
                            ImageVector.vectorResource(R.drawable.ic_baseline_arrow_drop_down_24_black)
                        },
                        contentDescription = null,
                        modifier =
                        Modifier
                            .padding(0.dp, 5.dp, 0.dp, 0.dp)
                            .clickable { expanded = !expanded }
                        //tint = Color.White,

                    )
                }
                Row(
                    modifier = Modifier
                        .padding(0.dp, 5.dp, 10.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        "TIPO:",
                        modifier = Modifier.padding(0.dp, 5.dp, 10.dp, 0.dp),
                        color = Color.Black,
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        currentSelected.value.toUpperCase(),
                        modifier = Modifier.padding(0.dp, 5.dp, 10.dp, 0.dp),
                        color = Color.Black,
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Bold
                    )
                }
                AnimatedVisibility(
                    visible = expanded,
                    enter = expandIn(),
                    exit = shrinkOut()
                ) {
                    Column()
                    {
                        RadioBtn(
                            listTypeCollecion
                        ,currentSelected
                        )
                        Log.e(
                            "REOS",
                            "BottomSheet-ExpandableTypeInvoices.RadioBtn.currentSelected" +currentSelected
                        )
                        //{ currentSelected=it }
                        Spacer(modifier = Modifier.height(24.dp))
                        Row()
                        {
                            IconButton(
                                onClick = {
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(Color.Gray)
                                , enabled = false
                            ,
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
                                        color = Color.White
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = {
                                    ExpandableInvoices()
                                    expanded=false
                                },
                                //colors = ButtonDefaults.buttonColors(Colors = MaterialTheme.colors.primary),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(BlueVistony)
                               // , enabled = false
                            ) {
                                Row()
                                {

                                    Text(
                                        text = "Siguiente",
                                        color = Color.White
                                    )
                                    Icon(
                                        ImageVector.vectorResource(R.drawable.ic_baseline_arrow_forward_24),
                                        tint = Color.White,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun ExpandableInvoices(
    invoiceViewModel: InvoicesViewModel,
    cliente_id: String,
    expandableCollectionProcess: (Any?) -> Unit,
) {
    Column(
    )
    {
        var invoicelegalnum by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(true) }
        //var invoicelegalnum by remember { mutableStateOf("") }

        Card(
            //modifier = Modifier
                //.clickable { expanded = !expanded },
            //.padding(20.dp),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
            {
                Row(
                    modifier = Modifier
                        .padding(0.dp, 5.dp, 10.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(BlueVistony, CircleShape)
                    ) {
                        Text(
                            text = "2",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        "SELECCIONE FACTURA:",
                        modifier = Modifier.padding(0.dp, 5.dp, 10.dp, 0.dp)
                        ,
                        color = Color.Black,
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector =
                        if (expanded) {
                            ImageVector.vectorResource(R.drawable.ic_baseline_arrow_drop_up_24_black)
                        } else {
                            ImageVector.vectorResource(R.drawable.ic_baseline_arrow_drop_down_24_black)
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .padding(0.dp, 5.dp, 0.dp, 0.dp)
                            .clickable { expanded = !expanded },
                        //tint = Color.White,
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(0.dp, 5.dp, 10.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        "FACTURA:",
                        modifier = Modifier.padding(0.dp, 5.dp, 10.dp, 0.dp)
                        ,
                        color = Color.Black,
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        invoicelegalnum
                        ,modifier = Modifier.padding(0.dp, 5.dp, 10.dp, 0.dp)
                        ,
                        color = Color.Black,
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Bold
                    )
                }
                AnimatedVisibility(
                    visible = expanded,
                    enter = expandIn(),
                    exit = shrinkOut()
                ) {
                    Column()
                    {
                        //var invoices:Invoices?=null
                        InvoicesList(
                            invoiceViewModel
                            , cliente_id
                            ,expandableCollectionProcess= {
                                    invoices -> expandableCollectionProcess(invoices)
                                invoicelegalnum=invoices?.nroFactura.toString()
                                                          }
                            ,expanded
                            ,invoicelegalnum
                        ){
                            expanded = it
                            Log.e(
                                "REOS",
                                "BottomSheet-ExpandableInvoices-expanded" +expanded
                            )
                        }
                    }
                }
            }
        }
    }
}






@Composable
fun ExpandableCollectionProcess(
    invoices: Invoices?,
    client:String,
    type: String,
    InfoDialog: () -> Unit
) {
    Log.e(
        "REOS",
        "BottomSheet-ExpandableCollectionProcess-invoices" +invoices
    )
    Column(
        //modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
    )
    {
        //Divider()
        var expanded by remember { mutableStateOf(true) }
        Card(
            modifier = Modifier
             //   .clickable { expanded = !expanded },
            //.padding(20.dp),
                ,elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
            {
                Row(
                    modifier = Modifier
                        .padding(0.dp, 5.dp, 10.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(BlueVistony, CircleShape)
                    ) {
                        Text(
                            text = "3",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        "GENERACION COBRANZA:",
                        modifier = Modifier.padding(0.dp, 5.dp, 10.dp, 0.dp)
                        ,
                        color = Color.Black,
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector =
                        if (expanded) {
                            ImageVector.vectorResource(R.drawable.ic_baseline_arrow_drop_up_24_black)
                        } else {
                            ImageVector.vectorResource(R.drawable.ic_baseline_arrow_drop_down_24_black)
                        },
                        contentDescription = null,
                        modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)
                        //tint = Color.White,
                    )

                }

                AnimatedVisibility(
                    visible = expanded,
                    enter = expandIn(),
                    exit = shrinkOut()
                ) {
                    Column()
                    {
                        CardProcessCollection(
                            invoices,type,client,
                            //invoiceViewModel, cliente_id
                            //,Imei: String,appContext: Context,lifecycleOwner:LifecycleOwner,invoicesRepository: InvoicesRepository
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CardProcessCollection(
    invoices: Invoices?
    ,type: String
    ,cardName:String
){

    var textNumber by remember { mutableStateOf( "0") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var enableEditext by remember { mutableStateOf( true) }
    var colorEditext by remember { mutableStateOf(BlueVistony)}
    var colorButtonSave by remember { mutableStateOf(Color.LightGray)}
    var colorButtonPrint by remember { mutableStateOf(Color.LightGray)}
    var colorButtonValidate by remember { mutableStateOf(Color.LightGray)}
    var enableButtonSave by remember { mutableStateOf(false)}
    var enableButtonPrint by remember { mutableStateOf(false)}
    var enableButtonValidate by remember { mutableStateOf(false)}
    var newBalance by remember { mutableStateOf(CalculateNewBalance(invoices?.saldo.toString(),textNumber))}
    val appContext = LocalContext.current
    //val lifecycleOwner = LocalContext.current as LifecycleOwner
    val collectionDetailRepository :CollectionDetailRepository= CollectionDetailRepository()
    val collectionDetailViewModel: CollectionDetailViewModel= viewModel(
        factory = CollectionDetailViewModel.CollectionDetailViewModelFactory(
            SesionEntity.imei,
            appContext,
            //lifecycleOwner,
            collectionDetailRepository
        )
    )
    val commentary:String=""
    var collectionDetail: CollectionDetail? = null
    var collectionDetailResponseAdd = collectionDetailViewModel.result_add.collectAsState()
    val activity = LocalContext.current as Activity

    Log.e(
        "REOS",
        "BottomSheet-CardProcessCollection-collectionDetailResponseAdd.value: "+collectionDetailResponseAdd.value
    )
    if(collectionDetailResponseAdd.value.Status.equals("Y"))
    {
        colorButtonSave=Color.Gray
        enableButtonSave=false
        colorButtonValidate= BlueVistony
        colorButtonPrint= BlueVistony
        enableButtonPrint=true
        enableButtonValidate=true
        collectionDetailViewModel.SendAPICollectionDetail(appContext,SesionEntity.compania_id,SesionEntity.usuario_id)
        Log.e(
            "REOS",
            "BottomSheet-CardProcessCollection-collectionDetailResponseAdd.EntroalIF.alterminarlaInsercion "
        )
    }
    var expanded by remember { mutableStateOf(true) }
    Column(modifier = Modifier.padding(top = 0.dp, bottom = 0.dp)) {
        Divider()
        AnimatedVisibility(
            visible = expanded,
            enter = expandIn(),
            exit = shrinkOut()
        ) {
                Card(
                    elevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ){
                    Column(modifier = Modifier
                        .padding(10.dp)){
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.5f)
                            ) {
                                Text(text = "Saldo", color = Color.Gray)
                                Text(text = "${Convert.currencyForView(invoices?.saldo)}", fontWeight = FontWeight.Bold)
                            }
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.5f)
                            ) {
                                Text(text = "Nuevo Saldo", color = Color.Gray)
                                newBalance=CalculateNewBalance(invoices?.saldo.toString(),textNumber)
                                "${Convert.currencyForView(newBalance)}"
                                    ?.let {
                                    Text(text =
                                    //"${Convert.currencyForView(invoices?.saldo)}"
                                    it, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                enabled= enableEditext ,
                                singleLine=true,
                                value = textNumber,
                                onValueChange = {
                                    //if(!it.contains("B") ) {textNumber = it}
                                    textNumber = it
                                },
                                //modifier = Modifier.padding(bottom = 300.dp),
                                placeholder = {
                                    Text(text = "Ingresa el monto cobrado")
                                },
                                label = { Text("Cobranza")},
                                trailingIcon = { Icon(painter = painterResource(id = R.drawable.ic_baseline_numbers_24), contentDescription = null, tint = colorEditext) },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                    //,keyboardLayout = KeyboardLayout.Qwerty
                                    ,imeAction = ImeAction.Go
                                ),
                                /*keyboardActions = KeyboardActions(
                                    onGo = {keyboardController?.hide()}
                                )*/
                                keyboardActions = KeyboardActions(
                                    onGo = {
                                        keyboardController?.hide()
                                        //enableEditext=false

                                        colorButtonSave= BlueVistony
                                        enableButtonSave=true
                                    }
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier= Modifier
                                    .weight(0.5f)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center
                                )
                                {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(50.dp)
                                                .background(colorButtonSave, CircleShape)
                                                .clickable {
                                                    if (enableButtonSave) {
                                                        enableEditext = false
                                                        colorEditext = Color.LightGray
                                                        collectionDetailViewModel.addListCollectionDetail(
                                                            invoices,
                                                            newBalance.toString(),
                                                            textNumber.toString(),
                                                            type,
                                                            SesionEntity.compania_id,
                                                            SesionEntity.usuario_id,
                                                            cardName,
                                                            commentary
                                                        )
                                                    } else {

                                                    }
                                                }
                                            , contentAlignment = Alignment.Center
                                        ) {
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
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier= Modifier
                                    .weight(0.5f)
                                    //.align(Alignment.CenterVertically)
                            ) {
                                Box(
                                    //modifier =
                                    //Modifier.align(alignment = Alignment.CenterHorizontally)
                                 contentAlignment = Alignment.Center
                                )
                                {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(50.dp)
                                                .background(colorButtonPrint, CircleShape)
                                                .clickable {
                                                    if (enableButtonPrint) {
                                                        var collectionReceipPDF: CollectionReceipPDF =
                                                            CollectionReceipPDF()

                                                        null
                                                        Log.e(
                                                            "REOS",
                                                            "BottomSheet-CardProcessCollection-collectionDetailResponseAdd.value.data!!.size: " + collectionDetailResponseAdd.value.data!!.size
                                                        )
                                                        for (i in 0 until collectionDetailResponseAdd.value.data!!.size) {
                                                            collectionDetail =
                                                                collectionDetailResponseAdd.value.data!!.get(
                                                                    i
                                                                )
                                                        }
                                                        collectionReceipPDF.generarPdf(
                                                            appContext,
                                                            collectionDetail
                                                        )

                                                        /*collectionDetailViewModel.getCollectionDetailUnit(

                                                        )*/


                                                    } else {

                                                    }
                                                }
                                            , contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                ImageVector.vectorResource(R.drawable.ic_print_black_24dp),
                                                tint = Color.White,
                                                contentDescription = null
                                            )
                                        }
                                        Text(
                                            text = "Imprimir",
                                            color = Color.Black,
                                            textAlign = TextAlign.Center,
                                            //modifier = Modifier.align(Alignment.Center)
                                        )
                                    }
                                }

                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier= Modifier
                                    .weight(0.5f)
                                //.align(Alignment.CenterVertically)
                            ) {
                                Box(

                                    contentAlignment = Alignment.Center,
                                    modifier =
                                    Modifier
                                        .align(alignment = Alignment.CenterHorizontally)
                                        .clickable {
                                            expanded = false
                                        }
                                )
                                {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(50.dp)
                                                .background(colorButtonValidate, CircleShape)

                                        ) {
                                            Icon(
                                                ImageVector.vectorResource(R.drawable.ic_menu_camera),
                                                tint = Color.White,
                                                contentDescription = null
                                            )
                                        }
                                        Text(
                                            text = "Validar",
                                            color = Color.Black,
                                            textAlign = TextAlign.Center,
                                            //modifier = Modifier.align(Alignment.Center)
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
        }

        var textNumber1 by remember { mutableStateOf( "0") }
        var enableEditext1 by remember { mutableStateOf( true) }
        var colorEditext1 by remember { mutableStateOf(BlueVistony)}
        AnimatedVisibility(
            visible = expanded.not(),
            enter = expandIn(),
            exit = shrinkOut()
        ) {
            Card(
                elevation = 4.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ){
                Column(modifier = Modifier
                    .padding(10.dp)){
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
                        OutlinedTextField(
                            enabled= enableEditext1 ,
                            singleLine=true,
                            value = textNumber1,
                            onValueChange = {
                                //if(!it.contains("B") ) {textNumber = it}
                                textNumber1 = it
                            },
                            //modifier = Modifier.padding(bottom = 300.dp),
                            placeholder = {
                                Text(text = "Ingresa el numero telefonico")
                            },
                            label = { Text("Telefono")},
                            trailingIcon = { Icon(painter = painterResource(id = R.drawable.ic_baseline_numbers_24), contentDescription = null, tint = colorEditext1) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                                //,keyboardLayout = KeyboardLayout.Qwerty
                                ,imeAction = ImeAction.Go
                            ),
                            /*keyboardActions = KeyboardActions(
                                onGo = {keyboardController?.hide()}
                            )*/
                            keyboardActions = KeyboardActions(
                                onGo = {
                                    keyboardController?.hide()
                                }
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Row()
                    {
                            Box(
                                modifier = Modifier
                                    //.size(200.dp)
                                    .weight(1f)
                                    .height(50.dp)
                                    .fillMaxWidth()
                                    .background(BlueVistony, RoundedCornerShape(16.dp))
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
                                        for (i in 0 until collectionDetailResponseAdd.value.data!!.size) {
                                            collectionDetail =
                                                collectionDetailResponseAdd.value.data!!.get(
                                                    i
                                                )
                                        }
                                        sendSMS(
                                            textNumber1,
                                            appContext,
                                            activity,
                                            collectionDetail
                                        )
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
                                        text = "Enviar",
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        //modifier = Modifier.align(Alignment.Center)
                                    )
                                    Icon(
                                        ImageVector.vectorResource(R.drawable.ic_baseline_send_24_white),
                                        tint = Color.White,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }


@Composable
fun ExpandableProcessStatusDispatch(
     detailDispatchSheet: DetailDispatchSheet,
     bitMapMutable: MutableState<Bitmap?>,
     openDialogShowImage: MutableState<Boolean?>,
     context: Context
){

    val statusDispatchRepository :StatusDispatchRepository= StatusDispatchRepository()
    val statusDispatchViewModel: StatusDispatchViewModel= viewModel(
        factory = StatusDispatchViewModel.StatusDispatchViewModelFactory(
            statusDispatchRepository,
            context
        )
    )

    Column() {
        var expanded by remember { mutableStateOf(true) }
        Card(
            modifier = Modifier.fillMaxHeight()
            ,elevation = 4.dp
        ) {

                    Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
                    {
                        Row(
                            modifier = Modifier
                                .padding(10.dp, 5.dp, 10.dp, 0.dp)
                                .fillMaxWidth()
                        ) {
                            /*Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .background(BlueVistony, CircleShape)
                            ) {
                                Text(
                                    text = "1",
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }*/
                            Spacer(modifier = Modifier.width(30.dp))
                            Text(
                                detailDispatchSheet.nombrecliente.toString(),
                                modifier = Modifier.padding(0.dp, 5.dp, 10.dp, 0.dp)
                                ,
                                color = Color.Black,
                                style = MaterialTheme.typography.subtitle2,
                                fontWeight = FontWeight.Bold
                            )
                            /*Icon(
                                imageVector =
                                if (expanded) {
                                    ImageVector.vectorResource(R.drawable.ic_baseline_arrow_drop_up_24_black)
                                } else {
                                    ImageVector.vectorResource(R.drawable.ic_baseline_arrow_drop_down_24_black)
                                },
                                contentDescription = null,
                                modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
                                //tint = Color.White,
                            )*/

                        }
                        /*LazyColumn()
                        {
                            items(1)
                            {*/
                                AnimatedVisibility(
                                    visible = expanded,
                                    enter = expandIn(),
                                    exit = shrinkOut()
                                ) {
                                    Column()
                                    {
                                        ProcessStatusDispatch(
                                            detailDispatchSheet,
                                            bitMapMutable,
                                            openDialogShowImage,
                                            context,
                                            statusDispatchViewModel
                                        )
                                    }
                                }
                            /*}
                        }*/
                    }
                //}
           // }
        }
    }
}


@Composable
fun ProcessStatusDispatch(
    detailDispatchSheet: DetailDispatchSheet,
    bitMapMutable: MutableState<Bitmap?>,
    openDialogShowImage: MutableState<Boolean?>,
    appContext: Context,
    statusDispatchViewModel: StatusDispatchViewModel
) {
    //val appContext = LocalContext.current as Context
    val activity = LocalContext.current as Activity
    val ComponentActivity = appContext as? ComponentActivity
    val typeDispatchRepository: TypeDispatchRepository = TypeDispatchRepository()
    val typeDispatchViewModel: TypeDispatchViewModel = viewModel(
        factory = TypeDispatchViewModel.TypeDispatchViewModelFactory(
            SesionEntity.imei,
            appContext,
            //lifecycleOwner,
            typeDispatchRepository
        )
    )

    val reasonDispatchRepository: ReasonDispatchRepository = ReasonDispatchRepository()
    val reasonDispatchViewModel: ReasonDispatchViewModel = viewModel(
        factory = ReasonDispatchViewModel.ReasonDispatchViewModelFactory(
            reasonDispatchRepository, appContext
        )
    )
    var reasonDispatchResponse = reasonDispatchViewModel.result_get.collectAsState()
    var reasonDispatchList: MutableList<String> = mutableListOf()
    reasonDispatchViewModel.getReasonDispatch()

    var typeDispatchResponse = typeDispatchViewModel.result_get.collectAsState()
    var typeDispatchList: MutableList<String> = mutableListOf()
    typeDispatchViewModel.getTypeDispatch(appContext)
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
    var expanded by remember { mutableStateOf(true) }


    Column() {

    }
        AnimatedVisibility(
            visible = expanded,
            enter = expandIn(),
            exit = shrinkOut()
        )
        {
            Card(
                modifier = Modifier, elevation = 4.dp
            )
            {
                Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
                {

                    /*Row(
                        modifier = Modifier
                            .padding(0.dp, 5.dp, 10.dp, 0.dp)
                            .fillMaxWidth()
                    ) {
                        //PreviewSpinner()

                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text(text = "Cliente", color = Color.Gray)
                            Text(
                                detailDispatchSheet.nombrecliente.toString(),
                                modifier = Modifier.padding(0.dp, 5.dp, 10.dp, 0.dp),
                                color = Color.Black,
                                style = MaterialTheme.typography.subtitle2,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }*/
                    Row(
                        modifier = Modifier
                            .padding(0.dp, 5.dp, 10.dp, 0.dp)
                            .fillMaxWidth()
                    ) {
                        //PreviewSpinner()
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text(text = "Entrega", color = Color.Gray)
                            Text(
                                text = detailDispatchSheet.entrega.toString(),
                                fontWeight = FontWeight.Bold
                            )
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
                            Text(text = "Tipo", color = Color.Gray)
                            PreviewSpinner(
                                typeDispatchList,
                                currentSelectionSpinner1
                            )
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
                            Text(text = "Motivo", color = Color.Gray)
                            PreviewSpinner(
                                reasonDispatchList,
                                currentSelectionSpinner2
                            )
                        }
                    }
                    MenuGetImage("Foto Local",false,true,true,
                        appContext,
                        activity,
                        resultBitmap = { result ->
                            //bitmapState.value = result
                            bitmapLocale.value=result
                            //openDialogShowImage.value=true
                        },
                        resultopenDialogShowImage = { result ->
                            //bitmapState.value = result
                            openDialogShowImage.value=result
                            bitMapMutable.value=bitmapLocale.value
                            //openDialogShowImage.value=true
                        },

                        )
                    Spacer(modifier = Modifier.width(10.dp))
                    MenuGetImage("Foto Guia",false,true,true,
                            appContext,
                        activity,
                        resultBitmap = { result ->
                            //bitmapStateDelivery.value = result
                            bitmapDelivery.value=result
                            //openDialogShowImage.value=true
                        },
                        resultopenDialogShowImage = { result ->
                            //bitmapState.value = result
                            openDialogShowImage.value=result
                            bitMapMutable.value=bitmapDelivery.value
                            //openDialogShowImage.value=true
                        },
                        //openDialogShowImage
                    )
                    /*Row(
                        modifier = Modifier
                            .padding(0.dp, 5.dp, 10.dp, 0.dp)
                            //.fillMaxWidth()
                            .align(Alignment.CenterHorizontally)

                    )
                    {
                                MakePhotoButtom(
                                    tittle = "Entrega",
                                    resultBitmap = { result ->
                                        bitmapStateDelivery.value = result
                                    },
                                    appContext,
                                    activity,
                                    bitmapStateDelivery.value
                                )
                                MakePhotoButtom(
                                        tittle = "Local",
                                        resultBitmap = { result ->
                                            bitmapState.value = result
                                        },
                                        appContext,
                                        activity,
                                    bitmapState.value
                                    )
                    }*/
                    Row()
                    {
                        buttonGetStatus(
                            expanded,
                            "Agregar Comentario",
                            ImageVector.vectorResource(R.drawable.ic_add_black_24dp),
                            ResultStatus = { result ->
                                expanded = result
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Row()
                    {
                        /*IconButton(
                            onClick = {
                            },
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.Gray)
                            , enabled = false
                            ,
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
                                    color = Color.White
                                )
                            }
                        }*/
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
                                        var typeDispatch_id:String=""
                                        var typeDispatch:String=""
                                        var reasonDispatch_id:String=""
                                        var reasonDispatch:String=""
                                        val elementsTypeDispatch = currentSelectionSpinner1.value.split("-", limit = 2)
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
                                        val elementsReasonDispatch = currentSelectionSpinner2.value.split("-", limit = 2)
                                        for (i in 0 until elementsReasonDispatch.size) {
                                            when (i) {
                                                0 -> {
                                                    reasonDispatch_id = elementsReasonDispatch.get(i)
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
                                            SaveImageStatusDispatch(appContext,bitmapDelivery.value,detailDispatchSheet.entrega,"G").toString(),
                                            latitud.toString(),
                                            longitud.toString(),
                                            detailDispatchSheet,
                                            SaveImageStatusDispatch(appContext,bitmapLocale.value,detailDispatchSheet.entrega,"L").toString(),
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
                        /*Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(5.dp))
                                .background(BlueVistony)
                                .clickable {

                                }
                            , contentAlignment = Alignment.Center
                            )
                            {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                        Row() {
                                            Text(
                                                text = "Guardar",
                                                color = Color.White,
                                                textAlign = TextAlign.Center,
                                                //modifier = Modifier.align(Alignment.Center)
                                            )
                                            Icon(
                                                ImageVector.vectorResource(R.drawable.ic_save_black_24dp),
                                                tint = Color.White,
                                                contentDescription = null
                                            )
                                        }
                                    //}
                                }
                            }*/
                        }
                    }
            }
        }

    AnimatedVisibility(
        visible = expanded.not(),
        enter = expandIn(),
        exit = shrinkOut()
    )
    {
        Card(
            modifier = Modifier, elevation = 4.dp
        )
        {
            Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp))
            {
                Row(
                    modifier = Modifier
                        .padding(0.dp, 5.dp, 10.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    EditextCommentary(
                        expanded,
                        ResultStatus = { result ->
                            expanded = result
                        }
                    )
                }
            }
        }
    }
}





