package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.kotlin.Utilities.CreateListCollectionDetail
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitApi
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitConfig
import com.vistony.salesforce.kotlin.Utilities.getNumberMax
import com.vistony.salesforce.kotlin.Utilities.getSumNumbersReceip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CollectionHeadRepository {
    private val _result_DB = MutableStateFlow(CollectionHeadEntity())
    val result_DB: StateFlow<CollectionHeadEntity> get() = _result_DB

    private val _result_API = MutableStateFlow(CollectionHeadEntity())
    val result_API: StateFlow<CollectionHeadEntity> get() = _result_API

    suspend fun addCollectionHead(context: Context,collectionHead: CollectionHead) {
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    database?.collectionHeadDao?.addCollectionHead(collectionHead)
                    _result_DB.value = CollectionHeadEntity(Status = "Y")
                    println("Tarea $i completada")
                }
            }
            executor.shutdown()
        } catch (e: Exception) {
            _result_DB.value = CollectionHeadEntity(Status = "N")
            Log.e("REOS", "CollectionHeadRepository-addCollectionHead-error: " + e.toString())
        }
    }

    fun sendAPICollectionHead(context: Context) {
        val executor: ExecutorService = Executors.newFixedThreadPool(1)
        for (i in 1..1) {
            executor.execute {
                println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                val data = database?.collectionHeadDao?.getCollectionHead()

                var json: String? = null
                val gson = Gson()
                try {
                    if (!data.isNullOrEmpty()) {
                        json = gson.toJson(data)
                        json = "{ \"Deposits\":$json}"
                    }
                    Log.e("REOS", "CollectionHeadRepository-sendAPICollectionHead-json: $json")
                    if (json != null) {
                        //val jsonRequest: RequestBody = RequestBody.create(json, .parse("application/json; charset=utf-8"))
                        val jsonRequest: RequestBody = RequestBody.create(
                                ("application/json; charset=utf-8").toMediaTypeOrNull(),
                                json
                        )
                        val retrofitConfig: RetrofitConfig? = RetrofitConfig()
                        val service = retrofitConfig?.getClientLog()?.create(
                                RetrofitApi
                                ::class.java
                        )

                        service?.sendDeposit(jsonRequest)?.enqueue(object : Callback<CollectionHeadEntity?> {
                            override fun onResponse(
                                    call: Call<CollectionHeadEntity?>,
                                    response: Response<CollectionHeadEntity?>
                            ) {
                                Log.e("REOS", "CollectionHeadRepository-sendAPICollectionHead-call: $call")
                                val collectionHeadEntity = response.body()
                                Log.e("REOS", "CollectionHeadRepository-sendAPICollectionHead-collectionHeadEntity: $collectionHeadEntity")
                                if (response.isSuccessful && collectionHeadEntity != null) {
                                    val responseData = ArrayList<String>()
                                    Log.e("REOS", "CollectionHeadRepository-sendAPICollectionHead-collectionHeadEntity.data!!:"+collectionHeadEntity.data)
                                    for (respuesta in collectionHeadEntity.data!!) {
                                        var response = "N"
                                        response =
                                                if (respuesta.APICode != null && respuesta.APIErrorCode == "0") {
                                                    //responseData.add("El Recibo fue aceptado en SAP")
                                                    "Y"
                                                } else {
                                                    //responseData.add("El Recibo no fue aceptado en SAP")
                                                    "N"
                                                }
                                        val executor1: ExecutorService =
                                                Executors.newFixedThreadPool(1)
                                        for (i in 1..1) {
                                            Log.e("REOS", "CollectionHeadRepository-sendAPICollectionHead-respuesta.Deposit:"+respuesta.Deposit)
                                            Log.e("REOS", "CollectionHeadRepository-sendAPICollectionHead-SesionEntity.fuerzatrabajo_id:"+SesionEntity.fuerzatrabajo_id)
                                            Log.e("REOS", "CollectionHeadRepository-sendAPICollectionHead-respuesta.APICode!!:"+respuesta.APICode!!)
                                            Log.e("REOS", "CollectionHeadRepository-sendAPICollectionHead-respuesta.APIMessage!!:"+respuesta.APIMessage!!)
                                            Log.e("REOS", "CollectionHeadRepository-sendAPICollectionHead-response:"+response)
                                            executor1.execute {
                                                val data = database?.collectionHeadDao
                                                        ?.updateCollectionHeadAPI(
                                                                respuesta.Deposit,
                                                                SesionEntity.fuerzatrabajo_id,
                                                                respuesta.APICode!!,
                                                                respuesta.APIMessage!!,
                                                                response
                                                        )
                                            }
                                        }
                                        executor1.shutdown()

                                        _result_API.value = CollectionHeadEntity(
                                                Status = response,
                                                data = emptyList()
                                        )
                                        Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-_result_send_API.value:" + _result_API.value)
                                    }
                                }
                            }

                            override fun onFailure(
                                    call: Call<CollectionHeadEntity?>,
                                    t: Throwable
                            ) {
                                Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-ingresoonFailure:" + t.toString())
                                _result_API.value = CollectionHeadEntity(Status = "N", data = emptyList())
                            }
                        })
                    }
                } catch (e: java.lang.Exception) {
                    Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-error: $e")
                }
                println("Tarea $i completada")
            }
        }
        executor.shutdown()
    }

}