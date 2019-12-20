package com.delivery.xianxian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.AddOtherModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.CommonUtil;
import com.delivery.xianxian.utils.MyLogger;
import com.squareup.okhttp.Request;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.delivery.xianxian.net.OkHttpClientManager.HOST;

/**
 * Created by zyz on 2019-11-19.
 * 添加额外需求
 */
public class AddOtherActivity extends BaseActivity {
    private RecyclerView recyclerView;
    List<AddOtherModel.OtherBean> list = new ArrayList<>();
    CommonAdapter<AddOtherModel.OtherBean> mAdapter;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addother);
    }

    @Override
    protected void initView() {
        textView = findViewByID_My(R.id.textView);
        recyclerView = findViewByID_My(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager1 = new LinearLayoutManager(AddOtherActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager1);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String other = "";
                String other_s = "";
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getIsgouxuan() == 1) {
                        other = other + list.get(i).getId() + ",";
                        other_s = other_s + list.get(i).getTitle() + ",";
                    }
                }
                if (other.length() > 0) {
                    other = other.substring(0, other.length() - 1);
                    other_s = other_s.substring(0, other_s.length() - 1);
                    Intent resultIntent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("other", other);
                    bundle.putString("other_s", other_s);
                    resultIntent.putExtras(bundle);
                    AddOtherActivity.this.setResult(RESULT_OK, resultIntent);
                }
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        //获取额外需求
        showProgress(true, getString(R.string.app_loading));
        Request("?token=" + localUserInfo.getToken());
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(AddOtherActivity.this, URLs.AddOther + string, new OkHttpClientManager.ResultCallback<AddOtherModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(AddOtherModel response) {
                MyLogger.i(">>>>>>>>>额外需求" + response);
                hideProgress();

                //基本路费
                list = response.getOther();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setIsgouxuan(0);
                }
                mAdapter = new CommonAdapter<AddOtherModel.OtherBean>
                        (AddOtherActivity.this, R.layout.item_addother, response.getOther()) {
                    @Override
                    protected void convert(ViewHolder holder, AddOtherModel.OtherBean model, int position) {
                        holder.setText(R.id.textView1, model.getTitle() + "（" + model.getPrice() + "）");//标题
                        ImageView imageView1 = holder.getView(R.id.imageView1);
                        if (model.getIsgouxuan() == 1) {
                            imageView1.setImageResource(R.mipmap.ic_gouxuan);
                        } else {
                            imageView1.setImageResource(R.mipmap.ic_weigouxuan);
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
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        return false;
                    }
                });
                recyclerView.setAdapter(mAdapter);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.textView1:
                Bundle bundle = new Bundle();
                bundle.putString("url", HOST + "/api/article/detail-html?id=");
                CommonUtil.gotoActivityWithData(AddOtherActivity.this, WebContentActivity.class, bundle, false);

                break;
            case R.id.textView2:
                Bundle bundle1 = new Bundle();
                bundle1.putString("url", HOST + "/api/article/detail-html?id=");
                CommonUtil.gotoActivityWithData(AddOtherActivity.this, WebContentActivity.class, bundle1, false);

                break;
        }
    }

    @Override
    protected void updateView() {
        titleView.setTitle("添加额外需求");
    }
}
