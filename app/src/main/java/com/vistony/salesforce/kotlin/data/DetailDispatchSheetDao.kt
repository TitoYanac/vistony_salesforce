package com.vistony.salesforce.kotlin.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DetailDispatchSheetDao {
    @Query("SELECT * FROM detaildispatchsheet")
    fun getDetailDispatchSheet(): LiveData<List<DetailDispatchSheet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailDispatchSheet(DetailDispatchSheet: List<DetailDispatchSheet>?)
}