package com.vistony.salesforce.kotlin.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "collectionhead")
data class CollectionHead(
        @PrimaryKey(autoGenerate = true)
        var Id:Int=0,
        var Status: String="P",
        var Comments: String="",
        var CancelReason: String="N",
        var AmountDeposit: String="",
        var Date: String="",
        var Banking: String="N",
        var UserID: String="",
        var DirectDeposit: String="N",
        var Deposit: String="",
        var POSPay: String="N",
        var DeferredDate: String="",
        var IncomeType: String="",
        var BankID: String="",
        var CompanyCode: String="",
        var SlpCode: String="",
        @SerializedName("ErrorCode")
        var APIErrorCode: String="",
        @SerializedName("Message")
        var APIMessage: String="",
        @SerializedName("Code")
        var APICode: String="",
        var AppVersion: String="",
        var Model: String="",
        var Brand: String="",
        var OSVersion: String="",
        var Intent: String="0",
        var U_VIS_CollectionSalesPerson: String="N",
        var APIStatus: String="N",
)

data class CollectionHeadEntity(
        var Status:String="",
        var Count:String="",
        @SerializedName("Deposits") val data: List<CollectionHead> = emptyList(),
)
