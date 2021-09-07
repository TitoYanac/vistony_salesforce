package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class DepositoRepository {
    private Context context;
    static String resultado="0";

    public DepositoRepository(final Context context){
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

        //params.put("Imei", Imei);
        //params.put("CrudType", TipoCrud);
        params.put("CompanyCode", Compania_ID);
        params.put("BankID", Banco_ID);
        params.put("IncomeType", (TipoIngreso.equals("Deposito"))?"DE":"CH");
        params.put("Deposit", Deposito_ID);//numero de operacion
        params.put("Date", FechaDeposito);
        params.put("DeferredDate", FechaDiferida);
        params.put("Banking",(Bancarizacion.equals("0"))?"N":"Y" );
        params.put("UserID", Usuario_ID);
        params.put("SlpCode",FuerzaTrabajo_ID);
        params.put("AmountDeposit", MontoDeposito);
        params.put("Status", (Estado.equals("Pendiente"))?"P":"P");
        params.put("Comments", Comentario);
        params.put("CancelReason", MotivoAnulacion);
        params.put("DirectDeposit", (DepositoDirecto.equals("0"))?"N":"Y");
        params.put("POSPay", (PagoPOS.equals("0"))?"N":"Y");

        RequestBody json = RequestBody.create("{ \"Deposits\":["+(new JSONObject(params)).toString()+"]}",okhttp3.MediaType.parse("application/json; charset=utf-8"));


        Call call = api.sendDeposit(json);

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
