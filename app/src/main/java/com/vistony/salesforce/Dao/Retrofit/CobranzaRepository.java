package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.Utilitario;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaClienteDetalleEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CobranzaDetalleEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CobranzaItemEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.View.CobranzaDetalleView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class CobranzaRepository {
    private Context context;
    private String resultado="0";

    public CobranzaRepository(final Context context){
        this.context=context;
    }

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
            String PagoPOS
    ){
        Api api = Config.getClient().create(Api.class);

        HashMap<String, String> params = new HashMap<>();

        params.put("BankID","");
        params.put("Deposit","");
        params.put("IncomeDate", FechaCobranza);
        params.put("Banking", (Bancarizacion.equals("0"))?"N":"Y");
        params.put("ItemDetail",Detalle_Item);
        params.put("CardCode", Cliente_ID);
        params.put("DocEntryFT", Documento_entry);
        params.put("DocNum",Documento_ID);
        params.put("DocTotal", ImporteDocumento);
        params.put("Balance", SaldoDocumento);
        params.put("AmountCharged",MontoCobrado);
        params.put("NewBalance",NuevoSaldoDocumento);
        params.put("Receip", Recibo);
        params.put("Status",Estado);
        params.put("Commentary", Comentario);
        params.put("QRStatus", (EstadoQR.equals("0"))?"N":"Y");
        params.put("UserID", Usuario_ID);
        params.put("SlpCode", Fuerzatrabajo_ID);
        params.put("POSPay",(PagoPOS.equals("0"))?"N":"Y");
        params.put("DirectDeposit",(DepositoDirecto.equals("0"))?"N":"Y");
        params.put("CancelReason", MotivoAnulacion);

        RequestBody json = RequestBody.create("{ \"Collections\":["+(new JSONObject(params)).toString()+"]}",okhttp3.MediaType.parse("application/json; charset=utf-8"));

        Call<CobranzaDetalleEntity> call = api.sendCollection("http://169.47.196.209/cl/api/Collections",json);
        try{
            Response response= call.execute();
            //sap_code
            if(response.isSuccessful()) {
                CobranzaDetalleEntity cobranzas= (CobranzaDetalleEntity) response.body();

                CobranzaDetalleSQLiteDao cobranzasqlite=new CobranzaDetalleSQLiteDao(context);

                for(CobranzaItemEntity cobranza:cobranzas.getCobranzaItem()){
                    if(cobranza.getErrorCode().equals("0") && cobranza.getCode()!=null){
                        cobranzasqlite.updateStatusCodeSap(Detalle_Item,cobranza.getCode(),Compania_ID);
                    }
                }

                resultado = "1";
            }else{
                resultado = "0";
            }
        }catch (Exception e){
            e.printStackTrace();
            resultado="0";
        }
        return resultado;
    }

    public static String sendPatch(String codeSap,String codeOperation,String codeBank){
        String resultado="0";
        Api api = Config.getClient().create(Api.class);

        HashMap<String, String> params = new HashMap<>();

        params.put("U_VIS_Deposit",codeOperation);
        params.put("U_VIS_BankID",codeBank);


        RequestBody json = RequestBody.create((new JSONObject(params)).toString(),okhttp3.MediaType.parse("application/json; charset=utf-8"));

        Call<CobranzaDetalleEntity> call = api.updateCollection("http://169.47.196.209/cl/api/Collections/"+codeSap,json);
        try{
            Response response= call.execute();
            //sap_code
            if(response.isSuccessful()) {
                resultado = "1";
            }else{
                resultado = "0";
            }
        }catch (Exception e){
            e.printStackTrace();
            resultado="0";
        }

        return resultado;
    }

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
                if(Anulacion.equals("1")&&TipoCRUD.equals("UPDATE"))
                {
                    banco_id="0";
                    deposito_id="1";
                    comentario=listaCobranzaDetalleSQLiteEntity.get(i).getComentario();
                }else if (Actualizacion.equals("1")&&TipoCRUD.equals("UPDATE"))
                {
                    banco_id=Banco;
                    comentario=listaCobranzaDetalleSQLiteEntity.get(i).getComentario();
                    deposito_id=listaCobranzaDetalleSQLiteEntity.get(i).getCobranza_id();
                    Log.e("REOS","UPDATE+1"+banco_id+"-"+comentario+"-"+deposito_id);
                }
                else if(TipoCRUD.equals("CREATE"))
                {
                    comentario=listaCobranzaDetalleSQLiteEntity.get(i).getComentario();
                    banco_id=listaCobranzaDetalleSQLiteEntity.get(i).getBanco_id();
                    deposito_id=listaCobranzaDetalleSQLiteEntity.get(i).getCobranza_id();
                }


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
                        listaCobranzaDetalleSQLiteEntity.get(i).getPagopos()
                );

            }

        }catch (Exception e){
            e.printStackTrace();
            resultado="0";
        }

        return resultado;
    }

}
