package com.aaron.yqb.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.ClipboardManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;

import com.aaron.yqb.network.http.HttpKeys;
import com.hundsun.quote.model.Realtime;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;


public class Util {
	public static void write(ByteArrayOutputStream byteArray, File file)
			throws IOException {
		if (byteArray != null && file != null) {
			InputStream inputStream = new FileInputStream(file);
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = inputStream.read(buffer)) > 0) {
				byteArray.write(buffer, 0, len);
			}
			inputStream.close();
		}
	}

	/**
	 * httpGet请求
	 * 
	 * @param spec
	 * @param paramsPart
	 * @return
	 * @throws IOException
	 */
	public static String httpGet(String spec, String paramsPart)
			throws Exception {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
		if (paramsPart != null && !("".equals(paramsPart))) {
			spec += "?" + paramsPart;
		}
		URL url = new URL(spec);
		//Log.e(GlobalVars.TAG, spec);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		//connection.setConnectTimeout(LuckinLogin.getInstance().getTimeout());

		InputStream in = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(in);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String result = "";
		String temp = "";
		while ((temp = bufferedReader.readLine()) != null) {
			result += temp;

		}
		in.close();
		return result;
	}
	

	public static String httpPost(String spec, File file) throws IOException {
		final String BOUNDARY = "0xCA811D6530E70313";
		URL url = new URL(spec);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setConnectTimeout(15 * 1000);
		connection.setDoOutput(true);

		connection.addRequestProperty("Content-Type",
				"multipart/formdata; boundary=" + BOUNDARY);

		String body = "--" + BOUNDARY + "\r\n";
		body += "Content-Disposition: formdata; name=\"file\"; filename=\"file\""
				+ "\r\n";
		body += "Content-Type: application/octet-stream" + "\r\n" + "\r\n";
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		byteArray.write(body.getBytes("UTF-8"));
		write(byteArray, file);
		byteArray.write(("\r\n" + "--" + BOUNDARY + "--" + "\r\n")
				.getBytes("UTF-8"));
		byteArray.writeTo(connection.getOutputStream());
		return "";
	}

	/**
	 * 根据设备唯一ID生成一个随机字符串
	 * @Title: getRandomString 
	 * @Description: TODO 
	 * @param @param length
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	public static String getRandomString(int length){
    	String str=PhoneInfoUtil.getDeviceId()+"QWERTYUIOPasdfghjkl123968457ZXCVBNMwisldhcoadojW";
    	Random random=new Random();
    	StringBuffer sf=new StringBuffer();
    	for(int i=0;i<length;i++){
    	    int number=random.nextInt(62);//0~61
    	    sf.append(str.charAt(number));
    	}
    	return sf.toString();
	}

	/**
	 * Md5
	 */
	public static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes("UTF-8");
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * UDID
	 * 
	 * @return
	 */
	public static String luckinUDID() {
		char[] key = new char[12];
		key[0] = 'c';
		key[1] = 'o';
		key[2] = 'm';
		key[3] = '.';
		key[4] = 'l';
		key[5] = 'u';
		key[6] = 'c';
		key[7] = 'k';
		key[8] = 'i';
		key[9] = 'n';
		key[10] = '.';
		key[11] = '*';
		String luckinUDID = new String(key);
		return luckinUDID;
	}

	/**
	 * json解析
	 * 
	 * @param obj
	 * @param key
	 * @return
	 */
	public static String parseJson(String obj, String key) {
		String result = "";
		if (obj != null) {
			if (obj.indexOf("{") >= 0) {
				try {
					JSONObject json = new JSONObject(obj);
					result = json.optString(key);
					if (null == result || result.equals("null")) {
						result = "";
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * json解析
	 *
	 * @param obj
	 * @return
	 */
	public static String alterJsonMsg(String obj) {
		String result = "";
		if (obj != null) {
			if (obj.indexOf("{") >= 0) {
				try {
					JSONObject json = new JSONObject(obj);
					if (json.has(HttpKeys.HTTP_KEY_MSG)) {
						json.remove(HttpKeys.HTTP_KEY_MSG);
						json.put(HttpKeys.HTTP_KEY_MSG,"");
					}
					result = json.toString();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * 检查网络连接是否可用
	 * 
	 * @return
	 */
	public static boolean isNetValid(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 二进制数组转为16进制
	 * @param bArray
	 * @return
	 */
	public static final String byte2hex(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp);
		}
		return sb.toString();
	}
	
	/**
	 *  转到万进制位String   保留小数点后1位
	 * @param 
	 * @return
	 */
	public static final String toTenThousandString(int data, String unit){
		String res = "0";
		if(data <= 10000){
			return res;
		}
		
		int n1 = data/1000;
		int d1 = n1%10;
		int d2 = n1/10;
		
		if(d1 == 0){
			res = String.format("%d%s", d2, unit);
		}
		else{
			res = String.format("%d.%d%s", d2, d1, unit);
		}
		
		return res;
	}
	
	/**
	 *  增加千位制逗号 转到千进制位String    
	 */
	public static final String toThousandFormatString(int data){
		final String res = String.valueOf(data);
		if(data < 1000){
			return res;
		}
		
		String str	= "";
		int index 	= 0;
		int len 	= res.length();
		int start 	= len-(4+index);
		int end 	= len-(1+index);
		
		while(index < (len-3)){
			start 	= len-(3+index);
			end 	= len-(index);
			str 	= ","+res.substring(start, end)+str;
			index += 3;
		}
		
		str = res.substring(0, len-index) + str;

		return str;
	}
	
	/**
	 *  增加千位制逗号 转到千进制位String    
	 */
	public static final String toThousandFormatString(final String data){
		String strPercent	= "";
		String strInt		= "";
		
		int indexPoint 	= data.indexOf(".");
		if(indexPoint < 0){
			//不存在
			strInt=data;
		}else{
			strInt 		= data.substring(0, indexPoint);
			strPercent	= data.substring(indexPoint);
		}

		if(Integer.parseInt(strInt) < 1000){
			return data;
		}

		String str	= "";
		int index 	= 0;
		int len 	= strInt.length();
		int start 	= len-(4+index);
		int end 	= len-(1+index);
		
		while(index < (len-3)){
			start 	= len-(3+index);
			end 	= len-(index);
			str 	= ","+strInt.substring(start, end)+str;
			index += 3;
		}
		
		str = strInt.substring(0, len-index) + str;

		return str+strPercent;
	}

	/**
	 * 检查密码格式是否正确
	 */
	public static final int verifyPwd(String pwd) {
		int errorCode = 0;

		if (pwd != null && !("".equals(pwd))) {
			pwd = pwd.trim();
		}
		
//		if (pwd == null || "".equals(pwd)) {
//			errorCode = ActivityConfig.ResultCode.RESULT_CODE_PASSWORD_NULL;
//		} else if (pwd.length() < 6 || pwd.length() > 36) {
//			errorCode = ActivityConfig.ResultCode.RESULT_CODE_PASSWORD_LENGTH_INVALID;
//		} else if (!Pattern.matches("[a-zA-Z0-9_]*", pwd)) {
//			errorCode = ActivityConfig.ResultCode.RESULT_CODE_PASSWORD_STRING_INVALID;
//		}
		
		return errorCode;
	}

	
	/**
	 * 检查用户名格式是否正确
	 */
	public static final int verifyUserName(String userName) {
		int errorCode = 0;
		if (userName != null && !("".equals(userName))) {
			userName = userName.trim();
		}
		
//		if (userName == null || "".equals(userName)) {
//			errorCode = ActivityConfig.ResultCode.RESULT_CODE_USERNAME_NULL;
//		} else if (userName.length() < 6 || userName.length() > 36) {
//			errorCode = ActivityConfig.ResultCode.RESULT_CODE_USERNAME_LENGTH_INVALID;
//		} else if (!Pattern.matches("[a-zA-Z0-9_]*", userName)) {
//			errorCode = ActivityConfig.ResultCode.RESULT_CODE_USERNAME_STRING_INVALID;
//		}
		
		return errorCode;
	}
	
	/**
	 * 获得保留2位 String
	 * @param d
	 * @return
	 */
	public static String getKeepDoubleString(Double d){
		if(d == null || d == 0){
			return "0.00";
		}
		
		String str 		= String.valueOf(d);
		int indexPoint 	= str.indexOf(".");
		if(indexPoint < 0){
			//不存在小数
			str = str+".00";
		}else{
			String subStr = str.substring(indexPoint+1);
			if(subStr.length() == 1){
				//1 位小数
				str = str+"0";
			}else if(subStr.length() > 2){
				str = str.substring(0, indexPoint+3);
			}
		}

		return str;
	}
	
	public static String  getKeepDoubleString(String d) {
        return new BigDecimal(d).setScale(2, BigDecimal.ROUND_DOWN).toString();
    }

	/**
	 * 获得保留2位 String
	 * @param param
	 * @return
	 */
	@Deprecated
	public static String getKeepDoubleStringWithAnotherWay(String param){
		if(param == null){
			return "";
		}
		return getKeepDoubleString(Double.valueOf(param));
	}
	
	public static int computeLenthByByte(String s) {
	    int counter = 0;
        for (int i = 0; i < s.length(); i++) {
            int length = (s.charAt(i)+"").getBytes().length;
            if(length>1){
                counter += 2;
            } else if (length==1){
                counter++;
            }else {//小于1
                continue;
            }
        }
        return counter;
    }

	@SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static void copyToClipboard(Context context,String content) {
        if (android.os.Build.VERSION.SDK_INT>11) {
            android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);  
            clipboardManager.setPrimaryClip(ClipData.newPlainText("Label", content.trim()));
        } else {
            ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);  
            cmb.setText(content.trim());  
        }
        
    }
    
    /**
	 * 查询能否交易状态
	 * @param status
	 * @return
	 */
	public static boolean getStockTradeEnable(int status){
		System.out.println("getStockTradeEnable"+status);
		boolean result = false;
		switch(status){
		case Realtime.TRADE_STATUS_ADD:
		case Realtime.TRADE_STATUS_BETW:
		case Realtime.TRADE_STATUS_DEL:
		case Realtime.TRADE_STATUS_FCALL:
		case Realtime.TRADE_STATUS_ICALL:
		case Realtime.TRADE_STATUS_IOBB:
		case Realtime.TRADE_STATUS_IPOBB:
		case Realtime.TRADE_STATUS_OCALL:
		case Realtime.TRADE_STATUS_OOBB:
		case Realtime.TRADE_STATUS_OPOBB:
		case Realtime.TRADE_STATUS_NOTRD:
		case Realtime.TRADE_STATUS_VOLA:
		case Realtime.TRADE_STATUS_PRETR:
		case Realtime.TRADE_STATUS_CLOSE:
		case Realtime.TRADE_STATUS_ENDTR:
		case Realtime.TRADE_STATUS_POSTR:
		case Realtime.TRADE_STATUS_BREAK:
		case Realtime.TRADE_STATUS_HALT:
		case Realtime.TRADE_STATUS_SUSP:
		case Realtime.TRADE_STATUS_STOP:
		
		{
			result = false;

		}break;
		case Realtime.TRADE_STATUS_TRADE:
		case Realtime.TRADE_STATUS_START:
		{
			result = true;

		}break;
		default:
			break;
		}
		
		return result;
	}
	
	
	/**
	 * 是否汉字开头
	 * @param string 输入字符串
	 * @return 中文开头返回true，否则返回false
	 */
	public static boolean startWithChinese(String string) {
	    if (!TextUtils.isEmpty(string)) {
	        return (Character.toString(string.charAt(0)).matches("[\\u4E00-\\u9FA5]+"));
        }
        return false;
    }
	
    public static SpannableString increaseNumberFontSize(String numberStr, float proportion, String unit) {
        SpannableString res = null;
        if (!TextUtils.isEmpty(numberStr)) {
            int end = numberStr.length();
            numberStr = numberStr + unit;
            res = new SpannableString(numberStr);
            res.setSpan(new RelativeSizeSpan(proportion), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return res;
    }

    public static SpannableString decreaseUnitFontSize(String numberStr, float proportion, String unit) {
        SpannableString res = null;
        if (!TextUtils.isEmpty(numberStr)) {
            int start = numberStr.length();
            numberStr = numberStr + unit;
            int end = numberStr.length();
            res = new SpannableString(numberStr);
            res.setSpan(new RelativeSizeSpan(proportion), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return res;
    }
}
