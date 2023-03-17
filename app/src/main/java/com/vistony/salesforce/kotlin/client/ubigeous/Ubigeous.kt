package com.vistony.salesforce.kotlin.client.ubigeous

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ubigeous")
class Ubigeous
{
    var compania_id: String?=null
    var fuerzatrabajo_id: String?=null
    var usuario_id: String?=null
    @PrimaryKey
    var code: String
    var U_SYP_DEPA: String?=null
    var U_SYP_PROV: String?=null
    var U_SYP_DIST: String?=null
    var U_VIS_Flete: String?=null
    constructor(compania_id: String,fuerzatrabajo_id:String,usuario_id:String,code:String,
                U_SYP_DEPA:String,U_SYP_PROV:String,U_SYP_DIST:String,U_VIS_Flete:String
                )
    {
        this.compania_id=compania_id
        this.fuerzatrabajo_id=fuerzatrabajo_id
        this.usuario_id=usuario_id
        this.code=code
        this.U_SYP_DEPA=U_SYP_DEPA
        this.U_SYP_PROV=U_SYP_PROV
        this.U_SYP_DIST=U_SYP_DIST
        this.U_VIS_Flete=U_VIS_Flete
    }

}
