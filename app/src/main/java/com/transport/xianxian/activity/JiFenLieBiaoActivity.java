package com.transport.xianxian.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.okhttp.Request;
import com.transport.xianxian.R;
import com.transport.xianxian.adapter.JiFenLieBiao_GridViewAdapter;
import com.transport.xianxian.base.BaseActivity;
import com.transport.xianxian.model.JiFenLieBiaoModel;
import com.transport.xianxian.model.JiFenMingXiModel;
import com.transport.xianxian.net.OkHttpClientManager;
import com.transport.xianxian.net.URLs;
import com.transport.xianxian.utils.CommonUtil;
import com.transport.xianxian.utils.MyLogger;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.transport.xianxian.net.OkHttpClientManager.HOST;

/**
 * Created by zyz on 2019-10-08.
 * 商品列表
 */
public class JiFenLieBiaoActivity extends BaseActivity {
    int type = 1;
    Banner banner;
    List<String> images = new ArrayList<>();

    GridView gridView;
    List<JiFenLieBiaoModel> list = new ArrayList<>();
    JiFenLieBiao_GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jifenliebiao);
    }

    @Override
    protected void initView() {
        images.add("http://file02.16sucai.com/d/file/2014/0825/dcb017b51479798f6c60b7b9bd340728.jpg");
        images.add("http://file02.16sucai.com/d/file/2014/0825/dcb017b51479798f6c60b7b9bd340728.jpg");
        images.add("http://file02.16sucai.com/d/file/2014/0825/dcb017b51479798f6c60b7b9bd340728.jpg");
        images.add("http://file02.16sucai.com/d/file/2014/0825/dcb017b51479798f6c60b7b9bd340728.jpg");

        banner = findViewByID_My(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });

        gridView = findViewByID_My(R.id.gridView);
        for (int i = 0; i < 10; i++) {
            list.add(new JiFenLieBiaoModel());
        }
        gridViewAdapter = new JiFenLieBiao_GridViewAdapter(this, list);
        gridView.setAdapter(gridViewAdapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_jilu:
                //兑换记录
                CommonUtil.gotoActivity(this, DuiHuanListActivity.class, false);
                break;
        }
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 1);
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
        OkHttpClientManager.getAsyn(this, URLs.JiFenMingXi + string, new OkHttpClientManager.ResultCallback<JiFenMingXiModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(final JiFenMingXiModel response) {
                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>商品列表" + response);

            }
        });
    }

    @Override
    protected void updateView() {
        titleView.setTitle("商品列表");
        titleView.showRightTextview("积分规则", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("url", HOST + "/wechat/article/detail?id=13a19f182849fa6440b88e4ee0a5e5e8");
                CommonUtil.gotoActivityWithData(JiFenLieBiaoActivity.this, WebContentActivity.class, bundle, false);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Glide 加载图片简单用法
            Glide.with(context)
                    .load(path)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(imageView);

            //Picasso 加载图片简单用法
//            Picasso.with(context).load(path).into(imageView);

            //用fresco加载图片简单用法，记得要写下面的createImageView方法
//            Uri uri = Uri.parse((String) path);
//            imageView.setImageURI(uri);
        }
    }
}
