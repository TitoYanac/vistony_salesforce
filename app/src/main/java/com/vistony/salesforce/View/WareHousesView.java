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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vistony.salesforce.Controller.Adapters.ListWareHousesAdapter;
import com.vistony.salesforce.Controller.Adapters.ListaHistoricContainerSalesAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Dao.Adapters.ListWareHouseDao;
import com.vistony.salesforce.Dao.Adapters.ListaHistoricContainerSalesDao;
import com.vistony.salesforce.Dao.Retrofit.HistoricContainerSalesRepository;
import com.vistony.salesforce.Dao.Retrofit.WareHousesRepository;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.WareHousesEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.text.ParseException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WareHousesView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WareHousesView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    static private ProgressDialog pd;
    static LifecycleOwner activity;
    static android.app.Activity Activity;
    static WareHousesRepository wareHousesRepository;
    static String ItemCode;
    static Context context;
    static ListView lv_warehouses;
    static ListWareHousesAdapter listWareHousesAdapter;
    static TextView tv_warehouses_count,tv_warehouses_amount;
    public WareHousesView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WareHousesView.
     */
    // TODO: Rename and change types and number of parameters
    public static WareHousesView newInstance(String param1, String param2) {
        WareHousesView fragment = new WareHousesView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static WareHousesView newInstanceGetItemCode (Object objeto) {
        WareHousesView fragment = new WareHousesView();
        Bundle args = new Bundle();
        ItemCode=(String) objeto;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        v= inflater.inflate(R.layout.fragment_ware_houses_view, container, false);
        lv_warehouses=v.findViewById(R.id.lv_warehouses);
        tv_warehouses_count=v.findViewById(R.id.tv_warehouses_count);
        tv_warehouses_amount=v.findViewById(R.id.tv_warehouses_amount);
        ObtenerListaHistoricContainerSKU();
        wareHousesRepository = new ViewModelProvider(getActivity()).get(WareHousesRepository.class);
        ObtenerListaHistoricContainerSKU();
        return v;
    }

    static public void ObtenerListaHistoricContainerSKU()
    {
        pd = new ProgressDialog(Activity);
        pd = ProgressDialog.show(Activity, "Por favor espere", "Consultando Productos", true, false);

        wareHousesRepository.getWareHouses(
                SesionEntity.imei,
                context,
                ItemCode
        ).observe(activity, data -> {
            Log.e("Jepicame","=>"+data);

            if(data==null)
            {
                lv_warehouses.setAdapter(null);
                Toast.makeText(context, "No se encontraron Facturas", Toast.LENGTH_SHORT).show();
                alertdialogInformative (context,"IMPORTANTE!!!","No se encontraron Documentos para realizar el Calculo").show();
            }
            else {
                getListWareHouse(data);
            }
        });
        pd.dismiss();
    }

    static public void getListWareHouse(List<WareHousesEntity> lista){

        Log.e("REOS","HistoricContainerSaleFocoView-ListarHistoricContainerSalesFoco-lista.SIZE():"+lista.size());
        try
        {
            listWareHousesAdapter = new ListWareHousesAdapter(context,  ListWareHouseDao.getInstance().getLeads(lista));
            lv_warehouses.setAdapter(listWareHousesAdapter);
            tv_warehouses_count.setText(String.valueOf(lista.size()));
            double suma=0;
            for(int i=0;i<lista.size();i++)
            {
                suma=suma+Double.parseDouble(lista.get(i).getEnStock());
            }
            tv_warehouses_amount.setText(Convert.currencyForView(String.valueOf(suma)));

        }catch (Exception e)
        {
            Log.e("REOS","HistoricContainerSaleFocoView-ListarHistoricContainerSalesFoco-e:"+e.toString());
        }
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