package com.aaron.myviews.model.newmodel.futures;

/**
 * futuresquota/getQuotaUrl
 */
public class FuturesQuotaServer {

    private String port;
    private String ip;

    public String getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return "FuturesQuotaServer{" +
                "port='" + port + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
