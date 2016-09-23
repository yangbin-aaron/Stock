package com.aaron.myviews.model.newmodel.account;

public class CheckStatus {

    public static final int STATUS_BINDING  = 1;
    public static final int STATUS_NONE = 0;
    public static final int STATUS_FILL_IN = 2;

    private int status;

    public int getStatus() {
        return status;
    }
}
