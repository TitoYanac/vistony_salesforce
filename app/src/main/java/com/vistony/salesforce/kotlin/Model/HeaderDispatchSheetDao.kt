package com.vistony.salesforce.kotlin.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HeaderDispatchSheetDao {
    @Query("SELECT * FROM headerdispatchsheet")
    fun getHeaderDispatchSheet(): LiveData<List<HeaderDispatchSheet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeaderDispatchSheet(HeaderDispatchSheet: List<HeaderDispatchSheet>?)

    @Query("DELETE FROM headerdispatchsheet WHERE fechahojadespacho = :fechahojadespacho")
    fun deleteHeaderDispatchSheet(fechahojadespacho: String)

}