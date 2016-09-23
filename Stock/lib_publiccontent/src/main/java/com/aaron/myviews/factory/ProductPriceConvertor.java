package com.aaron.myviews.factory;

import com.aaron.myviews.model.Product;
import com.aaron.myviews.utils.FinancialUtil;

/**
 * 产品价格转换器.
 */
public class ProductPriceConvertor implements NumberConvertor{

    private Product mProduct;

    public ProductPriceConvertor(Product product) {
        mProduct = product;
    }

    @Override
    public String convert(double value) {
        return FinancialUtil.formatPriceBasedOnFuturesType(value, mProduct);
    }
}
