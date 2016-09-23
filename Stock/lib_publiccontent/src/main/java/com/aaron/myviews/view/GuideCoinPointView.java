package com.aaron.myviews.view;
/** 
 * @Title: GuideCoinPointView.java 
 * @Package com.luckin.magnifier.view
 * @Description: TODO 
 * @ClassName: GuideCoinPointView 
 *
 * @author 于泽坤 
 * @date 2015-7-7 上午10:21:59 
*/

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

@SuppressLint({"DrawAllocation", "HandlerLeak"})
public class GuideCoinPointView extends View {
    
	private float mTextSize = 36;          // 文字大小
	private Paint mTextPaint;              // 画笔
	private FontMetrics mFontMetrics;      // 字体属性
	private int mShowNumbers[];            // 要显示的数字组合
	private float Y = 0;                   // Y坐标 (字中间线的坐标)
	private float Ytemp = 0;               //当前文本的Y坐标
	private int which = 0;                 // 当前的数值
	private final int sleep = 20;          //sleep的次数
	private int sleepTime = sleep;         // 线程时间
	private int animPrecision = 12;        // 动画精度(该动作分几次完成)
	private int mTextColor = Color.BLACK;
	private int mSymbolpoint = 0;          //符号，的个数
	private TextThread mTextThread ;

	public GuideCoinPointView(Context context) {
		super(context);
		mTextThread = new TextThread();
	}

	public Handler getHandler(int textSize, int colorRes, String coinPoints) {

		this.mTextSize = textSize;
		
		mSymbolpoint = 0;
		ArrayList<String> numberTemp = new ArrayList<String>();
		if(coinPoints.contains(".")){
		    String[] numberArray = coinPoints.split("\\.");
		    for (int i = 0; i < numberArray[0].length(); i++) {
		        if (i != 0 && (numberArray[0].length() - i) % 3 == 0) {
		            numberTemp.add("10");
		            mSymbolpoint++;
		        }
		        numberTemp.add(coinPoints.substring(i, i + 1));
		    }
		    numberTemp.add("20");
		    mSymbolpoint++;
		    for (int i = 0; i < numberArray[1].length(); i++) {
		        numberTemp.add(numberArray[1].substring(i, i + 1));
		    }
		}else{
		    for (int i = 0; i < coinPoints.length(); i++) {
		        if (i != 0 && (coinPoints.length() - i) % 3 == 0) {
		            numberTemp.add("10");
		            mSymbolpoint++;
		        }
		        numberTemp.add(coinPoints.substring(i, i + 1));
		    }
		}
		mShowNumbers = new int[numberTemp.size()];
		for (int i = 0; i < mShowNumbers.length; i++) {
			mShowNumbers[i] = Integer.parseInt(numberTemp.get(i));
		}
		initPaint(colorRes);
		return mHandler;
	}
	
	void initPaint(int colorRes) {
		mTextColor = colorRes;
		mTextPaint = new Paint();             // 文本
		mTextPaint.setColor(mTextColor);
		mTextPaint.setAntiAlias(true);        // 去掉边缘锯齿
		mTextPaint.setTextSize(mTextSize);
		mFontMetrics = mTextPaint.getFontMetrics();
		
		//文字居中线
		Y = mTextSize / 2 + (-mFontMetrics.ascent + mFontMetrics.descent) / 2
				- mFontMetrics.descent * 3 / 2 - 1;
		
		setLayoutParams(new LinearLayout.LayoutParams((int) (mTextSize / 2
				* ((float) mShowNumbers.length - (float) mSymbolpoint / 2) + mTextSize / 20),
				(int) (Y + mFontMetrics.descent) * 9 / 10));
		
		mTextPaint.setTextSize(mTextSize * 9 / 10);
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0 :// 动画
					which = 0;
					mTextThread.start();
					break;
			}
		}
	};

	class TextThread extends Thread {

		@Override
		public void run() {
			if (mShowNumbers != null && mShowNumbers.length > 0) {
				try {
					Thread.sleep(550 - sleepTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
				while (which < 10) {
					try {
						Thread.sleep(sleepTime);
					} catch (Exception e) {
						e.printStackTrace();
					}
					mHandler.post(runnable);
				}
				sleepTime = sleep;
			}
		}
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			if (Ytemp - mTextSize >= 0) {
				Ytemp = 0;
				which++;
				sleepTime -= (sleep / 10 - sleep / 200);
			} else{
			    Ytemp += mTextSize / animPrecision;
			}
			invalidate();
		}
	};

	@Override
	protected void onDraw(Canvas canvas) {
		if (mShowNumbers != null && mShowNumbers.length > 0) {
			int how = 0;
			for (int i = 0; i < mShowNumbers.length; i++) {
				if (mShowNumbers[i] == 10) {
					canvas.drawText(",", i * mTextSize / 2 - how * mTextSize / 4,
							Y, mTextPaint);
					how++;
				}else if(mShowNumbers[i] == 20){
				    canvas.drawText(".", i * mTextSize / 2 - how * mTextSize / 4,
				            Y, mTextPaint);
				    how++;
				}else{
					if (which < mShowNumbers[i]) {
						canvas.drawText(which + "", i * mTextSize / 2 - how
								* mTextSize / 4, Y - Ytemp, mTextPaint);
						canvas.drawText(which + 1 + "", i * mTextSize / 2 - how
								* mTextSize / 4,
								Y - Ytemp + getMeasuredHeight(), mTextPaint);
					} else {
						canvas.drawText(mShowNumbers[i] + "", i * mTextSize / 2 - how
								* mTextSize / 4, Y, mTextPaint);
					}
				}
			}
		}
		super.onDraw(canvas);
	}
}