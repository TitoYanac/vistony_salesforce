package com.vistony.salesforce.kotlin.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch

class InvoicesViewModel(
    private var Imei:String,
    private var context: Context,
    private var lifecycleOwner: LifecycleOwner,
    private var cliente_id:String,
    private var invoicesRepository: InvoicesRepository
): ViewModel()  {

    private val _invoices = MutableStateFlow<List<Invoices>>(emptyList())
    val invoices: StateFlow<List<Invoices>> get() = _invoices

    class InvoiceModelFactory(
        private var Imei:String,
        private var context: Context,
        private var lifecycleOwner: LifecycleOwner,
        private var cliente_id:String,
        private var invoicesRepository: InvoicesRepository
        ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return InvoicesViewModel(
                Imei,context,lifecycleOwner,cliente_id,invoicesRepository
            ) as T
        }
    }

     init{
         //getInvoices()
         viewModelScope.launch {
             // Observar cambios en invoicesRepository.invoices
             invoicesRepository.invoices.collect { newInvoices ->
                 // Actualizar el valor de _invoices cuando haya cambios
                 _invoices.value = newInvoices
             }
         }
         Log.e(
             "REOS",
             "InvoicesViewModel-getInvoices-_invoices.size"+_invoices.value
         )
    }



    /*fun resetClient(){
        _invoices.value= List<Invoices>(0)
    }*/


    fun getInvoices(
        cliente_id: String
    )
    {
        Log.e(
            "REOS",
            "InvoicesViewModel-getInvoices-fun"
        )
        viewModelScope.launch {
            Log.e(
                "REOS",
                "InvoicesViewModel-getInvoices-Imei"+Imei
            )
            Log.e(
                "REOS",
                "InvoicesViewModel-getInvoices-cliente_id"+cliente_id
            )
            invoicesRepository.getInvoices(
                Imei,
                context,
                cliente_id
            )
        }
    }
}