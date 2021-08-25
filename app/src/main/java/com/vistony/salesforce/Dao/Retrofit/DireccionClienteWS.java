package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.DireccionSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaDireccionClienteEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.AddressEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.DireccionClienteEntityResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DireccionClienteWS {
    private ArrayList<ListaDireccionClienteEntity> LDCliente = new ArrayList<>();
    private Context context;
    private DireccionSQLite direccionSQLite;

    public DireccionClienteWS (){
    }
    public DireccionClienteWS (final Context context){
        this.context=context;
    }

    public ArrayList<ListaDireccionClienteEntity> getDClienteWS(String Imei,String Cliente_id){
        Api api = Config.getClient().create(Api.class);

        Call<DireccionClienteEntityResponse> call = api.getDireccionCliente("https://graph.vistony.pe/Direccion?imei="+Imei+"&cliente="+Cliente_id);
        try
        {
            Response<DireccionClienteEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                DireccionClienteEntityResponse direccionClienteEntityResponse=response.body();

                for(int i = 0; i<direccionClienteEntityResponse.getAddressEntity().size(); i++){

                    ListaDireccionClienteEntity ObjDCliente = new ListaDireccionClienteEntity();

                    //ObjDCliente.compania_id = SesionEntity.compania_id;

                    // ACABO DE COMENTARLO ObjDCliente.cliente_id = direccionClienteEntityResponse.getAddressEntity().get(i).getClienteId();
                    ObjDCliente.domembarque_id = direccionClienteEntityResponse.getAddressEntity().get(i).getDomicilioEmbarque();
                    ObjDCliente.direccion = direccionClienteEntityResponse.getAddressEntity().get(i).getDireccion();
                    ObjDCliente.zona_id = direccionClienteEntityResponse.getAddressEntity().get(i).getZonaid();
                    ObjDCliente.zona=direccionClienteEntityResponse.getAddressEntity().get(i).getZona();
                    ObjDCliente.nombrefuerzatrabajo=direccionClienteEntityResponse.getAddressEntity().get(i).getNombrefuerzatrabajo();
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

    public void addAddress(List<AddressEntity> Lista,String companiCode,String customerCode) {
        Log.e("addAddress","Ingreso adress "+companiCode);
        direccionSQLite = new DireccionSQLite(context);
        for (int i = 0; i < Lista.size(); i++) {
            direccionSQLite.InsertaDireccionCliente(
                    companiCode,
                    customerCode,
                Lista.get(i).getDomicilioEmbarque(),
                Lista.get(i).getDireccion(),
                Lista.get(i).getZonaid(),
                Lista.get(i).getZona(),
                Lista.get(i).getFuerzatrabajoid(),
                Lista.get(i).getNombrefuerzatrabajo()
            );
        }
    }

    public Integer countAddress(){
        return direccionSQLite.ObtenerCantidadDireccionCliente();
    }
}
