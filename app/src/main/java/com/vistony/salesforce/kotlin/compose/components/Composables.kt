package com.vistony.salesforce.kotlin.compose.components

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.*
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.compose.DialogMain
import com.vistony.salesforce.kotlin.compose.theme.BlueVistony
import com.vistony.salesforce.kotlin.data.*
import com.vistony.salesforce.kotlin.utilities.Geolocation
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun ScreenDispatch(
    listDispatch: List<DetailDispatchSheet>,
    context: Context,
    lifecycleOwner:  LifecycleOwner
    )
{
    LazyColumn{
        items(listDispatch){ objDistpatch ->
            CardDispatch(objDistpatch,context,lifecycleOwner)
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardDispatch(
    list: DetailDispatchSheet,
    context: Context ,
    lifecycleOwner:  LifecycleOwner

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
                            text = list.nombrecliente.toString(),
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

                Log.e(
                    "REOS",
                    "Composables-CardDispatch-list.comentariodespacho" + list.comentariodespacho
                )
                Log.e(
                    "REOS",
                    "Composables-CardDispatch-list.item_id" + list.item_id
                )
                Log.e(
                    "REOS",
                    "Composables-CardDispatch-statuscollection." + list.statuscollection
                )
                Log.e(
                    "REOS",
                    "Composables-CardDispatch-statusvisitstart." + list.statusvisitstart
                )
                Log.e(
                    "REOS",
                    "Composables-CardDispatch-statusvisitend." + list.statusvisitend
                )
                Log.e(
                    "REOS",
                    "Composables-CardDispatch-statusupdatedispatch." + list.statusupdatedispatch
                )

                /*PruebaDialog(
                    title = "Turn on Location Service",
                    desc = "Explore the world without getting lost and keep the track of your location.",
                    onDismiss = {
                        //infoDialog.value = false
                        false
                    },
                    true
                )*/
                val openDialog = remember { mutableStateOf(false) }
                val openDialogend = remember { mutableStateOf(false) }
                HorizontalStepView(steps = steps, stepsStatus = stepsStatus, numberStatus = numberStatus
                    ,context,
                    InfoDialog = { openDialog.value = true },
                    InfoDialogEnd = { openDialogend.value = true }
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
                        list
                    )
                }


                /*if (openDialog.value) {
                    showDialog(
                        openDialog = openDialog.value,
                        onDialogDismiss = { openDialog.value = false }
                    )
                }*/

                /*PruebaDialog(
                    title = "Turn on Location Service",
                    desc = "Explore the world without getting lost and keep the track of your location.",
                    onDismiss = {
                        //infoDialog.value = false
                        true
                    })*/
                /*if(list.estado.equals("ANULADO")||list.estado_id.equals("VOLVER A PROGRAMAR"))
                {
                    Row {
                        Box(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Motivo",
                                //color = MaterialTheme.colors.secondaryVariant,
                                color = Color.Gray,
                                fontSize = 13.sp,
                                style = MaterialTheme.typography.body1
                            )
                        }
                        Box(modifier = Modifier.weight(3f)) {
                            Text(
                                text = list.motivo.toString(),
                                //color = MaterialTheme.colors.secondaryVariant,
                                color = Color.Black,
                                style = MaterialTheme.typography.subtitle2,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }*/

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

                /*Text(
                    "Content",
                    modifier = Modifier.padding(16.dp)
                )*/
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

@Composable
fun HorizontalStepView(
    steps: List<Pair<String, ImageVector>>,
    stepsStatus: Array<String?>,
    numberStatus: Array<String>,
    context: Context,
    InfoDialog: (status:String) -> Unit,
    InfoDialogEnd: (status:String) -> Unit
) {

    Box() {
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
                .padding(40.dp, 55.dp, 45.dp, 0.dp)
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
                    Icon(
                        imageVector = step.second,
                        contentDescription = null,
                        modifier =
                        Modifier
                            .size(30.dp)
                            .clickable {
                                if (step.first.equals("Entrada")) {
                                    InfoDialog(step.first.toString())
                                } else if (step.first.equals("Despacho")) {

                                } else if (step.first.equals("Cobranza")) {

                                } else if (step.first.equals("Salida")) {
                                    InfoDialogEnd(step.first.toString())
                                }
                            },
                        tint = if (stepsStatus.get(index) == "Y") BlueVistony else Color.Gray,

                    )
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
                            Log.e(
                                "REOS",
                                "Composables-CardDispatch-stepsStatus.size." + stepsStatus.size
                            )
                            Log.e(
                                "REOS",
                                "Composables-CardDispatch-index." + index
                            )
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

@Composable
private fun  DialogInformative() {

    var dialogOpen by remember {
        mutableStateOf(false)
    }

    if (dialogOpen) {
        Dialog(onDismissRequest = {
            dialogOpen = false
        }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(size = 10.dp)
            ) {
                Column(modifier = Modifier.padding(all = 16.dp)) {
                    Text(text = "Your Dialog UI Here")
                }
            }
        }
    }

    Button(onClick = { dialogOpen = true }) {
        Text(text = "OPEN")
    }
}


@Composable
fun showDialog(
    openDialog: Boolean,
    onDialogDismiss: () -> Unit) {
    if (openDialog) {

        AlertDialog(

            onDismissRequest = onDialogDismiss,
            title = {


                    Text(text = "Título del diálogo")


                    },
            text = { Text(text = "Mensaje del diálogo") },
            confirmButton = {
                Button(onClick = onDialogDismiss) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {
                Button(onClick = onDialogDismiss) {
                    Text(text = "Cerrar")
                }
            }
        )
        /*HeaderImage(
            modifier = Modifier
                .size(200.dp)
                //.align(Alignment.TopCenter)
                .clickable {},
            "Question"
        )*/
    }
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
                                    var visitSectionViewModel: VisitSectionViewModel
                                    val visitSectionRepository = VisitSectionRepository()
                                    visitSectionViewModel = VisitSectionViewModel(visitSectionRepository)
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
                                    visitSectionViewModel.status.observe(lifecycleOwner) { data ->
                                        // actualizar la UI con los datos obtenidos
                                        Log.e(
                                            "REOS",
                                            "Composables-InfoDialog.actualizar.observe.data.size"+data.size
                                        )
                                    }
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
    list: DetailDispatchSheet
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
                                onClick = onDismiss,
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

