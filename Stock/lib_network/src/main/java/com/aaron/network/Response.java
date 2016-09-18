package com.aaron.network;

public class Response<T> {

    private Integer code;
    private String msg;
    private Integer msgType;
    private String errparam;
    private T data;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(){
        msg = "";
    }

    public Integer getMsgType() {
        return msgType;
    }

    public String getErrparam() {
        return errparam;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        if (code != null && code.equals(RequestConfig.ResponseCode.HTTP_SUCCESS)) {
            return true;
        }
        return false;
    }

    public boolean hasData() {
        return data != null;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", errparam='" + errparam + '\'' +
                ", data=" + data +
                '}';
    }
}
