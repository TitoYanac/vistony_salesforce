package com.vistony.salesforce.kotlin.utilities

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class Geolocation(private val context: Context)
{
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fun obtenerUbicacionActual(onSuccess: (Location) -> Unit, onFailure: () -> Unit) {
        // Comprobar si se han otorgado los permisos necesarios
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                onFailure()
                return
            }

            // Obtener la ubicación actual del usuario
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let { onSuccess(it) } ?: onFailure()
            }.addOnFailureListener {
                onFailure()
            }
        }catch (e:Exception)
        {
            Log.e(
                "REOS",
                "Geolocation-obtenerUbicacionActual.error: "+e.toString()
            )
        }

    }

}