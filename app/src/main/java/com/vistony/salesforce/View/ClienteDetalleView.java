package com.vistony.salesforce.View;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
/*
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;

//import android.support.v7.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vistony.salesforce.Controller.Adapters.ListaClienteDetalleAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Dao.Retrofit.ClienteRepository;
import com.vistony.salesforce.Dao.SQLite.DocumentoSQLite;
import com.vistony.salesforce.Dao.SQLite.StatusDispatchSQLite;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.StatusDispatchEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Dao.Adapters.ListaClienteDetalleDao;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteDetalleEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClienteDetalleView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClienteDetalleView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClienteDetalleView extends Fragment implements Serializable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView output,tv_nombrecliente,tv_ruccliente,tv_cantidad_cliente_detalle,tv_monto_cliente_detalle;
    View v;
    private static String TAG_1 = "text";
    static String texto;
    String imei;
    String fuerzatrabajo_id;
    String compania_id;
    static String nombrecliente,domebarque,zona_id,chkruta;
    static String ruccliente;
    public static String textorecuperado;
    public ListView listaDDeuda;
    public ArrayList<DocumentoDeudaSQLiteEntity> listaDDeudaEntity;
    ArrayList<DocumentoDeudaSQLiteEntity> ArraylistDDeudaEntity;
    ArrayList<String> listaInformacion;
    ObtenerSQLiteDDeuda obtenerSQLiteDDeuda;
    DocumentoSQLite documentoSQLite;
    SesionEntity sesionEntity;
    ListaClienteDetalleAdapter listaClienteDetalleAdapter;
    ListaClienteDetalleDao listaClienteDetalleDao;
    private ProgressDialog pd;
    public static ArrayList<ListaClienteCabeceraEntity> Listado;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private MenuView menuView;
    FloatingActionButton fab;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout drawerLayout;
    private boolean mToolBarNavigationListenerIsRegistered = false;
    private final int MY_PERMISSIONS_REQUEST_CAMERA=1;
    //ConfigImpresoraView configImpresoraView;
    private static OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout refreshMasterClient;
    private ClienteRepository clienteRepository;
    private FloatingActionButton floatingButtonTakePhoto;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private Bitmap imgBitmap;
    public static ImageView imageViewPhoto;
    String mCurrentPhotoPath="";
    //ListenerBackPress.setCurrentFragment("FormListaDeudaCliente");
    private byte[] byteArray;
    MenuItem update_dispatch_reason;
    static public String Flujo="";

    public static ClienteDetalleView newInstance(Object objeto){
        //Log.e("jpcm","regreso here 1 de "+ListenerBackPress.getCurrentFragment());
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        ClienteDetalleView clienteDetalleView = new ClienteDetalleView();
        //ArrayList<String> Listado = new ArrayList<String>();
        Bundle b = new Bundle();
        ArrayList<ListaClienteCabeceraEntity> Lista = (ArrayList<ListaClienteCabeceraEntity>) objeto;
        Lista.size();
        b.putSerializable(TAG_1,Lista);
        clienteDetalleView.setArguments(b);
        Listado = Lista;
        Listado.size();
        return clienteDetalleView;
    }

    public static Fragment newInstancia (Object param1) {
        Log.e("jpcm","regreso here 12 1ra apertura");
        ClienteDetalleView fragment = new ClienteDetalleView();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, String.valueOf(param1));
        fragment.setArguments(args);

       String Fragment="ClienteDetalleView";
        String accion="inicio";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,param1);
        for(int i=0;i<Listado.size();i++)
        {
            nombrecliente=Listado.get(i).getNombrecliente();
            texto=Listado.get(i).getCliente_id();
            domebarque=Listado.get(i).getDomembarque_id();
            zona_id=Listado.get(i).getZona_id();
            ruccliente=texto;
        }

        textorecuperado=texto;
        return fragment;
    }

    public static ClienteDetalleView newInstanceReceipCustomerComplaint(Object objeto,String flujo){
        //Log.e("jpcm","regreso here 1 de "+ListenerBackPress.getCurrentFragment());
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        ClienteDetalleView clienteDetalleView = new ClienteDetalleView();
        //ArrayList<String> Listado = new ArrayList<String>();
        Bundle b = new Bundle();
        ArrayList<ListaClienteCabeceraEntity> Lista = (ArrayList<ListaClienteCabeceraEntity>) objeto;
        Flujo=flujo;
        Lista.size();
        b.putSerializable(TAG_1,Lista);
        clienteDetalleView.setArguments(b);
        Listado = Lista;
        Listado.size();
        return clienteDetalleView;
    }

    public static Fragment newInstanciaSendCustomerComplaint (Object param1) {
        Log.e("jpcm","regreso here 12 1ra apertura");
        ClienteDetalleView fragment = new ClienteDetalleView();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, String.valueOf(param1));
        fragment.setArguments(args);

        String Fragment="CustomerComplaintView";
        String accion="CustomerComplaintViewsendClient";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,param1);
        /*for(int i=0;i<Listado.size();i++)
        {
            nombrecliente=Listado.get(i).getNombrecliente();
            texto=Listado.get(i).getCliente_id();
            domebarque=Listado.get(i).getDomembarque_id();
            zona_id=Listado.get(i).getZona_id();
            ruccliente=texto;
        }

        textorecuperado=texto;*/
        return fragment;
    }
    public ClienteDetalleView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_cliente_detalle,container,false);
        menuView = new MenuView();
        listaDDeuda = (ListView) v.findViewById(R.id.listdeuda);
        tv_nombrecliente=v.findViewById(R.id.tv_nombrecliente);
        tv_ruccliente=v.findViewById(R.id.tv_ruccliente);
        tv_nombrecliente.setText(nombrecliente);
        tv_ruccliente.setText(ruccliente);
        listaDDeudaEntity= new ArrayList<DocumentoDeudaSQLiteEntity>();
        ArraylistDDeudaEntity= new ArrayList<DocumentoDeudaSQLiteEntity>();
        fab = (FloatingActionButton) v.findViewById(R.id.fabagregar);
        tv_cantidad_cliente_detalle = (TextView) v.findViewById(R.id.tv_cantidad_cliente_detalle);
        tv_monto_cliente_detalle = (TextView) v.findViewById(R.id.tv_monto_cliente_detalle);


        refreshMasterClient=v.findViewById(R.id.refreshClient);

        clienteRepository =  new ViewModelProvider(this.getActivity()).get(ClienteRepository.class);

        /*clienteRepository.updateInformationClient(imei,ruccliente,getContext()).observe(this.getActivity(), data -> {
            /*switch(data){
                case "if":
                    Toast.makeText(getContext(), "Documentos actualizados...", Toast.LENGTH_SHORT).show();
                    break;
                case "else":
                    Toast.makeText(getContext(), "Ocurrio un error al actualizar documentos...", Toast.LENGTH_SHORT).show();
                    break;
                case "onFailure":
                    Toast.makeText(getContext(), "Revisar conección a internet...", Toast.LENGTH_SHORT).show();
                    break;
            }
        });*/


        /*refreshMasterClient.setOnRefreshListener(() -> {
            clienteRepository.updateInformationClient(imei,ruccliente,getContext());
            refreshMasterClient.setRefreshing(false);
        });*/

        obtenerPametros();
        obtenerSQLiteDDeuda = new ObtenerSQLiteDDeuda();
        obtenerSQLiteDDeuda.execute();

        ClienteDetalleView.this.someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Bundle extras = result.getData().getExtras();
                imgBitmap = (Bitmap) extras.get("data");
                imageViewPhoto.setImageBitmap(imgBitmap);
            }
        });

        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        //showActionBar(); // the method to change ActionBar
        //getActivity().setTitle("Documento Deuda");
    }
    private void showActionBar() {
        // get the ToolBar from Main Activity
        final Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        // get the ActionBar from Main Activity
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        // inflate the customized Action Bar View
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.fragment_cliente_detalle_actionbar, null);

        if (actionBar != null) {
            // enable the customized view and disable title
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);

            actionBar.setCustomView(v);
            // remove Burger Icon
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar.setNavigationIcon(null);
            }

            // add click listener to the back arrow icon
            v.findViewById(R.id.back);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // reverse back the show
                    actionBar.setDisplayShowCustomEnabled(false);
                    actionBar.setDisplayShowTitleEnabled(true);
                    //get the Drawer and DrawerToggle from Main Activity
                    // set them back as normal
                    DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);

                   // ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


                    toggle.syncState();

                    String Fragment="ClienteDetalleView";
                    String accion="regresar";
                    String compuesto=Fragment+"-"+accion;
                    String param1="";
                    mListener.onFragmentInteraction(compuesto,param1);

                }
            });
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //onCreateOptionsMenu();
        documentoSQLite = new DocumentoSQLite(getContext());
        sesionEntity= new SesionEntity();
        texto=new String();
        setHasOptionsMenu(true);

        getActivity().setTitle(getActivity().getResources().getString(R.string.document_with_debt));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (getArguments() != null) {

          Listado= (ArrayList<ListaClienteCabeceraEntity>)getArguments().getSerializable(TAG_1);

            Bundle bundle = new Bundle();


            if(Listado !=null){
                Log.e("jpcm","ESTA  lISTADO TINENE DATA "+Listado.size());
                //  Listado=mParam1;
                for(int i=0;i<Listado.size();i++)
                {
                    nombrecliente=Listado.get(i).getNombrecliente();
                    texto=Listado.get(i).getCliente_id();
                    ruccliente=texto;
                    domebarque=Listado.get(i).getDomembarque_id();
                    zona_id=Listado.get(i).getZona_id();
                    chkruta=Listado.get(i).getChk_ruta();
                }
                Log.e("REOS","ClienteDetalleView-onCreate-chkruta "+chkruta);
                textorecuperado=texto;
                Log.e("ID :", "" + texto.toString());
            }else{
                Log.e("jpcm","ESTA NULO lISTADO");
            }


        }
    }

    public class ObtenerSQLiteDDeuda extends AsyncTask<String, Void, Object> {

        @Override
        protected String doInBackground(String... arg0) {
            try {
                listaDDeudaEntity= documentoSQLite.ObtenerDDeudaporcliente(compania_id,fuerzatrabajo_id,texto);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "1";
        }

        protected void onPostExecute(Object result) {
            String cantidad="",monto="";
            int cantidad_documento=0;

            BigDecimal  monto_cliente_detalle=new BigDecimal("0");
            BigDecimal  monto_cliente_detalle_dolares=new BigDecimal("0");

            cantidad_documento=listaDDeudaEntity.size();
            tv_cantidad_cliente_detalle.setText(""+cantidad_documento);

            for(int i=0;i<listaDDeudaEntity.size();i++){

               /* switch (listaDDeudaEntity.get(i).getMoneda()){
                    case "S/":
                        monto_cliente_detalle.add(new BigDecimal(listaDDeudaEntity.get(i).getSaldo())).setScale(3, RoundingMode.HALF_UP);
                        break;
                    case "US$":
                        monto_cliente_detalle_dolares.add(new BigDecimal(listaDDeudaEntity.get(i).getSaldo())).setScale(3, RoundingMode.HALF_UP);
                        break;
                    default:*/
                        monto_cliente_detalle=monto_cliente_detalle.add(new BigDecimal(listaDDeudaEntity.get(i).getSaldo()));
                        Log.e("Monto","=>>"+listaDDeudaEntity.get(i).getSaldo());
                        Log.e("Monto","=>"+monto_cliente_detalle.toString());
                  /*      break;
                }*/
            }

            Log.e("Monto","=>"+monto_cliente_detalle.toString());

            tv_monto_cliente_detalle.setText(Convert.currencyForView(monto_cliente_detalle.setScale(3, RoundingMode.HALF_UP).toString()));

            pd = ProgressDialog.show(getContext(), getActivity().getResources().getString(R.string.please_wait), getActivity().getResources().getString(R.string.querying_dates), true, false);

            listaClienteDetalleDao = new ListaClienteDetalleDao();
            pd.cancel();

            listaClienteDetalleAdapter = new ListaClienteDetalleAdapter(getActivity(),ListaClienteDetalleDao.getInstance().getLeads(listaDDeudaEntity,chkruta,domebarque));
            //listaClienteDetalleAdapter = new ListaClienteDetalleAdapter(getActivity(),listaDDeudaEntity);
            listaDDeuda.setAdapter(listaClienteDetalleAdapter);

        }

        private void obtenerLista() {
            }

        }



    public String getTexto()
    {
        return  getArguments().getString(TAG_1);
    }

/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("jpcm","regreso PerPerpERo");
        if(!ListenerBackPress.getCurrentFragment().equals("FormListClienteDetalleRutaVendedor")){
            ListenerBackPress.setCurrentFragment("FormListaDeudaCliente");
        }

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

        Log.e("jpcm","se regresoooo EERTTT");
        ListenerBackPress.setCurrentFragment("FormListaDeudaCliente");
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Log.e("jpcm","regreso PerPerpERo");
        /*if(!ListenerBackPress.getCurrentFragment().equals("FormListClienteDetalleRutaVendedor")){
            ListenerBackPress.setCurrentFragment("FormListaDeudaCliente");
        }*/

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
        //Log.e("jpcm","se regresoooo EERTTT");
        ListenerBackPress.setCurrentFragment("FormListaDeudaCliente");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_cliente_detalle, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

           case R.menu.menu_view:
               //getActivity().onBackPressed();
                // Not implemented here
                return true;
            case R.id.item_pago_adelantado:

                ArrayList <ListaClienteDetalleEntity> ArraylistaClienteDetalleEntity= new ArrayList <ListaClienteDetalleEntity>();
                ListaClienteDetalleEntity listaClienteDetalleEntity =  new ListaClienteDetalleEntity();
                listaClienteDetalleEntity.cliente_id=texto;
                listaClienteDetalleEntity.nombrecliente=nombrecliente;
                listaClienteDetalleEntity.domembarque=domebarque;
                Log.e("REOS","listaClienteDetalleEntity: domembarque: "+domebarque);
                listaClienteDetalleEntity.zona_id=zona_id;
                listaClienteDetalleEntity.chkruta=chkruta;
                Log.e("REOS","listaClienteDetalleEntity:  zona_id:"+zona_id);
                ArraylistaClienteDetalleEntity.add(listaClienteDetalleEntity);
                Object param1 = (Object)  ArraylistaClienteDetalleEntity;
                String Fragment="ClienteDetalleView";
                String accion="inicio";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,param1);
                return true;
            default:
                break;
        }

        return false;
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
        //void onFragmentInteraction(Uri uri);

        public void onFragmentInteraction(String tag,Object texto);
    }
    public void obtenerPametros()
    {
        imei=sesionEntity.imei;
        compania_id=sesionEntity.compania_id;
        fuerzatrabajo_id=sesionEntity.fuerzatrabajo_id;
    }



  /*
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instancias();
        textView.setText(this.getArguments().getString(TAG_1));

    }
*/
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {

      super.onActivityCreated(savedInstanceState);

      if (
              ContextCompat.checkSelfPermission(getContext(),
                      Manifest.permission.CAMERA)
                      != PackageManager.PERMISSION_GRANTED)
      {

          requestPermissions(new String[] {android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
      }

  }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
            {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                {

                } else
                {
                       /* while (ContextCompat.checkSelfPermission(this,
                                Manifest.permission.READ_PHONE_STATE)
                                != PackageManager.PERMISSION_GRANTED )
                        {*/
                    if ((ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED))
                    {
                        // ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                        //        MY_PERMISSIONS_REQUEST_CAMERA
                        //1
                        // );
                        requestPermissions(new String[] {android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                    //break;
                    //}
                }
                break;
            }
        }

    }

    private Dialog dialogStatusDispatch(String texto, Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_status_dispatch);
        Spinner spn_referral_guide,spn_type_dispatch,spn_reason_dispatch;
        TextView textMsj = dialog.findViewById(R.id.tv_titulo);
        textMsj.setText(texto);
        EditText et_comentario;
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        spn_referral_guide = dialog.findViewById(R.id.spn_referral_guide);
        spn_type_dispatch = dialog.findViewById(R.id.spn_type_dispatch);
        spn_reason_dispatch = dialog.findViewById(R.id.spn_reason_dispatch);
        et_comentario = dialog.findViewById(R.id.et_comentario);
        imageViewPhoto = v.findViewById(R.id.imageViewPhoto);
        imageViewPhoto.setBackgroundResource(R.drawable.portail);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        floatingButtonTakePhoto = (FloatingActionButton) dialog.findViewById(R.id.floatingButtonTakePhoto);

        floatingButtonTakePhoto.setOnClickListener(data -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                someActivityResultLauncher.launch(intent);
               // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Crea el File
                /*File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.e("log,",""+ex);
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(getContext(), getContext().getPackageName() , photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    getActivity().startActivityForResult(intent, 1);
                }*/
            }
        });

        dialogButtonOK.setOnClickListener(v -> {
            String encoded = null;
            if (imgBitmap != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imgBitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
                byteArray = stream.toByteArray();
                encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            }
            SimpleDateFormat dateFormathora = new SimpleDateFormat("HHmmss", Locale.getDefault());
            SimpleDateFormat FormatFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            Date date = new Date();
            List<StatusDispatchEntity> listStatusDispatchEntity=new ArrayList<>();
            UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
            UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
            ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();
            StatusDispatchSQLite statusDispatchSQLite=new StatusDispatchSQLite(getContext());

            StatusDispatchEntity statusDispatchEntity=new StatusDispatchEntity();
            statusDispatchEntity.compania_id=ObjUsuario.compania_id;
            statusDispatchEntity.fuerzatrabajo_id=ObjUsuario.fuerzatrabajo_id;
            statusDispatchEntity.usuario_id=ObjUsuario.usuario_id;
            statusDispatchEntity.Delivered =spn_type_dispatch.getSelectedItem().toString();
            statusDispatchEntity.ReturnReason =spn_reason_dispatch.getSelectedItem().toString();
            statusDispatchEntity.entrega_id =spn_referral_guide.getSelectedItem().toString();
            statusDispatchEntity.cliente_id =texto;
            statusDispatchEntity.factura_id ="";
            statusDispatchEntity.chkrecibido ="0";
            statusDispatchEntity.Comments =et_comentario.getText().toString();
            statusDispatchEntity.foto =encoded;
            statusDispatchEntity.fecha_registro =FormatFecha.format(date);
            statusDispatchEntity.hora_registro =dateFormathora.format(date);
            listStatusDispatchEntity.add(statusDispatchEntity);
            statusDispatchSQLite.addStatusDispatch(listStatusDispatchEntity);

            dialog.dismiss();
        });

        dialogButtonCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String CurrentDateAndTime = getCurrentDateAndTime();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        String TAG="tag";
        Log.d(TAG,"el path de la imagen es = " + mCurrentPhotoPath);
        MenuView.mCurrentPhotoPath=mCurrentPhotoPath;
        //return file;
        return image;
    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }
}
