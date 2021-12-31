package com.vistony.salesforce.View;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.vistony.salesforce.Controller.Utilitario.ResumenDiarioPDF;
import com.vistony.salesforce.Dao.Retrofit.PriceListRepository;
import com.vistony.salesforce.Dao.Retrofit.ResumenDiarioRepository;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuConsultaPedidosView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuConsultaPedidosView extends Fragment  implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    CardView cv_ordenventafecha,cv_resumen_diario,cv_consulta_stock;
    TextView tv_fecha_resumen_diario;
    ImageButton imb_consultar_fecha_resumen_diario;
    Date date,datesap;
    String fecha,fechasap;
    SimpleDateFormat dateFormat,dateFormatsap;
    private  int diadespacho,mesdespacho,anodespacho;
    private static DatePickerDialog oyenteSelectorFecha;
    private ResumenDiarioRepository resumenDiarioRepository;
    private ProgressDialog pd;

    public MenuConsultaPedidosView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuConsultaPedidosView.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuConsultaPedidosView newInstance(String param1, String param2) {
        MenuConsultaPedidosView fragment = new MenuConsultaPedidosView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    MenuConsultasView.OnFragmentInteractionListener mListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("Menu Consultas Pedidos");
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
        v= inflater.inflate(R.layout.fragment_menu_consulta_pedidos_view, container, false);
        cv_ordenventafecha=v.findViewById(R.id.cv_ordenventafecha);
        cv_resumen_diario=v.findViewById(R.id.cv_resumen_diario);
        cv_consulta_stock=v.findViewById(R.id.cv_consulta_stock);
        resumenDiarioRepository = new ViewModelProvider(getActivity()).get(ResumenDiarioRepository.class);
        cv_ordenventafecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="MenuConsultasView";
                String accion="historicoordenventa";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,"");
            }
        });

        cv_resumen_diario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String Fragment="MenuConsultasPedidosView";
                String accion="iniciar";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,"");*/
                AlertaObtenerResumenDiario("Seleccione la Fecha a generar el Reporte del Dia:").show();
            }
        });
        cv_consulta_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="MenuConsultasPedidosView";
                String accion="consulta_stock";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,"");
            }
        });
        return v;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        ListenerBackPress.setCurrentFragment("MenuConsultasView");
        ListenerBackPress.setTemporaIdentityFragment("onDetach");
    }

    @Override
    public void onAttach(Context context) {
        ListenerBackPress.setCurrentFragment("MenuConsultasView");
        ListenerBackPress.setTemporaIdentityFragment("onAttach");
        super.onAttach(context);
        if (context instanceof MenuAccionView.OnFragmentInteractionListener) {
            mListener = (MenuConsultasView.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public Dialog AlertaObtenerResumenDiario(String tipo) {
        String mensaje=tipo;
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_resumen_diario);
        TextView textTitle = dialog.findViewById(R.id.tv_titulo);
        textTitle.setText("ADVERTENCIA!");
        TextView textMsj = dialog.findViewById(R.id.tv_mensaje);
        textMsj.setText(mensaje);
        tv_fecha_resumen_diario = dialog.findViewById(R.id.tv_fecha_resumen_diario);
        imb_consultar_fecha_resumen_diario = dialog.findViewById(R.id.imb_consultar_fecha_resumen_diario);
        imb_consultar_fecha_resumen_diario.setOnClickListener(this);
        //obtenerSQLiteHojaDespachoCabecera=new ObtenerSQLiteHojaDespachoCabecera();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateFormatsap = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        date = new Date();
        datesap = new Date();
        fecha =dateFormat.format(date);
        tv_fecha_resumen_diario.setText(fecha);
        fechasap=dateFormatsap.format(datesap);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(getActivity());
                pd = ProgressDialog.show(getActivity(), "Por favor espere", "Enviando Datos de Cobranza POS", true, false);
                ///////////////////////////// ENVIAR DEPOSITOS ANULADOS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                resumenDiarioRepository.getResumenDiario(SesionEntity.compania_id ,SesionEntity.imei,fechasap,getContext(),fechasap).observe(getActivity(), data -> {
                    Log.e("REOS", "resumenDiarioRepository.data" + data.toString());
                    Log.e("REOS", "resumenDiarioRepository.data.size()" + data.size());

                    ResumenDiarioPDF resumenDiarioPDF=new ResumenDiarioPDF();
                    resumenDiarioPDF.generarPdf(getContext(),data,fechasap);
                    pd.dismiss();
                });


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
        tv_fecha_resumen_diario.setText(year + "-" + mes + "-" + dia);
        fechasap=year + "" + mes + "" + dia;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imb_consultar_fecha_resumen_diario:
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
            default:
                break;
        }
    }



}