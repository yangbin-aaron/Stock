package com.aaron.myviews.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页 Fragments
 */
public class MainPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private Map<Integer, String> mFragmentTags;
    private FragmentManager mFragmentManager;
    private List<Fragment> mFragments;

    public MainPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragmentManager = fm;
        mFragments = fragments;
        mFragmentTags = new HashMap<Integer, String>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
//        switch (position) {
//            case 0: return new HallFragment();
//            case 1: return new InformationFragment();
//            case 2: return new FoundFragment();
//            case 3: return new AccountFragment();
//        }
//        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment) {
            // record the fragment tag here.
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            mFragmentTags.put(position, tag);
        }
        return obj;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public Fragment getFragment(int position) {
        String tag = mFragmentTags.get(position);
        if (tag == null)
            return null;
        return mFragmentManager.findFragmentByTag(tag);
    }
}
