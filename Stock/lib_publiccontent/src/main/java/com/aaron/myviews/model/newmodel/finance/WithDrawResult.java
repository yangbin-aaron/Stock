package com.aaron.myviews.model.newmodel.finance;

import java.io.Serializable;

/**
 * Created by bvin on 2015/10/27.
 */
public class WithDrawResult implements Serializable{

    private String id;
    private double inOutAmt;
    private double factAmt;
    private String userName;
    private double factTax;
    private String bank;

    public String getId() {
        return id;
    }

    public double getInOutAmt() {
        return inOutAmt;
    }

    public double getFactAmt() {
        return factAmt;
    }

    public String getUserName() {
        return userName;
    }

    public double getFactTax() {
        return factTax;
    }

    public String getBank() {
        return bank;
    }
}
