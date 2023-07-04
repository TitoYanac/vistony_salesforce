package com.vistony.salesforce.kotlin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
//import androidx.work.OneTimeWorkRequestBuilder
//import androidx.work.WorkManager
//import androidx.work.workDataOf
//import com.vistony.salesforce.kotlin.data.UbigeousWorker.Companion.KEY_FILENAME
import com.vistony.salesforce.kotlin.utilities.DATABASE_NAME

@Database(entities = [
    Ubigeous::class,
    HeaderDispatchSheet::class,
    DetailDispatchSheet::class,
    Client::class,
    Address::class,
    Invoices::class,
    CollectionDetail::class,
    TypeDispatch::class,
    ReasonDispatch::class,
    StatusDispatch::class,
    VisitSection::class,
    //CollectionDetail::class

                     ], version = 62,exportSchema = false)
//@Database(entities = [Ubigeous::class], version = 1,exportSchema = false)
@TypeConverters(
    DetailDispatchConverter::class,
    Converters::class,
    AddressConverter::class,
    InvoicesConverter::class

)
abstract class AppDatabase : RoomDatabase() {
    abstract val ubigeoousDao: UbigeousDao
    abstract val headerDispatchSheetDao: HeaderDispatchSheetDao
    abstract val detailDispatchSheetDao: DetailDispatchSheetDao
    abstract val clientDao: ClientDao
    abstract val addressDao: AddressDao
    abstract val invoicesDao: InvoicesDao
    abstract val typeDispatchDao: TypeDispatchDao
    abstract val reasonDispatchDao: ReasonDispatchDao
    abstract val visitSectionDao: VisitSectionDao
    abstract val collectionDetailDao: CollectionDetailDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase?=null


        fun getInstance(context: Context): AppDatabase? {
            /*return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }*/
            instance ?: synchronized(this){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    //.allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    /*.addMigrations(
                        Migrations.MIGRATIONS_27_28
                    )*/
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
