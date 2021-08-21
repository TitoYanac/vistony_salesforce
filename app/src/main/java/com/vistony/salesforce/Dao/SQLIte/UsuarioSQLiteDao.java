package com.vistony.salesforce.Dao.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import java.util.ArrayList;

public class UsuarioSQLiteDao {
    SQLiteController sqLiteController;
    SQLiteDatabase bd;

    public UsuarioSQLiteDao (Context context)
    {
        sqLiteController = new SQLiteController(context);
    }
    public void abrir(){
        bd = sqLiteController.getWritableDatabase();
        Log.i("SQLite", "Se abre conexion sqlite desde "+this.getClass().getName());
    }

    public void cerrar(){
        sqLiteController.close();
    }


    public int InsertaUsuario (
            String compania_id,
            String fuerzatrabajo_id,
            String nombrecompania,
            String nombrefuerzatrabajo,
            String nombreusuario,
            String usuario_id,
            String recibo,
            String online,
            String perfil,
            String bloqueopago,
            String listaPrecios_id_1,
            String listaPrecios_id_2,
            String almacen_id,
            String CogsAcct,
            String U_VIST_CTAINGDCTO,
            String DocumentsOwner,
            String U_VIST_SUCUSU,
            String CentroCosto,
            String UnidadNegocio,
            String LineaProduccion,
            String Impuesto_ID,
            String Impuesto,
            String TipoCambio,
            String U_VIS_CashDscnt

    )
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("compania_id",compania_id);
        registro.put("fuerzatrabajo_id",fuerzatrabajo_id);
        registro.put("nombrecompania",nombrecompania);
        registro.put("nombrefuerzatrabajo",nombrefuerzatrabajo);
        registro.put("nombreusuario",nombreusuario);
        registro.put("usuario_id",usuario_id);
        registro.put("recibo",recibo);
        registro.put("chksesion","0");
        registro.put("online",online);
        registro.put("perfil",perfil);
        registro.put("chkbloqueopago",bloqueopago);
        registro.put("listaPrecios_id_1",listaPrecios_id_1);
        registro.put("listaPrecios_id_2",listaPrecios_id_2);
        registro.put("almacen_id",almacen_id);
        registro.put("CogsAcct",CogsAcct);
        registro.put("U_VIST_CTAINGDCTO",U_VIST_CTAINGDCTO);
        registro.put("DocumentsOwner",DocumentsOwner);
        registro.put("U_VIST_SUCUSU",U_VIST_SUCUSU);
        registro.put("CentroCosto",CentroCosto);
        registro.put("UnidadNegocio",UnidadNegocio);
        registro.put("LineaProduccion",LineaProduccion);
        registro.put("Impuesto_ID",Impuesto_ID);
        registro.put("Impuesto",Impuesto);
        registro.put("TipoCambio",TipoCambio);
        registro.put("U_VIS_CashDscnt",U_VIS_CashDscnt);
        bd.insert("usuario",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public int ActualizaUsuario (String CompanyName, String nombrefuerzatrabajo)
    {
        int resultado=0;
        abrir();
        try {
            ContentValues registro = new ContentValues();
            registro.put("online",1);
            registro.put("chksesion",1);

            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("usuario",registro,"nombrecompania like '%"+CompanyName+"%' and nombrefuerzatrabajo like '%"+nombrefuerzatrabajo+"%'" ,null);

            bd.close();
        }catch (Exception e){
            bd.close();
            Log.e("Error"," UsuarioSQLiteDato "+e.getMessage());
        }

        bd.close();
        return resultado;
    }

    public int LimpiarTablaUsuario ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from usuario ");
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ArrayList<UsuarioSQLiteEntity> ObtenerUsuario ()
    {
        ArrayList<UsuarioSQLiteEntity> listaDDeudaentity = new ArrayList<UsuarioSQLiteEntity>();
        try {


            UsuarioSQLiteEntity usuarioSQLiteEntity;
            abrir();
            Cursor fila = bd.rawQuery("Select * from usuario", null);

            while (fila.moveToNext()) {
                usuarioSQLiteEntity = new UsuarioSQLiteEntity();
                usuarioSQLiteEntity.setCompania_id(fila.getString(0));
                usuarioSQLiteEntity.setFuerzatrabajo_id(fila.getString(1));
                usuarioSQLiteEntity.setNombrecompania(fila.getString(2));
                usuarioSQLiteEntity.setNombrefuerzatrabajo(fila.getString(3));
                usuarioSQLiteEntity.setNombreUsuario(fila.getString(4));
                usuarioSQLiteEntity.setUsuario_id(fila.getString(5));
                usuarioSQLiteEntity.setRecibo(fila.getString(6));
                usuarioSQLiteEntity.setChksesion(fila.getString(7));
                usuarioSQLiteEntity.setOnline(fila.getString(8));
                usuarioSQLiteEntity.setPerfil(fila.getString(9));
                usuarioSQLiteEntity.setChkbloqueopago(fila.getString(10));
                usuarioSQLiteEntity.setListaprecio_id_1(fila.getString(11));
                usuarioSQLiteEntity.setListaprecio_id_2(fila.getString(12));
                usuarioSQLiteEntity.setAlmacen_id(fila.getString(14));
                usuarioSQLiteEntity.setCogsacct(fila.getString(15));
                usuarioSQLiteEntity.setU_vist_ctaingdcto(fila.getString(16));
                usuarioSQLiteEntity.setDocumentsowner(fila.getString(17));
                usuarioSQLiteEntity.setU_VIST_SUCUSU(fila.getString(18));
                usuarioSQLiteEntity.setCentroCosto(fila.getString(19));
                usuarioSQLiteEntity.setUnidadNegocio(fila.getString(20));
                usuarioSQLiteEntity.setLineaProduccion(fila.getString(21));
                usuarioSQLiteEntity.setImpuesto_ID(fila.getString(22));
                usuarioSQLiteEntity.setImpuesto(fila.getString(23));
                usuarioSQLiteEntity.setTipoCambio(fila.getString(24));
                usuarioSQLiteEntity.setU_VIS_CashDscnt(fila.getString(25));
                listaDDeudaentity.add(usuarioSQLiteEntity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        bd.close();
        return listaDDeudaentity;

    }

    public ArrayList<UsuarioSQLiteEntity> ObtenerUsuarioSesion ()
    {
        ArrayList<UsuarioSQLiteEntity> listaDDeudaentity = new ArrayList<UsuarioSQLiteEntity>();
        try {

            UsuarioSQLiteEntity usuarioSQLiteEntity;
            abrir();
            Cursor fila = bd.rawQuery("Select * from usuario where chksesion=? LIMIT 1;",new String [] {"1"});

            if (fila.moveToFirst()) {
                do {
                    usuarioSQLiteEntity = new UsuarioSQLiteEntity();
                    usuarioSQLiteEntity.setCompania_id(fila.getString(0));
                    usuarioSQLiteEntity.setFuerzatrabajo_id(fila.getString(1));
                    usuarioSQLiteEntity.setNombrecompania(fila.getString(2));
                    usuarioSQLiteEntity.setNombrefuerzatrabajo(fila.getString(3));
                    usuarioSQLiteEntity.setNombreUsuario(fila.getString(4));
                    usuarioSQLiteEntity.setUsuario_id(fila.getString(5));
                    usuarioSQLiteEntity.setRecibo(fila.getString(6));
                    usuarioSQLiteEntity.setChksesion(fila.getString(7));
                    usuarioSQLiteEntity.setOnline(fila.getString(8));
                    usuarioSQLiteEntity.setPerfil(fila.getString(9));
                    usuarioSQLiteEntity.setChkbloqueopago(fila.getString(10));
                    usuarioSQLiteEntity.setListaprecio_id_1(fila.getString(11));
                    usuarioSQLiteEntity.setListaprecio_id_2(fila.getString(12));
                    usuarioSQLiteEntity.setAlmacen_id(fila.getString(14));
                    usuarioSQLiteEntity.setCogsacct(fila.getString(15));
                    usuarioSQLiteEntity.setU_vist_ctaingdcto(fila.getString(16));
                    usuarioSQLiteEntity.setDocumentsowner(fila.getString(17));
                    usuarioSQLiteEntity.setU_VIST_SUCUSU(fila.getString(18));
                    usuarioSQLiteEntity.setCentroCosto(fila.getString(19));
                    usuarioSQLiteEntity.setUnidadNegocio(fila.getString(20));
                    usuarioSQLiteEntity.setLineaProduccion(fila.getString(21));
                    usuarioSQLiteEntity.setImpuesto_ID(fila.getString(22));
                    usuarioSQLiteEntity.setImpuesto(fila.getString(23));
                    usuarioSQLiteEntity.setTipoCambio(fila.getString(24));
                    usuarioSQLiteEntity.setU_VIS_CashDscnt(fila.getString(25));
                    listaDDeudaentity.add(usuarioSQLiteEntity);
                } while (fila.moveToNext());
            }


        }catch (Exception e){
           Log.e("Error","UsuarioSQLiteDato "+e.getMessage());
        }

        bd.close();
        return listaDDeudaentity;

    }

    public int ActualizaRecibo (String recibo)
    {

        int resultado=0;
        String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("recibo",recibo);
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("usuario",registro,"",null);

            /*Cursor fila = bd.rawQuery(
                    "update cobranzadetalle set cobranza_id = '"+cobranza_id+ "'" +
                            //+ ",chkdepositado = '"+chkdepositado+"'"+
                            " where recibo= '"+recibo+"'"
                            //+ " and compania_id= '"+compania_id+"'"
                    ,null);
            */

            //resultado=1;
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }

        return  resultado;

    }


    public ArrayList<UsuarioSQLiteEntity> ObtenerUsuarioBlockPay ()
    {
        ArrayList<UsuarioSQLiteEntity> listaDDeudaentity = new ArrayList<UsuarioSQLiteEntity>();
        try {


            UsuarioSQLiteEntity usuarioSQLiteEntity;
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select chkbloqueopago from usuario where chksesion='1'", null);

            while (fila.moveToNext()) {
                usuarioSQLiteEntity = new UsuarioSQLiteEntity();
                usuarioSQLiteEntity.setChkbloqueopago(fila.getString(0));
                listaDDeudaentity.add(usuarioSQLiteEntity);
            }

            bd.close();
        }catch (Exception e)
        {
            bd.close();
            e.printStackTrace();
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaDDeudaentity;

    }

    public ArrayList<String> ObtenerUsuario(String profile,String compania){
        try {
            abrir();

            Cursor fila = bd.rawQuery("SELECT nombreusuario AS usuario FROM usuario WHERE nombrecompania ='"+compania+"' AND perfil='"+profile+"';", null);
            ArrayList<String> usuarios = new  ArrayList<String>();

            if (fila.moveToFirst()) {
                do {
                    usuarios.add(fila.getString(fila.getColumnIndex("usuario")));
                } while (fila.moveToNext());
            }

            bd.close();
            return usuarios;

        }catch (Exception e){
            bd.close();
            return null;
        }
    }

    public ArrayList<String> ObtenerPerfiles(){
        try {
            abrir();

            Cursor fila = bd.rawQuery("SELECT DISTINCT(perfil) AS perfil FROM usuario;", null);
            ArrayList<String> perfiles = new  ArrayList<String>();

            if (fila.moveToFirst()) {
                if (fila.moveToFirst()) {
                    do {
                        perfiles.add(fila.getString(fila.getColumnIndex("perfil")));
                    } while (fila.moveToNext());
                }
            }

            bd.close();
            return perfiles;

        }catch (Exception e){
            bd.close();
            return null;
        }
    }

    public ArrayList<String> ObtenerCompania(String profile){
        try {
            abrir();
            Cursor fila = bd.rawQuery("SELECT DISTINCT(nombrecompania) AS compania FROM usuario WHERE perfil ='"+profile+"';", null);
            ArrayList<String> compania = new  ArrayList<String>();

            if (fila.moveToFirst()) {
                if (fila.moveToFirst()) {
                    do {
                        compania.add(fila.getString(fila.getColumnIndex("compania")));
                    } while (fila.moveToNext());
                }
            }
            bd.close();
            return compania;
        }catch (Exception e){
            bd.close();
            return null;
        }
    }

    public String ObtenerListaPrecioUsuario(String contado)
    {
        UsuarioSQLiteEntity usuarioSQLiteEntity;
        abrir();
        String codigolistaprecio="";
        Cursor fila=null;
        try
        {

            if(contado.equals("1"))
            {
                fila = bd.rawQuery(
                        "Select listaPrecios_id_1 from usuario where chksesion='1'", null);
            }
            else
                {
                 fila = bd.rawQuery(
                            "Select listaPrecios_id_2 from usuario where chksesion='1'", null);
                }

            while (fila.moveToNext()) {
                codigolistaprecio=fila.getString(0);
            }

            bd.close();
        }catch (Exception e)
        {
            bd.close();
            e.printStackTrace();
        }

        return codigolistaprecio;
    }

}
