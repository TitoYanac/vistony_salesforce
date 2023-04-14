package com.vistony.salesforce.kotlin.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visitsection")
data class VisitSection(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
var compania_id: String?,
val fuerzatrabajo_id: String,
val usuario_id: String,
val cliente_id: String,
val domembarque_id: String,
val latitudini: String,
val longitudini: String,
val dateini: String,
val timeini: String,
val latitudfin: String,
val longitudfin: String,
val datefin: String,
val timefin: String,
val chkrecibido: String,
val idref: String,
val cardname: String,
val idrefitemid: String,
val legalnumberref: String
)
