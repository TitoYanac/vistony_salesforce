package com.vistony.salesforce.kotlin.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CollectionDetailDao {
    @Query("SELECT * FROM collectiondetail WHERE StatusSendAPI = 'N' AND CompanyCode=:CompanyCode AND UserID=:UserID")
    fun getCollectionDetailSendAPIList(CompanyCode: String,UserID:String): List<CollectionDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListCollectionDetail(collectionDetailList: List<CollectionDetail>?)

    @Query("Select  max(CAST(Receip AS INTEGER)) ultimorecibo from collectiondetail  where CompanyCode=:CompanyCode and UserID=:UserID ")
    fun getLastReceip(CompanyCode: String,UserID:String): String

    @Query("SELECT * FROM collectiondetail where Receip=:Receip and UserID=:UserID ")
    fun getCollectionDetailUnit(Receip: String,UserID: String): List<CollectionDetail>

    @Query("SELECT * FROM collectiondetail where Receip=:Receip and UserID=:UserID ")
    fun getCollectionDetailObj(Receip: String,UserID: String): List<CollectionDetail>

    @Query("UPDATE collectiondetail SET APICode = :APICode, APIMessage = :APIMessage, StatusSendAPI = :StatusSendAPI WHERE Receip = :Receip and UserID=:UserID ")
    fun updateCollectionDetailAPI(Receip: String, UserID: String, APICode: String, APIMessage: String, StatusSendAPI: String)
}