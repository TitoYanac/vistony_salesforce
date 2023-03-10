package com.vistony.salesforce.kotlin.dispatchsheet.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.Fragment.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.compose.components.HeaderImage
import com.vistony.salesforce.kotlin.compose.theme.VistonySalesForce_PedidosTheme
import com.vistony.salesforce.kotlin.compose.components.SomeScreenViewModel

private  var latitude: String = "" //declaration inside class
private  var longitude: String = "" //declaration inside class
private  var latitudeClient: String = "" //declaration inside class
private  var longitudeClient: String = "" //declaration inside class

class DialogMain ():
    DialogFragment()
    //AlertDialog()
{


    fun vary(latitud:String,longitud:String,latitudClient:String,longitudClient: String ){
        //latitude==latitud
        //longitude==longitud
        Log.e(
            "REOS",
            "DialogMain-vary()-latitud: "+latitud
        )
        Log.e(
                "REOS",
        "DialogMain-vary()-longitud"+longitud
        )
        if (latitud != null) {
            latitude=latitud
        }
        longitude=longitud
        latitudeClient=latitudClient
        longitudeClient=longitudClient
        Log.e(
            "REOS",
            "DialogMain-vary()-latitude: "+latitude
        )
        Log.e(
            "REOS",
            "DialogMain-vary()-longitude"+longitude
        )
        Log.e(
            "REOS",
            "DialogMain-vary()-latitudeClient: "+latitudeClient
        )
        Log.e(
            "REOS",
            "DialogMain-vary()-longitudeClient"+longitudeClient
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()
        ).apply {
            setContent {
                //Modifier.border(border = BorderStroke(30.dp,Color.Black)
                 //   ,shape = RoundedCornerShape(30.dp))
                VistonySalesForce_PedidosTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        //modifier = Modifier

                        //    .border(0.dp,Color.Black, shape = RoundedCornerShape(10.dp))
                            //.border(border = BorderStroke(10.dp,Color.Black),shape = RoundedCornerShape(20.dp))
                        //,
                        //shape = RoundedCornerShape(30.dp),
                        //color = MaterialTheme.colors.background
                    ) {
                        //DialogScreenOne()
                        //MessageCard()
                        //MyDialog()
                        //DialogScreenOne2()

                        /*StyledAlertDialog(
                            bodyText = "Quedan solo cinco unidades de este producto.",
                            buttonText = "Aceptar",
                            onConfirm = {
                                "Ejemplo 5 -> 'ACEPTAR'"
                            },
                            onDismiss = {
                                true
                            },
                        )*/
                        /*PruebaDialog(
                            title = "Turn on Location Service",
                            desc = "Explore the world without getting lost and keep the track of your location.",
                            onDismiss = {
                                //infoDialog.value = false
                                false
                            },
                            true
                        )*/

                        DefaultPreview1()
                        Log.e(
                            "REOS",
                            "DialogMain-DialogScreenOne()"
                        )
                    }
                }
                //DialogScreenOne()
            }
        }
    }

    //@Preview(showBackground = true)
    @Composable
    fun DialogScreenOne(dvm: SomeScreenViewModel = viewModel()

    ) {

        // Context to toast a message
        val ctx: Context = LocalContext.current

        // Dialog state Manager
        val dialogState: MutableState<Boolean> = remember {
            mutableStateOf(false)
        }
        Log.e(
            "REOS",
            "SomeScreen-DialogScreenOne()"
        )
        Log.e(
            "REOS",
            "DialogMain-DialogScreenOne()-latitude: "+latitude
        )
        Log.e(
            "REOS",
            "DialogMain-DialogScreenOne()-longitude"+longitude
        )
        Log.e(
            "REOS",
            "DialogMain-DialogScreenOne()-latitudeClient: "+latitudeClient
        )
        Log.e(
            "REOS",
            "DialogMain-DialogScreenOne()-longitudeClient"+longitudeClient
        )

        Card(
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier.size(280.dp, 240.dp),
            border = BorderStroke(1.dp, Color.Black),
            elevation = 1.dp,
        )
        {
            Row(

            ) {
                Image(
                    painter = painterResource(id = R.mipmap.waze_logo),
                    contentDescription = "Waze", // decorative
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(20.dp)
                        //Set Image size to 40 dp
                        .size(100.dp)
                        //Clip Image to be shaped as a circle
                        //.clip(CircleShape)
                        //.border(1.5.dp, MaterialTheme.colors.secondary, CircleShape
                        //)
                        .clickable(
                            enabled = true,
                            onClick = {
                                val openURL = Intent(android.content.Intent.ACTION_VIEW)
                                openURL.data = Uri.parse(
                                    //"https://waze.com/ul?q=-11.863631,-77.054802&ll=-11.857904,-77.028424&navigate=yes"
                                    "https://waze.com/ul?q=" + latitude + "," + longitude + "&ll=" + latitudeClient + "," + longitudeClient + "&navigate=yes"
                                )

                                Log.e(
                                    "REOS",
                                    "SomeScreen-DialogScreenOne()-url.waze:" + "https://waze.com/ul?q=" + latitude + "," + longitude + "&ll=" + latitudeClient + "," + longitudeClient + "&navigate=yes"
                                )
                                startActivity(openURL)
                                //Toast.makeText(context,"Waze",Toast.LENGTH_SHORT).show()
                            })

                )
                Image(

                    painter = painterResource(id = R.mipmap.google_maps_icon),
                    contentDescription = "Google Maps", // decorative
                    contentScale = ContentScale.Crop,

                    modifier = Modifier
                        .padding(30.dp)
                        //Set Image size to 40 dp
                        .size(80.dp)
                        .clickable(
                            enabled = true,
                            onClick = {
                                val gmmIntentUri =
                                    //Uri.parse("google.navigation:q=-11.857904,-77.028424&")
                                    Uri.parse("google.navigation:q=" + latitudeClient + "," + longitudeClient)
                                Log.e(
                                    "REOS",
                                    "SomeScreen-DialogScreenOne()-url.googlemaps:" + "google.navigation:q=" + latitudeClient + "," + longitudeClient
                                )
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.setPackage("com.google.android.apps.maps")
                                startActivity(mapIntent)
                            })
                            //Clip Image to be shaped as a circle
                        //.clip(CircleShape)
                        //.border(1.5.dp, MaterialTheme.colors.secondary, CircleShape
                        //)
                )
            }
            Row(
                modifier = Modifier
                    .padding(0.dp, 130.dp, 0.dp, 0.dp)
                    .fillMaxHeight(1f)
                    .fillMaxWidth(1f),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,

            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
                    modifier =
                    Modifier
                        .padding(30.dp)
                    ,

                    onClick = {
                    dialogState.value = true

                }) {
                    Text(text = "Aceptar", fontSize = 22.sp,color = Color.White)
                }
            }
        }

    }



    @Composable
    fun MessageCard(){
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(painter = painterResource(id = R.drawable.end_green)
                , contentDescription = "Contact Profile Picture"
                , modifier = Modifier
                    //Set Image size to 40 dp
                    .size(40.dp)
                    //Clip Image to be shaped as a circle
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))

            var isExpanded by remember { mutableStateOf(false)}
            val surfaceColor by animateColorAsState(
                if(isExpanded)
                    MaterialTheme.colors.primary
                else MaterialTheme.colors.surface,  )

            Column(modifier = Modifier.clickable {
                isExpanded = !isExpanded
            }
            ) {
                Text(text = "Prueba"
                    , color = MaterialTheme.colors.secondaryVariant
                    , style = MaterialTheme.typography.subtitle2)
                Spacer(modifier = Modifier.height(4.dp))
                androidx.compose.material.Surface(
                    shape = MaterialTheme.shapes.medium,
                    elevation = 1.dp,
                    color = surfaceColor,
                    modifier = Modifier
                        .animateContentSize()
                        .padding(1.dp)
                ) {
                    Text(text = "Prueba",
                        modifier = Modifier.padding(all = 4.dp),
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.body2)
                }

            }
        }

    }






    @Composable
    fun StyledAlertDialog(
        bodyText: String,
        buttonText: String,
        onConfirm: () -> Unit,
        onDismiss: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                //Text(bodyText)
                Row(

                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.waze_logo),
                        contentDescription = "Waze", // decorative
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(20.dp)
                            //Set Image size to 40 dp
                            .size(100.dp)
                            //Clip Image to be shaped as a circle
                            //.clip(CircleShape)
                            //.border(1.5.dp, MaterialTheme.colors.secondary, CircleShape
                            //)
                            .clickable(
                                enabled = true,
                                onClick = {
                                    val openURL = Intent(android.content.Intent.ACTION_VIEW)
                                    openURL.data = Uri.parse(
                                        //"https://waze.com/ul?q=-11.863631,-77.054802&ll=-11.857904,-77.028424&navigate=yes"
                                        "https://waze.com/ul?q=" + latitude + "," + longitude + "&ll=" + latitudeClient + "," + longitudeClient + "&navigate=yes"
                                    )

                                    Log.e(
                                        "REOS",
                                        "SomeScreen-DialogScreenOne()-url.waze:" + "https://waze.com/ul?q=" + latitude + "," + longitude + "&ll=" + latitudeClient + "," + longitudeClient + "&navigate=yes"
                                    )
                                    startActivity(openURL)
                                    Toast
                                        .makeText(context, "Waze", Toast.LENGTH_SHORT)
                                        .show()
                                })

                    )
                    Image(

                        painter = painterResource(id = R.mipmap.google_map_icon_412x412),
                        contentDescription = "Google Maps", // decorative
                        contentScale = ContentScale.Crop,

                        modifier = Modifier
                            .padding(20.dp, 25.dp, 20.dp, 20.dp)
                            //Set Image size to 40 dp
                            .size(85.dp)
                            .clickable(
                                enabled = true,
                                onClick = {
                                    val gmmIntentUri =
                                        //Uri.parse("google.navigation:q=-11.857904,-77.028424&")
                                        Uri.parse("google.navigation:q=" + latitudeClient + "," + longitudeClient)
                                    Log.e(
                                        "REOS",
                                        "SomeScreen-DialogScreenOne()-url.googlemaps:" + "google.navigation:q=" + latitudeClient + "," + longitudeClient
                                    )
                                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                    mapIntent.setPackage("com.google.android.apps.maps")
                                    startActivity(mapIntent)
                                })
                        //Clip Image to be shaped as a circle
                        //.clip(CircleShape)
                        //.border(1.5.dp, MaterialTheme.colors.secondary, CircleShape
                        //)
                    )
                }
            },
            confirmButton= {
                Row(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 0.dp)
                        .fillMaxHeight(1f)
                        .fillMaxWidth(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        modifier = Modifier
                            .size(100.dp, 40.dp)
                            .padding(0.dp, 0.dp, 0.dp, 20.dp)
                        ,
                        border = BorderStroke(1.dp,Color.Red),
                        onClick = {
                            dismiss()
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Black),
                    ) {
                        Text(
                            modifier = Modifier.align(CenterVertically),
                            text = buttonText)
                    }
                }

            },
            shape = RoundedCornerShape(
                topEndPercent = 20,
                bottomStartPercent = 20
                , topStartPercent = 20
                , bottomEndPercent = 20
            ),
            backgroundColor = Color(0xFFFFFFFF),
            contentColor = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview1() {
    VistonySalesForce_PedidosTheme {
        PruebaDialog(
            title = "Turn on Location Service",
            desc = "Explore the world without getting lost and keep the track of your location.",
            onDismiss = {
                //infoDialog.value = false
                false
            },
            true
        )
        //MyUI()
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PruebaDialog(
    title: String?="Message",
    desc: String?="Your Message",
    onDismiss: () -> Unit,
    DialogOpen: Boolean
) {
    val contextForToast = LocalContext.current.applicationContext

    var dialogOpen by remember {
        //mutableStateOf(false)
        mutableStateOf(DialogOpen)
    }
    if (dialogOpen)
    {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {

            Box(
                modifier = Modifier
                    .height(460.dp)


            )
            {
                Column(

                )
                {
                    Spacer(modifier = Modifier.height(150.dp))

                    Box(
                        modifier =
                        Modifier.background(
                            //color = MaterialTheme.colorScheme.onPrimary,
                            color = MaterialTheme.colors.onPrimary,
                            shape = RoundedCornerShape(25.dp, 10.dp, 25.dp, 10.dp)
                        )

                    )
                    {
                        //Column
                        Column(
                            modifier = Modifier.align(Alignment.Center)
                        )
                        {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier
                                    //.fillMaxHeight(1f)
                                    .fillMaxWidth(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Box() {
                                    Text(
                                        text = "MAPA DE NAVEGACION",
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Black
                                        )
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    //.fillMaxHeight(1f)
                                    .fillMaxWidth(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Box() {
                                    Text(
                                        text = "Elija el mapa de navegacion:",
                                        fontSize = 18.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier
                                    //.fillMaxHeight(1f)
                                    .fillMaxWidth(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.SpaceBetween


                                ) {
                                    Image(
                                        painter = painterResource(id = R.mipmap.waze_logo),
                                        contentDescription = "Waze", // decorative
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            //Set Image size to 40 dp
                                            .size(80.dp)
                                            .align(CenterHorizontally)
                                            //Clip Image to be shaped as a circle
                                            //.clip(CircleShape)
                                            //.border(1.5.dp, MaterialTheme.colors.secondary, CircleShape
                                            //)
                                            .clip(
                                                RoundedCornerShape(
                                                    topEndPercent = 50,
                                                    bottomStartPercent = 50,
                                                    topStartPercent = 50,
                                                    bottomEndPercent = 50
                                                )
                                            )
                                            .clickable(
                                                enabled = true,
                                                onClick = {
                                                    val openURL =
                                                        Intent(android.content.Intent.ACTION_VIEW)
                                                    openURL.data = Uri.parse(
                                                        //"https://waze.com/ul?q=-11.863631,-77.054802&ll=-11.857904,-77.028424&navigate=yes"
                                                        "https://waze.com/ul?q=" + latitude + "," + longitude + "&ll=" + latitudeClient + "," + longitudeClient + "&navigate=yes"
                                                    )

                                                    Log.e(
                                                        "REOS",
                                                        "SomeScreen-DialogScreenOne()-url.waze:" + "https://waze.com/ul?q=" + latitude + "," + longitude + "&ll=" + latitudeClient + "," + longitudeClient + "&navigate=yes"
                                                    )
                                                    //var menuview:MenuView =MenuView()
                                                    /*startActivity(contextForToast, openURL,
                                                            Bundle()
                                                        )*/
                                                    //menuview.startActivity(openURL)
                                                    //Fragment.startActivity(openURL)
                                                    openURL.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                    contextForToast.startActivity(openURL)
                                                    Toast
                                                        .makeText(
                                                            contextForToast,
                                                            "Waze",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                })


                                    )
                                    Text(
                                        text = "Waze",
                                        modifier = Modifier.align(CenterHorizontally),
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 18.sp,

                                            )
                                    )


                                }
                                Column(
                                    modifier = Modifier.weight(1f),


                                    ) {
                                    Image(
                                        painter = painterResource(id = R.mipmap.google_map_icon_412x412),
                                        contentDescription = "Google Maps", // decorative
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier

                                            //Set Image size to 40 dp
                                            .size(80.dp)
                                            .align(CenterHorizontally)
                                            .clip(
                                                RoundedCornerShape(
                                                    topEndPercent = 50,
                                                    bottomStartPercent = 50,
                                                    topStartPercent = 50,
                                                    bottomEndPercent = 50
                                                )
                                            )
                                            .clickable(
                                                enabled = true,
                                                onClick = {
                                                    val gmmIntentUri =
                                                        //Uri.parse("google.navigation:q=-11.857904,-77.028424&")
                                                        Uri.parse("google.navigation:q=" + latitudeClient + "," + longitudeClient)
                                                    Log.e(
                                                        "REOS",
                                                        "SomeScreen-DialogScreenOne()-url.googlemaps:" + "google.navigation:q=" + latitudeClient + "," + longitudeClient
                                                    )
                                                    val mapIntent =
                                                        Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                                    mapIntent.setPackage("com.google.android.apps.maps")
                                                    //var menuView:MenuView= MenuView()
                                                    //menuView.startActivity(mapIntent)
                                                    mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                    //startActivity(contextForToast, mapIntent,Bundle())
                                                    contextForToast.startActivity(mapIntent)
                                                })
                                        //Clip Image to be shaped as a circle
                                        //.clip(CircleShape)
                                        //.border(1.5.dp, MaterialTheme.colors.secondary, CircleShape
                                        //)
                                    )


                                    Text(
                                        text = "Google Maps",
                                        modifier = Modifier.align(CenterHorizontally),
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 18.sp,

                                            )
                                    )
                                }
                            }



                            Spacer(modifier = Modifier.height(20.dp))


                            Row(
                                modifier = Modifier
                                    //.fillMaxHeight(1f)
                                    .fillMaxWidth(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {

                                Button(
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                                    onClick = {
                                        dialogOpen=false
                                        onDismiss()


                                    }) {
                                    Text(text = "Aceptar", fontSize = 18.sp, color = Color.White)
                                }

                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                        //Column
                    }
                }
                //////////////Box
                HeaderImage(
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.TopCenter)
                        .clickable {}
                        )

                }
            }
    }
}

@Composable
fun MyUI() {

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