package cn.bvin.lib.module.net;

import com.android.volley.Request;
import com.android.volley.VolleyError;

/**
 * 请求状态接口
 * @author bvin
 * @param <T> Request的泛型参数
 */
public interface RequestListener<T> {

	public void onRequestStart(Request<T> resquest);
	
	public void onRequestSuccess(T result);
	
	public void onRequestFailure(VolleyError error);
	
}
