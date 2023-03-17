package com.vistony.salesforce.kotlin.client.ubigeous

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UbigeousDao {
    @Query("SELECT * FROM ubigeous")
    fun getUbigeous(): LiveData<List<Ubigeous>>

    @Query("SELECT * FROM ubigeous WHERE code = :code")
    fun getUbigeo(code: String): LiveData<Ubigeous>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUbigeous(ubigeous: List<Ubigeous>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUbigeous(ubigeous: Ubigeous)

    fun insertUbigeois(){
        Log.e("REOS", "UbigeousDao-insertUbigeois-Inicio")
        val ubigeous: Ubigeous? =null
        var  Lista: List<Ubigeous>? = null

        ubigeous?.compania_id="C001"
        ubigeous?.fuerzatrabajo_id="7"
        ubigeous?.usuario_id="7"
        ubigeous?.code="10001057"
        ubigeous?.U_SYP_DEPA="Lima"
        ubigeous?.U_SYP_PROV="Lima"
        ubigeous?.U_SYP_DIST="Ancon"
        ubigeous?.U_VIS_Flete="2"


        Lista?.indexOf(ubigeous)
        Lista?.size
        //insertAllUbigeous(Lista)
        Log.e("REOS", "UbigeousDao-insertUbigeois-Lista?.size"+Lista?.size)
    }
}