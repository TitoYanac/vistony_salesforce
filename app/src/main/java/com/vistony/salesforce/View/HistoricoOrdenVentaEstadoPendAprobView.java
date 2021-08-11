package com.vistony.salesforce.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vistony.salesforce.Controller.Adapters.ListaHistoricoOrdenVentaAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaHistoricoOrdenVentaDao;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoOrdenVentaEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricoOrdenVentaEstadoPendAprobView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricoOrdenVentaEstadoPendAprobView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    ListView lv_historico_orden_venta_estado_pend_aprob;
    ListaHistoricoOrdenVentaAdapter listaHistoricoOrdenVentaAdapter;
    public HistoricoOrdenVentaEstadoPendAprobView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoticoOrdenVentaPendAprob.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricoOrdenVentaEstadoPendAprobView newInstance(String param1, String param2) {
        HistoricoOrdenVentaEstadoPendAprobView fragment = new HistoricoOrdenVentaEstadoPendAprobView();
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
        v= inflater.inflate(R.layout.fragment_histotico_orden_venta_pend_aprob, container, false);
        lv_historico_orden_venta_estado_pend_aprob=v.findViewById(R.id.lv_historico_orden_venta_pend_aprob);
        CargarHistoricoOrdenVentaEstadoPendAprob();
        return v;
    }

    public void CargarHistoricoOrdenVentaEstadoPendAprob()
    {
        ArrayList<ListaHistoricoOrdenVentaEntity> Listado=new ArrayList<>();
        ListaHistoricoOrdenVentaEntity listaHistoricoOrdenVentaEntity;
//        Log.e("REOS","HistoricoOrdenVentaEstadoPendRevView:HistoricoOrdenVentaEstadoView.Lista.size()"+HistoricoOrdenVentaEstadoView.Lista.size());
        for(int i=0;i<HistoricoOrdenVentaEstadoView.Lista.size();i++)
        {
            if(HistoricoOrdenVentaEstadoView.Lista.get(i).getEstadoaprobacion().equals("Pendiente Aprobacion"))
            {
                listaHistoricoOrdenVentaEntity = new ListaHistoricoOrdenVentaEntity();
                listaHistoricoOrdenVentaEntity.ordenventa_erp_id = HistoricoOrdenVentaEstadoView.Lista.get(i).getOrdenventa_erp_id();
                listaHistoricoOrdenVentaEntity.cliente_id = HistoricoOrdenVentaEstadoView.Lista.get(i).getCliente_id();
                listaHistoricoOrdenVentaEntity.rucdni = HistoricoOrdenVentaEstadoView.Lista.get(i).getRucdni();
                listaHistoricoOrdenVentaEntity.nombrecliente = HistoricoOrdenVentaEstadoView.Lista.get(i).getNombrecliente();
                listaHistoricoOrdenVentaEntity.montototalorden = HistoricoOrdenVentaEstadoView.Lista.get(i).getMontototalorden();
                listaHistoricoOrdenVentaEntity.estadoaprobacion = HistoricoOrdenVentaEstadoView.Lista.get(i).getEstadoaprobacion();
                listaHistoricoOrdenVentaEntity.comentarioaprobacion = HistoricoOrdenVentaEstadoView.Lista.get(i).getComentarioaprobacion();
                listaHistoricoOrdenVentaEntity.ordenventa_id = HistoricoOrdenVentaEstadoView.Lista.get(i).getOrdenventa_erp_id();
                listaHistoricoOrdenVentaEntity.recepcionERPOV = HistoricoOrdenVentaEstadoView.Lista.get(i).isRecepcionERPOV();
                listaHistoricoOrdenVentaEntity.comentariows = HistoricoOrdenVentaEstadoView.Lista.get(i).getComentariows();
                listaHistoricoOrdenVentaEntity.envioERPOV = HistoricoOrdenVentaEstadoView.Lista.get(i).isEnvioERPOV();
                Listado.add(listaHistoricoOrdenVentaEntity);
            }
        }
        Log.e("REOS","HistoricoOrdenVentaEstadoPendRevView:Listado.size()"+Listado.size());
        listaHistoricoOrdenVentaAdapter = new ListaHistoricoOrdenVentaAdapter(getActivity(), ListaHistoricoOrdenVentaDao.getInstance().getLeads(Listado));
        lv_historico_orden_venta_estado_pend_aprob.setAdapter(listaHistoricoOrdenVentaAdapter);
    }
}