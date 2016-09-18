package com.aaron.mvp.model;

import com.aaron.mvp.OnFinishedListener;

/**
 * Created by yangbin on 16/9/18.
 */
public class ImpModelLogin {

    private OnFinishedListener mOnFinishedListener;
    

    public void mVoid(OnFinishedListener onFinishedListener) {
        mOnFinishedListener = onFinishedListener;
    }
}
