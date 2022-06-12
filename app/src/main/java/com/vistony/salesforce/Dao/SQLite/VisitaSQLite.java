package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Controller.Utilitario.Utilitario;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

public class VisitaSQLite {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<VisitaSQLiteEntity> listaVisitaSQLiteEntity;
    Context Context;

    public VisitaSQLite(Context context)
    {

        sqliteController = new SqliteController(context);
        Context=context;
    }

    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos desde " + this.getClass().getName());
        bd = sqliteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos desde " + this.getClass().getName());
        sqliteController.close();
    }


    public int InsertaVisita(@NonNull VisitaSQLiteEntity visita)
    {

        abrir();
        ContentValues registro = new ContentValues();
        //registro.put("id", UUID.randomUUID().toString());
        registro.put("id", FormulasController.ObtenerFechaHoraCadena());
        registro.put("compania_id",visita.getCompania_id());
        registro.put("cliente_id",visita.getCardCode());
        registro.put("direccion_id",visita.getAddress());
        registro.put("fecha_registro",visita.getDate());
        registro.put("hora_registro",visita.getHour());
        registro.put("zona_id",visita.getTerritory());
        registro.put("fuerzatrabajo_id",visita.getSlpCode());
        registro.put("usuario_id",visita.getUserId());
        registro.put("tipo",visita.getType());
        registro.put("observacion",visita.getObservation());
        registro.put("chkenviado",visita.getChkenviado());
        registro.put("chkrecibido",visita.getChkrecibido());
        registro.put("latitud",visita.getLatitude());
        registro.put("longitud",visita.getLongitude());
        registro.put("countsend","1");
        registro.put("chkruta",visita.getStatusRoute());
        registro.put("id_trans_mobile",visita.getMobileID());
        registro.put("amount",visita.getAmount());
        registro.put("terminopago_id",visita.getTerminoPago_ID());
        registro.put("hora_anterior",visita.getHour_Before());
        bd.insert("visita",null,registro);
        bd.close();
        return 1;
    }

    public ArrayList<VisitaSQLiteEntity> ObtenerVisitas (){
        listaVisitaSQLiteEntity = new ArrayList<VisitaSQLiteEntity>();
        String brand = Build.MANUFACTURER;
        String model = Build.MODEL;
        String osVersion = android.os.Build.VERSION.RELEASE;
        try {
            abrir();
            Cursor fila = bd.rawQuery("SELECT id,cliente_id,direccion_id,fecha_registro,hora_registro,zona_id,fuerzatrabajo_id,usuario_id,tipo,motivo,observacion,latitud,longitud,countsend,IFNULL(chkruta,0) AS chkruta ,IFNULL(id_trans_mobile,0) AS id_trans_mobile ,IFNULL(amount,0) AS amount,IFNULL(hora_anterior,0)  AS hora_anterior FROM VISITA WHERE chkrecibido='0' AND fecha_registro>='20220601' LIMIT 30", null);

            if (fila.moveToFirst()) {
                do {
                    String chkruta="N";
                    VisitaSQLiteEntity visita = new VisitaSQLiteEntity();
                    visita.setIdVisit(fila.getString(fila.getColumnIndex("id")));
                    visita.setCardCode(fila.getString(fila.getColumnIndex("cliente_id")));
                    visita.setAddress(fila.getString(fila.getColumnIndex("direccion_id")));
                    visita.setDate(fila.getString(fila.getColumnIndex("fecha_registro")));
                    visita.setHour(fila.getString(fila.getColumnIndex("hora_registro")));
                    visita.setTerritory(fila.getString(fila.getColumnIndex("zona_id")));

                    visita.setType(fila.getString(fila.getColumnIndex("tipo")));
                    visita.setObservation(fila.getString(fila.getColumnIndex("observacion")));
                    visita.setLatitude(fila.getString(fila.getColumnIndex("latitud")));
                    visita.setLongitude(fila.getString(fila.getColumnIndex("longitud")));
                    visita.setAppVersion(Utilitario.getVersion(Context));
                    visita.setModel(model);
                    visita.setBrand(brand);
                    visita.setOSVersion(osVersion);
                    visita.setIntent (fila.getString(fila.getColumnIndex("countsend")));
                    if(fila.getString(fila.getColumnIndex("chkruta")).equals("1"))
                    {
                        chkruta="Y";
                    }else
                        {
                            chkruta="N";
                        }
                    visita.setStatusRoute (chkruta);
                    visita.setMobileID (fila.getString(fila.getColumnIndex("id_trans_mobile")));
                    visita.setAmount (fila.getString(fila.getColumnIndex("amount")));
                    visita.setHour_Before (fila.getString(fila.getColumnIndex("hora_anterior")));

                    visita.setSlpCode(SesionEntity.fuerzatrabajo_id);
                    visita.setUserId(SesionEntity.usuario_id);

                    listaVisitaSQLiteEntity.add(visita);

                    UpdateCountSend(fila.getString(fila.getColumnIndex("id")), SesionEntity.compania_id,SesionEntity.usuario_id,fila.getString(fila.getColumnIndex("countsend")));
                } while (fila.moveToNext());
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            Log.e("REOS", "VisitaSQLite-ObtenerVisitas-e"+e.toString());
        }finally {
            bd.close();
        }

        return listaVisitaSQLiteEntity;
    }

    public int LimpiarTablaVisita (){
        abrir();

        bd.execSQL("delete from visita");
        bd.close();
        return 1;
    }

    public int ActualizaResultadoVisitaEnviada (String idVisita){
        int status=0;

        try {
            abrir();

            ContentValues registro = new ContentValues();
            registro.put("chkrecibido","1");
            registro.put("chkenviado","1");

            bd.update("visita",registro,"id=?",new String[]{idVisita});
            status=1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }

        return status;
    }

    public int UpdateCountSend (String id, String compania_id,String usuario_id,String countsend)
    {
        int resultado=0;
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("countsend",String.valueOf(Integer.parseInt(countsend)+1));
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("visita",registro,"id='"+id+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'" ,null);

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }

        bd.close();
        return  resultado;
    }

    public int getCountVisitWithType (
            String date,
            String cardcode,
            String type,
            String chkruta,
            String direccion_id

    )
    {
        int resultado=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from visita where fecha_registro='"+date+"' and cliente_id='"+cardcode+"' and tipo='"+type+"' and chkruta='"+chkruta+"' and direccion_id='"+direccion_id+"'  ",null);

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

    public int getCountVisitWithOV (
            String date,
            String cardcode,
            String type,
            String chkruta,
            String direccion_id

    )
    {
        int resultado=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from visita where fecha_registro='"+date+"' and cliente_id='"+cardcode+"' and tipo IN ('01','12') and chkruta='"+chkruta+"' and direccion_id='"+direccion_id+"'  ",null);

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

    public int getCountVisitWithDate (
            String date,
            String cardcode,
            String chkruta,
            String direccion_id

    )
    {
        int resultado=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from visita where fecha_registro='"+date+"' and cliente_id='"+cardcode+"' and chkruta='"+chkruta+"' and direccion_id='"+direccion_id+"' ",null);

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

    public int getCountVisitWithTypeOVCOB (String date,String chkruta,String type)
    {
        int resultado=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "SELECT count(TABLE_A.compania_id) FROM  (Select compania_id from visita where fecha_registro='"+date+"' and chkruta='"+chkruta+"' and tipo='"+type+"' group by cliente_id) AS TABLE_A ",null);

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

    public int getCountVisitWithTypeVisit (String date,String chkruta)
    {
        int resultado=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "SELECT count(TABLE_A.compania_id) FROM  (Select  compania_id from visita where fecha_registro='"+date+"' and chkruta='"+chkruta+"'" +
                            " group by cliente_id) AS TABLE_A  ",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Log.e("REOS","VisitaSQLite.getCountVisitWithTypeVisit.e:" + e.toString());
        }
        bd.close();
        Log.e("REOS","VisitaSQLite.getCountVisitWithTypeVisit.resultado:" + resultado);
        return resultado;
    }
    public int getCountVisitWithTypeVisitCOB (String date,String chkruta,String type)
    {
        int resultado=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "SELECT count(TABLE_A.compania_id) FROM  (Select compania_id from visita where fecha_registro='"+date+"' and chkruta='"+chkruta+"' and tipo='"+type+"' and terminopago_id='0' group by cliente_id) AS TABLE_A ",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Log.e("REOS","VisitaSQLite.getCountVisitWithTypeVisit.e:" + e.toString());
        }
        bd.close();
        Log.e("REOS","VisitaSQLite.getCountVisitWithTypeVisit.resultado:" + resultado);
        return resultado;
    }

    public float getSumVisitWithTypeOVCOB (String date,String chkruta,String type)
    {
        float resultado=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select IFNULL(SUM(amount),0)  from visita where fecha_registro='"+date+"' and chkruta='"+chkruta+"' and tipo='"+type+"' ",null);

            while (fila.moveToNext())
            {
                resultado= Float.parseFloat(fila.getString(0));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Log.e("REOS","VisitaSQLite.getSumVisitWithTypeOVCOB.e:" + e.toString());
        }
        bd.close();
        Log.e("REOS","VisitaSQLite.getSumVisitWithTypeOVCOB.resultado:" + resultado);
        return resultado;
    }

    public float getSumVisitWithTypeCOB (String date,String chkruta,String type)
    {
        float resultado=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select IFNULL(SUM(amount),0)  from visita where fecha_registro='"+date+"' and chkruta='"+chkruta+"' and tipo='"+type+"' and terminopago_id='0'",null);

            while (fila.moveToNext())
            {
                resultado= Float.parseFloat(fila.getString(0));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Log.e("REOS","VisitaSQLite.getSumVisitWithTypeOVCOB.e:" + e.toString());
        }
        bd.close();
        Log.e("REOS","VisitaSQLite.getSumVisitWithTypeOVCOB.resultado:" + resultado);
        return resultado;
    }

    public int getCountVisitWithTypeCOB (String date,String chkruta,String type)
    {
        int resultado=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "SELECT count(TABLE_A.compania_id) FROM  (Select compania_id from visita where fecha_registro='"+date+"' and chkruta='"+chkruta+"' and tipo='"+type+"' group by cliente_id) AS TABLE_A ",null);

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

    public String getHourAfter (String date)
    {
        String resultado="";
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select IFNULL(max(hora_registro),0) from visita where fecha_registro='"+date+"'",null);

            while (fila.moveToNext())
            {
                resultado= String.valueOf(fila.getString(0));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        bd.close();
        return resultado;
    }
    public int getCountVisitPendingSend (String usuario_id,String compania_id)
    {
        int resultado=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "SELECT count(compania_id) FROM visita where usuario_id='"+usuario_id+"'" +" and compania_id='"+compania_id+"' AND chkrecibido='0' ",null);

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
}
