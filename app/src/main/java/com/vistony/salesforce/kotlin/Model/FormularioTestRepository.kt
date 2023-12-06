package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitApi
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitConfig
import com.vistony.salesforce.kotlin.Utilities.getDateTimeCurrent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FormularioTestRepository {
    private val _resultDB = MutableStateFlow(ApiResponseEntity())
    val resultDB: StateFlow<ApiResponseEntity> get() = _resultDB

    private val _resultAPI = MutableStateFlow(ApiResponseEntity())
    val resultAPI: StateFlow<ApiResponseEntity> get() = _resultAPI

    suspend fun addFormularioTest(context: Context,apiResponse: ApiResponse)
    {
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                Log.e("gettingForm6", "apiresponsemodel_asd: ${apiResponse?.galeria?.size}")

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

                    Log.e("galeria", "galeria enviada a room: " + apiResponse.galeria?.size)
                    if(apiResponse.galeria!!.isNotEmpty()){
                        for (formularioGaleria in apiResponse.galeria!!) {
                            formularioGaleria.numInforme=numInforme
                            Log.e("galeria", "galeria enviada (img $i ) num informe: " +apiResponse.formulario!!.get(i).numInforme)
                        }
                        var data6=database?.formularioTestDao?.addFormularioGaleria (apiResponse.galeria!!)
                    }else{
                        Log.e("galeria", "galeria vacia")
                    }

                    _resultDB.value=ApiResponseEntity(StatusCode = "Y")
                    println("Tarea $i completada")
                }

            }
            executor.shutdown()
            Log.e("mostrandoApiResponseAdd", "mostrandoApiResponseAdd: ${apiResponse.galeria!!.size}")
            Log.e("mostrandoApiResponseAdd", "mostrandoApiResponseAdd: ${_resultDB.value.Data!!.galeria!!.size}")
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

                    try {
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

                    data!!.galeria=database?.formularioTestDao!!.getFormularioGaleria(data!!.numInforme)
                    //aqui recuperar los datos de cameraviewmodel el listado de imagenes base 64 y convertir a json

                        Log.e("galeria", "galeria enviada a room num informe: " + data?.numInforme);
                        Log.e("galeria", "galeria enviada a room size: ${data?.galeria?.size}");
                        Log.e("galeria", "galeria enviada a sap : ${data?.galeria}");

                    var json: String? = null
                    val gson = Gson()
                        if (data!=null) {
                            json = gson.toJson(data)
                        }
                        Log.e("REOS", "FormularioTestRepository-sendFormularioTest-json: $json")
                        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        val file = File(directory, "json.txt")
                        if (json != null) {
                            file.writeText(json)
                        }
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

    suspend fun getFormularioTest(imei: String, date: String):ApiResponse?
    {
        var apiResponse: ApiResponse? = null  // Variable para almacenar la respuesta

        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {

                    val retrofitConfig: RetrofitConfig? = RetrofitConfig()
                    val service = retrofitConfig?.getClientLog()?.create(
                        RetrofitApi
                        ::class.java
                    )

                    service?.getFormSupervisor(
                        imei, date
                    )?.enqueue(object : Callback<ApiResponse?> {

                        override fun onResponse(
                            call: Call<ApiResponse?>,
                            response: Response<ApiResponse?>
                        ) {
                            Log.e("gettingForm", "reponse: $response")
                            Log.e("gettingForm", "reponse.body(): ${response.body()}")
                            val appResponse = response.body()
                            if (response.isSuccessful&&appResponse!=null) {
                                _resultAPI.value= ApiResponseEntity(StatusCode = "Y", Data = appResponse)
                            }
                            Log.e("gettingForm", "appResponse: $appResponse")
                            Log.e("gettingForm", "urlApi: ${BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Superviser"}")
                            /*val jsonResponse =  simulateApiCall(imei, date)

                            Log.e("miconsulta", "jsonResponse: $jsonResponse")

                            val gson = GsonBuilder().create()

                            apiResponse = gson.fromJson(jsonResponse, ApiResponse::class.java)*/


                        }

                        override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                            _resultAPI.value=ApiResponseEntity(StatusCode = "N")
                        }
                    })
                }
            }
            executor.shutdown()
            return apiResponse
        } catch (e: Exception) {
            Log.e("REOS", "FormularioTestRepository-getFormularioTest-error: " + e.toString())
            return null  // Retornamos null en caso de error

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
    @ColumnInfo(name = "numInforme")
    var numInforme: String = "",
    @ColumnInfo(name = "chkrecibido")
    var chkrecibido: String = "N",
    @ColumnInfo(name = "code")
    var code: String = "",
    @ColumnInfo(name = "message")
    var message: String = "",
    @ColumnInfo(name = "dateregister")
    var dateregister: String = "",
    @Ignore
    @ColumnInfo(name = "num_informe")
    var num_informe: String = "",
    @Ignore
    @SerializedName("galeria") var galeria: List<FormularioGaleria>? = listOf(), // lista de String de imagenes base 64
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
    @SerializedName("bitmap") var bitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
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
    @SerializedName("tipo_salida") var tipoSalida: List<TipoSalida>? = listOf(),
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
    var numInforme: String? = "0",
)

@Entity(tableName = "outputtype")
data class TipoSalida(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("opcion") var opcion: String? = "",
    @SerializedName("valor") var valor: String? = "",
    @SerializedName("marcado") var marcado: Boolean? = false,
    var numInforme: String? = "0",
)

@Entity(tableName = "visitsummary")
data class ResumenVisita(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("tipo") var tipo: String? = "",
    @SerializedName("cantidad") var cantidad: String? = "0",
    @SerializedName("monto") var monto: String? = "0",
    var numInforme: String? = "0",
)

@Entity(tableName = "questionanswer")
data class PreguntaRespuesta(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("pregunta") var pregunta: String? = "",
    @Ignore
    @SerializedName("opciones") var opciones: List<Opcion> = listOf(),
    @SerializedName("respuesta") var respuesta: String? = "",
    var numInforme: String? = "0",
    @SerializedName("code") var code: String?="",
)

@Entity(tableName = "option")
data class Opcion(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("opcion") var opcion: String? = "",
    @SerializedName("valor") var valor: String? = "",
    var numInforme: String? = "0",
)

data class ApiResponseEntity(
    var StatusCode:String="",
    @SerializedName("Data")
    var Data: ApiResponse? = null,
)
data class ApiResponseList(
    @SerializedName("Data")
    val data: List<ApiResponse>
)