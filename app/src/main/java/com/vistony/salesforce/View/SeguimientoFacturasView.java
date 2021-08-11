package com.vistony.salesforce.View;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.vistony.salesforce.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeguimientoFacturasView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeguimientoFacturasView extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    ListView lvseguimientofacturas;
    ImageButton imb_calendario_seguimiento_facturas;
    TextView tv_fecha_seguimiento_facturas;
    ImageButton btn_consultar_fecha_seguimiento_facturas;
    private  int diadespacho,mesdespacho,anodespacho;
    private static DatePickerDialog oyenteSelectorFecha;
    public SeguimientoFacturasView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeguimientoFacturasView.
     */
    // TODO: Rename and change types and number of parameters
    public static SeguimientoFacturasView newInstance(String param1, String param2) {
        SeguimientoFacturasView fragment = new SeguimientoFacturasView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        v= inflater.inflate(R.layout.fragment_seguimiento_facturas_view, container, false);
        btn_consultar_fecha_seguimiento_facturas = (ImageButton) v.findViewById(R.id.btn_consultar_fecha_seguimiento_facturas);
        lvseguimientofacturas = (ListView) v.findViewById(R.id.lvseguimientofacturas);
        imb_calendario_seguimiento_facturas = (ImageButton) v.findViewById(R.id.imb_calendario_seguimiento_facturas);
        tv_fecha_seguimiento_facturas = (TextView) v.findViewById(R.id.tv_fecha_seguimiento_facturas);



        return v;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String ano="",mes="",dia="";

        mes=String.valueOf(month+1);
        dia=String.valueOf(dayOfMonth);
        if(mes.length()==1)
        {
            mes='0'+mes;
        }
        if(dia.length()==1)
        {
            dia='0'+dia;
        }

        tv_fecha_seguimiento_facturas.setText(year + "-" + mes + "-" + dia);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imb_calendario_seguimiento_facturas:
                final Calendar c1 = Calendar.getInstance();
                diadespacho = c1.get(Calendar.DAY_OF_MONTH);
                mesdespacho = c1.get(Calendar.MONTH);
                anodespacho = c1.get(Calendar.YEAR);
                oyenteSelectorFecha = new DatePickerDialog(getContext(),this,
                        anodespacho,
                        mesdespacho,
                        diadespacho
                );
                oyenteSelectorFecha.show();


                break;
            case R.id.btn_consultar_fecha_seguimiento_facturas:
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

}