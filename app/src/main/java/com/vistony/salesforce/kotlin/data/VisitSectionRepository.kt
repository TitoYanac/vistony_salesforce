package com.vistony.salesforce.kotlin.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.vistony.salesforce.kotlin.api.RetrofitApi
import com.vistony.salesforce.kotlin.api.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class VisitSectionRepository {
    private val _list = MutableLiveData<List<VisitSection>>()
    val list: MutableLiveData<List<VisitSection>> = _list

    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status


    suspend fun getVisitSection(
        Imei:String,
        context: Context,
        cliente_id:String,
        domembarque_id:String,
        dateini:String,
        idref:String

    )
    {
        Log.e(
            "REOS",
            "VisitSectionRepository-getVisitSection-Imei"+Imei
        )
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

                    var data=database?.visitSectionDao
                        ?.getVisitSectionList(
                            cliente_id,
                            domembarque_id,
                            dateini,
                            idref
                        )
                    _list.postValue(data)
                    println("Tarea $i completada")
                }


            }
            executor.shutdown()
        }
        catch (e: Exception) {
            Log.e(
                "REOS",
                "VisitSectionRepository-getVisitSection-error: " + e.toString()
            )
        }
    }

    suspend fun addVisitSection(
        list:MutableList<VisitSection>?,
        context: Context
    )
    {
        _status.postValue("N")
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    //Thread.sleep(1000)
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }

                    var data=database?.visitSectionDao
                        ?.addVisitSection(
                            list
                        )
                    _status.postValue("Y")
                    println("Tarea $i completada")
                }


            }
            executor.shutdown()
        }
        catch (e: Exception) {
            Log.e(
                "REOS",
                "VisitSectionRepository-addVisitSection-error: " + e.toString()
            )
        }
    }

    suspend fun updateVisitSection(
        list:List<VisitSection>?,
        context: Context
    )
    {
        _status.postValue("N")
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    //Thread.sleep(1000)
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }

                    var data=database?.visitSectionDao
                        ?.updateVisitSection(
                            list
                        )
                    _status.postValue("Y")
                    println("Tarea $i completada")
                }


            }
            executor.shutdown()
        }
        catch (e: Exception) {
            Log.e(
                "REOS",
                "VisitSectionRepository-updateVisitSection-error: " + e.toString()
            )
        }
    }
}