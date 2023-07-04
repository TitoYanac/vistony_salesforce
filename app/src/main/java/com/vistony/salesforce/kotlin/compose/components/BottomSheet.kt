package com.vistony.salesforce.kotlin.compose.components

import android.content.Context
import android.util.Log
import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Controller.Utilitario.Induvis
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.compose.MyUI
import com.vistony.salesforce.kotlin.compose.theme.BlueVistony
import com.vistony.salesforce.kotlin.compose.theme.RedVistony
import com.vistony.salesforce.kotlin.data.*
import com.vistony.salesforce.kotlin.utilities.CalculateNewBalance
import com.vistony.salesforce.kotlin.utilities.CollectionReceipPDF
import com.vistony.salesforce.kotlin.utilities.Geolocation
import java.text.SimpleDateFormat
import java.util.*


@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun SheetLayout(currentScreen: BottomSheetScreen,onCloseBottomSheet :()->Unit,showIconClose:Boolean=true) {
    BottomSheetWithCloseDialog(onCloseBottomSheet,showIconClose=showIconClose){
        when(currentScreen){
            is BottomSheetScreen.collectionDetailBottom ->
                //PedidoDetalle(DocEntry=currentScreen.DocEntry, DocNum = currentScreen.DocNum,Moneda=currentScreen.Moneda,onCloseBottomSheet)
                /*CollectionDetailBottom(
                    Imei=currentScreen.Imei,
                    context = currentScreen.context,
                    lifecycleOwner=currentScreen.lifecycleOwner,
                    cliente_id=currentScreen.cliente_id,
                    invoicesRepository=currentScreen.invoicesRepository,
                    onCloseBottomSheet,
                    //invoiceViewModel=currentScreen.invoiceViewModel
                )*/
                Expandable(cliente_id=currentScreen.cliente_id
                    //,Imei=currentScreen.Imei,appContext=currentScreen.appContext,lifecycleOwner=currentScreen.lifecycleOwner,invoicesRepository=currentScreen.invoicesRepository
                        ,invoiceViewModel=currentScreen.invoiceViewModel
                )
        }
    }
    Log.e(
        "REOS",
        "BottomSheet-SheetLayout-fin"
    )
}


sealed class BottomSheetScreen() {

    //class collectionDetailBottom(val Imei:String,val context: Context,val lifecycleOwner: LifecycleOwner,val cliente_id:String,val invoicesRepository: InvoicesRepository,val invoiceViewModel: InvoicesViewModel
    class collectionDetailBottom(val cliente_id: String
                                 //,val Imei: String,val appContext: Context,val lifecycleOwner:LifecycleOwner,val invoicesRepository: InvoicesRepository
                                 ,val invoiceViewModel: InvoicesViewModel
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
private fun RadioBtn(
    listTypeCollecion: List<String>
     ,currentSelected:MutableState<String>
    //, currentSelectedChanged: (String) -> Unit
){

    Log.e(
        "REOS",
        "BottomSheet-RadioBtn-currentSelection.value" +currentSelected.value
    )
    RadioGroup(
        modifier = Modifier.fillMaxWidth(),
        items = listTypeCollecion,
        selection = currentSelected.value,
        onItemClick = { clickedItem ->
            currentSelected.value = clickedItem

        },
        true
    )
}

@Composable
private fun RadioGroup(
    modifier: Modifier,
    items: List<String>,
    selection: String,
    onItemClick: ((String) -> Unit),
    statusEnable:Boolean
) {
    Column(modifier = modifier) {
        items.forEach { item ->
            LabelledRadioButton(
                modifier = Modifier.fillMaxWidth(),
                label = item,
                selected = item == selection,
                onClick = {
                    onItemClick(item)
                },
                statusEnable

            )
        }
    }
}


@Composable
private fun LabelledRadioButton(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: (() -> Unit)?,
    enabled: Boolean,
    colors: RadioButtonColors = RadioButtonDefaults.colors()
) {

    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            enabled = enabled,
            //colors = colors
            colors = RadioButtonDefaults.colors(
                selectedColor = RedVistony, // Color cuando está seleccionado
                unselectedColor = Color.Gray, // Color cuando no está seleccionado
                disabledColor = Color.Gray, // Color cuando está deshabilitado
                //have = Color.Green // Color al pasar el ratón por encima
        )
        )

        //if(selected==true)
        //{
        when(label){
            "Cobranza Ordinaria"->{
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_qr_code_24),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .size(20.dp),
                    tint =
                    if(selected==true)
                    {
                        Color.Black
                    }else {
                        Color.Gray
                    }
                )
            }
            "Deposito Directo"->{
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_qr_code_24),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .size(20.dp),
                    tint =
                    if(selected==true)
                    {
                        Color.Black
                    }else {
                        Color.Gray
                    }
                )
            }
            "Pago POS"->{
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_qr_code_24),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .size(20.dp),
                    tint =
                    if(selected==true)
                    {
                        Color.Black
                    }else {
                        Color.Gray
                    }
                )
            }
            "Cobro Vendedor"->{
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_qr_code_24),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .size(20.dp),
                    tint =
                    if(selected==true)
                    {
                        Color.Black
                    }else {
                        Color.Gray
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = label,
            fontWeight =
            if(selected==true)
            {
                FontWeight.Bold
            }else {
                FontWeight.Normal
            },
            style = MaterialTheme.typography.body1.merge(),
            modifier = Modifier.padding(start = 3.dp),
        )
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
    var collectionDetailResponseAdd = collectionDetailViewModel.result_add.collectAsState()

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
                                                    if(enableButtonSave)
                                                                {
                                                                    enableEditext=false
                                                                    colorEditext=Color.LightGray
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
                                                                }
                                                                else
                                                                {

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
                                                    if(enableButtonPrint)
                                                    {
                                                        var collectionReceipPDF:CollectionReceipPDF = CollectionReceipPDF()
                                                        var collectionDetail:CollectionDetail?=null
                                                        Log.e(
                                                            "REOS",
                                                            "BottomSheet-CardProcessCollection-collectionDetailResponseAdd.value.data!!.size: "+collectionDetailResponseAdd.value.data!!.size
                                                        )
                                                        for (i in 0 until  collectionDetailResponseAdd.value.data!!.size)
                                                        {
                                                            collectionDetail=
                                                                collectionDetailResponseAdd.value.data!!.get(i)
                                                        }
                                                        collectionReceipPDF.generarPdf(appContext,
                                                            collectionDetail
                                                        )

                                                        /*collectionDetailViewModel.getCollectionDetailUnit(

                                                        )*/


                                                    }
                                                    else
                                                    {

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
                                    Modifier.align(alignment = Alignment.CenterHorizontally)
                                        .clickable {
                                            expanded=false
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
                                                   , contentAlignment = Alignment.Center
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
                        IconButton(
                            onClick = {
                                expanded=true
                            },
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(5.dp))
                                .background(BlueVistony)
                            , enabled = true
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
                                //ExpandableInvoices()
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
                                    text = "Enviar",
                                    color = Color.White
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
