package com.aaron.myviews.factory;

/**
 * Created by bvin on 2015/11/4.
 */
public interface TextLighter {
    /**
     * 满足某个条件就亮
     * @param origin 输入的字符串
     * @return 是否点亮
     */
    public boolean light(String origin);
}
