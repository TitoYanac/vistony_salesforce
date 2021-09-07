package com.vistony.salesforce.Controller.Retrofit;

import com.vistony.salesforce.Entity.Retrofit.Modelo.CatalogoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CobranzaDetalleEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VersionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VisitaEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.AgenciaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ClienteEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ComisionesEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.DocumentoDeudaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoCobranzaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoCobranzaUnidadEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoDepositoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoDepositoUnidadEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoFacturasEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoFacturasHistorialDespachoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoFacturasLineasNoFacturadasEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoOrdenVentaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ListaPrecioDetalleEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ListaPromocionEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.LoginEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PromocionCabeceraEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PromocionDetalleEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.RutaFuerzaTrabajoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.SalesOrderEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.StockEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.TerminoPagoEntityResponse;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {
    //@Headers("Content-Type: application/json")
    @GET("/Users")
    Call<LoginEntityResponse> getUsers(@Query("imei") String imei);

    @GET //EndPoint de acuerdo al FLAVOR
    Call<VersionEntity> getVs(@Url String url);

    @GET("/version")
    Call<ResponseBody> getNewApk(@Query("v") String version);

    @GET("/Banks")
    Call<BancoEntityResponse> getBanco(@Query("imei") String imei);

    @GET("/customers")
    Call<ClienteEntityResponse> getCliente (@Query("imei") String imei);

    @GET("/customers")
    Call<ClienteEntityResponse> getClienteInformation (@Query("imei") String imei,@Query("cliente") String cliente);

    @GET("/WorkPath")
    Call<RutaFuerzaTrabajoEntityResponse> getRutaFuerzaTrabajo (@Query("imei") String imei);

    @GET("/pricelist")
    Call<ListaPrecioDetalleEntityResponse> getListaPrecioDetalle (@Query("imei") String imei);

    @POST("/SalesOrder")
    Call<SalesOrderEntityResponse> sendOrder(@Body RequestBody params);

    @POST("/Collections")
    Call<CobranzaDetalleEntity> sendCollection( @Body RequestBody params);

    @PATCH("/Collections/{codeSap}")
    Call<CobranzaDetalleEntity> updateCollection(@Path("codeSap") String codeSap, @Body RequestBody params);

    @Multipart
    @POST
    Call<ResponseBody> sendBackup(@Url String url, @Part MultipartBody.Part fileSqlite);

    @GET
    Call<List<CatalogoEntity>> getCatalog(@Url String pathUrl);

    @GET("/agencies")
    Call<AgenciaEntityResponse> getAgencia (@Query("imei") String imei);

    @GET("/PaymentTerms")
    Call<TerminoPagoEntityResponse> getTerminoPago (@Query("imei") String imei);

    @POST("/Visit")
    Call<VisitaEntity> sendVisit (@Body RequestBody params/*@FieldMap HashMap<String, String> params*/);

    @POST("/SalesOrder")
    Call<HistoricoOrdenVentaEntityResponse> getHistoricoOrdenVenta (@Query("imei") String imei,@Query("fecha") String fecha);

    @POST("/Deposits")
    Call<Void> sendDeposit (@Body RequestBody params);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @GET
    Call<PromocionCabeceraEntityResponse> getPromomocionCabecera (@Url String url);

    @GET
    Call<PromocionDetalleEntityResponse> getPromomocionDetalle (@Url String url);

    @POST //("/customers")
    Call<Void> sendLead(@Url String url,@Body RequestBody params);

    //@GET
    //Call<DocumentoDeudaEntityResponse> getDocumentoDeuda (@Url String url);

    @GET
    Call<HistoricoCobranzaEntityResponse> getHistoricoCobranza (@Url String url);//revisar

    @GET//("/Stock")
    Call<StockEntityResponse> getStock (@Url String url,@Query("imei") String imei);

    @GET("/TipoListaPromo")
    Call<ListaPromocionEntityResponse> getListaPromocion (@Query("imei") String imei);

    //@GET
    //Call<DireccionClienteEntityResponse> getDireccionCliente (@Url String url);

    @GET
    Call<ComisionesEntityResponse> getComisiones (@Url String url);

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