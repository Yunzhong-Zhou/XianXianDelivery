package com.transport.xianxian.activity;

import android.os.Bundle;

import com.squareup.okhttp.Request;
import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;
import com.transport.xianxian.model.DuiHuanListModel;
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
 * Created by zyz on 2019-10-08.
 *兑换记录
 */
public class DuiHuanListActivity extends BaseActivity {
    private RecyclerView recyclerView;
    List<DuiHuanListModel> list = new ArrayList<>();
    CommonAdapter<DuiHuanListModel> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jifenmingxi);
    }

    @Override
    protected void initView() {
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

//        showProgress(true, getString(R.string.app_loading));

        /*String string = "?token=" + localUserInfo.getToken();
        Request(string);*/
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(this, URLs.JiFenMingXi + string, new OkHttpClientManager.ResultCallback<DuiHuanListModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(final DuiHuanListModel response) {
                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>订单" + response);

                mAdapter = new CommonAdapter<DuiHuanListModel>
                        (DuiHuanListActivity.this, R.layout.item_duihuanlist, list) {
                    @Override
                    protected void convert(ViewHolder holder, DuiHuanListModel model, int position) {
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
        titleView.setTitle("兑换记录");
    }
}
