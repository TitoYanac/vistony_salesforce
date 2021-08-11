package com.vistony.salesforce.Controller.Retrofit;

import com.vistony.salesforce.Entity.Retrofit.Modelo.CatalogoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VersionEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.AgenciaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ClienteEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.CobranzaCabeceraEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ComisionesEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.DireccionClienteEntityResponse;
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
import com.vistony.salesforce.Entity.Retrofit.Respuesta.StockEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.TerminoPagoEntityResponse;

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
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {

    @GET
    Call<LoginEntityResponse> getUsers(@Url String url);

    @GET
    Call<VersionEntity> getVs(@Url String url);

    @GET
    Call<ClienteEntityResponse> getCliente (@Url String url);

    @GET
    Call<AgenciaEntityResponse> getAgencia (@Url String url);

    @GET
    Call<BancoEntityResponse> getBanco (@Url String url);

    @GET
    Call<TerminoPagoEntityResponse> getTerminoPago (@Url String url);

    @GET
    Call<StockEntityResponse> getStock (@Url String url);

    @GET
    Call<ListaPromocionEntityResponse> getListaPromocion (@Url String url);

    @GET
    Call<RutaFuerzaTrabajoEntityResponse> getRutaFuerzaTrabajo (@Url String url);

    @GET
    Call<ListaPrecioDetalleEntityResponse> getListaPrecioDetalle (@Url String url);

    @GET
    Call<DocumentoDeudaEntityResponse> getDocumentoDeuda (@Url String url);

    @Multipart
    @POST
    Call<ResponseBody> sendBackup(@Url String pathUrl, @Part MultipartBody.Part fileSqlite);

    @GET
    Call<List<CatalogoEntity>> getCatalog(@Url String pathUrl);

    @GET
    Call<DireccionClienteEntityResponse> getDireccionCliente (@Url String url);

    @FormUrlEncoded
    @POST
    Call<Void> getVisita (@Url String url, @FieldMap HashMap<String, String> params);

    @GET
    Call<PromocionCabeceraEntityResponse> getPromomocionCabecera (@Url String url);

    @GET
    Call<PromocionDetalleEntityResponse> getPromomocionDetalle (@Url String url);

    @GET
    Call<HistoricoOrdenVentaEntityResponse> getHistoricoOrdenVenta (@Url String url);//revisar

    @GET
    Call<HistoricoCobranzaEntityResponse> getHistoricoCobranza (@Url String url);//revisar

    @POST
    Call<Void> PostInsertCobranzaD (@Url String url,@FieldMap HashMap<String, String> params);

    @POST
    Call<Void> sendOrder (@Url String url,@Body RequestBody params);

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @GET
    Call<ResponseBody> getNewApk(@Url String url);



    @GET("/AppVistonySalesTestNew/ServicioApp.svc/LeerCobranzaC/{Imei},{Compania_ID},{FechaDepositoIni},{FechaDepositoFin},{Fuerzatrabajo_ID}")
    Call<HistoricoDepositoEntityResponse> getHistoricoDeposito (@Path("Imei") String Imei,@Path("Compania_ID") String CompaniaID,@Path("FechaDepositoIni") String FechaDepositoIni,@Path("FechaDepositoFin") String FechaDepositoFin,@Path("Fuerzatrabajo_ID") String Fuerzatrabajo_ID);

    @GET("/AppVistonySalesTestNew/ServicioApp.svc/LeerCobranzaDeposito/{Imei},{Compania_ID},{Usuario_ID},{Banco_ID},{Deposito_ID}")
    Call<HistoricoDepositoUnidadEntityResponse> getHistoricoDepositoIndividual (@Path("Imei") String Imei,@Path("Compania_ID") String Compania_ID,@Path("Usuario_ID") String Usuario_ID,@Path("Banco_ID") String Banco_ID,@Path("Deposito_ID") String Deposito_ID);



    //@GET("/LeerCobranzaRecibo ")
    @GET("/AppVistonySalesTestNew/ServicioApp.svc/LeerCobranzaRecibo/{Imei},{Compania_ID},{Usuario_ID},{Recibo}")
    Call<HistoricoCobranzaUnidadEntityResponse> getHistoricoCobranzaIndividual (@Path("Imei") String Imei,@Path("Compania_ID") String Compania_ID,@Path("Usuario_ID") String Usuario_ID,@Path("Recibo") String Recibo);

    @GET("/AppVistonySalesTestNew/ServicioApp.svc/InsertCobranzaC/{Imei},{TipoCrud},{Compania_ID},{Banco_ID},{TipoIngreso}," +
            "{Deposito_ID},{Usuario_ID},{FechaDeposito},{MontoDeposito},{Estado},{Comentario}" +
            ",{FuerzaTrabajo_ID},{Bancarizacion},{FechaDiferida},{MotivoAnulacion},{DepositoDirecto},{PagoPOS}")
    Call<CobranzaCabeceraEntityResponse> PostInsertCobranzaC (
            @Path("Imei") String Imei,
            @Path("TipoCrud") String TipoCrud,
            @Path("Compania_ID") String Compania_ID,
            @Path("Banco_ID") String Banco_ID,
            @Path("TipoIngreso") String TipoIngreso,
            @Path("Deposito_ID") String Deposito_ID,
            @Path("Usuario_ID") String Usuario_ID,
            @Path("FechaDeposito") String FechaDeposito,
            @Path("MontoDeposito") String MontoDeposito,
            @Path("Estado") String Estado,
            @Path("Comentario") String Comentario,
            @Path("FuerzaTrabajo_ID") String FuerzaTrabajo_ID,
            @Path("Bancarizacion") String Bancarizacion,
            @Path("FechaDiferida") String FechaDiferida,
            @Path("MotivoAnulacion") String MotivoAnulacion,
            @Path("DepositoDirecto") String DepositoDirecto,
            @Path("PagoPOS") String pagopos

    );

    @GET
    Call<ComisionesEntityResponse> getComisiones (@Url String url);



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