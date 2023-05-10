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

class TypeDispatchRepository {
    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status

    suspend fun getTypeDispatch(Imei:String,context: Context)
    {
        Log.e(
            "REOS",
            "TypeDispatchRepository-getTypeDispatch-Imei"+Imei
        )

        try {
            val retrofitConfig: RetrofitConfig? = RetrofitConfig()
            val service = retrofitConfig?.getClientLog()?.create(
                RetrofitApi
                ::class.java
            )

            Log.e(
                "REOS",
                "TypeDispatchRepository-getTypeDispatch-service" + service.toString()
            )
            service?.getTypeDispatch(
                Imei
            )?.enqueue(object : Callback<TypeDispatchResponse?> {

                override fun onResponse(
                    call: Call<TypeDispatchResponse?>,
                    response: Response<TypeDispatchResponse?>
                ) {
                    Log.e(
                        "REOS",
                        "TypeDispatchRepository-getTypeDispatch-call$call"
                    )

                    Log.e(
                        "REOS",
                        "TypeDispatchRepository-getTypeDispatch-response$response"
                    )

                    Log.e(
                        "REOS",
                        "TypeDispatchRepository-getTypeDispatch-response.isSuccessful"
                    )
                    val typeDispatchResponse = response.body()
                    Log.e(
                        "REOS",
                        "TypeDispatchRepository?.getTypeDispatch()?.size" + typeDispatchResponse?.getTypeDispatch()?.size
                    )

                    if (response.isSuccessful&&typeDispatchResponse?.getTypeDispatch()?.size!!>0) {
                        val executor: ExecutorService = Executors.newFixedThreadPool(1)
                        for (i in 1..1) {
                            executor.execute {
                                println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                                //Thread.sleep(1000)
                                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                                database?.typeDispatchDao
                                    ?.deleteTypeDispatch()
                                database?.typeDispatchDao
                                    ?.inserTypeDispatch(
                                        typeDispatchResponse?.getTypeDispatch()
                                    )

                                println("Tarea $i completada")
                            }

                        }

                        executor.shutdown()
                        _status.setValue("1")
                        Log.e(
                            "REOS",
                            "TypeDispatchRepository-getTypeDispatch-response.isSuccessful.status" + _status.getValue()
                        )
                    } else {
                        Log.e(
                            "REOS",
                            "TypeDispatchRepository-getTypeDispatch-response.isSuccessful"
                        )
                        _status.setValue("0")
                        Log.e(
                            "REOS",
                            "TypeDispatchRepository-getTypeDispatch-response.isSuccessful.not.status" + _status.getValue()
                        )
                    }
                    Log.e(
                        "REOS",
                        "TypeDispatchRepository-getTypeDispatch-response.noentroenif.status" + _status.getValue()
                    )

                }

                override fun onFailure(call: Call<TypeDispatchResponse?>, t: Throwable) {
                    _status.setValue("0")

                    Log.e(
                        "REOS",
                        "TypeDispatchRepository-getTypeDispatch-error$t"
                    )
                }
            })


        } catch (e: Exception) {
            Log.e(
                "REOS",
                "TypeDispatchRepository-getTypeDispatch-error: " + e.toString()
            )
        }

    }

}