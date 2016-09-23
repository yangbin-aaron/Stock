package com.aaron.yqb.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aaron.myviews.model.Product;
import com.aaron.myviews.model.newmodel.ProductQuotation;
import com.aaron.myviews.model.newmodel.Response;
import com.aaron.myviews.model.newmodel.local.PositionOrderCount;
import com.aaron.myviews.model.newmodel.local.ProductListItem;
import com.aaron.myviews.transformation.GrayscaleTransformation;
import com.aaron.myviews.utils.FinancialUtil;
import com.aaron.yqb.R;
import com.aaron.yqb.config.ApiConfig;
import com.aaron.yqb.network.RequestBuilder;
import com.aaron.yqb.network.SimpleErrorListener;
import com.aaron.yqb.network.http.HttpKeys;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private List<ProductListItem> productList;
    private Context context;

    public ProductAdapter(Context context) {
        this.context = context;
        this.productList = new ArrayList<>();
    }

    public ProductAdapter(List<ProductListItem> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public void setProductList(List<ProductListItem> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_product_list, null);
            viewHolder = new ViewHolder();
            viewHolder.productIcon = (ImageView) convertView.findViewById(R.id.product_icon);
            viewHolder.holdingIcon = (ImageView) convertView.findViewById(R.id.holding_icon);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.product_name);
            viewHolder.advertisement = (TextView) convertView.findViewById(R.id.advertisement);
            viewHolder.quotation = (TextView) convertView.findViewById(R.id.quotation);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindingData(viewHolder, position, parent);
        return convertView;
    }

    private void bindingData(ViewHolder viewHolder, int position, ViewGroup parent) {
        ProductListItem item = (ProductListItem) getItem(position);
        Product product = item.getProduct();
        PositionOrderCount orderCount = item.getOrderCount();
        ProductQuotation quotation = item.getQuotation();

        if (TextUtils.isEmpty(product.getImgs())) {
            Picasso.with(context).load(R.drawable.ic_stock_inactive)
                    .into(viewHolder.productIcon);
        } else {
            if (product.getVendibility() == Product.VENDIBILITY_RED) {
                Picasso.with(context).load(product.getImgs())
                        .placeholder(R.drawable.ic_stock_inactive)
                        .into(viewHolder.productIcon);
            } else {
                Picasso.with(context).load(product.getImgs())
                        .transform(new GrayscaleTransformation())
                        .placeholder(R.drawable.ic_stock_inactive)
                        .into(viewHolder.productIcon);
            }
        }

        if (orderCount != null && orderCount.hasCashOrders()) {
            viewHolder.holdingIcon.setVisibility(View.VISIBLE);
        } else {
            viewHolder.holdingIcon.setVisibility(View.INVISIBLE);
        }

        viewHolder.productName.setText(product.getCommodityName());
        viewHolder.advertisement.setText(product.getAdvertisement());

        if (quotation != null) {
            setQuotationView(viewHolder.quotation, quotation, product);
        } else {
            requestQuotation(position, item, parent);
        }
    }

    public void requestQuotation(final int position, final ProductListItem item, final ViewGroup parent) {
        final ListView listView = (ListView) parent;
        new RequestBuilder().url(ApiConfig.getFullUrl(ApiConfig.ApiURL.PRODUCT_QUOTATION))
                .put(HttpKeys.FUTURES_TYPE, item.getProduct().getInstrumentCode())
                .type(new TypeToken<Response<ProductQuotation>>() {
                }.getType())
                .listener(new com.android.volley.Response.Listener<Response<ProductQuotation>>() {
                    @Override
                    public void onResponse(Response<ProductQuotation> response) {
                        if (response == null) return;
                        if (response.isSuccess()) {
                            updateOneItemView(position, item, listView, response.getData());
                        }
                    }
                }).errorListener(new SimpleErrorListener(false))
                .create().send();
    }

    private void updateOneItemView(int position, ProductListItem item, ListView listView, ProductQuotation data) {
        item.setQuotation(data);
        View itemView = listView.getChildAt(position - listView.getFirstVisiblePosition() + 1);
        // header is the first child, so + 1
        if (itemView == null) {
            return;
        }
        TextView quotationView = (TextView) itemView.findViewById(R.id.quotation);
        setQuotationView(quotationView, data, item.getProduct());
    }

    private void setQuotationView(TextView quotationView, ProductQuotation quotation, Product product) {
        String quotationStr = "— —\n— %";
        String quotationPercentage = "";
        if (quotation != null) {
            quotationStr = FinancialUtil.formatPriceBasedOnFuturesType(quotation.getLastPrice(), product)
                            + "\n" + quotation.getPercentage();
            quotationPercentage = quotation.getPercentage();
        }
        quotationView.setText(quotationStr);
        if (product.getMarketStatus() == Product.MARKET_STATUS_OPEN) {
            if (quotationPercentage.startsWith("+")) {
                quotationView.setTextColor(context.getResources().getColor(R.color.quotation_red));
            } else if (quotationPercentage.startsWith("-")) {
                quotationView.setTextColor(context.getResources().getColor(R.color.quotation_green));
            } else {
                quotationView.setTextColor(context.getResources().getColor(R.color.quotation_gray));
            }
        } else {
            quotationView.setTextColor(context.getResources().getColor(R.color.quotation_gray));
        }
    }

    private class ViewHolder {
        ImageView productIcon;
        ImageView holdingIcon;
        TextView productName;
        TextView advertisement;
        TextView quotation;
    }
}

