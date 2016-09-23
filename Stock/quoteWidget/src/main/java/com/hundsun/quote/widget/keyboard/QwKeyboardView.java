package com.hundsun.quote.widget.keyboard;

import java.util.ArrayList;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hundsun.quotewidget.R;

public class QwKeyboardView extends LinearLayout{
	public static final int KEYBOARD_NUMBER = 0;
	public static final int KEYBOARD_ALPHABET = 1;


	protected  int mKeyboardType = KEYBOARD_NUMBER;
	protected  LinearLayout[] mKeyboards;
	protected  String[]       mKeyboardIndicator = {"abc","123"};
	private    Button         mSwitcchButton;
	private    EditText       mTargetTextView;
	private    Context        mContext;

	public QwKeyboardView(Context context , int keyboardType ) {
		super(context);
		mKeyboardType = keyboardType;
		init(context);
	}

	public QwKeyboardView(Context context ) {
		super(context);
		init(context);
	}

	public QwKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	void init( Context context ){
		mContext = context;
		inflate(context, R.layout.keyboard, this);
		mKeyboards = new LinearLayout[2];
		mKeyboards[0] = (LinearLayout) findViewById(R.id.number_keyboard);
		setActionListener(mKeyboards[0]);

		//		mSwitcchButton = (Button)findViewById(R.id.keyboard_switch_button);
		//		mSwitcchButton.setOnClickListener( mActionListener );
		//		
		//		Button button = (Button)findViewById(R.id.keyboard_exit_button);
		//		button.setOnClickListener( mActionListener );
		//		
		//		button = (Button)findViewById(R.id.keyboard_enter_button);
		//		button.setOnClickListener( mActionListener );
		//		
		//		button = (Button)findViewById(R.id.keyboard_delete_button);
		//		button.setOnClickListener( mActionListener );
		//		button.setOnLongClickListener( new OnLongClickListener() {
		//			
		//			@Override
		//			public boolean onLongClick(View arg0) {
		//				onClearText();
		//				return true;
		//			}
		//		});
	}


	public void changeKeyboard(final int keyboardType) {
		LinearLayout keyboard = null;
		for (int i = 0 , length = mKeyboards.length; i < length; i++) {
			keyboard = mKeyboards[i];
			if (keyboardType == i) {
				if (null == keyboard) {
					keyboard = getKeyboard(keyboardType);
					mKeyboards[i] = keyboard;

					setActionListener(keyboard);
				}else if (!keyboard.isShown()) {
					keyboard.setVisibility(View.VISIBLE);
				}
			} else if (null != keyboard && keyboard.isShown()){
				keyboard.setVisibility(View.GONE);
			}
		}
	}

	private void setActionListener(LinearLayout keyboard) {
		ArrayList<View> inputViews = findChildButtons(keyboard);
		for (View view : inputViews) {
			view.setOnClickListener(mActionListener);
		}
	}

	LinearLayout getKeyboard( int keyboardType ){
		LinearLayout keyboard = null;
		switch ( keyboardType ) {
		case KEYBOARD_ALPHABET:
			((ViewStub) findViewById(R.id.alphabet_stub)).inflate();
			keyboard = (LinearLayout) findViewById(R.id.keyboard_alphabet);
			break;

		case KEYBOARD_NUMBER:
		default:
			break;
		}

		return keyboard;

	}

	void changeKeyboard(){
		mKeyboardType = (mKeyboardType+1)%2;
		changeKeyboard(mKeyboardType);
		mSwitcchButton.setText(mKeyboardIndicator[mKeyboardType]);
	}

	private OnClickListener mActionListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			int id = view.getId();
			if ( R.id.keyboard_num_abc == id ) {
				changeKeyboard( KEYBOARD_ALPHABET );
			}else if ( R.id.keyboard_eng_123_button == id ) {
				changeKeyboard( KEYBOARD_NUMBER );
			}else if(  R.id.keyboard_num_hide_button == id || R.id.keyboard_eng_hide_button == id ){
				if (null != mTargetTextView &&  mTargetTextView.isFocused() ) {
					mTargetTextView.clearFocus();
				}
				hideKeyboard();
			}else if(  R.id.keyboard_num_delete_button == id || R.id.keyboard_eng_delete_button == id  ){
				onDeleteText();
			}else if( R.id.keyboard_num_enter_button == id || R.id.keyboard_eng_enter_button == id  ){
				onEnter();
			}else if ( R.id.keyboard_num_clear_button == id ||  R.id.keyboard_eng_clear_button == id ) {
				onClearText();
			}else if ( view instanceof Button) {
				Button btn = (Button) view;
				onInputText( btn.getText() );
			}
		}
	};


	public void hideKeyboard(){
		this.setVisibility(View.GONE);
	}

	public void showKeyboard(){
		this.setVisibility(View.VISIBLE);
	}

	private void onDeleteText(){
		if (mTargetTextView != null) {
			Editable editable = mTargetTextView.getEditableText();
			int endIndex = editable.length() ;
			if (endIndex > 0) {
				editable.delete( endIndex -1 , endIndex );
			}
			editable = null;
		}
	}

	private void onClearText(){
		if (mTargetTextView != null) {
			Editable editable = mTargetTextView.getEditableText();
			editable.clear();
		}
	}

	private void onEnter(){

	}

	private void onInputText(CharSequence charSequence  ){
		if (mTargetTextView != null) {
			mTargetTextView.append(charSequence);
		}
	}


	ArrayList<View>  findChildButtons( ViewGroup viewGroup){
		ArrayList<View> views = new ArrayList<View>();
		if (viewGroup == null) {
			return views;
		}
		int size = viewGroup.getChildCount();
		View childView = null;
		for (int i = 0; i < size; i++) {
			childView =  viewGroup.getChildAt(i);
			if ( childView instanceof ViewGroup) {
				views.addAll(findChildButtons( (ViewGroup) childView ));
			} else if ( childView instanceof Button){
				views.add(childView);
			}
		}
		return views;
	}

	public EditText getTargetTextView() {
		return mTargetTextView;
	}

	//	private OnFocusChangeListener mTextFocusChangeListener = new OnFocusChangeListener() {
	//		
	//		@Override
	//		public void onFocusChange(View arg0, boolean focused) {
	//			if(focused){
	//				showKeyboard();
	//			}else{
	//				hideKeyboard();
	//			}
	//		}
	//	};
	public void setTargetTextView(EditText targetTextView) {
		this.mTargetTextView = targetTextView;
		//		if (mTargetTextView != null) {
		////			mTargetTextView.setOnFocusChangeListener( mTextFocusChangeListener );
		//			if ( mTargetTextView.isFocused() ) {
		//				showKeyboard();
		//			}
		//			mTargetTextView.setOnClickListener( new OnClickListener() {
		//				
		//				@Override
		//				public void onClick(View arg0) {
		//					showKeyboard();
		//				}
		//			});
		//		}
	}
}
