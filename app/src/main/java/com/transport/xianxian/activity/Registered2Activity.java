package com.transport.xianxian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;
import com.transport.xianxian.utils.CommonUtil;

import static com.transport.xianxian.net.OkHttpClientManager.HOST;


/**
 * Created by fafukeji01 on 2017/4/25.
 * 注册
 */

public class Registered2Activity extends BaseActivity {
    private EditText editText1, editText2, editText3;

    private ImageView imageView1;
    boolean isgouxuan = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered2);
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
            Glide.with(Registered1Activity.this)
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
        editText2 = findViewByID_My(R.id.editText2);
        editText3 = findViewByID_My(R.id.editText3);

        imageView1 = findViewByID_My(R.id.imageView1);

        /*//失去焦点时触发
        editText6.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    MyLogger.i(">>>>>>>>>>" + editText6.getText().toString().trim());
                    if (!editText6.getText().toString().trim().equals("")) {
                        String string = "?nickname=" + editText6.getText().toString().trim();
                        RequestNickName(string);//检测昵称是否可用
                    }
                }
            }
        });*/

    }

    @Override
    protected void initData() {
//        request(captchaURL);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView4:
                //用户注册协议
                Bundle bundle = new Bundle();
                bundle.putString("url", HOST + "/wechat/article/detail?id=13a19f182849fa6440b88e4ee0a5e5e8");
                CommonUtil.gotoActivityWithData(Registered2Activity.this, WebContentActivity.class, bundle, false);

                break;
            case R.id.textView2:
                //下一步
                if (match())
                    CommonUtil.gotoActivity(this, Registered1Activity.class);
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
        /*phonenum = editText1.getText().toString().trim();
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
        }*/

        if (!isgouxuan) {
            myToast("注册请勾选同意遵守《用户注册协议》");
            return false;
        }
        return true;
    }

    //注册
    /*private void RequestRegistered(Map<String, String> params) {
        OkHttpClientManager.postAsyn(Registered2Activity.this, URLs.Registered, params, new OkHttpClientManager.ResultCallback<String>() {
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
                *//*showToast("该账户尚未激活，请完成人脸识别后进行操作", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("mobile",phonenum);
                        CommonUtil.gotoActivityWithData(Registered1Activity.this,
                                RecordVideoActivity.class,bundle1);
                        dialog.dismiss();
                    }
                });
                hideProgress();*//*
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
                    CommonUtil.gotoActivityWithFinishOtherAllAndData(Registered2Activity.this, MainActivity.class, bundle, true);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }

            }
        }, true);

    }*/

    @Override
    protected void updateView() {
        titleView.setTitle("注册");
    }
}
