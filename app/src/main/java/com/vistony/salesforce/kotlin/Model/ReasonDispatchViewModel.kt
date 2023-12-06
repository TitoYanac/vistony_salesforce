package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReasonDispatchViewModel  (
    private val Imei:String,
    private val reasonDispatchRepository: ReasonDispatchRepository,
    private val context: Context,

) : ViewModel()
{
    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status

    private val _result_get = MutableStateFlow(ResponseReasonDispatch())
    val result_get: StateFlow<ResponseReasonDispatch> get() = _result_get

    class ReasonDispatchViewModelFactory(
        private val Imei:String,
        private val reasonDispatchRepository: ReasonDispatchRepository,
        private var context: Context
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ReasonDispatchViewModel(
                Imei,
                reasonDispatchRepository,
                context
            ) as T
        }
    }

    init{
        //getInvoices()
        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            reasonDispatchRepository.result_get.collect { newResultGet ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result_get.value = newResultGet
            }
        }
    }

    fun addReasonDispatch()
    {
        Log.e(
            "REOS",
            "ReasonDispatchViewModel-getReasonDispatch-fun"
        )
        viewModelScope.launch {
            Log.e(
                "REOS",
                "ReasonDispatchViewModel-getReasonDispatch-Imei"+Imei
            )
            reasonDispatchRepository.addReasonDispatch (Imei,context)
        }
        /*reasonDispatchRepository.status.observe(lifecycleOwner) { status ->
            // actualizar la UI con los datos obtenidos
            Log.e(
                "REOS",
                "ReasonDispatchViewModel-getReasonDispatch.result.observe.status"+status
            )
            _status.setValue(status)

        }
        Log.e(
            "REOS",
            "ReasonDispatchViewModel-getReasonDispatch-_status"+_status.getValue()
        )*/
    }

    fun getReasonDispatch()
    {
        viewModelScope.launch {
            reasonDispatchRepository.getReasonDispatch (context)
        }
    }
}