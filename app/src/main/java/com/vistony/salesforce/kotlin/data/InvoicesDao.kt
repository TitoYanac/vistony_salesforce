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

    //@Query("SELECT * FROM documentodeuda WHERE cliente_id=:cliente_id  and CAST(saldo as INTEGER)>0 order by fechaemision ASC ")
    @Query("SELECT * FROM documentodeuda WHERE cliente_id=:cliente_id order by fechaemision ASC ")
    fun getInvoicesForClient(cliente_id: String): List<Invoices>

    @Query("DELETE FROM documentodeuda")
    fun deleteInvoices()

}