package com.aaron.myviews.factory;

import com.aaron.myviews.utils.TextUtil;

/**
 * Created by bvin on 2015/11/4.
 */
public class SpacePhoneNumber implements TextConvertor{
    @Override
    public String convert(String source) {
        return TextUtil.formatPhoneNumberWithSpace(source);
    }
}
