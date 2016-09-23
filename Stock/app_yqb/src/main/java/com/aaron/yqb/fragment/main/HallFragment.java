package com.aaron.yqb.fragment.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaron.myviews.AppPrefs;
import com.aaron.myviews.dialog.ProgressDialog;
import com.aaron.myviews.model.Product;
import com.aaron.myviews.model.gson.NewsNoticeModel;
import com.aaron.myviews.model.newmodel.HolidayModel;
import com.aaron.myviews.model.newmodel.ListResponse;
import com.aaron.myviews.model.newmodel.Response;
import com.aaron.myviews.model.newmodel.local.PositionOrderCount;
import com.aaron.myviews.model.newmodel.local.ProductListItem;
import com.aaron.myviews.utils.DateUtil;
import com.aaron.myviews.utils.ListUtil;
import com.aaron.myviews.utils.ProductListHelper;
import com.aaron.myviews.utils.Storage;
import com.aaron.myviews.utils.ToastUtil;
import com.aaron.myviews.view.AdsBanner;
import com.aaron.myviews.view.TitleBar;
import com.aaron.yqb.R;
import com.aaron.yqb.activity.MainActivity;
import com.aaron.yqb.adapters.ProductAdapter;
import com.aaron.yqb.config.ApiConfig;
import com.aaron.yqb.fragment.BaseFragment;
import com.aaron.yqb.network.RequestBuilder;
import com.aaron.yqb.network.SimpleErrorListener;
import com.aaron.yqb.network.http.HttpKeys;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HallFragment extends BaseFragment implements MainActivity.OnMainPageChangeListener {

    private static int HALL_INDEX = MainActivity.TAB_TYPE.TAB_HALL;
    private final static int PERIOD = 5 * 1000; // 5s

    private MainActivity mActivity;

    private AdsBanner mAdsBanner;
    private ListView mListView;
    private ProductAdapter mProductAdapter;

    private List<ProductListItem> mProductList;
    private List<PositionOrderCount> mFuturesCounts;
    private List<PositionOrderCount> mCommodityCounts;

    private NewsNoticeModel mNewNoticeModel;
    private HolidayModel mHolidayModel;

    private TitleBar mTitleBar;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (getUserVisibleHint() && isResumed()) {
                mAdsBanner.next();
                updateProductQuotations();
            }
        }
    };

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.simulated_practice:
//                    SimulationPracticeActivity.enter(getActivity());
                    break;
                case R.id.get_cash_free:
                    openTaskCenter();
                    break;
                case R.id.holiday_game:
//                    HolidayGameActivity.enter(getActivity());
                    break;
            }
        }
    };

    private void openTaskCenter() {
//        if (UserInfoManager.getInstance().isLogin()) {
//            if (UserInfoManager.getInstance().hasActivatedCainiuAccount()) {
//                WebActivity.openTaskCenter(getActivity());
//            } else {
//                showActivateDialog();
//            }
//        } else {
//            showLoginFirstDialog();
//        }
    }

    private void updateProductQuotations() {
        if (mListView != null && mProductAdapter != null) {
            int first = mListView.getFirstVisiblePosition();
            int last = mListView.getLastVisiblePosition();
            for (int i = first; i <= last; i++) {
                if (i == 0) continue; // Header
                ProductListItem item = (ProductListItem) mProductAdapter.getItem(i - 1);
                if (item.getProduct().getMarketStatus() == Product.MARKET_STATUS_OPEN) {
                    mProductAdapter.requestQuotation(i - 1, item, mListView);
                }
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            mActivity = (MainActivity) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            activity.addMainPageChangeListener(HALL_INDEX, this);
        }
    }

    @Override
    public void pageChange(int index) {
        if (index == HALL_INDEX) {
            requestNewsURL();
            requestProductList();
            requestFuturesPositionOrderCount();
            requestCommodityPositionOrderCount();
            requestPopupAdsURL();

            updateBottomView();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hall, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();

        updateViewsFromCache();

        startScheduleJob(mHandler, PERIOD, PERIOD);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isAdded()) {
            updatePopupAdsView();
            updateHolidayGamePopupView();
        }
    }

    private void updateViewsFromCache() {
        NewsNoticeModel model = (NewsNoticeModel) Storage.getInstance(getActivity())
                .readFromCache(NewsNoticeModel.CACHE_KEY, NewsNoticeModel.class);
        updateNewsNotice(model);

        List<Product> productList = (List<Product>) Storage.getInstance(getActivity())
                .readFromCache(Product.CACHE_KEY_CASH, new TypeToken<List<Product>>() {
                }.getType());
        ProductListItem.updateProductList(mProductList, productList, mFuturesCounts, mCommodityCounts);
        ProductListHelper.getInstance().setProductList(mProductList);
        updateProductListView();

        mNewNoticeModel = (NewsNoticeModel) Storage.getInstance(getActivity())
                .readFromCache(NewsNoticeModel.CACHE_KEY_HOME_AD, NewsNoticeModel.class);
        updatePopupAdsView();
    }

    private void initViews() {
        mListView = (ListView) getView().findViewById(android.R.id.list);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.header_hall_list, null);
        mAdsBanner = (AdsBanner) view.findViewById(R.id.ads_banner);
        mAdsBanner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsNoticeModel.New newNotice = (NewsNoticeModel.New) parent.getItemAtPosition(position);
                if (newNotice != null && mActivity != null) {
//                    WebActivity.openAdBannerForResult(mActivity, newNotice, ActivityConfig.RequestCode.TASK_CENTER);
                    //WebViewActivity.openTaskCenter(mActivity, newNotice.getFullUrl(), newNotice.getTitle());
                }
            }
        });
        view.findViewById(R.id.simulated_practice).setOnClickListener(mClickListener);
        view.findViewById(R.id.get_cash_free).setOnClickListener(mClickListener);
        view.findViewById(R.id.holiday_game).setOnClickListener(mClickListener);
        mListView.addHeaderView(view);

        mProductAdapter = new ProductAdapter(mActivity);
        mProductList = new ArrayList<>();
        mListView.setAdapter(mProductAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductListItem item = (ProductListItem) parent.getItemAtPosition(position);
                if (item != null) {
                    Product product = item.getProduct();
                    if (product.getVendibility() == Product.VENDIBILITY_RED) {
                        if (product.getId() == Product.ID_STOCK) {
//                            Intent intent = new Intent(mActivity, StockHoldingActivity.class);
//                            mActivity.startActivityForResult(intent, 0);
                        } else {
                            requestFuturesQuotationServer(item);
                        }
                    } else {
                        showAlertDialog(R.string.hall_coming_soon);
                    }
                }
            }
        });

        mTitleBar = (TitleBar) getView().findViewById(R.id.title_bar);
        setTitleBarRightDrawableVisibility(true);
        mTitleBar.setOnButtonsClickListener(new TitleBar.OnRightButtonClickListener() {
            @Override
            public void onRightClick(View view) {
                if (mActivity != null) {
                    mActivity.showPopWindows();
                }
            }
        });
        mTitleBar.setOnBackPressedListener(new TitleBar.OnBackPressedListener() {
            @Override
            public void onBackPressed(View view) {
//                WebViewActivity.openWeb(getActivity(),
//                        getString(R.string.customer_service_title),
//                        ApiConfig.getFullUrl(ApiConfig.ApiURL.CUSTOMER_SERVICE));
//                WebActivity.openNewWebActivity(getActivity(),
//                        ApiConfig.getFullUrl(ApiConfig.ApiURL.CUSTOMER_SERVICE),
//                        false, true);
            }
        });
    }

    public void setTitleBarRightDrawableVisibility(boolean isVisibility) {
        mTitleBar.setRightDrawableGone(isVisibility);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestNewsURL();
        requestProductList();
        requestFuturesPositionOrderCount();
        requestCommodityPositionOrderCount();
        requestPopupAdsURL();

        updateBottomView();
    }

    private void updateBottomView() {
        TextView accountSecurity = (TextView) getView().findViewById(R.id.tv_account_security);
        RelativeLayout parentLayout = (RelativeLayout) accountSecurity.getParent();
        //  accountSecurity.setOnClickListener(mClikcListener);
        accountSecurity.setVisibility(View.VISIBLE);
        accountSecurity.setText(Html.fromHtml(getString(R.string.account_security)));
        parentLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    /**
     * 请求弹出广告图片URL
     */
    private void requestPopupAdsURL() {
        new RequestBuilder()
                .url(ApiConfig.getFullUrl(ApiConfig.ApiURL.NEWS_LIST))
                .put(HttpKeys.TYPE, NewsNoticeModel.HOME_AD)
                .put(HttpKeys.PAGE_SIZE, "5")
                .type(new TypeToken<Response<NewsNoticeModel>>() {
                }.getType())
                .listener(new com.android.volley.Response.Listener<Response<NewsNoticeModel>>() {
                    @Override
                    public void onResponse(Response<NewsNoticeModel> response) {
                        if (response == null) return;
                        if (response.isSuccess()) {
                            mNewNoticeModel = response.getData();
                            Storage.getInstance(getActivity()).writeToCache(
                                    NewsNoticeModel.CACHE_KEY_HOME_AD, mNewNoticeModel);
                            updatePopupAdsView();
                        } else {
                            ToastUtil.showShortToastMsg(getActivity(), response.getMsg());
                        }

                        requestIsHoliday();
                    }
                }).errorListener(new SimpleErrorListener(false))
                .create().send(getRequestTag());
    }

    /**
     * 请求是否是节假日
     */
    private void requestIsHoliday() {
        long holidayGameTimestamp = AppPrefs.getInstance(getActivity()).getHolidayGamePopupTimestamp();
        long currentTimestamp = new Date().getTime();
        if (DateUtil.isEarlier(holidayGameTimestamp, currentTimestamp, 60 * 60 * 3)) {
            // 超过（包括）3小时需要显示下节假日大弹窗
            new RequestBuilder().url(ApiConfig.getFullUrl(ApiConfig.ApiURL.IS_HOLIDAY))
                    .type(new TypeToken<Response<HolidayModel>>() {
                    }.getType())
                    .listener(new com.android.volley.Response.Listener<Response<HolidayModel>>() {
                        @Override
                        public void onResponse(Response<HolidayModel> response) {
                            if (response == null) return;
                            if (response.isSuccess()) {
                                mHolidayModel = response.getData();
                                updateHolidayGamePopupView();
                            }
                        }
                    }).errorListener(new SimpleErrorListener())
                    .create().send(getRequestTag());
        }
    }

    private void updateHolidayGamePopupView() {
        if (mActivity.getCurFragmentIndex() != MainActivity.TAB_TYPE.TAB_HALL) return;

        if (mHolidayModel != null && mHolidayModel.isHoliday()) {
            long holidayGameTimestamp = AppPrefs.getInstance(getActivity()).getHolidayGamePopupTimestamp();
            long currentTimestamp = new Date().getTime();
            if (DateUtil.isEarlier(holidayGameTimestamp, currentTimestamp, 60 * 60 * 3)) {
                mActivity.showHolidayGamePopupWindows();
                AppPrefs.getInstance(getActivity()).setHolidayGamePopupTimestamp(currentTimestamp);
            }
        }
    }

    private void updatePopupAdsView() {
        if (mNewNoticeModel != null && !mNewNoticeModel.getNewsNoticeList().isEmpty()) {
            mActivity.updatePopupNewsNotice(mNewNoticeModel);
            updateHallFragmentTitleRightDrawableVisibility(false);
            if (AppPrefs.getInstance(getActivity()).shouldShowPopWindows(mNewNoticeModel)) {
                mActivity.showPopWindows();
            }
        } else {
            updateHallFragmentTitleRightDrawableVisibility(true);
        }
    }

    public void closePopupAdsView() {
        if (mNewNoticeModel != null && !mNewNoticeModel.getNewsNoticeList().isEmpty()) {
            AppPrefs.getInstance(getActivity()).closePopWindows(mNewNoticeModel);
        }
    }

    private void updateHallFragmentTitleRightDrawableVisibility(boolean isVisibility) {
        if (mActivity != null
                && mActivity.getCurFragmentIndex() == MainActivity.TAB_TYPE.TAB_HALL) {
            setTitleBarRightDrawableVisibility(isVisibility);
        }
    }

    private void requestNewsURL() {
        new RequestBuilder()
                .url(ApiConfig.getFullUrl(ApiConfig.ApiURL.NEWS_LIST))
                .put(HttpKeys.TYPE, NewsNoticeModel.AD)
                .put(HttpKeys.PAGE_SIZE, "8")
                .type(new TypeToken<Response<NewsNoticeModel>>() {
                }.getType())
                .listener(new com.android.volley.Response.Listener<Response<NewsNoticeModel>>() {
                    @Override
                    public void onResponse(Response<NewsNoticeModel> response) {
                        if (response.isSuccess()) {
                            NewsNoticeModel newsNoticeModel = response.getData();
                            updateNewsNotice(newsNoticeModel);
                            Storage.getInstance(getActivity()).writeToCache(NewsNoticeModel.CACHE_KEY, newsNoticeModel);
                        } else {
                            ToastUtil.showShortToastMsg(getActivity(), response.getMsg());
                        }
                    }
                }).errorListener(new SimpleErrorListener(false))
                .create().send(getRequestTag());
    }

    private void updateNewsNotice(NewsNoticeModel newsNoticeModel) {
        if (newsNoticeModel != null && getView() != null) {
            List<NewsNoticeModel.New> newList = newsNoticeModel.getNewsNoticeList();
            if (ListUtil.isNotEmpty(newList)) {
                AdsBanner.AdsAdapter adsAdapter = new AdsBanner.AdsAdapter(mActivity, false, ApiConfig.getHost());
                adsAdapter.setNewList(newList);
                mAdsBanner.setAdsAdapter(adsAdapter);
            }
        }
    }

    private void requestFuturesPositionOrderCount() {
//        if (!UserInfoManager.getInstance().isLogin()) {
//            if (ListUtil.isNotEmpty(mProductList)) {
//                mFuturesCounts = null;
//                mCommodityCounts = null;
//                ProductListItem.removePositionCounts(mProductList);
//                ProductListHelper.getInstance().setProductList(mProductList);
//                updateProductListView();
//            }
//            return;
//        }

//        new RequestBuilder()
//                .url(ApiConfig.getFullUrl(ApiConfig.ApiURL.FUTURES_POSITION_COUNT))
//                .put(HttpKeys.TOKEN, UserInfoManager.getInstance().getToken())
//                .put(HttpKeys.VERSION, 20150915)
//                .type(new TypeToken<ListResponse<PositionOrderCount>>() {
//                }.getType())
//                .listener(new com.android.volley.Response.Listener<ListResponse<PositionOrderCount>>() {
//                    @Override
//                    public void onResponse(ListResponse<PositionOrderCount> response) {
//                        if (response != null) {
//                            if (response.isSuccess()) {
//                                mFuturesCounts = response.getData();
//                                if (ListUtil.isNotEmpty(mProductList)) {
//                                    ProductListItem.updateProductList(mProductList, mFuturesCounts);
//                                    ProductListHelper.getInstance().setProductList(mProductList);
//                                    updateProductListView();
//                                }
//                            } else {
//                                ToastUtil.showShortToastMsg(response.getMsg());
//                            }
//                        }
//                    }
//                }).errorListener(new SimpleErrorListener())
//                .create().send(getRequestTag());
    }

    private void requestCommodityPositionOrderCount() {
//        if (UserInfoManager.getInstance().isLogin()) {
//            String secret = UserInfoManager.getInstance().getUserSecret();
//            CommodityUser commodityUser = CommodityUser.getUser(secret);
//            if (commodityUser == null || commodityUser.isEmpty()) { // 现货未登入
//                mCommodityCounts = null;
//                ProductListItem.removeCommodityCounts(mProductList);
//                return;
//            }
//
//            new RequestBuilder()
//                    .url(ApiConfig.getFullUrl(ApiConfig.ApiURL.COMMODITY_POSITIONS_COUNT))
//                    .put(HttpKeys.TOKEN, commodityUser.getToken())
//                    .put(HttpKeys.TRADER_ID, commodityUser.getTraderId())
//                    .type(new TypeToken<ListResponse<PositionOrderCount>>() {
//                    }.getType())
//                    .listener(new com.android.volley.Response.Listener<ListResponse<PositionOrderCount>>() {
//                        @Override
//                        public void onResponse(ListResponse<PositionOrderCount> response) {
//                            if (response.isSuccess()) {
//                                mCommodityCounts = response.getData();
//                                if (ListUtil.isNotEmpty(mProductList)) {
//                                    ProductListItem.updateProductList(mProductList, mCommodityCounts, true);
//                                    ProductListHelper.getInstance().setProductList(mProductList);
//                                    updateProductListView();
//                                }
//                            }
//                        }
//                    }).errorListener(new SimpleErrorListener())
//                    .create().send(getRequestTag());
//        }
    }

    public void requestProductList() {
        ProgressDialog.showProgressDialog(getActivity());
        new RequestBuilder()
                .url(ApiConfig.getFullUrl(ApiConfig.ApiURL.NEW_PRODUCT_LIST))
                .put(HttpKeys.LOBBY, Product.LOBBY_CASH)
                .put(HttpKeys.TOKEN, "")
                .type(new TypeToken<ListResponse<Product>>() {
                }.getType())
                .listener(new com.android.volley.Response.Listener<ListResponse<Product>>() {
                    @Override
                    public void onResponse(ListResponse<Product> response) {
                        ProgressDialog.dismissProgressDialog();
                        if (response.isSuccess() && response.hasData()) {
                            if (ListUtil.isNotEmpty(mProductList)) {
                                mProductList.clear();
                            }

                            List<Product> productList = response.getData();
                            Storage.getInstance(getActivity()).writeToCache(Product.CACHE_KEY_CASH, productList);

                            ProductListItem.updateProductList(mProductList, productList,
                                    mFuturesCounts, mCommodityCounts);
                            ProductListHelper.getInstance().setProductList(mProductList);

                            updateProductListView();
                        }
                    }
                }).errorListener(new SimpleErrorListener(true) {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                super.onErrorResponse(volleyError);
                ProgressDialog.dismissProgressDialog();
            }
        }).create().send(getRequestTag());
    }

    private void updateProductListView() {
        if (ListUtil.isNotEmpty(mProductList)) {
            if (mProductAdapter != null) {
                mProductAdapter.setProductList(mProductList);
            } else {
                mProductAdapter = new ProductAdapter(mProductList, mActivity);
                mListView.setAdapter(mProductAdapter);
            }
        }
    }

    private void requestFuturesQuotationServer(final ProductListItem productListItem) {
//        final Product product = productListItem.getProduct();
//        ProgressDialog.showProgressDialog(getActivity());
//        new RequestBuilder()
//                .url(ApiConfig.getFullUrl(ApiConfig.ApiURL.FUTURES_QUOTA_SERVER))
//                .clazz(FuturesQuotaServer.class)
//                .listener(new com.android.volley.Response.Listener<FuturesQuotaServer>() {
//                    @Override
//                    public void onResponse(FuturesQuotaServer response) {
//                        NettyClient.getInstance().setHostAndPort(response.getIp(), response.getPort());
//                        NettyClient.getInstance().setFutureType(product.getInstrumentCode());
//                        startProductActivity(productListItem);
//                        ProgressDialog.dismissProgressDialog();
//                    }
//                }).errorListener(new SimpleErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                super.onErrorResponse(volleyError);
//                ProgressDialog.dismissProgressDialog();
//                startProductActivity(productListItem);
//            }
//        }).create().send(getRequestTag());
    }

    private void startProductActivity(ProductListItem productListItem) {
//        if (!isAdded()) return;
//
//        Product product = productListItem.getProduct();
//        PositionOrderCount orderCount = productListItem.getOrderCount();
//        boolean hasOrders = orderCount != null ? orderCount.hasCashOrders() : false;
//
//        if (product.isCashCommodity()) {
//            CommodityHoldingActivity.enter(mActivity, product, hasOrders);
//        } else {
//            GoldHoldingActivity.enter(mActivity, product, hasOrders);
//        }
    }

}
