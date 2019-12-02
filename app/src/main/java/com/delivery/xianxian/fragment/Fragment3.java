package com.delivery.xianxian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cy.dialog.BaseDialog;
import com.delivery.xianxian.R;
import com.delivery.xianxian.activity.ChangePasswordActivity;
import com.delivery.xianxian.activity.ChatMainActivity;
import com.delivery.xianxian.activity.FeeModelActivity;
import com.delivery.xianxian.activity.InvoiceActivity;
import com.delivery.xianxian.activity.JiFenShangChengActivity;
import com.delivery.xianxian.activity.LoginActivity;
import com.delivery.xianxian.activity.MainActivity;
import com.delivery.xianxian.activity.MyDriverActivity;
import com.delivery.xianxian.activity.MyProfileActivity;
import com.delivery.xianxian.activity.OrderListActivity;
import com.delivery.xianxian.activity.ServiceCenterActivity;
import com.delivery.xianxian.activity.WalletActivity;
import com.delivery.xianxian.base.BaseFragment;
import com.delivery.xianxian.model.Fragment3Model;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.CommonUtil;
import com.delivery.xianxian.utils.MyLogger;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

import static com.delivery.xianxian.net.OkHttpClientManager.IMGHOST;

/**
 * Created by fafukeji01 on 2016/1/6.
 * 我的
 */
public class Fragment3 extends BaseFragment {
    ImageView imageView1;
    TextView textView1, textView2;
    LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4, linearLayout5, linearLayout6,
            linearLayout7, linearLayout8, linearLayout9, linearLayout10, linearLayout11, linearLayout12, linearLayout13, linearLayout14;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.item == 1) {
            requestServer();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        /*if (MainActivity.item == 2) {
            requestServer();
        }*/
    }

    @Override
    protected void initView(View view) {
//        CommonUtil.setMargins(findViewByID_My(R.id.springView),0, (int) CommonUtil.getStatusBarHeight(getActivity()),0,0);
        setSpringViewMore(false);//不需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                requestCenter("?token=" + localUserInfo.getToken());
            }

            @Override
            public void onLoadmore() {

            }
        });
        imageView1 = findViewByID_My(R.id.imageView1);
        textView1 = findViewByID_My(R.id.textView1);
        textView1.setOnClickListener(this);
        textView2 = findViewByID_My(R.id.textView2);
        if (!localUserInfo.getNickname().equals("")) {
            textView1.setText(localUserInfo.getNickname());
        }
        textView2.setText(localUserInfo.getPhonenumber());
        if (!localUserInfo.getUserImage().equals(""))
            Glide.with(getActivity())
                    .load(IMGHOST + localUserInfo.getUserImage())
                    .centerCrop()
//                    .placeholder(R.mipmap.headimg)//加载站位图
//                    .error(R.mipmap.headimg)//加载失败
                    .into(imageView1);//加载图片
        else
            imageView1.setImageResource(R.mipmap.headimg);

        linearLayout1 = findViewByID_My(R.id.linearLayout1);
        linearLayout2 = findViewByID_My(R.id.linearLayout2);
        linearLayout3 = findViewByID_My(R.id.linearLayout3);
        linearLayout4 = findViewByID_My(R.id.linearLayout4);
        linearLayout5 = findViewByID_My(R.id.linearLayout5);
        linearLayout6 = findViewByID_My(R.id.linearLayout6);
        linearLayout7 = findViewByID_My(R.id.linearLayout7);
        linearLayout8 = findViewByID_My(R.id.linearLayout8);
        linearLayout9 = findViewByID_My(R.id.linearLayout9);
        linearLayout10 = findViewByID_My(R.id.linearLayout10);
        linearLayout11 = findViewByID_My(R.id.linearLayout11);
        linearLayout12 = findViewByID_My(R.id.linearLayout12);
        linearLayout13 = findViewByID_My(R.id.linearLayout13);
        linearLayout14 = findViewByID_My(R.id.linearLayout14);

        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);
        linearLayout5.setOnClickListener(this);
        linearLayout6.setOnClickListener(this);
        linearLayout7.setOnClickListener(this);
        linearLayout8.setOnClickListener(this);
        linearLayout9.setOnClickListener(this);
        linearLayout10.setOnClickListener(this);
        linearLayout11.setOnClickListener(this);
        linearLayout12.setOnClickListener(this);
        linearLayout13.setOnClickListener(this);
        linearLayout14.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        requestServer();
    }

    private void requestCenter(String string) {
        OkHttpClientManager.getAsyn(getActivity(), URLs.Center + string, new OkHttpClientManager.ResultCallback<Fragment3Model>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(Fragment3Model response) {
                MyLogger.i(">>>>>>>>>我的" + response);
                //昵称
                if (!response.getNickname().equals("")) {
                    textView1.setText(response.getNickname());
                    localUserInfo.setNickname(response.getNickname());
                }
                //头像
                localUserInfo.setUserImage(response.getHead());
                if (!response.getHead().equals(""))
                    Glide.with(getActivity()).load(IMGHOST + response.getHead())
                            .centerCrop()
//                            .placeholder(R.mipmap.headimg)//加载站位图
//                            .error(R.mipmap.headimg)//加载失败
                            .into(imageView1);//加载图片
                else
                    imageView1.setImageResource(R.mipmap.headimg);

                hideProgress();
            }
        });
    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();
        showProgress(true, getString(R.string.app_loading));
        requestCenter("?token=" + localUserInfo.getToken());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView1:
                //修改昵称
                dialog = new BaseDialog(getActivity());
                dialog.contentView(R.layout.dialog_changename)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
//                        TextView textView1 = dialog.findViewById(R.id.textView1);
//                        textView1.setText(e.getMessage());
                final EditText editText1 = dialog.findViewById(R.id.editText1);
                editText1.setSingleLine();
                dialog.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText1.getText().toString().trim().equals("")) {
                            CommonUtil.hideSoftKeyboard_fragment(v, getActivity());
                            dialog.dismiss();
                            showProgress(true, "正在修改，请稍候...");
                            HashMap<String, String> params = new HashMap<>();
                            params.put("nickname", editText1.getText().toString().trim());
                            params.put("head", "");
                            params.put("token", localUserInfo.getToken());
                            RequestNickname(params, editText1.getText().toString().trim());
                        } else {
                            myToast("请输入昵称");
                        }
                    }
                });
                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.linearLayout1:
                //个人资料
                CommonUtil.gotoActivity(getActivity(), MyProfileActivity.class);
                break;
            case R.id.linearLayout2:
                //货运订单
                CommonUtil.gotoActivity(getActivity(), OrderListActivity.class);
                break;
            case R.id.linearLayout3:
                //我的钱包
                CommonUtil.gotoActivity(getActivity(), WalletActivity.class);
                break;
            case R.id.linearLayout4:
                //积分商城
                CommonUtil.gotoActivity(getActivity(), JiFenShangChengActivity.class);
                break;
            case R.id.linearLayout5:
                //申请发票
                CommonUtil.gotoActivity(getActivity(), InvoiceActivity.class);
                break;
            case R.id.linearLayout6:
                //客服中心
                CommonUtil.gotoActivity(getActivity(), ServiceCenterActivity.class);
                break;
            case R.id.linearLayout7:
                //我的司机
                CommonUtil.gotoActivity(getActivity(), MyDriverActivity.class);
                break;
            case R.id.linearLayout8:
                //修改密码
                CommonUtil.gotoActivity(getActivity(), ChangePasswordActivity.class);
                break;
            case R.id.linearLayout9:
                //用户协议

                break;
            case R.id.linearLayout10:
                //收费标准
                CommonUtil.gotoActivity(getActivity(), FeeModelActivity.class);
                break;
            case R.id.linearLayout11:
                //关于我们
//                CommonUtil.gotoActivity(getActivity(), JiangLiHuoDongActivity.class);
                break;
            case R.id.linearLayout12:
                //分享APP
                Intent share_intent1 = new Intent();
                share_intent1.setAction(Intent.ACTION_SEND);
//                    share_intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                share_intent1.setType("text/plain");
                share_intent1.putExtra(Intent.EXTRA_TEXT, "鲜鲜拉\n"
                        +"www.baidu.com");

                share_intent1 = Intent.createChooser(share_intent1, "分享");
                startActivity(share_intent1);
                break;
            case R.id.linearLayout13:
                //会话列表
                CommonUtil.gotoActivity(getActivity(), ChatMainActivity.class);
                break;
            case R.id.linearLayout14:
                //退出登录
                showToast("确认退出登录吗？",
                        getString(R.string.app_confirm),
                        getString(R.string.app_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                showProgress(true, "正在注销登录，请稍候...");
                                requestOut("?token=" + localUserInfo.getToken());

                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                break;
        }
    }

    private void requestOut(String string) {
        OkHttpClientManager.getAsyn(getActivity(), URLs.Out + string, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                MyLogger.i(">>>>>>>>>退出" + response);
                hideProgress();
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
                        CommonUtil.gotoActivityWithFinishOtherAll(getActivity(), LoginActivity.class, true);
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String error) {

                    }
                });
            }
        });
    }

    private void RequestNickname(Map<String, String> params, String string) {
        OkHttpClientManager.postAsyn(getActivity(), URLs.ChangeProfile, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                hideProgress();
                MyLogger.i(">>>>>>>>>修改昵称" + response);
                myToast("修改昵称成功");
                localUserInfo.setNickname(string);
                textView1.setText(string);
            }
        }, false);

    }

    @Override
    protected void updateView() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
