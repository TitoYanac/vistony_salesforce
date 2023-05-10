package com.vistony.salesforce.kotlin.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TypeDispatchDao {
    @Query("SELECT * FROM typedispatch")
    fun getTypeDispatch(): LiveData<List<TypeDispatch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserTypeDispatch(invoices: List<TypeDispatch>?)

    @Query("DELETE FROM typedispatch")
    fun deleteTypeDispatch()
}