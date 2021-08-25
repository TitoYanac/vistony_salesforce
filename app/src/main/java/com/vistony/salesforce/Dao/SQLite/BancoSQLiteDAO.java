package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.BancoSQLiteEntity;

import java.util.ArrayList;

public class BancoSQLiteDAO {

    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<BancoSQLiteEntity> listaBancoSQLiteEntity;

    public BancoSQLiteDAO(Context context)
    {
        sqLiteController = new SQLiteController(context);
    }
    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos " + sqLiteController.getDatabaseName() );
        bd = sqLiteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + sqLiteController.getDatabaseName() );
        sqLiteController.close();
    }


    public int InsertaBanco (String banco_id,String compania_id,String nombrebanco)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("banco_id",banco_id);
        registro.put("compania_id",compania_id);
        registro.put("nombrebanco",nombrebanco);
        bd.insert("banco",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ArrayList<BancoSQLiteEntity> ObtenerBanco ()
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaBancoSQLiteEntity = new ArrayList<BancoSQLiteEntity>();
        BancoSQLiteEntity bancoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from banco",null);

        while (fila.moveToNext())
        {
            bancoSQLiteEntity= new BancoSQLiteEntity();
            bancoSQLiteEntity.setBanco_id(fila.getString(0));
            bancoSQLiteEntity.setCompania_id(fila.getString(1));
            bancoSQLiteEntity.setNombrebanco(fila.getString(2));
            listaBancoSQLiteEntity.add(bancoSQLiteEntity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaBancoSQLiteEntity;
    }

    public int LimpiarTablaBanco ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from banco ");
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public int ObtenerCantidadBancos ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(Banco_id) from banco",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));

            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        bd.close();

        return resultado;
    }

    public ArrayList<BancoSQLiteEntity> ObtenerBancoporCombo (String compania_id,String nombrebanco)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaBancoSQLiteEntity = new ArrayList<BancoSQLiteEntity>();
        BancoSQLiteEntity bancoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select banco_id from banco where compania_id= '"+compania_id+"' and nombrebanco= '"+nombrebanco+"'",null);

        while (fila.moveToNext())
        {
            bancoSQLiteEntity= new BancoSQLiteEntity();
            bancoSQLiteEntity.setBanco_id(fila.getString(0));
            listaBancoSQLiteEntity.add(bancoSQLiteEntity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaBancoSQLiteEntity;
    }


    public ArrayList<BancoSQLiteEntity> ObtenerBancoporID (String compania_id,String banco_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaBancoSQLiteEntity = new ArrayList<BancoSQLiteEntity>();
        BancoSQLiteEntity bancoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from banco where compania_id= '"+compania_id+"' and banco_id= '"+banco_id+"'",null);

        while (fila.moveToNext())
        {
            bancoSQLiteEntity= new BancoSQLiteEntity();
            bancoSQLiteEntity.setBanco_id(fila.getString(0));
            bancoSQLiteEntity.setCompania_id(fila.getString(1));
            bancoSQLiteEntity.setNombrebanco(fila.getString(2));
            listaBancoSQLiteEntity.add(bancoSQLiteEntity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaBancoSQLiteEntity;
    }



}
