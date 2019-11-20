package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.AddFeeModel;
import com.delivery.xianxian.utils.CommonUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zyz on 2019-11-16.
 * 费用详情
 */
public class FeeDetailActivity extends BaseActivity {
    AddFeeModel model;
    String city = "", car_type_id = "", use_type = "";

    private RecyclerView recyclerView;
    List<AddFeeModel.PriceListBean> list = new ArrayList<>();
    CommonAdapter<AddFeeModel.PriceListBean> mAdapter;
    TextView textView1, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedetail);
    }

    @Override
    protected void initView() {
        recyclerView = findViewByID_My(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(FeeDetailActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
    }

    @Override
    protected void initData() {
        model = (AddFeeModel) getIntent().getSerializableExtra("AddFeeModel");
        city = getIntent().getStringExtra("city");
        car_type_id = getIntent().getStringExtra("car_type_id");
        use_type = getIntent().getStringExtra("use_type");

        textView1.setText(model.getPrice());
        textView2.setText("（总里程" + model.getMillage() + "公里）");

        list = model.getPrice_list();
        mAdapter = new CommonAdapter<AddFeeModel.PriceListBean>
                (FeeDetailActivity.this, R.layout.item_feedetail, list) {
            @Override
            protected void convert(ViewHolder holder, AddFeeModel.PriceListBean model, int position) {
                holder.setText(R.id.textView1, model.getTitle());//标题
                holder.setText(R.id.textView2, model.getPrice());//价格
            }
        };
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void updateView() {
        titleView.setTitle("费用详情");
        titleView.showRightTextview("收费标准", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle4 = new Bundle();
                bundle4.putString("city", city);
                bundle4.putString("car_type_id", car_type_id);
                bundle4.putString("use_type", use_type);
                CommonUtil.gotoActivityWithData(FeeDetailActivity.this, FeeModelActivity.class, bundle4, false);

            }
        });
    }
}
