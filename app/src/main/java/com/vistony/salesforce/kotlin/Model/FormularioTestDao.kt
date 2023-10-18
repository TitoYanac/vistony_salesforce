package com.vistony.salesforce.kotlin.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vistony.salesforce.kotlin.View.Pages.ApiResponse
import com.vistony.salesforce.kotlin.View.Pages.DatosPrincipales
import com.vistony.salesforce.kotlin.View.Pages.DatosVisita
import com.vistony.salesforce.kotlin.View.Pages.Opcion
import com.vistony.salesforce.kotlin.View.Pages.PreguntaRespuesta
import com.vistony.salesforce.kotlin.View.Pages.ResumenVisita
import com.vistony.salesforce.kotlin.View.Pages.TipoSalida

@Dao
interface FormularioTestDao {

    @Query("SELECT  * FROM formsuperviser WHERE chkrecibido='N' LIMIT 1")
    fun getFormSuperviser(): ApiResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFormSuperviser(FormSuperviser: ApiResponse)

    @Query("DELETE FROM formsuperviser")
    fun deleteFormSuperviser()

    @Query("UPDATE formsuperviser SET code = :code, message = :message, chkrecibido = :chkrecibido WHERE numInforme = :num_informe ")
    fun updateStatusFormSupervisor(code: String, message: String, chkrecibido: String, num_informe: String)

    @Query("SELECT * FROM maindata where numInforme=:numInforme ")
    fun getDatosPrincipales(numInforme:String): DatosPrincipales

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDatosPrincipales(DatosPrincipales: DatosPrincipales)

    @Query("DELETE FROM maindata")
    fun deleteDatosPrincipales()

    @Query("SELECT * FROM visitdata where numInforme=:numInforme")
    fun getDatosVisita(numInforme:String): DatosVisita

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDatosVisita(DatosPrincipales: DatosVisita?)

    @Query("DELETE FROM visitdata")
    fun deleteDatosVisita()

    @Query("SELECT * FROM outputtype where numInforme=:numInforme")
    fun getTipoSalida(numInforme:String): List<TipoSalida>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTipoSalida(TipoSalida: List<TipoSalida>?)

    @Query("DELETE FROM outputtype")
    fun deleteTipoSalida()

    @Query("SELECT * FROM visitsummary where numInforme=:numInforme ")
    fun getResumenVisita(numInforme:String): List<ResumenVisita>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addResumenVisita(ResumenVisita: List<ResumenVisita>?)

    @Query("DELETE FROM visitsummary")
    fun deleteResumenVisita()

    @Query("SELECT * FROM questionanswer where numInforme=:numInforme")
    fun getPreguntaRespuesta(numInforme:String): List<PreguntaRespuesta>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPreguntaRespuesta(PreguntaRespuesta: List<PreguntaRespuesta>?)

    @Query("DELETE FROM questionanswer")
    fun deletePreguntaRespuesta()

    @Query("SELECT * FROM option")
    fun getOpcion(): LiveData<List<Opcion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOpcion(Opcion: List<Opcion>?)

    @Query("DELETE FROM option")
    fun deleteOpcion()

}
