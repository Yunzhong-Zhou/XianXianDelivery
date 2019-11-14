package com.delivery.xianxian.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.okhttp.Request;
import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.MyLogger;
import com.delivery.xianxian.utils.ZxingUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyz on 2019-10-20.
 * 转单
 */
public class ZhuanDanActivity extends BaseActivity {
    String id = "";
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuandan);
    }

    @Override
    protected void initView() {
        imageView1 = findViewByID_My(R.id.imageView1);
        findViewByID_My(R.id.tv_quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");

        showProgress(true, getString(R.string.app_loading));
        Map<String, String> params = new HashMap<>();
        params.put("token", localUserInfo.getToken());
        params.put("t_indent_id", id);
        params.put("type", "5");//转单确认
        RequestZhuanDan(params);

    }
    private void RequestZhuanDan(Map<String, String> params) {
        OkHttpClientManager.postAsyn(ZhuanDanActivity.this, URLs.OrderDetails_ZhuangHuo, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
//                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>司机-转单确认" + response);
//                myToast("确认成功");
                JSONObject jObj;
                try {
                    jObj = new JSONObject(response);
                    JSONObject data = jObj.getJSONObject("data");
                    //生成二维码
                    Bitmap mBitmap = ZxingUtils.createQRCodeBitmap(data.getString("id"), 480, 480);
                    imageView1.setImageBitmap(mBitmap);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, false);
    }

    @Override
    protected void updateView() {
        titleView.setTitle("转单二维码");
    }
}
