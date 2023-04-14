package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BancoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.UbigeoEntity;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UbigeoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

public class UbigeoSQLiteDao {

    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<UbigeoSQLiteEntity> listaUbigeoSQLiteEntity;

    public UbigeoSQLiteDao(Context context)
    {
        sqliteController = new SqliteController(context);
    }

    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos desde " +this.getClass().getName());
        bd = sqliteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos desde " +this.getClass().getName());
        sqliteController.close();
    }


    public int addListUbigeo (
            /*String compania_id,
            String fuerzatrabajo_id,
            String usuario_id,
            String code,
            String U_SYP_DEPA,
            String U_SYP_PROV,
            String U_SYP_DIST,
            String U_VIS_Flete*/
            List<UbigeoEntity> ubigeo
    )
    {
        abrir();
        /*ContentValues registro = new ContentValues();
        registro.put("compania_id",compania_id);
        registro.put("fuerzatrabajo_id",fuerzatrabajo_id);
        registro.put("usuario_id",usuario_id);
        registro.put("code",code);
        registro.put("U_SYP_DEPA",U_SYP_DEPA);
        registro.put("U_SYP_PROV",U_SYP_PROV);
        registro.put("U_SYP_DIST",U_SYP_DIST);
        registro.put("U_VIS_Flete",U_VIS_Flete);
        bd.insert("ubigeous",null,registro);*/

        for (int i = 0; i < ubigeo.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id",SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id",SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id",SesionEntity.usuario_id);
            registro.put("code",ubigeo.get(i).getCode());
            registro.put("U_SYP_DEPA",ubigeo.get(i).getU_SYP_DEPA());
            registro.put("U_SYP_PROV",ubigeo.get(i).getU_SYP_PROV());
            registro.put("U_SYP_DIST",ubigeo.get(i).getU_SYP_DIST());
            registro.put("U_VIS_Flete",ubigeo.get(i).getU_VIS_Flete());
            bd.insert("ubigeous",null,registro);
        }


        bd.close();
        return 1;
    }

    public ArrayList<UbigeoSQLiteEntity> ObtenerUbigeo ()
    {
        listaUbigeoSQLiteEntity = new ArrayList<UbigeoSQLiteEntity>();
        UbigeoSQLiteEntity ubigeoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from ubigeous",null);

        while (fila.moveToNext())
        {
            ubigeoSQLiteEntity= new UbigeoSQLiteEntity();
            ubigeoSQLiteEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
            ubigeoSQLiteEntity.setFuerzatrabajo_id (fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
            ubigeoSQLiteEntity.setUsuario_id (fila.getString(fila.getColumnIndex("usuario_id")));
            ubigeoSQLiteEntity.setCode(fila.getString(fila.getColumnIndex("code")));
            ubigeoSQLiteEntity.setU_SYP_DEPA(fila.getString(fila.getColumnIndex("U_SYP_DEPA")));
            ubigeoSQLiteEntity.setU_SYP_PROV(fila.getString(fila.getColumnIndex("U_SYP_PROV")));
            ubigeoSQLiteEntity.setU_SYP_DIST(fila.getString(fila.getColumnIndex("U_SYP_DIST")));
            ubigeoSQLiteEntity.setU_VIS_Flete(fila.getString(fila.getColumnIndex("U_VIS_Flete")));
            listaUbigeoSQLiteEntity.add(ubigeoSQLiteEntity);
        }
        bd.close();
        return listaUbigeoSQLiteEntity;
    }

    public ArrayList<UbigeoSQLiteEntity> ObtenerUbigeoporID (String ubigeo_id)
    {
        listaUbigeoSQLiteEntity = new ArrayList<UbigeoSQLiteEntity>();
        UbigeoSQLiteEntity ubigeoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from ubigeous where code= '"+ubigeo_id+"' ",null);

        while (fila.moveToNext())
        {
            ubigeoSQLiteEntity= new UbigeoSQLiteEntity();
            ubigeoSQLiteEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
            ubigeoSQLiteEntity.setFuerzatrabajo_id (fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
            ubigeoSQLiteEntity.setUsuario_id (fila.getString(fila.getColumnIndex("usuario_id")));
            ubigeoSQLiteEntity.setCode(fila.getString(fila.getColumnIndex("code")));
            ubigeoSQLiteEntity.setU_SYP_DEPA(fila.getString(fila.getColumnIndex("U_SYP_DEPA")));
            ubigeoSQLiteEntity.setU_SYP_PROV(fila.getString(fila.getColumnIndex("U_SYP_PROV")));
            ubigeoSQLiteEntity.setU_SYP_DIST(fila.getString(fila.getColumnIndex("U_SYP_DIST")));
            ubigeoSQLiteEntity.setU_VIS_Flete(fila.getString(fila.getColumnIndex("U_VIS_Flete")));
            listaUbigeoSQLiteEntity.add(ubigeoSQLiteEntity);
        }
        bd.close();
        return listaUbigeoSQLiteEntity;
    }

    public int LimpiarTablaUbigeo ()
    {
        abrir();
        bd.execSQL("delete from ubigeous");
        bd.close();
        return 1;
    }

    public int ObtenerCantidadUbigeo ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from ubigeous",null);

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
