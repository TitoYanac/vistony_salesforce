package com.vistony.salesforce.View;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

/*import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MantenimientoClienteView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MantenimientoClienteView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MantenimientoClienteView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView tv_nombrecliente,tv_ruccliente,tv_codigocliente,tv_codigodircliente,tv_dircliente;
    EditText et_telfmovilcliente,et_telffijocliente;
    CheckBox chk_telfmovilcliente,chk_telffijocliente;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    FloatingActionButton fabeditlocation;
    private OnFragmentInteractionListener mListener;
    ObtenerSQLiteCliente obtenerSQLiteCliente;
    public MantenimientoClienteView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MantenimientoClienteView.
     */
    // TODO: Rename and change types and number of parameters
    public static MantenimientoClienteView newInstance(Object param1) {
        MantenimientoClienteView fragment = new MantenimientoClienteView();
        Bundle args = new Bundle();
        String parametro=(String) param1;
        args.putString(ARG_PARAM1, parametro);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Editar Cliente");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_mantenimiento_cliente_view, container, false);
        fabeditlocation = (FloatingActionButton) v.findViewById(R.id.fabeditlocation);

        tv_nombrecliente = (TextView) v.findViewById(R.id.tv_nombrecliente);
        tv_ruccliente = (TextView) v.findViewById(R.id.tv_ruccliente);
        tv_codigocliente = (TextView) v.findViewById(R.id.tv_codigocliente);
        tv_codigodircliente = (TextView) v.findViewById(R.id.tv_codigodircliente);
        tv_dircliente = (TextView) v.findViewById(R.id.tv_dircliente);
        et_telfmovilcliente = (EditText) v.findViewById(R.id.et_telfmovilcliente);
        et_telffijocliente = (EditText) v.findViewById(R.id.et_telffijocliente);
        chk_telfmovilcliente = (CheckBox) v.findViewById(R.id.chk_telfmovilcliente);
        chk_telffijocliente = (CheckBox) v.findViewById(R.id.chk_telffijocliente);

        fabeditlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag="MantenimientoClienteView";
                String accion="";
                String compuesto=tag+"-"+accion;
                Object object=null;
                mListener.onFragmentInteraction(compuesto,object);
            }
            });

        chk_telfmovilcliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chk_telfmovilcliente.isChecked())
                {
                    et_telfmovilcliente.setEnabled(true);
                }
                else
                {
                    et_telfmovilcliente.setEnabled(false);
                }

            }});
        chk_telffijocliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chk_telffijocliente.isChecked())
                {
                    et_telffijocliente.setEnabled(true);
                }
                else
                {
                    et_telffijocliente.setEnabled(false);
                }

            }});


        obtenerSQLiteCliente=new ObtenerSQLiteCliente();
        obtenerSQLiteCliente.execute();
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

    public class ObtenerSQLiteCliente extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... arg0) {
            ArrayList<ClienteSQLiteEntity> listaClienteSQLiteEntity = new ArrayList<>();
            try {

                ClienteSQlite clienteSQlite = new ClienteSQlite(getContext());
                listaClienteSQLiteEntity= clienteSQlite.ObtenerDatosCliente(mParam1, SesionEntity.compania_id);

                //listaDDeudaEntity=documentoDeudaSQLiteDao.ObtenerDDeudaporcliente(compania_id,fuerzatrabajo_id,texto);


            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return listaClienteSQLiteEntity ;
        }

        protected void onPostExecute(Object result) {
            ArrayList<ClienteSQLiteEntity> listaClienteSQLiteEntity2 = new ArrayList<>();
            listaClienteSQLiteEntity2=(ArrayList<ClienteSQLiteEntity>) result;
            for(int i=0;i<listaClienteSQLiteEntity2.size();i++)
            {
                tv_nombrecliente.setText(listaClienteSQLiteEntity2.get(i).getNombrecliente());
                tv_ruccliente.setText(listaClienteSQLiteEntity2.get(i).getCliente_id());
                tv_codigocliente.setText(listaClienteSQLiteEntity2.get(i).getCliente_id());
                tv_codigodircliente.setText(listaClienteSQLiteEntity2.get(i).getDomembarque_id());
                tv_dircliente.setText(listaClienteSQLiteEntity2.get(i).getDireccion());


            }

        }
    }
}
