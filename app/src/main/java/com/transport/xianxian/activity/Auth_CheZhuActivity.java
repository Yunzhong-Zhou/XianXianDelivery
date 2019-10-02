package com.transport.xianxian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;
import com.transport.xianxian.utils.CommonUtil;

/**
 * Created by zyz on 2019-10-01.
 * 车主认证
 */
public class Auth_CheZhuActivity extends BaseActivity {
    ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_chezhu);
    }

    @Override
    protected void initView() {
        imageView1 = findViewByID_My(R.id.imageView1);
        imageView2 = findViewByID_My(R.id.imageView2);
        imageView3 = findViewByID_My(R.id.imageView3);
        imageView4 = findViewByID_My(R.id.imageView4);
        imageView5 = findViewByID_My(R.id.imageView5);
        imageView6 = findViewByID_My(R.id.imageView6);
        imageView7 = findViewByID_My(R.id.imageView7);
        imageView8 = findViewByID_My(R.id.imageView8);
        imageView9 = findViewByID_My(R.id.imageView9);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.linearLayout1:
                //身份证认证
                CommonUtil.gotoActivity(this, Auth_ShenFenZhengActivity.class, false);
                break;
            case R.id.linearLayout2:
                //驾驶证及行驶证认证
                CommonUtil.gotoActivity(this,Auth_JiaShiZhengActivity.class,false);
                break;
            case R.id.linearLayout3:
                //人脸认证
//                CommonUtil.gotoActivity(this,,false);
                break;
            case R.id.linearLayout4:
                //银行卡绑定
                CommonUtil.gotoActivity(this,Auth_YinHangKaActivity.class,false);
                break;
            case R.id.linearLayout5:
                //车辆保险
                CommonUtil.gotoActivity(this,Auth_CheLiangBaoXianActivity.class,false);
                break;
            case R.id.linearLayout6:
                //从业资格证
                CommonUtil.gotoActivity(this,Auth_ChongYeZhiGeActivity.class,false);
                break;
            case R.id.linearLayout7:
                //挂靠公司告知书
                CommonUtil.gotoActivity(this,Auth_GuaKaoGongSiActivity.class,false);
                break;
            case R.id.linearLayout8:
                //车牌号码
                CommonUtil.gotoActivity(this,Auth_ChePaiHaoMaActivity.class,false);
                break;
            case R.id.linearLayout9:
                //车辆照片
                CommonUtil.gotoActivity(this,Auth_CheLiangZhaoPianActivity.class,false);
                break;
            default:
                break;
        }
    }

    @Override
    protected void updateView() {
        titleView.setTitle("车主认证");
    }
}
