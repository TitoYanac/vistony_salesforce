package com.vistony.salesforce.kotlin.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "headerdispatchsheet")
data class DispatchSheet(
    @PrimaryKey
    @SerializedName("DocEntry")
    val docEntry: Int,
    @SerializedName("DriverCode")
    val driverCode: String,
    @SerializedName("DriverMobile")
    val driverMobile: String?,
    @SerializedName("AssistantCode")
    val assistantCode: String,
    @SerializedName("VehicleCode")
    val vehicleCode: String,
    @SerializedName("VehiclePlate")
    val vehiclePlate: String,
    @SerializedName("DriverName")
    val driverName: String,
    @SerializedName("AssistantName")
    val assistantName: String,
    @SerializedName("Brand")
    val brand: String,
    @SerializedName("OverallWeight")
    val overallWeight: Int,
    @SerializedName("TotalDocument")
    val totalDocument: Int,
    @SerializedName("Details")
    val details: List<DetailDispatchSheet_>
)

@Entity(tableName = "detaildispatchsheet")
data class DetailDispatchSheet_ (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerializedName("DocEntry")
    val docEntry: Int,
    @SerializedName("Address2")
    val address2: String,
    @SerializedName("Balance")
    val balance: Double,
    @SerializedName("CardCode")
    val cardCode: String,
    @SerializedName("DeliveryLegalNumber")
    val deliveryLegalNumber: String,
    @SerializedName("DeliveryNum")
    val deliveryNum: String,
    @SerializedName("InvoiceLegalNumber")
    val invoiceLegalNumber: String,
    @SerializedName("InvoiceNum")
    val invoiceNum: String,
    @SerializedName("Item")
    val item: Int,
    @SerializedName("StatusCode")
    val statusCode: String,
    @SerializedName("Comments")
    val comments: String,
    @SerializedName("OcurrencyCode")
    val ocurrencyCode: String,
    @SerializedName("PhotoDocument")
    val photoDocument: String?,
    @SerializedName("PhotoStore")
    val photoStore: String?,
    @SerializedName("PymntGroup")
    val pymntGroup: String,
    @SerializedName("ShipToCode")
    val shipToCode: String,
    @SerializedName("SlpCode")
    val slpCode: String,
    @SerializedName("SlpName")
    val slpName: String,
    @SerializedName("Status")
    val status: String?,
    @SerializedName("Weight")
    val weight: String
)
