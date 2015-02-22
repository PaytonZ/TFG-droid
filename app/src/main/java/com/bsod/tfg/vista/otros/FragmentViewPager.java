package com.bsod.tfg.vista.otros;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsod.tfg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentViewPager extends Fragment {


    public FragmentViewPager() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager, container, false);
    }


}
