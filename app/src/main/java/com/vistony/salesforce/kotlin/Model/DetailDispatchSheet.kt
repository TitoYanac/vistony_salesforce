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
    //@SerializedName("Notification")
    //var DATA: LiveData<List<Notification>> = emptyList(),
    val DATA: List<DetailDispatchSheet> = (emptyList())
)
/*
{
    // Constructor personalizado para Room
    constructor(compania_id: String?,
                fuerzatrabajo_id: String?,
                usuario_id: String?,
                id: Int,
                control_id: Int,
                item_id: Int,
                cliente_id: String,
                domembarque_id: String?,
                direccion: String?,
                factura_id: String?,
                entrega_id: String?,
                entrega: String?,
                factura: String?,
                saldo: String?,
                estado: String?,
                factura_fuerzatrabajo_id: String?,
                factura_fuerzatrabajo: String?,
                terminopago_id: String?,
                terminopago: String?,
                peso: String?,
                comentariodespacho: String?,
                nombrecliente: String?,
                estado_id: String?,
                motivo: String?,
                motivo_id: String?,
                fotoguia: String?,
                fotolocal: String?,
                ocurrencies: String?,
                latitud: String?,
                longitud: String?

    ) : this(
        compania_id,
        fuerzatrabajo_id,
        usuario_id,
        id,
        control_id,
        item_id,
        cliente_id,
        domembarque_id,
        direccion,
        factura_id,
        entrega_id,
        entrega,
        factura,
        saldo,
        estado,
        factura_fuerzatrabajo_id,
        factura_fuerzatrabajo,
        terminopago_id,
        terminopago,
        peso,
        comentariodespacho,
        nombrecliente,
        estado_id,
        motivo,
        motivo_id,
        fotoguia,
        fotolocal,
        ocurrencies,
        latitud,
        longitud,
        0,
        0,
        0,
        0,

    )
}*/
/*
{
    fun constructor(
        compania_id: String?,
        fuerzatrabajo_id: String?,
        usuario_id: String?,
        id: Int,
        control_id: Int,
        item_id: Int,
        cliente_id: String,
        domembarque_id: String?,
        direccion: String?,
        factura_id: String?,
        entrega_id: String?,
        entrega: String?,
        factura: String?,
        saldo: String?,
        estado: String?,
        factura_fuerzatrabajo_id: String?,
        factura_fuerzatrabajo: String?,
        terminopago_id: String?,
        terminopago: String?,
        peso: String?,
        comentariodespacho: String?,
        nombrecliente: String?,
        estado_id: String?,
        motivo: String?,
        motivo_id: String?,
        fotoguia: String?,
        fotolocal: String?,
        ocurrencies: String?,
        latitud: String?,
        longitud: String?,
        chkupdatedispatch: Boolean,
        chkvisitsectionstart: Boolean,
        chkvisitsectionend: Boolean,
        chkcollection: Boolean

    ){
        this.compania_id=compania_id
        this.fuerzatrabajo_id=fuerzatrabajo_id
        this.usuario_id=usuario_id
        this.id=id
        this.control_id=control_id
        this.item_id=item_id
        this.cliente_id=cliente_id
        this.domembarque_id=domembarque_id
        this.direccion=direccion
        this.factura_id=factura_id
        this.entrega_id=entrega_id
        this.entrega=entrega
        this.factura=factura
        this.saldo=saldo
        this.estado=estado
        this.factura_fuerzatrabajo_id=factura_fuerzatrabajo_id
        this.factura_fuerzatrabajo=factura_fuerzatrabajo
        this.terminopago_id=terminopago_id
        this.terminopago=terminopago
        this.peso=peso
        this.comentariodespacho=comentariodespacho
        this.nombrecliente=nombrecliente
        this.estado_id=estado_id
        this.motivo=motivo
        this.motivo_id=motivo_id
        this.fotoguia=fotoguia
        this.fotolocal=fotolocal
        this.ocurrencies=ocurrencies
        this.latitud=latitud
        this.longitud=longitud
        this.chkupdatedispatch=chkupdatedispatch
        this.chkvisitsectionstart=chkvisitsectionstart
        this.chkvisitsectionend=chkvisitsectionend
        this.chkcollection=chkcollection
    }
}*/




