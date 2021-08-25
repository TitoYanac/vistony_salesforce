package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.LeadSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.LeadEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadClienteViewModel extends ViewModel {

    private LeadSQLite leadSQLite;

    public MutableLiveData<String> sendLead(final HashMap<String, String> params,final Context context) {
        MutableLiveData<String> temp=new MutableLiveData<String>();

        if(leadSQLite==null){
            leadSQLite = new LeadSQLite(context);
        }

        temp.setValue("init");
        insertLeadSqlite(params,context);
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),("{ \"Leads\":["+new JSONObject(params).toString()+"]}"));
        Config.getClient().create(Api.class).sendLead("http://169.47.196.209/v1.0/api/customers",requestBody).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){

                    leadSQLite.updateStatusSend(params.get("DateTime"),params.get("CardName"));

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

    public MutableLiveData<String> sendLeadNotSend(final Context context){
        if(leadSQLite==null){
            leadSQLite = new LeadSQLite(context);
        }

        MutableLiveData<String> temp=new MutableLiveData<String>();

        ArrayList<LeadEntity> leadEntity=leadSQLite.getLeadNotSend();
        JSONArray jsArray = new JSONArray(leadEntity);

        temp.setValue(jsArray.toString());

        return temp;
    }

    private void insertLeadSqlite(final HashMap<String, String> params,final Context context){
        if(leadSQLite==null){
            leadSQLite = new LeadSQLite(context);
        }

        leadSQLite.addLead(
                params.get("CardName"),
                params.get("TaxNumber"),
                params.get("TradeName"),
                params.get("NumberPhono"),
                params.get("NumberCellPhone"),
                params.get("ContactPerson"),
                params.get("Email"),
                params.get("Latitude"),
                params.get("Longitude"),
                params.get("Comentary"),
                params.get("Category"),
                params.get("photo"),
                params.get("DateTime"),
                params.get("SalesPersonCode"),
                params.get("DocumentsOwner"),
                params.get("Address"),
                params.get("Reference")
        );
    }
}
