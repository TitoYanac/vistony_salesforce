package com.vistony.salesforce.kotlin.Model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HeaderDispatchSheetViewModelFactory
    (private val repository: HeaderDispatchSheetRepository,
     private val Imei:String,
     private val Fecha:String,
     private val context: Context) : ViewModelProvider.Factory {


}