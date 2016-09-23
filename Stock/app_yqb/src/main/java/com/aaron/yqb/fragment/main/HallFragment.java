package com.aaron.yqb.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaron.myviews.view.TitleBar;
import com.aaron.yqb.R;
import com.aaron.yqb.fragment.BaseFragment;

public class HallFragment extends BaseFragment  {

    private TitleBar mTitleBar;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hall, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    

}
