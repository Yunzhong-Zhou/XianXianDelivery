package com.transport.xianxian.activity;

import android.os.Bundle;

import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;

/**
 * Created by zyz on 2019-10-01.
 * 驾驶证及行驶证认证
 */
public class Auth_JiaShiZhengActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_jiashizheng);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateView() {
        titleView.setTitle("驾驶证及行驶证认证");
    }
}
