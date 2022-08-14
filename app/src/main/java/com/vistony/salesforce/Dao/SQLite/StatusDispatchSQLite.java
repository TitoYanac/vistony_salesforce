package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.ImageCameraController;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.StatusDispatchEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;

import java.io.ByteArrayOutputStream;
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
            registro.put("typedispatch_id", statusDispatchEntity.get(i).getDelivered());
            registro.put("reasondispatch_id", statusDispatchEntity.get(i).getReturnReason());
            registro.put("cliente_id", statusDispatchEntity.get(i).getCliente_id());
            registro.put("factura_id", statusDispatchEntity.get(i).getFactura_id());
            registro.put("entrega_id", statusDispatchEntity.get(i).getEntrega_id());
            registro.put("chkrecibido", statusDispatchEntity.get(i).getChkrecibido());
            registro.put("observation", statusDispatchEntity.get(i).getComments());
            registro.put("foto", statusDispatchEntity.get(i).getFoto());
            registro.put("fecha_registro", statusDispatchEntity.get(i).getFecha_registro());
            registro.put("hora_registro", statusDispatchEntity.get(i).getHora_registro());
            registro.put("fotoGuia", statusDispatchEntity.get(i).getPhotoDocument());
            registro.put("latitud", statusDispatchEntity.get(i).getLatitud());
            registro.put("longitud", statusDispatchEntity.get(i).getLongitud());
            registro.put("cliente", statusDispatchEntity.get(i).getCliente());
            registro.put("factura", statusDispatchEntity.get(i).getFactura());
            registro.put("entrega", statusDispatchEntity.get(i).getEntrega());
            registro.put("typedispatch", statusDispatchEntity.get(i).getTypedispatch());
            registro.put("reasondispatch", statusDispatchEntity.get(i).getReasondispatch());
            registro.put("control_id", statusDispatchEntity.get(i).getDocEntry());
            registro.put("item_id", statusDispatchEntity.get(i).getLineId());
            registro.put("domembarque_id", statusDispatchEntity.get(i).getAddress());
            registro.put("checkintime", statusDispatchEntity.get(i).getCheckintime());
            registro.put("checkouttime", statusDispatchEntity.get(i).getCheckouttime());
            registro.put("chk_timestatus", statusDispatchEntity.get(i).getChk_timestatus());
            bd.insert("statusdispatch", null, registro);
        }


        bd.close();
        return 1;
    }

    public ArrayList<StatusDispatchEntity> getListStatusDispatch (Context context)
    {

        ArrayList<StatusDispatchEntity> listStatusDispatchEntity=new ArrayList<>();
        StatusDispatchEntity statusDispatchEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select  * from statusdispatch" +
                            " where (chkrecibido='N' or chkrecibido='0')  "
                    ,null);

            while (fila.moveToNext())
            {
                byte[] byteArray=null,byteArray2=null;
                String encoded = null,encoded2 = null;
                Bitmap bitmap= null,bitmap2= null;
                ImageCameraController imageCameraController=new ImageCameraController();
                statusDispatchEntity= new StatusDispatchEntity();
                //statusDispatchEntity.setCompania_id(fila.getString(0));
                //statusDispatchEntity.setFuerzatrabajo_id(fila.getString(1));
                //statusDispatchEntity.setUsuario_id (fila.getString(2));
                statusDispatchEntity.setDelivered(fila.getString(3));
                statusDispatchEntity.setReturnReason(fila.getString(4));
                //statusDispatchEntity.setCliente_id (fila.getString(5));
                //statusDispatchEntity.setFactura_id (fila.getString(6));
                statusDispatchEntity.setEntrega_id(fila.getString(7));
                //statusDispatchEntity.setChkrecibido (fila.getString(8));
                statusDispatchEntity.setComments(fila.getString(9));
                /*byteArray=imageCameraController.getImageSDtoByte(context, fila.getString(10));
                bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                if (bitmap != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                    byteArray = stream.toByteArray();
                    encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                }
                //statusDispatchEntity.setPhotoStore(encoded);
                byteArray2=imageCameraController.getImageSDtoByte(context, fila.getString(13));
                bitmap2 = BitmapFactory.decodeByteArray(byteArray2, 0, byteArray2.length);

                if (bitmap2 != null) {
                    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 10, stream2);
                    byteArray2 = stream2.toByteArray();
                    encoded2 = Base64.encodeToString(byteArray2, Base64.DEFAULT);
                }*/
                encoded=imageCameraController.getBASE64(fila.getString(10));
                encoded2=imageCameraController.getBASE64(fila.getString(13));
                //statusDispatchEntity.setPhotoDocument(encoded2);
                String Base64PhotoLocal = encoded.replace("\n", "");
                //String Base64PhotoLocal = fila.getString(14).replace("\u003d", "=");
                String Base64PhotoLocal2 = Base64PhotoLocal.replace("'\u003d'", "=");
                String Base64PhotoGuia = encoded2.replace("\n", "");
                //String Base64PhotoGuia = fila.getString(15).replace("\u003d", "=");
                String Base64PhotoGuia2 = Base64PhotoGuia.replace("'\u003d'", "=");
                statusDispatchEntity.setPhotoStore(Base64PhotoLocal2);
                statusDispatchEntity.setPhotoDocument(Base64PhotoGuia2);
                statusDispatchEntity.setDocEntry(fila.getString(21));
                //statusDispatchEntity.setDocEntry("3");
                statusDispatchEntity.setLineId(fila.getString(22));
                //statusDispatchEntity.setLineId("1");
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
                            " where " +
                            //"(chkrecibido='N' or chkrecibido='0') AND" +
                            " fecha_registro='"+Date+"' "
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
                historicStatusDispatchEntity.setChk_Recibido(fila.getString(8));
                historicStatusDispatchEntity.setObservacion(fila.getString(9));
                historicStatusDispatchEntity.setFotoLocal(fila.getString(10));
                historicStatusDispatchEntity.setFotoGuia(fila.getString(13));
                historicStatusDispatchEntity.setCliente(fila.getString(16));
                historicStatusDispatchEntity.setEntrega(fila.getString(18));
                historicStatusDispatchEntity.setFactura(fila.getString(17));
                historicStatusDispatchEntity.setTipoDespacho(fila.getString(19));
                //historicStatusDispatchEntity.setMotivoDespacho(fila.getString(20));
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

    public int UpdateResultStatusDispatch (String control_id,String item_id){
        int status=0;
        try {
            abrir();
            ContentValues registro = new ContentValues();
            registro.put("chkrecibido","1");
            bd.update("statusdispatch",registro,"control_id=? and item_id=?",new String[]{control_id,item_id});
            status=1;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }
        return status;
    }

    public int UpdatePruebaJSON (String json,String Entrega_id){
        int status=0;

        try {
            abrir();

            ContentValues registro = new ContentValues();
            registro.put("reasondispatch",json);

            bd.update("statusdispatch",registro,"entrega_id=?",new String[]{Entrega_id});
            status=1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }
        return status;
    }

    public int UpdateTimeStatusDispatch (String cardcode,String address,String timeini,String timefin){
        int status=0;
        try {
            abrir();
            ContentValues registro = new ContentValues();
            registro.put("checkintime",timeini);
            registro.put("checkouttime",timefin);
            bd.update("statusdispatch",registro,"domembarque_id=? and cliente_id=?",new String[]{address,cardcode});
            status=1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }
        return status;
    }


    public ArrayList<StatusDispatchEntity> getListStatusDispatchTime (Context context)
    {

        ArrayList<StatusDispatchEntity> listStatusDispatchEntity=new ArrayList<>();
        StatusDispatchEntity statusDispatchEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select  * from statusdispatch" +
                            " where (chk_timestatus='N' or chk_timestatus='0') and  checkintime<>'0' "
                    ,null);

            while (fila.moveToNext())
            {
                statusDispatchEntity= new StatusDispatchEntity();
                statusDispatchEntity.setDocEntry(fila.getString(21));
                //statusDispatchEntity.setDocEntry("3");
                statusDispatchEntity.setLineId(fila.getString(22));
                //statusDispatchEntity.setLineId("1");
                statusDispatchEntity.setCheckintime(fila.getString(24));
                statusDispatchEntity.setCheckouttime(fila.getString(25));
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

    public int UpdateResultStatusDispatchTime (String control_id,String item_id){
        int status=0;
        try {
            abrir();
            ContentValues registro = new ContentValues();
            registro.put("chk_timestatus","1");
            bd.update("statusdispatch",registro,"control_id=? and item_id=?",new String[]{control_id,item_id});
            status=1;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }
        return status;
    }


}
