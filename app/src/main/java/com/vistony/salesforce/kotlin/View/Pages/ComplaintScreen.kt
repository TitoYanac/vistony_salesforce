package com.vistony.salesforce.kotlin.View.Pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.vistony.salesforce.View.MenuConsultaCobradoView
import com.vistony.salesforce.kotlin.View.Atoms.theme.VistonyTheme

class ComplaintScreen : Fragment()
{
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
                        ComplaintScreenPreview()
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun ComplaintScreenPreview(){
    VistonyTheme() {
        Column() {
            Scaffold(
                content = { content1()},
                bottomBar = { BottomBar()}

            )
        }

    }
}

@Composable
fun content1(){
    val  text by remember { mutableStateOf (text1) }
    Column() {
        Box (modifier = Modifier
            .background(color = Color.Blue)
            .fillMaxWidth()
            , contentAlignment = Alignment.Center

        ){
            Text(text = "Datos del Cliente",color = Color.White, textAlign = TextAlign.End )
        }
        Box (modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            , contentAlignment = Alignment.TopStart

        ){
            Text(
                text = "Razon Social",
                color = Color.Gray,
                textAlign = TextAlign.End,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.size(400.dp,40.dp)
            ) {
                IconButton(
                    onClick = {
                        val Fragment = "ValidationAccountClient"
                        val accion = "findClient"
                        val compuesto = "$Fragment-$accion"
                        val objeto = "findClientValidationAccountClient"
                        MenuConsultaCobradoView.mListener?.onFragmentInteraction(compuesto, objeto)
                        Log.e(
                            "REOS",
                            "ValidationAccountClient-content-Button-onClick-mListener?.onFragmentInteraction(compuesto, objeto) {  }"
                        )
                    },
                    modifier = Modifier.background(Color.Red, shape = RectangleShape)
                )
                {
                    Row() {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.White
                        )

                    }

                }
                Text(
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    text = "Razon Social", color = Color.Black, textAlign = TextAlign.Center
                )
            }
        }
        Box (modifier = Modifier
            .background(color = Color.Blue)
            .fillMaxWidth()
            , contentAlignment = Alignment.Center

        ){
            Text(text = "Documentos",color = Color.White, textAlign = TextAlign.Center )
        }
        LazyColumn(){
            items(200){
                Text(text = "Prueba",color = Color.Black, textAlign = TextAlign.End )
            }
        }


    }
}