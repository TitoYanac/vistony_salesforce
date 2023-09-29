package com.vistony.salesforce.kotlin.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceAppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addServicesApp(statusDispatch: List<ServiceApp>)

    @Query("DELETE FROM service")
    fun deleteServiceApp()

    @Query("SELECT * FROM service where DocEntry=:DocEntry ")
    fun getServiceApp(DocEntry:String): Flow<List<ServiceApp>>
}