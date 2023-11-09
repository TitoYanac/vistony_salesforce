package com.vistony.salesforce.kotlin.View.Template

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Controller.Utilitario.Induvis
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R

import com.vistony.salesforce.kotlin.View.components.*
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.Model.*
import com.vistony.salesforce.kotlin.Utilities.*
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Atoms.TextLabel
import com.vistony.salesforce.kotlin.View.Molecules.ButtonViewSurface
import com.vistony.salesforce.kotlin.View.Molecules.CardView
import com.vistony.salesforce.kotlin.View.Molecules.SpinnerView



@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun SheetLayout(currentScreen: BottomSheetScreen,onCloseBottomSheet :()->Unit,showIconClose:Boolean=true) {
    BottomSheetWithCloseDialog(onCloseBottomSheet,showIconClose=showIconClose){
        when(currentScreen){
            is BottomSheetScreen.collectionDetailBottom ->
                ProcessCollectionTemplate(cliente_id=currentScreen.cliente_id
                    //,Imei=currentScreen.Imei,appContext=currentScreen.appContext,lifecycleOwner=currentScreen.lifecycleOwner,invoicesRepository=currentScreen.invoicesRepository
                        ,invoiceViewModel=currentScreen.invoiceViewModel
                )
            is BottomSheetScreen.processDispatch ->
                ProcessDispatchTemplate(
                    detailDispatchSheet=currentScreen.detailDispatchSheet,
                    bitMapMutable=currentScreen.bitMapMutable,
                    openDialogShowImage=currentScreen.openDialogShowImage,
                    context=currentScreen.context,
                    openDialogEditCommentary=currentScreen.openDialogEditCommentary,
                    tittleDialogPhoto= currentScreen.tittleDialogPhoto
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
    class processDispatch(
        val detailDispatchSheet: DetailDispatchSheet,
        var bitMapMutable: MutableState<Bitmap?>,
        var openDialogShowImage: MutableState<Boolean?>,
        var context: Context,
        var openDialogEditCommentary: MutableState<Boolean?>,
        var tittleDialogPhoto: MutableState<String>,
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
    Row() {
        TableCell(text = "Cantidad de documentos: ${
            if (invoices.value.isNullOrEmpty()) {
                0
            } else {
                invoices.value.size
            }
        }", weight =1f ,title = true, textAlign = TextAlign.Start)
    }
    Column(
        //modifier = Modifier.padding(top = 0.dp, bottom = 0.dp)
        )
    {
        //Divider()
        LazyRow(

            modifier = Modifier,
                //.fillMaxWidth()
                //.padding(0.dp,0.dp,0.dp,100.dp)
                //.weight(1f),
            contentPadding = PaddingValues(10.dp)
        ) {
            itemsIndexed(invoices.value ) { index,line ->
                Card(
                    elevation = 4.dp,modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable {
                            //line.isSelected = !line.isSelected
                            //selectedLines[index] = !selectedLines[index]
                            selectedLineIndex.value = index
                            invoicesobj = line
                            expandableCollectionProcess(invoicesobj)
                            //onBooleanValueChanged(!statusEnable)
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
                        .padding(10.dp)
                        .fillMaxWidth()){
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                            //modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                               // modifier=Modifier.weight(1f)
                            ) {
                                Text(text = "Documento", color = Color.Gray )
                                Text(text = "${line.nroFactura}",fontWeight = FontWeight.Bold)
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            //modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                //modifier=Modifier.weight(0.5f)
                            ) {
                                Text(text = "F. Emisión", color = Color.Gray)
                                Text(text = "${Induvis.getDate(BuildConfig.FLAVOR,line.fechaEmision)  }", fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(
                                horizontalAlignment = Alignment.Start,
                                //modifier=Modifier.weight(0.5f)
                            ) {
                                Text(text = "F. Vencimiento", color = Color.Gray)
                                Text(text = "${Induvis.getDate(BuildConfig.FLAVOR,line.fechaVencimiento)}", fontWeight = FontWeight.Bold)
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            //modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                //modifier=Modifier.weight(0.5f)
                            ) {
                                Text(text = "Importe", color = Color.Gray)
                                Text(text = "${Convert.currencyForView(line.importeFactura)}", fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(
                                horizontalAlignment = Alignment.Start,
                                //modifier=Modifier.weight(0.5f)
                            ) {
                                Text(text = "Saldo", color = Color.Gray)
                                Text(text = "${Convert.currencyForView(line.saldo)}", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun CollectionType(
    ExpandableInvoices: () -> Unit
    ,listTypeCollecion: List<String>
    ,currentSelected:MutableState<String>
) {

    var expanded by remember { mutableStateOf(true) }
    CardView(
        cardtTittle =
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ButtonCircle(OnClick = {}) {
                    Text(
                        text = "1",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    ) }
                    TableCell(text = "TIPO DE COBRANZA", weight =1f ,title = true, textAlign = TextAlign.Center)

            }
        }
        , cardContent =
        {
            AnimatedVisibility(
                visible = expanded,
                enter = expandIn(),
                exit = shrinkOut()
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                SpinnerView(
                    "Seleccione tipo de cobranza: ", listTypeCollecion, currentSelected
                )
            }
        }
        , cardBottom =
        {
            AnimatedVisibility(
                visible = expanded,
                enter = expandIn(),
                exit = shrinkOut()
            ) {
                Spacer(modifier = Modifier.height(5.dp))
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
                            ExpandableInvoices()
                            expanded = false
                                  },
                        context = contexto,
                        backGroundColor = BlueVistony,
                        textColor = Color.White,
                        status = true
                    )
                }
            }
        }
    )
}



@Composable
fun InvoicesList(
    invoiceViewModel: InvoicesViewModel,
    cliente_id: String,
    expandableCollectionProcess: (Any?) -> Unit,
) {
    var invoicelegalnum by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(true) }
    var expanded2 by remember { mutableStateOf(true) }
    var invoice:Invoices?=null
    CardView(
        cardtTittle =
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ButtonCircle(OnClick = {}) {
                    Text(
                        text = "2",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    ) }
                TableCell(text = "FACTURAS", weight =1f ,title = true, textAlign = TextAlign.Center)
            }
        }
        , cardContent =
        {
            AnimatedVisibility(
                visible = expanded,
                enter = expandIn(),
                exit = shrinkOut()
            ) {

                InvoicesList(
                    invoiceViewModel
                    , cliente_id
                    ,expandableCollectionProcess= {
                            invoices ->
                        //expandableCollectionProcess(invoices)
                        invoice=invoices
                        invoicelegalnum=invoices?.nroFactura.toString()
                    }
                    ,expanded2
                    ,invoicelegalnum
                ){
                    //expanded = it
                    Log.e(
                        "REOS",
                        "BottomSheet-ExpandableInvoices-expanded" +expanded
                    )
                }
            }

        }
        , cardBottom =
        {
            AnimatedVisibility(
                visible = expanded,
                enter = expandIn(),
                exit = shrinkOut()
            ) {
                Spacer(modifier = Modifier.height(5.dp))
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
                            //expandableCollectionProcess(invoicesobj)
                            expanded=false
                            expandableCollectionProcess(invoice)

                        },
                        context = contexto,
                        backGroundColor = BlueVistony,
                        textColor = Color.White,
                        status = true
                    )
                }
            }
        }
    )
}






@Composable
fun CollectionProcess(
    invoices: Invoices?,
    client:String,
    type: String,
    InfoDialog: () -> Unit
) {


    CardView(
        cardtTittle =
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ButtonCircle(OnClick = {}) {
                    Text(
                        text = "3",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    ) }
                TableCell(text = "GENERACION COBRANZA:", weight =1f ,title = true, textAlign = TextAlign.Center)
            }
        }
        , cardContent =
        {
            CardProcessCollection(
                invoices,type,client,
                //invoiceViewModel, cliente_id
                //,Imei: String,appContext: Context,lifecycleOwner:LifecycleOwner,invoicesRepository: InvoicesRepository
            )
        },cardBottom=
        {

        }
    )

    Log.e(
        "REOS",
        "BottomSheet-ExpandableCollectionProcess-invoices" +invoices
    )
    /*Column(
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
    }*/
}

@SuppressLint("ShowToast")
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
    var colorButtonSave:MutableState<Color> = remember { mutableStateOf(Color.LightGray)}
    var colorButtonPrint by remember { mutableStateOf(Color.LightGray)}
    var colorButtonValidate by remember { mutableStateOf(Color.LightGray)}
    var enableButtonSave by remember { mutableStateOf(false)}
    var enableButtonPrint by remember { mutableStateOf(false)}
    var enableButtonValidate by remember { mutableStateOf(false)}

    val appContext = LocalContext.current
    //val lifecycleOwner = LocalContext.current as LifecycleOwner
    val collectionDetailRepository :CollectionDetailRepository= CollectionDetailRepository(appContext)
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
        colorButtonSave.value=Color.Gray
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
    var context= LocalContext.current
    val openDialogEdit:MutableState<Boolean?> = remember { mutableStateOf(false) }
    var DialogEditStatus by remember { mutableStateOf(true) }
    var DialogEditResult :MutableState<String> = remember { mutableStateOf("0") }
    var receip :MutableState<String> = remember { mutableStateOf("0") }
    val openDialogCommentary:MutableState<Boolean?> = remember { mutableStateOf(false) }
    var DialogResultCommentary :MutableState<String> = remember { mutableStateOf("") }
    var DialogCommentaryStatus by remember { mutableStateOf(true) }
    var newBalance by remember { mutableStateOf(CalculateNewBalance(invoices?.saldo.toString(),DialogEditResult.value))}


    if(openDialogEdit.value!!)
    {
        DialogView(
            "Cobranza"
            ,""
            ,onClickCancel = {
                openDialogEdit.value = false
            }
            ,onClickAccept = {
                openDialogEdit.value = false
            }
            ,statusButtonAccept = true
            ,statusButtonIcon = false
            ,context=context
        ){
            Editext(
                DialogEditStatus
                ,DialogEditResult
                ,"Ingrese cobranza"
                ,"Cobranza"
                ,painterResource(id = R.drawable.ic_baseline_numbers_24)
            , KeyboardType.Number
            )
        }
    }

    if(!DialogEditResult.value.equals("0")&&receip.value.equals("0")){
        colorButtonSave.value= BlueVistony
        enableButtonSave=true
    }

    when(collectionDetailResponseAdd.value.Status)
    {
        "Y"->{
            for (i in 0 until collectionDetailResponseAdd.value.data!!.size) {
                collectionDetail = collectionDetailResponseAdd.value.data!!.get(i)
                receip.value=collectionDetailResponseAdd.value.data!!.get(i).Receip.toString()
            }
        }
    }

    if(openDialogCommentary.value!!)
    {
        DialogView(
            "Comentario"
            ,""
            ,onClickCancel = {
                openDialogCommentary.value = false
            }
            ,onClickAccept = {
                openDialogCommentary.value = false
            }
            ,statusButtonAccept = true
            ,statusButtonIcon = false
            ,context=context
        ){
            Editext(
                DialogCommentaryStatus
                ,DialogResultCommentary
                ,"Ingrese comentario"
                ,"comentario"
                ,painterResource(id = R.drawable.ic_insert_comment_black_24dp)
                , KeyboardType.Text
            )
        }
    }

    Column(
        //modifier = Modifier.padding(top = 0.dp, bottom = 0.dp)
    ) {
        //Divider()
        AnimatedVisibility(
            visible = expanded,
            enter = expandIn(),
            exit = shrinkOut()
        ) {
                    Column(modifier = Modifier
                        .padding(5.dp)){
                        Row(
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.5f)
                            ) {
                                Row() {
                                    TableCell(
                                        text = "Recibo",
                                        color = Color.Gray,
                                        title = false,
                                        weight = 1f,
                                        //textAlign = TextAlign.End
                                    )
                                }
                            }
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.5f)
                            ) {
                                Row() {
                                    TableCell(
                                        text = receip.value.toString(),
                                        title = true,
                                        weight = 1f,
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.7f)
                            ) {
                                Row(

                                ) {
                                    TableCell(
                                        text = "Comentario",
                                        color = Color.White,
                                        title = false,
                                        weight = 1f,
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.3f)
                            ) {
                                ButtonCircle(
                                    OnClick={
                                        openDialogCommentary.value=true
                                        Toast.makeText(context,"Boton comentario",Toast.LENGTH_SHORT).show()
                                    }
                                    , size = DpSize(40.dp,40.dp)
                                    , color = BlueVistony
                                    , roundedCornerShape = CircleShape
                                )
                                {
                                    Icon(
                                        ImageVector.vectorResource(R.drawable.ic_baseline_add_comment_24),
                                        tint = Color.White,
                                        contentDescription = null
                                    )
                                }
                            }
                        }

                        Row(
                            //horizontalArrangement = Arrangement.Start,
                            //modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.5f)
                            ) {
                                //Text(text = "Importe", color = Color.Gray)
                                //Text(text = "${Convert.currencyForView(invoices?.importeFactura)}", fontWeight = FontWeight.Bold)
                                //TextLabel(text ="Importe", textAlign = TextAlign.Center )
                                Row() {
                                    TableCell(
                                        //text = "${Convert.currencyForView(invoices?.)}",
                                        text = "Importe",
                                        color = Color.Gray,
                                        title = false,
                                        weight = 1f,
                                        textAlign = TextAlign.End
                                    )
                                }
                                Row() {
                                    TableCell(
                                        text = "${Convert.currencyForView(invoices?.importeFactura)}",
                                        //color = textColor,
                                        title = true,
                                        weight = 1f,
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.5f)
                            ) {
                                Row() {
                                    TableCell(
                                        //text = "${Convert.currencyForView(invoices?.)}",
                                        text = "Saldo" ,
                                        color = Color.Gray,
                                        title = false,
                                        weight = 1f,
                                        textAlign = TextAlign.End
                                    )
                                }
                                //TextLabel(text ="Saldo" , textAlign = TextAlign.Center )
                                Row() {
                                    TableCell(
                                        text = "${Convert.currencyForView(invoices?.saldo)}",
                                        //color = textColor,
                                        title = true,
                                        weight = 1f,
                                        textAlign = TextAlign.End
                                    )
                                }
                                /*Column(
                                    horizontalAlignment = Alignment.Start,
                                    modifier=Modifier.weight(0.5f)
                                ) {
                                    Text(text = "Saldo", color = Color.Gray)
                                    Text(text = "${Convert.currencyForView(invoices?.saldo)}", fontWeight = FontWeight.Bold)
                                }*/
                            }
                        }
                        Row(
                            //horizontalArrangement = Arrangement.Start,
                            //modifier=Modifier.weight(0.5f)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier
                                    .weight(0.5f)
                                    .background(
                                    BlueVistony, RoundedCornerShape(4.dp)
                                ).clickable { openDialogEdit.value=true  }
                            ) {
                                Row() {
                                    TableCell(
                                        //text = "${Convert.currencyForView(invoices?.)}",
                                        text = "Cobranza",
                                        color = Color.White,
                                        title = false,
                                        weight = 1f,
                                        textAlign = TextAlign.End
                                    )
                                }
                                //TextLabel(text = "Cobranza", textAlign = TextAlign.Center )
                                Row() {
                                    TableCell(
                                        //text = "${Convert.currencyForView(invoices?.)}",
                                        text = "${Convert.currencyForView(DialogEditResult.value)}",
                                        color = Color.White,
                                        title = true,
                                        weight = 1f,
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                            /*Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.5f)
                            ) {
                                ButtonCircle(OnClick = { openDialogEditCommentary.value=true }) {
                                    Text(
                                        text = "1",
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                    ) }
                            }*/
                            /*Column(
                                //horizontalAlignment = Alignment.Start,
                                modifier=Modifier//.weight(0.5f)
                                    .clickable {
                                    openDialogEditCommentary.value=true }
                            ) {
                                Text(text = "Cobranza", color = Color.Gray)
                                Text(text = "${Convert.currencyForView(invoices?.saldo)}", fontWeight = FontWeight.Bold)
                            }*/
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.5f)
                            ) {
                                Row() {
                                    TableCell(
                                        //text = "${Convert.currencyForView(invoices?.)}",
                                        text = "Nuevo Saldo",
                                        color = Color.Gray,
                                        title = false,
                                        weight = 1f,
                                        textAlign = TextAlign.End
                                    )
                                }
                                //Text(text = "Nuevo Saldo", color = Color.Gray, textAlign = TextAlign.Center )
                                newBalance=CalculateNewBalance(invoices?.saldo.toString(),DialogEditResult.value)
                                Row() {
                                    "${Convert.currencyForView(newBalance)}"
                                        ?.let {

                                            TableCell(
                                                text = it,
                                                //color = textColor,
                                                title = true,
                                                weight = 1f,
                                                textAlign = TextAlign.End
                                            )
                                        }
                                }
                                /*Text(text = "Nuevo Saldo", color = Color.Gray)
                                newBalance=CalculateNewBalance(invoices?.saldo.toString(),textNumber)
                                "${Convert.currencyForView(newBalance)}"
                                    ?.let {
                                    Text(text =
                                    //"${Convert.currencyForView(invoices?.saldo)}"
                                    it, fontWeight = FontWeight.Bold)
                                }*/
                            }
                        }
                        /*Spacer(modifier = Modifier.height(10.dp))
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
                        }*/
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier= Modifier
                                    .weight(0.5f)
                            ) {
                                ButtonCircle(
                                    OnClick={
                                        if (enableButtonSave) {
                                        enableButtonSave = false
                                        //colorButtonSave.value = Color.LightGray
                                            collectionDetailViewModel.addListCollectionDetail(
                                            invoices,
                                            newBalance.toString(),
                                                DialogEditResult.value,
                                            type,
                                            SesionEntity.compania_id,
                                            SesionEntity.usuario_id,
                                            cardName,
                                            commentary
                                        )
                                            Toast.makeText(context,"Recibo guardado correctamente",Toast.LENGTH_SHORT)
                                            colorButtonSave.value=Color.Gray
                                    }else {
                                        Toast.makeText(context,"Boton deshabilitado",Toast.LENGTH_SHORT)
                                    }
                                            }
                                , size = DpSize(50.dp,50.dp)
                                , color = colorButtonSave.value
                                , roundedCornerShape = CircleShape
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
                                /*Box(
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
                                }*/

                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier= Modifier
                                    .weight(0.5f)
                                    //.align(Alignment.CenterVertically)
                            ) {
                                ButtonCircle(
                                    OnClick={
                                        if (enableButtonPrint) {
                                            var collectionReceipPDF: CollectionReceipPDF =
                                                CollectionReceipPDF()
                                            null
                                            Log.e(
                                                "REOS",
                                                "BottomSheet-CardProcessCollection-collectionDetailResponseAdd.value.data!!.size: " + collectionDetailResponseAdd.value.data!!.size
                                            )

                                            collectionReceipPDF.generarPdf(
                                                appContext,
                                                collectionDetail
                                            )

                                        } else {

                                        }
                                    }
                                    , size = DpSize(50.dp,50.dp)
                                    , color = colorButtonPrint
                                    , roundedCornerShape = CircleShape
                                )
                                {
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
                                /*Box(
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
                                }*/

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
                                            , contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                ImageVector.vectorResource(R.drawable.ic_baseline_camera_alt_black_24),
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
              //  }
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










