package com.hundsun.quote.widget;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hundsun.quote.model.CommonStockRankTool;
import com.hundsun.quote.tools.CommonTools;
import com.hundsun.quotewidget.R;

/**
 * 可滑动导航视图控件
 * @author 梁浩        2014-12-2-上午12:40:15
 * @copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 */
public class QwScrollNavigateView extends LinearLayout {

	private Context              mContext;
	private Resources            mRes;
	private HorizontalScrollView mHsv;

	private int      mScreenWidth = 800;
	private int      mColumnHeadHeight = 45;
	private int      mColumnSelectedLineHight = 2;
	private float    mFontSize    = 16.0F;
	private Drawable mSelectedBgLine;
	private Drawable mDefaultBgLine;
	private ArrayList<CommonStockRankTool> mColumnHeader; //头元素
	private String mType  = "";
	private boolean isScroll = true;

	private CommonColumnHeaderListener    mCommonColumnHeaderListener;
	private OnClickListener mColumnHeaderItemOnClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			mColumnHeaderItemOnClickListenerDo(v);
		}
	};

	public QwScrollNavigateView(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public QwScrollNavigateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	@SuppressWarnings("deprecation")
	private void init() {
		mScreenWidth = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		mRes = mContext.getResources();
		mColumnHeadHeight = (int) mRes.getDimension(R.dimen.navigate_bar_height);
		mColumnSelectedLineHight = (int) mRes.getDimension(R.dimen.navigate_bar_selected_line_height);
		mFontSize     = CommonTools.getScreenShowTextSize(mContext);
		mHsv = new HorizontalScrollView(mContext);
		this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, mColumnHeadHeight));
		mHsv.setBackgroundColor(mRes.getColor(R.color.common_hsv_bg));
		mHsv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, mColumnHeadHeight ));
		mHsv.setHorizontalScrollBarEnabled(false);
		mHsv.setHorizontalFadingEdgeEnabled(false);
		initBgPic();
		this.addView(mHsv);
		this.addView(buildSpliteLineView());
	}


	/**
	 * 滑动视图
	 */
	@SuppressWarnings("deprecation")
	private void buildScrollNavigateHeader() {
		if (mColumnHeader.size() == 0) {
			return;
		}
		LinearLayout columnLayout = new LinearLayout(mContext);
		columnLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, mColumnHeadHeight));
		columnLayout.setOrientation(LinearLayout.HORIZONTAL);
		RelativeLayout columnItemRL = null;
		TextView       columnItemTV = null;
		ImageView      columnItemIV = null;
		RelativeLayout.LayoutParams lp = null;
		int columnNum = mColumnHeader.size();
		
		if(isScroll){
			if(columnNum > 4){
				lp = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT);
				lp.leftMargin  = 8;
				lp.rightMargin = 8;
			}else{
				if(columnNum != 0){
					lp = new RelativeLayout.LayoutParams(mScreenWidth/columnNum, LayoutParams.MATCH_PARENT);
				}else{
					lp = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT);
					lp.leftMargin  = 8;
					lp.rightMargin = 8;
				}
			}
		}else{
			lp = new RelativeLayout.LayoutParams(mScreenWidth/columnNum, LayoutParams.MATCH_PARENT);
		}

		for(int columnItem = 0; columnItem < columnNum;columnItem++){
			CommonStockRankTool columnHeaderData = mColumnHeader.get(columnItem);
			columnItemRL = new RelativeLayout(mContext);
			columnItemRL.setLayoutParams(lp);
			RelativeLayout.LayoutParams tvlp = null;
			if(isScroll && columnNum > 4){
				 tvlp =  new RelativeLayout.LayoutParams(3*mColumnHeadHeight, LayoutParams.WRAP_CONTENT);
			}else{
				 tvlp =  new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			}
			tvlp.addRule(RelativeLayout.CENTER_IN_PARENT);
			columnItemTV = new TextView(mContext);
			columnItemTV.setId(R.id.Key_Widget_NavigateView_TextView_Id);
			String showName = columnHeaderData.getName();
			columnItemTV.setText(showName);
			//columnItemTV.setTextSize(TypedValue.COMPLEX_UNIT_SP,mFontSize);
			columnItemTV.setTextSize(TypedValue.COMPLEX_UNIT_SP,mRes.getDimension(R.dimen.font_small_px));
			//columnItemTV.setTextSize(mRes.getDimension(R.dimen.font_small_px));
			columnItemTV.setLayoutParams(tvlp);
			columnItemTV.setGravity(Gravity.CENTER);
			columnItemRL.addView(columnItemTV);

			columnItemTV.measure(columnItemTV.getMeasuredWidth(), columnItemTV.getMeasuredHeight());
			int iv_width = columnItemTV.getMeasuredWidth();
			RelativeLayout.LayoutParams ivlp = new RelativeLayout.LayoutParams(iv_width +8, mColumnSelectedLineHight);
			ivlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			ivlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			//ivlp.leftMargin = 10;
			//ivlp.rightMargin = 10;
			columnItemIV = new ImageView(mContext);
			columnItemIV.setId(R.id.Key_Widget_NavigateView_ImageView_Id);
			columnItemIV.setLayoutParams(ivlp);
			if(columnItem == 0){
				columnItemTV.setTextColor(mRes.getColor(R.color.common_hsv_tv_selected));
				//columnItemIV.setImageDrawable(mSelectedBgLine);
				columnItemIV.setBackgroundDrawable(mSelectedBgLine);
			}else{
				columnItemTV.setTextColor(mRes.getColor(R.color.common_hsv_tv_default));
				//columnItemIV.setImageDrawable(mDefaultBgLine);
				columnItemIV.setBackgroundDrawable(mDefaultBgLine);
			}
			columnItemRL.addView(columnItemIV);
			columnItemRL.setTag(columnHeaderData);
			columnItemRL.setOnClickListener(mColumnHeaderItemOnClickListener );
			columnLayout.addView(columnItemRL);
		}
		columnLayout.measure(columnLayout.getMeasuredWidth(), columnLayout.getMeasuredHeight());
		mHsv.getMaxScrollAmount();
		mHsv.computeScroll();
		mHsv.addView(columnLayout);
	}
	/**
	 * 分割线
	 * @return
	 */
	private View buildSpliteLineView() {
		View spliteView = new View(mContext);
		spliteView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1 ));
		spliteView.setBackgroundColor(mRes.getColor(R.color.common_hsv_splite_line_bg));
		return spliteView;
	}

	/**
	 * 初始化选中下划线
	 */
	private void initBgPic() {
		if(mSelectedBgLine == null){
			mSelectedBgLine = mRes.getDrawable(R.drawable.common_column_header_selected);
		}
		if(mDefaultBgLine == null){
			mDefaultBgLine = mRes.getDrawable(R.drawable.common_column_header_default);
		}
	}

	/**
	 * 生成导航滑动视图
	 */
	public void buildScrollNavigateView(){
		mColumnHeader = getColumnHeader();
		mType = getType();
		//如果没有设置值则看配置属性名，如果配置属性也没有，则初始化系统值
		if(mColumnHeader == null){
			mColumnHeader = new ArrayList<CommonStockRankTool>();
		}
		buildScrollNavigateHeader();
	}

	@SuppressWarnings("deprecation")
	protected void mColumnHeaderItemOnClickListenerDo(View v) {
		CommonStockRankTool tag = (CommonStockRankTool) v.getTag();
		resetColumnHeaderItemStatus();
		RelativeLayout columnItemRL = (RelativeLayout) v;
		TextView  columnItemTV = (TextView) columnItemRL.getChildAt(0);
		ImageView columnItemIV = (ImageView) columnItemRL.getChildAt(1);
		columnItemTV.setTextColor(mRes.getColor(R.color.common_hsv_tv_selected));
		//columnItemIV.setImageDrawable(mSelectedBgLine);
		columnItemIV.setBackgroundDrawable(mSelectedBgLine);
		mCommonColumnHeaderListener.loadData(tag.getValue());
	}

	/**
	 * 重置状态
	 */
	@SuppressWarnings("deprecation")
	private void resetColumnHeaderItemStatus() {
		LinearLayout sons = (LinearLayout) mHsv.getChildAt(0);
		if(sons != null){
			int sonsCount = sons.getChildCount();
			if(sonsCount > 0){
				for(int gradent = 0;gradent < sonsCount; gradent++){
					RelativeLayout columnRl = (RelativeLayout) sons.getChildAt(gradent);
					if(columnRl != null){
						TextView  columnItemTV = (TextView) columnRl.getChildAt(0);
						ImageView columnItemIV = (ImageView) columnRl.getChildAt(1);
						columnItemTV.setTextColor(mRes.getColor(R.color.common_hsv_tv_default));
						//columnItemIV.setImageDrawable(mDefaultBgLine);
						columnItemIV.setBackgroundDrawable(mDefaultBgLine);
					}
				}
			}
		}
	}

	/**
	 * 数据接口
	 * @author 梁浩        2014-11-13-上午10:54:23
	 *
	 */
	public interface CommonColumnHeaderListener{
		void loadData(String code);
	}

	public CommonColumnHeaderListener getCommonColumnHeaderListener() {
		return mCommonColumnHeaderListener;
	}

	public void setCommonColumnHeaderListener(CommonColumnHeaderListener commonColumnHeaderListener) {
		this.mCommonColumnHeaderListener = commonColumnHeaderListener;
	}

	public ArrayList<CommonStockRankTool> getColumnHeader() {
		return mColumnHeader;
	}

	public void setColumnHeader(ArrayList<CommonStockRankTool> columnHeader) {
		this.mColumnHeader = columnHeader;
	}

	public String getType() {
		return mType;
	}

	public void setType(String type) {
		this.mType = type;
	}

	public boolean isScroll() {
		return isScroll;
	}

	public void setScroll(boolean isScroll) {
		this.isScroll = isScroll;
	}


}
