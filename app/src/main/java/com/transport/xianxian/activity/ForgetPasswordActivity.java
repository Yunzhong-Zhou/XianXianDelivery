package com.transport.xianxian.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;
import com.transport.xianxian.net.OkHttpClientManager;
import com.transport.xianxian.net.URLs;
import com.transport.xianxian.utils.MyLogger;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by fafukeji01 on 2017/4/25.
 * 忘记密码
 */

public class ForgetPasswordActivity extends BaseActivity {
    private TextView textView1, textView2;
    private EditText editText1, editText2, editText3, editText4;

    String phonenum = "", password1 = "", password2 = "", code = "";

    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

//        mImmersionBar.reset().init();
//        findViewByID_My(R.id.headview).setPadding(0, (int) CommonUtil.getStatusBarHeight(this), 0, 0);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initView() {
        editText1 = findViewByID_My(R.id.editText1);

        editText2 = findViewByID_My(R.id.editText2);
        editText3 = findViewByID_My(R.id.editText3);
        editText4 = findViewByID_My(R.id.editText4);

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
                    params.put("type", "3");
                    RequestCode(params);//获取验证码
                }
                break;
            case R.id.textView2:
                //确认
                if (match()) {
                    textView2.setClickable(false);
                    this.showProgress(true, getString(R.string.forgetpassword_h9));
                    HashMap<String, String> params = new HashMap<>();
                    params.put("mobile", phonenum);//手机号
//                    params.put("password", password1);//密码（不能小于6位数）
//                    params.put("mobile_state_code", localUserInfo.getMobile_State_Code());
//                    params.put("uuid", CommonUtil.getIMEI(ForgetPasswordActivity.this));
                    params.put("password", password1);//密码（不能小于6位数）
                    params.put("code", code);//手机验证码
                    Request(params);
                }
                break;
            case R.id.textView:
                finish();
                break;
        }
    }

    private boolean match() {
        phonenum = editText1.getText().toString().trim();
        if (TextUtils.isEmpty(phonenum)) {
            myToast(getString(R.string.forgetpassword_h1));
            return false;
        }
        code = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            myToast(getString(R.string.forgetpassword_h2));
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
        return true;
    }

    private void RequestCode(Map<String, String> params) {
        OkHttpClientManager.postAsyn(ForgetPasswordActivity.this, URLs.Code, params, new OkHttpClientManager.ResultCallback<String>() {
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

    private void Request(Map<String, String> params) {
        OkHttpClientManager.postAsyn(ForgetPasswordActivity.this, URLs.ForgetPassword, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                textView2.setClickable(true);
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                textView2.setClickable(true);
                hideProgress();
                MyLogger.i(">>>>>>>>>重置密码" + response);
                myToast(getString(R.string.forgetpassword_h8));
                finish();
            }
        }, false);

    }

    @Override
    protected void updateView() {
        titleView.setTitle("忘记密码");
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
