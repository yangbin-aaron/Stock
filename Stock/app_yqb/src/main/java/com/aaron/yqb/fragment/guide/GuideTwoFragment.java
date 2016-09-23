
/** 
 * @Title: GuideTwoFragment.java
 * @Package com.luckin.magnifier.activity.guide 
 * @Description: TODO 
 * @ClassName: GuideTwoFragment
 *
 * @author 于泽坤 
 * @date 2015-7-7 上午10:21:43 
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

public class GuideTwoFragment extends Fragment {
    private ImageView mGuideWayImg;
    private LinearLayout mGuideTextll;
    private LinearLayout mGuideButtonll;
    private LinearLayout mGuideCowll;
    
    private TranslateAnimation mCowTAnimation;
    private TranslateAnimation mUpAnimation;
    private TranslateAnimation mDownAnimation;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide_two, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        
//        mguideCowll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            
//            @Override
//            public void onGlobalLayout() {
//                initCowAnimation();
//            }
//        });
    }

    private void initViews() {
        mGuideWayImg = (ImageView) getView().findViewById(R.id.guide_two_way_img);
        mGuideButtonll = (LinearLayout) getView().findViewById(R.id.guide_two_ll);
        mGuideTextll = (LinearLayout) getView().findViewById(R.id.guide_two_text_ll);
        mGuideCowll = (LinearLayout) getView().findViewById(R.id.guide_two_cow_ll);
    }

    /**
     * @Title: initPlanAnimation
     * @Description: 顶部动画
     * @throws
     */

    private void initWayAnimation() {
        mGuideWayImg.setVisibility(View.VISIBLE);
        mUpAnimation = new TranslateAnimation(0f, 0f,0f, -mGuideWayImg.getHeight());
        mUpAnimation.setDuration(1000);
        mUpAnimation.setFillAfter(true);
        mUpAnimation.setInterpolator(new AccelerateInterpolator());
        mGuideWayImg.startAnimation(mUpAnimation);
    }

    /**
     * @Title: initTextllAnimation
     * @Description: 底部文字动画
     * @throws
     */

    private void initTextllAnimation() {
        mGuideButtonll.setVisibility(View.VISIBLE);
        mDownAnimation = new TranslateAnimation(0f, 0f,-mGuideTextll.getHeight(), 0f);
        mDownAnimation.setDuration(1000);
        mDownAnimation.setFillAfter(true);
        mDownAnimation.setInterpolator(new LinearInterpolator());
        mGuideButtonll.startAnimation(mDownAnimation);
    }

    /**
     * @Title: initCowAnimation
     * @Description: 顶部超人（牛）动画
     * @throws
     */

    public void initCowAnimation() {
        mCowTAnimation = new TranslateAnimation(0f, 0f, mGuideCowll.getHeight(), -DisplayUtil.getHeight(getActivity())/7*3);
        mCowTAnimation.setDuration(500);
        mCowTAnimation.setFillAfter(true);
        mCowTAnimation.setInterpolator(new AccelerateInterpolator());
        mGuideCowll.startAnimation(mCowTAnimation);
        mCowTAnimation.setAnimationListener(new AnimationListener() {
            
            @Override
            public void onAnimationStart(Animation arg0) {
                mGuideCowll.setVisibility(View.VISIBLE);
            }
            
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            
            @Override
            public void onAnimationEnd(Animation arg0) {
                initWayAnimation();
                initTextllAnimation();
            }
        });
    }
}

