package com.vistony.salesforce.View;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

//import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.vistony.salesforce.Controller.Adapters.ListaHistoricoDepositoAdapter;
import com.vistony.salesforce.Dao.Retrofit.HistoricoDepositoWS;
import com.vistony.salesforce.Dao.SQLite.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.Adapters.ListaHistoricoDepositoDao;
import com.vistony.salesforce.Entity.SQLite.CobranzaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoDepositoEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HistoricoDepositoView extends Fragment implements View.OnClickListener,DatePickerDialog.OnDateSetListener, AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView tv_fechainidep,tv_fechafindep;
    public static ImageButton imb_fechainidep,imb_fechafindep;
    public static com.omega_r.libs.OmegaCenterIconButton imb_consultardep;
    public ListView listconsdepositos;
    private static OnFragmentInteractionListener mListener;
    View v;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;
    private  int diadespacho,mesdespacho,anodespacho,hora,minutos,diadespacho2,mesdespacho2,anodespacho2;
    private static DatePickerDialog oyenteSelectorFecha3,oyenteSelectorFecha4;
    String calendario="";
    private static final int TIPO_DIALOGO=0,TIPO_DIALOGO2=1;
    ArrayList<ListaHistoricoDepositoEntity> arraylistahistoricodespositoentity;
    ObtenerHistoricoDeposito obtenerHistoricoDeposito;
    ListaHistoricoDepositoAdapter listaHistoricoDepositoAdapter;
    ArrayList<ListaHistoricoDepositoEntity> listaHistoricoDepositoEntities;
    private ProgressDialog pd;

    public HistoricoDepositoView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment HistoricoDepositoView.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricoDepositoView newInstance(Object param1) {
        HistoricoDepositoView fragment = new HistoricoDepositoView();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        String Fragment="HistoricoDepositoView";
        String Accion="Deposito";
        String compuesto=Fragment+"-"+Accion;

        if(param1!=null && compuesto!=null){
            mListener.onFragmentInteraction(compuesto,param1);
        }

        return fragment;
    }

    public static HistoricoDepositoView newInstanciaComentarioAnulado (Object param1) {
        HistoricoDepositoView fragment = new HistoricoDepositoView();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        String Fragment="HistoricoDepositoView";
        String Accion="Deposito";
        String compuesto=Fragment+"-"+Accion;
        mListener.onFragmentInteraction(compuesto,param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        fecha =dateFormat.format(date);
        listaHistoricoDepositoEntities = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_historico_deposito, container, false);
        getActivity().setTitle("Consulta Deposito");
        tv_fechainidep = (TextView) v.findViewById(R.id.tv_fechainidep);
        tv_fechainidep.setText(fecha);
        tv_fechafindep = (TextView) v.findViewById(R.id.tv_fechafindep);
        tv_fechafindep.setText(fecha);
        imb_fechainidep = (ImageButton) v.findViewById(R.id.imb_fechainidep);
        imb_fechainidep.setOnClickListener(this);
        imb_fechafindep = (ImageButton) v.findViewById(R.id.imb_fechafindep);
        imb_fechafindep.setOnClickListener(this);
        imb_consultardep =  v.findViewById(R.id.imb_consultardep);
        imb_consultardep.setOnClickListener(this);
        //btnconsultafecha.setOnClickListener(this);
        listconsdepositos = (ListView) v.findViewById(R.id.listconsdepositos);
        listconsdepositos.setOnItemClickListener(this);
        return v;
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ListenerBackPress.setCurrentFragment("HistoricoDepositoView");

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
        Log.e("jpcm","ME EJCUTO CUANDO QUEIROOO NEL");
       // mListener = null;
    }
*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ListenerBackPress.setCurrentFragment("HistoricoDepositoView");
        ListenerBackPress.setTemporaIdentityFragment("onAttach");
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
        ListenerBackPress.setTemporaIdentityFragment("onDetach");
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.imb_fechainidep:
                calendario="";
                calendario="fechainidep";
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
            case R.id.imb_fechafindep:
                calendario="";
                calendario="fechafindep";
                final Calendar c12 = Calendar.getInstance();
                diadespacho2 = c12.get(Calendar.DAY_OF_MONTH);
                mesdespacho2 = c12.get(Calendar.MONTH);

                anodespacho2 = c12.get(Calendar.YEAR);
                oyenteSelectorFecha4 = new DatePickerDialog(getContext(),this,
                        anodespacho,
                        mesdespacho,
                        diadespacho
                );
                oyenteSelectorFecha4.show();


                break;
            case R.id.imb_consultardep:
                Log.e("jpcm","Buscando...");
                imb_consultardep.setEnabled(false);
                imb_consultardep.setClickable(false);

                obtenerHistoricoDeposito =  new ObtenerHistoricoDeposito();
                obtenerHistoricoDeposito.execute();

                String parametrofecha="";


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

        if(calendario.equals("fechainidep"))
        {
            tv_fechainidep.setText(year + "-" + mes + "-" + dia);
        }
        else if(calendario.equals("fechafindep"))
        {
            tv_fechafindep.setText(year + "-" + mes + "-" + dia);
        }


    }





    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        //Toast.makeText(getContext(), "click", Toast.LENGTH_LONG).show();
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
        void onFragmentInteraction(String tag,Object dato);
    }

    public class ObtenerHistoricoDeposito extends AsyncTask<String, Void, Object> {
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Consultando Depositos", true, false);
        }
        @Override
        protected String doInBackground(String... arg1) {
            try {

                arraylistahistoricodespositoentity = new ArrayList<ListaHistoricoDepositoEntity>();
                HistoricoDepositoWS historicoDepositoWS=new HistoricoDepositoWS(getContext());

                /*
                arraylistahistoricodespositoentity=historicoDepositoWSDao.consultarHistoricoDeposito(
                        SesionEntity.imei,
                        SesionEntity.compania_id,
                        tv_fechainidep.getText().toString(),
                        tv_fechafindep.getText().toString(),
                        SesionEntity.fuerzatrabajo_id
                );*/

                arraylistahistoricodespositoentity=historicoDepositoWS.getHistoricoDeposito(
                        SesionEntity.imei,
                        SesionEntity.compania_id,
                        tv_fechainidep.getText().toString(),
                        tv_fechafindep.getText().toString(),
                        SesionEntity.fuerzatrabajo_id
                );

                CobranzaCabeceraSQLiteDao cobranzaCabeceraSQLiteDao=new CobranzaCabeceraSQLiteDao(getContext());
                ArrayList<CobranzaCabeceraSQLiteEntity> listaCobranzaCabeceraSQLiteEntity = new ArrayList<>();

                listaCobranzaCabeceraSQLiteEntity=cobranzaCabeceraSQLiteDao.ObtenerHistoricoDepostito
                        (
                        SesionEntity.compania_id,
                        SesionEntity.usuario_id,
                        tv_fechainidep.getText().toString(),
                        tv_fechafindep.getText().toString()
                        );

                if(listaCobranzaCabeceraSQLiteEntity == null || listaCobranzaCabeceraSQLiteEntity.size() == 0)
                {
                    Log.e("jpcm","El arrayList del Sqlite es vacio");
                }

                ArrayList<String> listadepuracion1 = new ArrayList<>();
                ArrayList<String> listadepuracion2 = new ArrayList<>();


                for (int j=0;j<listaCobranzaCabeceraSQLiteEntity.size();j++)
                {
                    listadepuracion2.add(listaCobranzaCabeceraSQLiteEntity.get(j).getCobranza_id());
                }

                for (int l=0;l<arraylistahistoricodespositoentity.size();l++)
                {
                    listadepuracion1.add(arraylistahistoricodespositoentity.get(l).getDeposito_id());
                }

                if (!(listadepuracion1.size() == listadepuracion2.size()))
                {
                    listadepuracion2.removeAll(listadepuracion1);
                    for (int k = 0; k < listadepuracion2.size(); k++) {
                        for (int l = 0; l < listaCobranzaCabeceraSQLiteEntity.size(); l++) {
                            if (listadepuracion2.get(k).equals(listaCobranzaCabeceraSQLiteEntity.get(l).getCobranza_id())) {
                                String estado="";
                                ListaHistoricoDepositoEntity listaHistoricoDepositoEntity = new ListaHistoricoDepositoEntity();
                                listaHistoricoDepositoEntity.bancarizacion=listaCobranzaCabeceraSQLiteEntity.get(l).getChkbancarizado();
                                listaHistoricoDepositoEntity.banco_id=listaCobranzaCabeceraSQLiteEntity.get(l).getBanco_id();
                                listaHistoricoDepositoEntity.comentario="";
                                listaHistoricoDepositoEntity.compania_id=listaCobranzaCabeceraSQLiteEntity.get(l).getCompania_id();
                                listaHistoricoDepositoEntity.deposito_id=listaCobranzaCabeceraSQLiteEntity.get(l).getCobranza_id();
                                if(listaCobranzaCabeceraSQLiteEntity.get(l).getChkanulado().equals("1")){
                                    estado="Anulado";
                                }
                                else{
                                    estado="Pendiente";
                                }
                                listaHistoricoDepositoEntity.estado=estado;
                                String dia="",mes="",anio="",fecha="";
                                String[] separada = listaCobranzaCabeceraSQLiteEntity.get(l).getFechadeposito().split("-");
                                if(separada.length>1)
                                {
                                    dia=separada[0];
                                    mes=separada[1];
                                    anio=separada[2];
                                }

                                String FechaDeposito=dia+"/"+mes+"/"+anio+" 00:00:00.000";
                                Log.e("REOS","HistoricoDepositoView:FechaDeposito: "+FechaDeposito);

                                listaHistoricoDepositoEntity.fechadeposito=FechaDeposito;
                                        //listaCobranzaCabeceraSQLiteEntity.get(l).getFechadeposito();
                                String diadiferido="",mesdiferido="",aniodiferido="",fechadiferido="",horadiferido,diferido;
                                String[] separadadiferido = listaCobranzaCabeceraSQLiteEntity.get(l).getFechadiferido().split(" ");
                                Log.e("REOS","HistoricoDepositoView:separadadiferido: "+separadadiferido);

                                fechadiferido=separadadiferido[0];
                                Log.e("REOS","HistoricoDepositoView:fechadiferido: "+fechadiferido);
                                horadiferido=separadadiferido[1];

                                String[] separarfechadiferida = fechadiferido.split("/");


                                    diadiferido=separarfechadiferida[0];
                                    mesdiferido=separarfechadiferida[1];
                                    aniodiferido=separarfechadiferida[2];
                                if(diadiferido.length()==1)
                                {
                                    diadiferido="0"+diadiferido;
                                }

                                diferido=aniodiferido+"/"+mesdiferido+"/"+diadiferido+" 00:00:00.000";


                                listaHistoricoDepositoEntity.fechadiferida=
                                        //listaCobranzaCabeceraSQLiteEntity.get(l).getFechadiferido()
                                        diferido
                                ;
                                listaHistoricoDepositoEntity.fuerzatrabajo_id=listaCobranzaCabeceraSQLiteEntity.get(l).getFuerzatrabajo_id();
                                listaHistoricoDepositoEntity.montodeposito=listaCobranzaCabeceraSQLiteEntity.get(l).getTotalmontocobrado();
                                listaHistoricoDepositoEntity.motivoanulacion=listaCobranzaCabeceraSQLiteEntity.get(l).getComentarioanulado();
                                listaHistoricoDepositoEntity.tipoingreso=listaCobranzaCabeceraSQLiteEntity.get(l).getTipoingreso();
                                listaHistoricoDepositoEntity.usuario_id=listaCobranzaCabeceraSQLiteEntity.get(l).getUsuario_id();

                                arraylistahistoricodespositoentity.add(listaHistoricoDepositoEntity);
                            }
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
               Log.e("jpcm","Por alguna razon no cargo los depositos pendientes "+e);
            }
            return "1";
        }

        protected void onPostExecute(Object result){
            listconsdepositos.setAdapter(null);

            if(arraylistahistoricodespositoentity.size()>0){
                listaHistoricoDepositoAdapter = new ListaHistoricoDepositoAdapter(getActivity(), ListaHistoricoDepositoDao.getInstance().getLeads(arraylistahistoricodespositoentity));

                listconsdepositos.setAdapter(listaHistoricoDepositoAdapter);

                Toast.makeText(getContext(), "Depositos Obtenidos Exitosamente", Toast.LENGTH_LONG).show();
                imb_consultardep.setEnabled(true);
                imb_consultardep.setClickable(true);


            }else{
                Toast.makeText(getContext(), "No se encontraron Depositos", Toast.LENGTH_LONG).show();
                imb_consultardep.setEnabled(true);
                imb_consultardep.setClickable(true);
            }

            pd.dismiss();
        }
    }
}
