package com.hundsun.quote.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.hundsun.quote.model.Stock;
import com.hundsun.quote.model.kline.KlineKDJ;
import com.hundsun.quote.model.kline.KlineMACD;
import com.hundsun.quote.model.kline.KlinePSY;
import com.hundsun.quote.model.kline.KlineRSI;
import com.hundsun.quote.tools.ColorUtils;
import com.hundsun.quote.tools.FormatUtils;
import com.hundsun.quote.viewmodel.KlineViewModel;
import com.hundsun.quotewidget.R;

public class QwKlineViewLandscape extends View{

	public static final int KLINE_MSG = 9999;		//k线消息回调的类型
	/**字符串定义 START*/
	private static final String OPEN_PRICE = "openPrice";
	private static final String OPEN_PRICE_COLOR = "openPriceColor";
	
	private static final String MAX_PRICE = "maxPrice";
	private static final String MAX_PRICE_COLOR= "maxPriceColor";
	
	private static final String LAST_PRICE = "lastPrice";
	private static final String LAST_PRICE_COLOR= "lastPriceColor";
	
	private static final String RANGE = "range";
	private static final String RANGE_COLOR = "rangeColor";
	
	private static final String MONEY = "money";
	private static final String MONEY_COLOR = "moneyColor";
	
	private static final String MIN_PRICE = "minPrice";
	private static final String MIN_PRICE_COLOR = "minPriceColor";
	
	private static final String AMOUNT = "amount";
	private static final String AMOUNT_COLIR = "amountColor";
	//private static final String klineDataKey[] = { "时", "开", "高", "低", "收", "幅", "量", "额" }; //K线数据框的数据名称
	/**字符串定义 END*/
	
	public static final Typeface TEXT_FONT = Typeface.create(Typeface.DEFAULT,Typeface.BOLD); //使用的字体
	public static final Typeface REGULAR_TEXT_FONT = Typeface.create(
			Typeface.DEFAULT, Typeface.NORMAL);		//A text font for regular text, like the chart labels.
	private static final int CHAR_WIDTH = 7; 		//定义字体宽度
	public static boolean isDrawIn = false;		
	private static final int eMACD = 0;
	private static final int eRSI = 1;
	private static final int eKDJ = 2;
	private static final int ePSY = 3;
	private static final int eWR = 4;
	private KlineViewModel mViewModel;		//K线数据包
	private Handler handler;						//设置当前K线数值的回调句柄
//	public DecimalFormat decimalFormat;             //普通股票小数点精确
//	public DecimalFormat decimalFormatZs;			//指数小数点精确
	
	private int zhibiaoType;						//指标的种类 (macd:0,rsi:1,wr:2,kdj:3,psy:4,boll:5)
	Paint paint = new Paint();  					//The user interface thread handler.
	private Handler mHandler = new Handler();

	private Stock mStock;							//当前股票信息
	private LinearLayout mFunctionButtons; 
	private LinearLayout mStockTable;
	int klineBarWidth = 5;                         	//k线蜡烛的宽度(像素)
	int startIndex = -1;                          	//k线数据起始位置,可见区域内第一条数据在dataPack中的位置 
	public int focusIndex; 							//焦点线所在的位置,0标示不显示该线
	private float focusLineCoordinate = 0; 			//内部变量，表示K线的focus线纵坐标，以便绘制数据提示框
	private int klineCountPerScreen; 				//满屏幕可以显示的k线条数,有屏幕可用宽带/每条k线的宽度得到该值
	private float klinex;							// K线图的范围，以便触摸屏的实现
	private float klineWidth;						// K线图的范围，以便触摸屏的实现
	private float zhibiaox;							//指标图的范围x，以便触摸屏的实现
	private float zhibiaoy;							//指标图的范围y，以便触摸屏的实现

	private int mTextSize = 10;						//绘制文本的字体大小
	private float width;							//绘制宽度
	//private float height;							//绘制高度
	private int fontHeight;							//字体高度
	private float charWidth;					    //字符宽度，默认值CHAR_WIDTH
	private int klineAreaHeight;	              
	private int M5Height;
	private int amountHeight;
	private int zhibiaoHeight; 					//指标图的范围height，以便触摸屏的实现
	private int spaceKlineToAmount;
	private int spaceAmountToZhibiao;
	private Bitmap redPointBitmap;			        //焦点线上红点的图片资源	
	private Context mContext = null;						//当前Context内容
	public QwKlineViewLandscape(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public QwKlineViewLandscape(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	/**
	 * 初始化控件
	 */
	private void init() {
		mGestureDetector = new GestureDetector(mContext.getApplicationContext(),mGestureListener);
		mTextSize = getResources().getDimensionPixelSize(R.dimen.kline_text_size);
		Paint paint = new Paint();
		//mTextSize = getResources().getDimensionPixelSize(R.dimen.fenshi_price_text_size);
		paint.setTextSize(mTextSize);
		paint.setTypeface(REGULAR_TEXT_FONT);
		paint.measureText("test");
		Rect bounds = new Rect();
		paint.getTextBounds("test", 0, 4, bounds);
		fontHeight = bounds.height();
		charWidth = CHAR_WIDTH;
		
		/**设置 字体， k线区域高度*/		
		FontMetricsInt fmi = new FontMetricsInt();
		this.fontHeight = mContext.getResources().getDimensionPixelSize(R.dimen.kline_text_size_s);//paint.getFontMetricsInt(fmi);
		this.charWidth = paint.measureText("0");		
		M5Height = mContext.getResources().getDimensionPixelSize(R.dimen.kline_M5height);
		spaceKlineToAmount = mContext.getResources().getDimensionPixelSize(R.dimen.kline_spacetoamount_height);
		spaceAmountToZhibiao = mContext.getResources().getDimensionPixelSize(R.dimen.kline_spacetozhibiao_height);
	}
	/**
	 * 设置数据包
	 * @param data：数据包  ; handler 
	 */
	public void setData(KlineViewModel klinePacket, Handler handler) {
		if(null == klinePacket)
		{
			return;
		}
		startIndex = -1;
		mViewModel = klinePacket;
		this.handler = handler;
	}
	/**
	 * 设置当前股票信息
	 * @param stock：股票信息
	 */	
	public void setStock(Stock stock) {
		mStock = stock;
		
		if (null == stock)
		{
			return;
		}
		//获取小数点精度
//		int nDecimal = QuoteSimpleInitPacket.getDecimalPointSize(stock.getCodeInfo());
		
//		if(nDecimal < DECIMAL_POINT.length)
//		{
//			decimalFormat =  new DecimalFormat(DECIMAL_POINT[nDecimal]);
//		}

//		if (QuoteConstants.KIND_INDEX == stock. getKind())
//		{
//			decimalFormatZs = new DecimalFormat(DECIMAL_POINT[0]);
//		}
//		else
//		{
//			decimalFormatZs = decimalFormat;
//		}
	}

	int mHsTop;
	int mHsLeft;
	int mHsWidth;
	int mHsHeight;
	int count = 0;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (null == mViewModel)
		{
			return;
		}
		int top = 0;
		int left = 0;
		int width = this.getWidth();
		int height = this.getHeight();

		this.width = width;
		
		/**设置字体*/
		paint.setTextSize(mTextSize);
		paint.setTypeface(REGULAR_TEXT_FONT);
		
		/**绘制背景*/
		paint.setAntiAlias(true);
//		drawBackground(canvas, paint, left, top, width, height);

		zhibiaoHeight = (getHeight() - M5Height - spaceKlineToAmount - spaceAmountToZhibiao - fontHeight)/5;
		amountHeight = zhibiaoHeight;
		klineAreaHeight = getHeight() - M5Height - spaceKlineToAmount - spaceAmountToZhibiao - fontHeight - zhibiaoHeight - amountHeight;
		
		//绘制MA5 MA10 MA30
		drawKlineTitle(left, top + (M5Height - fontHeight)/2, width, fontHeight, canvas, paint);
		
		drawKlineBar(left, top + M5Height, width, klineAreaHeight, canvas,
				paint);
		
		drawAmountBar(left, top + M5Height + klineAreaHeight + spaceKlineToAmount, width, amountHeight, canvas,
				paint);
		
		zhibiaox = left;
		zhibiaoy = top +  M5Height + klineAreaHeight + spaceKlineToAmount + amountHeight +spaceAmountToZhibiao;
		drawZhibiao(left, 
					top + M5Height+ klineAreaHeight + spaceKlineToAmount + amountHeight,
					width, 
					zhibiaoHeight + spaceAmountToZhibiao, 
					canvas,
					paint);
		
		
		drawDate(left, 
				top + M5Height + klineAreaHeight + spaceKlineToAmount + amountHeight + spaceAmountToZhibiao + zhibiaoHeight, 
				width, fontHeight, canvas,
				paint);
		
		if (focusIndex != 0 && mViewModel.getDataSize() > 0)
		{
			updateKlineData();
		}
	}
	
	/**
	 * 设置当前K线位置的数值
	 */
	private void updateKlineData() {
		double yesterdayClosePrice = 0;
		
		mViewModel.setIndex(startIndex + focusIndex - 1);		
		yesterdayClosePrice = (startIndex + focusIndex - 1 <= 0)
								?mViewModel.getClosePrice(0):mViewModel.getClosePrice(startIndex + focusIndex - 1-1);
		
		Message msg = new Message();
		msg.what = KLINE_MSG;
		Bundle bundle = new Bundle();
		//开盘价 
		bundle.putString(OPEN_PRICE, FormatUtils.formatPrice( mStock , mViewModel.getOpenPrice()) );
		bundle.putInt(OPEN_PRICE_COLOR, ColorUtils.getColor(mViewModel.getOpenPrice(), yesterdayClosePrice));
		//最高价
		bundle.putString(MAX_PRICE, FormatUtils.formatPrice( mStock , mViewModel.getMaxPrice()) );
		bundle.putInt(MAX_PRICE_COLOR, ColorUtils.getColor(mViewModel.getMaxPrice(), yesterdayClosePrice));
		//涨跌幅
		if(startIndex + focusIndex - 1 <= 0)
		{
			bundle.putString(RANGE, "--%");
			bundle.putInt(RANGE_COLOR, ColorUtils.getColor(0, yesterdayClosePrice));
		}
		else
		{
			bundle.putString(RANGE, mViewModel.getUpDownPercentStr()+"%");
			bundle.putInt(RANGE_COLOR, ColorUtils.getColor(mViewModel.getClosePrice(), yesterdayClosePrice));
		}
		//总成交金额
//		if(QuoteConstants.MARKET_FUTURES == mStock.getMarket())
//		{
//			bundle.putString(MONEY, "--" );
//		}
//		else
		{			
			String moneyStr = FormatUtils.numberToStringWithUnit( String.valueOf( mViewModel.getTotalDealAmountOfMoney() ), 2);
			bundle.putString(MONEY,  moneyStr);
		}
		bundle.putInt(MONEY_COLOR, ColorUtils.COLOR_YELLOW);
		//最低价 
		bundle.putString(MIN_PRICE, FormatUtils.formatPrice( mStock , mViewModel.getMinPrice()) );
		bundle.putInt(MIN_PRICE_COLOR, ColorUtils.getColor(mViewModel.getMinPrice(), yesterdayClosePrice));
		//总成交量,单位转换，保留2位小数点
		bundle.putString(AMOUNT, FormatUtils.numberToStringWithUnit(String.valueOf( mViewModel.getTotalDealAmount() ), 2));
		bundle.putInt(AMOUNT_COLIR, ColorUtils.COLOR_YELLOW);
		
		
		//最低价 
		bundle.putString(LAST_PRICE, FormatUtils.formatPrice( mStock , mViewModel.getClosePrice()) );
		bundle.putInt(LAST_PRICE_COLOR, ColorUtils.getColor(mViewModel.getClosePrice(), yesterdayClosePrice));
		
		msg.setData(bundle);
		
		if(handler!=null)
			handler.sendMessage(msg);
	}

	/**
	 * 绘制头部的焦点线的MA5、MA10、MA30内容
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param canvas
	 * @param paint
	 */
	private void drawKlineTitle(int x, int y, int width, int height,
			Canvas canvas, Paint paint) {
		double ma5 = 0;
		double ma10 = 0;
		double ma30 = 0;
		
		int numWidth = 0;
		
		if (!isDrawIn) 
		{
			numWidth = (int) (paint.measureText("00000.0") + 2);
			x += numWidth;
			width -= numWidth;
		}
		
		//获取焦点线位置
		int tempFocusIndex = getFocusIndex();
		//设置焦点位置
		mViewModel.setIndex(tempFocusIndex);
		//获取ma5 ma10 ma30值
		if(mViewModel.getDataSize() > 0)
		{
			ma5 = mViewModel.getMA( 5 , tempFocusIndex);
			ma10 = mViewModel.getMA( 10 , tempFocusIndex);
			ma30 = mViewModel.getMA( 30 , tempFocusIndex);
		}
		
		//组装ma 字符串
		String ma5Str = "MA5" + " " + FormatUtils.formatPrice( mStock ,ma5);
		String ma10Str = "  MA10" + "  " + FormatUtils.formatPrice( mStock ,ma10);
		String ma30Str = "  MA30" + "  " + FormatUtils.formatPrice( mStock ,ma30);

		//计算宽度
		int tempWidth1 = (int) paint.measureText(ma5Str);
		int tempWidth2 = (int) paint.measureText(ma10Str);
		
		y += height; 
		paint.setColor(ColorUtils.MA_COLOR[0]);
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.LEFT);
		
		
		//绘制
		canvas.drawText( ma5Str, x, y, paint);
		paint.setColor(ColorUtils.MA_COLOR[1]);
		canvas.drawText(ma10Str, x + tempWidth1, y, paint);
		paint.setColor(ColorUtils.MA_COLOR[2]);
		canvas.drawText( ma30Str , x + tempWidth2 + tempWidth1, y, paint);
	}

	private Bitmap getRedpointBitmap() {
		if (null == redPointBitmap)
		{
			redPointBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.redpoint);
		}
		return redPointBitmap;
	}
	private void drawKlineBar(int x, int y, int width, int height,
			Canvas canvas, Paint paint) {
		
		int numWidth = 0;
		
		if (!isDrawIn) 
		{
			numWidth = (int) (paint.measureText("00000.0") + 2);
			x += numWidth;
			width -= numWidth;
		}
		
		klinex = x;
		klineWidth = width;
		if (klineBarWidth > width / 15) 
		{
			klineBarWidth -= 2;
		}
		klineCountPerScreen = width / (klineBarWidth + 1);
		if (-1 == startIndex) 
		{
			if (mViewModel.getDataSize() - klineCountPerScreen > 0) 
			{
				startIndex = mViewModel.getDataSize() - klineCountPerScreen;
			}
			else
			{
				startIndex = 0;
			}
		}
		paint.setColor(ColorUtils.BORDER_COLOR);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(0);
		paint.setAntiAlias(false);
		//绘制矩形及3条横线
		canvas.drawRect(x, y, x + width -1, y + height, paint);
		for (int i = 1; i < 4; i++)
		{
			canvas.drawLine(x, y + height * i / 4, x + width, y + height * i/ 4, paint);
		}
		
		if(0 == mViewModel.getDataSize())
		{
			return;
		}
		//获取当前K线显示的区域
		int tempEndIndex = getEndIndex();
		
		
		//计算K线区域中的最大最小值
		double tempTopValue = 0;//临时K线范围内的最高价
		double tempBottomValue = 0;//临时K线范围内的最低价
		//获取屏幕显示内的最高价和最低价
		tempTopValue = (float) mViewModel.getTopPriceDuringPointedDays(startIndex, tempEndIndex);
		tempBottomValue = (float) mViewModel.getBottomPriceDuringPointedDays(startIndex, tempEndIndex);
		//获取ma5 ma10 ma30最大值
		double tempTopMA5 = mViewModel.getMATopValue(5, startIndex, tempEndIndex);
		double tempTopMA10 = mViewModel.getMATopValue(10, startIndex, tempEndIndex);
		double tempTopMA30 = mViewModel.getMATopValue(30, startIndex, tempEndIndex);
		
		tempTopValue = (tempTopMA5 > tempTopValue)?tempTopMA5:tempTopValue;
		tempTopValue = (tempTopMA10 > tempTopValue)?tempTopMA10:tempTopValue;
		tempTopValue = (tempTopMA30 > tempTopValue)?tempTopMA30:tempTopValue;
	
		//获取ma5 ma10 ma30最小值
		double tempBottomMA5 = mViewModel.getMABottomValue(5, startIndex, tempEndIndex);
		double tempBottomMA10 = mViewModel.getMABottomValue(10, startIndex, tempEndIndex);
		double tempBottomMA30 = mViewModel.getMABottomValue(30, startIndex, tempEndIndex);
		if(tempBottomMA5 < tempBottomValue && tempBottomMA5 > 0)
		{
			tempBottomValue = tempBottomMA5;
		}
		if(tempBottomMA10 < tempBottomValue && tempBottomMA10 > 0)
		{
			tempBottomValue = tempBottomMA10;
		}
		if(tempBottomMA30 < tempBottomValue && tempBottomMA30 > 0)
		{
			tempBottomValue = tempBottomMA30;
		}
		//绘制蜡烛
		double openPrice = 0;
		double closePrice = 0;
		double maxPrice = 0;
		double minPrice = 0;
		float klineBarMiddlex = 0;
		float tempx;
		float tempy;
		float klineBarHeight = 0;
		float interval = (float) (tempTopValue-tempBottomValue);
		for (int i = startIndex; i < klineCountPerScreen + startIndex
				&& i < mViewModel.getDataSize(); i++) 
		{
			mViewModel.setIndex(i);//设置K线焦点位置
			
			klineBarMiddlex = x + (klineBarWidth + 1) * (i - startIndex) + (klineBarWidth + 1) / 2;
			openPrice = mViewModel.getOpenPrice();
			closePrice = mViewModel.getClosePrice();
			maxPrice = mViewModel.getMaxPrice();
			minPrice = mViewModel.getMinPrice();
			paint.setStyle(Style.STROKE);
			if (closePrice > openPrice) 
			{
				paint.setColor(ColorUtils.UPDOWN_COLOR[0]);
				//画蜡烛上下的线
				/*canvas.drawLine(klineBarMiddlex, 
								(int) (y + height * (tempTopValue - maxPrice) / (tempTopValue-tempBottomValue)),
								klineBarMiddlex, 
								(int) (y + height * (tempTopValue - minPrice) / (tempTopValue-tempBottomValue)),
								paint);*/
				
				klineBarHeight = (int) (height * (closePrice - openPrice) / interval);

				tempy = (int) (y + height * (tempTopValue - closePrice) / interval);
				
				canvas.drawLine(klineBarMiddlex, 
						(int) (y + height * (tempTopValue - maxPrice) / interval),
						klineBarMiddlex, 
						tempy,
						paint);
				
				canvas.drawLine(klineBarMiddlex, 
						tempy + klineBarHeight,
						klineBarMiddlex, 
						(int) (y + height * (tempTopValue - minPrice) / interval),
						paint);
				
				//画蜡烛
				if (klineBarWidth > 1)
				{
					tempx = klineBarMiddlex - (klineBarWidth - 1) / 2;
					tempy = (int) (y + height * (tempTopValue - closePrice)
							/ interval);
					paint.setStyle(Style.FILL);
					canvas.drawRect(tempx,
									tempy,
									tempx + klineBarWidth,
									tempy + (int) (height * (closePrice - openPrice) / interval),
									paint);
					
					
				/*	paint.setColor(ColorUtils.UPDOWN_COLOR[0]);
					//画蜡烛上线
					canvas.drawLine(klineBarMiddlex, 
									(int) (y + height * (tempTopValue - maxPrice) / (tempTopValue-tempBottomValue)),
									klineBarMiddlex, 
									tempy,
									paint);
					//画蜡烛下线
					canvas.drawLine(klineBarMiddlex, 
									tempy + (int) (height * (closePrice - openPrice) / (tempTopValue-tempBottomValue)),
									klineBarMiddlex, 
									(int) (y + height * (tempTopValue - minPrice) / (tempTopValue-tempBottomValue)),
									paint);*/
				}
				
				
				//颜色，空心
				/*paint.setColor(ColorUtils.BORDER_COLOR);
				paint.setStyle(Style.FILL);
				tempx = klineBarMiddlex - (klineBarWidth - 1) / 2 + 1;
				tempy = (int) (y + height * (tempTopValue - closePrice)
						/ (tempTopValue-tempBottomValue) + 1);
				canvas.drawRect(tempx, 
								tempy, 
								tempx + klineBarWidth - 2,
								tempy + (int) (height * (closePrice - openPrice)/ (tempTopValue-tempBottomValue) - 1), 
								paint);*/
				paint.setColor(ColorUtils.UPDOWN_COLOR[0]);
			} 
			else if (closePrice == openPrice)  //开盘等于昨收
			{
				if (0 == i) 
				{
					paint.setColor(ColorUtils.UPDOWN_COLOR[1]);
				}
				else if (closePrice < mViewModel.getClosePrice(i - 1)) 
				{
					paint.setColor(ColorUtils.UPDOWN_COLOR[1]);
				}
				else
				{
					paint.setColor(ColorUtils.UPDOWN_COLOR[0]);
				}
				canvas.drawLine(klineBarMiddlex, 
								(int) (y + height * (tempTopValue - maxPrice) / interval),
								klineBarMiddlex, 
								(int) (y + height * (tempTopValue - minPrice) / interval),
								paint);
				canvas.drawLine(klineBarMiddlex - (klineBarWidth - 1) / 2,
								(int) (y + height * (tempTopValue - openPrice)/ interval), 
								klineBarMiddlex + (klineBarWidth - 1) / 2, 
								(int) (y + height * (tempTopValue - openPrice) / interval),
								paint);
			}
			else
			{
				paint.setColor(ColorUtils.UPDOWN_COLOR[1]);
				canvas.drawLine(klineBarMiddlex, 
								(int) (y + height * (tempTopValue - maxPrice) / interval),
								klineBarMiddlex,
								(int) (y + height * (tempTopValue - minPrice) / interval),
								paint);
				if (klineBarWidth > 1) 
				{
					paint.setStyle(Style.FILL);
					tempx = klineBarMiddlex - (klineBarWidth - 1) / 2;
					tempy = (int) (y + height * (tempTopValue - openPrice)
							/ interval);
					canvas.drawRect(tempx,
									tempy,
									tempx + klineBarWidth,
									tempy + (int) (height * (openPrice - closePrice) / interval),
									paint);
				}
			}
		}

		//绘制m5 m10 m30三条线
		double ma5 = 0;
		double ma10 = 0;
		double ma30 = 0;
		double lma5 = 0;
		double lma10 = 0;
		double lma30 = 0;
		for (int i = startIndex; i < klineCountPerScreen + startIndex
				&& i < mViewModel.getDataSize(); i++) 
		{
			mViewModel.setIndex(i);
			klineBarMiddlex = x + (klineBarWidth + 1) * (i - startIndex)
					+ (klineBarWidth + 1) / 2;
			ma5 = mViewModel.getMA(5 , i);
			ma10 = mViewModel.getMA( 10 , i);
			ma30 = mViewModel.getMA( 30 , i);
			if (i == startIndex) 
			{
				lma5 = ma5;
				lma10 = ma10;
				lma30 = ma30;
				continue;
			}
			if (0 != lma5 && 0 != ma5)
			{
				paint.setColor(ColorUtils.MA_COLOR[0]);
				canvas.drawLine(klineBarMiddlex - klineBarWidth - 1,
								(int) (y + height * (tempTopValue - lma5) / interval),
								klineBarMiddlex, 
								(int) (y + height * (tempTopValue - ma5) / interval),
								paint);
			}
			if (0 != lma10 && 0 != ma10) 
			{
				paint.setColor(ColorUtils.MA_COLOR[1]);
				canvas.drawLine(klineBarMiddlex - klineBarWidth - 1,
								(int) (y + height * (tempTopValue - lma10)/ interval),
								klineBarMiddlex,
								(int) (y + height * (tempTopValue - ma10) / interval),
								paint);
			}
			if (0 != lma30 && 0 != ma30)
			{
				paint.setColor(ColorUtils.MA_COLOR[2]);
				canvas.drawLine(klineBarMiddlex - klineBarWidth - 1,
								(int) (y + height * (tempTopValue - lma30)/ interval),
								klineBarMiddlex,
								(int) (y + height * (tempTopValue - ma30) / interval),
								paint);
			}
			lma5 = ma5;
			lma10 = ma10;
			lma30 = ma30;
		}
		paint.setColor(ColorUtils.LEFT_DATA_COLOR);
		paint.setAntiAlias(true);
		//左侧最高，最低，平均值
		if (isDrawIn) 
		{
			paint.setTextAlign(Align.LEFT);
			canvas.drawText(FormatUtils.formatPrice( mStock , tempTopValue),
							x,
							y + fontHeight, 
							paint);
		} 
		else
		{
			paint.setTextAlign(Align.RIGHT);
			canvas.drawText(FormatUtils.formatPrice( mStock , tempTopValue),
							x - 1, 
							y + fontHeight, 
							paint);
		}

		//判断平均值是否可以画的下
		if(height >= (fontHeight * 3))
		{
			if (isDrawIn)
			{
				canvas.drawText(FormatUtils.formatPrice( mStock , (interval / 2 + tempBottomValue)),		
								x, 
								y + height / 2 + fontHeight/2, 
								paint);
			}
			else
			{
				canvas.drawText(FormatUtils.formatPrice( mStock ,(interval / 2 + tempBottomValue) / 1),
								x - 2, 
								y + height / 2 + fontHeight/2, 
								paint);
			}
		}
		
		if (isDrawIn)
		{
			canvas.drawText(FormatUtils.formatPrice( mStock ,tempBottomValue / 1), 
							x,
							y + height,
							paint);
		}

		else
		{
			canvas.drawText(FormatUtils.formatPrice( mStock ,tempBottomValue / 1),
							x - 2, 
							y + height,
							paint);
		}
			
		//绘制焦点十字线
		if (this.focusIndex > 0) 
		{
			paint.setColor(ColorUtils.FOCUS_LINE_COLOR);
			int fiy = x + (focusIndex - 1) * (klineBarWidth + 1)
					+ (klineBarWidth + 1) / 2;
			focusLineCoordinate = fiy;
			canvas.drawLine(fiy, y + 1, fiy, y + height - 2, paint);
			closePrice = mViewModel.getClosePrice(startIndex + focusIndex - 1);// startIndex+focusIndex-1
			canvas.drawLine(x + 1, 
							(int) (y + height * (tempTopValue - closePrice) / interval),
							x + width - 2, 
							(int) (y + height * (tempTopValue - closePrice) / interval),
							paint);
			int bmpY = (int) (y + height * (tempTopValue - closePrice) / interval);
			redPointBitmap = getRedpointBitmap();
			canvas.drawBitmap(redPointBitmap, 
							focusLineCoordinate - redPointBitmap.getWidth() / 2,
							bmpY - redPointBitmap.getHeight() / 2, 
							paint);
		}
		
		
	}

	/**
	 * 绘制当前K线区域内的成交量
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param canvas
	 * @param paint
	 */
	private void drawAmountBar(int x, int y, int width, int height,
			Canvas canvas, Paint paint) {
		if (!isDrawIn) 
		{
			int numWidth = (int) (paint.measureText("00000.0") + 2);
			x += numWidth;
			width -= numWidth;
		}
		//绘制背景边框和线
		paint.setColor(ColorUtils.BORDER_COLOR);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(0);
		paint.setAntiAlias(false);
		canvas.drawRect(x, y, x + width-1, y + height, paint);
		canvas.drawLine(x, y + height / 2, x + width-1, y + height / 2, paint);		
		if(0 == mViewModel.getDataSize())
		{
			return;
		}
		//获取当前K线显示的区域
		int tempEndIndex = getEndIndex();
		//获取屏幕区域内对应时间的最高，最低成交量
		double amountTopValue = mViewModel.getTopDealAmountDuringPointedDays(startIndex, tempEndIndex);
		double amountBottomValue = mViewModel.getBottomDealAmountDuringPointedDays(startIndex, tempEndIndex);

		int klineBarMiddlex = 0;
		long ta = 0;
		double openPrice = 0;
		double closePrice = 0;
		int PADDING_HEIGHT = 1;// 矩形框高度+1，避免出现高度为0的矩形。
		long tempx = 0;
		long tempy = 0;
		for (int i = startIndex; i < klineCountPerScreen + startIndex
				&& i < mViewModel.getDataSize(); i++) 
		{
			mViewModel.setIndex(i);
			klineBarMiddlex = x + (klineBarWidth + 1) * (i - startIndex)
								+ (klineBarWidth + 1) / 2;
			ta = mViewModel.getTotalDealAmount();
			if(ta<=0)
				continue;
			openPrice = mViewModel.getOpenPrice();
			closePrice = mViewModel.getClosePrice();
			paint.setStyle(Style.STROKE);
			//计算高度
			int tvdiff = (int) (height * (amountTopValue - ta) / (amountTopValue-amountBottomValue))
								- PADDING_HEIGHT;
			//开盘价大于昨收价，红色
			if (closePrice > openPrice) 
			{
				paint.setColor(ColorUtils.UPDOWN_COLOR[0]);
				paint.setStyle(Style.STROKE);
				tempx = klineBarMiddlex - (klineBarWidth - 1) / 2;
				tempy = y + tvdiff;
				canvas.drawRect(tempx, 
								tempy, 
								tempx + klineBarWidth - 1, 
								tempy + height - tvdiff, 
								paint);
			}
			else if (closePrice == openPrice) //开盘价等于昨收价
			{
				if (0 == i) 
				{
					paint.setColor(ColorUtils.UPDOWN_COLOR[1]); 
					paint.setStyle(Style.FILL);
					tempx = klineBarMiddlex - (klineBarWidth - 1) / 2;
					tempy = y + tvdiff;
					canvas.drawRect(tempx, tempy, tempx + klineBarWidth, tempy
							+ height - tvdiff, paint);
				} 
				else if (closePrice < mViewModel.getClosePrice(i - 1) )  //跟前一个昨收比较，小则绿色
				{
					paint.setColor(ColorUtils.UPDOWN_COLOR[1]);
					paint.setStyle(Style.FILL);
					tempx = klineBarMiddlex - (klineBarWidth - 1) / 2;
					tempy = y + tvdiff;
					canvas.drawRect(tempx,
									tempy, 
									tempx + klineBarWidth, 
									tempy + height - tvdiff, 
									paint);
				} 
				else
				{
					paint.setColor(ColorUtils.UPDOWN_COLOR[0]);
					paint.setStyle(Style.STROKE);
					tempx = klineBarMiddlex - (klineBarWidth - 1) / 2;
					tempy = y + tvdiff;
					canvas.drawRect(tempx, 
									tempy, 
									tempx + klineBarWidth - 1,
									tempy + height - tvdiff, 
									paint);
				}
			} 
			else 
			{
				paint.setColor(ColorUtils.UPDOWN_COLOR[1]);
				paint.setStyle(Style.FILL);
				tempx = klineBarMiddlex - (klineBarWidth - 1) / 2;
				tempy = y + tvdiff;
				canvas.drawRect(tempx, 
								tempy,
								tempx + klineBarWidth, 
								tempy + height - tvdiff, 
								paint);
			}
		}

		// 左侧最高最低成交量值
		paint.setColor(ColorUtils.AMOUNT_COLOR);
		paint.setAntiAlias(true);
		if (isDrawIn) 
		{
			paint.setTextAlign(Align.LEFT);
			canvas.drawText(FormatUtils.formatBigNumber(  amountTopValue ), x, y + fontHeight, paint);
		}
		else
		{
			paint.setTextAlign(Align.RIGHT);
			canvas.drawText(FormatUtils.formatBigNumber(  amountTopValue ), x - 2, y + fontHeight,paint);
		}
		if (isDrawIn)
		{
			canvas.drawText(FormatUtils.formatBigNumber( amountBottomValue ), x, y
					+ height, paint);
		}
		else
		{
			canvas.drawText(FormatUtils.formatBigNumber( amountBottomValue ), x - 2, y
					+ height, paint);
		}
		//绘制焦点线
		if (0 != focusIndex) 
		{
			paint.setColor(ColorUtils.FOCUS_LINE_COLOR);
			int fiy = x + (focusIndex - 1) * (klineBarWidth + 1)
					+ (klineBarWidth + 1) / 2;
			canvas.drawLine(fiy, y + 1, fiy, y + height - 1, paint);
		}
	}
	/**
	 * 获取当前焦点线位置
	 * @return iFocusIndex: 焦点线位置
	 */
	private int getFocusIndex(){
		int iFocusIndex = 0;
		
		iFocusIndex =(0 == focusIndex)?(mViewModel.getDataSize() - 1):(focusIndex + startIndex - 1);
		return iFocusIndex;
		
	}
	/**
	 * 获取当前K线显示的区域
	 * @return iEndIndex: 当前K线显示的区域
	 */
	private int getEndIndex(){
		
		int iEndIndex = startIndex+klineCountPerScreen;
		
		if(iEndIndex > mViewModel.getDataSize()-1)
		{
			iEndIndex = mViewModel.getDataSize()-1;
		}
		
		return iEndIndex;
	}
	/**
	 * 绘制MACD技术指标
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param canvas
	 * @param paint
	 */
	private void drawMacd(int x, int y, int width, int height, Canvas canvas,
			Paint paint) {
		
		//获取当前焦点线的位置
		int tempFocusIndex = getFocusIndex();
		//绘制当前点的macd指标数值
		mViewModel.setIndex(tempFocusIndex);
		//绘制当前焦点macd diff dea值
		paint.setColor(ColorUtils.CHAR_COLOR);
		paint.setTextAlign(Align.LEFT);
		
		KlineMACD klineMacd = mViewModel.getMACD();
		
		int originY = y;
		y += (spaceAmountToZhibiao -fontHeight)/2;
		canvas.drawText("MACD " + FormatUtils.format( 3 , klineMacd.getMACD(0)), x, y + fontHeight, paint);
		paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
		canvas.drawText("DIFF " + FormatUtils.format( 3 , klineMacd.getDIFF(0)), x + charWidth * 12, y + fontHeight, paint);
		paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[1]);
		canvas.drawText("DEA " + FormatUtils.format( 3 , klineMacd.getDea(0)), x + charWidth * 23, y + fontHeight, paint);
		
		//获取当前K线显示的区域
		int tempEndIndex = getEndIndex();
		
		//获取MACD的最大值，用于绘制纵坐标
		double topMacdValue = klineMacd.getMACDTopValue(startIndex, tempEndIndex);
		y = originY;
		paint.setColor(ColorUtils.AMOUNT_COLOR);
		if (isDrawIn) 
		{
			paint.setTextAlign(Align.LEFT);
			canvas.drawText(FormatUtils.format( 2, topMacdValue), x, y
					+ spaceAmountToZhibiao + fontHeight, paint);
		}
		else
		{
			paint.setTextAlign(Align.RIGHT);
			canvas.drawText(FormatUtils.format( 2, topMacdValue), x - 2, y
					+ spaceAmountToZhibiao + fontHeight, paint);
		}

		//获取MACD的最小值，用于绘制纵坐标
		double bottomMacdValue = klineMacd.getMACDBottomValue(startIndex, tempEndIndex);
		if (isDrawIn)
		{
			canvas.drawText(FormatUtils.format( 2, bottomMacdValue), x, y
					+ height, paint);
		}
		else
		{
			canvas.drawText(FormatUtils.format( 2, bottomMacdValue), x - 2, y
					+ height , paint);
		}
		
		//绘制MACD指标线
		float klineBarMiddlex = 0;
		double macd = 0;
		double diff = 0;
		double dea = 0;
		double lastDiff = 0;
		double lastDea = 0;
		paint.setAntiAlias(false);
		double tbdv = topMacdValue - bottomMacdValue;
		
		if (tbdv >= -0.00001 && tbdv <= 0.00001)
		{
			canvas.drawLine(x, y + spaceAmountToZhibiao+(height - spaceAmountToZhibiao) / 2, x + width, y
					+ spaceAmountToZhibiao+(height - spaceAmountToZhibiao) / 2, paint);
		}
		else
		{	
			canvas.drawLine(x, (int) (y + spaceAmountToZhibiao + (height - spaceAmountToZhibiao)
					* (topMacdValue) / tbdv), x + width,
					(int) (y + spaceAmountToZhibiao + (height - spaceAmountToZhibiao) * (topMacdValue)
							/ tbdv), paint);
		}
		y += spaceAmountToZhibiao;
		height -= spaceAmountToZhibiao;
		for (int i = startIndex; i < klineCountPerScreen + startIndex
				&& i < mViewModel.getDataSize(); i++)
		{
			klineBarMiddlex = x + (klineBarWidth + 1) * (i - startIndex) + (klineBarWidth + 1) / 2;
			macd = klineMacd.getMACD(i);
			diff = klineMacd.getDIFF(i);
			dea = klineMacd.getDea(i);
			
			if (macd > 0)
			{
				paint.setColor(ColorUtils.UPDOWN_COLOR[0]);
			} 
			else
			{
				paint.setColor(ColorUtils.UPDOWN_COLOR[1]);
			}
			canvas.drawLine(klineBarMiddlex, 
							(int) (y + height* (topMacdValue - macd) / tbdv), 
							klineBarMiddlex,
							(int) (y + height * (topMacdValue - 0) / tbdv), 
							paint);

			if (i == startIndex) 
			{
				lastDea = dea;
				lastDiff = diff;
				continue;
			}

			if (0 != lastDiff && 0 != diff) 
			{
				paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
				canvas.drawLine(klineBarMiddlex - klineBarWidth - 1,
								(int) (y + height * (topMacdValue - lastDiff) / tbdv),
								klineBarMiddlex, 
								(int) (y + height * (topMacdValue - diff)/ tbdv),
								paint);
			}
			if (0 != lastDea && 0 !=dea)
			{
				paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[1]);
				canvas.drawLine(klineBarMiddlex - klineBarWidth - 1,
								(int) (y + height * (topMacdValue - lastDea) / tbdv),
								klineBarMiddlex, 
								(int) (y + height * (topMacdValue - dea)/ tbdv),
								paint);
			}
			lastDea = dea;
			lastDiff = diff;
		}
	}

	/**
	 * 绘制RSI指标（相对强弱指标）
	 * 注：RSI取值范围应该在0-100之间，公式为：N日RSI =N日内收盘涨幅的平均值/(N日内收盘涨幅均值+N日内收盘跌幅均值) ×100% 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param canvas
	 * @param paint
	 */
	private void drawRsi(int x, int y, int width, int height, Canvas canvas,
			Paint paint) {
		//获取当前焦点线的位置
		int tempFocusIndex = getFocusIndex();
		//获取当前K线显示的区域
		int tempEndIndex = getEndIndex();
		 //设置焦点线位置
		mViewModel.setIndex(tempFocusIndex);
		KlineRSI rsi = mViewModel.getRSI();
		//绘制RSI，RSI1，RSI2，RSI3
		paint.setColor(ColorUtils.CHAR_COLOR);
		paint.setTextAlign(Align.LEFT);
		
		int originY = y;
		y += (spaceAmountToZhibiao -fontHeight)/2;
		
		canvas.drawText("RSI", x, y + fontHeight, paint);
		paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]); 
		canvas.drawText("RSI1 " + FormatUtils.format(  0 ,rsi.getRSI(6  , 0)), x + charWidth * 5, y + fontHeight, paint);
		paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[1]);
		canvas.drawText("RSI2 " + FormatUtils.format(  0 ,rsi.getRSI(12  , 0)), x + charWidth * 14, y+ fontHeight , paint);
		paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[2]);
		canvas.drawText("RSI3 " + FormatUtils.format(  0 ,rsi.getRSI(24  , 0)), x + charWidth * 23, y + fontHeight, paint);
		
		double maxRsiValue = 0;//区间内最高Rsi值
		double minRsiValue = Float.MAX_VALUE;//区间内最低Rsi值
		double tempValue;
		
		tempValue = rsi.getMaxRSIValue(6, startIndex, tempEndIndex);
		if(tempValue > maxRsiValue)
		{
			maxRsiValue = tempValue;
		}
		tempValue = rsi.getMaxRSIValue(12, startIndex, tempEndIndex);
		if(tempValue>maxRsiValue)
		{
			maxRsiValue = tempValue;
		}
		tempValue = rsi.getMaxRSIValue(24, startIndex, tempEndIndex);
		if(tempValue>maxRsiValue)
		{
			maxRsiValue = tempValue;
		}
		
		tempValue = rsi.getMinRSIValue(6, startIndex, tempEndIndex);
		if(tempValue < minRsiValue)
		{
			minRsiValue = tempValue;
		}
		tempValue = rsi.getMinRSIValue(12, startIndex, tempEndIndex);
		if(tempValue < minRsiValue)
		{
			minRsiValue = tempValue;
		}
		tempValue = rsi.getMinRSIValue(24, startIndex, tempEndIndex);
		if(tempValue<minRsiValue)
		{
			minRsiValue = tempValue;
		}
		
		y = originY;
		//绘制纵坐标上的数值
		paint.setColor(ColorUtils.AMOUNT_COLOR);
		if (isDrawIn)
		{
			paint.setTextAlign(Align.LEFT);
			canvas.drawText(FormatUtils.format(  0 ,maxRsiValue), x, y + spaceAmountToZhibiao + fontHeight, paint);
		}
		else
		{
			paint.setTextAlign(Align.RIGHT);
			canvas.drawText(FormatUtils.format(  0 ,maxRsiValue), x - 2, y + spaceAmountToZhibiao + fontHeight, paint);
		}
		if (isDrawIn)
		{
			canvas.drawText(FormatUtils.format(  0 ,minRsiValue), x, y + height, paint);
		}
		else
		{
			canvas.drawText(FormatUtils.format(  0 ,minRsiValue), x - 2, y + height, paint);
		}

		int klineBarMiddlex = 0;
		y += spaceAmountToZhibiao;
		height -= spaceAmountToZhibiao;
		double lastRsi6 = 0;
		double lastRsi12 = 0;
		double lastRsi24 = 0;
		double rsi6;
		double rsi12;
		double rsi24;
		double tbdv = maxRsiValue - minRsiValue;
		for (int i = startIndex; i < klineCountPerScreen + startIndex
				&& i < mViewModel.getDataSize(); i++)
		{
			mViewModel.setIndex(i);
			klineBarMiddlex = x + (klineBarWidth + 1) * (i - startIndex) + (klineBarWidth + 1) / 2;
			//取6天 12天 24天的数据
			rsi6 = rsi.getRSI( 6 , i);
			rsi12 = rsi.getRSI( 12 , i);
			rsi24 = rsi.getRSI( 24 , i);

			if (i == startIndex)
			{
				lastRsi6 = rsi6;
				lastRsi12 = rsi12;
				lastRsi24 = rsi24;
				continue;
			}

			if (0 != lastRsi6 && 0 != rsi6)
			{
				paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
				canvas.drawLine(klineBarMiddlex - klineBarWidth - 1,
								(int) (y + height * (maxRsiValue - lastRsi6) / tbdv),
								klineBarMiddlex, 
								(int) (y + height * (maxRsiValue - rsi6)/ tbdv),
								paint);
			}
			if (0 != lastRsi12 && 0 != rsi12) 
			{
				paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[1]);
				canvas.drawLine(klineBarMiddlex - klineBarWidth - 1,
								(int) (y + height * (maxRsiValue - lastRsi12) / tbdv),
								klineBarMiddlex, 
								(int) (y + height * (maxRsiValue - rsi12)/ tbdv),
								paint);
			}
			if (0 != lastRsi24 && 0 != rsi24 ) 
			{
				paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[2]);
				canvas.drawLine(klineBarMiddlex - klineBarWidth - 1,
								(int) (y + height * (maxRsiValue - lastRsi24) / tbdv),
								klineBarMiddlex, 
								(int) (y + height * (maxRsiValue - rsi24)/ tbdv), 
								paint);
			}
			lastRsi6 = rsi6;
			lastRsi12 = rsi12;
			lastRsi24 = rsi24;
		}
	}

	/**
	 * 绘制威廉指标,注：DTK提供的WR指标为近14个交易日的指标
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param canvas
	 * @param paint
	 */
	private void drawWr(int x, int y, int width, int height, Canvas canvas,
			Paint paint) {
		//获取当前焦点线的位置
		int tempFocusIndex = getFocusIndex();
		 //设置焦点线位置
		mViewModel.setIndex(tempFocusIndex);
		
		//绘制当前WR值
		paint.setColor(ColorUtils.CHAR_COLOR);
		paint.setTextAlign(Align.LEFT);
		canvas.drawText("WR", x, y +(spaceAmountToZhibiao -fontHeight)/2 + fontHeight, paint);
		paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
//		canvas.drawText("W%R " + FormatUtils.format( 2, quoteKlinePacket.getWR()), x + charWidth * 5, y + fontHeight, paint);

		//绘制纵坐标
		paint.setColor(ColorUtils.AMOUNT_COLOR);
		paint.setTextAlign(Align.RIGHT);
		if (isDrawIn)
		{
			canvas.drawText("100", x, y + spaceAmountToZhibiao + fontHeight, paint);
		}
		else
		{
			canvas.drawText("100", x - 2, y + spaceAmountToZhibiao + fontHeight, paint);
		}
		if (isDrawIn)
		{
			canvas.drawText("0", x, y + height, paint);
		}
		else
		{
			canvas.drawText("0", x - 2, y + height, paint);
		}
		
		//绘制WR线
		paint.setTextAlign(Align.LEFT);
		float klineBarMiddlex = 0;
		y += spaceAmountToZhibiao;
		height -= spaceAmountToZhibiao;
		float lastwr = 0;
		float wr = 0;
		float tbdv = 100;
		for (int i = startIndex; i < klineCountPerScreen + startIndex
				&& i < mViewModel.getDataSize(); i++) 
		{
			mViewModel.setIndex(i);
			klineBarMiddlex = x + (klineBarWidth + 1) * (i - startIndex) + (klineBarWidth + 1) / 2;
//			wr = quoteKlinePacket.getWR();
			if (i == startIndex)
			{
				lastwr = wr;
				continue;
			}

			paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
			canvas.drawLine(klineBarMiddlex - klineBarWidth,
							(y + height * (100 - lastwr) / tbdv),
							klineBarMiddlex, (y + height * (100 - wr) / tbdv),
							paint);
			lastwr = wr;
		}
	}

	private void drawDate(int x, int y, int width, int height,
			Canvas canvas, Paint paint){
		
		if(0 == mViewModel.getDataSize())
		{
			return;
		}
		
		int numWidth = 0;
		
		if (!isDrawIn) 
		{
			numWidth = (int) (paint.measureText("00000.0") + 2);
			x += numWidth;
			width -= numWidth;
		}
		//绘制起点，终点日期
		paint.setColor(ColorUtils.K_DATE_COLOR);
		paint.setTextAlign(Align.LEFT);
		
		if (null != mViewModel)
		{
			canvas.drawText(mViewModel.getDate(startIndex) % 100000000 + "",
							x,
							y + height,
							paint);
			
			int li = startIndex + klineCountPerScreen;
			if (li > mViewModel.getDataSize() - 1) 
			{
				li = mViewModel.getDataSize() - 1;
			}
			if (li >= mViewModel.getDataSize()) 
			{				
				li = mViewModel.getDataSize() - 1;
			}
			if (li < 0) 
			{
				li = 0;
			}
			paint.setTextAlign(Align.RIGHT);
			canvas.drawText(mViewModel.getDate(li) % 100000000 + "", 
							x + width, 
							y + height,
							paint);
		}
		// 绘制当前焦点的日期
		if (this.focusIndex > 0)
		{
			mViewModel.setIndex(startIndex + focusIndex - 1);
			long tempTime = mViewModel.getDate(startIndex + focusIndex - 1)% 100000000;
			int dataWidth = (int) (paint.measureText(tempTime + "") + 2);
			int hdw = dataWidth / 2;
			float tflc = focusLineCoordinate;
			if (focusLineCoordinate < hdw + numWidth) 
			{
				tflc = hdw + numWidth;
			}
			else if (focusLineCoordinate > width - hdw + numWidth) 
			{
				tflc = width - hdw + numWidth;
			}
			paint.setAntiAlias(false);
			paint.setColor(0xFF737e85);
			RectF rect = new RectF(tflc - hdw, y + height - fontHeight -4,
					tflc + hdw + 8, y + height);
			paint.setColor(ColorUtils.DATE_COLOR);
			paint.setStyle(Style.FILL);

			canvas.drawRoundRect(rect, 6, 6, paint);

			paint.setColor(ColorUtils.COLOR_WHITE_);
			paint.setTextAlign(Align.CENTER);
			paint.setAntiAlias(true);
			canvas.drawText(tempTime + "", tflc+4, y + height -2, paint);
			
		}		
	}
	private void drawZhibiao(int x, int y, int width, int height,
			Canvas canvas, Paint paint) {
		if (!isDrawIn) 
		{
			int numWidth = (int) (paint.measureText("00000.0") + 2);
			x += numWidth;
			width -= numWidth;
		}
		
		paint.setColor(ColorUtils.BORDER_COLOR);
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		//canvas.drawRect(x, y + 1, x + width, y + height, paint);
		paint.setColor(ColorUtils.BORDER_COLOR);
		canvas.drawRect(x, y + spaceAmountToZhibiao, x + width, y + height, paint);
		int ty = (int) (y + spaceAmountToZhibiao + (height - spaceAmountToZhibiao) / 2);
		if (eMACD != zhibiaoType) 
		{
			canvas.drawLine(x, ty, x + width, ty, paint);
		}
		
		if(0 == mViewModel.getDataSize())
		{
			return;
		}
		//y += spaceAmountToZhibiao;
		switch (zhibiaoType) {
		case eMACD:	
			drawMacd(x, y, width, height, canvas, paint);
			break;
		case eRSI:
			drawRsi(x, y, width, height, canvas, paint);
			break;
		case eWR:
			drawWr(x, y, width, height, canvas, paint);
			break;
		case eKDJ:
			drawKdj(x, y, width, height, canvas, paint);
			break;
		case ePSY:
			drawPsy(x, y, width, height, canvas, paint);
			break;

		default: //eMACD
			drawMacd(x, y, width, height, canvas, paint);
			break;
		}
		//绘制焦点线
		if (0 != focusIndex) 
		{
			paint.setColor(ColorUtils.FOCUS_LINE_COLOR);
			int fiy = x + (focusIndex - 1) * (klineBarWidth + 1)+ (klineBarWidth + 1) / 2;
			canvas.drawLine(fiy, y + 1 + fontHeight, fiy, y + height - 1, paint);
		}
	}

	private void drawKdj(int x, int y, int width, int height, Canvas canvas,
			Paint paint) {
		//获取当前焦点线的位置
		int tempFocusIndex = getFocusIndex();
		//获取当前K线显示的区域
		int tempEndIndex = getEndIndex();
		 //设置焦点线位置
		mViewModel.setIndex(tempFocusIndex);
		KlineKDJ kdj = mViewModel.getKDJ();
		paint.setColor(ColorUtils.CHAR_COLOR);
		paint.setTextAlign(Align.LEFT);
		
		int textY = y + (spaceAmountToZhibiao -fontHeight)/2 + fontHeight;
		
		canvas.drawText("KDJ", x, textY, paint);
		paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
		canvas.drawText("K " + FormatUtils.format( 2, kdj.getKData(0)), x + charWidth * 5, textY, paint);
		paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[1]);
		canvas.drawText("D " + FormatUtils.format( 2, kdj.getDData(0)), x + charWidth * 13, textY, paint);
		paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[2]);
		canvas.drawText("J " + FormatUtils.format( 2, kdj.getJData(0)), x + charWidth * 20, textY, paint);
		
		//绘制纵坐标的KDJ值
		double topKdj = 0;
		double bottomKdj = 0;
		topKdj = kdj.getKDJTopValue(startIndex, tempEndIndex);
		bottomKdj = kdj.getKDJBottomValue(startIndex, tempEndIndex);
		paint.setColor(ColorUtils.AMOUNT_COLOR);
		if (isDrawIn)
		{
			canvas.drawText(FormatUtils.format( 2, topKdj), x, y + spaceAmountToZhibiao + fontHeight, paint);
		}
		else 
		{
			paint.setTextAlign(Align.RIGHT);
			canvas.drawText(FormatUtils.format( 2, topKdj), x - 2, y + spaceAmountToZhibiao + fontHeight, paint);
		}
		if (isDrawIn)
		{
			canvas.drawText(FormatUtils.format( 2, bottomKdj), x, y + height, paint);
		}
		else
		{
			canvas.drawText(FormatUtils.format( 2, bottomKdj), x - 2, y + height, paint);
		}
		//绘制轨迹
		int klineBarMiddlex = 0;
		y += spaceAmountToZhibiao;
		height -=spaceAmountToZhibiao;
		double lastk = 0;
		double lastd = 0;
		double lastj = 0;
		double k;
		double d;
		double j;
		double tbdv = topKdj - bottomKdj;
		for (int i = startIndex; i < klineCountPerScreen + startIndex
				&& i < mViewModel.getDataSize(); i++) 
		{
			mViewModel.setIndex(i);
			klineBarMiddlex = x + (klineBarWidth + 1) * (i - startIndex)
					+ (klineBarWidth + 1) / 2;
			k = kdj.getKData(i);
			d = kdj.getDData(i);
			j = kdj.getJData(i);

			if (i == startIndex)
			{
				lastk = k;
				lastd = d;
				lastj = j;
				continue;
			}

			paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
			canvas.drawLine(klineBarMiddlex - klineBarWidth - 1,
							(int) (y + height * (topKdj - lastk) / tbdv),
							klineBarMiddlex, (int) (y + height * (topKdj - k) / tbdv),
							paint);
			paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[1]);
			canvas.drawLine(klineBarMiddlex - klineBarWidth - 1,
							(int) (y + height * (topKdj - lastd) / tbdv),
							klineBarMiddlex, (int) (y + height * (topKdj - d) / tbdv),
							paint);
			paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[2]);
			canvas.drawLine(klineBarMiddlex - klineBarWidth - 1,
							(int) (y + height * (topKdj - lastj) / tbdv),
							klineBarMiddlex, (int) (y + height * (topKdj - j) / tbdv),
							paint);
			lastk = k;
			lastd = d;
			lastj = j;
		}
	}

	/**
	 * 绘制PSY指标（心理线）
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param canvas
	 * @param paint
	 */
	private void drawPsy(int x, int y, int width, int height, Canvas canvas, Paint paint) {
		
		KlinePSY psy = mViewModel.getPSY();
		
		//获取当前焦点线的位置
		int tempFocusIndex = getFocusIndex();
		//获取当前K线显示的区域
		int tempEndIndex = getEndIndex();
		 //设置焦点线位置		
		mViewModel.setIndex(tempFocusIndex);
		//psy值
		paint.setColor(ColorUtils.CHAR_COLOR);
		paint.setTextAlign(Align.LEFT);
		
		int originY = y + (spaceAmountToZhibiao - fontHeight)/2 + fontHeight;
		canvas.drawText("PSY", x, originY, paint);
		paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
		canvas.drawText("PSY " + FormatUtils.format( 2, psy.getPSY(0)), x + charWidth * 5, originY, paint);
		paint.setColor(ColorUtils.AMOUNT_COLOR);
		
		//绘制纵坐标的psy最大，最小值
		double maxPsyValue = psy.getPSYAndPSYMATopValue(startIndex, tempEndIndex);
		double minPsyValue = psy.getPSYAndPSYMABottomValue(startIndex, tempEndIndex);
		if (isDrawIn)
		{
			canvas.drawText(FormatUtils.format( 2, maxPsyValue), x, y + spaceAmountToZhibiao + fontHeight, paint);
		}
		else 
		{
			paint.setTextAlign(Align.RIGHT);
			canvas.drawText(FormatUtils.format( 2, maxPsyValue), x - 2, y + spaceAmountToZhibiao + fontHeight, paint);
		}
		if (isDrawIn)
		{
			canvas.drawText(FormatUtils.format( 2, minPsyValue), x, y + height, paint);
		}
		else
		{
			canvas.drawText(FormatUtils.format( 2, minPsyValue), x - 2, y + height, paint);
		}
		//绘制轨迹
		y += spaceAmountToZhibiao;
		height -= spaceAmountToZhibiao;
		double tempPsy;
		double bewteenPsyValue = maxPsyValue - minPsyValue;//最大与最小之间的差值
		float proPositionX=0,prePosistionY=0;//前一点的坐标位置
		float positionX, positionY;//当前点的坐标位置
		paint.setColor(ColorUtils.TECHNICAL_INDICATOR_COLOR[0]);
		for (int i = startIndex; i < klineCountPerScreen + startIndex
				&& i < mViewModel.getDataSize(); i++) 
		{
			mViewModel.setIndex(i);
			tempPsy = psy.getPSY(i);
			positionX = x + (klineBarWidth + 1) * (i - startIndex) + (klineBarWidth + 1) / 2;
			positionY = (float) (y + height * (maxPsyValue - tempPsy) / bewteenPsyValue);
			if (i != startIndex)
			{//第一个点不画线
				canvas.drawLine(proPositionX,
						prePosistionY,
						positionX,
						positionY, paint);
			}
			
			proPositionX = positionX;
			prePosistionY = positionY;
		}
	}
	/**
	 * 绘制背景颜色
	 * 
	 * @param canvas
	 * @param paint
	 * @param x:起始横坐标
	 * @param y:起始纵坐标
	 * @param width:最大宽度
	 * @param height:最大高度
	 */
	private void drawBackground(Canvas canvas, Paint paint, int x, int y, int width, int height) {
		paint.setColor(getBackgroundColor());
		paint.setStyle(Style.FILL);
		canvas.drawRect(x, y, x + width, y + height, paint);
	}
	/**
	 * 绘制背景颜色
	 * @return color:背景颜色
	 */
	public int getBackgroundColor() {
		return ColorUtils.K_BG_COLOR;
	}

	/**
	 * Schedule a user interface repaint.
	 */
	public void repaint() {
		mHandler.post(new Runnable() {
			public void run() {
				invalidate();
			}
		});
	}

	/**
	 * 按键向下导航处理
	 * @param keyCode：键值
	 * @param event：按键事件
	 * @return 处理结果
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
				|| keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
				|| keyCode == KeyEvent.KEYCODE_DPAD_DOWN
				|| keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			dealKey(keyCode);
			invalidate();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	/**
	 * 按键向上导航处理
	 * @param keyCode：键值
	 * @param event：按键事件
	 * @return 处理结果
	 */
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
				|| keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
				|| keyCode == KeyEvent.KEYCODE_DPAD_DOWN
				|| keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			// dealKey(keyCode);
			// invalidate();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private void dealKey(int gameAction) {
		if (KeyEvent.KEYCODE_DPAD_UP == gameAction) 
		{
			focusIndex = 0;
			startIndex = -1; // K线放大,这2个状态复位
			klineBarWidth += 2;
			
			if (klineBarWidth > width / 15) 
			{
				klineBarWidth -= 2;
			}
		} 
		else if (gameAction == KeyEvent.KEYCODE_DPAD_DOWN) 
		{
			focusIndex = 0;
			startIndex = -1; // K线放大,这2个状态复位
			klineBarWidth -= 2;
			
			if (klineBarWidth <= 0) 
			{
				klineBarWidth = 1;
			}
		} 
		else if (gameAction == KeyEvent.KEYCODE_DPAD_LEFT) 
		{
			if (focusIndex == 0) 
			{
				if (klineCountPerScreen > mViewModel.getDataSize()) 
				{
					focusIndex = mViewModel.getDataSize();
				}
				else 
				{
					focusIndex = klineCountPerScreen;
				}
			} 
			else if (focusIndex == 1) 
			{
				if (startIndex > 0)
				{
					startIndex--;
				}
			} 
			else 
			{
				focusIndex--;
			}
		} 
		else if (gameAction == KeyEvent.KEYCODE_DPAD_RIGHT) 
		{
			if (focusIndex == klineCountPerScreen)
			{
				if (startIndex + klineCountPerScreen < mViewModel.getDataSize()) 
				{
					startIndex++;
				}
			} 
			else if (startIndex + focusIndex >= mViewModel.getDataSize()) 
			{
				// focusIndex = 1;
			} 
			else
			{
				focusIndex++;
			}
		}

		repaint();
	}

	public void releaseFocus() {
		if (focusIndex != 0)
			focusIndex = 0;
	}

	private GestureDetector mGestureDetector;
	private OnGestureListener mGestureListener = new GestureDetector.OnGestureListener() {

		@Override
		public boolean onDown(MotionEvent e) {
//			System.out.println("touch onDown()");
			return true;
		}

		/**
		 * Touch了滑动时触发
		 */
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			
			if(null == mViewModel)
			{
				return false;
			}
			
			float x = 0;
			x = e2.getX();
			if (x >= klinex && x <= klinex + klineWidth) {
				focusIndex = (int) ((x - klinex) * 241 / klineWidth);
			}
			if (x >= klinex && x < klinex + klineWidth-2) 
			{
				focusIndex = (int) ((x - klinex) / (klineBarWidth + 1) + 1);
			}
			else if(x<klinex)
			{
				if(startIndex>0)
				{
					startIndex --;
				}
				else
				{
					startIndex = 0;
				}
			}
			else
			{
				if(startIndex+klineCountPerScreen<mViewModel.getDataSize()-1)
				{
					startIndex ++;
				}
				else
				{
					//startIndex = quoteKlinePacket.getDataSize()-klineCountPerScreen-1;
				}
			}
			if (focusIndex > mViewModel.getDataSize())
			{
				focusIndex = mViewModel.getDataSize();
			}
			if (focusIndex + startIndex > mViewModel.getDataSize()
					&& mViewModel.getDataSize() > startIndex)
			{
				focusIndex = mViewModel.getDataSize() - startIndex;
			}
			repaint();
			return true;
		}
	
		@Override
		public void onShowPress(MotionEvent event) {

			float x, y;
			x = event.getX();
			y = event.getY();
			if (x > zhibiaox && x < zhibiaox + width && y > zhibiaoy
					&& y < zhibiaoy + zhibiaoHeight) 
			{

				return;
			}
			if (x > klinex && x < klinex + klineWidth) 
			{
				focusIndex = (int) ((x - klinex) / (klineBarWidth + 1) + 1);
				if (focusIndex > mViewModel.getDataSize())
				{
					focusIndex = mViewModel.getDataSize();
				}
				if (focusIndex + startIndex > mViewModel.getDataSize()
						&& mViewModel.getDataSize() > startIndex)
				{
					focusIndex = mViewModel.getDataSize() - startIndex;
				}
			}
			repaint();
			return;
		}

		/**
		 * 指标切换
		 * 在touch down后又没有滑动（onScroll），又没有长按（onLongPress），然后Touchup时触发
		 */
		@Override
		
		public boolean onSingleTapUp(MotionEvent event) {
			float x, y;
			x = event.getX();
			y = event.getY();

			if (x > zhibiaox && x < zhibiaox + width && y > zhibiaoy
					&& y < zhibiaoy + zhibiaoHeight)
			{
				setZhiBiaoType(++zhibiaoType % 4);
				repaint();
				return true;
			}
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (Math.abs(velocityY) > Math.abs(velocityX)) 
			{
				if (velocityY > 0)
				{
					dealKey(KeyEvent.KEYCODE_DPAD_DOWN);
				} 
				else 
				{
					dealKey(KeyEvent.KEYCODE_DPAD_UP);
				}
				return true;
			}
			
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			float x = e.getX();
			mLongPressX = x;
			mHandler.post(mPressRunnable);

		}

	};
	private float mLongPressX;
	private Runnable mPressRunnable = new Runnable() {

		@Override
		public void run() {
			handlerLongPress(mLongPressX);
			if (!mPressStopped) {
				mHandler.postDelayed(mPressRunnable, 200);
			}
		}
	};
	private boolean mPressStopped;

	public void setPeriodLinearLayout(LinearLayout linearLayout)
	{
		mFunctionButtons = linearLayout;
	}
	public void setStockTableLinearLayout(LinearLayout linearLayout)
	{
		mStockTable = linearLayout;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int actionType = event.getAction(); 
		if (event.getX() >= klinex && event.getX() <= klinex + klineWidth
				&& event.getY() > M5Height && event.getY() <= klinex + klineAreaHeight
				&& mViewModel.getDataSize() > 0) 
		{
			if (actionType == MotionEvent.ACTION_UP) 
			{
				//显示周期
				if(mFunctionButtons != null){
					mFunctionButtons.setVisibility(View.VISIBLE);   
				}
				if(mStockTable != null){
					mStockTable.setVisibility(View.GONE);
				}
				
				mPressStopped = true;
				
			} 
			else//显示当前焦点价格等内容  
			{
				if(mFunctionButtons != null){
					 mFunctionButtons.setVisibility(View.GONE);  
				}
				if(mStockTable != null){
					mStockTable.setVisibility(View.VISIBLE);
				}

				mPressStopped = false;
			}
		}
		
		return mGestureDetector.onTouchEvent(event);
	}

	public void setZhiBiaoType(int zhiBiaoType) {
		if (zhiBiaoType < 0)
			zhiBiaoType = 0 - zhiBiaoType;
		this.zhibiaoType = zhiBiaoType % 5;
	}


	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		if (y == 0) {// 左右移动
			if (x > 0) {// 右
				dealKey(KeyEvent.KEYCODE_DPAD_RIGHT);
				// rightKey();
			} else if (x < 0) {// 左
				dealKey(KeyEvent.KEYCODE_DPAD_LEFT);
				// leftKey();
			}
			invalidate();
			return true;
		} else if (x == 0) {// 上下移动
			if (y < 0) {
				dealKey(KeyEvent.KEYCODE_DPAD_UP);
			} else if (y > 0) {
				dealKey(KeyEvent.KEYCODE_DPAD_DOWN);
			}
			return true;
		}
		return super.onTrackballEvent(event);
	}

	private void handlerLongPress(float x) {
		if (x >= klinex && x <= klinex + 50) 
		{
			dealKey(KeyEvent.KEYCODE_DPAD_LEFT);
		}
		else if (x >= klinex + klineWidth - 50 && x <= klinex + klineWidth) 
		{
			dealKey(KeyEvent.KEYCODE_DPAD_RIGHT);
		}
		repaint();
	}

}
