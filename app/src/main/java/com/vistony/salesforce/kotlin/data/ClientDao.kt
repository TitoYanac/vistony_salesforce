package com.vistony.salesforce.kotlin.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ClientDao {
    @Query("SELECT * FROM cliente")
    fun getClient(): LiveData<List<Client>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertClient(client: List<Client>?)

    @Query("DELETE FROM cliente")
    fun deleteClient()
}