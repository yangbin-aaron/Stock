package com.aaron.myviews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;


/**
 * @类描述：自定义Gallery，兼容
 * @创建人：Eran
 * @创建时间：2015-05-01
 * @修改人：
 * @修改时间：
 * @修改备注：
 * @version
 */
public class MyGallery extends Gallery{

	public MyGallery(Context context) {
		super(context);
	}
	
	public MyGallery(Context context,AttributeSet attrs) {
        super(context,attrs);
    }
	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true); 
		return super.onInterceptTouchEvent(ev); 
	}
	@Override 
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, 
	                       float velocityY) { 
	    int kEvent; 
	    // 实现短距离滚动 取消惯性  
	    if (isScrollingLeft(e1, e2)) { 
	        // Check if scrolling left 
	        kEvent = KeyEvent.KEYCODE_DPAD_LEFT; 
	    } else { 
	        // Otherwise scrolling right 
	        kEvent = KeyEvent.KEYCODE_DPAD_RIGHT; 
	    } 
	    onKeyDown(kEvent, null); 
	    return true; 
	} 
	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) { 
	    return e2.getX() > e1.getX(); 
	} 
}
