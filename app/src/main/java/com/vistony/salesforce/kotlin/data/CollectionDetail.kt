package com.vistony.salesforce.kotlin.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "collectiondetail")
data class CollectionDetail(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
var ItemDetail: String?,
val CardCode: String,
val DocNum: String,
val DocTotal: String,
val Balance: String,
val NewBalance: String,
val AmountCharged: String,
val IncomeDate: String,
val Receip: String,
val QRStatus: String,
val Banking: String,
val CancelReason: String,
val BankID: String,
val Commentary: String,
val DirectDeposit: String,
val POSPay: String,
val IncomeTime: String,
val Status: String,
val Deposit: String,
val DocEntryFT: String,
val UserID: String,
val SlpCode: String,
val Code: String,
val U_VIS_UserID: String,
val Intent: String,
val AppVersion: String,
val Model: String,
val Brand: String,
val OSVersion: String,
val CollectionCheck: String,
val Data: String,
val CardName: String,
val SlpName: String,
val LegalNumber: String,
val CodeSMS: String,
val Phone: String,
val U_VIS_CollectionSalesperson: String,
val U_VIS_Type: String
)
