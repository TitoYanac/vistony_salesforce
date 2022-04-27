package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.JSON.CollectionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.StatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoCobranzaEntityResponse;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

public class StatusDispatchSQLite {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity;
    android.content.Context Context;

    public StatusDispatchSQLite(Context context)
    {
        sqliteController = new SqliteController(context);
        Context=context;
    }

    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos desde" + this.getClass().getName() );
        bd = sqliteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos desde" + this.getClass().getName() );
        sqliteController.close();
    }

    public int addStatusDispatch (List<StatusDispatchEntity> statusDispatchEntity)
    {
        abrir();

        for (int i = 0; i < statusDispatchEntity.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id", statusDispatchEntity.get(i).getCompania_id());
            registro.put("fuerzatrabajo_id", statusDispatchEntity.get(i).getFuerzatrabajo_id());
            registro.put("usuario_id", statusDispatchEntity.get(i).getUsuario_id());
            registro.put("typedispatch_id", statusDispatchEntity.get(i).getTypedispatch_id());
            registro.put("reasondispatch_id", statusDispatchEntity.get(i).getReasondispatch_id());
            registro.put("cliente_id", statusDispatchEntity.get(i).getCliente_id());
            registro.put("factura_id", statusDispatchEntity.get(i).getFactura_id());
            registro.put("entrega_id", statusDispatchEntity.get(i).getEntrega_id());
            registro.put("chkrecibido", statusDispatchEntity.get(i).getChkrecibido());
            registro.put("observation", statusDispatchEntity.get(i).getObservation());
            registro.put("foto", statusDispatchEntity.get(i).getFoto());
            registro.put("fecha_registro", statusDispatchEntity.get(i).getFecha_registro());
            registro.put("hora_registro", statusDispatchEntity.get(i).getHora_registro());
            registro.put("fotoGuia", statusDispatchEntity.get(i).getFotoGuia());
            registro.put("latitud", statusDispatchEntity.get(i).getLatitud());
            registro.put("longitud", statusDispatchEntity.get(i).getLongitud());
            registro.put("cliente", statusDispatchEntity.get(i).getCliente());
            registro.put("factura", statusDispatchEntity.get(i).getFactura());
            registro.put("entrega", statusDispatchEntity.get(i).getEntrega());
            registro.put("typedispatch", statusDispatchEntity.get(i).getTypedispatch());
            registro.put("reasondispatch", statusDispatchEntity.get(i).getReasondispatch());
            bd.insert("statusdispatch", null, registro);
        }

        bd.close();
        return 1;
    }

    public ArrayList<StatusDispatchEntity> getListStatusDispatch ()
    {

        ArrayList<StatusDispatchEntity> listStatusDispatchEntity=new ArrayList<>();
        StatusDispatchEntity statusDispatchEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from statusdispatch" +
                            " where (chkrecibido='N' or chkrecibido='0') "
                    ,null);

            while (fila.moveToNext())
            {
                statusDispatchEntity= new StatusDispatchEntity();
                statusDispatchEntity.setCompania_id(fila.getString(0));
                statusDispatchEntity.setFuerzatrabajo_id(fila.getString(1));
                statusDispatchEntity.setUsuario_id (fila.getString(2));
                statusDispatchEntity.setTypedispatch_id (fila.getString(3));
                statusDispatchEntity.setReasondispatch_id (fila.getString(4));
                statusDispatchEntity.setCliente_id (fila.getString(5));
                statusDispatchEntity.setFactura_id (fila.getString(6));
                statusDispatchEntity.setEntrega_id (fila.getString(7));
                statusDispatchEntity.setChkrecibido (fila.getString(8));
                statusDispatchEntity.setObservation (fila.getString(9));
                listStatusDispatchEntity.add(statusDispatchEntity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listStatusDispatchEntity;
    }

    public List<HistoricStatusDispatchEntity> getListStatusDispatchforDate (String Date)
    {

        List<HistoricStatusDispatchEntity> listStatusDispatchEntity=new ArrayList<>();
        HistoricStatusDispatchEntity historicStatusDispatchEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from statusdispatch" +
                            " where (chkrecibido='N' or chkrecibido='0') AND fecha_registro='"+Date+"' "
                    ,null);

            while (fila.moveToNext())
            {
                historicStatusDispatchEntity= new HistoricStatusDispatchEntity();
                //historicStatusDispatchEntity.setCompania_id(fila.getString(0));
                //historicStatusDispatchEntity.setFuerzatrabajo_id(fila.getString(1));
                //historicStatusDispatchEntity.setUsuario_id (fila.getString(2));
                historicStatusDispatchEntity.setTipoDespacho_ID (fila.getString(3));
                historicStatusDispatchEntity.setMotivoDespacho_ID(fila.getString(4));
                historicStatusDispatchEntity.setCliente_ID(fila.getString(5));
                //historicStatusDispatchEntity.setFactura_id(fila.getString(6));
                historicStatusDispatchEntity.setEntrega_ID(fila.getString(7));
                //historicStatusDispatchEntity.setChkrecibido (fila.getString(8));
                historicStatusDispatchEntity.setObservacion(fila.getString(9));
                historicStatusDispatchEntity.setFotoLocal(fila.getString(10));
                historicStatusDispatchEntity.setFotoGuia(fila.getString(13));
                historicStatusDispatchEntity.setCliente(fila.getString(16));
                historicStatusDispatchEntity.setEntrega(fila.getString(18));
                historicStatusDispatchEntity.setFactura(fila.getString(17));
                historicStatusDispatchEntity.setTipoDespacho(fila.getString(19));
                historicStatusDispatchEntity.setMotivoDespacho(fila.getString(20));
                listStatusDispatchEntity.add(historicStatusDispatchEntity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listStatusDispatchEntity;
    }
}
