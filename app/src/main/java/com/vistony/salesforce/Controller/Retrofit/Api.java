package com.vistony.salesforce.Controller.Retrofit;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CatalogoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CobranzaDetalleEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerInvoiceEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VersionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VisitaEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.AgenciaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ClienteEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ComisionesEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.DepositList;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.EscColoursCEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.EscColoursDEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricContainerSalesEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoCobranzaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoCobranzaUnidadEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoDepositoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoDepositoUnidadEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoFacturasEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoFacturasHistorialDespachoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoFacturasLineasNoFacturadasEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoOrdenVentaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.KardexPagoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ListaPrecioDetalleEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ListaPromocionEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.LoginEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.MotivoVisitaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PriceListEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PromocionCabeceraEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PromocionDetalleEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.QuotasPerCustomerDetailEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.QuotasPerCustomerEntityHeadResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.QuotasPerCustomerInvoiceEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ResumenDiarioEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.RutaFuerzaTrabajoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.SalesOrderEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.StockEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.TerminoPagoEntityResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {
    //@Headers("Content-Type: application/json")
    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Users")
    Call<LoginEntityResponse> getUsers(@Query("imei") String imei);

    @GET //EndPoint de acuerdo al FLAVOR
    Call<VersionEntity> getVs(@Url String url);

    //@Streaming
    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/version")
    Call<ResponseBody> getNewApk(@Query("v") String version);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Banks")
    Call<BancoEntityResponse> getBanco(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/customers")
    Call<ClienteEntityResponse> getCliente (@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/customers")
    Call<ClienteEntityResponse> getClienteInformation (@Query("imei") String imei,@Query("cliente") String cliente);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/WorkPath")
    Call<RutaFuerzaTrabajoEntityResponse> getRutaFuerzaTrabajo (@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/pricelist")
    Call<ListaPrecioDetalleEntityResponse> getListaPrecioDetalle (@Query("imei") String imei);

    @POST(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/SalesOrder")
    Call<SalesOrderEntityResponse> sendOrder(@Body RequestBody params);

    @POST(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Collections")
    Call<CobranzaDetalleEntity> sendCollection( @Body RequestBody params);

    @PATCH(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Collections/{codeSap}")
    Call<CobranzaDetalleEntity> updateCollection(@Path("codeSap") String codeSap, @Body RequestBody params);

    @PATCH(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Collections/{codeSap}")
    Call<CobranzaDetalleEntity> updateCollectionQR(@Path("codeSap") String codeSap, @Body RequestBody params);

    @Multipart
    @POST
    Call<ResponseBody> sendBackup(@Url String url, @Part MultipartBody.Part fileSqlite);

    @GET
    Call<List<CatalogoEntity>> getCatalog(@Url String pathUrl);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/agencies")
    Call<AgenciaEntityResponse> getAgencia (@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/PaymentTerms")
    Call<TerminoPagoEntityResponse> getTerminoPago (@Query("imei") String imei);

    @POST(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Visit")
    Call<VisitaEntity> sendVisit (@Body RequestBody params/*@FieldMap HashMap<String, String> params*/);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/SalesOrder")
    Call<HistoricoOrdenVentaEntityResponse> getHistoricoOrdenVenta (@Query("imei") String imei,@Query("fecha") String fecha);

    @POST(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Deposits")
    Call<DepositList> sendDeposit (@Body RequestBody params);


    @PATCH(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Deposits/{codeSap}")
    Call<DepositList> updateDeposit(@Path("codeSap") String codeSap, @Body RequestBody params);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Deposits")
    Call<HistoricoDepositoEntityResponse> getHistoricoDeposito (@Query("imei") String imei,@Query("fecIni") String fecIni,@Query("fecFin") String fecFin);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Collections")
    Call<HistoricoCobranzaEntityResponse> getHistoricoCobranza(@Query("imei") String imei,@Query("fecha") String fecha);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Collections")
    Call<HistoricoCobranzaEntityResponse> getHistoricoCobranzaPD(@Query("imei") String imei,@Query("status") String TipoFecha);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Collections")
    Call<HistoricoCobranzaEntityResponse> getHistoricoCobranzaDE(@Query("imei") String imei,@Query("deposit") String Deposito);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/PromotionType")
    Call<ListaPromocionEntityResponse> getListaPromocion(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/PromotionHeader")
    Call<PromocionCabeceraEntityResponse> getPromomocionCabecera(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/PromotionDetail")
    Call<PromocionDetalleEntityResponse> getPromomocionDetalle(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/ReasonVisit")
    Call<MotivoVisitaEntityResponse> getReasonVisits(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Kardex")
    Call<KardexPagoEntityResponse> getKardexPago(@Query("CardCode") String Cardcode);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/PriceListHead")
    Call<PriceListEntityResponse> getPriceList(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/HistoricalSales")
    Call<HistoricContainerSalesEntityResponse> getDailySummary(
            @Query("company") String company,
            @Query("imei") String imei,
            @Query("fecini") String fecini,
            @Query("fecfin") String fecfin
            );

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/HistoricalSales")
    Call<HistoricContainerSalesEntityResponse> getHistoricContainerSales(
            @Query("company") String company,
            @Query("imei") String imei,
            @Query("type") String type,
            @Query("cardCode") String cardcode,
            @Query("fecini") String fecini,
            @Query("fecfin") String fecfin
            );


    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Quota")
    Call<QuotasPerCustomerEntityHeadResponse> getQuotasPerCustomerHead(
            @Query("CardCode") String CardCode,
            @Query("SlpCode") String SlpCode
    );

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/QuotaDetail")
    Call<QuotasPerCustomerDetailEntityResponse> getQuotasPerCustomerDetail(
            @Query("CardCode") String CardCode,
            @Query("SlpCode") String SlpCode
    );

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/QuotaInvoice")
    Call<QuotasPerCustomerInvoiceEntityResponse> getQuotasPerCustomerInvoice(
            @Query("SlpCode") String SlpCode,
            @Query("CardCode") String CardCode

    );

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Invoices") //Pruebas Mockups Pedidos
    Call<HistoricoFacturasEntityResponse> getHistoricoFactura (@Query("imei") String Imei,
                                                               @Query("fecha") String FechaFactura);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Commissions")
    Call<ComisionesEntityResponse> getComisiones (@Query("imei") String Imei,
                                                  @Query("SlpCode") String SlpCode,
                                                  @Query("DateIni") String Year,
                                                  @Query("DateFin") String Month
            );

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/ScaColoursC")
    Call<EscColoursCEntityResponse> getScColoursC (@Query("imei") String Imei
    );

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/ScaColoursD")
    Call<EscColoursDEntityResponse> getScColoursD (@Query("imei") String Imei
    );
   // @GET("/AppVistonySalesTestNew/ServicioApp.svc/Pedidos_Leer_FacturaC/{Imei},{Compania_ID},{Fuerzatrabajo_ID},{FechaFactura}") //Pruebas Mockups Pedidos
   //Call<HistoricoFacturasEntityResponse> getHistoricoFactura (@Path("Imei") String Imei,@Path("Compania_ID") String Compania_ID,@Path("Fuerzatrabajo_ID") String Fuerzatrabajo_ID,@Path("FechaFactura") String FechaFactura);
    //@GET
    //Call<PromocionDetalleEntityResponse> getPromomocionDetalle (@Url String url);


    //@GET
    //Call<PromocionCabeceraEntityResponse> getPromomocionCabecera (@Url String url);


    //@GET("/TipoListaPromo")
    //Call<ListaPromocionEntityResponse> getListaPromocion (@Query("imei") String imei);


    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    @POST //("/customers")
    Call<Void> sendLead(@Url String url,@Body RequestBody params);

    //@GET
    //Call<DocumentoDeudaEntityResponse> getDocumentoDeuda (@Url String url);

    @GET
    Call<HistoricoCobranzaEntityResponse> getHistoricoCobranza (@Url String url);//revisar

    @GET//("/Stock")
    Call<StockEntityResponse> getStock (@Url String url,@Query("imei") String imei);



    //@GET
    //Call<DireccionClienteEntityResponse> getDireccionCliente (@Url String url);

   // @GET
   // Call<ComisionesEntityResponse> getComisiones (@Url String url);


    @GET("/AppVistonySalesTestNew/ServicioApp.svc/LeerCobranzaC/{Imei},{Compania_ID},{FechaDepositoIni},{FechaDepositoFin},{Fuerzatrabajo_ID}")
    Call<HistoricoDepositoEntityResponse> getHistoricoDeposito (@Path("Imei") String Imei,@Path("Compania_ID") String CompaniaID,@Path("FechaDepositoIni") String FechaDepositoIni,@Path("FechaDepositoFin") String FechaDepositoFin,@Path("Fuerzatrabajo_ID") String Fuerzatrabajo_ID);

    @GET("/AppVistonySalesTestNew/ServicioApp.svc/LeerCobranzaDeposito/{Imei},{Compania_ID},{Usuario_ID},{Banco_ID},{Deposito_ID}")
    Call<HistoricoDepositoUnidadEntityResponse> getHistoricoDepositoIndividual (@Path("Imei") String Imei,@Path("Compania_ID") String Compania_ID,@Path("Usuario_ID") String Usuario_ID,@Path("Banco_ID") String Banco_ID,@Path("Deposito_ID") String Deposito_ID);

    //@GET("/LeerCobranzaRecibo ")
    @GET("/AppVistonySalesTestNew/ServicioApp.svc/LeerCobranzaRecibo/{Imei},{Compania_ID},{Usuario_ID},{Recibo}")
    Call<HistoricoCobranzaUnidadEntityResponse> getHistoricoCobranzaIndividual (@Path("Imei") String Imei,@Path("Compania_ID") String Compania_ID,@Path("Usuario_ID") String Usuario_ID,@Path("Recibo") String Recibo);

    //@GET("/v3/410aace1-0575-4b4a-8090-c497576b4f0b") //Pruebas Mockups Pedidos
    @GET("/AppVistonySalesTestNew/ServicioApp.svc/Pedidos_Leer_OrdenVentaC/{Imei},{Compania_ID},{Fuerzatrabajo_ID},{FechaOrdenVenta}")
    Call<HistoricoOrdenVentaEntityResponse> getHistoricoOrdenVentaEstado (@Query("Imei") String Imei, @Query("Compania_ID") String Compania_ID, @Query("Fuerzatrabajo_ID") String Fuerzatrabajo_ID, @Query("FechaOrdenVentaInicio") String FechaOrdenVentaInicio, @Query("FechaOrdenVentaFinal") String FechaOrdenVentaFinal);
    //Call<HistoricoOrdenVentaEntityResponse> getHistoricoOrdenVenta (@Path("Imei") String Imei, @Path("Compania_ID") String Compania_ID, @Path("Fuerzatrabajo_ID") String Fuerzatrabajo_ID, @Path("FechaOrdenVenta") String FechaOrdenVenta);

    @GET("/AppVistonySalesTestNew/ServicioApp.svc/Pedidos_Leer_FacturaC/{Imei},{Compania_ID},{Fuerzatrabajo_ID},{FechaFactura}") //Pruebas Mockups Pedidos
    Call<HistoricoFacturasEntityResponse> getHistoricoFactura (@Path("Imei") String Imei,@Path("Compania_ID") String Compania_ID,@Path("Fuerzatrabajo_ID") String Fuerzatrabajo_ID,@Path("FechaFactura") String FechaFactura);

    @GET("/v3/4263f10d-0aa3-43aa-a97f-6382e333c08f")//Pruebas Mockups Pedidos
    Call<HistoricoFacturasLineasNoFacturadasEntityResponse> getHistoricoFacturaLineasNoFacturadas (
            @Query("Imei") String Imei,
            @Query("Compania_ID") String Compania_ID,
            @Query("FuerzaTrabajo_ID") String FuerzaTrabajo_ID,
            @Query("Cliente_ID") String Cliente_ID,
            @Query("Documento_ID") String Documento_ID
    );

    @GET("/v3/c060ff3e-56b0-459f-aafb-e943512e8a4b")//Pruebas Mockups Pedidos
    Call<HistoricoFacturasHistorialDespachoEntityResponse> getHistoricoFacturasHistorialDespachos (
            @Query("Imei") String Imei,
            @Query("Compania_ID") String Compania_ID,
            @Query("FuerzaTrabajo_ID") String FuerzaTrabajo_ID,
            @Query("Cliente_ID") String Cliente_ID,
            @Query("Documento_ID") String Documento_ID
    );
}