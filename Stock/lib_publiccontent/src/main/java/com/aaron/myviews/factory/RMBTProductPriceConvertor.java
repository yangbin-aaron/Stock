package com.aaron.myviews.factory;

import com.aaron.myviews.model.Product;

/**
 * RMBT(RMBThousandSeparator)ProductPriceConvertor：即带前置人民币单位和千分位的产品价格转换器.
 */
public class RMBTProductPriceConvertor extends UTProductPriceConvertor {
    public RMBTProductPriceConvertor(Product product) {
        super(product, "￥");
    }
}
