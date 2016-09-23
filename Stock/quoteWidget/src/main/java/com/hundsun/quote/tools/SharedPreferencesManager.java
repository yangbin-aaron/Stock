package com.hundsun.quote.tools;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * @DESCRIPTION：Shared Preferences Management
 * @AUTHORITIY :LiangHao
 * @DATA       :2012-3-3
 */
public class SharedPreferencesManager {
	private static final String KPREFERENCES_NAME = "preferences";
	private static Context mContext;
	public SharedPreferencesManager(Context context){
		mContext = context;
	}


	/**
	 * @Description:保存简单的数据的Preference【String】
	 * @Authorities:LiangHao
	 * @Date       :2012-1-14
	 * @Param	   :
	 */
	public static String getStringPreferenceSaveValue(String key){
		String value = "";
		SharedPreferences preference = mContext.getApplicationContext().getSharedPreferences(KPREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		value = preference.getString(key, "");
		return value;
	}

	/**
	 * @Description:保存简单的数据的Preference【Integer】
	 * @Authorities:LiangHao
	 * @Date       :2012-1-14
	 * @Param	   :
	 */
	public static int getIntegerPreferenceSaveValue(String key){
		int value = 0;
		SharedPreferences preference = mContext.getApplicationContext().getSharedPreferences(KPREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		value = preference.getInt(key,0);
		return value;
	}

	/**
	 * @Description:保存简单的数据的Preference【boolean】
	 * @Authorities:LiangHao
	 * @Date       :2012-1-14
	 * @Param	   :
	 */
	public static boolean getBooleanPreferenceSaveValue(String key){
		boolean value = false;
		SharedPreferences preference = mContext.getApplicationContext().getSharedPreferences(KPREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		value = preference.getBoolean(key, false);
		return value;
	}

	/**
	 * @Description:保存简单的数据的Preference【float】
	 * @Authorities:LiangHao
	 * @Date       :2012-1-14
	 * @Param	   :
	 */
	public static float getDoublePreferenceSaveValue(String key){
		float value = 0.0f;
		SharedPreferences preference = mContext.getApplicationContext().getSharedPreferences(KPREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		value = preference.getFloat(key, 0.0f);
		return value;
	}

	/**
	 * @Description:保存简单数据值
	 * @Authorities:LiangHao
	 * @Date       :2012-1-14
	 * @Param	   :
	 */
	public static void setPreferenceValue(String key,Object value){
		SharedPreferences preference = mContext.getApplicationContext().getSharedPreferences(KPREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		Editor edit = preference.edit();
		if(value instanceof String){
			edit.putString(key,(String)value);
		}else if(value instanceof Integer){
			edit.putInt(key, (Integer)value);
		}else if(value instanceof Float){
			edit.putFloat(key, (Float)value);
		}else if(value instanceof Boolean){
			edit.putBoolean(key, (Boolean)value);
		}
		edit.commit();
	}

	/****
	 * 如果想用SharedPreferences存取复杂的数据类型，就需要对这些数据进行编码。通常会将数据转换成Base64编码，然后将转换后的数据以字符串存储。
	 * @throws IOException 
	 */
	//存储：
	//将map转换为byte[]   
	public void saveMapDataToSharePreference(String key,HashMap<String,String> mapData) throws IOException{
		SharedPreferences preference = mContext.getApplicationContext().getSharedPreferences(KPREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		Editor edit = preference.edit();
		ByteArrayOutputStream toByte = new ByteArrayOutputStream();  
		ObjectOutputStream    oos    = new ObjectOutputStream(toByte);  
		oos.writeObject(mapData);  
		//对byte[]进行Base64编码   
		String storeComplexObjectBase64 = new String(Base64Coder.encode(toByte.toByteArray())); 
		edit.putString(key, storeComplexObjectBase64);
		edit.commit();
	}

	//下面是读取： 
	public HashMap<String, String> getMapDataFromSharePreference(String key) throws StreamCorruptedException, IOException, ClassNotFoundException{
		SharedPreferences preference = mContext.getApplicationContext().getSharedPreferences(KPREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String codes = preference.getString(key, null);
		if(codes == null){
			return new HashMap<String,String>();
		}
		byte[] compleObject64Bytes = Base64Coder.decode(codes);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compleObject64Bytes); 
		ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream); 
		@SuppressWarnings("unchecked")
		HashMap<String,String> returnMap = (HashMap<String, String>) ois.readObject();
		return returnMap;
	}




	/**
	 * 用于读取字符串数组
	 * @param key
	 * @return
	 */
	public String[] getSharedPreferStringArray(String key) {
		String regularEx = ",";
		String[] str = null;
		SharedPreferences sp = mContext.getApplicationContext().getSharedPreferences(KPREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		String values; 
		values = sp.getString(key, ""); 
		str = values.split(regularEx);
		return str;
	}

	/**
	 * 存放 字符串数组
	 * @param key
	 * @param values
	 */
	public void saveSharePreferStringArray(String key, String[] values) {
		String regularEx = ",";
		String str = "";
		SharedPreferences sp = mContext.getApplicationContext().getSharedPreferences(KPREFERENCES_NAME, Context.MODE_WORLD_READABLE);
		if (values != null && values.length > 0) {
			for (String value : values) {
				str += value;
				str += regularEx;
			}
			Editor et = sp.edit();
			et.putString(key, str);
			et.commit();
		}  

	}










	}


