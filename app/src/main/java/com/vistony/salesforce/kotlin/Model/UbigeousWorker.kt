package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UbigeousWorker (
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams

) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {

            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
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
                var ubigeousDao: UbigeousDao? = null
                ubigeousDao?.insertAllUbigeous(Lista)

                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        /*val plantType = object : TypeToken<List<Ubigeous>>() {}.type
                        val plantList: List<Ubigeous> = Gson().fromJson(jsonReader, plantType)

                        val database = RoomDatabase.getInstance(applicationContext)
                        database.plantDao().insertAll(plantList)*/
                        //val ubigeousDao: UbigeousDao?
                        //ubigeousDao?.insertUbigeois()
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
                        var ubigeousDao: UbigeousDao? = null
                        ubigeousDao?.insertAllUbigeous(Lista)
                        //UbigeousDao.insertAllUbigeous(Lista)
                        Result.success()
                    }
                }
            } else {
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
        const val KEY_FILENAME = "PLANT_DATA_FILENAME"
    }
}