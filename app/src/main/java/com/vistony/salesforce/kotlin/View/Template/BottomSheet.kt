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
import com.vistony.salesforce.kotlin.View.Atoms.theme.RedVistony
import com.vistony.salesforce.kotlin.View.Molecules.ButtonViewSurface
import com.vistony.salesforce.kotlin.View.Molecules.CardView
import com.vistony.salesforce.kotlin.View.Molecules.SpinnerView



@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun SheetLayout(currentScreen: BottomSheetScreen, onCloseBottomSheet: () -> Unit, showIconClose: Boolean = true) {
    Log.e("REOS", "BottomSheet-SheetLayout-nuevo ingreso-currentScreen $currentScreen")
    BottomSheetWithCloseDialog(onCloseBottomSheet, showIconClose = showIconClose) {
        when (currentScreen) {
            is BottomSheetScreen.collectionDetailBottom -> {
                // Realizar lógica específica si es necesario
                ProcessCollectionTemplate(
                    detailDispatchSheet = currentScreen.detailDispatchSheet,
                )
            }
            is BottomSheetScreen.processDispatch -> {
                // Realizar lógica específica si es necesario
                ProcessDispatchTemplate(
                    detailDispatchSheet = currentScreen.detailDispatchSheet,
                    bitMapMutable = currentScreen.bitMapMutable,
                    openDialogShowImage = currentScreen.openDialogShowImage,
                    context = currentScreen.context,
                    openDialogEditCommentary = currentScreen.openDialogEditCommentary,
                    tittleDialogPhoto = currentScreen.tittleDialogPhoto
                )
            }
        }
    }
}
/*fun SheetLayout(currentScreen: BottomSheetScreen,onCloseBottomSheet :()->Unit,showIconClose:Boolean=true) {
    Log.e("REOS", "BottomSheet-SheetLayout-nuevo ingreso-currentScreen "+currentScreen)
    BottomSheetWithCloseDialog(onCloseBottomSheet,showIconClose=showIconClose){
        when(currentScreen){

            is BottomSheetScreen.collectionDetailBottom ->

                ProcessCollectionTemplate(
                    //cliente_id=currentScreen.cliente_id
                    //,Imei=currentScreen.Imei,appContext=currentScreen.appContext,lifecycleOwner=currentScreen.lifecycleOwner,invoicesRepository=currentScreen.invoicesRepository
                        //,invoiceViewModel=currentScreen.invoiceViewModel
                    detailDispatchSheet=currentScreen.detailDispatchSheet,
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
}*/


sealed class BottomSheetScreen() {
    class collectionDetailBottom(
        //val cliente_id: String,val invoiceViewModel: InvoicesViewModel
        val detailDispatchSheet: DetailDispatchSheetUI,
    ):BottomSheetScreen()
    class processDispatch(
        val detailDispatchSheet: DetailDispatchSheetUI,
        var bitMapMutable: MutableState<Bitmap?>,
        var openDialogShowImage: MutableState<Boolean?>,
        var context: Context,
        var openDialogEditCommentary: MutableState<Boolean?>,
        var tittleDialogPhoto: MutableState<String>
    ):BottomSheetScreen()
}

@Composable
fun BottomSheetWithCloseDialog(onClosePressed: () -> Unit, closeButtonColor: Color = Color.Gray, showIconClose:Boolean, content: @Composable() () -> Unit){
    Box{
        content()

        if(showIconClose){
            IconButton(
                onClick = {
                            onClosePressed()
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 5.dp, end = 10.dp, start = 16.dp, bottom = 16.dp)
                    .size(29.dp)
            ) {
                Icon(Icons.Filled.Close, tint = closeButtonColor, contentDescription = null)
            }
            //bottomSheetVisible=false
        }
    }
}

@Composable
private fun InvoicesListDetail(
    invoiceViewModel: InvoicesViewModel
    ,cliente_id: String
    ,expandableCollectionProcess: (invoices:Invoices?) -> Unit
){
    Log.e("REOS", "BottomSheet-InvoicesListDetail-cliente_id: " + cliente_id)
    invoiceViewModel.getInvoices(cliente_id)
    var invoices = invoiceViewModel.invoices.collectAsState()
    Log.e("REOS", "BottomSheet-InvoicesListDetail-invoices: " + invoices)
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
    appContext:Context,
    ExpandableInvoices: () -> Unit
    ,listTypeCollecion: List<String>
    ,currentSelected:MutableState<String>
    ,expandedCollectionType:MutableState<Boolean>
    ,expandedInvoiceList:MutableState<Boolean>
    ,detailDispatchSheet: DetailDispatchSheetUI,
) {


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
                    TableCell(text = "TIPO DE COBRANZA", weight =1f ,title = true, textAlign = TextAlign.Center)

            }
        }
        , cardContent =
        {
            AnimatedVisibility(
                visible = expandedCollectionType.value,
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
                visible = expandedCollectionType.value,
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
                            if(detailDispatchSheet.saldo.equals("0")&&!currentSelected.value.equals("Pago Adelantado"))
                            {
                                Toast.makeText(appContext,"El cliente no tiene deuda, cambiar a Pago Adelantado",Toast.LENGTH_SHORT).show()
                            }else {
                                ExpandableInvoices()
                                expandedCollectionType.value = false
                                expandedInvoiceList.value=true
                            }
                                  },
                        context = contexto,
                        backGroundColor = RedVistony,
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
    expandedProcessCollection:MutableState<Boolean>,
    expandedCollectionType:MutableState<Boolean>,
    expandedInvoiceList:MutableState<Boolean>,
    invoiceViewModel: InvoicesViewModel,
    cliente_id: String,
    expandableCollectionProcess: (Any?) -> Unit,
) {
    Log.e("REOS", "BottomSheet-InvoicesList-cliente_id: " +cliente_id)
    var invoicelegalnum by remember { mutableStateOf("") }

    var invoice:Invoices?=null
    CardView(
        cardtTittle =
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ButtonCircle(OnClick = {}, color = BlueVistony) {
                    Text(
                        text = "2",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    ) }
                TableCell(text = "DOCUMENTOS CON SALDO", weight =1f ,title = true, textAlign = TextAlign.Center)
            }
        }
        , cardContent =
        {
            AnimatedVisibility(
                visible = expandedInvoiceList.value,
                enter = expandIn(),
                exit = shrinkOut()
            ) {

                InvoicesListDetail(
                    invoiceViewModel
                    , cliente_id
                    ,expandableCollectionProcess= {
                            invoices ->
                        //expandableCollectionProcess(invoices)
                        invoice=invoices
                        invoicelegalnum=invoices?.nroFactura.toString()
                    }
                    //,expanded2
                    //,invoicelegalnum
                )
                /*{
                    //expanded = it
                    Log.e(
                        "REOS",
                        "BottomSheet-ExpandableInvoices-expanded" +expandedInvoiceList.value
                    )
                }*/
            }

        }
        , cardBottom =
        {
            AnimatedVisibility(
                visible = expandedInvoiceList.value,
                enter = expandIn(),
                exit = shrinkOut()
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                Row() {

                    ButtonView(
                        description = "Anterior",
                        OnClick = {
                            expandedCollectionType.value=true
                            expandedInvoiceList.value=false
                                  },
                        context = contexto,
                        backGroundColor = RedVistony,
                        textColor = Color.White,
                        status = true
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    ButtonView(
                        description = "Siguente",
                        OnClick = {
                            //expandableCollectionProcess(invoicesobj)
                            expandedInvoiceList.value=false
                            expandableCollectionProcess(invoice)
                            expandedProcessCollection.value=true

                        },
                        context = contexto,
                        backGroundColor = RedVistony,
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
    invoiceViewModel: InvoicesViewModel,
    expandedInvoiceList:MutableState<Boolean>,
    expandedProcessCollection:MutableState<Boolean>,
    invoices: Invoices?,
    client:String,
    type: String,
    InfoDialog: () -> Unit
) {

    var colorButtonCollectionAfter:MutableState<Color> = remember {
        mutableStateOf(RedVistony)
    }
    var statusButtonCollectionAfter:MutableState<Boolean> = remember {
        mutableStateOf(true)
    }

    CardView(
        cardtTittle =
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ButtonCircle(OnClick = {}, color = BlueVistony) {
                    Text(
                        text = "3",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    ) }
                TableCell(text = "GENERACION DE COBRANZA:", weight =1f ,title = true, textAlign = TextAlign.Center)
            }
        }
        , cardContent =
        {
            CardProcessCollection(invoiceViewModel,invoices,type,client,expandedProcessCollection,colorButtonCollectionAfter,statusButtonCollectionAfter )
        },cardBottom=
        {
            AnimatedVisibility(
                visible = expandedProcessCollection.value,
                enter = expandIn(),
                exit = shrinkOut()
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                Row() {

                    ButtonView(
                        description = "Anterior",
                        OnClick = {
                            expandedInvoiceList.value=true
                            expandedProcessCollection.value=false
                        },
                        context = contexto,
                        backGroundColor = colorButtonCollectionAfter.value,
                        textColor = Color.White,
                        status = statusButtonCollectionAfter.value
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    ButtonView(
                        description = "Siguente",
                        OnClick = {
                            //expandableCollectionProcess(invoicesobj)
                            //expandedInvoiceList.value=false
                            //expandableCollectionProcess(invoice)

                        },
                        context = contexto,
                        backGroundColor = Color.Gray,
                        textColor = Color.White,
                        status = false
                    )
                }
            }
        }
    )

    Log.e(
        "REOS",
        "BottomSheet-ExpandableCollectionProcess-invoices" +invoices
    )
}

@SuppressLint("ShowToast")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CardProcessCollection(
    invoiceViewModel:InvoicesViewModel,
    invoices: Invoices?
    ,type: String
    ,cardName:String
    ,expandedProcessCollection:MutableState<Boolean>
    ,colorButtonCollectionAfter:MutableState<Color>
    ,statusButtonCollectionAfter:MutableState<Boolean>
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
    Log.e("REOS", "BottomSheet-CardProcessCollection-collectionDetailResponseAdd.value: "+collectionDetailResponseAdd.value)
    Log.e("REOS", "BottomSheet-CardProcessCollection-cardName: "+cardName)
    if(collectionDetailResponseAdd.value.Status.equals("Y"))
    {
        colorButtonSave.value=Color.Gray
        enableButtonSave=false
        colorButtonValidate= RedVistony
        colorButtonPrint= RedVistony
        enableButtonPrint=true
        enableButtonValidate=true
        collectionDetailViewModel.SendAPICollectionDetail(appContext,SesionEntity.compania_id,SesionEntity.usuario_id)
        Log.e(
            "REOS",
            "BottomSheet-CardProcessCollection-collectionDetailResponseAdd.EntroalIF.alterminarlaInsercion "
        )
    }
    var context= LocalContext.current
    val openDialogEdit:MutableState<Boolean?> = remember { mutableStateOf(false) }
    var DialogEditStatus by remember { mutableStateOf(true) }
    var DialogEditResult :MutableState<String> = remember { mutableStateOf("0") }
    var receip :MutableState<String> = remember { mutableStateOf("0") }
    val openDialogCommentary:MutableState<Boolean?> = remember { mutableStateOf(false) }
    var DialogResultCommentary :MutableState<String> = remember { mutableStateOf("") }
    var DialogCommentaryStatus by remember { mutableStateOf(true) }
    var newBalance by remember { mutableStateOf(CalculateNewBalance(invoices?.saldo.toString(),DialogEditResult.value))}
    var DialogEditResultSMS :MutableState<String> = remember { mutableStateOf("0") }
    val openDialogSensSMS:MutableState<Boolean?> = remember { mutableStateOf(false) }
    var colorButtonCollection by remember { mutableStateOf(RedVistony)}
    var enableButtonCollection by remember { mutableStateOf(true)}



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
        colorButtonSave.value= RedVistony
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

    if(openDialogSensSMS.value!!)
    {
        DialogView(
                "SMS"
                ,""
                ,onClickCancel = {
            openDialogSensSMS.value = false
        }
                ,onClickAccept = {
            for (i in 0 until collectionDetailResponseAdd.value.data!!.size) {
                collectionDetail =
                        collectionDetailResponseAdd.value.data!!.get(
                                i
                        )
            }
            sendSMS(
                    DialogEditResultSMS.value,
                    appContext,
                    activity,
                    collectionDetail
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

    Column(
        //modifier = Modifier.padding(top = 0.dp, bottom = 0.dp)
    ) {
        //Divider()
        AnimatedVisibility(
            visible = expandedProcessCollection.value,
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
                                        if(enableButtonCollection)
                                        {
                                            //openDialogEdit.value = true
                                            openDialogCommentary.value=true
                                            Toast.makeText(context,"Boton comentario",Toast.LENGTH_SHORT).show()
                                        }else{
                                            Toast.makeText(context,"Boton deshabilitado",Toast.LENGTH_SHORT).show()
                                        }

                                    }
                                    , size = DpSize(40.dp,40.dp)
                                    , color = colorButtonCollection
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
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier=Modifier.weight(0.5f)
                            ) {
                                Row() {
                                    TableCell(
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
                            }
                        }
                        Row(
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier= Modifier
                                    .weight(0.5f)
                                    .background(
                                        colorButtonCollection, RoundedCornerShape(4.dp)
                                    )
                                    .clickable {
                                        if (enableButtonCollection) {
                                            openDialogEdit.value = true
                                        } else {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Boton deshabilitado",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }

                                    }
                            ) {
                                Row() {
                                    TableCell(
                                        text = "Cobranza",
                                        color = Color.White,
                                        title = false,
                                        weight = 1f,
                                        textAlign = TextAlign.End
                                    )
                                }
                                Row() {
                                    TableCell(
                                        text = "${Convert.currencyForView(DialogEditResult.value)}",
                                        color = Color.White,
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
                            }
                        }
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
                                    OnClick=
                                    {
                                            if (enableButtonSave)
                                            {
                                                colorButtonCollection=Color.Gray
                                                enableButtonSave = false
                                                enableButtonCollection= false
                                                colorButtonCollectionAfter.value = Color.Gray
                                                statusButtonCollectionAfter.value = false
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
                                                Toast.makeText(context, "Recibo guardado correctamente", Toast.LENGTH_SHORT)
                                                colorButtonSave.value = Color.Gray
                                                invoiceViewModel.resetInvoices()

                                            }
                                            else
                                            {
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
                                            var collectionReceipPDF: CollectionReceipPDF = CollectionReceipPDF()
                                            collectionReceipPDF.generarPdf(appContext, collectionDetail)

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
                                            openDialogSensSMS.value = true
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

        }
    }










