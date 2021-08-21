package com.vistony.salesforce.Dao.Retrofit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdenVentaViewModel extends ViewModel {

    public MutableLiveData<String []> sendSalesOrder(final String jsonOv) {
        MutableLiveData<String []> temp=new MutableLiveData<String []>();
        String [] Lista=new String[]{"1","Orden de Venta registrada con éxito.", "118713","201200007"};

        try {
            RequestBody json = null;

            json = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonOv)).toString());

            Config.getClient().create(Api.class).sendOrder(json).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if(response.isSuccessful()){
                        temp.setValue(Lista);
                    }else{
                        temp.setValue(null);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    temp.setValue(null);
                }
            });


        } catch (JSONException e) {
            temp.setValue(null);
            e.printStackTrace();
        }
        return temp;
    }

}
