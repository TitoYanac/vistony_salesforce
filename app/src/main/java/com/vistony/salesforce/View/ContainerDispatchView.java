package com.vistony.salesforce.View;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.vistony.salesforce.Controller.Adapters.PageAdapter;
import com.vistony.salesforce.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContainerDispatchView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContainerDispatchView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    private ViewPager viewPager;
    private Toolbar tollbartab;
    private TabLayout tabLayout;
    private PageAdapter pageAdapter;
    public ContainerDispatchView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContainnerDispatchView.
     */
    // TODO: Rename and change types and number of parameters
    public static ContainerDispatchView newInstance(String param1, String param2) {
        ContainerDispatchView fragment = new ContainerDispatchView();
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
        v= inflater.inflate(R.layout.fragment_containner_dispatch_view, container, false);
        tollbartab=v.findViewById(R.id.tollbartab);
        viewPager=v.findViewById(R.id.viewpager);
        tabLayout=v.findViewById(R.id.tablayout);
        pageAdapter= new PageAdapter(getChildFragmentManager());
        pageAdapter.addFRagment(new ContainerDispatchSheetView(),"Despacho");
        pageAdapter.addFRagment(new GoogleMaps() ,"Mapa");

        //tabLayout.getTabAt(0).getCustomView()
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_delete_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_delete_black_24dp));
        viewPager.setAdapter(pageAdapter);
        //viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //pageAdapter.addFRagment(new ContainerDispatchSheetView(),"Despacho");
        //pageAdapter.addFRagment(new RutaVendedorNoRutaView(),"Mapa");
        //viewPager.setAdapter(pageAdapter);
        return v;
    }
}