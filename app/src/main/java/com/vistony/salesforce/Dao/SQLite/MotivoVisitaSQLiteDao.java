package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BancoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.MotivoVisitaEntity;
import com.vistony.salesforce.Entity.SQLite.MotivoVisitaSQLiteEntity;

import java.util.ArrayList;
import java.util.List;

public class MotivoVisitaSQLiteDao {
    SqliteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<MotivoVisitaSQLiteEntity> listaMotivoVisitaSQLiteEntity;

    public MotivoVisitaSQLiteDao(Context context)
    {
        sqLiteController = new SqliteController(context);
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


    public int InsertaMotivoVisita (
            String compania_id,
            String fuerzatrabajo_id,
            String usuario_id,
            String code,
            String name,
            String type

    )
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();


        ContentValues registro = new ContentValues();
        registro.put("compania_id",compania_id);
        registro.put("fuerzatrabajo_id",fuerzatrabajo_id);
        registro.put("usuario_id",usuario_id);
        registro.put("code",code);
        registro.put("name",name);
        registro.put("type",type);

        bd.insert("motivovisita",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public int LimpiarTablaMotivoVisita ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from motivovisita");
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public int ObtenerCantidadMotivoVisita ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from motivovisita",null);

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

    public ArrayList<MotivoVisitaSQLiteEntity> ObtenerMotivoVisita (String compania_id,String fuerzatrabajo_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaMotivoVisitaSQLiteEntity = new ArrayList<MotivoVisitaSQLiteEntity>();
        MotivoVisitaSQLiteEntity motivoVisitaSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from motivovisita where compania_id='"+compania_id+"' and fuerzatrabajo_id='"+fuerzatrabajo_id+"'",null);

        while (fila.moveToNext())
        {
            motivoVisitaSQLiteEntity= new MotivoVisitaSQLiteEntity();
            motivoVisitaSQLiteEntity.setCompania_id(fila.getString(0));
            motivoVisitaSQLiteEntity.setFuerzatrabajo_id(fila.getString(1));
            motivoVisitaSQLiteEntity.setUsuario_id(fila.getString(2));
            motivoVisitaSQLiteEntity.setCode(fila.getString(3));
            motivoVisitaSQLiteEntity.setName(fila.getString(4));
            motivoVisitaSQLiteEntity.setType(fila.getString(5));
            listaMotivoVisitaSQLiteEntity.add(motivoVisitaSQLiteEntity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaMotivoVisitaSQLiteEntity;
    }
}
