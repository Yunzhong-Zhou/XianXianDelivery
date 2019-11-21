package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cy.cyflowlayoutlibrary.FlowLayout;
import com.cy.cyflowlayoutlibrary.FlowLayoutAdapter;
import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.OrderDetailsModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.MyLogger;
import com.squareup.okhttp.Request;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zyz on 2019-11-21.
 * 取消订单详情
 */
public class OrderCancelDetailActivity extends BaseActivity {
    String id = "";
    OrderDetailsModel model;
    private RecyclerView recyclerView;
    List<OrderDetailsModel.TindentBean.AddrListBean> list = new ArrayList<>();
    CommonAdapter<OrderDetailsModel.TindentBean.AddrListBean> mAdapter;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordercanceldetail);
    }

    @Override
    protected void initView() {
        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);
        textView4 = findViewByID_My(R.id.textView4);
        textView5 = findViewByID_My(R.id.textView5);
        textView6 = findViewByID_My(R.id.textView6);
        textView7 = findViewByID_My(R.id.textView7);
        textView8 = findViewByID_My(R.id.textView8);

        recyclerView = findViewByID_My(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(OrderCancelDetailActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        requestServer();
    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();
        showProgress(true, getString(R.string.app_loading));
        String string = "?token=" + localUserInfo.getToken()
                + "&id=" + id;
        Request(string);
    }
    private void Request(String string) {
        OkHttpClientManager.getAsyn(OrderCancelDetailActivity.this, URLs.OrderDetails + string, new OkHttpClientManager.ResultCallback<OrderDetailsModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    showToast(info, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onResponse(OrderDetailsModel response) {
//                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>订单详情" + response);
                model = response;

                textView1.setText("发布时间：" + response.getTindent().getPlan_time());//发布时间
                textView2.setText("订单号：" + response.getTindent().getSn());//订单号：
                textView3.setText(response.getTindent().getCar_type());// 订单车型
//                textView4.setText(response.getTindent().get);//联系电话
                textView5.setText(response.getTindent().getTemperature());//温层
                textView6.setText(response.getTindent().getRemark());//备注
                textView7.setText("¥ " + response.getTindent().getTotal_price());//金额


                list = response.getTindent().getAddr_list();
                mAdapter = new CommonAdapter<OrderDetailsModel.TindentBean.AddrListBean>
                        (OrderCancelDetailActivity.this, R.layout.item_orderdetail, list) {
                    @Override
                    protected void convert(ViewHolder holder, OrderDetailsModel.TindentBean.AddrListBean model, int position) {
                        //车型
                        TextView textView1 = holder.getView(R.id.tv1);
                        if (position == 0) {
                            textView1.setText("发");
                            textView1.setBackgroundResource(R.drawable.yuanxing_lanse);
                        } else if (position == (response.getTindent().getAddr_list().size() - 1)) {
                            textView1.setText("收");
                            textView1.setBackgroundResource(R.drawable.yuanxing_juse);
                        } else {
                            //途经点
                            textView1.setText("途");
                            textView1.setBackgroundResource(R.drawable.yuanxing_huise);
                        }
                        holder.setText(R.id.tv2, model.getAddr());//地址
                        holder.setText(R.id.tv3, "发货人：" + model.getName());//发货人
                        holder.setText(R.id.tv4, "手机号：" + model.getMobile());//手机号
                    }
                };
                recyclerView.setAdapter(mAdapter);

                //标签
                FlowLayoutAdapter<String> flowLayoutAdapter;
               /* List<String> stringList = new ArrayList<>();
                for (int i = 0; i < response.getTindent().getTag().size(); i++) {
                    stringList.add(response.getTindent().getTag().get(i).getVal());
                }*/
                flowLayoutAdapter = new FlowLayoutAdapter<String>(response.getTindent().getOther_tag()) {
                    @Override
                    public void bindDataToView(FlowLayoutAdapter.ViewHolder holder, int position, String bean) {
//                                holder.setText(R.id.tv,bean);
                        TextView tv = holder.getView(R.id.tv);
                        tv.setText(bean);
                        tv.setTextColor(getResources().getColor(R.color.black1));
                        tv.setBackgroundResource(R.drawable.yuanjiao_3_huise);
                    }

                    @Override
                    public void onItemClick(int position, String bean) {
//                        showToast("点击" + position);
                    }

                    @Override
                    public int getItemLayoutID(int position, String bean) {
                        return R.layout.item_flowlayout;
                    }
                };
                ((FlowLayout) findViewByID_My(R.id.flowLayout)).setAdapter(flowLayoutAdapter);

            }
        });
    }

    @Override
    protected void updateView() {
        titleView.setTitle("取消订单详情");
    }
}
