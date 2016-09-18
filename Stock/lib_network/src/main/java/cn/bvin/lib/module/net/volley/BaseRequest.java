package cn.bvin.lib.module.net.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.bvin.lib.module.net.utils.StringUtils;

/**
 * 请求的输入封装，如设置参数集合，头部等
 * @author bvin
 */
public abstract class BaseRequest<T> extends Request<T>{

    protected Map<String, String> headerMap;
    
    protected Map<String, Object> mapParams;
    
    /**
     * 无参数默认GET请求
     * @param url 请求地址
     * @param listener 错误监听器
     */
    public BaseRequest(String url, ErrorListener listener) {
        super(Method.GET,url,listener);
    }

    /**
     * 带参数的请求
     * @param method 请求类型[GET|POST|PUT|DELETE]
     * @param url 请求地址
     * @param mapParams 请求参数Map集合
     * @param listener 错误监听器
     */
    public BaseRequest(int method, String url, Map<String, Object> mapParams, ErrorListener listener) {
        super(method, url, listener);
        this.mapParams = mapParams;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (headerMap!=null) {
            return headerMap;
        } else {
            return super.getHeaders();
        }
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mapParams!=null&&!mapParams.isEmpty()) {
            Map<String, String> params = new HashMap<String, String>();
            for (Entry<String, Object> entry: mapParams.entrySet()) {
                params.put(entry.getKey(), entry.getValue().toString());
            }
            return params;
        }
        return super.getParams();
    }

    /**只拼接了参数，没有作任何加密，如需要需要，重写这个方法*/
    protected byte[] encodeParameters(Map<String, Object> params, String paramsEncoding) {
        try {
            String encodedStr = StringUtils.getStringFromMap(params);
            return encodedStr.getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    /**
     * 设置请求头
     * @param headerMap
     */
    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    /**
     * getUrlWithParams()
     * 返回未加密同时带参数的Url(形式如同GET请求:www.xxx.com?p1=v1&p2=v2&p3=v3),便于调试。
     * 相当于urlgetUrl()+params.toString()
     * */
    public String getUrlWithParams() {
        if (mapParams!=null&&mapParams.size()>0) {
            return getUrl()+"?"+StringUtils.getStringFromMap(mapParams);
        } else {
            return getUrl();
        }
    }
}
