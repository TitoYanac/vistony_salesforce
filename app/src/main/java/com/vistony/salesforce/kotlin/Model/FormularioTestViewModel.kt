package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vistony.salesforce.kotlin.View.Pages.ApiResponse
import com.vistony.salesforce.kotlin.View.Pages.ApiResponseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FormularioTestViewModel(
        private val formularioTestRepository: FormularioTestRepository,
        private val context: Context,
): ViewModel() {
    private val _resultDB = MutableStateFlow(ApiResponseEntity())
    val resultDB: StateFlow<ApiResponseEntity> get() = _resultDB

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
            // Observar cambios en invoicesRepository.invoices
            formularioTestRepository.resultDB.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _resultDB.value = newResult
                Log.e("REOS", "FormularioTestViewModel-init-_result.value: " +_resultDB.value)
            }
        }
    }

    fun addFormularioTest(apiResponse: ApiResponse)
    {
        viewModelScope.launch {
            formularioTestRepository.addFormularioTest(context,apiResponse)
        }
    }

}