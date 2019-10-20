package com.transport.xianxian.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;

/**
 * Created by zyz on 2019-10-20.
 * 增加附加费
 */
public class AddSurchargeActivity extends BaseActivity {
    EditText editText1, editText2, editText3, editText4;
    TextView textView1, textView2;
    TextView tv_add, tv_baocun, tv_queren;
    LinearLayout add_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsurcharge);
    }

    @Override
    protected void initView() {
        editText1 = findViewByID_My(R.id.editText1);
        editText2 = findViewByID_My(R.id.editText2);
        editText3 = findViewByID_My(R.id.editText3);
        editText4 = findViewByID_My(R.id.editText4);
        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        tv_add = findViewByID_My(R.id.tv_add);
        tv_baocun = findViewByID_My(R.id.tv_baocun);
        tv_queren = findViewByID_My(R.id.tv_queren);
        add_ll = findViewByID_My(R.id.add_ll);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_add:
                //添加费用
                addView("", "");
                break;
            case R.id.tv_baocun:
                //保存
                if (match()) {
                    showProgress(true, "正在上传数据，请稍后...");
//                    params.put("token", localUserInfo.getToken());//token
//                    params.put("id", model.getId());//id
//                    params.put("start_at", start_at);//派工时间
//                    params.put("end_at", end_at);//完工时间
//                    params.put("secure_item", secure_item);//安全事项
//                    params.put("implement_content", implement_content);//实施内容
//                    params.put("client_mobile", client_mobile);//用户电话
//                    params.put("client_name", client_name);//用户姓名
//                    params.put("client_feedback", client_feedback);//用户反馈
//                    params.put("visit_time", visit_time);//回访时间
//                    params.put("visit_name", visit_name);//回访人
//
//                    params.put("manhour", manhour);//工时消耗（json数据：天/金额） 如：{ "day": "10", "money": "1000"}
//                    params.put("mileage", mileage);//汽车消耗（json数据：KM/金额） 如：{ "length": "10", "money": "500"}
//                    params.put("material", material);//材料消耗（json数据：种类/数量/金额） 如：[{ "material_id": "x01", "qty": 8, "money": "800"}, { "material_id": "x02", "qty": 20, "money": "2000"}]
//                    RequestUpdata(params);
                }
                break;
            case R.id.tv_queren:
                //发送客户确认
                if (match()) {
                    showProgress(true, "正在上传数据，请稍后...");
//                    params.put("token", localUserInfo.getToken());//token
//                    params.put("id", model.getId());//id
//                    params.put("start_at", start_at);//派工时间
//                    params.put("end_at", end_at);//完工时间
//                    params.put("secure_item", secure_item);//安全事项
//                    params.put("implement_content", implement_content);//实施内容
//                    params.put("client_mobile", client_mobile);//用户电话
//                    params.put("client_name", client_name);//用户姓名
//                    params.put("client_feedback", client_feedback);//用户反馈
//                    params.put("visit_time", visit_time);//回访时间
//                    params.put("visit_name", visit_name);//回访人
//
//                    params.put("manhour", manhour);//工时消耗（json数据：天/金额） 如：{ "day": "10", "money": "1000"}
//                    params.put("mileage", mileage);//汽车消耗（json数据：KM/金额） 如：{ "length": "10", "money": "500"}
//                    params.put("material", material);//材料消耗（json数据：种类/数量/金额） 如：[{ "material_id": "x01", "qty": 8, "money": "800"}, { "material_id": "x02", "qty": 20, "money": "2000"}]
//                    RequestUpdata(params);
                }
                break;
        }
    }

    @Override
    protected void initData() {

    }

    //添加材料布局
    private void addView(String title, String money) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = LayoutInflater.from(AddSurchargeActivity.this);
        View view = inflater.inflate(R.layout.item_addsurcharge, null, false);
        view.setLayoutParams(lp);
        //实例化子页面的控件
        TextView textView_1 = (TextView) view.findViewById(R.id.textView_1);
        TextView editText_1 = (TextView) view.findViewById(R.id.editText_1);
        EditText editText_2 = (EditText) view.findViewById(R.id.editText_2);
        ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
        if (!title.equals("")) {
//            textView_1.setText(id);
            editText_1.setText(title);
            editText_2.setText(money);
        }
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_1.setText("");
            }
        });
        add_ll.addView(view);
    }
    private boolean match() {
        /*//附加费标准
        day = editText1.getText().toString().trim();
        if (TextUtils.isEmpty(day)) {
            myToast("请输入附加费标准");
            return false;
        }
        //逾时等候费
        money1 = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(money1)) {
            showToast("请输入逾时等候费");
            return false;
        }
        //路桥费
        length = editText3.getText().toString().trim();
        if (TextUtils.isEmpty(length)) {
            showToast("请输入路桥费");
            return false;
        }
        //搬运费
        money2 = editText4.getText().toString().trim();
        if (TextUtils.isEmpty(money2)) {
            showToast("请输入搬运费");
            return false;
        }

        //材料消耗
        JSONArray idCradArray = new JSONArray();
        for (int i = 0; i < add_ll.getChildCount(); i++) {
            View childAt = add_ll.getChildAt(i);
            TextView textView_1 = (TextView) childAt.findViewById(R.id.textView_1);
            TextView editText_1 = (TextView) childAt.findViewById(R.id.editText_1);
            EditText editText_2 = (EditText) childAt.findViewById(R.id.editText_2);

            if (!TextUtils.isEmpty(editText_1.getText().toString())
                    && !TextUtils.isEmpty(editText_2.getText().toString())) {
                try {
                    JSONObject stoneObject = new JSONObject();
//                    stoneObject.put("id", textView_1.getText().toString());
                    stoneObject.put("title", editText_1.getText().toString());
                    stoneObject.put("money", editText_2.getText().toString().trim());
                    idCradArray.put(stoneObject);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            } else {
//                showToast("请完善附加费");
//                return false;
            }
        }

        material = idCradArray.toString();
        MyLogger.i(">>>>>"+material);*/

        return true;
    }
    @Override
    protected void updateView() {
        titleView.setTitle("增加附加费");
    }
}
