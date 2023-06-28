package com.vistony.salesforce.kotlin.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectionDetailViewModel(
    private var Imei:String,
    private var context: Context,
    private var lifecycleOwner: LifecycleOwner,
    private var collectionDetailRepository: CollectionDetailRepository
):ViewModel() {
    private val _result_add = MutableStateFlow(Response())
    val result_add: StateFlow<Response> get() = _result_add

    class CollectionDetailViewModelFactory(
        private var Imei:String,
        private var context: Context,
        private var lifecycleOwner: LifecycleOwner,
        private var collectionDetailRepository: CollectionDetailRepository
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CollectionDetailViewModelFactory(
                Imei,context,lifecycleOwner,collectionDetailRepository
            ) as T
        }
    }

    /*init{
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
    }*/

    fun addCollectionDetail(
        invoices:Invoices,
        /*newBalance:String,
        collection:String,
        typeCollection:String,
        company:String,
        userid:String,
        cardName:String,
        commentary:String*/
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
            /*Log.e(
                "REOS",
                "InvoicesViewModel-getInvoices-cliente_id"+cliente_id
            )*/
            collectionDetailRepository.addListCollectionDetail(
                Imei,
                context,
                invoices,
                newBalance,
                collection,
                typeCollection,
                company,
                userid,
                cardName,
                commentary
            )
        }
    }

}