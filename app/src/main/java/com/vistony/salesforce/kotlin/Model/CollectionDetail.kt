package com.vistony.salesforce.kotlin.Model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CollectionDetailEntity(
    var Status:String="",
    var Count:String="",
    @SerializedName("CollectionDetail") val data: List<CollectionDetail> = emptyList(),
)

@Entity(tableName = "collectiondetail")
data class CollectionDetail(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("ItemDetail")
    var ItemDetail: String="",
    var CardCode: String="",
    var DocNum: String="",
    var DocTotal: String="",
    var Balance: String="",
    var NewBalance: String="",
    var AmountCharged: String="",
    var IncomeDate: String="",
    @SerializedName("Receip")
    var Receip: String="",
    var QRStatus: String?="N",
    var Banking: String="",
    @SerializedName("CancellationReason")
    var CancelReason: String="",
    @SerializedName("BankID")
    var BankID: String="",
    var Commentary: String="",
    var DirectDeposit: String="N",
    var POSPay: String="N",
    var IncomeTime: String="",
    @SerializedName("DepositID")
    var Deposit: String="",
    var DocEntryFT: String="",
    @SerializedName("UserCode")
    var UserID: String="",
    var SlpCode: String="",
    var Intent: String="",
    var AppVersion: String="",
    var Model: String="",
    var Brand: String="",
    var OSVersion: String="",
    var CollectionCheck: String="N",
    var Data: String="",
    var CardName: String="",
    var SlpName: String="",
    var LegalNumber: String="",
    var CodeSMS: String="",
    var Phone: String="",
    var U_VIS_CollectionSalesperson: String="N",
    var U_VIS_Type: String="",
    var CompanyCode: String="",
    var Status: String="Pendiente",
    var StatusSendAPI:String="N",
    var StatusSendAPIQR:String="N",
    var CANCELED:String="N",
    var StatusDeposit:String="N",
    var StatusSendAPIDeposit:String="N",
    @SerializedName("Code")
    var APICode:String="",
    @SerializedName("Message")
    var APIMessage:String="",
    @SerializedName("ErrorCode")
    var APIErrorCode:String="",
    @Ignore
    var StatusSelection:Boolean=false,
)


