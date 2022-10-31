package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.Retrofit.JSON.CollectionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CobranzaDetalleEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CobranzaItemEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SignatureEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SignatureREntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoCobranzaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.SignatureEntityResponse;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CobranzaRepository extends ViewModel {
    private Context context;
    private String resultado="0";
    private ArrayList<ListaHistoricoCobranzaEntity> LHDCobranza  = new ArrayList<>();

    public CobranzaRepository(final Context context){
        this.context=context;
    }
    public CobranzaRepository(){}

    public String PostCobranzaDetalleWS(
            String Imei,
            String TipoCrud,
            String Compania_ID,
            String Banco_ID,
            String TipoIngreso,
            String Deposito_ID,
            String Detalle_Item,
            String Cliente_ID,
            String Documento_entry,
            String Documento_ID,
            String ImporteDocumento,
            String SaldoDocumento,
            String MontoCobrado,
            String NuevoSaldoDocumento,
            String FechaCobranza,
            String Recibo,
            String Estado,
            String Comentario,
            String Usuario_ID,
            String Fuerzatrabajo_ID,
            String Bancarizacion,
            String EstadoQR,
            String MotivoAnulacion,
            String DepositoDirecto,
            String PagoPOS,
            String Hora
    ){
        Api api = Config.getClient().create(Api.class);
        int acumResul=0;
        HashMap<String, String> params = new HashMap<>();

        params.put("BankID",""); //ok
        params.put("Deposit",""); //ok
        params.put("IncomeDate", FechaCobranza);//ok
        params.put("Banking", (Bancarizacion));//ok
        params.put("ItemDetail",Detalle_Item);//ok
        params.put("CardCode", Cliente_ID);//ok
        params.put("DocEntryFT", Documento_entry);
        params.put("DocNum",Documento_ID);//ok
        params.put("DocTotal", ImporteDocumento);//ok
        params.put("Balance", SaldoDocumento);//ok
        params.put("AmountCharged",MontoCobrado); //ok
        params.put("NewBalance",NuevoSaldoDocumento); //ok
        params.put("Receip", Recibo);//ok
        params.put("Status",Estado);
        params.put("Commentary", Comentario);//ok
        params.put("QRStatus", (EstadoQR));//ok
        params.put("UserID", Usuario_ID);//ok
        params.put("SlpCode", Fuerzatrabajo_ID); //ok
        params.put("POSPay",(PagoPOS));//ok
        params.put("DirectDeposit",(DepositoDirecto));//ok
        params.put("CancelReason", MotivoAnulacion); //ok
        Log.e("CobranzaRepository-PostCobranzaDetalleWS-IncomeTime: ",Induvis.getTimeSAP(BuildConfig.FLAVOR,Hora));
        params.put("IncomeTime", Induvis.getTimeSAP(BuildConfig.FLAVOR,Hora));

        RequestBody json = RequestBody.create("{ \"Collections\":["+(new JSONObject(params)).toString()+"]}",okhttp3.MediaType.parse("application/json; charset=utf-8"));
        Log.e("CobranzaRepository-PostCobranzaDetalleWS-json: ",json.toString());
        Call<CobranzaDetalleEntity> call = api.sendCollection(json);
        Log.e("CobranzaRepository-PostCobranzaDetalleWS-call: ",call.toString());
        try{
            Response response= call.execute();
            //sap_code
            if(response.isSuccessful()) {
                CobranzaDetalleEntity cobranzas= (CobranzaDetalleEntity) response.body();
                Log.e("CobranzaRepository-PostCobranzaDetalleWS-response: ",response.toString());
                CobranzaDetalleSQLiteDao cobranzasqlite=new CobranzaDetalleSQLiteDao(context);

                for(CobranzaItemEntity cobranza:cobranzas.getCobranzaItem()){
                    if(cobranza.getCode()!=null && cobranza.getErrorCode().equals("0")){
                        cobranzasqlite.updateStatusCodeSap(cobranza.getItemDetail(),cobranza.getCode(),Compania_ID,cobranza.getMessage(),"Y",cobranza.getReceip());
                    }else{
                        cobranzasqlite.updateStatusCodeSap(cobranza.getItemDetail(),cobranza.getCode(),Compania_ID,cobranza.getMessage(),"N",cobranza.getReceip());
                        acumResul=acumResul+1;
                    }
                }
            }else{
                acumResul=acumResul+1;
            }
        }catch (Exception e){
            e.printStackTrace();
            acumResul=acumResul+1;
        }

        if(acumResul==0){
            return "Y";
        }else{
            return "N";
        }
    }

    public static String sendPatch(String codeSap,String codeOperation,String codeBank,String receip){
        String resultado="0";
        Api api = Config.getClient().create(Api.class);

        HashMap<String, String> params = new HashMap<>();
        Log.e("REOS", "CobranzaRepository-sendPatch-codeSAP"+codeSap+"-codeOperation: "+codeOperation+"-codeBank: "+codeBank);

        params.put("Code",codeSap);
        params.put("U_VIS_Deposit",codeOperation);
        params.put("U_VIS_BankID",codeBank);
        params.put("U_VIS_Receip",receip);


        RequestBody json = RequestBody.create((new JSONObject(params)).toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));
        Call<CobranzaDetalleEntity> call = api.updateCollection(codeSap,json);
//        Call<CobranzaDetalleEntity> call = api.updateCollection(json);
        Log.e("REOS", "CobranzaRepository-sendPatch-call: "+call.toString());
        try{
            Response response= call.execute();
            //sap_code
            Log.e("REOS", "CobranzaRepository-sendPatch-response: "+response.toString());
            if(response.isSuccessful()) {
                resultado = "Y";
            }else{
                resultado = "N";
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("REOS", "CobranzaRepository-sendPatch-response-e: "+e.toString());
            resultado="N";
        }
        Log.e("REOS", "CobranzaRepository-sendPatch-response-resultado: "+resultado.toString());
        return resultado;
    }


    //Peru
    //-----------------------
    public static String sendPatchQR(String codeSap,String StatusQR){
        String resultado="0";
        Api api = Config.getClient().create(Api.class);
        Log.e("REOS", "CobranzaRepository-sendPatchQR-codeSAP"+codeSap+"-StatusQR: "+StatusQR);
        HashMap<String, String> params = new HashMap<>();

        params.put("Code",codeSap);
        params.put("U_VIS_QRStatus",StatusQR);


        RequestBody json = RequestBody.create((new JSONObject(params)).toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));
        Call<CobranzaDetalleEntity> call = api.updateCollectionQR(codeSap,json);
        Log.e("REOS", "CobranzaRepository-sendPatchQR-call: "+call.toString());

        try{
            Response response= call.execute();
            //sap_code
            Log.e("REOS", "CobranzaRepository-sendPatchQR-response: "+response.toString());
            if(response.isSuccessful()) {
                resultado = "Y";
            }else{
                resultado = "N";
            }
        }catch (Exception e){
            e.printStackTrace();
            resultado="N";
        }
        Log.e("REOS", "CobranzaRepository-sendPatchQR-resultado: "+resultado.toString());
        return resultado;
    }
    //-----------------------
    public static String EnviarReciboWsRetrofit(
            ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity,
            Context context,
            String TipoCRUD,
            String Anulacion,
            String Comentario,
            String Banco,
            String Actualizacion
    )
    {
        String resultado="0";
        CobranzaRepository cobranzaRepository =new CobranzaRepository(context);
        String banco_id="",deposito_id="",comentario="";

        try
        {
            for(int i=0;i<listaCobranzaDetalleSQLiteEntity.size();i++)
            {
                if(Anulacion.equals("Y")&&TipoCRUD.equals("UPDATE"))
                {
                    Log.e("REOS","CobranzaRepository-EnviarReciboWsRetrofit-UPDATE-Anulacion");
                    banco_id="";
                    deposito_id="";
                    comentario=listaCobranzaDetalleSQLiteEntity.get(i).getComentario();
                }else if (Actualizacion.equals("Y")&&TipoCRUD.equals("UPDATE"))
                {
                    Log.e("REOS","CobranzaRepository-EnviarReciboWsRetrofit-UPDATE-Actualizacion");
                    banco_id=Banco;
                    comentario=listaCobranzaDetalleSQLiteEntity.get(i).getComentario();
                    deposito_id=listaCobranzaDetalleSQLiteEntity.get(i).getCobranza_id();
                    Log.e("REOS","UPDATE+1"+banco_id+"-"+comentario+"-"+deposito_id);
                }
                else if(TipoCRUD.equals("CREATE"))
                {
                    Log.e("REOS","CobranzaRepository-EnviarReciboWsRetrofit-CREATE");
                    comentario=listaCobranzaDetalleSQLiteEntity.get(i).getComentario();
                    banco_id=listaCobranzaDetalleSQLiteEntity.get(i).getBanco_id();
                    deposito_id=listaCobranzaDetalleSQLiteEntity.get(i).getCobranza_id();
                }

                Log.e("REOS","CobranzaRepository-EnviarReciboWsRetrofit-TipoCRUD:"+TipoCRUD);
                resultado= cobranzaRepository.PostCobranzaDetalleWS(
                        SesionEntity.imei,
                        TipoCRUD,
                        listaCobranzaDetalleSQLiteEntity.get(i).getCompania_id(),
                        banco_id,
                        "0",
                        deposito_id,
                        listaCobranzaDetalleSQLiteEntity.get(i).getCobranza_id(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getCliente_id(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getDocumento_entry(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getDocumento_id(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getImportedocumento(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getSaldodocumento(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getSaldocobrado(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getNuevosaldodocumento(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getFechacobranza(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getRecibo(),
                        "P",  //                        "Pendiente",
                        comentario,
                        SesionEntity.usuario_id,
                        SesionEntity.fuerzatrabajo_id,
                        listaCobranzaDetalleSQLiteEntity.get(i).getChkbancarizado(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getChkqrvalidado(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getMotivoanulacion(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getPagodirecto(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getPagopos(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getHoracobranza()

                );

            }

        }catch (Exception e){
            e.printStackTrace();
            resultado="N";
        }

        return resultado;
    }

    public ArrayList<ListaHistoricoCobranzaEntity> getHistoricoCobranza(
            String Imei,
            String Fecha,
            String TipoFecha,
            String deposito_id
    ){
        Api api = Config.getClient().create(Api.class);
        Call<HistoricoCobranzaEntityResponse> call=null;

        if (TipoFecha.equals("COBRANZA"))
        {
            Log.e("REOS","CobranzaRepository-getHistoricoCobranza-TipoFecha-COBRANZA-entro");
            call = api.getHistoricoCobranza(
                    Imei,
                    Fecha
            );
            Log.e("REOS","CobranzaRepository-getHistoricoCobranza-TipoFecha-COBRANZA-api"+api.toString());
        }else if(TipoFecha.equals("PENDIENTE_DEPOSITO"))
        {
            if(BuildConfig.FLAVOR.equals("peru"))
            {
                Log.e("REOS", "CobranzaRepository-getHistoricoCobranza-TipoFecha-PENDIENTE_DEPOSITO-entro");
                call = api.getHistoricoCobranzaPDSupervisor(
                        Imei,
                        "PD",
                        SesionEntity.usuario_id
                );
            }else {
                Log.e("REOS", "CobranzaRepository-getHistoricoCobranza-TipoFecha-PENDIENTE_DEPOSITO-entro");
                call = api.getHistoricoCobranzaPD(
                        Imei,
                        "PD"
                );
            }

        }else if(TipoFecha.equals("RECIBO_ANULADO"))
        {
            Log.e("REOS","CobranzaRepository-getHistoricoCobranza-TipoFecha-RECIBO_ANULADO-entro");
            call = api.getHistoricoCobranzaPD(
                    Imei,
                    "A"
            );
        }else if(TipoFecha.equals("Deposito"))
        {
            Log.e("REOS","CobranzaRepository-getHistoricoCobranza-TipoFecha-Deposito-entro");
            Log.e("REOS","CobranzaRepository-getHistoricoCobranza-TipoFecha-Deposito-Imei"+Imei);
            Log.e("REOS","CobranzaRepository-getHistoricoCobranza-TipoFecha-Deposito-deposito_id"+deposito_id);
            call = api.getHistoricoCobranzaDE(
                    Imei,
                    deposito_id
            );
            Log.e("REOS","CobranzaRepository-getHistoricoCobranza-TipoFecha-Deposito-entro-api.getHistoricoCobranzaDE"+api.getHistoricoCobranzaDE(Imei,
                    deposito_id).toString());
            Log.e("REOS","CobranzaRepository-getHistoricoCobranza-TipoFecha-Deposito-entro-call"+call.toString());
        }

        try{
            Response<HistoricoCobranzaEntityResponse> response= call.execute();
            Log.e("REOS","CobranzaRepository-getHistoricoCobranza-call: "+call.toString());
            if(response.isSuccessful()) {

                Log.e("REOS","CobranzaRepository-getHistoricoCobranza-response(): "+response.toString());
                HistoricoCobranzaEntityResponse historicoCobranzaEntityResponse=response.body();
                Log.e("REOS","CobranzaRepository-getHistoricoCobranza-response.body(): "+response.body().toString());
//

                for(int i=0;i<historicoCobranzaEntityResponse.getHistoricoCobranza().size();i++){

                    ListaHistoricoCobranzaEntity ObjLHCobranza = new ListaHistoricoCobranzaEntity();
                    ObjLHCobranza.bancarizacion = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getBancarizacion();
                    ObjLHCobranza.banco_id = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getBanco_id();
                    ObjLHCobranza.cliente_id   = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getCliente_id();
                    ObjLHCobranza.cliente_nombre= historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getNombrecliente();
                    ObjLHCobranza.comentario = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getComentario();
                    ObjLHCobranza.compania_id = SesionEntity.compania_id;
                    ObjLHCobranza.depositodirecto = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getDepositodirecto();
                    ObjLHCobranza.detalle_item = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getDetalle_item();
                    ObjLHCobranza.documento_id = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getDocumento_id();
                    ObjLHCobranza.deposito_id = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getDeposito_id();
                    ObjLHCobranza.estado= historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getEstado();
                    ObjLHCobranza.estadoqr = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getEstadoqr();
                    /*String[] fechasepacion = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getFechacobranza().split(" ");
                    String fecha,horatotal,año,mes,dia;
                    fecha=fechasepacion[0];
                    horatotal=fechasepacion[1];
                    String[] fechasplit=fechasepacion[0].split("/");
                    mes=fechasplit[0];
                    dia=fechasplit[1];
                    año=fechasplit[2];
                    if(mes.length()==1)
                    {
                        mes='0'+mes;
                    }
                    if(dia.length()==1)
                    {
                        dia='0'+dia;
                    }
                    ObjLHCobranza.fechacobranza =  año+mes+dia;*/
                    ObjLHCobranza.fechacobranza =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getFechacobranza();
                    ObjLHCobranza.fechadeposito =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getFechadeposito();
                    ObjLHCobranza.fuerzatrabajo_id =  SesionEntity.fuerzatrabajo_id;
                    ObjLHCobranza.importedocumento =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getImportedocumento();
                    ObjLHCobranza.montocobrado =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getMontocobrado();
                    ObjLHCobranza.nro_documento =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getNro_documento();
                    ObjLHCobranza.nuevosaldodocumento =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getNuevosaldodocumento();
                    ObjLHCobranza.recibo =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getRecibo();
                    ObjLHCobranza.saldodocumento =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getSaldodocumento();
                    ObjLHCobranza.tipoingreso =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getTipoingreso();
                    ObjLHCobranza.usuario_id =  SesionEntity.usuario_id;
                    ObjLHCobranza.tipoingreso = "N";
                    ObjLHCobranza.chkwsrecibido = "Y";
                    ObjLHCobranza.pagopos=historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getPagopos();
                    ObjLHCobranza.motivoanulacion=historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getMotivoanulacion();
                    ObjLHCobranza.codesap=historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getCodesap();
                    ObjLHCobranza.mensajeWS="Recibo Registrado Correctamente";
                    ObjLHCobranza.collectioncheck=historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getCollectioncheck();
                    Log.e("REOS","CobranzaRepository-getHistoricoCobranza-historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getCodesap(): "+historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getCodesap());
                    LHDCobranza.add(ObjLHCobranza);
                    Log.e("REOS","CobranzaRepository-getHistoricoCobranza-Lista: "+LHDCobranza.size());
                }
            }

            call.cancel();
        }catch (Exception e){
            call.cancel();
            e.printStackTrace();
            Log.e("REOS","CobranzaRepository-getHistoricoCobranza-e.error: "+e.toString());
        }

        //Config.closeConection();
        Log.e("REOS","CobranzaRepository-getHistoricoCobranza-LHDCobranza: "+LHDCobranza.size());
        return LHDCobranza;
    }

    //-----------------------------------Nueva arquitectura ViewModel--------------------------
    //////////////////Enviar Recibos sin Depositar\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao;
    public MutableLiveData<String> UndepositedPendingCollection(Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();

        sendCollection(context, new CollectionCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay recibos sin depositar pendientes de enviar");
                }else{
                    temp.setValue(data.get(0));
                }
            }
            @Override
            public void onResponseErrorSap(String response) {
                temp.setValue(response);
            }
        });

        return temp;
    }

    private void sendCollection(final Context context,final CollectionCallback callback){
        String  json=null;
        Gson gson=new Gson();
        if(cobranzaDetalleSQLiteDao==null){
            cobranzaDetalleSQLiteDao =new CobranzaDetalleSQLiteDao(context);
        }
        ArrayList<CollectionEntity> listCollection=new ArrayList<>();

        listCollection = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetallePendientesFormatoJSON(SesionEntity.compania_id, SesionEntity.usuario_id);
        Log.e("REOS","CobranzaRepository-UndepositedPendingCollection-listCollection.size(): "+listCollection.size());
        try {
            if (listCollection != null && listCollection.size() > 0) {
                json = gson.toJson(listCollection);
                json = "{ \"Collections\":" + json + "}";
            }
            Log.e("REOS","CobranzaRepository-sendCollection-json: "+json);
            if (json != null) {
                RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
                Config.getClient().create(Api.class).sendCollection(jsonRequest).enqueue(new Callback<CobranzaDetalleEntity>() {
                    @Override
                    public void onResponse(Call<CobranzaDetalleEntity> call, Response<CobranzaDetalleEntity> response) {

                        CobranzaDetalleEntity cobranzaDetalleEntity = response.body();
                        Log.e("REOS","CobranzaRepository-sendCollection-response: "+response.toString());
                        if (response.isSuccessful() && cobranzaDetalleEntity != null) {
                            ArrayList<String> responseData = new ArrayList<>();

                            for (CobranzaItemEntity respuesta : cobranzaDetalleEntity.getCobranzaItem()) {

                                String val = "N";
                                if (respuesta.getCode() != null && respuesta.getErrorCode().equals("0")) {

                                    responseData.add("El Recibo fue aceptado en SAP");
                                    val = "Y";
                                } else {
                                    responseData.add("El Recibo no fue aceptado en SAP");


                                    val = "N";
                                }
                                Log.e("REOS","CobranzaRepository-sendCollection-respuesta-val"+val);
                                Log.e("REOS","CobranzaRepository-sendCollection-respuesta.getItemDetail()"+respuesta.getItemDetail());
                                Log.e("REOS","CobranzaRepository-sendCollection-respuesta.getCode()"+respuesta.getCode());
                                Log.e("REOS","CobranzaRepository-sendCollection-SesionEntity.compania_id"+SesionEntity.compania_id);
                                Log.e("REOS","CobranzaRepository-sendCollection-respuesta.getMessage()"+respuesta.getMessage());
                                Log.e("REOS","CobranzaRepository-sendCollection-respuesta.getReceip()"+respuesta.getReceip());
                                cobranzaDetalleSQLiteDao.updateStatusCodeSap(respuesta.getItemDetail(), respuesta.getCode(), SesionEntity.compania_id, respuesta.getMessage(), val,respuesta.getReceip());
                            }

                            callback.onResponseSap(responseData);
                        } else {
                            callback.onResponseErrorSap(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CobranzaDetalleEntity> call, Throwable t) {
                        callback.onResponseErrorSap(t.getMessage());
                        call.cancel();
                        Log.e("REOS","CobranzaRepository-sendCollection-onFailure: "+t.toString());
                    }
                });
            } else {
                callback.onResponseErrorSap("No hay recibos sin depositar pendientes de enviar");
            }
        }catch (Exception e)
        {
            Log.e("REOS","CobranzaRepository-sendCollection-error: "+e.toString());
        }
    }

    //////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    //////////////////Enviar Recibos  Depositados\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public MutableLiveData<String> depositedPendingCollection(Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();

        UpdateCollection(context, new CollectionCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay recibos depositados pendientes de enviar");
                }else{
                    temp.setValue(data.get(0));
                }
            }
            @Override
            public void onResponseErrorSap(String response) {
                temp.setValue(response);
            }
        });

        return temp;
    }

    private void UpdateCollection(final Context contexto,final CollectionCallback callback){
        String  json=null;
        Gson gson=new Gson();
        if(cobranzaDetalleSQLiteDao==null){
            cobranzaDetalleSQLiteDao =new CobranzaDetalleSQLiteDao(context);
        }
        ArrayList<CollectionEntity> listCollection=null;

        if(SesionEntity.perfil_id.equals("Chofer")||SesionEntity.perfil_id.equals("CHOFER"))
        {
            listCollection = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetallePendienteEnvioEstadoDepositadosJSONDrivers(SesionEntity.compania_id, SesionEntity.usuario_id);
        }else {
            listCollection = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetallePendienteEnvioEstadoDepositadosJSON(SesionEntity.compania_id, SesionEntity.usuario_id);
        }


        Log.e("REOS","CobranzaRepository-depositedPendingCollection-UpdateCollection-listCollection.size(): "+listCollection.size());
        if(listCollection!=null && listCollection.size()>0){
            json = gson.toJson(listCollection);
            json = "{ \"Collections\":" + json + "}";

            //EnviarPatchDepositoPendiente(listCollection);

        }
        Log.e("REOS","CobranzaRepository-depositedPendingCollection-UpdateCollection-json: "+json);

        try {
            if (json != null) {
                //params.put("Code",codeSap);
                //params.put("U_VIS_Deposit",codeOperation);
                //params.put("U_VIS_BankID",codeBank);
                //params.put("U_VIS_Receip",receip);


                //RequestBody json = RequestBody.create((new JSONObject(params)).toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));
                //Call<CobranzaDetalleEntity> call = api.updateCollection(codeSap,json);

                RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
                Log.e("REOS", "CobranzaRepository-depositedPendingCollection-UpdateCollection-jsonRequest: " + jsonRequest.toString());
                //RequestBody jsonRequest = RequestBody.create((new JSONObject(params)).toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));

                //Config.getClient().create(Api.class).updateCollection(jsonRequest).enqueue(new Callback<CobranzaDetalleEntity>() {
                        Config.getClient().create(Api.class).updateCollection(listCollection.get(0).getCode(),jsonRequest).enqueue(new Callback<CobranzaDetalleEntity>() {
                    @Override
                    public void onResponse(Call<CobranzaDetalleEntity> call, Response<CobranzaDetalleEntity> response) {
                        ArrayList<String> responseData = new ArrayList<>();
                        CobranzaDetalleEntity cobranzaDetalleEntity = response.body();
                        //Log.e("REOS", "CobranzaRepository-depositedPendingCollection-UpdateCollection-cobranzaDetalleEntity: " + cobranzaDetalleEntity.toString());
                        if (response.isSuccessful() && cobranzaDetalleEntity != null) {
                            responseData = new ArrayList<>();
                            for (CobranzaItemEntity respuesta : cobranzaDetalleEntity.getCobranzaItem()) {
                                if (respuesta.getCode() != null && respuesta.getErrorCode().equals("0")) {
                                    Log.e("REOS", "CobranzaRepository-depositedPendingCollection-UpdateCollection-respuesta.getDeposit(): " + respuesta.getDeposit());
                                    Log.e("REOS", "CobranzaRepository-depositedPendingCollection-UpdateCollection-respuesta.getRecibo(): " + respuesta.getRecibo());
                                    Log.e("REOS", "CobranzaRepository-depositedPendingCollection-UpdateCollection-SesionEntity.compania_id: " + SesionEntity.compania_id);
                                    Log.e("REOS", "CobranzaRepository-depositedPendingCollection-UpdateCollection-respuesta.getBankid(): " + respuesta.getBankid());
                                    cobranzaDetalleSQLiteDao.UpdateCollectionDeposit(
                                            respuesta.getDeposit(),
                                            respuesta.getRecibo(),
                                            SesionEntity.compania_id,
                                            respuesta.getBankid()
                                    );
                                }
                            }

                            responseData.add("Se Enviaron los Recibos depositados Pendientes");
                            callback.onResponseSap(responseData);
                        } else {
                            responseData.add("No se Enviaron los Recibos depositados Pendientes");
                            callback.onResponseErrorSap(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CobranzaDetalleEntity> call, Throwable t) {
                        callback.onResponseErrorSap(t.getMessage());
                        call.cancel();
                    }
                });
            } else {
                callback.onResponseErrorSap("No hay Recibos pendientes de enviar");
            }
        }catch (Exception e)
        {
            Log.e("REOS", "CobranzaRepository-depositedPendingCollection-UpdateCollection-error: " + e.toString());
        }
    }

    public MutableLiveData<String> Synchronizevoidedreceipts (Context context,String imei){
        MutableLiveData<String> temp=new MutableLiveData<String>();

        Synchronizereceipts(context,imei, new CollectionCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay recibos depositados pendientes de enviar");
                }else{
                    temp.setValue(data.get(0));
                }
            }
            @Override
            public void onResponseErrorSap(String response) {
                temp.setValue(response);
            }
        });

        return temp;
    }

    private void Synchronizereceipts(final Context context,String imei,final CollectionCallback callback){

        Config.getClient().create(Api.class).getHistoricoCobranzaPD(imei,"A").enqueue(new Callback<HistoricoCobranzaEntityResponse>() {
            @Override
            public void onResponse(Call<HistoricoCobranzaEntityResponse> call, Response<HistoricoCobranzaEntityResponse> response) {

                HistoricoCobranzaEntityResponse historicoCobranzaEntityResponse = response.body();
                ArrayList<String> responseData = new ArrayList<>();
                if (response.isSuccessful() && historicoCobranzaEntityResponse != null) {
                    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(context);
                    cobranzaDetalleSQLiteDao.SincronizarEstadoAnuladoCobranzaDetalle(historicoCobranzaEntityResponse.getHistoricoCobranza());

                    responseData.add("Se Sincronizo los recibos Anulados");
                    callback.onResponseSap(responseData);
                } else {
                    responseData.add("No existe recibos Anulados por Sincronizar");
                    callback.onResponseErrorSap(response.message());
                }
            }
            @Override
            public void onFailure(Call<HistoricoCobranzaEntityResponse> call, Throwable t) {
                callback.onResponseErrorSap(t.getMessage());
                call.cancel();
            }
        });

    }

    public MutableLiveData<String> PendingCollectionQR (Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();
        UpdateCollectionQR(context, new CollectionCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay recibos depositados pendientes de enviar");
                }else{
                    temp.setValue(data.get(0));
                }
            }
            @Override
            public void onResponseErrorSap(String response) {
                temp.setValue(response);
            }
        });
        return temp;
    }

    private void UpdateCollectionQR(final Context contexto,final CollectionCallback callback){
        String  json=null;
        Gson gson=new Gson();
        if(cobranzaDetalleSQLiteDao==null){
            cobranzaDetalleSQLiteDao =new CobranzaDetalleSQLiteDao(contexto);
        }
        ArrayList<CollectionEntity> listCollection=null;
        listCollection = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetallePendienteEnvioEstadoQRJSON(SesionEntity.compania_id, SesionEntity.usuario_id);
        Log.e("REOS","CobranzaRepository-depositedPendingCollection-UpdateCollection-listCollection.size(): "+listCollection.size());
        if(listCollection!=null && listCollection.size()>0){
            json = gson.toJson(listCollection);
            json = "{ \"Collections\":" + json + "}";
        }
        Log.e("REOS","CobranzaRepository-depositedPendingCollection-UpdateCollection-json: "+json);
        if(json!=null){
            RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
            Log.e("REOS", "CobranzaRepository-depositedPendingCollection-UpdateCollection-jsonRequest: " + jsonRequest.toString());
            Config.getClient().create(Api.class).updateCollection(listCollection.get(0).getCode(), jsonRequest).enqueue(new Callback<CobranzaDetalleEntity>() {
            //Config.getClient().create(Api.class).updateCollection(jsonRequest).enqueue(new Callback<CobranzaDetalleEntity>() {
                @Override
                public void onResponse(Call<CobranzaDetalleEntity> call, Response<CobranzaDetalleEntity> response) {

                    CobranzaDetalleEntity cobranzaDetalleEntity = response.body();
                    ArrayList<String> responseData=new ArrayList<>();
                    if (response.isSuccessful() && cobranzaDetalleEntity != null) {
                        responseData = new ArrayList<>();
                        for (CobranzaItemEntity respuesta : cobranzaDetalleEntity.getCobranzaItem()) {
                            Log.e("REOS","CobranzaRepository-UpdateCollectionQR-UpdateCollectionQR-respuesta.getRecibo(): "+respuesta.getRecibo());
                            Log.e("REOS","CobranzaRepository-UpdateCollectionQR-UpdateCollectionQR-SesionEntity.compania_id: "+SesionEntity.compania_id);
                            Log.e("REOS","CobranzaRepository-UpdateCollectionQR-UpdateCollectionQR-SesionEntity.usuario_id: "+SesionEntity.usuario_id);
                            if (respuesta.getCode() != null && respuesta.getErrorCode().equals("0")) {
                                cobranzaDetalleSQLiteDao.ActualizaWSQRValidadoCobranzaDetalle(
                                        respuesta.getRecibo(),
                                        SesionEntity.compania_id,
                                        SesionEntity.usuario_id,
                                        "Y"
                                );
                            }
                        }

                        responseData.add("Se Enviaron los Recibos con QR Pendientes");
                        callback.onResponseSap(responseData);
                    } else {
                        responseData.add("No se encontraron los Recibos con QR Pendientes");
                        callback.onResponseErrorSap(response.message());
                    }
                }
                @Override
                public void onFailure(Call<CobranzaDetalleEntity> call, Throwable t) {
                    callback.onResponseErrorSap(t.getMessage());
                    call.cancel();
                }
            });
        }else{
            callback.onResponseErrorSap("No hay Recibos pendientes de enviar");
        }
    }

    public MutableLiveData<String> SynchronizedepositedPendingCollection (Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();
        SynchronizedepositedPendingreceips(context, new CollectionCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay recibos pendientes de depositar");
                }else{
                    temp.setValue(data.get(0));
                }
            }
            @Override
            public void onResponseErrorSap(String response) {
                temp.setValue(response);
            }
        });
        return temp;
    }

    private void SynchronizedepositedPendingreceips(final Context contexto,final CollectionCallback callback){

        if(cobranzaDetalleSQLiteDao==null){
            cobranzaDetalleSQLiteDao =new CobranzaDetalleSQLiteDao(contexto);
        }
        String resultadocantidadcobranzadetalle;
        CobranzaDetalleSQLiteDao cantidadregistroscobranzadetalle = new CobranzaDetalleSQLiteDao(contexto);
        resultadocantidadcobranzadetalle = cantidadregistroscobranzadetalle.ObtenerCantidadCobranzaDetalle(SesionEntity.usuario_id, SesionEntity.compania_id);
        ArrayList<String> responseData = new ArrayList<>();
        if(resultadocantidadcobranzadetalle.equals("0")){
            if(BuildConfig.FLAVOR.equals("peru")) {
                Config.getClient().create(Api.class).getHistoricoCobranzaPDSupervisor(SesionEntity.imei, "PD", SesionEntity.usuario_id).enqueue(new Callback<HistoricoCobranzaEntityResponse>() {
                    @Override
                    public void onResponse(Call<HistoricoCobranzaEntityResponse> call, Response<HistoricoCobranzaEntityResponse> response) {

                        if (response.isSuccessful()) {
                            HistoricoCobranzaEntityResponse historicoCobranzaEntityResponse = response.body();
                            CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(contexto);
                            cobranzaDetalleSQLiteDao.addCollection(historicoCobranzaEntityResponse);

                            responseData.add("Se Sincronizaron los recibos Correctamente");
                            callback.onResponseSap(responseData);
                        } else {
                            responseData.add("No se encontraron los Recibos con QR Pendientes");
                            callback.onResponseErrorSap(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<HistoricoCobranzaEntityResponse> call, Throwable t) {
                        callback.onResponseErrorSap(t.getMessage());
                        call.cancel();
                    }
                });

            }else {
                Config.getClient().create(Api.class).getHistoricoCobranzaPD(SesionEntity.imei, "PD").enqueue(new Callback<HistoricoCobranzaEntityResponse>() {
                    @Override
                    public void onResponse(Call<HistoricoCobranzaEntityResponse> call, Response<HistoricoCobranzaEntityResponse> response) {

                        if (response.isSuccessful()) {
                            HistoricoCobranzaEntityResponse historicoCobranzaEntityResponse = response.body();
                            CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(contexto);
                            cobranzaDetalleSQLiteDao.addCollection(historicoCobranzaEntityResponse);

                            responseData.add("Se Sincronizaron los recibos Correctamente");
                            callback.onResponseSap(responseData);
                        } else {
                            responseData.add("No se encontraron los Recibos con QR Pendientes");
                            callback.onResponseErrorSap(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<HistoricoCobranzaEntityResponse> call, Throwable t) {
                        callback.onResponseErrorSap(t.getMessage());
                        call.cancel();
                    }
                });
            }
        }else{
            callback.onResponseErrorSap("Base de datos con data, no es necesario sincronizar");
        }
    }


    public MutableLiveData<String> SynchronizedepositedPendingCollectionForced (Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();
        SynchronizedepositedPendingreceipsForced(context, new CollectionCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay recibos pendientes de depositar");
                }else{
                    temp.setValue(data.get(0));
                }
            }
            @Override
            public void onResponseErrorSap(String response) {
                temp.setValue(response);
            }
        });
        return temp;
    }

    private void SynchronizedepositedPendingreceipsForced(final Context contexto,final CollectionCallback callback){
        ArrayList<String> responseData = new ArrayList<>();

            Config.getClient().create(Api.class).getHistoricoCobranzaPDSupervisor(SesionEntity.imei,"PD",SesionEntity.usuario_id).enqueue(new Callback<HistoricoCobranzaEntityResponse>() {
                @Override
                public void onResponse(Call<HistoricoCobranzaEntityResponse> call, Response<HistoricoCobranzaEntityResponse> response) {

                    if(response.isSuccessful()) {
                        HistoricoCobranzaEntityResponse historicoCobranzaEntityResponse=response.body();
                        CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(contexto);
                        cobranzaDetalleSQLiteDao.addCollection(historicoCobranzaEntityResponse);

                        responseData.add("Se Sincronizaron los recibos Correctamente");
                        callback.onResponseSap(responseData);
                    } else {
                        responseData.add("No se encontraron los Recibos con QR Pendientes");
                        callback.onResponseErrorSap(response.message());
                    }
                }


                @Override
                public void onFailure(Call<HistoricoCobranzaEntityResponse> call, Throwable t) {
                    callback.onResponseErrorSap(t.getMessage());
                    call.cancel();
                }
            });
    }
    //////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    //////////////////Enviar Recibos  Desvinculado de Deposito\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public MutableLiveData<String> ReceiptDetachedDeposit(Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();

        UpdateReceiptDetachedDeposit(context, new CollectionCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay recibos depositados pendientes de enviar");
                }else{
                    temp.setValue(data.get(0));
                }
            }
            @Override
            public void onResponseErrorSap(String response) {
                temp.setValue(response);
            }
        });

        return temp;
    }

    private void UpdateReceiptDetachedDeposit(final Context contexto,final CollectionCallback callback){
        String  json=null;
        Gson gson=new Gson();
        if(cobranzaDetalleSQLiteDao==null){
            cobranzaDetalleSQLiteDao =new CobranzaDetalleSQLiteDao(context);
        }
        ArrayList<CollectionEntity> listCollection=null;
        listCollection = cobranzaDetalleSQLiteDao.UpdateRecibosDesvinculadosDepositoJSON(SesionEntity.compania_id, SesionEntity.usuario_id);
        Log.e("REOS","CobranzaRepository-depositedPendingCollection-UpdateCollection-listCollection.size(): "+listCollection.size());
        if(listCollection!=null && listCollection.size()>0){
            json = gson.toJson(listCollection);
            json = "{ \"Collections\":" + json + "}";

            //EnviarPatchDepositoPendiente(listCollection);

        }
        Log.e("REOS","CobranzaRepository-depositedPendingCollection-UpdateCollection-json: "+json);

        try {
            if (json != null) {
                //params.put("Code",codeSap);
                //params.put("U_VIS_Deposit",codeOperation);
                //params.put("U_VIS_BankID",codeBank);
                //params.put("U_VIS_Receip",receip);


                //RequestBody json = RequestBody.create((new JSONObject(params)).toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));
                //Call<CobranzaDetalleEntity> call = api.updateCollection(codeSap,json);

                RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
                Log.e("REOS", "CobranzaRepository-depositedPendingCollection-UpdateCollection-jsonRequest: " + jsonRequest.toString());
                //RequestBody jsonRequest = RequestBody.create((new JSONObject(params)).toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));
                Config.getClient().create(Api.class).updateCollection(listCollection.get(0).getCode(), jsonRequest).enqueue(new Callback<CobranzaDetalleEntity>() {
                //Config.getClient().create(Api.class).updateCollection(jsonRequest).enqueue(new Callback<CobranzaDetalleEntity>() {
                    @Override
                    public void onResponse(Call<CobranzaDetalleEntity> call, Response<CobranzaDetalleEntity> response) {
                        ArrayList<String> responseData = new ArrayList<>();
                        CobranzaDetalleEntity cobranzaDetalleEntity = response.body();
                        if (response.isSuccessful() && cobranzaDetalleEntity != null) {
                            responseData = new ArrayList<>();
                            for (CobranzaItemEntity respuesta : cobranzaDetalleEntity.getCobranzaItem()) {
                                if (respuesta.getCode() != null && respuesta.getErrorCode().equals("0")) {
                                    Log.e("REOS", "CobranzaRepository-depositedPendingCollection-UpdateCollection-respuesta.getDeposit(): " + respuesta.getDeposit());
                                    Log.e("REOS", "CobranzaRepository-depositedPendingCollection-UpdateCollection-respuesta.getRecibo(): " + respuesta.getRecibo());
                                    Log.e("REOS", "CobranzaRepository-depositedPendingCollection-UpdateCollection-SesionEntity.compania_id: " + SesionEntity.compania_id);
                                    Log.e("REOS", "CobranzaRepository-depositedPendingCollection-UpdateCollection-respuesta.getBankid(): " + respuesta.getBankid());
                                    cobranzaDetalleSQLiteDao.UpdateCollectionDetachedDeposit(
                                            respuesta.getDeposit(),
                                            respuesta.getRecibo(),
                                            SesionEntity.compania_id,
                                            respuesta.getBankid()
                                    );
                                }
                            }

                            responseData.add("Se Enviaron las Firmas Digitales de Cobranzas");
                            callback.onResponseSap(responseData);
                        } else {
                            responseData.add("No Se Enviaron las Firmas Digitales de Cobranzas");
                            callback.onResponseErrorSap(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CobranzaDetalleEntity> call, Throwable t) {
                        callback.onResponseErrorSap(t.getMessage());
                        call.cancel();
                    }
                });
            } else {
                callback.onResponseErrorSap("No hay Firmas Digitales de Cobranzas Pendientes de Envio");
            }
        }catch (Exception e)
        {
            Log.e("REOS", "CobranzaRepository-depositedPendingCollection-UpdateCollection-error: " + e.toString());
        }
    }

    public MutableLiveData<String> PendingCollectionSignatureList (Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();
        UpdateCollectionSignatureList(context, new CollectionCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay recibos depositados pendientes de enviar");
                }else{
                    temp.setValue(data.get(0));
                }
            }
            @Override
            public void onResponseErrorSap(String response) {
                temp.setValue(response);
            }
        });
        return temp;
    }

    private void UpdateCollectionSignatureList(final Context contexto,final CollectionCallback callback){
        String  json=null;
        Gson gson=new Gson();
        UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(contexto);
        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();

        if(cobranzaDetalleSQLiteDao==null){
            cobranzaDetalleSQLiteDao =new CobranzaDetalleSQLiteDao(contexto);
        }
        ArrayList<CollectionEntity> listCollection=null;
        listCollection = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetallePendienteEnvioE_SignatureJSON(ObjUsuario.compania_id, ObjUsuario.usuario_id,contexto);
        Log.e("REOS","CobranzaRepository-UpdateCollectionSignatureList-UpdateCollection-listCollection.size(): "+listCollection.size());
        if(listCollection!=null && listCollection.size()>0){
            json = gson.toJson(listCollection);
            json = "{ \"signatures\":" + json + "}";
        }
        Log.e("REOS","CobranzaRepository-UpdateCollectionSignatureList-UpdateCollection-json: "+json);
        if(json!=null){
            RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
            Log.e("REOS", "CobranzaRepository-UpdateCollectionSignatureList-UpdateCollection-jsonRequest: " + jsonRequest.toString());
            Config.getClient().create(Api.class).updateCollectionSignature(SesionEntity.imei, jsonRequest).enqueue(new Callback<SignatureEntityResponse>() {
                @Override
                public void onResponse(Call<SignatureEntityResponse> call, Response<SignatureEntityResponse> response) {
                    UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
                    UsuarioSQLite usuarioSQLite=new UsuarioSQLite(contexto);
                    ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();

                    SignatureEntityResponse signatureEntityResponse = response.body();
                    ArrayList<String> responseData=new ArrayList<>();
                    if (response.isSuccessful() && signatureEntityResponse != null) {
                        responseData = new ArrayList<>();
                        for (SignatureREntity respuesta : signatureEntityResponse.getSignatureREntity()) {
                            Log.e("REOS","CobranzaRepository-PendingCollectionSignatureList-UpdateCollectionSignatureList-respuesta.getRecibo(): "+respuesta.getReceipt ());
                            Log.e("REOS","CobranzaRepository-PendingCollectionSignatureList-UpdateCollectionSignatureList-SesionEntity.compania_id: "+SesionEntity.compania_id);
                            Log.e("REOS","CobranzaRepository-PendingCollectionSignatureList-UpdateCollectionSignatureList-SesionEntity.usuario_id: "+SesionEntity.usuario_id);
                            if (respuesta.getCode() != null && respuesta.getErrorCode().equals("N")) {
                                cobranzaDetalleSQLiteDao.UpdateDBCollectionE_Signature(
                                        respuesta.getReceipt(),
                                        ObjUsuario.compania_id,
                                        ObjUsuario.usuario_id,
                                        "Y"
                                );
                            }
                        }

                        responseData.add("Se Enviaron los Recibos con QR Pendientes");
                        callback.onResponseSap(responseData);
                    } else {
                        responseData.add("No se encontraron los Recibos con QR Pendientes");
                        callback.onResponseErrorSap(response.message());
                    }
                }
                @Override
                public void onFailure(Call<SignatureEntityResponse> call, Throwable t) {
                    callback.onResponseErrorSap(t.getMessage());
                    call.cancel();
                }
            });
        }else{
            callback.onResponseErrorSap("No hay Recibos pendientes de enviar");
        }
    }
}
interface CollectionCallback {
    void onResponseSap(ArrayList<String> response);
    void onResponseErrorSap(String response);
}
