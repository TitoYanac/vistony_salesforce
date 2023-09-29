package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitApi
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotificationRepository {
    private val _resultDB = MutableStateFlow(NotificationEntity())
    val resultDB: StateFlow<NotificationEntity> get() = _resultDB

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
                            /*try {
                                data.collect { value ->
                                    println("Received $value")
                                }
                            } catch (e: Exception) {
                                println("The flow has thrown an exception: $e")
                            }*/



                    /*data.map { notifications ->
                        if (notifications.size>0)
                        {
                            _resultDB.value=NotificationEntity(Status = "Y", DATA = notifications)
                        }else {
                            _resultDB.value=NotificationEntity(Status = "N", DATA = emptyList())
                        }
                    }*/
                    /*viewModelScope.launch {
                        data.collect { notifications ->
                            _notificationStateFlow.value = notifications
                        }
                    }

                    if(data.collect())
                    {
                        _resultDB.value=NotificationEntity(Status = "Y", DATA = data!!)
                        Log.e("REOS", "NotificationRepository-getNotification-_resultDB.value: " + _resultDB.value)
                    }
                    else {
                        _resultDB.value=NotificationEntity(Status = "N", DATA = emptyList())
                    }*/

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

    /*suspend fun getData(data:Flow<List<Notification>>?)
    {
        try {
            data!!.collect { value ->
                println("Received $value")
            }
        } catch (e: Exception) {
            println("The flow has thrown an exception: $e")
        }
    }*/
}