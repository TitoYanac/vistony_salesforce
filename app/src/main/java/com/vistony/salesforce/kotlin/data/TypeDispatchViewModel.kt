package com.vistony.salesforce.kotlin.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TypeDispatchViewModel (
    private val typeDispatchRepository: TypeDispatchRepository
) : ViewModel()  {
    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status

    fun getTypeDispatch(Imei:String, context: Context, lifecycleOwner: LifecycleOwner)
    {
        Log.e(
            "REOS",
            "TypeDispatchViewModel-getTypeDispatch-fun"
        )
        viewModelScope.launch {
            Log.e(
                "REOS",
                "TypeDispatchViewModel-getTypeDispatch-Imei"+Imei
            )
            typeDispatchRepository.getTypeDispatch(Imei,context)
        }


        typeDispatchRepository.status.observe(lifecycleOwner) { status ->
            // actualizar la UI con los datos obtenidos
            Log.e(
                "REOS",
                "TypeDispatchViewModel-getTypeDispatch.result.observe.status"+status
            )
            _status.setValue(status)

        }
        Log.e(
            "REOS",
            "TypeDispatchViewModel-getTypeDispatch-_status"+_status.getValue()
        )
    }

}