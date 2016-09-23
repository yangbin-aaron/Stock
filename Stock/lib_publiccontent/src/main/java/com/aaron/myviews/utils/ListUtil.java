package com.aaron.myviews.utils;

import java.util.List;

public class ListUtil {

    public static boolean isNotEmpty(List list) {
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(List list) {
        return !isNotEmpty(list);
    }
}
