package com.vistony.salesforce.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReclamoClientesView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReclamoClientesView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View v;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private WebView webView;
    private ProgressDialog progDailog;
    String URLWeb;
    private OnFragmentInteractionListener mListener;
    private ProgressDialog pd;
    private ValueCallback<Uri> uploadMessage;
    public static ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;

    public ReclamoClientesView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SugClienteView.
     */
    // TODO: Rename and change types and number of parameters
    public static ReclamoClientesView newInstance(String param1, String param2) {
        ReclamoClientesView fragment = new ReclamoClientesView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        v = inflater.inflate(R.layout.fragment_sug_cliente_view,container,false);
        getActivity().setTitle("Reclamos de Clientes");

        progDailog = ProgressDialog.show(getActivity(), "Loading", "Please wait...", true);
        progDailog.setCancelable(false);

         webView = (WebView)v.findViewById(R.id.WebView);


        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                openImageChooserActivity();
                return true;
            }

            // progDailog.show();
            //view.loadUrl(url);

            // progDailog.dismiss();


            public void onProgressChanged(WebView view, int newProgress) {
                progDailog.dismiss();
            }
        });


        /*CifradoController cifradoController=new CifradoController();
        String parametros=SesionEntity.fuerzatrabajo_id+","+SesionEntity.nombrefuerzadetrabajo+","+SesionEntity.imei;
        String encData="";
        try {
            encData= CifradoController.encrypt("Vistony2019*".getBytes("UTF-16LE"), parametros.getBytes("UTF-16LE"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("JPCM","http://104.131.186.170/formulario?token="+encData);
        webView.loadUrl("http://104.131.186.170/formulario?token="+encData);*/
        Uri uri=Uri.parse(SesionEntity.nombrefuerzadetrabajo);
        Log.e("REOS","https://reclamos.vistonyapp.com/formulario?value="+SesionEntity.imei+","+SesionEntity.fuerzatrabajo_id+","+uri);
        webView.loadUrl("https://reclamos.vistonyapp.com/formulario?value="+SesionEntity.imei+","+SesionEntity.fuerzatrabajo_id+","+uri);







        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
  /*  public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

        private void openImageChooserActivity()
        {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            // i.setType("image/*");//Image upload
            // i.setType("file/*");//File upload
            i.setType("*/*");//File upload
            getActivity().startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
        }

        /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ListenerBackPress.setCurrentFragment("Webview");
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
        super.onAttach(context);
        ListenerBackPress.setCurrentFragment("Webview");
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
        void onFragmentInteraction(String tag, Object dato);
    }
}

