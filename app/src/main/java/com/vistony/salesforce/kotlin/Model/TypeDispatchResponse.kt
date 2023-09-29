package com.vistony.salesforce.kotlin.Model

import com.google.gson.annotations.SerializedName

class TypeDispatchResponse
{
    @SerializedName("DispatchTypes")
    private var typeDispatch: List<TypeDispatch>? = null

    fun setTypeDispatch(typeDispatch: List<TypeDispatch>?)
    {
        this.typeDispatch = typeDispatch
    }

    fun getTypeDispatch(): List<TypeDispatch>? {
        return typeDispatch
    }
}