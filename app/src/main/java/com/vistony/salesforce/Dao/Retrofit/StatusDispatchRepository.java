package com.vistony.salesforce.Dao.Retrofit;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SummaryofeffectivenessEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricStatusDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoCobranzaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.SummaryofeffectivenessEntityResponse;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusDispatchRepository  extends ViewModel {
    private MutableLiveData<List<HistoricStatusDispatchEntity>> ListHistoricStatusDispatchEntity= new MutableLiveData<>();

    public MutableLiveData<List<HistoricStatusDispatchEntity>> getHistoricStatusDispatch(
            String Imei,
            String Date){
        ListHistoricStatusDispatchEntity= new MutableLiveData<>();
        Config.getClient().create(Api.class).geHistoricStatusDispatch(Imei,Date).enqueue(new Callback<HistoricStatusDispatchEntityResponse>() {
            @Override
            public void onResponse(Call<HistoricStatusDispatchEntityResponse> call, Response<HistoricStatusDispatchEntityResponse> response) {
                HistoricStatusDispatchEntityResponse historicStatusDispatchEntityResponse=response.body();
                Log.e("REOS","StatusDispatchRepository-getHistoricStatusDispatch-call: "+call.toString());
                Log.e("REOS","StatusDispatchRepository-getHistoricStatusDispatch-response: "+response.toString());
                if(response.isSuccessful() && historicStatusDispatchEntityResponse.getHistoricStatusDispatch() .size()>0){
                    ListHistoricStatusDispatchEntity.setValue(historicStatusDispatchEntityResponse.getHistoricStatusDispatch());
                }else
                {
                    ListHistoricStatusDispatchEntity.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<HistoricStatusDispatchEntityResponse> call, Throwable t) {
                ListHistoricStatusDispatchEntity=null;
            }
        });
        return ListHistoricStatusDispatchEntity;
    }

}
