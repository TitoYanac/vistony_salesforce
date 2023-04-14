package com.vistony.salesforce.kotlin.data

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
