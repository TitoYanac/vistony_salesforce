package com.vistony.salesforce.kotlin.Utilities

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.vistony.salesforce.Controller.Utilitario.Convert
import com.vistony.salesforce.Dao.Retrofit.HistoricSalesOrderTraceabilityRepository
import com.vistony.salesforce.Dao.SQLite.OrdenVentaCabeceraSQLite
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.View.MenuView
import com.vistony.salesforce.kotlin.Model.Notification
import com.vistony.salesforce.kotlin.Model.NotificationRepository
import com.vistony.salesforce.kotlin.Model.SalesCalendarRepository
import com.vistony.salesforce.kotlin.Model.ServiceAppRepository
//import com.vistony.salesforce.View.MenuView
//import com.vistony.salesforce.View.MenuView
import com.vistony.salesforce.kotlin.View.components.contexto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServiceApp: Service()  {
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
        //var statusService:String="N"
        val serviceAppRepository:ServiceAppRepository= ServiceAppRepository()
        val salesCalendarRepository:SalesCalendarRepository= SalesCalendarRepository()
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        var interval:Long=360000
        var statusService:Boolean=false
        var statusVariableRange:Boolean=false
        var statusDayHabil:Boolean=false
        //serviceAppRepository.getServiceApp(context = contexto,"1")
        coroutineScope.launch {
            serviceAppRepository.getServiceApp(context = contexto,"1")
        }
        coroutineScope.launch {
            salesCalendarRepository.getSalesCalendar(context = contexto, getDate().toString())
        }
        coroutineScope.launch {
            serviceAppRepository.resultDB.collect{newValue ->
                Log.e("REOS", "Service-onStartCommand-serviceAppRepository.resultDB.newValue" + newValue.Data)
                for (i in 0 until newValue.Data!!.size)
                {
                    Log.e("REOS", "Service-onStartCommand-newValue.Data!!.size" + newValue.Data!!.size)
                    Log.e("REOS", "Service-onStartCommand-newValue.Data!!.get(i).Status" + newValue.Data!!.get(i).Status)
                    Log.e("REOS", "Service-onStartCommand-newValue.Data!!.get(i).StartTime" + newValue.Data!!.get(i).StartTime)
                    Log.e("REOS", "Service-onStartCommand-newValue.Data!!.get(i).EndTime" + newValue.Data!!.get(i).EndTime)
                    Log.e("REOS", "Service-onStartCommand-newValue.Data!!.get(i).Description" + newValue.Data!!.get(i).Description)
                    Log.e("REOS", "Service-onStartCommand-newValue.Data!!.get(i).Interval" + newValue.Data!!.get(i).Interval)
                    Log.e("REOS", "Service-onStartCommand-getTimeCurrentHHMM()  " + getTimeCurrentHHMM()  )
                    getTimeCurrent()
                    interval=newValue.Data!!.get(i).Interval!!.toLong()
                    Log.e("REOS", "Service-onStartCommand-interval: " + interval)
                    statusVariableRange = getStatusVariableinRange(
                        newValue.Data!!.get(i).StartTime!!,
                        newValue.Data!!.get(i).EndTime!!
                    )
                    if(newValue.Data!!.get(i).Status.toString().equals("N"))
                    {
                        statusService=false
                    }

                    /*else {
                        isServiceRunning=true
                    }*/


                }
            }
        }

        coroutineScope.launch {
            salesCalendarRepository.resultDB.collect{newValue ->
                Log.e("REOS", "Service-onStartCommand-salesCalendarRepository.resultDB.newValue" + newValue.Data)
                for (i in 0 until newValue.Data!!.size)
                {
                   if(newValue.Data!!.get(i).Habil.equals("Y"))
                   {
                       statusDayHabil=true
                   }else{
                       statusDayHabil=false
                   }
                }
            }
        }

        try
        {

            Log.e(
                "REOS",
                "Service-onStartCommand-statusVariableRange: " + statusVariableRange
            )
            if (!statusService || !statusVariableRange || !statusDayHabil
            ) {
                Log.e(
                    "REOS",
                    "Service-onStartCommand-entro-if: "
                )
                isServiceRunning = false
            }
        }catch (e:Exception)
        {
            Log.e(
                "REOS",
                "Service-onStartCommand-error "+e
            )
        }
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
                        //handler.postDelayed(runnable, 20000) // Ejecutar cada segundo
                        //handler.postDelayed(runnable, 10000) // Ejecutar cada segundo
                        handler.postDelayed(runnable, interval) // Ejecutar cada segundo
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
                                                        showNotification(contexto,counter,"La cotización del cliente "+data.get(j).nombrecliente +", con monto "+ Convert.currencyForView(data.get(j).montototalorden)+",  cambio al estado: "+data.get(j).comentarioaprobacion)
                                                        ordenVentaCabeceraSQLite.UpdateStatusSales(data.get(j).ordenventa_id,data.get(j).comentarioaprobacion)
                                                        var notificationRepository:NotificationRepository=
                                                            NotificationRepository()
                                                        notificationRepository.addNotification(
                                                            contexto,Notification(id = 0, message = "La cotización del cliente "+data.get(j).nombrecliente +", con monto "+ Convert.currencyForView(data.get(j).montototalorden)+",  cambio al estado: "+data.get(j).comentarioaprobacion, date = getDateCurrent(), time = getTimeCurrent()))
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