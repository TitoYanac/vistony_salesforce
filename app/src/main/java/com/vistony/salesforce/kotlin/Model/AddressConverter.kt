package com.vistony.salesforce.kotlin.Model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddressConverter {
    @TypeConverter
    fun fromJson(json: String): List<Address>? {
        val listType = object : TypeToken<List<Address>>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun toJson(details: List<Address>?): String {
        return Gson().toJson(details)
    }
}