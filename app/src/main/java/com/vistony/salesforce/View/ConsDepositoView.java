package com.vistony.salesforce.View;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
/*
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
*/
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import com.vistony.salesforce.Controller.Adapters.ListaConsDepositoAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.Adapters.ListaConsDepositoDao;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaConsDepositoEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsDepositoView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsDepositoView#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ConsDepositoView extends Fragment implements View.OnClickListener,DatePickerDialog.OnDateSetListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View v;
    // TODO: Rename and change types of parameters
    private static String mParam1;
    private String mParam2;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;
    TextView tv_fechacobranza;
    public static TextView tv_cantidad_consdeposito,tv_monto_consdeposito;
    ImageButton imv_calendario;
    Button btnconsultafecha;
    ListView listaViewCobranzas;
    private  int diadespacho,mesdespacho,anodespacho,hora,minutos;
    private static DatePickerDialog oyenteSelectorFecha3;
    private static OnFragmentInteractionListener mListener;
    private static final int TIPO_DIALOGO3=0;
    ObtenerTareaListaCobranzas obtenerTareaListaCobranzas;
    ArrayList<CobranzaDetalleSQLiteEntity>  listacobranzaDetalleSQLiteEntity;
    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao;
    ListaConsDepositoAdapter listaConsDepositoAdapter;
    static ArrayList<ListaConsDepositoEntity>  listaConsDepositoEntity;
    static ArrayList<ListaConsDepositoEntity>  ArraylistaConsDepositoEntity;
    static ListaConsDepositoEntity ObjetolistaConsDepositoEntity;
    static String TAG_1 = "text";
    String calendario="";
    //ArrayList<ListaConsDepositoEntity> ArraylistaConsDepositoEntity;
    MenuItem vincular;
    static String Recibos_agregados="";
    private final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE=2;
    public ConsDepositoView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ConsDepositoView.
     */
    // TODO: Rename and change types and number of parameters
    /*public static ConsDepositoView newInstanciada(String param1) {
        ListenerBackPress.setTemporaIdentityFragment("redirectToDepositoItem1");
        Log.e("jpcm","esta isntancia no la comente AYAAA BAG BONNY XDDD");
        ConsDepositoView fragment = new ConsDepositoView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);

        return fragment;
    }*/
    public static ConsDepositoView newInstanciada(String param1) {
        ListenerBackPress.setTemporaIdentityFragment("redirectToDepositoItem1");
        Log.e("jpcm","esta isntancia no la comente AYAAA BAG BONNY XDDD");
        ConsDepositoView fragment = new ConsDepositoView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    /*
    public static ConsDepositoView newInstance(Object Lista,String Dato) {
        Log.e("jpcm","abrio add list and back press");
        ListenerBackPress.setTemporaIdentityFragment("redirectToDepositoItem1");
        ConsDepositoView fragment = new ConsDepositoView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, Dato);
        mParam1=Dato;
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        ArrayList<ListaConsDepositoEntity> Listado = new ArrayList<ListaConsDepositoEntity>();
        Listado=(ArrayList<ListaConsDepositoEntity>) Lista;
        for(int i=0;i<Listado.size();i++)
        {
            if(i==0)
            {
                Recibos_agregados=Listado.get(i).getRecibo();
            }
            else if(Listado.size()>1&&i>0)
            {
                Recibos_agregados=Recibos_agregados+","+Listado.get(i).getRecibo();
            }
        }
        return fragment;
    }*/
    public static ConsDepositoView newInstance(Object Lista,String Dato) {
        Log.e("jpcm","abrio add list and back press");
        ListenerBackPress.setTemporaIdentityFragment("redirectToDepositoItem1");
        ConsDepositoView fragment = new ConsDepositoView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, Dato);
        mParam1=Dato;
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        ArrayList<ListaConsDepositoEntity> Listado = new ArrayList<ListaConsDepositoEntity>();
        Listado=(ArrayList<ListaConsDepositoEntity>) Lista;
        for(int i=0;i<Listado.size();i++)
        {
            if(i==0)
            {
                Recibos_agregados=Listado.get(i).getRecibo();
            }
            else if(Listado.size()>1&&i>0)
            {
                Recibos_agregados=Recibos_agregados+","+Listado.get(i).getRecibo();
            }

        }
        return fragment;
    }
    public ConsDepositoView nuevaInstancia(Object objeto){
        ListenerBackPress.setTemporaIdentityFragment("temporaAddCorbaznaToListDeposito");
        //Log.e("jpcm","NEW INSTANCE XDDDDD PERCONA897987897987");
        //ListenerBackPress.setTemporaIdentityFragment("temporaAddCorbaznaToListDeposito");
        String Variable,FragmentFinal;
        ConsDepositoView consDepositoView = new ConsDepositoView();
        Bundle b = new Bundle();
        listaConsDepositoEntity = new  ArrayList<ListaConsDepositoEntity>();
        listaConsDepositoEntity = (ArrayList<ListaConsDepositoEntity> )objeto;
        ArraylistaConsDepositoEntity = new  ArrayList<ListaConsDepositoEntity>();

        for(int i=0;i<listaConsDepositoEntity.size();i++)
        {
            if(listaConsDepositoEntity.get(i).checkbox==true)
            {
                ObjetolistaConsDepositoEntity = new ListaConsDepositoEntity();
                ObjetolistaConsDepositoEntity.cobrado=listaConsDepositoEntity.get(i).getCobrado();
                ArraylistaConsDepositoEntity.add(ObjetolistaConsDepositoEntity);
            }
        }


        tv_cantidad_consdeposito.setText(String.valueOf(ArraylistaConsDepositoEntity.size()));

        BigDecimal monto=new BigDecimal("0");

        for(int j=0;j<ArraylistaConsDepositoEntity.size();j++){
            monto=monto.add(new BigDecimal(ArraylistaConsDepositoEntity.get(j).getCobrado()));
        }

        tv_monto_consdeposito.setText(Convert.currencyForView(monto.setScale(3, RoundingMode.HALF_UP).toString()));

        return consDepositoView;

    }


    public static ConsDepositoView newInstancia (Object objeto,String Dato){
        ListenerBackPress.setTemporaIdentityFragment("Deposito");
        String Variable,FragmentFinal;
        ConsDepositoView consDepositoView = new ConsDepositoView();
        Bundle b = new Bundle();
        //b.putString(ARG_PARAM1, String.valueOf(objeto));
        //fragment.setArguments(args);
        String Fragment="ConsDepositoView";
        Variable=Dato;
        FragmentFinal=Fragment+"-"+Variable;
        mListener.onFragmentInteraction(FragmentFinal,objeto);
        return consDepositoView;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            if(mParam1.equals("nuevalista"))
            {
                Recibos_agregados="";
            }
            //mParam2 = getArguments().getString(ARG_PARAM2);
            setHasOptionsMenu(true);
        }
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        fecha =dateFormat.format(date);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_cons_deposito_view, container, false);
        tv_fechacobranza = (TextView) v.findViewById(R.id.tv_fechacobranza);
        tv_fechacobranza.setText(fecha);
        imv_calendario = (ImageButton) v.findViewById(R.id.imvcalendario);
        imv_calendario.setOnClickListener(this);
        btnconsultafecha = (Button) v.findViewById(R.id.btnconsultarfecha);
        btnconsultafecha.setOnClickListener(this);
       listaViewCobranzas =v.findViewById(R.id.listcobranzas);
        //listaViewCobranzas.setDividerHeight(3);
       // listaViewCobranzas.notifyDataSetChanged();
        //listaViewCobranzas.setLayoutManager(new LinearLayoutManager(getActivity()));
        //listaViewCobranzas.setHasFixedSize(true);
        tv_cantidad_consdeposito= (TextView) v.findViewById(R.id.tv_cantidad_consdeposito);
        tv_monto_consdeposito= (TextView) v.findViewById(R.id.tv_monto_consdeposito);


        getActivity().setTitle("Pendientes Deposito");
        return v;
    }

    private class ObtenerTareaListaCobranzas extends AsyncTask<String, Void, Object> {

        @Override
        protected String doInBackground(String... arg1) {
            String fecha=arg1[0];
            Log.e("jpcm","execute");
            try {
                //listacobranzaDetalleSQLiteEntity = cobranzaDetalleSQLiteDao.ObtenerBanco();
                listacobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
                cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
                listacobranzaDetalleSQLiteEntity = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporFecha(fecha, SesionEntity.compania_id,SesionEntity.usuario_id,Recibos_agregados,SesionEntity.fuerzatrabajo_id);
            } catch (Exception e)
            {
               Log.e("jpcm",""+e);
            }
            return "1";
        }

        protected void onPostExecute(Object result)
        {
            listaViewCobranzas.setAdapter(null);


            if(listacobranzaDetalleSQLiteEntity.size()>0)
            {
                //listaConsDepositoAdapter.clear();
                listaConsDepositoAdapter = new ListaConsDepositoAdapter(
                        getActivity(),
                        ListaConsDepositoDao.getInstance().getLeads(listacobranzaDetalleSQLiteEntity
                        , getContext()
                ));
                listaViewCobranzas.setAdapter(listaConsDepositoAdapter);
                listaConsDepositoAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getContext(), "No se encontraron Cobranzas del dia", Toast.LENGTH_LONG).show();
            }
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        if(!ListenerBackPress.getTemporaIdentityFragment().equals("Deposito")){
            ListenerBackPress.setCurrentFragment("ConsultaCobranzaParaDepositarView");
        }
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
*/
    @Override
    public void onAttach(Context context) {
        if(!ListenerBackPress.getTemporaIdentityFragment().equals("Deposito")){
            ListenerBackPress.setCurrentFragment("ConsultaCobranzaParaDepositarView");
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imvcalendario:
                String texto="",texto2="";
                texto="vacio";
                texto2=texto;
                final Calendar c1 = Calendar.getInstance();
                diadespacho = c1.get(Calendar.DAY_OF_MONTH);
                mesdespacho = c1.get(Calendar.MONTH);
                anodespacho = c1.get(Calendar.YEAR);
                oyenteSelectorFecha3 = new DatePickerDialog(getContext(),ConsDepositoView.this,
                        anodespacho,
                        mesdespacho,
                        diadespacho
                        );
                oyenteSelectorFecha3.show();


                break;
            case R.id.btnconsultarfecha:
                //String texto3="",texto4="";
                //texto3="vacio";
                //texto4=texto3;
                String parametrofecha="";
                parametrofecha=String.valueOf(tv_fechacobranza.getText());
                obtenerTareaListaCobranzas = new ObtenerTareaListaCobranzas();
                obtenerTareaListaCobranzas.execute(parametrofecha);


                break;
            default:
                break;
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String ano="",mes="",dia="";

        mes=String.valueOf(month+1);
        dia=String.valueOf(dayOfMonth);
        if(mes.length()==1)
        {
            mes='0'+mes;
        }
        if(dia.length()==1)
        {
            dia='0'+dia;
        }
        //anodespacho = year;

        //diadespacho = dayOfMonth;
        //mesdespacho = month;
        tv_fechacobranza.setText(year + "-" + mes + "-" + dia);
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
        void onFragmentInteraction(String Tag,Object object);
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_consdeposito, menu);
        vincular = menu.findItem(R.id.vincular);
        Drawable drawable = menu.findItem(R.id.vincular).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.white));
        menu.findItem(R.id.vincular).setIcon(drawable);

        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.vincular:
                Object objeto=null,object2=null;

                    if(!SesionEntity.listaConsDeposito.equals("0"))
                    {
                        objeto=listaConsDepositoAdapter.ObtenerListaConsDeposito();
                    object2=objeto;
                    listaConsDepositoEntity = new ArrayList<ListaConsDepositoEntity>();
                    listaConsDepositoEntity = (ArrayList<ListaConsDepositoEntity>) objeto;
                    ConsDepositoView.newInstancia(listaConsDepositoEntity,mParam1);
                    SesionEntity.listaConsDeposito="0";
                }
                else
                  {
                       Toast.makeText(getContext(), "No se eligio recibos, elegir recibir para depositar", Toast.LENGTH_LONG).show();
                   }

                return false;

            default:
                break;
        }

        return false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if (
                ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
        {

            requestPermissions(new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE:
            {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                {

                } else
                {
                    if ((ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED))
                    {

                        requestPermissions(new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
                    }
                }
                break;
            }
        }

    }


}
