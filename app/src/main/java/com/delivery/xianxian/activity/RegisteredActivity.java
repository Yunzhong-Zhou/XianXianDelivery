package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.CommonUtil;
import com.delivery.xianxian.utils.MyLogger;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.delivery.xianxian.net.OkHttpClientManager.HOST;


/**
 * Created by fafukeji01 on 2017/4/25.
 * 注册
 */

public class RegisteredActivity extends BaseActivity {
    String third_id="";
    private TextView textView1, textView2, textView4;
    private EditText editText1, editText2, editText3, editText4;

    private ImageView imageView1;
    boolean isgouxuan = false;

    String phonenum = "", password1 = "", password2 = "", code = "", num = "", nickname = "", register_addr = "", hx_username = "";

    private TimeCount time;

    //定位
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
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
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null)
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }
    @Override
    protected void initView() {
        editText1 = findViewByID_My(R.id.editText1);
        editText2 = findViewByID_My(R.id.editText2);
        editText3 = findViewByID_My(R.id.editText3);
        editText4 = findViewByID_My(R.id.editText4);

        imageView1 = findViewByID_My(R.id.imageView1);

        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
    }

    @Override
    protected void initData() {
//        request(captchaURL);
        third_id = getIntent().getStringExtra("third_id");
        MyLogger.i(">>>>>>"+third_id);

        time = new TimeCount(60000, 1000);//构造CountDownTimer对象

        //初始化定位
        mLocationClient = new AMapLocationClient(RegisteredActivity.this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        //设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。AMapLocationMode.Battery_Saving，低功耗模式。AMapLocationMode.Device_Sensors，仅设备模式。
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：默认为false。
        option.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        option.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        option.setInterval(5 * 1000);
        //设置是否返回地址信息（默认返回地址信息）
        option.setNeedAddress(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        option.setMockEnable(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        option.setHttpTimeOut(30000);
        //是否开启定位缓存机制
        option.setLocationCacheEnable(false);

        mLocationClient.setLocationOption(option);

        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        /*//可在其中解析amapLocation获取相应内容。
                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        aMapLocation.getLatitude();//获取纬度
                        aMapLocation.getLongitude();//获取经度
                        aMapLocation.getAccuracy();//获取精度信息
                        aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                        aMapLocation.getCountry();//国家信息
                        aMapLocation.getProvince();//省信息
                        aMapLocation.getCity();//城市信息
                        aMapLocation.getDistrict();//城区信息
                        aMapLocation.getStreet();//街道信息
                        aMapLocation.getStreetNum();//街道门牌号信息
                        aMapLocation.getCityCode();//城市编码
                        aMapLocation.getAdCode();//地区编码
                        aMapLocation.getAoiName();//获取当前定位点的AOI信息
                        aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                        aMapLocation.getFloor();//获取当前室内定位的楼层
                        aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                        //获取定位时间
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(amapLocation.getTime());
                        df.format(date);*/

                        MyLogger.i(">>>>>>>>>>定位信息：\n纬度：" + aMapLocation.getLatitude()
                                + "\n经度:" + aMapLocation.getLongitude()
                                + "\n地址:" + aMapLocation.getAddress());
                        register_addr = aMapLocation.getAddress();

                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        MyLogger.e("定位失败：", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                        myToast("" + aMapLocation.getErrorInfo());
                    }
                }
            }
        });

        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
//        mLocationClient.stopLocation();
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        mLocationClient.startLocation();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView1:
                //发送验证码
                phonenum = editText1.getText().toString().trim();
                if (TextUtils.isEmpty(phonenum)) {
                    myToast(getString(R.string.registered_h1));
                } else {
                    showProgress(true, "正在获取短信验证码...");
                    textView1.setClickable(false);
                    HashMap<String, String> params = new HashMap<>();
                    params.put("mobile", phonenum);
                    params.put("type", "1");
//                    params.put("code", piccode);
                    RequestCode(params);//获取验证码

                    /*showProgress(true, "正在生成图形验证码...");
                    HashMap<String, String> params = new HashMap<>();
                    params.put("mobile", phonenum);
                    params.put("type", "1");
                    RequestPicCode(params);*/

                }
                break;
            case R.id.textView4:
                //用户注册协议
                Bundle bundle = new Bundle();
                bundle.putString("url", HOST + "/api/article/detail-html?id=dc8e69cd0e22f7ceb7f737f003f3c947");
                CommonUtil.gotoActivityWithData(RegisteredActivity.this, WebContentActivity.class, bundle, false);

                break;
            case R.id.textView2:
                //确认注册
//                MyLogger.i(">>>>>>" + CommonUtil.isRealMachine() + CommonUtil.getIMEI(RegisteredActivity.this));
//                if (CommonUtil.isRealMachine()){
                //是真机
                if (match()) {
                    textView2.setClickable(false);
                    showProgress(true, getString(R.string.app_loading1));
                    HashMap<String, String> params = new HashMap<>();
                    params.put("mobile", phonenum);//手机号
                    params.put("password", password1);//密码（不能小于6位数）
                    params.put("code", code);//手机验证码
//                    params.put("nickname", nickname);//昵称
//                    params.put("invite_code", num);//邀请码
                    params.put("uuid", CommonUtil.getIMEI(RegisteredActivity.this));//IMEI
                    params.put("register_addr", register_addr);//注册地址
//                    params.put("mobile_state_code", localUserInfo.getMobile_State_Code());

                    if (third_id !=null && !third_id.equals("")){
                        params.put("third_id", third_id);//微信登录
                        getIntent().removeExtra("third_id");
                    }
                    RequestRegistered(params);//注册
                }
//                }else {
//                    //不是真机
//                    myToast("该设备不能进行注册");
//                }
                break;

            case R.id.imageView1:
                //勾选协议
                isgouxuan = !isgouxuan;
                if (isgouxuan)
                    imageView1.setImageResource(R.mipmap.ic_gouxuan);
                else
                    imageView1.setImageResource(R.mipmap.ic_weigouxuan);
                break;
        }
    }

    private boolean match() {
        phonenum = editText1.getText().toString().trim();
        if (TextUtils.isEmpty(phonenum)) {
            myToast(getString(R.string.registered_h1));
            return false;
        }
        if (phonenum.length() != 11) {
            myToast("请输入11位手机号");
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
        if (password1.length() < 8) {
            myToast("密码过于简单，请重新设置");
            return false;
        }
        if (!password1.equals(password2)) {
            myToast(getString(R.string.registered_h12));
            return false;
        }

        if (!isgouxuan) {
            myToast("注册请勾选同意遵守《用户注册协议和用户隐私协议》");
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

    private void RequestCode(Map<String, String> params) {
        OkHttpClientManager.postAsyn(this, URLs.Code, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                textView1.setClickable(true);
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                hideProgress();
                textView1.setClickable(true);
                MyLogger.i(">>>>>>>>>验证码" + response);
                time.start();//开始计时
                myToast(getString(R.string.app_sendcode_hint));
            }
        }, false);

    }

    //注册
    private void RequestRegistered(Map<String, String> params) {
        OkHttpClientManager.postAsyn(RegisteredActivity.this, URLs.Registered, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                textView2.setClickable(true);
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(final String response) {
                MyLogger.i(">>>>>>>>>注册" + response);
                textView2.setClickable(true);
                //下一步

                //                localUserInfo.setTime(System.currentTimeMillis() + "");
                JSONObject jObj;
                try {
                    jObj = new JSONObject(response);

                    JSONObject jObj1 = jObj.getJSONObject("data");
                    //保存Token
                    String token = jObj1.getString("fresh_token");
                    localUserInfo.setToken(token);
                    //保存用户id
                    String id = jObj1.getString("id");
//                    localUserInfo.setUserId(id);
                    //保存电话号码
                    String mobile = jObj1.getString("mobile");
                    localUserInfo.setPhoneNumber(mobile);
                    /*//保存用户昵称
                    String nickname = jObj1.getString("nickname");
                    localUserInfo.setNickname(nickname);*/
                    //保存是否认证
                    String identity = jObj1.getString("is_certification");
                    localUserInfo.setIsVerified(identity);//1 认证 2 未认证
                    //保存环信ID
                    localUserInfo.setHxid(jObj1.getString("hx_username"));

                    //环信注册
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                hx_username = jObj1.getString("hx_username");
                                EMClient.getInstance().createAccount(hx_username, "123456");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                        hideProgress();
                                        //登录环信
                                        EMClient.getInstance().logout(false);
                                        EMClient.getInstance().login(hx_username, "123456", new EMCallBack() {
                                            @Override
                                            public void onSuccess() {
                                                hideProgress();
                                                //保存id
                                                localUserInfo.setUserId(id);
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("isShowAd", 1);
                                                CommonUtil.gotoActivityWithFinishOtherAllAndData(
                                                        RegisteredActivity.this, MainActivity.class,
                                                        bundle, true);
                                            }

                                            @Override
                                            public void onProgress(int progress, String status) {
                                            }

                                            @Override
                                            public void onError(int code, String error) {
                                                runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        hideProgress();
                                                        MyLogger.i("环信登录失败：" + error);
                                                        myToast("环信登录失败：" + error);
                                                    }
                                                });
                                            }
                                        });

                                        /*//去完善信息
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", id);
                                        CommonUtil.gotoActivityWithData(RegisteredActivity.this, Registered2Activity.class, bundle, true);*/
                                    }
                                });
                            } catch (final HyphenateException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideProgress();
                                        /**
                                         * 关于错误码可以参考官方api详细说明
                                         * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                                         */
                                        int errorCode = e.getErrorCode();
                                        String message = e.getMessage();
                                        Log.d("lzan13", String.format("sign up - errorCode:%d, errorMsg:%s", errorCode, e.getMessage()));
                                        switch (errorCode) {
                                            // 网络错误
                                            case EMError.NETWORK_ERROR:
                                                MyLogger.i("网络错误 code: " + errorCode + ", message:" + message);
                                                break;
                                            // 用户已存在
                                            case EMError.USER_ALREADY_EXIST:
                                                myToast("用户已存在 code: " + errorCode + ", message:" + message);
                                                break;
                                            // 参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册
                                            case EMError.USER_ILLEGAL_ARGUMENT:
                                                MyLogger.i("参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册 code: " + errorCode + ", message:" + message);
                                                break;
                                            // 服务器未知错误
                                            case EMError.SERVER_UNKNOWN_ERROR:
                                                MyLogger.i("服务器未知错误 code: " + errorCode + ", message:" + message);
                                                break;
                                            case EMError.USER_REG_FAILED:
                                                myToast("账户注册失败 code: " + errorCode + ", message:" + message);
                                                break;
                                            default:
                                                MyLogger.i("ml_sign_up_failed code: " + errorCode + ", message:" + message);
                                                break;
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }

            }
        }, false);

    }

    @Override
    protected void updateView() {
        titleView.setTitle("注册");
    }

    //获取验证码倒计时
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            textView1.setText(getString(R.string.app_reacquirecode));
            textView1.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            textView1.setClickable(false);
            textView1.setText(millisUntilFinished / 1000 + getString(R.string.app_codethen));
        }
    }
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
