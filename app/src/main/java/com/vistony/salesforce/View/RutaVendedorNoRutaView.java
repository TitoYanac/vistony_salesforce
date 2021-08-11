package com.vistony.salesforce.View;

import android.content.Context;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vistony.salesforce.Controller.Adapters.ListaClienteCabeceraAdapter;
import com.vistony.salesforce.Controller.Funcionalidades.FormulasController;
import com.vistony.salesforce.Dao.Adapters.ListaClienteCabeceraDao;
import com.vistony.salesforce.Dao.SQLIte.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RutaVendedorNoRutaView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RutaVendedorNoRutaView extends Fragment implements SearchView.OnQueryTextListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    public static OnFragmentInteractionListener mListener;
    FloatingActionButton fabagregarclientenoruta;
    static Context context;
    static ArrayList<ListaClienteCabeceraEntity>  listaClienteCabeceraEntities=new ArrayList<>();
    static ArrayList<ListaClienteCabeceraEntity>  listaConsClienteCabeceraEntities=new ArrayList<>();
    ListaClienteCabeceraAdapter listaClienteCabeceraAdapter;
    ListView listrutavendedornoruta;
    static ObtenerRutaVendedorNoRuta obtenerRutaVendedorNoRuta;
    private SearchView mSearchView;
    TextView tv_cantidad_cliente_no_ruta_total,tv_cantidad_cliente_no_ruta_visita,tv_cantidad_cliente_no_ruta_pedido,tv_cantidad_cliente_no_ruta_cobranza;
    static boolean clienteagregado=false;
    public RutaVendedorNoRutaView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RutaVendedorNoRutaView.
     */
    // TODO: Rename and change types and number of parameters
    public static RutaVendedorNoRutaView newInstance(String param1, String param2) {
        RutaVendedorNoRutaView fragment = new RutaVendedorNoRutaView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static RutaVendedorNoRutaView newInstanceRecibeCliente (Object object) {
        RutaVendedorNoRutaView fragment = new RutaVendedorNoRutaView();
        Bundle args = new Bundle();
        listaConsClienteCabeceraEntities=(ArrayList<ListaClienteCabeceraEntity>) object;
        ListaClienteCabeceraEntity listaClienteCabeceraEntity;
        for(int i=0;i<listaConsClienteCabeceraEntities.size();i++)
        {
            listaClienteCabeceraEntity = new ListaClienteCabeceraEntity();
            listaClienteCabeceraEntity.cliente_id = listaConsClienteCabeceraEntities.get(i).getCliente_id();
            listaClienteCabeceraEntity.nombrecliente = listaConsClienteCabeceraEntities.get(i).getNombrecliente();
            listaClienteCabeceraEntity.direccion = listaConsClienteCabeceraEntities.get(i).getDireccion();
            listaClienteCabeceraEntity.moneda = listaConsClienteCabeceraEntities.get(i).getMoneda();
            listaClienteCabeceraEntity.domembarque_id=listaConsClienteCabeceraEntities.get(i).getDomembarque_id();
            listaClienteCabeceraEntity.saldo=listaConsClienteCabeceraEntities.get(i).getSaldo();
            listaClienteCabeceraEntity.rucdni=listaConsClienteCabeceraEntities.get(i).getRucdni();
            listaClienteCabeceraEntity.categoria=listaConsClienteCabeceraEntities.get(i).getCategoria();
            listaClienteCabeceraEntity.linea_credito=listaConsClienteCabeceraEntities.get(i).getLinea_credito();
            listaClienteCabeceraEntity.terminopago_id=listaConsClienteCabeceraEntities.get(i).getTerminopago_id();
            listaClienteCabeceraEntity.compania_id = listaConsClienteCabeceraEntities.get(i).getCompania_id();
            listaClienteCabeceraEntity.zona_id = listaConsClienteCabeceraEntities.get(i).getZona_id();
            listaClienteCabeceraEntity.ordenvisita = listaConsClienteCabeceraEntities.get(i).getOrdenvisita();
            listaClienteCabeceraEntity.zona = listaConsClienteCabeceraEntities.get(i).getZona();
            listaClienteCabeceraEntity.telefonofijo = listaConsClienteCabeceraEntities.get(i).getTelefonofijo();
            listaClienteCabeceraEntity.telefonomovil = listaConsClienteCabeceraEntities.get(i).getTelefonomovil();
            listaClienteCabeceraEntity.correo = listaConsClienteCabeceraEntities.get(i).getCorreo();
            listaClienteCabeceraEntity.ubigeo_id = listaConsClienteCabeceraEntities.get(i).getUbigeo_id();
            listaClienteCabeceraEntity.tipocambio = listaConsClienteCabeceraEntities.get(i).getTipocambio();
            listaClienteCabeceraEntities.add(listaClienteCabeceraEntity);
        }
        clienteagregado=true;
        obtenerRutaVendedorNoRuta.execute();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Ruta Vendedor");
        setHasOptionsMenu(true);
        context=getContext();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_ruta_vendedor_no_ruta_view, container, false);
        listrutavendedornoruta=v.findViewById(R.id.listrutavendedornoruta);
        fabagregarclientenoruta=v.findViewById(R.id.fabagregarclientenoruta);
        tv_cantidad_cliente_no_ruta_total=v.findViewById(R.id.tv_cantidad_cliente_no_ruta_total);
        tv_cantidad_cliente_no_ruta_visita=v.findViewById(R.id.tv_cantidad_cliente_no_ruta_visita);
        tv_cantidad_cliente_no_ruta_pedido=v.findViewById(R.id.tv_cantidad_cliente_no_ruta_pedido);
        tv_cantidad_cliente_no_ruta_cobranza=v.findViewById(R.id.tv_cantidad_cliente_no_ruta_cobranza);

        obtenerRutaVendedorNoRuta=new ObtenerRutaVendedorNoRuta();
        obtenerRutaVendedorNoRuta.execute();
        fabagregarclientenoruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String Fragment="RutaVendedorNorutaView";
                String accion="agregarClienteNoRuta";
                String compuesto=Fragment+"-"+accion;
                String objeto="";
                if(mListener!=null)
                {
                    mListener.onFragmentInteraction(compuesto, objeto);
                }
            }
        });
        return v;

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String Dato,Object Lista);
    }

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("jpcm","se cargo ruta vendedorrr");
        ListenerBackPress.setTemporaIdentityFragment("rutaVendedor");
        ListenerBackPress.setCurrentFragment("RutaVendedorView");

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
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("jpcm","se cargo ruta vendedorrr");
        ListenerBackPress.setTemporaIdentityFragment("rutaVendedor");
        ListenerBackPress.setCurrentFragment("RutaVendedorView");
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

    public class ObtenerRutaVendedorNoRuta extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... arg0) {

            try {

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return "";
        }

        protected void onPostExecute(Object result)
        {
                String fecha="";
                Log.e("REOS","se cargo no ruta vendedorrr");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();

                FormulasController formulasController=new FormulasController(getContext());
                RutaVendedorSQLiteDao rutaVendedorSQLiteDao=new RutaVendedorSQLiteDao(getContext());
                ArrayList<ListaClienteCabeceraEntity> listaClienteCabeceraEntityconnoruta=new ArrayList<>();
                String chk_ruta="0";
                //formulasController.RegistrarRutaVendedor(listaClienteCabeceraEntities,fecha,"0");
                //Evalua si RutaVendedor ya tiene clientes
                fecha =dateFormat.format(date);

                if(clienteagregado)
                {
                    formulasController.RegistrarRutaVendedor(listaConsClienteCabeceraEntities, fecha, chk_ruta);
                    clienteagregado=false;
                }

                listaClienteCabeceraEntityconnoruta=rutaVendedorSQLiteDao.ObtenerRutaVendedorPorFecha(fecha,chk_ruta,getContext());



                listaClienteCabeceraAdapter = new ListaClienteCabeceraAdapter(getActivity(), ListaClienteCabeceraDao.getInstance().getLeads(listaClienteCabeceraEntityconnoruta));
                listrutavendedornoruta.setAdapter(listaClienteCabeceraAdapter);
                obtenerRutaVendedorNoRuta=new ObtenerRutaVendedorNoRuta();


                int visita=0,pedido=0,cobranza=0;
                for(int i=0;i<listaClienteCabeceraEntityconnoruta.size();i++)
                {
                    if(listaClienteCabeceraEntityconnoruta.get(i).getChk_visita().equals("1"))
                    {
                        visita++;
                    }
                    if(listaClienteCabeceraEntityconnoruta.get(i).getChk_pedido().equals("1"))
                    {
                        pedido++;
                    }
                    if(listaClienteCabeceraEntityconnoruta.get(i).getChk_cobranza().equals("1"))
                    {
                        cobranza++;
                    }
                }
                tv_cantidad_cliente_no_ruta_total.setText(String.valueOf(listaClienteCabeceraEntityconnoruta.size()));
                tv_cantidad_cliente_no_ruta_visita.setText(String.valueOf(visita));
                tv_cantidad_cliente_no_ruta_cobranza.setText(String.valueOf(cobranza));
                tv_cantidad_cliente_no_ruta_pedido.setText(String.valueOf(pedido));
                //getActivity().setTitle("Ruta Vendedor");
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
        String text = newText;
        listaClienteCabeceraAdapter.filter(text);
        return true;
    }

}