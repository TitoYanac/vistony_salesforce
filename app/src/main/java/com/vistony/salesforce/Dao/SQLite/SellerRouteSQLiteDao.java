package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BancoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SellerRouteEntity;
import com.vistony.salesforce.Entity.SQLite.BancoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;

public class SellerRouteSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<SellerRouteEntity> listaSellerRouteEntity;

    public SellerRouteSQLiteDao(Context context)
    {
        sqliteController = new SqliteController(context);
    }
    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos " + sqliteController.getDatabaseName() );
        bd = sqliteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + sqliteController.getDatabaseName() );
        sqliteController.close();
    }


    //public int InsertaBanco (String banco_id,String compania_id,String nombrebanco)
    public int addSellerRoute (List<SellerRouteEntity> sellerRoute)
    {
        abrir();

        for (int i = 0; i < sellerRoute.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("CardCode",sellerRoute.get(i).getCardCode());
            registro.put("Address", sellerRoute.get(i).getAddress());
            registro.put("Chk_Visit",sellerRoute.get(i).getChk_Visit());
            registro.put("Chk_Pedido",sellerRoute.get(i).getChk_Pedido());
            registro.put("Chk_Cobranza",sellerRoute.get(i).getChk_Cobranza());
            registro.put("Chk_Ruta",sellerRoute.get(i).getChk_Ruta());
            registro.put("FechaRuta",sellerRoute.get(i).getFechaRuta());
            bd.insert("sellerroute",null,registro);
        }

        bd.close();
        return 1;
    }

    public ArrayList<SellerRouteEntity> getSellerRoute ()
    {
        listaSellerRouteEntity = new ArrayList<SellerRouteEntity>();
        SellerRouteEntity sellerRouteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from sellerroute",null);

        while (fila.moveToNext())
        {
            sellerRouteEntity= new SellerRouteEntity();
            sellerRouteEntity.setCardCode(fila.getString(0));
            sellerRouteEntity.setAddress(fila.getString(1));
            sellerRouteEntity.setChk_Visit(fila.getString(3));
            sellerRouteEntity.setChk_Pedido(fila.getString(4));
            sellerRouteEntity.setChk_Cobranza(fila.getString(5));
            sellerRouteEntity.setChk_Ruta(fila.getString(6));
            sellerRouteEntity.setFechaRuta(fila.getString(7));
            listaSellerRouteEntity.add(sellerRouteEntity);
        }

        bd.close();
        return listaSellerRouteEntity;
    }

    public int ClearTableSellerRoute ()
    {
        abrir();
        bd.execSQL("delete from sellerroute ");
        bd.close();
        return 1;
    }

    public Integer getCountSellerRoute ()
    {
        Integer resultado=0;

        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(CardCode) from sellerroute",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));
            }
        }catch (Exception e)
        {
            Sentry.captureMessage(e.getMessage());
        }finally {
            bd.close();
        }
        return resultado;
    }
}
