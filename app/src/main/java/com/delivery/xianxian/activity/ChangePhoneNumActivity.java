package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.CommonUtil;
import com.delivery.xianxian.utils.MyLogger;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by fafukeji01 on 2017/4/25.
 * 修改手机号
 */

public class ChangePhoneNumActivity extends BaseActivity {
    private TextView textView1, textView2;
    private EditText editText1, editText2;

    String phonenum = "", code = "";

    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changephonenum);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initView() {
        editText1 = findViewByID_My(R.id.editText1);

//        editText1.setText(localUserInfo.getPhonenumber());
//        editText1.setEnabled(false);

        editText2 = findViewByID_My(R.id.editText2);

        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
    }

    @Override
    protected void initData() {
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView1:
                //发送验证码
                phonenum = editText1.getText().toString().trim();
                if (TextUtils.isEmpty(phonenum)) {
                    myToast(getString(R.string.forgetpassword_h1));
                } else {
                    textView1.setClickable(false);
                    this.showProgress(true, getString(R.string.app_sendcode_hint1));
                    HashMap<String, String> params = new HashMap<>();
                    params.put("mobile", phonenum);
                    params.put("type", "4");
                    RequestCode(params);//获取验证码
                }
                break;
            case R.id.textView2:
                //修改密码
                if (match()) {
                    textView2.setClickable(false);
                    showProgress(true, getString(R.string.registered_h14));
                    HashMap<String, String> params = new HashMap<>();
                    params.put("mobile", phonenum);
                    params.put("code", code);//手机验证码
                    params.put("token", localUserInfo.getToken());//手机验证码
                    Request(params);//注册
                }
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

    //修改密码
    private void Request(Map<String, String> params) {
        OkHttpClientManager.postAsyn(ChangePhoneNumActivity.this, URLs.ChangePhoneNum, params, new OkHttpClientManager.ResultCallback<String>() {
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
                MyLogger.i(">>>>>>>>>修改手机号" + response);
                textView2.setClickable(true);
                hideProgress();
                myToast("修改手机号成功，请重新登录");
                localUserInfo.setUserId("");
                localUserInfo.setUserName("");
                localUserInfo.setToken("");
                localUserInfo.setPhoneNumber("");
                localUserInfo.setNickname("");
                localUserInfo.setWalletaddr("");
                localUserInfo.setEmail("");
                localUserInfo.setUserImage("");
                //环信退出登录
                EMClient.getInstance().logout(false, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        CommonUtil.gotoActivityWithFinishOtherAll(ChangePhoneNumActivity.this, LoginActivity.class, true);
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String error) {

                    }
                });
            }
        }, false);

    }

    @Override
    protected void updateView() {
        titleView.setTitle("修改手机号");
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

}
