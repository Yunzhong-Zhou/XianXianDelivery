package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.MoneyListModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.MyLogger;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.delivery.xianxian.net.OkHttpClientManager.IMGHOST;

/**
 * Created by zyz on 2019-10-08.
 * 余额明细
 */
public class MoneyListActivity extends BaseActivity {
    int page = 1;
    private RecyclerView recyclerView;
    List<MoneyListModel> list = new ArrayList<>();
    CommonAdapter<MoneyListModel> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jifenmingxi);
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
        OkHttpClientManager.getAsyn(this, URLs.MoneyList + string, new OkHttpClientManager.ResultCallback<String>() {
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
                hideProgress();
                MyLogger.i(">>>>>>>>>余额明细" + response);
                JSONObject jObj;
                try {
                    jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("data");
                    list = JSON.parseArray(jsonArray.toString(), MoneyListModel.class);
                    if (list.size() > 0) {
                        mAdapter = new CommonAdapter<MoneyListModel>
                                (MoneyListActivity.this, R.layout.item_moneylist, list) {
                            @Override
                            protected void convert(ViewHolder holder, MoneyListModel model, int position) {
                                holder.setText(R.id.tv1, model.getTitle());
                                holder.setText(R.id.tv2, model.getCreated_at());
                                TextView tv3 = holder.getView(R.id.tv3);
                                if (model.getOut_in() == 1){
                                    tv3.setText("+¥" + model.getMoney());
                                    tv3.setTextColor(getResources().getColor(R.color.black1));
                                }else {
                                    tv3.setText("-¥" + model.getMoney());
                                    tv3.setTextColor(getResources().getColor(R.color.red));
                                }

                                ImageView imageView1 = holder.getView(R.id.imageView1);
                                if (model.getIcon() != null && !model.getIcon().equals(""))
                                    Glide.with(MoneyListActivity.this)
                                            .load(IMGHOST + model.getIcon())
                                            .centerCrop()
//                                    .placeholder(R.mipmap.headimg)//加载站位图
//                                    .error(R.mipmap.headimg)//加载失败
                                            .into(imageView1);//加载图片
                            }
                        };
                        recyclerView.setAdapter(mAdapter);
                    } else {
                        showEmptyPage();//空数据
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
    }

    private void RequestMore(String string) {
        OkHttpClientManager.getAsyn(this, URLs.MoneyList + string, new OkHttpClientManager.ResultCallback<String>() {
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
            public void onResponse(String response) {
                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>余额明细更多" + response);
                JSONObject jObj;
                List<MoneyListModel> list1 = new ArrayList<>();
                try {
                    jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("data");
                    list1 = JSON.parseArray(jsonArray.toString(), MoneyListModel.class);
                    if (list1.size() == 0) {
                        page--;
                        myToast(getString(R.string.app_nomore));
                    } else {
                        list.addAll(list1);
                        mAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void updateView() {
        titleView.setTitle("余额明细");
    }
}
