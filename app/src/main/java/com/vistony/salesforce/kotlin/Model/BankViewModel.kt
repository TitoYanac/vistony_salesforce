package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BankViewModel(
    private val bankRepository: BankRepository,
    private val context: Context,
    private val imei:String
):ViewModel()
{
    private val _resultAPI = MutableStateFlow(BankEntity())
    val resultAPI: StateFlow<BankEntity> get() = _resultAPI

    private val _resultDB = MutableStateFlow(BankEntity())
    val resultDB: StateFlow<BankEntity> get() = _resultDB

    class BankViewModelFactory(
        private val bankRepository: BankRepository,
        private val context: Context,
        private val imei:String
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BankViewModel(
                bankRepository,
                context,
                imei
            ) as T
        }
    }

    init{
        //getInvoices()
        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            bankRepository.resultDB.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _resultDB.value = newResult
                Log.e("REOS", "NotificationViewModel-getNotification-_result.value: " +_resultDB.value)
            }
        }
    }

    fun getAddBanks()
    {
        viewModelScope.launch {
            bankRepository.getAddBanks(imei,context)
        }
    }

    fun getBanks()
    {
        viewModelScope.launch {
            bankRepository.getBanks(context)
        }
    }

}