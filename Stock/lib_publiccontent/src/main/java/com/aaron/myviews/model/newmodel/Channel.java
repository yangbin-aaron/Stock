package com.aaron.myviews.model.newmodel;

public class Channel {

    private String channelNumber;
    private String channelStr;

    public Channel(String channelNumber, String channelStr) {
        this.channelNumber = channelNumber;
        this.channelStr = channelStr;
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public String getChannelStr() {
        return channelStr;
    }
}
