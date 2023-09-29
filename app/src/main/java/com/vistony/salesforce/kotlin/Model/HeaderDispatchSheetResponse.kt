package com.vistony.salesforce.kotlin.Model

import com.google.gson.annotations.SerializedName

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