package com.vistony.salesforce.View;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.vistony.salesforce.Controller.Adapters.ListaClienteCabeceraAdapter;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Dao.Adapters.ListaClienteCabeceraDao;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.RutaFuerzaTrabajoSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.SQLite.RutaFuerzaTrabajoSQLiteEntity;
import com.vistony.salesforce.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RutaVendedorRutaView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RutaVendedorRutaView extends Fragment implements SearchView.OnQueryTextListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListaClienteCabeceraAdapter listaClienteCabeceraAdapter;
    View v;
    ListView listrutavendedorruta;
    ObtenerSQLiteRutaFuerzaTrabajo obtenerSQLiteRutaFuerzaTrabajo;
    private SearchView mSearchView;
    TextView tv_cantidad_cliente_ruta,tv_cantidad_cliente_cabecera_total,tv_cantidad_cliente_cabecera_visita,tv_cantidad_cliente_cabecera_cobranza,tv_cantidad_cliente_cabecera_pedido;
    private ProgressDialog pd;

    public RutaVendedorRutaView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RutaVendedorRutaView.
     */
    // TODO: Rename and change types and number of parameters
    public static RutaVendedorRutaView newInstance(String param1, String param2) {
        RutaVendedorRutaView fragment = new RutaVendedorRutaView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_ruta_vendedor_ruta_view, container, false);
        listrutavendedorruta=v.findViewById(R.id.listrutavendedorruta);
        tv_cantidad_cliente_cabecera_total=v.findViewById(R.id.tv_cantidad_cliente_cabecera_total);
        tv_cantidad_cliente_cabecera_visita=v.findViewById(R.id.tv_cantidad_cliente_cabecera_visita);
        tv_cantidad_cliente_cabecera_cobranza=v.findViewById(R.id.tv_cantidad_cliente_cabecera_cobranza);
        tv_cantidad_cliente_cabecera_pedido=v.findViewById(R.id.tv_cantidad_cliente_cabecera_pedido);
        obtenerSQLiteRutaFuerzaTrabajo=new ObtenerSQLiteRutaFuerzaTrabajo();
        obtenerSQLiteRutaFuerzaTrabajo.execute();
        return v;
    }



    public class ObtenerSQLiteRutaFuerzaTrabajo extends AsyncTask<String, Void, Object> {

        String fecha;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getContext());
            pd = ProgressDialog.show(getContext(), "Por favor espere", "Calculando Ruta de Trabajo", true, false);
        }

        @Override
        protected Object doInBackground(String... arg0) {
            ArrayList<RutaFuerzaTrabajoSQLiteEntity> listaRutaFuerzaTrabajoSQLiteEntity=new ArrayList<>();
            try {
                //Proceso de Consulta de Ruta de Vendedor
                //Declaracion de Variables
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();
                RutaFuerzaTrabajoSQLiteDao rutaFuerzaTrabajoSQLiteDaoO=new RutaFuerzaTrabajoSQLiteDao(getContext());
                //Carga de Data
                fecha =dateFormat.format(date);
                listaRutaFuerzaTrabajoSQLiteEntity=rutaFuerzaTrabajoSQLiteDaoO.ObtenerRutaFuerzaTrabajoPorFecha(fecha);

                if(listaRutaFuerzaTrabajoSQLiteEntity==null || listaRutaFuerzaTrabajoSQLiteEntity.isEmpty() || listaRutaFuerzaTrabajoSQLiteEntity.size()==0){
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), "No hay ruta de trabajo para el dia de hoy", Toast.LENGTH_SHORT).show();
                    });
                }


            } catch (Exception e) {
               e.printStackTrace();
            }
            return listaRutaFuerzaTrabajoSQLiteEntity;
        }

        protected void onPostExecute(Object result) {
            //Declaracion de Variables
            String zona_id="";
            ArrayList<RutaFuerzaTrabajoSQLiteEntity>Lista=(ArrayList<RutaFuerzaTrabajoSQLiteEntity>) result;
            ArrayList<ListaClienteCabeceraEntity> listaClienteCabeceraEntities=new ArrayList<>();

            RutaFuerzaTrabajoSQLiteDao rutaFuerzaTrabajoSQLiteDaoO=new RutaFuerzaTrabajoSQLiteDao(getContext());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            RutaFuerzaTrabajoSQLiteDao rutaFuerzaTrabajoSQLiteDao=new RutaFuerzaTrabajoSQLiteDao(getContext());

            //Evalua si la lista obtenida por fecha tiene data y si la tabla tiene registros
            if(Lista.isEmpty()&&rutaFuerzaTrabajoSQLiteDaoO.ObtenerCantidadRutaFuerzaTrabajo()>0){

                Log.e("REOS","Entro if");
                //Carga Data
                String fecha =dateFormat.format(date);
                Lista=rutaFuerzaTrabajoSQLiteDao.ObtenerRutaFuerzaTrabajoFechaMenor(fecha);

                //Evalua si la lista no esta vacia
                while (!(Lista.isEmpty())){

                    String fechainicioruta="",fecharutaactualizada="";
                    Date fechainiciorutadate;

                    //recorre la lista
                    for(int i=0;i<Lista.size();i++)
                    {
                        fechainicioruta=Lista.get(i).getFechainicioruta();
                        fechainiciorutadate=ConvertirFechaStringDate(fechainicioruta);
                        //Actualiza la Fecha de la tabla a la mas cercana a la actual
                        fecharutaactualizada=sumarDiasAFecha(fechainiciorutadate,Integer.parseInt(Lista.get(i).getFrecuencia()));
                        //Actualiza en la tabla
                        rutaFuerzaTrabajoSQLiteDao.ActualizaFechaInicioRuta(
                                Lista.get(i).getCompania_id(),
                                Lista.get(i).getZona_id(),
                                Lista.get(i).getDia(),
                                String.valueOf(fecharutaactualizada)
                        );
                    }


                    //Obtiene lista menor con fecha actual
                    Lista=rutaFuerzaTrabajoSQLiteDao.ObtenerRutaFuerzaTrabajoFechaMenor(fecha);
                }
            }else{

                Log.e("REOS","entro else");
                    //recorre lista para obtener codigo de Zona
                    for(int i=0;i<Lista.size();i++)
                    {
                        zona_id=Lista.get(i).getZona_id();
                        Log.e("REOS",zona_id);
                    }
                    //Declaracion Variables
                    ArrayList<ListaClienteCabeceraEntity> listaClienteCabeceraEntityconruta=new ArrayList<>();
                    ClienteSQlite clienteSQlite =new ClienteSQlite(getContext());

                    RutaVendedorSQLiteDao rutaVendedorSQLiteDao=new RutaVendedorSQLiteDao(getContext());

                    FormulasController formulasController=new FormulasController(getContext());
                    String chk_ruta="1";
                    //Obtiene Clientes con zona del dia
                    listaClienteCabeceraEntities= clienteSQlite.ObtenerClientePorZonaCompleto(zona_id);

                    //Evalua si RutaVendedor ya tiene clientes
                    listaClienteCabeceraEntityconruta=rutaVendedorSQLiteDao.ObtenerRutaVendedorPorFecha(fecha,chk_ruta,getContext());
                    if(listaClienteCabeceraEntityconruta.isEmpty()){
                        formulasController.RegistrarRutaVendedor(listaClienteCabeceraEntities,fecha,chk_ruta);
                        listaClienteCabeceraEntityconruta=rutaVendedorSQLiteDao.ObtenerRutaVendedorPorFecha(fecha,chk_ruta,getContext());
                    }



                    listaClienteCabeceraAdapter = new ListaClienteCabeceraAdapter(getActivity(), ListaClienteCabeceraDao.getInstance().getLeads(listaClienteCabeceraEntityconruta));
                    listrutavendedorruta.setAdapter(listaClienteCabeceraAdapter);



                    int visita=0,pedido=0,cobranza=0;
                    for(int i=0;i<listaClienteCabeceraEntityconruta.size();i++)
                    {
                        if(listaClienteCabeceraEntityconruta.get(i).getChk_visita().equals("1"))
                        {
                            visita++;
                        }
                        if(listaClienteCabeceraEntityconruta.get(i).getChk_pedido().equals("1"))
                        {
                            pedido++;
                        }
                        if(listaClienteCabeceraEntityconruta.get(i).getChk_cobranza().equals("1"))
                        {
                            cobranza++;
                        }
                    }
                    tv_cantidad_cliente_cabecera_total.setText(String.valueOf(listaClienteCabeceraEntityconruta.size()));
                    tv_cantidad_cliente_cabecera_visita.setText(String.valueOf(visita));
                    tv_cantidad_cliente_cabecera_cobranza.setText(String.valueOf(cobranza));
                    tv_cantidad_cliente_cabecera_pedido.setText(String.valueOf(pedido));
                    //getActivity().setTitle("Ruta Vendedor");
                }
            Log.e("REOS","Finaliza Hilo");

            pd.dismiss();
        }


        public Date ConvertirFechaStringDate(String fecha){

            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date fechaDate = new Date();
            try {
                fechaDate = formato.parse(fecha);
            }
            catch (ParseException ex)
            {
                System.out.println(ex);
            }
            return fechaDate;
        }

        public  String  sumarDiasAFecha(Date fecha, int dias){
            String year,mes,dia,fechaActualizada;
            int mescalendario,yearcalendario,diacalendario;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            calendar.add(Calendar.DAY_OF_YEAR, dias);

            yearcalendario = calendar.get(calendar.YEAR);
            mescalendario = calendar.get(calendar.MONTH);
            diacalendario = calendar.get(calendar.DAY_OF_MONTH);

            mes=String.valueOf(mescalendario+1);
            dia=String.valueOf(diacalendario);
            year=String.valueOf(yearcalendario);
            if(mes.length()==1)
            {
                mes='0'+mes;
            }
            if(dia.length()==1)
            {
                dia='0'+dia;
            }

            fechaActualizada=(year + "-" + mes + "-" + dia);


            return fechaActualizada;
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_ruta_vendedor, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search2);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Buscar Cliente");
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(listaClienteCabeceraAdapter!=null) {
            listaClienteCabeceraAdapter.filter(newText);
        }

        return true;
    }

}