package com.vistony.salesforce.kotlin.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "salescalendar")
data class SalesCalendar (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var Code: String?,
    var Year: String?,
    var Month:String?,
    var Day:String?,
    var Habil:String?
)

data class SalesCalendarEntity(
    var Status:String="",
    @SerializedName("SalesCalendar")
    var Data: List<SalesCalendar>? = emptyList(),
)