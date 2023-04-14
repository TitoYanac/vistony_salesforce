package com.vistony.salesforce.kotlin.data

import com.google.gson.annotations.SerializedName
import com.vistony.salesforce.Entity.Retrofit.Modelo.HeaderDispatchSheetEntity

data class HeaderDispatchSheetResponse
    (
    @SerializedName("Dispatch")
    val dispatch: List<HeaderDispatchSheet>
    )
{
    private var headerDispatchSheetEntity: List<HeaderDispatchSheet?>? = null

    fun getHeaderDispatchSheetEntity(): List<HeaderDispatchSheet?>? {
        return headerDispatchSheetEntity
    }

    fun setHeaderDispatchSheetEntity(headerDispatchSheetEntity: List<HeaderDispatchSheet?>?) {
        this.headerDispatchSheetEntity = headerDispatchSheetEntity
    }
}