package com.aaron.myviews.model.newmodel;

import android.text.TextUtils;
import android.util.Log;

import com.aaron.myviews.model.chart.TrendViewModel;
import com.aaron.myviews.utils.FinancialUtil;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * /market/futureCommodity/select 大厅商品数据接口

 {
 "id": 9,
 "imgs": "http://adminstock.oss-cn-hangzhou.aliyuncs.com/2015-09-14_marketru.png",
 "marketCode": "CFFEX",//中国金融期货交易所 //CZCE-郑州商品交易所 SRPME-南方稀贵金属交易所 SHFE-上海期货交易所 SGX-新加坡交易所
 "commodityName": "天然橡胶",
 "instrumentID": "ru1601",
 "instrumentCode": "ru",
 "currency": "CNY",
 "multiple": 10.0,
 "decimalPlaces": 0,
 "commodityDesc": "09:00-23:00",
 "advertisement": "波动大，行情好抓",
 "vendibility": 1,
 "tag": 0,
 "timeTag": 2,
 "marketId": 9,
 "marketName": "新加坡交易所",
 "marketStatus": 1,
 "baseline": 6,
 "interval": 80,
 "isDoule": 0,
 "scale": "1",
 "timeAndNum": "09:00/76;10:30/61;13:30/151;15:00",
 "nightTimeAndNum": "21:00/241;01:00",
 "timeline": "09:00;10:15;10:30;11:30;13:30;15:00;21:00;23:00"
 "loddyType":
 "currencyName": "人民币"
 "currencySign": "￥"
 "currencyUnit": "元"
 "minPrice": 0.01
 }

 */
public class Product implements Serializable {

    public static final String CACHE_KEY_CASH = "product_cache_cash";
    public static final String CACHE_KEY_SCORE = "product_cache_score";
    public static final String KEY_PRODUCT = "product";

    public static final int ID_STOCK = 0; // 股票
    public static final int ID_AG = 2; // 沪银
    public static final int ID_AU = 1; // 沪金
    public static final int ID_IF = 3; // 期指
    public static final int ID_CU = 5; // 沪铜
    public static final int ID_AL = 6; // 沪铝
    public static final int ID_RB = 4; // 螺纹
    public static final int ID_NI = 8; // 沪镍
    public static final int ID_BU = 7; // 石油沥青
    public static final int ID_RU = 9; // 天然橡胶
    public static final int ID_CN = 1001; // A50
    public static final int ID_HSI = 1101; // 恒指
    public static final int ID_CL = 1002; // 美原油

    public static final String MARKET_CODE_SHFE = "SHFE";
    public static final String MARKET_CODE_DCE = "DCE";
    public static final String MARKET_CODE_CZCE = "CZCE";

    public static final int MARKET_STATUS_CLOSE = 0;
    public static final int MARKET_STATUS_OPEN = 1;

    public static final int VENDIBILITY_RED = 1;
    public static final int VENDIBILITY_GRAY = 0;

    public static final int TAG_NEW = 2;
    public static final int TAG_HOT = 1;
    public static final int TAG_NONE = 0;

    public static final int TIME_TAG_DAYTIME = 0;
    public static final int TIME_TAG_NIGHT = 1;
    public static final int TIME_TAG_ALL_DAY = 2;

    public static final float DEFAULT_PRICE_RANGE = 60;
    public static final int DEFAULT_PRICE_SCALE = 2;
    public static final float DEFAULT_MINIMUM_RANGE_FACTOR = 0.005f;

    public static final int LOBBY_CASH = 1; // 现金大厅
    public static final int LOBBY_SCORE = 2; // 积分大厅
    public static final int LOBBY_HOLIDAY = 3; // 周末模拟场

    public static final String CURRENCY_CNY = "CNY";
    public static final String CURRENCY_HKD = "HKD";
    public static final String CURRENCY_USD = "USD";

    private Integer id;
    private String imgs;
    private String marketCode;
    private String commodityName;
    private String instrumentID;
    private String instrumentCode;

    private String currency;
    private Double multiple;
    private Integer decimalPlaces;
    private String commodityDesc;
    private String advertisement;

    private Integer vendibility;
    private Integer tag;
    private Integer timeTag;
    private Integer marketId;
    private String marketName;
    private Integer marketStatus;

    private Integer baseline;
    private Float interval;
    private Integer isDoule;
    private Float scale;
    private String timeAndNum;
    private String nightTimeAndNum;
    private String timeline;
    
    private int loddyType;
    private String currencyName;
    private String currencySign;
    private String currencyUnit;
    private String accountCode;
    private double minPrice;

    public String getAccountCode() {
        if (!TextUtils.isEmpty(accountCode)) {
            return accountCode;
        }
        return "";
    }

    public Integer getId() {
        return id;
    }

    public String getImgs() {
        return imgs;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public String getInstrumentID() {
        return instrumentID;
    }

    public String getCommodityDesc() {
        return commodityDesc;
    }

    public String getAdvertisement() {
        return advertisement;
    }

    public Integer getVendibility() {
        if (vendibility != null) {
            return vendibility;
        }
        return VENDIBILITY_GRAY;
    }

    public Integer getTag() {
        return tag;
    }

    public String getInstrumentCode() {
        return instrumentCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getMultiple() {
        return multiple;
    }

    private Integer getDecimalPlaces() {
        return decimalPlaces;
    }

    public Integer getTimeTag() {
        if (timeTag != null) {
            return timeTag;
        }
        return TIME_TAG_ALL_DAY;
    }

    public Integer getMarketId() {
        return marketId;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public Integer getMarketStatus() {
        if (marketStatus != null) {
            return marketStatus;
        }
        return MARKET_STATUS_CLOSE;
    }

    public Integer getBaseline() {
        if (baseline != null) {
            return baseline;
        }
        return 6;
    }

    private Float getInterval() {
        if (interval != null) {
            return interval;
        }
        return DEFAULT_PRICE_RANGE;
    }

    public Boolean isDoubleLines() {
        if (isDoule != null) {
            return isDoule == 1;
        }
        return false;
    }

    private Float getScale() {
        return scale;
    }

    private String[] getTimeAndNum() {
        String[] result = new String[0];
        if (!TextUtils.isEmpty(timeAndNum)) {
            result = timeAndNum.split(";");
        }
        return result;
    }

    public String[] getNightTimeAndNum() {
        String[] result = new String[0];
        if (!TextUtils.isEmpty(nightTimeAndNum)) {
            result = nightTimeAndNum.split(";");
        }
        return result;
    }

    private String[] getTimeLines() {
        String[] result = new String[0];
        if (!TextUtils.isEmpty(timeline)) {
            result = timeline.split(";");
        }
        return result;
    }

    public boolean hasTag() {
        if (tag != null && tag != TAG_NONE) {
            return true;
        }
        return false;
    }

    public boolean isValidDate(String date) {
        String hhmm = date.substring(8, 10) + ":" + date.substring(10, 12);
        return isBetweenTimeLines(getTimeLines(), hhmm);
    }

    private boolean isBetweenTimeLines(String[] timeLines, String hhmm) {
        int size = timeLines.length;
        size = (size % 2 == 0 ? size : size - 1);

        for (int i = 0; i < size; i += 2) {
            if (isBetweenTimeStr(timeLines[i], timeLines[i + 1], hhmm)) {
                return true;
            }
        }

        Log.d("TEST", "hhmm: " + hhmm);

        return false;
    }

    private boolean isBetweenTimeStr(String time1, String time2, String time) {
        if (time1.compareTo(time2) <= 0) {
            return time.compareTo(time1) >= 0 && time.compareTo(time2) <= 0;
        } else {
            return time.compareTo(time1) >= 0 || time.compareTo(time2) <= 0;
        }
    }

    public boolean isNightData(TrendViewModel viewModel) {
        String nightStartDate = getNightStartTime();
        if (TextUtils.isEmpty(nightStartDate)) {
            return false;
        }

        if (viewModel.getHhmmDate().compareTo(nightStartDate) >= 0) {
            return true;
        }

        return false;
    }

    public float getPriceInterval() {
        return getInterval();
    }

    public int getPriceScale() {
        if (getDecimalPlaces() != null) {
            return getDecimalPlaces();
        }
        return DEFAULT_PRICE_SCALE;
    }

    public float getMinimumRangeFactor() {
        if (getScale() != null) {
            return getScale().floatValue() / 100;
        }
        return DEFAULT_MINIMUM_RANGE_FACTOR;
    }

    /**
     * 根据不同产品返回时间轴值的偏移量 offset
     * @return
     */
    public int[] getDayTimeLineOffset() {
        String[] timeAndNum = getTimeAndNum();
        int[] result = new int[timeAndNum.length];
        for (int i = 0; i < timeAndNum.length; i++) {
            String item = timeAndNum[i];
            if (item.indexOf("/") > -1) {
                String offset = item.substring(item.indexOf("/") + 1, item.length());
                if (i + 1 < timeAndNum.length) {
                    result[i + 1] = result[i] + Integer.valueOf(offset);
                }
            }
        }
        return result;
    }

    public String[] getTimeLine(boolean isAtNight) {
        String[] timeAndNum;
        if (isAtNight) {
            timeAndNum = getNightTimeAndNum();
        } else {
            timeAndNum = getTimeAndNum();
        }

        String[] result = new String[timeAndNum.length];
        for (int i = 0; i < timeAndNum.length; i++) {
            if (timeAndNum[i].indexOf("/") > -1) {
                result[i] = timeAndNum[i].substring(0, timeAndNum[i].indexOf("/"));
            } else {
                result[i] = timeAndNum[i];
            }
        }
        return result;
    }

    /**
     * 根据不同产品返回夜盘时间轴值的 offset
     * @return
     */
    public int[] getNightTimeLineOffset() {
        String[] nightTimeAndNum = getNightTimeAndNum();
        int[] result = new int[nightTimeAndNum.length];
        for (int i = 0; i < nightTimeAndNum.length; i++) {
            String item = nightTimeAndNum[i];
            if (item.indexOf("/") > -1) {
                String offset = item.substring(item.indexOf("/") + 1, item.length());
                if (i + 1 < nightTimeAndNum.length) {
                    result[i + 1] = result[i] + Integer.valueOf(offset);
                }
            }
        }
        return result;
    }

    public int getDayPointsNumber() {
        String[] timeAndNum = getTimeAndNum();
        int result = 0;
        for (int i = 0; i < timeAndNum.length; i++) {
            String item = timeAndNum[i];
            if (item.indexOf("/") > -1) {
                String offset = item.substring(item.indexOf("/") + 1, item.length());
                result += Integer.valueOf(offset);
            }
        }
        return result;
    }

    public int getNightPointsNumber() {
        String[] nightTimeAndNum = getNightTimeAndNum();
        int result = 0;
        for (int i = 0; i < nightTimeAndNum.length; i++) {
            String item = nightTimeAndNum[i];
            if (item.indexOf("/") > -1) {
                String offset = item.substring(item.indexOf("/") + 1, item.length());
                result += Integer.valueOf(offset);
            }
        }
        return result;
    }

    public String getNightStartTime() {
        String[] nightTimeAndNum = getNightTimeAndNum();
        if (nightTimeAndNum.length > 1 && nightTimeAndNum[0].indexOf("/") > -1) {
            return nightTimeAndNum[0].substring(0, nightTimeAndNum[0].indexOf("/"));
        }
        return null;
    }

    public int getIndexFromDate(String date, boolean isAtNight) {
        String[] timeLines = getTimeLines();
        int size = timeLines.length;
        size = (size % 2 == 0 ? size : size - 1);

        int index = 0;
        if (isAtNight) {
            if (size < 2) return 0;
            String startNightTime = timeLines[size - 2];
            String endNightTime = timeLines[size - 1];
            if (isBetweenTimeStr(startNightTime, endNightTime, date)) {
                index = getMinutes(startNightTime, date);
            }
        } else {
            for (int i = 0; i < size; i += 2) {
                if (isBetweenTimeStr(timeLines[i], timeLines[i + 1], date)) {
                    index = getMinutes(timeLines[i], date);
                    for (int j = 0; j < i; j += 2) {
                        // the total points of this period
                        index += getMinutes(timeLines[j], timeLines[j + 1]) + 1;
                    }
                }
            }
        }

        return index;
    }

    private int getMinutes(String startDate, String endDate) {
        long diff = 0;
        try {
            SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
            long start = parser.parse(startDate).getTime();
            long end = parser.parse(endDate).getTime();

            if (startDate.compareTo(endDate) <= 0) { // eg. 09:00 <= 09:10
                diff = end - start;
            } else { // eg. 21:00 ~ 01:00, we should change 01:00 to 25:00
                diff = end + 24 * 60 * 60 * 1000 - start;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return (int) (diff / (60 * 1000));
        }
    }

    /**
     * when marketCode is SHFE, DCE or CZCE, we should process the morning open market time
     * @return true specific market that need special treatment. false otherwise
     */
    public boolean isSpecificMarket() {
        if (!TextUtils.isEmpty(marketCode)) {
            return marketCode.equalsIgnoreCase(MARKET_CODE_SHFE) ||
                    marketCode.equalsIgnoreCase(MARKET_CODE_DCE) ||
                    marketCode.equalsIgnoreCase(MARKET_CODE_CZCE);
        }
        return false;
    }

    /**
     * 是否是期货
     * @return
     */
    public boolean isCashCommodity() {
        return getInstrumentCode().endsWith("XH");
    }

    public int getLoddyType() {
        return loddyType;
    }

    /**
     * 返回当前产品对应的货币单位
     * @return ’元/美元/港币‘
     */
    public String getCurrencyUnit() {
        if (!TextUtils.isEmpty(currencyUnit)) {
            return currencyUnit;
        }
        return FinancialUtil.UNIT_YUAN;
    }

    /**
     * 返回当前产品对应的货币单位符号
     * @return ’￥\$\HK$‘
     */
    public String getCurrencySign() {
        if (!TextUtils.isEmpty(currencySign)) {
            return currencySign;
        }
        return FinancialUtil.UNIT_SIGN_CNY;
    }

    public boolean isForex() {
        if (currencyUnit != null && !currencyUnit.equals(FinancialUtil.UNIT_YUAN)) {
            return true;
        }
        return false;
    }

    public String getPriceChangePerJump() {
        if (isCashCommodity()) {
            return "每次跳动=" + FinancialUtil.accurateTheSecondDecimalPlace(multiple * minPrice)
                    + currencyUnit + " x 手数";
        }
        return "每次跳动=" + (int) (multiple * minPrice) + currencyUnit;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", imgs='" + imgs + '\'' +
                ", marketCode='" + marketCode + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", instrumentID='" + instrumentID + '\'' +
                ", instrumentCode='" + instrumentCode + '\'' +
                ", currency='" + currency + '\'' +
                ", multiple=" + multiple +
                ", decimalPlaces=" + decimalPlaces +
                ", commodityDesc='" + commodityDesc + '\'' +
                ", advertisement='" + advertisement + '\'' +
                ", vendibility=" + vendibility +
                ", tag=" + tag +
                ", timeTag=" + timeTag +
                ", marketId=" + marketId +
                ", marketName='" + marketName + '\'' +
                ", marketStatus=" + marketStatus +
                ", baseline=" + baseline +
                ", interval=" + interval +
                ", isDoule=" + isDoule +
                ", scale=" + scale +
                ", timeAndNum='" + timeAndNum + '\'' +
                ", nightTimeAndNum='" + nightTimeAndNum + '\'' +
                ", timeline='" + timeline + '\'' +
                '}';
    }
}
