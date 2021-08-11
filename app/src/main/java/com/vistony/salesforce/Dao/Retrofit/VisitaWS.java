package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Utilitario;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class VisitaWS {
    private Context context;
    private static String resultado="0";

    public VisitaWS(final Context context){
        this.context=context;
    }

    public String PostVisitaWS(
            String Imei,
            String Cliente_ID,
            String Direccion_ID,
            String Fecha_Registro,
            String Hora_Registro,
            String Zona_ID,
            String Tipo,
            String Motivo,
            String Observacion,
            String Latitud,
            String Longitud
    ){

        Api api = Config.getClient().create(Api.class);

        HashMap<String, String> params = new HashMap<>();

        params.put("Imei", Imei);
        params.put("CardCode", Cliente_ID);
        params.put("Address", Direccion_ID);
        params.put("Date", Fecha_Registro);
        params.put("Hour", Hora_Registro);
        params.put("Territory", Zona_ID);
        params.put("Type", Tipo);
        params.put("Motive", Motivo);
        params.put("Observation",Observacion);
        params.put("Latitude", Latitud);
        params.put("Longitude", Longitud);

        if(Utilitario.validationNotNull(params)){
            //https://graph.vistony.pe/RegistroVisita
            Call call = api.getVisita("http://169.47.196.209/v1.0/api/RegistroVisita",params);
            try{
                Response response= call.execute();
                if(response.isSuccessful()) {
                    resultado = "1";
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            Log.e("ERROR DE ENVIO","LA VISITA DE "+Cliente_ID+" DE TIPO "+Tipo+" EL DIA "+Fecha_Registro+" EN LA DIRECCIÓN "+Direccion_ID);
        }

        return resultado;
    }
}
