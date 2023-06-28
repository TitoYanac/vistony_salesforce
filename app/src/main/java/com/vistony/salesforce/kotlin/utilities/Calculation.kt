package com.vistony.salesforce.kotlin.utilities

import android.content.Context
import android.os.Build
import com.vistony.salesforce.Controller.Utilitario.Utilitario
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.kotlin.data.CollectionDetail
import com.vistony.salesforce.kotlin.data.Invoices
import java.math.BigDecimal
import java.math.RoundingMode

fun CalculateNewBalance(
    balance: String?,
    collection: String?
): String? {
    var _collection: String? =collection
    if (_collection.isNullOrEmpty())
    {
        _collection="0";
    }
    val var1 = BigDecimal(balance)
    val result = var1.subtract(BigDecimal(_collection)).setScale(3, RoundingMode.HALF_UP)
    return result.toString()
}

fun CreateListCollectionDetail(
    context: Context,
    invoices: Invoices,
    newBalance:String,
    collection:String,
    typeCollection:String,
    cardName:String,
    commentary:String,
    lastReceip:String?
): List<CollectionDetail>? {

    var collectionDetail: CollectionDetail? = null
    var listCollectionDetail: List<CollectionDetail>? = null
    var collectionOrdinary: String = "N";
    var pagoPOS: String = "N";
    var prePayment: String = "N";
    var collectionSalesPerson: String = "N";
    var directDeposit: String = "N"
    var QR:String="N"
    val brand = Build.MANUFACTURER
    val model = Build.MODEL
    val osVersion = Build.VERSION.RELEASE

    when (typeCollection) {
        "Cobranza Ordinaria" -> {
            collectionOrdinary = "Y"
            pagoPOS = "N"
            prePayment = "N"
            collectionSalesPerson = "N"
            directDeposit = "N"
        }
        "Deposito Directo" -> {
            collectionOrdinary = "N"
            pagoPOS = "N"
            prePayment = "N"
            collectionSalesPerson = "N"
            directDeposit = "Y"
        }
        "Pago POS" -> {
            collectionOrdinary = "N"
            pagoPOS = "Y"
            prePayment = "N"
            collectionSalesPerson = "N"
            directDeposit = "N"
        }
        "Cobro Vendedor" -> {
            collectionOrdinary = "N"
            pagoPOS = "N"
            prePayment = "N"
            collectionSalesPerson = "Y"
            directDeposit = "N"
        }
        "Pago Adelantado" -> {
            collectionOrdinary = "N"
            pagoPOS = "N"
            prePayment = "Y"
            collectionSalesPerson = "N"
            directDeposit = "N"
        }
    }
    if (SesionEntity.perfil_id == "CHOFER" || SesionEntity.perfil_id == "Chofer") {
        QR = "Y"
    }
    var ObjUsuario = UsuarioSQLiteEntity()
    val usuarioSQLite = UsuarioSQLite(context)
    ObjUsuario = usuarioSQLite.ObtenerUsuarioSesion()


    collectionDetail?.AmountCharged = collection
    collectionDetail?.CollectionCheck = "N"
    collectionDetail?.Balance = invoices?.saldo.toString()
    collectionDetail?.CardCode = invoices?.cliente_id.toString()
    collectionDetail?.BankID = ""
    collectionDetail?.Banking = "N"
    collectionDetail?.CancelReason = ""
    collectionDetail?.AppVersion = Utilitario.getVersion(context)
    collectionDetail?.Brand = brand
    collectionDetail?.Code = ""
    collectionDetail?.CardName = cardName
    collectionDetail?.Commentary = commentary
    collectionDetail?.CodeSMS = ""
    collectionDetail?.Data = ""
    collectionDetail?.Deposit = ""
    collectionDetail?.DirectDeposit = directDeposit.toString()
    collectionDetail?.DocEntryFT = invoices.docEntry.toString()
    collectionDetail?.DocNum=invoices.documentoId.toString()
    collectionDetail?.DocTotal=invoices.importeFactura.toString()
    collectionDetail?.IncomeDate= getDateCurrent().toString()
    collectionDetail?.IncomeTime= getTimeCurrent().toString()
    collectionDetail?.Intent="1"
    collectionDetail?.LegalNumber=invoices.nroFactura.toString()
    collectionDetail?.Model=model
    collectionDetail?.NewBalance=newBalance
    collectionDetail?.OSVersion=osVersion
    collectionDetail?.POSPay=pagoPOS
    collectionDetail?.Phone=""
    collectionDetail?.QRStatus=QR
    if (lastReceip != null) {
        collectionDetail?.Receip=lastReceip
    }
    collectionDetail?.SlpCode=ObjUsuario.fuerzatrabajo_id
    collectionDetail?.SlpName=ObjUsuario.nombrefuerzatrabajo
    collectionDetail?.Status=""
    collectionDetail?.U_VIS_CollectionSalesperson=collectionSalesPerson
    collectionDetail?.U_VIS_Type=typeCollection
    collectionDetail?.UserID=ObjUsuario.usuario_id


    if (listCollectionDetail == null) {
        listCollectionDetail = ArrayList()
        if (collectionDetail != null) {
            listCollectionDetail.add(collectionDetail)
        }
    }

    return listCollectionDetail
}


