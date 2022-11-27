package com.vistony.salesforce.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
/*
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.vistony.salesforce.Controller.Adapters.PageAdapter;
import com.vistony.salesforce.Controller.Adapters.SeccionesAdapter;
import com.vistony.salesforce.Controller.Utilitario.UtilidadesController;
import com.vistony.salesforce.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContenedorComisionesView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContenedorComisionesView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContenedorComisionesView extends Fragment {

    View v;
    private AppBarLayout appBar;
    private TabLayout pestanas;
    private ViewPager viewPager;
    private Toolbar tollbartab;
    private TabLayout tabLayout;
    private PageAdapter pageAdapter;
    public static Activity activity;
    public static Context context;
    private OnFragmentInteractionListener mListener;

    public ContenedorComisionesView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContenedorComisionesView.
     */
    // TODO: Rename and change types and number of parameters
    public static ContenedorComisionesView newInstance(String param1, String param2) {
        ContenedorComisionesView fragment = new ContenedorComisionesView();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity=getActivity();
        context=getContext();
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getActivity().getResources().getString(R.string.commissions));
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_contenedor_comisiones, container, false);
        tollbartab=v.findViewById(R.id.tollbartab);
        viewPager=v.findViewById(R.id.viewpager);
        tabLayout=v.findViewById(R.id.tablayout);
        pageAdapter= new PageAdapter(getChildFragmentManager());
        pageAdapter.addFRagment(new ComisionesView(),getActivity().getResources().getString(R.string.period_current));
        pageAdapter.addFRagment(new PronosticoComisionesView(),getActivity().getResources().getString(R.string.period_before));
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        ComisionesView.newInstance("");
        PronosticoComisionesView.newInstance("");

        return v;
    }

   /* private void llenarViewPager(ViewPager viewPager) {
        SeccionesAdapter adapter=new SeccionesAdapter(getFragmentManager());
        adapter.addFragment(new ComisionesView(),"Comisiones");
        adapter.addFragment(new PronosticoComisionesView(),"Pronostico");

        viewPager.setAdapter(adapter);
    }
    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*if(UtilidadesController.rotacion==0){
            appBar.removeView(pestanas);
        }*/

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
        void onFragmentInteraction(String tag,Object dato);
    }
}
