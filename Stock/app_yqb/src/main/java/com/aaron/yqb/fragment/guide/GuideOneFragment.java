/**
 * @Title: GuideOneFragment.java
 * @Package com.luckin.magnifier.activity.guide
 * @Description: TODO
 * @ClassName: GuideOneFragment
 * @author 于泽坤
 * @date 2015-7-7 上午10:21:26
 */

package com.aaron.yqb.fragment.guide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aaron.myviews.utils.DisplayUtil;
import com.aaron.yqb.R;

/**
 * @Description: TODO
 */

public class GuideOneFragment extends Fragment {
    private ImageView mGuideWayImg;
    private LinearLayout mGuideTextll;
    private ImageView mGuidePlaneImg;
    private LinearLayout mGuideButtonll;

    private TranslateAnimation mLeftTAnimation;
    private TranslateAnimation mUpAnimation;
    private TranslateAnimation mDownAnimation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide_one, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();

        initWayAnimation();
    }

    private void initViews() {
        mGuideWayImg = (ImageView) getView().findViewById(R.id.guide_one_way_img);
        mGuideButtonll = (LinearLayout) getView().findViewById(R.id.guide_one_ll);
        mGuideTextll = (LinearLayout) getView().findViewById(R.id.guide_one_text_ll);
        mGuidePlaneImg = (ImageView) getView().findViewById(R.id.guide_one_plane_img);
    }

    /**
     * @Title: initPlanAnimation
     * @Description: 火箭动画
     * @throws
     */

    private void initPlanAnimation() {
        mGuidePlaneImg.setVisibility(View.VISIBLE);
        mUpAnimation = new TranslateAnimation(0f, 0f, mGuidePlaneImg.getHeight(), -DisplayUtil.getHeight(getActivity()) / 3);
        mUpAnimation.setDuration(1000);
        mUpAnimation.setFillAfter(true);
        mUpAnimation.setInterpolator(new AccelerateInterpolator());
        mGuidePlaneImg.startAnimation(mUpAnimation);
        mUpAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                initTextllAnimation();
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
        mDownAnimation.setDuration(500);
        mDownAnimation.setFillAfter(true);
        mDownAnimation.setInterpolator(new LinearInterpolator());
        mGuideButtonll.startAnimation(mDownAnimation);
    }

    /**
     * @Title: initWayAnimation
     * @Description: 顶部图片动画
     * @throws
     */

    private void initWayAnimation() {

        mLeftTAnimation = new TranslateAnimation(0f, DisplayUtil.getWidth(getActivity()), 0f, 0f);
        mLeftTAnimation.setDuration(1500);
        mLeftTAnimation.setFillAfter(true);
        mLeftTAnimation.setInterpolator(new AccelerateInterpolator());
        mGuideWayImg.setAnimation(mLeftTAnimation);
        mLeftTAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                initPlanAnimation();
            }
        });
    }
}
