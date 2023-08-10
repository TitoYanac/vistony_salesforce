package com.vistony.salesforce.kotlin.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class VisitSectionViewModel(
    private val visitSectionRepository: VisitSectionRepository
): ViewModel()  {
    private val _list = MutableLiveData<List<VisitSection>>()
    val list: MutableLiveData<List<VisitSection>> = _list

    private val _status = MutableLiveData<String>()
    val status: MutableLiveData<String> = _status


    fun getVisitSection(
        Imei:String,
        context: Context,
        lifecycleOwner: LifecycleOwner,
        cliente_id:String,
        domembarque_id:String,
        dateini:String,
        idref:String

    )
    {
        Log.e(
            "REOS",
            "VisitSectionViewModel-getVisitSection-fun"
        )
        viewModelScope.launch {
            Log.e(
                "REOS",
                "VisitSectionViewModel-getVisitSection-Imei"+Imei
            )
            visitSectionRepository.getVisitSection(
                Imei,
                context,
                cliente_id,
                domembarque_id,
                dateini,
                idref
            )
        }


        visitSectionRepository.list.observe(lifecycleOwner) { status ->
            // actualizar la UI con los datos obtenidos
            Log.e(
                "REOS",
                "VisitSectionViewModel-getVisitSection.result.observe.status"+status
            )
            _list.setValue(status)

        }
        Log.e(
            "REOS",
            "VisitSectionViewModel-getVisitSection-_list"+_list.getValue()
        )
    }

    fun addVisitSection(
        list:MutableList<VisitSection>?,
        lifecycleOwner: LifecycleOwner,
        context: Context
    )
    {
        Log.e(
            "REOS",
            "VisitSectionViewModel-addVisitSection-fun"
        )
        Log.e(
            "REOS",
            "VisitSectionViewModel-addVisitSection-list.size()"+list?.size
        )

        viewModelScope.launch {

            visitSectionRepository.addVisitSection(
                list,
                context
            )
        }


        visitSectionRepository.status.observe(lifecycleOwner) { status ->
            // actualizar la UI con los datos obtenidos
            Log.e(
                "REOS",
                "VisitSectionViewModel-addVisitSection.result.observe.status"+status
            )
            _status.setValue(status)

        }
        Log.e(
            "REOS",
            "VisitSectionViewModel-addVisitSection-_status"+_status.getValue()
        )
    }

    fun updateVisitSection(
        list:List<VisitSection>?,
        lifecycleOwner: LifecycleOwner,
        context: Context
    )
    {
        Log.e(
            "REOS",
            "VisitSectionViewModel-addVisitSection-fun"
        )
        Log.e(
            "REOS",
            "VisitSectionViewModel-addVisitSection-list.size()"+list?.size
        )

        viewModelScope.launch {

            visitSectionRepository.updateVisitSection(
                list,
                context
            )
        }



        visitSectionRepository.status.observe(lifecycleOwner) { status ->
            // actualizar la UI con los datos obtenidos
            Log.e(
                "REOS",
                "VisitSectionViewModel-addVisitSection.result.observe.status"+status
            )
            _status.setValue(status)

        }
        Log.e(
            "REOS",
            "VisitSectionViewModel-addVisitSection-_status"+_status.getValue()
        )
    }
}