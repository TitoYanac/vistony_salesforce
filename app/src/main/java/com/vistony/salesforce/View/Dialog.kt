package com.vistony.salesforce.View

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.vistony.salesforce.View.ui.theme.VistonySalesForce_PedidosTheme
import com.vistony.salesforce.features.featureone.DialogScreenOne

//import com.karthik.scribblecompose.features.featureone.DialogScreenOne
//import com.karthik.scribblecompose.ui.theme.ScribbleComposeTheme

class DialogMain : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VistonySalesForce_PedidosTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    DialogScreenOne()
                    Log.e(
                        "REOS",
                        "DialogMain-DialogScreenOne()"
                    )
                }
            }
        }
    }
}