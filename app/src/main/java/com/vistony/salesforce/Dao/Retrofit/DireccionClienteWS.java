package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Adapters.ListaDireccionClienteEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.DireccionClienteEntityResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class DireccionClienteWS {
    private ArrayList<ListaDireccionClienteEntity> LDCliente = new ArrayList<>();
    private Context context;

    public DireccionClienteWS (final Context context){
        this.context=context;
    }

    public ArrayList<ListaDireccionClienteEntity> getDClienteWS(String Imei,String Cliente_id
    ){
        Api api = Config.getClient().create(Api.class);

        Call<DireccionClienteEntityResponse> call = api.getDireccionCliente("https://graph.vistony.pe/Direccion?imei="+Imei+"&cliente="+Cliente_id);
        try
        {
            Response<DireccionClienteEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                DireccionClienteEntityResponse direccionClienteEntityResponse=response.body();

                for(int i=0;i<direccionClienteEntityResponse.getDireccionClienteEntity().size();i++){

                    ListaDireccionClienteEntity ObjDCliente = new ListaDireccionClienteEntity();
                    //ObjDCliente.compania_id = SesionEntity.compania_id;
                    ObjDCliente.cliente_id = direccionClienteEntityResponse.getDireccionClienteEntity().get(i).getClienteId();
                    ObjDCliente.domembarque_id = direccionClienteEntityResponse.getDireccionClienteEntity().get(i).getDomicilioEmbarque();
                    ObjDCliente.direccion = direccionClienteEntityResponse.getDireccionClienteEntity().get(i).getDireccion();
                    ObjDCliente.zona_id = direccionClienteEntityResponse.getDireccionClienteEntity().get(i).getZonaid();
                    ObjDCliente.zona=direccionClienteEntityResponse.getDireccionClienteEntity().get(i).getZona();
                    ObjDCliente.nombrefuerzatrabajo=direccionClienteEntityResponse.getDireccionClienteEntity().get(i).getNombrefuerzatrabajo();
                    LDCliente.add(ObjDCliente);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS","DireccionClienteWS-Error:: "+e.toString());
        }
        Log.e("REOS","DireccionClienteWS-LDCliente:: "+LDCliente.size());
        return LDCliente;
    }
}
