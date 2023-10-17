package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import com.vistony.salesforce.kotlin.View.Pages.ApiResponse
import com.vistony.salesforce.kotlin.View.Pages.ApiResponseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
                    for (i in 0 until apiResponse.datosPrincipales.tipoSalida!!.size)
                    {
                        apiResponse.datosPrincipales.tipoSalida!!.get(i).numInforme=numInforme
                    }
                    for (i in 0 until apiResponse.datosVisita.resumen.size)
                    {
                        apiResponse.datosVisita.resumen!!.get(i).numInforme=numInforme
                    }
                    for (i in 0 until apiResponse.formulario!!.size)
                    {
                        apiResponse.formulario!!.get(i).numInforme=numInforme
                    }

                    var data=database?.formularioTestDao?.addFormSuperviser (apiResponse)
                    var data1=database?.formularioTestDao?.addDatosPrincipales (apiResponse.datosPrincipales)
                    var data2=database?.formularioTestDao?.addDatosVisita (apiResponse.datosVisita)
                    var data3=database?.formularioTestDao?.addTipoSalida (apiResponse.datosPrincipales.tipoSalida)
                    var data4=database?.formularioTestDao?.addResumenVisita (apiResponse.datosVisita!!.resumen)
                    var data5=database?.formularioTestDao?.addPreguntaRespuesta (apiResponse.formulario)

                    //var data6=database?.formularioTestDao?.addOpcion (apiResponse.formulario.size)

                    println("Tarea $i completada")
                }

            }
            executor.shutdown()
        } catch (e: Exception) {
            Log.e("REOS", "FormularioTestRepository-addFormularioTest-error: " + e.toString())
        }
    }
}