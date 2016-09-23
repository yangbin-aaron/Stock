package com.aaron.myviews.model.newmodel.commodity;

/**
 * Created by bvin on 2016/1/5.
 */
public class AvailableAmount {

    private String amount;

    public int getCount() {
        int count = 0;
        try {
            count = Integer.valueOf(amount);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return count;
    }
}
