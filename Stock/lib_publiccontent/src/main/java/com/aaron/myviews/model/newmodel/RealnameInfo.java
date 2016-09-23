package com.aaron.myviews.model.newmodel;


import com.aaron.myviews.model.newmodel.account.CheckUsername;

/**
 * Created by bvin on 2015/10/21.
 */
public class RealnameInfo extends CheckUsername {

    /**
     * 是否有效
     * @return true意味着已绑定或已记录，false表示未记录
     */
    public boolean isValid() {
        Integer status = getStatus();
        if (status!=null) {
            if (status==1||status==2) {
                return true;
            }
            if (status==0) {
                return false;
            }
        }
        return false;
    }
}
