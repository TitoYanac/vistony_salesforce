package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectionHeadViewModel (
        private var context: Context,
        private var collectionHeadRepository: CollectionHeadRepository
): ViewModel() {
    private val _result_DB = MutableStateFlow(CollectionHeadEntity())
    val result_DB: StateFlow<CollectionHeadEntity> get() = _result_DB

    class CollectionHeadViewModelFactory(
            private var context: Context,
            private var collectionHeadRepository: CollectionHeadRepository
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CollectionHeadViewModel(
                    context,collectionHeadRepository
            ) as T
        }
    }

    init {
        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            collectionHeadRepository.result_DB.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result_DB.value = newResult
            }
        }
    }

    fun addCollectionHead(collectionHead: CollectionHead)
    {
        viewModelScope.launch {
            try {
                collectionHeadRepository.addCollectionHead(context,collectionHead)
            }
            catch (e: Exception) {
                Log.e("REOS", "CollectionHeadViewModel-addCollectionHead-error: " + e.toString())
            }
        }
    }

    fun sendAPICollectionHead()
    {
        viewModelScope.launch {
            try {
                collectionHeadRepository.sendAPICollectionHead(context)
            }
            catch (e: Exception) {
                Log.e("REOS", "CollectionHeadViewModel-addCollectionHead-error: " + e.toString())
            }
        }
    }

    fun getCollectionHeadForDate(startDate:String,endDate:String)
    {
        viewModelScope.launch {
            try {
                collectionHeadRepository.getCollectionHeadForDate(startDate,endDate)
            }
            catch (e: Exception) {
                Log.e("REOS", "CollectionHeadViewModel-getCollectionHeadForDate-error: " + e.toString())
            }
        }
    }

    fun updateCancelCollectionHead(deposit:String,date:String,comment:String)
    {
        viewModelScope.launch {
            try {
                collectionHeadRepository.updateCancelCollectionHead(deposit,date,comment)
            }
            catch (e: Exception) {
                Log.e("REOS", "CollectionHeadViewModel-updateCancelCollectionHead-error: " + e.toString())
            }
        }
    }

    fun sendAPICancelCollectionHead()
    {
        viewModelScope.launch {
            try {
                collectionHeadRepository.sendAPICancelDepositCollectionHead()
            }
            catch (e: Exception) {
                Log.e("REOS", "CollectionHeadViewModel-sendAPICancelCollectionHead-error: " + e.toString())
            }
        }
    }
}