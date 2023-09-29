package com.vistony.salesforce.kotlin.Model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cliente")
data class Client(
    @PrimaryKey
    @SerializedName("CardCode")
    var cliente_id: String,
    var compania_id: String?,
    @SerializedName("CardName")
    var nombrecliente: String?,
    var domembarque_id: String?,
    @SerializedName("PayToCode")
    var domfactura_id: String?,
    @SerializedName("Address")
    var direccion: String?,
    @SerializedName("TerritoryId")
    var zona_id: String?,
    @SerializedName("VisitOrder")
    var orden: String?,
    @SerializedName("Territory")
    var zona: String?,
    @SerializedName("LicTradNum")
    var rucdni: String?,
    @SerializedName("Currency")
    var moneda: String?,
    @SerializedName("Phone")
    var telefonofijo: String?,
    @SerializedName("CellPhone")
    var telefonomovil: String?,
    @SerializedName("Email")
    var correo: String?,
    @SerializedName("ZipCode")
    var ubigeo_id: String?,
    var impuesto_id: String?,
    var impuesto: String?,
    var tipocambio: String?,
    @SerializedName("Category")
    var categoria: String?,
    @SerializedName("CreditLimit")
    var linea_credito: String?,
    @SerializedName("Balance")
    var linea_credito_usado: String?,
    @SerializedName("PymntGroup")
    var terminopago_id: String?,
    @SerializedName("PriceList")
    var lista_precio: String?,
    @SerializedName("DueDays")
    var dueDays: String?,
    @SerializedName("LineOfBusiness")
    var lineofbusiness: String?,
    @SerializedName("LastPurchase")
    var lastpurchase: String?,
    var DeliveryDay: String?,
    @SerializedName("SinTerminoContado")
    var statuscounted: String?,
    @Ignore
    @SerializedName("Addresses")
    var listAddress: List<Address>?,
    @Ignore
    @SerializedName("Invoices")
    var listInvoice: List<Invoices>?
){
    constructor(
cliente_id: String,
compania_id: String?,
nombrecliente: String?,
domembarque_id: String?,
domfactura_id: String?,
direccion: String?,
zona_id: String?,
orden: String?,
zona: String?,
rucdni: String?,
moneda: String?,
telefonofijo: String?,
telefonomovil: String?,
correo: String?,
ubigeo_id: String?,
impuesto_id: String?,
impuesto: String?,
tipocambio: String?,
categoria: String?,
linea_credito: String?,
linea_credito_usado: String?,
terminopago_id: String?,
lista_precio: String?,
dueDays: String?,
lineofbusiness: String?,
lastpurchase: String?,
DeliveryDay: String?,
statuscounted: String?
    ) : this(
        cliente_id,
        compania_id,
    nombrecliente,
    domembarque_id,
    domfactura_id,
    direccion,
    zona_id,
    orden,
    zona,
    rucdni,
    moneda,
    telefonofijo,
    telefonomovil,
    correo,
    ubigeo_id,
    impuesto_id,
    impuesto,
    tipocambio,
    categoria,
    linea_credito,
    linea_credito_usado,
    terminopago_id,
    lista_precio,
    dueDays,
    lineofbusiness,
    lastpurchase,
    DeliveryDay,
    statuscounted,
        null,
        null
    )
}
