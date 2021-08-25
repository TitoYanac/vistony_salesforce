package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.AddressEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SeguridadEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ClienteEntityResponse;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteViewModel extends ViewModel {
    private ArrayList<ClienteSQLiteEntity> LCliente = new ArrayList<>();
    private ArrayList<SeguridadEntity> LSeguridad = new ArrayList<>();
    private MutableLiveData<String> estado= new MutableLiveData<>();
    private Context context;
    private ClienteSQlite clienteSQlite;
    private DireccionClienteWS direccionClienteWS;

    public ClienteViewModel(){}

    public ClienteViewModel(Context context){
        this.context=context;
    }

    public ArrayList<ClienteSQLiteEntity>getClienteWS(String Imei){
        Call<ClienteEntityResponse> call = Config.getClient().create(Api.class).getCliente(Imei);
        try{
            Response<ClienteEntityResponse> response= call.execute();
                if(response.isSuccessful()) {
                    ClienteEntityResponse clienteEntityResponse=response.body();

                    for(int i = 0; i<clienteEntityResponse.getCustomersEntity().size(); i++){

                        ClienteSQLiteEntity ObjCliente = new ClienteSQLiteEntity();
                        ObjCliente.setCliente_id (clienteEntityResponse.getCustomersEntity().get(i).getClienteId());
                        ObjCliente.setDomembarque_id (clienteEntityResponse.getCustomersEntity().get(i).getDomicilioEmbarque());
                        ObjCliente.setCompania_id (SesionEntity.compania_id);
                        ObjCliente.setNombrecliente (clienteEntityResponse.getCustomersEntity().get(i).getNombre());
                        ObjCliente.setDireccion (clienteEntityResponse.getCustomersEntity().get(i).getDireccion());
                        ObjCliente.setOrden(clienteEntityResponse.getCustomersEntity().get(i).getOrdenVisita());
                        ObjCliente.setZona_id(clienteEntityResponse.getCustomersEntity().get(i).getZonaId());
                        ObjCliente.setZona(clienteEntityResponse.getCustomersEntity().get(i).getZona());
                        ObjCliente.setRucdni(clienteEntityResponse.getCustomersEntity().get(i).getDocumento());
                        ObjCliente.setMoneda(clienteEntityResponse.getCustomersEntity().get(i).getMoneda());
                        ObjCliente.setTelefonofijo(clienteEntityResponse.getCustomersEntity().get(i).getTelefoFijo());
                        ObjCliente.setTelefonomovil(clienteEntityResponse.getCustomersEntity().get(i).getTelefonoMovil());
                        ObjCliente.setCorreo(clienteEntityResponse.getCustomersEntity().get(i).getCorreo());
                        ObjCliente.setUbigeo_id(clienteEntityResponse.getCustomersEntity().get(i).getUbigeoId());

                        if(clienteEntityResponse.getCustomersEntity().get(i).getAddress().size()==0){
                            ObjCliente.setListAddress(null);
                        }else{
                            ObjCliente.setListAddress(clienteEntityResponse.getCustomersEntity().get(i).getAddress());
                        }

                        ObjCliente.setListInvoice(clienteEntityResponse.getCustomersEntity().get(i).getInvoices());

                        ObjCliente.setCategoria(clienteEntityResponse.getCustomersEntity().get(i).getCategoria());
                        ObjCliente.setLinea_credito(clienteEntityResponse.getCustomersEntity().get(i).getLinea_credito());
                        ObjCliente.setLinea_credito_usado(clienteEntityResponse.getCustomersEntity().get(i).getlinea_credito_usado());
                        ObjCliente.setTerminopago_id(clienteEntityResponse.getCustomersEntity().get(i).getTerminoPago_id());

                        LCliente.add(ObjCliente);
                    }
                }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("getClienteWS",""+e);
        }
        Log.e("getClienteWS","=> salido"+LCliente.size());
        return LCliente;
    }

    public MutableLiveData<String> updateInformationClient(final String imei, final String cliente,Context context){
        Config.getClient().create(Api.class).getClienteInformation(imei,cliente).enqueue(new Callback<ClienteEntityResponse>() {
            @Override
            public void onResponse(Call<ClienteEntityResponse> call, Response<ClienteEntityResponse> response) {
                //ClienteSQliteDAO clienteSQliteDAO= new ClienteSQliteDAO(context);
                if(response.isSuccessful() && response.body().getCustomersEntity().size()>0){
                    /*usuarioSQLiteDao.LimpiarTablaUsuario();
                    for (int i = 0; i < response.body().getClienteEntity().size(); i++){

                    }*/
                    estado.setValue("if");
                }else{
                    estado.setValue("else");
                }
            }

            @Override
            public void onFailure(Call<ClienteEntityResponse> call, Throwable t) {
                estado.setValue("onFailure");
            }
        });

        return estado;
    }

    public void addCustomer(List<ClienteSQLiteEntity> Lista){
        Log.e("addCustomer","ENTRO");
        clienteSQlite=new ClienteSQlite(context);
        direccionClienteWS=new DireccionClienteWS(context);

        for (int i = 0; i < Lista.size(); i++) {

            List<AddressEntity> addressCustomer=Lista.get(i).getListAddress();
            if(addressCustomer!=null){
                direccionClienteWS.addAddress(addressCustomer,Lista.get(i).getCompania_id(),Lista.get(i).getCliente_id());
            }

            //Lista.get(i).getListInvoice();

            clienteSQlite.InsertaCliente(
                Lista.get(i).getCliente_id(),
                Lista.get(i).getDomembarque_id(),
                Lista.get(i).getCompania_id(),
                Lista.get(i).getNombrecliente(),
                Lista.get(i).getDireccion(),
                Lista.get(i).getZona_id(),
                Lista.get(i).getOrden(),
                Lista.get(i).getZona(),
                Lista.get(i).getRucdni(),
                Lista.get(i).getMoneda(),
                Lista.get(i).getTelefonofijo(),
                Lista.get(i).getTelefonomovil(),
                Lista.get(i).getUbigeo_id(),
                Lista.get(i).getImpuesto_id(),
                Lista.get(i).getImpuesto(),
                Lista.get(i).getTipocambio(),
                Lista.get(i).getCategoria(),
                Lista.get(i).getLinea_credito(),
                Lista.get(i).getLinea_credito_usado(),
                Lista.get(i).getTerminopago_id()
            );
        }
    }

    public Integer countCustomer(){
        return clienteSQlite.ObtenerCantidadClientes();
    }
}
