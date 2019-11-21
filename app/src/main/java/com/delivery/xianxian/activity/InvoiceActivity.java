package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.InvoiceModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.MyLogger;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zyz on 2019-11-21.
 * 申请发票
 */
public class InvoiceActivity extends BaseActivity {
    int page = 1;
    private RecyclerView recyclerView;
    List<InvoiceModel> list = new ArrayList<>();
    CommonAdapter<InvoiceModel> mAdapter;

    boolean isQuanXuan = false;
    ImageView imageView1;
    TextView textView,textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestServer();
    }

    @Override
    protected void initView() {
        //刷新
        setSpringViewMore(true);//不需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                String string = "";

                page = 1;
                string = "?page=" + page//当前页号
                        + "&count=" + "10"//页面行数
                        + "&token=" + localUserInfo.getToken();
                Request(string);
            }

            @Override
            public void onLoadmore() {
                String string = "";
                page++;
                string = "?page=" + page//当前页号
                        + "&count=" + "10"//页面行数
                        + "&token=" + localUserInfo.getToken();
                RequestMore(string);
            }
        });

        recyclerView = findViewByID_My(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(InvoiceActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        textView = findViewByID_My(R.id.textView);
        textView1 = findViewByID_My(R.id.textView1);
        imageView1 = findViewByID_My(R.id.imageView1);
    }


    @Override
    protected void initData() {

    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();

        showProgress(true, getString(R.string.app_loading));
        String string = "";
        page = 1;
        string = "?page=" + page//当前页号
                + "&count=" + "10"//页面行数
                + "&token=" + localUserInfo.getToken();
        Request(string);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(InvoiceActivity.this, URLs.Invoice + string,
                new OkHttpClientManager.ResultCallback<InvoiceModel>() {
                    @Override
                    public void onError(Request request, String info, Exception e) {
                        showErrorPage();
                        hideProgress();
                        if (!info.equals("")) {
                            myToast(info);
                        }
                    }

                    @Override
                    public void onResponse(InvoiceModel response) {
                        showContentPage();
                        MyLogger.i(">>>>>>>>>申请发票" + response);
//                        list = response.getTindent_list();
                        mAdapter = new CommonAdapter<InvoiceModel>
                                (InvoiceActivity.this, R.layout.item_invoice, list) {
                            @Override
                            protected void convert(ViewHolder holder, InvoiceModel model, int position) {
                                //车型
                                /*TextView textView1 = holder.getView(R.id.textView1);
                                textView1.setText(model.getUse_type());
                                switch (model.getUse_type_id()) {
                                    case 1:
                                        //专车
                                        textView1.setBackgroundResource(R.drawable.yuanjiao_5_juse_topright_dwonleft);
                                        break;
                                    case 2:
                                        //顺风车
                                        textView1.setBackgroundResource(R.drawable.yuanjiao_5_lanse_topright_dwonleft);
                                        break;
                                    case 3:
                                        //快递
                                        textView1.setBackgroundResource(R.drawable.yuanjiao_5_hongse_topright_dwonleft);
                                        break;
                                }
                                holder.setText(R.id.textView2, "时间：" + model.getCreated_at());//时间
                                holder.setText(R.id.textView3, "订单号：" + model.getCar_type());//订单号
                                holder.setText(R.id.textView4, model.getStart_addr());//发货地
                                holder.setText(R.id.textView5, model.getEnd_addr());//收货地
                                holder.setText(R.id.textView6, "费用：¥ " + model.getPrice());//费用*/

                            }
                        };
                        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                                /*Bundle bundle = new Bundle();
                                bundle.putString("id", list1.get(i).getId());
                                CommonUtil.gotoActivityWithData(OrderListActivity.this, OrderDetailsActivity.class, bundle);*/
                            }

                            @Override
                            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                                return false;
                            }
                        });

                        if (list.size() > 0) {
                            showContentPage();
                            recyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            showEmptyPage();//空数据
                        }

                        hideProgress();

                    }

                });
    }

    private void RequestMore(String string) {
        OkHttpClientManager.getAsyn(InvoiceActivity.this, URLs.Invoice + string, new OkHttpClientManager.ResultCallback<InvoiceModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                page--;
                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(InvoiceModel response) {
                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>订单列表更多" + response);
                JSONObject jObj;
                List<InvoiceModel> list1_1 = new ArrayList<>();
//                list1_1 = response;
                if (list1_1.size() == 0) {
                    page--;
                    myToast(getString(R.string.app_nomore));
                } else {
                    list.addAll(list1_1);
                    mAdapter.notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayout1:
                //全选
                isQuanXuan = !isQuanXuan;
                if (isQuanXuan){
                    imageView1.setImageResource(R.mipmap.ic_xuanzhong);
                }else {
                    imageView1.setImageResource(R.mipmap.ic_weixuan);
                }
                break;
            case R.id.textView:
                //确认开票


                break;

        }
    }



    @Override
    protected void updateView() {
        titleView.setTitle("申请发票");
    }
}
