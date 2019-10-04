package com.transport.xianxian.activity;

import android.os.Bundle;

import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;
import com.transport.xianxian.model.ScoreDetailModel;
import com.transport.xianxian.net.OkHttpClientManager;
import com.transport.xianxian.net.URLs;
import com.transport.xianxian.utils.MyLogger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zyz on 2019-10-03.
 * 评分详情
 */
public class ScoreDetailActivity extends BaseActivity {
    private RecyclerView recyclerView;
    List<ScoreDetailModel> list = new ArrayList<>();
    CommonAdapter<ScoreDetailModel> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoredetail);
    }

    @Override
    protected void initView() {
        setSpringViewMore(false);//不需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                Request("?token=" + localUserInfo.getToken());
            }

            @Override
            public void onLoadmore() {
            }
        });

        recyclerView = findViewByID_My(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ScoreDetailActivity.this);
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
        String string = "?token=" + localUserInfo.getToken();
        Request(string);
    }
    private void Request(String string) {
        OkHttpClientManager.getAsyn(ScoreDetailActivity.this, URLs.ScoreDetail + string, new OkHttpClientManager.ResultCallback<ScoreDetailModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(ScoreDetailModel response) {
//                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>公告详情" + response);
//                list = response.;
                mAdapter = new CommonAdapter<ScoreDetailModel>
                        (ScoreDetailActivity.this, R.layout.item_scoredetail, list) {
                    @Override
                    protected void convert(ViewHolder holder, ScoreDetailModel model, int position) {
                        /*holder.setText(R.id.textView1, model.getMember_nickname());
                        holder.setText(R.id.textView2, model.getMoney() + getString(R.string.app_ge));
                        holder.setText(R.id.textView3, model.getShow_created_at());
                        ImageView imageView1 = holder.getView(R.id.imageView1);
                        if (!model.getMember_head().equals(""))
                            Glide.with(getActivity())
                                    .load(IMGHOST + model.getMember_head())
                                    .centerCrop()
//                                    .placeholder(R.mipmap.headimg)//加载站位图
//                                    .error(R.mipmap.headimg)//加载失败
                                    .into(imageView1);//加载图片
                        else
                            imageView1.setImageResource(R.mipmap.headimg);*/

                    }
                };
            }
        });
    }
    @Override
    protected void updateView() {
        titleView.setTitle("评分详情");
    }
}
