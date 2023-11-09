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
}