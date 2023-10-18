package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitApi
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitConfig
import com.vistony.salesforce.kotlin.Utilities.getDateTimeCurrent
import com.vistony.salesforce.kotlin.View.Pages.ApiResponse
import com.vistony.salesforce.kotlin.View.Pages.ApiResponseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FormularioTestRepository {
    private val _resultDB = MutableStateFlow(ApiResponseEntity())
    val resultDB: StateFlow<ApiResponseEntity> get() = _resultDB

    suspend fun addFormularioTest(context: Context,apiResponse: ApiResponse)
    {
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    var numInforme=apiResponse.datosPrincipales!!.numInforme!!
                    apiResponse.numInforme= numInforme
                    apiResponse.datosVisita!!.numInforme=numInforme
                    apiResponse.dateregister= getDateTimeCurrent()!!
                    for (i in 0 until apiResponse.datosPrincipales!!.tipoSalida!!.size)
                    {
                        apiResponse.datosPrincipales!!.tipoSalida!!.get(i).numInforme=numInforme
                    }
                    for (i in 0 until apiResponse.datosVisita!!.resumen.size)
                    {
                        apiResponse.datosVisita!!.resumen!!.get(i).numInforme=numInforme
                    }
                    for (i in 0 until apiResponse.formulario!!.size)
                    {
                        apiResponse.formulario!!.get(i).numInforme=numInforme
                    }

                    var data=database?.formularioTestDao?.addFormSuperviser (apiResponse)
                    var data1=database?.formularioTestDao?.addDatosPrincipales (apiResponse.datosPrincipales!!)
                    var data2=database?.formularioTestDao?.addDatosVisita (apiResponse.datosVisita)
                    var data3=database?.formularioTestDao?.addTipoSalida (apiResponse.datosPrincipales!!.tipoSalida)
                    var data4=database?.formularioTestDao?.addResumenVisita (apiResponse.datosVisita!!.resumen)
                    var data5=database?.formularioTestDao?.addPreguntaRespuesta (apiResponse.formulario)

                    _resultDB.value=ApiResponseEntity(StatusCode = "Y")
                    println("Tarea $i completada")
                }

            }
            executor.shutdown()
        } catch (e: Exception) {
            _resultDB.value=ApiResponseEntity(StatusCode = "N")
            Log.e("REOS", "FormularioTestRepository-addFormularioTest-error: " + e.toString())
        }
    }

    suspend fun sendFormularioTest(context: Context)
    {
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    var data=database?.formularioTestDao?.getFormSuperviser ()
                    Log.e("REOS", "FormularioTestRepository-sendFormularioTest-data: " + data)
                    data!!.datosPrincipales=database?.formularioTestDao!!.getDatosPrincipales(data!!.numInforme)
                    Log.e("REOS", "FormularioTestRepository-sendFormularioTest-data: " + data)
                    data!!.datosPrincipales!!.tipoSalida=database?.formularioTestDao!!.getTipoSalida(data!!.numInforme)
                    Log.e("REOS", "FormularioTestRepository-sendFormularioTest-data: " + data)
                    data!!.datosVisita=database?.formularioTestDao!!.getDatosVisita(data!!.numInforme)
                    Log.e("REOS", "FormularioTestRepository-sendFormularioTest-data: " + data)
                    data!!.datosVisita!!.resumen=database?.formularioTestDao!!.getResumenVisita(data!!.numInforme)
                    Log.e("REOS", "FormularioTestRepository-sendFormularioTest-data: " + data)
                    data!!.formulario=database?.formularioTestDao!!.getPreguntaRespuesta(data!!.numInforme)
                    //var data1=database?.formularioTestDao?.addDatosPrincipales (apiResponse.datosPrincipales)
                    //var data2=database?.formularioTestDao?.addDatosVisita (apiResponse.datosVisita)
                    //var data3=database?.formularioTestDao?.addTipoSalida (apiResponse.datosPrincipales.tipoSalida)
                    //var data4=database?.formularioTestDao?.addResumenVisita (apiResponse.datosVisita!!.resumen)
                    //var data5=database?.formularioTestDao?.addPreguntaRespuesta (apiResponse.formulario)
                    Log.e("REOS", "FormularioTestRepository-sendFormularioTest-data: " + data)


                    var json: String? = null
                    val gson = Gson()
                    try {
                        if (data!=null) {
                            json = gson.toJson(data)
                        }
                        Log.e("REOS", "FormularioTestRepository-sendFormularioTest-json: $json")
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

                            service?.sendFormSupervisor(
                                    jsonRequest
                            )?.enqueue(object : Callback<ApiResponseEntity?> {
                                override fun onResponse(
                                        call: Call<ApiResponseEntity?>,
                                        response: Response<ApiResponseEntity?>
                                ) {
                                    val responseFormSupervisor = response.body()
                                    if (response.isSuccessful && responseFormSupervisor != null) {
                                        var code="";
                                        var numinforme="";
                                        var message="";
                                        var status="";

                                        if (responseFormSupervisor.StatusCode == "201") {
                                            status="Y"
                                        } else {
                                            status="N"
                                        }
                                        code=responseFormSupervisor.Data!!.code
                                        message=responseFormSupervisor.Data!!.message
                                        numinforme=responseFormSupervisor.Data!!.num_informe
                                        val executor1: ExecutorService = Executors.newFixedThreadPool(1)

                                        for (i in 1..1) {
                                            executor1.execute {
                                                database?.formularioTestDao?.updateStatusFormSupervisor(code, message, status, numinforme)
                                            }
                                        }

                                        executor1.shutdown()
                                        Log.e("REOS", "FormularioTestRepository-sendFormularioTest-responseFormSupervisor: $responseFormSupervisor")
                                       // for (respuesta in responseFormSupervisor!!.) {
                                            /*var response = "N"
                                            response =
                                                    if (respuesta.APICode != null && respuesta.APIErrorCode == "0") {
                                                        "Y"
                                                    } else {
                                                        "N"
                                                    }
                                            val executor1: ExecutorService =
                                                    Executors.newFixedThreadPool(1)
                                            for (i in 1..1) {
                                                executor1.execute {
                                                    val data = database?.collectionDetailDao
                                                            ?.updateCollectionDetailAPI(
                                                                    respuesta.Receip.toString(),
                                                                    UserID,
                                                                    respuesta.APICode!!,
                                                                    respuesta.APIMessage!!,
                                                                    response
                                                            )
                                                }
                                            }
                                            executor1.shutdown()

                                            _result_send_API.value = CollectionDetailEntity(
                                                    Status = response,
                                                    data = emptyList()
                                            )
                                            Log.e(
                                                    "REOS",
                                                    "CollectionDetailRepository-SendAPICollectionDetail-_result_send_API.value:" + _result_send_API.value
                                            )
                                        */
                                       // }
                                    }
                                }

                                override fun onFailure(
                                        call: Call<ApiResponseEntity?>,
                                        t: Throwable
                                ) {
                                    Log.e("REOS", "FormularioTestRepository-sendFormularioTest-ingresoonFailure:"+ t.toString())
                                    //Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-ingresoonFailure:" + t.toString())
                                    //_result_send_API.value = CollectionDetailEntity(Status = "N", data = emptyList())
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
        } catch (e: Exception) {
            Log.e("REOS", "FormularioTestRepository-sendFormularioTest-error: " + e.toString())
        }
    }
}