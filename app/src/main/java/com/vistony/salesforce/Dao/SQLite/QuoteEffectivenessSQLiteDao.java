package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuoteEffectivenessEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ReasonDispatchEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

public class QuoteEffectivenessSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<QuoteEffectivenessEntity> listQuoteEffectivenessEntity;

    public QuoteEffectivenessSQLiteDao(Context context)
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
    public int AddQuoteEffectiveness (List<QuoteEffectivenessEntity> listQuoteEffectivenessEntity)
    {
        abrir();
        for (int i = 0; i < listQuoteEffectivenessEntity.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id", SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id", SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id", SesionEntity.usuario_id);
            registro.put("code",listQuoteEffectivenessEntity.get(i).getCode());
            registro.put("type",listQuoteEffectivenessEntity.get(i).getType());
            registro.put("quote",listQuoteEffectivenessEntity.get(i).getQuote());
            registro.put("umd",listQuoteEffectivenessEntity.get(i).getUmd());
            bd.insert("quoteeffectiveness",null,registro);
        }
        bd.close();
        return 1;
    }

    public int DeleteTableQuoteEffectiveness  ()
    {
        abrir();
        bd.execSQL("delete from quoteeffectiveness");
        bd.close();
        return 1;
    }

    public Integer getCountQuoteEffectiveness  ()
    {
        Integer resultado=0;

        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(compania_id) from quoteeffectiveness",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));
            }
        }catch (Exception e)
        {
            //Sentry.captureMessage(e.getMessage());
        }finally {
            bd.close();
        }

        return resultado;
    }


    public ArrayList<QuoteEffectivenessEntity> getListQuoteEffectivenessEntity (String code)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listQuoteEffectivenessEntity = new ArrayList<QuoteEffectivenessEntity>();
        QuoteEffectivenessEntity quoteEffectivenessEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from quoteeffectiveness where code='"+code+"'",null);

        while (fila.moveToNext())
        {
            quoteEffectivenessEntity= new QuoteEffectivenessEntity();
            quoteEffectivenessEntity.setCompania_id(fila.getString(0));
            quoteEffectivenessEntity.setFuerzatrabajo_id(fila.getString(1));
            quoteEffectivenessEntity.setUsuario_id(fila.getString(2));
            quoteEffectivenessEntity.setCode(fila.getString(3));
            quoteEffectivenessEntity.setType(fila.getString(4));
            quoteEffectivenessEntity.setQuote(fila.getString(5));
            quoteEffectivenessEntity.setUmd(fila.getString(6));
            listQuoteEffectivenessEntity.add(quoteEffectivenessEntity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listQuoteEffectivenessEntity;
    }


}
