package com.vistony.salesforce.kotlin.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.kotlin.api.RetrofitApi
import com.vistony.salesforce.kotlin.api.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ClientRepository {
    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status

    suspend fun getClient(Imei:String,FechaDespacho:String,context: Context)
    {
        Log.e(
            "REOS",
            "ClientRepository-getClient-Imei"+Imei
        )
        try {
            val retrofitConfig: RetrofitConfig? = RetrofitConfig()
            val service = retrofitConfig?.getClientLog()?.create(
                RetrofitApi
                ::class.java
            )

            Log.e(
                "REOS",
                "ClientRepository-getClient-service" + service.toString()
            )
            service?.getClientDelivery(
                Imei,
                FechaDespacho
            )?.enqueue(object : Callback<ClientResponse?> {

                override fun onResponse(
                    call: Call<ClientResponse?>,
                    response: Response<ClientResponse?>
                ) {
                    val clientResponse = response.body()
                    if (response.isSuccessful&&clientResponse?.getClient()?.size!!>0) {
                        for (i in 0..clientResponse?.getClient()?.size!! - 1) {
                            clientResponse?.getClient()!!.get(i).compania_id= SesionEntity.compania_id
                            for (j in 0..clientResponse?.getClient()?.get(i)?.listAddress?.size!! - 1) {
                                clientResponse?.getClient()?.get(i)?.listAddress?.get(j)?.companiaid =SesionEntity.compania_id
                                clientResponse?.getClient()?.get(i)?.listAddress?.get(j)?.cliente_id =
                                    clientResponse?.getClient()?.get(i)?.cliente_id
                            }
                        }

                        val executor: ExecutorService = Executors.newFixedThreadPool(1)
                        executor.execute {
                        for (i in 0..clientResponse?.getClient()?.size!! - 1) {

                                println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                                //Thread.sleep(1000)
                                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                                database?.clientDao
                                    ?.insertClient(
                                        //headerDispatchSheetResponse?.getDispatchSheetEntity()!!
                                        //headerDispatchSheetResponse?.getDispatchSheetEntity()
                                        clientResponse?.getClient()
                                    )
                                for (i in 0..clientResponse?.getClient()?.size!! - 1) {
                                    if(clientResponse?.getClient()
                                            ?.get(i)?.listAddress!=null)
                                    {
                                        database?.addressDao?.insertAddress(
                                            clientResponse?.getClient()
                                                ?.get(i)?.listAddress
                                        )
                                    }

                                    if(clientResponse?.getClient()
                                            ?.get(i)?.listInvoice!=null)
                                    {
                                        database?.invoicesDao?.insertInvoices(
                                            clientResponse?.getClient()
                                                ?.get(i)?.listInvoice
                                        )
                                    }
                                }

                                println("Tarea $i completada")
                            }

                        }

                        executor.shutdown()
                        _status.setValue("1")
                        Log.e(
                            "REOS",
                            "ClientRepository-getClient-response.isSuccessful.status" + _status.getValue()
                        )
                    } else {
                        Log.e(
                            "REOS",
                            "ClientRepository-getClient-response.isSuccessful"
                        )
                        _status.setValue("0")
                        Log.e(
                            "REOS",
                            "ClientRepository-getClient-response.isSuccessful.not.status" + _status.getValue()
                        )
                    }
                    Log.e(
                        "REOS",
                        "ClientRepository-getClient-response.noentroenif.status" + _status.getValue()
                    )

                }

                override fun onFailure(call: Call<ClientResponse?>, t: Throwable) {
                    _status.setValue("0")

                    Log.e(
                        "REOS",
                        "ClientRepository-getClient-error$t"
                    )
                }
            })


        } catch (e: Exception) {
            Log.e(
                "REOS",
                "ClientRepository-getClient-error: " + e.toString()
            )
        }

    }
}