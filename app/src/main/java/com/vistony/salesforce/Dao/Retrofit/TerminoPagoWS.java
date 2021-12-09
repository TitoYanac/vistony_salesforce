package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.TerminoPagoEntityResponse;
import com.vistony.salesforce.Entity.SQLite.TerminoPagoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class TerminoPagoWS {

    private ArrayList<TerminoPagoSQLiteEntity> LTPago =  new ArrayList<>();
    private Context context;

    public TerminoPagoWS (final Context context){
        this.context=context;
    }

    public ArrayList<TerminoPagoSQLiteEntity> getTerminoPagoWS(String Imei){
        Api api = Config.getClient().create(Api.class);

        Call<TerminoPagoEntityResponse> call = api.getTerminoPago(Imei);
        try
        {
            Response<TerminoPagoEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                TerminoPagoEntityResponse terminoPagoEntityResponse=response.body();
                for(int i=0;i<terminoPagoEntityResponse.getTerminoPagoEntity().size();i++){

                    TerminoPagoSQLiteEntity ObjTerminoPago = new TerminoPagoSQLiteEntity();
                    ObjTerminoPago.terminopago_id = terminoPagoEntityResponse.getTerminoPagoEntity().get(i).getTerminopago_id();
                    ObjTerminoPago.terminopago = terminoPagoEntityResponse.getTerminoPagoEntity().get(i).getTerminopago();
                    ObjTerminoPago.compania_id = SesionEntity.compania_id;

                    if(terminoPagoEntityResponse.getTerminoPagoEntity().get(i).getContado().equals("True"))
                    {
                        ObjTerminoPago.contado="1";
                    }else
                    {
                        ObjTerminoPago.contado="0";
                    }

                    //ObjTerminoPago.contado = terminoPagoEntityResponse.getTerminoPagoEntity().get(i).getContado();
                    ObjTerminoPago.dias_vencimiento = terminoPagoEntityResponse.getTerminoPagoEntity().get(i).getDias_vencimiento();
                    LTPago.add(ObjTerminoPago);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return LTPago;
    }
}
