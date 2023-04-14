package com.vistony.salesforce.View

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vistony.salesforce.kotlin.compose.MenuOptions
import com.vistony.salesforce.kotlin.compose.ValidationAccountClient
import com.vistony.salesforce.kotlin.compose.ValidationAccountClientPreview


@Composable
fun NavigationHost(navController: NavHostController){
    NavHost(navController = navController
        , startDestination = MenuOptions.Pantalla1.ruta)
    {
        composable(MenuOptions.Pantalla1.ruta){
            Log.e(
                "REOS",
                "NavigationHost-MenuOptions.Pantalla1.ruta-ValidationAccountClient"
            )

            //Prueba()
            ValidationAccountClientPreview()
        }
        composable(MenuOptions.Pantalla2.ruta){
            Log.e(
                "REOS",
                "NavigationHost-MenuOptions.Pantalla2.ruta-ValidationAccountClient"
            )
            ValidationAccountClient()
        }

    }

}