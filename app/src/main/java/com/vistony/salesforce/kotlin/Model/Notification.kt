package com.vistony.salesforce.kotlin.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "notification")
data class Notification(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var message: String?,
    var date: String?,
    var time: String?
)

data class NotificationEntity(
    var Status:String="",
    //@SerializedName("Notification")
    //var DATA: LiveData<List<Notification>> = emptyList(),
    val DATA: List<Notification> = (emptyList())
)