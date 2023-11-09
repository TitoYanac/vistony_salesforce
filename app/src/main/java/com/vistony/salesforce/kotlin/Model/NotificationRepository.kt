package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitApi
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NotificationRepository {
    private val _resultDB = MutableStateFlow(NotificationEntity())
    val resultDB: StateFlow<NotificationEntity> get() = _resultDB

    private val _resultAPI = MutableStateFlow(NotificationQuotationEntity())
    val resultAPI: StateFlow<NotificationQuotationEntity> get() = _resultAPI

    fun addNotification(context: Context,notificationList: Notification)
    {
        try {
                        val executor: ExecutorService = Executors.newFixedThreadPool(1)
                        for (i in 1..1) {
                            executor.execute {
                                println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                                //Thread.sleep(1000)
                                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                                database?.notificationDao
                                    ?.addNotification (
                                        notificationList
                                    )

                                println("Tarea $i completada")
                            }

                        }
                        executor.shutdown()
        } catch (e: Exception) {
            Log.e(
                "REOS",
                "ReasonDispatchRepository-getReasonDispatch-error: " + e.toString()
            )
        }
    }

    suspend fun getNotification(context: Context,startDate: String,endDate:String)
    {
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    //Thread.sleep(1000)
                    Log.e("REOS", "NotificationRepository-getNotification-startDate: " + startDate)
                    Log.e("REOS", "NotificationRepository-getNotification-endDate: " + endDate)
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    var data=database?.notificationDao
                        ?.getNotification (
                            startDate, endDate
                        )
                    Log.e("REOS", "NotificationRepository-getNotification-data: " + data!!)
                    //Log.e("REOS", "NotificationRepository-getNotification-data.value!!.size: " + data.value!!.size)
                    CoroutineScope(Dispatchers.Default).launch {
                            try {
                                data.collect { value ->
                                    Log.e("REOS", "NotificationRepository-getNotification-value.size: " + value.size)
                                    if (value.size>0)
                                    {
                                        _resultDB.value=NotificationEntity(Status = "Y", DATA = value)
                                    }else {
                                        _resultDB.value=NotificationEntity(Status = "N", DATA = emptyList())
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
                "NotificationRepository-getNotification-error: " + e.toString()
            )
        }
    }

    suspend fun getNotificationQuotation(Imei: String,lista:ArrayList<String> )
    {
        try {
            val retrofitConfig: RetrofitConfig? = RetrofitConfig()
            val service = retrofitConfig?.getClientLog()?.create(
                    RetrofitApi
                    ::class.java
            )
            var json: String = ""
            val gson = Gson()
            if (lista != null) {
                json = gson.toJson(lista)
                json = "{ \"Imei\":\"${Imei}\",\"DocEntry\":$json}"
            }
            Log.e("REOS", "NotificationRepository-getNotificationQuotation-json: " +json)
            val jsonRequest: RequestBody = RequestBody.create(
                    ("application/json; charset=utf-8").toMediaTypeOrNull(),
                    json
            )
            service?.getNotificationQuotation(
                    jsonRequest
            )?.enqueue(object : Callback<NotificationQuotationEntity?> {

                override fun onResponse(
                        call: Call<NotificationQuotationEntity?>,
                        response: Response<NotificationQuotationEntity?>
                ) {
                    Log.e("REOS", "NotificationRepository-getNotificationQuotation-call:: " +call)
                    Log.e("REOS", "NotificationRepository-getNotificationQuotation-response: " +response)
                    val notificationQuotation = response.body()

                    if (response.isSuccessful&&notificationQuotation?.DATA?.size!!>0)
                    {
                        var list= notificationQuotation.DATA
                        _resultAPI.value= NotificationQuotationEntity (Status = "Y", DATA = list)
                    }
                    Log.e("REOS", "NotificationRepository-getNotificationQuotation-_resultAPI.value.DATA: " +_resultAPI.value.DATA)
                }

                override fun onFailure(call: Call<NotificationQuotationEntity?>, t: Throwable) {
                    _resultAPI.value= NotificationQuotationEntity (Status = "N")
                }
            })


        } catch (e: Exception) {

        }
    }
}