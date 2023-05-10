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

/*
interface HeaderDispatchSheetRepositorykt {

    suspend fun getStatusDispatchSheet(Imei: String,FechaDespacho: String,context: Context): MutableLiveData<String>
}*/


class HeaderDispatchSheetRepository(
    )
{
    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status

    suspend fun getStateDispatchSheet(Imei:String,FechaDespacho:String,context:Context)
    //: MutableLiveData<String>
    {
        //_status.value =dispatchRepository.getDispatch(Imei, FechaDespacho)!!
        // _dispatch.value = result
        Log.e(
            "REOS",
            "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-Imei"+Imei
        )
        Log.e(
            "REOS",
            "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-FechaDespacho"+FechaDespacho
        )
        try {
            val retrofitConfig: RetrofitConfig? = RetrofitConfig()
            val service = retrofitConfig?.getClientLog()?.create(
                RetrofitApi
                ::class.java
            )

            Log.e(
                "REOS",
                "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-service" + service.toString()
            )
            service?.getHeaderDispatchSheet(
                Imei, FechaDespacho
            )?.enqueue(object : Callback<DispatchSheetResponse?> {

                override fun onResponse(
                    call: Call<DispatchSheetResponse?>,
                    response: Response<DispatchSheetResponse?>
                ) {
                    Log.e(
                        "REOS",
                        "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-call$call"
                    )

                    Log.e(
                        "REOS",
                        "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-response$response"
                    )

                        Log.e(
                            "REOS",
                            "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-response.isSuccessful"
                        )
                        val headerDispatchSheetResponse = response.body()
                        //headerDispatchSheetResponse?.getDispatchSheetEntity()?.size
                        Log.e(
                            "REOS",
                            "HeaderDispatchSheetRepositorykt?.getStateDispatchSheet()?.size" + headerDispatchSheetResponse?.getDispatchSheetEntity()?.size
                        )

                    if (response.isSuccessful&&headerDispatchSheetResponse?.getDispatchSheetEntity()?.size!!>0) {
                        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                        val calendario = GregorianCalendar()
                        for (i in 0..headerDispatchSheetResponse?.getDispatchSheetEntity()?.size!! - 1) {
                            headerDispatchSheetResponse?.getDispatchSheetEntity()!!
                                .get(i).control_id
                            Log.e(
                                "REOS",
                                "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-headerDispatchSheetResponse?.getDispatchSheetEntity()?.get(i).docEntry" + headerDispatchSheetResponse?.getDispatchSheetEntity()!!
                                    .get(i).control_id
                            )
                            headerDispatchSheetResponse?.getDispatchSheetEntity()!!
                                .get(i).compania_id=SesionEntity.compania_id
                            headerDispatchSheetResponse?.getDispatchSheetEntity()!!
                                .get(i).fuerzatrabajo_id=SesionEntity.fuerzatrabajo_id
                            headerDispatchSheetResponse?.getDispatchSheetEntity()!!
                                .get(i).usuario_id=SesionEntity.usuario_id
                            headerDispatchSheetResponse?.getDispatchSheetEntity()!!
                                .get(i).fechahojadespacho=FechaDespacho
                            headerDispatchSheetResponse?.getDispatchSheetEntity()!!
                                .get(i).Datetimeregister=sdf.format(calendario.getTime())
                            for (j in 0..headerDispatchSheetResponse?.getDispatchSheetEntity()
                                ?.get(i)?.details?.size!! - 1) {
                                Log.e(
                                    "REOS",
                                    "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-headerDispatchSheetResponse?.getDispatchSheetEntity()?.get(i)?.details!!.get(j).invoiceNum" +
                                            headerDispatchSheetResponse?.getDispatchSheetEntity()
                                                ?.get(i)?.details!!.get(j).factura_id
                                )
                                headerDispatchSheetResponse?.getDispatchSheetEntity()
                                    ?.get(i)?.details?.get(j)?.control_id=headerDispatchSheetResponse?.getDispatchSheetEntity()!!
                                    .get(i).control_id
                                headerDispatchSheetResponse?.getDispatchSheetEntity()
                                    ?.get(i)?.details?.get(j)?.compania_id=SesionEntity.compania_id
                                headerDispatchSheetResponse?.getDispatchSheetEntity()
                                    ?.get(i)?.details?.get(j)?.fuerzatrabajo_id=SesionEntity.fuerzatrabajo_id
                                headerDispatchSheetResponse?.getDispatchSheetEntity()
                                    ?.get(i)?.details?.get(j)?.usuario_id=SesionEntity.usuario_id

                            }

                        }
                        val executor: ExecutorService = Executors.newFixedThreadPool(1)

                        for (i in 1..1) {
                            executor.execute {
                                println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                                //Thread.sleep(1000)
                                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                                database?.headerDispatchSheetDao
                                    ?.deleteHeaderDispatchSheet(
                                        FechaDespacho
                                    )
                                database?.headerDispatchSheetDao
                                    ?.insertHeaderDispatchSheet(
                                        headerDispatchSheetResponse?.getDispatchSheetEntity()
                                    )
                                for (i in 0..headerDispatchSheetResponse?.getDispatchSheetEntity()?.size!! - 1) {
                                    database?.detailDispatchSheetDao
                                        ?.deleteDetailDispatchSheet(
                                            headerDispatchSheetResponse?.getDispatchSheetEntity()!!
                                                .get(i).control_id
                                        )
                                    database?.detailDispatchSheetDao?.insertDetailDispatchSheet(
                                        headerDispatchSheetResponse?.getDispatchSheetEntity()
                                            ?.get(i)?.details
                                    )
                                }

                                println("Tarea $i completada")
                            }

                        }

                        executor.shutdown()
                        _status.setValue("1")
                        /*Thread {
                            //Do your database´s operations here
                            val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                            database?.headerDispatchSheetDao
                                ?.insertHeaderDispatchSheet(
                                    //headerDispatchSheetResponse?.getDispatchSheetEntity()!!
                                    headerDispatchSheetResponse?.getDispatchSheetEntity()
                                )
                            for (i in 0..headerDispatchSheetResponse?.getDispatchSheetEntity()?.size!! - 1) {
                                database?.detailDispatchSheetDao?.insertDetailDispatchSheet(
                                    headerDispatchSheetResponse?.getDispatchSheetEntity()
                                        ?.get(i)?.details
                                )
                            }

                        }.start()*/


                        Log.e(
                            "REOS",
                            "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-response.isSuccessful.status" + _status.getValue()
                        )
                    } else {
                        Log.e(
                            "REOS",
                            "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-response.isSuccessful"
                        )
                        _status.setValue("0")
                        Log.e(
                            "REOS",
                            "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-response.isSuccessful.not.status" + _status.getValue()
                        )
                    }
                    Log.e(
                        "REOS",
                        "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-response.noentroenif.status" + _status.getValue()
                    )

                }

                override fun onFailure(call: Call<DispatchSheetResponse?>, t: Throwable) {
                    _status.setValue("0")

                    Log.e(
                        "REOS",
                        "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-error$t"
                    )
                }
            })


        } catch (e: Exception) {
            Log.e(
                "REOS",
                "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-error: " + e.toString()
            )
        }

    }
}

