package com.vistony.salesforce.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.vistony.salesforce.Controller.Adapters.ListaCatalogoAdapter;
import com.vistony.salesforce.Dao.Retrofit.CatalogoWS;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CatalogoView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatalogoView extends Fragment implements SearchView.OnQueryTextListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    ListView listacatalogo;
    ListaCatalogoAdapter listaCatalogoAdapter;
    //ObtenerSQLiteCatalogo obtenerSQLiteCatalogo;
    OnFragmentInteractionListener mListener;
    SearchView mSearchView;
    public CatalogoView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CatalogoView.
     */
    // TODO: Rename and change types and number of parameters
    public static CatalogoView newInstance(String param1, String param2) {
        CatalogoView fragment = new CatalogoView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Ficha Tecnica");
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_catalogo_view, container, false);
        listacatalogo=v.findViewById(R.id.listacatalogo);
        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Cargando");
        progress.setMessage("Por favor espere...");
        progress.setCancelable(false);
        progress.show();
        CatalogoWS xd=new CatalogoWS(getActivity());
        xd.send("asdasd").observe(getActivity(), data -> {
            progress.dismiss();
            if(data.getClass().getName().equals("java.lang.String")){
                getFragmentManager().popBackStack();
                Toast.makeText(getContext(), data.toString(), Toast.LENGTH_SHORT).show();
            }else{
                listaCatalogoAdapter= (ListaCatalogoAdapter) data;
                listacatalogo.setAdapter(listaCatalogoAdapter);
            }
        });
        return v;
    }


    /*public class ObtenerSQLiteCatalogo extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... arg0) {
            ArrayList<CatalogoSQLiteEntity> listaCatalogoSQLiteEntity=new ArrayList<>();
            try {
                CatalogoSQLiteDao catalogoSQLiteDao=new CatalogoSQLiteDao(getContext());
                listaCatalogoSQLiteEntity=catalogoSQLiteDao.ObtenerCatalogo();

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return listaCatalogoSQLiteEntity;
        }

        protected void onPostExecute(Object result) {
            ArrayList<CatalogoSQLiteEntity> Lista = (ArrayList<CatalogoSQLiteEntity>) result;
            if(Lista.isEmpty())
            {

            }
            else
            {
                listaCatalogoAdapter = new ListaCatalogoAdapter(getActivity(), ListaCatalogoDao.getInstance().getLeads(Lista));
                listacatalogo.setAdapter(listaCatalogoAdapter);
            }
        }
    }*/

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String Dato,Object Lista);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ListenerBackPress.setCurrentFragment("catalogoView");
        ListenerBackPress.setTemporaIdentityFragment("onAttach");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()+ " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        ListenerBackPress.setCurrentFragment("onDetach");
        mListener = null;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_catalogos, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search4);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Buscar Catalogo");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        listaCatalogoAdapter.filter(text);
        return true;
    }


}