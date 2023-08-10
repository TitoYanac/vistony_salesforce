package com.vistony.salesforce.kotlin.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "reasondispatch")
data class ReasonDispatch(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("Value")
    var reasondispatch_id: String?,
    @SerializedName("Dscription")
    var reasondispatch: String?,
    @SerializedName("typedispatch_id")
    var typedispatch_id: String?,
    var compania_id: String?,
    var fuerzatrabajo_id: String?,
    var usuario_id: String?
)

data class ResponseReasonDispatch(
    var Status:String="",
    @SerializedName("Ocurrencies")
    var data: List<ReasonDispatch>? = emptyList(),
)