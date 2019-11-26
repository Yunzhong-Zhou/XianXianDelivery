package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.delivery.xianxian.R;
import com.delivery.xianxian.adapter.Appraisal_GridViewAdapter;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.AppraiseModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.MyLogger;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.delivery.xianxian.net.OkHttpClientManager.IMGHOST;

/**
 * Created by zyz on 2019-11-21.
 * 评价
 */
public class AppraiseActivity extends BaseActivity {
    String id = "", score = "0";
    ImageView imageView1;
    TextView textView, textView1, textView2, textView3, textView4, textView5;
    EditText editText1;
    GridView gridView;
    Appraisal_GridViewAdapter adapter;
    List<AppraiseModel.TagListBean> list = new ArrayList<>();

    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appraise);
    }

    @Override
    protected void initView() {
        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                score = rating + "";
            }
        });
        imageView1 = findViewByID_My(R.id.imageView1);
        textView = findViewByID_My(R.id.textView);
        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);
        textView4 = findViewByID_My(R.id.textView4);
        textView5 = findViewByID_My(R.id.textView5);
        editText1 = findViewByID_My(R.id.editText1);
        gridView = findViewByID_My(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).getIsgouxuan() == 0) {
                    list.get(position).setIsgouxuan(1);
                } else {
                    list.get(position).setIsgouxuan(0);
                }


                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getIsgouxuan() == 1) {

                    }
                }

                adapter.notifyDataSetChanged();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String other = "";
                String other_s = "";
                int num = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getIsgouxuan() == 1) {
                        num++;
                        other = other + list.get(i).getKey() + ",";
                        other_s = other_s + list.get(i).getVal() + ",";
                    }
                }
                if (num > 3) {
                    myToast("最多选择三个");
                } else {
                    Map<String, String> params = new HashMap<>();
                    params.put("token", localUserInfo.getToken());
                    params.put("score", score);
                    params.put("t_indent_id", id);
                    params.put("tag_id", other);
                    params.put("remark", editText1.getText().toString().trim());
                    RequestUpdata(params);
                }

            }
        });
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
                + "&t_indent_id=" + id;
        Request(string);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(AppraiseActivity.this, URLs.Appraise + string, new OkHttpClientManager.ResultCallback<AppraiseModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(AppraiseModel response) {
//                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>评价" + response);
                if (!response.getDriver_info().getHead().equals(""))
                    Glide.with(AppraiseActivity.this)
                            .load(IMGHOST + response.getDriver_info().getHead())
                            .centerCrop()
//                    .placeholder(R.mipmap.headimg)//加载站位图
//                    .error(R.mipmap.headimg)//加载失败
                            .into(imageView1);//加载图片
                textView1.setText(response.getDriver_info().getNickname());//昵称
                textView2.setText("评分" + response.getDriver_info().getComment_score());//评分
                textView3.setText("" + response.getDriver_info().getCard_number());//车牌
                textView4.setText(response.getDriver_info().getUse_type());//类型
                textView5.setText("¥" + response.getDriver_info().getMoney());//金额

                list = response.getTag_list();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setIsgouxuan(0);
                }
                adapter = new Appraisal_GridViewAdapter(AppraiseActivity.this, list);
                gridView.setAdapter(adapter);

            }
        });
    }
    private void RequestUpdata(Map<String, String> params) {
        OkHttpClientManager.postAsyn(AppraiseActivity.this, URLs.Appraise, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
//                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>发布评论" + response);
                myToast("发布成功");
                /*JSONObject jObj;
                try {
                    jObj = new JSONObject(response);
                    myToast(jObj.getString("msg"));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/

            }
        }, false);
    }
    @Override
    protected void updateView() {
        titleView.setTitle("评价");
    }
}
