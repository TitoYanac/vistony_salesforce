package com.vistony.salesforce.View;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Dao.Retrofit.BackupRepository;
import com.vistony.salesforce.Dao.Retrofit.CobranzaRepository;
import com.vistony.salesforce.Dao.Retrofit.HistoricoDepositoUnidadWS;
import com.vistony.salesforce.Dao.SQLite.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaCabeceraSQLite;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Dao.SQLite.VisitaSQLite;
import com.vistony.salesforce.Entity.SQLite.CobranzaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfigSistemaView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfigSistemaView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigSistemaView extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View v;
    private CardView cveliminar,cvsincronizar,createFile,deletecajachica,clearTemp;
    private SharedPreferences preferencia;
    private OnFragmentInteractionListener mListener;
    private CobranzaRepository cobranzaRepository;

    private File fileRuta;
 private UsuarioSQLite usuarioSQLite;
    public ConfigSistemaView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigSistemaView.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigSistemaView newInstance(String param1, String param2) {
        ConfigSistemaView fragment = new ConfigSistemaView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_config_sistema_view, container, false);
        clearTemp=v.findViewById(R.id.clearTemp);
        usuarioSQLite =new UsuarioSQLite(getContext());
        cobranzaRepository = new ViewModelProvider(getActivity()).get(CobranzaRepository.class);
        LinearLayout linear=v.findViewById(R.id.firstFile);

        /*if(fileRuta.exists()) {
            linear.setVisibility(View.GONE);
        }else{
            linear.setVisibility(View.VISIBLE);
        }*/

        deletecajachica=v.findViewById(R.id.deletecajachica);
        /*ArrayList<UsuarioSQLiteEntity> listausuariosqliteentity = new ArrayList<>();
        listausuariosqliteentity= usuarioSQLite.ObtenerUsuarioBlockPay();

        if(listausuariosqliteentity.size()>0){
            if(listausuariosqliteentity.get(0).getChkbloqueopago().equals("0")){
                deletecajachica.setVisibility(View.GONE);
            }else{
                deletecajachica.setVisibility(View.VISIBLE);
            }
        }else{
            deletecajachica.setVisibility(View.VISIBLE);
        }*/
        /*if(SesionEntity.formhabilPrint.equals("Y"))
        {
            deletecajachica.setVisibility(View.VISIBLE);
        }
        else
            {
                deletecajachica.setVisibility(View.GONE);
            }*/

        deletecajachica.setVisibility(View.GONE);

        deletecajachica.setOnClickListener(v -> AlertaEnablePrint().show());


        cveliminar = v.findViewById(R.id.cveliminar);
        cvsincronizar =v.findViewById(R.id.cvsincronizar);
        createFile=v.findViewById(R.id.asdFirstFile);

        cveliminar.setOnClickListener(v -> AlertaEliminarDialog().show());
        cvsincronizar.setOnClickListener(v -> AlertaSincronizarDialog().show());
        clearTemp.setOnClickListener(v->{
            File fdelete = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"OrdenVenta");
            File fdelete2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"Cobranza");
            File fdelete3 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"ResumenDiario");
            File fdelete4 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"KardexPago");
            File fdelete5 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"Quota");
            if (fdelete.isDirectory()){
                String[] children = fdelete.list();
                for (int i = 0; i < children.length; i++)
                {
                    new File(fdelete, children[i]).delete();
                }
            }

            if (fdelete2.isDirectory()){
                String[] children = fdelete2.list();
                for (int i = 0; i < children.length; i++)
                {
                    new File(fdelete2, children[i]).delete();
                }
            }

            if (fdelete3.isDirectory()){
                String[] children = fdelete3.list();
                for (int i = 0; i < children.length; i++)
                {
                    new File(fdelete3, children[i]).delete();
                }
            }

            if (fdelete4.isDirectory()){
                String[] children = fdelete4.list();
                for (int i = 0; i < children.length; i++)
                {
                    new File(fdelete4, children[i]).delete();
                }
            }

            if (fdelete5.isDirectory()){
                String[] children = fdelete5.list();
                for (int i = 0; i < children.length; i++)
                {
                    new File(fdelete5, children[i]).delete();
                }
            }



            Toast.makeText(getContext(), "Proceso terminado...", Toast.LENGTH_SHORT).show();

        });
        /*deletecajachica.setOnClickListener(v -> {
            String validarblockpay="";
            int validar=0;
            ArrayList<UsuarioSQLiteEntity> listausuariosqliteentity1 = new ArrayList<>();
            listausuariosqliteentity1 = usuarioSQLite.ObtenerUsuarioBlockPay();

            for(int i = 0; i< listausuariosqliteentity1.size(); i++)
            {
                validarblockpay= listausuariosqliteentity1.get(i).getChkbloqueopago();
            }
            //Revisar
            // SesionEntity.eliminarBD=validarblockpay;
            switch(validarblockpay)
            {
                case "0":
                    AlertaAccesoCajaChica().show();
                    break;
                case "1":
                    AlertaEliminarDialog().show();
                    break;
            }
        });*/

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 100);
        }

        createFile.setOnClickListener(v ->{
            //AlertaCreateFile().show();

            ProgressDialog progress = new ProgressDialog(getActivity());
            progress.setTitle("Cargando");
            progress.setMessage("Por favor espere...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();

            BackupRepository xd=new BackupRepository();
            xd.sendSqlite(getContext()).observe(getActivity(), data -> {
                progress.dismiss();

                if(data.getClass().getName().equals("java.lang.String")){
                    Toast.makeText(getContext(), data.toString(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Analisis enviado al servidor", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return v;
    }

    private Dialog AlertaAccesoCajaChica() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA!");

        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("Ud. No se encuentra Autorizado para Usar esta Opcion");

        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    // Converting File to Base64.encode String type using Method
    public static String getStringFile(File originalFile) {

       String encodedBase64 = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
            byte[] bytes = new byte[(int) originalFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64=Base64.encodeToString(bytes,Base64.NO_WRAP|Base64.DEFAULT);

           // String text = new String(encodedBase64,"UTF-8");

            return URLEncoder.encode(encodedBase64,"UTF-8");
            //encodedBase64 = new String(Base64.encode(bytes));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        ListenerBackPress.setCurrentFragment("ConfigSistemaView");
        super.onAttach(context);

        fileRuta= new File(Environment.getExternalStorageDirectory(), "RECIBOS");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    */

    @Override
    public void onAttach(Context context) {
        ListenerBackPress.setCurrentFragment("ConfigSistemaView");
        super.onAttach(context);
        fileRuta= new File(Environment.getExternalStorageDirectory(), "RECIBOS");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ListenerBackPress.setTemporaIdentityFragment("regresarMenuConfig");
        mListener = null;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String Tag,Object data);
    }


    public Dialog AlertaEliminarDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_alert_dialog);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA");

        final TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("Se cerrara la aplicacion y tendra que volver a ingresar ¿Seguro de Eliminar la Base de Datos Local?");

        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonExit = (Button) dialog.findViewById(R.id.dialogButtonCancel);

        dialogButton.setText("SI, CONTINUAR");
        dialogButtonExit.setText("CANCELAR");


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogButtonExit.setOnClickListener(v -> dialog.dismiss());

        dialogButton.setOnClickListener(v -> {
            /*ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                ObtenerCobranzaDetallePendienteWS HiloOCDPWS= new ObtenerCobranzaDetallePendienteWS();
                HiloOCDPWS.execute();
                dialog.dismiss();
            }else{
                Alerta("6").show();
            }*/
            if(getCountPendingSend())
            {
                Alerta("5").show();
            }else {

                SqliteController sqliteController = new SqliteController(getContext());
                sqliteController.deleteDatabase(getContext());
                System.exit(0);
            }
            dialog.dismiss();
        });
        return  dialog;
    }


    public boolean getCountPendingSend()
    {
        boolean estado;
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
        CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(getContext());
        OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite=new OrdenVentaCabeceraSQLite(getContext());
        VisitaSQLite visitaSQLite=new VisitaSQLite(getContext());
        UsuarioSQLiteEntity usuarioSQLiteEntity=new UsuarioSQLiteEntity();
        int countCollection,countSalesOrder,countVisit;
        usuarioSQLiteEntity=usuarioSQLite.ObtenerUsuarioSesion();

        countCollection=cobranzaDetalleSQLiteDao.getCountCollectionPendingSend(
                usuarioSQLiteEntity.usuario_id,
                usuarioSQLiteEntity.compania_id);
        countSalesOrder=ordenVentaCabeceraSQLite.getCountSalesOrderPendingSend(
                usuarioSQLiteEntity.usuario_id,
                usuarioSQLiteEntity.compania_id);
        countVisit=visitaSQLite.getCountVisitPendingSend(
                usuarioSQLiteEntity.usuario_id,
                usuarioSQLiteEntity.compania_id);
        Log.e("REOS","ConfigSistemaView-getCountPendingSend-countVisit:"+countVisit);
        Log.e("REOS","ConfigSistemaView-getCountPendingSend-countSalesOrder:"+countSalesOrder);
        Log.e("REOS","ConfigSistemaView-getCountPendingSend-countVisit:"+countVisit);
        if(countVisit>0||countSalesOrder>0||countCollection>0)
        {
            estado=true;
        }else
            {
                estado=false;
            }
        Log.e("REOS","ConfigSistemaView-getCountPendingSend-estado:"+estado);
        return estado;

    }



    public Dialog AlertaSincronizarDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_alert_dialog);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA");

        final TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("Seguro de Sincronizar la Base de Datos Local?");

        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonExit = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        // if button is clicked, close the custom dialog
        dialogButton.setText("SI, SINCRONIZAR");
        dialogButtonExit.setText("CANCELAR");

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogButtonExit.setOnClickListener(v -> dialog.dismiss());

        dialogButton.setOnClickListener(v -> {

            /////////////////////Sincronizar Recibos Pendientes de Depositar\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            cobranzaRepository.SynchronizedepositedPendingCollectionForced(getContext()).observe(getActivity(), data -> {
                Log.e("Jepicame", "=>" + data);


           /* ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                ObtenerHistoricoCobranzaPendiente  TareaHistoricoCobranzaPendiente = new ObtenerHistoricoCobranzaPendiente();
                TareaHistoricoCobranzaPendiente.execute();
            }else{
                Toast.makeText(getContext(), "No hay conexión a internet para sincronzar...", Toast.LENGTH_SHORT).show();
            }*/
                dialog.dismiss();
            });
        });
        return  dialog;
    }

    public Dialog AlertaCreateFile() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_alert_dialog);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA");

        final TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("Ejecuta esta opción cuando helpdesk te indique...");

        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonExit = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        // if button is clicked, close the custom dialog
        dialogButton.setText("EJECUTAR");
        dialogButtonExit.setText("CANCELAR");


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date now = new Date();
                DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

                try {

                    String mPath = Environment.getExternalStorageDirectory().toString() + "/RECIBOS/" + now + ".jpg";

                    // create bitmap screen capture
                    View v1 = getActivity().getWindow().getDecorView().getRootView();
                    v1.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                    v1.setDrawingCacheEnabled(false);

                    boolean isCreada = fileRuta.exists();
                    if(isCreada == false) {
                        isCreada = fileRuta.mkdirs();
                    }

                    File imageFile=null;

                    if(isCreada == true) {
                        imageFile = new File(mPath);
                    }

                    FileOutputStream outputStream = new FileOutputStream(imageFile);
                    int quality = 50;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();

                    Toast.makeText(getContext(), "Carpeta creada...", Toast.LENGTH_SHORT).show();

                } catch (Throwable e) {
                    Log.e("jpcm",""+e);
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        return  dialog;
    }


    public Dialog Alerta(String tipo) {

        String mensaje="";
        if(tipo.equals("1"))
        {
            mensaje="Ud. Cuenta con Depositos Pendientes de Sincronizacion, revisar en Consulta Deposito";
        }
        else if(tipo.equals("2"))
        {
            mensaje="Ud. Cuenta con Recibos Pendiente de Deposito, Depositar todos sus recibos antes de Eliminar Base de Datos";
        }
        else if(tipo.equals("3"))
        {
            mensaje="Ud. Cuenta con Recibos Pendientes de Sincronizacion, revisar en Consulta Cobrado";
        }
        else if(tipo.equals("4"))
        {
            mensaje="Ud. Cuenta con Ordenes de Venta Pendientes de Envio,revisar en Consulta de Orden Venta";
        }else if(tipo.equals("5")){
            mensaje="Ud. Cuenta con recibos/ordenes de Venta/visitas pendientes de envio a SAP, cierre la aplicación y vuelva a ingresar con Conexion a Internet";
        }
        else if(tipo.equals("6")){
            mensaje="Esta opción esta habilitada con internet";
        }

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_alert_dialog_info);

        TextView textTitle = dialog.findViewById(R.id.text_alert_dialog_info);
        textTitle.setText("MENSAJE");

        final TextView textMsj = dialog.findViewById(R.id.textViewMsj_alert_dialog_info);
        textMsj.setText(mensaje);

        ImageView image = (ImageView) dialog.findViewById(R.id.image_alert_dialog_info);

        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK_alert_dialog_info);
        dialogButton.setText("OK");

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogButton.setOnClickListener(v -> dialog.dismiss());
        return  dialog;
    }


    public class ObtenerCobranzaDetallePendienteWS extends AsyncTask<String, Void, Object> {
        @Override
        protected String doInBackground(String... arg1) {
            String resultado="";
            String resultadocompuesto="0";//,tipo="0";
            try {

                //SQLiteController sqLiteController=new SQLiteController(getContext());
                    resultado=ObtenerPendientesEnvioWS();
                    Log.e("Result Jpcm",""+resultado); //0-0

                    String[] sourceSplitemision2= resultado.split("-");
                    resultadocompuesto= sourceSplitemision2[0];
                   // tipo= sourceSplitemision2[1];

                    if(resultadocompuesto.equals("1"))
                    {
                        resultado=ObtenerPendientesEnvioWS();
                    }

            } catch (Exception e){
                resultado="1";
                Log.e("jpcm",""+e);
                e.printStackTrace();
            }
            return resultado;
        }

        protected void onPostExecute(Object result)
        {
            String resultado="";
            resultado=String.valueOf(result);

            String resultadocompuesto="0",tipo="0";
            String[] sourceSplitemision2= resultado.split("-");
            resultadocompuesto= sourceSplitemision2[0];
            tipo= sourceSplitemision2[1];

            if(resultadocompuesto.equals("1"))
            {
                Alerta(tipo).show();
            }else
            {
                SqliteController sqliteController = new SqliteController(getContext());
                sqliteController.deleteDatabase(getContext());
                System.exit(0);

                //Toast.makeText(getContext(), "Base de datos eliminada!!", Toast.LENGTH_SHORT).show();

            }
        }



        private String ObtenerPendientesEnvioWS(){

            String resultadoPendientesEnvioWS="",resultadoPendientesEnvioCabeceraWS="0",resultadoPendientesEnvioDetalleWS="0",resultadoRegistroCabecera="0",resultadoRegistroDetalle="0";
            String tipo="0",compuesto="0";

            //Envia Cabecera Pendiente de Envio
            CobranzaCabeceraSQLiteDao cobranzaCabeceraSQLiteDao = new CobranzaCabeceraSQLiteDao(getContext());
            ArrayList<CobranzaCabeceraSQLiteEntity> listaCobranzaCabeceraSQLiteEntity = new ArrayList<>();

            listaCobranzaCabeceraSQLiteEntity = cobranzaCabeceraSQLiteDao.ObtenerCobranzaCabeceraPendientesEnvioTotalWS(SesionEntity.compania_id, SesionEntity.usuario_id);
            if (!(listaCobranzaCabeceraSQLiteEntity.isEmpty())){

                resultadoPendientesEnvioCabeceraWS="1";
                HistoricoDepositoUnidadWS historicoDepositoUnidadWS=new HistoricoDepositoUnidadWS(getContext());
                for (int j = 0; j < listaCobranzaCabeceraSQLiteEntity.size(); j++)
                {
                    List<CobranzaCabeceraSQLiteEntity> listaleercobranzacabecera = new ArrayList<>();

                    listaleercobranzacabecera = historicoDepositoUnidadWS.getHistoricoDepositoIndividual(
                            SesionEntity.imei,
                            SesionEntity.compania_id,
                            SesionEntity.usuario_id,
                            //listaCobranzaDetalleSQLiteEntity.get(j).getRecibo().toString()
                            listaCobranzaCabeceraSQLiteEntity.get(j).getBanco_id(),
                            listaCobranzaCabeceraSQLiteEntity.get(j).getCobranza_id()
                    );

                    if (listaleercobranzacabecera.isEmpty()) {

                        Alerta("5").show();
                        //MOSTRAR ALERT DE REINGRESO DE APP
/*
                        CobranzaCabeceraWS cobranzaCabeceraWS=new CobranzaCabeceraWS(getContext());
                        resultadoRegistroCabecera=
                                cobranzaCabeceraWS.PostCobranzaCabeceraWS
                                        (
                                            SesionEntity.imei,
                                            "CREATE",
                                            SesionEntity.compania_id,
                                            listaCobranzaCabeceraSQLiteEntity.get(j).getBanco_id(),
                                            listaCobranzaCabeceraSQLiteEntity.get(j).getTipoingreso(),
                                            listaCobranzaCabeceraSQLiteEntity.get(j).getCobranza_id(),
                                            SesionEntity.usuario_id,
                                            listaCobranzaCabeceraSQLiteEntity.get(j).getFechadeposito(),
                                            listaCobranzaCabeceraSQLiteEntity.get(j).getTotalmontocobrado(),
                                            "Pendiente",
                                            "0",
                                            SesionEntity.fuerzatrabajo_id,
                                            listaCobranzaCabeceraSQLiteEntity.get(j).getChkbancarizado(),
                                            listaCobranzaCabeceraSQLiteEntity.get(j).getFechadiferido(),
                                            listaCobranzaCabeceraSQLiteEntity.get(j).getComentarioanulado(),
                                            listaCobranzaCabeceraSQLiteEntity.get(j).getPagodirecto(),
                                            listaCobranzaCabeceraSQLiteEntity.get(j).getPagodirecto()
                                        );


                        if (resultadoRegistroCabecera.equals("1")) {
                            cobranzaCabeceraSQLiteDao.ActualizarCobranzaCabeceraWS(
                                    listaCobranzaCabeceraSQLiteEntity.get(j).getCobranza_id(),
                                    SesionEntity.compania_id,
                                    listaCobranzaCabeceraSQLiteEntity.get(j).getFuerzatrabajo_id(),
                                    resultadoRegistroCabecera);

                        }*/

                    }
                }
            }

            if(resultadoPendientesEnvioCabeceraWS.equals("0")) {

                CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());

                    ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity = new ArrayList<>();
                    listaCobranzaDetalleSQLiteEntity = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetallePendientesEnvioTotalWS(SesionEntity.compania_id, SesionEntity.usuario_id);
                    //Envio de Cobranza Detalle Pendientes a WS
                    if (!(listaCobranzaDetalleSQLiteEntity.isEmpty())) {
                        tipo="3";
                        FormulasController formulasController=new FormulasController(getContext());
                        for (int j = 0; j < listaCobranzaDetalleSQLiteEntity.size(); j++) {

                            List<CobranzaDetalleSQLiteEntity> listaleercobranza = new ArrayList<>();

                            listaleercobranza=formulasController.ObtenerListaConvertidaCobranzaDetalleSQLite(
                                    getContext(),
                                    SesionEntity.imei,
                                    SesionEntity.compania_id,
                                    SesionEntity.usuario_id,
                                    listaCobranzaDetalleSQLiteEntity.get(j).getRecibo()
                            );

                            if (listaleercobranza.isEmpty()) {
                                if (listaCobranzaDetalleSQLiteEntity.get(j).getCobranza_id().equals("1")) {

                                    Alerta("5").show();
                                    /*
                                    ArrayList<CobranzaDetalleSQLiteEntity> ListaCobranzaDetalleSQLiteEntity = new ArrayList<>();
                                    CobranzaDetalleSQLiteEntity cobranzaDetalleSQLiteEntity = new CobranzaDetalleSQLiteEntity();
                                    cobranzaDetalleSQLiteEntity.id = listaCobranzaDetalleSQLiteEntity.get(j).getId();
                                    cobranzaDetalleSQLiteEntity.cobranza_id = listaCobranzaDetalleSQLiteEntity.get(j).getCobranza_id();
                                    cobranzaDetalleSQLiteEntity.cliente_id = listaCobranzaDetalleSQLiteEntity.get(j).getCliente_id();
                                    cobranzaDetalleSQLiteEntity.compania_id = listaCobranzaDetalleSQLiteEntity.get(j).getCompania_id();
                                    cobranzaDetalleSQLiteEntity.documento_id = listaCobranzaDetalleSQLiteEntity.get(j).getDocumento_id();
                                    cobranzaDetalleSQLiteEntity.fechacobranza = listaCobranzaDetalleSQLiteEntity.get(j).getFechacobranza();
                                    cobranzaDetalleSQLiteEntity.importedocumento = listaCobranzaDetalleSQLiteEntity.get(j).getImportedocumento();
                                    cobranzaDetalleSQLiteEntity.saldocobrado = listaCobranzaDetalleSQLiteEntity.get(j).getSaldocobrado();
                                    cobranzaDetalleSQLiteEntity.nuevosaldodocumento = listaCobranzaDetalleSQLiteEntity.get(j).getNuevosaldodocumento();
                                    cobranzaDetalleSQLiteEntity.recibo = listaCobranzaDetalleSQLiteEntity.get(j).getRecibo();
                                    cobranzaDetalleSQLiteEntity.saldodocumento = listaCobranzaDetalleSQLiteEntity.get(j).getSaldodocumento();
                                    cobranzaDetalleSQLiteEntity.chkbancarizado = listaCobranzaDetalleSQLiteEntity.get(j).getChkbancarizado();
                                    cobranzaDetalleSQLiteEntity.motivoanulacion = listaCobranzaDetalleSQLiteEntity.get(j).getMotivoanulacion();
                                    cobranzaDetalleSQLiteEntity.chkqrvalidado = listaCobranzaDetalleSQLiteEntity.get(j).getChkqrvalidado();
                                    cobranzaDetalleSQLiteEntity.banco_id = listaCobranzaDetalleSQLiteEntity.get(j).getBanco_id();
                                    cobranzaDetalleSQLiteEntity.comentario = listaCobranzaDetalleSQLiteEntity.get(j).getComentario();

                                    ListaCobranzaDetalleSQLiteEntity.add(cobranzaDetalleSQLiteEntity);
                                    //resultadoRegistroDetalle = cobranzaDetalleWSDao.enviarRecibo(ListaCobranzaDetalleSQLiteEntity, SesionEntity.imei, SesionEntity.usuario_id, listaCobranzaDetalleSQLiteEntity.get(j).getComentario(), SesionEntity.fuerzatrabajo_id);
                                    resultadoRegistroDetalle=formulasController.EnviarReciboWsRetrofit(
                                            cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(
                                                    ListaCobranzaDetalleSQLiteEntity.get(j).getRecibo(), SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id),
                                            getContext(),
                                            "CREATE",
                                            "0",
                                            "0",
                                            "0",
                                            "0"
                                    );
                                    if (resultadoRegistroDetalle.equals("1")) {
                                        cobranzaDetalleSQLiteDao.ActualizaConexionWSCobranzaDetalle(listaCobranzaDetalleSQLiteEntity.get(j).getRecibo(), SesionEntity.compania_id, SesionEntity.usuario_id, resultadoRegistroDetalle);
                                    }
                                    else{
                                        resultadoPendientesEnvioWS = "1";
                                    }*/

                                }
                            }


                        }


                    } else {
                        resultadoPendientesEnvioWS = "0";
                    }

                /*}else
                {

                    resultadoPendientesEnvioWS = "1";
                    tipo="2";
                }*/

            }
            else{
                resultadoPendientesEnvioWS="1";
                tipo="1";
            }
            //Validacion de Orden de Venta
            ArrayList<String> listaordenventacabecerasqliteentity=new ArrayList<>();
            OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite =new OrdenVentaCabeceraSQLite(getContext());

            listaordenventacabecerasqliteentity= ordenVentaCabeceraSQLite.ObtenerOrdenVentaCabeceraPendientesEnvioWS();
            if(!listaordenventacabecerasqliteentity.isEmpty())
            {
                resultadoPendientesEnvioWS="1";
                tipo="4";
            }

            compuesto=resultadoPendientesEnvioWS+"-"+tipo;

            return compuesto;

        }



    }

    public Dialog AlertaEnablePrint() {

        String mensaje="";
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_dialog_enable_print);

        TextView textTitle = dialog.findViewById(R.id.title);
        textTitle.setText("IMPORTANTE!!!");
        TextView textmsm = dialog.findViewById(R.id.tv_mensaje);
        textmsm.setText("Seleccione el Estado de la Impresora:");

        Switch sw_enanle_print = dialog.findViewById(R.id.sw_enanle_print);

        if(SesionEntity.Print.equals("Y"))
        {
            sw_enanle_print.setChecked(true);
        }else
            {
                sw_enanle_print.setChecked(false);
            }

        sw_enanle_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusSwitch1, statusSwitch2;
                if (sw_enanle_print.isChecked()) {
                    SesionEntity.Print="Y";
                    Toast.makeText(getContext(), "Impresora Habilitada", Toast.LENGTH_LONG).show(); // display the current state for switch's
                }else
                    {
                        SesionEntity.Print="N";
                        Toast.makeText(getContext(), "Impresora DesHabilitada", Toast.LENGTH_LONG).show(); // display the current state for switch's
                }

            }
        });
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setText("ACEPTAR");

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogButton.setOnClickListener(v -> dialog.dismiss());
        return  dialog;
    }
}
