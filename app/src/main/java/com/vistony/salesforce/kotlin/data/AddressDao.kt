package com.vistony.salesforce.kotlin.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AddressDao {
    @Query("SELECT * FROM direccioncliente")
    fun getAddress(): LiveData<List<Address>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddress(address: List<Address>?)

    @Query("DELETE FROM  direccioncliente")
    fun deleteAddress()
}