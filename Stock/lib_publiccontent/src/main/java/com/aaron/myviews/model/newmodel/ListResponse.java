package com.aaron.myviews.model.newmodel;


import com.aaron.myviews.config.RequestConfig;

import java.util.List;

public class ListResponse<T> {

    private Integer code;
    private String msg;
    private String errparam;
    private List<T> data;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(){
        msg = "";
    }

    public String getErrparam() {
        return errparam;
    }

    public List<T> getData() {
        return data;
    }

    public boolean hasData() {
        if (data != null && data.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean isSuccess() {
        if (code != null && code.equals(RequestConfig.ResponseCode.HTTP_SUCCESS)) {
            return true;
        }
        return false;
    }
}
