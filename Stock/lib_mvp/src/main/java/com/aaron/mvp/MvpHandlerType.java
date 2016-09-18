package com.aaron.mvp;

/**
 * Created by yangbin on 16/9/18.
 * mvp模式的model层里面，传递message时用来区分不同的数据
 */
public interface MvpHandlerType {
    
    // 登录model相关
    String LOGIN_DEMO = "LOGIN_DEMO";
}