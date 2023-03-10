package com.vistony.salesforce.kotlin.validationaccountclient.ui

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.vistony.salesforce.View.MenuConsultaCobradoView.mListener
import com.vistony.salesforce.kotlin.compose.theme.VistonySalesForce_PedidosTheme


private const val ARG_PARAM1 = "param1"
var text1: String = "Elegir Cliente"

class ValidationAccountClient: Fragment() {
    private var param1:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("REOS", "ValidationAccountClient-onCreate")
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

            Log.e("REOS", "ValidationAccountClient-onCreate-param1")
        }
    }

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
                        ValidationAccountClientPreview()
                    }
                }
            }
        }
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VistonySalesForce_PedidosTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {

                    Log.e(
                        "REOS",
                        "ValidationAccountClient-onCreate()"
                    )
                    ValidationAccountClientPreview()
                }
            }
        }
    }*/



    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(tag: String?, dato: Any?)
    }

    var mListener: OnFragmentInteractionListener? = null

    companion object{
        @JvmStatic
        fun newInstanceValidationAccountClient(param1: String)=ValidationAccountClient().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1,param1)
                text1 = param1
                Log.e("REOS", "ValidationAccountClient-newInstanceValidationAccountClient-text1"+text1)
            }
        }

        @Composable
        fun data(name:String )
        {
            BoxNameClient(name)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ValidationAccountClientPreview(){
    VistonySalesForce_PedidosTheme {
        Column() {
            Scaffold(
                content = { content()},
                bottomBar = { BottomBar()}

            )
        }

    }
}
@Composable
fun BottomBar(){

    Row(modifier = Modifier
        .background(color = Color.Blue)
        .fillMaxWidth()
    ) {
        Column(modifier = Modifier.weight(2f)) {
            Box(modifier = Modifier
                .background(color = Color.Blue)
                .fillMaxWidth(),
                contentAlignment = Alignment.TopStart ){
                Text(text = "Cantidad",color = Color.White, fontSize = 22.sp)
            }
            Box(modifier = Modifier
                .background(color = Color.Blue)
                .fillMaxWidth(),
                contentAlignment = Alignment.TopStart ){
                Text(text = "Monto",color = Color.White, fontSize = 22.sp )
            }

        }
        Column(modifier = Modifier.weight(2f)) {
            Box(modifier = Modifier
                .background(color = Color.Blue)
                .fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd ){
                Text(text = "Cantidad1",color = Color.White, fontSize = 22.sp)
            }
            Box(modifier = Modifier
                .background(color = Color.Blue)
                .fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd ){
                Text(text = "Monto1",color = Color.White, fontSize = 22.sp )
            }
        }
    }
}


//val  text by remember { mutableStateOf ("Elegir Cliente") }



@Composable
fun content(){
    val  text by remember { mutableStateOf (text1) }
    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                val Fragment = "ValidationAccountClient"
                val accion = "findClient"
                val compuesto = "$Fragment-$accion"
                val objeto = "findClientValidationAccountClient"


                //mListener?.onFragmentInteraction(compuesto, objeto).apply {  }
                //mListener?.onFragmentInteraction(compuesto, objeto).runCatching {  }
                //mListener?.onFragmentInteraction(compuesto, objeto).run {  }
                /*menuview.onFragmentInteraction(compuesto, objeto)
                mListener.run {
                    OnFragmentInteractionListener(compuesto, objeto)
                }*/
                /*mListener?.apply {
                    onFragmentInteraction(compuesto, objeto)
                }
                mListener.apply { OrdenVentaCabeceraView.OnFragmentInteractionListener }*/
                //mListener.run { OnFragmentInteractionListener(compuesto,objeto) }
                //mListener?.onFragmentInteraction(compuesto, objeto)
                //onFragmentInteraction(compuesto, objeto)
                Log.e("REOS", "ValidationAccountClient-content-Button-onClick")
                /*val ft: FragmentTransaction? = null
                var contentFragment: Fragment? = null
                contentFragment = BuscarClienteView()*/
                //val taKardexOfPaymentView = "start"
                //KardexOfPaymentFragment  =getSupportFragmentManager().findFragmentByTag(taKardexOfPaymentView);
                //ft.hide(KardexOfPaymentFragment);
                //ft.add(R.id.content_menu_view,contentFragment,tag2);
                //KardexOfPaymentFragment  =getSupportFragmentManager().findFragmentByTag(taKardexOfPaymentView);
                //ft.hide(KardexOfPaymentFragment);
                //ft.add(R.id.content_menu_view,contentFragment,tag2);
                /*ft.add(
                    R.id.content_menu_view,
                    BuscarClienteView.newInstanciaHistoricContainerSale(Lista),
                    tag2
                )
                ft.addToBackStack("popsssggggersa")
                ft.commit()*/
                /*var buscarClienteView: BuscarClienteView = BuscarClienteView()
                var intent: Intent = Intent()
                buscarClienteView.startActivity(intent)*/
                //var menuview: MenuView = MenuView()
                //menuview.onFragmentInteraction(compuesto, objeto)
                //mListener?.onFragmentInteraction(compuesto, objeto)
                //mListener?.onFragmentInteraction(compuesto, objeto).apply {  ]
                //mListener?.onFragmentInteraction(compuesto, objeto).runCatching {  }
                mListener?.onFragmentInteraction(compuesto, objeto)
                Log.e("REOS", "ValidationAccountClient-content-Button-onClick-mListener?.onFragmentInteraction(compuesto, objeto) {  }")
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            )
            {
                Icon(imageVector = Icons.Default.Search
                    , contentDescription = null, tint = Color.White
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = BottomCenter,

                ){
                Text(text = text, fontSize = 22.sp)
                //BoxNameClient(name = "Elegir Cliente")
            }
        }
        Box (modifier = Modifier
            .background(color = Color.Blue)
            .fillMaxWidth()
            , contentAlignment = Alignment.Center

        ){
            Text(text = "Documentos",color = Color.White, textAlign = TextAlign.End )
        }
        LazyColumn(){
            items(200){
                Text(text = "Prueba",color = Color.Black, textAlign = TextAlign.End )
            }
        }


    }


}
@Composable
fun EditBox(name: String){
    BoxNameClient(name = name)
}


@Composable
fun BoxNameClient(name: String){

    Text(text = name, fontSize = 22.sp)

}

