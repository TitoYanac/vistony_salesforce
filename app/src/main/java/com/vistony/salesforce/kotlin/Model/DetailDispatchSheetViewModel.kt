package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailDispatchSheetViewModel
    (
    private val detailDispatchSheetRepository: DetailDispatchSheetRepository,
    private val context: Context
    ): ViewModel()
{
    private val _resultDB = MutableStateFlow(DetailDispatchSheetEntity())
    val resultDB: StateFlow<DetailDispatchSheetEntity> get() = _resultDB

    class DetailDispatchSheetViewModelFactory(
        private val detailDispatchSheetRepository: DetailDispatchSheetRepository,
        private val context: Context
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailDispatchSheetViewModel(
                detailDispatchSheetRepository,
                context
            ) as T
        }
    }


    init{
        //getInvoices()
        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            detailDispatchSheetRepository.resultDB.collect { newResultGet ->
                // Actualizar el valor de _invoices cuando haya cambios
                _resultDB.value = newResultGet
                Log.e("REOS", "NotificationViewModel-getNotification-_resultDB.value: " +_resultDB.value)
            }
        }
    }

    fun getStateDetailDispatchSheet(
        Imei:String,
        FechaDespacho:String,
        context: Context,
        lifecycleOwner: LifecycleOwner,
        type: String

    )
    {
        viewModelScope.launch {
            detailDispatchSheetRepository.getStateDispatchSheet(Imei,FechaDespacho,context,type)
        }
        /*detailDispatchSheetRepository.status.observe(lifecycleOwner) { data ->
            // actualizar la UI con los datos obtenidos
            _status.setValue(data)
        }*/
    }
}