package com.vistony.salesforce.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vistony.salesforce.AppExecutors;
import com.vistony.salesforce.Controller.Adapters.ListaDireccionClienteAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaDireccionClienteDao;
import com.vistony.salesforce.Dao.Retrofit.DireccionRepository;
import com.vistony.salesforce.Entity.Adapters.DireccionCliente;
import com.vistony.salesforce.R;

import java.util.ArrayList;

public class DireccionClienteView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView lv_direccioncliente;

    private String mParam1;
    private String mParam2;
    View v;
    public static OnFragmentInteractionListener mListener;
    static String codigocliente="";
    static MenuItem habilitar_direccioncliente;
    private ProgressDialog pd;
    Context context;

    private DireccionRepository direccionRepository;

    public DireccionClienteView() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DireccionClienteView newInstance(Object CodigoCliente) {
        DireccionClienteView fragment = new DireccionClienteView();
        codigocliente=(String) CodigoCliente;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static DireccionClienteView newInstanceDevuelveDireccion(DireccionCliente direccionSelecionada) {
        DireccionClienteView fragment = new DireccionClienteView();
        String Fragment="DireccionClienteView";
        String accion="nuevadireccion";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,direccionSelecionada);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        v =inflater.inflate(R.layout.fragment_direccion_cliente_view, container, false);

        lv_direccioncliente=v.findViewById(R.id.lv_direccioncliente);
        lv_direccioncliente.setLayoutManager(new LinearLayoutManager(getContext()));

        direccionRepository =  new ViewModelProvider(this).get(DireccionRepository.class);

        pd = new ProgressDialog(getActivity());
        pd = ProgressDialog.show(getActivity(), getActivity().getResources().getString(R.string.please_wait), getActivity().getResources().getString(R.string.querying_dates), true, false);

        getActivity().setTitle(getActivity().getResources().getString(R.string.adresses));
        AppExecutors executor=new AppExecutors();

        direccionRepository.getAddress(getActivity(),executor.diskIO(),codigocliente).observe(getActivity(), direcciones -> {
            if(direcciones!=null){
                ListaDireccionClienteAdapter listaDireccionClienteAdapter = new ListaDireccionClienteAdapter(direcciones,getContext());
                lv_direccioncliente.setAdapter(listaDireccionClienteAdapter);
            }else{
                Toast.makeText(getContext()
                        ,getActivity().getResources().getString(R.string.msm_data_available), Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().popBackStack();
            }

            pd.dismiss();
        });

        return v;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    @Override
    public void onAttach(Context context) {
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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_direccion_cliente, menu);
        habilitar_direccioncliente = menu.findItem(R.id.habilitar_direccioncliente);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.habilitar_direccioncliente:
                alertaHabilitarDireccionCliente(getActivity().getResources().getString(R.string.mse_enable_adresses)).show();
                return false;
            default:
                break;
        }
        return false;
    }
    private Dialog alertaHabilitarDireccionCliente(String texto) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_advertencia);

        TextView textMsj = dialog.findViewById(R.id.tv_texto);
        textMsj.setText(texto);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);


        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_direccioncliente.setEnabled(true);
                lv_direccioncliente.setClickable(true);
                lv_direccioncliente.setFocusable(true);
                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.mse_enable_sucessful), Toast.LENGTH_SHORT).show();

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