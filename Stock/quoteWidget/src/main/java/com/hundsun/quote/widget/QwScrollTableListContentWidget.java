package com.hundsun.quote.widget;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hundsun.quote.model.CommonStockRankTool;
import com.hundsun.quote.model.Stock;
import com.hundsun.quote.tools.CommonTools;
import com.hundsun.quote.viewmodel.RankStockResult;
import com.hundsun.quote.viewmodel.RankStockViewModel;
import com.hundsun.quotewidget.R;

/**
 * [滑动内容]构建类似 ListView 的控件,支持左右、上下滑动
 * @author 梁浩        2015-2-17-上午1:09:38
  *@copyright 恒生电子股份有限公司  研发中心 移动终端产品组 授权
 */
public class QwScrollTableListContentWidget extends ScrollView {

	private Context      	mContext;
	private Resources    	mResource;
	private LinearLayout 	mScrollTableFixContentLl;
	private LinearLayout 	mScrollTableMoveableContentLl;
	public  QwScrollTableHorizontalScrollView   mMoveableContentHsv ;
	/*列表项单击接口*/
	private ScrollTableListItemOnClickListener  mScrollTableListItemOnClickListener;  

	private int             mScreenWidth    = 0;
	private int          	mItemHeight     = 23;

	public QwScrollTableListContentWidget(Context context){
		super(context);
		init(context);
	}

	public QwScrollTableListContentWidget(Context context, AttributeSet attributeSet){
		super(context, attributeSet);
		init(context);
	}

	public QwScrollTableListContentWidget(Context context, AttributeSet attributeSet, int paramInt){
		super(context, attributeSet, paramInt);
		init(context);
	}

	@SuppressWarnings("deprecation")
	protected void init(Context context){
		this.mContext = context;
		mResource     = context.getResources();
		mScreenWidth  = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		mItemHeight   = (int) mResource.getDimension(R.dimen.scroll_table_item_height);
		LayoutInflater localLayoutInflater = LayoutInflater.from(context);
		View localView = null;
		localView = localLayoutInflater.inflate(R.layout.qw_scroll_table_list_view_scrolls_content, this);
		mMoveableContentHsv = (QwScrollTableHorizontalScrollView)localView.findViewById(R.id.scroll_table_moveable_content_hsv);
		mMoveableContentHsv.setTouchAble(true);
		mMoveableContentHsv.setHorizontalScrollBarEnabled(false);
		this.mScrollTableFixContentLl      = ((LinearLayout)localView.findViewById(R.id.scroll_table_fixed_content_ll));
		this.mScrollTableMoveableContentLl = ((LinearLayout)localView.findViewById(R.id.scroll_table_moveable_content_ll));
	}

	/**
	 * 添加可移动表格内容
	 * @param moveableTitleLayout
	 * @param moveableContentLayout
	 */
	private void addScrollTableContentViews(LinearLayout moveableTitleLayout, LinearLayout moveableContentLayout){
		if (moveableTitleLayout.getChildCount() > 0){
			this.mScrollTableFixContentLl.addView(moveableTitleLayout);
		}
		if (moveableContentLayout.getChildCount() <= 0){
			return;
		}

		this.mScrollTableMoveableContentLl.addView(moveableContentLayout);
	}

	/**
	 * 清理滑动表格内容视图及数据
	 */
	public void clearScrollTableContentAllViews(){
		if (this.mScrollTableFixContentLl.getChildCount() > 0)
			this.mScrollTableFixContentLl.removeAllViews();
		if (this.mScrollTableMoveableContentLl.getChildCount() <= 0)
			return;
		this.mScrollTableMoveableContentLl.removeAllViews();
	}

	/**
	 * 构建双向滑动行情列表内容
	 * @param rankStockModels:双向滑动行情列表内容模型
	 * @param rankTableHeaders :双向滑动行情列表表头
	 */
	@SuppressWarnings("deprecation")
	public void setScrollTableContent(List<RankStockViewModel> rankStockModels,List<CommonStockRankTool> rankTableHeaders){
		LinearLayout fixContentLl;
		LinearLayout moveableContentLl;
		TextView contentTv;
		LinearLayout.LayoutParams childrenLp;
		LinearLayout.LayoutParams parentLp;
		fixContentLl = new LinearLayout(this.mContext);
		moveableContentLl = new LinearLayout(this.mContext);

		fixContentLl.setOrientation(LinearLayout.VERTICAL);
		moveableContentLl.setOrientation(LinearLayout.VERTICAL);

		parentLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		fixContentLl.setLayoutParams(parentLp);
		moveableContentLl.setLayoutParams(parentLp);

		for(int model =0;rankStockModels != null && model < rankStockModels.size();model++){
			final RankStockViewModel rankStockModel = rankStockModels.get(model);
			LinearLayout fixTitleItemLL;
			LinearLayout fixContentItemLL;
			fixTitleItemLL   = new LinearLayout(this.mContext);
			fixContentItemLL = new LinearLayout(this.mContext);
			fixTitleItemLL.setOrientation(LinearLayout.VERTICAL);
			fixTitleItemLL.setClickable(true);
			fixTitleItemLL.setBackgroundColor(mContext.getResources().getColor(R.color.solid_white));
			fixContentItemLL.setBackgroundColor(mContext.getResources().getColor(R.color.solid_white));
			fixTitleItemLL.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					scrollTableListItemOnClickListener(v);
				}
			});
			fixContentItemLL.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					scrollTableListItemOnClickListener(v);
				}
			});
			fixContentItemLL.setClickable(true);
			for(int content =0;content < rankTableHeaders.size();content++){
				CommonStockRankTool columnHeaderItem = rankTableHeaders.get(content);
				RankStockResult     rankStockResult  = rankStockModel.getStockRankFieldValue(columnHeaderItem.key);
				contentTv = new TextView(this.mContext);
				contentTv.setText(rankStockResult.getValue());
				contentTv.setSingleLine();
				if(columnHeaderItem.isFixed){
					contentTv.setPadding(10, 0, 0, 0);
					if(columnHeaderItem.key == CommonTools.KEY_SORT_STOCK_NAME){
						contentTv.setEllipsize(TextUtils.TruncateAt.END);
						contentTv.setGravity(Gravity.LEFT|Gravity.BOTTOM);
						if(columnHeaderItem.contentFontSize != 0){
							contentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,columnHeaderItem.contentFontSize);
						}else{
							contentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,mResource.getDimension(R.dimen.font_small_px));
						}
						if(columnHeaderItem.contentFontColor != 0){
							contentTv.setTextColor(columnHeaderItem.contentFontColor);
						}else{
							contentTv.setTextColor(rankStockResult.getColor());
						}
						childrenLp = new LinearLayout.LayoutParams(mScreenWidth/4 + 15, mItemHeight );
						fixTitleItemLL.addView(contentTv,childrenLp);
					}else{
						contentTv.setGravity(Gravity.LEFT|Gravity.TOP);
						if(columnHeaderItem.contentFontSize != 0){
							contentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,columnHeaderItem.contentFontSize);
						}else{
							contentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,mResource.getDimension(R.dimen.font_smallest_px));
						}
						if(columnHeaderItem.contentFontColor != 0){
							contentTv.setTextColor(columnHeaderItem.contentFontColor);
						}else{
							contentTv.setTextColor(rankStockResult.getColor());
						}
						childrenLp = new LinearLayout.LayoutParams(mScreenWidth/4 + 15, mItemHeight);
						fixTitleItemLL.addView(contentTv,childrenLp);
					}
				}else{
					contentTv.setGravity(Gravity.CENTER);
					if(columnHeaderItem.contentFontSize != 0){
						contentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,columnHeaderItem.contentFontSize);
					}else{
						contentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,mResource.getDimension(R.dimen.font_small_px));
					}
					if(columnHeaderItem.contentFontColor != 0){
						contentTv.setTextColor(columnHeaderItem.contentFontColor);
					}else{
						contentTv.setTextColor(rankStockResult.getColor());
					}
					childrenLp = new LinearLayout.LayoutParams(mScreenWidth/4,2*mItemHeight);
					fixContentItemLL.addView(contentTv,childrenLp);
				}
			}
			parentLp.bottomMargin = 1;

			fixTitleItemLL.setTag(rankStockModel.getStock());
			fixContentItemLL.setTag(rankStockModel.getStock());

			fixContentLl.addView(fixTitleItemLL,parentLp);
			moveableContentLl.addView(fixContentItemLL,parentLp);
		}
		/*String className = this.mContext.getClass().getName();
		if(!TextUtils.isEmpty(className) && "com.hundsun.qii.activity.QiiQuoteComplexRankStockActivity".equals(className)){
			TextView titleEmptyTV = new TextView(this.mContext);
			titleEmptyTV.setBackgroundColor(Color.WHITE);
			LinearLayout.LayoutParams  params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,2*mItemHeight);
			fixContentLl.addView(titleEmptyTV,params);
			TextView contentEmptyTV = new TextView(this.mContext);
			contentEmptyTV.setBackgroundColor(Color.WHITE);
			contentEmptyTV.setId(R.id.scroll_table_down_empty_id);
			contentEmptyTV.setText("上拉翻页");
			contentEmptyTV.setPadding(80, 0, 0, 0);
			contentEmptyTV.setGravity(Gravity.LEFT|Gravity.TOP);
			LinearLayout.LayoutParams  params2 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,2*mItemHeight);
			moveableContentLl.addView(contentEmptyTV,params2);
		}*/
		clearScrollTableContentAllViews();
		addScrollTableContentViews(fixContentLl, moveableContentLl);
	}

	public int getScreenWidth(){
		return this.mScreenWidth;
	}

	public void setScreenWidth(int screenWidth){
		this.mScreenWidth = screenWidth;
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

	public void setEmptyView(View emptyView) {
		// TODO Auto-generated method stub

	}

	public QwScrollTableHorizontalScrollView getQwScrollTableHorizontalScrollView(){
		return mMoveableContentHsv;
	}

	/**
	 * 滑动表格Item单击接口
	 * @author 梁浩        2015-1-1-下午3:45:46
	 *
	 */
	public interface ScrollTableListItemOnClickListener {
		void itemOnClick(Stock stock);
	}

	/**
	 * 获取 -滑动表格Item单击接口实例
	 * @return
	 */
	public ScrollTableListItemOnClickListener getScrollTableListItemOnClickListener() {
		return mScrollTableListItemOnClickListener;
	}

	/**
	 *  设置 -滑动表格Item单击接口实例
	 * @param scrollTableTitleSortOnClickListener
	 */
	public void setScrollTableListItemOnClickListener(ScrollTableListItemOnClickListener scrollTableTitleSortOnClickListener) {
		this.mScrollTableListItemOnClickListener = scrollTableTitleSortOnClickListener;
	}

	/**
	 * 是否滚动到边界接口
	 * @author 梁浩        2015-2-27-下午5:28:47
	 *
	 */
	public interface OnScrollToBoundaryListener{
		void isScroolToTopBoundary();
		void isScroolBottomTopBoundary();
		void isNotScroolToBoundary();
	}

	private OnScrollToBoundaryListener onScrollToBoundaryListener;


	public OnScrollToBoundaryListener getOnScrollToBoundaryListener() {
		return onScrollToBoundaryListener;
	}

	public void setOnScrollToBoundaryListener(OnScrollToBoundaryListener onScrollToBoundaryListener) {
		this.onScrollToBoundaryListener = onScrollToBoundaryListener;
	}

	@Override
	public void onScrollChanged(int l, int t, int oldl, int oldt) {
		if(onScrollToBoundaryListener != null){
			if(t + getHeight() >=  computeVerticalScrollRange()){
				onScrollToBoundaryListener.isScroolBottomTopBoundary();
			}else if(this.getScrollY() == 0){
				onScrollToBoundaryListener.isScroolToTopBoundary();
			}else{
				onScrollToBoundaryListener.isNotScroolToBoundary();
			}
		}
	}
}