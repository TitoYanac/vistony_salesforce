package com.vistony.salesforce.kotlin.Model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class InvoicesConverter {
    @TypeConverter
    fun fromJson(json: String): List<Invoices>? {
        val listType = object : TypeToken<List<Invoices>?>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun toJson(details: List<Invoices>?): String {
        return Gson().toJson(details)
    }
}