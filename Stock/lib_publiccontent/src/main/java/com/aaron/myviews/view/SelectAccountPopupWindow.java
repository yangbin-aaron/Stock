package com.aaron.myviews.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaron.myviews.R;
import com.aaron.myviews.model.AccountInfoListModel;
import com.aaron.myviews.model.Product;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4.
 * 动态添加持仓页面 下拉选择的账户
 */
public class SelectAccountPopupWindow extends PopupWindow implements
        View.OnClickListener {
    private Context mContext;
    private RelativeLayout mMain;
    private LinearLayout lrNull;
    private LinearLayout lrAccount;
    private ViewClickListener mSelectAccountListener;
    private OnGetData mOnGetData;
    private List<AccountInfoListModel> mAccountInfoList;
    private Product product;
    private RelativeLayout rlAccountFz;
    private RelativeLayout rlAccountCn;
    private RelativeLayout rlAccountNj;
    private TextView tvStatusFz;
    private TextView tvStatusCn;
    private TextView tvStatusNj;

    public SelectAccountPopupWindow(Context context) {
        super(context);
        mContext = context;
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);


        initView();
        setListener();
    }

    public void setData(OnGetData sd) {
        mOnGetData = sd;
        if (mOnGetData != null) {
            mAccountInfoList = mOnGetData.onList();
            product = mOnGetData.onProduct();
            hasAvailableAccount(product);
        }
    }

    // 数据接口抽象方法
    public interface OnGetData {
        abstract List<AccountInfoListModel> onList();

        abstract Product onProduct();

    }

    private void initView() {
        findView();
        // 添加菜单视图
        this.setContentView(mMain);
    }

    private void setListener() {

    }

    private void findView() {
        mMain = (RelativeLayout) LayoutInflater.from(mContext).inflate(
                R.layout.popupwindow_selectaccount, null);
        lrNull = (LinearLayout) mMain.findViewById(R.id.lr_null);
        lrAccount = (LinearLayout) mMain.findViewById(R.id.lr_account);
        rlAccountFz = (RelativeLayout) lrAccount.findViewById(R.id.rl_account_fz);
        rlAccountCn = (RelativeLayout) lrAccount.findViewById(R.id.rl_account_cn);
        rlAccountNj = (RelativeLayout) lrAccount.findViewById(R.id.rl_account_nj);
        tvStatusCn = (TextView) lrAccount.findViewById(R.id.tv_status_cn);
        tvStatusFz = (TextView) lrAccount.findViewById(R.id.tv_status_fz);
        tvStatusNj = (TextView) lrAccount.findViewById(R.id.tv_status_nj);
        rlAccountFz.setOnClickListener(this);
        rlAccountCn.setOnClickListener(this);
        rlAccountNj.setOnClickListener(this);
        lrNull.setOnClickListener(this);

    }

    public void hasAvailableAccount(Product product) {
        String[] accountList = product.getAccountCode().split(";");
        for (int i = 0; i < accountList.length; i++) {
            String supportedAccount = accountList[i];
            if (supportedAccount.equals(AccountInfoListModel.CODE_CAINIU)) {
                rlAccountCn.setVisibility(View.VISIBLE);
                rlAccountFz.setVisibility(View.GONE);
                rlAccountNj.setVisibility(View.GONE);
            } else if (supportedAccount.equals(AccountInfoListModel.CODE_NANJS)) {
                rlAccountCn.setVisibility(View.GONE);
                rlAccountFz.setVisibility(View.GONE);
                rlAccountNj.setVisibility(View.VISIBLE);
            }
        }

    }

    public void setSelectAccountListtner(ViewClickListener listener) {
        mSelectAccountListener = listener;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        mHandler.sendEmptyMessage(0);
    }

    public void onAnimationRepeat(Animation animation) {
    }

    /**
     * dismiss 时 必须用到Handler不然 会报错。原因不明
     **/
    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            dismiss();
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_account_fz) {
            mSelectAccountListener.onClick("fz");
        } else if (v.getId() == R.id.rl_account_cn) {
            mSelectAccountListener.onClick("cn");
        } else if (v.getId() == R.id.rl_account_nj) {
            mSelectAccountListener.onClick("nj");
        } else if (v.getId() == R.id.lr_null) {
            dismiss();
        }
    }

    public interface ViewClickListener {
        public void onClick(String state);
    }


}
