package com.delivery.xianxian.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.cy.dialog.BaseDialog;
import com.delivery.xianxian.R;
import com.delivery.xianxian.adapter.TemperatureAdapter;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.AddFeeModel;
import com.delivery.xianxian.model.ConfirmOrderModel;
import com.delivery.xianxian.model.ConfirmOrderPayModel;
import com.delivery.xianxian.model.TemperatureModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.CommonUtil;
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

import static com.delivery.xianxian.net.OkHttpClientManager.HOST;
import static com.delivery.xianxian.net.OkHttpClientManager.IMGHOST;

/**
 * Created by zyz on 2019-11-16.
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseActivity {
    List<TemperatureModel.TemperatureListBean> list_h = new ArrayList<>();
    int i1 = -1;

    AddFeeModel model;
    String city = "", car_type_id = "", use_type = "", is_plan = "", plan_time = "", addr_ids = "",
            temperature = "", name = "", mobile = "", urgent_fee = "0", remark = "", other = "",
            contacts_name = "", contacts_mobile = "",
            goods_name = "", goods_quantity = "", goods_weight = "", goods_bulk = "", goods_send_home = "";
    TextView textView, textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8,
            textView9, textView10, textView11, textView12;

    private ImageView imageView1;
    boolean isgouxuan = true;

    int pay_item = 0;

    List<ConfirmOrderModel.PayTypeListBean> list_pay = new ArrayList<>();
    String pay_type = "", order_id = "";

    double money = 0, money1 = 0, money2 = 0;//合计费用，提前预冷费，加急费

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
                        MyLogger.i("支付成功" + payResult);
                        showToast("支付成功");

                        localUserInfo.setIsordertrue("1");//是否下单成功//0未成功，1成功
                        //跳转订单派送
                        Bundle bundle = new Bundle();
                        bundle.putString("id", order_id);
                        CommonUtil.gotoActivityWithData(ConfirmOrderActivity.this, OrderDetailsActivity.class, bundle, true);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        MyLogger.i("支付失败" + payResult);
                        showToast("支付失败");
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
        setContentView(R.layout.activity_confirmorder);
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }*/

    @Override
    protected void initView() {
        textView = findViewByID_My(R.id.textView);
        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);
        textView4 = findViewByID_My(R.id.textView4);
        textView5 = findViewByID_My(R.id.textView5);
        textView6 = findViewByID_My(R.id.textView6);
        textView7 = findViewByID_My(R.id.textView7);
        textView8 = findViewByID_My(R.id.textView8);
        textView9 = findViewByID_My(R.id.textView9);
        textView10 = findViewByID_My(R.id.textView10);
        textView11 = findViewByID_My(R.id.textView11);
        textView12 = findViewByID_My(R.id.textView12);

        imageView1 = findViewByID_My(R.id.imageView1);
    }

    @Override
    protected void initData() {
        model = (AddFeeModel) getIntent().getSerializableExtra("AddFeeModel");
        city = getIntent().getStringExtra("city");
        car_type_id = getIntent().getStringExtra("car_type_id");
        use_type = getIntent().getStringExtra("use_type");
        is_plan = getIntent().getStringExtra("is_plan");
        plan_time = getIntent().getStringExtra("plan_time");
        addr_ids = getIntent().getStringExtra("addr_ids");
        goods_name = getIntent().getStringExtra("goods_name");
        goods_weight = getIntent().getStringExtra("goods_weight");
        goods_quantity = getIntent().getStringExtra("goods_quantity");
        goods_bulk = getIntent().getStringExtra("goods_bulk");
        goods_send_home = getIntent().getStringExtra("goods_send_home");

        if (is_plan.equals("1") && !plan_time.equals("")) {
            textView6.setText(plan_time);
        } else {
            textView6.setText("现在用车");
        }

        money = Double.valueOf(model.getPrice());
        textView11.setText("合计费用：￥" + money);

        textView7.setText(localUserInfo.getNickname());
        textView8.setText(localUserInfo.getPhonenumber());

        //获取预冷数据
        requestServer();

    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();
        showProgress(true, getString(R.string.app_loading));
        Map<String, String> params = new HashMap<>();
        params.put("token", localUserInfo.getToken());
        params.put("type", "get_temperature");
        params.put("car_type_id", car_type_id);
        Request(params);
    }

    private void Request(Map<String, String> params) {
        OkHttpClientManager.postAsyn(ConfirmOrderActivity.this, URLs.GetTemperature, params, new OkHttpClientManager.ResultCallback<TemperatureModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(TemperatureModel response) {
                MyLogger.i(">>>>>>>>>获取温层费用：" + response);
                hideProgress();
                list_h = response.getTemperature_list();
                /*if (list_h.size() > 0) {
                    temperature = list_h.get(0).getId();
                    textView1.setText(list_h.get(0).getTitle() + "：" + list_h.get(0).getTemperature());
                    textView2.setText("¥ " + list_h.get(0).getPrice());
                }*/
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.linearLayout1:
                //提前预冷
                dialog = new BaseDialog(ConfirmOrderActivity.this);
                dialog.contentView(R.layout.dialog_temperature)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                ListView listView_h = dialog.findViewById(R.id.listView);
                TemperatureAdapter adapter_h = new TemperatureAdapter(ConfirmOrderActivity.this, list_h);
                adapter_h.setSelectItem(i1);
                listView_h.setAdapter(adapter_h);
                listView_h.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.dismiss();
                        i1 = position;
                        adapter_h.setSelectItem(position);

                        temperature = list_h.get(position).getId();
                        textView1.setText(list_h.get(position).getTitle() + "：" + list_h.get(position).getTemperature());
                        textView2.setText("¥ " + list_h.get(position).getPrice());

                        money1 = Double.valueOf(list_h.get(position).getPrice());

                        textView11.setText("合计费用：￥" + (money + money1 + money2));
                    }
                });
                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        temperature = "";
                        textView1.setText("");
                        textView2.setText("请选择");
                        money1 = 0;
                        textView11.setText("合计费用：￥" + (money + money1 + money2));
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.linearLayout2:
                //加急费
                dialog = new BaseDialog(ConfirmOrderActivity.this);
                dialog.contentView(R.layout.dialog_jiajifei)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                /*TextView textView1 = dialog.findViewById(R.id.textView1);
                textView1.setText("加急费用");*/
                final EditText editText1 = dialog.findViewById(R.id.editText1);
                if (money2 > 0) {
                    editText1.setText("" + money2);
                }

                /*TextView textView3 = dialog.findViewById(R.id.textView3);
                textView3.setText("确认");*/
                dialog.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText1.getText().toString().trim().equals("")) {
                            CommonUtil.hideSoftKeyboard_fragment(v, ConfirmOrderActivity.this);
                            dialog.dismiss();

                            money2 = Double.valueOf(String.format("%.2f", Double.valueOf(editText1.getText().toString().trim())));

                            urgent_fee = money2 + "";
                            textView3.setText("¥ " + money2);

                            textView11.setText("合计费用：￥" + (money + money1 + money2));
                        } else {
                            myToast("请输入与加急费用");
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
            case R.id.linearLayout3:
                //备注
                dialog = new BaseDialog(ConfirmOrderActivity.this);
                dialog.contentView(R.layout.dialog_beizhu)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                /*TextView textView_1 = dialog.findViewById(R.id.textView1);
                textView_1.setText("备注");*/
                final EditText editText_1 = dialog.findViewById(R.id.editText1);
//                editText_1.setHint("输入备注（如货物类别，或需要什么协助）");
//                TextView textView_3 = dialog.findViewById(R.id.textView3);
//                textView_3.setText("确认");
                dialog.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText_1.getText().toString().trim().equals("")) {
                            CommonUtil.hideSoftKeyboard_fragment(v, ConfirmOrderActivity.this);
                            dialog.dismiss();

                            remark = editText_1.getText().toString().trim();
                            textView4.setText("备注：" + editText_1.getText().toString().trim());
                        } else {
                            myToast("请输入备注");
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
            case R.id.linearLayout4:
                //添加其他
                Intent intent1 = new Intent(ConfirmOrderActivity.this, AddOtherActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", 10004);
                intent1.putExtras(bundle1);
                startActivityForResult(intent1, 10004, bundle1);
                break;
            case R.id.textView9:
                //通讯录
                selectContact();
                break;
            case R.id.imageView1:
                //勾选协议
                isgouxuan = !isgouxuan;
                if (isgouxuan)
                    imageView1.setImageResource(R.mipmap.ic_gouxuan);
                else
                    imageView1.setImageResource(R.mipmap.ic_weigouxuan);
                break;
            case R.id.textView10:
                //协议
                Bundle bundle = new Bundle();
                bundle.putString("url", HOST + "/api/article/detail-html?id=8cfd6cf28dc97604e4d8b58461461e21");
                CommonUtil.gotoActivityWithData(ConfirmOrderActivity.this, WebContentActivity.class, bundle, false);
                break;
            case R.id.textView12:
                //费用明细
                Bundle bundle4 = new Bundle();
                bundle4.putString("city", city);
                bundle4.putString("car_type_id", car_type_id);
                bundle4.putString("use_type", use_type);
                bundle4.putSerializable("AddFeeModel", model);
                CommonUtil.gotoActivityWithData(ConfirmOrderActivity.this, FeeDetailActivity.class, bundle4, false);
                break;

            case R.id.textView:
                //确认下单
                if (match()) {
                    textView.setClickable(false);
                    Map<String, String> params = new HashMap<>();
                    params.put("token", localUserInfo.getToken());
                    params.put("city", city);
                    params.put("car_type_id", car_type_id);
                    params.put("use_type", use_type + "");
                    params.put("is_plan", is_plan);
                    params.put("plan_time", plan_time);
                    params.put("mileage", model.getMillage());
                    params.put("pre_time", model.getDuration());
                    params.put("price", model.getPrice());
                    params.put("addr_ids", addr_ids);
                    params.put("temperature", temperature);
                    params.put("urgent_fee", urgent_fee);
                    params.put("other", other);
                    params.put("remark", remark);
                    params.put("contacts_name", textView7.getText().toString().trim());
                    params.put("contacts_mobile", textView8.getText().toString().trim());
                    params.put("urgent", "");
                    params.put("owner_fee", "");
                    params.put("other_fee", "");
                    params.put("goods_name", goods_name);
                    params.put("goods_weight", goods_weight);
                    params.put("goods_quantity", goods_quantity);
                    params.put("goods_bulk", goods_bulk);
                    params.put("goods_send_home", goods_send_home);
                    RequestAdd(params);
                }

                break;
        }
    }

    private void RequestAdd(Map<String, String> params) {
        OkHttpClientManager.postAsyn(ConfirmOrderActivity.this, URLs.ConfirmOrder, params, new OkHttpClientManager.ResultCallback<ConfirmOrderModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    if (info.contains("认证")) {
                        showToast(info,
                                "取消", "去认证",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("item", 1);
                                        CommonUtil.gotoActivityWithFinishOtherAllAndData(ConfirmOrderActivity.this,
                                                MainActivity.class, bundle, true);
                                    }
                                });
                    } else {
                        showToast(info);
                    }
                }
                textView.setClickable(true);
            }

            @Override
            public void onResponse(ConfirmOrderModel response) {
                MyLogger.i(">>>>>>>>>下单" + response);
                hideProgress();
                textView.setClickable(true);
//                myToast("下单成功");
                //弹出支付框
//                showToast("下单成功，确认支付");
                dialog = new BaseDialog(ConfirmOrderActivity.this);
                dialog.contentView(R.layout.dialog_pay)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(false)
                        .dimAmount(0.8f)
                        .show();
                TextView tv1 = dialog.findViewById(R.id.tv1);
                tv1.setText(response.getTotal_price());

                list_pay = response.getPay_type_list();
                RecyclerView rv = dialog.findViewById(R.id.rv);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ConfirmOrderActivity.this);
                rv.setLayoutManager(mLinearLayoutManager);
                CommonAdapter<ConfirmOrderModel.PayTypeListBean> mAdapter = new CommonAdapter<ConfirmOrderModel.PayTypeListBean>
                        (ConfirmOrderActivity.this, R.layout.item_pay, list_pay) {
                    @Override
                    protected void convert(ViewHolder holder, ConfirmOrderModel.PayTypeListBean model, int position) {
                        holder.setText(R.id.tv1, model.getTitle());//标题
                        TextView tv2 = holder.getView(R.id.tv2);
                        if (!model.getSub_title().equals("")) {
                            tv2.setVisibility(View.VISIBLE);
                            tv2.setText(model.getSub_title());
                        } else {
                            tv2.setVisibility(View.GONE);
                        }
                        ImageView iv1 = holder.getView(R.id.iv1);
                        Glide.with(ConfirmOrderActivity.this).load(IMGHOST + model.getIcon())
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
                        pay_type = list_pay.get(i).getType();
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        return false;
                    }
                });
                rv.setAdapter(mAdapter);

                TextView tv3 = dialog.findViewById(R.id.tv3);
                tv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        showProgress(true, "正在获取支付订单...");

                        Map<String, String> params = new HashMap<>();
                        params.put("token", localUserInfo.getToken());
                        params.put("pay_type", response.getPay_type_list().get(pay_item).getType() + "");
                        params.put("t_indent_id", response.getId());
                        RequestPay(params, response.getId());

                    }
                });

                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void RequestPay(Map<String, String> params, String id) {
        OkHttpClientManager.postAsyn(ConfirmOrderActivity.this, URLs.ConfirmOrder_Pay, params, new OkHttpClientManager.ResultCallback<ConfirmOrderPayModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(ConfirmOrderPayModel response) {
                MyLogger.i(">>>>>>>>>下单" + response);
                hideProgress();
//                myToast("下单成功");
                order_id = id;
                MyLogger.i(">>>>>>" + response.getResult());
                switch (pay_type) {
                    case "1":
                        //余额
                    case "2":
                        //微信
                        showToast("支付成功");
                        localUserInfo.setIsordertrue("1");//是否下单成功//0未成功，1成功
                        //跳转订单派送
                        Bundle bundle = new Bundle();
                        bundle.putString("id", order_id);
                        CommonUtil.gotoActivityWithData(ConfirmOrderActivity.this, OrderDetailsActivity.class, bundle, true);
                        break;
                    case "3":
                        //支付宝
                        //弹出支付宝
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(ConfirmOrderActivity.this);
                                Map<String, String> result = alipay.payV2(response.getResult(), true);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                        break;
                }

            }
        });
    }

    private boolean match() {
        if (!isgouxuan) {
            myToast("下单请勾选同意遵守《货物托运居间服务协议》");
            return false;
        }
       /* if (temperature.equals("")) {
            myToast("请选择提前预冷");
            return false;
        }*/
        return true;
    }

    @Override
    protected void updateView() {
        titleView.setTitle("确认订单");
    }

    /**
     * **************************************打开通讯录，选择联系人***************************************
     */
    public static final int REQUEST_SELECT_PHONE_NUMBER = 1;

    public void selectContact() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {

            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
            Cursor cursor = getContentResolver().query(contactUri, projection,
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                name = cursor.getString(nameIndex);      //联系人姓名
                mobile = cursor.getString(numberIndex);  //联系人号码
                textView7.setText(name);
                textView8.setText(mobile);

                cursor.close();
            }
        }
        MyLogger.i(">>>>>requestCode:" + requestCode +
                "\n>>>>>resultCode:" + resultCode +
                "\n>>>>>data:" + data);
        switch (requestCode) {
            case 10004:
                //其他需求
                if (data != null) {
                    Bundle bundle1 = data.getExtras();
                    other = bundle1.getString("other");
                    textView5.setText(bundle1.getString("other_s"));
                }
                break;
        }
    }
}
