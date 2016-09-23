package com.aaron.myviews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * @类描述：顶部悬停
 * @创建人：龙章煌
 * @创建时间：2015-05-10
 * @修改人：
 * @修改时间：
 * @修改备注：
 * @version
 */

public class MyScrollView extends ScrollView {

	View mTopView;
	View mFlowView;
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		
		super.onScrollChanged(l, t, oldl, oldt);
		
		try{
			int[] position = new int[2];
	        if(mTopView != null){
	        	
	        }
	        mTopView.getLocationOnScreen(position);

	        if(position[1]-mTopView.getHeight()<0) {
				mFlowView.setVisibility(View.VISIBLE);
			} else {
				mFlowView.setVisibility(View.GONE);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
        
	}
	
	public void listenerFlowViewScrollState(View topView, View flowView) {
		mTopView = topView;
		mFlowView = flowView;
	}
	
}
