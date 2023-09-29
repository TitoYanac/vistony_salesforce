package com.vistony.salesforce.kotlin.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "service")
data class ServiceApp (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var Description: String?,
    var DocEntry: String?,
    var Status:String?,
    var StartTime:String?,
    var EndTime:String?,
    var Interval:String?
)

data class ServiceAppEntity(
    var Status:String="",
    @SerializedName("Service")
    var Data: List<ServiceApp>? = emptyList(),
)