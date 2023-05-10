package com.vistony.salesforce.kotlin.data

import androidx.room.DatabaseView
/*
@DatabaseView("Select A.id,A.compania_id,A.fuerzatrabajo_id,A.usuario_id,A.control_id,A.item_id,A.cliente_id,A.domembarque_id,A.direccion,A.factura_id,A.entrega_id,A.entrega,A.factura," +
        "A.saldo,A.estado,A.factura_fuerzatrabajo_id,A.factura_fuerzatrabajo,A.terminopago_id,A.terminopago,A.peso,A.comentariodespacho,B.nombrecliente,IFNULL(c.compania_id,'') as chkupdatedispatch " +
        ", IFNULL(E.timeini,'0') timeini, IFNULL(A.estado,IFNULL(E.timefin,'0')) timefin,IFNULL(F.DocEntryFT,'')   chk_collection,G.typedispatch,H.reasondispatch,IFNULL(I.latitude,'0') as latitud, IFNULL(I.longitude,'0') as longitud " +
        "from detaildispatchsheet A" +
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
        " AND G.typedispatch_id in ('A','V') " +
        " ORDER BY A.item_id")*/
//data class DispatchSheetVistTable()
