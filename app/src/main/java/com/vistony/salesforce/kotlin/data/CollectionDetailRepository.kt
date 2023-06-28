package com.vistony.salesforce.kotlin.data

import android.content.Context
import android.util.Log
import com.vistony.salesforce.kotlin.utilities.CreateListCollectionDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CollectionDetailRepository {
    private val _result_add = MutableStateFlow(Response())
    val result_add: StateFlow<Response> get() = _result_add

    suspend fun addListCollectionDetail(
        Imei:String,
        context: Context,
        invoices:Invoices,
        newBalance:String,
        collection:String,
        typeCollection:String,
        company:String,
        userid:String,
        cardName:String,
        commentary:String
    )
    {
        Log.e(
            "REOS",
            "CollectionDetailRepository-addListCollectionDetail-invoices"+invoices
        )
        Log.e(
            "REOS",
            "CollectionDetailRepository-addListCollectionDetail-newBalance"+newBalance
        )
        Log.e(
            "REOS",
            "CollectionDetailRepository-addListCollectionDetail-collection"+collection
        )
        Log.e(
            "REOS",
            "CollectionDetailRepository-addListCollectionDetail-typeCollection"+typeCollection
        )
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    //Thread.sleep(1000)
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    val lastreceip=database?.collectionDetailDao
                        ?.getLastReceip(
                            company,
                            userid
                        )
                    val data=database?.collectionDetailDao
                        ?.addListCollectionDetail(
                            CreateListCollectionDetail(
                                context,
                                invoices,
                                newBalance,
                                collection,
                                typeCollection,
                                cardName,
                                commentary,
                                lastreceip
                            )
                        )
                    _result_add.value.Status="Y"
                    /*_invoices.value= data!!
                    Log.e(
                        "REOS",
                        "InvoicesRepository-getInvoices-data.size " + data.size
                    )*/

                    //_list.postValue(data)
                    println("Tarea $i completada")
                }
            }

            executor.shutdown()
        }
        catch (e: Exception) {
            _result_add.value.Status="N"
            Log.e(
                "REOS",
                "InvoicesRepository-getInvoices-error: " + e.toString()
            )
        }
    }
}