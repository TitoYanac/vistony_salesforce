package com.vistony.salesforce.View;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bxl.config.editor.BXLConfigLoader;
import com.bxl.config.util.BXLBluetoothLE;
import com.bxl.config.util.BXLNetwork;
import com.vistony.salesforce.Dao.SQLIte.ConfiguracionSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.ConfiguracionSQLEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import jpos.JposException;

//import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static androidx.core.content.ContextCompat.checkSelfPermission;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfigImpresoraView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfigImpresoraView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigImpresoraView extends Fragment implements RadioGroup.OnCheckedChangeListener,AdapterView.OnItemClickListener, View.OnTouchListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    private final int REQUEST_PERMISSION = 0;
    private final String DEVICE_ADDRESS_START = " (";
    private final String DEVICE_ADDRESS_END = ")";
    Spinner spnrollonuevo;
    private final ArrayList<CharSequence> bondedDevices = new ArrayList<>();
    private ArrayAdapter<CharSequence> arrayAdapter;
    ConfiguracionSQLiteDao configuracionSQLiteDao;


    private int portType = BXLConfigLoader.DEVICE_BUS_BLUETOOTH;
    private String logicalName = "";
    private String address = "";

   // private LinearLayout layoutModel;
    //private LinearLayout layoutIPAddress;

    private RadioGroup radioGroupPortType;
    private TextView textViewBluetooth;
    private ListView listView;
    //private EditText editTextIPAddress;
    private CheckBox checkBoxAsyncMode;
    EditText et_tamaniopapel;
    Button btncalcularhoja;
    TextView tv_cantidatotalrecibos,tv_sucuencia;
    private Button btnPrinterOpen,btnPruebaPrinter,btnguardar_cambios;
    ObtenerSQLiteConfiguracion obtenerSQLiteConfiguracion;
    //private ProgressBar mProgressLarge;
    ArrayList<ConfiguracionSQLEntity> arraylistConfiguracionentity;
    ArrayList<ConfiguracionSQLEntity> listaConfiguracionEntity;
    private OnFragmentInteractionListener mListener;
    SesionEntity sesionEntity;
    String device;
    String Indicador="0";
    String indicador="0";

    private SwipeRefreshLayout pullToRefresh;

    public static ConfigImpresoraView newInstance(String param1, String param2) {
        ConfigImpresoraView fragment = new ConfigImpresoraView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configuracionSQLiteDao =  new ConfiguracionSQLiteDao(getContext());
        arraylistConfiguracionentity = new ArrayList<ConfiguracionSQLEntity>();
        listaConfiguracionEntity= new ArrayList<ConfiguracionSQLEntity>();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ListenerBackPress.setTemporaIdentityFragment("fragmentoConfigImpresora");
        v=inflater.inflate(R.layout.fragment_config_impresora_view, container, false);
        //layoutModel = v.findViewById(R.id.LinearLayout2);
             //  layoutIPAddress = v.findViewById(R.id.LinearLayout3);
       // layoutIPAddress.setVisibility(View.GONE);
        getActivity().setTitle("Configuracion Impresora");
        radioGroupPortType = v.findViewById(R.id.radioGroupPortType);
        radioGroupPortType.setOnCheckedChangeListener(this);
        et_tamaniopapel = (EditText) v.findViewById(R.id.et_tamaniopapel);
        btncalcularhoja = (Button) v.findViewById(R.id.btncalcularhoja);
        btnguardar_cambios = (Button) v.findViewById(R.id.btnguardar_cambios);
        spnrollonuevo = (Spinner) v.findViewById(R.id.spnrollonuevo);
        tv_cantidatotalrecibos = (TextView) v.findViewById(R.id.tv_cantidatotalrecibos) ;
        tv_sucuencia = (TextView) v.findViewById(R.id.tv_sucuencia);
        String [] valores =  new String[]{"No","Si"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, valores);

        spnrollonuevo.setAdapter(adapter);
        spnrollonuevo.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        if(position==0)
                        {
                            et_tamaniopapel.setEnabled(false);
                            btncalcularhoja.setEnabled(false);
                        }
                        if(position==1)
                        {
                            et_tamaniopapel.setEnabled(true);
                            btncalcularhoja.setEnabled(true);

                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );



        btncalcularhoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tamaniopapel="";
                int resultado=0;
                tamaniopapel=et_tamaniopapel.getText().toString();
                resultado=(Integer.parseInt(tamaniopapel)*100)/8;
                tv_cantidatotalrecibos.setText(String.valueOf(resultado));


            }
        });



        et_tamaniopapel.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if(!s.equals("0") && s.length()>0){
                    String tamaniopapel="";
                    int resultado=0;
                    tamaniopapel=et_tamaniopapel.getText().toString();
                    resultado=(Integer.parseInt(tamaniopapel)*100)/8;
                    //tv_cantidatotalrecibos.setText(String.valueOf(resultado));


                    tv_cantidatotalrecibos.setText(String.valueOf(resultado));
                }

            }
        });



        btnguardar_cambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                configuracionSQLiteDao.LimpiarTablaConfiguracion();
                configuracionSQLiteDao.InsertaConfiguracion(
                        device,
                        et_tamaniopapel.getText().toString(),
                        tv_cantidatotalrecibos.getText().toString(),
                        tv_cantidatotalrecibos.getText().toString(),
                        logicalName,
                        address,
                        "0",
                        indicador
                );
                Toast.makeText(getContext(), "Cambios Guardados Correctamente", Toast.LENGTH_SHORT).show();

            }
        });
        textViewBluetooth = v.findViewById(R.id.textViewBluetoothList);
        //editTextIPAddress = v.findViewById(R.id.editTextIPAddr);
        //editTextIPAddress.setText("192.168.0.1");

        checkBoxAsyncMode = v.findViewById(R.id.checkBoxAsyncMode);

        btnPrinterOpen = v.findViewById(R.id.btnPrinterOpen);
        btnPrinterOpen.setOnClickListener(this);

        btnPruebaPrinter = v.findViewById(R.id.btnPruebaPrinter);
        btnPruebaPrinter.setOnClickListener(this);

       /* mProgressLarge = v.findViewById(R.id.progressBar1);
        mProgressLarge.setVisibility(ProgressBar.GONE);*/

        setPairedDevices();

        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_single_choice, bondedDevices);
        listView = v.findViewById(R.id.listViewPairedDevices);
        listView.setAdapter(arrayAdapter);

        pullToRefresh = v.findViewById(R.id.pullToRefresh);

        pullToRefresh.setOnRefreshListener(() -> {
            setPairedDevices();
            Toast.makeText(getContext(), "Lista actualizada...", Toast.LENGTH_SHORT).show();
            pullToRefresh.setRefreshing(false);
        });

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(this);
        listView.setOnTouchListener(this);

        Spinner modelList = v.findViewById(R.id.spinnerModelList);

        ArrayAdapter modelAdapter = ArrayAdapter.createFromResource(getContext(), R.array.modelList, android.R.layout.simple_spinner_dropdown_item);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelList.setAdapter(modelAdapter);
        modelList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                logicalName = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                }
            }
        }

        obtenerSQLiteConfiguracion = new ObtenerSQLiteConfiguracion();
        obtenerSQLiteConfiguracion.execute();

        return v;
    }

    private void setPairedDevices() {
        bondedDevices.clear();

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bondedDeviceSet = bluetoothAdapter.getBondedDevices();

        for (BluetoothDevice device : bondedDeviceSet) {
            bondedDevices.add(device.getName() + DEVICE_ADDRESS_START + device.getAddress() + DEVICE_ADDRESS_END);
        }

        if (arrayAdapter != null) {
            arrayAdapter.notifyDataSetChanged();
        }
    }

    private void setBleDevices() {
        mHandler.obtainMessage(0).sendToTarget();
        BXLBluetoothLE.setBLEDeviceSearchOption(5, 1);
        new searchBLEPrinterTask().execute();
    }

    private void setNetworkDevices() {
        mHandler.obtainMessage(0).sendToTarget();
        BXLNetwork.setWifiSearchOption(5, 1);
        new searchNetworkPrinterTask().execute();
    }

    private class searchNetworkPrinterTask extends AsyncTask<Integer, Integer, Set<CharSequence>> {
        private String message;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Set<CharSequence> NetworkDeviceSet) {
            bondedDevices.clear();

            String[] items;
            if (NetworkDeviceSet != null && !NetworkDeviceSet.isEmpty()) {
                items = NetworkDeviceSet.toArray(new String[NetworkDeviceSet.size()]);
                for (int i = 0; (items != null) && (i < items.length); i++) {
                    bondedDevices.add(items[i]);
                }
            } else {
                Toast.makeText(getContext(), "Can't found network devices. ", Toast.LENGTH_SHORT).show();
            }

            if (arrayAdapter != null) {
                arrayAdapter.notifyDataSetChanged();
            }

            if (message != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

            mHandler.obtainMessage(1).sendToTarget();
        }

        @Override
        protected Set<CharSequence> doInBackground(Integer... params) {
            try {
                return BXLNetwork.getNetworkPrinters(getActivity(), BXLNetwork.SEARCH_WIFI_ALWAYS);
            } catch (NumberFormatException | JposException e) {
                message = e.getMessage();
                return new HashSet<>();
            }
        }
    }

    private class searchBLEPrinterTask extends AsyncTask<Integer, Integer, Set<BluetoothDevice>> {
        private String message;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Set<BluetoothDevice> bluetoothDeviceSet) {
            bondedDevices.clear();

            if (bluetoothDeviceSet.size() > 0) {
                for (BluetoothDevice device : bluetoothDeviceSet) {
                    bondedDevices.add(device.getName() + DEVICE_ADDRESS_START + device.getAddress() + DEVICE_ADDRESS_END);
                }
            } else {
                Toast.makeText(getContext(), "Can't found BLE devices. ", Toast.LENGTH_SHORT).show();
            }

            if (arrayAdapter != null) {
                arrayAdapter.notifyDataSetChanged();
            }

            if (message != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

            mHandler.obtainMessage(1).sendToTarget();
        }

        @Override
        protected Set<BluetoothDevice> doInBackground(Integer... params) {
            try {
                return BXLBluetoothLE.getBLEPrinters(getActivity(), BXLBluetoothLE.SEARCH_BLE_ALWAYS);
            } catch (NumberFormatException | JposException e) {
                message = e.getMessage();
                return new HashSet<>();
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.radioBT:
                portType = BXLConfigLoader.DEVICE_BUS_BLUETOOTH;
                textViewBluetooth.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
               // layoutIPAddress.setVisibility(View.GONE);

                setPairedDevices();
                break;
            case R.id.radioBLE:
                portType = BXLConfigLoader.DEVICE_BUS_BLUETOOTH_LE;
                textViewBluetooth.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
               // layoutIPAddress.setVisibility(View.GONE);

                setBleDevices();
                break;
            case R.id.radioWifi:
                portType = BXLConfigLoader.DEVICE_BUS_WIFI;
              //  layoutIPAddress.setVisibility(View.VISIBLE);
                textViewBluetooth.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);

                setNetworkDevices();
                break;
            case R.id.radioUSB:
                portType = BXLConfigLoader.DEVICE_BUS_USB;
               // layoutIPAddress.setVisibility(View.GONE);
                textViewBluetooth.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP)
            listView.requestDisallowInterceptTouchEvent(false);
        else listView.requestDisallowInterceptTouchEvent(true);
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        device = ((TextView) view).getText().toString();


        String deviceserialnumber=device;

        String[] compuestoserialnumber= deviceserialnumber.split(" ");
        String serialnumberbluetooh= compuestoserialnumber[0];

        SesionEntity.serialnumber=serialnumberbluetooh;


        if(portType == BXLConfigLoader.DEVICE_BUS_WIFI)
        {
            //editTextIPAddress.setText(device);
            address = device;
        }
        else {
            address = device.substring(device.indexOf(DEVICE_ADDRESS_START) + DEVICE_ADDRESS_START.length(), device.indexOf(DEVICE_ADDRESS_END));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPrinterOpen:

                Indicador="1";
                OpenPrinter(Indicador,getContext());

                break;
            case R.id.btnPruebaPrinter:
                String strData = "Vistony moviendo el futuro!!!" + "\n\n\n\n\n\n";
                int alignment=0, attribute = 0;
                MenuView.getPrinterInstance().printText(strData, alignment, attribute, 1);
                break;
        }
    }

    public void OpenPrinter(String indicador,Context context)
    {
        int puerto=0;
        String nombre="",direccion="";
        boolean modo=false;
        if (indicador.equals("1"))
        {
            puerto=portType;
            nombre=logicalName;
            direccion=address;
            modo=checkBoxAsyncMode.isChecked();

        }else if(indicador.equals("0"))
        {
            ArrayList<ConfiguracionSQLEntity> arraylistConfiguracionentity;

            ConfiguracionSQLiteDao configuracionSQLiteDao2 =  new ConfiguracionSQLiteDao(context);
            arraylistConfiguracionentity=configuracionSQLiteDao2.ObtenerConfiguracion();
            for(int i=0;i<arraylistConfiguracionentity.size();i++)
            {
                puerto=Integer.parseInt(arraylistConfiguracionentity.get(i).getTipoimpresora());
                nombre=arraylistConfiguracionentity.get(i).getModeloimpresora();
                direccion=(arraylistConfiguracionentity.get(i).getDireccionimpresora());
                modo=true;
            }

        }

       /* if(indicador.equals("1"))
        {
            mHandler.obtainMessage(0).sendToTarget();
        }*/
        //mHandler.obtainMessage(0).sendToTarget();
        final int finalPuerto = puerto;
        final String finalNombre = nombre;
        final String finalDireccion = direccion;
        final boolean finalModo = modo;
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (portType == BXLConfigLoader.DEVICE_BUS_WIFI) {
                   // address = editTextIPAddress.getText().toString();
                }

                if (MenuView.getPrinterInstance().printerOpen(finalPuerto, finalNombre, finalDireccion, finalModo)) {
                    //getActivity().finish();
                    //Toast.makeText(getContext(), "Impresora Vinculada Correctamente ", Toast.LENGTH_SHORT).show();
                    mHandler.obtainMessage(1, 0, 0, "Impresora Vinculada Correctamente").sendToTarget();
                } else {
                    //Indicador="0";
                    mHandler.obtainMessage(1, 0, 0, "Fail to printer open!!").sendToTarget();
                }
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public final Handler mHandler = new Handler(new Handler.Callback() {
        @SuppressWarnings("unchecked")
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if(Indicador.equals("1"))
                    {
                        //mProgressLarge.setVisibility(ProgressBar.VISIBLE);
                        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }
                    /*else
                        {
                        //Indicador="0";
                            mProgressLarge.setVisibility(ProgressBar.VISIBLE);
                            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }*/



                    break;
                case 1:
                    String data = (String) msg.obj;

                    if(data.equals("Impresora Vinculada Correctamente"))
                    {
                        indicador="1";
                    }
                    else if(data.equals("Fail to printer open!!"))
                        {
                            indicador="0";
                        }
                    if (data != null && data.length() > 0) {
                        //Toast.makeText(getContext(), data+indicador, Toast.LENGTH_LONG).show();
                        ConfiguracionSQLiteDao configuracionSQLiteDao3=  new ConfiguracionSQLiteDao(MenuView.context);
                        configuracionSQLiteDao3.ActualizaVinculo(indicador);
                    }
                    if(Indicador.equals("1"))
                    {
                        Toast.makeText(getContext(), data, Toast.LENGTH_LONG).show();
                       // mProgressLarge.setVisibility(ProgressBar.GONE);

                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        //mHandler.obtainMessage(0).sendToTarget();
                    }
                    //mProgressLarge.setVisibility(ProgressBar.GONE);
                    //getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    break;
            }
            return false;
        }
    });

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
       /* if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    /*@Override
    public void onAttach(Context context) {

        ListenerBackPress.setCurrentFragment("ConfigImpresoraView");
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
    }*/

    @Override
    public void onAttach(Context context) {


        ListenerBackPress.setCurrentFragment("ConfigImpresoraView");
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
        ListenerBackPress.setTemporaIdentityFragment("menuConfig");
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String Tag,Object data);
    }


    public class ObtenerSQLiteConfiguracion extends AsyncTask<String, Void, Object> {

        @Override
        protected String doInBackground(String... arg0) {
            try {

                arraylistConfiguracionentity=configuracionSQLiteDao.ObtenerConfiguracion();
                if (arraylistConfiguracionentity.isEmpty())
                {
                    //Toast.makeText(getContext(),"Lista Vacia", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return "1";
        }

        protected void onPostExecute(Object result) {

            for(int i=0;i<arraylistConfiguracionentity.size();i++)
            {
                et_tamaniopapel.setText(arraylistConfiguracionentity.get(i).getTamanio());
                tv_sucuencia.setText(arraylistConfiguracionentity.get(i).getSecuenciarecibos());
                tv_cantidatotalrecibos.setText(arraylistConfiguracionentity.get(i).getTotalrecibos());
            }
        }
    }

}
