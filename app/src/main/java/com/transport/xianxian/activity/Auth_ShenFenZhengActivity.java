package com.transport.xianxian.activity;

import android.os.Bundle;

import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;

/**
 * Created by zyz on 2019-10-01.
 * 身份证认证
 */
public class Auth_ShenFenZhengActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_shenfenzheng);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateView() {
        titleView.setTitle("身份证认证");
    }
}
