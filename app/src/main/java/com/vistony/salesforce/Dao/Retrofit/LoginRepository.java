package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
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

        Config.getClientLogin().create(Api.class).getUsers(imei).enqueue(new Callback<LoginEntityResponse>() {
            @Override
            public void onResponse(Call<LoginEntityResponse> call, Response<LoginEntityResponse> response) {
                LoginEntityResponse loginEntityResponse=response.body();
                usuarioSQLite = new UsuarioSQLite(context);
                if(response.isSuccessful() && loginEntityResponse.getUsers().size()>0){
                    Log.e("REOS","LoginRepository-getAndLoadUsers-loginEntityResponse.getUsers().size():"+loginEntityResponse.getUsers().size());
                    usuarioSQLite.LimpiarTablaUsuario();
                    //Integer status=usuarioSQLite.InsertaUsuario(loginEntityResponse.getUsers());
                    Integer status=usuarioSQLite.InsertaUsuario(loginEntityResponse.getUsers());
                    /*for (int i = 0; i < response.body().getUsers().size(); i++){

                        if(status==0){
                            Toast.makeText(context, "Ocurrio un error al obtener la configuración de "+response.body().getUsers().get(i).getNombreusuario(), Toast.LENGTH_SHORT).show();
                        }
                    }*/
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

    public UsuarioSQLiteEntity loginUser(String compania, String usuario){
        Integer statusQuery= usuarioSQLite.ActualizaUsuario(compania,usuario);
        UsuarioSQLiteEntity userSesion= usuarioSQLite.ObtenerUsuarioSesion();
        if(userSesion!=null && statusQuery==1){
            return userSesion;
        }else{
            return null;
        }
    }
}
