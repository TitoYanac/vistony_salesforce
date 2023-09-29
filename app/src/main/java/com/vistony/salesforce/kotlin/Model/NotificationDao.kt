package com.vistony.salesforce.kotlin.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notification where date>=:startDate and date<=:endDate")
    fun getNotification(startDate:String,endDate:String): Flow<List<Notification>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNotification(notification: Notification)

    @Query("DELETE FROM  notification")
    fun deleteNotification()
}