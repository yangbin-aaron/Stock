package com.aaron.yqb.network.http;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.aaron.myviews.config.RequestConfig;
import com.aaron.network.IntentConfig;
import com.aaron.yqb.App;
import com.aaron.yqb.utils.PhoneInfoUtil;
import com.aaron.yqb.utils.Util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class HttpProcessThread implements Runnable {

    private static final String TAG = "HttpProcessThread";

    private String mStrUrl;
    private List<NameValuePair> mPostParams;
    private Handler mHandler;

    private boolean flagRun = true;
//    private boolean isReLogin = false;

    public void setFlagRun(boolean flagRun) {
        this.flagRun = flagRun;
    }

    public HttpProcessThread(String strUrl, List<NameValuePair> post_params, Handler handler) {
        this.mStrUrl = strUrl;
        this.mPostParams = post_params;
        this.mHandler = handler;
    }

    @Override
    public void run() {

        if (mStrUrl == null || mHandler == null) {
            return;
        }
        Message msg = mHandler.obtainMessage();
        try {
            String strIpUrl = HttpConfig.HTTP_HEAD_STR + HttpConfig.SERVER_DOMAIN + mStrUrl;
            HttpResponse httpResponse;
            HttpPost httpPost = new HttpPost(strIpUrl);

            if (mPostParams != null) {
                mPostParams.addAll(PhoneInfoUtil.getRequestExtra());
                httpPost.setEntity(new UrlEncodedFormEntity(mPostParams, HTTP.UTF_8));

                Log.i(TAG, mPostParams.toString());
            }

            HttpClient httpClient = new DefaultHttpClient();
            httpResponse = httpClient.execute(httpPost);

            String responseString = "";

            if (flagRun) {
                HttpEntity httpEntity = httpResponse.getEntity();
                responseString = EntityUtils.toString(httpEntity, "UTF-8");
                JSONObject jsonObject = new JSONObject(responseString);
                int responseCode = jsonObject.optInt(HttpKeys.HTTP_KEY_CODE);

                if (responseCode == RequestConfig.ResponseCode.RE_LOGIN) {
                    Intent intent = new Intent(IntentConfig.Actions.ACTION_RELOGIN);
                    App.getAppContext().sendBroadcast(intent);
                    responseString = Util.alterJsonMsg(responseString);
                }
            }

            if (flagRun) {
                msg.what = HttpConfig.HttpSendResult.HTTP_SEND_SUCCESS;
                msg.obj = responseString;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            msg.what = HttpConfig.HttpSendResult.HTTP_SEND_FAILED;
        } catch (IOException e) {
            e.printStackTrace();
            msg.what = HttpConfig.HttpSendResult.HTTP_SEND_FAILED;
        } catch (JSONException e) {
            e.printStackTrace();
            msg.what = HttpConfig.HttpSendResult.HTTP_SEND_FAILED;
        } finally {
            if (msg.obj != null) {
                mHandler.sendMessage(msg);
                Log.i("HTTP-POST:" + HttpConfig.SERVER_DOMAIN + mStrUrl, (String) msg.obj);
            }
        }
    }
}

