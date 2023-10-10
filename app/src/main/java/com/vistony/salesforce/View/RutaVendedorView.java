package com.vistony.salesforce.View;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

//import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import com.vistony.salesforce.Controller.Adapters.ListaRutaVendedorAdapter;
import com.vistony.salesforce.Controller.Adapters.PageAdapter;
import com.vistony.salesforce.Controller.Adapters.TabsPagerAdapter;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.ConfiguracionSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.ConfiguracionSQLEntity;
import com.vistony.salesforce.Entity.SQLite.RutaVendedorSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;


public class RutaVendedorView extends Fragment //implements SearchView.OnQueryTextListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ClienteSQlite clienteSQlite;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //String texto;
    View v;
    ListView listcliente;
    ArrayList<RutaVendedorSQLiteEntity> listaRutaVendedorSQLiteEntity;

    private ProgressDialog pd;
    ListaRutaVendedorAdapter listaRutaVendedorAdapter;
    //private SearchView mSearchView;
    RutaVendedorSQLiteDao rutaVendedorSQLiteDao;
    public static OnFragmentInteractionListener mListener;
    SearchView mSearchView2;
    TextView tv_cantidad_ruta_vendedor,tv_monto_ruta_vendedor;
    private Toolbar tollbartab;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;


    private final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE=255;
    ConfigImpresoraView configImpresoraView;
    public static TabItem tabiruta,tabinoruta;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "Top Rated", "Games" };
    public RutaVendedorView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RutaVendedorView.
     */
    // TODO: Rename and change types and number of parameters
    public static RutaVendedorView newInstance(String param1, String param2) {
        RutaVendedorView fragment = new RutaVendedorView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static RutaVendedorView newInstancia(Object param1) {
        RutaVendedorView fragment = new RutaVendedorView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, String.valueOf(param1));
        fragment.setArguments(args);
        String Fragment="RutaVendedorView";

        Log.e("JEPICAMEE","=> newInstancia");


        String accion="inicioRutaVendedorView";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,param1);

        return fragment;
    }

    public static RutaVendedorView newInstanciaMenu(Object param1)
    {
        RutaVendedorView fragment = new RutaVendedorView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, String.valueOf(param1));
        fragment.setArguments(args);

        String Fragment="RutaVendedorView";
        Log.e("JEPICAMEE","=> newInstanciaMenu");
        String accion="inicioRutaVendedorView";
        String compuesto=Fragment+"-"+accion;
        if(mListener!=null) {
            mListener.onFragmentInteraction(compuesto, param1);
        }
        return fragment;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Ruta Vendedor");
        clienteSQlite = new ClienteSQlite(getContext());
        rutaVendedorSQLiteDao = new RutaVendedorSQLiteDao(getContext());
        //setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(SesionEntity.Print.equals("Y"))
        {
            String vinculaimpresora="0",serialnumber="0";
            ArrayList<ConfiguracionSQLEntity> arraylistConfiguracionentity;
            ConfiguracionSQLiteDao configuracionSQLiteDao2 =  new ConfiguracionSQLiteDao(getContext());

            arraylistConfiguracionentity=configuracionSQLiteDao2.ObtenerConfiguracion();
            for(int i=0;i<arraylistConfiguracionentity.size();i++)
            {
                vinculaimpresora=arraylistConfiguracionentity.get(i).getVinculaimpresora();
                serialnumber=arraylistConfiguracionentity.get(i).getPapel();
                String deviceserialnumber=serialnumber;
                try
                {
                    String[] compuestoserialnumber = deviceserialnumber.split(" ");
                    String serialnumberbluetooh = compuestoserialnumber[0];
                    SesionEntity.serialnumber=serialnumberbluetooh;
                }catch (Exception e)
                {
                    Toast.makeText(getContext(), "Impresora Blueetooh no fue configurada Correctamente", Toast.LENGTH_SHORT).show();
                }

            }
            if ((vinculaimpresora.equals("0"))) {
                configImpresoraView = new ConfigImpresoraView();
                configImpresoraView.OpenPrinter(
                        //MenuView.indicador
                        "0"
                        , getContext());
                SesionEntity.loginSesion = "1";
            }
        }

        MenuView.indicador="3";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_ruta_vendedor_view,container,false);
        tollbartab=v.findViewById(R.id.tollbartab);

        viewPager=v.findViewById(R.id.viewpager);
        tabLayout=v.findViewById(R.id.tablayout);
//        tabinoruta=v.findViewById(R.id.tabinoruta);
//        tabiruta=v.findViewById(R.id.tabiruta);

        //tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_directions_walk_24).setText("RUTA"));
        //tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_directions_walk_24).setText("NO RUTA"));

        pageAdapter= new PageAdapter(getChildFragmentManager());
        //mAdapter=new TabsPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        pageAdapter.addFRagment(new RutaVendedorRutaView(),"RUTA");
        pageAdapter.addFRagment(new RutaVendedorNoRutaView(),"NO RUTA");
        viewPager.setAdapter(pageAdapter);
        //tabLayout.getTabAt(0).getCustomView()

        //viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return v;

    }







    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/
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
        void onFragmentInteraction(String Dato,Object Lista);
    }


    /*
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
                        {
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
*/


}