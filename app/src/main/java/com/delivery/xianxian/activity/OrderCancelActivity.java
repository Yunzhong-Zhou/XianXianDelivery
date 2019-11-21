package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.OrderCancelModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.MyLogger;
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

/**
 * Created by zyz on 2019-11-21.
 * 取消订单
 */
public class OrderCancelActivity extends BaseActivity {
    String id;
    OrderCancelModel model;
    private RecyclerView recyclerView;
    List<OrderCancelModel.OwnerCancelReasonListBean> list = new ArrayList<>();
    CommonAdapter<OrderCancelModel.OwnerCancelReasonListBean> mAdapter;

    TextView tv_confirm;

    int item = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordercancel);
    }

    @Override
    protected void initView() {
        recyclerView = findViewByID_My(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(OrderCancelActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        tv_confirm = findViewByID_My(R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item >= 0) {
                    Map<String, String> params = new HashMap<>();
                    params.put("token", localUserInfo.getToken());
                    params.put("id", id);
                    params.put("type", "owner_cancel");
                    params.put("cancel_reason_id", list.get(item).getKey());
                    RequestQuXiao(params);
                } else {
                    myToast("请选择取消订单原因");
                }
            }
        });
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        model = (OrderCancelModel) getIntent().getSerializableExtra("model");

        list = model.getOwner_cancel_reason_list();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setIsgouxuan(0);
        }
        mAdapter = new CommonAdapter<OrderCancelModel.OwnerCancelReasonListBean>
                (OrderCancelActivity.this, R.layout.item_ordercancel, list) {
            @Override
            protected void convert(ViewHolder holder, OrderCancelModel.OwnerCancelReasonListBean model, int position) {
                holder.setText(R.id.textView1, model.getVal());//标题
                ImageView imageView1 = holder.getView(R.id.imageView1);
                if (position == item) {
                    imageView1.setImageResource(R.mipmap.ic_gouxuan);
                } else {
                    imageView1.setImageResource(R.mipmap.ic_weigouxuan);
                }
            }
        };
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                item = i;
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
        recyclerView.setAdapter(mAdapter);
    }
    private void RequestQuXiao(Map<String, String> params) {
        OkHttpClientManager.postAsyn(OrderCancelActivity.this, URLs.OrderDetails_QuXiao, params, new OkHttpClientManager.ResultCallback<OrderCancelModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(OrderCancelModel response) {
//                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>取消" + response);
                myToast("取消订单成功");
                finish();
            }
        }, false);
    }
    @Override
    protected void updateView() {
        titleView.setTitle("取消订单");
    }
}
