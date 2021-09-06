package com.vistony.salesforce.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.vistony.salesforce.Controller.Adapters.ListaComisionesDetalleAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaComisionesDetalleDao;
import com.vistony.salesforce.Dao.Retrofit.ComisionesWS;
import com.vistony.salesforce.Entity.SQLite.ComisionesSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComisionesView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ComisionesView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComisionesView extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BarChart barChart;
    //private HorizontalBarChart barChart;
    private OnFragmentInteractionListener mListener;
    View v;
    //private String [] Indices=new String []{"Total","Re-programado"};
    private String [] Indices;
    //private int [] colors = new int []{Color.BLUE};
    private int [] colors;
    Spinner spnano,spnmes;
    public static com.omega_r.libs.OmegaCenterIconButton btn_comisiones_consultar;
    ListView listviewdetallecomisiones;
    ListaComisionesDetalleAdapter listaComisionesDetalleAdapter;
    private ProgressDialog pd;

    public ComisionesView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComisionesView.
     */
    // TODO: Rename and change types and number of parameters
    public static ComisionesView newInstance(String param1, String param2) {
        ComisionesView fragment = new ComisionesView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Comisiones");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //ListenerBackPress.setTemporaIdentityFragment("fragmentoConfigImpresora");
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_comisiones_view, container, false);
        barChart=(BarChart) v.findViewById(R.id.barChart);
        spnano=(Spinner) v.findViewById(R.id.spnano);
        spnmes=(Spinner) v.findViewById(R.id.spnmes);
        listviewdetallecomisiones=(ListView) v.findViewById(R.id.listviewdetallecomisiones);
        btn_comisiones_consultar= v.findViewById(R.id.btn_comisiones_consultar);
        btn_comisiones_consultar.setOnClickListener(this);

        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/
/*
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
    */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ListenerBackPress.setCurrentFragment("ComisionesView");
        ListenerBackPress.setTemporaIdentityFragment("onAttach");
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
        ListenerBackPress.setTemporaIdentityFragment("onDetach");
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String Dato,Object Lista);
    }

    private DataSet getData(DataSet dataSet)
    {
        dataSet.setColors(colors);
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private BarData getBarData(ArrayList<ComisionesSQLiteEntity> Lista)
    {
        BarDataSet barDataSet=(BarDataSet)getData(new BarDataSet(getBarEntries(Lista),""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData= new BarData(barDataSet);
        //barData.setBarWidth(0.55f);
        barData.setBarWidth(0.40f);
        return barData;
    }

    private ArrayList<BarEntry> getBarEntries(ArrayList<ComisionesSQLiteEntity> Lista)
    {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0;i<Lista.size();i++)
        {
            float valor=0;
            valor=(Float.parseFloat (Lista.get(i).getPorcentajeavance())*100);
            entries.add(new BarEntry(i,valor));
        }
        return entries;

    }
    private Chart getSameChart2(Chart chart, String description, int textColor, int background, int animateY)
    {

        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(background);
        chart.animateY(animateY);
        legend2(chart);
        return chart;
    }
    private void legend2 (Chart chart)
    {
        Legend legend=chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setMaxSizePercent(0.95f );
        ArrayList<LegendEntry>entries=new ArrayList<>();
        for(int i=0;i<Indices.length;i++)
        {
            LegendEntry entry=new LegendEntry();
            entry.formColor=colors[i];
            entry.label=Indices[i];

            entries.add(entry);
        }
        legend.setWordWrapEnabled(true);
        legend.setEnabled(false);
        legend.setCustom(entries);

    }
    private void axisX(XAxis axis)
    {
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(Indices));
    }
    private void axisLeft(YAxis axis)
    {
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
    }
    private void axisRight(YAxis axis)
    {
        axis.setEnabled(false);
    }

    public void createCharts(ArrayList<ComisionesSQLiteEntity> Lista)
    {
        barChart=(BarChart)getSameChart2(barChart,""
                //+(Indice())*100
                ,Color.RED,Color.WHITE,
                //3000
                100
        );
        //barChart.setDescription("desc");
        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(true);
        barChart.setData(getBarData(Lista));
        barChart.invalidate();
        barChart.setFitBars(true);
        //barChart.setVisibleXRange(0,100);
        //barChart.setMaxVisibleValueCount();
        barChart.setVisibleYRange(0,100,null);
        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
    }

    private class HiloObtenerComisiones extends AsyncTask<String, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Consultando Recibos", true, false);
        }
        @Override
        protected Object doInBackground(String... arg0) {
            ComisionesWS comisionesWS=new ComisionesWS(getContext());
            ArrayList<ComisionesSQLiteEntity> listaComisionesSQLiteEntity=new ArrayList<>();
            String[] mes = spnmes.getSelectedItem().toString().split("-");
            String mes1,mes2,ano;
            mes1=mes[0];
            mes2=mes[1];
            ano=spnano.getSelectedItem().toString();
            try {
                listaComisionesSQLiteEntity=comisionesWS.getComisiones(
                        SesionEntity.imei,
                        SesionEntity.compania_id,
                        SesionEntity.fuerzatrabajo_id,
                        ano,
                        mes1
                );
            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
                Log.e("REOS","ComisionesView:Error:"+e.toString());
            }

            return listaComisionesSQLiteEntity;
        }

        protected void onPostExecute(Object result)
        {
            ArrayList<ComisionesSQLiteEntity> Lista = (ArrayList<ComisionesSQLiteEntity>) result;
            if (Lista.isEmpty())
            {
                Toast.makeText(getContext(), "Error en la Consulta", Toast.LENGTH_SHORT).show();
            }else
            {
                cargarVariables(Lista);
                createCharts(Lista);
                listaComisionesDetalleAdapter = new ListaComisionesDetalleAdapter(getActivity(), ListaComisionesDetalleDao.getInstance().getLeads(Lista));
                listviewdetallecomisiones.setAdapter(listaComisionesDetalleAdapter);
            }
            pd.dismiss();
        }
    }

    public void cargarVariables(ArrayList<ComisionesSQLiteEntity> Lista)
    {
        Indices=new String [Lista.size()];
        colors=new int [Lista.size()];
        for(int i=0;i<Lista.size();i++)
        {
            Indices[i]=Lista.get(i).getVariable();
            colors[i]=Color.BLUE;
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_comisiones_consultar:
                HiloObtenerComisiones hiloObtenerComisiones=new HiloObtenerComisiones();
                hiloObtenerComisiones.execute();
                break;
            default:
                break;
        }
    }



}
