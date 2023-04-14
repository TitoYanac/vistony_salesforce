package com.vistony.salesforce.kotlin.data

import com.google.gson.annotations.SerializedName
import com.vistony.salesforce.Entity.Retrofit.Modelo.AgenciaEntity

class DispatchSheetResponse(
){
    @SerializedName("Dispatch")
    private var headerDispatchSheet: List<HeaderDispatchSheet>? = null

    fun setDispatchSheetEntity(headerDispatchSheet: List<HeaderDispatchSheet>?)
    {
        this.headerDispatchSheet = headerDispatchSheet
    }

    fun getDispatchSheetEntity(): List<HeaderDispatchSheet>? {
        return headerDispatchSheet
    }
}
