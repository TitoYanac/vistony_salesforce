package com.vistony.salesforce.View;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
/*
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.drawable.DrawableCompat;
*/
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.ListaClienteDetalleAdapter;
import com.vistony.salesforce.Controller.Adapters.ListaCobranzaCabeceraAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Controller.Utilitario.Utilitario;
import com.vistony.salesforce.Dao.Retrofit.DepositoRepository;
import com.vistony.salesforce.Dao.Retrofit.CobranzaRepository;
import com.vistony.salesforce.Dao.Retrofit.HeaderDispatchSheetRepository;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.Adapters.ListaCobranzaCabeceraDao;
import com.vistony.salesforce.Dao.SQLite.HeaderDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.SQLite.BancoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteDetalleEntity;
import com.vistony.salesforce.Entity.Adapters.ListaConsDepositoEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CobranzaCabeceraView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CobranzaCabeceraView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CobranzaCabeceraView extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static Spinner spnbanco;
    static Spinner spntipo;
    TextView txtfecha,txtsumacobrado,tv_fechacobrocheque_edit,tv_fechacobrocheque;
    View v;
    public static EditText etgrupo;
    List<String> listabanco;
    static BancoSQLite bancoSQLite;
    static List <String> Nombresbanco;
    static ArrayAdapter<String> Combonombresbanco;
    static List<BancoSQLiteEntity> listabancosqliteentity;
    static ObtenerTareaBancos obtenerTareaBancos;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SimpleDateFormat dateFormat,dataFormatToday;
    private OnFragmentInteractionListener mListener;
    Date date;
    String fecha,tipo,bancarizado,fechadiferida,depositodirecto;
    ListaClienteDetalleAdapter listaClienteDetalleAdapter;
    ListView listViewCobranzaCabecera;
    ListaClienteDetalleEntity listaClienteDetalleEntity;
    ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleEntity;
    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao;
    ListaCobranzaCabeceraAdapter listaCobranzaCabeceraAdapter;
    FloatingActionButton fab,fab2;
    static String TAG_1 = "text";
    static ArrayList<ListaConsDepositoEntity> listaConsDepositoAdapterFragment;
    String Banco;
    String banco="";
    static ObtenerListaCobranzas obtenerListaCobranzas;
    ObtenerListaCobranzas obtenerListaCobranza;
    static ObtenerListaCobranzas obtenerLista;
    CobranzaCabeceraSQLiteDao cobranzaCabeceraSQLiteDao;
    public static MenuItem abrir,guardar_deposito,agregar_foto_deposito;
    ArrayList<String> ListaTipo;
    ImageButton imv_calendario_cheque;
    private  int diadespacho,mesdespacho,anodespacho,hora,minutos,diadespacho2,mesdespacho2,anodespacho2;
    private static DatePickerDialog oyenteSelectorFecha3;
    static CheckBox chkbancarizado,chkdepositodirecto,chk_check;
    static ArrayList<ListaConsDepositoEntity> listaAdd= new ArrayList<ListaConsDepositoEntity>();
    String Grupo="",Sumacobrado="";
    BigDecimal sumacobrado;
    public static Menu menu_variable;
    private final int  MY_PERMISSIONS_REQUEST_CAMERA=0;
    String mCurrentPhotoPath="";
    private final int REQUEST_IMAGE_CAPTURE =1;
    public static int estado=0;
    private ProgressDialog pd;
    private DepositoRepository depositoRepository;
    private CobranzaRepository cobranzaRepository;
    Spinner spn_control_despacho_id;
    ImageButton imb_consultar_codigo_control;
    TextView tv_fecha_hoja_despacho;
    static int bancarizados=0,depositodirectos=0,count_check=0;
    static Context context;
    String calendar;
    static Activity activity;

    public CobranzaCabeceraView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CobranzaCabeceraView.
     */
    // TODO: Rename and change types and number of parameters
    public static CobranzaCabeceraView newInstance(String param1, String param2) {
        CobranzaCabeceraView fragment = new CobranzaCabeceraView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static CobranzaCabeceraView newInstancia (Object objeto){
        bancarizados=0;
        depositodirectos=0;
        count_check=0;
        CobranzaCabeceraView cobranzaCabeceraView = new CobranzaCabeceraView();
        ArrayList<String> Listado = new ArrayList<String>();
        Bundle b = new Bundle();
        ArrayList<ListaConsDepositoEntity> Lista = (ArrayList<ListaConsDepositoEntity>) objeto;
        Lista.size();
        b.putSerializable(TAG_1,Lista);

        cobranzaCabeceraView.setArguments(b);
        listaConsDepositoAdapterFragment= (ArrayList<ListaConsDepositoEntity>) objeto;
        //int p=0;

        for(int k=0;k<Lista.size();k++)
        {
            if(Lista.get(k).getTv_txtbancarizado().equals("Y"))
            {
                bancarizados++;
            }
            if(Lista.get(k).getTv_txtpagodirecto().equals("Y"))
            {
                depositodirectos++;
            }
            if(Lista.get(k).getCollectioncheck().equals("Y"))
            {
                count_check++;
            }
            Log.e("REOS","CobranzaCabeceraView-newInstancia-bancarizados"+bancarizados);
            Log.e("REOS","CobranzaCabeceraView-newInstancia-depositodirectos"+depositodirectos);
            Log.e("REOS","CobranzaCabeceraView-newInstancia-count_check"+count_check);
        }

        if(bancarizados>0)
        {

            chkbancarizado.setChecked(true);
            spntipo.setEnabled(false);

        }
        if(depositodirectos>0)
        {
            chkdepositodirecto.setChecked(true);
        }
        if(count_check>0)
        {
            chk_check.setChecked(true);
        }

        obtenerListaCobranzas.execute();
        updateTypeDeposit();

        obtenerTareaBancos = new ObtenerTareaBancos();
        obtenerTareaBancos.execute();
        return cobranzaCabeceraView;

    }

    public void Lista ()
    {
        obtenerListaCobranza = new ObtenerListaCobranzas();
    }

    public static CobranzaCabeceraView newInstanciaAdd(Object object) {
        CobranzaCabeceraView fragment = new CobranzaCabeceraView();
        //listaConsDepositoAdapterFragment.add((ListaConsDepositoEntity) object);

        listaAdd=(ArrayList<ListaConsDepositoEntity>) object;

        for(int i=0;i<listaAdd.size();i++)
        {
            ListaConsDepositoEntity listaConsDepositoEntity= new ListaConsDepositoEntity();
            listaConsDepositoEntity.cliente_id=listaAdd.get(i).getCliente_id();
            listaConsDepositoEntity.nombrecliente=listaAdd.get(i).getNombrecliente();
            listaConsDepositoEntity.recibo=listaAdd.get(i).getRecibo();
            listaConsDepositoEntity.documento_id=listaAdd.get(i).getDocumento_id();
            listaConsDepositoEntity.nrodocumento=listaAdd.get(i).getNrodocumento();
            listaConsDepositoEntity.fechacobranza=listaAdd.get(i).getFechacobranza();
            listaConsDepositoEntity.importe=listaAdd.get(i).getImporte();
            listaConsDepositoEntity.saldo=listaAdd.get(i).getSaldo();
            listaConsDepositoEntity.cobrado=listaAdd.get(i).getCobrado();
            listaConsDepositoEntity.nuevosaldo=listaAdd.get(i).getNuevosaldo();
            listaConsDepositoEntity.checkbox=listaAdd.get(i).isCheckbox();
            listaConsDepositoAdapterFragment.add(listaConsDepositoEntity);
        }

        obtenerLista.execute();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=getActivity();
        listabanco = new ArrayList<>();
        bancoSQLite = new BancoSQLite(getContext());
        cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
        Nombresbanco = new ArrayList<>();
        listabancosqliteentity = new ArrayList<BancoSQLiteEntity>();
        listaCobranzaDetalleEntity =  new ArrayList<CobranzaDetalleSQLiteEntity>();
        listaConsDepositoAdapterFragment =  new ArrayList<ListaConsDepositoEntity>();
        //listaClienteDetalleAdapter.ArraylistaClienteDetalleEntity.size();
        dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        //dataFormatToday = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        ListaTipo = new ArrayList<String>();
        context=getContext();
        obtenerLista =new ObtenerListaCobranzas();
        date = new Date();

        fecha =dateFormat.format(date);
        obtenerListaCobranzas = new ObtenerListaCobranzas();
         cobranzaCabeceraSQLiteDao= new CobranzaCabeceraSQLiteDao(getContext());

        if (getArguments() != null) {
            //abrir.setEnabled(true);
            guardar_deposito.setEnabled(true);
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            listaConsDepositoAdapterFragment= (ArrayList<ListaConsDepositoEntity>)getArguments().getSerializable(TAG_1);
            //listaConsDepositoAdapterFragment.size();
            obtenerListaCobranzas = new ObtenerListaCobranzas();
            obtenerListaCobranzas.execute();
        }

        estado=0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_cobranza_cabecera_view, container, false);
        getActivity().setTitle("Depósito");
        spnbanco=(Spinner) v.findViewById(R.id.spnbanco);
        tv_fechacobrocheque_edit = (TextView) v.findViewById(R.id.tv_fechacobrocheque_edit);
        spnbanco.setEnabled(false);
        spnbanco.setClickable(false);
        etgrupo = (EditText) v.findViewById(R.id.etgrupo);
        txtfecha=(TextView) v.findViewById(R.id.txtfecha);
        txtsumacobrado=(TextView) v.findViewById(R.id.txtsumacobrado);
        listViewCobranzaCabecera = (ListView) v.findViewById(R.id.listViewCobranzaCabecera);
        spntipo = (Spinner)v.findViewById(R.id.spntipo);
        imv_calendario_cheque = (ImageButton) v.findViewById(R.id.imv_calendario_cheque);
        imv_calendario_cheque.setOnClickListener(this);
        tv_fechacobrocheque = (TextView) v.findViewById(R.id.tv_fechacobrocheque);
        chkbancarizado = (CheckBox) v.findViewById(R.id.chkbancarizado);
        chkdepositodirecto = (CheckBox) v.findViewById(R.id.chkdepositodirecto);
        chk_check = (CheckBox) v.findViewById(R.id.chk_check);

        imb_consultar_codigo_control= (ImageButton)  v.findViewById(R.id.imb_consultar_codigo_control);
        fechadiferida="19000101";
        if(!SesionEntity.perfil_id.equals("CHOFER"))
        {
            if(BuildConfig.FLAVOR.equals("espania"))
            {

            }else {
                imb_consultar_codigo_control.setVisibility(View.GONE);
            }
        }
        String [] valores = null;
        /*if(BuildConfig.FLAVOR.equals("chile"))
        {
            valores = new String[]{"Deposito Efectivo","Cheque Diferido","Cheque Dia"};
        }else {
            valores =  new String[]{"Deposito","Cheque"};
        }*/
        valores =  new String[]{"Deposito","Cheque"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, valores);

        spntipo.setAdapter(adapter);
        txtfecha.setText(fecha);

        //tv_fechacobrocheque_edit.setText(dataFormatToday.format(date));
        fab = (FloatingActionButton) v.findViewById(R.id.fabagregar);

        if(listaConsDepositoAdapterFragment.isEmpty())
        {
            setHasOptionsMenu(true);
        }
        spntipo.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        if(position==0)
                        {
                            tipo="Deposito";
                            imv_calendario_cheque.setVisibility(View.INVISIBLE);
                            tv_fechacobrocheque_edit.setVisibility(View.INVISIBLE);
                            tv_fechacobrocheque.setVisibility(View.INVISIBLE);

                        }
                        if(position==1)
                        {
                            tipo="Cheque";
                            tv_fechacobrocheque_edit.setText(Induvis.getDate(BuildConfig.FLAVOR,dateFormat.format(date)));
                            fechadiferida=dateFormat.format(date);
                            tv_fechacobrocheque_edit.setVisibility(View.VISIBLE);
                            tv_fechacobrocheque.setVisibility(View.VISIBLE);
                            imv_calendario_cheque.setVisibility(View.VISIBLE);
                            Utilitario.enableTextView(tv_fechacobrocheque_edit);
                            Utilitario.enableTextView(tv_fechacobrocheque);
                            Utilitario.enableImageButtton(imv_calendario_cheque,getContext());
                        }
                        if(position==2)
                        {
                            tipo="ChequeDia";
                            tv_fechacobrocheque_edit.setText(Induvis.getDate(BuildConfig.FLAVOR,dateFormat.format(date)));
                            fechadiferida=dateFormat.format(date);
                            tv_fechacobrocheque_edit.setVisibility(View.VISIBLE);
                            tv_fechacobrocheque.setVisibility(View.VISIBLE);
                            imv_calendario_cheque.setVisibility(View.VISIBLE);
                            Utilitario.disabledTextView(tv_fechacobrocheque_edit);
                            Utilitario.disabledTextView(tv_fechacobrocheque);
                            Utilitario.disabledImageButtton(imv_calendario_cheque,getContext());
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        spnbanco.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        //String banco="";

                        for(int i=0;i<Nombresbanco.size();i++)
                        {
                            if(position==i)
                            {
                                banco=Nombresbanco.get(i).toString();
                            }
                        }

                        ArrayList<BancoSQLiteEntity> listaBancoSQLiteEntity = new ArrayList<BancoSQLiteEntity>();
                        BancoSQLite bancoSQLite =new BancoSQLite(getContext());
                        /*if(chkdepositodirecto.isChecked()){
                            listaBancoSQLiteEntity= bancoSQLite.getBankDriver();
                        }else {
                            listaBancoSQLiteEntity= bancoSQLite.ObtenerBancoporCombo(SesionEntity.compania_id,banco);

                        }*/
                        listaBancoSQLiteEntity= bancoSQLite.ObtenerBancoporCombo(SesionEntity.compania_id,banco);
                        for(int j=0;j<listaBancoSQLiteEntity.size();j++)
                        {
                            Banco=listaBancoSQLiteEntity.get(j).getBanco_id();
                        }

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listaConsDepositoAdapterFragment.size()>0){
                    String tag="CobranzaCabeceraView";
                    String accion="agregarlista";
                    String compuesto=tag+"-"+accion;
                    Object object=null;

                    mListener.onFragmentInteraction(compuesto,listaConsDepositoAdapterFragment);
                }else{
                    String tag="CobranzaCabeceraView";
                    String accion="nuevalista";
                    String compuesto=tag+"-"+accion;
                    Object object=null;
                    mListener.onFragmentInteraction(compuesto,object);
                }
            }
        });
        imb_consultar_codigo_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BuildConfig.FLAVOR.equals("espania"))
                {
                    alertDepositDuplicate().show();
                }else
                {
                    if (guardar_deposito.isEnabled()) {
                        String texto = "Elija El el Codigo de Control: ";
                        AlertaObtenerCodigoControlDespacho(texto).show();
                    }
                }
            }});


       return v;
    }

    public static void updateTypeDeposit()
    {
        String [] valores = null;
        //Cambio Chile Mejoras sin efecto
        /*if(BuildConfig.FLAVOR.equals("chile"))
        {
            if(count_check>0){
                valores = new String[]{"Deposito Efectivo","Cheque Diferido","Cheque Dia"};
            }else {
                valores =  new String[]{"Deposito Efectivo"};
            }


        }else {
            valores =  new String[]{"Deposito","Cheque"};
        }*/

        valores =  new String[]{"Deposito","Cheque"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, valores);

        spntipo.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if (
                ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
        {

                    requestPermissions(new String[] {android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
            {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){

                } else{
                    if ((ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED))
                    {
                        requestPermissions(new String[] {android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                }
                break;
            }
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        date = new Date();
        fecha =dateFormat.format(date);


            String ano = "", mes = "", dia = "";

            mes = String.valueOf(month + 1);
            dia = String.valueOf(dayOfMonth);
            if (mes.length() == 1) {
                mes = '0' + mes;
            }
            if (dia.length() == 1) {
                dia = '0' + dia;
            }
        Log.e("REOS","CobranzaCabeceraView-onDateSet-fecha"+fecha);
        Log.e("jpcm","CobranzaCabeceraView-onDateSet-year+month+dayOfMonth"+year+mes+dia);
        if(calendar.equals("imv_calendario_cheque"))
        {
            if(Integer.parseInt(fecha)<Integer.parseInt(year+mes+dia))
            {
                //tv_fechacobrocheque_edit.setText(year + "/" + mes + "/" + dia);
                tv_fechacobrocheque_edit.setText(year + "-" + mes + "-" + dia);
                fechadiferida = year + mes + dia;

            }else {
                Toast.makeText(getContext(), "La fecha debe ser mayor a la actual, seleccione nuevamente", Toast.LENGTH_SHORT).show();
            }
        }
        else if(calendar.equals("imb_consultar_fecha_hoja_despacho"))
        {
            if (tv_fecha_hoja_despacho != null) {
                tv_fecha_hoja_despacho.setText(year + "-" + mes + "-" + dia);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imv_calendario_cheque:
                calendar="imv_calendario_cheque";
                final Calendar c1 = Calendar.getInstance();
                diadespacho = c1.get(Calendar.DAY_OF_MONTH);
                mesdespacho = c1.get(Calendar.MONTH);

                anodespacho = c1.get(Calendar.YEAR);
                oyenteSelectorFecha3 = new DatePickerDialog(getContext(),this,
                        anodespacho,
                        mesdespacho,
                        diadespacho
                );
                oyenteSelectorFecha3.show();


                break;
            case R.id.imb_consultar_fecha_hoja_despacho:
                calendar="imb_consultar_fecha_hoja_despacho";
                final Calendar c2 = Calendar.getInstance();
                diadespacho2 = c2.get(Calendar.DAY_OF_MONTH);
                mesdespacho2 = c2.get(Calendar.MONTH);

                anodespacho2 = c2.get(Calendar.YEAR);
                oyenteSelectorFecha3 = new DatePickerDialog(getContext(),this,
                        anodespacho2,
                        mesdespacho2,
                        diadespacho2
                );
                oyenteSelectorFecha3.show();
                break;
            default:
                break;
        }
    }


    static private class ObtenerTareaBancos extends AsyncTask<String, Void, Object> {


        @Override
        protected String doInBackground(String... arg0) {
            try {
                //listabancosqliteentity = bancoSQLite.ObtenerBanco();
                if(SesionEntity.perfil_id.equals("CHOFER")||SesionEntity.perfil_id.equals("Chofer"))
                {
                    if (chkdepositodirecto.isChecked()) {
                        listabancosqliteentity = bancoSQLite.ObtenerBanco();
                    } else {

                        listabancosqliteentity = bancoSQLite.getBankDriver();
                    }
                }else
                {
                    listabancosqliteentity = bancoSQLite.ObtenerBanco();
                }
                //listabancosqliteentity = bancoSQLite.getBankDriver();
            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return "1";
        }

        protected void onPostExecute(Object result)
        {
            for(int i = 0;i<listabancosqliteentity.size();i++)
            {
                if(i==0)
                {
                    Nombresbanco.add("---SELECCIONAR BANCO---");
                    Nombresbanco.add(listabancosqliteentity.get(i).getNombrebanco());
                }
                    else
                    {
                        Nombresbanco.add(listabancosqliteentity.get(i).getNombrebanco());
                    }

            }
            activity.setTitle("Deposito");
            ObtenerBanco();

        }
    }
    private class ObtenerListaCobranzas extends AsyncTask<String, Void, Object> {

        @Override
        protected String doInBackground(String... arg0) {
            try {
            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return "1";
        }

        protected void onPostExecute(Object result)
        {
            listaCobranzaCabeceraAdapter = new ListaCobranzaCabeceraAdapter(getActivity(), ListaCobranzaCabeceraDao.getInstance().getLeads(listaConsDepositoAdapterFragment,getContext()));
            listViewCobranzaCabecera.setAdapter(listaCobranzaCabeceraAdapter);
            txtsumacobrado.setText(Convert.currencyForView(ObtenerTotalCobrado(listaConsDepositoAdapterFragment)));
            obtenerLista = new ObtenerListaCobranzas();

        }
    }



    static public void ObtenerBanco()
    {
        Combonombresbanco= new ArrayAdapter<>(activity,android.R.layout.simple_spinner_item, Nombresbanco);
        spnbanco.setAdapter(Combonombresbanco);
    }

    public String ObtenerTotalCobrado(ArrayList<ListaConsDepositoEntity> Lista){
        sumacobrado=new BigDecimal("0");
        for(int i=0;i<Lista.size();i++)
        {
            sumacobrado=sumacobrado.add(new BigDecimal(Lista.get(i).getCobrado()));
        }

        return sumacobrado.setScale(3, RoundingMode.HALF_UP).toString();
    }
    @Override
    public void onAttach(Context context) {
        ListenerBackPress.setCurrentFragment("DepositoViewXd");
        Log.e("jpcm","este es el dEPOSUITOvIREW XXDD "+ListenerBackPress.getCurrentFragment());
        super.onAttach(context);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_cobranza_cabecera, menu);
        menu_variable=menu;
        if(listaConsDepositoAdapterFragment.isEmpty())
        {
            abrir = menu.findItem(R.id.abrir);
            guardar_deposito = menu.findItem(R.id.guardar_deposito);
            agregar_foto_deposito = menu.findItem(R.id.agregar_foto_deposito);
            Drawable drawable = menu.findItem(R.id.abrir).getIcon();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.white));
            menu.findItem(R.id.abrir).setIcon(drawable);
            Drawable drawable2 = menu.findItem(R.id.guardar_deposito).getIcon();
            drawable2 = DrawableCompat.wrap(drawable2);
            DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(), R.color.Black));
            menu.findItem(R.id.guardar_deposito).setIcon(drawable2);
            Drawable drawable3 = menu.findItem(R.id.agregar_foto_deposito).getIcon();
            drawable3 = DrawableCompat.wrap(drawable3);
            DrawableCompat.setTint(drawable3, ContextCompat.getColor(getContext(), R.color.Black));
            menu.findItem(R.id.agregar_foto_deposito).setIcon(drawable3);
            guardar_deposito.setEnabled(false);
            agregar_foto_deposito.setEnabled(false);
            fab.setEnabled(false);
            spntipo.setEnabled(false);
        }
        else
            {
                if(SesionEntity.perfil_id.equals("CHOFER")||SesionEntity.perfil_id.equals("Chofer")||BuildConfig.FLAVOR.equals("espania"))
                {
                abrir = menu.findItem(R.id.abrir);
                guardar_deposito = menu.findItem(R.id.guardar_deposito);
                agregar_foto_deposito = menu.findItem(R.id.agregar_foto_deposito);
                Drawable drawable = menu.findItem(R.id.abrir).getIcon();
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.Black));
                menu.findItem(R.id.abrir).setIcon(drawable);
                Drawable drawable3 = menu.findItem(R.id.agregar_foto_deposito).getIcon();
                drawable3 = DrawableCompat.wrap(drawable3);
                DrawableCompat.setTint(drawable3, ContextCompat.getColor(getContext(), R.color.Black));
                menu.findItem(R.id.agregar_foto_deposito).setIcon(drawable3);
                Drawable drawable2 = menu.findItem(R.id.guardar_deposito).getIcon();
                drawable2 = DrawableCompat.wrap(drawable2);
                DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(), R.color.white));
                menu.findItem(R.id.guardar_deposito).setIcon(drawable2);
                abrir.setEnabled(false);
                guardar_deposito.setEnabled(true);
                spntipo.setEnabled(true);
                etgrupo.setEnabled(true);
                agregar_foto_deposito.setEnabled(false);
                chkbancarizado.setVisibility(View.INVISIBLE);
                //chkdepositodirecto.setVisibility(View.INVISIBLE);
                imb_consultar_codigo_control.setEnabled(true);
                imb_consultar_codigo_control.setClickable(true);
                imb_consultar_codigo_control.setBackgroundColor(Color.parseColor("#D6001C"));
                }
                else {
                    abrir = menu.findItem(R.id.abrir);
                    guardar_deposito = menu.findItem(R.id.guardar_deposito);
                    agregar_foto_deposito = menu.findItem(R.id.agregar_foto_deposito);
                    Drawable drawable = menu.findItem(R.id.abrir).getIcon();
                    drawable = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.Black));
                    menu.findItem(R.id.abrir).setIcon(drawable);
                    Drawable drawable3 = menu.findItem(R.id.agregar_foto_deposito).getIcon();
                    drawable3 = DrawableCompat.wrap(drawable3);
                    DrawableCompat.setTint(drawable3, ContextCompat.getColor(getContext(), R.color.white));
                    menu.findItem(R.id.agregar_foto_deposito).setIcon(drawable3);
                    Drawable drawable2 = menu.findItem(R.id.guardar_deposito).getIcon();
                    drawable2 = DrawableCompat.wrap(drawable2);
                    DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(), R.color.Black));
                    menu.findItem(R.id.guardar_deposito).setIcon(drawable2);
                    abrir.setEnabled(false);
                    guardar_deposito.setEnabled(false);
                    spntipo.setEnabled(true);
                }

            }

        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.agregar_foto_deposito:
                alertaFotoDeposito().show();
                return true;
            case R.id.abrir:
                item.setEnabled(false);
                alertaAbrirDeposito(item).show();
                return true;
            case R.id.guardar_deposito:
                String tipo="0";
                Grupo=etgrupo.getText().toString();
                if(etgrupo.getText().toString().equals(""))
                {
                    //Toast.makeText(getContext(), "No se ah Ingresado nombre de Deposito", Toast.LENGTH_SHORT).show();
                    tipo="1";
                    Alerta(tipo).show();
                }
                else if(banco.equals("---SELECCIONAR BANCO---")||banco==null)
                {
                    tipo="2";
                    Alerta(tipo).show();
                    //Toast.makeText(getContext(), "Debe Elegir un Banco para poder Guardar el Deposito", Toast.LENGTH_SHORT).show();
                }
                else {

                    Sumacobrado = txtsumacobrado.getText().toString();
                    int z = 0;
                    if (listaConsDepositoAdapterFragment.isEmpty()) {
                        tipo="3";
                        Alerta(tipo).show();
                        // Toast.makeText(getContext(), "No se puede Enviar un Deposito sin recibos", Toast.LENGTH_SHORT).show();
                    } else {
                        alertaGuardarDeposito().show();
                    }
                }
                return true;

            default:
                break;
        }

        return false;
    }

    public AlertDialog alertaGuardarDeposito() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia")
                .setMessage("Seguro de Guardar el Deposito?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                guardar_deposito.setEnabled(false);
                                if(chkbancarizado.isChecked())
                                {
                                    bancarizado="Y";
                                }else
                                {
                                    bancarizado="N";
                                }
                                if(chkdepositodirecto.isChecked())
                                {
                                    depositodirecto="Y";
                                }else
                                {
                                    depositodirecto="N";
                                }

                                if(imv_calendario_cheque.getVisibility()==View.VISIBLE)
                                {

                                    //fechadiferida=tv_fechacobrocheque_edit.getText().toString();

                                }
                                else
                                {
                                    fechadiferida="19000101";
                                }
                                GuardarDepositoVincularRecibos();
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //listener.onNegativeButtonClick();
                            }
                        });

        return builder.create();
    }

    private void GuardarDepositoVincularRecibos()
    {

        if(Banco!=null) {
            depositoRepository = new ViewModelProvider(getActivity()).get(DepositoRepository.class);
            cobranzaRepository = new ViewModelProvider(getActivity()).get(CobranzaRepository.class);
            int ValidaSQLite = 0;
            ValidaSQLite = cobranzaCabeceraSQLiteDao.InsertaCobranzaCabecera(
                    Grupo,
                    SesionEntity.usuario_id,
                    SesionEntity.fuerzatrabajo_id,
                    Banco,
                    SesionEntity.compania_id,
                    sumacobrado.setScale(3, RoundingMode.HALF_UP).toString(),
                    tipo,
                    bancarizado,
                    fechadiferida,
                    fecha,
                    depositodirecto,
                    "N"
            );

            if (ValidaSQLite == 1) {
                for (int i = 0; i < listaConsDepositoAdapterFragment.size(); i++) {
                    cobranzaDetalleSQLiteDao.ActualizaCobranzaDetalle(
                            Grupo,
                            listaConsDepositoAdapterFragment.get(i).getRecibo(),
                            SesionEntity.compania_id,
                            Banco
                    );
                }
            }

            etgrupo.setEnabled(false);
            txtfecha.setEnabled(false);
            spnbanco.setEnabled(false);
            spnbanco.setClickable(false);

            Toast.makeText(getContext(), "Deposito Registrado Correctamente", Toast.LENGTH_SHORT).show();
            String fragment = "", accion = "", compuesto = "";
            fragment = "CobranzaCabeceraView";
            accion = "nuevoinicio";
            compuesto = fragment + "-" + accion;
            Object object = null;
            mListener.onFragmentInteraction(compuesto, object);

            ///////////////////////////// ENVIAR DEPOSITOS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            depositoRepository.depositResend(getContext()).observe(getActivity(), data -> {
                Log.e("Jepicame", "=>" + data);
            });
            ///////////////  /ENVIAR RECIBOS PENDIENTE CON DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\
            cobranzaRepository.depositedPendingCollection(getContext()).observe(getActivity(), data -> {
                Log.e("Jepicame", "=>" + data);
            });
        }
        else {
            Toast.makeText(getContext(), "No se eligio Banco, debe elegir banco para poder continuar...", Toast.LENGTH_SHORT).show();
        }
    }


    private void camaraTomar(){
        if(txtfecha.isEnabled())
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Crea el File
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("log,",""+ex);
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(), getContext().getPackageName() , photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                getActivity().startActivityForResult(intent, 21);
            }
        }else{
            estado=0;
            Toast.makeText(getContext(), "Abrir el Deposito Antes de Realizar la Foto", Toast.LENGTH_SHORT).show();
        }
    }

    public AlertDialog alertaFotoDeposito() {

        String[] colors = {"Camara", "Galeria"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("OPCIÓN");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        camaraTomar();
                        break;
                    case 1:
                        Toast.makeText(getContext(), "Abriendo galerias...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        getActivity().startActivityForResult(intent.createChooser(intent, "SELECIONE LA IMAGEN"), 155);

                        break;
                    default:
                        estado = 0;
                        break;
                }
            }
        });
        return builder.create();
    }

        private File createImageFile() throws IOException {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir =getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            String CurrentDateAndTime = getCurrentDateAndTime();
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            mCurrentPhotoPath = image.getAbsolutePath();
            String TAG="tag";
            Log.d(TAG,"el path de la imagen es = " + mCurrentPhotoPath);
            MenuView.mCurrentPhotoPath=mCurrentPhotoPath;
            //return file;
            return image;
        }
    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public Dialog alertaAbrirDeposito(MenuItem item) {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_alert_dialog);
        dialog.setCanceledOnTouchOutside(false);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("CONFIRMACIÓN!");

        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("¿Esta seguro de generar un nuevo deposito?");


        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///////////////////////////
                abrir.setEnabled(false);
                guardar_deposito.setEnabled(true);
                agregar_foto_deposito.setEnabled(true);
                txtfecha.setEnabled(true);
                spnbanco.setEnabled(true);
                spnbanco.setClickable(true);
                fab.setEnabled(true);
                fab.setClickable(true);
                //obtenerTareaBancos = new ObtenerTareaBancos();
                //obtenerTareaBancos.execute();

                if(listaConsDepositoAdapterFragment.size()>0)
                {
                    String tag="CobranzaCabeceraView";
                    String accion="agregarlista";
                    String compuesto=tag+"-"+accion;
                    Object object=null;
                    mListener.onFragmentInteraction(compuesto,object);

                }else{
                    String tag="CobranzaCabeceraView";
                    String accion="nuevalista";
                    String compuesto=tag+"-"+accion;
                    Object object=null;
                    mListener.onFragmentInteraction(compuesto,object);
                }
                dialog.dismiss();

                item.setEnabled(true);
            }
        });


        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                item.setEnabled(true);
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    public Dialog Alerta(String tipo) {

        String mensaje="";
        if(tipo.equals("1"))
        {
            mensaje="No se ah Ingresado un Codigo de Deposito";
        }
        else if(tipo.equals("2"))
        {
            mensaje="Debe Elegir un Banco para poder Guardar el Deposito";
        }
        else if(tipo.equals("3"))
        {
            mensaje="No se puede Enviar un Deposito sin recibos";
        }

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA!");

        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText(mensaje);


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
    public Dialog AlertaObtenerCodigoControlDespacho(String tipo) {

        String mensaje=tipo;

        ImageButton imb_consultar_fecha_hoja_despacho,imb_consultar_codigo_control_cabecera;


        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_obtener_codigo_control_despacho);

        TextView textTitle = dialog.findViewById(R.id.tv_titulo);
        textTitle.setText("ADVERTENCIA!");

        TextView textMsj = dialog.findViewById(R.id.tv_mensaje);
        textMsj.setText(mensaje);

        tv_fecha_hoja_despacho = dialog.findViewById(R.id.tv_fecha_hoja_despacho);
        imb_consultar_fecha_hoja_despacho = dialog.findViewById(R.id.imb_consultar_fecha_hoja_despacho);
        imb_consultar_codigo_control_cabecera = dialog.findViewById(R.id.imb_consultar_codigo_control_cabecera);
        spn_control_despacho_id = dialog.findViewById(R.id.spn_control_despacho_id);
        imb_consultar_fecha_hoja_despacho.setOnClickListener(this);


        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        fecha =dateFormat.format(date);
        tv_fecha_hoja_despacho.setText(fecha);
        imb_consultar_codigo_control_cabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HeaderDispatchSheetRepository headerDispatchSheetRepository= new ViewModelProvider(getActivity()).get(HeaderDispatchSheetRepository.class);
                ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);;
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();

                if (networkInfo != null) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        Log.e("REOS","DispatchSheetView-getMastersDelivery-entraif");
                        headerDispatchSheetRepository.getAndInsertHeaderDispatchSheet(SesionEntity.imei ,tv_fecha_hoja_despacho.getText().toString() ,getContext()).observe(getActivity(), data -> {
                            Log.e("REOS","DispatchSheetView-getMastersDelivery-data"+data.toString());
                            getListDispatchSheet(tv_fecha_hoja_despacho.getText().toString(),getContext());
                        });

                    } else {
                        Log.e("REOS","DispatchSheetView-getMastersDelivery-entraelse");
                    }
                }else{
                    Log.e("REOS","DispatchSheetView-getMastersDelivery-entraelse");
                    getListDispatchSheet(tv_fecha_hoja_despacho.getText().toString(),getContext());
                }

            }});


        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CobranzaCabeceraSQLiteDao cobranzaCabeceraSQLiteDao=new CobranzaCabeceraSQLiteDao(getContext());
                FormulasController formulasController=new FormulasController(getContext());
                if(spn_control_despacho_id.getSelectedItem()!=null)
                {
                    if(cobranzaCabeceraSQLiteDao.getCountValidateDeposit
                            (SesionEntity.compania_id,
                                    SesionEntity.usuario_id,
                                    spn_control_despacho_id.getSelectedItem().toString())>0)
                    {
                        alertDepositDuplicate().show();
                        dialog.dismiss();
                    }
                    else
                        {
                            etgrupo.setText(spn_control_despacho_id.getSelectedItem().toString());
                            dialog.dismiss();
                        }

                }
            }
        });
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return  dialog;
    }


    public void getListDispatchSheet(String dispatchDate,Context context)
    {
        ArrayList<HojaDespachoCabeceraSQLiteEntity> listaHeaderDispatchSheetSQLite = new ArrayList<>();
        HeaderDispatchSheetSQLite headerDispatchSheetSQLite= new HeaderDispatchSheetSQLite(context);
        listaHeaderDispatchSheetSQLite=headerDispatchSheetSQLite.getHeaderDispatchSheetforDate(dispatchDate);
        String [] Lista_Control_ID= new String [listaHeaderDispatchSheetSQLite.size()];
        int Contador=0;
        for(int i=0;i<listaHeaderDispatchSheetSQLite.size();i++)
        {
            Lista_Control_ID[Contador] = listaHeaderDispatchSheetSQLite.get(i).getControl_id();
            Contador++;
        }
        CargaSpinnerControl(Lista_Control_ID);
    }

    public void CargaSpinnerControl(String [] control_id)
    {
        ArrayAdapter<String> adapter_control_id = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, control_id);
        spn_control_despacho_id.setAdapter(adapter_control_id);
    }

    public Dialog alertDepositDuplicate() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_alert_dialog);
        dialog.setCanceledOnTouchOutside(false);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("CONFIRMACIÓN!");

        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        if(BuildConfig.FLAVOR.equals("espania"))
        {
            textMsj.setText("¿Desea asignar un ID unico a este Deposito?");
        }else {
            textMsj.setText("¿El Nro de Depósito, esta duplicado, desea asignarle un ID unico?");
        }



        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormulasController formulasController=new FormulasController(getContext());
                etgrupo.setText(formulasController.ObtenerFechaHoraCadena());
                dialog.dismiss();
            }
        });


        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }


}