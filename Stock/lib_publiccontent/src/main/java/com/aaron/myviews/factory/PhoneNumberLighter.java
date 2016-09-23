package com.aaron.myviews.factory;

import com.aaron.myviews.utils.Validator;

/**
 * Created by bvin on 2015/11/4.
 */
public class PhoneNumberLighter implements TextLighter{

    @Override
    public boolean light(String origin) {
        return Validator.isMobileNumber(origin);
    }

}
