package com.hundsun.quote.widget;

import java.util.HashMap;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hundsun.quote.viewmodel.KlineViewModel;
import com.hundsun.quote.widget.QwKlineView;
import com.hundsun.quote.widget.QwKlineView.IKlineEvent;
import com.hundsun.quote.widget.QwKlineView.TechnicalIndicatorType;
import com.hundsun.quotewidget.R;

/**
 *源程序名称:QiiKlineLandscapeWidget.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QIIPro-1.1
 *模块名称: 
 *功能说明:
 *作    者: huangcheng
 *开发日期:2014-10-23 下午7:17:50
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class QiiKlineLandscapeWidget extends LinearLayout{
	private QwKlineView mQwKlineView;
	private ListView mKlineTecListView;
	private ListView mKlineModeListView;
	private ArrayAdapter<String> mAdapter;
	TextView mCurrentSelectView;
//	private int mCurrentPosition;
	private HashMap<String , TechnicalIndicatorType> mTechnicalIndicatorTypeMap;

	public QiiKlineLandscapeWidget(Context context) {
		this(context, null);
	}
	
	public QiiKlineLandscapeWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView( context );
		initTechnicalIndicator(context);
	}
	
	private int getKlineMode(){
		int mode = 0;
//		String modeStr = Core.getInstance(getContext()).readConfig(Keys.KEY_QII_KLINE_MODE);
//		if ( !TextUtils.isEmpty(modeStr) ) {
//			mode = Integer.parseInt(modeStr);
//		}
		return mode;
	}
	
	private TextView mCurrentModeSelectView;
	private void setCurrentModeSelectView( View view){
		if (null != mCurrentModeSelectView) {
			mCurrentModeSelectView.setSelected(false);
		}
		mCurrentModeSelectView = (TextView) view;
		mCurrentModeSelectView.setSelected(true);
	}
	
	private void initKlineModeView(KlineViewModel viewModel){
		final String[] modes = {"不复权","复权"};
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>( this.getContext(), R.layout.simple_list_item, modes){
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				final View view = super.getView(position, convertView, parent);
				int mode = getKlineMode();
				if (mode == position) {
					mKlineModeListView.postDelayed( new Runnable() {
					
						@Override
						public void run() {
							setCurrentModeSelectView(view);
						}
					}, 300);
				}
				
				return view;
			}
			
		};
		mKlineModeListView.setAdapter(adapter);
		mKlineModeListView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				setCurrentModeSelectView(arg1);
//				Core.getInstance(getContext()).writeConfig(Keys.KEY_QII_KLINE_MODE ,String.valueOf(position) );
			}
		});
	}

	private void initTechnicalIndicator(Context context) {
		final String[] technicals = {"成交量","MACD","RSI","KDJ","PSY","WR" ,"CCI" , "BOLL" , "BIAS" , "ASI" , "VR" , "OBV" , "VOL"};
		mAdapter = new ArrayAdapter<String>(context, R.layout.simple_list_item, technicals){

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				final TextView view = (TextView) super.getView(position, convertView, parent);
				TechnicalIndicatorType currentType = mQwKlineView.getTechnicalIndicatorType();
				TechnicalIndicatorType type = mTechnicalIndicatorTypeMap.get(technicals[position]);
				if (type == currentType) {
					setCurrentSelectView(view);
				}else{
					view.setTextColor( getResources().getColor(R.color.app_navi_bar_font_color));
				}
				
				return view;
			}
			
		};
		mKlineTecListView.setAdapter(mAdapter);
		mTechnicalIndicatorTypeMap = new HashMap<String, QwKlineView.TechnicalIndicatorType>();
		mTechnicalIndicatorTypeMap.put("成交量", QwKlineView.TechnicalIndicatorType.VOMLUME);
		mTechnicalIndicatorTypeMap.put("MACD", QwKlineView.TechnicalIndicatorType.MACD);
		mTechnicalIndicatorTypeMap.put("RSI", QwKlineView.TechnicalIndicatorType.RSI);
		mTechnicalIndicatorTypeMap.put("KDJ", QwKlineView.TechnicalIndicatorType.KDJ);
		mTechnicalIndicatorTypeMap.put("PSY", QwKlineView.TechnicalIndicatorType.PSY);
		mTechnicalIndicatorTypeMap.put("WR", QwKlineView.TechnicalIndicatorType.WR);
		mTechnicalIndicatorTypeMap.put("CCI", QwKlineView.TechnicalIndicatorType.CCI);
		mTechnicalIndicatorTypeMap.put("BOLL", QwKlineView.TechnicalIndicatorType.BOLL);
		
		mTechnicalIndicatorTypeMap.put("BIAS", QwKlineView.TechnicalIndicatorType.BIAS);
		mTechnicalIndicatorTypeMap.put("ASI", QwKlineView.TechnicalIndicatorType.ASI);
		mTechnicalIndicatorTypeMap.put("VR", QwKlineView.TechnicalIndicatorType.VR);
		mTechnicalIndicatorTypeMap.put("OBV", QwKlineView.TechnicalIndicatorType.OBV);
		mTechnicalIndicatorTypeMap.put("VOL", QwKlineView.TechnicalIndicatorType.VOL);
		
		mKlineTecListView.setOnItemClickListener(new OnItemClickListener() {
			

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				TechnicalIndicatorType type = mTechnicalIndicatorTypeMap.get(technicals[position]);
				mQwKlineView.setTechnicalIndicatorType( type );
				setCurrentSelectView(arg1);
			}
		});
		
	}
	
	public void setViewModel( KlineViewModel viewModel ){
		mQwKlineView.setData(viewModel);
//		initKlineModeView( viewModel );
	}

	private void initView(Context context) {
		this.setOrientation(HORIZONTAL);
		inflate(context, R.layout.qii_widget_kline_landscape, this);
		mQwKlineView = (QwKlineView) findViewById(R.id.quote_kline_view);
		mQwKlineView.setEnableTouch(true);
		mQwKlineView.setDrawAxisInside(false);
		mKlineTecListView = (ListView) findViewById(R.id.quote_kline_tec);
		mKlineModeListView = (ListView) findViewById(R.id.quote_kline_mode);
	}
	
	public void setKlineEvent( IKlineEvent klilneEvent ){
		mQwKlineView.setKlineEvent( klilneEvent );
	}

	public void invalidateKlineView() {
		mQwKlineView.invalidate();
	}

	public void dataAdded(int dataAdded) {
		mQwKlineView.dataAdded( dataAdded );
	}
	
	public void setTechnicalIndicatorType( TechnicalIndicatorType type ){
		if (null == mQwKlineView) {
			return;
		}
//		TechnicalIndicatorType[] values = TechnicalIndicatorType.values();
//		for (int i = 0; i < values.length; i++) {
//			if (values[i] == type) {
//				mCurrentPosition = i;
//				break;
//			}
//		}
//		mAdapter.notifyDataSetChanged();
		mQwKlineView.setTechnicalIndicatorType(type);
	}
	
	private void setCurrentSelectView( View view){
		if (null != mCurrentSelectView) {
//			mCurrentSelectView.setSelected(false);
//			mCurrentSelectView.setEnabled(true);
			mCurrentSelectView.setTextColor( getResources().getColor(R.color.app_navi_bar_font_color));
		}
		mCurrentSelectView = (TextView) view;
		mCurrentSelectView.setTextColor( getResources().getColor(R.color.app_navi_bar_font_color_selected));
//		mCurrentSelectView.setSelected(true);
//		mCurrentSelectView.setEnabled(false);
	}

//	private void selectedTechnicalIndicator(TechnicalIndicatorType type) {
//		TechnicalIndicatorType[] values = TechnicalIndicatorType.values();
//		int position = 0;
//		for (int i = 0; i < values.length; i++) {
//			if (values[i] == type) {
//				position = i;
//				break;
//			}
//		}
//		if (null != mCurrentSelectView) {
//			mCurrentSelectView.setSelected(false);
//		}
//		try {
//			mCurrentSelectView = (TextView) mListView.getChildAt(position);
//			if (null == mCurrentSelectView) {
//				return;
//			}
//			mCurrentSelectView.setSelected(true);
//		} catch (Exception e) {
//		}
//	}
	public TechnicalIndicatorType getTechnicalIndicatorType(){
		if (null == mQwKlineView) {
			return TechnicalIndicatorType.VOMLUME;
		}
		
		return mQwKlineView.getTechnicalIndicatorType();
	}

}

