package com.aaron.myviews.factory;

import com.aaron.myviews.model.Product;
import com.aaron.myviews.utils.FinancialUtil;

/**
 * TU(UnitThousandSeparator)ProductPriceConvertor：即带前置单位和千分位的产品价格转换器.
 */
public class UTProductPriceConvertor extends ProductPriceConvertor{

    private final String mUnit;

    public UTProductPriceConvertor(Product product, String unit) {
        super(product);
        mUnit = unit;
    }

    @Override
    public String convert(double value) {
        //super.convert(value)必须是可以可以格式化的数字，否则可能会发生NumberFormat异常
        return mUnit+ FinancialUtil.formatWithThousandsSeparator(super.convert(value));
    }
}
