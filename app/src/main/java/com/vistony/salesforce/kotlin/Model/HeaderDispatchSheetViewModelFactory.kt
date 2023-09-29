package com.vistony.salesforce.kotlin.Model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HeaderDispatchSheetViewModelFactory
    (private val repository: HeaderDispatchSheetRepository,
     private val Imei:String,
     private val Fecha:String,
     private val context: Context) : ViewModelProvider.Factory {

    /*override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e(
            "REOS",
            "HeaderDispatchSheetViewModelFactory-create.inicio"
        )
        Log.e(
            "REOS",
            "HeaderDispatchSheetViewModelFactory-create.Imei"+Imei
        )
        Log.e(
            "REOS",
            "HeaderDispatchSheetViewModelFactory-create.Fecha"+Fecha
        )

        if (modelClass.isAssignableFrom(HeaderDispatchSheetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HeaderDispatchSheetViewModel(repository,Imei,Fecha,context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }*/
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeaderDispatchSheetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HeaderDispatchSheetViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}