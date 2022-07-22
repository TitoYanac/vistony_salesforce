package com.vistony.salesforce.View;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vistony.salesforce.Controller.Adapters.ListCurrencyChargedAdapter;
import com.vistony.salesforce.Controller.Adapters.ListaParametrosAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Dao.Adapters.ListCurrencyChargedDao;
import com.vistony.salesforce.Dao.Adapters.ListaParametrosDao;
import com.vistony.salesforce.Dao.Retrofit.DepositoRepository;
import com.vistony.salesforce.Entity.Adapters.ListCurrencyChargedEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricoDepositoEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrencyChargedView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrencyChargedView extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    private OnFragmentInteractionListener mListener;
    ImageButton imb_consultar_fecha_hoja_despacho,imb_consultar_code_dispatch;
    private  int diadespacho,mesdespacho,anodespacho;
    private static DatePickerDialog oyenteSelectorFecha;
    SimpleDateFormat dateFormat,dataFormatSAP;
    Date date;
    String fecha,fechaSAP;
    static TextView tv_fecha_hoja_despacho,tv_amount_charged,tv_amount_reported;
    Spinner spn_control_despacho_id;
    DepositoRepository depositoRepository;
    static private ProgressDialog pd;
    List<HistoricoDepositoEntity> listHistDeposits;
    FloatingActionButton fab_add_currency_charged;
    static ListCurrencyChargedAdapter listCurrencyChargedAdapter;
    static ListView lv_currency_detail;
    static public ArrayList<ListCurrencyChargedEntity> listCurrencyChargedEntities;
    static Context context;
    public CurrencyChargedView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoneyChargedView.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrencyChargedView newInstance(String param1, String param2) {
        CurrencyChargedView fragment = new CurrencyChargedView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static CurrencyChargedView newInstanceUpdateList() {
        CurrencyChargedView fragment = new CurrencyChargedView();
        Bundle args = new Bundle();

        ChargedListCurrency();
        fragment.setArguments(args);
        return fragment;
    }

    public static OrdenVentaDetalleView newInstanceActualizarResumen() {
        OrdenVentaDetalleView ordenVentaDetalleView = new OrdenVentaDetalleView();
        Bundle b = new Bundle();
        ordenVentaDetalleView.setArguments(b);
        UpdateSummary();
        return ordenVentaDetalleView;
    }

    public static OrdenVentaDetalleView newInstanceClarDetailCurrency(Object objeto) {
        OrdenVentaDetalleView ordenVentaDetalleView = new OrdenVentaDetalleView();
        Bundle b = new Bundle();
        int Position=(int) objeto;
        listCurrencyChargedEntities.remove(Position);
        for(int i=0;i<listCurrencyChargedEntities.size();i++)
        {
            listCurrencyChargedEntities.get(i).setId(String.valueOf(i+1));
        }
        ChargedListCurrency();
        UpdateSummary();
        return ordenVentaDetalleView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listHistDeposits=new ArrayList<>();
        context=getContext();
        listCurrencyChargedEntities=new ArrayList<>();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_money_charged_view, container, false);
        Induvis.setTituloContenedor("Dinero Cobrado",getActivity());
        imb_consultar_fecha_hoja_despacho = (ImageButton) v.findViewById(R.id.imb_consultar_fecha_hoja_despacho);
        imb_consultar_fecha_hoja_despacho.setOnClickListener(this);
        tv_fecha_hoja_despacho = (TextView) v.findViewById(R.id.tv_fecha_hoja_despacho);
        imb_consultar_code_dispatch= (ImageButton) v.findViewById(R.id.imb_consultar_code_dispatch);
        imb_consultar_code_dispatch.setOnClickListener(this);
        spn_control_despacho_id = (Spinner) v.findViewById(R.id.spn_control_despacho_id);
        tv_amount_charged=(TextView) v.findViewById(R.id.tv_amount_charged);
        tv_amount_reported=(TextView) v.findViewById(R.id.tv_amount_reported);
        fab_add_currency_charged=(FloatingActionButton) v.findViewById(R.id.fab_add_currency_charged);
        lv_currency_detail=(ListView) v.findViewById(R.id.lv_currency_detail);

        depositoRepository= new ViewModelProvider(getActivity()).get(DepositoRepository.class);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dataFormatSAP = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());;
        date = new Date();

        //
        fecha =dateFormat.format(date);
        fechaSAP=dataFormatSAP.format(date);
        tv_fecha_hoja_despacho.setText(fecha);
        tv_amount_charged.setText(Convert.currencyForView("0"));
        tv_amount_reported.setText(Convert.currencyForView("0"));
        spn_control_despacho_id.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String deposit_id;
                        deposit_id = spn_control_despacho_id.getSelectedItem().toString();

                        for(int i=0;i<listHistDeposits.size();i++)
                        {
                            if(listHistDeposits.get(i).getDeposito_id().equals(deposit_id))
                            {
                                tv_amount_charged.setText(Convert.currencyForView(listHistDeposits.get(i).getMontodeposito()));
                            }
                            else if(deposit_id.equals("--SELECCIONAR--")){
                                tv_amount_charged.setText(Convert.currencyForView("0"));
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        tv_amount_charged.setText(Convert.currencyForView("0"));
                    }
                }
        );

        fab_add_currency_charged.setOnClickListener(view -> {
            listCurrencyChargedEntities.add(addLine(listCurrencyChargedEntities));
            listCurrencyChargedAdapter = new ListCurrencyChargedAdapter(getActivity(), ListCurrencyChargedDao.getInstance().getLeads(listCurrencyChargedEntities));
            lv_currency_detail.setAdapter(listCurrencyChargedAdapter);
            listCurrencyChargedAdapter.notifyDataSetChanged();

        });

        return v;
    }

    public ListCurrencyChargedEntity addLine(ArrayList<ListCurrencyChargedEntity> List)
    {
        ListCurrencyChargedEntity Obj=new ListCurrencyChargedEntity(
                String.valueOf(List.size()+1),
                "",
                "",
                "",
                "",
                "",
                "0",
                true,
                true
        );
        return Obj;
    }

    static public void ChargedListCurrency()
    {
        listCurrencyChargedAdapter = new ListCurrencyChargedAdapter(context, ListCurrencyChargedDao.getInstance().getLeads(listCurrencyChargedEntities));
        lv_currency_detail.setAdapter(listCurrencyChargedAdapter);
    }

    static public void UpdateSummary()
    {
        Float Amount=0.f;
        for(int i=0;i<listCurrencyChargedEntities.size();i++){
            Amount=Amount+Float.parseFloat(listCurrencyChargedEntities.get(i).getAmount());
        }
        tv_amount_reported.setText(Convert.currencyForView(String.valueOf(Amount)));
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

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        //dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        //date = new Date();
        fechaSAP =dateFormat.format(date);


        String ano = "", mes = "", dia = "";

        mes = String.valueOf(month + 1);
        dia = String.valueOf(dayOfMonth);
        if (mes.length() == 1) {
            mes = '0' + mes;
        }
        if (dia.length() == 1) {
            dia = '0' + dia;
        }
        Log.e("REOS","CobranzaCabeceraView-onDateSet-fecha"+fecha);
        Log.e("jpcm","CobranzaCabeceraView-onDateSet-year+month+dayOfMonth"+year+mes+dia);
        /*if(Integer.parseInt(fechaSAP)<Integer.parseInt(year+mes+dia))
        {

            if (tv_fecha_hoja_despacho != null) {
                tv_fecha_hoja_despacho.setText(year + "-" + mes + "-" + dia);
            }
        }else {
            Toast.makeText(getContext(), "La fecha debe ser mayor a la actual, seleccione nuevamente", Toast.LENGTH_SHORT).show();
        }*/
        fechaSAP=year+mes+dia;
        if (tv_fecha_hoja_despacho != null) {
            tv_fecha_hoja_despacho.setText(year + "-" + mes + "-" + dia);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String Dato,Object Lista);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imb_consultar_fecha_hoja_despacho:
                final Calendar c2 = Calendar.getInstance();
                diadespacho = c2.get(Calendar.DAY_OF_MONTH);
                mesdespacho = c2.get(Calendar.MONTH);
                anodespacho = c2.get(Calendar.YEAR);
                oyenteSelectorFecha = new DatePickerDialog(getContext(),this,
                        anodespacho,
                        mesdespacho,
                        diadespacho
                );
                oyenteSelectorFecha.show();
                break;
            case R.id.imb_consultar_code_dispatch:
                pd = new ProgressDialog(getActivity());
                pd = ProgressDialog.show(getActivity(), "Por favor espere", "Consultando Ordenes de Venta", true, false);
                depositoRepository.getHistoricDeposits  (
                        SesionEntity.imei,
                        fechaSAP,
                        fechaSAP).observe(getActivity(), data -> {
                    Log.e("Jepicame","=>"+data);
                    if(data!=null)
                    {
                        listHistDeposits=data;
                        getListDepositsID(data);
                    }else {
                        Toast.makeText(getContext(), "No se encontraron Depositos del Dia", Toast.LENGTH_SHORT).show();
                    }
                    pd.dismiss();
                });

                break;
            default:
                break;
        }
    }

    public void getListDepositsID(List<HistoricoDepositoEntity> listHistDeposits)
    {
        int Contador=1;
        String [] Lista_Control_ID= new String [listHistDeposits.size()+1];
        Lista_Control_ID[0] = "--SELECCIONAR--";
        for(int i=0;i<listHistDeposits.size();i++)
        {
            Lista_Control_ID[Contador] = listHistDeposits.get(i).getDeposito_id();
            Contador++;
        }
        CargaSpinnerControl(Lista_Control_ID);
    }

    public void CargaSpinnerControl(String [] control_id)
    {
        ArrayAdapter<String> adapter_control_id = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, control_id);
        spn_control_despacho_id.setAdapter(adapter_control_id);
    }
}