
/** 
 * @Title: HoldingFuturesModel.java 
 * @Package com.luckin.magnifier.model.gson 
 * @ClassName: HoldingFuturesModel
 *
 * @author 于泽坤 
 * @date 2015-7-29 下午1:58:13 
*/
 
package com.aaron.myviews.model.gson;

import java.util.ArrayList;

/** 
 * 期货持仓
 */
public class HoldingFuturesModel {

    private String score;
    private String usedAmt;
    
    private ArrayList<FuturesModel> futuresScoreOrderList;
    private ArrayList<FuturesModel> futuresCashOrderList;

    public String getScore() {
        return score;
    }

    public String getUsedAmt() {
        return usedAmt;
    }

    public ArrayList<FuturesModel> getFuturesScoreOrderList() {
        return futuresScoreOrderList;
    }

    public ArrayList<FuturesModel> getFuturesCashOrderList() {
        return futuresCashOrderList;
    }
}

