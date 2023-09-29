package com.vistony.salesforce.kotlin.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DetailDispatchSheetDao {
    @Query("SELECT * FROM detaildispatchsheet")
    fun getDetailDispatchSheet(): LiveData<List<DetailDispatchSheet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailDispatchSheet(DetailDispatchSheet: List<DetailDispatchSheet>?)

    @Query("DELETE FROM detaildispatchsheet WHERE control_id = :codeControl")
    fun deleteDetailDispatchSheet(codeControl: Int)

    @Query(
        "Select DISTINCT A.id,A.compania_id,A.fuerzatrabajo_id,A.usuario_id,A.control_id,A.item_id,A.cliente_id,A.domembarque_id,A.direccion,A.factura_id,A.entrega_id,A.entrega,A.factura " +
                ",A.saldo,G.typedispatch as estado,A.factura_fuerzatrabajo_id,A.factura_fuerzatrabajo,A.terminopago_id,A.terminopago " +
                ",A.peso" +
                ",G.typedispatch,H.reasondispatch as motivo,IFNULL(I.latitude,'0') as latitud, IFNULL(I.longitude,'0') as longitud " +
                ",A.comentariodespacho,B.nombrecliente" +
                ",IFNULL(E.timeini,A.timeini) timeini, IFNULL(A.estado,IFNULL(E.timefin,A.timefin)) timefin" +
                ",(CASE WHEN E.timeini IS NOT NULL THEN 'Y' ELSE 'N' END) AS statusvisitstart" +
                ",(CASE WHEN c.compania_id IS NOT NULL  THEN 'Y' ELSE 'N' END) AS statusupdatedispatch" +
                ",(CASE WHEN F.DocEntryFT IS NOT NULL THEN 'Y' ELSE 'N' END) AS statuscollection" +
                ",(CASE WHEN E.timefin <> 0 THEN 'Y' ELSE 'N' END) AS  statusvisitend" +
                " from detaildispatchsheet A" +
                " left outer join cliente B ON  " +
                "A.cliente_id=B.cliente_id " +
                " left outer join (SELECT control_id,fechahojadespacho,fuerzatrabajo_id FROM headerdispatchsheet group by control_id,fechahojadespacho,fuerzatrabajo_id ) D ON  " +
                "A.fuerzatrabajo_id=D.fuerzatrabajo_id  AND " +
                "A.control_id=D.control_id   " +
                " left outer join (SELECT compania_id,cliente_id,entrega_id,fuerzatrabajo_id,DocEntry as control_id,LineId as item_id FROM statusdispatch group by compania_id,cliente_id,entrega_id,fuerzatrabajo_id,DocEntry,LineId ) C ON  " +
                "A.cliente_id=C.cliente_id  AND " +
                "A.entrega_id=C.entrega_id  AND " +
                "A.fuerzatrabajo_id=C.fuerzatrabajo_id AND " +
                "A.control_id=C.control_id AND " +
                "A.item_id=C.item_id " +
                " left outer join visitsection E ON  " +
                "D.fuerzatrabajo_id=E.fuerzatrabajo_id  AND " +
                "D.control_id=E.idref AND " +
                "A.cliente_id=E.cliente_id AND " +
                "A.domembarque_id=E.domembarque_id  " +
                "LEFT OUTER JOIN collectiondetail F ON " +
                "A.cliente_id=F.CardCode  AND " +
                "F.DocEntryFT=A.factura_id" +
                " LEFT OUTER JOIN typedispatch G ON " +
                "A.estado_id=G.typedispatch_id  " +
                " LEFT OUTER JOIN reasondispatch H ON " +
                "A.motivo_id=H.reasondispatch_id  " +
                " LEFT OUTER JOIN direccioncliente I ON " +
                "a.cliente_id=I.cliente_id and " +
                "a.domembarque_id=I.domembarque_id  " +
                " where D.fechahojadespacho= :dateDispatch " +
                " AND G.typedispatch_id in (:statusValue1,:statusValue2) " +
                //" AND G.typedispatch_id in :statusDispatch " +
                " ORDER BY A.item_id"
    )
    fun getDetailDispatchSheetforDateStatus(
        dateDispatch: String,
        statusValue1: String,
        statusValue2: String
    ): List<DetailDispatchSheet>?
}