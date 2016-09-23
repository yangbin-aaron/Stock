package com.hundsun.quote.widget;

import android.content.Context;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hundsun.quote.widget.keyboard.QwKeyboardView;
import com.hundsun.quotewidget.R;

public class QwSearchView extends LinearLayout{
	
	private QwKeyboardView mKeyboardView;
	private EditText mInputEditView;
	private ListView mListView;

	public QwSearchView(Context context) {
		super(context );
		init(context);
	}

	public QwSearchView(Context context, AttributeSet attrs) {
		super(context, attrs );
		init(context);
	}
	
	public void clearText(){
		mInputEditView.setText("");
	}
	
	public void changeKeyboard( int keyboardType){
		mKeyboardView.changeKeyboard(keyboardType);
	}
	
	private void init(Context context) {
		inflate(context, R.layout.qw_widget_search_view, this);
		mKeyboardView = (QwKeyboardView)findViewById(R.id.qw_keyboardview);
		mInputEditView= (EditText)findViewById(R.id.qw_code_edit);
		mKeyboardView.setTargetTextView(mInputEditView);
		mListView = (ListView)findViewById(R.id.qw_stock_list);
//		mListView.setOnScrollListener( new OnScrollListener() {
//			
//			@Override
//			public void onScrollStateChanged(AbsListView listview, int scrollState) {
//				
//			}
//			
//			@Override
//			public void onScroll(AbsListView listview, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//				if ( firstVisibleItem > 0) {
//					mKeyboardView.hideKeyboard();
//				} else {
//					mKeyboardView.showKeyboard();
//				}
//			}
//		});
		
		mInputEditView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				EditText et = (EditText)v;
				if (event.getAction() == MotionEvent.ACTION_UP) {
					int inType = et.getInputType(); // backup the input type
					et.setInputType(InputType.TYPE_NULL); // disable soft input
					et.onTouchEvent(event); // call native handler
					et.setInputType(inType); // restore input type
					et.setCursorVisible(true);
					v.requestFocus();
					String text = et.getText().toString();
					if(text != null && text.length() > 0)
						et.setSelection(text.length());
					mKeyboardView.showKeyboard();
					return true;
				}
				return false;
			}
		});
	}

	
	public void addTextChangedListener( TextWatcher textWatcher ){
		if ( null != mInputEditView && null != textWatcher) {
			mInputEditView.addTextChangedListener(textWatcher);
		}
	}
	
	public void removeTextChangedListener( TextWatcher textWatcher ){
		if ( null != mInputEditView && null != textWatcher) {
			mInputEditView.removeTextChangedListener( textWatcher );
		}
	}
	
	public void setOnItemClickListener( OnItemClickListener listener){
		mListView.setOnItemClickListener(listener);
	}
	
	public ListView getListView( ){
		return mListView;
	}
}
