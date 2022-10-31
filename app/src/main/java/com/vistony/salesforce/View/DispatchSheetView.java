package com.vistony.salesforce.View;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.vistony.salesforce.Controller.Adapters.ListaHojaDespachoAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.Utilitario;
import com.vistony.salesforce.Dao.Adapters.ListaHojaDespachoDao;
import com.vistony.salesforce.Dao.Retrofit.ClienteRepository;
import com.vistony.salesforce.Dao.Retrofit.DetailDispatchSheetRepository;
import com.vistony.salesforce.Dao.Retrofit.HeaderDispatchSheetRepository;
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.HeaderDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DispatchSheetView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DispatchSheetView extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener,SearchView.OnQueryTextListener  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static OnFragmentInteractionListener mListener;
    View v;
    ListView lista_despachos;
    ListaHojaDespachoAdapter listaHojaDespachoAdapter;
    //ObtenerSQLiteHojaDespachoCabecera obtenerSQLiteHojaDespachoCabecera;
    //ObtenerSQLiteHojaDespachoDetalle  obtenerSQLiteHojaDespachoDetalle;
    Spinner spn_control_id;
    private static DatePickerDialog oyenteSelectorFecha;
    private  int dia,mes,año;
    private SearchView mSearchView;
    ImageButton imb_consultar_fecha_hoja_despacho,imb_consultar_codigo_control,imb_consultar_QR;
    SimpleDateFormat dateFormat,dateFormat2;
    Date date,date2;
    String fecha,parametrofecha;
    TextView tv_fecha_hoja_despacho,tv_cantidad_despachos,tv_total_deuda,tv_status_despachos,tv_total_collection;
    Button btn_consultar_fecha_despacho;
    private ProgressDialog pd;
    //DecimalFormat format = new DecimalFormat("#0.00");
    ClienteRepository clienteRepository;
    List<ClienteSQLiteEntity> LclientesqlSQLiteEntity;
    ParametrosSQLite parametrosSQLite;
    GetAsyncTaskCustomer getAsyncTaskCustomer;
    TableRow table_row_status_dispatch;
    SwipeRefreshLayout swipeRefreshLayout;

    public DispatchSheetView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HojaDespachoView.
     */
    // TODO: Rename and change types and number of parameters
    public static DispatchSheetView newInstance(String param1, String param2) {
        DispatchSheetView fragment = new DispatchSheetView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static DispatchSheetView newInstanciaMenu(Object param1)
    {
        DispatchSheetView fragment = new DispatchSheetView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, String.valueOf(param1));
        fragment.setArguments(args);
        String Fragment="HojaDespachoView";
        String accion="inicioHojaDespachoViewView";
        String compuesto=Fragment+"-"+accion;
        if(mListener!=null) {
            mListener.onFragmentInteraction(compuesto, param1);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        v= inflater.inflate(R.layout.fragment_hoja_despacho_view, container, false);
        lista_despachos = v.findViewById(R.id.lista_despachos);
        spn_control_id = v.findViewById(R.id.spn_control_id);
        imb_consultar_fecha_hoja_despacho= v.findViewById(R.id.imb_consultar_fecha_hoja_despacho);
        imb_consultar_codigo_control= v.findViewById(R.id.imb_consultar_codigo_control);
        tv_fecha_hoja_despacho=v.findViewById(R.id.tv_fecha_hoja_despacho);
        tv_cantidad_despachos=v.findViewById(R.id.tv_cantidad_despachos);
        tv_total_deuda=v.findViewById(R.id.tv_total_deuda);
        tv_status_despachos=v.findViewById(R.id.tv_status_despachos);
        table_row_status_dispatch=v.findViewById(R.id.table_row_status_dispatch);
        btn_consultar_fecha_despacho=v.findViewById(R.id.btn_consultar_fecha_despacho);
        imb_consultar_QR=v.findViewById(R.id.imb_consultar_QR);
        tv_total_collection=v.findViewById(R.id.tv_total_collection);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        imb_consultar_fecha_hoja_despacho.setOnClickListener(this);
        imb_consultar_codigo_control.setOnClickListener(this);
        btn_consultar_fecha_despacho.setOnClickListener(this);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        dateFormat2= new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        date2 = new Date();
        parametrofecha =dateFormat2.format(date2);
        fecha =dateFormat.format(date);
        tv_fecha_hoja_despacho.setText(fecha);
        obtenerTituloFormulario();
        //table_row_status_dispatch.setVisibility(View.GONE);
        getStatusQR();

        imb_consultar_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmapqr = null;
                ArrayList<HojaDespachoCabeceraSQLiteEntity> listaHeaderDispatchSheetSQLite = new ArrayList<>();
                HeaderDispatchSheetSQLite headerDispatchSheetSQLite= new HeaderDispatchSheetSQLite(getContext());
                String asistente_id=null,drivercode=null,vehiclecode=null,variable,encoded="";
                try {

                    listaHeaderDispatchSheetSQLite=headerDispatchSheetSQLite.getHeaderDispatchSheetforDateAll(parametrofecha);
                    if(!listaHeaderDispatchSheetSQLite.isEmpty())
                    {
                        for (int i = 0; i < listaHeaderDispatchSheetSQLite.size(); i++) {
                            asistente_id=listaHeaderDispatchSheetSQLite.get(i).getAsistente_id();
                            drivercode=listaHeaderDispatchSheetSQLite.get(i).getDriverCode();
                            vehiclecode=listaHeaderDispatchSheetSQLite.get(i).getVehiculeCode();
                        }
                    }else
                    {
                        alertdialogInformative(getContext(),"IMPORTANTE","No hay Datos Disponibles del Despacho en la Fecha Seleccionada").show();
                        spn_control_id.setAdapter(null);
                    }
                    variable=drivercode+"|"+asistente_id+"|"+vehiclecode;
                    if(drivercode!=null &&asistente_id!=null&&vehiclecode!=null)
                    {
                        Log.e("REOS", "DispatchSheetView-imb_consultar_QR.setOnClickListener-listaHeaderDispatchSheetSQLite.size()" + listaHeaderDispatchSheetSQLite.size());
                        Log.e("REOS", "DispatchSheetView-imb_consultar_QR.setOnClickListener-parametrofecha" + parametrofecha);
                        Log.e("REOS", "DispatchSheetView-imb_consultar_QR.setOnClickListener-variable" + variable);
                        //bitmapqr = barcodeEncoder.encodeBitmap(spn_control_id.getSelectedItem().toString(), BarcodeFormat.QR_CODE, 300, 300);
                        encoded = Base64.encodeToString(variable.getBytes(), Base64.DEFAULT);
                        bitmapqr = barcodeEncoder.encodeBitmap(encoded, BarcodeFormat.QR_CODE, 300, 300);
                    }
                    else {
                        if(drivercode==null){
                            Toast.makeText(getContext(), "No se pudo generar Codigo QR, sin codigo de Chofer", Toast.LENGTH_SHORT).show();
                        }else if(asistente_id==null)
                        {
                            Toast.makeText(getContext(), "No se pudo generar Codigo QR, sin codigo de Asistente", Toast.LENGTH_SHORT).show();
                        }else if(vehiclecode==null)
                        {
                            Toast.makeText(getContext(), "No se pudo generar Codigo QR, sin codigo de Vehiculo", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                getalertPhoto("CODIGO QR",bitmapqr).show();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                UpdateListView(SesionEntity.imei,parametrofecha,getContext());
            }
        });

        return v;
    }



    public void getStatusQR()
    {
        if(spn_control_id.getSelectedItem()==null){
            Utilitario.disabledImageButtton(imb_consultar_QR,getContext());
        }else {
            Utilitario.enableImageButtton(imb_consultar_QR,getContext());
        }
    }

    public void obtenerTituloFormulario()
    {
        getActivity().setTitle("Hoja Despacho");
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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imb_consultar_fecha_hoja_despacho:
                final Calendar c1 = Calendar.getInstance();
                dia = c1.get(Calendar.DAY_OF_MONTH);
                mes = c1.get(Calendar.MONTH);
                año = c1.get(Calendar.YEAR);
                oyenteSelectorFecha = new DatePickerDialog(getContext(),this,
                        año,
                        mes,
                        dia
                );
                oyenteSelectorFecha.show();


                break;
            case R.id.btn_consultar_fecha_despacho:
                //Log.e("REOS","HojaDespachoView.btnconsultarfecha: Ingreso");

                /*getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        obtenerSQLiteHojaDespachoCabecera = new ObtenerSQLiteHojaDespachoCabecera();
                        obtenerSQLiteHojaDespachoCabecera.execute();

                    }
                });*/
                Log.e("REOS","HojaDespachoView.btn_consultar_fecha_despacho: tv_fecha_hoja_despacho.getText().toString()-"+tv_fecha_hoja_despacho.getText().toString());
                getMastersDelivery(SesionEntity.imei,parametrofecha,getContext());
                break;
            case R.id.imb_consultar_codigo_control:
                Log.e("REOS","HojaDespachoView.btnconsultarfecha: Ingreso");

                /*getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lista_despachos.setAdapter(null);
                        obtenerSQLiteHojaDespachoDetalle = new ObtenerSQLiteHojaDespachoDetalle();
                        obtenerSQLiteHojaDespachoDetalle.execute();
                    }
                });*/
                if(spn_control_id.getSelectedItem()!=null)
                {
                    getListDetailDispatchSheet(spn_control_id.getSelectedItem().toString(), getContext());
                    getStatusQR();
                }
                break;
            default:
                break;
        }
    }


    public void getMastersDelivery(String Imei,String DispatchDate,Context context)
    {
        Log.e("REOS","HojaDespachoView.getMastersDelivery-DispatchDate:"+DispatchDate);
        HeaderDispatchSheetRepository headerDispatchSheetRepository= new ViewModelProvider(getActivity()).get(HeaderDispatchSheetRepository.class);
        ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        HeaderDispatchSheetSQLite headerDispatchSheetSQLite=new HeaderDispatchSheetSQLite(getContext());
        if(headerDispatchSheetSQLite.getCountHeaderDispatchSheetDate(DispatchDate)>0)
        {
            getListDispatchSheet(DispatchDate,context);
        }else {
            if (networkInfo != null) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    Log.e("REOS","DispatchSheetView-getMastersDelivery-entraif");
                    getAsyncTaskCustomer=new GetAsyncTaskCustomer();
                    getAsyncTaskCustomer.execute();
                    headerDispatchSheetRepository.getAndInsertHeaderDispatchSheet(Imei,DispatchDate,context).observe(getActivity(), data -> {
                        Log.e("REOS","DispatchSheetView-getMastersDelivery-data"+data.toString());
                        getListDispatchSheet(DispatchDate,context);
                    });

                } else {
                    Log.e("REOS","DispatchSheetView-getMastersDelivery-entraelse");
                }
            }else{
                Log.e("REOS","DispatchSheetView-getMastersDelivery-entraelse");
                getListDispatchSheet(DispatchDate,context);
            }
        }


        /*detailDispatchSheetRepository.getAndInsertDetailDispatchSheet(Imei,DispatchDate,context).observe(getActivity(), data -> {
            Log.e("REOS", "DispatchSheetView-getMastersDelivery-detailDispatchSheetRepository-data" + data);
        });*/




    }
    public void CargaSpinnerControl(String [] control_id)
    {
        ArrayAdapter<String> adapter_control_id = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, control_id);
        spn_control_id.setAdapter(adapter_control_id);
    }

    public void getListDispatchSheet(String dispatchDate,Context context)
    {
        ArrayList<HojaDespachoCabeceraSQLiteEntity> listaHeaderDispatchSheetSQLite = new ArrayList<>();
        HeaderDispatchSheetSQLite headerDispatchSheetSQLite= new HeaderDispatchSheetSQLite(context);
        listaHeaderDispatchSheetSQLite=headerDispatchSheetSQLite.getHeaderDispatchSheetforDate(dispatchDate);
        if(!listaHeaderDispatchSheetSQLite.isEmpty())
        {
            String[] Lista_Control_ID = new String[listaHeaderDispatchSheetSQLite.size()];
            int Contador = 0;
            for (int i = 0; i < listaHeaderDispatchSheetSQLite.size(); i++) {
                Lista_Control_ID[Contador] = listaHeaderDispatchSheetSQLite.get(i).getControl_id();
                Contador++;
            }
            CargaSpinnerControl(Lista_Control_ID);
            lista_despachos.setAdapter(null);
        }else
        {
            alertdialogInformative(getContext(),"IMPORTANTE","No hay Datos Disponibles del Despacho en la Fecha Seleccionada").show();
            spn_control_id.setAdapter(null);
            lista_despachos.setAdapter(null);
        }
    }


    public int registrarClienteSQLite(List<ClienteSQLiteEntity> Lista){

        ClienteRepository clienteRepository =new ClienteRepository(getContext());
        clienteRepository.addCustomer(Lista);

        return clienteRepository.countCustomer();
    }

    public void getListDetailDispatchSheet (String codeControl,Context context){
        Log.e("REOS", "DispatchSheetView-getMastersDelivery-headerDispatchSheetRepository-codeControl" + codeControl);
        ArrayList<HojaDespachoDetalleSQLiteEntity> listDetailDispatchSheetSQLite=new ArrayList<>();
        DetailDispatchSheetSQLite detailDispatchSheetSQLite=new DetailDispatchSheetSQLite(context);
        listDetailDispatchSheetSQLite=detailDispatchSheetSQLite.getDetailDispatchSheetforCodeControl(codeControl);
        Log.e("REOS", "DispatchSheetView-getMastersDelivery-headerDispatchSheetRepository-listDetailDispatchSheetSQLite" + listDetailDispatchSheetSQLite.size());
        listaHojaDespachoAdapter = new ListaHojaDespachoAdapter(getActivity(), ListaHojaDespachoDao.getInstance().getLeads(listDetailDispatchSheetSQLite));
        lista_despachos.setAdapter(listaHojaDespachoAdapter);

        Double total_deuda=0.0;
        Integer total_status_dispatch=0,total_collections=0;
        for(int i=0;i<listDetailDispatchSheetSQLite.size();i++)
        {
            total_deuda=total_deuda+Double.parseDouble(listDetailDispatchSheetSQLite.get(i).getSaldo());
            if(listDetailDispatchSheetSQLite.get(i).isChkupdatedispatch())
            {
                total_status_dispatch++;
            }
            if(listDetailDispatchSheetSQLite.get(i).isChkcollection())
            {
                total_collections++;
            }
            Log.e("REOS","DispatchSheetView-getListDetailDispatchSheet-listDetailDispatchSheetSQLite.get(i).getItem_id():"+listDetailDispatchSheetSQLite.get(i).getItem_id());
        }

        tv_cantidad_despachos.setText(String.valueOf(listDetailDispatchSheetSQLite.size()));
        tv_total_deuda.setText(Convert.currencyForView(String.valueOf((total_deuda))));
        tv_status_despachos.setText(String.valueOf(total_status_dispatch));
        tv_total_collection.setText(String.valueOf(total_collections));

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
        parametrofecha=year+mes+dia;
        tv_fecha_hoja_despacho.setText(year + "-" + mes + "-" + dia);
    }

    public class GetAsyncTaskCustomer extends AsyncTask<String, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Consultando Datos", true, false);
        }
        @Override
        protected Object doInBackground(String... arg0) {
            int CantClientes=0;
            ClienteRepository clienteRepository = new ClienteRepository(getContext());
            ParametrosSQLite parametrosSQLite=new ParametrosSQLite(getActivity());
            LclientesqlSQLiteEntity = clienteRepository.getCustomers(SesionEntity.imei ,tv_fecha_hoja_despacho.getText().toString());
            Log.e("REOS","HojaDespachoView.getMastersDelivery-LclientesqlSQLiteEntity:"+LclientesqlSQLiteEntity.size());
            if (!(LclientesqlSQLiteEntity.isEmpty())) {
                CantClientes = registrarClienteSQLite(LclientesqlSQLiteEntity);
                parametrosSQLite.ActualizaCantidadRegistros("1", "CLIENTES", ""+CantClientes, getDateTime());
            }
            return 1;
        }
        protected void onPostExecute(Object result) {
            pd.dismiss();
        }
    }
    public static boolean esValorUnico(String valor, ArrayList<HojaDespachoDetalleSQLiteEntity> arreglo) {
        int contador = 0;
        for (int x = 0; x < arreglo.size(); x++) {
            if (arreglo.get(x).getControl_id().equals(valor))
                contador++;
        }
        return contador == 1;
    }

    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Buscar Cliente");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_hoja_despacho, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(listaHojaDespachoAdapter!=null) {
            listaHojaDespachoAdapter.filter(newText);
        }
        return true;
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

    private Dialog getalertPhoto(String foto, Bitmap bitmap) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_qr);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText(foto);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        ImageView imageViewPhoto = (ImageView) dialog.findViewById(R.id.imageViewPhoto);
        imageViewPhoto.setImageBitmap(bitmap);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog


        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }


    public void UpdateListView(String Imei,String DispatchDate,Context context) {
        Log.e("REOS","HojaDespachoView.getMastersDelivery-DispatchDate:"+DispatchDate);
        HeaderDispatchSheetRepository headerDispatchSheetRepository= new ViewModelProvider(getActivity()).get(HeaderDispatchSheetRepository.class);
        ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                Log.e("REOS","DispatchSheetView-getMastersDelivery-entraif");
                getAsyncTaskCustomer=new GetAsyncTaskCustomer();
                getAsyncTaskCustomer.execute();
                headerDispatchSheetRepository.getAndInsertHeaderDispatchSheet(Imei,DispatchDate,context).observe(getActivity(), data -> {
                    Log.e("REOS","DispatchSheetView-getMastersDelivery-data"+data.toString());
                    getListDispatchSheet(DispatchDate,context);
                });

            } else {
                Log.e("REOS","DispatchSheetView-getMastersDelivery-entraelse");
            }
        }else{
            Log.e("REOS","DispatchSheetView-getMastersDelivery-entraelse");
            getListDispatchSheet(DispatchDate,context);
        }
    }

}