package com.aaron.yqb.network;

import com.aaron.yqb.App;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.umeng.message.proguard.T;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import cn.bvin.lib.module.net.volley.GsonRequest;

public class RequestBuilder {

    private String url;
    private Map<String, Object> params;

    private Type type;
    private Class<T> clazz;
    private Map<String, String> headers;
    private Response.Listener listener;
    private Response.ErrorListener errorListener;
    private int method;
    private RetryPolicy retryPolicy;

    private GsonRequest request;

    public RequestBuilder() {
        this.method = Request.Method.POST;
        this.params = new HashMap<String, Object>();
    }

    public RequestBuilder(int method) {
        this.method = method;
    }

    public RequestBuilder url(String url) {
        this.url = url;
        return this;
    }

    /**
     * Put params for request
     *
     * @param key
     * @param param
     * @return
     */
    public RequestBuilder put(String key, Object param) {
        if (params == null) {
            this.params = new HashMap<String, Object>();
        }
        this.params.put(key, param);
        return this;
    }

    /**
     * Put params for request
     *
     * @param params
     * @return
     */
    public RequestBuilder put(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    public RequestBuilder type(Type type) {
        this.type = type;
        return this;
    }

    public RequestBuilder clazz(Class clazz) {
        this.clazz = clazz;
        return this;
    }

    public RequestBuilder listener(Response.Listener listener) {
        this.listener = listener;
        return this;
    }

    public RequestBuilder errorListener(Response.ErrorListener errorListener) {
        this.errorListener = errorListener;
        return this;
    }

    public RequestBuilder retryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
        return this;
    }

    /**
     * Put header for reqeust
     *
     * @param key
     * @param value
     * @return
     */
    public RequestBuilder header(String key, String value) {
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        headers.put(key, value);
        return this;
    }

    public RequestBuilder method(int method) {
        this.method = method;
        return this;
    }

    public RequestBuilder create() {
        if (url == null) throw new IllegalArgumentException("url is null");
        if (params == null) throw new IllegalArgumentException("params is null");
        if (type == null && clazz == null) throw new IllegalArgumentException("type and clazz are all null");
        // if (headers == null) throw new IllegalArgumentException("headers is null");
        if (listener == null) throw new IllegalArgumentException("listener is null");
        if (errorListener == null) throw new IllegalArgumentException("errorListener is null");
        if (method < -1) throw new IllegalArgumentException("method is illegal");

        if (type != null) {
            request = new GsonRequest(App.getAppContext(), method, url, params, type, listener, errorListener);
        } else if (clazz != null) {
            request = new GsonRequest(App.getAppContext(), method, url, params, clazz, listener, errorListener);
        }

        if (retryPolicy != null) {
            request.setRetryPolicy(retryPolicy);
        }

        return this;
    }

    public void send(String tag) {
        if (request != null) {
            RequestManager.executeRequest(request, tag);
        }
    }

    public void send() {
        if (request != null) {
            RequestManager.executeRequest(request);
        }
    }
}