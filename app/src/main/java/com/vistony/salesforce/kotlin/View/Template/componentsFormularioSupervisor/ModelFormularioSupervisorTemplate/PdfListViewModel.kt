
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vistony.salesforce.Entity.SesionEntity.imei
import com.vistony.salesforce.kotlin.Model.ApiResponse
import com.vistony.salesforce.kotlin.Model.ApiResponseList
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitApi
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitConfig
import com.vistony.salesforce.kotlin.View.Template.PdfItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    private val _pdfItems = MutableStateFlow<List<PdfItemData>>(emptyList())
    val pdfItems= _pdfItems.asStateFlow()

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
        var startDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(_selectedStartDate.value.time)
        var endDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(_selectedEndDate.value.time)

        val retrofitConfig: RetrofitConfig? = RetrofitConfig()
        val service = retrofitConfig?.getClientLog()?.create(
            RetrofitApi
            ::class.java
        )
        service?.getListFormSupervisor(fini = startDate, fin = endDate, imei = imei)?.enqueue(object :
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
                                Log.e("jesusdebug5", "ListApiResponse size: ${listApiResponse?.data?.size}")
                                var aux = emptyList<PdfItemData>()
                                if(listApiResponse != null) {
                                    listApiResponse?.data
                                        ?.filterNotNull()
                                        ?.forEach { apiResponseItem ->
                                            try {
                                                val fechaString = apiResponseItem.datosPrincipales?.fechaHoy.toString().split(" ").first()
                                                val formatoEsperado = "yyyy-MM-dd"
                                                val formato = SimpleDateFormat(formatoEsperado, Locale.getDefault())
                                                val fechaParseada: Date? = fechaString?.let { formato.parse(it) }

                                                val calendar = Calendar.getInstance()
                                                fechaParseada?.let { calendar.time = it }

                                                val obj = PdfItemData(
                                                    numInforme = apiResponseItem.datosPrincipales?.numInforme?.toString() ?: "",
                                                    name = apiResponseItem.datosPrincipales?.nombreVendedor ?: "",
                                                    description = apiResponseItem.datosPrincipales?.nombreSupervisor ?: "",
                                                    date = calendar ?: Calendar.getInstance()
                                                )

                                                aux += obj


                                            } catch (e: Exception) {
                                                Log.e("retrofitTest", "Error al procesar un elemento de la API: ${e.message}")
                                            }
                                        }

                                    _pdfItems.value = aux

                                }
                                Log.e("jesusdebug5", "pdflist size: ${_pdfItems.value?.size}")


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
