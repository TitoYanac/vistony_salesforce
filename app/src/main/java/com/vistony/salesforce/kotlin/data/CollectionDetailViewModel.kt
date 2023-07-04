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
    //private var lifecycleOwner: LifecycleOwner,
    private var collectionDetailRepository: CollectionDetailRepository
):ViewModel() {
    private val _result_add = MutableStateFlow(ResponseCollectionDetail())
    val result_add: StateFlow<ResponseCollectionDetail> get() = _result_add

    private val _result_collection_unit = MutableStateFlow(ResponseCollectionDetail())
    val result_collection_unit: StateFlow<ResponseCollectionDetail> get() = _result_collection_unit

    private val _result_send_API = MutableStateFlow(ResponseCollectionDetail())
    val result_send_API: StateFlow<ResponseCollectionDetail> get() = _result_send_API

    class CollectionDetailViewModelFactory(
        private var Imei:String,
        private var context: Context,
        //private var lifecycleOwner: LifecycleOwner,
        private var collectionDetailRepository: CollectionDetailRepository
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CollectionDetailViewModel(
                Imei,context
                //,lifecycleOwner
                ,collectionDetailRepository
            ) as T
        }
    }

    init{
        //getInvoices()
        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            collectionDetailRepository.result_add.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result_add.value = newResult
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-addListCollectionDetail-init-_result_add.value"+_result_add.value
                )
            }

            collectionDetailRepository.result_collection_unit.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result_collection_unit.value = newResult
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-init-_result_add.value"+_result_collection_unit.value
                )
            }

            collectionDetailRepository.result_collection_unit.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result_send_API.value = newResult
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-init-_result_send_API.value"+_result_send_API.value
                )
            }

        }
    }

    fun addListCollectionDetail(
        invoices:Invoices?,
        newBalance:String,
        collection:String,
        typeCollection:String,
        company:String,
        userid:String,
        cardName:String,
        commentary:String
    )
    {
        viewModelScope.launch {
            try {
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
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-addListCollectionDetail-_result_add.value"+_result_add.value
                )
            }

            catch (e: Exception) {
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-addListCollectionDetail-error: " + e.toString()
                )
            }

        }
    }

    fun getCollectionDetailUnit(
        Receip:String,
        UserID:String,
        context: Context
    )
    {
        viewModelScope.launch {
            try {
                collectionDetailRepository.getCollectionDetailUnit(
                    Receip,
                    UserID,
                    context
                )
            }

            catch (e: Exception) {
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-getCollectionDetailUnit-error: " + e.toString()
                )
            }

        }
    }

    fun SendAPICollectionDetail(
        context: Context,CompanyCode:String,UserID: String
    )
    {
        viewModelScope.launch {
            try {
                collectionDetailRepository.SendAPICollectionDetail(
                    context,CompanyCode,UserID
                )
            }

            catch (e: Exception) {
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-SendAPICollectionDetail-error: " + e.toString()
                )
            }

        }
    }
}