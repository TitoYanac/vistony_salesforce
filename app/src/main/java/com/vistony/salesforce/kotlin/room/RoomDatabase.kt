package com.vistony.salesforce.kotlin.room

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
//import androidx.work.OneTimeWorkRequestBuilder
//import androidx.work.WorkManager
//import androidx.work.workDataOf
import com.vistony.salesforce.kotlin.client.ubigeous.Converters
import com.vistony.salesforce.kotlin.client.ubigeous.Ubigeous
import com.vistony.salesforce.kotlin.client.ubigeous.UbigeousDao
import com.vistony.salesforce.kotlin.client.ubigeous.UbigeousWorker
//import com.vistony.salesforce.kotlin.client.ubigeous.UbigeousWorker.Companion.KEY_FILENAME
import com.vistony.salesforce.kotlin.utilities.DATABASE_NAME
import com.vistony.salesforce.kotlin.utilities.PLANT_DATA_FILENAME

@Database(entities = [Ubigeous::class], version = 27,exportSchema = false)
//@Database(entities = [Ubigeous::class], version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun ubigeoousDao(): UbigeousDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: RoomDatabase? = null


        fun getInstance(context: Context): RoomDatabase? {
            /*return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }*/
            instance?: synchronized(this){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabase::class.java,
                    DATABASE_NAME
                )
                    //.allowMainThreadQueries()
                    //.fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        /*fun buildDatabase(context: Context): RoomDatabase {
            return Room.databaseBuilder(context, RoomDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<UbigeousWorker>()
                                .setInputData(workDataOf(KEY_FILENAME to PLANT_DATA_FILENAME))
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }*/
    }
}
