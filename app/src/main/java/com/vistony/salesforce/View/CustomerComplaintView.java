package com.vistony.salesforce.View;

import static android.app.Activity.RESULT_OK;

import static com.vistony.salesforce.View.MenuView.closeSilently;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.ListCustomerComplaintAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.ImageCameraController;
import com.vistony.salesforce.Dao.Retrofit.CustomerComplaintRepository;
import com.vistony.salesforce.Dao.Retrofit.FormsRepository;
import com.vistony.salesforce.Dao.Retrofit.KardexPagoRepository;
import com.vistony.salesforce.Dao.Retrofit.UbigeoRepository;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintFormsEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintQuestionsEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintResponseEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintSectionEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteDetalleEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerComplaintView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerComplaintView extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static MutableLiveData<String> RutaFilePhoto=new MutableLiveData<>();
    public static MutableLiveData<String> RutaFileVideo=new MutableLiveData<>();
    public static MutableLiveData<String> RutaFileAttach=new MutableLiveData<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    static ListView list_customer_complaint,list_document,list_cardview;
    static private ProgressDialog pd;
    Button btn_getclient,btn_getdocument;
    public static OnFragmentInteractionListener mListener;
    static TextView tv_ruc,tv_cardname,tv_address;
    static ArrayList<ListaClienteCabeceraEntity> obtClient;
    Context context;
    ListCustomerComplaintAdapter listCustomerComplaintAdapter;
    CardView cardView;
    private static String TAG_1 = "data";
    Button btnSubtmit;
    CustomerComplaintRepository customerComplaintRepository;
    static LifecycleOwner lifecycleOwner;
    public static ActivityResultLauncher<Intent> someActivityResultLauncherPhotomake;
    public static ActivityResultLauncher<Intent> someActivityResultLauncherPhotoAttach;
    public static String mCurrentPhotoPathG="",mCurrentPhotoPathL="";
    static MenuItem send,save;
    public static ActivityResultLauncher<Intent> someActivityResultLauncherVideoAttach;
    public static ActivityResultLauncher<Intent> someActivityResultLauncherFileAttach;
    private FormsRepository formsRepository;


    public CustomerComplaintView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerComplaintView.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerComplaintView newInstance(String param1, String param2) {
        CustomerComplaintView fragment = new CustomerComplaintView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static CustomerComplaintView newInstancesetClient (Object objeto) {
        CustomerComplaintView fragment = new CustomerComplaintView();
        Bundle args = new Bundle();
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        obtClient = (ArrayList<ListaClienteCabeceraEntity>) objeto;
        for(int s=0;s<obtClient.size();s++){
            Log.e("REOS","CustomerComplaintView.newInstancesetClient.Lista.get(s).getDireccion()"+obtClient.get(s).getDireccion());
            Log.e("REOS","CustomerComplaintView.newInstancesetClient.Lista.get(s).getDomembarque_id()"+obtClient.get(s).getDomembarque_id());
            Log.e("REOS","CustomerComplaintView.newInstancesetClient.Lista.get(s).getDomfactura_id()"+obtClient.get(s).getDomfactura_id());
            Log.e("REOS","CustomerComplaintView.newInstancesetClient.Lista.get(s).getCliente_id()"+obtClient.get(s).getCliente_id());
            Log.e("REOS","CustomerComplaintView.newInstancesetClient.Lista.get(s).getZona_id()"+obtClient.get(s).getZona_id());;
            tv_cardname.setText(obtClient.get(s).getNombrecliente());
            tv_address.setText(obtClient.get(s).getDireccion());
            tv_ruc.setText(obtClient.get(s).getRucdni());
        }
        tv_ruc.setHint("RUC/DNI");
        tv_ruc.setGravity(Gravity.TOP);
        //getListKardexOfPayment(CardCode);
        fragment.setArguments(args);

        return fragment;
    }

    public static CustomerComplaintView newInstanceReceipDocument(Object objeto) {
        //Log.e("jpcm","Este es NUEVA ISNTANCIA 3 ESTO VA PARA CLIENTE VERIFICADO");

        ListenerBackPress.setCurrentFragment("FormDetalleCobranzaCliente");
        CustomerComplaintView fragment = new CustomerComplaintView();
        ArrayList<String> Listado = new ArrayList<String>();
        Bundle b = new Bundle();
        ArrayList<ListaClienteDetalleEntity> Lista = (ArrayList<ListaClienteDetalleEntity>) objeto;
        Lista.size();
        //b.putSerializable(TAG_1, Lista);
        Log.e("REOS","CustomerComplaint-onCreate-newInstanceReceipDocument-Lista.size() "+Lista.size());
        fragment.setArguments(b);
        return fragment;
    }

    public static CustomerComplaintView newInstance(Object objeto) {

        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        CustomerComplaintView CustomerComplaintView = new CustomerComplaintView();
        Bundle b = new Bundle();
        ArrayList<ListaClienteCabeceraEntity> Lista = (ArrayList<ListaClienteCabeceraEntity>) objeto;
        b.putSerializable(TAG_1,Lista);
        CustomerComplaintView.setArguments(b);
        obtClient = (ArrayList<ListaClienteCabeceraEntity>) objeto;
        for(int s=0;s<obtClient.size();s++){
            Log.e("REOS","CustomerComplaintView.newInstance.Lista.get(s).getDireccion()"+obtClient.get(s).getDireccion());
            Log.e("REOS","CustomerComplaintView.newInstance.Lista.get(s).getDomembarque_id()"+obtClient.get(s).getDomembarque_id());
            Log.e("REOS","CustomerComplaintView.newInstance.Lista.get(s).getDomfactura_id()"+obtClient.get(s).getDomfactura_id());
            Log.e("REOS","CustomerComplaintView.newInstance.Lista.get(s).getCliente_id()"+obtClient.get(s).getCliente_id());
            Log.e("REOS","CustomerComplaintView.newInstance.Lista.get(s).getZona_id()"+obtClient.get(s).getZona_id());;
        }
        return CustomerComplaintView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("Reclamos");
        context=getContext();
        customerComplaintRepository = new ViewModelProvider(getActivity()).get(CustomerComplaintRepository.class);
        lifecycleOwner=getActivity();
        setHasOptionsMenu(true);
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
        v= inflater.inflate(R.layout.fragment_customer_complaint_view, container, false);
        list_customer_complaint=v.findViewById(R.id.list_customer_complaint);
        list_document=v.findViewById(R.id.list_document);
        btn_getclient=v.findViewById(R.id.btn_getclient);
        btn_getdocument=v.findViewById(R.id.btn_getdocument);
        tv_ruc=v.findViewById(R.id.tv_ruc);
        tv_cardname=v.findViewById(R.id.tv_cardname);
        tv_address=v.findViewById(R.id.tv_address);
        list_cardview=v.findViewById(R.id.list_cardview);
        cardView=v.findViewById(R.id.cardView);
        btnSubtmit=v.findViewById(R.id.btnSubtmit);
        cardView.setVisibility(View.GONE);
        btnSubtmit.setVisibility(View.GONE);
        formsRepository = new ViewModelProvider(getActivity()).get(FormsRepository.class);


        btn_getclient.setOnClickListener(v -> {
            String Fragment="CustomerComplaintView";
            String accion="findClient";
            String compuesto=Fragment+"-"+accion;
            String objeto="customerComplaintView";
            mListener.onFragmentInteraction(compuesto, objeto);
        });
        btn_getdocument.setOnClickListener(v -> {
            Log.e("REOS","CustomerComplaint-onCreate-btn_getdocument");
            String Fragment="CustomerComplaintView";
            String accion="CustomerComplaintViewcobranza";
            String compuesto=Fragment+"-"+accion;
            mListener.onFragmentInteraction(compuesto,obtClient);
            SesionEntity.pagodirecto="N";
            SesionEntity.pagopos="N";
            SesionEntity.collectioncheck="N";
            SesionEntity.collection_salesperson="N";

        });
        getListCustomerComplaint();

        someActivityResultLauncherPhotomake = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult()
                , result -> {
                    File fileguia;

                    if (result.getResultCode() == RESULT_OK) {
                        Bitmap bitmap2=null;
                        try {
                            File file = new File(mCurrentPhotoPathG);


                            bitmap2 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.fromFile(file));
                            ImageCameraController imageCameraController = new ImageCameraController();
                            fileguia=imageCameraController.SaveImage (getContext(),bitmap2,ListCustomerComplaintAdapter.foto_id);

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "No se pudo mostrar la imagen en miniatura - error: "+e.toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "No se pudo mostrar la imagen en miniatura - error: "+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                });

        someActivityResultLauncherPhotoAttach= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult()
                , result -> {
                    Log.e("jpcm", "ingreso 155 add foto");

                    Uri uriPhoto = result.getData().getData();

                    File sourceFile2 = new File(getPathFromGooglePhotosUri(uriPhoto));

                    //////////////////////////////////////////
                    Bitmap bitmap3=null;
                    try {
                        bitmap3 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(sourceFile2));


                        String resultado;
                        SesionEntity.imagen = "DEP";

                        ImageCameraController imageCameraController3 = new ImageCameraController();
                        resultado=imageCameraController3.SaveImage (getContext(),bitmap3).toString();
                        RutaFilePhoto.setValue(resultado);
                        Log.e("REOS","CustomerComplaintView-someActivityResultLauncherPhotoAttach-resultado: "+resultado.toString());
                        Toast.makeText(getContext(), "Imagen adjuntada...", Toast.LENGTH_SHORT).show();
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            // Procesar el resultado aquí si es necesario
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        someActivityResultLauncherVideoAttach= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult()
                , result -> {
                    try {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Uri uri = result.getData().getData();
                            String filePath = getRealPathFromURI(uri,getActivity(),"Video");
                            Log.e("REOS","CustomerComplaintView-someActivityResultLauncherVideoAttach-filePath: "+filePath.toString());
                            RutaFileVideo.setValue(filePath);
                            Toast.makeText(getContext(), "Imagen adjuntada...", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        someActivityResultLauncherFileAttach= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult()
                , result -> {
                    try {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Uri uri = result.getData().getData();
                            String filePath = getRealPathFromURI(uri,getActivity(),"File");
                            //Log.e("REOS","CustomerComplaintView-someActivityResultLauncherFileAttach-filePath: "+filePath.toString());
                            RutaFileAttach.setValue(filePath);
                            Toast.makeText(getContext(), "Imagen adjuntada...", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        return v;
    }

    private String getRealPathFromURI(Uri contentUri,Activity activity,String type) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.getContentResolver().query(contentUri, projection, null, null, null);
        int columnIndex=0;
        String filePath="";
        try
        {
            if (type.equals("Photo")) {
                columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                filePath = cursor.getString(columnIndex);
                cursor.close();
            } else if (type.equals("Video")) {
                columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                cursor.moveToFirst();
                filePath = cursor.getString(columnIndex);
                cursor.close();
            } else if (type.equals("File")) {
                InputStream inputStream = null;

                // Obtener el nombre del archivo
                cursor = activity.getContentResolver().query(contentUri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME);
                    filePath = cursor.getString(nameIndex);
                    cursor.close();
                }

                // Obtener los datos del archivo
                try {
                    inputStream = activity.getContentResolver().openInputStream(contentUri);
                    // Leer los datos del archivo
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Usa el valor del nombre del archivo y los datos del archivo aquí
                if (filePath != null && inputStream != null) {
                    // Do something with fileName and inputStream
                    Log.e("REOS","CustomerComplaintView-getRealPathFromURI-filePath: "+filePath.toString());
                    Log.e("REOS","CustomerComplaintView-getRealPathFromURI-inputStream: "+inputStream.toString());
                }
                //columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns);
                /*columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
                filePath = cursor.getString(columnIndex);
                cursor.close();*/
                /*String[] projection1 = { MediaStore.Files.FileColumns.DATA };
                Cursor cursor1 = activity.getContentResolver().query(contentUri, projection1, null, null, null);
                //int columnIndex=0;
                if (cursor1 != null && cursor1.moveToFirst()) {
                    columnIndex = cursor1.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
                    filePath = cursor1.getString(columnIndex);
                    cursor1.close();
                    // Usa el valor de la ruta de acceso del archivo aquí
                } else {
                    Log.e("REOS","CustomerComplaintView-getRealPathFromURI-CursorVacio");
                    // El cursor está vacío o no contiene filas, maneja el error aquí
                }*/
                /*if ("file".equals(contentUri.getScheme())) {
                    // Si el esquema es "file", obtenemos la ruta de acceso directamente desde el objeto Uri
                    filePath = contentUri.getPath();
                } else {
                    // Si el esquema no es "file", convertimos la Uri en un objeto File para obtener su ruta de acceso
                    File file = new File(contentUri.getPath());
                    if (file.exists()) {
                        filePath = file.getAbsolutePath();
                    }
                }*/
                /*String[] projection1 = {MediaStore.Files.FileColumns.};
                Cursor cursor1 = activity.getContentResolver().query(contentUri, projection1, null, null, null);
                if (cursor1 != null && cursor1.moveToFirst()) {
                    int columnIndex1 = cursor1.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
                    filePath = cursor1.getString(columnIndex1);
                    cursor1.close();
                    // Usa el valor de la ruta de acceso del archivo aquí
                } else {
                    // El cursor está vacío o no contiene filas, maneja el error aquí
                }*/
            }
        }catch (Exception e){
            Log.e("REOS","CustomerComplaintView-getRealPathFromURI-error: "+e.toString());
        }

        if (filePath == null) {
            // Do something with filePath
            filePath="vacio";
        }

        return filePath;
    }

    public String getPathFromGooglePhotosUri(Uri uriPhoto) {
        if (uriPhoto == null)
            return null;

        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            ParcelFileDescriptor pfd = getActivity(). getContentResolver() .openFileDescriptor(uriPhoto, "r");
            FileDescriptor fd = pfd.getFileDescriptor();
            input = new FileInputStream(fd);

            String tempFilename = getTempFilename(getActivity());
            output = new FileOutputStream(tempFilename);

            int read;
            byte[] bytes = new byte[4096];
            while ((read = input.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }
            return tempFilename;
        } catch (IOException ignored) {
            // Nothing we can do
        } finally {
            closeSilently(input);
            closeSilently(output);
        }
        return null;
    }

    private static String getTempFilename(Context context) throws IOException {
        File outputDir = context.getCacheDir();
        File outputFile = File.createTempFile("image", "tmp", outputDir);
        return outputFile.getAbsolutePath();
    }


    private void getListCustomerComplaint()
    {
        Log.e("REOS","CustomerComplaintView-getListCustomerComplaint.ingreso ");
        try {
            //pd = new ProgressDialog(context);
            //pd = ProgressDialog.show(context, context.getResources().getString(R.string.please_wait), context.getResources().getString(R.string.querying_dates), true, false);
            ArrayList<CustomerComplaintFormsEntity> listCustomerComplaintFormsEntity = new ArrayList<>();
            CustomerComplaintFormsEntity objListCustomerComplaintFormsEntity = new CustomerComplaintFormsEntity();
            objListCustomerComplaintFormsEntity.setForms("MODULO DE RECLAMOS");
            List<CustomerComplaintSectionEntity> ArrayListCustomerComplaintSectionEntity = new ArrayList<>();
            CustomerComplaintSectionEntity listCustomerComplaintSectionEntity = new CustomerComplaintSectionEntity();
            listCustomerComplaintSectionEntity.setSection("DATOS DE CLIENTE");
            CustomerComplaintQuestionsEntity listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            ArrayList<CustomerComplaintQuestionsEntity> arrayListCustomerComplaintQuestionsEntity=new ArrayList<>();
            listCustomerComplaintQuestionsEntity.setQuestion("Nombre ó Razón Social");
            listCustomerComplaintQuestionsEntity.setType("RT");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
             listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            listCustomerComplaintQuestionsEntity.setQuestion("Documento de Identidad");
            listCustomerComplaintQuestionsEntity.setType("RT");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            listCustomerComplaintQuestionsEntity.setQuestion("Dirección");
            listCustomerComplaintQuestionsEntity.setType("RT");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            listCustomerComplaintSectionEntity.setListCustomercomplaintQuestions(arrayListCustomerComplaintQuestionsEntity);
            ArrayListCustomerComplaintSectionEntity.add(listCustomerComplaintSectionEntity);
            ///////////////////////////////////////////////////////////////
            //ArrayListCustomerComplaintSectionEntity = new ArrayList<>();
            listCustomerComplaintSectionEntity = new CustomerComplaintSectionEntity();
            listCustomerComplaintSectionEntity.setSection("DATOS DE CLIENTE2");
            listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            arrayListCustomerComplaintQuestionsEntity=new ArrayList<>();
            listCustomerComplaintQuestionsEntity.setQuestion("Nombre ó Razón Social");
            listCustomerComplaintQuestionsEntity.setType("RT");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            listCustomerComplaintQuestionsEntity.setQuestion("Documento de Identidad");
            listCustomerComplaintQuestionsEntity.setType("RT");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            listCustomerComplaintQuestionsEntity.setQuestion("Dirección");
            listCustomerComplaintQuestionsEntity.setType("RT");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            /*listCustomerComplaintSectionEntity.setListCustomercomplaintQuestions(arrayListCustomerComplaintQuestionsEntity);
            ArrayListCustomerComplaintSectionEntity.add(listCustomerComplaintSectionEntity);*/

            ///////////////////////////////////////////////////////////////
            //ArrayListCustomerComplaintSectionEntity = new ArrayList<>();
            listCustomerComplaintSectionEntity = new CustomerComplaintSectionEntity();
            listCustomerComplaintSectionEntity.setSection("Sustento y Descripción del Reclamo");
            listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            arrayListCustomerComplaintQuestionsEntity=new ArrayList<>();
            listCustomerComplaintQuestionsEntity.setQuestion("Fecha de Reclamo");
            listCustomerComplaintQuestionsEntity.setType("RT");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            listCustomerComplaintQuestionsEntity.setQuestion("Sectorista Responsable");
            listCustomerComplaintQuestionsEntity.setType("RT");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            listCustomerComplaintQuestionsEntity.setQuestion("Vendedor");
            listCustomerComplaintQuestionsEntity.setType("RT");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            listCustomerComplaintQuestionsEntity.setQuestion("Factura / Boleta de Venta");
            listCustomerComplaintQuestionsEntity.setType("RT");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            listCustomerComplaintQuestionsEntity.setQuestion("Guía de Remisión");
            listCustomerComplaintQuestionsEntity.setType("RT");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            listCustomerComplaintQuestionsEntity.setQuestion("Fecha de Factura / Boleta de Venta");
            listCustomerComplaintQuestionsEntity.setType("RT");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            listCustomerComplaintSectionEntity.setListCustomercomplaintQuestions(arrayListCustomerComplaintQuestionsEntity);
            ArrayListCustomerComplaintSectionEntity.add(listCustomerComplaintSectionEntity);
            //////////////////////////////////////////////////////
            listCustomerComplaintSectionEntity = new CustomerComplaintSectionEntity();
            listCustomerComplaintSectionEntity.setSection("DATOS DE DOCUMENTO");
            listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            arrayListCustomerComplaintQuestionsEntity=new ArrayList<>();
            listCustomerComplaintQuestionsEntity.setQuestion("Motivo del Reclamo");
            listCustomerComplaintQuestionsEntity.setType("RU");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            CustomerComplaintResponseEntity customerComplaintResponseEntity=new CustomerComplaintResponseEntity();
            ArrayList<CustomerComplaintResponseEntity> arrayListCustomerComplaintResponseEntity=new ArrayList<>();
            customerComplaintResponseEntity.setResponse("RespuestaUnica1");
            arrayListCustomerComplaintResponseEntity.add(customerComplaintResponseEntity);
            customerComplaintResponseEntity=new CustomerComplaintResponseEntity();
            customerComplaintResponseEntity.setResponse("RespuestaUnica2");
            arrayListCustomerComplaintResponseEntity.add(customerComplaintResponseEntity);
            customerComplaintResponseEntity=new CustomerComplaintResponseEntity();
            customerComplaintResponseEntity.setResponse("RespuestaUnica3");
            arrayListCustomerComplaintResponseEntity.add(customerComplaintResponseEntity);
            listCustomerComplaintQuestionsEntity.setListCustomerComplaintResponse(arrayListCustomerComplaintResponseEntity);
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            listCustomerComplaintQuestionsEntity.setQuestion("Pregunta 7");
            listCustomerComplaintQuestionsEntity.setType("RU");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            customerComplaintResponseEntity=new CustomerComplaintResponseEntity();
            arrayListCustomerComplaintResponseEntity=new ArrayList<>();
            customerComplaintResponseEntity.setResponse("RespuestaUnica4");
            arrayListCustomerComplaintResponseEntity.add(customerComplaintResponseEntity);
            customerComplaintResponseEntity=new CustomerComplaintResponseEntity();
            customerComplaintResponseEntity.setResponse("RespuestaUnica5");
            arrayListCustomerComplaintResponseEntity.add(customerComplaintResponseEntity);
            customerComplaintResponseEntity=new CustomerComplaintResponseEntity();
            customerComplaintResponseEntity.setResponse("RespuestaUnica6");
            arrayListCustomerComplaintResponseEntity.add(customerComplaintResponseEntity);
            listCustomerComplaintQuestionsEntity.setListCustomerComplaintResponse(arrayListCustomerComplaintResponseEntity);
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            //////////////////////////////////////////////////////////////////////////////////////////
            listCustomerComplaintSectionEntity.setListCustomercomplaintQuestions(arrayListCustomerComplaintQuestionsEntity);
            ArrayListCustomerComplaintSectionEntity.add(listCustomerComplaintSectionEntity);
            listCustomerComplaintSectionEntity = new CustomerComplaintSectionEntity();
            listCustomerComplaintSectionEntity.setSection( "DATOS DE RECLAMO");
            listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            arrayListCustomerComplaintQuestionsEntity=new ArrayList<>();
            listCustomerComplaintQuestionsEntity.setQuestion("Motivo del Reclamo");
            listCustomerComplaintQuestionsEntity.setType("RM");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RP");
            customerComplaintResponseEntity=new CustomerComplaintResponseEntity();
            arrayListCustomerComplaintResponseEntity=new ArrayList<>();
            customerComplaintResponseEntity.setResponse("Envases filtrados");
            arrayListCustomerComplaintResponseEntity.add(customerComplaintResponseEntity);
            customerComplaintResponseEntity=new CustomerComplaintResponseEntity();
            customerComplaintResponseEntity.setResponse("Envases semillenos");
            arrayListCustomerComplaintResponseEntity.add(customerComplaintResponseEntity);
            customerComplaintResponseEntity=new CustomerComplaintResponseEntity();
            customerComplaintResponseEntity.setResponse("Despacho con faltante de mercaderia");
            arrayListCustomerComplaintResponseEntity.add(customerComplaintResponseEntity);
            listCustomerComplaintQuestionsEntity.setListCustomerComplaintResponse(arrayListCustomerComplaintResponseEntity);
            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            listCustomerComplaintSectionEntity.setListCustomercomplaintQuestions(arrayListCustomerComplaintQuestionsEntity);
            ArrayListCustomerComplaintSectionEntity.add(listCustomerComplaintSectionEntity);
            objListCustomerComplaintFormsEntity.setListCustomerComplaintSection(ArrayListCustomerComplaintSectionEntity);
            /////////////////////////////////////////////////////////////////////////////////////////
            listCustomerComplaintSectionEntity = new CustomerComplaintSectionEntity();
            listCustomerComplaintSectionEntity.setSection("DATOS DE ADJUNTO");
            listCustomerComplaintQuestionsEntity=new CustomerComplaintQuestionsEntity();
            arrayListCustomerComplaintQuestionsEntity=new ArrayList<>();
            listCustomerComplaintQuestionsEntity.setQuestion("Pregunta 6");
            listCustomerComplaintQuestionsEntity.setType("RA");
            listCustomerComplaintQuestionsEntity.setQuestionsEdit("RU");
            customerComplaintResponseEntity=new CustomerComplaintResponseEntity();
            arrayListCustomerComplaintResponseEntity=new ArrayList<>();
            customerComplaintResponseEntity.setResponse("RespuestaUnica1");
            arrayListCustomerComplaintResponseEntity.add(customerComplaintResponseEntity);
            customerComplaintResponseEntity=new CustomerComplaintResponseEntity();
            customerComplaintResponseEntity.setResponse("RespuestaUnica2");
            arrayListCustomerComplaintResponseEntity.add(customerComplaintResponseEntity);
            customerComplaintResponseEntity=new CustomerComplaintResponseEntity();
            customerComplaintResponseEntity.setResponse("RespuestaUnica3");
            arrayListCustomerComplaintResponseEntity.add(customerComplaintResponseEntity);
            listCustomerComplaintQuestionsEntity.setListCustomerComplaintResponse(arrayListCustomerComplaintResponseEntity);

            arrayListCustomerComplaintQuestionsEntity.add(listCustomerComplaintQuestionsEntity);
            listCustomerComplaintSectionEntity.setListCustomercomplaintQuestions(arrayListCustomerComplaintQuestionsEntity);
            ArrayListCustomerComplaintSectionEntity.add(listCustomerComplaintSectionEntity);
            /////////////////////////////////////////////////////////////////////////////////////
            listCustomerComplaintFormsEntity.add(objListCustomerComplaintFormsEntity);
            Log.e("REOS","CustomerComplaintView-getListCustomerComplaint.listCustomerComplaintFormsEntity.size() "+listCustomerComplaintFormsEntity.size());

            /*pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), getActivity().getResources().getString(R.string.please_wait), getActivity().getResources().getString(R.string.querying_dates), true, false);
                customerComplaintRepository.getCustomerComplaint().observe(lifecycleOwner, data -> {
                Convert convert=new Convert();
                Log.e("Jepicame","=>"+data);
                if(data!=null)
                {
                    listCustomerComplaintAdapter = new ListCustomerComplaintAdapter
                            (
                                    getContext(),
                                    data.getListCustomerComplaintSection(),
                                    getActivity(),
                                    this
                            //listCustomerComplaintFormsEntity.get(0).getListCustomerComplaintSection()
                            );
                    list_cardview.setAdapter(listCustomerComplaintAdapter);
                }else
                {

                    Toast.makeText(getActivity(), getActivity() .getResources().getString(R.string.mse_not_data_available), Toast.LENGTH_SHORT).show();
                    //alertdialogInformative(context,activity.getResources().getString(R.string.important),activity.getResources().getString(R.string.mse_not_data_available)).show();
                }
                pd.dismiss();
            });*/

            listCustomerComplaintAdapter = new ListCustomerComplaintAdapter
                    (
                            getContext(),
                            //data.getListCustomerComplaintSection(),

                            listCustomerComplaintFormsEntity.get(0).getListCustomerComplaintSection(),
                            getActivity(),
                            getActivity()
                    );
            list_cardview.setAdapter(listCustomerComplaintAdapter);
        /*listKardexOfPaymentAdapter
                =new ListKardexOfPaymentAdapter(
                activity,
                listKardexOfPaymentEntityList);
        lv_kardex_of_payment.setAdapter(listKardexOfPaymentAdapter);*/

            //pd.dismiss();
        }catch (Exception e)
        {
            Log.e("REOS","CustomerComplaintView-getListCustomerComplaint.e "+e.toString());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_getclient:
                String Fragment="CustomerComplaintView";
                String accion="findClient";
                String compuesto=Fragment+"-"+accion;
                String objeto="customerComplaintView";
                mListener.onFragmentInteraction(compuesto, objeto);
                break;
            default:
                break;
        }
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
        inflater.inflate(R.menu.menu_customer_complaint, menu);
        send = menu.findItem(R.id.send);
        save = menu.findItem(R.id.save);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send:
                /*listCustomerComplaintAdapter.getCustomerSection();*/
                formsRepository.getDataSendForms
                        (getContext()).observe(getActivity(), data -> {
                    Log.e("REOS","CustomersComplaintView-onOptionsItemSelected-R.id.send.data"+data);
                });
                break;
            case R.id.save:
                try {
                    listCustomerComplaintAdapter.getCustomerSection();
                    Toast.makeText(getContext(),
                            "Se Guardo Correctamente el Reclamo"
                            , Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {
                    Log.e("REOS","CustomersComplaintView-onOptionsItemSelected-R.id.save.error: "+e.toString());
                }
                /*formsRepository.getDataSendForms
                        (getContext()).observe(getActivity(), data -> {
                    Log.e("REOS","CustomersComplaintView-onOptionsItemSelected-R.id.send.data"+data);
                });*/
                break;
            default:
                break;
        }

        return false;
    }



}
