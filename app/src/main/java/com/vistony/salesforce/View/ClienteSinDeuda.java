package com.vistony.salesforce.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.vistony.salesforce.Controller.Adapters.ListaClienteCabeceraAdapter;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.Adapters.ListaClienteCabeceraDao;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class ClienteSinDeuda extends Fragment implements SearchView.OnQueryTextListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ClienteSQlite clienteSQlite;
    private MenuView menuView;
    public static OnFragmentInteractionListener mListener;
    ListView listcliente2;
    ArrayList<ListaClienteCabeceraEntity> listaClienteSQLiteEntity;
    ListaClienteCabeceraAdapter listaClienteCabeceraAdapter2;
    String texto;
    TextView tv;
    private View v;
    Button btnconsultar;
    private SearchView mSearchView;
    TextView tv_cantidad_cliente_cabecera,tv_monto_cliente_cabecera,tv_monto_cliente_cabecera_dolares;
    int cantidad_cliente_cabecera;
    private final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE=2;
    //ConfigImpresoraView configImpresoraView;


    public ClienteSinDeuda() {
        // Required empty public constructor
    }

    public static ClienteSinDeuda newInstance(String param1, String param2) {
        ClienteSinDeuda fragment = new ClienteSinDeuda();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ClienteCabeceraView newInstancia(Object param1) {
        ClienteCabeceraView fragment = new ClienteCabeceraView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, String.valueOf(param1));
        fragment.setArguments(args);
        String Fragment="ClienteCabeceraView";
        String accion="inicioClienteCabeceraView";
        String compuesto=Fragment+"-"+accion;

        if(mListener!=null)
        {
            mListener.onFragmentInteraction(compuesto, param1);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clienteSQlite = new ClienteSQlite(getContext());
        menuView = new MenuView();


        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        /*String vinculaimpresora="0";
        ArrayList<ConfiguracionSQLEntity> arraylistConfiguracionentity;

        ConfiguracionSQLiteDao configuracionSQLiteDao2 =  new ConfiguracionSQLiteDao(getContext());
        arraylistConfiguracionentity=configuracionSQLiteDao2.ObtenerConfiguracion();
        for(int i=0;i<arraylistConfiguracionentity.size();i++)
        {
            vinculaimpresora=arraylistConfiguracionentity.get(i).getVinculaimpresora();

        }
        if ((vinculaimpresora.equals("0"))) {
            configImpresoraView = new ConfigImpresoraView();
            configImpresoraView.OpenPrinter(
                    //MenuView.indicador
                    "0"
                    , getContext());
            SesionEntity.loginSesion = "1";
        }
        MenuView.indicador="3";*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_cliente_sin_deuda, container, false);
        getActivity().setTitle("Clientes");


        ListenerBackPress.setCurrentFragment("CLIENTESINDEUDA");

        listcliente2 = (ListView) v.findViewById(R.id.listcliente);
        listaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        //obtenerSQLiteCliente = new ClienteDeuda.ObtenerSQLiteCliente();
        tv_monto_cliente_cabecera= (TextView) v.findViewById(R.id.tv_monto_cliente_cabecera);
        tv_cantidad_cliente_cabecera= (TextView) v.findViewById(R.id.tv_cantidad_cliente_cabecera);
        tv_monto_cliente_cabecera_dolares= (TextView) v.findViewById(R.id.tv_monto_cliente_cabecera_dolares);

        listcliente2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("jpcm","Se dio click a la imagen");

                for(int i=0;i<ListaClienteCabeceraAdapter.ArraylistaClienteCabeceraEntity.size();i++)
                {
                    texto=String.valueOf(ListaClienteCabeceraAdapter.ArraylistaClienteCabeceraEntity.get(i).getCliente_id()) ;
                }

                String Fragment="ClientCabeceraView";
                try {
                    mListener.onFragmentInteraction(Fragment,texto);
                }catch (Exception e)
                {
                    Log.d("jpcm","Error "+e);
                    System.out.println(e.getMessage());
                }

            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/


    /*@Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        listaClienteCabeceraAdapter2.filter(text);
        return true;
    }

    private void cargarClientesSqlite(){
        listaClienteSQLiteEntity.clear();
        listaClienteSQLiteEntity = clienteSQlite.ObtenerClienteSinDeuda();
        float monto_cliente_cabecera=0,monto_cliente_cabecera_dolares=0;
        DecimalFormat format =  new DecimalFormat("###,###.##");
        cantidad_cliente_cabecera=listaClienteSQLiteEntity.size();
        tv_cantidad_cliente_cabecera.setText(String.valueOf(cantidad_cliente_cabecera));

        for(int i=0;i<listaClienteSQLiteEntity.size();i++)
        {
            if(listaClienteSQLiteEntity.get(i).getMoneda().equals("SOL"))
            {
                monto_cliente_cabecera=monto_cliente_cabecera+Float.parseFloat(listaClienteSQLiteEntity.get(i).getSaldo());
            }
            else if(listaClienteSQLiteEntity.get(i).getMoneda().equals("USD"))
            {
                monto_cliente_cabecera_dolares=monto_cliente_cabecera_dolares+Float.parseFloat(listaClienteSQLiteEntity.get(i).getSaldo());
            }
        }

        tv_monto_cliente_cabecera.setText(String.valueOf(format.format(monto_cliente_cabecera)));
        tv_monto_cliente_cabecera_dolares.setText(String.valueOf(format.format(monto_cliente_cabecera_dolares)));
        listaClienteCabeceraAdapter2 = new ListaClienteCabeceraAdapter(getActivity(), ListaClienteCabeceraDao.getInstance().getLeads(listaClienteSQLiteEntity));
        listcliente2.setAdapter(listaClienteCabeceraAdapter2);

        listcliente2.setTextFilterEnabled(true);
        //setupSearchView();
    }
    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Buscar Cliente");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.menu.menu_view:

                // Not implemented here
                return false;
            case R.menu.menu_cliente_cabecera:

                // Do Fragment menu item stuff here
                return true;

            default:
                break;
        }

        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_cliente_cabecera, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView();
        cargarClientesSqlite();
        super.onCreateOptionsMenu(menu, inflater);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction( String tag,Object data);
    }
}
