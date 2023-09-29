package com.vistony.salesforce.kotlin.Model

import com.google.gson.annotations.SerializedName

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
