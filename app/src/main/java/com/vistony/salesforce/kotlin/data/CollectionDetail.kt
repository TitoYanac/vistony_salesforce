package com.vistony.salesforce.kotlin.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ResponseCollectionDetail(
    var Status:String="",
    @SerializedName("Collections")
    val data: List<CollectionDetail>? = emptyList(),
)

@Entity(tableName = "collectiondetail")
data class CollectionDetail(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("ItemDetail")
    var ItemDetail: String?,
    var CardCode: String,
    var DocNum: String,
    var DocTotal: String,
    var Balance: String,
    var NewBalance: String,
    var AmountCharged: String,
    var IncomeDate: String,
    @SerializedName("Receip")
    var Receip: String?,
    var QRStatus: String,
    var Banking: String,
    var CancelReason: String,
    @SerializedName("BankID")
    var BankID: String,
    var Commentary: String,
    var DirectDeposit: String,
    var POSPay: String,
    var IncomeTime: String,
    @SerializedName("Deposit")
    var Deposit: String,
    var DocEntryFT: String,
    var UserID: String,
    var SlpCode: String,
    //var Code: String,
    var Intent: String,
    var AppVersion: String,
    var Model: String,
    var Brand: String,
    var OSVersion: String,
    var CollectionCheck: String,
    var Data: String,
    var CardName: String,
    var SlpName: String,
    var LegalNumber: String,
    var CodeSMS: String,
    var Phone: String,
    var U_VIS_CollectionSalesperson: String,
    var U_VIS_Type: String,
    var CompanyCode: String,
    var Status: String,
    var StatusSendAPI:String,
    var StatusSendAPIQR:String,
    var CANCELED:String,
    var StatusDeposit:String,
    var StatusSendAPIDeposit:String,
    @SerializedName("Code")
    var APICode:String,
    @SerializedName("Message")
    var APIMessage:String,
    @SerializedName("ErrorCode")
    var APIErrorCode:String
)


