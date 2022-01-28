package com.vistony.salesforce.View;

import android.app.Activity;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Dao.Adapters.ListaComisionesDetalleDao;
import com.vistony.salesforce.Dao.Retrofit.ComisionesWS;
import com.vistony.salesforce.Entity.SQLite.ComisionesSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PronosticoComisionesView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PronosticoComisionesView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PronosticoComisionesView extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static private BarChart barChartAvanceAnterior;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    static private String [] Indices;
    //private int [] colors = new int []{Color.BLUE};
    static private int [] colors;
    Spinner spnano,spnmes;
    public static com.omega_r.libs.OmegaCenterIconButton btn_comisiones_consultar;
    static ListView listviewAvanceAnterior;
    static ListaComisionesDetalleAdapter listaComisionesDetalleAdapter;
    static private ProgressDialog pd;
    static SimpleDateFormat dateFormat;
    static Date date;
    static String fecha;
    static HiloObtenerComisiones hiloObtenerComisiones;
    static Activity activity;
    static Context context;
    static TextView tv_ano,tv_periodo;
    private OnFragmentInteractionListener mListener;
    static String dia,mes,ano;
    ViewGroup layout;
    static DecimalFormat format = new DecimalFormat("#0");
    public PronosticoComisionesView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PronosticoComisionesView.
     */
    // TODO: Rename and change types and number of parameters
    public static PronosticoComisionesView newInstance(Object object) {
        PronosticoComisionesView fragment = new PronosticoComisionesView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        hiloObtenerComisiones=new HiloObtenerComisiones();
        hiloObtenerComisiones.execute();
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
        ObtenerFecha();
        v= inflater.inflate(R.layout.fragment_pronostico_comisiones_view, container, false);
        barChartAvanceAnterior=(BarChart) v.findViewById(R.id.barChartAvanceAnterior);
        //spnano=(Spinner) v.findViewById(R.id.spnano);
        //spnmes=(Spinner) v.findViewById(R.id.spnmes);
        tv_ano=(TextView) v.findViewById(R.id.tv_ano);
        tv_periodo=(TextView) v.findViewById(R.id.tv_periodo);
        tv_ano.setText(ano);
        tv_periodo.setText(mes);
        listviewAvanceAnterior=(ListView) v.findViewById(R.id.listviewAvanceAnterior);
        //btn_comisiones_consultar= v.findViewById(R.id.btn_comisiones_consultar);
        //btn_comisiones_consultar.setOnClickListener(this);
        layout=(ViewGroup) v.findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        int id = R.layout.layout_leyenda_comisiones_detalle;
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(id, null, false);
        layout.addView(relativeLayout);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

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

    static private DataSet getData(DataSet dataSet)
    {
        dataSet.setColors(colors);
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    static private BarData getBarData(ArrayList<ComisionesSQLiteEntity> Lista)
    {

        BarDataSet barDataSet=(BarDataSet)getData(new BarDataSet(getBarEntries(Lista),""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData= new BarData(barDataSet);
        //barData.setBarWidth(0.55f);
        barData.setBarWidth(0.40f);
        return barData;
    }

    static private ArrayList<BarEntry> getBarEntries(ArrayList<ComisionesSQLiteEntity> Lista)
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
    static private Chart getSameChart2(Chart chart, String description, int textColor, int background, int animateY)
    {

        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(background);
        chart.animateY(animateY);
        legend2(chart);
        return chart;
    }
    static private void legend2 (Chart chart)
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
            Log.e("REOS", "ComisionesView-legend2-Indices[i]:" +Indices[i]);
            entries.add(entry);
        }
        legend.setWordWrapEnabled(false);
        legend.setEnabled(false);
        legend.setCustom(entries);

    }
    static private void axisX(XAxis axis)
    {
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setAxisMaximum(8);
        //axis.setSpaceMax(10);
        //axis.setCenterAxisLabels(true);
        axis.setTextSize(10);
        axis.setTextColor(Color.BLACK);
        //axis.setLabelRotationAngle(10);
        //axis.setSpaceMax(10)
        //axis.setAxisLineColor();
        //axis.setAxisLineColor(Color.RED);
        axis.setValueFormatter(new IndexAxisValueFormatter(Indices));
    }
    static private void axisLeft(YAxis axis)
    {
        axis.setSpaceTop(10);
        axis.setAxisMinimum(0);
    }
    static private void axisRight(YAxis axis)
    {
        axis.setEnabled(false);
    }

    static public void createCharts(ArrayList<ComisionesSQLiteEntity> Lista)
    {
        barChartAvanceAnterior=(BarChart)getSameChart2(barChartAvanceAnterior,""
                //+(Indice())*100
                ,Color.RED,Color.WHITE,
                //3000
                100
        );
        //barChart.setDescription("desc");
        barChartAvanceAnterior.setDrawGridBackground(true);
        barChartAvanceAnterior.setDrawBarShadow(true);
        barChartAvanceAnterior.setData(getBarData(Lista));
        barChartAvanceAnterior.invalidate();
        barChartAvanceAnterior.setFitBars(true);
        //barChart.setVisibleXRange(0,100);
        //barChart.setMaxVisibleValueCount();
        barChartAvanceAnterior.setVisibleYRange(0,100,null);
        axisX(barChartAvanceAnterior.getXAxis());
        axisLeft(barChartAvanceAnterior.getAxisLeft());
        axisRight(barChartAvanceAnterior.getAxisRight());
    }

    static private class HiloObtenerComisiones extends AsyncTask<String, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ContenedorComisionesView.activity);
            pd = ProgressDialog.show(ContenedorComisionesView.activity, "Por favor espere", "Consultando Avance Variables Periodo Anterior", true, false);
        }
        @Override
        protected Object doInBackground(String... arg0) {
            ComisionesWS comisionesWS=new ComisionesWS(ContenedorComisionesView.context);
            ArrayList<ComisionesSQLiteEntity> listaComisionesSQLiteEntity=new ArrayList<>();
            ObtenerFecha();
            try {
                listaComisionesSQLiteEntity=comisionesWS.getComisiones(
                        SesionEntity.imei,
                        SesionEntity.compania_id,
                        SesionEntity.fuerzatrabajo_id,
                        ano,
                        mes
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
                Toast.makeText(ContenedorComisionesView.context, "Error en la Consulta", Toast.LENGTH_SHORT).show();
            }else
            {
                cargarVariables(Lista);
                createCharts(Lista);
                listaComisionesDetalleAdapter = new ListaComisionesDetalleAdapter(ContenedorComisionesView.activity, ListaComisionesDetalleDao.getInstance().getLeads(Lista));
                listviewAvanceAnterior.setAdapter(listaComisionesDetalleAdapter);
            }
            pd.dismiss();
        }
    }

    static public void cargarVariables(ArrayList<ComisionesSQLiteEntity> Lista)
    {
        Indices=new String [Lista.size()];
        colors=new int [Lista.size()];
        for(int i=0;i<Lista.size();i++)
        {
            Log.e("REOS", "ComisionesView-cargarVariables-Lista.get(i).getVariable():" + Lista.get(i).getVariable());
            Indices[i]=Lista.get(i).getVariable();
            colors[i]=Color.BLUE;
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            /*case R.id.btn_comisiones_consultar:
                HiloObtenerComisiones hiloObtenerComisiones=new HiloObtenerComisiones();
                hiloObtenerComisiones.execute();
                break;*/
            default:
                break;
        }
    }

    static public void ObtenerFecha()
    {
        dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        date = new Date();
        fecha = dateFormat.format(date);
        String fechainduvis="";
        try {
            fechainduvis= Induvis.changeMonthNow(fecha,-1);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("REOS","HistoricContainerSalesSemaforo-error"+e);
        }
        Log.e("REOS","PronosticoComisionesView-HiloObtenerComisiones-fechainduvis"+fechainduvis);


        //String[] arrayfecha = fecha.toString().split("-");
        ano = fechainduvis.substring(0, 4);
        mes = fechainduvis.substring(4, 6);
        dia = fechainduvis.substring(6, 8);
        //tv_ano.setText(ano);
        //tv_periodo.setText(mes);
        Log.e("REOS","PronosticoComisionesView-HiloObtenerComisiones-ano"+ano);
        Log.e("REOS","PronosticoComisionesView-HiloObtenerComisiones-mes"+mes);
    }

}
