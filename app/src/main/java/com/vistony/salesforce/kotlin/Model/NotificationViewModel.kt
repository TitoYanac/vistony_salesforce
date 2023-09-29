package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(
    private var notificationRepository: NotificationRepository,
    private var context: Context
): ViewModel()  {
    private val _resultDB = MutableStateFlow(NotificationEntity())
    val resultDB: StateFlow<NotificationEntity> get() = _resultDB

    class NotificationViewModelFactory(
        private val notificationRepository: NotificationRepository,
        private var context: Context
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NotificationViewModel(
                notificationRepository,
                context
            ) as T
        }
    }

    init{
        //getInvoices()
        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            notificationRepository.resultDB.collect { newResultGet ->
                // Actualizar el valor de _invoices cuando haya cambios
                _resultDB.value = newResultGet
                Log.e("REOS", "NotificationViewModel-getNotification-_resultDB.value: " +_resultDB.value)
            }
        }
    }

    fun addNotification(notificationList: List<Notification>?)
    {
        viewModelScope.launch {
            //notificationRepository.addNotification (context,notificationList)
        }
    }

    fun getNotification(startDate: String,endDate:String)
    {
        Log.e("REOS", "NotificationViewModel-lgetNotification-startDate: " +startDate)
        Log.e("REOS", "NotificationViewModel-lgetNotification-endDate: " +endDate)
        viewModelScope.launch {
            notificationRepository.getNotification (context,startDate,endDate)
        }
    }

    /*fun getNotificationLiveData(startDate: String,endDate:String)
    {
        Log.e("REOS", "NotificationViewModel-lgetNotification-startDate: " +startDate)
        Log.e("REOS", "NotificationViewModel-lgetNotification-endDate: " +endDate)
        viewModelScope.launch {
            notificationLiveData=notificationRepository.getNotificationLiveData(context = context, startDate = startDate,endDate=endDate)
        }
    }*/
}