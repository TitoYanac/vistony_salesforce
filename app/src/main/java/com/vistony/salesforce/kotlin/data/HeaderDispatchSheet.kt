package com.vistony.salesforce.kotlin.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "headerdispatchsheet")
data class HeaderDispatchSheet(
    var compania_id: String? = null,
    var fuerzatrabajo_id: String? = null,
    var usuario_id: String? = null,
    @PrimaryKey
    @SerializedName("DocEntry")
    val control_id: Int,
    @SerializedName("DriverCode")
    val driverCode: String,
    @SerializedName("DriverMobile")
    val driverMobile: String?,
    @SerializedName("AssistantCode")
    val asistente_id: String,
    @SerializedName("VehicleCode")
    val vehicleCode: String,
    @SerializedName("VehiclePlate")
    val vehiclePlate: String,
    @SerializedName("DriverName")
    val driverName: String,
    @SerializedName("AssistantName")
    val asistente: String,
    @SerializedName("Brand")
    val marca: String,
    @SerializedName("OverallWeight")
    val pesototal: Int,
    @SerializedName("TotalDocument")
    val totalDocument: Int,
    @SerializedName("Details")
    val details: List<DetailDispatchSheet>,
    var fechahojadespacho: String? = null,
    var Datetimeregister: String? = null
)


