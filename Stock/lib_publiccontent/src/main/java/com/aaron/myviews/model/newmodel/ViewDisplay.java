package com.aaron.myviews.model.newmodel;

/**
 * http://stock.luckin.cn/user/sys/display
 *
 * { "code": 200,
 * "msg": "操作成功",
 * "msgType": 0,
 * "errparam": "",
 * "data":
     { "clear": "1",
     "alipay": "1",
     "cots": "1",
     "kline": "1"}}
 clear   清仓   1清 其余不清
 "cots": "1",  账户中心的现货开户
 "kline": "1" K线是否显示
 alipay 支付宝展示 1展示  其余不展示
 */
public class ViewDisplay {

    public static final String DISPAY = "1";
    public static final String NO_DISPAY = "0";

    private String clear; // 早上是否清仓
    private String alipay; // 是否显示账号中心现货开户
    private String cots; // k线是否显示
    private String kline; // 支付宝是否展示

    private static ViewDisplay sInstance;

    public static ViewDisplay getInstance() {
        if (sInstance == null) {
            sInstance = new ViewDisplay();
        }
        return sInstance;
    }

    public ViewDisplay() {
        this.clear = NO_DISPAY;
        this.alipay = NO_DISPAY;
        this.cots = NO_DISPAY;
        this.kline = NO_DISPAY;
    }

    public void setViewDisplay(ViewDisplay display) {
        sInstance = display;
    }

    public String getClear() {
        if (clear != null) {
            return clear;
        }
        return NO_DISPAY;
    }

    public String getAlipay() {
        if (alipay != null) {
            return alipay;
        }
        return NO_DISPAY;
    }

    public String getCots() {
        if (cots != null) {
            return cots;
        }
        return NO_DISPAY;
    }

    public String getKline() {
        if (kline != null) {
            return kline;
        }
        return NO_DISPAY;
    }

    public interface onFetchListener {
        void fetchSuccess();
    }

    public static void fetchViewDisplay() {
        fetchViewDisplay(null);
    }

    public static void fetchViewDisplay(final onFetchListener listener) {
//        new RequestBuilder().url(ApiConfig.getFullUrl(ApiConfig.ApiURL.VIEW_DISPLAY))
//                .type(new TypeToken<Response<ViewDisplay>>() {}.getType())
//                .listener(new com.android.volley.Response.Listener<Response<ViewDisplay>>() {
//                    @Override
//                    public void onResponse(Response<ViewDisplay> response) {
//                        if (response == null) return;
//                        if (response != null && response.isSuccess()) {
//                            ViewDisplay.getInstance().setViewDisplay(response.getData());
//                        }
//                        if (listener != null) {
//                            listener.fetchSuccess();
//                        }
//                    }
//                }).errorListener(new SimpleErrorListener(false))
//                .create().send();
    }
}
