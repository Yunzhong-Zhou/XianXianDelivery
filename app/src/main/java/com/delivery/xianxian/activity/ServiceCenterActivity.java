package com.delivery.xianxian.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.ServiceCenterModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.MyLogger;
import com.squareup.okhttp.Request;

/**
 * Created by zyz on 2019-10-01.
 * 客服中心
 */
public class ServiceCenterActivity extends BaseActivity {
    TextView textView1;
    ServiceCenterModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicecenter);
    }

    @Override
    protected void initView() {
        textView1 = findViewByID_My(R.id.textView1);
    }

    @Override
    protected void initData() {
        String string = "?token=" + localUserInfo.getToken();
        Request(string);
    }
    private void Request(String string) {
        OkHttpClientManager.getAsyn(this, URLs.ServiceCenter + string, new OkHttpClientManager.ResultCallback<ServiceCenterModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(ServiceCenterModel response) {
//                showContentPage();
                hideProgress();
                model = response;
                MyLogger.i(">>>>>>>>>客服中心" + response);
                textView1.setText(response.getMobile());

            }
        });
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.linearLayout1:
                //订单投诉
//                CommonUtil.gotoActivity(this, Auth_ShenFenZhengActivity.class, false);
                break;
            case R.id.linearLayout2:
                //常见问题教程
//                CommonUtil.gotoActivity(this,Auth_JiaShiZhengActivity.class,false);
                break;
            case R.id.linearLayout3:
                //在线客服
                showToast("确认拨打 " + model.getMobile() + " 吗？", "确认", "取消",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                //创建打电话的意图
                                Intent intent = new Intent();
                                //设置拨打电话的动作
                                intent.setAction(Intent.ACTION_CALL);//直接拨出电话
//                                                                        intent.setAction(Intent.ACTION_DIAL);//只调用拨号界面，不拨出电话
                                //设置拨打电话的号码
                                intent.setData(Uri.parse("tel:" + model.getMobile()));
                                //开启打电话的意图
                                startActivity(intent);
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                break;
            case R.id.linearLayout4:
                //反馈记录
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
