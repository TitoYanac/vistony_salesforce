package com.vistony.salesforce.kotlin.Model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SalesCalendarViewModel(
    private var salesCalendarRepository: SalesCalendarRepository,
    private var context: Context
) : ViewModel() {
    private val _result = MutableStateFlow(SalesCalendarEntity())
    val result: StateFlow<SalesCalendarEntity> get() = _result

    class SalesCalendarViewModelFactory(
        private val salesCalendarRepository: SalesCalendarRepository,
        private var context: Context
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SalesCalendarViewModel(
                salesCalendarRepository,
                context
            ) as T
        }
    }

    init{
        //getInvoices()
        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            salesCalendarRepository.result.collect { newResultGet ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result.value = newResultGet
            }
        }
    }

    fun addSalesCalendar(Imei:String
                         ,from:String
                         ,to:String)
    {
        viewModelScope.launch {
            salesCalendarRepository.addSalesCalendar (Imei,context,from,to)
        }
    }

    fun getSalesCalendar(Date:String)
    {
        viewModelScope.launch {
            salesCalendarRepository.getSalesCalendar (context,Date)
        }
    }
}