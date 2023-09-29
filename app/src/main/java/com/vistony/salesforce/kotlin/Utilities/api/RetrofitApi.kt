package com.vistony.salesforce.kotlin.Utilities.api

import com.vistony.salesforce.BuildConfig
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse
import com.vistony.salesforce.kotlin.Model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Ocurrencies/Dispatch")
    fun getReasonDispatch(@Query("imei") imei: String?): Call<ReasonDispatchResponse?>?

    @POST(BuildConfig.BASE_ENDPOINTPOST + BuildConfig.BASE_ENVIRONMENT + "/Collections")
    fun sendCollection(@Body params: RequestBody?): Call<CollectionDetailEntity>

    @PATCH(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Dispatch")
    fun sendStatusDispatch(@Body params: RequestBody?): Call<ResponseSendAPIStatusDispatch?>?

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Notifications/APP_SERVICE")
    fun getServicesApp(@Query("imei") imei: String?,@Query("code") code: String?): Call<ServiceAppEntity?>?

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Notifications/APP_SALESCALENDAR")
    fun getCalendar(
        @Query("imei") imei: String?,
        @Query("from") from: String?,
        @Query("to") to: String?,
    ): Call<SalesCalendarEntity?>?

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Collections/Status2")
    fun getCollectionDetail(
        @Query("imei") imei: String?,
        @Query("status") TipoFecha: String?,
        @Query("user") user: String?
    ): Call<CollectionDetailEntity?>?

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Banks")
    fun getBanks(@Query("imei") imei: String?): Call<BankEntity?>?
}