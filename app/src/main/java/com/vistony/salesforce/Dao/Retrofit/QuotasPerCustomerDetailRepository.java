package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerDetailEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.QuotasPerCustomerDetailEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.QuotasPerCustomerEntityHeadResponse;
import com.vistony.salesforce.View.QuotasPerCustomerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotasPerCustomerDetailRepository  extends ViewModel {
    private BancoSQLite bancoSQLite;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<List<QuotasPerCustomerDetailEntity>> status= new MutableLiveData<>();


    public MutableLiveData<List<QuotasPerCustomerDetailEntity>> getQuotasPerCustomerDetail(
            String Imei,
            Context context,
            String CardCode
    ){
        Config.getClient().create(Api.class).getQuotasPerCustomerDetail(CardCode,Imei).enqueue(new Callback<QuotasPerCustomerDetailEntityResponse>() {
            @Override
            public void onResponse(Call<QuotasPerCustomerDetailEntityResponse> call, Response<QuotasPerCustomerDetailEntityResponse> response) {
                Log.e("REOS","QuotasPerCustomerDetailRepository.getQuotasPerCustomerDetail.call:" + call.toString());
                QuotasPerCustomerDetailEntityResponse QuotasPerCustomerDetailEntityResponse=response.body();
                Log.e("REOS","QuotasPerCustomerDetailRepository.getQuotasPerCustomerDetail.response:" + response.toString());
                if(response.isSuccessful() && QuotasPerCustomerDetailEntityResponse.getQuotasPerCustomerDetailEntity().size()>0) {
                    Log.e("REOS", "QuotasPerCustomerDetailRepository.getQuotasPerCustomerDetail.entro:");
                    //QuotasPerCustomerView.newInstanceRecibirListaDetail(QuotasPerCustomerDetailEntityResponse.getQuotasPerCustomerDetailEntity());
                    // }
                    status.setValue(QuotasPerCustomerDetailEntityResponse.getQuotasPerCustomerDetailEntity());
                }
            }

            @Override
            public void onFailure(Call<QuotasPerCustomerDetailEntityResponse> call, Throwable t) {
                status.setValue(null);
            }
        });
        return status;
    }
}
