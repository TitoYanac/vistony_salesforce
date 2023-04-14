package com.vistony.salesforce.kotlin.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "documentodeuda")
data class Invoices(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("DocEntry")
    var docEntry: String?,
    @SerializedName("DocNum")
    var documentoId: String?,
    @SerializedName("ShipToCode")
    var domicilioEmbarqueId: String?,
    @SerializedName("TaxDate")
    var fechaEmision: String?,
    @SerializedName("DueDate")
    var fechaVencimiento: String?,
    @SerializedName("DocTotal")
    var importeFactura: String?,
    @SerializedName("Balance")
    var saldo: String?,
    @SerializedName("Currency")
    var moneda: String?,
    @SerializedName("LegalNumber")
    var nroFactura: String?,
    @SerializedName("RawBalance")
    var saldoSinProcesar: String?,
    @SerializedName("Driver")
    var iddriver: String?,
    @SerializedName("Mobile")
    var mobile: String?,
    @SerializedName("U_VIS_SalesOrderID")
    var U_VIS_SalesOrderID: String?,
    @SerializedName("DeliveryDate")
    var fechadespacho: String?,
    @SerializedName("DeliveryStatus")
    var estadodespacho: String?,
    @SerializedName("LegalNumberDelivery")
    var legalnumberdelivery: String?,
    @SerializedName("Ocurrency")
    var U_SYP_DT_OCUR: String?,
    @SerializedName("PymntGroup")
    var pymntgroup: String?
)
