package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitApi
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ServiceAppRepository {
    private val _resultAPI = MutableStateFlow(ServiceAppEntity())
    val resultAPI: StateFlow<ServiceAppEntity> get() = _resultAPI

    private val _resultDB = MutableStateFlow(ServiceAppEntity())
    val resultDB: StateFlow<ServiceAppEntity> get() = _resultDB

    fun addServiceApp(Imei:String,context: Context)
    {
        try {
            val retrofitConfig: RetrofitConfig? = RetrofitConfig()
            val service = retrofitConfig?.getClientLog()?.create(
                RetrofitApi
                ::class.java
            )
            service?.getServicesApp(
                Imei,
                "1"
            )?.enqueue(object : Callback<ServiceAppEntity?> {

                override fun onResponse(
                    call: Call<ServiceAppEntity?>,
                    response: Response<ServiceAppEntity?>
                ) {
                    Log.e("REOS", "-RetrofitConfig-getClientLog-call $call")
                    Log.e("REOS", "-RetrofitConfig-getClientLog-response.body ${response.body()}")
                    val servicesAppEntityResponse = response.body()
                    if (response.isSuccessful&&servicesAppEntityResponse?.Data?.size!!>0) {
                        val executor: ExecutorService = Executors.newFixedThreadPool(1)
                        for (i in 1..1) {
                            executor.execute {
                                println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                                //Thread.sleep(1000)
                                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                                database?.serviceAppDao
                                    ?.deleteServiceApp()
                                database?.serviceAppDao
                                    ?.addServicesApp (
                                        servicesAppEntityResponse?.Data!!
                                    )

                                println("Tarea $i completada")
                            }

                        }

                        executor.shutdown()
                        _resultAPI.value= ServiceAppEntity(Status = "Y")
                    } else {
                        _resultAPI.value = ServiceAppEntity(Status = "N")
                    }
                }
                override fun onFailure(call: Call<ServiceAppEntity?>, t: Throwable) {
                    _resultAPI.value=ServiceAppEntity(Status = "N")
                }
            })


        } catch (e: Exception) {
            Log.e(
                "REOS",
                "ServiceAppRepository-addServiceApp-error: " + e.toString()
            )
        }
    }

    suspend fun getServiceApp(context: Context,DocEntry:String)
    {
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    var data=database?.serviceAppDao
                        ?.getServiceApp (
                            DocEntry
                        )
                    Log.e("REOS", "ServiceAppRepository-getServiceApp-data: " + data!!)
                    //Log.e("REOS", "NotificationRepository-getNotification-data.value!!.size: " + data.value!!.size)
                    CoroutineScope(Dispatchers.Default).launch {
                        try {
                            data.collect { value ->
                                Log.e("REOS", "ServiceAppRepository-getServiceApp-value.size: " + value.size)
                                if (value.size>0)
                                {
                                    Log.e(
                                        "REOS",
                                        "ServiceAppRepository-getServiceApp-value: " + value
                                    )

                                    _resultDB.value=ServiceAppEntity(Status = "Y", Data = value)
                                }else {
                                    _resultDB.value=ServiceAppEntity(Status = "N")
                                }
                            }
                        } catch (e: Exception) {
                            println("The flow has thrown an exception: $e")
                        }
                    }
                    println("Tarea $i completada")
                }

            }
            executor.shutdown()
        } catch (e: Exception) {
            Log.e(
                "REOS",
                "ServiceAppRepository-getServiceApp-error: " + e.toString()
            )
        }
    }
}
