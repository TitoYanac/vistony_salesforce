package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.AddressEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.InvoicesEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SeguridadEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ClienteEntityResponse;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteRepository extends ViewModel {
    private ArrayList<ClienteSQLiteEntity> LCliente = new ArrayList<>();
    private ArrayList<SeguridadEntity> LSeguridad = new ArrayList<>();
    private MutableLiveData<String> estado= new MutableLiveData<>();
    private Context context;
    private ClienteSQlite clienteSQlite;
    private DireccionViewModel direccionViewModel;

    public ClienteRepository(){}

    public ClienteRepository(Context context){
        this.context=context;
    }

    public ArrayList<ClienteSQLiteEntity>getCustomers(String Imei){
        Call<ClienteEntityResponse> call = Config.getClient().create(Api.class).getCliente("http://169.47.196.209/cl/api/customers",Imei);
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
                        ObjCliente.setMoneda(clienteEntityResponse.getCustomersEntity().get(i).getMoneda());
                        ObjCliente.setTelefonofijo(clienteEntityResponse.getCustomersEntity().get(i).getTelefoFijo());
                        ObjCliente.setTelefonomovil(clienteEntityResponse.getCustomersEntity().get(i).getTelefonoMovil());
                        ObjCliente.setCorreo(clienteEntityResponse.getCustomersEntity().get(i).getCorreo());
                        ObjCliente.setUbigeo_id(clienteEntityResponse.getCustomersEntity().get(i).getUbigeoId());
                        ObjCliente.setRucdni(clienteEntityResponse.getCustomersEntity().get(i).getLicTradNum());

                        //Documentos
                        if(clienteEntityResponse.getCustomersEntity().get(i).getInvoices().size()==0){
                            ObjCliente.setListInvoice(null);
                        }else{
                            List<InvoicesEntity> listaDocumentos=clienteEntityResponse.getCustomersEntity().get(i).getInvoices();
                        }

                        //Direcciones
                        if(clienteEntityResponse.getCustomersEntity().get(i).getAddress().size()==0){
                            ObjCliente.setListAddress(null);
                        }else{
                            List<AddressEntity> listaDirecciones=clienteEntityResponse.getCustomersEntity().get(i).getAddress();

                            for(AddressEntity direccion:listaDirecciones){
                                if(direccion.getFuerzatrabajoid().equals(SesionEntity.fuerzatrabajo_id)){
                                    ObjCliente.setDireccion(direccion.getDireccion());
                                    ObjCliente.setZona(direccion.getZona());
                                    ObjCliente.setZona_id(direccion.getZonaid());
                                    ObjCliente.setDomembarque_id(direccion.getDomicilioEmbarque());
                                    break;
                                }
                            }

                            ObjCliente.setListAddress(listaDirecciones);
                        }

                        ObjCliente.setListInvoice(clienteEntityResponse.getCustomersEntity().get(i).getInvoices());
                        ObjCliente.setCategoria(clienteEntityResponse.getCustomersEntity().get(i).getCategoria());
                        ObjCliente.setLinea_credito(clienteEntityResponse.getCustomersEntity().get(i).getLinea_credito());
                        ObjCliente.setLinea_credito_usado(clienteEntityResponse.getCustomersEntity().get(i).getlinea_credito_usado());
                        ObjCliente.setTerminopago_id(clienteEntityResponse.getCustomersEntity().get(i).getTerminoPago_id());
                        ObjCliente.setLista_precio(clienteEntityResponse.getCustomersEntity().get(i).getLista_precio());

                        //la funcion addCustomer  debe darse aqui!
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
        //DEbe ir dentro de la funcion getCustomers
        clienteSQlite=new ClienteSQlite(context);
        direccionViewModel =new DireccionViewModel();

        for (int i = 0; i < Lista.size(); i++) {

            List<AddressEntity> addressCustomer=Lista.get(i).getListAddress();
            if(addressCustomer!=null){
                direccionViewModel.addAddress(context,addressCustomer,Lista.get(i).getCompania_id(),Lista.get(i).getCliente_id());
            }

            //FLATA GUARDAR LOS DOCUMENTOS
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
                Lista.get(i).getTerminopago_id(),
                Lista.get(i).getLista_precio()
            );
        }
    }

    public Integer countCustomer(){
        return clienteSQlite.ObtenerCantidadClientes();
    }

    public MutableLiveData<ArrayList<ListaClienteCabeceraEntity>> getCustomerNotRoute(Context context, Executor executor){

        MutableLiveData<ArrayList<ListaClienteCabeceraEntity>> listCustomerNotRoute=new MutableLiveData<ArrayList<ListaClienteCabeceraEntity>>();

        if(clienteSQlite==null){
            clienteSQlite=new ClienteSQlite(context);
        }

        executor.execute(() -> {
            ArrayList<ListaClienteCabeceraEntity> listaTemp=clienteSQlite.ObtenerClientes();

            if(listaTemp!=null && listaTemp.size()>0){
                listCustomerNotRoute.postValue(clienteSQlite.ObtenerClientes());
            }else{
                listCustomerNotRoute.postValue(null);
            }
        });

        return  listCustomerNotRoute;
    }
}
