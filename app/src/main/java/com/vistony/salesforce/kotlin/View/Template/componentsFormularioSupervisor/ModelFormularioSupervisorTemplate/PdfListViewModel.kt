
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vistony.salesforce.kotlin.Model.ApiResponse
import com.vistony.salesforce.kotlin.Model.ApiResponseList
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitApi
import com.vistony.salesforce.kotlin.View.Template.PdfItemData
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

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
        // Establecer la fecha inicial al primer día del mes actual
        _selectedStartDate.value.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), 1)
        onStartDateSelected(_selectedStartDate.value)

        // Establecer la fecha final a la fecha actual
        _selectedEndDate.value = Calendar.getInstance()
        onEndDateSelected(_selectedEndDate.value)
    }

    fun loadPdfItems() {
        viewModelScope.launch {
            val client = OkHttpClient.Builder().build()

// Crear una instancia de Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("http://190.12.79.132:8083/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

// Crear una instancia de la interfaz que contiene el método getListFormSupervisor
            val api = retrofit.create(RetrofitApi::class.java)

            var startDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(_selectedStartDate.value.time)
            var endDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(_selectedEndDate.value.time)
            /*startDate = "2023-12-04"
            endDate = "2023-12-04"*/
            Log.e("fechalistapiresponse", "startDate: $startDate")
            Log.e("fechalistapiresponse", "endDate: $endDate")
            api.getListFormSupervisor(startDate, endDate)?.enqueue(object :
                Callback<ApiResponseList?> {
                override fun onResponse(
                    call: Call<ApiResponseList?>,
                    response: Response<ApiResponseList?>
                ) {
                    if (response.isSuccessful) {
                        val listApiResponse: ApiResponseList? = response.body()
                        val pdfItems = mutableListOf<PdfItemData>()
                        _apiResponseList.value = listApiResponse  // Update the attribute
                        Log.e("retrofitListapiresponse", "ListApiResponse size: ${listApiResponse?.data?.size}")

                        val cont = _apiResponseList.value?.data?.size
                        for ( i in 0 until cont!!) {
                            val item = _apiResponseList.value?.data?.get(i)
                            if(item!=null){
                                val fechaString = item.datosPrincipales?.fechaHoy.toString().split(" ").first()
                                val formatoEsperado = "yyyy-MM-dd"
                                val formato = SimpleDateFormat(formatoEsperado, Locale.getDefault())
                                val fechaParseada: Date? = fechaString?.let { formato.parse(it) }

                                val calendar = Calendar.getInstance()
                                if (fechaParseada != null) {
                                    calendar.time = fechaParseada
                                }
                                pdfItems.add(PdfItemData(item.datosPrincipales!!.numInforme!!.toString(),item.datosPrincipales!!.nombreVendedor!!, item.datosPrincipales!!.nombreSupervisor!!,calendar!! ))
                            }
                        }

                        _pdfItems.value = pdfItems;
                    } else {
                        // Manejar error de respuesta
                        Log.e("retrofitTest", "Error en la respuesta de la API: ${response.headers()}")
                        Log.e("retrofitTest", "Error en la respuesta de la API: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ApiResponseList?>, t: Throwable) {
                    // Manejar error de red u otro tipo de error
                    Log.e("retrofitTest", "Error en la llamada a la API: ${t.message}")
                }
            })

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
        _endDateButtonText.value =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(_selectedEndDate.value.time)
        loadPdfItems()
    }
}
