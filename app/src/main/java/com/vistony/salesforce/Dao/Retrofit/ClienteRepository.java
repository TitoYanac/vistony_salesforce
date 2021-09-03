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
    private DireccionRepository direccionRepository;
    private DocumentoRepository documentoRepository;

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

                    for(int i = 0; i<clienteEntityResponse.getClienteEntity().size(); i++){

                        ClienteSQLiteEntity ObjCliente = new ClienteSQLiteEntity();
                        ObjCliente.setCliente_id (clienteEntityResponse.getClienteEntity().get(i).getClienteId());
                        ObjCliente.setDomembarque_id (clienteEntityResponse.getClienteEntity().get(i).getDomicilioEmbarque());
                        ObjCliente.setCompania_id (SesionEntity.compania_id);
                        ObjCliente.setNombrecliente (clienteEntityResponse.getClienteEntity().get(i).getNombre());
                        ObjCliente.setDireccion (clienteEntityResponse.getClienteEntity().get(i).getDireccion());
                        ObjCliente.setOrden(clienteEntityResponse.getClienteEntity().get(i).getOrdenVisita());
                        ObjCliente.setZona_id(clienteEntityResponse.getClienteEntity().get(i).getZonaId());
                        ObjCliente.setZona(clienteEntityResponse.getClienteEntity().get(i).getZona());
                        ObjCliente.setMoneda(clienteEntityResponse.getClienteEntity().get(i).getMoneda());
                        ObjCliente.setTelefonofijo(clienteEntityResponse.getClienteEntity().get(i).getTelefoFijo());
                        ObjCliente.setTelefonomovil(clienteEntityResponse.getClienteEntity().get(i).getTelefonoMovil());
                        ObjCliente.setCorreo(clienteEntityResponse.getClienteEntity().get(i).getCorreo());
                        ObjCliente.setUbigeo_id(clienteEntityResponse.getClienteEntity().get(i).getUbigeoId());
                        ObjCliente.setRucdni(clienteEntityResponse.getClienteEntity().get(i).getLicTradNum());

                        //Documentos
                        if(clienteEntityResponse.getClienteEntity().get(i).getInvoices() == null || clienteEntityResponse.getClienteEntity().get(i).getInvoices().size()==0){
                            ObjCliente.setListInvoice(null);
                        }else{
                            List<InvoicesEntity> listaDocumentos=clienteEntityResponse.getClienteEntity().get(i).getInvoices();
                            ObjCliente.setListInvoice(listaDocumentos);
                        }

                        //Direcciones
                        if(clienteEntityResponse.getClienteEntity().get(i).getAddress() == null || clienteEntityResponse.getClienteEntity().get(i).getAddress().size()==0){
                            ObjCliente.setListAddress(null);
                        }else{
                            List<AddressEntity> listaDirecciones=clienteEntityResponse.getClienteEntity().get(i).getAddress();

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

                        ObjCliente.setListInvoice(clienteEntityResponse.getClienteEntity().get(i).getInvoices());
                        ObjCliente.setCategoria(clienteEntityResponse.getClienteEntity().get(i).getCategoria());
                        ObjCliente.setLinea_credito(clienteEntityResponse.getClienteEntity().get(i).getLinea_credito());
                        ObjCliente.setLinea_credito_usado(clienteEntityResponse.getClienteEntity().get(i).getlinea_credito_usado());
                        ObjCliente.setTerminopago_id(clienteEntityResponse.getClienteEntity().get(i).getTerminoPago_id());
                        ObjCliente.setLista_precio(clienteEntityResponse.getClienteEntity().get(i).getLista_precio());
                        ObjCliente.setDueDays(clienteEntityResponse.getClienteEntity().get(i).getDiasVencidos());
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
                if(response.isSuccessful() && response.body().getClienteEntity().size()>0){
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

        if(clienteSQlite==null){
            clienteSQlite=new ClienteSQlite(context);
        }

        if(direccionRepository==null){
            direccionRepository =new DireccionRepository();
        }

        if(documentoRepository==null){
            documentoRepository =new DocumentoRepository();
        }

        for (int i = 0; i < Lista.size(); i++) {

            //direcciones
            List<AddressEntity> addressCustomer=Lista.get(i).getListAddress();
            if(addressCustomer!=null){
                direccionRepository.addAddress(context,addressCustomer,Lista.get(i).getCompania_id(),Lista.get(i).getCliente_id());
            }

            //documentos
            List<InvoicesEntity> invoicesCustomer=Lista.get(i).getListInvoice();
            if(invoicesCustomer!=null){
                documentoRepository.addInvoices(context,invoicesCustomer,Lista.get(i).getCompania_id(),Lista.get(i).getCliente_id());
            }

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
                Lista.get(i).getLista_precio(),
                    Lista.get(i).getDueDays()
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
