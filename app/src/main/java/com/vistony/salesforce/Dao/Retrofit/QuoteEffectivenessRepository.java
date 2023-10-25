package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.QuoteEffectivenessSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ReasonDispatchSQLite;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.QuoteEffectivenessEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ReasonDispatchEntityResponse;

import java.util.Objects;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuoteEffectivenessRepository extends ViewModel {
    private MutableLiveData<String> status= new MutableLiveData<>();
    QuoteEffectivenessSQLiteDao quoteEffectivenessSQLiteDao;
    ParametrosSQLite parametrosSQLite;

    public MutableLiveData<String> getQuoteEffectiveness (String Imei, Context context, Executor executor){
        Config.getClient().create(Api.class).getQuoteEffectiveness (Imei).enqueue(new Callback<QuoteEffectivenessEntityResponse>() {
            @Override
            public void onResponse(Call<QuoteEffectivenessEntityResponse> call, Response<QuoteEffectivenessEntityResponse> response) {

                QuoteEffectivenessEntityResponse quoteEffectivenessEntityResponse=response.body();
                if(response.isSuccessful() && Objects.requireNonNull(quoteEffectivenessEntityResponse).getQuoteEffectivenessEntities().size()>0){
                    executor.execute(() -> {
                    quoteEffectivenessSQLiteDao = new QuoteEffectivenessSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);
                    quoteEffectivenessSQLiteDao.DeleteTableQuoteEffectiveness();
                    quoteEffectivenessSQLiteDao.AddQuoteEffectiveness (quoteEffectivenessEntityResponse.getQuoteEffectivenessEntities()   );
                    Integer countReasonDispatch=getCountReasonDispatch(context);
                    });
                    status.setValue("1");
                }else
                {
                    status.setValue("0");
                }
            }

            @Override
            public void onFailure(Call<QuoteEffectivenessEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getCountReasonDispatch(Context context){
        return quoteEffectivenessSQLiteDao.getCountQuoteEffectiveness() ;
    }

}
