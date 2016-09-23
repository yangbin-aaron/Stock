package com.hundsun.quote.widget;


import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hundsun.quote.model.CommonStockRankTool;
import com.hundsun.quote.model.Stock;
import com.hundsun.quote.tools.CommonTools;
import com.hundsun.quote.tools.CommonTools.QUOTE_SORT;
import com.hundsun.quote.tools.CommonTools.ScrollTableRefreshMode;
import com.hundsun.quotewidget.R;

/**
 * @author 梁浩        2015-2-12-上午9:14:22
 * @copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 */
public class QwScrollTableListHeadWidget extends QwScrollTableBaseRelativeLayout{

	private Context      	mContext;
	private Resources    	mResource;
	private LinearLayout 	mScrollTableFixTitleLl;
	private LinearLayout 	mScrollTableMoveableTitleLl;
	public  QwScrollTableHorizontalScrollView mMoveableTitleHsv ;
	private ScrollTableRefreshMode mRefreshMode = ScrollTableRefreshMode.None;
	private int             mScreenWidth    = 0;
	//private float         mFontSize       = 0.0F;
	private int          	mItemHeight     = 23;
	private int          	lastKey	     = 0;          //区别点击是否是同一个视图
	private int          	currentSortMode = -1; //排序模式 （三种、第一种升序、第二种降序、第三种不排序）本来采用枚举类型实现，现在采用int
	private int          	lastSortMode    = -1; //记录上一次排序模式，用于在两视图切换时区别
	private Drawable 	 	mAscDrawable;  //升序图标
	private Drawable     	mDescDrawable; //降序图标
	 /*排序监听接口*/
	protected ScrollTableTitleSortOnClickListener mScrollTableTitleSortOnClickListener;
	private ScrollTableListItemOnClickListener  mScrollTableListItemOnClickListener;  //列表项单击接口
	/*是否允许不排序状态*/
	private boolean mEnableNoneSortting;

	public QwScrollTableListHeadWidget(Context context){
		super(context);
		init(context);
	}

	public QwScrollTableListHeadWidget(Context context, AttributeSet attributeSet){
		super(context, attributeSet);
		init(context);
	}

	public QwScrollTableListHeadWidget(Context context, AttributeSet attributeSet, int paramInt){
		super(context, attributeSet, paramInt);
		init(context);
	}
	
	@SuppressWarnings("deprecation")
	protected void init(Context context){
		this.mContext = context;
		mEnableNoneSortting = true;
		mResource     = context.getResources();
		mScreenWidth  = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		mItemHeight   = (int) mResource.getDimension(R.dimen.scroll_table_item_height);
		if(mAscDrawable == null){
			mAscDrawable  = mContext.getResources().getDrawable(R.drawable.common_arrow_up_gray);	
			mAscDrawable.setBounds(0, 0, mAscDrawable.getMinimumWidth(), mAscDrawable.getMinimumHeight());//必须设置图片大小，否则不显示
		}
		if(mDescDrawable == null){
			mDescDrawable  = mContext.getResources().getDrawable(R.drawable.common_arrow_down_gray);	
			mDescDrawable.setBounds(0, 0, mDescDrawable.getMinimumWidth(), mDescDrawable.getMinimumHeight());//必须设置图片大小，否则不显示
		}

		LayoutInflater localLayoutInflater = LayoutInflater.from(context);
		View localView = null;
		localView = localLayoutInflater.inflate(R.layout.qw_scroll_table_list_view_scrolls_head, this);
		mMoveableTitleHsv = (QwScrollTableHorizontalScrollView)localView.findViewById(R.id.scroll_table_moveable_title_hsv);
		mMoveableTitleHsv.setTouchAble(true);
		mMoveableTitleHsv.setHorizontalScrollBarEnabled(false);
		this.mScrollTableFixTitleLl      = ((LinearLayout)localView.findViewById(R.id.scroll_table_fixed_title_ll));
		this.mScrollTableMoveableTitleLl = ((LinearLayout)localView.findViewById(R.id.scroll_table_moveable_title_ll));
		
	}

	/**
	 * 添加固定表头
	 * @param fixTitleLayout
	 * @param moveableTitleLayout
	 */
	private void addScrollTableTitleViews(LinearLayout fixTitleLayout, LinearLayout moveableTitleLayout){
		if (fixTitleLayout.getChildCount() > 0){
			this.mScrollTableFixTitleLl.addView(fixTitleLayout);
		}
		if (moveableTitleLayout.getChildCount() <= 0){
			return;
		}
		this.mScrollTableMoveableTitleLl.addView(moveableTitleLayout);
	}

	/**
	 * 清理滑动表格表头视图及数据
	 */
	private void clearTitleAllViews() {
		if (this.mScrollTableFixTitleLl.getChildCount() > 0)
			this.mScrollTableFixTitleLl.removeAllViews();
		if (this.mScrollTableMoveableTitleLl.getChildCount() <= 0)
			return;
		this.mScrollTableMoveableTitleLl.removeAllViews();
	}

	/**
	 * 设置滑动表格控件标题
	 * @param titles
	 */
	public void setScrollTableTitle(List<CommonStockRankTool> sortColumnHeaders){
		if(sortColumnHeaders == null){
			return;
		}
		LinearLayout fixTitleLL;
		LinearLayout moveableTitleLL;
		LinearLayout moveableTitleItemLL;
		TextView     titleTv;
		LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT); 
		LinearLayout.LayoutParams lp;

		fixTitleLL      = new LinearLayout(this.mContext);
		moveableTitleLL = new LinearLayout(this.mContext);
		int len = sortColumnHeaders.size();
		for(int title_item = 0;title_item < len;title_item ++){
			CommonStockRankTool columnHeader = sortColumnHeaders.get(title_item);
			moveableTitleItemLL = new LinearLayout(mContext);
			titleTv = new TextView(this.mContext);
			if(columnHeader.headFontSize != 0){
				titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,columnHeader.headFontSize);
			}else{
				titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,mResource.getDimension(R.dimen.font_small_px));
			}
			if(columnHeader.headFontColor != 0){
				titleTv.setTextColor(columnHeader.headFontColor);
			}else{
				titleTv.setTextColor(mResource.getColor(R.color.complex_ranking_name));
			}
			titleTv.setText(columnHeader.getName());
			titleTv.setGravity(Gravity.CENTER);
			if(columnHeader.isFixed){
				lp = new LinearLayout.LayoutParams(mScreenWidth/4+15, (int) mResource.getDimension(R.dimen.navigate_bar_height)); 
			}else{
				lp = new LinearLayout.LayoutParams(mScreenWidth/4, (int) mResource.getDimension(R.dimen.navigate_bar_height));
				if(columnHeader.isSupportSort == false){
					//不能排序列
					titleTv.setTextColor(mResource.getColor(R.color.rank_volume_color));
				}else{
					titleTv.setOnClickListener(new OnClickListener(){
						@Override
						public void OnContentItemClickListener(View view) {
						}
						@Override
						public void OnTitleClickListener(View view) {
						}
						@Override
						public void onClick(View v) {
							scrollTableColumnSortOnClickListener(v);
						}
					});
					//默认排序设置
					if(columnHeader.ascending != -1){
						if(columnHeader.ascending == 0){
							titleTv.setCompoundDrawables(null, null, mAscDrawable, null);
							titleTv.setCompoundDrawablePadding(5);
						}else if(columnHeader.ascending == 1){
							titleTv.setCompoundDrawables(null, null, mDescDrawable, null);
							titleTv.setCompoundDrawablePadding(5);
						}
					}
				}
				titleTv.setTag(columnHeader);
			}
			titleTv.setLayoutParams(titleLp);
			moveableTitleItemLL.addView(titleTv,titleLp);
			lp.gravity = Gravity.CENTER;
			if(columnHeader.isFixed){
				if(columnHeader.key== CommonTools.KEY_SORT_STOCK_CODE){
					moveableTitleItemLL.setPadding(5, 0, 0, 0);
					moveableTitleItemLL.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
					fixTitleLL.addView(moveableTitleItemLL, lp);
				}
			}else{
				moveableTitleItemLL.setGravity(Gravity.CENTER);
				moveableTitleLL.addView(moveableTitleItemLL, lp);
			}
		}
		clearTitleAllViews();
		addScrollTableTitleViews(fixTitleLL, moveableTitleLL);
	}

	

	public int getScreenWidth(){
		return this.mScreenWidth;
	}

	public void setScreenWidth(int screenWidth){
		this.mScreenWidth = screenWidth;
	}

	/**
	 * 滑动表头单击排序接口
	 * @author 梁浩        2015-1-1-下午1:15:46
	 *
	 */
	public interface ScrollTableTitleSortOnClickListener {
		void changeSort(QUOTE_SORT sortType, int ascending);
	}

	public ScrollTableTitleSortOnClickListener getScrollTableTitleSortOnClickListener() {
		return mScrollTableTitleSortOnClickListener;
	}

	public void setScrollTableTitleSortOnClickListener(ScrollTableTitleSortOnClickListener scrollTableTitleSortOnClickListener) {
		this.mScrollTableTitleSortOnClickListener = scrollTableTitleSortOnClickListener;
	}

	/**
	 * 获取排序模式-当单击其它字段时
	 * @param currentMode
	 * @return
	 */
	private int getNextSortMode( int currentMode ){
		int nextMode = 0;
		if(currentMode == 1){
			nextMode = 0;	
		}else if(currentMode == 0){
			if (mEnableNoneSortting) {
				nextMode = -1;
			} else {
				nextMode = 1;
			}
		}else if(currentMode == -1){
			nextMode = 1;
		}
		return nextMode;
	}

	private void scrollTableColumnSortOnClickListener(View v) {
		resetSortInfo();//重置排序状态
		TextView currentTv = (TextView) v;
		CommonStockRankTool columnData =  (CommonStockRankTool) v.getTag();
		int currentKey =  columnData.key;
		if ( lastKey == 0 ) {
			lastSortMode = columnData.ascending;
		}
		if(currentKey != lastKey && lastKey != 0){
			currentSortMode = 1;
		}else{
			currentSortMode = getNextSortMode( lastSortMode );
		}
		changeColumnHeaderStatusAndSort(currentTv, columnData, currentKey);
	}

	/**
	 * 升降序
	 * @param currentTv
	 * @param columnData
	 */
	private void changeColumnHeaderStatusAndSort(TextView currentTv,CommonStockRankTool columnData,int currentKey) {
		switch(currentSortMode){
		case 1:
			//降序排列
			currentTv.setCompoundDrawables(null, null, mDescDrawable, null);
			currentTv.setCompoundDrawablePadding(5);
			mScrollTableTitleSortOnClickListener.changeSort(columnData.getSort(), currentSortMode);
			break;
		case 0:
			//升序排列
			currentTv.setCompoundDrawables(null, null, mAscDrawable, null);
			currentTv.setCompoundDrawablePadding(5);
			mScrollTableTitleSortOnClickListener.changeSort(columnData.getSort(), currentSortMode);
			break;
		case -1:
			//不排序
			currentTv.setCompoundDrawables(null, null, null, null);
			currentTv.setCompoundDrawablePadding(5);
			mScrollTableTitleSortOnClickListener.changeSort(columnData.getSort(), currentSortMode);
			break;
		}
		lastSortMode = currentSortMode;
		lastKey = currentKey;
	}

	/**
	 * 重置排序
	 */
	public void resetSortInfo() {
		if(this.mScrollTableMoveableTitleLl == null){
			return;
		}
		LinearLayout moveableTitleLayout = (LinearLayout)this.mScrollTableMoveableTitleLl.getChildAt(0);
		if(moveableTitleLayout == null){
			return;
		}
		int len = moveableTitleLayout.getChildCount();
		if(len <= 0){
			return;
		}
		for(int col = 0;col < len;col++){
			LinearLayout moveableTitleItemLL =	(LinearLayout) moveableTitleLayout.getChildAt(col);
			TextView moveableTitleTv =	(TextView) moveableTitleItemLL.getChildAt(0);
			if(moveableTitleTv != null){
				moveableTitleTv.setCompoundDrawables(null, null, null, null);
			}
		}
	}

	/**
	 * 重置排序状态
	 */
	public void resetSortStatus() {
		lastKey = 0;
	}

	/**
	 * 滑动表格Item单击接口
	 * @author 梁浩        2015-1-1-下午3:45:46
	 *
	 */
	public interface ScrollTableListItemOnClickListener {
		void itemOnClick(Stock stock);
	}
	
	
	public ScrollTableListItemOnClickListener getScrollTableListItemOnClickListener() {
		return mScrollTableListItemOnClickListener;
	}

	public void setScrollTableListItemOnClickListener(ScrollTableListItemOnClickListener scrollTableTitleSortOnClickListener) {
		this.mScrollTableListItemOnClickListener = scrollTableTitleSortOnClickListener;
	}

	/**
	 * 列表项点击接口
	 * @param v
	 */
	protected void scrollTableListItemOnClickListener(View v) {
		Stock stock  = (Stock) v.getTag();
		if(mScrollTableListItemOnClickListener != null){
			mScrollTableListItemOnClickListener.itemOnClick(stock);	
		}
	}

	
	/**
	 * 控制当前是否排序
	 * @param enable
	 */
	public void setEnableNoneSortting( boolean enable ){
		mEnableNoneSortting = enable;
	}
	
	public boolean getEnableNoneSortting( ){
		return mEnableNoneSortting ;
	}

}