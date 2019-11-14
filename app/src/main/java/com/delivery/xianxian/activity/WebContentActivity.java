package com.delivery.xianxian.activity;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.utils.MyLogger;


/**
 * Created by zyz on 2017/9/12.
 * 文章详情
 */

public class WebContentActivity extends BaseActivity {
    String article_id = "";
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webcontent);
    }

    @Override
    protected void initView() {
        showProgress(true, getString(R.string.app_loading));
        webView = (WebView) findViewById(R.id.webView);
        String url = getIntent().getStringExtra("url");
        MyLogger.i(">>>>>>url" + url);

        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webView.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        webView.getSettings().setSupportZoom(false);//是否可以缩放，默认true
        webView.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        webView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webView.getSettings().setAppCacheEnabled(true);//是否使用缓存
        webView.getSettings().setDomStorageEnabled(true);//DOM Storage
        webView.getSettings().setAllowFileAccess(true);// 设置可以访问文件
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // webSettings.setDatabaseEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);

//        webView.getSettings().setUserAgentString("User-Agent:Android");//设置用户代理，一般不用
        //页面打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                MyLogger.i(">>>>>>" + url);
                //打开支付宝
                if (url.startsWith("alipays://platformapi/startApp?")||url.startsWith("alipays://platformapi/startapp?")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

                //打开微信
                if (url.startsWith("weixin://wap/pay?")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if ((null != view.getUrl()) && (view.getUrl().startsWith("https://open.t.qq.com"))) {
                    handler.proceed();// 接受证书
                } else {
                    handler.cancel(); // 默认的处理方式，WebView变成空白
                }
                // handleMessage(Message msg); 其他处理
            }

        });
        //页面标题
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                MyLogger.i(">>>>>页面标题" + title);
                titleView.setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //当进度走到100的时,加载完成
                if (newProgress == 100) {
                    hideProgress();
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.loadUrl(url);//加载web资源
        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    protected void initData() {
        /*article_id = getIntent().getStringExtra("article_id");
        if (!article_id.equals("")){
            String string = "?article_id=" + article_id
                    + "&token=" + localUserInfo.getToken();
            RequestNoticeBrowse(string);//创建公告浏览
        }*/
    }

    /*//创建公告浏览
    private void RequestNoticeBrowse(String string) {
        OkHttpClientManager.getAsyn(URLs.NoticeBrowse + string, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                showErrorPage();
                if (info.equals("13")) {
                    localUserInfo.setUserId("");
                    myToast(getString(R.string.app_re_register));
                    CommonUtil.gotoActivityWithFinishOtherAll(WebContentActivity.this, LoginActivity.class, true);
                } else {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                showContentPage();
                MyLogger.i(">>>>>>>>>创建公告浏览" + response);
            }
        });

    }*/
    @Override
    protected void updateView() {

    }
}
