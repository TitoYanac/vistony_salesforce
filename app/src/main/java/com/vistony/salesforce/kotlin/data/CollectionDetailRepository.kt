package com.vistony.salesforce.kotlin.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.vistony.salesforce.Controller.Retrofit.Api
import com.vistony.salesforce.Controller.Retrofit.Config
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.Entity.Retrofit.Modelo.CobranzaDetalleEntity
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.kotlin.api.RetrofitApi
import com.vistony.salesforce.kotlin.api.RetrofitConfig
import com.vistony.salesforce.kotlin.utilities.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CollectionDetailRepository {
    private val _result_add = MutableStateFlow(ResponseCollectionDetail())
    val result_add: StateFlow<ResponseCollectionDetail> get() = _result_add

    private val _result_collection_unit = MutableStateFlow(ResponseCollectionDetail())
    val result_collection_unit: StateFlow<ResponseCollectionDetail> get() = _result_collection_unit

    private val _result_send_API = MutableStateFlow(ResponseCollectionDetail())
    val result_send_API: StateFlow<ResponseCollectionDetail> get() = _result_send_API

    suspend fun addListCollectionDetail(
        Imei:String,
        context: Context,
        invoices:Invoices?,
        newBalance:String,
        collection:String,
        typeCollection:String,
        company:String,
        userid:String,
        cardName:String,
        commentary:String
    )
    {
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    var ultimocorrelativorecibo:Int=0
                    var ObjUsuario = UsuarioSQLiteEntity()
                    val usuarioSQLite = UsuarioSQLite(context)
                    ObjUsuario = usuarioSQLite.ObtenerUsuarioSesion()

                    var lastreceip=database?.collectionDetailDao
                        ?.getLastReceip(
                            company,
                            userid
                        )
                    ultimocorrelativorecibo=
                        getSumNumbersReceip(getNumberMax(lastreceip,ObjUsuario.recibo),"1")!!.toInt()
                    database?.collectionDetailDao
                        ?.addListCollectionDetail(
                            CreateListCollectionDetail(
                                context,
                                invoices,
                                newBalance,
                                collection,
                                typeCollection,
                                cardName,
                                commentary,
                                ultimocorrelativorecibo.toString()
                            )
                        )
                    val data=database?.collectionDetailDao
                    ?.getCollectionDetailUnit(
                        ultimocorrelativorecibo.toString(),
                        SesionEntity.usuario_id
                    )
                    _result_add.value=ResponseCollectionDetail(data = data, Status="Y")
                    Log.e(
                        "REOS",
                        "CollectionDetailRepository-addListCollectionDetail-_result_add.value.Status: " + _result_add.value.Status
                    )
                    println("Tarea $i completada")
                }
            }
            executor.shutdown()
        }
        catch (e: Exception) {
            _result_add.value.Status="N"
            Log.e(
                "REOS",
                "CollectionDetailRepository-addListCollectionDetail-error: " + e.toString()
            )
        }
    }

    suspend fun getCollectionDetailUnit(
    Receip:String,
    UserID:String,
    context:Context
    )
    {
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    val data=database?.collectionDetailDao
                        ?.getCollectionDetailUnit(
                            Receip,
                            UserID
                        )
                    _result_collection_unit.value=ResponseCollectionDetail(data = data, Status = "Y")
                    Log.e(
                        "REOS",
                        "CollectionDetailRepository-getCollectionDetailUnit-_result_collection_unit.value.Status: " + _result_collection_unit.value.Status
                    )
                    println("Tarea $i completada")
                }
            }
            executor.shutdown()
        }
        catch (e: Exception) {
            _result_add.value.Status="N"
            Log.e(
                "REOS",
                "CollectionDetailRepository-addListCollectionDetail-error: " + e.toString()
            )
        }
    }


    fun SendAPICollectionDetail(context: Context,CompanyCode:String,UserID: String)
    {
        val executor: ExecutorService = Executors.newFixedThreadPool(1)
        for (i in 1..1) {
            executor.execute {
                println("Tarea $i en ejecución en ${Thread.currentThread().name}")
            val database by lazy { AppDatabase.getInstance(context.applicationContext) }
            val data=database?.collectionDetailDao
                ?.getCollectionDetailSendAPIList(
                    CompanyCode,
                    UserID
                )
            var json: String? = null
            val gson = Gson()
            try {
                if (!data.isNullOrEmpty()) {
                    //json = gson.toJson(listCollectionDetail)
                    json = gson.toJson(data)
                    json = "{ \"Collections\":$json}"
                }
                Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-json: $json")
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

                    service?.sendCollection(
                        jsonRequest
                    )?.enqueue(object : Callback<ResponseCollectionDetail?> {
                        override fun onResponse(
                            call: Call<ResponseCollectionDetail?>,
                            response: Response<ResponseCollectionDetail?>
                        ) {
                            Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-ingresoOnResponse")
                            val cobranzaDetalleEntity = response.body()
                            Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-response.isSuccessful"+response.isSuccessful)
                            Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-cobranzaDetalleEntity: "+cobranzaDetalleEntity)

                            if (response.isSuccessful && cobranzaDetalleEntity != null) {
                                Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-response.isSuccessful")
                                val responseData = ArrayList<String>()
                                for (respuesta in cobranzaDetalleEntity.data!!) {
                                    Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail- for")
                                    var response = "N"
                                    response =
                                        if (respuesta.APICode != null && respuesta.APIErrorCode == "0") {
                                            //responseData.add("El Recibo fue aceptado en SAP")
                                            "Y"
                                        } else {
                                            //responseData.add("El Recibo no fue aceptado en SAP")
                                            "N"
                                        }
                                    val executor1: ExecutorService = Executors.newFixedThreadPool(1)
                                    for (i in 1..1) {
                                        executor1.execute {
                                            val data = database?.collectionDetailDao
                                                ?.updateCollectionDetailAPI(
                                                    respuesta.Receip.toString(),
                                                    UserID,
                                                    respuesta.APICode,
                                                    respuesta.APIMessage,
                                                    response
                                                )
                                        }
                                    }
                                    executor1.shutdown()

                                    _result_send_API.value=ResponseCollectionDetail(Status = response, data = null)
                                    Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-_result_send_API.value:"+_result_send_API.value)
                                }
                            }
                        }
                        override fun onFailure(call: Call<ResponseCollectionDetail?>, t: Throwable) {
                            Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-ingresoonFailure:"+t.toString())
                            _result_send_API.value=ResponseCollectionDetail(Status = "N", data = null)
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