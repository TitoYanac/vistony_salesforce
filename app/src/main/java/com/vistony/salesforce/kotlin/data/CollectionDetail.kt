package com.vistony.salesforce.kotlin.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "collectiondetail")
data class CollectionDetail(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var ItemDetail: String?,
    var CardCode: String,
    var DocNum: String,
    var DocTotal: String,
    var Balance: String,
    var NewBalance: String,
    var AmountCharged: String,
    var IncomeDate: String,
    var Receip: String,
    var QRStatus: String,
    var Banking: String,
    var CancelReason: String,
    var BankID: String,
    var Commentary: String,
    var DirectDeposit: String,
    var POSPay: String,
    var IncomeTime: String,
    var Status: String,
    var Deposit: String,
    var DocEntryFT: String,
    var UserID: String,
    var SlpCode: String,
    var Code: String,
    val U_VIS_UserID: String,
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
    var Company_id: String
)
