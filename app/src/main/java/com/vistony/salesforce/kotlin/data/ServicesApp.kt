package com.vistony.salesforce.kotlin.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicebackground")
data class ServicesApp (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var description: String?,
    var type: String?,
    var status:String?,
    var startTime:String?,
    var endTime:String?,
    var interval:String?
)