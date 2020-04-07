package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.OrderListModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.CommonUtil;
import com.delivery.xianxian.utils.MyLogger;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by fafukeji01 on 2016/1/6.
 * 订单列表
 */
public class OrderListActivity extends BaseActivity {
    int page1 = 1, page2 = 1, page3 = 1, status = 1;
    private RecyclerView recyclerView;
    List<OrderListModel.TindentListBean> list = new ArrayList<>();
    CommonAdapter<OrderListModel.TindentListBean> mAdapter;

    private LinearLayout linearLayout1, linearLayout2, linearLayout3;
    private TextView textView1, textView2, textView3;
    private View view1, view2, view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        /*if (MainActivity.item == 1) {
            requestServer();
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        status = getIntent().getIntExtra("status", status);
        changeUI();
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
                switch (status) {
                    case 1:
                        page1 = 1;
                        string = "?page=" + page1//当前页号
                                + "&count=" + "10"//页面行数
                                + "&status=" + status//1进行中2已完成3已取消
                                + "&token=" + localUserInfo.getToken();
                        break;
                    case 2:
                        page2 = 1;
                        string = "?page=" + page2//当前页号
                                + "&count=" + "10"//页面行数
                                + "&status=" + status//1进行中2已完成3已取消
                                + "&token=" + localUserInfo.getToken();
                        break;
                    case 3:
                        page3 = 1;
                        string = "?page=" + page3//当前页号
                                + "&count=" + "10"//页面行数
                                + "&status=" + status//1进行中2已完成3已取消
                                + "&token=" + localUserInfo.getToken();
                        break;
                }
                Request(string);
            }

            @Override
            public void onLoadmore() {
                String string = "";
                switch (status) {
                    case 1:
                        page1++;
                        string = "?page=" + page1//当前页号
                                + "&count=" + "10"//页面行数
                                + "&status=" + status//1进行中2已完成3已取消
                                + "&token=" + localUserInfo.getToken();
                        break;
                    case 2:
                        page2++;
                        string = "?page=" + page2//当前页号
                                + "&count=" + "10"//页面行数
                                + "&status=" + status//1进行中2已完成3已取消
                                + "&token=" + localUserInfo.getToken();
                        break;
                    case 3:
                        page3++;
                        string = "?page=" + page3//当前页号
                                + "&count=" + "10"//页面行数
                                + "&status=" + status//1进行中2已完成3已取消
                                + "&token=" + localUserInfo.getToken();
                        break;
                }
                RequestMore(string);
            }
        });

        recyclerView = findViewByID_My(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(OrderListActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        linearLayout1 = findViewByID_My(R.id.linearLayout1);
        linearLayout2 = findViewByID_My(R.id.linearLayout2);
        linearLayout3 = findViewByID_My(R.id.linearLayout3);

        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);

        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);

        view1 = findViewByID_My(R.id.view1);
        view2 = findViewByID_My(R.id.view2);
        view3 = findViewByID_My(R.id.view3);

    }

    @Override
    protected void initData() {
//        requestServer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayout1:
                status = 1;
                changeUI();
                requestServer();
                break;
            case R.id.linearLayout2:
                status = 2;
                changeUI();
                requestServer();
                break;
            case R.id.linearLayout3:
                status = 3;
                changeUI();
                requestServer();
                break;
        }
    }

    private void changeUI() {
        switch (status) {
            case 1:
                textView1.setTextColor(getResources().getColor(R.color.blue));
                textView2.setTextColor(getResources().getColor(R.color.black2));
                textView3.setTextColor(getResources().getColor(R.color.black2));
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.INVISIBLE);

                break;
            case 2:
                textView1.setTextColor(getResources().getColor(R.color.black2));
                textView2.setTextColor(getResources().getColor(R.color.blue));
                textView3.setTextColor(getResources().getColor(R.color.black2));

                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                textView1.setTextColor(getResources().getColor(R.color.black2));
                textView2.setTextColor(getResources().getColor(R.color.black2));
                textView3.setTextColor(getResources().getColor(R.color.blue));
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
//        requestServer();
    }

    @Override
    protected void updateView() {
        titleView.setTitle("货运订单");
        titleView.showRightTextview("申请发票", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.gotoActivity(OrderListActivity.this, InvoiceActivity.class, false);
            }
        });
    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();

        showProgress(true, getString(R.string.app_loading));
        String string = "";
        switch (status) {
            case 1:
                page1 = 1;
                string = "?page=" + page1//当前页号
                        + "&count=" + "10"//页面行数
                        + "&status=" + status//1进行中2已完成3已取消
                        + "&token=" + localUserInfo.getToken();
                break;
            case 2:
                page2 = 1;
                string = "?page=" + page2//当前页号
                        + "&count=" + "10"//页面行数
                        + "&status=" + status//1进行中2已完成3已取消
                        + "&token=" + localUserInfo.getToken();
                break;
            case 3:
                page3 = 1;
                string = "?page=" + page3//当前页号
                        + "&count=" + "10"//页面行数
                        + "&status=" + status//1进行中2已完成3已取消
                        + "&token=" + localUserInfo.getToken();
                break;
        }
        Request(string);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(OrderListActivity.this, URLs.OrderList + string,
                new OkHttpClientManager.ResultCallback<OrderListModel>() {
                    @Override
                    public void onError(Request request, String info, Exception e) {
                        showErrorPage();
                        hideProgress();
                        if (!info.equals("")) {
                            myToast(info);
                        }
                    }

                    @Override
                    public void onResponse(OrderListModel response) {
                        showContentPage();
                        MyLogger.i(">>>>>>>>>订单" + response);
                        list = response.getTindent_list();
                        mAdapter = new CommonAdapter<OrderListModel.TindentListBean>
                                (OrderListActivity.this, R.layout.item_orderlist, list) {
                            @Override
                            protected void convert(ViewHolder holder, OrderListModel.TindentListBean model, int position) {
                                //订单号
                                holder.setText(R.id.tv_ordernum, "订单号：" + model.getSn());
                                //车型
                                TextView textView1 = holder.getView(R.id.textView1);
                                textView1.setText(model.getUse_type());
                                switch (model.getUse_type_id()) {
                                    case 1:
                                        //专车
                                        textView1.setBackgroundResource(R.drawable.yuanjiao_50_juse_right);
                                        break;
                                    case 2:
                                        //顺风车
                                        textView1.setBackgroundResource(R.drawable.yuanjiao_50_lanse_right);
                                        break;
                                    case 3:
                                        //快递
                                        textView1.setBackgroundResource(R.drawable.yuanjiao_50_hongse_right);
                                        break;
                                }
                                holder.setText(R.id.textView2, "时间：" + model.getCreated_at());//时间
                                //状态
                                TextView textView3 = holder.getView(R.id.textView3);
                                if (status == 2){
                                    //已完成-判断附加费是否收取
                                    if (model.getIs_attach_fee() == 1){
                                        textView3.setText(model.getStatus_text()+"-附加费已支付");
                                    }else if (model.getIs_attach_fee() == 2){
                                        textView3.setText(model.getStatus_text()+"-附加费未支付");
                                    }else if (model.getIs_attach_fee() == 3){
                                        textView3.setText(model.getStatus_text()+"-附加费未添加");
                                    }

                                }else {
                                    textView3.setText(model.getStatus_text());
                                }

                                switch (model.getStatus()) {
                                    case 0:
                                        //匹配中
                                        textView3.setTextColor(getResources().getColor(R.color.blue));
                                        break;
                                    case 1:
                                        //待发货
                                        textView3.setTextColor(getResources().getColor(R.color.red));
                                        break;
                                    default:
                                        //其他
                                        textView3.setTextColor(getResources().getColor(R.color.orange));
                                        break;
                                }

                                holder.setText(R.id.textView4, "车型：" + model.getCar_type());//车型
                                //温层
                                if (model.getTemperature().equals("")) {
                                    holder.setText(R.id.textView5, "");
                                } else {
                                    holder.setText(R.id.textView5, "温层：" + model.getTemperature());
                                }
                                //地址列表
                                LinearLayout ll_add = holder.getView(R.id.ll_add);
                                ll_add.removeAllViews();
                                for (int i = 0; i < model.getAddr_list().size(); i++) {
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    LayoutInflater inflater = LayoutInflater.from(OrderListActivity.this);
                                    View view = inflater.inflate(R.layout.item_add_fragment2_1, null, false);
                                    view.setLayoutParams(lp);
                                    TextView tv1 = (TextView) view.findViewById(R.id.tv1);
                                    TextView tv2 = (TextView) view.findViewById(R.id.tv2);

                                    if (i == 0) {
                                        tv1.setText("发");
                                        tv1.setBackgroundResource(R.drawable.yuanxing_lanse);

                                    } else if (i == (model.getAddr_list().size() - 1)) {
                                        tv1.setText("收");
                                        tv1.setBackgroundResource(R.drawable.yuanxing_juse);

                                    } else {
                                        tv1.setText("途");
                                        tv1.setBackgroundResource(R.drawable.yuanxing_huise);
                                    }
                                    tv2.setText(model.getAddr_list().get(i).getAddr());//地址

                                    ll_add.addView(view);
                                }

                                if (model.getIs_plan() == 1) {//是预约订单
                                    holder.setText(R.id.textView8, "预约：" + model.getPlan_time());//预约
                                }
                                holder.setText(R.id.textView9, "货运价格：" + model.getPrice());//货运价格

                            }
                        };
                        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                                if (status == 3) {
                                    //跳转取消订单详情
                                    Bundle bundle = new Bundle();
                                    bundle.putString("id", list.get(i).getId());
                                    CommonUtil.gotoActivityWithData(OrderListActivity.this, OrderCancelDetailActivity.class, bundle);
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("id", list.get(i).getId());
                                    CommonUtil.gotoActivityWithData(OrderListActivity.this, OrderDetailsActivity.class, bundle);
                                }
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
        OkHttpClientManager.getAsyn(OrderListActivity.this, URLs.OrderList + string, new OkHttpClientManager.ResultCallback<OrderListModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                switch (status){
                    case 1:
                        page1--;
                        break;
                    case 2:
                        page2--;
                        break;
                    case 3:
                        page3--;
                        break;
                }
                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(OrderListModel response) {
                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>订单列表更多" + response);

                List<OrderListModel.TindentListBean> list1_1 = new ArrayList<>();
                list1_1 = response.getTindent_list();
                if (list1_1.size() == 0) {
                    switch (status){
                        case 1:
                            page1--;
                            break;
                        case 2:
                            page2--;
                            break;
                        case 3:
                            page3--;
                            break;
                    }
                    myToast(getString(R.string.app_nomore));
                } else {
                    list.addAll(list1_1);
                    mAdapter.notifyDataSetChanged();
                }

            }
        });

    }

}
