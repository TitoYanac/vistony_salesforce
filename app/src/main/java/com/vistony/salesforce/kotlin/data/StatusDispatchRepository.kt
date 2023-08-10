package com.vistony.salesforce.kotlin.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.vistony.salesforce.kotlin.api.RetrofitApi
import com.vistony.salesforce.kotlin.api.RetrofitConfig
import com.vistony.salesforce.kotlin.utilities.getBASE64
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StatusDispatchRepository {
    private val _result_update = MutableStateFlow(ResponseStatusDispatch())
    val result_update: StateFlow<ResponseStatusDispatch> get() = _result_update


    suspend fun updateStatusDispatch(
        context: Context,
        checkintime: String?, checkouttime: String?, latitud: String?, longitud: String?, domembarque_id: String?, cliente_id: String?
    )
    {
        Log.e(
            "REOS",
            "InvoicesRepository-getInvoices-cliente_id"+cliente_id
        )
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)

            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    //Thread.sleep(1000)
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }

                    val data=database?.statusDispatchDao
                        ?.updateStatusDispatch(
                            checkintime, checkouttime, latitud, longitud, domembarque_id, cliente_id
                        )
                    _result_update.value=ResponseStatusDispatch (data = null, Status="Y")

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

    fun sendAPIStatusDispatch (context: Context)
    {
        val executor: ExecutorService = Executors.newFixedThreadPool(1)
        for (i in 1..1) {
            executor.execute {
                println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                /*val data=database?.statusDispatchDao
                    ?.getAPIStatusDispatch()*/
                val listHeaderStatusDispatchEntity = ArrayList<SendAPIStatusDispatch>()
                val controlId = database?.statusDispatchDao?.getControlIdWithNullStatusServer()?.DocEntry
                if (!controlId.isNullOrEmpty()) {
                    val sendAPIStatusDispatch:SendAPIStatusDispatch=
                        SendAPIStatusDispatch(DocEntry = controlId, Details = database?.statusDispatchDao?.getDetailListStatusDispatchByControlId(controlId) )
                        //= database?.statusDispatchDao?.getHeaderStatusDispatchByControlId(controlId)
                   /* headerStatusDispatch?.let {
                        it.Details = database?.statusDispatchDao?.getDetailListStatusDispatchByControlId(controlId)
                        listHeaderStatusDispatchEntity.add(it)
                    }*/
                    listHeaderStatusDispatchEntity.add(sendAPIStatusDispatch)
                }

                var json: String? = null
                val gson = Gson()
                try {
                    if (!listHeaderStatusDispatchEntity.isNullOrEmpty()) {
                        //json = gson.toJson(listCollectionDetail)
                        json = gson.toJson(listHeaderStatusDispatchEntity)
                        json = "{ \"Dispatch\":$json}"
                    }
                    Log.e("REOS", "StatusDispatchRepository-sendAPIStatusDispatch-json: $json")
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

                        service?.sendStatusDispatch(
                            jsonRequest
                        )?.enqueue(object : Callback<ResponseSendAPIStatusDispatch?> {
                            override fun onResponse(
                                call: Call<ResponseSendAPIStatusDispatch?>,
                                response: Response<ResponseSendAPIStatusDispatch?>
                            ) {
                                Log.e("REOS", "StatusDispatchRepository-sendAPIStatusDispatch-call: $call")
                                Log.e("REOS", "StatusDispatchRepository-sendAPIStatusDispatch-response: $response")
                                val responseSendAPIStatusDispatch: ResponseSendAPIStatusDispatch? =
                                    response.body()
                                Log.e("REOS", "StatusDispatchRepository-sendAPIStatusDispatch-responseSendAPIStatusDispatch: $responseSendAPIStatusDispatch")
                                if (response.isSuccessful && responseSendAPIStatusDispatch != null) {
                                    val responseData = java.util.ArrayList<String>()
                                    responseData.add("Cargando Lista de Estados de Despachos")
                                    for (respuesta in responseSendAPIStatusDispatch.data!!) {
                                        for (respuestaDetalle in respuesta.Details!!) {
                                            if (respuestaDetalle.haveError == "N") { //se envio
                                                responseData.add("La Lista Estado de Despacho fue aceptado en SAP")
                                                val executor1: ExecutorService = Executors.newFixedThreadPool(1)
                                                for (j in 1..1) {
                                                    executor1.execute {
                                                        database?.statusDispatchDao?.updateStatusAPIDispatch(
                                                            respuesta.DocEntry,
                                                            respuestaDetalle.LineId,
                                                            respuestaDetalle.Message
                                                        )
                                                    }
                                                }
                                                executor1.shutdown()
                                            } else { //tiene error
                                                responseData.add("La Lista Estado de Despacho no fue aceptado en SAP")
                                            }
                                        }
                                    }
                                } else {

                                }
                            }
                            override fun onFailure(call: Call<ResponseSendAPIStatusDispatch?>, t: Throwable) {

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

    fun sendPhotoAPIStatusDispatch (context: Context)
    {
        val executor: ExecutorService = Executors.newFixedThreadPool(1)
        for (i in 1..1) {
            executor.execute {
                println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                /*val data=database?.statusDispatchDao
                    ?.getAPIStatusDispatch()*/
                val listHeaderStatusDispatchEntity = ArrayList<SendAPIStatusDispatch>()
                val controlId = database?.statusDispatchDao?.getControlIdWithNullPhotoStatusServer()?.DocEntry
                if (!controlId.isNullOrEmpty()) {

                    var listDetailStatusDispatchEntity :List<StatusDispatch> = database?.statusDispatchDao?.getDetailListPhotoStatusDispatchByControlId(controlId)!!
                    for (i in 0 until listDetailStatusDispatchEntity.size)
                    {
                        var encoded:String=""
                        var encoded2:String=""
                        encoded= getBASE64(listDetailStatusDispatchEntity.get(i).PhotoStore!!)!!
                        encoded2= getBASE64(listDetailStatusDispatchEntity.get(i).PhotoDocument!!)!!
                        if (encoded == "") {
                            listDetailStatusDispatchEntity.get(i).PhotoStore=encoded
                        } else {
                            val Base64PhotoLocal: String = encoded.replace("\n", "")
                            val Base64PhotoLocal2 = Base64PhotoLocal.replace("'\u003d'", "=")
                            listDetailStatusDispatchEntity.get(i).PhotoStore=Base64PhotoLocal2
                        }
                        if (encoded2 == "") {
                            listDetailStatusDispatchEntity.get(i).PhotoDocument=encoded2
                        } else {
                            val Base64PhotoDocument: String = encoded2.replace("\n", "")
                            val Base64PhotoDocument2 = Base64PhotoDocument.replace("'\u003d'", "=")
                            listDetailStatusDispatchEntity.get(i).PhotoDocument=Base64PhotoDocument2
                        }
                    }
                    val sendAPIStatusDispatch:SendAPIStatusDispatch=
                        SendAPIStatusDispatch(DocEntry = controlId,
                            Details = listDetailStatusDispatchEntity )

                    listHeaderStatusDispatchEntity.add(sendAPIStatusDispatch)
                }

                var json: String? = null
                val gson = Gson()
                try {
                    if (!listHeaderStatusDispatchEntity.isNullOrEmpty()) {
                        //json = gson.toJson(listCollectionDetail)
                        json = gson.toJson(listHeaderStatusDispatchEntity)
                        json = "{ \"Dispatch\":$json}"
                    }
                    Log.e("REOS", "StatusDispatchRepository-sendPhotoAPIStatusDispatch-json: $json")
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

                        service?.sendStatusDispatch(
                            jsonRequest
                        )?.enqueue(object : Callback<ResponseSendAPIStatusDispatch?> {
                            override fun onResponse(
                                call: Call<ResponseSendAPIStatusDispatch?>,
                                response: Response<ResponseSendAPIStatusDispatch?>
                            ) {
                                Log.e("REOS", "StatusDispatchRepository-sendPhotoAPIStatusDispatch-call: $call")
                                Log.e("REOS", "StatusDispatchRepository-sendPhotoAPIStatusDispatch-response: $response")
                                val responseSendAPIStatusDispatch: ResponseSendAPIStatusDispatch? =
                                    response.body()
                                Log.e("REOS", "StatusDispatchRepository-sendPhotoAPIStatusDispatch-responseSendAPIStatusDispatch: $responseSendAPIStatusDispatch")
                                if (response.isSuccessful && responseSendAPIStatusDispatch != null) {
                                    val responseData = java.util.ArrayList<String>()
                                    responseData.add("Cargando Lista de Estados de Despachos")
                                    for (respuesta in responseSendAPIStatusDispatch.data!!) {
                                        for (respuestaDetalle in respuesta.Details!!) {
                                            if (respuestaDetalle.haveError == "N") { //se envio
                                                responseData.add("La Lista Estado de Despacho fue aceptado en SAP")
                                                val executor1: ExecutorService = Executors.newFixedThreadPool(1)
                                                for (j in 1..1) {
                                                    executor1.execute {
                                                        database?.statusDispatchDao?.updatePhotoStatusAPIDispatch(
                                                            respuesta.DocEntry,
                                                            respuestaDetalle.LineId,
                                                            respuestaDetalle.Message
                                                        )
                                                    }
                                                }
                                                executor1.shutdown()
                                            } else { //tiene error
                                                responseData.add("La Lista Estado de Despacho no fue aceptado en SAP")
                                            }
                                        }
                                    }
                                } else {

                                }
                            }
                            override fun onFailure(call: Call<ResponseSendAPIStatusDispatch?>, t: Throwable) {

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