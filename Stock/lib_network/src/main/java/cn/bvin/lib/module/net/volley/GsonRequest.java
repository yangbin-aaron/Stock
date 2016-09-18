package cn.bvin.lib.module.net.volley;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.aaron.network.IntentConfig;
import com.aaron.network.ListResponse;
import com.aaron.network.RequestConfig;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

public class GsonRequest<T> extends BaseRequest<T> {

    private final static String DEFAULT_CHARSET = "UTF-8";

    private Context mContext;

    private final Gson gson = new Gson();

    private final Class<T> clazz;
    private final Type type;
    private final Listener<T> listener;

    /**
     * 不带参数的Gson请求
     *
     * @param url           请求地址
     * @param clazz         gson解析模具类
     * @param listener      请求响应监听器
     * @param errorListener 错误监听器
     */
    public GsonRequest(Context context, String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
        super(url, errorListener);
        this.mContext = context;
        this.clazz = clazz;
        this.listener = listener;
        this.type = null;
    }

    public GsonRequest(Context context, String url, Type type, Listener<T> listener, ErrorListener errorListener) {
        super(url, errorListener);
        this.mContext = context;
        this.type = type;
        this.clazz = null;
        this.listener = listener;
        type = null;
    }

    /**
     * 带参数的Gson请求
     *
     * @param method        请求类型[GET|POST|PUT|DELETE]
     * @param url           请求地址
     * @param mapParams     请求参数map集
     * @param clazz         gson解析模具类
     * @param listener      请求响应监听器
     * @param errorListener 错误监听器
     */
    public GsonRequest(Context context, int method, String url, Map<String, Object> mapParams, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
        super(method, url, mapParams, errorListener);
        this.mContext = context;
        this.clazz = clazz;
        this.listener = listener;
        this.type = null;
    }

    public GsonRequest(Context context, int method, String url, Map<String, Object> mapParams, Type type, Listener<T> listener, ErrorListener errorListener) {
        super(method, url, mapParams, errorListener);
        this.mContext = context;
        this.type = type;
        this.clazz = null;
        this.listener = listener;
    }


    @Override
    protected void deliverResponse(T response) {
        if (response instanceof com.aaron.network.Response) {
            Integer code = ((com.aaron.network.Response) response).getCode();
            if (code != null && code == RequestConfig.ResponseCode.RE_LOGIN) {
                Intent intent = new Intent(IntentConfig.Actions.ACTION_RELOGIN);
                mContext.sendBroadcast(intent);
                ((com.aaron.network.Response) response).setMsg();
            }
        } else {
            if (response instanceof ListResponse) {
                Integer code = ((ListResponse) response).getCode();
                if (code != null && code == RequestConfig.ResponseCode.RE_LOGIN) {
                    Intent intent = new Intent(IntentConfig.Actions.ACTION_RELOGIN);
                    mContext.sendBroadcast(intent);
                    ((ListResponse) response).setMsg();
                }
            }
        }
        listener.onResponse(response);

    }

    @SuppressWarnings("unchecked")
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String json = null;
        try {
            String charset = HttpHeaderParser.parseCharset(response.headers);
            if (!charset.equalsIgnoreCase(DEFAULT_CHARSET)) {
                charset = DEFAULT_CHARSET;
            }
            json = new String(response.data, charset);
            if (clazz != null) {
                return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
            } else if (type != null) {
                return (Response<T>) Response.success(gson.fromJson(json, type), HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return Response.error(new VolleyError("gson解析失败，因为gson的class<T>或者type为空"));
            }

        } catch (UnsupportedEncodingException e) {
            return Response.error(new VolleyError(e));

        } catch (JsonSyntaxException e) {
            if (TextUtils.isEmpty(json)) {
                return Response.error(new ParseError(e));
            } else {
                return Response.error(new JsonParseError(json, e));
            }
        }
    }

}
