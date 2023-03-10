package com.vistony.salesforce.kotlin.validationaccountclient.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vistony.salesforce.Controller.Retrofit.Api
import com.vistony.salesforce.Controller.Retrofit.Config
import com.vistony.salesforce.Controller.Utilitario.KardexPagoPDF
import com.vistony.salesforce.Dao.SQLite.BancoSQLite
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite
import com.vistony.salesforce.Entity.Retrofit.Modelo.KardexPagoEntity
import com.vistony.salesforce.Entity.Retrofit.Respuesta.KardexPagoEntityResponse
import com.vistony.salesforce.kotlin.validationaccountclient.ui.ValidationAccountClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ValidationAccountClientApi : ViewModel() {
    private val bancoSQLite: BancoSQLite? = null
    private val parametrosSQLite: ParametrosSQLite? = null
    private var status = MutableLiveData<List<ValidationAccountClientModel>?>()


    fun getValidationAccountClientApi(
        Imei: String?,
        SalesRepCode: String?,
        context: Context?,
        Day: String?
    ): MutableLiveData<List<ValidationAccountClientModel>?>? {
        status = MutableLiveData()
        Config.getClient().create(
            Api::class.java
        ).getValidationAccountClient(SalesRepCode,Day).enqueue(object : Callback<ValidationAccountClientModelResponse?> {
            override fun onResponse(
                call: Call<ValidationAccountClientModelResponse?>,
                response: Response<ValidationAccountClientModelResponse?>
            ) {
                Log.e("REOS", "KardexPagoRepository.getKardexPago.call:$call")
                val ValidationAccountClient = KardexPagoPDF()
                val ValidationAccountClientModelResponse = response.body()
                Log.e("REOS", "KardexPagoRepository.getKardexPago.response:$response")
                if (response.isSuccessful && ValidationAccountClientModelResponse!!.getValidationAccountModel()?.size!! > 0) {
                    status.setValue(ValidationAccountClientModelResponse.getValidationAccountModel() as List<ValidationAccountClientModel>?)
                } else {
                    status.setValue(null)
                }
            }

            override fun onFailure(call: Call<ValidationAccountClientModelResponse?>, t: Throwable) {
                status.value = null
            }
        })
        return status
    }
}