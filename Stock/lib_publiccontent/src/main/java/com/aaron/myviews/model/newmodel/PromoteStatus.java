package com.aaron.myviews.model.newmodel;

/**
 * Created by Administrator on 2015/10/26.
 */
public class PromoteStatus{
    Integer isPromote;

    public Integer getIsPromote() {
        return isPromote;
    }

    public boolean isPromoter() {
        if (isPromote != null && isPromote == 3) {
            return true;
        }
        return false;
    }
}