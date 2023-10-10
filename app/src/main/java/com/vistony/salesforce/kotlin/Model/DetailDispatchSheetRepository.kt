package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailDispatchSheetRepository {
    private val _resultDB = MutableStateFlow(DetailDispatchSheetEntity())
    val resultDB: StateFlow<DetailDispatchSheetEntity> get() = _resultDB

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
                                ?.getDetailDispatchSheetforDateStatus(
                                    FechaDespacho, "A","V")

                            CoroutineScope(Dispatchers.Default).launch {
                                try {
                                    data!!.collect { value ->
                                        _resultDB.value=DetailDispatchSheetEntity(Status="Y", DATA = value!!)
                                    }
                                } catch (e: Exception) {
                                    println("The flow has thrown an exception: $e")
                                }
                            }

                        }
                        type == "P" ->
                        {
                            var data=database?.detailDispatchSheetDao
                                ?.getDetailDispatchSheetforDateStatus(
                                    FechaDespacho, "S","P")
                            CoroutineScope(Dispatchers.Default).launch {
                                try {
                                    data!!.collect { value ->
                                        _resultDB.value=DetailDispatchSheetEntity(Status="Y", DATA = value!!)
                                    }
                                } catch (e: Exception) {
                                    println("The flow has thrown an exception: $e")
                                }
                            }
                        }
                        type == "E" ->
                        {
                            var data=database?.detailDispatchSheetDao
                                ?.getDetailDispatchSheetforDateStatus(
                                    FechaDespacho, "E","")
                            CoroutineScope(Dispatchers.Default).launch {
                                try {
                                    data!!.collect { value ->
                                        _resultDB.value=DetailDispatchSheetEntity(Status="Y", DATA = value!!)
                                    }
                                } catch (e: Exception) {
                                    println("The flow has thrown an exception: $e")
                                }
                            }
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