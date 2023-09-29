package com.vistony.salesforce.kotlin.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visitsection")
data class VisitSection(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var compania_id: String?,
    var fuerzatrabajo_id: String?,
    var usuario_id: String?,
    var cliente_id: String?,
    var domembarque_id: String?,
    var latitudini: String?,
    var longitudini: String?,
    var dateini: String?,
    var timeini: String?,
    var latitudfin: String?,
    var longitudfin: String?,
    var datefin: String?,
    var timefin: String?,
    var chkrecibido: String?,
    var idref: String?,
    var cardname: String?,
    var idrefitemid: String?,
    var legalnumberref: String?
)

/*{
    constructor(
                     compania_id: String?,
                     fuerzatrabajo_id: String?,
                     usuario_id: String?,
                     cliente_id: String?,
                     domembarque_id: String?,
                     latitudini: String?,
                     longitudini: String?,
                     dateini: String?,
                     timeini: String?,
                     latitudfin: String?,
                     longitudfin: String?,
                     datefin: String?,
                     timefin: String?,
                     chkrecibido: String?,
                     idref: String?,
                     cardname: String?,
                     idrefitemid: String?,
                     legalnumberref: String?
    ):this
        (
        compania_id,
        fuerzatrabajo_id,
        usuario_id,
        cliente_id,
        domembarque_id,
        latitudini,
        longitudini,
        dateini,
        timeini,
        latitudfin,
        longitudfin,
        datefin,
        timefin,
        chkrecibido,
        idref,
        cardname,
        idrefitemid,
        legalnumberref
    )
}*/
