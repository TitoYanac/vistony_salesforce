package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitApi
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitConfig
import com.vistony.salesforce.kotlin.View.Pages.ApiResponseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _resultDB = MutableStateFlow(HeaderDispatchSheetEntity())
    val resultDB: StateFlow<HeaderDispatchSheetEntity> get() = _resultDB

    suspend fun getStateDispatchSheet(Imei:String,FechaDespacho:String,context:Context)
    {
        try {
            val retrofitConfig: RetrofitConfig? = RetrofitConfig()
            val service = retrofitConfig?.getClientLog()?.create(
                RetrofitApi
                ::class.java
            )
            service?.getHeaderDispatchSheet(
                Imei, FechaDespacho
            )?.enqueue(object : Callback<DispatchSheetResponse?> {

                override fun onResponse(
                    call: Call<DispatchSheetResponse?>,
                    response: Response<DispatchSheetResponse?>
                ) {
                        val headerDispatchSheetResponse = response.body()
                    if (response.isSuccessful&&headerDispatchSheetResponse?.getDispatchSheetEntity()?.size!!>0) {
                        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                        val calendario = GregorianCalendar()
                        for (i in 0..headerDispatchSheetResponse?.getDispatchSheetEntity()?.size!! - 1) {
                            headerDispatchSheetResponse?.getDispatchSheetEntity()!!
                                .get(i).control_id
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
                    } else {
                        _status.setValue("0")
                    }
                }

                override fun onFailure(call: Call<DispatchSheetResponse?>, t: Throwable) {
                    _status.setValue("0")
                }
            })
        } catch (e: Exception) {
            Log.e(
                "REOS",
                "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-error: " + e.toString()
            )
        }
    }


    suspend fun getCodeDispatch(FechaDespacho:String,context:Context)
    {
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    //Thread.sleep(1000)
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    Log.e(
                            "REOS",
                            "HeaderDispatchSheetRepository-getCodeDispatch-FechaDespacho: " + FechaDespacho
                    )
                    val data =  database?.headerDispatchSheetDao
                            ?.getCodeDispatch(
                                    FechaDespacho
                            )
                    Log.e(
                            "REOS",
                            "HeaderDispatchSheetRepository-getCodeDispatch-data.value: " + data!!.value
                    )
                    _resultDB.value=HeaderDispatchSheetEntity(status = "Y", data = data!!.value!!)
                    println("Tarea $i completada")
                }
            }
            executor.shutdown()

        } catch (e: Exception) {
            Log.e(
                    "REOS",
                    "HeaderDispatchSheetRepositorykt-getStateDispatchSheet-error: " + e.toString()
            )
        }
    }
}

