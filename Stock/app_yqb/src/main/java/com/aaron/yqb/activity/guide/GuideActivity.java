/**
 * 引导页
 *
 * @Title: GuideActivity.java
 * @Package com.luckin.magnifier.activity.guide
 * @Description: TODO 不要继承BaseActivity
 * @ClassName: GuideActivity
 * @author 于泽坤
 * @date 2015-7-6 下午5:15:30
 */

package com.aaron.yqb.activity.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aaron.myviews.AppPrefs;
import com.aaron.myviews.adapters.GuideFragmentPagerAdapter;
import com.aaron.yqb.R;
import com.aaron.yqb.activity.MainActivity;
import com.aaron.yqb.fragment.guide.GuideFragment;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import java.util.ArrayList;

public class GuideActivity extends FragmentActivity implements OnPageChangeListener, View.OnClickListener {

    private ViewPager mViewPage;
    private GuideFragmentPagerAdapter mViewPageAdapter;

    private ArrayList<Fragment> mFragments;
    private GuideFragment mGuideOneFragment;
    private GuideFragment mGuideTwoFragment;
    private GuideFragment mGuideThreeFragment;

    // 底部小点图片
    private ImageView[] mBottomPoints;

    // 记录当前选中位置
    private int mCurrentTabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        PushAgent.getInstance(this).onAppStart();
        initViews();
        // 初始化底部小点
        initDots();

        findViewById(R.id.btn_guide).setOnClickListener(this);
    }

    private void initViews() {
        mGuideOneFragment = new GuideFragment();
        mGuideTwoFragment = new GuideFragment();
        mGuideThreeFragment = new GuideFragment();

        mGuideOneFragment.updateImage(R.drawable.guide_one_img,false);
        mGuideTwoFragment.updateImage(R.drawable.guide_two_img,true);
        mGuideThreeFragment.updateImage(R.drawable.guide_three_img,false);

        mGuideOneFragment.updateSpeedText(R.string.guide_one_speed, R.string.guide_speed_one);
        mGuideTwoFragment.updateSpeedText(R.string.guide_two_speed, R.string.guide_speed_two);
        mGuideThreeFragment.updateSpeedText(R.string.guide_three_speed, R.string.guide_speed_three);

        mFragments = new ArrayList<Fragment>();
        mFragments.add(mGuideOneFragment);
        mFragments.add(mGuideTwoFragment);
        mFragments.add(mGuideThreeFragment);

        // 初始化Adapter
        mViewPage = (ViewPager) findViewById(R.id.viewpager);
        mViewPageAdapter = new GuideFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPage.setAdapter(mViewPageAdapter);
        // 绑定回调
        mViewPage.setOnPageChangeListener(this);
        mViewPage.setOffscreenPageLimit(3);

        mViewPage.setCurrentItem(mCurrentTabIndex);
        mCurrentTabIndex = 0;
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.guide_bottom_ll);
        mBottomPoints = new ImageView[mFragments.size()];

        // 循环取得小点图片
        for (int i = 0; i < mFragments.size(); i++) {
            mBottomPoints[i] = (ImageView) ll.getChildAt(i);
            mBottomPoints[i].setEnabled(true);// 都设为灰色
        }

        mCurrentTabIndex = 0;
        mBottomPoints[mCurrentTabIndex].setEnabled(false);// 设置为红色，即选中状态
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > mFragments.size() - 1 || mCurrentTabIndex == position) {
            return;
        }

        mBottomPoints[position].setEnabled(false);
        mBottomPoints[mCurrentTabIndex].setEnabled(true);

        mCurrentTabIndex = position;
    }


    // 当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int position) {
        // 设置底部小点选中状态
        setCurrentDot(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_guide:
                AppPrefs.getInstance(this).setFirstStart(false);
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                GuideActivity.this.startActivity(intent);
                GuideActivity.this.finish();
                break;
        }
    }
}
