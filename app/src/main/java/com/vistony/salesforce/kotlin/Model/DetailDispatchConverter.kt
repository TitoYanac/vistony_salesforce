package com.vistony.salesforce.kotlin.Model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailDispatchConverter {
    @TypeConverter
    fun fromJson(json: String): List<DetailDispatchSheet> {
        val listType = object : TypeToken<List<DetailDispatchSheet>>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun toJson(details: List<DetailDispatchSheet>): String {
        return Gson().toJson(details)
    }
}