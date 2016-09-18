package cn.bvin.lib.module.net;

import com.android.volley.Request;


public interface RequestOperate<T> {

	public void addRequest(Request<T> request);
	
	public void cancelRequest();
	
	public void reloadRequest();
}
