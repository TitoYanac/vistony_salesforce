package com.vistony.salesforce.kotlin.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailDispatchSheetRepository {
    private val _status = MutableLiveData<List<DetailDispatchSheet>>()
    val status: MutableLiveData<List<DetailDispatchSheet>> = _status

    suspend fun getStateDispatchSheet(
        Imei:String,
        FechaDespacho:String,
        context: Context,
        type: String
        )
    //: MutableLiveData<String>
    {
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            /*Log.e(
                "REOS",
                "DetailDispatchSheetRepository-getStateDispatchSheet-FechaDespacho: " + FechaDespacho
            )*/
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    //Thread.sleep(1000)
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }

                    when {
                        type == "F" ->
                        {
                            var data=database?.detailDispatchSheetDao
                            ?.getDetailDispatchSheetforDispatchDateFailed(
                                FechaDespacho
                            )
                            _status.postValue(data)
                        }
                        type == "P" ->
                        {
                            var data=database?.detailDispatchSheetDao
                            ?.getDetailDispatchSheetforDispatchDatePending(
                                FechaDespacho
                            )
                            _status.postValue(data)
                        }
                        type == "E" ->
                        {
                            var data=database?.detailDispatchSheetDao
                                ?.getDetailDispatchSheetforDispatchDateSucessful(
                                    FechaDespacho
                                )
                            _status.postValue(data)
                        }
                    }

                    println("Tarea $i completada")
                }


            }
            executor.shutdown()
        }
        catch (e: Exception) {
            Log.e(
                "REOS",
                "DetailDispatchSheetRepository-getStateDispatchSheet-error: " + e.toString()
            )
        }

    }
}