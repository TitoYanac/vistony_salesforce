package com.vistony.salesforce.Controller.Retrofit;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CatalogoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CobranzaDetalleEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintFormsEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintSectionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SalesOrderEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VersionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VisitaEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.AgenciaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ClienteEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ComisionesEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.CustomerComplaintFormsEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.CustomerComplaintSectionEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.DepositList;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.DetailDispatchSheetEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.EscColoursCEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.EscColoursDEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HeaderDispatchSheetEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HeaderStatusDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricContainerSalesEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricSalesAnalysisByRouteEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricSalesOrderTraceabilityEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricStatusDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoCobranzaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoCobranzaUnidadEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoDepositoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoDepositoUnidadEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoFacturasEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoFacturasHistorialDespachoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoFacturasLineasNoFacturadasEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoOrdenVentaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.KardexPagoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.LeadAddressEntityResponse;
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
import com.vistony.salesforce.Entity.Retrofit.Respuesta.QuotationEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.QuoteEffectivenessEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ReasonDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.RutaFuerzaTrabajoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.SalesOrderEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.SignatureEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.StockEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.SummaryofeffectivenessEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.TerminoPagoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.TypeDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.UbigeoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.WareHousesEntityResponse;
import com.vistony.salesforce.kotlin.data.ValidationAccountClientModelResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {
    //@Headers("Content-Type: application/json")
    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Users")
    Call<LoginEntityResponse> getUsers(@Query("imei") String imei);

    @GET
        //EndPoint de acuerdo al FLAVOR
    Call<VersionEntity> getVs(@Url String url);

    //@Streaming
    @GET(BuildConfig.BASE_ENDPOINTPOST + BuildConfig.BASE_ENVIRONMENT + "/version")
    Call<ResponseBody> getNewApk(@Query("v") String version);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Banks")
    Call<BancoEntityResponse> getBanco(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/customers")
    Call<ClienteEntityResponse> getCliente(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/customers")
    Call<ClienteEntityResponse> getClienteInformation(@Query("imei") String imei, @Query("cliente") String cliente);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Dispatch/Customer")
        //@GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Dispatch")
    Call<ClienteEntityResponse> getClientDelivery(@Query("imei") String imei, @Query("fecha") String fecha);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/WorkPath")
    Call<RutaFuerzaTrabajoEntityResponse> getRutaFuerzaTrabajo(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/pricelist")
    Call<ListaPrecioDetalleEntityResponse> getListaPrecioDetalle(@Query("imei") String imei);

    @POST(BuildConfig.BASE_ENDPOINTPOST + BuildConfig.BASE_ENVIRONMENT + "/SalesOrder")
    Call<SalesOrderEntityResponse> sendOrder(@Body RequestBody params);

    @POST(BuildConfig.BASE_ENDPOINTPOST + BuildConfig.BASE_ENVIRONMENT + "/Collections")
        //@POST(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Collections")
    Call<CobranzaDetalleEntity> sendCollection(@Body RequestBody params);

    //@POST(BuildConfig.BASE_ENDPOINTPOST+BuildConfig.BASE_ENVIRONMENT+"/Collections/Valid")
    //@POST(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Collections")
    //Call<CobranzaDetalleEntity> sendCollectionCountSend ( @Body RequestBody params);

    //@PATCH(BuildConfig.BASE_ENDPOINTPOST+BuildConfig.BASE_ENVIRONMENT+"/Collections")
    @PATCH(BuildConfig.BASE_ENDPOINTPOST + BuildConfig.BASE_ENVIRONMENT + "/Collections/{codeSap}")
    Call<CobranzaDetalleEntity> updateCollection(@Path("codeSap") String codeSap, @Body RequestBody params);
    //Call<CobranzaDetalleEntity> updateCollection(@Body RequestBody params);

    @PATCH(BuildConfig.BASE_ENDPOINTPOST + BuildConfig.BASE_ENVIRONMENT + "/Collections/{codeSap}")
        //@PATCH(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Collections/{codeSap}")
    Call<CobranzaDetalleEntity> updateCollectionQR(@Path("codeSap") String codeSap, @Body RequestBody params);

    @Multipart
    @POST
    Call<ResponseBody> sendBackup(@Url String url, @Part MultipartBody.Part fileSqlite);

    @GET
    Call<List<CatalogoEntity>> getCatalog(@Url String pathUrl);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/agencies")
    Call<AgenciaEntityResponse> getAgencia(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/PaymentTerms")
    Call<TerminoPagoEntityResponse> getTerminoPago(@Query("imei") String imei);

    @POST(BuildConfig.BASE_ENDPOINTPOST + BuildConfig.BASE_ENVIRONMENT + "/Visit")
        //@POST(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Visit")
    Call<VisitaEntity> sendVisit(@Header("Token") String content_type, @Body RequestBody params/*@FieldMap HashMap<String, String> params*/);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/SalesOrder")
    Call<HistoricoOrdenVentaEntityResponse> getHistoricoOrdenVenta(@Query("imei") String imei, @Query("fecha") String fecha);

    @POST(BuildConfig.BASE_ENDPOINTPOST + BuildConfig.BASE_ENVIRONMENT + "/Deposits")
        //@POST(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Deposits")
    Call<DepositList> sendDeposit(@Body RequestBody params);


    //@PATCH(BuildConfig.BASE_ENDPOINTPOST+BuildConfig.BASE_ENVIRONMENT+"/Deposits/{codeSap}")
    @PATCH(BuildConfig.BASE_ENDPOINTPOST + BuildConfig.BASE_ENVIRONMENT + "/Deposits")
    //Call<DepositList> updateDeposit(@Path("codeSap") String codeSap, @Body RequestBody params);
    Call<DepositList> updateDeposit(@Body RequestBody params);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Deposits")
    Call<HistoricoDepositoEntityResponse> getHistoricoDeposito(@Query("imei") String imei, @Query("fecIni") String fecIni, @Query("fecFin") String fecFin);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Collections/Date")
    Call<HistoricoCobranzaEntityResponse> getHistoricoCobranza(@Query("imei") String imei, @Query("fecha") String fecha);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Collections/Status2")
    Call<HistoricoCobranzaEntityResponse> getHistoricoCobranzaPDSupervisor(@Query("imei") String imei, @Query("status") String TipoFecha, @Query("user") String user);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Collections/Status")
    Call<HistoricoCobranzaEntityResponse> getHistoricoCobranzaPD(@Query("imei") String imei, @Query("status") String TipoFecha);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Collections/Deposit")
    Call<HistoricoCobranzaEntityResponse> getHistoricoCobranzaDE(@Query("imei") String imei, @Query("deposit") String Deposito);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/PromotionType")
    Call<ListaPromocionEntityResponse> getListaPromocion(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/PromotionHeader")
    Call<PromocionCabeceraEntityResponse> getPromomocionCabecera(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/PromotionDetail")
    Call<PromocionDetalleEntityResponse> getPromomocionDetalle(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/ReasonVisit")
    Call<MotivoVisitaEntityResponse> getReasonVisits(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Kardex")
    Call<KardexPagoEntityResponse> getKardexPago(@Query("CardCode") String Cardcode);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/PriceListHead")
    Call<PriceListEntityResponse> getPriceList(@Query("imei") String imei);


    @PATCH(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Dispatch")
        //@POST(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Visit")
    Call<HeaderStatusDispatchEntityResponse> sendStatusDispatch(@Body RequestBody params/*@FieldMap HashMap<String, String> params*/);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/HistoricalSales/Variable")
    Call<HistoricContainerSalesEntityResponse> getDailySummary(
            @Query("imei") String imei,
            @Query("fecini") String fecini,
            @Query("fecfin") String fecfin
    );

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/HistoricalSales/Type")
    Call<HistoricContainerSalesEntityResponse> getHistoricContainerSales(
            @Query("imei") String imei,
            @Query("type") String type,
            @Query("cardCode") String cardcode,
            @Query("fecini") String fecini,
            @Query("fecfin") String fecfin
    );


    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Quota")
    Call<QuotasPerCustomerEntityHeadResponse> getQuotasPerCustomerHead(
            @Query("CardCode") String CardCode,
            @Query("SlpCode") String SlpCode
    );

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Quota/Detail")
    Call<QuotasPerCustomerDetailEntityResponse> getQuotasPerCustomerDetail(
            @Query("CardCode") String CardCode,
            @Query("SlpCode") String SlpCode
    );

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Quota/Invoice")
    Call<QuotasPerCustomerInvoiceEntityResponse> getQuotasPerCustomerInvoice(
            @Query("SlpCode") String SlpCode,
            @Query("CardCode") String CardCode

    );

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Invoices")
        //Pruebas Mockups Pedidos
    Call<HistoricoFacturasEntityResponse> getHistoricoFactura(@Query("imei") String Imei,
                                                              @Query("fecha") String FechaFactura);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Commissions")
    Call<ComisionesEntityResponse> getComisiones(@Query("imei") String Imei,
                                                 @Query("Year") String Year,
                                                 @Query("Month") String Month
    );

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Colors")
    Call<EscColoursCEntityResponse> getScColoursC(@Query("imei") String Imei
    );

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/ScaColoursD")
    Call<EscColoursDEntityResponse> getScColoursD(@Query("imei") String Imei
    );

    //@GET("/AppVistonySalesTest/ServicioApp.svc/Obtener_DespachoC/{Imei},{Compania_ID},{FuerzaTrabajo_ID},{FechaDespacho}") //Maestro de Hoja de Despacho Cabecera
    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Dispatch/List")
    //@GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Dispatch")
    Call<HeaderDispatchSheetEntityResponse> getHeaderDispatchSheet(
            @Query("imei") String Imei
            , @Query("fecha") String FechaDespacho

    );

    //@GET("/AppVistonySalesTest/ServicioApp.svc/Obtener_DespachoD/{Imei},{Compania_ID},{FuerzaTrabajo_ID},{FechaDespacho}") //Maestro de Hoja de Despacho Cabecera
    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/DetailDispatchSheet")
    Call<DetailDispatchSheetEntityResponse> getDetailDispatchSheet(
            @Query("Imei") String Imei
            , @Query("FechaDespacho") String FechaDespacho
    );

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/WareHouses")
    Call<WareHousesEntityResponse> getWareHouses(@Query("imei") String imei, @Query("ItemCode") String ItemCode);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Effectiveness")
    Call<SummaryofeffectivenessEntityResponse> getSummaryofEffectiveness(
            @Query("imei") String imei,
            @Query("startDate") String fecini,
            @Query("endDate") String fecfin
    );

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Dispatch/Type")
    Call<TypeDispatchEntityResponse> getTypeDispatch(@Query("imei") String imei);


    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/HistoricStatusDispatch")
    Call<HistoricStatusDispatchEntityResponse> geHistoricStatusDispatch(@Query("imei") String imei, @Query("date") String date);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Traceabilities")
    Call<HistoricSalesOrderTraceabilityEntityResponse> geHistoricSalesOrderTraceability(@Query("imei") String imei, @Query("fecha") String date);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/AnalysisRoute")
    Call<HistoricSalesAnalysisByRouteEntityResponse> geHistoricSalesAnalysisByRoute(@Query("imei") String imei, @Query("dia") String date);

    @POST("https://reclamos.vistonyapp.com/upload/image")
    Call<Void> postPrueba(@Body RequestBody params);

    @POST(BuildConfig.BASE_ENDPOINTPOST + BuildConfig.BASE_ENVIRONMENT + "/Collections/signature/{codeSap}")
        //@PATCH(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Collections/{codeSap}")
    Call<SignatureEntityResponse> updateCollectionSignature(@Path("codeSap") String codeSap, @Body RequestBody params);

    @POST(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Quotation")
    Call<QuotationEntityResponse> geHistoricQuotation(@Body RequestBody params);

    @POST(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Quotation/({docEntry})/CreateSalesOrder")
    Call<SalesOrderEntityResponse> sendQuotation(@Path("docEntry") int docEntry, @Body RequestBody params);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Effectiveness/Quote")
    Call<QuoteEffectivenessEntityResponse> getQuoteEffectiveness(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Dispatch/List")
    Call<HeaderDispatchSheetEntityResponse> getHeaderDispatchSheetSalesPerson(
            @Query("imei") String Imei
            , @Query("fecha") String FechaDespacho
            , @Query("flag") String Seller
    );

    @PATCH(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Customers/Address")
    Call<LeadAddressEntityResponse> sendLeadAddress(@Body RequestBody params/*@FieldMap HashMap<String, String> params*/);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/ValidationAccountClient")
    Call<ValidationAccountClientModelResponse> getValidationAccountClient(@Query("SalesRepCode") String SalesRepCode, @Query("Day") String Day);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Ubigeos")
    Call<UbigeoEntityResponse> getUbigeo(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/Reclamo")
    Call<CustomerComplaintFormsEntityResponse> getCustomerComplaint(
            //@Query("Imei") String imei, @Query("CardCode") String CardCode
    );

    @POST(BuildConfig.BASE_ENDPOINTPOST + BuildConfig.BASE_ENVIRONMENT + "/Reclamo")
        //@POST(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Collections")
    Call<CustomerComplaintFormsEntity> sendForms(@Body RequestBody params);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Ocurrencies/Dispatch")
    //@GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Ocurrencies")
    Call<ReasonDispatchEntityResponse> getReasonDispatch(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/Ocurrencies/FreeTransfer")
    Call<ReasonDispatchEntityResponse> getReasonFreeTransfer(@Query("imei") String imei);

    @GET(BuildConfig.BASE_ENDPOINT + BuildConfig.BASE_ENVIRONMENT + "/SalesOrder/Validate")
    Call<SalesOrderEntity> getSalesOrderValidate(
            @Query("CardCode") String CardCode,
            @Query("DocDate") String DocDate,
            @Query("SalesOrderID") String SalesOrderID,
            @Query("slpCode") String slpCode
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