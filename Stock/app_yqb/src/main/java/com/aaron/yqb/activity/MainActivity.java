package com.aaron.yqb.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaron.myviews.adapters.MainPagerAdapter;
import com.aaron.myviews.dialog.SimpleAlertDialog;
import com.aaron.myviews.model.gson.NewsNoticeModel;
import com.aaron.myviews.view.AdsBanner;
import com.aaron.network.IntentConfig;
import com.aaron.yqb.R;
import com.aaron.yqb.fragment.main.AccountFragment;
import com.aaron.yqb.fragment.main.FoundFragment;
import com.aaron.yqb.fragment.main.HallFragment;
import com.aaron.yqb.fragment.main.InformationFragment;
import com.aaron.yqb.model.ServerTime;
import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity {


    /**
     * 首页页面改变监听
     */
    public interface OnMainPageChangeListener {
        void pageChange(int index);
    }

    public static void enter(Context context, int tabIndex) {
        Intent intent = new Intent();
        intent.putExtra(IntentConfig.Keys.POSITION, tabIndex);
        context.startActivity(intent);
    }

    private Map<Integer, OnMainPageChangeListener> mainPageChangeListeners;

    private AdsBanner mAdsBanner;

    private TextView[] mTabs;
    private ViewPager mViewPager;

    private LinearLayout mPopViewLayout;
    private int mCurrentTabIndex = 0;
    private PushAgent mPushAgent;

    private boolean mShowHolidayGamePopup;

    public static final class TAB_TYPE {
        public static final int TAB_HALL = 0;
        public static final int TAB_INFORMATION = TAB_HALL + 1;
        public static final int TAB_DISCOVERY = TAB_INFORMATION + 1;
        public static final int TAB_ACCOUNT = TAB_DISCOVERY + 1;
    }

    private BroadcastReceiver portraitReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(IntentConfig.Actions.ACTION_UPDATE_PORTRAIT)) {
//                if(mViewPager.getAdapter() != null && mViewPager.getAdapter() instanceof MainPagerAdapter){
//                    Fragment fragment =
//                            ((MainPagerAdapter) mViewPager.getAdapter()).getFragment(TAB_TYPE.TAB_ACCOUNT);
//                    if(fragment != null && fragment instanceof AccountFragment){
//                        ((AccountFragment) fragment).updateHeadPortrait();
//                    }
//                }
            }
        }
    };

    /**
     * 添加首页页面改变监听
     *
     * @param index    当前Fragment的index
     * @param listener
     */
    public void addMainPageChangeListener(int index, OnMainPageChangeListener listener) {
        if (mainPageChangeListeners == null) {
            mainPageChangeListeners = new HashMap<>();
        }
        mainPageChangeListeners.put(index, listener);
    }

    private void registerReceiver() {
        registerReceiver(portraitReceiver, new IntentFilter(IntentConfig.Actions.ACTION_UPDATE_PORTRAIT));
    }

    private void unRegisterReceiver() {
        unregisterReceiver(portraitReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
        initUmengInfo();
        ServerTime.scheduleSynchronize();
//        ViewDisplay.fetchViewDisplay();
//        OnlineEnvironment.requestEnvironments();
        registerReceiver();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra(IntentConfig.Keys.REGISTER_SUCCESS)) {
            showCompleteInfoDialog(intent.getStringExtra(IntentConfig.Keys.REGISTER_SUCCESS));
        } else if (intent.hasExtra(IntentConfig.Keys.POSITION)) {
            if (mViewPager != null) {
                int position = intent.getIntExtra(IntentConfig.Keys.POSITION, 0);
                mViewPager.setCurrentItem(position, false);
            }
        }
    }

    private void initUmengInfo() {
        mPushAgent = PushAgent.getInstance(MainActivity.this);
        mPushAgent.enable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        UserInfoManager.getInstance().reset();
//        ServerTime.stop();
        unRegisterReceiver();
    }

    public int getCurFragmentIndex() {
        if (mViewPager != null) {
            return mViewPager.getCurrentItem();
        }
        return -1;
    }

    /**
     * 初始化恒生Session
     */
    private void initH5Session() {
//        H5DataCenter h5DataCenter = H5DataCenter.getInstance();
//        h5DataCenter.initSession(this);
    }

    /**
     * 销毁恒生Session
     */
    private void destroyH5Session() {
//        H5DataCenter h5DataCenter = H5DataCenter.getInstance();
//        h5DataCenter.destroySession();
    }

    private void initViews() {
        mTabs = new TextView[4];
        mTabs[TAB_TYPE.TAB_HALL] = (TextView) findViewById(R.id.tv_hall);
        mTabs[TAB_TYPE.TAB_INFORMATION] = (TextView) findViewById(R.id.tv_information);
        mTabs[TAB_TYPE.TAB_DISCOVERY] = (TextView) findViewById(R.id.tv_discovery);
        mTabs[TAB_TYPE.TAB_ACCOUNT] = (TextView) findViewById(R.id.tv_account);
        mTabs[TAB_TYPE.TAB_HALL].setSelected(true);

        mViewPager = (ViewPager) findViewById(R.id.atv_main_ViewPager_pager);
        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), createFragments()));
        mViewPager.setOnPageChangeListener(mPageChangeListener);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(mCurrentTabIndex);

        mCurrentTabIndex = TAB_TYPE.TAB_HALL;

        mPopViewLayout = (LinearLayout) findViewById(R.id.layout_pop_hall);
        mAdsBanner = (AdsBanner) mPopViewLayout.findViewById(R.id.advertisement_banner);
        Gallery gallery = (Gallery) mAdsBanner.findViewById(R.id.gallery_ads);
        gallery.setSpacing(0);

        mAdsBanner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsNoticeModel.New newNotice = (NewsNoticeModel.New) parent.getItemAtPosition(position);
                if (newNotice != null) {
//                    WebViewActivity.openTaskCenter(MainActivity.this, newNotice.getFullUrl(), newNotice.getTitle());
                }
            }
        });
    }

    private List<Fragment> createFragments() {
        List<Fragment> fragments = new ArrayList<>();
        AccountFragment accountFragment = new AccountFragment();
        InformationFragment informationFragment = new InformationFragment();
        FoundFragment foundFragment = new FoundFragment();
        HallFragment hallFragment = new HallFragment();
        fragments.add(hallFragment);
        fragments.add(informationFragment);
        fragments.add(foundFragment);
        fragments.add(accountFragment);
        return fragments;
    }

    public void updatePopupNewsNotice(NewsNoticeModel newsNoticeModel) {
        if (newsNoticeModel != null) {
            List<NewsNoticeModel.New> newList = newsNoticeModel.getNewsNoticeList();
//            if (ListUtil.isNotEmpty(newList)) {
//                AdsBanner.AdsAdapter adsAdapter = new AdsBanner.AdsAdapter(MainActivity.this, true);
//                adsAdapter.setNewList(newList);
//                mAdsBanner.setAdsAdapter(adsAdapter);
//                adsAdapter.notifyDataSetChanged();
//            }
        }
    }

    private void initListeners() {
//        mPopViewLayout.findViewById(R.id.btn_holding_advertising_close).setOnClickListener(this);
//        mPopViewLayout.findViewById(R.id.btn_take_part_in).setOnClickListener(this);
    }

    public void showHolidayGamePopupWindows() {
        if (mCurrentTabIndex == TAB_TYPE.TAB_HALL && mPopViewLayout != null) {
            if (mPopViewLayout.getVisibility() == View.VISIBLE) {
                if (mPopViewLayout.findViewById(R.id.holiday_game_popup).getVisibility() == View.GONE) {
                    mShowHolidayGamePopup = true;
                }
            } else if (mPopViewLayout.getVisibility() == View.GONE) {
                mPopViewLayout.setVisibility(View.VISIBLE);
                mPopViewLayout.findViewById(R.id.advertisement_banner).setVisibility(View.GONE);
                mPopViewLayout.findViewById(R.id.holiday_game_popup).setVisibility(View.VISIBLE);
                showScaleLineWithAnim();
            }
        }
    }

    public void showPopWindows() {
        if (mPopViewLayout.getVisibility() == View.GONE && mCurrentTabIndex == TAB_TYPE.TAB_HALL) {
            mPopViewLayout.setVisibility(View.VISIBLE);
            mPopViewLayout.findViewById(R.id.advertisement_banner).setVisibility(View.VISIBLE);
            mPopViewLayout.findViewById(R.id.holiday_game_popup).setVisibility(View.GONE);
            showScaleLineWithAnim();
        }
    }

    private void showScaleLineWithAnim() {
        ImageView scaleLine = (ImageView) mPopViewLayout.findViewById(R.id.iv_line_advertising);
//        Animation scaleLineAnim = AnimationUtils.loadAnimation(this, R.anim.line_scale_top_bottom);
//        scaleLineAnim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                showPopupContentWithAnim();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//        scaleLine.startAnimation(scaleLineAnim);
    }

    private void showPopupContentWithAnim() {
        //Animation imageAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.image_anim_transparent_blurry);
        View view = mPopViewLayout.findViewById(R.id.popup_content);
        //view.startAnimation(imageAnim);
        view.setVisibility(View.VISIBLE);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            updateTabs(position);
            if (mainPageChangeListeners != null
                    && mainPageChangeListeners.get(position) != null) {
                mainPageChangeListeners.get(position).pageChange(position);
            }
        }
    };

    public void onTabClick(View v) {
        int index = -1;
        switch (v.getId()) {
            case R.id.tv_hall:
                index = TAB_TYPE.TAB_HALL;
                break;
            case R.id.tv_information:
                index = TAB_TYPE.TAB_INFORMATION;
                break;
            case R.id.tv_discovery:
                index = TAB_TYPE.TAB_DISCOVERY;
                break;
            case R.id.tv_account:
                index = TAB_TYPE.TAB_ACCOUNT;
                break;
        }
        if (index == mCurrentTabIndex || index == -1) return;
        mViewPager.setCurrentItem(index, false);
    }

    public void updateTabs(int index) {
        if (index == mCurrentTabIndex) return;

        // 把当前tab设为选中状态
        mTabs[mCurrentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        mCurrentTabIndex = index;
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true); // override back to home
        new SimpleAlertDialog.Builder(this)
                .setCancelable(true)
                .setMessage(R.string.exit_confirm)
                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    protected void processDialogTipEvent(Bundle bundle) {
//        super.processDialogTipEvent(bundle);
//        int strId = bundle.getInt(ViewConfig.EXTRAS_KEY_STR.TIP_RESULT_STRID_STR);
//        //完善资料
//        if (R.string.complete_info == strId) {
//            ProfileActivity.enter(this);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                //忘记密码/注册成功后回到账户页面更新数据
                mViewPager.setCurrentItem(TAB_TYPE.TAB_ACCOUNT);
                break;
//            case ActivityConfig.ResultCode.RESULT_CODE_FINISH_ACTIVITY:
//                if (data != null) {
//                    int code = data.getIntExtra(ViewConfig.EXTRAS_KEY_STR.ACTIVITY, 0);
//                    switch (code) {
//                        case ActivityConfig.ResultCode.RESULT_CODE_MAIN_ACTIVITY_FINANCING:
////                            mViewPager.setCurrentItem(TAB_TYPE.TAB_BUY);
//                            break;
//                        case ActivityConfig.ResultCode.RESULT_CODE_MAIN_ACTIVITY_HOLDING:
//                            startActivityForResult(new Intent(this, StockHoldingActivity.class), 0);
//                            break;
//                        case ActivityConfig.ResultCode.RESULT_CODE_MAIN_ACTIVITY_ACCOUNT:
//                            mViewPager.setCurrentItem(TAB_TYPE.TAB_ACCOUNT);
//                            break;
//                    }
//                }
//                break;
//            case ActivityConfig.ResultCode.RESULT_CODE_RECHARGE://充值成功
//                if (needCompleteInfo()) {
//                    setDialogTipType(ViewConfig.TIP_LAYOUT_TYPE.TIP_LAYOUT_TYPE_DUO);
//                    setDialogTipText(R.string.complete_info_tip, R.string.negative, R.string.complete_info);
//                    mDialogTip.show();
//                } else {
//                    SimpleLogger.log_e(UserInfoManager.getInstance().getBindRealname(), UserInfoManager.getInstance()
//                            .getBankName());
//                }
//                break;
//            case ActivityConfig.ResultCode.RESULT_CODE_HALL:
//                //任务中心，领取红包失败或者未达到领取条件，回到大厅
//                mViewPager.setCurrentItem(TAB_TYPE.TAB_HALL);
//                break;
//            case ActivityConfig.ResultCode.RESULT_CODE_FOUND:
//                mViewPager.setCurrentItem(TAB_TYPE.TAB_DISCOVERY);
//                break;
//            case ActivityConfig.ResultCode.RESULT_CODE_ACCOUNT:
//                mViewPager.setCurrentItem(TAB_TYPE.TAB_ACCOUNT);
//                break;
        }
    }

    private void showCompleteInfoDialog(String message) {
//        new SimpleAlertDialog.Builder(this)
//                .setMessage(message)
//                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).create().show();
    }

    //是否需要完善资料
//    private boolean needCompleteInfo() {
//        return TextUtils.isEmpty(UserInfoManager.getInstance().getBindRealname())
//                || TextUtils.isEmpty(UserInfoManager.getInstance().getBankName());
//    }

    public void updateReplyView(int visible) {
        findViewById(R.id.iv_feedback_round).setVisibility(visible);
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_holding_advertising_close:
//                closePopupView();
//                break;
//            case R.id.btn_take_part_in:
//                HolidayGameActivity.enter(this);
//                break;
//        }
//    }

    private void closePopupView() {
        closePopupContentWithAnim();

        // 下面代码主要是对AppPref进行处理记住广告关闭
//        HallFragment hallFragment = (HallFragment) ((MainPagerAdapter) mViewPager.getAdapter())
//                .getFragment(TAB_TYPE.TAB_HALL);
//        if (hallFragment != null) {
//            hallFragment.closePopupAdsView();
//        }
    }

    private void closePopupContentWithAnim() {
//        Animation imageAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.image_scale_bottom_top);
//        final View view = mPopViewLayout.findViewById(R.id.popup_content);
//        imageAnim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                view.setVisibility(View.INVISIBLE);
//                closeScaleLineWithAnim();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//        view.startAnimation(imageAnim);
    }

    private void closeScaleLineWithAnim() {
//        Animation scaleLineAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.line_scale_bottom_top);
//        ImageView scaleLine = (ImageView) mPopViewLayout.findViewById(R.id.iv_line_advertising);
//        scaleLine.startAnimation(scaleLineAnim);
//        scaleLineAnim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                mPopViewLayout.setVisibility(View.GONE);
//
//                if (mShowHolidayGamePopup) {
//                    mPopViewLayout.setVisibility(View.VISIBLE);
//                    mPopViewLayout.findViewById(R.id.advertisement_banner).setVisibility(View.GONE);
//                    mPopViewLayout.findViewById(R.id.holiday_game_popup).setVisibility(View.VISIBLE);
//                    mShowHolidayGamePopup = false;
//
//                    showScaleLineWithAnim();
//                } else {
//                    mPopViewLayout.findViewById(R.id.advertisement_banner).setVisibility(View.VISIBLE);
//                    mPopViewLayout.findViewById(R.id.holiday_game_popup).setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
    }
}