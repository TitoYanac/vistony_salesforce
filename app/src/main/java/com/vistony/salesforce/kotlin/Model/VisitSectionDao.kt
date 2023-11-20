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

    @Query("SELECT * FROM visitsection where cliente_id=:cliente_id and domembarque_id=:domembarque_id and idref=:idref and idrefitemid=:idrefitemid ")
    fun getVisitSectionList(cliente_id: String , domembarque_id: String, idref:String,idrefitemid:String): List<VisitSection>

    @Update
    fun updateVisitSection(visitSection: List<VisitSection>?)
}