package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.DireccionSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaDireccionClienteEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.AddressEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.DireccionClienteEntityResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Response;

public class DireccionRepository extends ViewModel{

    private DireccionSQLite direccionSQLite;
    private MutableLiveData<ArrayList<ListaDireccionClienteEntity>> LDCliente= new MutableLiveData<>();


    public MutableLiveData<ArrayList<ListaDireccionClienteEntity>> getAddress(Context context,Executor executor,String Cliente_id){

        if(direccionSQLite==null){
            direccionSQLite = new DireccionSQLite(context);
        }

        executor.execute(() -> {
            LDCliente.postValue(direccionSQLite.getListAddress(Cliente_id));
        });


        return LDCliente;
    }

    public void addAddress(Context context,List<AddressEntity> Lista,String companiCode,String customerCode) {
       if(direccionSQLite==null){
           direccionSQLite = new DireccionSQLite(context);
       }

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
