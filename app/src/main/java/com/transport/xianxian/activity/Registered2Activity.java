package com.transport.xianxian.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
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

import static com.transport.xianxian.net.OkHttpClientManager.HOST;


/**
 * Created by fafukeji01 on 2017/4/25.
 * 注册
 */

public class Registered2Activity extends BaseActivity {
    //    String
    private EditText editText1, editText2, editText3;
    private TextView textView2;
    private ImageView imageView1;
    boolean isgouxuan = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered2);

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
        textView2 = findViewByID_My(R.id.textView2);
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
                bundle.putString("url", HOST + "/api/driver/article/gvrp");
                CommonUtil.gotoActivityWithData(Registered2Activity.this, WebContentActivity.class, bundle, false);

                break;
            case R.id.textView2:
                //下一步
                if (match()) {
                    textView2.setClickable(false);
                    showProgress(true, getString(R.string.registered_h14));
                    HashMap<String, String> params = new HashMap<>();
//                    params.put("nickname", nickname);//昵称
//                    params.put("invite_code", num);//邀请码
//                    params.put("mobile_state_code", localUserInfo.getMobile_State_Code());
                    RequestRegistered(params);//注册
                }
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
    private void RequestRegistered(Map<String, String> params) {
        OkHttpClientManager.postAsyn(Registered2Activity.this, URLs.Registered, params, new OkHttpClientManager.ResultCallback<String>() {
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
//                localUserInfo.setTime(System.currentTimeMillis() + "");
                JSONObject jObj;
                try {
                    jObj = new JSONObject(response);

                    JSONObject jObj1 = jObj.getJSONObject("data");
                    //保存Token
                    String token = jObj1.getString("fresh_token");
                    localUserInfo.setToken(token);
                    //保存用户id
                    final String id = jObj1.getString("id");
                    localUserInfo.setUserId(id);
                    //保存电话号码
                    String mobile = jObj1.getString("mobile");
                    localUserInfo.setPhoneNumber(mobile);
                    /*//保存用户昵称
                    String nickname = jObj1.getString("nickname");
                    localUserInfo.setNickname(nickname);*/

                    //环信注册
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().createAccount(mobile, "123456");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideProgress();
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("isShowAd", 1);
                                        CommonUtil.gotoActivityWithFinishOtherAllAndData(
                                                Registered2Activity.this, MainActivity.class,
                                                bundle, true);
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
                                                MyLogger.i("用户已存在 code: " + errorCode + ", message:" + message);
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
                                                MyLogger.i("账户注册失败 code: " + errorCode + ", message:" + message);
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
}
