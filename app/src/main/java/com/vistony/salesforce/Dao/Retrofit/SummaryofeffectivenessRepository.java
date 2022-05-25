package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SummaryofeffectivenessEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.WareHousesEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.SummaryofeffectivenessEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.WareHousesEntityResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryofeffectivenessRepository extends ViewModel {
    private MutableLiveData<List<SummaryofeffectivenessEntity>> summaryofeffectivenessEntityList= new MutableLiveData<>();


    public MutableLiveData<List<SummaryofeffectivenessEntity>> getSummaryofEffectiveness(
            String Imei,
            String FecIni,
            String FecFin){
        summaryofeffectivenessEntityList= new MutableLiveData<>();
        Config.getClient().create(Api.class).getSummaryofEffectiveness(Imei,FecIni,FecFin).enqueue(new Callback<SummaryofeffectivenessEntityResponse>() {
            @Override
            public void onResponse(Call<SummaryofeffectivenessEntityResponse> call, Response<SummaryofeffectivenessEntityResponse> response) {

                SummaryofeffectivenessEntityResponse summaryofeffectivenessEntityResponse=response.body();
                Log.e("REOS","SummaryofeffectivenessRepository-getSummaryofEffectiveness-call"+call);
                Log.e("REOS","SummaryofeffectivenessRepository-getSummaryofEffectiveness-response"+response);
                if(response.isSuccessful() && summaryofeffectivenessEntityResponse.getSummaryofeffectivenessEntities() .size()>0){
                    Log.e("REOS","SummaryofeffectivenessRepository-getSummaryofEffectiveness-summaryofeffectivenessEntityResponse:"+summaryofeffectivenessEntityResponse.toString());
                    summaryofeffectivenessEntityList.setValue(summaryofeffectivenessEntityResponse.getSummaryofeffectivenessEntities());
                }else
                {
                    summaryofeffectivenessEntityList.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SummaryofeffectivenessEntityResponse> call, Throwable t) {
                summaryofeffectivenessEntityList=null;
            }
        });
        return summaryofeffectivenessEntityList;
    }
}
