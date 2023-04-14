package com.vistony.salesforce.kotlin.data

import com.vistony.salesforce.kotlin.data.UbigeousDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UbigeousRepository @Inject constructor(private val ubigeousDao: UbigeousDao) {

    fun getUbigeous() = ubigeousDao.getUbigeous()

    fun getUbigeo(code: String) = ubigeousDao.getUbigeo(code)

    /*fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
        plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: PlantRepository? = null

        fun getInstance(plantDao: PlantDao) =
            instance ?: synchronized(this) {
                instance ?: PlantRepository(plantDao).also { instance = it }
            }
    }*/

}