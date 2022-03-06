package com.vistony.salesforce.View;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

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

import com.vistony.salesforce.Controller.Adapters.ListaHistoricContainerSalesAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Dao.Adapters.ListaHistoricContainerSalesDao;
import com.vistony.salesforce.Dao.Retrofit.HistoricContainerSalesRepository;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricContainerSKU#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricContainerSKU extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    static ListView lv_HistoricContainerSKU;
    SimpleDateFormat dateFormat;
    Date date;
    static String fecha;
    static Context context;
    static ListaHistoricContainerSalesAdapter listaHistoricContainerSalesAdapter;
    static HistoricContainerSalesRepository historicContainerSalesRepository;
    public static OnFragmentInteractionListener mListener;
    static public String CardCode="",CardName="",LineOfBussiness="";
    MenuItem buscar_cliente;
    static LifecycleOwner activity;
    static Activity Activity;
    static TextView tv_cantidad_historico_venta,tv_monto_historico_venta,tv_linea_negocio;
    static private ProgressDialog pd;
    public HistoricContainerSKU() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoricContainerSKU.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricContainerSKU newInstance(String param1, String param2) {
        HistoricContainerSKU fragment = new HistoricContainerSKU();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static HistoricContainerSKU newInstanceListarClienteSKU(Object object) {
        HistoricContainerSKU fragment = new HistoricContainerSKU();
        ListarHistoricContainerSalesSKU((List<HistoricContainerSalesEntity>) object);
        //fragment.setArguments(args);
        return fragment;
    }

    public static HistoricContainerSaleView newInstanceRecibirCliente(Object object) {
        HistoricContainerSaleView fragment = new HistoricContainerSaleView();

        ArrayList<ListaClienteCabeceraEntity> Listado=(ArrayList<ListaClienteCabeceraEntity>) object;
        for(int i=0;i<Listado.size();i++)
        {
            CardCode=Listado.get(i).getCliente_id();
            CardName=Listado.get(i).getNombrecliente();
            LineOfBussiness=Listado.get(i).getLineofbussiness();
        }
        ObtenerListaHistoricContainerSKU();
        Induvis.setTituloContenedor(CardName,Activity);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Induvis.setTituloContenedor("HISTORICO SKU",getActivity());
        activity=getActivity();
        Activity=getActivity();
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_historic_container_s_k_u, container, false);
        lv_HistoricContainerSKU=v.findViewById(R.id.lv_HistoricContainerSKU);
        tv_cantidad_historico_venta=v.findViewById(R.id.tv_cantidad_historico_venta);
        tv_monto_historico_venta=v.findViewById(R.id.tv_monto_historico_venta);
        tv_linea_negocio=v.findViewById(R.id.tv_linea_negocio);

        dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        date = new Date();
        context = getContext();
        fecha = dateFormat.format(date);
        historicContainerSalesRepository = new ViewModelProvider(getActivity()).get(HistoricContainerSalesRepository.class);

        //ObtenerListaHistoricContainerSKU();
        return v;
    }

    static public void ListarHistoricContainerSalesSKU(List<HistoricContainerSalesEntity> lista){
        Log.e("REOS","HistoricContainerSaleFocoView-ListarHistoricContainerSalesFoco-lista.SIZE():"+lista.size());
        try
        {
        listaHistoricContainerSalesAdapter = new ListaHistoricContainerSalesAdapter(context,  ListaHistoricContainerSalesDao.getInstance().getLeads(lista,"SKU"),"SKU");
            lv_HistoricContainerSKU.setAdapter(listaHistoricContainerSalesAdapter);
            tv_cantidad_historico_venta.setText(String.valueOf(lista.size()));
            double suma=0;
            for(int i=0;i<lista.size();i++)
            {
                suma=suma+Double.parseDouble(lista.get(i).getMontototal());
            }
            tv_monto_historico_venta.setText(Convert.currencyForView(String.valueOf(suma)));
            tv_linea_negocio.setText(LineOfBussiness);
        }catch (Exception e)
        {
            Log.e("REOS","HistoricContainerSaleFocoView-ListarHistoricContainerSalesFoco-e:"+e.toString());
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("REOS","HistoricContainerSalesFamilyView-onAttach");
        ListenerBackPress.setCurrentFragment("FormParametrosView");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        Log.e("REOS","HistoricContainerSalesFamilyView-onDetach");
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
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
                String accion="dialogoagregarclienteSKU";
                String compuesto=Fragment+"-"+accion;
                String objeto="historicContainerSaleViewSKU";
                mListener.onFragmentInteraction(compuesto, objeto);
                return false;
            default:
                break;
        }
        return false;
    }

    static public void ObtenerListaHistoricContainerSKU()
    {
        pd = new ProgressDialog(Activity);
        pd = ProgressDialog.show(Activity, "Por favor espere", "Consultando Facturas", true, false);
        try {
            historicContainerSalesRepository.getHistoricContainerSales(
                    SesionEntity.imei,
                    context,
                    CardCode,
                    Induvis.changeMonth(fecha,-6),
                    fecha,
                    "SKU",
                    "SKU"
            ).observe(activity, data -> {
                Log.e("Jepicame","=>"+data);

                if(data==null)
                {
                    lv_HistoricContainerSKU.setAdapter(null);
                    Toast.makeText(context, "No se encontraron Facturas", Toast.LENGTH_SHORT).show();
                    alertdialogInformative(context,"IMPORTANTE!!!","No se encontraron Documentos para realizar el Calculo").show();
                }
                else {
                    ListarHistoricContainerSalesSKU(data);
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("REOS","HistoricContainerSaleFocoView-onCreateView-e:"+e.toString());
        }
        pd.dismiss();
    }

    static private Dialog alertdialogInformative(Context context, String titulo, String message) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        TextView textViewMsj=(TextView) dialog.findViewById(R.id.textViewMsj);
        TextView text=(TextView) dialog.findViewById(R.id.text);
        text.setText(titulo);
        textViewMsj.setText(message);
        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

}