
/**
 * @Title: FoundFragment.java
 * @Package com.luckin.magnifier.fragment
 * @Description: TODO
 * @ClassName: FoundFragment
 * @author 于泽坤
 * @date 2015-7-27 上午9:59:09
 */

package com.aaron.yqb.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaron.yqb.R;
import com.aaron.yqb.fragment.BaseFragment;

public class FoundFragment extends BaseFragment  {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_found, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      //  isPromote();
    }
}
