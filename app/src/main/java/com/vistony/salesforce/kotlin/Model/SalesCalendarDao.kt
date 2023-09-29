package com.vistony.salesforce.kotlin.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SalesCalendarDao {
    @Query("SELECT * FROM salescalendar where Code=:Date")
    fun getSalesCalendar(Date:String): Flow<List<SalesCalendar>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSalesCalendar(address: List<SalesCalendar>?)

    @Query("DELETE FROM  salescalendar")
    fun deleteSalesCalendar()
}