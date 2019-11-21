package com.delivery.xianxian.activity;

import android.os.Bundle;

import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;

/**
 * Created by zyz on 2019-11-21.
 * 确认开票
 */
public class AddInvoiceActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinvoice);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateView() {
        titleView.setTitle("确认开票");
    }
}
