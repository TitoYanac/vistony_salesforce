package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLIte.UsuarioSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VersionEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.CobranzaCabeceraEntityResponse;

import retrofit2.Call;
import retrofit2.Response;

public class CobranzaCabeceraWS {
    private UsuarioSQLiteDao usuarioSQLiteDao;
    private VersionEntity vsDatada;
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
        Call<CobranzaCabeceraEntityResponse> call = api.PostInsertCobranzaC(
                Imei,
                TipoCrud,
                Compania_ID,
                Banco_ID,
                TipoIngreso,
                Deposito_ID,
                Usuario_ID,
                FechaDeposito,
                MontoDeposito,
                Estado,
                Comentario,
                FuerzaTrabajo_ID,
                Bancarizacion,
                FechaDiferida,
                MotivoAnulacion,
                DepositoDirecto,
                PagoPOS
        );
        /*call.enqueue(new Callback<CobranzaCabeceraEntityResponse>() {
            @Override
            public void onResponse(Call<CobranzaCabeceraEntityResponse> call, Response<CobranzaCabeceraEntityResponse> response) {

                if(response.isSuccessful()) {
                    CobranzaCabeceraEntityResponse cobranzaCabeceraEntityResponse=response.body();
                    for(int i=0;i<cobranzaCabeceraEntityResponse.getCobranzaCabeceraEntity().size();i++){
                        resultado = Integer.parseInt(cobranzaCabeceraEntityResponse.getCobranzaCabeceraEntity().get(i).getResultado()) ;
                    }
                }
            }
            @Override
            public void onFailure(Call<CobranzaCabeceraEntityResponse> call, Throwable t) {

            }
        });*/
        try
        {
            Response<CobranzaCabeceraEntityResponse> response= call.execute();
            Log.e("REOS","Response-CobranzaCabeceraWS: "+response.toString());
            if(response.isSuccessful()) {
                CobranzaCabeceraEntityResponse cobranzaCabeceraEntityResponse=response.body();
                //Log.e("REOS","response.body()-CobranzaCabeceraWS: "+response.body().toString());

                //for(int i=0;i<cobranzaCabeceraEntityResponse.getCobranzaCabeceraEntity().size();i++){
                resultado = cobranzaCabeceraEntityResponse.getCobranzaCabeceraEntity().toString();
                //}
                Log.e("REOS","cobranzaCabeceraEntityResponse.getCobranzaCabeceraEntity()-CobranzaCabeceraWS: "+cobranzaCabeceraEntityResponse.getCobranzaCabeceraEntity().toString());
            }


        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS ->","CobranzaCabeceraWSError: "+e);
            resultado="0";
        }
        Log.e("REOS ->","CobranzaCabeceraWSResultado: "+String.valueOf(resultado) );
        return resultado;
    }
}
