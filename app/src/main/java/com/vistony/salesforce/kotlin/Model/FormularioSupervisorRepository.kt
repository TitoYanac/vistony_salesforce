package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitApi
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitConfig
import com.vistony.salesforce.kotlin.Utilities.getDateTimeCurrent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.max

@Suppress("SpellCheckingInspection")
class FormularioSupervisorRepository(private val context: Context) {

    private val _resultDB = MutableStateFlow(ApiResponseEntity())
    val resultDB: StateFlow<ApiResponseEntity> get() = _resultDB

    private val _resultAPI = MutableStateFlow(ApiResponseEntity())
    val resultAPI: StateFlow<ApiResponseEntity> get() = _resultAPI

    private suspend fun addFormularioToDatabase(apiResponse: ApiResponse) {


        withContext(Dispatchers.IO) {
            try {
                apiResponse.dateregister = getDateTimeCurrent()!!

                val numInforme = apiResponse.datosPrincipales!!.numInforme!!
                apiResponse.numInforme = numInforme
                for (tiposalida in apiResponse.datosPrincipales!!.tipoSalida!!) {
                    tiposalida.numInforme = numInforme
                }
                apiResponse.datosVisita!!.numInforme = numInforme
                for (resumen in apiResponse.datosVisita!!.resumen!!) {
                    resumen.numInforme = numInforme
                }
                for (pregunta in apiResponse.formulario!!) {
                    pregunta.numInforme = numInforme
                    for(option in pregunta.opciones!!){
                        option.numInforme = numInforme
                    }
                }
                for (imagen in apiResponse.galeria!!) {
                    imagen.numInforme = numInforme
                }
                /*apiResponse!!.galeria!!.forEach { item->
                    item.numInforme = numInforme
                }*/

                Log.e("jesusdebug3", "apiResponse.numInforme ${apiResponse.numInforme}")
                Log.e("jesusdebug3", "apiResponse.datosPrincipales ${apiResponse.datosPrincipales!!.numInforme}")
                Log.e("jesusdebug3", "apiResponse.datosPrincipales ${apiResponse.datosPrincipales!!.tipoSalida!![0].numInforme}")
                Log.e("jesusdebug3", "apiResponse.datosVisita ${apiResponse.datosVisita!!.numInforme}")
                Log.e("jesusdebug3", "apiResponse.datosVisita ${apiResponse.datosVisita!!.resumen!![0].numInforme}")
                Log.e("jesusdebug3", "apiResponse.formulario ${apiResponse.formulario!![0].numInforme}")
                Log.e("jesusdebug3", "apiResponse.galeria ${apiResponse.galeria!![0].numInforme}")
                Log.e("jesusdebug3", "apiResponse.comentario ${apiResponse.comentario}")

                for (preguntaRespuesta in apiResponse.formulario!!) {
                    Log.e("jesusdebug3", "pregunta ${preguntaRespuesta.numInforme}")
                }
                for (imagen in apiResponse.galeria!!) {
                    Log.e("jesusdebug3", "galeria ${imagen.numInforme}")
                }

                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                database?.formularioSupervisorDao?.addFormSuperviser(apiResponse)
                database?.formularioSupervisorDao?.addDatosPrincipales(apiResponse.datosPrincipales!!)
                database?.formularioSupervisorDao?.addDatosVisita(apiResponse.datosVisita)
                database?.formularioSupervisorDao?.addTipoSalida(apiResponse.datosPrincipales!!.tipoSalida)
                database?.formularioSupervisorDao?.addResumenVisita(apiResponse.datosVisita!!.resumen)
                database?.formularioSupervisorDao?.addPreguntaRespuesta(apiResponse.formulario)

                database?.formularioSupervisorDao?.addFormularioGaleria(apiResponse.galeria!!)


                _resultDB.value = ApiResponseEntity(StatusCode = "Y", Data = apiResponse)
                Log.e("jesusdebug4", "addFormularioToDatabase: ${_resultDB.value}")
            } catch (e: Exception) {
                //borrar registro que capturo el catch del room
                _resultDB.value = ApiResponseEntity(StatusCode = "N", Data = apiResponse)
                Log.e("REOS", "FormularioSupervisorRepository-addFormularioToDatabase-error: $e")
            }
        }
    }

    suspend fun sendFormularioToApi(apiResponse: ApiResponse) {
        Log.e("jesusdebug2", "sendFormularioToApi")
        withContext(Dispatchers.IO) {
            try {
                // Lógica para enviar formulario a la API
                val json = Gson().toJson(apiResponse)
                Log.e("jesusdebug2", "json: $json")
                val jsonRequest: RequestBody = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val retrofitConfig: RetrofitConfig? = RetrofitConfig()
                val service = retrofitConfig?.getClientLog()?.create(RetrofitApi::class.java)


                service?.sendFormSupervisor(jsonRequest)?.enqueue(object : Callback<ApiResponseEntity?> {
                    override fun onResponse(call: Call<ApiResponseEntity?>, response: Response<ApiResponseEntity?>) {
                        val responseFormSupervisor = response.body()
                        if (response.isSuccessful && responseFormSupervisor != null) {
                            // Lógica para manejar la respuesta de la API
                            var code = ""
                            var numinforme = ""
                            var message = ""
                            var status = ""

                            if (responseFormSupervisor.StatusCode == "201") {
                                status = "Y"
                            } else {
                                status = "N"
                            }
                            code = responseFormSupervisor.Data!!.code
                            message = responseFormSupervisor.Data!!.message
                            numinforme = responseFormSupervisor.Data!!.numInforme

                            val executor1: ExecutorService = Executors.newFixedThreadPool(1)

                            for (i in 1..1) {
                                executor1.execute {
                                    // Definir la variable database aquí
                                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                                    database?.formularioSupervisorDao?.updateStatusFormSupervisor(code, message, status, numinforme)
                                }
                            }

                            executor1.shutdown()
                            Log.e("REOS", "FormularioSupervisorRepository-sendFormularioToApi-responseFormSupervisor: $responseFormSupervisor")
                        }
                    }

                    override fun onFailure(call: Call<ApiResponseEntity?>, t: Throwable) {
                        Log.e("REOS", "FormularioSupervisorRepository-sendFormularioToApi-onFailure: ${t.toString()}")
                    }
                })

                // Actualizar estado
                _resultAPI.value = ApiResponseEntity(StatusCode = "Y")
            } catch (e: Exception) {
                // Manejar excepciones de manera específica
                Log.e("jesuserror", "FormularioSupervisorRepository-sendFormularioToApi-error: $e")
            }
        }
    }

    suspend fun enviar_a_ROOM_FormularioSupervisor(apiResponse: ApiResponse) {
        Log.e("jesusdebug", "enviando a room apiResponse: $apiResponse")
        try {
            addFormularioToDatabase(apiResponse)
        } catch (e: Exception) {
            Log.e("jesuserror", "FormularioSupervisorRepository-addFormularioTest-error: $e")
        }
    }

    /*suspend fun sendFormularioSupervisor(apiResponse: ApiResponse) {
        try {
            withContext(Dispatchers.IO) {
                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                val data = database?.formularioSupervisorDao?.getFormSuperviser()
                Log.e("jesusdebug2", "data: $data")

                // Lógica para obtener formulario de la base de datos y enviar a la API
                if (data != null) {
                    Log.e("jesusdebug2", "hay datos en la base de datos")
                    sendFormularioToApi(apiResponse)
                } else {
                    Log.e("jesusdebug2", "no hay datos en la base de datos")
                }
            }
        } catch (e: Exception) {
            Log.e("jesuserror", "FormularioSupervisorRepository-sendFormularioTest-error: $e")
        }
    }*/
    suspend fun conseguir_desde_API_FormularioSupervisor(imei: String, date: String): ApiResponseEntity {
        try {
            return withContext(Dispatchers.IO) {
                val retrofitConfig: RetrofitConfig? = RetrofitConfig()
                val service = retrofitConfig?.getClientLog()?.create(RetrofitApi::class.java)


                // Lógica para obtener formulario de la API
                val response = service?.getFormSupervisor(imei, date)?.execute()


                if (response != null && response.isSuccessful) {

                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    val ultimoFormulario = database?.formularioSupervisorDao?.getLastNumInformeFormSuperviser()!!
                    Log.e("jesusdebug", "ultimoFormulario: $ultimoFormulario")
                    Log.e("jesusdebug", "response.body()!!.numInforme: ${response.body()!!.datosPrincipales!!.numInforme}")
                    val correlativo = max(ultimoFormulario.toInt(),response.body()!!.datosPrincipales!!.numInforme!!.toInt()) + 1
                    Log.e("jesusdebug", "correlativo: $correlativo")
                    Log.e("jesusdebug", "FormularioSupervisorRepository-getFormularioTest-response.body: ${response.body()}")
                    val obj = ApiResponseEntity( StatusCode = "Y", Data = response.body())
                    obj.Data!!.datosPrincipales!!.numInforme = correlativo.toString()
                    return@withContext obj
                    //return@withContext response.body()
                } else {
                    return@withContext ApiResponseEntity(StatusCode = "N")
                }

                //return@withContext null
            }
        } catch (e: Exception) {
            Log.e("REOS", "FormularioSupervisorRepository-getFormularioTest-error: $e")
            return ApiResponseEntity(StatusCode = "N")
        }
    }


}


fun simulateApiCall(imei: String? = "", date: String): String {
    val client = OkHttpClient()

    // Construct the complete URL with query parameters
    //val url = "https://salesforce.vistony.pe/get.test/api/Superviser?imei=$imei&date=$date"
    //var url = "http://190.12.79.132:8087/get/api/Superviser?imei=359128310282559&date=2023-10-21"

    //var url = "http://190.12.79.132:8087/get/api/Superviser?imei=$imei&date=$date"
    var url = "http://190.12.79.132:8087/get/api/Superviser?imei=359128310282559&date=2023-10-21"
    //https://salesforce.vistony.pe/get.test/api/Superviser?imei=359128310282559&date=2023-11-03
    Log.e("miUrl", "url: $url")



    val request = Request.Builder()
        .url(url)
        .build()

    try {
        val response: okhttp3.Response = client.newCall(request).execute()

        Log.e("miResponse", "Response: ${response.body}")
        if (response.isSuccessful) {
            // Get the response as a string
            return response.body?.string() ?: ""
        } else {
            // Handle the case where the response is not successful
            return "Respuesta no exitosa: ${response.code}"
        }
    } catch (e: IOException) {
        e.printStackTrace()
        // Handle any exceptions that may occur during the request
        return "Error de red: ${e.message}"
    }
}

@Entity(tableName = "formsuperviser")
data class ApiResponse(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @SerializedName("id_supervisor") var idSupervisor: String? = "",
    @SerializedName("id_vendedor") var idVendedor: String? = "",
    @Ignore
    @SerializedName("datos_principales") var datosPrincipales: DatosPrincipales? = null,
    @Ignore
    @SerializedName("datos_visita") var datosVisita: DatosVisita? = null,
    @Ignore
    @SerializedName("formulario") var formulario: List<PreguntaRespuesta>? = listOf(),
    @SerializedName("comentario") var comentario: String = "",
    @SerializedName("num_informe") var numInforme: String = "",
    @SerializedName("chkrecibido") var chkrecibido: String = "N",
    @SerializedName("code") var code: String = "",
    @SerializedName("message") var message: String = "",
    @SerializedName("dateregister") var dateregister: String = "",
    @Ignore
    @SerializedName("galeria") var galeria: List<FormularioGaleria>? = listOf() // lista de String de imagenes base 64
)

@Entity(tableName = "FormularioGaleria")
data class FormularioGaleria(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("base64") var base64: String = "",// lista de String de imagenes base 64
    @SerializedName("uri") var uri: String = "",
    @SerializedName("status") var status: String = "N",
    @SerializedName("message") var message: String = "",
    @SerializedName("ErrorCode") var errorCode: String = "",
    var numInforme: String? = "",
    @Ignore
    @SerializedName("bitmap") var bitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
)

@Entity(tableName = "maindata")
data class DatosPrincipales(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("num_informe") var numInforme: String? = "0",
    @SerializedName("fecha_hoy") var fechaHoy: String? = "",
    @SerializedName("nombre_supervisor") var nombreSupervisor: String? = "",
    @SerializedName("nombre_vendedor") var nombreVendedor: String? = "",
    @Ignore
    @SerializedName("tipo_salida") var tipoSalida: List<TipoSalida>? = listOf()
)

@Entity(tableName = "visitdata")
data class DatosVisita(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("zona") var zona: String? = "",
    @SerializedName("observacion_zona") var observacionZona: String? = "",
    @SerializedName("hora_inicio") var horaInicio: String? = "",
    @SerializedName("hora_fin") var horaFin: String? = "",
    @Ignore
    @SerializedName("resumen") var resumen: List<ResumenVisita> = listOf(),
    @SerializedName("clientes_nuevos") var clientesNuevos: String? = "0",
    @SerializedName("clientes_empadronados") var clientesEmpadronados: String? = "0",
    var numInforme: String? = "0"
)

@Entity(tableName = "outputtype")
data class TipoSalida(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("opcion") var opcion: String? = "",
    @SerializedName("valor") var valor: String? = "",
    @SerializedName("marcado") var marcado: Boolean? = false,
    var numInforme: String? = "0"
)

@Entity(tableName = "visitsummary")
data class ResumenVisita(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("tipo") var tipo: String? = "",
    @SerializedName("cantidad") var cantidad: String? = "0",
    @SerializedName("monto") var monto: String? = "0",
    var numInforme: String? = "0"
)

@Entity(tableName = "questionanswer")
data class PreguntaRespuesta(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("pregunta") var pregunta: String? = "",
    @Ignore
    @SerializedName("opciones") var opciones: List<Opcion> = listOf(),
    @SerializedName("respuesta") var respuesta: String? = "",
    @SerializedName("valor") var valor: String? = "",
    var numInforme: String? = "0",
    @SerializedName("code") var code: String? = ""
)

@Entity(tableName = "option")
data class Opcion(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("code") var code: String? = "",
    @SerializedName("opcion") var opcion: String? = "",
    @SerializedName("valor") var valor: String? = "",
    //@SerializedName("marcado") var marcado: Boolean = false,
    var numInforme: String? = "0"
)

data class ApiResponseEntity(
    var StatusCode:String="",
    @SerializedName("Data")
    var Data: ApiResponse? = null
)

data class ApiResponseList(
    @SerializedName("Data")
    val data: List<ApiResponse>
)
