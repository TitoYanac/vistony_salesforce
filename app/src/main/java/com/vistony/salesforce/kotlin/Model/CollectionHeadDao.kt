package com.vistony.salesforce.kotlin.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CollectionHeadDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCollectionHead(collectionHeadList: CollectionHead)

    @Query("SELECT * FROM collectionhead WHERE APIStatus = 'N'")
    fun getCollectionHead(): List<CollectionHead>

    @Query("UPDATE collectionhead SET APICode = :APICode, APIMessage = :APIMessage, APIStatus = :APIStatus WHERE Deposit = :Deposit and SlpCode=:SlpCode ")
    fun updateCollectionHeadAPI(Deposit: String, SlpCode: String, APICode: String, APIMessage: String, APIStatus: String)

    @Query("UPDATE collectionhead SET Intent = :Intent WHERE Deposit = :Deposit and SlpCode=:SlpCode ")
    fun updateIntentCollectionHead(Deposit: String, SlpCode: String, Intent: String)

    @Query("SELECT * FROM collectionhead WHERE Date >=:startDate AND Date<=:endDate ")
    fun getCollectionHeadForDate(startDate:String,endDate:String): List<CollectionHead>

    @Query("UPDATE collectionhead SET Status = 'A',APIStatusCancel='Y', CancelReason=:comment WHERE Deposit = :deposit and Date=:date ")
    fun updateCancelCollectionHead(deposit:String,date:String,comment:String)

    @Query("SELECT * FROM collectionhead WHERE Status ='A' and APIStatusCancel='Y' ")
    fun getCollectionHeadCancel(): List<CollectionHead>
}