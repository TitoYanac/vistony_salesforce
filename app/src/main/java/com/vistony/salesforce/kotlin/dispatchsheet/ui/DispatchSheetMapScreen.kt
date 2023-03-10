package com.vistony.salesforce.kotlin.dispatchsheet.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite

import com.vistony.salesforce.kotlin.compose.theme.VistonySalesForce_PedidosTheme

class DispatchSheetMapScreen : Fragment()
    ,OnMapReadyCallback

{
    private lateinit var mMap: GoogleMap  //declaration inside class


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
        Log.e(
            "REOS",
            "GooleMaps-onMapReady-ingreso"
        )
    }
}

@Composable
fun StartGoogleMaps()
{
    val appContext = LocalContext.current
    //val marker = com.google.android.gms.maps.model.LatLng(28.270833, -16.63916)


    GoogleMap(modifier = Modifier.fillMaxSize(),
        properties = MapProperties(mapType = MapType.NORMAL, isMyLocationEnabled = true )
        ){

            var lista=DetailDispatchSheetSQLite(appContext)
                .getDetailDispatchSheetforCodeControlMap("10697")

            Log.e(
                "REOS",
                "GooleMaps-StartGoogleMaps-lista.size"+lista.size
            )
            for ( i in 0..lista.size-1)
            {
                val marker = com.google.android.gms.maps.model.LatLng(
                    Convert.stringToDouble(lista[i].getLatitud())
                    ,
                    Convert.stringToDouble(lista[i].getLongitud())
                )
                val icono=null
                Log.e(
                    "REOS",
                    "GooleMaps-StartGoogleMaps-lista[i].getEstado(): "+lista[i].getEstado()
                )


                Log.e(
                    "REOS",
                    "GooleMaps-StartGoogleMaps-icono: "+icono
                )
                when (lista[i].getEstado()){
                    "ENTREGADO" -> {
                        Marker(position = marker,
                        title = lista[i].getNombrecliente(),
                        snippet = "Estado: "+lista[i].getEstado(),
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    )
                    }
                    "ANULADO" -> {
                        Marker(position = marker,
                        title = lista[i].getNombrecliente(),
                        snippet = "Estado: "+lista[i].getEstado(),
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    )
                    }
                    "VOLVER A PROGRAMAR" -> {
                        Marker(position = marker,
                        title = lista[i].getNombrecliente(),
                        snippet = "Estado: "+lista[i].getEstado(),
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    )}
                    "PROGRAMADO" -> {
                        Marker(
                            position = marker,
                            title = lista[i].getNombrecliente(),
                            snippet = "Estado: " + lista[i].getEstado(),
                            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                        )
                    }
                }

                /*Marker(position = marker,
                    title = lista[i].getNombrecliente(),
                    snippet = "Estado: "+lista[i].getEstado(),
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )*/

                Log.e(
                    "REOS",
                    "GooleMaps-StartGoogleMaps-i: "+i
                )
                /*Log.e(
                    "REOS",
                    "GooleMaps-StartGoogleMaps-PRUEBA: "+"[$i, ${lista[i].latitud}]"
                )*/
                Log.e(
                    "REOS",
                    "GooleMaps-StartGoogleMaps-PRUEBA1: "+"[$i, ${lista[i].getLatitud()}]"
                )
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VistonySalesForce_PedidosTheme {
        Greeting("Android")
    }
}