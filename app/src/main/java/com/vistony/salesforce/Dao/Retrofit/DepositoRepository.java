package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoDepositoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CobranzaDetalleEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.DepositEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.DepositList;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoDepositoEntityResponse;
import com.vistony.salesforce.Entity.SesionEntity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepositoRepository extends ViewModel {
    private Context context;
    private static String resultado="N";
    private CobranzaCabeceraSQLiteDao cobranzaCabeceraSQLiteDao;
    private ArrayList<ListaHistoricoDepositoEntity> LHDeposito =  new ArrayList<>();

    public DepositoRepository(final Context context){
        this.context=context;
    }

    public DepositoRepository(){
    }

    public String PostCobranzaCabeceraWS(
            String Imei,
            String TipoCrud,
            String Compania_ID,
            String Banco_ID,
            String TipoIngreso,
            String Deposito_ID,
            String Usuario_ID,
            String FechaDeposito,
            String MontoDeposito,
            String Estado,
            String Comentario,
            String FuerzaTrabajo_ID,
            String Bancarizacion,
            String FechaDiferida,
            String MotivoAnulacion,
            String DepositoDirecto,
            String PagoPOS
    ){

        Api api = Config.getClient().create(Api.class);

        HashMap<String, String> params = new HashMap<>();

        //params.put("Imei", Imei);
        //params.put("CrudType", TipoCrud);
        params.put("CompanyCode", Compania_ID); //ok
        params.put("BankID", Banco_ID); //ok
        params.put("IncomeType", (TipoIngreso.equals("Deposito"))?"DE":"CH"); //ok
        params.put("Deposit", Deposito_ID);//ok
        params.put("Date", FechaDeposito);//ok
        params.put("DeferredDate", FechaDiferida); //ok
        params.put("Banking",(Bancarizacion)); //ok
        params.put("UserID", Usuario_ID); //ok
        params.put("SlpCode",FuerzaTrabajo_ID); //ok
        params.put("AmountDeposit", MontoDeposito); //ok
        params.put("Status", (Estado.equals("Pendiente"))?"P":"P"); //ok
        params.put("Comments", Comentario); //No
        params.put("CancelReason", MotivoAnulacion); //ok
        params.put("DirectDeposit", (DepositoDirecto)); //ok
        params.put("POSPay", (PagoPOS)); //ok

        RequestBody json = RequestBody.create("{ \"Deposits\":["+(new JSONObject(params)).toString()+"]}",okhttp3.MediaType.parse("application/json; charset=utf-8"));
        Log.e("DepositoRepository-PostCobranzaCabeceraWS-json: ",json.toString());

        Call call = api.sendDeposit(json);
        Log.e("DepositoRepository-PostCobranzaCabeceraWS-call: ",call.toString());
        try{
            Response response= call.execute();
            Log.e("DepositoRepository-PostCobranzaCabeceraWS-response: ",response.toString());
            if(response.isSuccessful()) {
                //response.body().toString();
                resultado ="Y";

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return resultado;
    }


    public MutableLiveData<String> depositResend(Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();

        enviarDepositos(context, new DepositCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay Depositos pendientes de enviar");
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


    private void enviarDepositos(Context context,final DepositCallback callback){

        String  json=null;
        Gson gson=new Gson();

        if(cobranzaCabeceraSQLiteDao==null){
            cobranzaCabeceraSQLiteDao =new CobranzaCabeceraSQLiteDao(context);
        }

        ArrayList<DepositEntity> depositos = cobranzaCabeceraSQLiteDao.ObtenerCobranzaCabeceraPendientesWS(SesionEntity.compania_id, SesionEntity.usuario_id);
        Log.e("REOS","DepositoRepository-enviarDepositos-depositos.size(): "+depositos.size());

        if(depositos!=null && depositos.size()>0){
            json = gson.toJson(depositos);
            json = "{ \"Deposits\":" + json + "}";
        }
        Log.e("REOS","DepositoRepository-enviarDepositos-json: "+json);
        if(json!=null){
            RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
            Config.getClient().create(Api.class).sendDeposit(jsonRequest).enqueue(new Callback<DepositList>() {
                @Override
                public void onResponse(Call<DepositList> call, Response<DepositList> response) {

                    DepositList depositos=response.body();

                    if(response.isSuccessful() && depositos!=null){
                        ArrayList<String> responseData=new ArrayList<>();

                        for (DepositEntity respuesta:depositos.getDepositos()) {
                            if(respuesta.getErrorCode()!=null && respuesta.getErrorCode().equals("0")){//se envio
                                responseData.add("El deposito fue aceptado en SAP");
                                Log.e("REOS","DepositoRepository-enviarDepositos-respuesta.getDeposit(): "+respuesta.getDeposit());
                                Log.e("REOS","DepositoRepository-enviarDepositos-respuesta.getCompanyCode(): "+respuesta.getCompanyCode());
                                Log.e("REOS","DepositoRepository-enviarDepositos-respuesta.getSlpCode(): "+respuesta.getSlpCode());
                                Log.e("REOS","DepositoRepository-enviarDepositos-respuesta.getCode(): "+respuesta.getCode());
                                Log.e("REOS","DepositoRepository-enviarDepositos-respuesta.getMessage(): "+respuesta.getMessage());
                                cobranzaCabeceraSQLiteDao.ActualizarCobranzaCabeceraWS(respuesta.getDeposit(),SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id,"Y",respuesta.getCode(), respuesta.getMessage());

                            }else{//tiene error
                                responseData.add("El Deposito no fue aceptado en SAP");
                            }
                        }

                        callback.onResponseSap(responseData);
                    }else{
                        callback.onResponseErrorSap(response.message());
                    }
                }
                @Override
                public void onFailure(Call<DepositList> call, Throwable t) {
                    callback.onResponseErrorSap(t.getMessage());
                    call.cancel();
                }
            });
        }else{
            callback.onResponseErrorSap("No hay Depositos pendientes de enviar");
        }
    }

    public ArrayList<ListaHistoricoDepositoEntity> getHistoricoDeposito(
            String Imei,
            String Compania_id,
            String FechaDepositoIni,
            String FechaDepositoFin,
            String Fuerzatrabajo_ID
    ){
        Api api = Config.getClient().create(Api.class);

        Call<HistoricoDepositoEntityResponse> call = api.getHistoricoDeposito(
                Imei,
                FechaDepositoIni,
                FechaDepositoFin
        );
        LHDeposito =  new ArrayList<>();
        try
        {
            Response<HistoricoDepositoEntityResponse> response= call.execute();
            Log.e("REOS","DepositoRepository-getHistoricoDeposito-call: "+call.toString());
            if(response.isSuccessful()) {
                HistoricoDepositoEntityResponse historicoDepositoEntityResponse=response.body();
                Log.e("REOS","DepositoRepository-getHistoricoDeposito-response: "+response.toString());
                for(int i=0;i<historicoDepositoEntityResponse.getHistoricoDeposito().size();i++){
                    ListaHistoricoDepositoEntity ObjLHDeposito = new ListaHistoricoDepositoEntity();
                    ObjLHDeposito.bancarizacion = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getBancarizacion();
                    ObjLHDeposito.banco_id = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getBanco_id();
                    ObjLHDeposito.comentario   = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getComentario();
                    ObjLHDeposito.compania_id=SesionEntity.compania_id;
                    ObjLHDeposito.depositodirecto = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getDepositodirecto();
                    ObjLHDeposito.deposito_id = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getDeposito_id();
                    ObjLHDeposito.estado = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getEstado();
                    ObjLHDeposito.fechadeposito = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getFechadeposito();
                    ObjLHDeposito.fechadiferida = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getFechadiferida();
                    ObjLHDeposito.fuerzatrabajo_id= SesionEntity.fuerzatrabajo_id;
                    ObjLHDeposito.montodeposito = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getMontodeposito();
                    ObjLHDeposito.motivoanulacion =  historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getMotivoanulacion();
                    ObjLHDeposito.tipoingreso =  historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getTipoingreso();
                    ObjLHDeposito.usuario_id =  SesionEntity.usuario_id;
                    ObjLHDeposito.bankname =  historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getBankname();
                    ObjLHDeposito.code =  historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getCode();
                    LHDeposito.add(ObjLHDeposito);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS","DepositoRepository-getHistoricoDeposito-e: "+e.toString());
        }
        Log.e("REOS","DepositoRepository-getHistoricoDeposito-LHDeposito: "+LHDeposito.size());
        return LHDeposito;
    }


    public MutableLiveData<String> UpdatedepositStatus(Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();

        UpdatedepositStatusJSON(context, new DepositCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay Depositos Anulados pendientes de enviar");
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


    private void UpdatedepositStatusJSON(Context context,final DepositCallback callback){

        String  json=null;
        Gson gson=new Gson();

        if(cobranzaCabeceraSQLiteDao==null){
            cobranzaCabeceraSQLiteDao =new CobranzaCabeceraSQLiteDao(context);
        }

        ArrayList<DepositEntity> depositos = cobranzaCabeceraSQLiteDao.UpdateDepositCanceled(SesionEntity.compania_id, SesionEntity.usuario_id);
        Log.e("REOS","DepositoRepository-UpdatedepositStatusJSON-depositos.size(): "+depositos.size());

        if(depositos!=null && depositos.size()>0){
            json = gson.toJson(depositos);
            json = "{ \"Deposits\":" + json + "}";
        }
        Log.e("REOS","DepositoRepository-UpdatedepositStatusJSON-json: "+json);
        if(json!=null){
            RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
            Log.e("REOS", "DepositoRepository-UpdatedepositStatusJSON-UpdateCollection-jsonRequest: " + jsonRequest.toString());
            //RequestBody jsonRequest = RequestBody.create((new JSONObject(params)).toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));
            Config.getClient().create(Api.class).updateDeposit(depositos.get(0).getCode(), jsonRequest).enqueue(new Callback<DepositList>() {
                @Override
                public void onResponse(Call<DepositList> call, Response<DepositList> response) {
                    Log.e("REOS","DepositoRepository-UpdatedepositStatusJSON-response: "+response.toString());
                    DepositList depositos=response.body();

                    if(response.isSuccessful() && depositos!=null){
                        ArrayList<String> responseData=new ArrayList<>();

                        for (DepositEntity respuesta:depositos.getDepositos()) {
                            if(respuesta.getErrorCode()!=null && respuesta.getErrorCode().equals("0")){//se envio
                                responseData.add("El deposito fue aceptado en SAP");
                                Log.e("REOS","DepositoRepository-enviarDepositos-respuesta.getDeposit(): "+respuesta.getDeposit());
                                Log.e("REOS","DepositoRepository-enviarDepositos-respuesta.getCompanyCode(): "+respuesta.getCompanyCode());
                                Log.e("REOS","DepositoRepository-enviarDepositos-respuesta.getSlpCode(): "+respuesta.getSlpCode());
                                cobranzaCabeceraSQLiteDao.UpdateAnularCobranzaCabecera(respuesta.getDeposit(),SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id);

                            }else{//tiene error
                                responseData.add("El Deposito no fue aceptado en SAP");
                            }
                        }

                        callback.onResponseSap(responseData);
                    }else{
                        callback.onResponseErrorSap(response.message());
                    }
                }
                @Override
                public void onFailure(Call<DepositList> call, Throwable t) {
                    callback.onResponseErrorSap(t.getMessage());
                    call.cancel();
                }
            });
        }else{
            callback.onResponseErrorSap("No hay Depositos pendientes de enviar");
        }
    }
}

interface DepositCallback {
    void onResponseSap(ArrayList<String> response);
    void onResponseErrorSap(String response);
}
