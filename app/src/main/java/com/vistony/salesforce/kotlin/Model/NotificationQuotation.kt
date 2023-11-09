package com.vistony.salesforce.kotlin.Model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class NotificationQuotation(
        var Object: String?,
        var Cliente_ID: String?,
        var NombreCliente: String?,
        var RucDni: String?,
        var DocEntry: String?,
        var DocNum: String?,
        var DomEmbarque_ID: String?,
        var DomEmbarque: String?,
        var CANCELED: String?,
        var MontoTotalOrden: String?,
        var EstadoAprobacion: String?,
        var OrdenVenta_ID: String?,
        var SlpCode: String?,
        var PymntGroup: String?
)

class NotificationQuotationEntity(
        var Status:String="",
        @SerializedName("Quotation")
        var DATA: List<NotificationQuotation> = emptyList()
)