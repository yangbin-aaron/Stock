package com.cainiu.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class YQBDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(2, "com.aaron.myviews.greendao");

        addKlineModel(schema);

        new DaoGenerator().generateAll(schema, "lib_publiccontent/src/main/java");
    }

    private static void addKlineModel(Schema schema) {
        Entity klineModel = schema.addEntity("KlineModel");
        klineModel.addIdProperty().autoincrement().primaryKey();
        klineModel.addFloatProperty("openPrice");
        klineModel.addFloatProperty("maxPrice");
        klineModel.addFloatProperty("closePrice");
        klineModel.addFloatProperty("minPrice");
        klineModel.addStringProperty("instrumentID");
        klineModel.addIntProperty("volume");
        klineModel.addStringProperty("time");
        klineModel.setHasKeepSections(true);
    }
}
