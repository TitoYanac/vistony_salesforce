package com.vistony.salesforce.kotlin.View.Pages

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Controller.Utilitario.Induvis.getTime
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.View.ContainerDispatchView
import com.vistony.salesforce.kotlin.View.Atoms.theme.VistonyTheme
import com.vistony.salesforce.kotlin.Model.*
import com.vistony.salesforce.kotlin.View.Molecules.SpinnerView

class DispatchSheetMapScreen : Fragment(),ViewModelStoreOwner
    ,OnMapReadyCallback

{
    //private lateinit var mMap: GoogleMap  //declaration inside class
    //private lateinit var headerDispatchSheetViewModel: HeaderDispatchSheetViewModel
    private lateinit var headerDispatchSheetViewModel: HeaderDispatchSheetViewModel
    private lateinit var clientViewModel: ClientViewModel
    private lateinit var detailDispatchSheetViewModel: DetailDispatchSheetViewModel
    private lateinit var typeDispatchViewModel: TypeDispatchViewModel
    private lateinit var reasonDispatchViewModel: ReasonDispatchViewModel

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                //Text(text = "Hello world.")
                VistonyTheme() {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        //val typesCollection = listOf( "Cobranza Ordinaria", "Deposito Directo","Pago POS", "Cobro Vendedor", "Pago Adelantado")
                        //val currentSelected = remember { mutableStateOf(typesCollection.find { it.toUpperCase() == typesCollection.toString().toUpperCase() } ?: typesCollection.first()) }
                        Column {
                            /*SpinnerView(
                                "Seleccione tipo de cobranza: ", typesCollection, currentSelected
                            )*/
                            val appContext = LocalContext.current
                            val lifecycleOwner = LocalContext.current as LifecycleOwner
                            val detailDispatchSheetRepository = DetailDispatchSheetRepository()
                            val detailDispatchSheetViewModel: DetailDispatchSheetViewModel =
                                viewModel(
                                    factory = DetailDispatchSheetViewModel.DetailDispatchSheetViewModelFactory(
                                        detailDispatchSheetRepository,
                                        appContext,
                                        SesionEntity.imei,
                                    )
                                )
                            detailDispatchSheetViewModel?.getStateDetailDispatchSheet(
                                ContainerDispatchView.parametrofecha,
                                "M"
                            )

                            var detailDispatchSheetDB =
                                detailDispatchSheetViewModel.resultDB.collectAsState()
                            //val notificationEntity by notificationViewModel.notificationLiveData.observeAsState(NotificationEntity())

                            when (detailDispatchSheetDB.value.Status) {
                                "Y" -> {
                                    StartGoogleMaps(detailDispatchSheetDB.value.UI)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
        /*Log.e(
            "REOS",
            "GooleMaps-onMapReady-ingreso"
        )*/
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(tag: String?, dato: Any?)
    }

    var mListener: OnFragmentInteractionListener? = null
}




@Composable
fun StartGoogleMaps(
    lista:List<DetailDispatchSheetUI>
)
{
    val appContext = LocalContext.current
    Log.e(
        "REOS",
        "DispatchSheetMapScreen-headerDispatchSheetViewModel-fin"
    )
    val openDialogShowMap: MutableState<Boolean> = remember { mutableStateOf(true) }


    GoogleMap(modifier = Modifier.fillMaxSize(),
        properties = MapProperties
            (mapType = MapType.NORMAL, isMyLocationEnabled = true )

        ){

        /*val icon = bitmapDescriptorFromVector(
            LocalContext.current, R.drawable.ic_baseline_place_24
        )*/



        //var lista=DetailDispatchSheetSQLite(appContext).getDetailDispatchSheetforCodeControlMap(ContainerDispatchView.parametrofecha)

            Log.e(
                "REOS",
                "DispatchSheetMapScreen-StartGoogleMaps-lista"+lista
            )
            for ( i in 0..lista.size-1)
            {
                val marker = com.google.android.gms.maps.model.LatLng(
                    Convert.stringToDouble(lista[i].latitud)
                    ,
                    Convert.stringToDouble(lista[i].longitud)
                )

                MarkerInfoWindow(
                    state = MarkerState(position = marker),
                    visible = openDialogShowMap.value,
                    icon = (BitmapDescriptorFactory.fromBitmap(
                        createMarkerIcon(
                            lista[i].item_id.toString(),
                            appContext,
                            appContext.getResources().getColor(R.color.gray)
                        )
                    )),
                ) { marker ->
                    Box(
                        modifier = Modifier
                            //.height(300.dp)
                            //.fillMaxHeight()
                            .width(300.dp)
                    ) {
                        Column(
                            modifier = Modifier
                        ) {
                            Spacer(modifier = Modifier.height(90.dp))
                            Box(
                                modifier = Modifier
                                    //.height(490.dp)
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
                                        text = lista[i].nombrecliente.toString(),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            //.padding(top = 10.dp)
                                            .fillMaxWidth(),
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Black
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    //.........................Text : description
                                    Text(
                                        text = lista[i].domembarque_id +" "+lista[i].direccion,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(top = 10.dp)
                                            .fillMaxWidth(),
                                        style = TextStyle(
                                            color = Color.Gray,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Black
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Column(modifier = Modifier.padding(10.dp,0.dp,10.dp,0.dp)) {
                                        Row {
                                            Box(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = "Código",
                                                    //color = MaterialTheme.colors.secondaryVariant,
                                                    color = Color.Gray,
                                                    fontSize = 13.sp
                                                    ,
                                                    style = MaterialTheme.typography.body1
                                                )
                                            }
                                            Box(modifier = Modifier.weight(3f)) {
                                                Text(
                                                    text = lista[i].control_id.toString(),
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
                                                    text = "Item",
                                                    //color = MaterialTheme.colors.secondaryVariant,
                                                    color = Color.Gray,
                                                    fontSize = 13.sp
                                                    ,
                                                    style = MaterialTheme.typography.body1
                                                )
                                            }
                                            Box(modifier = Modifier.weight(3f)) {
                                                Text(
                                                    text = lista[i].item_id.toString(),
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
                                                    text = "Hora",
                                                    //color = MaterialTheme.colors.secondaryVariant,
                                                    color = Color.Gray,
                                                    fontSize = 13.sp
                                                    ,
                                                    style = MaterialTheme.typography.body1
                                                )
                                            }
                                            Box(modifier = Modifier.weight(3f)) {
                                                Text(
                                                    text = getTime(BuildConfig.FLAVOR, lista[i].timefin.toString()),
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
                                                    text = "Estado",
                                                    //color = MaterialTheme.colors.secondaryVariant,
                                                    color = Color.Gray,
                                                    fontSize = 13.sp
                                                    ,
                                                    style = MaterialTheme.typography.body1
                                                )
                                            }
                                            Box(modifier = Modifier.weight(3f)) {
                                                Text(
                                                    text = lista[i].estado.toString(),
                                                    //color = MaterialTheme.colors.secondaryVariant,
                                                    color = Color.Black,
                                                    style = MaterialTheme.typography.subtitle2,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        if(lista[i].estado.equals("ANULADO")||lista[i].estado.equals("VOLVER A PROGRAMAR"))
                                        {
                                            Row {
                                                Box(modifier = Modifier.weight(1f)) {
                                                    Text(
                                                        text = "Motivo",
                                                        //color = MaterialTheme.colors.secondaryVariant,
                                                        color = Color.Gray,
                                                        fontSize = 13.sp
                                                        ,
                                                        style = MaterialTheme.typography.body1
                                                    )
                                                }
                                                Box(modifier = Modifier.weight(3f)) {
                                                    Text(
                                                        text = lista[i].motivo.toString(),
                                                        //color = MaterialTheme.colors.secondaryVariant,
                                                        color = Color.Black,
                                                        style = MaterialTheme.typography.subtitle2,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    /*Row(){
                                        Button(
                                            onClick = {
                                                Log.e("REOS", "DispatchSheetMapScreen-StartGoogleMaps-openDialogShowMap: " +openDialogShowMap.value)
                                                openDialogShowMap.value=false
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
                                    }*/

                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .size(130.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topEndPercent = 50,
                                        bottomStartPercent = 50,
                                        topStartPercent = 50,
                                        bottomEndPercent = 50
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
                val icono=null
                when (lista[i].estado){
                    "ENTREGADO" -> {
                        Marker(
                            state = MarkerState(position = marker),
                            icon = (BitmapDescriptorFactory.fromBitmap(createMarkerIcon(
                                lista[i].item_id.toString(),
                                appContext,
                                appContext.getResources().getColor(R.color.green)
                            )))
                        )
                    }
                    "ANULADO" -> {
                        Marker(
                            state = MarkerState(position = marker),
                            icon = (BitmapDescriptorFactory.fromBitmap(createMarkerIcon(
                                lista[i].item_id.toString(),
                                appContext,
                                appContext.getResources().getColor(R.color.red)
                            )))
                        )
                    }
                    "VOLVER A PROGRAMAR" -> {
                        Marker(
                            state = MarkerState(position = marker),
                            icon = (BitmapDescriptorFactory.fromBitmap(createMarkerIcon(
                                lista[i].item_id.toString(),
                                appContext,
                                appContext.getResources().getColor(R.color.red)
                            )))
                        )

                    }
                    "PROGRAMADO" -> {
                        Marker(
                            state = MarkerState(position = marker),
                            icon = (BitmapDescriptorFactory.fromBitmap(createMarkerIcon(
                                lista[i].item_id.toString(),
                                appContext,
                                appContext.getResources().getColor(R.color.blue)
                            ))),
                        )
                    }
                }
                println("[$i, ${lista[i].latitud}]")
                println("[$i, ${lista[i].longitud}]")
            }

    }

}

@Preview(showBackground = true)
@Composable
fun RoundedButton(
    //modifier: Modifier, onClick: () -> Unit
) {
    Box(modifier = Modifier.padding(horizontal = 10.dp)) {
        Button(
            onClick = { /* ... */ },
            shape = RoundedCornerShape(20.dp,20.dp,30.dp,30.dp),
            //shape = com.google.android.gms.maps.model.Marker,
            modifier = Modifier.size(40.dp,50.dp)
        ) {
            Text(text = "10",color = Color.White, fontSize = 5.sp)
        }

    }
}

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    //drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    drawable.setBounds(0, 0, 80,80)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

// Helper function to create a bitmap with a number as the icon
fun createMarkerIcon(
    number: String,
    context: Context,
    int: Int
)
: Bitmap {
    val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply {
        //color = context.getResources().getColor(R.color.blue)
        color = int
        style = Paint.Style.FILL
        textSize = 40f
        textAlign = Paint.Align.CENTER

    }
    canvas.drawCircle(40f, 40f, 40f, paint)
    DrawPinMarker(canvas,context,int)
    //canvas.drawCircle(width / 2f, height / 2f, 20f, paint)
    paint.setColor(context.getResources().getColor(R.color.white))
    canvas.drawText(number, 40f, 54f, paint)

    ////////////////////////////////////////////////


    return bitmap
}

fun DrawPinMarker(
    canvas: Canvas,
    context: Context,
    int: Int
):Canvas
{
    val canvasWidth = canvas.width
    val canvasHeight = canvas.height

    val markerX = canvasWidth / 2
    val markerY = canvasHeight / 2
    // Dibujar el círculo del marcador
    //canvas.drawCircle(markerX.toFloat(), markerY.toFloat(), 40f, Paint())
    /*val paint = Paint().apply {
        color = context.getResources().getColor(R.color.blue)
        //color = int
        style = Paint.Style.FILL
        textSize = 30f
        textAlign = Paint.Align.CENTER

    }*/
    //canvas.drawCircle(50f, 50f, 50f, paint)
    // Dibujar el triángulo en la parte inferior del círculo
    val path = Path()
    //path.moveTo(markerX.toFloat(), (markerY + 10).toFloat())
    //path.moveTo(markerX.toFloat(), (markerY + 30).toFloat())
    path.moveTo(40f, 100f)
    //path.lineTo((markerX - 10).toFloat(), (markerY + 30).toFloat())
    path.lineTo((markerX - 50).toFloat(), (markerY +0).toFloat())
    path.lineTo((markerX + 30).toFloat(), (markerY +0).toFloat())
    path.close()

    canvas.drawPath(path, Paint())
    val trianglePaint = Paint()
    //trianglePaint.color = context.getResources().getColor(R.color.blue)
    trianglePaint.color = int
    canvas.drawPath(path, trianglePaint)

    //val circlePaint = Paint()
    //circlePaint.color = context.getResources().getColor(R.color.blue)
    //canvas.drawCircle(markerX.toFloat(), markerY.toFloat(), 30f, circlePaint)

    return canvas

}



