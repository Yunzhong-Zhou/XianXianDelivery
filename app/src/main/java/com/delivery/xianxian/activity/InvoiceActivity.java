package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.InvoiceModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.CommonUtil;
import com.delivery.xianxian.utils.MyLogger;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
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
                new OkHttpClientManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, String info, Exception e) {
                        showErrorPage();
                        hideProgress();
                        if (!info.equals("")) {
                            myToast(info);
                        }
                    }

                    @Override
                    public void onResponse(String response) {
                        showContentPage();
                        MyLogger.i(">>>>>>>>>申请发票" + response);
                        JSONObject jObj;
                        try {
                            jObj = new JSONObject(response);
                            JSONArray jsonArray = jObj.getJSONArray("data");
                            list = JSON.parseArray(jsonArray.toString(), InvoiceModel.class);
                            for (int i = 0; i < list.size(); i++) {
                                list.get(i).setIsgouxuan(0);
                            }
                            isQuanXuan = false;
                            imageView1.setImageResource(R.mipmap.ic_weixuan);
                            if (list.size() > 0) {
                                mAdapter = new CommonAdapter<InvoiceModel>
                                        (InvoiceActivity.this, R.layout.item_invoice, list) {
                                    @Override
                                    protected void convert(ViewHolder holder, InvoiceModel model, int position) {
                                        //车型
                                        TextView textView1 = holder.getView(R.id.textView1);
                                        textView1.setText(model.getUse_type_text());
                                        switch (model.getUse_type()) {
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
                                        holder.setText(R.id.textView3, "订单号：" + model.getSn());//订单号
                                        holder.setText(R.id.textView4, model.getStart());//发货地
                                        holder.setText(R.id.textView5, model.getEnd());//收货地
                                        holder.setText(R.id.textView6, "费用：¥ " + model.getPrice());//费用

                                        ImageView imageView1 = holder.getView(R.id.imageView1);
                                        if (model.getIsgouxuan() ==1){
                                            imageView1.setImageResource(R.mipmap.ic_xuanzhong);
                                        }else {
                                            imageView1.setImageResource(R.mipmap.ic_weixuan);
                                        }
                                    }
                                };
                                mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                                        if (list.get(i).getIsgouxuan() == 0) {
                                            list.get(i).setIsgouxuan(1);
                                        } else {
                                            list.get(i).setIsgouxuan(0);
                                        }
                                        mAdapter.notifyDataSetChanged();

                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", list.get(i).getT_indent_id());
                                        CommonUtil.gotoActivityWithData(InvoiceActivity.this, AddInvoiceActivity.class, bundle);
                                    }

                                    @Override
                                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                                        return false;
                                    }
                                });
                                recyclerView.setAdapter(mAdapter);
                            } else {
                                showEmptyPage();//空数据
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
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
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setIsgouxuan(1);
                    }
                }else {
                    imageView1.setImageResource(R.mipmap.ic_weixuan);
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setIsgouxuan(0);
                    }
                }
                mAdapter.notifyDataSetChanged();
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
