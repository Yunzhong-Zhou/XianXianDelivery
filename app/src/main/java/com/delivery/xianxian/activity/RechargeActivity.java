package com.delivery.xianxian.activity;

import android.os.Bundle;

import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;

/**
 * Created by zyz on 2019-11-25.
 * 充值
 */
public class RechargeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateView() {
        titleView.setTitle("充值");
    }
}
