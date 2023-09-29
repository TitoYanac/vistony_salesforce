package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class InvoicesRepository {
    private val _invoices = MutableStateFlow<List<Invoices>>(emptyList())
    val invoices: StateFlow<List<Invoices>> = _invoices

    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status


    suspend fun getInvoices(
        Imei:String,
        context: Context,
        cliente_id:String
    )
    {
        Log.e(
            "REOS",
            "InvoicesRepository-getInvoices-Imei"+Imei
        )
        Log.e(
            "REOS",
            "InvoicesRepository-getInvoices-cliente_id"+cliente_id
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

                    val data=database?.invoicesDao
                        ?.getInvoicesForClient(
                            cliente_id
                        )
                    _invoices.value= data!!
                    Log.e(
                        "REOS",
                        "InvoicesRepository-getInvoices-data.size " + data.size
                    )
                    //_list.postValue(data)
                    println("Tarea $i completada")
                }


            }
            executor.shutdown()
        }
        catch (e: Exception) {
            Log.e(
                "REOS",
                "InvoicesRepository-getInvoices-error: " + e.toString()
            )
        }
    }
}