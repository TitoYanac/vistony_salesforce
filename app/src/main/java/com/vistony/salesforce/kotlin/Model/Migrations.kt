package com.vistony.salesforce.kotlin.Model

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val MIGRATIONS_27_28: Migration = object : Migration(27,28){
        override fun migrate(database: SupportSQLiteDatabase) {


            //parametrosSQLite=new ParametrosSQLite(context);
            //Sistema
            database.execSQL("CREATE TABLE parametros ( parametro_id text PRINARY KEY,nombreparametro text, cantidadregistros text, fechacarga text)")
            //database.execSQL("INSERT INTO parametros (parametro_id,nombreparametro,cantidadregistros,fechacarga) SELECT parametro_id,nombreparametro,cantidadregistros,fechacarga FROM [dbcobranzas].[parametros] ")
        }
    }
}