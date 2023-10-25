package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch

class HeaderDispatchSheetViewModel(
    private val headerDispatchSheetRepository: HeaderDispatchSheetRepository,
    private val context: Context,
    ) : ViewModel()  {

    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status

    private val _resultDB = MutableStateFlow(HeaderDispatchSheetEntity())
    val resultDB: StateFlow<HeaderDispatchSheetEntity> get() = _resultDB

    class HeaderDispatchSheetViewModelFactory(
            private val headerDispatchSheetRepository: HeaderDispatchSheetRepository,
            private val context: Context,
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HeaderDispatchSheetViewModel(
                    headerDispatchSheetRepository,
                    context,
            ) as T
        }
    }

    init{
        //getInvoices()
        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            headerDispatchSheetRepository.resultDB.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _resultDB.value = newResult
                Log.e("REOS", "FormularioTestViewModel-init-_result.value: " +_resultDB.value)
            }
        }
    }

    fun getStateDispatchSheet(Imei:String,FechaDespacho:String,context: Context,lifecycleOwner:LifecycleOwner)
    {
        Log.e(
            "REOS",
            "HeaderDispatchSheetViewModel-getStateDispatchSheet-fun"
        )
        viewModelScope.launch {
            Log.e(
                "REOS",
                "HeaderDispatchSheetViewModel-getStateDispatchSheet-Imei"+Imei
            )
            Log.e(
                "REOS",
                "HeaderDispatchSheetViewModel-getStateDispatchSheet-FechaDespacho"+FechaDespacho
            )
            headerDispatchSheetRepository.getStateDispatchSheet(Imei,FechaDespacho,context)
        }


        headerDispatchSheetRepository.status.observe(lifecycleOwner) { status ->
            // actualizar la UI con los datos obtenidos
            Log.e(
                "REOS",
                "HeaderDispatchSheetViewModel-getStateDispatchSheet.result.observe.status"+status
            )
            _status.setValue(status)

        }
        Log.e(
            "REOS",
            "HeaderDispatchSheetViewModel-getStateDispatchSheet-_status"+_status.getValue()
        )
    }

    fun getCodeDispatch(FechaDespacho:String,context: Context)
    {
        viewModelScope.launch {
            headerDispatchSheetRepository.getCodeDispatch(FechaDespacho,context)
        }
    }

    }


