package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServiceAppViewModel(
    private var serviceAppRepository: ServiceAppRepository,
    private var context: Context,
    private var Imei: String
) : ViewModel()
{
    private val _resultAPI = MutableStateFlow(ServiceAppEntity())
    val resultAPI: StateFlow<ServiceAppEntity> get() = _resultAPI

    private val _resultDB = MutableStateFlow(ServiceAppEntity())
    val resultDB: StateFlow<ServiceAppEntity> get() = _resultDB

    class ServiceAppViewModelFactory(
        private val serviceAppRepository: ServiceAppRepository,
        private var context: Context,
        private var Imei: String
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ServiceAppViewModel(
                serviceAppRepository,
                context,Imei
            ) as T
        }
    }

    init{
        //getInvoices()
        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            serviceAppRepository.resultDB.collect { newResultGet ->
                // Actualizar el valor de _invoices cuando haya cambios
                _resultDB.value = newResultGet
            }
        }
    }

    fun addServiceApp()
    {
        viewModelScope.launch {
            serviceAppRepository.addServiceApp (Imei,context)
        }
    }

    fun getServiceApp(DocEntry:String)
    {
        viewModelScope.launch {
            serviceAppRepository.getServiceApp (context,DocEntry)
        }
    }

    fun getResultJAVA(): StateFlow<ServiceAppEntity> {
        return resultDB
    }
}