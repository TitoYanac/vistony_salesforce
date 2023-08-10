package com.vistony.salesforce.kotlin.utilities

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.vistony.salesforce.Dao.Retrofit.HistoricSalesOrderTraceabilityRepository
import com.vistony.salesforce.Dao.SQLite.OrdenVentaCabeceraSQLite
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.View.HistoricSalesOrderTraceabilityView
import com.vistony.salesforce.View.MenuView
import com.vistony.salesforce.kotlin.compose.components.contexto

class ServiceNotificationApp: Service()  {
    //var isServiceRunning:Boolean = false
    private var counter = 0
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onBind(intent: Intent): IBinder? {
        // No necesitamos un enlace, así que simplemente devolvemos null
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Aquí puedes realizar las tareas en segundo plano que necesites hacer
        // Puedes usar un hilo separado o un Worker para ejecutar las tareas
        Log.e(
            "REOS",
            "Service-onStartCommand-inicia"
        )
        // Devuelve START_STICKY si deseas que el servicio se reinicie automáticamente
        // después de que el sistema lo haya destruido
        Log.e(
            "REOS",
            "Service-onStartCommand-contexto"+contexto
        )
        try {
            if (contexto != null) {
                //val notification = createNotification(contexto)
                // Resto del código
                val notification = createNotification(contexto)
                val NOTIFICATION_ID = 1
                // Mostrar el servicio en primer plano
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    startForeground(
                        NOTIFICATION_ID,
                        notification!!,
                        ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
                    )
                } else {
                    startForeground(NOTIFICATION_ID, notification)
                }
                isServiceRunning = true
                handler = Handler()
                runnable = Runnable {
                    if (isServiceRunning)
                    {
                        counter++
                        //Toast.makeText(this, "Contador: $counter", Toast.LENGTH_SHORT).show()
                        handler.postDelayed(runnable, 60000) // Ejecutar cada segundo
                        Log.e(
                            "REOS",
                            "Service-onStartCommand-counter" + counter
                        )


                        var ordenVentaCabeceraSQLite: OrdenVentaCabeceraSQLite =OrdenVentaCabeceraSQLite(contexto)
                        var listOrdenVentaCabecera:List<OrdenVentaCabeceraSQLiteEntity>
                        listOrdenVentaCabecera=ordenVentaCabeceraSQLite.getQuotationStatus()
                        Log.e(
                            "REOS",
                            "Service-onStartCommand-listOrdenVentaCabecera.size" + listOrdenVentaCabecera.size
                        )
                        try {
                            for (i in 0 until listOrdenVentaCabecera.size)
                            {
                                var historicSalesOrderTraceabilityRepository: HistoricSalesOrderTraceabilityRepository
                                        = ViewModelProvider(MenuView.viewModelStoreOwner).get(
                                    HistoricSalesOrderTraceabilityRepository::class.java
                                )
                                var fecha:String=""
                                fecha=listOrdenVentaCabecera.get(i).getFecharegistro()
                                Log.e(
                                    "REOS",
                                    "Service-onStartCommand-listOrdenVentaCabecera.fecha: " + fecha
                                )
                                Log.e(
                                    "REOS",
                                    "Service-onStartCommand-i: " + i.toString()
                                )
                                Log.e(
                                    "REOS",
                                    "Service-onStartCommand-listOrdenVentaCabecera.get(i).getFecharegistro(): " + listOrdenVentaCabecera.get(i).getFecharegistro()
                                )
                                Log.e(
                                    "REOS",
                                    "Service-onStartCommand-historicSalesOrderTraceabilityRepository: " + historicSalesOrderTraceabilityRepository
                                )
                                historicSalesOrderTraceabilityRepository.getHistoricSalesOrderTraceabilityRepository(
                                    SesionEntity.imei, //listOrdenVentaCabecera.get(i).getFecharegistro()
                                    fecha
                                ).observe(MenuView.lifecycleOwner)
                                { data ->
                                    if (data != null) {
                                        Log.e(
                                            "REOS",
                                            "Service-onStartCommand-data" + data.size
                                        )
                                        for(j in 0 until data.size)
                                        {
                                            try {
                                                if(listOrdenVentaCabecera.get(i).getOrdenventa_id().equals(data.get(j).ordenventa_id))
                                                {
                                                    Log.e(
                                                        "REOS",
                                                        "Service-onStartCommand-data.get(j).ordenventa_id" + data.get(j).ordenventa_id
                                                    )
                                                    Log.e(
                                                        "REOS",
                                                        "Service-onStartCommand-data.get(j).ordenventa_id" + data.get(j).comentarioaprobacion
                                                    )
                                                    Log.e(
                                                        "REOS",
                                                        "Service-onStartCommand-listOrdenVentaCabecera.get(i).getStatus()" + listOrdenVentaCabecera.get(i).getStatus()
                                                    )
                                                    if (!listOrdenVentaCabecera.get(i).getStatus().equals(data.get(j).comentarioaprobacion)){
                                                        showNotification(contexto,counter,"Su Cotizacion: "+data.get(j).ordenventa_id+" cambio al estado: "+data.get(j).comentarioaprobacion)
                                                        ordenVentaCabeceraSQLite.UpdateStatusSales(data.get(j).ordenventa_id,data.get(j).comentarioaprobacion)
                                                    }

                                                }
                                            }catch (ex:Exception)
                                            {
                                                Log.e(
                                                    "REOS",
                                                    "Service-onStartCommand-error ex:" + ex.toString()
                                                )
                                            }

                                        }
                                    }
                                }
                            }
                        }catch (e:Exception)
                        {
                            Log.e(
                                "REOS",
                                "Service-onStartCommand-error: " + e.toString()
                            )
                        }
                    }
                }
                        Log.e(
                        "REOS",
                        "Service-onStartCommand-counterpost" + counter
                        )
            handler.postDelayed(runnable, 1000) // Iniciar el contador
    } else {
    Log.e("REOS", "Service-onStartCommand: contexto no inicializado")
    }

    }catch (e:Exception){
    Log.e(
    "REOS",
    "Service-onStartCommand-error: " + e.toString()
    )
}

return START_STICKY
}

    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning = false
        handler.removeCallbacks(runnable) // Detener el contador
        // Aquí puedes liberar cualquier recurso utilizado por el servicio
    }

    companion object {
        @kotlin.jvm.JvmField
        //var isServiceRunning: Boolean
        var isServiceRunning: Boolean = false
        //var isServiceRunning:Boolean= false
    }


}