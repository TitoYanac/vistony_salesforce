package com.vistony.salesforce.Entity.Retrofit.Modelo

import com.google.gson.annotations.SerializedName

class AddressEntityPrueba {
    var companiaid: String? = null
    var clienteId: String? = null

    @SerializedName("ShipToCode")
    var domicilioEmbarque: String? = null

    @SerializedName("Street")
    var direccion: String? = null

    @SerializedName("TerritoryID")
    var zonaid: String? = null

    @SerializedName("Territory")
    var zona: String? = null

    @SerializedName("SlpCode")
    var fuerzatrabajoid: String? = null

    @SerializedName("SlpName")
    var nombrefuerzatrabajo: String? = null

    @SerializedName("Latitude")
    var latitude: String? = null

    @SerializedName("Longitude")
    var longitude: String? = null

    @SerializedName("AddressCode")
    var addresscode: String? = null

    @SerializedName("DayDelivery")
    var deliveryDay: String? = null
}