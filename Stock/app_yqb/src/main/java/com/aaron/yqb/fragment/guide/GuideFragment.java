package com.aaron.yqb.fragment.guide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaron.myviews.utils.DisplayUtil;
import com.aaron.yqb.R;
import com.aaron.yqb.fragment.BaseFragment;

/**
 * Created by Administrator on 2015/10/9.
 */
public class GuideFragment extends BaseFragment {
    private TextView mGuideSpeedTv;
    private TextView mSpeedGuideTv;
    private ImageView mGuideIv;

    private boolean mMoveRight;
    private int mImageSrc;
    private int mGuideSpeed, mSpeedGuide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGuideIv = (ImageView) getView().findViewById(R.id.iv_guide);
        mGuideSpeedTv = (TextView) getView().findViewById(R.id.tv_guide_speed);
        mSpeedGuideTv = (TextView) getView().findViewById(R.id.tv_speed_guide);
        updateImage(mImageSrc, mMoveRight);
        updateSpeedText(mGuideSpeed, mSpeedGuide);
    }

    /**
     * @param guideSpeed 灰色字
     * @param speedGuide 红字
     */
    public void updateSpeedText(int guideSpeed, int speedGuide) {
        this.mGuideSpeed = guideSpeed;
        this.mSpeedGuide = speedGuide;
        if (mGuideSpeedTv != null && mSpeedGuideTv != null) {
            mGuideSpeedTv.setText(guideSpeed);
            mSpeedGuideTv.setText(speedGuide);
        }
    }

    public void updateImage(int imageSrc, boolean moveRight) {
        this.mImageSrc = imageSrc;
        this.mMoveRight = moveRight;
        if (mGuideIv != null) {
            if (moveRight) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mGuideIv.getLayoutParams());
                layoutParams.weight = 2.0f;
                layoutParams.setMargins((int) DisplayUtil.convertDp2Px(getActivity(), 8), 0, 0, 0);
                mGuideIv.setLayoutParams(layoutParams);
            }
            mGuideIv.setImageResource(imageSrc);
        }
    }
}
