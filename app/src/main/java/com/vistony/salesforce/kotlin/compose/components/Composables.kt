@file:OptIn(ExperimentalFoundationApi::class)

package com.vistony.salesforce.kotlin.compose.components

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.View.MenuView
import com.vistony.salesforce.kotlin.compose.DialogMain
import com.vistony.salesforce.kotlin.compose.theme.BlueVistony
import com.vistony.salesforce.kotlin.data.*
import com.vistony.salesforce.kotlin.utilities.Geolocation
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

var contexto: Context = MenuView.context

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ScreenDispatch(
    listDispatch: List<DetailDispatchSheet>,
    context: Context,
    lifecycleOwner:  LifecycleOwner
    )
{
    var modal = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, confirmStateChange = {false})
    val scope = rememberCoroutineScope()
    var currentBottomSheet: BottomSheetScreen? by remember { mutableStateOf(null) }
    val closeSheet: () -> Unit = { scope.launch { modal.hide() }}
    val openDialogShowImage:MutableState<Boolean?> = remember { mutableStateOf(false) }
    val bitMapMutable:MutableState<Bitmap?> = remember { mutableStateOf(null) }

    Log.e(
        "REOS",
        "Composables-ScreenDispatch-openDialogShowImage.value: "+openDialogShowImage.value
    )
    Log.e(
        "REOS",
        "Composables-ScreenDispatch-bitMapMutable.value:"+bitMapMutable.value
    )
    if(openDialogShowImage.value!!)
    {
        DialogShowImage(
                "Imagen",
        "Prueba",
        onDismiss = {
            openDialogShowImage.value = false
        },
        bitMap_ =bitMapMutable
        )
    }

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
        LazyColumn{
            items(listDispatch){ objDistpatch ->
                Log.e(
                    "REOS",
                    "Composables-ScreenDispatch-objDistpatch.cliente_id:"+objDistpatch.cliente_id.toString()
                )
                val openSheet: (BottomSheetScreen) -> Unit = {
                    scope.launch {
                        currentBottomSheet = it
                        modal.animateTo(ModalBottomSheetValue.Expanded)
                    }
                }
                val appContext = LocalContext.current
                val lifecycleOwner = LocalContext.current as LifecycleOwner
                val invoicesRepository:InvoicesRepository= InvoicesRepository()

                val invoiceViewModel: InvoicesViewModel= viewModel(
                    factory = InvoicesViewModel.InvoiceModelFactory(
                        SesionEntity.imei,
                        context,
                        lifecycleOwner,
                        objDistpatch.cliente_id,
                        invoicesRepository
                    )
                )

                CardDispatch(
                    objDistpatch,context,lifecycleOwner,
                    formProcessCollection =   {
                        //bottomSheetVisible = true
                        invoiceViewModel.getInvoices(objDistpatch.cliente_id)
                        openSheet(
                            BottomSheetScreen.collectionDetailBottom(
                                objDistpatch.nombrecliente.toString()
                                ,invoiceViewModel
                            )
                        )
                    },
                    formProcessStatusDispatch ={
                        openSheet(
                            BottomSheetScreen.processStatusDispatch(
                                objDistpatch,
                                bitMapMutable,
                            openDialogShowImage,
                                context
                            )
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun CardDispatch(
    list: DetailDispatchSheet,
    context: Context ,
    lifecycleOwner:  LifecycleOwner,
    //formProcessCollection:() -> Unit
    formProcessCollection : (cliente_id: String) -> Unit,
    formProcessStatusDispatch: (list: DetailDispatchSheet) -> Unit,
){

    Card(
        elevation = 10.dp,
        //onClick = { },
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth(),
    )
    {
        Column()
        {
            Row(Modifier.padding(10.dp,10.dp,10.dp,0.dp))
            {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(BlueVistony, CircleShape)
                ) {
                    Text(
                        text = list.item_id.toString(),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(270.dp, 0.dp, 0.dp, 0.dp)
                        .size(40.dp)
                        .background(BlueVistony, CircleShape)
                        .clickable(onClick = {
                            val geoLocalizacion = Geolocation(context)

                            geoLocalizacion.obtenerUbicacionActual(
                                onSuccess = { location ->
                                    // Haz algo con la ubicación, como mostrarla en un mapa
                                    var fragmentManager: FragmentManager? = null
                                    val dialogMain = DialogMain()
                                    dialogMain.vary(
                                        location.latitude.toString(),
                                        location.longitude.toString(),
                                        list.latitud.toString(),
                                        list.longitud.toString()
                                    )
                                    fragmentManager =
                                        (context as AppCompatActivity).supportFragmentManager
                                    dialogMain.show(fragmentManager, "")
                                },
                                onFailure = {
                                    // No se pudo obtener la ubicación actual del usuario
                                }
                            )


                        }),
                    contentAlignment = Alignment.BottomEnd,

                    ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_directions_24),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
            Column(modifier = Modifier.padding(10.dp,0.dp,10.dp,0.dp)) {
                Row {
                    Box(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Razon Social",
                            //color = MaterialTheme.colors.secondaryVariant,
                            color = Color.Gray,
                            fontSize = 13.sp
                            ,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Box(modifier = Modifier.weight(3f)) {
                        Text(
                            text =list.nombrecliente.toString(),
                            //color = MaterialTheme.colors.secondaryVariant,
                            color = Color.Black,
                            style = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Row {
                    Box(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Dirección",
                            //color = MaterialTheme.colors.secondaryVariant,
                            color = Color.Gray,
                            fontSize = 13.sp
                            ,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Box(modifier = Modifier.weight(3f)) {
                        Text(
                            text = list.direccion.toString(),
                            //color = MaterialTheme.colors.secondaryVariant,
                            color = Color.Black,
                            style = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Column()
            {
                val listelementsinvoice: List<Pair<String, String>>

                if(list.estado.equals("ANULADO")||list.estado_id.equals("VOLVER A PROGRAMAR"))
                {
                    listelementsinvoice = listOf(
                        Pair("Factura", list.factura.toString()),
                        Pair("Cond.Venta",list.terminopago.toString()),
                        Pair("Saldo",list.saldo.toString()),
                        Pair("Entrega", list.entrega.toString()),
                        Pair("Estado", list.estado.toString()),
                        Pair("Motivo", list.motivo.toString()),
                        Pair("Vendedor", list.factura_fuerzatrabajo.toString()),
                    )
                }else{
                    listelementsinvoice = listOf(
                        Pair("Factura", list.factura.toString()),
                        Pair("Cond.Venta",list.terminopago.toString()),
                        Pair("Saldo",list.saldo.toString()),
                        Pair("Entrega", list.entrega.toString()),
                        Pair("Estado", list.estado.toString()),
                        Pair("Vendedor", list.factura_fuerzatrabajo.toString()),
                    )
                }
                val title:String = "Detalle"
                ExpandableCard(title,listelementsinvoice)

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

                val stepsStatus = arrayOf(
                    list.statusvisitstart,
                    list.statusupdatedispatch,
                    list.statuscollection,
                    list.statusvisitend
                )

                val numberStatus = arrayOf(
                    "1",
                    "2",
                    "3",
                    "4"
                )
                val openDialog = remember { mutableStateOf(false) }
                val openDialogend = remember { mutableStateOf(false) }

                HorizontalStepView(steps = steps, stepsStatus = stepsStatus, numberStatus = numberStatus
                    ,context,
                    InfoDialog = { openDialog.value = true },
                    InfoDialogEnd = { openDialogend.value = true },
                    list
                , formProcessCollection = { clienteId -> formProcessCollection(clienteId) }
                , formProcessStatusDispatch={list -> formProcessStatusDispatch(list)}
                )
                if (openDialog.value)
                {
                    InfoDialog(
                        title = "Importante",
                        desc = "Desea iniciar la visita al cliente?",
                        onDismiss = {
                            openDialog.value = false
                            //false
                        },
                        list,
                        context,
                        lifecycleOwner
                    )
                }
                if (openDialogend.value)
                {
                    InfoDialogEnd(
                        title = "Importante",
                        desc = "Desea cerrar la visita al cliente?",
                        onDismiss = {
                            openDialogend.value = false
                            //false
                        },
                        list,
                        context,
                        lifecycleOwner
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableCard(
    title:String,
    listelementsinvoice: List<Pair<String, String>>
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .clickable { expanded = !expanded },
            //.padding(20.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(10.dp,0.dp,10.dp,0.dp)) {
            Row() {
                Text(title,modifier = Modifier.padding(0.dp,10.dp,10.dp,10.dp),color = Color.Black,
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Bold)
                Icon(
                    imageVector =
                    if(expanded){
                        ImageVector.vectorResource(R.drawable.ic_baseline_arrow_drop_up_24_black)
                    }else{
                        ImageVector.vectorResource(R.drawable.ic_baseline_arrow_drop_down_24_black)
                    }
                    ,
                    contentDescription = null,
                    modifier = Modifier.padding(0.dp,10.dp,0.dp,0.dp)
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
                    listelementsinvoice.forEachIndexed { index, step ->
                        Row {
                            Box(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = step.first,
                                    //color = MaterialTheme.colors.secondaryVariant,
                                    color = Color.Gray,
                                    fontSize = 13.sp
                                    ,
                                    style = MaterialTheme.typography.body1
                                )
                            }
                            Box(modifier = Modifier.weight(3f)) {
                                Text(
                                    text = step.second,
                                    //color = MaterialTheme.colors.secondaryVariant,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.subtitle2,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun HorizontalStepView(
    steps: List<Pair<String, ImageVector>>,
    stepsStatus: Array<String?>,
    numberStatus: Array<String>,
    context: Context,
    InfoDialog: (status:String) -> Unit,
    InfoDialogEnd: (status:String) -> Unit,
    list: DetailDispatchSheet,
    formProcessCollection: (cliente_id:String) -> Unit,
    formProcessStatusDispatch: (list: DetailDispatchSheet) -> Unit,
) {

    val scope = rememberCoroutineScope()
    var currentBottomSheet: BottomSheetScreen? by remember { mutableStateOf(null) }
    val modal = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, confirmStateChange = {false})
    val appContext:Context = LocalContext.current
    val lifecycleOwner = LocalContext.current as LifecycleOwner
    var ObjUsuario = UsuarioSQLiteEntity()
    val usuarioSQLite = UsuarioSQLite(appContext)
    ObjUsuario = usuarioSQLite.ObtenerUsuarioSesion()

    Box {
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

            steps.forEachIndexed { index, step ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    val showDialog = remember { mutableStateOf(false) }
                    IconButton(
                        onClick = {
                            showDialog.value = true
                        },

                        ){
                        Icon(
                            imageVector = step.second,
                            contentDescription = null,
                            modifier =
                            Modifier
                                .size(30.dp),
                            tint = if (stepsStatus.get(index) == "Y") BlueVistony else Color.Gray,
                        )
                    }
                    if (showDialog.value)
                    {
                        StatusIcons(
                            step.first,
                            InfoDialog,
                            InfoDialogEnd,
                            list,
                            context,
                            //formProcessCollection = formProcessCollection(list.cliente_id)
                            { clienteId -> formProcessCollection(clienteId) }
                            //{formProcessCollection(index, list)}
                        , {list -> formProcessStatusDispatch(list)}
                        )
                        showDialog.value = false

                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(
                                    //BlueVistony
                                    if (stepsStatus.get(index) == "Y") BlueVistony else Color.Gray,
                                    CircleShape
                                )
                                .align(Alignment.Center)
                        ) {
                            if (stepsStatus.get(index) == "Y")
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_check),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                    //tint = if ( stepsStatus.get(index) == "Y") BlueVistony else Color.Gray
                                )
                            else
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_baseline_close_24_white),
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
                        color = if (stepsStatus.get(index) == "Y") BlueVistony else Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/*
@Composable
fun showLinearProgressIndicator(progress:Float){
    LinearProgressIndicator(
        progress = progress
        ,
        color = BlueVistony,
        backgroundColor = Color.Gray, // el color de fondo siempre será gris
        //progress = 0.3f,
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp, 75.dp, 45.dp, 0.dp)
    )
}*/

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun StatusIcons(
    icons:String,
    InfoDialog: (status:String) -> Unit,
    InfoDialogEnd: (status:String) -> Unit,
    list: DetailDispatchSheet,
    context1: Context,
    formProcessCollection:(cliente_id:String) -> Unit,
    formProcessStatusDispatch:(detailDispatchSheet:DetailDispatchSheet) -> Unit,
)
{
    var activity1 = LocalContext.current as Activity
    contexto  = context1
    Log.e(
        "REOS",
        "Composables-StatusIcons-Ingreso"
    )
    when (icons) {
        "Entrada" -> {
            InfoDialog("Entrada")
        }
        "Despacho" -> {
            /*val dialogFragment: DialogFragment = StatusDispatchDialog(
                list.cliente_id,
                list.nombrecliente,
                list.control_id.toString(),
                list.item_id.toString(),
                list.domembarque_id
            )
            dialogFragment.show(
                (context as FragmentActivity).supportFragmentManager,
                "un dialogo"
            )*/
            formProcessStatusDispatch(list)
            //showNotification(context)
            //Inicio de Servicio y Envio de Notificacion
            /*try {
                val intent = Intent(context1, ServiceNotificationApp::class.java)
                Log.e("REOS", "Composables-StatusIcons-contexto: "+context1)
                Log.e("REOS", "Composables-StatusIcons-intent: "+intent)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.e("REOS", "Composables-StatusIcons-startForegroundService")
                    ContextCompat.startForegroundService(activity1, intent)
                } else {
                    Log.e("REOS", "Composables-StatusIcons-startService ")
                    var serviceNotificationApp:ServiceNotificationApp=ServiceNotificationApp()
                    serviceNotificationApp.startService(intent)
                }
            }catch (e:Exception){
                Log.e("REOS", "Composables-StatusIcons-error: "+e.toString())
            }*/
            ///////////////////////////////////////////

            /*
            LaunchedEffect(Unit) {
                if (areNotificationsEnabled(context)) {
                    showNotification(context)
                    // Las notificaciones están habilitadas
                } else {
                    openNotificationSettings(context)
                    // Las notificaciones están deshabilitadas
                }
                //openNotificationSettings(context)
                //showNotification(context)
            }*/
        }
        "Cobranza" -> {
            Log.e(
                "REOS",
                "Composables-StatusIcons-Cobranza"
            )
            formProcessCollection(list.cliente_id)
            }
        "Salida" -> {
            InfoDialogEnd("Salida")
        }
    }
    Log.e(
        "REOS",
        "Composables-StatusIcons-Final"
    )
}

@Composable
fun InfoDialog(
    title: String?="Message",
    desc: String?="Your Message",
    onDismiss: () -> Unit,
    list: DetailDispatchSheet,
    context: Context,
    lifecycleOwner:  LifecycleOwner
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {

        Box(
            modifier = Modifier
                .height(300.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.height(90.dp))
                Box(
                    modifier = Modifier
                        .height(490.dp)
                        .background(
                            //color = MaterialTheme.colorScheme.onPrimary,
                            color = MaterialTheme.colors.onPrimary,
                            shape = RoundedCornerShape(25.dp, 10.dp, 25.dp, 10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = title!!.toUpperCase(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            color = Color.Black,
                            style = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))


                        Text(
                            text = desc!!,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(){
                            Button(
                                onClick = {


                                    val geoLocalizacion = Geolocation(context)

                                    geoLocalizacion.obtenerUbicacionActual(
                                        onSuccess = { location ->
                                            var latitude:String=""
                                            var longitude:String=""
                                            latitude=location.latitude.toString()
                                            longitude=location.longitude.toString()
                                            var visitSectionViewModel: VisitSectionViewModel
                                            val visitSectionRepository = VisitSectionRepository()
                                            visitSectionViewModel = VisitSectionViewModel(visitSectionRepository)
                                            val dateFormathora =
                                                SimpleDateFormat("HHmmss", Locale.getDefault())
                                            val FormatFecha =
                                                SimpleDateFormat("yyyyMMdd", Locale.getDefault())
                                            val date = Date()
                                            visitSectionViewModel?.getVisitSection(
                                                SesionEntity.imei,
                                                context,
                                                lifecycleOwner,
                                                list.cliente_id.toString(),
                                                list.domembarque_id.toString(),
                                                FormatFecha.format(date),
                                                list.control_id.toString()
                                            )
                                            visitSectionViewModel.list.observe(lifecycleOwner) { data ->
                                                // actualizar la UI con los datos obtenidos
                                                Log.e(
                                                    "REOS",
                                                    "Composables-InfoDialog.visitSectionViewModel.observe.data.size"+data.size
                                                )

                                                if(data.isEmpty())
                                                {

                                                    if(location.latitude==0.0&&location.longitude==0.0)
                                                    {
                                                        Log.e(
                                                            "REOS",
                                                            "Composables-InfoDialog.geoLocalizacion.entroif"
                                                        )
                                                    }else{
                                                        Log.e(
                                                            "REOS",
                                                            "Composables-InfoDialog.geoLocalizacion.noentroif"
                                                        )
                                                        try {
                                                            var listVisitSection: MutableList<VisitSection> =
                                                                mutableListOf()

                                                            var ObjUsuario = UsuarioSQLiteEntity()
                                                            val usuarioSQLite: UsuarioSQLite =
                                                                UsuarioSQLite(context)
                                                            ObjUsuario =
                                                                usuarioSQLite.ObtenerUsuarioSesion()
                                                            var visitSection: VisitSection? = VisitSection(
                                                                //ObtenerFechaHoraCadena().toInt(),
                                                                0,
                                                                ObjUsuario.compania_id,
                                                                ObjUsuario.fuerzatrabajo_id,
                                                                ObjUsuario.usuario_id,
                                                                list.cliente_id.toString(),
                                                                list.domembarque_id.toString(),
                                                                latitude,
                                                                longitude,
                                                                FormatFecha.format(date),
                                                                dateFormathora.format(date),
                                                                "0",
                                                                "0",
                                                                "0",
                                                                "0",
                                                                "0",
                                                                list.control_id.toString(),
                                                                list.nombrecliente.toString(),
                                                                list.item_id.toString(),
                                                                list.entrega.toString()
                                                            )
                                                            if (visitSection != null) {
                                                                listVisitSection?.add(visitSection)
                                                            }

                                                            Log.e(
                                                                "REOS",
                                                                "Composables-InfoDialog.visitSectionViewModel.visitSection" + visitSection
                                                            )
                                                            visitSectionViewModel?.addVisitSection(
                                                                listVisitSection,
                                                                lifecycleOwner,
                                                                context,
                                                            )
                                                            visitSectionViewModel.status.observe(
                                                                lifecycleOwner
                                                            ) { data ->
                                                                // actualizar la UI con los datos obtenidos
                                                                Log.e(
                                                                    "REOS",
                                                                    "Composables-InfoDialog.visitSectionViewModel.observe.statusadd.size" + data.toString()
                                                                )
                                                            }
                                                        }catch (e: Exception)
                                                        {
                                                            Log.e(
                                                                "REOS",
                                                                "Composables-InfoDialog-error" + e.toString()
                                                            )
                                                        }

                                                    }

                                                }else {
                                                    Log.e(
                                                        "REOS",
                                                        "Composables-InfoDialog.geoLocalizacion.lalistatraedata"
                                                    )
                                                }
                                            }
                                            Log.e(
                                                "REOS",
                                                "Composables-InfoDialog.geoLocalizacion.latitude"+latitude
                                            )
                                            Log.e(
                                                "REOS",
                                                "Composables-InfoDialog.geoLocalizacion.longitude"+longitude
                                            )

                                        },
                                        onFailure = {
                                            // No se pudo obtener la ubicación actual del usuario
                                        }
                                    )



                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    text = "Aceptar",
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = onDismiss,
                                //colors = ButtonDefaults.buttonColors(Colors = MaterialTheme.colors.primary),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    text = "Cerrar",
                                    color = Color.White
                                )
                            }
                        }

                    }
                }
            }
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .clip(
                        RoundedCornerShape(
                            topEndPercent = 50,
                            bottomStartPercent = 50, topStartPercent = 50, bottomEndPercent = 50
                        )
                    )
                    //.background(Color(0xFF5FA777))
                    .background(
                        Color.White
                    )
                    .align(Alignment.TopCenter)
            )

            Image(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = "Google Maps", // decorative
                contentScale = ContentScale.Crop,


                modifier = Modifier
                    //Set Image size to 40 dp
                    .size(120.dp)
                    .align(Alignment.TopCenter)
                    .padding(0.dp, 20.dp, 0.dp, 0.dp)
            )
            /*HeaderImage(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.TopCenter),
                        "Question"

                )*/

        }
    }
}


@Composable
fun InfoDialogEnd(
    title: String?="Message",
    desc: String?="Your Message",
    onDismiss: () -> Unit,
    list: DetailDispatchSheet,
    context: Context,
    lifecycleOwner:  LifecycleOwner
) {
    var statusDispatchRepository:StatusDispatchRepository= StatusDispatchRepository()

    val statusDispatchViewModel: StatusDispatchViewModel = viewModel(
        factory = StatusDispatchViewModel.StatusDispatchViewModelFactory(
            statusDispatchRepository,
            context
        )
    )

    Dialog(
        onDismissRequest = onDismiss
    ) {

        Box(
            modifier = Modifier
                .height(300.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.height(90.dp))
                Box(
                    modifier = Modifier
                        .height(490.dp)
                        .background(
                            //color = MaterialTheme.colorScheme.onPrimary,
                            color = MaterialTheme.colors.onPrimary,
                            shape = RoundedCornerShape(25.dp, 10.dp, 25.dp, 10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = title!!.toUpperCase(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            color = Color.Black,
                            style = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))


                        Text(
                            text = desc!!,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(){
                            Button(
                                onClick = {


                                    val geoLocalizacion = Geolocation(context)

                                    geoLocalizacion.obtenerUbicacionActual(
                                        onSuccess = { location ->
                                            var latitude:String=""
                                            var longitude:String=""
                                            latitude=location.latitude.toString()
                                            longitude=location.longitude.toString()
                                            var visitSectionViewModel: VisitSectionViewModel
                                            val visitSectionRepository = VisitSectionRepository()
                                            visitSectionViewModel = VisitSectionViewModel(visitSectionRepository)
                                            val dateFormathora =
                                                SimpleDateFormat("HHmmss", Locale.getDefault())
                                            val FormatFecha =
                                                SimpleDateFormat("yyyyMMdd", Locale.getDefault())
                                            val date = Date()
                                            visitSectionViewModel?.getVisitSection(
                                                SesionEntity.imei,
                                                context,
                                                lifecycleOwner,
                                                list.cliente_id.toString(),
                                                list.domembarque_id.toString(),
                                                FormatFecha.format(date),
                                                list.control_id.toString()
                                            )
                                            visitSectionViewModel.list.observe(lifecycleOwner) { data ->
                                                // actualizar la UI con los datos obtenidos
                                                Log.e(
                                                    "REOS",
                                                    "Composables-InfoDialogEnd.visitSectionViewModel.observe.data.size"+data.size
                                                )

                                                if(!data.isEmpty())
                                                {
                                                    if(location.latitude!=0.0&&location.longitude!=0.0)
                                                    {
                                                        Log.e(
                                                            "REOS",
                                                            "Composables-InfoDialogEnd.visitSectionViewModel.location.latitude"+location.latitude
                                                        )
                                                        Log.e(
                                                            "REOS",
                                                            "Composables-InfoDialogEnd.visitSectionViewModel.location.longitude"+location.longitude
                                                        )

                                                        data.forEachIndexed { index, step ->
                                                            data.get(index).datefin=FormatFecha.format(date)
                                                            data.get(index).timefin=dateFormathora.format(date)
                                                            data.get(index).latitudfin=latitude
                                                            data.get(index).longitudfin=longitude
                                                        }

                                                        /*for (i in 0..data.size) {
                                                            data.get(i).datefin=FormatFecha.format(date)
                                                            data.get(i).timefin=dateFormathora.format(date)
                                                            data.get(i).latitudfin=latitude
                                                            data.get(i).longitudfin=longitude
                                                        }*/

                                                        visitSectionViewModel?.updateVisitSection(
                                                            data,
                                                            lifecycleOwner,
                                                            context,
                                                        )

                                                        visitSectionViewModel.status.observe(
                                                            lifecycleOwner
                                                        ) { data ->
                                                            // actualizar la UI con los datos obtenidos
                                                            Log.e(
                                                                "REOS",
                                                                "Composables-InfoDialogEnd.visitSectionViewModel.observe.statusadd.size" + data.toString()
                                                            )
                                                        }

                                                        if (SesionEntity.perfil_id == "CHOFER" || SesionEntity.perfil_id == "chofer") {
                                                            data.forEachIndexed { index, step ->
                                                                statusDispatchViewModel.updateStatusDispatch(
                                                                    data.get(index).timeini,
                                                                    data.get(index).timefin,
                                                                    data.get(index).latitudini,
                                                                    data.get(index).longitudini,
                                                                    list.domembarque_id,
                                                                    list.cliente_id
                                                                )
                                                                /*data.get(index).datefin=FormatFecha.format(date)
                                                                data.get(index).timefin=dateFormathora.format(date)
                                                                data.get(index).latitudfin=latitude
                                                                data.get(index).longitudfin=longitude*/
                                                            }

                                                            /*statusDispatchSQLite.UpdateTimeStatusDispatch(
                                                                MenuAccionView.CardCode,
                                                                MenuAccionView.DomEmbarque_ID,
                                                                timeini,
                                                                dateFormathora.format(date),
                                                                Latitudini,
                                                                Longitudini
                                                            )
                                                            val executor = AppExecutors()
                                                            /////////////////////ENVIAR RECIBOS PENDIENTES SIN DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                                                            //statusDispatchRepository.statusDispatchSendTime(getContext()).observe(getActivity(), data -> {
                                                            statusDispatchRepository.statusDispatchSendTime(
                                                                getContext(),
                                                                executor.diskIO()
                                                            ).observe(getActivity(),
                                                                androidx.lifecycle.Observer { data: String ->
                                                                    Log.e(
                                                                        "REOS",
                                                                        "statusDispatchRepository-->statusDispatchSend-->resultdata$data"
                                                                    )
                                                                })*/
                                                        }


                                                    }else{


                                                    }

                                                }else {
                                                    Log.e(
                                                        "REOS",
                                                        "Composables-InfoDialog.geoLocalizacion.lalistatraedata"
                                                    )
                                                }
                                            }
                                        },
                                        onFailure = {
                                            // No se pudo obtener la ubicación actual del usuario
                                        }
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    text = "Aceptar",
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = onDismiss,
                                //colors = ButtonDefaults.buttonColors(Colors = MaterialTheme.colors.primary),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    text = "Cerrar",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .clip(
                        RoundedCornerShape(
                            topEndPercent = 50,
                            bottomStartPercent = 50, topStartPercent = 50, bottomEndPercent = 50
                        )
                    )
                    //.background(Color(0xFF5FA777))
                    .background(
                        Color.White
                    )
                    .align(Alignment.TopCenter)
            )

            Image(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = "Google Maps", // decorative
                contentScale = ContentScale.Crop,


                modifier = Modifier
                    //Set Image size to 40 dp
                    .size(120.dp)
                    .align(Alignment.TopCenter)
                    .padding(0.dp, 20.dp, 0.dp, 0.dp)
            )
            /*HeaderImage(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.TopCenter),
                        "Question"

                )*/

        }
    }
}



@Composable
fun InfoDialogCollection(
    title: String?="Message",
    desc: String?="Your Message",
    onDismiss: () -> Unit,
    context: Context,
    lifecycleOwner:  LifecycleOwner
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {

        Box(
            modifier = Modifier
                .height(300.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.height(90.dp))
                Box(
                    modifier = Modifier
                        .height(490.dp)
                        .background(
                            //color = MaterialTheme.colorScheme.onPrimary,
                            color = MaterialTheme.colors.onPrimary,
                            shape = RoundedCornerShape(25.dp, 10.dp, 25.dp, 10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = title!!.toUpperCase(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            color = Color.Black,
                            style = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))


                        Text(
                            text = desc!!,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(){
                            Button(
                                onClick = {
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    text = "Aceptar",
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = onDismiss,
                                //colors = ButtonDefaults.buttonColors(Colors = MaterialTheme.colors.primary),
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    text = "Cerrar",
                                    color = Color.White
                                )
                            }
                        }

                    }
                }
            }
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .clip(
                        RoundedCornerShape(
                            topEndPercent = 50,
                            bottomStartPercent = 50, topStartPercent = 50, bottomEndPercent = 50
                        )
                    )
                    //.background(Color(0xFF5FA777))
                    .background(
                        Color.White
                    )
                    .align(Alignment.TopCenter)
            )

            Image(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = "Google Maps", // decorative
                contentScale = ContentScale.Crop,


                modifier = Modifier
                    //Set Image size to 40 dp
                    .size(120.dp)
                    .align(Alignment.TopCenter)
                    .padding(0.dp, 20.dp, 0.dp, 0.dp)
            )
        }
    }
}

