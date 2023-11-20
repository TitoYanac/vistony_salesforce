package com.vistony.salesforce.kotlin.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "detaildispatchsheet")
data class DetailDispatchSheet (
    var compania_id: String? = null,
    var fuerzatrabajo_id: String? = null,
    var usuario_id: String? = null,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("DocEntry")
    var control_id: Int,
    @SerializedName("Item")
    var item_id: Int,
    @SerializedName("CardCode")
    var cliente_id: String,
    @SerializedName("ShipToCode")
    var domembarque_id: String? = null,
    @SerializedName("Address2")
    var direccion: String? = null,
    @SerializedName("InvoiceNum")
    var factura_id: String? = null,
    @SerializedName("DeliveryNum")
    var entrega_id: String? = null,
    @SerializedName("DeliveryLegalNumber")
    var entrega: String? = null,
    @SerializedName("InvoiceLegalNumber")
    var factura: String? = null,
    @SerializedName("Balance")
    var saldo: String? = null,

    var estado: String? = null,
    @SerializedName("SlpCode")
    var factura_fuerzatrabajo_id: String? = null,
    @SerializedName("SlpName")
    var factura_fuerzatrabajo: String? = null,
    var terminopago_id: String? = null,
    @SerializedName("PymntGroup")
    var terminopago: String? = null,
    @SerializedName("Weight")
    var peso: String? = null,
    @SerializedName("Comments")
    var comentariodespacho: String? = null,
    var nombrecliente: String? = null,
    @SerializedName("StatusCode")
    var estado_id: String? = null,
    var motivo: String? = null,
    @SerializedName("OcurrencyCode")
    var motivo_id: String? = null,
    @SerializedName("PhotoDocument")
    var fotoguia: String? = null,
    @SerializedName("PhotoStore")
    var fotolocal: String? = null,
    var ocurrencies: String? = null,
    var latitud: String? = null,
    var longitud: String? = null,
    var statusupdatedispatch: String? = "N",
    var statusvisitstart: String? = "N",
    var statusvisitend: String? = "N",
    var statuscollection: String? = "N",
    var timeini: String? = null,
    @SerializedName("Status")
    var timefin: String? = null
)

data class DetailDispatchSheetEntity(
    var Status:String="",
    val DATA: List<DetailDispatchSheet> = (emptyList()),
    val UI: List<DetailDispatchSheetUI> = (emptyList())
)


data class DetailDispatchSheetUI (
    var compania_id: String? = null,
    var fuerzatrabajo_id: String? = null,
    var usuario_id: String? = null,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("DocEntry")
    var control_id: Int,
    @SerializedName("Item")
    var item_id: Int,
    @SerializedName("CardCode")
    var cliente_id: String,
    @SerializedName("ShipToCode")
    var domembarque_id: String? = null,
    @SerializedName("Address2")
    var direccion: String? = null,
    @SerializedName("InvoiceNum")
    var factura_id: String? = null,
    @SerializedName("DeliveryNum")
    var entrega_id: String? = null,
    @SerializedName("DeliveryLegalNumber")
    var entrega: String? = null,
    @SerializedName("InvoiceLegalNumber")
    var factura: String? = null,
    @SerializedName("Balance")
    var saldo: String? = null,
    var estado: String? = null,
    @SerializedName("SlpCode")
    var factura_fuerzatrabajo_id: String? = null,
    @SerializedName("SlpName")
    var factura_fuerzatrabajo: String? = null,
    var terminopago_id: String? = null,
    @SerializedName("PymntGroup")
    var terminopago: String? = null,
    @SerializedName("Weight")
    var peso: String? = null,
    @SerializedName("Comments")
    var comentariodespacho: String? = null,
    var nombrecliente: String? = null,
    @SerializedName("StatusCode")
    var estado_id: String? = null,
    var motivo: String? = null,
    @SerializedName("OcurrencyCode")
    var motivo_id: String? = null,
    @SerializedName("PhotoDocument")
    var fotoguia: String? = null,
    @SerializedName("PhotoStore")
    var fotolocal: String? = null,
    var ocurrencies: String? = null,
    var latitud: String? = null,
    var longitud: String? = null,
    var statusupdatedispatch: String? = "N",
    var statusvisitstart: String? = "N",
    var statusvisitend: String? = "N",
    var statuscollection: String? = "N",
    var timeini: String? = null,
    @SerializedName("Status")
    var timefin: String? = null,
    var chk_statusServerDispatch: String? = null,
    var driverName: String? = null,
    var driverMobile: String? = null,
    var messageServerDispatch: String? = null
)

