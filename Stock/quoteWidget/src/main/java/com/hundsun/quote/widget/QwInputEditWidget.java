package com.hundsun.quote.widget;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hundsun.quotewidget.R;

/**
 * @author 梁浩        2015-2-28-下午4:08:09
 * @copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 */
public class QwInputEditWidget extends LinearLayout {
	private Context        mContext;
	private Activity       mAct;
	private Handler        mHandler;
	private int            mMaxLength;
	private int            mFixLength;
	private View 		   mInputView;
	private LinearLayout   mClewLL;          //文字提示区
	private TextView	   mLeftClewTV;      //左边提示
	private TextView 	   mCenterClewTV;    //中间提示
	private TextView	   mRightClewTV;     //右边提示
	private EditText	   mInputET;         //编辑框
	private LayoutInflater mLayoutInflater;

	public QwInputEditWidget(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public QwInputEditWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	private void init() {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mInputView      = mLayoutInflater.inflate(R.layout.common_input_widget, null);
		lp.setMargins(3, 0, 3, 0);
		mInputView.setLayoutParams(lp);
		mClewLL         = (LinearLayout)mInputView.findViewById(R.id.CIWClewLL);
		mLeftClewTV     = (TextView)mInputView.findViewById(R.id.CIWLeftClewTV);
		mCenterClewTV   = (TextView)mInputView.findViewById(R.id.CIWCenterClewTV);
		mRightClewTV    = (TextView)mInputView.findViewById(R.id.CIWRightClewTV);
		mInputET        = (EditText)mInputView.findViewById(R.id.CIWInputET);
		mInputET.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable arg0) {}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,int count) {
				int counts = s.length();
				final int countLen = mMaxLength - mFixLength - counts;
				mAct.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						mClewLL.setVisibility(View.VISIBLE);
						if(countLen > 0 ){
							mLeftClewTV.setVisibility(View.VISIBLE);
							mRightClewTV.setVisibility(View.VISIBLE);
							mCenterClewTV.setText(countLen+"");
							mCenterClewTV.setTextColor(Color.BLACK);
						}else{
							String ellClew   = mContext.getString(R.string.max_input_length_clew);
							String moreClew   = mContext.getString(R.string.input_content_clew_word_more);
							String wordClew   = mContext.getString(R.string.input_content_clew_word);
							mLeftClewTV.setVisibility(View.GONE);
							mRightClewTV.setVisibility(View.GONE);
							if(~countLen+1 != 0){
								mCenterClewTV.setText(moreClew +(~countLen+1)+wordClew);	
								mCenterClewTV.setTextColor(Color.RED);
							}else{
								if(mMaxLength == 0 && mFixLength == 0){
									mClewLL.setVisibility(View.GONE);
									return;
								}else{
									mCenterClewTV.setText(ellClew +(mMaxLength - mFixLength)+wordClew);	
								}
								mCenterClewTV.setTextColor(Color.BLUE);
							}
						}
					}});
			}});
		this.addView(mInputView);
	}

	public void setInit(Activity activity_,Handler handler_) {
		this.mAct       = activity_;
		this.mHandler   = handler_;
	}

	public void setInitParam(int fixLen,int maxLen) {
		this.mFixLength = fixLen;
		this.mMaxLength = maxLen;
	}

	public void setInputContent(String inputContent){
		mInputET.setText(inputContent);
	}
	public String getInputContent(){
		return mInputET.getText().toString();
	}
	public void setInitHint(String initHint){
		mInputET.setHint(initHint);
	}
	/**
	 * 保存到草稿箱

	public void saveToDraftBox(){
		String  inputContent = mInputET.getText().toString();
			HybridApplication.getInstance(mAct).getCore().writeAppDataForKey(QiissContant.KEY_Save_To_Draft_Box_FeedBack, inputContent);
	}
	 */
	/**
	 * 从草稿箱中删除

	public void deleteFromDraftBox(){
		if(mAct instanceof QIIFeedBackNewActivity){
			HybridApplication.getInstance(mAct).getCore().writeAppDataForKey(QiissContant.KEY_Save_To_Draft_Box_FeedBack, "");
		}
	}
	 */
	/**
	 * 打开提示区
	 */
	public void openClewRegional(){
		mClewLL.setVisibility(View.VISIBLE);
	}
	/**
	 * 关闭提示区
	 */
	public void closeClewRegional(){
		mClewLL.setVisibility(View.GONE);
	}
	/**
	 * 设置选中
	 */
	public void setSelection() {
		Editable editable = mInputET.getText();
		mInputET.setSelection(editable.length());
	}
	/**
	 * 移动到光标位置
	 */
	public int getSelectionStart() {
		return mInputET.getSelectionStart();
	}
	/**
	 * 在光标处插入数据
	 * @param mLastPostion
	 * @param string
	 */
	public void insertTxtInEditable(int mLastPostion, String data) {
		Editable inputEditable = mInputET.getText();
		inputEditable.insert(mLastPostion, data);
	}

	InputMethodManager inputManager = null;
	/**
	 * 自动弹出软键盘
	 */
	public void autoOpenSoftKeyboard() {
		new Timer().schedule(new TimerTask(){
			public void run() {
				inputManager = (InputMethodManager)mInputET.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(mInputET, 0);
			}
		}, 998);
	}

	public void closeSoftKeybord() {
		if(inputManager != null){
			if(inputManager.isActive()){
				inputManager.hideSoftInputFromWindow(mInputET.getWindowToken(), 0);
			}	
		}
	}
}
