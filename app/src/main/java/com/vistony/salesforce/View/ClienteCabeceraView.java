package com.vistony.salesforce.View;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.vistony.salesforce.Controller.Adapters.ListaClienteCabeceraAdapter;
import com.vistony.salesforce.Controller.Adapters.PageAdapter;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.ConfiguracionSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.ConfiguracionSQLEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClienteCabeceraView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClienteCabeceraView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClienteCabeceraView extends Fragment /*implements SearchView.OnQueryTextListener*/{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ClienteSQlite clienteSQlite;
    private MenuView menuView;
    private ProgressDialog pd;
    public static OnFragmentInteractionListener mListener;
    ListView listcliente;
    ArrayList<String> listaInformacion;
    ArrayList<ListaClienteCabeceraEntity> listaClienteSQLiteEntity;
    ListaClienteCabeceraAdapter listaClienteCabeceraAdapter;
    //ObtenerSQLiteCliente obtenerSQLiteCliente;
    ListaClienteCabeceraAdapter ArraylistaClienteCabeceraEntity;
    String texto;
    TextView tv;
    private View v;
    Button btnconsultar;
    private SearchView mSearchView;
    public ArrayList<ListaClienteCabeceraEntity> listaClienteCabeceraEntities;
    TextView tv_cantidad_cliente_cabecera,tv_monto_cliente_cabecera,tv_monto_cliente_cabecera_dolares;
    int cantidad_cliente_cabecera,monto_cliente_cabecera;
    private final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE=2;
    ConfigImpresoraView configImpresoraView;



    private Toolbar tollbartab;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;

    public ClienteCabeceraView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClienteCabeceraView.
     */
    // TODO: Rename and change types and number of parameters
    public static ClienteCabeceraView newInstance(String param1, String param2) {
        ClienteCabeceraView fragment = new ClienteCabeceraView();
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
        String vinculaimpresora="0",serialnumber="0";

        ArrayList<ConfiguracionSQLEntity> arraylistConfiguracionentity;

        ConfiguracionSQLiteDao configuracionSQLiteDao2 =  new ConfiguracionSQLiteDao(getContext());
        arraylistConfiguracionentity=configuracionSQLiteDao2.ObtenerConfiguracion();
        for(int i=0;i<arraylistConfiguracionentity.size();i++)
        {
            vinculaimpresora=arraylistConfiguracionentity.get(i).getVinculaimpresora();
            serialnumber=arraylistConfiguracionentity.get(i).getPapel();
            String deviceserialnumber=serialnumber;
            String[] compuestoserialnumber= deviceserialnumber.split(" ");
            String serialnumberbluetooh= compuestoserialnumber[0];
            SesionEntity.serialnumber=serialnumberbluetooh;


        }


            if ((vinculaimpresora.equals("0"))) {
            configImpresoraView = new ConfigImpresoraView();
            configImpresoraView.OpenPrinter(
                    //MenuView.indicador
                    "0"
                    , getContext());
            SesionEntity.loginSesion = "1";
        }
        MenuView.indicador="3";


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_cliente_cabecera,container,false);

        getActivity().setTitle("Clientes");


        tollbartab=v.findViewById(R.id.tollbartab);
        viewPager=v.findViewById(R.id.viewpager);
        tabLayout=v.findViewById(R.id.tablayout);

        // setSupportActionBar(tollbar);

       // pageAdapter= new PageAdapter(getFragmentManager());
        pageAdapter= new PageAdapter(getChildFragmentManager());
        pageAdapter.addFRagment(new ClienteDeuda(),"CON DEUDA");
        pageAdapter.addFRagment(new ClienteSinDeuda(),"SIN DEUDA");


        viewPager.setAdapter(pageAdapter);

        tabLayout.setupWithViewPager(viewPager);
        return v;
    }


    private void obtenerLista() {
        listaInformacion=new ArrayList<String>();

        for (int i = 0; i< listaClienteSQLiteEntity.size(); i++){
            listaInformacion.add(listaClienteSQLiteEntity.get(i).getCliente_id());
                    //+" - " +listaClienteSQLiteEntity.get(i).getCompania_id());
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ListenerBackPress.setCurrentFragment("CLIENTE ASDASD");


       if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

   /* @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


       /******  COMENTADO DESCOMETNAR
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        mSearchView = (SearchView) searchItem.getActionView();
        //mSearchView=(SearchView) menu.findItem(R.id.app_bar_search);
                //v.findViewById(R.id.app_bar_search);

        setupSearchView();
        obtenerSQLiteCliente.execute();
*/

        super.onCreateOptionsMenu(menu, inflater);

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
        //void onFragmentInteraction(Uri uri);
        public void onFragmentInteraction(
                //ArrayList<ListaClienteCabeceraEntity> listaClienteCabeceraEntities
                String tag,Object data
        );
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
                       /* while (ContextCompat.checkSelfPermission(this,
                                Manifest.permission.READ_PHONE_STATE)
                                != PackageManager.PERMISSION_GRANTED )
                        {*/
                    if ((ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED))
                    {
                        // ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                        //        MY_PERMISSIONS_REQUEST_CAMERA
                        //1
                        // );
                        requestPermissions(new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
                    }
                    //break;
                    //}
                }
                break;
            }
        }

    }
}



