package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;

/**
 * Created by zyz on 2019-11-21.
 * 确认开票
 */
public class AddInvoiceActivity extends BaseActivity {
    String id = "", head_up = "", duty_paragraph = "", bank = "", reg_addr = "", phone = "", price = "", tax_point = "", tax_amount = "";
    LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4, linearLayout5, linearLayout6,
            linearLayout7, linearLayout8, linearLayout9, linearLayout10, linearLayout11, linearLayout12;
    ImageView imageView1, imageView2, imageView3, imageView4;
    EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8;
    TextView textView1, textView2;

    int invoice_type = 1, head_up_type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinvoice);
    }

    @Override
    protected void initView() {
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

        imageView1 = findViewByID_My(R.id.imageView1);
        imageView2 = findViewByID_My(R.id.imageView2);
        imageView3 = findViewByID_My(R.id.imageView3);
        imageView4 = findViewByID_My(R.id.imageView4);

        editText1 = findViewByID_My(R.id.editText1);
        editText2 = findViewByID_My(R.id.editText2);
        editText3 = findViewByID_My(R.id.editText3);
        editText4 = findViewByID_My(R.id.editText4);
        editText5 = findViewByID_My(R.id.editText5);
        editText6 = findViewByID_My(R.id.editText6);
        editText7 = findViewByID_My(R.id.editText7);
        editText8 = findViewByID_My(R.id.editText8);

        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);

        changeUI();

    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.linearLayout1:
                //普票 电子
                invoice_type = 1;
                changeUI();

                break;
            case R.id.linearLayout2:
                //专票 到付（到付）
                invoice_type = 2;
                changeUI();

                break;
            case R.id.linearLayout3:
                //企业单位
                head_up_type = 1;
                imageView3.setImageResource(R.mipmap.ic_xuanzhong);
                imageView4.setImageResource(R.mipmap.ic_weixuan);
                break;
            case R.id.linearLayout4:
                //个人/非企业单位
                head_up_type = 2;
                imageView3.setImageResource(R.mipmap.ic_weixuan);
                imageView4.setImageResource(R.mipmap.ic_xuanzhong);
                break;
            case R.id.textView2:
                if (match()) {

                }
                break;
        }
    }

    private void changeUI() {
        switch (invoice_type) {
            case 1:
                imageView1.setImageResource(R.mipmap.ic_xuanzhong);
                imageView2.setImageResource(R.mipmap.ic_weixuan);
                linearLayout7.setVisibility(View.VISIBLE);
                linearLayout8.setVisibility(View.VISIBLE);
                linearLayout9.setVisibility(View.GONE);
                linearLayout10.setVisibility(View.GONE);
                linearLayout11.setVisibility(View.GONE);
                linearLayout12.setVisibility(View.GONE);

                break;
            case 2:
                imageView1.setImageResource(R.mipmap.ic_weixuan);
                imageView2.setImageResource(R.mipmap.ic_xuanzhong);
                linearLayout7.setVisibility(View.GONE);
                linearLayout8.setVisibility(View.GONE);
                linearLayout9.setVisibility(View.VISIBLE);
                linearLayout10.setVisibility(View.VISIBLE);
                linearLayout11.setVisibility(View.VISIBLE);
                linearLayout12.setVisibility(View.VISIBLE);

                break;
        }
    }

    private boolean match() {
        head_up = editText1.getText().toString().trim();
        if (TextUtils.isEmpty(head_up)) {
            myToast("请输入发票抬头");
            return false;
        }
        duty_paragraph = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(duty_paragraph)) {
            myToast("请输入纳税人识别号");
            return false;
        }
        bank = editText5.getText().toString().trim();
        if (TextUtils.isEmpty(bank)) {
            myToast("请输入开户银行");
            return false;
        }
        reg_addr = editText7.getText().toString().trim();
        if (TextUtils.isEmpty(reg_addr)) {
            myToast("请输入注册地址");
            return false;
        }
        phone = editText8.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            myToast("请输入固定电话");
            return false;
        }

        return true;
    }

    @Override
    protected void updateView() {
        titleView.setTitle("确认开票");
    }
}
