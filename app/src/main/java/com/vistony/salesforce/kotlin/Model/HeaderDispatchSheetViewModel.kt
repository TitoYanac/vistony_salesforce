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
    private val Imei: String,
    ) : ViewModel()  {

    private val _resultDB = MutableStateFlow(HeaderDispatchSheetEntity())
    val resultDB: StateFlow<HeaderDispatchSheetEntity> get() = _resultDB

    private val _resultAPI = MutableStateFlow(HeaderDispatchSheetEntity())
    val resultAPI: StateFlow<HeaderDispatchSheetEntity> get() = _resultAPI
    class HeaderDispatchSheetViewModelFactory(
            private val headerDispatchSheetRepository: HeaderDispatchSheetRepository,
            private val context: Context,
            private val Imei: String,
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HeaderDispatchSheetViewModel(
                    headerDispatchSheetRepository,
                    context,
                    Imei
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
                Log.e("REOS", "HeaderDispatchSheetViewModel-init-_resultDB.value: " +_resultDB.value)
            }
        }
        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            headerDispatchSheetRepository.resultAPI.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _resultDB.value = newResult
                Log.e("REOS", "HeaderDispatchSheetViewModel-init-resultAPI.value: " +resultAPI.value)
            }
        }
    }

    fun resetgetMasterDispatchSheetDB(){
        _resultDB.value=HeaderDispatchSheetEntity()
    }

    fun getMasterDispatchSheetAPI(FechaDespacho:String)
    {
        viewModelScope.launch {
            headerDispatchSheetRepository.getMasterDispatchSheetAPI(Imei,FechaDespacho,context)
        }
    }

    fun getMasterDispatchSheetDB(FechaDespacho:String)
    {
        Log.e("REOS", "HeaderDispatchSheetViewModel-getMasterDispatchSheetDB-FechaDespacho: " +FechaDespacho)
        viewModelScope.launch {
            headerDispatchSheetRepository.getMasterDispatchSheetDB(FechaDespacho,context)
        }
    }

    }


