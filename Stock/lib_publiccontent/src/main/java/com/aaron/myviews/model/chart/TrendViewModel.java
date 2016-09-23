package com.aaron.myviews.model.chart;

import android.util.Log;

import com.aaron.myviews.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class TrendViewModel {

    private String instrumentID;
    private Float lastPrice;
    private String date; //yyyyMMddHHmmss

    public String getInstrumentID() {
        return instrumentID;
    }

    public Float getLastPrice() {
        return lastPrice;
    }

    public String getDate() {
        return date;
    }

    public String getHhmmDate() {
        return date.substring(8, 10) + ":" + date.substring(10, 12);
    }

    public TrendViewModel(String instrumentID, String lastPrice, String date) {
        this.instrumentID = instrumentID;
        this.lastPrice = new BigDecimal(lastPrice)
                .setScale(2, RoundingMode.HALF_EVEN).floatValue();
        this.date = date;
    }

    public TrendViewModel(TrendViewModel chartModel, Double lastPrice) {
        this.instrumentID = chartModel.getInstrumentID();
        this.lastPrice = lastPrice.floatValue();
        this.date = addOneMinute(chartModel.getDate());
    }

    private static String addOneMinute(String date) {
        if (date != null && date.length() == 14) {
            SimpleDateFormat parser = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
                long originDate = parser.parse(date).getTime();
                long finalDate = originDate + 60 * 1000; // 1 min
                return parser.format(new Date(finalDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static List<TrendViewModel> createListData(String data, Product product) {
        List<TrendViewModel> result = new ArrayList<TrendViewModel>();
        HashSet hashSet = new HashSet();
        int length = data.length();
        int start = 0;
        while (start < length) {
            int end = data.indexOf("|", start);
            if (end > start) {
                String single = data.substring(start, end);
                String[] splitData = single.split(",");
                String modelDate = splitData[2];
                TrendViewModel model = new TrendViewModel(splitData[0], splitData[1], modelDate);
                start = end + 1;
                // filter the same data and the invalid data
                if (isValidDate(modelDate, product, hashSet)) {
                    result.add(model);
                }
            }
        }
        Log.d("TEST", "hashSet.size: " + hashSet.size());
        return result;
    }

    private static boolean isValidDate(String modelDate, Product product, HashSet hashSet) {
        if (modelDate.length() != 14) return false;

        // yyyyMMddhhmmss -> hhmm
        String dateWithHourMinute = modelDate.substring(8, 12);
        if (!hashSet.add(dateWithHourMinute)) {
            return false;
        }

        if (product != null) {
            return product.isValidDate(modelDate);
        }

        return false;
    }

    @Override
    public String toString() {
        return "LineChartViewModel{" +
                "instrumentID='" + instrumentID + '\'' +
                ", lastPrice=" + lastPrice +
                ", date='" + date + '\'' +
                '}';
    }
}
