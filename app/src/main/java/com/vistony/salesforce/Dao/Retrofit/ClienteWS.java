package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SeguridadEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ClienteEntityResponse;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class ClienteWS {
    private ArrayList<ClienteSQLiteEntity> LCliente = new ArrayList<>();
    private ArrayList<SeguridadEntity> LSeguridad = new ArrayList<>();
    private Context context;

    public ClienteWS (final Context context){
        this.context=context;
    }

    public
    //Object[]
    ArrayList<ClienteSQLiteEntity>
    getClienteWS(String Imei){
        Api api = Config.getClient().create(Api.class);
        Object[] objects=new Object[2];
        Call<ClienteEntityResponse> call = api.getCliente(Imei);
        try
        {
            Response<ClienteEntityResponse> response= call.execute();
                if(response.isSuccessful()) {
                    ClienteEntityResponse clienteEntityResponse=response.body();

                    for(int i=0;i<clienteEntityResponse.getClienteEntity().size();i++){

                        ClienteSQLiteEntity ObjCliente = new ClienteSQLiteEntity();
                        ObjCliente.setCliente_id ( clienteEntityResponse.getClienteEntity().get(i).getClienteId());
                        ObjCliente.setDomembarque_id (clienteEntityResponse.getClienteEntity().get(i).getDomicilioEmbarque());
                        ObjCliente.setCompania_id ( SesionEntity.compania_id);
                        ObjCliente.setNombrecliente ( clienteEntityResponse.getClienteEntity().get(i).getNombre());
                        ObjCliente.setDireccion ( clienteEntityResponse.getClienteEntity().get(i).getDireccion());
                        ObjCliente.setOrden(clienteEntityResponse.getClienteEntity().get(i).getOrdenVisita());
                        ObjCliente.setZona_id(clienteEntityResponse.getClienteEntity().get(i).getZonaId());
                        ObjCliente.setZona(clienteEntityResponse.getClienteEntity().get(i).getZona());
                        ObjCliente.setRucdni(clienteEntityResponse.getClienteEntity().get(i).getDocumento());
                        ObjCliente.setMoneda(clienteEntityResponse.getClienteEntity().get(i).getMoneda());
                        ObjCliente.setTelefonofijo(clienteEntityResponse.getClienteEntity().get(i).getTelefoFijo());
                        ObjCliente.setTelefonomovil(clienteEntityResponse.getClienteEntity().get(i).getTelefonoMovil());
                        ObjCliente.setCorreo(clienteEntityResponse.getClienteEntity().get(i).getCorreo());
                        ObjCliente.setUbigeo_id(clienteEntityResponse.getClienteEntity().get(i).getUbigeoId());

                        /*
                        ObjCliente.impuesto_id=clienteEntityResponse.getClienteEntity().get(i).getImpuesto_id();
                        ObjCliente.impuesto=clienteEntityResponse.getClienteEntity().get(i).getImpuesto();
                        ObjCliente.tipocambio=clienteEntityResponse.getClienteEntity().get(i).getTipocambio();
                        */

                        ObjCliente.setCategoria(clienteEntityResponse.getClienteEntity().get(i).getCategoria());
                        ObjCliente.setLinea_credito(clienteEntityResponse.getClienteEntity().get(i).getLinea_credito());
                        ObjCliente.setLinea_credito_usado(clienteEntityResponse.getClienteEntity().get(i).getlinea_credito_usado());
                        ObjCliente.setTerminopago_id(clienteEntityResponse.getClienteEntity().get(i).getTerminoPago_id());
                        LCliente.add(ObjCliente);
                    }
                    /*for(int i=0;i<clienteEntityResponse.getSeguridadEntity().size();i++){

                        SeguridadEntity objSeguridad = new SeguridadEntity();
                        objSeguridad.hash=clienteEntityResponse.getSeguridadEntity().get(i).getHash();
                        LSeguridad.add(objSeguridad);
                    }*/
                }
            //objects[0]=LCliente;
            //objects[1]=LSeguridad;

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS error",""+e);
        }
        Log.e("REOS ListaClienteFinal",""+LCliente.size());
        return LCliente

                //objects
                ;
    }
}
