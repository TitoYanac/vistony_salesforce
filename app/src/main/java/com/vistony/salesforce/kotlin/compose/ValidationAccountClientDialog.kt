package com.vistony.salesforce.kotlin.compose
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.data.Ubigeous
import com.vistony.salesforce.kotlin.compose.components.HeaderImage
import com.vistony.salesforce.kotlin.compose.theme.VistonySalesForce_PedidosTheme
import com.vistony.salesforce.kotlin.data.AppDatabase
import com.vistony.salesforce.kotlin.viewmodels.ValidationAccountClientViewModel
import kotlinx.coroutines.launch


class DialogValidationAccountClient ():
    DialogFragment()
//AlertDialog()
{
//    var activities: Activity = TODO()


    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()
        ).apply {
            setContent {
                //Modifier.border(
                    //border = BorderStroke(30.dp,Color.Black)
                    //,shape = RoundedCornerShape(30.dp)

                //)
                VistonySalesForce_PedidosTheme {
                    // A surface container using the 'background' color from the theme
                    Surface {
                        //RoomDatabase.getInstance(context.applicationContext)
                        //val database = RoomDatabase.getInstance(context.applicationContext)

                        val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                        //val room = Room.databaseBuilder(context.applicationContext, RoomDatabase::class.java, "dbcobranzas1").build();
                        lifecycleScope.launch{

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
                        }
                        //database.ubigeoousDao.insertUbigeois()
                        //val ubigeousWorker: UbigeousWorker
                        /*val request = OneTimeWorkRequestBuilder<UbigeousWorker>()
                            .setInputData(workDataOf(KEY_FILENAME to PLANT_DATA_FILENAME))
                            .build()
                        WorkManager.getInstance(context).enqueue(request)*/
                        //var roomdatabaseImpl: RoomDatabase_Impl?=null
                        //roomdatabaseImpl.run {  }
                        //Room.databaseBuilder(context.applicationContext, RoomDatabase::class.java, "tasks-db").build();
                        DefaultPreview2()
                        //HeaderImage()
                        /*Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .verticalScroll(rememberScrollState())
                        ) {


                            Spacer(modifier = Modifier.height(47.dp))

                            //...................................................................
                            // Info dialog demo button to show Info Jetpack compose custom dialog
                            Button(
                                onClick = {
                                    //infoDialog.value = true
                                         // true
                                    //DefaultPreview2()
                                },
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            ) {
                                Text(
                                    text = "Show Confirmation Dialog",
                                    //color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    //style = MaterialTheme.typography.labelLarge,
                                )
                            }
                        }*/
                    }
                    }
                }
            }
        }
    }


    @Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    VistonySalesForce_PedidosTheme {
    
            /*MyUI(
                bodyText = "Quedan solo cinco unidades de este producto.",
                buttonText = "Aceptar",
                onConfirm = {
                    "Ejemplo 5 -> 'ACEPTAR'"
                },
                onDismiss = {
                    false
                },)*/
            PruebaDialog(
                title = "Turn on Location Service",
                desc = "Explore the world without getting lost and keep the track of your location.",
                onDismiss = {
                    //infoDialog.value = false
                    true
                })
        /*InfoDialog(
            title = "Turn on Location Service",
            desc = "Explore the world without getting lost and keep the track of your location.",
            onDismiss = {
                //infoDialog.value = false
                false
            })*/
            //HeaderImage()


    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyUI(
    bodyText: String,
    buttonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Log.e(
        "REOS",
        "DialogValidationAccountClient-MyUI")

    val contextForToast = LocalContext.current.applicationContext
    var list= ValidationAccountClientViewModel().getSalesRoute(contextForToast)



    var selectedItem by remember {
        mutableStateOf(list[0])
    }

    var expanded by remember {
        mutableStateOf(false)
    }


    AlertDialog(
        title = {

        },
        onDismissRequest = onDismiss,
        text = {
            /*Card(
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier.size(280.dp, 240.dp),
                border = BorderStroke(1.dp, Color.Black),
                elevation = 1.dp,
            )
            {*/
            Box(modifier = Modifier
                .height(460.dp))
            {
                Column()
                {

                    Row(
                        modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp),

                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(1f),
                            contentAlignment = Center
                        ) {
                            Text(
                                text = "REPORTE",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black
                                )
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(1f),
                            contentAlignment = Center
                        ) {
                            Text(
                                text = "Validacion de cuenta de Cliente",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black
                                )
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(1f),
                            contentAlignment = Center
                        ) {
                            Text(
                                text = "Debe elegir un Dia, seleccionar el boton PDF",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 14.sp,
                                    //fontWeight = FontWeight.Black
                                )
                            )
                        }
                    }
                    Row(
                        verticalAlignment = CenterVertically,

                        ) {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
                            }, Modifier.weight(2f)
                        ) {

                            // text field
                            TextField(
                                value = selectedItem,
                                onValueChange = {},
                                readOnly = true,
                                label = {
                                    Text(
                                        text = "Dia",
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            //fontWeight = FontWeight.Black
                                        ),
                                    )
                                },
                                trailingIcon = {

                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded

                                    )
                                },

                                //colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Red,
                                    unfocusedBorderColor = Color.Red
                                ),
                                textStyle = TextStyle(color = Color.Black),


                                )

                            // menu
                            ExposedDropdownMenu(

                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                list.forEach { selectedOption ->
                                    // menu item
                                    DropdownMenuItem(onClick = {
                                        selectedItem = selectedOption
                                        Toast.makeText(
                                            contextForToast,
                                            selectedOption,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        expanded = false
                                    }) {
                                        Text(
                                            text = selectedOption,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 14.sp,
                                                //fontWeight = FontWeight.Black
                                            ),

                                            )
                                    }
                                }
                            }
                        }
                        Button(
                            modifier = Modifier
                                .weight(0.5f)
                                .width(55.dp)
                                .height(55.dp),
                            onClick = {
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)

                        )
                        {
                            Icon(
                                painter = painterResource(R.drawable.ic_baseline_picture_as_pdf_24),
                                contentDescription = null, tint = Color.White
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
                                onDismiss()

                            }) {
                            Text(text = "Aceptar", fontSize = 18.sp, color = Color.White)
                        }
                    }
                    // the box
                }
                // }

            }

            /*HeaderImage(
                modifier = Modifier
                    .size(200.dp)

                    //.align(Alignment.TopCenter)
                /*.border(
                    border = BorderStroke(width = 5.dp, color = Color.White),
                    shape = CircleShape
                )*/
            )*/
         },
        confirmButton= {},
        /*confirmButton= {
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
                        //dismiss()
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Black),
                ) {
                    Text(
                        modifier = Modifier.align(CenterVertically),
                        text = buttonText)
                }
            }
        },*/
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


@Composable
fun InfoDialog(
    title: String?="Message",
    desc: String?="Your Message",
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {

        Box(
            modifier = Modifier
                .height(460.dp)
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
                            text = title!!,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            //style = MaterialTheme.typography.headlineSmall,
                            //style = MaterialTheme.typography,
                            //color = MaterialTheme.colorScheme.onPrimaryContainer,
                            //color = MaterialTheme.colors.onPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))


                        Text(
                            text = desc!!,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                            //style = MaterialTheme.typography.bodyLarge,
                            //style = MaterialTheme.typography.body1,
                            //color = MaterialTheme.colorScheme.onPrimaryContainer,
                            //color = MaterialTheme.colors.onPrimary
                        )
                        Spacer(modifier = Modifier.height(24.dp))


                        Button(
                            onClick = onDismiss,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            Text(
                                text = "Later",
                                color = Color.White
                            )
                        }
                        Button(
                            onClick = onDismiss,
                            //colors = ButtonDefaults.buttonColors(Colors = MaterialTheme.colors.primary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            Text(
                                text = "Enable Location",
                                color = Color.White
                            )
                        }


                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
            /*Box(
                modifier = Modifier
                .background(
                Color.White
                )
                .size(130.dp)
                .align(Alignment.TopCenter)
                //.clip(CircleShape)
                    .clip(RoundedCornerShape(
                        topEndPercent = 50,
                        bottomStartPercent = 50
                        , topStartPercent = 50
                        , bottomEndPercent = 50))
            )*/
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
                    /*.clip(CircleShape)
                    padding(0.dp,0.dp,0.dp,0.dp)
                    .background(Color.White,
                        RoundedCornerShape(
                        topEndPercent = 20,
                        bottomStartPercent = 20
                        , topStartPercent = 20
                        , bottomEndPercent = 20
                    )
                    )*/
            )
            /*HeaderImage(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.TopCenter)
                /*.border(
                    border = BorderStroke(width = 5.dp, color = Color.White),
                    shape = CircleShape
                )*/
            )*/
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PruebaDialog(
    title: String?="Message",
    desc: String?="Your Message",
    onDismiss: () -> Unit
) {
    val contextForToast = LocalContext.current.applicationContext
    val activity = LocalContext.current as Activity
    val viewModelStoreOwner = LocalContext.current as ViewModelStoreOwner
    val lifecycleOwner = LocalContext.current as LifecycleOwner
    var list= ValidationAccountClientViewModel().getSalesRoute(contextForToast)

    var selectedItem by remember {
        mutableStateOf(list[0])
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    Dialog(
        onDismissRequest = onDismiss,
                properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
    )
    ) {

        Box(modifier = Modifier
            .height(460.dp)
        )
        {
            Column(

            )
            {
                Spacer(modifier = Modifier.height(90.dp))

                Box(
                    modifier=
                    Modifier.background(
                        //color = MaterialTheme.colorScheme.onPrimary,
                        color = MaterialTheme.colors.onPrimary,
                        shape = RoundedCornerShape(25.dp, 10.dp, 25.dp, 10.dp)
                    )

                )
                {
                    Column()
                    {
                        Spacer(modifier = Modifier.height(30.dp))
                        Row(
                            modifier = Modifier
                                .padding(0.dp, 10.dp, 0.dp, 0.dp)
                                .background(Color.White),

                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .background(Color.White),
                                contentAlignment = Center

                            ) {
                                Text(
                                    text = "REPORTE",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                )
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.background(Color.White)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth(1f),
                                contentAlignment = Center
                            ) {
                                Text(
                                    text = "Validacion de cuenta de Cliente",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                )
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(10.dp)
                                .background(Color.White)

                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .background(Color.White),
                                contentAlignment = Center

                            ) {
                                Text(
                                    text = "Debe elegir un Dia, seleccionar el boton PDF",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        //fontWeight = FontWeight.Black
                                    )
                                )
                            }
                        }
                        Row(
                            verticalAlignment = CenterVertically,
                            modifier = Modifier.background(Color.White)
                        ) {
                            var seleccion:String=""
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = {
                                    expanded = !expanded
                                }, Modifier.weight(2f)
                            ) {

                                // text field
                                TextField(
                                    value = selectedItem,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = {
                                        Text(
                                            text = "Dia",
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 14.sp,
                                                //fontWeight = FontWeight.Black
                                            ),
                                        )
                                    },
                                    trailingIcon = {

                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expanded

                                        )
                                    },

                                    //colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        focusedBorderColor = Color.Red,
                                        unfocusedBorderColor = Color.Red
                                    ),
                                    textStyle = TextStyle(color = Color.Black),


                                    )

                                // menu
                                ExposedDropdownMenu(

                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    list.forEach { selectedOption ->
                                        // menu item
                                        DropdownMenuItem(onClick = {
                                            seleccion=selectedOption.toString()
                                            selectedItem = selectedOption
                                            Toast.makeText(
                                                contextForToast,
                                                selectedOption,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            expanded = false
                                        }) {
                                            Text(
                                                text = selectedOption,
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontSize = 14.sp,
                                                    //fontWeight = FontWeight.Black
                                                ),

                                                )
                                        }
                                    }
                                }
                            }
                            Button(
                                modifier = Modifier
                                    .weight(0.5f)
                                    .width(55.dp)
                                    .height(55.dp),
                                onClick = {
                                          var validation: ValidationAccountClientViewModel = ValidationAccountClientViewModel()
                                            validation.generatePDF(
                                                contextForToast,
                                                activity,
                                                seleccion,
                                                viewModelStoreOwner,
                                                lifecycleOwner


                                            )

                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)

                            )
                            {
                                Icon(
                                    painter = painterResource(R.drawable.ic_baseline_picture_as_pdf_24),
                                    contentDescription = null, tint = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
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
                                    onDismiss()

                                }) {
                                Text(text = "Aceptar", fontSize = 18.sp, color = Color.White)
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
            HeaderImage(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.TopCenter)
            ,"Geolocation"
            )
        }
    }
}
