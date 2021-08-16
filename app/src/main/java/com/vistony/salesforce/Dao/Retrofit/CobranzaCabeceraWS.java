package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLIte.UsuarioSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VersionEntity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class CobranzaCabeceraWS {
    private Context context;
    static String resultado="0";

    public CobranzaCabeceraWS(final Context context){
        this.context=context;
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

        params.put("Imei", Imei);
        params.put("CrudType", TipoCrud);
        params.put("CompanyCode", Compania_ID);
        params.put("BankID", Banco_ID);
        params.put("IncomeType", TipoIngreso);
        params.put("DepositID", Deposito_ID);
        params.put("DepositDate", FechaDeposito);
        params.put("DeferredDate", FechaDiferida);
        params.put("Banking", Bancarizacion);
        params.put("UserCode", Usuario_ID);
        params.put("SlpCode",FuerzaTrabajo_ID);
        params.put("DepositAmount", MontoDeposito);
        params.put("Status", Estado);
        params.put("Commentary", Comentario);
        params.put("CancellationReason", MotivoAnulacion);
        params.put("DirectDeposit", DepositoDirecto);
        params.put("POSPay", PagoPOS);

        Call call = api.sendDeposit("https://graph.vistony.pe/deposit",params);

        try{
            Response response= call.execute();

            if(response.isSuccessful()) {
                //response.body().toString();
                resultado ="1";
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return resultado;
    }
}
