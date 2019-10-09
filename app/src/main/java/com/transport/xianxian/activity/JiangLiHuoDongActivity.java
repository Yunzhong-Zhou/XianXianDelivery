package com.transport.xianxian.activity;

import android.os.Bundle;
import android.view.View;

import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;
import com.transport.xianxian.utils.CommonUtil;

/**
 * Created by zyz on 2019-10-01.
 * 奖励活动
 */
public class JiangLiHuoDongActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jianglihuodong);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.linearLayout1:
                //分享APP
                /*Bundle bundle1 = new Bundle();
                bundle1.putInt("type",1);
                CommonUtil.gotoActivityWithData(this,JiFenLieBiaoActivity.class,bundle1,false);*/
                break;
            case R.id.linearLayout2:
                //立即招募
                /*Bundle bundle2 = new Bundle();
                bundle2.putInt("type",2);
                CommonUtil.gotoActivityWithData(this,JiFenLieBiaoActivity.class,bundle2,false);*/
                break;
            case R.id.linearLayout3:
                //奖励记录
                CommonUtil.gotoActivity(this, JiangLiListActivity.class, false);
                break;

        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateView() {
        titleView.setTitle("奖励活动");
    }
}
