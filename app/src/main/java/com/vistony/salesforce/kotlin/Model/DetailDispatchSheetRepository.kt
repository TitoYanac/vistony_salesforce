package com.vistony.salesforce.kotlin.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailDispatchSheetRepository(context: Context) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val database by lazy {
        AppDatabase.getInstance(context.applicationContext)
    }

    private val retrofitConfig by lazy {
        RetrofitConfig()
    }

    private val _result = MutableStateFlow(DetailDispatchSheetEntity())
    val result: StateFlow<DetailDispatchSheetEntity> get() = _result

    private val _resultDB = MutableLiveData<DetailDispatchSheetEntity>()
    val resultDB: LiveData<DetailDispatchSheetEntity> get() = _resultDB

    suspend fun getStateDispatchSheet(
        Imei:String,
        FechaDespacho:String,
        context: Context,
        type: String
        )
    //: MutableLiveData<String>
    {
        try {
            //val executor: ExecutorService = Executors.newFixedThreadPool(1)
            /*Log.e(
                "REOS",
                "DetailDispatchSheetRepository-getStateDispatchSheet-FechaDespacho: " + FechaDespacho
            )*/
            //for (i in 1..1) {
                //executor.execute {
                    //println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    //Thread.sleep(1000)
            withContext(Dispatchers.IO) {
                val database by lazy { AppDatabase.getInstance(context.applicationContext) }

                when {
                    type == "F" -> {
                        var data = database?.detailDispatchSheetDao
                            ?.getDetailDispatchSheetforDateStatus(
                                FechaDespacho, "A", "V"
                            )
                        Log.e("REOS", "DetailDispatchSheetRepository-getStateDispatchSheet-F-data: " +data)
                        //CoroutineScope(Dispatchers.Default).launch {
                            try {
                                //data!!.collect { value ->
                                // _resultDB.value=DetailDispatchSheetEntity(Status="Y", DATA = value!!)
                                //}
                                _result.value =
                                //_resultDB.postValue(
                                    DetailDispatchSheetEntity(Status = "Y", UI = data!!)
                                //)
                            } catch (e: Exception) {
                                println("The flow has thrown an exception: $e")
                            }
                        //}

                    }

                    type == "P" -> {
                        var data = database?.detailDispatchSheetDao
                            ?.getDetailDispatchSheetforDateStatus(
                                FechaDespacho, "S", "P"
                            )
                        Log.e("REOS", "DetailDispatchSheetRepository-getStateDispatchSheet-P-data: " +data)
                        //CoroutineScope(Dispatchers.Default).launch {
                            try {
                                //data!!.collect { value ->
                                //    _resultDB.value=DetailDispatchSheetEntity(Status="Y", DATA = value!!)
                                // }
                                _result.value =
                                //_resultDB.postValue(
                                    DetailDispatchSheetEntity(Status = "Y", UI = data!!)
                                //)
                            } catch (e: Exception) {
                                println("The flow has thrown an exception: $e")
                            }
                        //}
                    }

                    type == "E" -> {
                        var data = database?.detailDispatchSheetDao
                            ?.getDetailDispatchSheetforDateStatus(
                                FechaDespacho, "E", ""
                            )
                        Log.e("REOS", "DetailDispatchSheetRepository-getStateDispatchSheet-E-data: " +data)
                        //CoroutineScope(Dispatchers.Default).launch {
                            try {
                                //data!!.collect { value ->
                                    _result.value=DetailDispatchSheetEntity(Status="Y", UI = data!!)
                                //}
                                //_resultDB.value =
                                //_resultDB.postValue(DetailDispatchSheetEntity(Status = "Y", UI = data!!))
                            } catch (e: Exception) {
                                println("The flow has thrown an exception: $e")
                            }
                        //}
                    }

                    type == "M" -> {
                        var data = database?.detailDispatchSheetDao
                            ?.getDetailDispatchSheetforMap(
                                FechaDespacho
                            )
                        //CoroutineScope(Dispatchers.Default).launch {
                            try {
                                //data!!.collect { value ->
                                //    _resultDB.value=DetailDispatchSheetEntity(Status="Y", DATA = value!!)
                                //}
                                var cont = 0
                                data!!.forEach {
                                    cont++
                                    it.item_id = cont
                                }
                                _result.value =
                                //_resultDB.postValue
                            (DetailDispatchSheetEntity(Status = "Y", UI = data!!))
                            } catch (e: Exception) {
                                println("The flow has thrown an exception: $e")
                            }
                       // }
                    }

                    else -> {}
                }

                // println("Tarea $i completada")
                // }

            }
            //}
           // executor.shutdown()
        }
        catch (e: Exception) {
            Log.e(
                "REOS",
                "DetailDispatchSheetRepository-getStateDispatchSheet-error: " + e.toString()
            )
        }
    }

    suspend fun getDetailDispatchSheetforMap(
        FechaDespacho:String,
        context: Context,
        type: String
    )
    {
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            /*Log.e(
                "REOS",
                "DetailDispatchSheetRepository-getStateDispatchSheet-FechaDespacho: " + FechaDespacho
            )*/
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    //Thread.sleep(1000)
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }

                    when {
                        type == "F" ->
                        {
                            var data=database?.detailDispatchSheetDao
                                ?.getDetailDispatchSheetforDateStatus(
                                    FechaDespacho, "A","V")

                            CoroutineScope(Dispatchers.Default).launch {
                                try {
                                    //data!!.collect { value ->
                                    // _resultDB.value=DetailDispatchSheetEntity(Status="Y", DATA = value!!)
                                    //}
                                    _resultDB.value=DetailDispatchSheetEntity(Status="Y", UI = data!!)
                                } catch (e: Exception) {
                                    println("The flow has thrown an exception: $e")
                                }
                            }

                        }
                        type == "P" ->
                        {
                            var data=database?.detailDispatchSheetDao
                                ?.getDetailDispatchSheetforDateStatus(
                                    FechaDespacho, "S","P")
                            CoroutineScope(Dispatchers.Default).launch {
                                try {
                                    //data!!.collect { value ->
                                    //    _resultDB.value=DetailDispatchSheetEntity(Status="Y", DATA = value!!)
                                    // }
                                    _resultDB.value=DetailDispatchSheetEntity(Status="Y", UI = data!!)
                                } catch (e: Exception) {
                                    println("The flow has thrown an exception: $e")
                                }
                            }
                        }
                        type == "E" ->
                        {
                            var data=database?.detailDispatchSheetDao
                                ?.getDetailDispatchSheetforDateStatus(
                                    FechaDespacho, "E","")
                            CoroutineScope(Dispatchers.Default).launch {
                                try {
                                    //data!!.collect { value ->
                                    //    _resultDB.value=DetailDispatchSheetEntity(Status="Y", DATA = value!!)
                                    //}
                                    _resultDB.value=DetailDispatchSheetEntity(Status="Y", UI = data!!)
                                } catch (e: Exception) {
                                    println("The flow has thrown an exception: $e")
                                }
                            }
                        }
                    }

                    println("Tarea $i completada")
                }


            }
            executor.shutdown()
        }
        catch (e: Exception) {
            Log.e(
                "REOS",
                "DetailDispatchSheetRepository-getStateDispatchSheet-error: " + e.toString()
            )
        }
    }
}