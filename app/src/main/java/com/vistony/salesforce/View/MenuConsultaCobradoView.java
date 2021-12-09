package com.vistony.salesforce.View;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.vistony.salesforce.Controller.Utilitario.QuotasPerCustomerPDF;
import com.vistony.salesforce.Dao.Retrofit.QuotasPerCustomerDetailRepository;
import com.vistony.salesforce.Dao.Retrofit.QuotasPerCustomerHeadRepository;
import com.vistony.salesforce.Dao.Retrofit.QuotasPerCustomerInvoiceRepository;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerInvoiceEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuConsultaCobradoView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuConsultaCobradoView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    CardView cv_cobradoporfecha,cv_calculo_cuotas;
    public static OnFragmentInteractionListener mListener;
    static Dialog dialog=null;
    static public String CardCode="",CardName="";
    static TextView tv_nombre_cliente_historic;
    static QuotasPerCustomerInvoiceRepository quotasPerCustomerRepository;
    QuotasPerCustomerDetailRepository quotasPerCustomerDetailRepository;
    static ArrayList<ListaClienteCabeceraEntity> Listado;
    public MenuConsultaCobradoView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuConsultaCobradoView.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuConsultaCobradoView newInstance(String param1, String param2) {
        MenuConsultaCobradoView fragment = new MenuConsultaCobradoView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static MenuConsultaCobradoView newInstanceRecibirCliente(Object object) {
        MenuConsultaCobradoView fragment = new MenuConsultaCobradoView();

        Listado=(ArrayList<ListaClienteCabeceraEntity>) object;

        for(int i=0;i<Listado.size();i++)
        {
            CardCode=Listado.get(i).getCliente_id();
            CardName=Listado.get(i).getNombrecliente();
        }

        tv_nombre_cliente_historic.setText(CardName);
        dialog.show();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quotasPerCustomerRepository = new ViewModelProvider(getActivity()).get(QuotasPerCustomerInvoiceRepository.class);
        quotasPerCustomerDetailRepository = new ViewModelProvider(getActivity()).get(QuotasPerCustomerDetailRepository.class);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_menu_consulta_cobrado_view, container, false);
        dialog= new Dialog(getContext());
        cv_cobradoporfecha=v.findViewById(R.id.cv_cobradoporfecha);
        cv_calculo_cuotas=v.findViewById(R.id.cv_calculo_cuotas);
        cv_cobradoporfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="HistoricoCobranzaView";
                String accion="COBRANZA";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,"");
            }
        });

        cv_calculo_cuotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String Fragment="HistoricoMenuCobranzaView";
                //String accion="QUOTAS";
                //String compuesto=Fragment+"-"+accion;
                //mListener.onFragmentInteraction(compuesto,"");

                alertaGetClient("Use el Boton de Buscar para Obtener el Cliente:").show();
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
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private Dialog alertaGetClient(String texto) {

        dialog.setContentView(R.layout.layout_dialog_getclient_historic_container_sale);
        TextView titulo = dialog.findViewById(R.id.tv_titulo);
        TextView textMsj = dialog.findViewById(R.id.tv_mensaje);
        textMsj.setText(texto);
        ImageView image = dialog.findViewById(R.id.image);
        image.setImageResource(R.mipmap.logo_circulo);
        titulo.setText("IMPORTANTE!!!");
        ImageButton imb_obtener_cliente;
        tv_nombre_cliente_historic = dialog.findViewById(R.id.tv_nombre_cliente_historic);
        imb_obtener_cliente = dialog.findViewById(R.id.imb_obtener_cliente);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        imb_obtener_cliente.setOnClickListener(v1 -> {
                    String Fragment="QuotasPerCustomer";
                    String accion="dialogoagregarclienteMenu";
                    String compuesto=Fragment+"-"+accion;
                    String objeto="dialogoagregarclienteMenu";
                    mListener.onFragmentInteraction(compuesto, objeto);
                    dialog.hide();
                }
        );
        dialogButtonOK.setOnClickListener(v -> {
            //ObtenerParametrosCheck();
            GenerarQuotaPerCustomerPDF();
            //dialog.dismiss();
        });
        dialogButtonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }
    final List<QuotasPerCustomerInvoiceEntity> listQuotasPerCustomerInvoiceEntity=null;
    public void GenerarQuotaPerCustomerPDF()
    {
        ///////////////////////////// BANCO SLITE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        Object object;
        try {


        quotasPerCustomerRepository.getQuotasPerCustomerInvoice(SesionEntity.fuerzatrabajo_id,getContext(),CardCode).observe(getActivity(), data1 ->
        {
            quotasPerCustomerDetailRepository.getQuotasPerCustomerDetail (SesionEntity.fuerzatrabajo_id,getContext(),CardCode).observe(getActivity(), data2 ->
            {

                if(!data2.isEmpty())
                {
                    QuotasPerCustomerPDF quotasPerCustomerPDF=new QuotasPerCustomerPDF();
                    quotasPerCustomerPDF.generarPdf(getContext(),
                            data1,
                            data2,
                            Listado
                    );
                }
            });

        });

        }catch (Exception e)
        {
            Log.e("REOS","MenuConsultaCobradoView-GenerarQuotaPerCustomerPDF-e"+e.toString());
        }
    }

}