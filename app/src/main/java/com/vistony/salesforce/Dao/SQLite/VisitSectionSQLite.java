package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BancoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VisitSectionEntity;
import com.vistony.salesforce.Entity.SQLite.BancoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;

public class VisitSectionSQLite {

    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<VisitSectionEntity> listVisitSection;

    public VisitSectionSQLite(Context context)
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
    public int addVisitSection (List<VisitSectionEntity> visitsection)
    {
        abrir();

        for (int i = 0; i < visitsection.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id",visitsection.get(i).getCompania_id());
            registro.put("fuerzatrabajo_id", visitsection.get(i).getFuerzatrabajo_id());
            registro.put("usuario_id",visitsection.get(i).getUsuario_id());
            registro.put("cliente_id",visitsection.get(i).getCliente_id());
            registro.put("domembarque_id",visitsection.get(i).getDomembarque_id());
            registro.put("latitudini",visitsection.get(i).getLatitudini());
            registro.put("longitudini",visitsection.get(i).getLongitudini());
            registro.put("dateini",visitsection.get(i).getDateini());
            registro.put("timeini",visitsection.get(i).getTimeini());
            registro.put("latitudfin",visitsection.get(i).getLatitudfin());
            registro.put("longitudfin",visitsection.get(i).getLongitudfin());
            registro.put("datefin",visitsection.get(i).getDatefin());
            registro.put("timefin",visitsection.get(i).getTimefin());
            registro.put("chkrecibido",visitsection.get(i).getChkrecibido());
            registro.put("idref",visitsection.get(i).getIdref());

            bd.insert("visitsection",null,registro);
        }

        bd.close();
        return 1;
    }

    public ArrayList<VisitSectionEntity> getVisitSection (String cliente_id,String domembarque_id,String dateini,String idref)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listVisitSection = new ArrayList<VisitSectionEntity>();
        VisitSectionEntity visitSectionEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from visitsection where cliente_id='"+cliente_id+"' and domembarque_id='"+domembarque_id+"' and dateini='"+dateini+"' and idref=='"+idref+"'",null);

        while (fila.moveToNext())
        {
            visitSectionEntity= new VisitSectionEntity();
            visitSectionEntity.setCompania_id(fila.getString(0));
            visitSectionEntity.setFuerzatrabajo_id(fila.getString(1));
            visitSectionEntity.setUsuario_id(fila.getString(2));
            visitSectionEntity.setCliente_id(fila.getString(3));
            visitSectionEntity.setDomembarque_id(fila.getString(4));
            visitSectionEntity.setLatitudini(fila.getString(5));
            visitSectionEntity.setLongitudini(fila.getString(6));
            visitSectionEntity.setDateini(fila.getString(7));
            visitSectionEntity.setTimeini(fila.getString(8));
            visitSectionEntity.setLatitudfin(fila.getString(9));
            visitSectionEntity.setLongitudfin(fila.getString(10));
            visitSectionEntity.setDatefin(fila.getString(11));
            visitSectionEntity.setTimefin(fila.getString(12));
            visitSectionEntity.setChkrecibido(fila.getString(13));
            listVisitSection.add(visitSectionEntity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listVisitSection;
    }

    public int ClearTableVisitSection ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from visitsection ");
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public Integer getCountVisitSection ()
    {
        Integer resultado=0;

        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(compania_id) from visitsection",null);

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

    public int UpdateVisitSectionForClient (String cliente,String domembarque_id,String date,String latitudfin,String longitudfin,String datefin,String timefin){
        int resultado=0;

        try{

            ContentValues registro = new ContentValues();
            registro.put("latitudfin",latitudfin);
            registro.put("longitudfin",longitudfin);
            registro.put("datefin",datefin);
            registro.put("timefin",timefin);
            bd = sqliteController.getWritableDatabase();
            resultado=bd.update("visitsection",registro,"cliente_id=? and domembarque_id=? and dateini=? " ,new String[]{cliente, domembarque_id,date});
            bd.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }

        return resultado;
    }

}
