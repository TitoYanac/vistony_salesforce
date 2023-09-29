package com.vistony.salesforce.kotlin.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "direccioncliente")
data class Address(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var companiaid: String?,
    var cliente_id: String?,
    @SerializedName("ShipToCode")
    var domembarque_id: String?,
    @SerializedName("Street")
    var direccion: String?,
    @SerializedName("TerritoryID")
    var zonaid: String?,
    @SerializedName("Territory")
    var zona: String?,
    @SerializedName("SlpCode")
    var fuerzatrabajoid: String?,
    @SerializedName("SlpName")
    var nombrefuerzatrabajo: String?,
    @SerializedName("Latitude")
    var latitude: String?,
    @SerializedName("Longitude")
    var longitude: String?,
    @SerializedName("AddressCode")
    var addresscode: String?,
    @SerializedName("DayDelivery")
    var DeliveryDay: String?,
    @SerializedName("ZipCode")
    var ZipCode: String?
)
