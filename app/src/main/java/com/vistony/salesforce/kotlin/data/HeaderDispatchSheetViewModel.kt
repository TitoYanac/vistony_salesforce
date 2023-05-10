package com.vistony.salesforce.kotlin.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*

import com.vistony.salesforce.kotlin.api.RetrofitApi
import com.vistony.salesforce.kotlin.api.RetrofitConfig
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeaderDispatchSheetViewModel(
    private val headerDispatchSheetRepository: HeaderDispatchSheetRepository
    ) : ViewModel()  {

    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status

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



    }


