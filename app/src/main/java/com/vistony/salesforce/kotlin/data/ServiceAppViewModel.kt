package com.vistony.salesforce.kotlin.data

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ServiceAppViewModel(
    private var serviceAppRepository: ServiceAppRepository,
    private var context: Context
) : ViewModel()
{
    private val _result_get = MutableStateFlow(ResponseTypeDispatch())
    val result_get: StateFlow<ResponseTypeDispatch> get() = _result_get




}