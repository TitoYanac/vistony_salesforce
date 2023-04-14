package com.vistony.salesforce.kotlin.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InvoicesDao {
    @Query("SELECT * FROM documentodeuda")
    fun getInvoices(): LiveData<List<Invoices>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvoices(invoices: List<Invoices>?)
}