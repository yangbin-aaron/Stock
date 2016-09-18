package com.aaron.mvp.presenter;

import com.aaron.mvp.OnFinishedListener;
import com.aaron.mvp.model.ImpModelLogin;
import com.aaron.mvp.view.ILoginView;

/**
 * Created by yangbin on 16/9/18.
 */
public class ImpPresenterLogin implements OnFinishedListener {

    private ImpModelLogin mImpModelLogin;
    private ILoginView mILoginView;

    public ImpPresenterLogin(ILoginView ILoginView) {
        mILoginView = ILoginView;
        mImpModelLogin = new ImpModelLogin();
    }

    @Override
    public void onFinished(Object object) {

    }
}
