package com.vistony.salesforce.kotlin.Model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VisitSectionDao {
    @Query("SELECT * FROM visitsection")
    fun getVisitSection(): LiveData<List<VisitSection>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addVisitSection(visitSection: List<VisitSection>?)

    @Query("DELETE FROM visitsection")
    fun deleteVisitSection()

    @Query("SELECT * FROM visitsection where cliente_id=:cliente_id and domembarque_id=:domembarque_id and dateini=:dateini and idref=:idref ")
    fun getVisitSectionList(cliente_id: String , domembarque_id: String, dateini: String, idref:String): List<VisitSection>

    @Update
    fun updateVisitSection(visitSection: List<VisitSection>?)
}