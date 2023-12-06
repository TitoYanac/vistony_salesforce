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
    private val context: Context,
    private val Imei:String,
    ): ViewModel()
{
    private val _result = MutableStateFlow(DetailDispatchSheetEntity())
    val result: StateFlow<DetailDispatchSheetEntity> get() = _result

    private val _resultDB = MutableLiveData<DetailDispatchSheetEntity>()
    val resultDB: LiveData<DetailDispatchSheetEntity> get() = _resultDB

    class DetailDispatchSheetViewModelFactory(
        private val detailDispatchSheetRepository: DetailDispatchSheetRepository,
        private val context: Context,
        private val Imei:String,
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailDispatchSheetViewModel(
                detailDispatchSheetRepository,
                context,
                Imei
            ) as T
        }
    }


    init{
        //getInvoices()
        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            //detailDispatchSheetRepository.resultDB.collect { newResultGet ->
                detailDispatchSheetRepository.resultDB.observeForever { newResultGet ->
                // Actualizar el valor de _invoices cuando haya cambios
                _resultDB.value = newResultGet
                Log.e("REOS", "DetailDispatchSheetViewModel-newResultGet: " +newResultGet)
            }
        }

        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            detailDispatchSheetRepository.result.collect { newResultGet ->
            //detailDispatchSheetRepository.resultDB.observeForever { newResultGet ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result.value = newResultGet
                Log.e("REOS", "DetailDispatchSheetViewModel-newResultGet: " +newResultGet)
            }
        }
    }

    fun resetDetailDispatchSheet()
    {
        viewModelScope.launch {
            _resultDB.value = DetailDispatchSheetEntity()
        }
    }
    fun getStateDetailDispatchSheet(
        FechaDespacho:String,
        type: String
    )
    {
        viewModelScope.launch {
            detailDispatchSheetRepository.getStateDispatchSheet(Imei,FechaDespacho,context,type)
        }
    }

}