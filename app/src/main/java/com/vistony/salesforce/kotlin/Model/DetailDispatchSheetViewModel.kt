package com.vistony.salesforce.kotlin.Model

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetailDispatchSheetViewModel
    (private val detailDispatchSheetRepository: DetailDispatchSheetRepository): ViewModel()
{
    private val _status = MutableLiveData<List<DetailDispatchSheet>>()
    val status: MutableLiveData<List<DetailDispatchSheet>> = _status

    fun getStateDetailDispatchSheet(
        Imei:String,
        FechaDespacho:String,
        context: Context,
        lifecycleOwner: LifecycleOwner,
        type: String

    )
    {
       /* Log.e(
            "REOS",
            "DetailDispatchSheetViewModel-getStateDetailDispatchSheet-fun"
        )*/
        viewModelScope.launch {
            /*Log.e(
                "REOS",
                "DetailDispatchSheetViewModel-getStateDetailDispatchSheet-Imei"+Imei
            )
            Log.e(
                "REOS",
                "DetailDispatchSheetViewModel-getStateDetailDispatchSheet-FechaDespacho"+FechaDespacho
            )*/
            detailDispatchSheetRepository.getStateDispatchSheet(Imei,FechaDespacho,context,type)
        }


        detailDispatchSheetRepository.status.observe(lifecycleOwner) { data ->
            // actualizar la UI con los datos obtenidos
            /*Log.e(
                "REOS",
                "DetailDispatchSheetViewModel-getStateDetailDispatchSheet.result.observe.data.size"+data.size
            )*/
            _status.setValue(data)

        }
        /*Log.e(
            "REOS",
            "DetailDispatchSheetViewModel-getStateDetailDispatchSheet-_status"+_status.getValue()
        )*/
    }
}