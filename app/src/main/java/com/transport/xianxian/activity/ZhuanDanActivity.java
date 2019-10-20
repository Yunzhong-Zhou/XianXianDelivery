package com.transport.xianxian.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;
import com.transport.xianxian.utils.ZxingUtils;

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
        //生成二维码
        Bitmap mBitmap = ZxingUtils.createQRCodeBitmap(id, 480, 480);
        imageView1.setImageBitmap(mBitmap);
    }

    @Override
    protected void updateView() {
        titleView.setTitle("转单二维码");
    }
}
