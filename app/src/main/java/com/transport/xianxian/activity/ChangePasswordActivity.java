package com.transport.xianxian.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;
import com.transport.xianxian.net.OkHttpClientManager;
import com.transport.xianxian.net.URLs;
import com.transport.xianxian.utils.CommonUtil;
import com.transport.xianxian.utils.MyLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by fafukeji01 on 2017/4/25.
 * 修改密码
 */

public class ChangePasswordActivity extends BaseActivity {
    private TextView textView1, textView2, textView3;
    private EditText editText1, editText2, editText3, editText4;

    String phonenum = "", password1 = "", password2 = "", code = "", num = "", nickname = "", register_addr = "";

    String register_agreement = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
       /* mImmersionBar.reset()
                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题
                .init();*/
//        findViewByID_My(R.id.headview).setPadding(0, (int) CommonUtil.getStatusBarHeight(this), 0, 0);

        /*// 定位初始化
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        initLocation();
        mLocationClient.start();*/

    }

    @Override
    protected void onResume() {
        super.onResume();

        /*String string3 = "?lang_type=" + localUserInfo.getLanguage_Type();
        RequestSmsCodeList(string3);//手机号国家代码集合

        textView.setText("+" + localUserInfo.getMobile_State_Code());

        if (!localUserInfo.getCountry_IMG().equals(""))
            Glide.with(RegisteredActivity.this)
                    .load(IMGHOST + localUserInfo.getCountry_IMG())
                    .centerCrop()
//                    .placeholder(R.mipmap.ic_guoqi)//加载站位图
//                    .error(R.mipmap.ic_guoqi)//加载失败
                    .into(title_right);//加载图片
        else
            title_right.setImageResource(R.mipmap.ic_guoqi);*/
    }

    @Override
    protected void initView() {
        editText1 = findViewByID_My(R.id.editText1);
        editText1.setText(localUserInfo.getPhonenumber());
        editText1.setEnabled(false);
        editText2 = findViewByID_My(R.id.editText2);
        editText3 = findViewByID_My(R.id.editText3);
        editText4 = findViewByID_My(R.id.editText4);

    }

    @Override
    protected void initData() {
//        request(captchaURL);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView1:
                //发送验证码
                phonenum = editText1.getText().toString().trim();
                if (TextUtils.isEmpty(phonenum)) {
                    Toast.makeText(this, getString(R.string.registered_h1), Toast.LENGTH_SHORT).show();
                } else {
                    /*showProgress(true, "正在获取短信验证码...");
                    HashMap<String, String> params = new HashMap<>();
                    params.put("mobile", phonenum);
                    params.put("type", "1");
//                    params.put("code", piccode);
                    RequestCode(params);//获取验证码*/

                    /*showProgress(true, "正在生成图形验证码...");
                    HashMap<String, String> params = new HashMap<>();
                    params.put("mobile", phonenum);
                    params.put("type", "1");
                    RequestPicCode(params);*/

                }
                break;
            case R.id.textView2:
                //确认注册
//                MyLogger.i(">>>>>>" + CommonUtil.isRealMachine() + CommonUtil.getIMEI(RegisteredActivity.this));
//                if (CommonUtil.isRealMachine()){
                //是真机
                if (match()) {
                    textView3.setClickable(false);
                    showProgress(true, getString(R.string.registered_h14));
                    HashMap<String, String> params = new HashMap<>();
                    params.put("mobile", phonenum);//手机号
                    params.put("password", password1);//密码（不能小于6位数）
                    params.put("code", code);//手机验证码
//                    params.put("nickname", nickname);//昵称
//                    params.put("invite_code", num);//邀请码
                    params.put("uuid", CommonUtil.getIMEI(ChangePasswordActivity.this));//IMEI
                    params.put("register_addr", register_addr);//注册地址
//                    params.put("mobile_state_code", localUserInfo.getMobile_State_Code());
                    RequestRegistered(params);//注册
                }
//                }else {
//                    //不是真机
//                    myToast("该设备不能进行注册");
//                }
                break;
        }
    }

    private boolean match() {
        phonenum = editText1.getText().toString().trim();
        if (TextUtils.isEmpty(phonenum)) {
            myToast(getString(R.string.registered_h1));
            return false;
        }
        code = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            myToast(getString(R.string.registered_h2));
            return false;
        }
        password1 = editText3.getText().toString().trim();
        if (TextUtils.isEmpty(password1)) {
            myToast(getString(R.string.registered_h3));
            return false;
        }
        password2 = editText4.getText().toString().trim();
        if (TextUtils.isEmpty(password2)) {
            myToast(getString(R.string.registered_h4));
            return false;
        }
        if (!password1.equals(password2)) {
            myToast(getString(R.string.registered_h12));
            return false;
        }

        /*num = editText5.getText().toString().trim();
        if (TextUtils.isEmpty(num)) {
            myToast(getString(R.string.registered_h5));
            return false;
        }

        nickname = editText6.getText().toString().trim();
        if (TextUtils.isEmpty(nickname)) {
            myToast(getString(R.string.registered_h11));
            return false;
        }*/
        /*if (isNickName==false){
            myToast("昵称不可用");
            return false;
        }*/
        /*if (register_addr.equals("")) {
            showToast(getString(R.string.registered_position_hint),
                    getString(R.string.app_confirm),
                    getString(R.string.app_cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            register_addr = getString(R.string.registered_noposition) +"";
                            dialog.dismiss();
                        }
                    });
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisteredActivity.this);
            builder.setMessage(getString(R.string.registered_position_hint));
            builder.setTitle(getString(R.string.app_prompt));
            builder.setPositiveButton(getString(R.string.app_confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(getString(R.string.app_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    register_addr = getString(R.string.registered_noposition);
                    dialog.dismiss();
                }
            });
            builder.create().show();
            return false;
        }*/

        return true;
    }

    //注册
    private void RequestRegistered(Map<String, String> params) {
        OkHttpClientManager.postAsyn(ChangePasswordActivity.this, URLs.Registered, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                textView3.setClickable(true);
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(final String response) {
                MyLogger.i(">>>>>>>>>注册" + response);
                textView3.setClickable(true);
//                localUserInfo.setTime(System.currentTimeMillis() + "");
                /*showToast("该账户尚未激活，请完成人脸识别后进行操作", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("mobile",phonenum);
                        CommonUtil.gotoActivityWithData(RegisteredActivity.this,
                                RecordVideoActivity.class,bundle1);
                        dialog.dismiss();
                    }
                });
                hideProgress();*/
                hideProgress();
                JSONObject jObj;
                try {
                    jObj = new JSONObject(response);

                    JSONObject jObj1 = jObj.getJSONObject("data");
                    //保存Token
                    String token = jObj1.getString("fresh_token");
                    localUserInfo.setToken(token);
                    //保存用户id
                    final String id = jObj1.getString("id");

                    //保存电话号码
                    String mobile = jObj1.getString("mobile");
                    localUserInfo.setPhoneNumber(mobile);
                    localUserInfo.setPhoneNumber(phonenum);
                    //保存用户昵称
                    String nickname = jObj1.getString("nickname");
                    localUserInfo.setNickname(nickname);

                    localUserInfo.setUserId(id);

                    Bundle bundle = new Bundle();
//                    bundle.putInt("isShowAd", jObj1.getInt("experience"));
                    bundle.putInt("isShowAd", 1);
                    CommonUtil.gotoActivityWithFinishOtherAllAndData(ChangePasswordActivity.this, MainActivity.class, bundle, true);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }

            }
        }, true);

    }

    @Override
    protected void updateView() {
        titleView.setTitle("修改密码");
    }

    /*//屏蔽返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }*/


    /**
     * ********************************************定位**********************************************
     */
    /*private void initLocation() {
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        option.setCoorType("bd09ll");

        option.setScanSpan(1000);

        option.setOpenGps(true);

        option.setIsNeedAddress(true);

        option.setLocationNotify(true);//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);

        option.SetIgnoreCacheException(false);

//        option.setWifiCacheTimeOut(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setEnableSimulateGps(false);

        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
//            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
//
//            String coorType = location.getCoorType();//获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
//
//            int errorCode = location.getLocType();//获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            String addr = location.getAddrStr();    //获取详细地址信息
//            String country = location.getCountry();    //获取国家
//            String province = location.getProvince();    //获取省份
//            String city = location.getCity();    //获取城市
//            String district = location.getDistrict();    //获取区县
//            String street = location.getStreet();    //获取街道信息

            register_addr = addr + "";

            MyLogger.i(">>>>>>>>>>>>获取定位详细信息:" + addr + longitude + ">>>>" + latitude);

            mLocationClient.stop();

        }
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocationClient.stop();
        super.onDestroy();
    }*/

}
