package com.delivery.xianxian.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.delivery.xianxian.R;
import com.delivery.xianxian.adapter.Recharge_GridViewAdapter;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.ConfirmOrderModel;
import com.delivery.xianxian.model.RechargeModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.MyLogger;
import com.delivery.xianxian.utils.alipay.PayResult;
import com.squareup.okhttp.Request;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.delivery.xianxian.net.OkHttpClientManager.IMGHOST;

/**
 * Created by zyz on 2019-11-25.
 * 充值
 */
public class RechargeActivity extends BaseActivity {
    GridView gridView;
    Recharge_GridViewAdapter adapter;
    int pay_item = 0;
    RecyclerView recyclerView;
    List<RechargeModel.PayTypeListBean> list = new ArrayList<>();
    CommonAdapter<RechargeModel.PayTypeListBean> mAdapter;
    TextView textView, textView1;

    String pay_type = "", money = "";

    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showToast("支付成功"+payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showToast("支付失败" + payResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
    }

    @Override
    protected void initView() {
        gridView = findViewByID_My(R.id.gridView);
        recyclerView = findViewByID_My(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(RechargeActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        textView = findViewByID_My(R.id.textView);
        textView1 = findViewByID_My(R.id.textView1);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay_type = list.get(pay_item).getType();
                Map<String, String> params = new HashMap<>();
                params.put("token", localUserInfo.getToken());
                params.put("pay_type", pay_type);
                params.put("money", money);
                RequestPay(params);
            }
        });
    }


    @Override
    protected void initData() {
        requestServer();
    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();
        showProgress(true, getString(R.string.app_loading));
        String string = "";
        string = "?token=" + localUserInfo.getToken();
        Request(string);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(RechargeActivity.this, URLs.Recharge + string,
                new OkHttpClientManager.ResultCallback<RechargeModel>() {
                    @Override
                    public void onError(Request request, String info, Exception e) {
                        showErrorPage();
                        hideProgress();
                        if (!info.equals("")) {
                            myToast(info);
                        }
                    }

                    @Override
                    public void onResponse(RechargeModel response) {
                        showContentPage();
                        MyLogger.i(">>>>>>>>>充值" + response);
                        //充值金额
                        if (response.getMoney_list().size() > 0) {
                            money = response.getMoney_list().get(0).getPrice();
                            textView1.setText("金额：￥" + response.getMoney_list().get(0).getPrice());
                            adapter = new Recharge_GridViewAdapter(RechargeActivity.this, response.getMoney_list());
                            gridView.setAdapter(adapter);
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    adapter.setSelectItem(position);
                                    adapter.notifyDataSetChanged();
                                    textView1.setText("金额：￥" + response.getMoney_list().get(position).getPrice());
                                    money = response.getMoney_list().get(position).getPrice();
                                }
                            });
                        }


                        //充值方式
                        list = response.getPay_type_list();
                        mAdapter = new CommonAdapter<RechargeModel.PayTypeListBean>
                                (RechargeActivity.this, R.layout.item_pay, list) {
                            @Override
                            protected void convert(ViewHolder holder, RechargeModel.PayTypeListBean model, int position) {
                                holder.setText(R.id.tv1, model.getTitle());//标题
                                TextView tv2 = holder.getView(R.id.tv2);
                                tv2.setVisibility(View.GONE);
                                /*if (!model.getSub_title().equals("")) {
                                    tv2.setVisibility(View.VISIBLE);
                                    tv2.setText(model.getSub_title());
                                } else {
                                    tv2.setVisibility(View.GONE);
                                }*/
                                ImageView iv1 = holder.getView(R.id.iv1);
                                Glide.with(RechargeActivity.this).load(IMGHOST + model.getIcon())
                                        .centerCrop()
//                            .placeholder(R.mipmap.headimg)//加载站位图
//                            .error(R.mipmap.headimg)//加载失败
                                        .into(iv1);//加载图片
                                ImageView iv2 = holder.getView(R.id.iv2);
                                if (pay_item == position) {
                                    iv2.setImageResource(R.mipmap.ic_xuanzhong);
                                } else {
                                    iv2.setImageResource(R.mipmap.ic_weixuan);
                                }

                            }
                        };
                        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                                pay_item = i;
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                                return false;
                            }
                        });

                        recyclerView.setAdapter(mAdapter);
                        hideProgress();

                    }

                });
    }

    private void RequestPay(Map<String, String> params) {
        OkHttpClientManager.postAsyn(RechargeActivity.this, URLs.Recharge_Add, params, new OkHttpClientManager.ResultCallback<ConfirmOrderModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(ConfirmOrderModel response) {
                MyLogger.i(">>>>>>>>>充值" + response);
                hideProgress();
                myToast("订单提交成功，等待后台审核");

                //弹出支付宝
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(RechargeActivity.this);
                        Map <String,String> result = alipay.payV2("",true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
        });
    }

    @Override
    protected void updateView() {
        titleView.setTitle("充值");
    }
}
