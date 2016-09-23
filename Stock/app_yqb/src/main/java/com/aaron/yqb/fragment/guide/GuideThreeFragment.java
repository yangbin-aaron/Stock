/**
 * @Title: GuideThreeFragment.java
 * @Package com.luckin.magnifier.activity.guide
 * @Description: TODO
 * @ClassName: GuideThreeFragment
 * @author 于泽坤
 * @date 2015-7-7 上午10:21:59
 */

package com.aaron.yqb.fragment.guide;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaron.myviews.utils.DisplayUtil;
import com.aaron.myviews.view.GuideCoinPointView;
import com.aaron.yqb.R;

/**
 * @Description: TODO
 */

public class GuideThreeFragment extends Fragment {
    private LinearLayout mGuideTextll;
    private LinearLayout mGuideButtonll;
    private RelativeLayout mGuideHandll;
    private RelativeLayout mGudieCoinRy;
    private LinearLayout mGuideCoinll;
    private TextView mGuideCoinTv;
    private GuideCoinPointView mGuideCoinPointView;

    private TranslateAnimation mDownAnimation;
    private RotateAnimation mRotateAnimation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide_three, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        mGuideButtonll = (LinearLayout) getView().findViewById(R.id.guide_three_ll);
        mGuideTextll = (LinearLayout) getView().findViewById(R.id.guide_three_text_ll);
        mGuideHandll = (RelativeLayout) getView().findViewById(R.id.guide_three_ly);
        mGuideCoinll = (LinearLayout) getView().findViewById(R.id.guide_three_coin_ll);
        mGuideCoinTv = (TextView) getView().findViewById(R.id.guide_three_tv);
        mGudieCoinRy = (RelativeLayout) getView().findViewById(R.id.guide_three_coin_ry);
    }

    /**
     *
     * @Title: initHandllAnimation
     * @Description: TODO 旋转动画
     * @throws
     */
    public void initHandllAnimation() {

        mRotateAnimation = new RotateAnimation(345f, 360f, Animation.RELATIVE_TO_SELF, 0.8f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(250);
        mRotateAnimation.setFillAfter(true);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mGuideHandll.startAnimation(mRotateAnimation);

        mRotateAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                Log.d("TAG", mGuideHandll.getHeight() + "");
                RelativeLayout.LayoutParams coinParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                coinParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                coinParams.topMargin = mGuideHandll.getHeight() / 4;
                Log.d("TAG", coinParams.topMargin + "");
                mGudieCoinRy.setLayoutParams(coinParams);
                mGudieCoinRy.setVisibility(View.VISIBLE);
                mGuideCoinPointView = new GuideCoinPointView(getActivity());
                mGuideCoinll.addView(mGuideCoinPointView);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                mGuideCoinTv.setVisibility(View.GONE);
                initTextllAnimation();
                mGuideCoinPointView.getHandler((int) DisplayUtil.convertSp2Px(getActivity(), 27), Color.WHITE, "85632")
                        .sendEmptyMessage(0);
            }
        });
    }

    /**
     * @Title: initTextllAnimation
     * @Description: 底部文字动画
     * @throws
     */

    private void initTextllAnimation() {
        mGuideButtonll.setVisibility(View.VISIBLE);
        mDownAnimation = new TranslateAnimation(0f, 0f, -mGuideTextll.getHeight(), 0f);
        mDownAnimation.setDuration(1000);
        mDownAnimation.setFillAfter(true);
        mDownAnimation.setInterpolator(new LinearInterpolator());
        mGuideButtonll.startAnimation(mDownAnimation);
    }
}
