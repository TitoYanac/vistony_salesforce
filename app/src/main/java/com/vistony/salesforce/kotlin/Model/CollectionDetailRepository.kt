package com.vistony.salesforce.kotlin.Model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity
import com.vistony.salesforce.Entity.SesionEntity
import com.vistony.salesforce.kotlin.Utilities.*
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitApi
import com.vistony.salesforce.kotlin.Utilities.api.RetrofitConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CollectionDetailRepository(appContext: Context) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val database by lazy {
        AppDatabase.getInstance(appContext.applicationContext)
    }

    private val retrofitConfig by lazy {
        RetrofitConfig()
    }

    private val _result_add = MutableStateFlow(CollectionDetailEntity())
    val result_add: StateFlow<CollectionDetailEntity> get() = _result_add

    private val _result_collection_unit = MutableStateFlow(CollectionDetailEntity())
    val result_collection_unit: StateFlow<CollectionDetailEntity> get() = _result_collection_unit

    private val _result_send_API = MutableStateFlow(CollectionDetailEntity())
    val result_send_API: StateFlow<CollectionDetailEntity> get() = _result_send_API

    private val _result_pending_deposit = MutableStateFlow(CollectionDetailEntity())
    val result_pending_deposit: StateFlow<CollectionDetailEntity> get() = _result_pending_deposit

    private val _result_get_API = MutableStateFlow(CollectionDetailEntity())
    val result_get_API: StateFlow<CollectionDetailEntity> get() = _result_get_API

    private val _result_get_DB = MutableStateFlow(CollectionDetailEntity())
    val result_get_DB: StateFlow<CollectionDetailEntity> get() = _result_get_DB

    suspend fun addListCollectionDetail(
        Imei: String,
        context: Context,
        invoices: Invoices?,
        newBalance: String,
        collection: String,
        typeCollection: String,
        company: String,
        userid: String,
        cardName: String,
        commentary: String
    ) {
        var ultimocorrelativorecibo: Int = 0
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    var ObjUsuario = UsuarioSQLiteEntity()
                    val usuarioSQLite = UsuarioSQLite(context)
                    ObjUsuario = usuarioSQLite.ObtenerUsuarioSesion()

                    var lastreceip = database?.collectionDetailDao
                        ?.getLastReceip(
                            company,
                            userid
                        )
                    ultimocorrelativorecibo =
                        getSumNumbersReceip(
                            getNumberMax(lastreceip, ObjUsuario.recibo),
                            "1"
                        )!!.toInt()
                    database?.collectionDetailDao
                        ?.addListCollectionDetail(
                            CreateListCollectionDetail(
                                context,
                                invoices,
                                newBalance,
                                collection,
                                typeCollection,
                                cardName,
                                commentary,
                                ultimocorrelativorecibo.toString()
                            )
                        )
                    val data = database?.collectionDetailDao
                        ?.getCollectionDetailUnit(
                            ultimocorrelativorecibo.toString(),
                            SesionEntity.usuario_id
                        )
                    _result_add.value = CollectionDetailEntity(data = data!!, Status = "Y")
                    Log.e(
                        "REOS",
                        "CollectionDetailRepository-addListCollectionDetail-_result_add.value.Status: " + _result_add.value.Status
                    )
                    println("Tarea $i completada")
                }
            }
            executor.shutdown()

        } catch (e: Exception) {
            _result_add.value.Status = "N"
            Log.e(
                "REOS",
                "CollectionDetailRepository-addListCollectionDetail-error: " + e.toString()
            )
        }
    }

    suspend fun getCollectionDetailUnit(
        Receip: String,
        UserID: String,
        context: Context
    ) {
        try {
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    val data = database?.collectionDetailDao
                        ?.getCollectionDetailUnit(
                            Receip,
                            UserID
                        )
                    _result_collection_unit.value =
                        CollectionDetailEntity(data = data!!, Status = "Y")
                    Log.e(
                        "REOS",
                        "CollectionDetailRepository-getCollectionDetailUnit-_result_collection_unit.value.Status: " + _result_collection_unit.value.Status
                    )
                    println("Tarea $i completada")
                }
            }
            executor.shutdown()
        } catch (e: Exception) {
            _result_add.value.Status = "N"
            Log.e(
                "REOS",
                "CollectionDetailRepository-addListCollectionDetail-error: " + e.toString()
            )
        }
    }


    fun SendAPICollectionDetail(context: Context, CompanyCode: String, UserID: String) {
        Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-Context "+ context)
        Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-CompanyCode" + CompanyCode)
        Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-ingresoOnResponse" + UserID)
        val executor: ExecutorService = Executors.newFixedThreadPool(1)
        for (i in 1..1) {
            executor.execute {
                println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                val data = database?.collectionDetailDao
                  //  ?.getCollectionDetailSendAPIList(
                        ?.sendAPICollectionDetail(
                        CompanyCode,
                        UserID
                    )

                var json: String? = null
                val gson = Gson()
                try {
                    if (!data.isNullOrEmpty()) {
                        //json = gson.toJson(listCollectionDetail)
                        json = gson.toJson(data)
                        json = "{ \"Collections\":$json}"
                    }
                    Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-json: $json")
                    if (json != null) {
                        //val jsonRequest: RequestBody = RequestBody.create(json, .parse("application/json; charset=utf-8"))
                        val jsonRequest: RequestBody = RequestBody.create(
                            ("application/json; charset=utf-8").toMediaTypeOrNull(),
                            json
                        )
                        val retrofitConfig: RetrofitConfig? = RetrofitConfig()
                        val service = retrofitConfig?.getClientLog()?.create(
                            RetrofitApi
                            ::class.java
                        )

                        service?.sendCollection(
                            jsonRequest
                        )?.enqueue(object : Callback<CollectionDetailEntity?> {
                            override fun onResponse(
                                call: Call<CollectionDetailEntity?>,
                                response: Response<CollectionDetailEntity?>
                            ) {
                                Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-ingresoOnResponse")
                                val cobranzaDetalleEntity = response.body()
                                Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-response.isSuccessful" + response.isSuccessful)
                                Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-cobranzaDetalleEntity: " + cobranzaDetalleEntity)

                                if (response.isSuccessful && cobranzaDetalleEntity != null) {
                                    Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-response.isSuccessful")
                                    val responseData = ArrayList<String>()
                                    for (respuesta in cobranzaDetalleEntity.data!!) {
                                        Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail- for")
                                        var response = "N"
                                        response =
                                            if (respuesta.APICode != null && respuesta.APIErrorCode == "0") {
                                                //responseData.add("El Recibo fue aceptado en SAP")
                                                "Y"
                                            } else {
                                                //responseData.add("El Recibo no fue aceptado en SAP")
                                                "N"
                                            }
                                        val executor1: ExecutorService =
                                            Executors.newFixedThreadPool(1)
                                        for (i in 1..1) {
                                            executor1.execute {
                                                Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-respuesta.Receip.toString() "+ respuesta.Receip.toString())
                                                Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-UserID " +UserID)
                                                Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-respuesta.APICode!! " + respuesta.APICode!!)
                                                Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail- respuesta.APIMessage!! "+respuesta.APIMessage!!)
                                                Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-response " +response)
                                                val data = database?.collectionDetailDao
                                                    ?.updateCollectionDetailAPI(
                                                        respuesta.Receip.toString(),
                                                        UserID,
                                                        respuesta.APICode!!,
                                                        respuesta.APIMessage!!,
                                                        response
                                                    )
                                            }
                                        }
                                        executor1.shutdown()

                                        _result_send_API.value = CollectionDetailEntity(
                                            Status = response,
                                            data = emptyList()
                                        )
                                        Log.e(
                                            "REOS",
                                            "CollectionDetailRepository-SendAPICollectionDetail-_result_send_API.value:" + _result_send_API.value
                                        )
                                    }
                                }
                            }

                            override fun onFailure(
                                call: Call<CollectionDetailEntity?>,
                                t: Throwable
                            ) {
                                Log.e(
                                    "REOS",
                                    "CollectionDetailRepository-SendAPICollectionDetail-ingresoonFailure:" + t.toString()
                                )
                                _result_send_API.value =
                                    CollectionDetailEntity(Status = "N", data = emptyList())
                            }
                        })
                    }
                } catch (e: java.lang.Exception) {
                    Log.e("REOS", "CollectionDetailRepository-SendAPICollectionDetail-error: $e")
                }
                println("Tarea $i completada")
            }
        }
        executor.shutdown()
    }

    fun getCollectionDetailPendingDeposit(
        IncomeDate: String,
        context: Context
    ) {
        try {
            Log.e("REOS", "CollectionDetailRepository-getCollectionDetailPendingDeposit-IncomeDate: " + IncomeDate)
            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            for (i in 1..1) {
                executor.execute {
                    println("Tarea $i en ejecución en ${Thread.currentThread().name}")
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                    val data = database?.collectionDetailDao
                        ?.getCollectionDetailPendingDeposit(
                            IncomeDate
                        )
                    _result_pending_deposit.value =
                        CollectionDetailEntity(data = data!!, Status = "Y")
                    Log.e(
                        "REOS",
                        "CollectionDetailRepository-getCollectionDetailPendingDeposit-_result_pending_deposit.value: " + _result_collection_unit.value
                    )
                    println("Tarea $i completada")
                }
            }
            executor.shutdown()
        } catch (e: Exception) {
            _result_pending_deposit.value.Status = "N"
            Log.e(
                "REOS",
                "CollectionDetailRepository-getCollectionDetailPendingDeposit-error: " + e.toString()
            )
        }
    }


    fun getAPICollectionDetail(context: Context, Imei: String, UserID: String, Status: String) {
        val executor: ExecutorService = Executors.newFixedThreadPool(1)
        for (i in 1..1) {
            executor.execute {
                try {
                    val retrofitConfig: RetrofitConfig? = RetrofitConfig()
                    val service = retrofitConfig?.getClientLog()?.create(
                        RetrofitApi
                        ::class.java
                    )
                    service?.getCollectionDetail(
                        Imei, Status, UserID
                    )?.enqueue(object : Callback<CollectionDetailEntity?> {
                        override fun onResponse(
                            call: Call<CollectionDetailEntity?>,
                            response: Response<CollectionDetailEntity?>
                        ) {
                            Log.e(
                                "REOS",
                                "CollectionDetailRepository-getAPICollectionDetail-ingresoOnResponse"
                            )
                            val cobranzaDetalleEntity = response.body()
                            Log.e(
                                "REOS",
                                "CollectionDetailRepository-getAPICollectionDetail-response.isSuccessful" + response.isSuccessful
                            )
                            Log.e(
                                "REOS",
                                "CollectionDetailRepository-getAPICollectionDetail-cobranzaDetalleEntity: " + cobranzaDetalleEntity
                            )

                            if (response.isSuccessful && cobranzaDetalleEntity != null) {
                                Log.e(
                                    "REOS",
                                    "CollectionDetailRepository-getAPICollectionDetail-response.isSuccessful"
                                )
                                    _result_get_API.value = CollectionDetailEntity(
                                        Status = "Y",
                                        data = cobranzaDetalleEntity.data
                                    )

                                    val executor1: ExecutorService = Executors.newFixedThreadPool(1)
                                    for (i in 1..1) {
                                        executor1.execute {
                                            val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                                            val data = database?.collectionDetailDao
                                                ?.addListCollectionDetail(
                                                    cobranzaDetalleEntity.data
                                                )
                                        }
                                    }
                                    executor1.shutdown()

                            }
                        }

                        override fun onFailure(call: Call<CollectionDetailEntity?>, t: Throwable) {
                            Log.e(
                                "REOS",
                                "CollectionDetailRepository-getAPICollectionDetail-ingresoonFailure:" + t.toString()
                            )
                            _result_get_API.value =
                                CollectionDetailEntity(Status = "N", data = emptyList())
                        }
                    })

                } catch (e: java.lang.Exception) {
                    Log.e("REOS", "CollectionDetailRepository-getAPICollectionDetail-error: $e")
                }
                println("Tarea $i completada")
            }
        }
        executor.shutdown()
    }

    fun getCountCollectionDetail(context: Context) {
        val executor: ExecutorService = Executors.newFixedThreadPool(1)
        for (i in 1..1) {
            executor.execute {
                val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                val data = database?.collectionDetailDao
                    ?.getCountCollectionDetail()
                _result_get_DB.value =
                    CollectionDetailEntity(Status = "Y", Count = data.toString(),data = emptyList())
            }
        }
        executor.shutdown()
    }


    fun updateDepositCollectionDetail(
            context: Context,
            collectionDetailList:List<CollectionDetail>,
            deposit:String,
            bank:String
    ) {
        var bankList= bank.split(Regex("-"))
        var bankCode=bankList[0]
        var bankName=bankList[1]

            val executor: ExecutorService = Executors.newFixedThreadPool(1)
            executor.execute {
                try {
                    val database by lazy { AppDatabase.getInstance(context.applicationContext) }

                    for (i in 0 until collectionDetailList.size)
                    {

                        database?.collectionDetailDao?.updateDepositCollectionDetail(
                                collectionDetailList.get(i).Receip,
                                deposit,
                                bankCode
                        )
                    }
                }
                catch (e:Exception)
                {
                    Log.e("REOS", "CollectionDetailRepository-updateDepositCollectionDetail-error: $e")
                }

            }
            executor.shutdown()
    }

    /*
    fun updateDepositCollectionDetail(
            context: Context,
            collectionDetailList:List<CollectionDetail>,
            deposit:String,
            bank:String
    ) {
        coroutineScope.launch {
            try {
                 updateDepositDB(collectionDetailList)
            } catch (e: Exception) {
                Log.e("REOS", "Error sending API update for deposit collection detail: $e")
            }
        }
    }

    private suspend fun updateDepositDB(data: List<CollectionDetail>,deposit:String,bank:String) {
        withContext(Dispatchers.IO) {
            var bankList= bank.split(Regex("-"))
            var bankCode=bankList[0]
            var bankName=bankList[1]
            data.forEach { detail ->
                database?.collectionDetailDao?.updateDepositCollectionDetail(
                        collectionDetailList.get(i).Receip,
                        deposit,
                        bankCode
                )
            }
        }
    }*/
    suspend fun sendAPIUpdateDepositCollectionDetail() {
        coroutineScope.launch {
            try {
                // Preparar y enviar datos a la API
                val response = sendDepositDetails()
                // Manejar la respuesta aquí o pasar los datos a otro método para su procesamiento
                if (response!!.isSuccessful) {
                    response.body()?.let { entity ->
                        updateDatabase(entity.data)
                    }
                } else {
                    // Log or handle error response
                }
            } catch (e: Exception) {
                Log.e("REOS", "Error sending API update for deposit collection detail: $e")
            }
        }
    }

    private suspend fun sendDepositDetails(): Response<CollectionDetailEntity?>? {
        val data = database!!.collectionDetailDao.getCollectionDetailDeposited()
        val json = prepareJson(data)
        val service = retrofitConfig.getClientLog()!!.create(RetrofitApi::class.java)
        val jsonRequest = json!!.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return service.updateCollection(data.first().APICode, jsonRequest)
    }

    private fun prepareJson(data: List<CollectionDetailPendingDeposit>?): String? {
        if (data.isNullOrEmpty()) return null

        return Gson().toJson(data).let {
            "{ \"Collections\":$it}"
        }
    }

    private suspend fun updateDatabase(data: List<CollectionDetail>) {
        withContext(Dispatchers.IO) {
            data.forEach { detail ->
                database!!.collectionDetailDao?.updateDepositReceiveCollectionDetail(detail.Number)
            }
        }
    }

    private fun String.toRequestBody(mediaType: MediaType?): RequestBody {
        return RequestBody.create(mediaType, this)
    }

    suspend fun getCollectionDetailForDate(IncomeDate: String)
    {
        try {
            getCollectionDetailForDateDB(IncomeDate)

        } catch (e: Exception) {
            Log.e("REOS", "Error sending API collection detail: $e")
            _result_get_DB.value = CollectionDetailEntity(Status = "N",data = emptyList())
        }
    }

    private suspend fun getCollectionDetailForDateDB(IncomeDate: String) {
        withContext(Dispatchers.IO) {
                var data=database!!.collectionDetailDao?.getCollectionDetailForDate(IncomeDate)
                _result_get_DB.value = CollectionDetailEntity(Status = "Y",data = data!!)
                Log.e("REOS", "CollectionDetailRepository-getCollectionDetailForDateDB-_result_get_DB.value  "+_result_get_DB.value )
        }
    }

    /*
    fun getCollectionDetailForDateAPI(Imei: String, Date: String) {
        val executor: ExecutorService = Executors.newFixedThreadPool(1)
        for (i in 1..1) {
            executor.execute {
                try {
                    val retrofitConfig: RetrofitConfig? = RetrofitConfig()
                    val service = retrofitConfig?.getClientLog()?.create(
                            RetrofitApi
                            ::class.java
                    )
                    service?.getCollectionDetail(
                            Imei, Status, UserID
                    )?.enqueue(object : Callback<CollectionDetailEntity?> {
                        override fun onResponse(
                                call: Call<CollectionDetailEntity?>,
                                response: Response<CollectionDetailEntity?>
                        ) {
                            val cobranzaDetalleEntity = response.body()

                            if (response.isSuccessful && cobranzaDetalleEntity != null) {
                                _result_get_API.value = CollectionDetailEntity(
                                        Status = "Y",
                                        data = cobranzaDetalleEntity.data
                                )

                                val executor1: ExecutorService = Executors.newFixedThreadPool(1)
                                for (i in 1..1) {
                                    executor1.execute {
                                        val database by lazy { AppDatabase.getInstance(context.applicationContext) }
                                        val data = database?.collectionDetailDao
                                                ?.addListCollectionDetail(
                                                        cobranzaDetalleEntity.data
                                                )
                                    }
                                }
                                executor1.shutdown()

                            }
                        }

                        override fun onFailure(call: Call<CollectionDetailEntity?>, t: Throwable) {
                            Log.e(
                                    "REOS",
                                    "CollectionDetailRepository-getAPICollectionDetail-ingresoonFailure:" + t.toString()
                            )
                            _result_get_API.value =
                                    CollectionDetailEntity(Status = "N", data = emptyList())
                        }
                    })

                } catch (e: java.lang.Exception) {
                    Log.e("REOS", "CollectionDetailRepository-getAPICollectionDetail-error: $e")
                }
                println("Tarea $i completada")
            }
        }
        executor.shutdown()
    }

    suspend fun getCollectionDetailForDateAPI(Imei: String, Date: String) {
        coroutineScope.launch {
            try {
                // Preparar y enviar datos a la API
                val response = sendDepositDetails()
                // Manejar la respuesta aquí o pasar los datos a otro método para su procesamiento
                if (response!!.isSuccessful) {
                    response.body()?.let { entity ->
                        updateDatabase(entity.data)
                    }
                } else {
                    // Log or handle error response
                }
            } catch (e: Exception) {
                Log.e("REOS", "Error sending API update for deposit collection detail: $e")
            }
        }
    }*/
}