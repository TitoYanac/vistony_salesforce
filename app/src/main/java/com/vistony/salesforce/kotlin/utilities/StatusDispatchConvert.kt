package com.vistony.salesforce.kotlin.utilities

import android.content.Context
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.kotlin.data.AppDatabase
import com.vistony.salesforce.kotlin.data.DetailDispatchSheet
import com.vistony.salesforce.kotlin.data.StatusDispatch
import com.vistony.salesforce.kotlin.data.StatusDispatchViewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.collections.ArrayList


public fun ConvertStatusDispatch(
        Delivered:String,
        ReturnReason:String,
        comments:String,
        PhotoDocument:String,
        latitud:String,
        longitud:String,
        detailDispatchSheet: DetailDispatchSheet,
        PhotoStore:String,
        context:Context,
        DeliveryNotes:String,
        ReturnReasonText:String,
        statusDispatchViewModel: StatusDispatchViewModel
        ) {
    val dateFormathora = SimpleDateFormat("HHmmss", Locale.getDefault())
    val FormatFecha = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    val date = Date()
    var listStatusDispatch: List<StatusDispatch> = emptyList()
    var statusDispatch: StatusDispatch = StatusDispatch(
        0,
        SesionEntity.compania_id,
        SesionEntity.fuerzatrabajo_id,
        SesionEntity.usuario_id,
        Delivered,
        ReturnReason,
        detailDispatchSheet.cliente_id,
        detailDispatchSheet.factura_id,
        detailDispatchSheet.entrega_id,
        "N",
        comments,
        FormatFecha.format(date),
        dateFormathora.format(date),
        PhotoDocument,
        latitud,
        longitud,
        detailDispatchSheet.nombrecliente,
        detailDispatchSheet.factura,
        detailDispatchSheet.entrega,
        "",
        "",
        "N",
        "",
        detailDispatchSheet.control_id.toString(),
        detailDispatchSheet.item_id.toString(),
        PhotoStore,
        detailDispatchSheet.control_id.toString(),
        detailDispatchSheet.direccion,
        "0",
        "0",
        "N",
        SesionEntity.nombrefuerzadetrabajo,
        "N",
        "",
        "",
        detailDispatchSheet.domembarque_id.toString(),
        DeliveryNotes,
        ReturnReasonText,
    )
    listStatusDispatch += statusDispatch
    val executor: ExecutorService = Executors.newFixedThreadPool(1)
    for (i in 1..1) {
        executor.execute {
            println("Tarea $i en ejecución en ${Thread.currentThread().name}")
            val database by lazy { AppDatabase.getInstance(context.applicationContext) }
            database?.statusDispatchDao?.addStatusDispatch(listStatusDispatch)
            statusDispatchViewModel.sendAPIStatusDispatch(context)
            statusDispatchViewModel.sendAPIPhotoStatusDispatch(context)
        }

    }

}
