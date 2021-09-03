package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VisitaEntity;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;

import java.util.ArrayList;

public class VisitaSQLite {
    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<VisitaSQLiteEntity> listaVisitaSQLiteEntity;

    public VisitaSQLite(Context context)
    {
        sqLiteController = new SQLiteController(context);
    }

    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos desde " + this.getClass().getName());
        bd = sqLiteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos desde " + this.getClass().getName());
        sqLiteController.close();
    }


    public int InsertaVisita(@NonNull VisitaSQLiteEntity visita)
    {

        abrir();
        ContentValues registro = new ContentValues();
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

        bd.insert("visita",null,registro);
        bd.close();
        return 1;
    }

    public void updateEnviado(@NonNull String fecha,@NonNull String user_id){
        abrir();
        bd.execSQL("UPDATE visita SET enviado='1' WHERE fecha='"+fecha+"' AND user_id='"+user_id+"';");
        bd.close();
    }

    public ArrayList<VisitaSQLiteEntity> ObtenerVisitas (){
        listaVisitaSQLiteEntity = new ArrayList<VisitaSQLiteEntity>();
        try {
            abrir();
            Cursor fila = bd.rawQuery("SELECT id,cliente_id,direccion_id,fecha_registro,hora_registro,zona_id,fuerzatrabajo_id,usuario_id,tipo,motivo,observacion,latitud,longitud FROM VISITA WHERE chkrecibido='0' ", null);

            if (fila.moveToFirst()) {
                do {
                    VisitaSQLiteEntity visita = new VisitaSQLiteEntity();

                    visita.setIdVisit(fila.getString(fila.getColumnIndex("id")));
                    visita.setCardCode(fila.getString(fila.getColumnIndex("cliente_id")));
                    visita.setAddress(fila.getString(fila.getColumnIndex("direccion_id")));
                    visita.setDate(fila.getString(fila.getColumnIndex("fecha_registro")));
                    visita.setHour(fila.getString(fila.getColumnIndex("hora_registro")));
                    visita.setTerritory(fila.getString(fila.getColumnIndex("zona_id")));
                    visita.setSlpCode(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
                    visita.setUserId(fila.getString(fila.getColumnIndex("usuario_id")));
                    visita.setType(fila.getString(fila.getColumnIndex("tipo")));
                    visita.setObservation(fila.getString(fila.getColumnIndex("observacion")));
                    visita.setLatitude(fila.getString(fila.getColumnIndex("latitud")));
                    visita.setLongitude(fila.getString(fila.getColumnIndex("longitud")));

                    listaVisitaSQLiteEntity.add(visita);
                } while (fila.moveToNext());
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            bd.close();
        }

        return listaVisitaSQLiteEntity;
    }

    public int LimpiarTablaVisita ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from visita");
        bd.close();
        return 1;
    }

    public boolean ActualizaEstadoWSVisita (VisitaEntity data){
        int status=0;

        try {
            abrir();

            for(VisitaSQLiteEntity visita:data.getVisitas()){

                if(visita.getHaveError().equals("0")){
                    ContentValues registro = new ContentValues();
                    registro.put("chkrecibido","1");
                    registro.put("chkenviado","1");

                    bd.update("visita",registro,"id=?",new String[]{visita.getIdVisit()});
                }else{
                    status=status+1;
                }

            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            bd.close();
        }

        if(status!=0){
            return false;
        }else{
            return true;
        }
    }
}
