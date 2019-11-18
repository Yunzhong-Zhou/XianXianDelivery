package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.view.View;

import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.MyLogger;
import com.squareup.okhttp.Request;

/**
 * Created by zyz on 2019-11-16.
 * 费用详情
 */
public class FeeDetailActivity extends BaseActivity {
    String city = "", car_type_id = "", use_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedetail);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        city = getIntent().getStringExtra("city");
        car_type_id = getIntent().getStringExtra("car_type_id");
        use_type = getIntent().getStringExtra("use_type");
        //获取费用信息
        showProgress(true, getString(R.string.app_loading));
        Request("?token=" + localUserInfo.getToken()
                + "&city=" + city
                + "&car_type_id=" + car_type_id
                + "&use_type=" + use_type);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(FeeDetailActivity.this, URLs.FeeDetail + string, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                MyLogger.i(">>>>>>>>>费用详情" + response);
                hideProgress();
            }
        });
    }

    @Override
    protected void updateView() {
        titleView.setTitle("费用详情");
        titleView.showRightTextview("收费标准", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
