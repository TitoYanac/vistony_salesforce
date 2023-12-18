package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FormularioTestViewModel(
    private val formularioTestRepository: FormularioTestRepository,
    private val context: Context,
) : ViewModel() {
    private val _resultDB = MutableStateFlow(ApiResponseEntity())
    val resultDB: StateFlow<ApiResponseEntity> get() = _resultDB

    private val _resultAPI = MutableStateFlow(ApiResponseEntity())
    val resultAPI: StateFlow<ApiResponseEntity> get() = _resultAPI

    class FormularioTestViewModelFactory(
            private val formularioTestRepository: FormularioTestRepository,
            private val context: Context,
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FormularioTestViewModel(
                    formularioTestRepository,
                    context,
            ) as T
        }
    }

    init{
        //getInvoices()
        viewModelScope.launch {
            formularioTestRepository.resultDB.collectLatest { newResult ->
                _resultDB.value = newResult
                Log.e("REOS", "FormularioTestViewModel-init-_result.value: $_resultDB.value")
            }
        }

        viewModelScope.launch {
            formularioTestRepository.resultAPI.collectLatest { newResult ->
                _resultAPI.value = newResult
                Log.e("REOS", "FormularioTestViewModel-init-_result.value: $_resultAPI.value")
            }
        }

    }

    fun addFormularioTest(apiResponse: ApiResponse)
    {
        viewModelScope.launch {
            /*Log.e("gettingForm5", "apiresponsemodel_asd: ${apiResponse?.galeria?.size}")
            Log.e("gettingForm5", "apiresponsemodel_asd: ${apiResponse?.galeria?.get(0)?.base64}")*/
            /*
            val base64String = apiResponse?.galeria?.get(0)?.base64 ?: ""
            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(directory, "imagen_base64.txt")
            file.writeText(base64String)
             */
            formularioTestRepository.addFormularioTest(apiResponse)
        }
    }

    fun sendFormularioTest()
    {
        viewModelScope.launch {
            formularioTestRepository.sendFormularioTest()
        }
    }

    fun getFormularioTest(imei:String,date:String)
    {
        Log.e("gettingForm", "imei: $imei, date: $date")
        viewModelScope.launch {
            formularioTestRepository.getFormularioTest(imei,date)
        }
    }


    private val _isSection1Expanded = MutableStateFlow(false)
    val isSection1Expanded: StateFlow<Boolean> get() = _isSection1Expanded

    private val _isSection2Expanded = MutableStateFlow(true)
    val isSection2Expanded: StateFlow<Boolean> get() = _isSection2Expanded

    private val _isSection3Expanded = MutableStateFlow(true)
    val isSection3Expanded: StateFlow<Boolean> get() = _isSection3Expanded


    private val _isButtonEnabled = MutableStateFlow(true)
    val isButtonEnabled: StateFlow<Boolean> get() = _isButtonEnabled

    private val _isButtonEnabled2 = MutableStateFlow(false)
    val isButtonEnabled2: StateFlow<Boolean> get() = _isButtonEnabled2

    private val _shouldShowPopup = MutableStateFlow(false)
    val shouldShowPopup: StateFlow<Boolean> get() = _shouldShowPopup

    private val _popupMessage = MutableStateFlow("")
    val popupMessage: StateFlow<String> get() = _popupMessage

    // Funciones para actualizar los estados
    fun updateButtonEnabled(isEnabled: Boolean) {
        _isButtonEnabled.value = isEnabled
    }

    fun updateButtonEnabled2(isEnabled: Boolean) {
        _isButtonEnabled2.value = isEnabled
    }

    fun updateShouldShowPopup(shouldShow: Boolean) {
        _shouldShowPopup.value = shouldShow
    }

    fun updatePopupMessage(valor: String) {
        _popupMessage.value = valor
    }
    fun updateSection1Expanded(expanded: Boolean) {
        _isSection1Expanded.value = expanded
    }
    fun updateSection2Expanded(expanded: Boolean) {
        _isSection2Expanded.value = expanded
    }
    fun updateSection3Expanded(expanded: Boolean) {
        _isSection3Expanded.value = expanded
    }
}