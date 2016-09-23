package com.aaron.myviews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import com.aaron.myviews.R;

public class AdsBanner extends FrameLayout {
    private static final float ASPECTRATIO = 0.7446f; //广告图片宽高比

    private MyGallery mAdsGallery;
    private FlowIndicator mFlowIndicator;

    public AdsBanner(Context context) {
        super(context);
        init(context);
    }

    public AdsBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_ads_banner, this, true);

        mAdsGallery = (MyGallery) findViewById(R.id.gallery_ads);
        mFlowIndicator = (FlowIndicator) findViewById(R.id.flow_indicator);

        mAdsGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mFlowIndicator != null) {
                    mFlowIndicator.setSeletion(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        if (mAdsGallery != null) {
            mAdsGallery.setOnItemClickListener(onItemClickListener);
        }
    }

    public void setAdsAdapter(ArrayAdapter adsAdapter) {
        if (mAdsGallery != null && mFlowIndicator != null) {
            mAdsGallery.setAdapter(adsAdapter);
            mFlowIndicator.setCount(adsAdapter.getCount());
        }
    }

    public void next() {
        if (mAdsGallery != null && mAdsGallery.getCount() != 0) {
            int position = mAdsGallery.getSelectedItemPosition();
            position = (position + 1) % mAdsGallery.getCount();
            mAdsGallery.setSelection(position);
        }
    }

//    public static class AdsAdapter extends ArrayAdapter<NewsNoticeModel.New> {
//        private boolean hasFillet = false;
//
//        public AdsAdapter(Context context, boolean hasFillet) {
//            super(context, 0);
//            this.hasFillet = hasFillet;
//        }
//
//        public void setNewList(List<NewsNoticeModel.New> newList) {
//            for (int i = 0; i < newList.size(); i++) {
//                add(newList.get(i));
//            }
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ImageView imageView = (ImageView) convertView;
//            if (imageView == null) {
//                imageView = new ImageView(getContext());
//                imageView.setLayoutParams(new Gallery.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.MATCH_PARENT));
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setAdjustViewBounds(false);
//                imageView.setBackgroundResource(R.color.transparent);
//            }
//            NewsNoticeModel.New ad = getItem(position);
//            String imageUrl = ad.getFullMiddleBanner() ;
//            if (!TextUtils.isEmpty(imageUrl)) {
//                if (hasFillet) {
//                    Picasso.with(getContext()).load(imageUrl)
//                            .placeholder(R.drawable.ads_placeholder)
//                            .transform(new RoundedCornersTransformation((int) DisplayUtil.convertDp2Px(mContext,c5), 0))
//                            .into(imageView);
//                } else {
//                    Picasso.with(getContext()).load(imageUrl).into(imageView);
//                }
//            }
//            return imageView;
//        }
//    }
}
