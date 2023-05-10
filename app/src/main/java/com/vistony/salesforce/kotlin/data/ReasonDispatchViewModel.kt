package com.vistony.salesforce.kotlin.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ReasonDispatchViewModel  (
    private val reasonDispatchRepository: ReasonDispatchRepository
) : ViewModel()
{
    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status

    fun getReasonDispatch(Imei:String, context: Context, lifecycleOwner: LifecycleOwner)
    {
        Log.e(
            "REOS",
            "ReasonDispatchViewModel-getReasonDispatch-fun"
        )
        viewModelScope.launch {
            Log.e(
                "REOS",
                "ReasonDispatchViewModel-getReasonDispatch-Imei"+Imei
            )
            reasonDispatchRepository.getReasonDispatch (Imei,context)
        }


        reasonDispatchRepository.status.observe(lifecycleOwner) { status ->
            // actualizar la UI con los datos obtenidos
            Log.e(
                "REOS",
                "ReasonDispatchViewModel-getReasonDispatch.result.observe.status"+status
            )
            _status.setValue(status)

        }
        Log.e(
            "REOS",
            "ReasonDispatchViewModel-getReasonDispatch-_status"+_status.getValue()
        )
    }
}