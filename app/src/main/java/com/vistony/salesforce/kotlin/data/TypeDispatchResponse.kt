package com.vistony.salesforce.kotlin.data

import com.google.gson.annotations.SerializedName
import com.vistony.salesforce.Entity.Retrofit.Modelo.TypeDispatchEntity

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