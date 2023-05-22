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

import com.vistony.salesforce.Controller.Adapters.ListHistoricPromotionAdapter;
import com.vistony.salesforce.Controller.Adapters.ListaConsultaStockAdapter;
import com.vistony.salesforce.Controller.Utilitario.HistoricPromotionPDF;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Controller.Utilitario.ResumenDiarioPDF;
import com.vistony.salesforce.Dao.Adapters.ListaConsultaStockDao;
import com.vistony.salesforce.Dao.SQLite.ListaPrecioDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.PromocionCabeceraSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaConsultaStockEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricPromotionView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricPromotionView extends Fragment  implements SearchView.OnQueryTextListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    public static OnFragmentInteractionListener mListener;
    private SearchView mSearchView;
    ListHistoricPromotionAdapter listHistoricPromotionAdapter;
    ListView lv_historicpromotion;
    private ProgressDialog pd;
    MenuItem generate_pdf;

    public HistoricPromotionView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoricPromotionView.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricPromotionView newInstance(String param1, String param2) {
        HistoricPromotionView fragment = new HistoricPromotionView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static HistoricPromotionView newInstanceEnviarPromocion(Object objeto) {
        HistoricPromotionView fragment = new HistoricPromotionView();
        Bundle args = new Bundle();
        String Fragment="HistoricPromotionView";
        String accion="listadopromocion";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,objeto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.promotions_vigents));
        SesionEntity.flagquerystock="Y";
        SesionEntity.flagquerystockPromotion="Y";
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
        v= inflater.inflate(R.layout.fragment_historic_promotion_view, container, false);
        lv_historicpromotion = v.findViewById(R.id.lv_historicpromotion);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        cargarConsultaStock();

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
            throw new RuntimeException(context.toString()+ " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_historic_promotion, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        mSearchView = (SearchView) searchItem.getActionView();
        generate_pdf = menu.findItem(R.id.generate_pdf);
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint(getActivity().getResources().getString(R.string.find)+" "+getActivity().getResources().getString(R.string.article));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(listHistoricPromotionAdapter!=null)
        {
            listHistoricPromotionAdapter.filter(newText);
        }
        return true;
    }
    private void cargarConsultaStock()
    {
        pd = new ProgressDialog(getActivity());
        pd = ProgressDialog.show(getActivity(), getActivity().getResources().getString(R.string.please_wait), getActivity().getResources().getString(R.string.mse_not_data_available_for_date), true, false);
        ArrayList<ListaConsultaStockEntity> ListaConsultaStockEntity=new ArrayList<>();
        ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao=new ListaPrecioDetalleSQLiteDao(getContext());
        ListaConsultaStockEntity=listaPrecioDetalleSQLiteDao.getQueryStockPromotion(getContext());

        if(ListaConsultaStockEntity==null || ListaConsultaStockEntity.size()<0){
            Toast.makeText(getContext(),getContext().getResources().getString(R.string.msm_data_available), Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().popBackStack();
        }else{
            listHistoricPromotionAdapter = new ListHistoricPromotionAdapter(getActivity(), ListaConsultaStockDao.getInstance().getLeads(ListaConsultaStockEntity));
            lv_historicpromotion.setAdapter(listHistoricPromotionAdapter);
        }
        pd.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.generate_pdf:
                pd = new ProgressDialog(getActivity());
                pd = ProgressDialog.show(getActivity(), getActivity().getResources().getString(R.string.please_wait)    , getActivity().getResources().getString(R.string.querying_dates), true, false);
                PromocionCabeceraSQLiteDao promocionCabeceraSQLiteDao=new PromocionCabeceraSQLiteDao(getContext());

                HistoricPromotionPDF historicPromotionPDF=new HistoricPromotionPDF();
                historicPromotionPDF.generarPdf(getContext(),promocionCabeceraSQLiteDao.getListPromotionVigent());
                pd.dismiss();
                return true;
            default:
                break;
        }
        return false;
    }
}