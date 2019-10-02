package com.transport.xianxian.activity;

import android.os.Bundle;

import com.liaoinstan.springview.widget.SpringView;
import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;

/**
 * Created by zyz on 2019-10-02.
 */
public class WalletActivity extends BaseActivity {

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
//                Request("?token=" + localUserInfo.getToken());
            }

            @Override
            public void onLoadmore() {
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateView() {
        titleView.setTitle("钱包");
    }
}
