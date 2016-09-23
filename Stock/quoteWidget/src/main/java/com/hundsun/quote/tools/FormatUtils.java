package com.hundsun.quote.tools;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import android.text.TextUtils;

import com.hundsun.quote.model.Stock;

/**
 * @author LiangHao
 *
 */
public class FormatUtils {
	public static final DecimalFormat[] DECIMALFORMATS = {
		new DecimalFormat("#0"),
		new DecimalFormat("#0.0"),
		new DecimalFormat("#0.00"),
		new DecimalFormat("#0.000"),
		new DecimalFormat("#0.0000"),
		new DecimalFormat("#0.00000"),
		new DecimalFormat("#0.000000")
	};
	
	static{
		for (int i = 0; i < DECIMALFORMATS.length; i++) {
			DECIMALFORMATS[i].setRoundingMode(RoundingMode.HALF_UP);
		}
	}
	
	//	/** 无小数点格式 */
	//	public static final DecimalFormat DECIMALFORMAT_0 = new DecimalFormat("#0");
	//	/** 1位小数格式 */
	//	public static final DecimalFormat DECIMALFORMAT_1 = new DecimalFormat("#0.0");
	//	/** 2位小数格式 */
	//	public static final DecimalFormat DECIMALFORMAT_2 = new DecimalFormat("#0.00");
	//	/** 3位小数格式 */
	//	public static final DecimalFormat DECIMALFORMAT_3 = new DecimalFormat("#0.000");

	private static DecimalFormat df = new DecimalFormat("0.00%");

	public static String formatPrice( String codeType , double priceValue ){
		int persision = QuoteUtils.getPricePrecision(codeType);
		return format(persision,priceValue);
	}

	/**
	 * 支持H5、DTK -- 价格格式化
	 * @param stock
	 * @param priceValue
	 * @return
	 */
	public static String formatPrice(Stock stock , double priceValue){
		if(stock == null || priceValue == 0){
			return "--";
		}
		return formatPrice(stock.getCodeType(), priceValue);
	}
	/**
	 * 支持H5、DTK -- 价格格式化
	 * @param stock
	 * @param priceValue
	 * @return
	 */
	public static String formatPriceChange(Stock stock , double priceChange){
		if(stock == null || priceChange == 0){
			return "0.00";
		}
		int persision = QuoteUtils.getPricePrecision(stock.getCodeType());
		return format(persision,priceChange);
	}

	public static double getDoublePrice(Stock stock, double tnp) {
		int priceUnit = QuoteUtils.getPriceUnit(stock);
		return (double) (tnp/priceUnit);
	}

	public static String formatPercent( double value ){
		return df.format(value);
	}


	public static String format( int points , double value){
		//		switch (points) {
		//		case 0:
		//			return DECIMALFORMAT_0.format(value);
		//		case 1:
		//			return DECIMALFORMAT_1.format(value);
		//		case 2:
		//			return DECIMALFORMAT_2.format(value);
		//		case 3:
		//			return DECIMALFORMAT_3.format(value);
		//
		//		default:
		//			break;
		//		}
		if (points>= 0 && points < DECIMALFORMATS.length) {
			return DECIMALFORMATS[points].format(value);
		}
		return String.valueOf( value );
	}

	/**
	 * 对double类型数据进行四舍五入处理后保持精度
	 * @param points
	 * @param value
	 * @return
	 * @author  梁浩
	 */
	public static double formatDouble( int points , double value){
		double returnValue = Double.valueOf( format(points, value));
		return  returnValue ; 
	}

	/**
	 * 格式化大数据值,自动根据数据大小决定是否需要保留小数位
	 * @return
	 */
	public static String formatBigNumber(double amount){
		if ( 0 == amount) {
			return "--";
		}
		String amountStr = String.valueOf(amount);
		if ( amount < 100000 ) {
			//小于10万
			amountStr = DECIMALFORMATS[0].format(amount);
		}else if ( amount < 1000000 ) {
			//小于100万
			amountStr = DECIMALFORMATS[2].format(amount/10000)+"万";
		}else if ( amount < 100000000 ) {
			//小于1亿
			amountStr = DECIMALFORMATS[0].format(amount/10000)+"万";
		}else if (amount < 10000000000L ) {
			//小于100亿
			amountStr = DECIMALFORMATS[2].format(amount/100000000)+"亿";
		}else{
			//大于100亿
			amountStr = DECIMALFORMATS[0].format(amount/100000000)+"亿";
		}


		return amountStr;
	}

	public static String formatStockVolume( Stock stock ,double volume){
		if (0 == volume) {
			return "--";
		}
		String str = formatBigNumber( volume );
		if (getStockCategory(stock) == StockCategory.CN_STOCK) {
			return str+ "手";
		}
		return str+"股";
	}

	//	/**
	//	 * 将服务器传来的量信息转换成显示的字符串
	//	 * 
	//	 * @param f 数量，成交量或成交金额
	//	 * @return
	//	 */
	//	public static String amountToString2(double f, int bs) {
	//		String returnValue = "--";
	//		String units = "";
	//		if (f == 0){
	//			return returnValue;
	//		}
	//		if(f > 100000000){
	//			f = f/100000000;
	//			units = "亿";
	//		}else if(f > 1000000){
	//			f = f/10000;
	//			units = "万";
	//		}else{
	//			units = "";
	//		}
	//		return	returnValue = format(bs,f)+units;
	//	}
	//
	//	/**
	//	 * 将服务器传来的量信息转换成显示的字符串
	//	 * 
	//	 * @param f 数量，成交量或成交金额
	//	 * @return
	//	 */
	//	public static String amountToString(double f, long bs) {
	//		if (bs > 0)
	//			f *= bs;
	//		if (f == 0)
	//			return "--";
	//		String strReturn = f + "";
	//		if (f > 100000000) {
	//			strReturn = f / 100000000 + ".";
	//			if (f / 1000000 % 100 < 10)
	//				strReturn = strReturn + "0";
	//			strReturn = strReturn + f / 1000000 % 100 + "亿";
	//		} else if (f > 1000000)
	//			strReturn = f / 10000 + "万";
	//		else
	//			strReturn = f + "";
	//		return strReturn;
	//	}

	/**
	 * 数字字符串转为有单位(万, 亿, 千亿)的字符串
	 * 
	 * @param numberStr
	 * @param dec
	 *            小数点
	 * @return
	 */
	public static String numberToStringWithUnit(String numberStr, int dec) {
		dec = dec +1; //四舍五入处理
		if(numberStr.endsWith("-")){
			return numberStr;
		}
		String ret = numberStr;
		String unit = "";
		int point = numberStr.indexOf(".");
		if (point < 0){
			point = numberStr.length();
		}		
		if (point < 5) {
		} else if (point < 9) {
			point -= 4;
			unit = "万";
		}else{
			point -= 8;
			unit = "亿";
		}
		//		} else if (point < 12) {
		//			point -= 8;
		//			unit = "亿";
		//		} else {
		//			point -= 11;
		//			unit = "千亿";
		//		}
		ret = numberStr.substring(0, point);
		String decStr = "";
		if (dec > 0) {
			if(numberStr.length() == point ){
				for (int i = 0; i < dec; i++) {
					decStr += "0";
				}
			}	
			else if (numberStr.length() - point <= dec + 1) {
				decStr = numberStr.substring(point + 1);
				for (int i = decStr.length(); i < dec; i++) {
					decStr += "0";
				}
			} else {
				decStr = numberStr.substring(point, point + dec);
			}
		}
		if (decStr.length() > 0)
			ret += "." + decStr;
		return formatValue(ret,dec,unit);
	}

	/**
	 * 数字字符串转为有单位(万, 亿, 千亿)的字符串
	 * 
	 * @param numberStr
	 * @param dec
	 *            小数点
	 * @return
	 */
	public static String numberToStringWithUnit2(String numberStr, int dec) {
		dec = dec +1; //四舍五入处理
		if(TextUtils.isEmpty(numberStr)){
			return "--";
		}
		if(numberStr.contains(",")){
			numberStr = numberStr.replace(",", "");
		}
		if(numberStr.endsWith("-")){
			return numberStr;
		}
		String ret = numberStr;
		String unit = "";
		int point = numberStr.indexOf(".");
		if (point < 0){
			point = numberStr.length();
		}		
		if (point < 5) {
		} else if (point < 9) {
			point -= 4;
			unit = "万";
		}else{
			point -= 8;
			unit = "亿";	
		}
		//		} else if (point < 12) {
		//			point -= 8;
		//			unit = "亿";
		//		} else {
		//			point -= 11;
		//			unit = "千亿";
		//		}
		ret = numberStr.substring(0, point);
		String decStr = "";
		if (dec > 0) {
			if(numberStr.length() == point ){
				for (int i = 0; i < dec; i++) {
					decStr += "0";
				}
			}	
			else if (numberStr.length() - point <= dec + 1) {
				decStr = numberStr.substring(point + 1);
				for (int i = decStr.length(); i < dec; i++) {
					decStr += "0";
				}
			} else {
				decStr = numberStr.substring(point, point + dec);
			}
		}
		if (decStr.length() > 0)
			ret += "." + decStr;

		return formatValue(ret,dec,unit);
	}

	private static String formatValue(String ret, int dec, String unit) {
		if(TextUtils.isEmpty(ret)){
			return "--";
		}
		double getValue =  0.00;
		getValue = Double.parseDouble(ret);
		return  format(dec-1,getValue) + unit;
	}

	/**
	 * 将服务器传来的量信息转换成显示的字符串
	 * 
	 * @param amount 数量，成交量或成交金额
	 * @return
	 */
	public static float stringAmountReturnFNumberValue(String amount) {
		float retValue = 0;
		if (TextUtils.isEmpty(amount))
			return retValue;
		if(amount.contains("亿")){
			retValue = Float.parseFloat(amount.replace("亿", ""));
			retValue = retValue * 100000000 ;
		}else if(amount.contains("万")){
			retValue = Float.parseFloat(amount.replace("万", ""));
			retValue = retValue * 1000000 ;
		}else{
			return retValue = Float.parseFloat(amount);
		}
		return retValue;
	}
	/**
	 * 将服务器传来的量信息转换成显示的字符串
	 * 
	 * @param amount 数量，成交量或成交金额
	 * @return
	 */
	public static long stringAmountReturnLNumberValue(String amount) {
		long retValue = 0;
		float amount_ = 0;
		if (TextUtils.isEmpty(amount))
			return retValue;
		if(amount.contains("亿")){
			amount_ = Float.parseFloat(amount.replace("亿", ""));
			retValue =  (long) (amount_ * 100000000) ;
		}else if(amount.contains("万")){
			amount_ = Float.parseFloat(amount.replace("万", ""));
			retValue =  (long) (amount_ * 1000000) ;
		}else{
			amount_ = Float.parseFloat(amount);
			retValue =  (long) amount_;
		}
		return retValue;
	}
	
	enum StockCategory{
		CN_STOCK , //沪深
		US_STOCK   //美股
	};
	
	/**
	 * 获取股票分类，美股、沪深
	 * 默认返回沪深类型
	 * @param stock
	 * @return
	 */
	static StockCategory getStockCategory( Stock stock ){
		if ( null == stock) {
			return StockCategory.CN_STOCK;
		}
		String codeType = stock.getCodeType();
	    
	    if ( codeType.startsWith( "XSHG." ) || codeType.startsWith( "XSHE." ) ) {
	    	return StockCategory.CN_STOCK;
		}
		
		
	    if (codeType.startsWith( "XNAS." ) || codeType.startsWith( "XNYS." ) || codeType.startsWith( "XASE." ) ) {
	    	return StockCategory.US_STOCK;
		}
		return StockCategory.CN_STOCK;
	}
}
