package com.transport.xianxian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;

/**
 * Created by zyz on 2019-10-03.
 * 提现
 */
public class TakeCashActivity extends BaseActivity {
    TextView textView, textView1, textView2, textView3, textView4;
    EditText editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takecash);
    }

    @Override
    protected void initView() {
        textView = findViewByID_My(R.id.textView);
        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);
        textView4 = findViewByID_My(R.id.textView4);

        editText1 = findViewByID_My(R.id.editText1);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.linearLayout1:
                //选择到账方式

                break;
            case R.id.textView5:
                //全部提现

                break;
            case R.id.textView:
                //提现

                break;
        }
    }

    @Override
    protected void updateView() {
        titleView.setTitle("提现");
    }
}
