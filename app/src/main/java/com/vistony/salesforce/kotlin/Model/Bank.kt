package com.vistony.salesforce.kotlin.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "bank")
data class Bank(
    @PrimaryKey(autoGenerate = true)
    var Id:Int=0,
    var BankId: String="",
    var BankName: String="",
    var SingleDeposit: String="N",
    var Post: String="N"
)

data class BankEntity(
    var Status:String="",
    @SerializedName("Banks")
    val Data: List<Bank> = (emptyList())
)