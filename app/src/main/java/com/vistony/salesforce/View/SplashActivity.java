package com.vistony.salesforce.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        switch (BuildConfig.FLAVOR)
        {
            case "perurofalab":
                setTheme(R.style.SplashThemeRofalab);
                break;
            default:
                setTheme(R.style.SplashTheme);
                break;
        }

        //setTheme(R.style.SplashTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Intent intent = new Intent(SplashActivity.this, LoginView.class);
        startActivity(intent);
        finish();
    }
}
