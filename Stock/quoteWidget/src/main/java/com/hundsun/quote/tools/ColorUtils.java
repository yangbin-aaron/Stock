package com.hundsun.quote.tools;

public class ColorUtils {


	public static final int HOME_NAV_BUTTON_COLOR = 0xFF000000;
	public static final int KLINE_NAV_BUTTON_COLOR = 0xFF000000;
	

//	public static int TREND_FOCUSDATA_BORDER_COLOR = 0xFF737e85;//分时焦点值浮动框边框颜色
//	public static int TREND_FOCUSDATA_BG_COLOR = 0x75000000;//分时焦点值浮动框背景颜色
	public static int TREND_FOCUSDATA_BORDER_COLOR = 0xFF69889e;//分时焦点值浮动框边框颜色//QII LiangHao 2014-03-25
	public static int TREND_FOCUSDATA_BG_COLOR = 0xFF00bbff;    //分时焦点值浮动框背景颜色  //QII LiangHao 2014-03-25
	
	public static int TREND_PRICE_LINE_COLOR = 0xFF50b2e5;    //分时价格线颜色
	public static int TREND_PRICE_LINE_COLOR_BG = 0x1150b2e5;    //分时价格线颜色
	public static int KLINE_PRICE_LINE_COLOR_BG = 0xDDFFFFFF;    //分时价格线颜色
	//public static int TREND_AVERAGE_LINE_COLOR = 0xFFedd927;//分时均线颜色
	
	public static int TREND_AVERAGE_LINE_COLOR = 0xffffbb22;//分时均线颜色  QII LiangHao
	
	
	public static int TRADERED = 0xffc20a0b;
	public static int TRADEGREEN = 0xff097c25;
	public static int TRADEFONTCOLOR = 0xFF000000;
	

	public static final int COLOR_RED    = 0xffd61100;
	public static final int COLOR_GREEN  = 0xff2d8133;//17e100
	public static final int COLOR_WHITE  = 0xffa3a3a3; //这里直接修改成与背景相反的灰色
	public static final int COLOR_YELLOW = 0xffff8f2d;
	public static final int COLOR_BLACK  = 0xff000000;
	public static final int COLOR_WHITE_ = 0xffffffff; 
	
	
	public static final int INFO_TITLE_COLOR = 0xFF0042ff;

	
	//K线，分时页面色值定义 START
	public static int TREND_BG_COLOR = 0xFF132155;//分时背景颜色
	public static int OPENCLOSE_COLOR = 0xFF1f518a; //开闭式文字颜色 
	
	public static int CHAR_COLOR = 0xff494949; 		//普通文字颜色      //QII LiangHao 2014-03-25
	
	public static int BORDER_COLOR = 0xFFa4a4a4; 	//边框颜色     //QII LiangHao 2014-03-25
	public static int FOCUS_LINE_COLOR = 0xFF008AFF; 	//焦点线颜色    //QII LiangHao 2014-03-25
	public static int AMOUNT_COLOR = 0xffffbb22;	//成交量颜色    QII LiangHao 
	public static int UPDOWN_COLOR[] = { 0xfff00000, COLOR_GREEN }; 	 //涨跌颜色{红色，绿色}
	public static int DATE_COLOR = 0xBB000000; //日期颜色
	public static int K_FOCUS_DATE_COLOR = 0xff91a5c6; //日期颜色
	public static int K_DATE_COLOR = 0xff1f518a; //日期颜色
	public static int K_BG_COLOR = 0xFF132155; 			// k线背景色 
	public static final int LEFT_DATA_COLOR = 0xFF000000;	//图左侧值的颜 //QII LiangHao 2014-03-25
	public static final int MA_COLOR[] = { 0xffffbb22, 0xff32cd32, 0xFF008AFF }; //MA5,MA10,MA30线的颜色 

	public static final int TECHNICAL_INDICATOR_COLOR[] = { 0xffffbb22, 0xff32cd32, 0xFF0000FF }; // MACD指标的颜色 	
	//K线，分时页面色值定义 END
	
	public static final int SETTING_TITLE_COLOR = 0xFF373737;//设置中类型TITLE颜色
	
	/**
	 * 跟据现价和昨收价比较 返回显示颜色
	 * @param newPrice
	 * @param prevClose
	 * @return
	 */
	public static int getColor(float newPrice, float prevClose) {
		int retColor;
		if (newPrice == 0) {
			retColor = COLOR_WHITE;
		} else {
			int temp = Float.compare(newPrice, prevClose);
			if (temp > 0){
				retColor = COLOR_RED;
			}else if (temp == 0){
				retColor = COLOR_WHITE;
			}else{
				retColor = COLOR_GREEN;
			}
		}
		return retColor;
	}
	
	/**
	 * 跟据现价和昨收价比较 返回显示颜色
	 * @param newPrice
	 * @param prevClose
	 * @return
	 */
	public static int getColor(double newPrice, double prevClose) {
		int retColor;
		if (newPrice == 0) {
			retColor = COLOR_WHITE;
		} else {
			int temp = Double.compare(newPrice, prevClose);
			if (temp > 0){
				retColor = COLOR_RED;
			}else if (temp == 0){
				retColor = COLOR_WHITE;
			}else{
				retColor = COLOR_GREEN;
			}
		}
		return retColor;
	}
	
	public static int getColor(String difference){
		float fDiff = 0;
		if(difference != null){
			if(difference.endsWith("%"))
				difference = difference.substring(0, difference.length() - 1);
			try{
				fDiff = Float.parseFloat(difference);
			}catch(Exception ex){}
		}
		if(fDiff == 0)
			return COLOR_WHITE;
		else if(fDiff > 0)
			return COLOR_RED;
		else
			return COLOR_GREEN;
	}

	
	/**
	 * 取委比或委差颜色
	 * 
	 * @return
	 */
	public static int getWeiChaOrBiColor(int buyCount1,int buyCount2,int buyCount3,int buyCount4,int buyCount5,
			int sellCount1,int sellCount2,int sellCount3,int sellCount4,int sellCount5) {
		int buyCount = buyCount1 + buyCount2 + buyCount3 + buyCount4 + buyCount5;
		int sellCount = sellCount1 + sellCount2 + sellCount3 + sellCount4 + sellCount5;
		int del = buyCount - sellCount;
		if (del == 0)
			return COLOR_WHITE;
		else if (del > 0)
			return COLOR_RED;
		else
			return COLOR_GREEN;
	}
	

}
