package com.vistony.salesforce.kotlin.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "statusdispatch")
data class StatusDispatch(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var compania_id: String?,
    @SerializedName("UserCode")
var fuerzatrabajo_id: String?,
var usuario_id: String?,
@SerializedName("Delivered")
var Delivered: String?,
@SerializedName("ReturnReason")
var ReturnReason: String?,
@SerializedName("cliente_id")
var cliente_id: String?,
@SerializedName("factura_id")
var factura_id: String?,
@SerializedName("entrega_id")
var entrega_id: String?,
@SerializedName("chkrecibido")
var chkrecibido: String?,
@SerializedName("Comments")
var Comments: String?,
var foto: String?,
var fecha_registro: String?,
var hora_registro: String?,
var PhotoDocument: String?,
@SerializedName("Latitude")
var latitud: String?,
@SerializedName("Longitude")
var longitud: String?,
var cliente: String?,
var entrega: String?,
var factura: String?,
var typedispatch: String?,
var reasondispatch: String?,
@SerializedName("ErrorCode")
var haveError: String?,
@SerializedName("Message")
var Message: String?,
@SerializedName("IdStatusDispatch")
var idStatusDispatch: String?,
@SerializedName("LineId")
var LineId: String?,
var PhotoStore: String?,
@SerializedName("CodeControl")
var DocEntry: String?,
var Address: String?,
var checkintime: String?,
var checkouttime: String?,
var chk_timestatus: String?,
@SerializedName("UserName")
var fuerzatrabajo: String?,
var messageServerDispatch: String?,
var messageServerTimeDispatch: String?


)
