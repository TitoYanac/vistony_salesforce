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

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Controller.Adapters.ListKardexOfPaymentAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.KardexPagoPDF;
import com.vistony.salesforce.Dao.Adapters.ListKardexOfPaymentDao;
import com.vistony.salesforce.Dao.Adapters.ListaConsultaStockDao;
import com.vistony.salesforce.Dao.Retrofit.KardexPagoRepository;
import com.vistony.salesforce.Entity.Adapters.ListKardexOfPaymentEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.KardexPagoEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KardexOfPaymentView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KardexOfPaymentView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //View
    View v;
    static ListView lv_kardex_of_payment;
    Button btn_find_client;
    static TextView tv_client,tv_quantity_invoice_kardex,tv_docamount_kardex_invoice;
    static KardexPagoRepository kardexPagoRepository;
    static Context context;
    static ListKardexOfPaymentAdapter listKardexOfPaymentAdapter;
    static String CardCode;
    static Activity activity;
    static LifecycleOwner lifecycleOwner;
    public static OnFragmentInteractionListener mListener;
    static List<KardexPagoEntity> kardexPagoEntityList;
    public static List<ListKardexOfPaymentEntity> listKardexOfPaymentEntityList;
    static double docamount;
    MenuItem checklist_all,generate_pdf;
    static private ProgressDialog pd;

    public KardexOfPaymentView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KardexOfPaymentView.
     */
    // TODO: Rename and change types and number of parameters
    public static KardexOfPaymentView newInstance(String param1, String param2) {
        KardexOfPaymentView fragment = new KardexOfPaymentView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static KardexOfPaymentView newInstanceRecibirLista (Object objeto) {
        KardexOfPaymentView fragment = new KardexOfPaymentView();
        Bundle args = new Bundle();

        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        MenuAccionView menuAccionView = new MenuAccionView();

        Bundle b = new Bundle();
        ArrayList<ListaClienteCabeceraEntity> Lista = (ArrayList<ListaClienteCabeceraEntity>) objeto;


        for(int s=0;s<Lista.size();s++){
            Log.e("JEPICAMEE","=>"+Lista.get(s).getDireccion());
            Log.e("JEPICAMEE","=>"+Lista.get(s).getDomembarque_id());
            Log.e("JEPICAMEE","=>"+Lista.get(s).getDomfactura_id());
            Log.e("JEPICAMEE","=>"+Lista.get(s).getCliente_id());
            Log.e("JEPICAMEE","=>"+Lista.get(s).getZona_id());
            CardCode=Lista.get(s).getCliente_id();
            tv_client.setText(Lista.get(s).getNombrecliente());
        }
        getListKardexOfPayment(CardCode);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getActivity().getResources().getString(R.string.lbl_kardex_of_payment));
        setHasOptionsMenu(true);
        kardexPagoRepository = new ViewModelProvider(getActivity()).get(KardexPagoRepository.class);
        context=getContext();
        activity=getActivity();
        lifecycleOwner=getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v= inflater.inflate(R.layout.fragment_kardex_of_payment_view, container, false);
        lv_kardex_of_payment=v.findViewById(R.id.lv_kardex_of_payment);
        btn_find_client=v.findViewById(R.id.btn_find_client);
        tv_client=v.findViewById(R.id.tv_client);
        tv_quantity_invoice_kardex=v.findViewById(R.id.tv_quantity_invoice_kardex);
        tv_docamount_kardex_invoice=v.findViewById(R.id.tv_docamount_kardex_invoice);
        btn_find_client.setOnClickListener(v -> {
            String Fragment="KardexOfPaymentView";
            String accion="findClient";
            String compuesto=Fragment+"-"+accion;
            String objeto="kardexofpayment";
            mListener.onFragmentInteraction(compuesto, objeto);
        });
        return v;
    }


    static private void getListKardexOfPayment(String CardCode)
    {
        pd = new ProgressDialog(activity);
        pd = ProgressDialog.show(activity, activity.getResources().getString(R.string.please_wait), activity.getResources().getString(R.string.querying_dates), true, false);
        kardexPagoEntityList=new ArrayList<>();
        listKardexOfPaymentEntityList=new ArrayList<>();
        docamount=0;
        kardexPagoRepository.getKardexPago(SesionEntity.imei, CardCode,context).observe(lifecycleOwner, data -> {
        Convert convert=new Convert();
            Log.e("Jepicame","=>"+data);
            if(data!=null)
            {
            listKardexOfPaymentEntityList=convert.getConvertListKardexOfPayment(data);
            kardexPagoEntityList=data;
             listKardexOfPaymentAdapter
                    =new ListKardexOfPaymentAdapter(
                     activity,
                     listKardexOfPaymentEntityList);
                    lv_kardex_of_payment.setAdapter(listKardexOfPaymentAdapter);
                    Log.e("REOS","KardexOfPaymentView.getListKardexOfPayment.listKardexOfPaymentEntityList.size()"+listKardexOfPaymentEntityList.size());
                    for(int i=0;i<listKardexOfPaymentEntityList.size();i++)
                    {
                        docamount=docamount+Double.parseDouble(listKardexOfPaymentEntityList.get(i).getDocAmount()) ;
                        //tv_docamount_kardex_invoice.setText(Convert.currencyForView(String.valueOf(listKardexOfPaymentEntityList.get(i).getDocAmount())));
                    }
            tv_quantity_invoice_kardex.setText(String.valueOf(listKardexOfPaymentEntityList.size()));
            tv_docamount_kardex_invoice.setText(Convert.currencyForView(String.valueOf(docamount)));
            }else {
                Toast.makeText(context, activity.getResources().getString(R.string.mse_not_data_available), Toast.LENGTH_SHORT).show();
                alertdialogInformative(context,activity.getResources().getString(R.string.important),activity.getResources().getString(R.string.mse_not_data_available)).show();
            }
            pd.dismiss();
        });
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
        if (context instanceof MenuAccionView.OnFragmentInteractionListener) {
            super.onAttach(context);
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }


    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_kardex_of_payment, menu);
        checklist_all = menu.findItem(R.id.checklist_all);
        generate_pdf = menu.findItem(R.id.generate_pdf);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.checklist_all:
                alertaSeleccionarTodo(getActivity().getResources().getString(R.string.are_you_sure_selecction_all_invoices)).show();
                return false;
            case R.id.generate_pdf:
                //alertaSeleccionarTodo("Esta Seguro de Seleccionar Todos los parametros?").show();
                KardexPagoPDF kardexPagoPDF=new KardexPagoPDF();
                kardexPagoPDF.generarPdf(context, getListKardexPagoEntity(listKardexOfPaymentEntityList,kardexPagoEntityList));
                return false;
            default:
                break;
        }
        return false;
    }

    private Dialog alertaSeleccionarTodo(String texto) {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_advertencia);

        TextView textMsj = dialog.findViewById(R.id.tv_texto);
        textMsj.setText(texto);

        ImageView image = dialog.findViewById(R.id.image);

        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);

        dialogButtonOK.setOnClickListener(v -> {
            //ObtenerParametrosCheck();
            //getListKardexOfPayment(CardCode);
            getCheckListAll(listKardexOfPaymentEntityList);
            dialog.dismiss();
        });
        dialogButtonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    private List<KardexPagoEntity> getListKardexPagoEntity
            (List<ListKardexOfPaymentEntity> listKardexOfPaymentEntityList,
             List<KardexPagoEntity> ListKardexPagoEntity)
    {
        List<KardexPagoEntity> newListKardexPagoEntity=new ArrayList<>();
        KardexPagoEntity kardexPagoEntity=new KardexPagoEntity();
        for(int i=0;i<listKardexOfPaymentEntityList.size();i++)
        {
            if(listKardexOfPaymentEntityList.get(i).isInvoice())
            {
                for(int j=0;j<ListKardexPagoEntity.size();j++) {
                    if(listKardexOfPaymentEntityList.get(i).getLegalnumber().equals(ListKardexPagoEntity.get(j).getNumAtCard()))
                    {
                        kardexPagoEntity = new KardexPagoEntity();
                        kardexPagoEntity.cardCode = ListKardexPagoEntity.get(j).getCardCode();
                        kardexPagoEntity.docCur = ListKardexPagoEntity.get(j).getDocCur();
                        kardexPagoEntity.u_SYP_MDSD = ListKardexPagoEntity.get(j).getU_SYP_MDSD();
                        kardexPagoEntity.u_SYP_MDCD = ListKardexPagoEntity.get(j).getU_SYP_MDCD();
                        kardexPagoEntity.taxDate = ListKardexPagoEntity.get(j).getTaxDate();
                        kardexPagoEntity.docDueDate = ListKardexPagoEntity.get(j).getDocDueDate();
                        kardexPagoEntity.docTotal = ListKardexPagoEntity.get(j).getDocTotal();
                        kardexPagoEntity.docEntry = ListKardexPagoEntity.get(j).getDocEntry();
                        kardexPagoEntity.objType = ListKardexPagoEntity.get(j).getObjType();
                        kardexPagoEntity.sALDO = ListKardexPagoEntity.get(j).getsALDO();
                        kardexPagoEntity.cardName = ListKardexPagoEntity.get(j).getCardName();
                        kardexPagoEntity.licTradNum = ListKardexPagoEntity.get(j).getLicTradNum();
                        kardexPagoEntity.phone1 = ListKardexPagoEntity.get(j).getPhone1();
                        kardexPagoEntity.u_VIS_SlpCode = ListKardexPagoEntity.get(j).getU_VIS_SlpCode();
                        kardexPagoEntity.street = ListKardexPagoEntity.get(j).getStreet();
                        kardexPagoEntity.pymntGroup = ListKardexPagoEntity.get(j).getPymntGroup();
                        kardexPagoEntity.u_SYP_DEPA = ListKardexPagoEntity.get(j).getU_SYP_DEPA();
                        kardexPagoEntity.u_SYP_PROV = ListKardexPagoEntity.get(j).getU_SYP_PROV();
                        kardexPagoEntity.u_SYP_DIST = ListKardexPagoEntity.get(j).getU_SYP_DIST();
                        kardexPagoEntity.importe = ListKardexPagoEntity.get(j).getImporte();
                        kardexPagoEntity.importeCobrado = ListKardexPagoEntity.get(j).getImporteCobrado();
                        kardexPagoEntity.fECHADEPAGO = ListKardexPagoEntity.get(j).getfECHADEPAGO();
                        kardexPagoEntity.nROOPERA = ListKardexPagoEntity.get(j).getnROOPERA();
                        kardexPagoEntity.docNum = ListKardexPagoEntity.get(j).getDocNum();
                        kardexPagoEntity.jrnlMemo = ListKardexPagoEntity.get(j).getJrnlMemo();
                        kardexPagoEntity.comments = ListKardexPagoEntity.get(j).getComments();
                        kardexPagoEntity.banco = ListKardexPagoEntity.get(j).getBanco();
                        kardexPagoEntity.numAtCard = ListKardexPagoEntity.get(j).getNumAtCard();
                        kardexPagoEntity.salesinvoice = ListKardexPagoEntity.get(j).getSalesinvoice();
                        kardexPagoEntity.collectorinvoice = ListKardexPagoEntity.get(j).getCollectorinvoice();
                        newListKardexPagoEntity.add(kardexPagoEntity);
                    }
                }

            }
        }
        return newListKardexPagoEntity;
    }

    private void getCheckListAll (List<ListKardexOfPaymentEntity> listKardexOfPaymentEntityList)
    {
        for(int i=0;i<listKardexOfPaymentEntityList.size();i++)
        {
            listKardexOfPaymentEntityList.get(i).setInvoice(true);
        }
        listKardexOfPaymentAdapter
                =new ListKardexOfPaymentAdapter(
                activity,
                listKardexOfPaymentEntityList);
        lv_kardex_of_payment.setAdapter(listKardexOfPaymentAdapter);

    }

    static private Dialog alertdialogInformative(Context context,String titulo,String message) {

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