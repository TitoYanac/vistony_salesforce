@file:OptIn(ExperimentalFoundationApi::class)

package com.vistony.salesforce.kotlin.View.components

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.View.MenuView
import com.vistony.salesforce.kotlin.Model.*
import com.vistony.salesforce.kotlin.View.Molecules.CardDispatch
import com.vistony.salesforce.kotlin.View.Orgsnisms.contentMapNavigation
import com.vistony.salesforce.kotlin.View.Orgsnisms.contentVisitDriver
import com.vistony.salesforce.kotlin.View.Template.BottomSheetScreen
import com.vistony.salesforce.kotlin.View.Template.SheetLayout
import kotlinx.coroutines.launch

var contexto: Context = MenuView.context

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class
)
@Composable
fun DispatchSheetTemplate(
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
    val openDialogEditCommentary:MutableState<Boolean?> = remember { mutableStateOf(false) }
    val tittleDialogPhoto:MutableState<String> = remember { mutableStateOf("") }
    var expandedCommentary by remember { mutableStateOf(true) }
    var commentary :MutableState<String> = remember { mutableStateOf("") }
    val invoicesRepository:InvoicesRepository= InvoicesRepository()
    val openVisitDriver:MutableState<Boolean> = remember { mutableStateOf(false) }
    val openDialogMapNavigation:MutableState<Boolean> = remember { mutableStateOf(false) }
    val latitud:MutableState<String> = remember { mutableStateOf("") }
    val longitud:MutableState<String> = remember { mutableStateOf("") }


    var statusDispatchRepository:StatusDispatchRepository= StatusDispatchRepository()
    val statusDispatchViewModel: StatusDispatchViewModel = viewModel(
        factory = StatusDispatchViewModel.StatusDispatchViewModelFactory(
            statusDispatchRepository,
            context
        )
    )

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
        DialogView(
        tittle = tittleDialogPhoto.value
            , subtittle = ""
        ,onClickCancel = {
            openDialogShowImage.value = false
        }
        ,onClickAccept = {
                openDialogShowImage.value = false
        }
        ,statusButtonAccept = false
        ,statusButtonIcon = false
                    ,context=context
        ){
            Image(
                bitmap = bitMapMutable.value!!.asImageBitmap(),
                contentDescription = "Google Maps", // decorative
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    //Set Image size to 40 dp
                    .size(400.dp)
                    //.align(Alignment.TopCenter)
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
            )
        }
    }

    if(openDialogEditCommentary.value!!)
    {
        DialogView(
            "Comentario"
            ,""
            ,onClickCancel = {
                openDialogEditCommentary.value = false
            }
            ,onClickAccept = {
                openDialogEditCommentary.value = false
            }
            ,statusButtonAccept = true
            ,statusButtonIcon = false
            ,context=context
        ){
            Editext(
                expandedCommentary
                ,commentary
                ,"Ingrese Comentario"
                ,"Comentario"
                ,painterResource(id = R.drawable.ic_insert_comment_black_24dp)
                , KeyboardType.Text
            )
        }
    }


    /*val numberStatus = arrayOf(
        "1",
        "2",
        "3",
        "4"
    )*/
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
                val listelementsinvoice: List<Pair<String, String>>
                        = listOf(
                    Pair("Factura", objDistpatch.factura.toString()),
                    Pair("Cond.Venta",objDistpatch.terminopago.toString()),
                    Pair("Saldo",objDistpatch.saldo.toString()),
                    Pair("Entrega", objDistpatch.entrega.toString()),
                    Pair("Estado", objDistpatch.estado.toString()),
                    Pair("Motivo", objDistpatch.motivo.toString()),
                    Pair("Vendedor", objDistpatch.factura_fuerzatrabajo.toString()),
                )
                val stepsStatus = arrayOf(
                    objDistpatch.statusvisitstart,
                    objDistpatch.statusupdatedispatch,
                    objDistpatch.statuscollection,
                    objDistpatch.statusvisitend
                )
                val openSheet: (BottomSheetScreen) -> Unit = {
                    scope.launch {
                        currentBottomSheet = it
                        modal.animateTo(ModalBottomSheetValue.Expanded)
                    }
                }
                val invoiceViewModel: InvoicesViewModel= viewModel(
                    factory = InvoicesViewModel.InvoiceModelFactory(
                        SesionEntity.imei,
                        context,
                        lifecycleOwner,
                        objDistpatch.cliente_id,
                        invoicesRepository
                    )
                )

                if(openVisitDriver.value!!)
                {
                    DialogView(
                        tittle = "Registre su visita"
                        , subtittle = if(objDistpatch.statusvisitstart.equals("Y")){"Desea finalizar la visita al cliente?"}else{"Desea iniciar la visita al cliente?"}
                        ,onClickCancel = {
                            openVisitDriver.value = false
                        }
                        ,onClickAccept = {

                            contentVisitDriver(
                                context = context,
                                lifecycleOwner = lifecycleOwner,
                                list = objDistpatch,
                                statusDispatchViewModel =statusDispatchViewModel,
                                statusStartVisit =objDistpatch.statusvisitstart.toString()
                            )
                            openVisitDriver.value = false
                        }
                        ,statusButtonAccept = true
                        ,statusButtonIcon = false
                        ,context=context
                    ){

                    }
                }

                if(openDialogMapNavigation.value!!)
                {
                    DialogView(
                        tittle = "Mapa de Navegacion"
                        , subtittle = "Elija el mapa de su preferencia:"
                        ,onClickCancel = {
                            openDialogMapNavigation.value = false
                        }
                        ,onClickAccept = {
                            openDialogMapNavigation.value = false
                        }
                        ,statusButtonAccept = false
                        ,statusButtonIcon = false
                        ,context=context
                    ){
                        contentMapNavigation(
                            context,
                            latitud.value,
                            longitud.value,
                            objDistpatch.latitud.toString(),
                            objDistpatch.longitud.toString()
                        )
                    }
                }


                CardDispatch(
                    objDistpatch,context,
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
                            BottomSheetScreen.processDispatch(
                                objDistpatch,
                                bitMapMutable,
                                openDialogShowImage,
                                context,
                                openDialogEditCommentary,
                                tittleDialogPhoto
                            )
                        )
                    },
                    listelementsinvoice,
                    //steps,
                    //numberStatus,
                    stepsStatus,
                    openVisitDriver,
                    openDialogMapNavigation,
                    latitud,
                    longitud
                )
            }
        }
    }
}









/*
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
                                                            }
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
*/

