package com.vistony.salesforce.kotlin.data

import com.google.gson.annotations.SerializedName

class ReasonDispatchResponse {
    @SerializedName("Ocurrencies")
    private var reasonDispatch: List<ReasonDispatch>? = null

    fun setReasonDispatch(typeDispatch: List<ReasonDispatch>?)
    {
        this.reasonDispatch = reasonDispatch
    }

    fun getReasonDispatch(): List<ReasonDispatch>? {
        return reasonDispatch
    }
}