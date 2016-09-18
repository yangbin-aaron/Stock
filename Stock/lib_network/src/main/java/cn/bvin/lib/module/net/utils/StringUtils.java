package cn.bvin.lib.module.net.utils;

import android.text.TextUtils;

import java.util.Map;

public class StringUtils {

    /**
     *
     * @Title: getStringFromMap
     * @Description: 以a=1&b=2的形式返回map字符串
     * @param map
     * @return: String
     */
    public static String getStringFromMap(Map<String, Object> map){
        StringBuilder encodedParams = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue()!=null) {
                    encodedParams.append(entry.getKey());
                    encodedParams.append('=');
                    encodedParams.append(entry.getValue().toString());
                    encodedParams.append('&');
                }
        }
        if (encodedParams.toString().endsWith("&")) {
            encodedParams.deleteCharAt(encodedParams.length()-1);
        }
        return encodedParams.toString();
    }
    
    /**
     * 
     * @Title: hazAvailableContent 
     * @Description: TextUtils.isEmpty+trim
     * @param content
     * @return: boolean||return true if the content after trim not empty
     */
    public static boolean hazAvailableContent(String content){
        if (!TextUtils.isEmpty(content)) {
            String trimText = content.trim();
            if (!TextUtils.isEmpty(trimText)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 生成url字符串
     * @param map 输入的参数
     * @return 返回类似a=1&b=2形式的字符串
     */
    public static String generateUrlString(Map<String, Object> map) {
        return getStringFromMap(map);
    }
}
