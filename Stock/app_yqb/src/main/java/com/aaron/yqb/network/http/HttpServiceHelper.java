package com.aaron.yqb.network.http;

import android.os.Handler;

import com.aaron.myviews.utils.NetInfoUtil;
import com.aaron.myviews.utils.ToastUtil;
import com.aaron.yqb.App;
import com.aaron.yqb.R;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * @类描述：HTTP传输类
 * @创建人：周宇
 * @创建时间：2015-05-
 * @修改人：
 * @修改时间：2015-05-29
 * @修改备注：
 */

public class HttpServiceHelper {

    private Thread mHttpThread;
    private String mHttpUrl;
    private List<NameValuePair> mHttpParams;
    private Handler mHandler;

    private HttpProcessThread mHttpProcessThread;

    public boolean send(String strUrl, final List<NameValuePair> params, Handler handler) {

        if (!NetInfoUtil.isNetworkAvailable(App.getAppContext())) {
            ToastUtil.showShortToastMsg(App.getAppContext(), R.string.network_is_not_available);
            return false;
        }

        try {
            mHttpUrl = strUrl;
            mHttpParams = params;
            mHandler = handler;

            mHttpProcessThread = new HttpProcessThread(mHttpUrl, mHttpParams, mHandler);
            mHttpThread = new Thread(mHttpProcessThread);
            mHttpThread.start();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void stop() {
        if (mHttpProcessThread != null) {
            mHttpProcessThread.setFlagRun(false);
        }

        if (mHttpThread != null) {
            //mHttpThread.stop();
        }
    }
    
	/*public boolean sendPic(String strUrl,  Handler handler){
		
		try{
			mHttpUrl = strUrl;
			mHandler = handler;
			
			mHttpThread = new Thread(new HttpPicThread(mHttpUrl, mHandler));
			mHttpThread.start();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}*/

}
