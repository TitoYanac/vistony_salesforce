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

class ReasonDispatchRepository {
    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status

    suspend fun getReasonDispatch(Imei:String,context: Context)
    {
        Log.e(
            "REOS",
            "ReasonDispatchRepository-getReasonDispatch-Imei"+Imei
        )

        try {
            val retrofitConfig: RetrofitConfig? = RetrofitConfig()
            val service = retrofitConfig?.getClientLog()?.create(
                RetrofitApi
                ::class.java
            )

            Log.e(
                "REOS",
                "ReasonDispatchRepository-getReasonDispatch-service" + service.toString()
            )
            service?.getReasonDispatch(
                Imei
            )?.enqueue(object : Callback<ReasonDispatchResponse?> {

                override fun onResponse(
                    call: Call<ReasonDispatchResponse?>,
                    response: Response<ReasonDispatchResponse?>
                ) {
                    Log.e(
                        "REOS",
                        "ReasonDispatchRepository-getReasonDispatch-call$call"
                    )

                    Log.e(
                        "REOS",
                        "ReasonDispatchRepository-getReasonDispatch-response$response"
                    )

                    Log.e(
                        "REOS",
                        "ReasonDispatchRepository-getReasonDispatch-response.isSuccessful"
                    )
                    val reasonDispatchResponse = response.body()
                    Log.e(
                        "REOS",
                        "ReasonDispatchRepository?.getReasonDispatch()?.size" + reasonDispatchResponse?.getReasonDispatch()?.size
                    )

                    if (response.isSuccessful&&reasonDispatchResponse?.getReasonDispatch()?.size!!>0) {
                        val executor: ExecutorService = Executors.newFixedThreadPool(1)
                        for (i in 1..1) {
                            executor.execute {
                                println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                                //Thread.sleep(1000)
                                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                                database?.reasonDispatchDao
                                    ?.deleteReasonDispatch()
                                database?.reasonDispatchDao
                                    ?.inserReasonDispatch (
                                        reasonDispatchResponse?.getReasonDispatch()
                                    )

                                println("Tarea $i completada")
                            }

                        }

                        executor.shutdown()
                        _status.setValue("1")
                        Log.e(
                            "REOS",
                            "ReasonDispatchRepository-getReasonDispatch-response.isSuccessful.status" + _status.getValue()
                        )
                    } else {
                        Log.e(
                            "REOS",
                            "ReasonDispatchRepository-getReasonDispatch-response.isSuccessful"
                        )
                        _status.setValue("0")
                        Log.e(
                            "REOS",
                            "ReasonDispatchRepository-getReasonDispatch-response.isSuccessful.not.status" + _status.getValue()
                        )
                    }
                    Log.e(
                        "REOS",
                        "ReasonDispatchRepository-getReasonDispatch-response.noentroenif.status" + _status.getValue()
                    )

                }

                override fun onFailure(call: Call<ReasonDispatchResponse?>, t: Throwable) {
                    _status.setValue("0")

                    Log.e(
                        "REOS",
                        "ReasonDispatchRepository-getReasonDispatch-error$t"
                    )
                }
            })


        } catch (e: Exception) {
            Log.e(
                "REOS",
                "ReasonDispatchRepository-getReasonDispatch-error: " + e.toString()
            )
        }

    }

}