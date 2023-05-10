package com.vistony.salesforce.kotlin.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class VisitSectionViewModel(
    private val visitSectionRepository: VisitSectionRepository
): ViewModel()  {
    private val _status = MutableLiveData<List<VisitSection>>()
    val status: MutableLiveData<List<VisitSection>> = _status

    fun getVisitSection(
        Imei:String,
        context: Context,
        lifecycleOwner: LifecycleOwner,
        cliente_id:String,
        domembarque_id:String,
        dateini:String,
        idref:String

    )
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
            visitSectionRepository.getVisitSection(
                Imei,
                context,
                cliente_id,
                domembarque_id,
                dateini,
                idref
            )
        }


        visitSectionRepository.status.observe(lifecycleOwner) { status ->
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