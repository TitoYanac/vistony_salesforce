package com.vistony.salesforce.kotlin.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "typedispatch")
data class TypeDispatch(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("Value")
    var typedispatch_id: String?,
    @SerializedName("Dscription")
    var typedispatch: String?,
    var compania_id: String?,
    var fuerzatrabajo_id: String?,
    var usuario_id: String?,
    @SerializedName("Flag")
    var statusupdate: String?
)

data class ResponseTypeDispatch(
    var Status:String="",
    @SerializedName("DispatchTypes")
    var data: List<TypeDispatch>? = emptyList(),
)
