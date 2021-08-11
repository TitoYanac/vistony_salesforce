package com.vistony.salesforce.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

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

import com.vistony.salesforce.Controller.Adapters.ListaDireccionClienteAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaDireccionClienteDao;
import com.vistony.salesforce.Dao.Retrofit.DireccionClienteWS;
import com.vistony.salesforce.Entity.Adapters.ListaDireccionClienteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;

public class DireccionClienteView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView lv_direccioncliente;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    public static OnFragmentInteractionListener mListener;
    ListaDireccionClienteAdapter listaDireccionClienteAdapter;
    ObtenerSQLiteDireccionCliente obtenerSQLiteDireccionCliente;
    static String codigocliente="";
    static MenuItem habilitar_direccioncliente;
    private ProgressDialog pd;
    Context context;
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

    public static DireccionClienteView newInstanceDevuelveDireccion(Object ListaDireccionCliente) {
        DireccionClienteView fragment = new DireccionClienteView();
        String Fragment="DireccionClienteView";
        String accion="nuevadireccion";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,ListaDireccionCliente);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =inflater.inflate(R.layout.fragment_direccion_cliente_view, container, false);
        lv_direccioncliente=(ListView)v.findViewById(R.id.lv_direccioncliente);
        obtenerSQLiteDireccionCliente=new ObtenerSQLiteDireccionCliente();
        obtenerSQLiteDireccionCliente.execute();

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

    public class ObtenerSQLiteDireccionCliente extends AsyncTask<String, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Cargando Direcciones", true, false);
        }
        @Override
        protected Object doInBackground(String... arg0) {
            ArrayList<ListaDireccionClienteEntity> listaDireccionClienteEntity=new ArrayList<>();
            try {
                DireccionClienteWS direccionClienteWS=new DireccionClienteWS(getContext());
                listaDireccionClienteEntity=direccionClienteWS.getDClienteWS(
                        SesionEntity.imei,
                        codigocliente
                );


            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return listaDireccionClienteEntity;
        }

        protected void onPostExecute(Object result) {
            getActivity().setTitle("Direccion Cliente");
            ArrayList<ListaDireccionClienteEntity> Lista = (ArrayList<ListaDireccionClienteEntity>) result;
            if(Lista.isEmpty())
            {

            }
            else
            {

                listaDireccionClienteAdapter = new ListaDireccionClienteAdapter(getActivity(), ListaDireccionClienteDao.getInstance().getLeads(Lista));
                lv_direccioncliente.setAdapter(listaDireccionClienteAdapter);


            }
            pd.dismiss();
            Toast.makeText(getContext(), "Direccion Actualizada...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.habilitar_direccioncliente:
                alertaHabilitarDireccionCliente("Esta Seguro de Habilitar las Direcciones?").show();
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
                Toast.makeText(getContext(), "Se Habilito Correctamente las Direcciones", Toast.LENGTH_SHORT).show();

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
    public AlertDialog alertaHabilitarDireccionCliente2() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia")
                .setMessage("Esta Seguro de Habilitar las Direcciones?")
                .setPositiveButton("OK",

                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lv_direccioncliente.setEnabled(true);
                                lv_direccioncliente.setClickable(true);
                                lv_direccioncliente.setFocusable(true);
                                Toast.makeText(getContext(), "Se Habilito Correctamente las Direcciones", Toast.LENGTH_SHORT).show();

                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

        return builder.create();
    }
}