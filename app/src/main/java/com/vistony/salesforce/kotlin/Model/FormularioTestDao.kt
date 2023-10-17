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

    @Query("SELECT * FROM formsuperviser")
    fun getFormSuperviser(): LiveData<List<ApiResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFormSuperviser(FormSuperviser: ApiResponse)

    @Query("DELETE FROM formsuperviser")
    fun deleteFormSuperviser()

    @Query("SELECT * FROM maindata")
    fun getDatosPrincipales(): LiveData<List<DatosPrincipales>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDatosPrincipales(DatosPrincipales: DatosPrincipales)

    @Query("DELETE FROM maindata")
    fun deleteDatosPrincipales()

    @Query("SELECT * FROM visitdata")
    fun getDatosVisita(): LiveData<List<DatosVisita>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDatosVisita(DatosPrincipales: DatosVisita?)

    @Query("DELETE FROM visitdata")
    fun deleteDatosVisita()

    @Query("SELECT * FROM outputtype")
    fun getTipoSalida(): LiveData<List<TipoSalida>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTipoSalida(TipoSalida: List<TipoSalida>?)

    @Query("DELETE FROM outputtype")
    fun deleteTipoSalida()

    @Query("SELECT * FROM visitsummary")
    fun getResumenVisita(): LiveData<List<ResumenVisita>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addResumenVisita(ResumenVisita: List<ResumenVisita>?)

    @Query("DELETE FROM visitsummary")
    fun deleteResumenVisita()

    @Query("SELECT * FROM questionanswer")
    fun getPreguntaRespuesta(): LiveData<List<PreguntaRespuesta>>

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
