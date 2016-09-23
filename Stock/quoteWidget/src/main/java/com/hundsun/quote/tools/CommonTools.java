package com.hundsun.quote.tools;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hundsun.quote.model.Stock;

/**
 *源程序名称:Tools.java
 *软件著作权:恒生电子股份有限公司
 *系统名称:QuoteWidget
 *模块名称:模块名
 *功能说明:工具类。
 *作    者: 梁浩 
 *开发日期: 2014-10-28-下午4:12:05
 *版 本 号: 1.0.0.1
 *备    注: 
 */
public class CommonTools {
	public final static int MSG_KEYBOARD_GENIUS_SUCCESSFUL_KEY = 0x01;
	public final static int MSG_KEYBOARD_GENIUS_EXCEPTION_KEY  = 0x02;
	public static final int MSG_AT_ATENTION_POP_WINDOW         = 0x883;
	public final static int KEY_SEARCH_LIST_MAX_NUM  = 10; 
	public final static String KEY_SEARCH_LIST       = "search_list";

	//排序-股票-key
	public final static int KEY_SORT_STOCK_CODE       =    0x1001;   // 股票代码
	public final static int KEY_SORT_STOCK_NAME       =    0x1002;   // 股票名称
	public final static int KEY_SORT_NEW_PRICE        =    0x1003;   // 最新价 -成交价
	public final static int KEY_SORT_OPEN_PRICE       =    0x1004;   // 开盘价
	public final static int KEY_SORT_PRE_CLOSE_PRICE  =    0x1005;   // 昨收价
	public final static int KEY_SORT_AVERAGE_PRICE    =    0x1006;   // 均价
	public final static int KEY_SORT_HIGH_PRICE       =    0x1007;   // 最高价
	public final static int KEY_SORT_LOW_PRICE        =    0x1008;   // 最低价
	public final static int KEY_SORT_AMPLITUDE        =    0x1009;   // 振幅
	public final static int KEY_SORT_ENTRUST_RADIO    =    0x1010;   // 委比
	public final static int KEY_SORT_ENTRUST_DIFF     =    0x1011;     // 委差
	public final static int KEY_SORT_VOLUME_RADIO     =    0x1012;      // 量比
	public final static int KEY_SORT_CHANGE_HAND_RADIO    =    0x1013;  // 换手率
	public final static int KEY_SORT_PRICE_CHANGE_PERCENT =    0x1014;  // 涨跌幅
	public final static int KEY_SORT_PRICE_CHANGE     =    0x1015;      // 涨跌
	public final static int KEY_SORT_TOTAL_MONEY      =    0x1016;      // 总成交金额
	public final static int KEY_SORT_TOTAL_VOLUME     =    0x1017;      // 总成交量   
	public final static int KEY_SORT_CURRENT          =    0x1018;      // 现手
	public final static int KEY_SORT_TOTAL_HAND       =    0x1019;      // 总手
	public final static int KEY_SORT_PE_RADIO         =    0x1020;      // 市盈率
	public final static int KEY_SORT_RISE_SPEED       =    0x1021;      // 涨速
	public final static int KEY_SORT_INSIDE           =    0x1022;      // 内盘
	public final static int KEY_SORT_OUTSIDE          =    0x1023;      // 外盘
	public final static int KEY_SORT_NO               =    0x1024;      // 不排序

	
	/**
	 * 市场类型枚举
	 * @author 梁浩        2014-11-7-下午2:04:17
	 */
	public  enum  QUOTE_MARKET {
		//中国
		CCFX,   // china financial futures exchange
		CSSX,   // china stainless steel exchange
		SGEX,   // shanghai gold exchange
		XCFE,  //  china  foreign exchange trade system
		XDCE,  //dalian commodity exchange
		XSGE,  //shanghai futures exchange
		XINE,  //shanghai international energy exchange
		XSHE,  //shenzhen stock exchange
		XSHG,  //shanghai stock exchange 
		XSSC,  //shanghai stock exchange -shanghai -hong kong stock connect 
		XZCE,  // zhengzhou commondiity exchange
		//中国香港
		CGMH,  // CITI MATCH
		DBHK,  // 	DEUTSCHE BANK HONG KONG ATS
		EOTC,  // 	E-OTC
		HKME	,  // HONG KONG MERCANTILE EXCHANGE
		HSXA,  // HSBC-X HONG KONG
		MEHK	,  // MACQUARIE EXECUTION (HK)
		SIGH	,  // SIGMA X HONG KONG
		TOCP,  // TORA CROSSPOINT
		UBSX	,  // UBS CROSS
		XCGS	,  // CHINESE GOLD & SILVER EXCHANGE SOCIETY
		XHKF	,  // HONG KONG FUTURES EXCHANGE LTD.
		XHKG	,  // HONG KONG EXCHANGES AND CLEARING LTD
		SHSC	,  // STOCK EXCHANGE OF HONG KONG LIMITED - SHANGHAI - HONG KONG STOCK CONNECT
		XGEM	,  // HONG KONG GROWTH ENTERPRISES MARKET
		XIHK	,  // INSTINET PACIFIC LTD
		XPST	  // POSIT - ASIA PACIFIC
		//美国

	}

	/**
	 * 交易所类型枚举
	 * @author 梁浩        2014-11-7-下午2:04:17
	 */
	public  enum  QUOTE_TRADE {
		//中国
		CCFX,   // china financial futures exchange
		CSSX,   // china stainless steel exchange
		SGEX,   // shanghai gold exchange
		XCFE,  //  china  foreign exchange trade system
		XDCE,  //dalian commodity exchange
		XSGE,  //shanghai futures exchange
		XINE,  //shanghai international energy exchange
		XSHE,  //shenzhen stock exchange
		XSHG,  //shanghai stock exchange 
		XSSC,  //shanghai stock exchange -shanghai -hong kong stock connect 
		XZCE,  // zhengzhou commondiity exchange
		//中国香港
		CGMH,  // CITI MATCH
		DBHK,  // 	DEUTSCHE BANK HONG KONG ATS
		EOTC,  // 	E-OTC
		HKME	,  // HONG KONG MERCANTILE EXCHANGE
		HSXA,  // HSBC-X HONG KONG
		MEHK	,  // MACQUARIE EXECUTION (HK)
		SIGH	,  // SIGMA X HONG KONG
		TOCP,  // TORA CROSSPOINT
		UBSX	,  // UBS CROSS
		XCGS	,  // CHINESE GOLD & SILVER EXCHANGE SOCIETY
		XHKF	,  // HONG KONG FUTURES EXCHANGE LTD.
		XHKG	,  // HONG KONG EXCHANGES AND CLEARING LTD
		SHSC	,  // STOCK EXCHANGE OF HONG KONG LIMITED - SHANGHAI - HONG KONG STOCK CONNECT
		XGEM	,  // HONG KONG GROWTH ENTERPRISES MARKET
		XIHK	,  // INSTINET PACIFIC LTD
		XPST	  // POSIT - ASIA PACIFIC
		//美国

	}
	/**
	 * 丰富表头排序字段枚举
	 * @author 梁浩        2014-11-7-下午2:04:17
	 */
	public enum  QUOTE_SORT {
		STOCK_CODE,   		  // 股票代码
		STOCK_NAME,    		  // 股票名称
		NEW_PRICE,             // 最新价 -成交价
		OPEN_PRICE,            // 开盘价
		PRE_CLOSE_PRICE,       // 换手率
		AVERAGE_PRICE,         // 均价
		HIGH_PRICE,            // 最高价
		LOW_PRICE,             // 最低价
		AMPLITUDE,             // 振幅
		ENTRUST_RADIO,         // 委比
		ENTRUST_DIFF,          // 委差
		VOLUME_RADIO,          // 量比
		CHANGE_HAND_RADIO,     // 换手率
		PRICE_CHANGE_PERCENT,  // 涨跌幅
		PRICE_CHANGE,          // 涨跌
		TOTAL_MONEY,           // 总成交金额
		TOTAL_VOLUME,          // 总成交量      
		CURRENT,               // 现手
		TOTAL_HAND,            // 总手
		PE_RADIO,              // 市盈率
		RISE_SPEED,            // 涨速
		INSIDE,                // 内盘
		OUTSIDE,                // 外盘
		NO                     // 不排序
	}
	
	/**
	 * 获取排序字段
	 * @return
	 */
	public static int[] getQuoteSortKey(){
		 int[]  quoteSortKey ={
					KEY_SORT_STOCK_CODE,   		  // 股票代码
					KEY_SORT_STOCK_NAME,    		  // 股票名称
					KEY_SORT_NEW_PRICE,             // 最新价 -成交价
					KEY_SORT_OPEN_PRICE,            // 开盘价
					KEY_SORT_PRE_CLOSE_PRICE,       // 昨收价
					KEY_SORT_AVERAGE_PRICE,         // 均价
					KEY_SORT_HIGH_PRICE,            // 最高价
					KEY_SORT_LOW_PRICE,             // 最低价
					KEY_SORT_AMPLITUDE,             // 振幅
					KEY_SORT_ENTRUST_RADIO,         // 委比
					KEY_SORT_ENTRUST_DIFF,          // 委差
					KEY_SORT_VOLUME_RADIO,          // 量比
					KEY_SORT_CHANGE_HAND_RADIO,     // 换手率
					KEY_SORT_PRICE_CHANGE_PERCENT,  // 涨跌幅
					KEY_SORT_PRICE_CHANGE,          // 涨跌
					KEY_SORT_TOTAL_MONEY,           // 总成交金额
					KEY_SORT_TOTAL_VOLUME,          // 总成交量      
					KEY_SORT_CURRENT,               // 现手
					KEY_SORT_TOTAL_HAND,            // 总手
					KEY_SORT_PE_RADIO,              // 市盈率
					KEY_SORT_RISE_SPEED,            // 涨速
					KEY_SORT_INSIDE,                // 内盘
					KEY_SORT_OUTSIDE,                // 外盘
					KEY_SORT_NO                     // 不排序
				};
		return quoteSortKey;
	}
	/**
	 * 丰富表头排序字段枚举
	 * @author 梁浩        2014-11-7-下午2:04:17
	 */
	
	

	//排名业务类型
	public enum RankBusiType {
		MyStockRanking, //自选排名
		BlockRanking,   //板块行情排名
		ComplexRanking, //综合排名
		FeatureRanking, //特色排名
		ClassifyRanking //分类排名
	}
	public enum RequestStatus {Request,Update};
	/**
	 * 获取排序列表
	 * @param type
	 */
	public static QUOTE_SORT[] getSortList(){
		return new QUOTE_SORT[]{
				//"现价", "涨跌幅%", "涨跌额","成交量", "换手率", "现手"
				QUOTE_SORT.NEW_PRICE,
				QUOTE_SORT.PRICE_CHANGE_PERCENT,
				QUOTE_SORT.PRICE_CHANGE,
				QUOTE_SORT.TOTAL_VOLUME,
				QUOTE_SORT.CHANGE_HAND_RADIO,
				QUOTE_SORT.CURRENT
		};
	}
	/**
	 * 获取市场类型
	 * @param type
	 */
	public static  int getDtkMarketTypeValue(QUOTE_MARKET marketType){

		return QuoteConstants.BOURSE_STOCK_SH_A;
	}
	/**
	 * 获取排序字段值
	 * @param type
	 */
	public static int getDtkSortFieldValue(QUOTE_SORT marketType){
		if(marketType == QUOTE_SORT.NEW_PRICE ){
			return QuoteConstants.COLUMN_HQ_BASE_NEW_PRICE;
		}else if(marketType == QUOTE_SORT.PRICE_CHANGE_PERCENT ){
			return QuoteConstants.COLUMN_HQ_BASE_RISE_RATIO;
		}else if(marketType == QUOTE_SORT.PRICE_CHANGE){
			return QuoteConstants.COLUMN_HQ_BASE_RISE_VALUE;
		}else if(marketType == QUOTE_SORT.CHANGE_HAND_RADIO){
			return QuoteConstants.COLUMN_HQ_EX_EXHAND_RATIO;
		}else if(marketType == QUOTE_SORT.CURRENT){
			return QuoteConstants.COLUMN_HQ_BASE_HAND;
		}
		return QuoteConstants.PAIXU_ZHANGFU;
	}

	/**
	 * 获取市场类型
	 * @param type
	 */
	public static int getH5MarketTypeValue(QUOTE_MARKET marketType){
		return QuoteConstants.BOURSE_STOCK_BK;
	}
	/**
	 * 获取排序字段值
	 * @param type
	 */
	public static int getH5SortFieldValue(QUOTE_SORT marketType){
		return QuoteConstants.PAIXU_ZHANGFU;
	}
	/**
	 * 保存搜索记录到本地文件
	 * @param context
	 * @param searchStock
	 */
	public static void saveRecentlySearchRecord(Context context,Stock searchStock){
		if( searchStock == null){
			return;
		}	
		String stockString = searchStock.getStockCode() + "--"+searchStock.getStockName() + "--"+searchStock.getCodeType();

		SharedPreferencesManager sharedPrefer = new SharedPreferencesManager(context);
		String[] searchArray = sharedPrefer.getSharedPreferStringArray(KEY_SEARCH_LIST);

		ArrayList<String> searchList = new ArrayList<String>();
		for(int i = 0 ;i < searchArray.length;i++){
			if (stockString.equalsIgnoreCase(searchArray[i])) {
				continue;
			}
			searchList.add(searchArray[i]);
		}
		int len = searchList.size();
		if(len >= KEY_SEARCH_LIST_MAX_NUM){
			searchList.remove(1);
		}

		searchList.add(stockString);
		final int saveSize = searchList.size();
		String[] retSearchArray = (String[])searchList.toArray(new String[saveSize]);
		sharedPrefer.saveSharePreferStringArray(KEY_SEARCH_LIST,retSearchArray);
	}

	/**
	 * 获取最近搜索记录
	 * @author  梁浩
	 * @param context
	 * @return
	 */
	public static ArrayList<Stock> getRecentlySearchRecord(Context context){
		ArrayList<Stock>  searchRecordList = new ArrayList<Stock>();
		SharedPreferencesManager sharedPrefer = new SharedPreferencesManager(context);
		String[] searchLists = sharedPrefer.getSharedPreferStringArray(KEY_SEARCH_LIST);
		if(searchLists != null ){
			if(searchLists.length > 0){
				for(int searchItem = searchLists.length-1; searchItem > 0;searchItem--){
					String sSearchItem = searchLists[searchItem];
					if(!TextUtils.isEmpty(sSearchItem) && sSearchItem.contains("--")){
						String[] searchStocks = sSearchItem.split("--");
						Stock stock = new Stock();
						stock.setCodeType(searchStocks[2]);
						stock.setStockCode(searchStocks[0]);
						stock.setStockName(searchStocks[1]);
						searchRecordList.add(stock);
					}
				}
			}
		}
		return searchRecordList;
	}

	//自定义滑动列表刷新枚举
	public enum ScrollTableRefreshMode {
		Up,   //上拉刷新
		Down, //下拉刷新
		None  //不刷新模式
	}
	/**
	 * 获得当前手机屏幕的宽度
	 * @author  梁浩
	 * @param act
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getCurrScreenWidth(Activity act) {
		if(act == null){
			return 480;
		}
		WindowManager windowManager = act.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		return display.getWidth();
	}

	/**
	 * 获得当前手机屏幕的高度
	 * @author  梁浩
	 * @param act
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getCurrScreenHeight(Activity act) {
		if(act == null){
			return 800;
		}
		WindowManager windowManager = act.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		return display.getHeight();
	}

	/**
	 * 通过图片名称获取图片资源
	 * @param context
	 * @param picName
	 * @return
	 */
	public static BitmapDrawable getBitmapDrawableByPictrueName(Context context,String picName) {
		if(TextUtils.isEmpty(picName)){
			return null;
		}
		Integer value = 0;
		Class<com.hundsun.quotewidget.R.drawable> drawableClass = com.hundsun.quotewidget.R.drawable.class;
		try {
			value= drawableClass.getDeclaredField(picName).getInt(null);
			return (BitmapDrawable) context.getResources().getDrawable(value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}
	//获取字体大小  
	public static int adjustFontSize(Activity act) {  
		int screenWidth  = getCurrScreenWidth(act) ;
		int screenHeight = getCurrScreenHeight(act) ;
		screenWidth=screenWidth>screenHeight?screenWidth:screenHeight;  
		/** 
		 * 1. 在视图的 onsizechanged里获取视图宽度，一般情况下默认宽度是320，所以计算一个缩放比率 
	            rate = (float) w/320   w是实际宽度 
	           2.然后在设置字体尺寸时 paint.setTextSize((int)(8*rate));   8是在分辨率宽为320 下需要设置的字体大小 
	            实际字体大小 = 默认字体大小 x  rate 
		 */  
		int rate = (int)(5*(float) screenWidth/480); //我自己测试这个倍数比较适合，当然你可以测试后再修改  
		return rate<14?15:rate; //字体太小也不好看的  
	} 

	//获取字体大小  
	public static int adjustFontSize(Context context) {  
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		int screenHeight = dm.heightPixels;
		int screenWidth = dm.widthPixels;
		screenWidth=screenWidth>screenHeight?screenWidth:screenHeight;  
		/** 
		 * 1. 在视图的 onsizechanged里获取视图宽度，一般情况下默认宽度是320，所以计算一个缩放比率 
	            rate = (float) w/320   w是实际宽度 
	           2.然后在设置字体尺寸时 paint.setTextSize((int)(8*rate));   8是在分辨率宽为320 下需要设置的字体大小 
	            实际字体大小 = 默认字体大小 x  rate 
		 */  
		int rate = (int)(screenWidth/480); //我自己测试这个倍数比较适合，当然你可以测试后再修改  
		return rate; //字体太小也不好看的  
	} 


	/**
	 * 不同分辨率字体适应
	 * @param context
	 * @param textSize
	 * @return
	 */
	public static int getFontSize(Context context, int textSize) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		int screenHeight = dm.heightPixels;
		int rate = (int) (textSize * (float) screenHeight / 1280);
		return rate;
	}
	public static float getScreenShowTextSize(Context context) {
		float textSize  = 0.0F;
		float textSizeValue  = 16.0F;
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		textSize = dm.density;
		if(textSize <= 0.75){
			textSizeValue= 10.0F;
		}else if(textSize > 0.75 && textSize <= 1){
			textSizeValue= 12.0F;
		}else if(textSize > 1 && textSize <= 1.5){
			textSizeValue= 14.0F;
		}else if(textSize > 1.5 && textSize <= 2){
			textSizeValue= 16.0F;
		}
		return textSizeValue;
	}
	public static int getScrollTableItemHeight(Context context) {
		float screenDensity  = 0.0F;
		int itemHeight  = 30;
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		screenDensity = dm.density;
		if(screenDensity <= 0.75){
			itemHeight  = 30;
		}else if(screenDensity > 0.75 && screenDensity <= 1){
			itemHeight  = 30;
		}else if(screenDensity > 1 && screenDensity <= 1.5){
			itemHeight  = 30;
		}else if(screenDensity > 1.5 && screenDensity <= 2){
			itemHeight  = 38;
		}else{
			itemHeight  = 30;
		}
		return itemHeight;
	}

    /**
     * ScrollView 中嵌套 ListView 显示问题
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); 
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
