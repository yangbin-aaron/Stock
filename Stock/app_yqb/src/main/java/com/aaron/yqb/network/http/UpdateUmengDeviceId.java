package com.aaron.yqb.network.http;

/**
 * Created by Administrator on 2015/9/28.
 */
public class UpdateUmengDeviceId {

    private static class UpdateUmengDeviceIdHolder{
        private static final UpdateUmengDeviceId INSTANCE = new UpdateUmengDeviceId();
    }

    public static UpdateUmengDeviceId getInstance(){
        return UpdateUmengDeviceIdHolder.INSTANCE;
    }

    public void updateUmengDeviceId(){
//        new RequestBuilder()
//                .url(ApiConfig.getFullUrl(HttpConfig.HttpURL.SET_USER_UPDATEUMCODE))
//                .put(HttpKeys.TOKEN, UserInfoManager.getInstance().getToken())
//                .put(HttpKeys.HTTP_KEY_UMCODE, PhoneInfoUtil.getUmengDeviceId())
//                .put(HttpKeys.HTTP_KEY_PLATFORM, "android")
//                .put(HttpKeys.HTTP_KEY_ENVIRONMENT, Environment.getEnvironment().getGroup())
//                .put(HttpKeys.HTTP_KEY_PKGTYPE, "androidcainiu")
//                .type(new TypeToken<Response<Object>>(){}.getType())
//                .listener(new com.android.volley.Response.Listener<Response<Object>>() {
//                    public void onResponse(Response<Object> response) {
//                    }
//                }).errorListener(new SimpleErrorListener()) .create().send();
    }
}
