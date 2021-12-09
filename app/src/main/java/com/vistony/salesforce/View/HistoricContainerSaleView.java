package com.vistony.salesforce.View;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.vistony.salesforce.Controller.Adapters.PageAdapter;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricContainerSaleView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricContainerSaleView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    private Toolbar tollbartab;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;
    MenuItem buscar_cliente;
    public static RutaVendedorNoRutaView.OnFragmentInteractionListener mListener;
    static public String CardCode="";
    public HistoricContainerSaleView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoricContainerSale.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricContainerSaleView newInstance(String param1, String param2) {
        HistoricContainerSaleView fragment = new HistoricContainerSaleView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static HistoricContainerSaleView newInstanceRecibirCliente(Object object) {
        HistoricContainerSaleView fragment = new HistoricContainerSaleView();

        ArrayList<ListaClienteCabeceraEntity> Listado=(ArrayList<ListaClienteCabeceraEntity>) object;
        for(int i=0;i<Listado.size();i++)
        {
            CardCode=Listado.get(i).getCliente_id();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("REOS","HistoricContainerSaleView-onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        Log.e("REOS","HistoricContainerSaleView-onCreateView");
        v= inflater.inflate(R.layout.fragment_historic_container_sale, container, false);
        viewPager=v.findViewById(R.id.viewpager);
        tabLayout=v.findViewById(R.id.tablayout);
        pageAdapter= new PageAdapter(getChildFragmentManager());
        pageAdapter.addFRagment(new HistoricContainerSalesSemaforoView(),"SEMAFORO");
        pageAdapter.addFRagment(new HistoricContainerSalesFamilyView(),"FAMILIA");
        pageAdapter.addFRagment(new HistoricContainerSaleFocoView(),"FOCO");
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return v;

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_historic_container_sale, menu);
        buscar_cliente = menu.findItem(R.id.buscar_cliente);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.buscar_cliente:
                //alertaGetClient("Seleccione el Cliente:").show();
                String Fragment="HistoricContainerSaleView";
                String accion="dialogoagregarcliente";
                String compuesto=Fragment+"-"+accion;
                String objeto="historicContainerSaleView";
                mListener.onFragmentInteraction(compuesto, objeto);
                return false;
            default:
                break;
        }
        return false;
    }

    private Dialog alertaGetClient(String texto) {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_getclient_historic_container_sale);
        TextView titulo = dialog.findViewById(R.id.tv_titulo);
        TextView textMsj = dialog.findViewById(R.id.tv_mensaje);
        textMsj.setText(texto);
        ImageView image = dialog.findViewById(R.id.image);
        image.setImageResource(R.mipmap.logo_circulo);
        titulo.setText("IMPORTANTE!!!");
        ImageButton imb_obtener_cliente;
        TextView tv_nombre_cliente_historic;
        tv_nombre_cliente_historic = dialog.findViewById(R.id.tv_nombre_cliente_historic);
        imb_obtener_cliente = dialog.findViewById(R.id.imb_obtener_cliente);


        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        imb_obtener_cliente.setOnClickListener(v1 -> {
                        String Fragment="HistoricContainerSaleView";
                        String accion="dialogoagregarcliente";
                        String compuesto=Fragment+"-"+accion;
                        String objeto="";
                        mListener.onFragmentInteraction(compuesto, objeto);
                }
        );
        dialogButtonOK.setOnClickListener(v -> {
            //ObtenerParametrosCheck();
            dialog.dismiss();
        });
        dialogButtonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("jpcm","se cargo ruta vendedorrr");
        ListenerBackPress.setTemporaIdentityFragment("rutaVendedor");
        ListenerBackPress.setCurrentFragment("RutaVendedorView");
        if (context instanceof RutaVendedorNoRutaView.OnFragmentInteractionListener) {
            mListener = (RutaVendedorNoRutaView.OnFragmentInteractionListener) context;
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

}