package com.vistony.salesforce.kotlin.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BankDao {
    @Query("SELECT * FROM bank ")
    fun getBank(): Flow<List<Bank>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBanks(banks: List<Bank> )

    @Query("DELETE FROM  bank")
    fun deleteBank()
}