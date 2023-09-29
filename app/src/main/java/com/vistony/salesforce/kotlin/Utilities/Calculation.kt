package com.vistony.salesforce.kotlin.Utilities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.Controller.Utilitario.*
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.kotlin.Model.CollectionDetail
import com.vistony.salesforce.kotlin.Model.Invoices
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

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
    invoices: Invoices?,
    newBalance:String,
    collection:String,
    typeCollection:String,
    cardName:String,
    commentary:String,
    lastReceip:String?
): List<CollectionDetail>? {


        var listCollectionDetail: List<CollectionDetail>? = ArrayList()
        var collectionOrdinary: String = "N";
        var pagoPOS: String = "N";
        var prePayment: String = "N";
        var collectionSalesPerson: String = "N";
        var directDeposit: String = "N"
        var QR: String = "N"
        val brand = Build.MANUFACTURER
        val model = Build.MODEL
        val osVersion = Build.VERSION.RELEASE
        val numAleatorio = Random()
        val codeSMS: Int = numAleatorio.nextInt(9999 + 1000 + 1) + 1000
    /*Log.e(
        "REOS",
        "Calculation-CreateListCollectionDetail-invoices: " + invoices
    )
    Log.e(
        "REOS",
        "Calculation-CreateListCollectionDetail-newBalance: " + newBalance
    )
    Log.e(
        "REOS",
        "Calculation-CreateListCollectionDetail-collection: " + collection
    )
    Log.e(
        "REOS",
        "Calculation-CreateListCollectionDetail-typeCollection: " + typeCollection
    )
    Log.e(
        "REOS",
        "Calculation-CreateListCollectionDetail-cardName: " + cardName
    )
    Log.e(
        "REOS",
        "Calculation-CreateListCollectionDetail-lastReceip: " + lastReceip
    )*/
        try {
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
            var collectionDetail: CollectionDetail? =
                CollectionDetail(
                    0
                    , getDateTimeCurrent()!!
                    ,invoices?.cliente_id.toString()
                    ,invoices?.documentoId.toString()
                    ,invoices?.importeFactura.toString()
                    ,invoices?.saldo.toString()
                    ,newBalance
                    ,collection
                    ,getDateCurrent().toString()
                    , lastReceip!!
                    ,QR
                    ,"N"
                    ,invoices?.nroFactura.toString()
                    ,""
                    ,commentary
                    ,directDeposit
                    ,pagoPOS
                    ,getTimeCurrent().toString()
                    ,""
                    ,invoices?.docEntry.toString()
                    ,ObjUsuario.usuario_id
                    ,ObjUsuario.fuerzatrabajo_id
                    //,""
                    ,"1"
                    ,Utilitario.getVersion(context)
                    ,model
                    ,brand
                    ,osVersion
                    ,"N"
                    ,""
                    ,cardName
                    ,ObjUsuario.nombrefuerzatrabajo
                    ,invoices?.nroFactura.toString()
                    ,codeSMS.toString()
                    ,""
                    ,collectionSalesPerson
                    ,typeCollection
                    ,ObjUsuario.compania_id
                    ,"P"
                    ,"N"
                    ,"N"
                    ,"N"
                    ,"N"
                    ,"N"
                    ,""
                    ,""
                    ,"N"
            )
            /*collectionDetail?.AmountCharged
            collectionDetail?.AmountCharged = collection
            collectionDetail?.CollectionCheck = "N"
            collectionDetail?.Balance =
            collectionDetail?.CardCode =
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
            collectionDetail?.DocEntryFT =
            collectionDetail?.DocNum = invoices?.documentoId.toString()
            collectionDetail?.DocTotal =
            collectionDetail?.IncomeDate = getDateCurrent().toString()
            collectionDetail?.IncomeTime = getTimeCurrent().toString()
            collectionDetail?.Intent = "1"
            collectionDetail?.LegalNumber = invoices?.nroFactura.toString()
            collectionDetail?.Model = model
            collectionDetail?.NewBalance = newBalance
            collectionDetail?.OSVersion = osVersion
            collectionDetail?.POSPay = pagoPOS
            collectionDetail?.Phone = ""
            collectionDetail?.QRStatus = QR
            if (lastReceip != null) {
                collectionDetail?.Receip = lastReceip
            }
            collectionDetail?.SlpCode = ObjUsuario.fuerzatrabajo_id
            collectionDetail?.SlpName = ObjUsuario.nombrefuerzatrabajo
            collectionDetail?.Status = ""
            collectionDetail?.U_VIS_CollectionSalesperson = collectionSalesPerson
            collectionDetail?.U_VIS_Type = typeCollection
            collectionDetail?.UserID = ObjUsuario.usuario_id*/

            /*Log.e(
                "REOS",
                "Calculation-CreateListCollectionDetail-collectionDetail?.UserID: " + collectionDetail?.UserID
            )
            Log.e(
                "REOS",
                "Calculation-CreateListCollectionDetail-collectionDetail?.U_VIS_Type: " + collectionDetail?.U_VIS_Type
            )
            Log.e(
                "REOS",
                "Calculation-CreateListCollectionDetail-collectionDetail?.AmountCharged: " + collectionDetail?.AmountCharged
            )*/
            //if (listCollectionDetail == null) {
                listCollectionDetail = ArrayList()
                if (collectionDetail != null) {
                    /*Log.e(
                        "REOS",
                        "Calculation-CreateListCollectionDetail-collectionDetail != null: "
                    )*/
                    listCollectionDetail.add(collectionDetail)
                }
            //}
            /*if (collectionDetail != null)
            {
                listCollectionDetail.add(collectionDetail)
            }*/
            /*if (listCollectionDetail == null) {
                listCollectionDetail = ArrayList()
                if (collectionDetail != null) {
                    listCollectionDetail.add(collectionDetail)
                }
            }*/

    }catch (e: Exception){
            Log.e(
                "REOS",
                "Calculation-CreateListCollectionDetail-error: " + e.toString()
            )
    }
    /*Log.e(
        "REOS",
        "Calculation-CreateListCollectionDetail-listCollectionDetail.size: " + listCollectionDetail?.size
    )*/
    return listCollectionDetail
}

fun getNumberMax(
    number1: String?,
    number2: String?
): String? {
    var number1_:Int=0
    var number2_:Int=0
    if(number1.isNullOrEmpty())
    {
        number1_=0
    }else{
        number1_=number1.toInt()
    }
    if(number2.isNullOrEmpty())
    {
        number2_=0
    }else{
        number2_=number2.toInt()
    }
    return maxOf(number1_, number2_).toString()
}

fun getSumNumbersReceip(
    number1: String?,
    number2: String?
): String? {
    var num1 = if (number1 == "" || number1 == null) "0" else number1
    var num2 = if (number2 == "" || number2 == null) "0" else number2

    val temp1: BigDecimal = BigDecimal(num1)
    val rpta = temp1.add(BigDecimal(num2))

    return rpta.setScale(0, RoundingMode.HALF_UP).toString()
}


fun convertirCadenaEnvioSMS(
    /*Lista: ArrayList<ListaClienteDetalleEntity>,
    fuerzatrabajo_id: String,
    nombrefuerzatrabajo: String,
    recibo: String,
    fecharecibo: String?*/
    collectionDetail: CollectionDetail?
): String? {
    var CadenaSMS = ""
    try {
        //for (i in Lista.indices) {
            //CadenaSMS="GlaGlaGla2";
            CadenaSMS = """VISTONY
        R.U.C N 20102306598 Mz. B1 Lt. 1 - Parque Industrial de Ancon -
        Acompia Central: (01) 5521325 E-mail: ventas@vistony.com Web:
        www.vistony.com
        ${collectionDetail?.CardCode}
        ${collectionDetail?.CardName}
        *********************DATOS COBRADOR*******************
        ${collectionDetail?.SlpCode} ${collectionDetail?.SlpName}
        *********************DATOS DOCUMENTO******************
        Fecha:     ${Induvis.getDate(BuildConfig.FLAVOR, collectionDetail?.IncomeDate)}
        Recibo:     ${collectionDetail?.Receip}
        Documento:     ${collectionDetail?.LegalNumber}
        Importe :     """ + Convert.currencyForView(collectionDetail?.DocTotal) + "\n" +
        "Saldo :      " + Convert.currencyForView(collectionDetail?.Balance) + "\n" +
        "Cobrado :     " + Convert.currencyForView(collectionDetail?.AmountCharged) + "\n" +
        "Nuevo Saldo:     " + Convert.currencyForView(collectionDetail?.NewBalance) + "\n" +  //"www.vistony.com/intranet/hash="+encHash+ "\n" +
        "Codigo Validacion SMS:" + collectionDetail?.CodeSMS + ""
        //   }
            } catch (e: java.lang.Exception) {
                Log.e("REOS", "formulascontroller-convertirCadenaEnvioSMS-error-$e")
            }
    Log.e("REOS", "formulascontroller-convertirCadenaEnvioSMS-CadenaSMS-$CadenaSMS")
    return CadenaSMS
}


fun sendSMS(telefono: String?,context: Context,activity: Activity,collectionDetail: CollectionDetail?) {

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.SEND_SMS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.SEND_SMS), 1)
    }
    val smsManager = SmsManager.getDefault()
    try {
        //SmsManager sms = SmsManager.getDefault();
        val parts = smsManager.divideMessage(
            /*formulasController.convertirCadenaEnvioSMS(
                listaClienteDetalleAdapterFragment,
                SesionEntity.fuerzatrabajo_id,
                SesionEntity.nombrefuerzadetrabajo,
                CobranzaDetalleView.recibo,
                fecha
            )*/
            convertirCadenaEnvioSMS(collectionDetail)
        )
        //sms.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
        smsManager.sendMultipartTextMessage(
            telefono,
            null //, formulasController.convertirCadenaEnvioSMS(listaClienteDetalleAdapterFragment, SesionEntity.fuerzatrabajo_id, SesionEntity.nombrefuerzadetrabajo, recibo, fecha)
            ,
            parts,
            null,
            null
        )
        Toast.makeText(context, "Mensaje de Texto Enviado Correctamente", Toast.LENGTH_SHORT)
            .show()
        //alertdialogInformative(getContext()).show()
    } catch (e: java.lang.Exception) {
        Log.e("REOS", "CobranzaDetalleView-alertaEnviarSMS-Erroe-$e")
    }
}
