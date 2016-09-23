package com.aaron.yqb.fragment.main;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aaron.myviews.model.newmodel.information.Article;
import com.aaron.yqb.R;
import com.aaron.yqb.activity.MainActivity;
import com.aaron.yqb.fragment.BaseFragment;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

public class InformationFragment extends BaseFragment  {

    private MainActivity mActivity;
    private PullToRefreshListView mPullToRefreshListView;
    private ImageView mRefreshButton;

    private int mPageNo;
    private static final int PAGE_SIZE = 5;
    private List<Article> mArticleList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            mActivity = (MainActivity) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

}
