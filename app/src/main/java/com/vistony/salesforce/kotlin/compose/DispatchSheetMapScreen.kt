package com.vistony.salesforce.kotlin.compose

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
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
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.View.ContainerDispatchView
import com.vistony.salesforce.kotlin.compose.theme.VistonySalesForce_PedidosTheme
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity
import com.vistony.salesforce.kotlin.data.*
import com.vistony.salesforce.kotlin.utilities.Geolocation
import java.text.SimpleDateFormat
import java.util.*

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
                VistonySalesForce_PedidosTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        StartGoogleMaps()
                        val appContext = LocalContext.current
                        val viewModelStoreOwner = LocalContext.current as ViewModelStoreOwner
                        val lifecycleOwner = LocalContext.current as LifecycleOwner
                        //val dispatchRepository = HeaderDispatchSheetRepository()

                        //viewModel = ViewModelProvider(this, DispatchViewModelFactory(dispatchRepository))
                        //   .get(HeaderDispatchSheetViewModel::class.java)
                        //viewModel = ViewModelProvider(viewModelStoreOwner)[HeaderDispatchSheetViewModel::class.java]

                        Log.e(
                            "REOS",
                            "DispatchSheetMapScreen-onCreateView.inicia"
                        )
                        Log.e(
                            "REOS",
                            "DispatchSheetMapScreen-onCreateView.SesionEntity.imei" + SesionEntity.imei
                        )
                        Log.e(
                            "REOS",
                            "DispatchSheetMapScreen-onCreateView.ContainerDispatchView.parametrofecha" + ContainerDispatchView.parametrofecha
                        )
                        try
                        {
                            /*val headerDispatchSheetRepository = HeaderDispatchSheetRepositoryImpl()
                            var viewModel: HeaderDispatchSheetViewModel? = null
                            var headerDispatchSheetViewModelFactory: HeaderDispatchSheetViewModelFactory? = null

                            viewModel = ViewModelProvider(
                                viewModelStoreOwner,
                                HeaderDispatchSheetViewModelFactory
                                //HeaderDispatchSheetRepositorykt
                                    (
                                    headerDispatchSheetRepository,
                                    SesionEntity.imei,
                                    ContainerDispatchView.parametrofecha,
                                    appContext
                                )
                            ).get(HeaderDispatchSheetViewModel::class.java)

                            viewModel.status.observe(viewLifecycleOwner, Observer { usuarios ->
                                //Aquí se actualiza la vista con la lista de usuarios
                                //Por ejemplo, se podría utilizar un RecyclerView para mostrar los usuarios
                                Log.e(
                                    "REOS",
                                    "DispatchSheetMapScreen-viewModel.result.observe.dentro MMVM repository"
                                )
                            })*/
                            val headerDispatchSheetRepository = HeaderDispatchSheetRepository()
                            val clientRepository = ClientRepository()
                            val typeDispatchRepository = TypeDispatchRepository()
                            val reasonDispatchRepository = ReasonDispatchRepository()
                            //viewModel = ViewModelProvider(viewModelStoreOwner, HeaderDispatchSheetViewModel(headerDispatchSheetRepository))[HeaderDispatchSheetViewModel::class.java]
                            //viewModel = ViewModelProvider(viewModelStoreOwner)[HeaderDispatchSheetViewModel::class.java]
                            headerDispatchSheetViewModel = HeaderDispatchSheetViewModel(headerDispatchSheetRepository)
                            typeDispatchViewModel = TypeDispatchViewModel(SesionEntity.imei,appContext,typeDispatchRepository)
                            reasonDispatchViewModel = ReasonDispatchViewModel(reasonDispatchRepository,appContext)

                            typeDispatchViewModel.status.observe(lifecycleOwner) { status ->
                                // actualizar la UI con los datos obtenidos
                                Log.e(
                                    "REOS",
                                    "DispatchSheetMapScreen-typeDispatchViewModel.result.observe.status"+status
                                )
                            }

                            typeDispatchViewModel?.addTypeDispatch(
                                SesionEntity.imei,
                                appContext,
                                lifecycleOwner
                            )

                            reasonDispatchViewModel.status.observe(lifecycleOwner) { status ->
                                // actualizar la UI con los datos obtenidos
                                Log.e(
                                    "REOS",
                                    "DispatchSheetMapScreen-reasonDispatchViewModel.result.observe.status"+status
                                )
                            }

                            reasonDispatchViewModel?.addReasonDispatch(
                                SesionEntity.imei,
                                appContext,
                                lifecycleOwner
                            )

                            headerDispatchSheetViewModel.status.observe(lifecycleOwner) { status ->
                                // actualizar la UI con los datos obtenidos
                                Log.e(
                                "REOS",
                                "DispatchSheetMapScreen-headerDispatchSheetViewModel.result.observe.status"+status
                                    )


                            }



                            headerDispatchSheetViewModel?.getStateDispatchSheet(
                                SesionEntity.imei,
                                ContainerDispatchView.parametrofecha,
                                appContext,
                                lifecycleOwner
                                )
                            clientViewModel = ClientViewModel(clientRepository)
                            clientViewModel.status.observe(lifecycleOwner) { status ->
                                // actualizar la UI con los datos obtenidos
                                Log.e(
                                    "REOS",
                                    "DispatchSheetMapScreen-clientViewModel.result.observe.status"+status
                                )
                            }

                            clientViewModel?.getClient(
                                SesionEntity.imei,
                                ContainerDispatchView.parametrofecha,
                                appContext
                            )


                        }catch (e:Exception){
                            Log.e(
                                "REOS",
                                "DispatchSheetMapScreen-onCreateView-error"+e.toString()
                            )
                        }
                        Log.e(
                            "REOS",
                            "DispatchSheetMapScreen-onCreateView.fin"
                        )
                        /*viewModel.status
                            .observe(viewLifecycleOwner) { repos ->
                            // update UI with repos data
                                Log.e(
                                    "REOS",
                                    "DispatchSheetMapScreen-viewModel.result.observe.status"+repos.toString()
                                )
                                /*Log.e(
                                    "REOS",
                                    "DispatchSheetMapScreen-viewModel.result.observe.status.size"+repos.getValue()
                                )*/

                                /*if(repos!=null)
                                {
                                    if (!repos?.isEmpty()!!) {
                                        val database by lazy { AppDatabase.getInstance(context.applicationContext) }


                                        //Thread {
                                            //Do your database´s operations here
                                            database?.headerDispatchSheetDao
                                                ?.insertHeaderDispatchSheet(
                                                    //headerDispatchSheetResponse?.getDispatchSheetEntity()!!
                                                    repos
                                                )

                                            /*for (i in 0..repos?.size!! - 1)
                                            {
                                                database?.detailDispatchSheetDao?.insertDetailDispatchSheet(
                                                    repos.get(i).details
                                                )
                                            }*/

                                        //}.start()
                                        /*lifecycleScope.launch {
                                            database?.headerDispatchSheetDao
                                                ?.insertHeaderDispatchSheet(
                                                    //headerDispatchSheetResponse?.getDispatchSheetEntity()!!
                                                    repos
                                                )
                                        }*/
                                    }
                                }*/
                                /*lifecycleScope.launch {
                                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                                    database?.ubigeoousDao?.addUbigeous(
                                        Ubigeous(
                                            "C001",
                                            "7",
                                            "7",
                                            "10001057",
                                            "Lima",
                                            "Lima",
                                            "Ancon",
                                            "2"
                                        )
                                    )
                                }*/
                                //val room = Room.databaseBuilder(context.applicationContext, RoomDatabase::class.java, "dbcobranzas1").build();

                        }

                       viewModel.getDispatch(
                            SesionEntity.imei,
                            ContainerDispatchView.parametrofecha,
                            appContext
                       )*/
                        /*Log.e(
                            "REOS",
                            "DispatchSheetMapScreen-viewModel.result.observe.status"+status.getValue()
                        )*/
                        /*viewModel.dispatch.observe(lifecycleOwner, { dispatchList ->
                            // Actualizar la vista con la lista de despacho
                        })*/

                        //val database by lazy { context?.let { AppDatabase.getInstance(it.applicationContext) } }
                        /*lifecycleScope.launch{

                            database?.ubigeoousDao?.addUbigeous(
                                Ubigeous(
                                    "C001",
                                    "7",
                                    "7",
                                    "10001057",
                                    "Lima",
                                    "Lima",
                                    "Ancon",
                                    "2"
                                )
                            )
                        }*/

                        /*headerDispatchSheetViewModel = ViewModelProvider(viewModelStoreOwner).get(HeaderDispatchSheetViewModel::class.java)
                        Log.e(
                            "REOS",
                            "DispatchSheetMapScreen-headerDispatchSheetViewModel-inicia"
                        )
                        try {
                            lifecycleScope.launch {

                                headerDispatchSheetViewModel.getAndInsertHeaderDispatchSheetRepository(
                                    SesionEntity.imei,
                                    ContainerDispatchView.parametrofecha,
                                    appContext
                                )?.observe(lifecycleOwner, { posts ->
                                    // Actualiza la interfaz de usuario con los datos obtenidos de la API
                                    Log.e(
                                        "REOS",
                                        "DispatchSheetMapScreen-headerDispatchSheetViewModel-resultado"
                                    )
                                })
                            }
                        }catch (e: Exception){
                            Log.e(
                                "REOS",
                                "DispatchSheetMapScreen-headerDispatchSheetViewModel-error"+e.toString()
                            )
                        }*/

                    }
                }
            }
        }
    }

    /*
    /** Called when the user clicks a marker.  */
    override fun onMarkerClick(marker: Marker): Boolean {
        val appContext = LocalContext.current
        // Retrieve the data from the marker.
        val clickCount = marker.tag as? Int

        // Check if a click count was set, then display the click count.
        clickCount?.let {
            val newClickCount = it + 1
            marker.tag = newClickCount
            Toast.makeText(
                appContext,
                "${marker.title} has been clicked $newClickCount times.",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }
*/
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
fun StartGoogleMaps()
{
    val appContext = LocalContext.current
    /*viewModelStore = ViewModelStore()
    val viewModelStoreOwner = LocalContext.current as ViewModelStoreOwner
    val lifecycleOwner = LocalContext.current as LifecycleOwner
    //val marker = com.google.android.gms.maps.model.LatLng(28.270833, -16.63916)
    //pageAdapter.addFRagment(new ContainerDispatchSheetView(),"Despacho");
    //pageAdapter.addFRagment(new RutaVendedorNoRutaView(),"Mapa");
    //viewPager.setAdapter(pageAdapter);

    headerDispatchSheetViewModel = ViewModelProvider(viewModelStoreOwner).get(HeaderDispatchSheetViewModel::class.java)
    Log.e(
        "REOS",
        "DispatchSheetMapScreen-headerDispatchSheetViewModel-inicia"
    )

    headerDispatchSheetViewModel.getAndInsertHeaderDispatchSheetRepository(
        SesionEntity.imei,
        ContainerDispatchView.parametrofecha,
        appContext
    )?.observe(lifecycleOwner, { posts ->
        // Actualiza la interfaz de usuario con los datos obtenidos de la API
        Log.e(
            "REOS",
            "DispatchSheetMapScreen-headerDispatchSheetViewModel-resultado"
        )
    })*/



    Log.e(
        "REOS",
        "DispatchSheetMapScreen-headerDispatchSheetViewModel-fin"
    )
    GoogleMap(modifier = Modifier.fillMaxSize(),
        properties = MapProperties
            (mapType = MapType.NORMAL, isMyLocationEnabled = true )

        ){

        val icon = bitmapDescriptorFromVector(
            LocalContext.current, R.drawable.ic_baseline_place_24
        )



        var lista=DetailDispatchSheetSQLite(appContext)
                .getDetailDispatchSheetforCodeControlMap(ContainerDispatchView.parametrofecha)

            /*Log.e(
                "REOS",
                "GooleMaps-StartGoogleMaps-lista.size"+lista.size
            )*/
            for ( i in 0..lista.size-1)
            {
                val marker = com.google.android.gms.maps.model.LatLng(
                    Convert.stringToDouble(lista[i].getLatitud())
                    ,
                    Convert.stringToDouble(lista[i].getLongitud())
                )

                MarkerInfoWindow(
                    state = MarkerState(position = marker),
                    //visible = false
                    icon = (BitmapDescriptorFactory.fromBitmap(
                        createMarkerIcon(
                            lista[i].getItem_id(),
                            appContext,
                            appContext.getResources().getColor(R.color.gray)
                        )
                    )),
                ) { marker ->
                    /*val appContext = LocalContext.current
                    val lifecycleOwner = LocalContext.current as LifecycleOwner

                    val openDialog = remember { mutableStateOf(false) }
                    if (openDialog.value)
                    {
                        InfoDialog(
                            title = "Importante",
                            desc = "Desea iniciar la visita al cliente?",
                            onDismiss = {
                                openDialog.value = false
                                //false
                            },
                            appContext,
                            lifecycleOwner
                        )
                    }*/
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
                                        text = lista[i].getNombrecliente(),
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
                                        text = lista[i].getDomembarque_id()+" "+lista[i].getDireccion(),
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
                                                    text = lista[i].getControl_id(),
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
                                                    text = lista[i].getItem_id(),
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
                                                    text = lista[i].getEstado(),
                                                    //color = MaterialTheme.colors.secondaryVariant,
                                                    color = Color.Black,
                                                    style = MaterialTheme.typography.subtitle2,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        if(lista[i].getEstado().equals("ANULADO")||lista[i].getEstado().equals("VOLVER A PROGRAMAR"))
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
                                                        text = lista[i].getOcurrencies(),
                                                        //color = MaterialTheme.colors.secondaryVariant,
                                                        color = Color.Black,
                                                        style = MaterialTheme.typography.subtitle2,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }
                                        }
                                    }

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
                        /*HeaderImage(
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.TopCenter),
                                    "Question"

                            )*/

                    }
                    /*Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colors.background,
                                shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp)
                            ),
                    ) {


                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Image(
                                painter = painterResource(id = R.mipmap.logo),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .height(80.dp)
                                    .fillMaxWidth(),

                                )
                            //.........................Spacer
                            //Spacer(modifier = Modifier.height(24.dp))
                            //.........................Text: title
                            Text(
                                text = "Cliente",
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
                            Text(
                                text = lista[i].getNombrecliente(),
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
                            Spacer(modifier = Modifier.height(8.dp))
                            //.........................Text : description
                            Text(
                                text = "Estado",
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
                            Text(
                                text = lista[i].getEstado(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    //.padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                    .fillMaxWidth(),
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black
                                    )
                                //style = MaterialTheme.typography.bodyLarge,
                                //color = MaterialTheme.colorScheme.primary,
                            )
                            //.........................Spacer
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Motivo",
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
                            Text(
                                text = lista[i].getOcurrencies(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    //.padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                    .fillMaxWidth(),
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black
                                )
                                //style = MaterialTheme.typography.bodyLarge,
                                //color = MaterialTheme.colorScheme.primary,
                            )
                        }

                    }*/

                }

                //IconButtonPrueba()
                //BottomNavigation()
                /*MapMarker(

                    position = marker,
                    title = "Your Title 2",
                    context = LocalContext.current,
                    iconResourceId = R.drawable.ic_baseline_place_24
                )*/
                val icono=null
                /*Log.e(
                    "REOS",
                    "GooleMaps-StartGoogleMaps-lista[i].getEstado(): "+lista[i].getEstado()
                )


                Log.e(
                    "REOS",
                    "GooleMaps-StartGoogleMaps-icono: "+icono
                )*/
                when (lista[i].getEstado()){
                    "ENTREGADO" -> {
                        /*Marker(position = marker,
                        title = lista[i].getNombrecliente(),
                        snippet = "Estado: "+lista[i].getEstado(),
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    )*/
                        Marker(
                            state = MarkerState(position = marker),
                            //oposition = marker,
                            //title = lista[i].getNombrecliente(),
                            //snippet = "Estado: "+lista[i].getEstado(),
                            //icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                            icon = (BitmapDescriptorFactory.fromBitmap(createMarkerIcon(
                                lista[i].getItem_id(),
                                appContext,
                                appContext.getResources().getColor(R.color.green)
                            )))
                        )
                    }
                    "ANULADO" -> {
                        /*Marker(position = marker,
                        title = lista[i].getNombrecliente(),
                        snippet = "Estado: "+lista[i].getEstado(),
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    )*/
                        Marker(
                            //position = marker,
                            state = MarkerState(position = marker),
                            //title = lista[i].getNombrecliente(),
                            //snippet = "Estado: "+lista[i].getEstado(),
                            //icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                            icon = (BitmapDescriptorFactory.fromBitmap(createMarkerIcon(
                                lista[i].getItem_id(),
                                appContext,
                                appContext.getResources().getColor(R.color.red)
                            )))
                        )
                    }
                    "VOLVER A PROGRAMAR" -> {
                        /*Marker(position = marker,
                        title = lista[i].getNombrecliente(),
                        snippet = "Estado: "+lista[i].getEstado(),
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    )*/
                        Marker(
                            //position = marker,
                            state = MarkerState(position = marker),
                            //title = lista[i].getNombrecliente(),
                            //snippet = "Estado: "+lista[i].getEstado(),
                            //icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                            icon = (BitmapDescriptorFactory.fromBitmap(createMarkerIcon(
                                lista[i].getItem_id(),
                                appContext,
                                appContext.getResources().getColor(R.color.red)
                            )))
                        )

                    }
                    "PROGRAMADO" -> {
                        /*Marker(
                            position = marker,
                            title = lista[i].getNombrecliente(),
                            snippet = "Estado: " + lista[i].getEstado(),
                            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                        )*/
                        Marker(
                            //position = marker,
                            state = MarkerState(position = marker),
                            //title = lista[i].getNombrecliente(),
                            //snippet = "Estado: "+lista[i].getEstado(),
                            //icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                            icon = (BitmapDescriptorFactory.fromBitmap(createMarkerIcon(
                                lista[i].getItem_id(),
                                appContext,
                                appContext.getResources().getColor(R.color.blue)
                            ))),

                        )
                            //icon = BitmapDes
                            //icon =

                    }

                }

                //val markerIcon = createMarkerIcon(lista[i].getItem_id(), appContext)
                /*Marker(position = marker,
                    title = lista[i].getNombrecliente(),
                    snippet = "Estado: "+lista[i].getEstado(),
                    //icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    icon = (BitmapDescriptorFactory.fromBitmap(createMarkerIcon(
                        lista[i].getItem_id(),
                        appContext,
                        appContext.getResources().getColor(R.color.blue)
                        )))
                )*/

                /*Log.e(
                    "REOS",
                    "GooleMaps-StartGoogleMaps-i: "+i
                )*/
                /*Log.e(
                    "REOS",
                    "GooleMaps-StartGoogleMaps-PRUEBA: "+"[$i, ${lista[i].latitud}]"
                )*/
                /*Log.e(
                    "REOS",
                    "GooleMaps-StartGoogleMaps-PRUEBA1: "+"[$i, ${lista[i].getLatitud()}]"
                )*/
                println("[$i, ${lista[i].latitud}]")
                println("[$i, ${lista[i].getLatitud()}]")
                /*Log.e(
                    "REOS",
                    "GooleMaps-StartGoogleMaps-lista.getLatitud(): "+lista[$i].getLatitud()
                )
                Log.e(
                    "REOS",
                    "GooleMaps-StartGoogleMaps-lista.getLongitud(): "+lista[i].getLongitud()
                )*/
            }

    }

}


/*
fun onMapReady(googleMap: GoogleMap) {
    mMap = googleMap
    mMap.setOnMarkerClickListener { marker ->
        if (marker.isInfoWindowShown) {
            marker.hideInfoWindow()
        } else {
            marker.showInfoWindow()
        }
        true
    }
}*/


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//@Preview(showBackground = true)
@Composable
fun IconButtonPrueba() {
        /*IconButton(
            modifier =
            Modifier
                .size(40.dp, 60.dp)
                .border(border = BorderStroke(1.dp, Color.Black))
                .clip(CircleShape)
            ,
            onClick = {
            }) {
            Text(text = "10", fontSize = 18.sp, color = Color.Black)
        }*/
    Icon(
        painter = painterResource(id = R.drawable.ic_baseline_place_24),
        contentDescription = "10",
        modifier = Modifier
            .size(50.dp)
            .testTag("10") ,
        tint = Color(0xCC2196F3),


    )
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
            // Inner content including an icon and a text label
            /*Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Favorite",
                modifier = Modifier.size(20.dp)
            )*/
            Text(text = "10",color = Color.White, fontSize = 5.sp)
        }

    }
}

@Composable
fun MapMarker(
    context: Context,
    position: LatLng,
    title: String,
    @DrawableRes iconResourceId: Int
) {
    val icon = bitmapDescriptorFromVector(
        context, iconResourceId
    )

    Marker(
        //anchor = Offset(2.0f, 4.0f),
        alpha = 5.0f,
        state = MarkerState(position = position),
        //position=position,
        title = title,
        icon = icon,

    )
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


@Composable
fun BottomNavigation() {
    BottomNavigation() {
        BottomNavigationItem(
            icon = {
                BadgedBox(badge = { Badge { Text("8") } }) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Favorite"
                    )
                }
            },
            selected = false,
            onClick = {}
        )
    }
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


@Composable
fun InfoDialog(
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
            /*HeaderImage(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.TopCenter),
                        "Question"

                )*/

        }
    }
}




