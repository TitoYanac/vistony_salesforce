package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
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

class BankRepository {
    private val _resultAPI = MutableStateFlow(BankEntity())
    val resultAPI: StateFlow<BankEntity> get() = _resultAPI

    private val _resultDB = MutableStateFlow(BankEntity())
    val resultDB: StateFlow<BankEntity> get() = _resultDB

    fun getAddBanks(Imei:String,context: Context)
    {
        try {
            val retrofitConfig: RetrofitConfig? = RetrofitConfig()
            val service = retrofitConfig?.getClientLog()?.create(
                RetrofitApi
                ::class.java
            )
            service?.getBanks(
                Imei
            )?.enqueue(object : Callback<BankEntity?> {
                override fun onResponse(
                    call: Call<BankEntity?>,
                    response: Response<BankEntity?>
                ) {
                    val bankEntity = response.body()
                    if (response.isSuccessful&&bankEntity?.Data?.size!!>0) {
                        val executor: ExecutorService = Executors.newFixedThreadPool(1)
                        for (i in 1..1) {
                            executor.execute {
                                println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                                //Thread.sleep(1000)
                                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                                database?.bankDao
                                    ?.deleteBank()
                                database?.bankDao
                                    ?.addBanks(
                                        bankEntity?.Data
                                    )
                                println("Tarea $i completada")
                            }

                        }

                        executor.shutdown()
                        _resultAPI.value=BankEntity(Status = "Y")
                    } else {
                        _resultAPI.value=BankEntity(Status = "N")
                    }
                }
                override fun onFailure(call: Call<BankEntity?>, t: Throwable) {
                    _resultAPI.value=BankEntity(Status = "N")
                }
            })
        } catch (e: Exception) {
            Log.e(
                "REOS",
                "BankRepository-addBanks-error: " + e.toString()
            )
        }
    }

    suspend fun getBanks(context: Context)
    {
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    var data=database?.bankDao
                        ?.getBank ()
                    Log.e("REOS", "BankRepository-getBanks-data: " + data!!)
                    //Log.e("REOS", "NotificationRepository-getNotification-data.value!!.size: " + data.value!!.size)
                    CoroutineScope(Dispatchers.Default).launch {
                        try {
                            data.collect { value ->
                                Log.e("REOS", "BankRepository-getBanks-value.size: " + value.size)
                                if (value.size>0)
                                {
                                    _resultDB.value=BankEntity(Status = "Y", Data = value)
                                }else {
                                    _resultDB.value=BankEntity(Status = "N", Data = emptyList())
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
                "BankRepository-getBanks-error: " + e.toString()
            )
        }
    }
}