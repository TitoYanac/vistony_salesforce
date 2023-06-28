package com.vistony.salesforce.kotlin.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CollectionDetailDao {
    @Query("SELECT * FROM collectiondetail")
    fun getCollectionDetail(): LiveData<List<CollectionDetail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListCollectionDetail(collectionDetailList: List<CollectionDetail>?)

    @Query("Select  max(CAST(Receip AS INTEGER)) ultimorecibo from collectiondetail  where Company_id=:Company_id and UserID=:UserID ")
    fun getLastReceip(Company_id: String,UserID:String): String
}