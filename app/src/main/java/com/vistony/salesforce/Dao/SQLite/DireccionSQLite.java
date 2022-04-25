package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.DataBaseManager;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Adapters.DireccionCliente;
import com.vistony.salesforce.Entity.SQLite.DireccionClienteSQLiteEntity;

import java.util.ArrayList;

public class DireccionSQLite {
    private ArrayList<DireccionClienteSQLiteEntity> listaDireccionClienteSQLiteEntity;
    SqliteController sqliteController;
    SQLiteDatabase bd;
    private void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos desde " + this.getClass().getName());
        bd = sqliteController.getWritableDatabase();
    }

    private void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion desde " + this.getClass().getName());
        sqliteController.close();
    }
    public DireccionSQLite(Context context){

        DataBaseManager.initializeInstance(new SqliteController(context));
        sqliteController = new SqliteController(context);
        bd = sqliteController.getWritableDatabase();
    }

    public int InsertaDireccionCliente (
            String compania_id,
            String cliente_id,
            String domembarque_id,
            String direccion,
            String zona_id,
            String zona,
            String fuerzatrabajo_id,
            String nombrefuerzatrabajo,
            String latitud,
            String longitud

    )
    {
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        ContentValues registro = new ContentValues();
        registro.put("compania_id",compania_id);
        registro.put("cliente_id",cliente_id);
        registro.put("domembarque_id",domembarque_id);
        registro.put("direccion",direccion);
        registro.put("zona_id",zona_id);
        registro.put("zona",zona);
        registro.put("fuerzatrabajo_id",fuerzatrabajo_id);
        registro.put("nombrefuerzatrabajo",nombrefuerzatrabajo);
        registro.put("latitud",latitud);
        registro.put("longitud",longitud);
        sqlite.insert("direccioncliente",null,registro);
        DataBaseManager.getInstance().closeDatabase();

        return 1;
    }

    public ArrayList<DireccionCliente> getListAddress(String cliente_id){
        ArrayList<DireccionCliente> LDCliente = new ArrayList<>();

        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        try {

            Cursor fila = sqlite.rawQuery("SELECT DISTINCT cliente_id,domembarque_id,direccion,zona_id,zona,nombrefuerzatrabajo FROM direccioncliente WHERE cliente_id=?", new String[]{cliente_id});

            if (fila.moveToFirst()) {
                if (fila.moveToFirst()) {
                    do {
                        DireccionCliente ObjDCliente = new DireccionCliente();
                        ObjDCliente.setCliente_id(fila.getString(fila.getColumnIndex("cliente_id")));
                        ObjDCliente.setDomembarque_id(fila.getString(fila.getColumnIndex("domembarque_id")));
                        ObjDCliente.setDireccion(fila.getString(fila.getColumnIndex("direccion")));
                        ObjDCliente.setZona_id(fila.getString(fila.getColumnIndex("zona_id")));
                        ObjDCliente.setZona(fila.getString(fila.getColumnIndex("zona")));
                        ObjDCliente.setNombrefuerzatrabajo(fila.getString(fila.getColumnIndex("nombrefuerzatrabajo")));

                        LDCliente.add(ObjDCliente);
                    } while (fila.moveToNext());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return LDCliente;
    }

    public int LimpiarTablaDireccionCliente(){
        int status=0;
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        try{
            sqlite.execSQL("DELETE FROM direccioncliente");
            status=1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return status;
    }

    public int ObtenerCantidadDireccionCliente ()
    {
        int resultado=0;

        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
        try {
            Cursor fila = sqlite.rawQuery(
                    "Select count(compania_id) from direccioncliente",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return resultado;
    }

    public ArrayList<DireccionCliente> getListAddressOV(String cliente_id,String domembarque_id){
        ArrayList<DireccionCliente> LDCliente = new ArrayList<>();

        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        try {

            Cursor fila = sqlite.rawQuery("SELECT DISTINCT cliente_id,domembarque_id,direccion,zona_id,zona,nombrefuerzatrabajo FROM direccioncliente WHERE cliente_id=? and domembarque_id=?", new String[]{cliente_id,domembarque_id});

            if (fila.moveToFirst()) {
                if (fila.moveToFirst()) {
                    do {
                        DireccionCliente ObjDCliente = new DireccionCliente();
                        ObjDCliente.setCliente_id(fila.getString(fila.getColumnIndex("cliente_id")));
                        ObjDCliente.setDomembarque_id(fila.getString(fila.getColumnIndex("domembarque_id")));
                        ObjDCliente.setDireccion(fila.getString(fila.getColumnIndex("direccion")));
                        ObjDCliente.setZona_id(fila.getString(fila.getColumnIndex("zona_id")));
                        ObjDCliente.setZona(fila.getString(fila.getColumnIndex("zona")));
                        ObjDCliente.setNombrefuerzatrabajo(fila.getString(fila.getColumnIndex("nombrefuerzatrabajo")));

                        LDCliente.add(ObjDCliente);
                    } while (fila.moveToNext());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return LDCliente;
    }

    public int updateCoordenatesAddress(String cliente_id, String domembarque_id,String latitude,String longitude)
    {
        int resultado=0;

        try {
            abrir();
            ContentValues registro = new ContentValues();
            registro.put("latitud",latitude);
            registro.put("longitud",longitude);

            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("direccioncliente",registro,"cliente_id ='"+cliente_id+"' and domembarque_id = '"+domembarque_id+"' " ,null);
            cerrar();
        }catch (Exception e){
            Log.e("Error"," DireccionSQLitge "+e.getMessage());
        }finally {
            cerrar();
        }

        return resultado;
    }


}
