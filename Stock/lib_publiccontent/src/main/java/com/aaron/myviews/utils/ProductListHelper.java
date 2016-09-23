package com.aaron.myviews.utils;


import com.aaron.myviews.model.newmodel.local.ProductListItem;

import java.util.List;

/**
 * 此类为单例模式,用于ProductList的共享数据
 *
 * Created by Yuan on 16/2/23.
 */
public class ProductListHelper {

    private static ProductListHelper helper = null;

    private List<ProductListItem> mProductList;

    private ProductListHelper() {
    }

    public static ProductListHelper getInstance() {
        if (helper == null) {
            helper = new ProductListHelper();
        }
        return helper;
    }

    public void setProductList(List<ProductListItem> list){
        this.mProductList = list;
    }

    public List<ProductListItem> getProductList(){
        return mProductList;
    }

}
