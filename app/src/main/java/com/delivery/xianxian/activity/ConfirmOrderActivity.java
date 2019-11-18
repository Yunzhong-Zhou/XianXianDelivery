package com.delivery.xianxian.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cy.dialog.BaseDialog;
import com.delivery.xianxian.R;
import com.delivery.xianxian.adapter.TemperatureAdapter;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.TemperatureModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.CommonUtil;
import com.delivery.xianxian.utils.MyLogger;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.delivery.xianxian.net.OkHttpClientManager.HOST;

/**
 * Created by zyz on 2019-11-16.
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseActivity {
    List<TemperatureModel.TemperatureListBean> list_h = new ArrayList<>();
    int i1 = 0;

    String city = "", car_type_id = "", use_type = "", is_plan = "", plan_time = "", mileage = "",
            pre_time = "", price = "", addr_ids = "", temperature = "", name = "", mobile = "",
            urgent_fee ="0",remark = "";
    TextView textView, textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8,
            textView9, textView10, textView11, textView12;

    private ImageView imageView1;
    boolean isgouxuan = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
    }

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
        city = getIntent().getStringExtra("city");
        car_type_id = getIntent().getStringExtra("car_type_id");
        use_type = getIntent().getStringExtra("use_type");
        is_plan = getIntent().getStringExtra("is_plan");
        plan_time = getIntent().getStringExtra("plan_time");
        mileage = getIntent().getStringExtra("mileage");
        pre_time = getIntent().getStringExtra("pre_time");
        price = getIntent().getStringExtra("price");
        addr_ids = getIntent().getStringExtra("addr_ids");

        textView6.setText(plan_time);
        textView11.setText("合计费用：￥"+price);
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
        params.put("car_type_id", "1");
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
                if (list_h.size() > 0) {
                    temperature = list_h.get(0).getId();
                    textView1.setText(list_h.get(0).getTitle() + "：" + list_h.get(0).getTemperature());
                    textView2.setText("¥ " + list_h.get(0).getPrice());
                }
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
                    }
                });
                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                /*TextView textView3 = dialog.findViewById(R.id.textView3);
                textView3.setText("确认");*/
                dialog.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText1.getText().toString().trim().equals("")) {
                            CommonUtil.hideSoftKeyboard_fragment(v, ConfirmOrderActivity.this);
                            dialog.dismiss();
                            urgent_fee = editText1.getText().toString().trim();
                            textView3.setText("¥ " + editText1.getText().toString().trim());
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
                bundle.putString("url", HOST + "/api/driver/article/login");
                CommonUtil.gotoActivityWithData(ConfirmOrderActivity.this, WebContentActivity.class, bundle, false);
                break;
            case R.id.textView12:
                //明细
                Bundle bundle4 = new Bundle();
                bundle4.putString("city", city);
                bundle4.putString("car_type_id", car_type_id);
                bundle4.putString("use_type", use_type);
                bundle4.putString("addr_ids", addr_ids);
                CommonUtil.gotoActivityWithData(ConfirmOrderActivity.this, FeeDetailActivity.class, bundle4, false);
                break;

            case R.id.textView:
                //确认下单
                if (match()) {
                    //先计算费用-返回路程等信息
                    Map<String, String> params = new HashMap<>();
                    params.put("token", localUserInfo.getToken());
                    params.put("city", city);
                    params.put("car_type_id", car_type_id);
                    params.put("use_type", use_type + "");
                    params.put("is_plan", is_plan);
                    params.put("plan_time", plan_time);
                    params.put("mileage", mileage);
                    params.put("pre_time", pre_time);
                    params.put("price", price);
                    params.put("addr_ids", addr_ids);
                    params.put("temperature", temperature);
                    params.put("urgent_fee", urgent_fee);
//                    params.put("urgent", urgent);
//                    params.put("owner_fee", owner_fee);
//                    params.put("other_fee", other_fee);
//                    params.put("other", other);
                    params.put("remark", remark);
                    RequestAdd(params);
                }

                break;
        }
    }
    private void RequestAdd(Map<String, String> params) {
        OkHttpClientManager.postAsyn(ConfirmOrderActivity.this, URLs.ConfirmOrder, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                MyLogger.i(">>>>>>>>>下单" + response);
                hideProgress();
            }
        });
    }
    private boolean match() {
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
    }
}
