package com.vistony.salesforce.kotlin.api

import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.Entity.Retrofit.Modelo.CobranzaDetalleEntity
import com.vistony.salesforce.kotlin.data.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST(BuildConfig.BASE_ENDPOINTPOST + BuildConfig.BASE_ENVIRONMENT + "/Collections")
    fun sendCollection(@Body params: RequestBody?): Call<ResponseCollectionDetail>
}