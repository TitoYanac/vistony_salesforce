package com.vistony.salesforce.kotlin.View.Orgsnisms

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.Model.*
import com.vistony.salesforce.kotlin.Utilities.Geolocation
import com.vistony.salesforce.kotlin.ViewModel.VisitSectionViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun contentMapNavigation(
    context:Context,
    latitude:String,
    longitude:String,
    latitudeClient:String,
    longitudeClient:String,
)
{
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
                    .align(Alignment.CenterHorizontally)
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
                                Intent(Intent.ACTION_VIEW)
                            openURL.data = Uri.parse(
                                //"https://waze.com/ul?q=-11.863631,-77.054802&ll=-11.857904,-77.028424&navigate=yes"
                                "https://waze.com/ul?q=" + latitude + "," + longitude + "&ll=" + latitudeClient + "," + longitudeClient + "&navigate=yes"
                            )

                            Log.e(
                                "REOS",
                                "SomeScreen-DialogScreenOne()-url.waze:" + "https://waze.com/ul?q=" + latitude + "," + longitude + "&ll=" + latitudeClient + "," + longitudeClient + "&navigate=yes"
                            )
                            openURL.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(openURL)
                            Toast
                                .makeText(context, "Waze", Toast.LENGTH_SHORT)
                                .show()
                        })


            )
            Text(
                text = "Waze",
                modifier = Modifier.align(Alignment.CenterHorizontally),
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
                    .align(Alignment.CenterHorizontally)
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
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            mapIntent.setPackage("com.google.android.apps.maps")
                            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(mapIntent)
                        })
            )
            Text(
                text = "Google Maps",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    )
            )
        }
    }
}


fun contentVisitDriver(
    context:Context,
    lifecycleOwner: LifecycleOwner,
    list: DetailDispatchSheetUI,
    statusDispatchViewModel: StatusDispatchViewModel,
    statusStartVisit:String
)
{
    Log.e("REOS", "ContentDialog-contentVisitDriver-inicio")
    val geoLocalizacion = Geolocation(context)
    geoLocalizacion.obtenerUbicacionActual(
        onSuccess = { location ->
            var latitude:String=""
            var longitude:String=""
            Log.e("REOS", "ContentDialog-contentVisitDriver-location.latitude.toString(): "+location.latitude.toString())
            Log.e("REOS", "ContentDialog-contentVisitDriver-location.longitude.toString(): "+location.longitude.toString())
            latitude=location.latitude.toString()
            longitude=location.longitude.toString()
            var visitSectionViewModel: VisitSectionViewModel
            val visitSectionRepository = VisitSectionRepository()
            visitSectionViewModel = VisitSectionViewModel(visitSectionRepository)
            val dateFormathora =
                SimpleDateFormat("HHmmss", Locale.getDefault())
            val FormatFecha =
                SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            val date = Date()
            Log.e("REOS", "ContentDialog-contentVisitDriver-statusStartVisit: "+statusStartVisit)
            if(statusStartVisit.equals("Y"))
            {
                Log.e("REOS", "ContentDialog-contentVisitDriver-if-SesionEntity.imei: "+SesionEntity.imei)
                Log.e("REOS", "ContentDialog-contentVisitDriver-if-list.cliente_id: "+list.cliente_id.toString())
                Log.e("REOS", "ContentDialog-contentVisitDriver-if-list.domembarque_id: "+list.domembarque_id)
                Log.e("REOS", "ContentDialog-contentVisitDriver-if-FormatFecha.format(date): "+FormatFecha.format(date))
                Log.e("REOS", "ContentDialog-contentVisitDriver-if-list.control_id.toString(): "+list.control_id)
                visitSectionViewModel?.getVisitSection(
                    SesionEntity.imei,
                    context,
                    lifecycleOwner,
                    list.cliente_id.toString(),
                    list.domembarque_id.toString(),
                    FormatFecha.format(date),
                    list.control_id.toString(),
                    list.item_id.toString()
                )
                visitSectionViewModel.list.observe(lifecycleOwner) { data ->
                    // actualizar la UI con los datos obtenidos
                    Log.e("REOS", "ContentDialog-contentVisitDriver.visitSectionViewModel.observe.data.size"+data.size)

                    if(!data.isEmpty())
                    {
                        if(location.latitude!=0.0&&location.longitude!=0.0)
                        {
                            Log.e(
                                "REOS",
                                "Composables-InfoDialogEnd.visitSectionViewModel.location.latitude"+location.latitude
                            )
                            Log.e(
                                "REOS",
                                "Composables-InfoDialogEnd.visitSectionViewModel.location.longitude"+location.longitude
                            )

                            data.forEachIndexed { index, step ->
                                data.get(index).datefin=FormatFecha.format(date)
                                data.get(index).timefin=dateFormathora.format(date)
                                data.get(index).latitudfin=latitude
                                data.get(index).longitudfin=longitude
                            }
                            visitSectionViewModel?.updateVisitSection(
                                data,
                                lifecycleOwner,
                                context,
                            )

                            visitSectionViewModel.status.observe(
                                lifecycleOwner
                            ) { data ->
                                // actualizar la UI con los datos obtenidos
                                Log.e(
                                    "REOS",
                                    "Composables-InfoDialogEnd.visitSectionViewModel.observe.statusadd.size" + data.toString()
                                )
                            }

                            if (SesionEntity.perfil_id == "CHOFER" || SesionEntity.perfil_id == "chofer") {
                                data.forEachIndexed { index, step ->
                                    statusDispatchViewModel.updateStatusDispatch(
                                        data.get(index).timeini,
                                        data.get(index).timefin,
                                        data.get(index).latitudini,
                                        data.get(index).longitudini,
                                        list.domembarque_id,
                                        list.cliente_id
                                    )
                                }
                            }
                        }else{


                        }

                    }else {
                        Log.e(
                            "REOS",
                            "Composables-InfoDialog.geoLocalizacion.lalistatraedata"
                        )
                    }
                }
            }else {
                Log.e("REOS", "ContentDialog-contentVisitDriver-noif-SesionEntity.imei: "+SesionEntity.imei)
                Log.e("REOS", "ContentDialog-contentVisitDriver-noif-list.cliente_id: "+list.cliente_id.toString())
                Log.e("REOS", "ContentDialog-contentVisitDriver-noif-list.domembarque_id: "+list.domembarque_id)
                Log.e("REOS", "ContentDialog-contentVisitDriver-noif-FormatFecha.format(date): "+FormatFecha.format(date))
                Log.e("REOS", "ContentDialog-contentVisitDriver-noif-list.control_id.toString(): "+list.control_id)
                visitSectionViewModel?.getVisitSection(
                    SesionEntity.imei,
                    context,
                    lifecycleOwner,
                    list.cliente_id.toString(),
                    list.domembarque_id.toString(),
                    FormatFecha.format(date),
                    list.control_id.toString(),
                    list.item_id.toString()
                )
                visitSectionViewModel.list.observe(lifecycleOwner) { data ->
                    // actualizar la UI con los datos obtenidos
                    Log.e(
                        "REOS",
                        "Composables-InfoDialog.visitSectionViewModel.observe.data.size"+data.size
                    )

                    if(data.isEmpty())
                    {

                        if(location.latitude==0.0&&location.longitude==0.0)
                        {
                            Log.e(
                                "REOS",
                                "Composables-InfoDialog.geoLocalizacion.entroif"
                            )
                        }else{
                            Log.e(
                                "REOS",
                                "Composables-InfoDialog.geoLocalizacion.noentroif"
                            )
                            try {
                                var listVisitSection: MutableList<VisitSection> =
                                    mutableListOf()

                                var ObjUsuario = UsuarioSQLiteEntity()
                                val usuarioSQLite: UsuarioSQLite =
                                    UsuarioSQLite(context)
                                ObjUsuario =
                                    usuarioSQLite.ObtenerUsuarioSesion()
                                var visitSection: VisitSection? = VisitSection(
                                    //ObtenerFechaHoraCadena().toInt(),
                                    0,
                                    ObjUsuario.compania_id,
                                    ObjUsuario.fuerzatrabajo_id,
                                    ObjUsuario.usuario_id,
                                    list.cliente_id.toString(),
                                    list.domembarque_id.toString(),
                                    latitude,
                                    longitude,
                                    FormatFecha.format(date),
                                    dateFormathora.format(date),
                                    "0",
                                    "0",
                                    "0",
                                    "0",
                                    "0",
                                    list.control_id.toString(),
                                    list.nombrecliente.toString(),
                                    list.item_id.toString(),
                                    list.entrega.toString()
                                )
                                if (visitSection != null) {
                                    listVisitSection?.add(visitSection)
                                }

                                Log.e(
                                    "REOS",
                                    "Composables-InfoDialog.visitSectionViewModel.visitSection" + visitSection
                                )
                                visitSectionViewModel?.addVisitSection(
                                    listVisitSection,
                                    lifecycleOwner,
                                    context,
                                )
                                visitSectionViewModel.status.observe(
                                    lifecycleOwner
                                ) { data ->
                                    // actualizar la UI con los datos obtenidos
                                    Log.e(
                                        "REOS",
                                        "Composables-InfoDialog.visitSectionViewModel.observe.statusadd.size" + data.toString()
                                    )
                                }
                            }catch (e: Exception)
                            {
                                Log.e(
                                    "REOS",
                                    "Composables-InfoDialog-error" + e.toString()
                                )
                            }

                        }

                    }else {
                        Log.e(
                            "REOS",
                            "Composables-InfoDialog.geoLocalizacion.lalistatraedata"
                        )
                    }
                }
            }

        },
        onFailure = {
            // No se pudo obtener la ubicación actual del usuario
        }

    )
}