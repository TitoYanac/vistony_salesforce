package com.vistony.salesforce.kotlin.api

import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.Entity.Retrofit.Respuesta.TypeDispatchEntityResponse
import com.vistony.salesforce.kotlin.data.ClientResponse
import com.vistony.salesforce.kotlin.data.DispatchSheetResponse
import com.vistony.salesforce.kotlin.data.ReasonDispatchResponse
import com.vistony.salesforce.kotlin.data.TypeDispatchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Dispatch/List")
    fun getHeaderDispatchSheet(
        @Query("imei") Imei: String?, @Query("fecha") FechaDespacho: String?
    ): Call<DispatchSheetResponse>

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/customers")
    fun getClient(@Query("imei") imei: String?):
            Call<ClientResponse?>?

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Dispatch/Customer")
    fun  getClientDelivery(
        @Query("imei") imei: String?,
        @Query("fecha") fecha: String?
    ): Call<ClientResponse?>?

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Dispatch/Type")
    fun getTypeDispatch(@Query("imei") imei: String?): Call<TypeDispatchResponse?>?

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Ocurrencies")
    fun getReasonDispatch(@Query("imei") imei: String?): Call<ReasonDispatchResponse?>?
}