package com.vistony.salesforce.kotlin.View.Pages

import FormularioTest1Template
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.kotlin.Model.BankRepository
import com.vistony.salesforce.kotlin.Model.BankViewModel
import com.vistony.salesforce.kotlin.Model.FormularioTestRepository
import com.vistony.salesforce.kotlin.Model.FormularioTestViewModel
import com.vistony.salesforce.kotlin.Model.ServiceApp
import com.vistony.salesforce.kotlin.View.Atoms.theme.VistonyTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class FormularioTest1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            // Simulate an API call and get the JSON response
            val imei = "355531114784577"
            val date = "2023-10-13"

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val jsonResponse = simulateApiCall(imei, date)

                    Log.e("miconsulta", "jsonResponse: $jsonResponse")

                    val gson = GsonBuilder().create()

                    val apiResponse = gson.fromJson(jsonResponse, ApiResponse::class.java)

                    launch(Dispatchers.Main) {
                        setContent {
                            VistonyTheme {
                                // A surface container using the 'background' color from the theme
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                ) {
                                    FormularioTest1Template(apiResponse)
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("FormularioTest1", "SALIO $e")
                    launch(Dispatchers.Main) {
                        setContent {
                            VistonyTheme {
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                ) {
                                    // Display an error message to the user
                                    // You can create a Composable for this purpose
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun simulateApiCall(imei: String? = "", date: String): String {
    val client = OkHttpClient()

    // Construct the complete URL with query parameters
    val url = "https://salesforce.vistony.pe/get.test/api/Superviser?imei=$imei&date=$date"

    val request = Request.Builder()
        .url(url)
        .build()

    try {
        val response: Response = client.newCall(request).execute()

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
    var id: Int = 0,
    @SerializedName("id_supervisor") var idSupervisor: String?="",
    @SerializedName("id_vendedor") var idVendedor: String?="",
    @Ignore
    @SerializedName("datos_principales") val datosPrincipales: DatosPrincipales? = null,
    @Ignore
    @SerializedName("datos_visita") val datosVisita: DatosVisita? = null,
    @Ignore
    @SerializedName("formulario") val formulario: List<PreguntaRespuesta>? = listOf(),
    @SerializedName("comentario") var comentario: String = "",
    var numInforme: String = "",

)

@Entity(tableName = "maindata")
data class DatosPrincipales(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("num_informe") var numInforme: String? = "",
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
    var numInforme: String? = "",
)

@Entity(tableName = "outputtype")
data class TipoSalida(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("opcion") var opcion: String? = "",
    @SerializedName("valor") var valor: String? = "",
    @SerializedName("marcado") var marcado: Boolean? = false,
    var numInforme: String? = "",
)

@Entity(tableName = "visitsummary")
data class ResumenVisita(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("tipo") var tipo: String? = "",
    @SerializedName("cantidad") var cantidad: String? = "0",
    @SerializedName("monto") var monto: String? = "0",
    var numInforme: String? = "",
)

@Entity(tableName = "questionanswer")
data class PreguntaRespuesta(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        @SerializedName("pregunta") var pregunta: String? = "",
        @Ignore
        @SerializedName("opciones") var opciones: List<Opcion> = listOf(),
        @SerializedName("respuesta") var respuesta: String? = "",
        var numInforme: String? = "",
)

@Entity(tableName = "option")
data class Opcion(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("opcion") var opcion: String? = "",
    @SerializedName("valor") var valor: String? = "",
    var numInforme: String? = "",
)

data class ApiResponseEntity(
        var Status:String="",
        @SerializedName("Service")
        var Data: ApiResponse? = null,
)
