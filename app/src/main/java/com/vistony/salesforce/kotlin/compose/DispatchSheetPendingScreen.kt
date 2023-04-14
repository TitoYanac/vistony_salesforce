package com.vistony.salesforce.kotlin.compose

import android.os.Bundle
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.vistony.salesforce.kotlin.compose.theme.VistonySalesForce_PedidosTheme

class DispatchSheetPendingScreen : Fragment()
{
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
                        val list = mutableListOf<com.vistony.salesforce.kotlin.compose.Message>()
                        val message=com.vistony.salesforce.kotlin.compose.Message("autor1","body1")
                        list.add(message)
                        Conversation(list)
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
            Image(
                painter = painterResource(id = com.vistony.salesforce.R.drawable.ic_baseline_account_circle_24),
                contentDescription = "Contact Profile Picture",
                modifier = Modifier
                    //Set Image size to 40 dp
                    .size(40.dp)
                    //Clip Image to be shaped as a circle
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
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
    }

    LinearProgressIndicator(
        progress = 50f / 100f, // Divide by 100 to get a percentage value
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.primary
    )

    MyApp()
    CustomProgressBar(2,3)
    //funStep()
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

/*
@Composable
fun MyApp2() {
    val steps = listOf("Step 1", "Step 2", "Step 3", "Step 4")
    val currentStep = remember { mutableStateOf(1) } // current step, initialized to 1
    Column {
        // other composable elements
        StepperProgressIndicator(
            steps = steps,
            currentStep = currentStep.value - 1,
            modifier = Modifier.padding(16.dp)
        )
        // other composable elements
    }
}
*/
/*
@Composable
fun funStep(){
    val numberStep = 4
    var currentStep by rememberSaveable { mutableStateOf(1) }
    val titleList= arrayListOf("Step 1","Step 2","Step 3","Step 4")

    Stepper(
        modifier = Modifier.fillMaxWidth(),
        numberOfSteps = numberStep,
        currentStep = currentStep,
        stepDescriptionList = titleList,
        selectedColor = Color.Blue,
        unSelectedColor= Color.LightGray
    )
}*/