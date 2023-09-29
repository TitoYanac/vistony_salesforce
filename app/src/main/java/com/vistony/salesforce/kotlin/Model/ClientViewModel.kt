package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ClientViewModel(
    private val clientRepository: ClientRepository
) : ViewModel()  {

    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status

    fun getClient(Imei:String,FechaDespacho:String, context: Context)
    {
        Log.e(
            "REOS",
            "ClientViewModel-getClient-fun"
        )
        viewModelScope.launch {
            Log.e(
                "REOS",
                "ClientViewModel-getClient-Imei"+Imei
            )
            Log.e(
                "REOS",
                "ClientViewModel-getClient-FechaDespacho"+FechaDespacho
            )
            clientRepository.getClient(Imei,FechaDespacho,context)
        }
        Log.e(
            "REOS",
            "ClientViewModel-getClient-_status"+_status.getValue()
        )
    }
}