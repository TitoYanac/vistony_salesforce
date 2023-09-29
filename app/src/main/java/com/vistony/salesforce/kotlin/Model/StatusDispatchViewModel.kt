package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StatusDispatchViewModel(
    private var statusDispatchRepository: StatusDispatchRepository,
    private var context: Context
): ViewModel() {

    private val _result_update = MutableStateFlow(ResponseStatusDispatch())
    val result_update: StateFlow<ResponseStatusDispatch> get() = _result_update

    class StatusDispatchViewModelFactory(
        private var statusDispatchRepository: StatusDispatchRepository,
        private var context: Context
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return StatusDispatchViewModel(
                statusDispatchRepository,
                context
            ) as T
        }
    }

    init{
        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            statusDispatchRepository.result_update.collect { newInvoices ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result_update.value = newInvoices
            }
        }
    }

    fun updateStatusDispatch(
        checkintime: String?, checkouttime: String?, latitud: String?, longitud: String?, domembarque_id: String?, cliente_id: String?
    )
    {
        Log.e(
            "REOS",
            "InvoicesViewModel-getInvoices-fun"
        )
        viewModelScope.launch {
            Log.e(
                "REOS",
                "InvoicesViewModel-getInvoices-cliente_id"+cliente_id
            )
            statusDispatchRepository.updateStatusDispatch(
                context,
                checkintime, checkouttime, latitud, longitud, domembarque_id, cliente_id
            )
        }
    }

    fun sendAPIStatusDispatch(
        context: Context
    )
    {
        Log.e(
            "REOS",
            "StatusDispatchViewModel-sendAPIStatusDispatch"
        )
        viewModelScope.launch {
            statusDispatchRepository.sendAPIStatusDispatch(
                context
            )
        }
    }

    fun sendAPIPhotoStatusDispatch(
        context: Context
    )
    {
        Log.e(
            "REOS",
            "StatusDispatchViewModel-sendAPIStatusDispatch"
        )
        viewModelScope.launch {
            statusDispatchRepository.sendPhotoAPIStatusDispatch(
                context
            )
        }
    }
}