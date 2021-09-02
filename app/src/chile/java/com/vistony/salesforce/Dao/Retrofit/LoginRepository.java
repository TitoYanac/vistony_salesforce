package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.LoginEntityResponse;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository extends ViewModel{
    private UsuarioSQLite usuarioSQLite;

    private MutableLiveData<ArrayList<String>> profile= new MutableLiveData<>();

    public MutableLiveData<ArrayList<String>> getAndLoadUsers(final String imei,final Context context){

        Config.getClient().create(Api.class).getUsers("http://169.47.196.209/cl/api/Users",imei).enqueue(new Callback<LoginEntityResponse>() {
            @Override
            public void onResponse(Call<LoginEntityResponse> call, Response<LoginEntityResponse> response) {

                usuarioSQLite = new UsuarioSQLite(context);
                if(response.isSuccessful() && response.body().getUsers().size()>0){
                    usuarioSQLite.LimpiarTablaUsuario();
                    for (int i = 0; i < response.body().getUsers().size(); i++){
                        usuarioSQLite.InsertaUsuario(response.body().getUsers().get(i));
                    }

                }else{
                    Log.e("JEPICAME","=>"+response.code() +"\n=>"+imei);
                }

                profile.setValue(usuarioSQLite.ObtenerPerfiles());
                call.cancel();
            }

            @Override
            public void onFailure(Call<LoginEntityResponse> call, Throwable t) {

                usuarioSQLite = new UsuarioSQLite(context);
                ArrayList<String> perfiles= usuarioSQLite.ObtenerPerfiles();
                if(perfiles.size()>0){
                    profile.setValue(perfiles);
                }else{
                    profile.setValue(null);
                }
                call.cancel();
            }
        });
        return profile;
    }

    public ArrayList<String> getCompanies(String profile){
        return usuarioSQLite.ObtenerCompania(profile);
    }

    public ArrayList<String> getUsers(String profile, String compania){
        return usuarioSQLite.ObtenerUsuario(profile,compania);
    }

    public ArrayList<String> getLenguage(String profile, String compania){
        return usuarioSQLite.ObtenerUsuario(profile,compania);
    }

    public ArrayList<UsuarioSQLiteEntity> loginUser(String compania, String usuario){
        Integer statusQuery= usuarioSQLite.ActualizaUsuario(compania,usuario);
        ArrayList<UsuarioSQLiteEntity> userSesion= usuarioSQLite.ObtenerUsuarioSesion();
        if(!userSesion.isEmpty() && statusQuery==1){
            return userSesion;
        }else{
            return null;
        }
    }
}
