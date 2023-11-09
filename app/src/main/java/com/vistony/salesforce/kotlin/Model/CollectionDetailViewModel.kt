package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectionDetailViewModel(
    private var Imei:String,
    private var context: Context,
    //private var lifecycleOwner: LifecycleOwner,
    private var collectionDetailRepository: CollectionDetailRepository
):ViewModel() {
    private val _result_add = MutableStateFlow(CollectionDetailEntity())
    val result_add: StateFlow<CollectionDetailEntity> get() = _result_add

    private val _result_collection_unit = MutableStateFlow(CollectionDetailEntity())
    val result_collection_unit: StateFlow<CollectionDetailEntity> get() = _result_collection_unit

    private val _result_send_API = MutableStateFlow(CollectionDetailEntity())
    val result_send_API: StateFlow<CollectionDetailEntity> get() = _result_send_API

    private val _result_pending_deposit = MutableStateFlow(CollectionDetailEntity())
    val result_pending_deposit: StateFlow<CollectionDetailEntity> get() = _result_pending_deposit

    private val _result_get_API = MutableStateFlow(CollectionDetailEntity())
    val result_get_API: StateFlow<CollectionDetailEntity> get() = _result_get_API

    private val _result_get_DB = MutableStateFlow(CollectionDetailEntity())
    val result_get_DB: StateFlow<CollectionDetailEntity> get() = _result_get_DB

    class CollectionDetailViewModelFactory(
        private var Imei:String,
        private var context: Context,
        //private var lifecycleOwner: LifecycleOwner,
        private var collectionDetailRepository: CollectionDetailRepository
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CollectionDetailViewModel(
                Imei,context
                //,lifecycleOwner
                ,collectionDetailRepository
            ) as T
        }
    }

    init{
        //getInvoices()
        viewModelScope.launch {
            // Observar cambios en invoicesRepository.invoices
            collectionDetailRepository.result_add.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result_add.value = newResult
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-addListCollectionDetail-init-_result_add.value" + _result_add.value
                )
            }
        }
        viewModelScope.launch {
            collectionDetailRepository.result_collection_unit.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result_collection_unit.value = newResult
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-init-_result_add.value" + _result_collection_unit.value
                )
            }
        }
        viewModelScope.launch {
            collectionDetailRepository.result_collection_unit.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result_send_API.value = newResult
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-init-_result_send_API.value" + _result_send_API.value
                )
            }
        }
        viewModelScope.launch {
            collectionDetailRepository.result_pending_deposit.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result_pending_deposit.value = newResult
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-init-_result_pending_deposit.value"+_result_pending_deposit.value
                )
            }
        }

        viewModelScope.launch {
            collectionDetailRepository.result_get_API.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result_get_API.value = newResult
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-init-_result_get_API.value"+_result_get_API.value
                )
            }
        }


        viewModelScope.launch {
            collectionDetailRepository.result_get_DB.collect { newResult ->
                // Actualizar el valor de _invoices cuando haya cambios
                _result_get_DB.value = newResult
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-init-_result_get_DB"+_result_get_DB.value
                )
            }
        }
    }

    fun addListCollectionDetail(
        invoices:Invoices?,
        newBalance:String,
        collection:String,
        typeCollection:String,
        company:String,
        userid:String,
        cardName:String,
        commentary:String
    )
    {
        viewModelScope.launch {
            try {
                collectionDetailRepository.addListCollectionDetail(
                    Imei,
                    context,
                    invoices,
                    newBalance,
                    collection,
                    typeCollection,
                    company,
                    userid,
                    cardName,
                    commentary
                )
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-addListCollectionDetail-_result_add.value"+_result_add.value
                )
            }

            catch (e: Exception) {
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-addListCollectionDetail-error: " + e.toString()
                )
            }
        }
    }

    fun getCollectionDetailUnit(
        Receip:String,
        UserID:String,
        context: Context
    )
    {
        viewModelScope.launch {
            try {
                collectionDetailRepository.getCollectionDetailUnit(
                    Receip,
                    UserID,
                    context
                )
            }

            catch (e: Exception) {
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-getCollectionDetailUnit-error: " + e.toString()
                )
            }

        }
    }

    fun SendAPICollectionDetail(
        context: Context,CompanyCode:String,UserID: String
    )
    {
        viewModelScope.launch {
            try {
                collectionDetailRepository.SendAPICollectionDetail(
                    context,CompanyCode,UserID
                )
            }
            catch (e: Exception) {
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-SendAPICollectionDetail-error: " + e.toString()
                )
            }
        }
    }

    fun getCollectionDetailPendingDeposit(IncomeDate:String)
    {
        viewModelScope.launch {
            try {
                collectionDetailRepository.getCollectionDetailPendingDeposit(
                    IncomeDate,
                    context
                )
            }
            catch (e: Exception) {
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-getCollectionDetailPendingDeposit-error: " + e.toString()
                )
            }
        }
    }

    fun getAPICollectionDetail(
        UserID:String,
        Status:String,
    )
    {
        viewModelScope.launch {
            try {
                collectionDetailRepository.getAPICollectionDetail(
                    context, Imei, UserID, Status
                )
            }
            catch (e: Exception) {
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-getAPICollectionDetail-error: " + e.toString()
                )
            }
        }
    }

    fun getCountCollectionDetail(
    )
    {
        viewModelScope.launch {
            try {
                collectionDetailRepository.getCountCollectionDetail(
                    context
                )
            }
            catch (e: Exception) {
                Log.e(
                    "REOS",
                    "CollectionDetailViewModel-getCountCollectionDetail-error: " + e.toString()
                )
            }
        }
    }

    fun updateDepositCollectionDetail(
            collectionDetailList:List<CollectionDetail>,
            deposit:String,
            bank:String
    )
    {
        viewModelScope.launch {
            try {
                collectionDetailRepository.updateDepositCollectionDetail(
                        context,
                        collectionDetailList,
                        deposit,
                        bank
                )
            }
            catch (e: Exception) {
                Log.e(
                        "REOS",
                        "CollectionDetailViewModel-updateDepositCollectionDetail-error: " + e.toString()
                )
            }
        }
    }

    fun sendAPIUpdateDepositCollectionDetail()
    {
        viewModelScope.launch {
            try {
                collectionDetailRepository.sendAPIUpdateDepositCollectionDetail()
            }
            catch (e: Exception) {
                Log.e("REOS", "CollectionDetailViewModel-sendAPIUpdateDepositCollectionDetail-error: " + e.toString())
            }
        }
    }

    fun getCollectionDetailForDate(IncomeDate:String)
    {
        viewModelScope.launch {
            try {
                collectionDetailRepository.getCollectionDetailForDate(IncomeDate)
            }
            catch (e: Exception) {
                Log.e("REOS", "CollectionDetailViewModel-getCollectionDetailForDate-error: " + e.toString())
            }
        }
    }
}