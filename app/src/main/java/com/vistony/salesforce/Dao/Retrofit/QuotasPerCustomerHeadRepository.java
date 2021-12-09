package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerHeadEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.QuotasPerCustomerEntityHeadResponse;
import com.vistony.salesforce.View.QuotasPerCustomerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotasPerCustomerHeadRepository extends ViewModel  {
    private BancoSQLite bancoSQLite;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<List<QuotasPerCustomerHeadEntity>> status= new MutableLiveData<>();


    public MutableLiveData<List<QuotasPerCustomerHeadEntity>> getQuotasPerCustomer(
            String SlpCode,
            Context context,
            String CardCode
    ){

        Config.getClient().create(Api.class).getQuotasPerCustomerHead(CardCode,SlpCode).enqueue(new Callback<QuotasPerCustomerEntityHeadResponse>() {
            @Override
            public void onResponse(Call<QuotasPerCustomerEntityHeadResponse> call, Response<QuotasPerCustomerEntityHeadResponse> response) {
                QuotasPerCustomerEntityHeadResponse QuotasPerCustomerEntityResponse=response.body();
                Log.e("REOS","QuotasPerCustomerHeadRepository-getQuotasPerCustomer-call"+call);
                Log.e("REOS","QuotasPerCustomerHeadRepository-getQuotasPerCustomer-response"+response);
                if(response.isSuccessful() && QuotasPerCustomerEntityResponse.getQuotasPerCustomerEntity().size()>0){
                    //QuotasPerCustomerView.newInstanceRecibirLista(QuotasPerCustomerEntityResponse.getQuotasPerCustomerEntity());
                status.setValue(QuotasPerCustomerEntityResponse.getQuotasPerCustomerEntity());
                }
            }

            @Override
            public void onFailure(Call<QuotasPerCustomerEntityHeadResponse> call, Throwable t) {
                status.setValue(null);
            }
        });
        return status;
    }

}
