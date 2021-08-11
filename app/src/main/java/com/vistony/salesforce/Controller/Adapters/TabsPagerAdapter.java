package com.vistony.salesforce.Controller.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.vistony.salesforce.View.RutaVendedorNoRutaView;
import com.vistony.salesforce.View.RutaVendedorRutaView;

public class TabsPagerAdapter extends FragmentPagerAdapter {

        int _numerodetabs;
        public TabsPagerAdapter(FragmentManager fm,int  numerodetabs) {
            super(fm);
            this._numerodetabs=numerodetabs;
        }

        @Override
        public Fragment getItem(int index) {

            switch (index) {
                case 0:
                    // Top Rated fragment activity
                    return new RutaVendedorRutaView();
                case 1:
                    // Games fragment activity
                    return new RutaVendedorNoRutaView();
            }

            return null;
        }

        @Override
        public int getCount() {
            // get item count - equal to number of tabs
            return _numerodetabs;
        }

}

