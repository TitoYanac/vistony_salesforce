package com.vistony.salesforce.kotlin.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReasonDispatchDao {
    @Query("SELECT * FROM reasondispatch")
    fun getReasonDispatch(): List<ReasonDispatch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserReasonDispatch(reasondispatch: List<ReasonDispatch>?)

    @Query("DELETE FROM reasondispatch")
    fun deleteReasonDispatch()
}