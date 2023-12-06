package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ClientViewModel(
    private val clientRepository: ClientRepository,
    private val Imei:String,
    private val context: Context
) : ViewModel()  {

    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status

    class ClientViewModelFactory(
        private val clientRepository: ClientRepository,
        private val Imei:String,
        private val context: Context
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ClientViewModel(
                clientRepository,
                Imei,
                context
            ) as T
        }
    }

    fun getMasterClientAPI(FechaDespacho:String)
    {
        viewModelScope.launch {
            clientRepository.getMasterClientAPI(Imei,FechaDespacho,context)
        }
    }
}