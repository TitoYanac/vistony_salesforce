package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;

import java.util.ArrayList;

public class VisitaSQLiteDAO {
    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<VisitaSQLiteEntity> listaVisitaSQLiteEntity;

    public VisitaSQLiteDAO(Context context)
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
        registro.put("cliente_id",visita.getCliente_id());
        registro.put("direccion_id",visita.getDireccion_id());
        registro.put("fecha_registro",visita.getFecha_registro());
        registro.put("hora_registro",visita.getHora_registro());
        registro.put("zona_id",visita.getZona_id());
        registro.put("fuerzatrabajo_id",visita.getFuerzatrabajo_id());
        registro.put("usuario_id",visita.getUsuario_id());
        registro.put("tipo",visita.getTipo());
        registro.put("motivo",visita.getMotivo());
        registro.put("observacion",visita.getObservacion());
        registro.put("chkenviado",visita.getChkenviado());
        registro.put("chkrecibido",visita.getChkrecibido());
        registro.put("latitud",visita.getLatitud());
        registro.put("longitud",visita.getLongitud());

        bd.insert("visita",null,registro);
        bd.close();
        return 1;
    }

    public void updateEnviado(@NonNull String fecha,@NonNull String user_id){
        abrir();
        bd.execSQL("UPDATE visita SET enviado='1' WHERE fecha='"+fecha+"' AND user_id='"+user_id+"';");
        bd.close();
    }

    public ArrayList<VisitaSQLiteEntity> ObtenerVisitas ()
    {
        listaVisitaSQLiteEntity = new ArrayList<VisitaSQLiteEntity>();
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from visita where chkrecibido='0' ",null);

        while (fila.moveToNext()){

            VisitaSQLiteEntity visita=new VisitaSQLiteEntity();

            visita.setCompania_id(fila.getString(0));
            visita.setCliente_id(fila.getString(1));
            visita.setDireccion_id(fila.getString(2));
            visita.setFecha_registro(fila.getString(3));
            visita.setHora_registro(fila.getString(4));
            visita.setZona_id(fila.getString(5));
            visita.setFuerzatrabajo_id(fila.getString(6));
            visita.setUsuario_id(fila.getString(7));
            visita.setTipo(fila.getString(8));
            visita.setMotivo(fila.getString(9));
            visita.setObservacion(fila.getString(10));
            visita.setChkenviado(fila.getString(11));
            visita.setChkrecibido(fila.getString(12));
            visita.setLatitud(fila.getString(13));
            visita.setLongitud(fila.getString(14));
            listaVisitaSQLiteEntity.add(visita);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
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
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }
    public int ActualizaEstadoWSVisita (
            String cliente_id,
            String direccion_id,
            String compania_id,
            String fuerzatrabajo_id,
            String fecharegistro,
            String horaregistro,
            String tipo,
            String motivo

    )
    {
        int resultado=0;
        abrir();
        try {

            ContentValues registro = new ContentValues();

            registro.put("chkrecibido","1");
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("visita",registro,"compania_id='"+compania_id+"'"+" and cliente_id='"+cliente_id+"' and hora_registro='"+horaregistro+"' " +
                    "and direccion_id='"+direccion_id+"' and  fuerzatrabajo_id='"+fuerzatrabajo_id+"' and fecha_registro='"+fecharegistro+"' and  tipo='"+tipo+"' and  motivo='"+motivo+"'" ,null);


            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }

        bd.close();
        return resultado;
    }
}
