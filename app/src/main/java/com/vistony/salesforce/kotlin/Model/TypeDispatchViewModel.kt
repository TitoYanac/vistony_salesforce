package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TypeDispatchViewModel (
    private var Imei:String,
    private var context: Context,
    private val typeDispatchRepository: TypeDispatchRepository,

) : ViewModel()  {
    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status

    private val _result_get = MutableStateFlow(ResponseTypeDispatch())
    val result_get: StateFlow<ResponseTypeDispatch> get() = _result_get

    class TypeDispatchViewModelFactory(
        private var Imei:String,
        private var context: Context,
        private val typeDispatchRepository: TypeDispatchRepository
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TypeDispatchViewModel(
                Imei,context,typeDispatchRepository
            ) as T
        }
    }

    init{
        //getInvoices()
        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            typeDispatchRepository.result_get.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result_get.value = newResult
            }
        }
    }

    fun addTypeDispatch()
    {
        Log.e(
            "REOS",
            "TypeDispatchViewModel-getTypeDispatch-fun"
        )
        viewModelScope.launch {
            Log.e(
                "REOS",
                "TypeDispatchViewModel-getTypeDispatch-Imei"+Imei
            )
            typeDispatchRepository.addTypeDispatch(Imei,context)
        }


        /*typeDispatchRepository.status.observe(lifecycleOwner) { status ->
            // actualizar la UI con los datos obtenidos
            Log.e(
                "REOS",
                "TypeDispatchViewModel-getTypeDispatch.result.observe.status"+status
            )
            _status.setValue(status)
        }
        Log.e(
            "REOS",
            "TypeDispatchViewModel-getTypeDispatch-_status"+_status.getValue()
        )*/
    }

    fun getTypeDispatch(
        context: Context
    )
    {
        viewModelScope.launch {
            typeDispatchRepository.getTypeDispatch(
                context
            )
        }
    }

}