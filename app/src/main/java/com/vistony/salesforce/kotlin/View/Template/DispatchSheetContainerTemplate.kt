package com.vistony.salesforce.kotlin.View.Template

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetRepository
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetUI
import com.vistony.salesforce.kotlin.Model.DetailDispatchSheetViewModel
import com.vistony.salesforce.kotlin.Utilities.getDate
import com.vistony.salesforce.kotlin.View.Atoms.CalendarApp
import com.vistony.salesforce.kotlin.View.Atoms.Cell
import com.vistony.salesforce.kotlin.View.Atoms.SwitchExample
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Atoms.TextLabel
import com.vistony.salesforce.kotlin.View.Atoms.theme.BlueVistony
import com.vistony.salesforce.kotlin.View.Pages.DispatchSheetFailedScreen
import com.vistony.salesforce.kotlin.View.Pages.DispatchSheetPendingScreen
import com.vistony.salesforce.kotlin.View.Pages.DispatchSheetSucessfulScreen
import com.vistony.salesforce.kotlin.View.components.ButtonCircle
import com.vistony.salesforce.kotlin.View.components.DialogView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import androidx.fragment.app.FragmentManager
import com.vistony.salesforce.View.ContainerDispatchView
import com.vistony.salesforce.kotlin.Model.ClientRepository
import com.vistony.salesforce.kotlin.Model.ClientViewModel
import com.vistony.salesforce.kotlin.Model.HeaderDispatchSheetRepository
import com.vistony.salesforce.kotlin.Model.HeaderDispatchSheetViewModel
import com.vistony.salesforce.kotlin.Model.ReasonDispatchRepository
import com.vistony.salesforce.kotlin.Model.ReasonDispatchViewModel
import com.vistony.salesforce.kotlin.Model.TypeDispatchRepository
import com.vistony.salesforce.kotlin.Model.TypeDispatchViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DispatchSheetContainerTemplate(fragment:FragmentTransaction)
{
    var DateApp: MutableState<String> = remember { mutableStateOf(getDate()!!) }
    val appContext = LocalContext.current
    var quantity: MutableState<String> = remember {  mutableStateOf("0") }
    var amount: MutableState<String> = remember {  mutableStateOf("0") }
    var showTabsExample by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalContext.current as LifecycleOwner
    //Carga de Despachos
    val headerDispatchSheetRepository = HeaderDispatchSheetRepository()
    val headerDispatchSheetViewModel: HeaderDispatchSheetViewModel = viewModel(
        factory = HeaderDispatchSheetViewModel.HeaderDispatchSheetViewModelFactory(
            headerDispatchSheetRepository,
            appContext,
            SesionEntity.imei,
        )
    )
    val typeDispatchRepository: TypeDispatchRepository = TypeDispatchRepository()
    val typeDispatchViewModel: TypeDispatchViewModel = viewModel(
        factory = TypeDispatchViewModel.TypeDispatchViewModelFactory(
            SesionEntity.imei,
            appContext,
            typeDispatchRepository
        )
    )
    typeDispatchViewModel?.addTypeDispatch()
    val reasonDispatchRepository: ReasonDispatchRepository = ReasonDispatchRepository()
    val reasonDispatchViewModel: ReasonDispatchViewModel = viewModel(
        factory = ReasonDispatchViewModel.ReasonDispatchViewModelFactory(
            SesionEntity.imei,reasonDispatchRepository, appContext
        )
    )
    val clientRepository: ClientRepository = ClientRepository()
    val clientViewModel: ClientViewModel = viewModel(
        factory = ClientViewModel.ClientViewModelFactory(
            clientRepository, SesionEntity.imei, appContext
        )
    )
    reasonDispatchViewModel?.addReasonDispatch()
    var headerDispatchSheetAPI=headerDispatchSheetViewModel.resultAPI.collectAsState()
    var headerDispatchSheetDB=headerDispatchSheetViewModel.resultDB.collectAsState()
    Scaffold(
        topBar = {
        },
        bottomBar = {
            //BottomBar(appContext,quantity,amount)
        }
    )
    {
        Column {
            Row() {
                //Consulta de fecha de cobranza
                DateQueryDispatchContainer(DateApp)
                {
                    //showTabsExample = !showTabsExample
                    headerDispatchSheetViewModel?.getMasterDispatchSheetDB(DateApp.value,)
                }
            }
            Row (modifier= Modifier
                .padding(0.dp, 0.dp)
                .background(BlueVistony)
                .height(30.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
            ){
                TableCell(text = "DESPACHOS", color = Color.White, title = true, weight = 1f)
            }
            Log.e("REOS", "DispatchSheetContainerTemplate-DispatchSheetContainerTemplate-headerDispatchSheetDB.value.status"+headerDispatchSheetDB.value.status)
            when(headerDispatchSheetDB.value.status)
            {
                "Y"->{
                    TabsExampleContainer(DateApp,appContext,lifecycleOwner,fragment)
                    headerDispatchSheetViewModel.resetgetMasterDispatchSheetDB()
                }
                /*"N"->{
                    headerDispatchSheetViewModel?.getMasterDispatchSheetAPI(DateApp.value,)
                    clientViewModel?.getMasterClientAPI(DateApp.value)
                }*/
            }
            /*if (showTabsExample) {
                Log.e("REOS", "DispatchSheetContainerTemplate-DispatchSheetContainerTemplate-showTabsExample"+showTabsExample)
                Log.e("REOS", "DispatchSheetContainerTemplate-DispatchSheetContainerTemplate-headerDispatchSheetDB.value.status"+headerDispatchSheetDB.value.status)
            }*/
        }
    }
}

@Composable
fun DateQueryDispatchContainer(
    DateApp: MutableState<String>,
    onClick: ()->Unit
)
{
    Column(
        //modifier= Modifier.padding(10.dp)
    ) {
        TextLabel(text = "Elegir una fecha para su consulta")
        Row() {
            Column(modifier = Modifier.weight(1f)) {
                //CalendarAppView(tittle = "Elegir una fecha para su consulta", DateApp = DateApp)
                CalendarApp(
                    DateApp = DateApp
                )
            }
            //Spacer(modifier = Modifier.width(5.dp))
            Column(modifier = Modifier.width(40.dp)) {
                ButtonCircle(
                    OnClick = {
                        onClick()
                    }, roundedCornerShape = RoundedCornerShape(0.dp)
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

@Composable
fun TabsExampleContainer(
    DateApp: MutableState<String>,
    appContext: Context,
    lifecycleOwner: LifecycleOwner,
    fragmentTransaction:FragmentTransaction
) {

    var selectedTabIndex by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
        //.padding(16.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            contentColor = Color.White
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text("Pendientes")
            }
            /*Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text("Exitosos")
            }
            Tab(
                selected = selectedTabIndex == 2,
                onClick = { selectedTabIndex = 2 },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text("Fallidos")
            }*/
        }

        //Spacer(modifier = Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> //TabContentContainer(DateApp,appContext,"P",lifecycleOwner)
                //DispatchSheetPendingScreen()
                //DispatchSheetSucessfulScreen().
                DispatchSheetSucessfulScreen.newInstance(DateApp.value)
            //1 -> //TabContentContainer(DateApp,appContext,"E",lifecycleOwner)
            //    DispatchSheetSucessfulScreen()
           // 2 -> //TabContentContainer(DateApp,appContext,"F",lifecycleOwner)
           //     DispatchSheetFailedScreen()
                //DispatchSheetSucessfulScreen()
        }
        //val parametroFecha = "parametrofecha"
        Log.e("REOS", "DispatchSheetContainerTemplate-TabsExampleContainer-DateApp.value: " +DateApp.value)
        /*val fragment = DispatchSheetSucessfulScreen.newInstance(DateApp.value)
        fragmentTransaction.add(R.id.content_menu_view, fragment)
        fragmentTransaction.commit()*/
        /*val fragment1 = DispatchSheetSucessfulScreen.newInstance(DateApp.value)
        fragmentTransaction.add(R.id.content_menu_view, fragment1)
        fragmentTransaction.commit()
        val fragment2 = DispatchSheetPendingScreen.newInstance(DateApp.value)
        fragmentTransaction.add(R.id.content_menu_view, fragment2)
        fragmentTransaction.commit()*/
    }
}