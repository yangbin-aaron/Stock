package cn.bvin.lib.module.net.volley;

import android.net.http.AndroidHttpClient;
import android.os.Build;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NoCache;

public class RequestManager {

	private static RequestQueue mRequestQueue = newRequestQueue();

	private RequestManager() {
	}
	
	private static RequestQueue newRequestQueue() {
		RequestQueue requestQueue;
		Network network = new BasicNetwork(getStack());
		requestQueue = new RequestQueue(getCache(), network);
		requestQueue.start();
		return requestQueue;
	}
	
	/**
	 * 添加请求
	 * @param request 请求对象
	 * @param tag 标签，通过这个标签可以操作(取消)请求
	 */
	public static void addRequest(Request request, Object tag) {
		request.setTag(tag!=null?tag:request.getUrl());
		mRequestQueue.add(request);
	}
	
	/**
	 * 添加请求，标签为Request的Url
	 * @param request 请求对象
	 */
	public static void addRequest(Request request) {
		addRequest(request, null);
	}
	
	/**
	 * 按标签清除(取消)请求
	 * @param tag 请求标签
	 */
	public static void clearRequests(Object tag) {
		mRequestQueue.cancelAll(tag);
	}
	
	private static Cache getCache() {
		Cache cache = new NoCache();
		return cache;
	}
	
	private static HttpStack getStack() {
        if (Build.VERSION.SDK_INT >= 9) {
        	return new HurlStack();
        } else {
            // Prior to Gingerbread, HttpUrlConnection was unreliable.
            // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
        	return new HttpClientStack(AndroidHttpClient.newInstance(userAgent()));
        }
	}
	
	
	
	private static String userAgent() {
		return "volley";
	}
}
