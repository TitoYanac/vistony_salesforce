package com.vistony.salesforce.kotlin.compose

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.vistony.salesforce.kotlin.compose.theme.VistonySalesForce_PedidosTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.vistony.salesforce.R
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import com.bxl.mupdf.Stepper
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.View.ContainerDispatchView
import com.vistony.salesforce.kotlin.compose.components.ScreenDispatch
import com.vistony.salesforce.kotlin.data.DetailDispatchSheet
import com.vistony.salesforce.kotlin.data.DetailDispatchSheetRepository
import com.vistony.salesforce.kotlin.data.DetailDispatchSheetViewModel

class DispatchSheetPendingScreen : Fragment()
{
    private lateinit var detailDispatchSheetViewModel: DetailDispatchSheetViewModel

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
                        //color = MaterialTheme.colors.background
                    ) {
                        val appContext = LocalContext.current
                        val lifecycleOwner = LocalContext.current as LifecycleOwner
                        val detailDispatchSheetRepository = DetailDispatchSheetRepository()
                        var detailDispatchSheetList by remember { mutableStateOf(emptyList<DetailDispatchSheet>()) }
                        detailDispatchSheetViewModel = DetailDispatchSheetViewModel(detailDispatchSheetRepository)
                        detailDispatchSheetViewModel?.getStateDetailDispatchSheet(
                            SesionEntity.imei,
                            ContainerDispatchView.parametrofecha,
                            appContext,
                            lifecycleOwner,
                            "P"

                        )
                        detailDispatchSheetViewModel.status.observe(lifecycleOwner) { data ->
                            // actualizar la UI con los datos obtenidos
                            Log.e(
                                "REOS",
                                "DispatchSheetPendingScreen-onCreateView.result.observe.data.size"+data.size
                            )
                            //Conversation1(data)
                            detailDispatchSheetList= data

                        }
                        ScreenDispatch(detailDispatchSheetList,appContext,lifecycleOwner)
                    }
                }
            }
        }
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(tag: String?, dato: Any?)
    }

    var mListener: OnFragmentInteractionListener? = null
}

/*
data class Message(val author: String, val body: String)


@Composable
fun Conversation(messages: List<com.vistony.salesforce.kotlin.compose.Message>)
{
    LazyColumn{
        items(messages){ message ->
            MessageCard(message)

        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageCard(msg: com.vistony.salesforce.kotlin.compose.Message){
    Card(
        elevation = 10.dp,
        onClick = { },
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth(),
        //.border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
    )
    {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            /*Image(
                painter = painterResource(id = com.vistony.salesforce.R.drawable.ic_baseline_account_circle_24),
                contentDescription = "Contact Profile Picture",
                modifier = Modifier
                    //Set Image size to 40 dp
                    .size(40.dp)
                    //Clip Image to be shaped as a circle
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )*/
            Button(
                onClick = { /* ... */ },
                //shape = RoundedCornerShape(CircleShape),
                //shape = com.google.android.gms.maps.model.Marker,
                modifier = Modifier
                    //.size(50.dp,50.dp)
                    .clip(
                        RoundedCornerShape(
                            topEndPercent = 50,
                            bottomStartPercent = 50, topStartPercent = 50, bottomEndPercent = 50
                        )
                    )
                    //.padding(20.dp)

            ) {
                // Inner content including an icon and a text label
                /*Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(20.dp)
                )*/
                Text(text = "10",color = Color.White, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))

            var isExpanded by remember { mutableStateOf(false) }
            val surfaceColor by animateColorAsState(
                if (isExpanded)
                    MaterialTheme.colors.primary
                else MaterialTheme.colors.surface,
            )

            Column(modifier = Modifier.clickable {
                isExpanded = !isExpanded
            }
            ) {
                Text(
                    text = msg.author,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    elevation = 1.dp,
                    color = surfaceColor,
                    modifier = Modifier
                        .animateContentSize()
                        .padding(1.dp)
                ) {
                    Text(
                        text = msg.body,
                        modifier = Modifier.padding(all = 4.dp),
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.body2
                    )
                }

            }
        }
        val steps = listOf(
            Pair("Entrada", Icons.Filled.Home),
            Pair("Cobranza", ImageVector.vectorResource(R.drawable.ic_local_atm_black_24dp)),
            Pair("Despacho", ImageVector.vectorResource(R.drawable.ic_baseline_local_shipping_black_24)),
            Pair("Salida", Icons.Filled.ExitToApp)
        )
        /*val currentStep = remember { mutableStateOf(0) }
        ProgressBarWithIcons(steps,0)
        LinearProgressIndicator(
            progress = 50f / 100f, // Divide by 100 to get a percentage value
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.primary
        )

        val steps1 = listOf("Paso 1", "Paso 2", "Paso 3")*/
        val currentStep1 = 1

        HorizontalStepView(steps = steps, currentStep = currentStep1)
    }



    /*val iconos = arrayOf(
        ImageVector.vectorResource(id = R.drawable.ic_arrow_back_black_24dp),
        ImageVector.vectorResource(id = R.drawable.ic_baseline_picture_as_pdf_24),
        ImageVector.vectorResource(id = R.drawable.ic_print_black_24dp),
    )*/

    //MyApp()

    //funStep()
    //CustomProgressBar(icons = iconos, progress = 0.3f)
    //CustomProgressBar(2,3)


}

@Composable
fun StateProgressBar(totalStates: Int, currentState: Int) {
    val progress = remember { mutableStateOf(currentState.toFloat() / totalStates.toFloat() * 360f) }
    CircularProgressIndicator(
        modifier = Modifier.size(64.dp),
        progress = progress.value,
        strokeWidth = 8.dp,
        color = MaterialTheme.colors.primary
    )
}

@Composable
fun MyApp() {
    val totalStates = 4 // total number of states
    val currentState = remember { mutableStateOf(1) } // current state, initialized to 1
    Column {
        // other composable elements
        StateProgressBar(totalStates, currentState.value)
        // other composable elements
    }
}

@Composable
fun CustomProgressBar(currentStep: Int, totalSteps: Int) {
    val steps = listOf("Step 1", "Step 2", "Step 3")
    val currentStepIndex = currentStep - 1

    Row(verticalAlignment = Alignment.CenterVertically) {
        // Render each step
        steps.forEachIndexed { index, step ->
            // Render the current step with a different color
            val color = if (index == currentStepIndex) Color.Blue else Color.LightGray
            val shape = if (index == currentStepIndex) CircleShape else RectangleShape

            // Render the step label
            Text(
                text = step,
                style = MaterialTheme.typography.caption,
                color = color,
                modifier = Modifier.padding(8.dp)
            )

            // Render the progress bar
            if (index < totalSteps - 1) {
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .size(4.dp, 16.dp)
                        .clip(shape)
                        .background(color)
                )
            }
        }
    }
}


@Composable
fun CustomProgressBar(icons: Array<ImageVector>, progress: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in icons.indices) {
            Icon(
                modifier = Modifier.wrapContentSize(),
                imageVector = icons[i],
                contentDescription = null,
                tint = if (progress >= i.toFloat() / (icons.size - 1)) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
            )
            if (i < icons.size - 1) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        /*LinearProgressIndicator(
            modifier = Modifier.weight(1f),
            progress = progress,
            backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
            color = MaterialTheme.colors.primary,
        )*/
    }
}

@Composable
fun ProgressWithStates() {
    val progress = remember { mutableStateOf(0.5f) }
    val states = listOf(
        Pair("Estado 1", Icons.Filled.CheckCircle),
        Pair("Estado 2", Icons.Filled.Star),
        Pair("Estado 3", Icons.Filled.Search),
        Pair("Estado 4", Icons.Filled.AccountBox)
    )

    LazyColumn {
        items(states.size) { index ->
            val state = states[index]
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(state.second, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = state.first)
            }
            if (index < states.size - 1) {
                LinearProgressIndicator(
                    progress = progress.value,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun ProgressBarWithIcons(
    steps: List<Pair<String, ImageVector>>,
    currentStep: Int,
) {
    LazyRow(
        modifier = Modifier.padding(0.dp,50.dp,0.dp,0.dp)
    ) {
        itemsIndexed(steps) { index, step ->
            Column(
                //verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = step.second,
                    contentDescription = null,
                    tint = if (index <= currentStep) Color.Green else Color.Gray
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = step.first,
                    style = MaterialTheme.typography.subtitle1,
                    color = if (index <= currentStep) Color.Green else Color.Gray
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(
                            if (index <= currentStep) Color.Black else Color.Gray
                        )
                )
            }
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
    /*LinearProgressIndicator(
        progress = (currentStep + 1) / steps.size.toFloat(),
        //modifier = Modifier.fillMaxWidth()
    )*/
}

@Composable
fun HorizontalStepView(
    //steps: List<String>,
    steps: List<Pair<String, ImageVector>>,
    currentStep: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(0.dp,150.dp,0.dp,20.dp)
    ) {
        steps.forEachIndexed { index, step ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = step.second,
                    contentDescription = null,
                    tint = if (index <= currentStep) Color.Blue else Color.Gray
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    //text = step,
                    text = step.first,
                    fontWeight = FontWeight.Bold,
                    color = if (index == currentStep) Color.Blue else Color.Gray
                )
                if (index != steps.lastIndex) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(
                                if (index <= currentStep) Color.Blue else Color.White
                            )
                    )
                }
            }
        }
    }
}
*/
