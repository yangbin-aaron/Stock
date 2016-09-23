package com.aaron.yqb.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaron.myviews.view.TitleBar;
import com.aaron.yqb.R;
import com.aaron.yqb.activity.MainActivity;
import com.aaron.yqb.fragment.BaseFragment;


public class AccountFragment extends BaseFragment {

    private static int ACCOUNT_INDEX = MainActivity.TAB_TYPE.TAB_ACCOUNT;

    private TitleBar mTitleBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
//            activity.addMainPageChangeListener(ACCOUNT_INDEX, this);
        }
    }

//    @Override
//    public void pageChange(int index) {
//        if (index == ACCOUNT_INDEX) {
//            updateUserInfo();
//            requestUserFinanceData();
//            queryAvailableCouponCount();
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
