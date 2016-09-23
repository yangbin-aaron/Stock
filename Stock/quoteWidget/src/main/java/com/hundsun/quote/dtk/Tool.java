/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: 恒生电子股份有限公司</p>
 * <p>Project: 投资赢家移动理财终端5.0</p>
 */
package com.hundsun.quote.dtk;




/**
 * 工具类（字节转数字、数字转字节、字节转字符串）
 * 
 * @author LiangHao
 * 
 */
public class Tool {
	
	/**
	 * 数字字符串转为有单位(万, 亿, 千亿)的字符串
	 * 
	 * @param numberStr
	 * @param dec
	 *            小数点
	 * @return
	 */
	public static String numberToStringWithUnit(String numberStr, int dec) {
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
		return ret + unit;
	}
}
