package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.view.View;

import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;

/**
 * Created by zyz on 2019-10-01.
 * 客服中心
 */
public class ServiceCenterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicecenter);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.linearLayout1:
               /* //服务中心
                CommonUtil.gotoActivity(this, Auth_ShenFenZhengActivity.class, false);*/
                break;
            case R.id.linearLayout2:
                /*//申诉中心
                CommonUtil.gotoActivity(this,Auth_JiaShiZhengActivity.class,false);*/
                break;
            case R.id.linearLayout3:
                //问题反馈
//                CommonUtil.gotoActivity(this,,false);
                break;
            default:
                break;
        }
    }

    @Override
    protected void updateView() {
        titleView.setTitle("客服中心");
    }
}
