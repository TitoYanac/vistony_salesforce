package com.vistony.salesforce.Dao.Retrofit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.FieldMap;

public class LeadClienteViewModel extends ViewModel {

    public MutableLiveData<String> sendLead(HashMap<String, String> params) {
        MutableLiveData<String> temp=new MutableLiveData<String>();

        temp.setValue("init");

        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),("{ \"Leads\":["+new JSONObject(params).toString()+"]}"));
        Config.getClient().create(Api.class).sendLead(requestBody).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    temp.setValue("successful");
                }else{
                    temp.setValue("else " +response.code()+"=>"+response.message()+"=>"+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                temp.setValue("failure");
            }
        });

        return temp;
    }
}
