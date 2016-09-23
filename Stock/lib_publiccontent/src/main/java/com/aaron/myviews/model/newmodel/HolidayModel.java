package com.aaron.myviews.model.newmodel;

public class HolidayModel {

    public static final String HOLIDAY = "1";

    private String holiday;

    public boolean isHoliday() {
        if (holiday != null) {
            return holiday.equals(HOLIDAY);
        }
        return false;
    }
}
