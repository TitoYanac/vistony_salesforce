
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vistony.salesforce.kotlin.Model.ApiResponse
import com.vistony.salesforce.kotlin.Model.ApiResponseList
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitApi
import com.vistony.salesforce.kotlin.View.Template.PdfItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PdfListViewModel : ViewModel() {
    private val _pdfItems = mutableStateOf<List<PdfItemData>>(emptyList())

    val pdfItems: State<List<PdfItemData>> = _pdfItems

    private val _apiResponseList = mutableStateOf<ApiResponseList?>(null)
    val apiResponseList: State<ApiResponseList?> = _apiResponseList

    private val _selectedStartDate = mutableStateOf(Calendar.getInstance())
    val selectedStartDate: State<Calendar> = _selectedStartDate

    private val _selectedEndDate = mutableStateOf(Calendar.getInstance())
    val selectedEndDate: State<Calendar> = _selectedEndDate

    private val _startDateButtonText = mutableStateOf("Seleccionar Fecha Inicial")
    val startDateButtonText: State<String> = _startDateButtonText

    private val _endDateButtonText = mutableStateOf("Seleccionar Fecha Final")
    val endDateButtonText: State<String> = _endDateButtonText

    init {
        initializeDates()
        loadPdfItems()
    }

    private fun initializeDates() {
        _selectedStartDate.value.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), 1)
        onStartDateSelected(_selectedStartDate.value)
        _selectedEndDate.value = Calendar.getInstance()
        onEndDateSelected(_selectedEndDate.value)
    }

    fun loadPdfItems() {
        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://190.12.79.132:8083/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val api = retrofit.create(RetrofitApi::class.java)

        var startDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(_selectedStartDate.value.time)
        var endDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(_selectedEndDate.value.time)

        api.getListFormSupervisor(startDate, endDate)?.enqueue(object :
            Callback<ApiResponseList?> {
            override fun onResponse(
                call: Call<ApiResponseList?>,
                response: Response<ApiResponseList?>
            ) {
                val executor: ExecutorService = Executors.newFixedThreadPool(1)
                for (i in 1..1) {
                    viewModelScope.launch  {
                        try {
                            if (response.isSuccessful) {
                                val listApiResponse: ApiResponseList? = response.body()
                                _apiResponseList.value = listApiResponse
                                Log.e("retrofitListapiresponse", "ListApiResponse size: ${listApiResponse?.data?.size}")

                                val pdfItems = listApiResponse?.data?.mapNotNull { item ->
                                    val fechaString = item.datosPrincipales?.fechaHoy.toString().split(" ").first()
                                    val formatoEsperado = "yyyy-MM-dd"
                                    val formato = SimpleDateFormat(formatoEsperado, Locale.getDefault())
                                    val fechaParseada: Date? = fechaString?.let { formato.parse(it) }

                                    val calendar = Calendar.getInstance()
                                    fechaParseada?.let { calendar.time = it }

                                    /*if (item.galeria != null) {
                                        Log.e("retrofitTest3", "galeria size: ${item.galeria!!.size}")
                                        val cont = item.galeria!!.size

                                        try {
                                            cargarImagenes(apiResponse = item, context = context)
                                            // Procesar las imágenes cargadas...
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }*/

                                    PdfItemData(
                                        numInforme = item.datosPrincipales?.numInforme?.toString() ?: "",
                                        name = item.datosPrincipales?.nombreVendedor ?: "",
                                        description = item.datosPrincipales?.nombreSupervisor ?: "",
                                        date = calendar ?: Calendar.getInstance()
                                    )
                                }

                                _pdfItems.value = pdfItems ?: emptyList()
                            } else {
                                Log.e("retrofitTest", "Error en la respuesta de la API: ${response.headers()}")
                                Log.e("retrofitTest", "Error en la respuesta de la API: ${response.code()}")
                            }
                        } catch (e: Exception) {
                            Log.e("retrofitTest", "Error en la llamada a la API: ${e.message}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponseList?>, t: Throwable) {
                Log.e("retrofitTest", "Error en la llamada a la API: ${t.message}")
            }
        })
    }

    private suspend fun loadImageFromUrl(url: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.connect()
                val input = connection.inputStream
                BitmapFactory.decodeStream(input)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    private suspend fun cargarImagenes(apiResponse: ApiResponse, context: Context) {
        apiResponse.galeria?.forEach { formularioGaleria ->
            if (formularioGaleria.uri != null && formularioGaleria.uri.isNotBlank()) {
                val bitmap = loadImageFromUrl(formularioGaleria.uri)
                if (bitmap != null) {
                    formularioGaleria.bitmap = bitmap
                }
            }
        }
    }

    fun onStartDateSelected(date: Calendar) {
        _selectedStartDate.value = date
        updateStartDateButtonText()
    }

    fun onEndDateSelected(date: Calendar) {
        _selectedEndDate.value = date
        updateEndDateButtonText()
    }

    fun onStartDateButtonTextUpdated(text: String) {
        _startDateButtonText.value = text
    }

    fun onEndDateButtonTextUpdated(text: String) {
        _endDateButtonText.value = text
    }

    private fun updateStartDateButtonText() {
        _startDateButtonText.value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(_selectedStartDate.value.time)
        loadPdfItems()
    }

    private fun updateEndDateButtonText() {
        _endDateButtonText.value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(_selectedEndDate.value.time)
        loadPdfItems()
    }
}
