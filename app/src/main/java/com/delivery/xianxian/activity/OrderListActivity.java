package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.view.View;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by fafukeji01 on 2016/1/6.
 * 订单
 */
public class OrderListActivity extends BaseActivity {
    int page1 = 1, status = 1;
    private RecyclerView recyclerView;
    List<OrderListModel.TindentListBean> list1 = new ArrayList<>();
    CommonAdapter<OrderListModel.TindentListBean> mAdapter1;

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

                page1 = 1;
                string = "?page=" + page1//当前页号
                        + "&count=" + "10"//页面行数
                        + "&status=" + status//1进行中2已完成3已取消
                        + "&token=" + localUserInfo.getToken();
                Request(string);
            }

            @Override
            public void onLoadmore() {
                String string = "";
                page1++;
                string = "?page=" + page1//当前页号
                        + "&count=" + "10"//页面行数
                        + "&status=" + status//1进行中2已完成3已取消
                        + "&token=" + localUserInfo.getToken();
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
        page1 = 1;
        string = "?page=" + page1//当前页号
                + "&count=" + "10"//页面行数
                + "&status=" + status//1进行中2已完成3已取消
                + "&token=" + localUserInfo.getToken();
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
                        list1 = response.getTindent_list();
                        mAdapter1 = new CommonAdapter<OrderListModel.TindentListBean>
                                (OrderListActivity.this, R.layout.item_orderlist_1, list1) {
                            @Override
                            protected void convert(ViewHolder holder, OrderListModel.TindentListBean model, int position) {
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
                                textView3.setText(model.getStatus_text());
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
                                    holder.setText(R.id.textView5, "（" + model.getRemark() + "）");
                                } else {
                                    holder.setText(R.id.textView5, "温层：" + model.getTemperature() + "（" + model.getRemark() + "）");
                                }
                                holder.setText(R.id.textView6, model.getStart_addr());//发货地
                                holder.setText(R.id.textView7, model.getEnd_addr());//收货地
                                if (!model.getPlan_time().equals("")) {
                                    holder.setText(R.id.textView8, "预约：" + model.getPlan_time());//预约
                                }
                                holder.setText(R.id.textView9, "货运价格：" + model.getPrice());//货运价格

                            }
                        };
                        mAdapter1.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                                Bundle bundle = new Bundle();
                                bundle.putString("id", list1.get(i).getId());
                                CommonUtil.gotoActivityWithData(OrderListActivity.this, OrderDetailsActivity.class, bundle);
                            }

                            @Override
                            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                                return false;
                            }
                        });

                        if (list1.size() > 0) {
                            showContentPage();
                            recyclerView.setAdapter(mAdapter1);
                            mAdapter1.notifyDataSetChanged();
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
                page1--;
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
                JSONObject jObj;
                List<OrderListModel.TindentListBean> list1_1 = new ArrayList<>();
                list1_1 = response.getTindent_list();
                if (list1_1.size() == 0) {
                    page1--;
                    myToast(getString(R.string.app_nomore));
                } else {
                    list1.addAll(list1_1);
                    mAdapter1.notifyDataSetChanged();
                }

            }
        });

    }

}
