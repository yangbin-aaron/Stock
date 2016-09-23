/**
 * 引导页适配器
 * 
 * @Title: ViewPagerAdapter.java
 * @Package com.luckin.magnifier.adapter
 * @Description: TODO
 * @ClassName: ViewPagerAdapter
 * 
 * @author 于泽坤
 * @date 2015-7-6 下午5:18:56
 */

package com.aaron.myviews.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class GuideFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<Fragment>();

    public GuideFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

}
