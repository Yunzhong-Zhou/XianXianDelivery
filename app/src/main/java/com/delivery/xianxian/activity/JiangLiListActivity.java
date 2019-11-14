package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.JiangLiListModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.CommonUtil;
import com.delivery.xianxian.utils.MyLogger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zyz on 2019-10-09.
 * 奖励记录
 */
public class JiangLiListActivity extends BaseActivity {
    int page = 1;
    private RecyclerView recyclerView;
    List<JiangLiListModel.TscoreDataBean> list = new ArrayList<>();
    CommonAdapter<JiangLiListModel.TscoreDataBean> mAdapter;
    TextView textView1, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jianglilist);
    }

    @Override
    protected void initView() {
        setSpringViewMore(true);//不需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                String string = "?page=" + page//当前页号
                        + "&count=" + "10"//页面行数
                        + "&token=" + localUserInfo.getToken();
                Request(string);
            }

            @Override
            public void onLoadmore() {
                page++;
                String string = "?page=" + page//当前页号
                        + "&count=" + "10"//页面行数
                        + "&token=" + localUserInfo.getToken();
                RequestMore(string);
            }
        });
        recyclerView = findViewByID_My(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_shangcheng:
                CommonUtil.gotoActivity(this, JiFenShangChengActivity.class, false);
                break;
        }
    }

    @Override
    protected void initData() {
        requestServer();
    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();

        showProgress(true, getString(R.string.app_loading));
        String string = "?page=" + page//当前页号
                + "&count=" + "10"//页面行数
                + "&token=" + localUserInfo.getToken();
        Request(string);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(this, URLs.JiangLiList + string, new OkHttpClientManager.ResultCallback<JiangLiListModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(final JiangLiListModel response) {
                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>奖励记录" + response);
                textView1.setText(response.getActivity_score());
                textView2.setText(response.getTotal_score());
                if (list.size() > 0) {
                    mAdapter = new CommonAdapter<JiangLiListModel.TscoreDataBean>
                            (JiangLiListActivity.this, R.layout.item_jifenmingxi, list) {
                        @Override
                        protected void convert(ViewHolder holder, JiangLiListModel.TscoreDataBean model, int position) {
                            holder.setText(R.id.tv1, model.getTitle());
                            holder.setText(R.id.tv2, model.getCreated_at());
                            TextView tv3 = holder.getView(R.id.tv3);
                            tv3.setTextColor(getResources().getColor(R.color.green));
                            tv3.setText("+" + model.getScore());

                        }
                    };
                    recyclerView.setAdapter(mAdapter);
                } else {
                    showEmptyPage();//空数据
                }
            }
        });
    }

    private void RequestMore(String string) {
        OkHttpClientManager.getAsyn(this, URLs.JiangLiList + string, new OkHttpClientManager.ResultCallback<JiangLiListModel>() {
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
            public void onResponse(JiangLiListModel response) {
                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>奖励记录更多" + response);
                List<JiangLiListModel.TscoreDataBean> list1 = new ArrayList<>();
                list1 = response.getTscore_data();
                if (list1.size() == 0) {
                    page--;
                    myToast(getString(R.string.app_nomore));
                } else {
                    list.addAll(list1);
                    mAdapter.notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    protected void updateView() {
        titleView.setTitle("奖励记录");
    }
}
