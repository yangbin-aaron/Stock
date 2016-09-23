package com.aaron.yqb.local;


public class ProductListItem {

//    private Product product;
//    private PositionOrderCount orderCount;
//    private ProductQuotation quotation;
//
//    public ProductListItem(Product product) {
//        this.product = product;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public PositionOrderCount getOrderCount() {
//        return orderCount;
//    }
//
//    public ProductQuotation getQuotation() {
//        return quotation;
//    }
//
//    public void setOrderCount(PositionOrderCount orderCount) {
//        this.orderCount = orderCount;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public void setQuotation(ProductQuotation quotation) {
//        this.quotation = quotation;
//    }
//
//    public static void updateProductList(List<ProductListItem> productList, List<Product> products,
//                                         List<PositionOrderCount> futuresCounts,
//                                         List<PositionOrderCount> commodityCounts) {
//        for (int i = 0; products != null && i < products.size(); i++) {
//            Product product = products.get(i);
//            ProductListItem item = new ProductListItem(product);
//            for (int j = 0; futuresCounts != null && j < futuresCounts.size(); j++) {
//                PositionOrderCount count = futuresCounts.get(j);
//                if (product.getInstrumentCode().equalsIgnoreCase(count.getInstrumentCode())) {
//                    item.setOrderCount(count);
//                    break;
//                }
//            }
//            for (int j = 0; commodityCounts != null && j< commodityCounts.size(); j++) {
//                PositionOrderCount count = commodityCounts.get(j);
//                if (product.getInstrumentCode().equalsIgnoreCase(count.getInstrumentCode())) {
//                    item.setOrderCount(count);
//                    break;
//                }
//            }
//            productList.add(item);
//        }
//    }
//
//    public static void updateProductList(List<ProductListItem> productList,
//                                         List<PositionOrderCount> counts) {
//        updateProductList(productList, counts, false);
//    }
//
//    public static void updateProductList(List<ProductListItem> productList,
//                                         List<PositionOrderCount> counts, boolean cashCommodity) {
//        for (int i = 0; i < productList.size(); i++) {
//            ProductListItem item = productList.get(i);
//            Product product = item.getProduct();
//
//            if (!cashCommodity && product.isCashCommodity()) continue;
//            if (cashCommodity && !product.isCashCommodity()) continue;
//
//            for (int j = 0; counts != null && j < counts.size(); j++) {
//                PositionOrderCount count = counts.get(j);
//                if (product.getInstrumentCode().equalsIgnoreCase(count.getInstrumentCode())) {
//                    item.setOrderCount(count);
//                    break;
//                }
//            }
//        }
//    }

//    public static void removePositionCounts(List<ProductListItem> productList) {
//        for (int i = 0; i < productList.size(); i++) {
//            ProductListItem item = productList.get(i);
//            item.setOrderCount(null);
//        }
//    }
//
//    public static void removeCommodityCounts(List<ProductListItem> productList) {
//        for (int i = 0; i < productList.size(); i++) {
//            ProductListItem item = productList.get(i);
//            Product product = item.getProduct();
//            if (product.isCashCommodity()) {
//                item.setOrderCount(null);
//            }
//        }
//    }
}
