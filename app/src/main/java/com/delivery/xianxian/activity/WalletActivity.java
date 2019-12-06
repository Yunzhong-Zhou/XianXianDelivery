package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.WalletModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.CommonUtil;
import com.delivery.xianxian.utils.MyLogger;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zyz on 2019-10-02.
 * 钱包
 */
public class WalletActivity extends BaseActivity {
    TextView textView1, textView2;
    private RecyclerView recyclerView;
    List<WalletModel.CouponListBean> list = new ArrayList<>();
    CommonAdapter<WalletModel.CouponListBean> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
    }

    @Override
    protected void initView() {
        setSpringViewMore(false);//不需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                String string = "?token=" + localUserInfo.getToken();
                Request(string);
            }

            @Override
            public void onLoadmore() {
            }
        });
        recyclerView = findViewByID_My(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(WalletActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.textView2:
                //充值
                CommonUtil.gotoActivity(WalletActivity.this, RechargeActivity.class);
                break;

            default:
                break;
        }
    }

    @Override
    protected void initData() {
        requestServer();
    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();

        showProgress(true, getString(R.string.app_loading));
        String string = "?token=" + localUserInfo.getToken();
        Request(string);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(WalletActivity.this, URLs.Wallet + string, new OkHttpClientManager.ResultCallback<WalletModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(WalletModel response) {
                hideProgress();
                MyLogger.i(">>>>>>>>>钱包" + response);
                textView1.setText(response.getMoney());//余额
                list = response.getCoupon_list();
                if (list.size() > 0) {
                    showContentPage();
                    mAdapter = new CommonAdapter<WalletModel.CouponListBean>
                            (WalletActivity.this, R.layout.item_wallet, list) {
                        @Override
                        protected void convert(ViewHolder holder, WalletModel.CouponListBean model, int position) {
                            holder.setText(R.id.textView1, "¥ "+model.getMoney());
                            holder.setText(R.id.textView2, model.getTitle());
//                            TextView textView3 = holder.getView(R.id.textView3);
                            holder.setText(R.id.textView4, "有效期："+model.getExpired_at());

                            TextView textView3 = holder.getView(R.id.textView3);
                            textView3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    MainActivity.item = 0;
                                    CommonUtil.gotoActivityWithFinishOtherAll(WalletActivity.this,MainActivity.class,true);
                                }
                            });
                        }
                    };
                    recyclerView.setAdapter(mAdapter);
                } else {
                    showEmptyPage();
                }

            }
        });
    }

    @Override
    protected void updateView() {
        titleView.setTitle("钱包");
        titleView.showRightTextview("余额明细", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.gotoActivity(WalletActivity.this,MoneyListActivity.class,false);
            }
        });
    }
}
