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

class SalesCalendarRepository {
    private val _result = MutableStateFlow(SalesCalendarEntity())
    val result: StateFlow<SalesCalendarEntity> get() = _result

    private val _resultDB = MutableStateFlow(SalesCalendarEntity())
    val resultDB: StateFlow<SalesCalendarEntity> get() = _resultDB

    fun addSalesCalendar(
        Imei:String
        ,context: Context
        ,from:String
        ,to:String
    )
    {
        try {
            val retrofitConfig: RetrofitConfig? = RetrofitConfig()
            val service = retrofitConfig?.getClientLog()?.create(
                RetrofitApi
                ::class.java
            )
            service?.getCalendar(
                Imei,
                from,
                to
            )?.enqueue(object : Callback<SalesCalendarEntity?> {

                override fun onResponse(
                    call: Call<SalesCalendarEntity?>,
                    response: Response<SalesCalendarEntity?>
                ) {
                    val calendarEntityResponse = response.body()
                    if (response.isSuccessful&&calendarEntityResponse?.Data?.size!!>0) {
                        val executor: ExecutorService = Executors.newFixedThreadPool(1)
                        for (i in 1..1) {
                            executor.execute {
                                println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                                //Thread.sleep(1000)
                                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                                database?.salesCalendarDao
                                    ?.deleteSalesCalendar()
                                database?.salesCalendarDao
                                    ?.addSalesCalendar (
                                        calendarEntityResponse?.Data!!
                                    )

                                println("Tarea $i completada")
                            }

                        }

                        executor.shutdown()
                        _result.value= SalesCalendarEntity(Status = "Y", Data = null)
                    } else {
                        _result.value = SalesCalendarEntity(Status = "N", Data = null)
                    }
                }
                override fun onFailure(call: Call<SalesCalendarEntity?>, t: Throwable) {
                    _result.value=SalesCalendarEntity(Status = "N", Data = null)
                }
            })


        } catch (e: Exception) {
            Log.e(
                "REOS",
                "ReasonDispatchRepository-getReasonDispatch-error: " + e.toString()
            )
        }
    }

    suspend fun getSalesCalendar(context: Context,Date:String)
    {
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    var data=database?.salesCalendarDao
                        ?.getSalesCalendar (
                            Date
                        )
                    Log.e("REOS", "SalesCalendarRepository-getSalesCalendar-data: " + data!!)
                    //Log.e("REOS", "NotificationRepository-getNotification-data.value!!.size: " + data.value!!.size)
                    CoroutineScope(Dispatchers.Default).launch {
                        try {
                            data.collect { value ->
                                Log.e("REOS", "SalesCalendarRepository-getSalesCalendar-value.size: " + value.size)
                                if (value.size>0)
                                {
                                    Log.e(
                                        "REOS",
                                        "SalesCalendarRepository-getSalesCalendar-value: " + value
                                    )

                                    _resultDB.value=SalesCalendarEntity(Status = "Y", Data = value)
                                }else {
                                    _resultDB.value=SalesCalendarEntity(Status = "N")
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
                "ServiceAppRepository-getSalesCalendar-error: " + e.toString()
            )
        }
    }
}