package com.transport.xianxian.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cy.dialog.BaseDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.transport.xianxian.R;
import com.transport.xianxian.utils.LocalUserInfo;
import com.transport.xianxian.view.LoadingLayout;
import com.transport.xianxian.view.TitleView;

import java.util.HashMap;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


/**
 * description:
 */
public abstract class BaseActivity extends SwipeBackActivity implements IBaseView_Response, View.OnClickListener {
    private ProgressDialog pd;
    private ViewGroup layout_body;
    protected TitleView titleView;
    protected SpringView springView;
    protected View layout_current;
    protected LocalUserInfo localUserInfo;
    protected LoadingLayout loadingLayout;
    protected LayoutInflater inflater;
    public HashMap<String, String> params = new HashMap<>();

    protected ImmersionBar mImmersionBar;

    protected BaseDialog dialog;

    private SwipeBackLayout mSwipeBackLayout;//侧滑返回

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("BaseActivity", getClass().getSimpleName());

        try {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏 （如果状态栏为透明色在8.0+会报错）
        } catch (IllegalStateException e) {
            // Only fullscreen activities can request orientation
            e.printStackTrace();
        }


        localUserInfo = LocalUserInfo.getInstance(this);
        inflater = LayoutInflater.from(this);
        dialog = new BaseDialog(this);

        //BaseActivity
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.reset()
                .fitsSystemWindows(true)
                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题
                .statusBarColor(R.color.title_bg)
//                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .addTag("common")
                .init();

        //侧滑返回
        mSwipeBackLayout = getSwipeBackLayout();
        //设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
//        mSwipeBackLayout.setEdgeSize(200);//滑动删除的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.base_layout);

        /*if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/

        initContentView(layoutResID);
        initCommonView();
        initView();
        initData();
        updateView();
    }

    protected void initCommonView() {
        springView = findViewByID_My(R.id.springView);
        if (springView != null) {
            //默认风格
            springView.setHeader(new DefaultHeader(this));
            springView.setFooter(new DefaultFooter(this));

            //阿里风格
//        springView.setHeader(new AliHeader(getActivity(),true));
//        springView.setFooter(new AliFooter(getActivity(),true));

            //美团风格
//        springView.setHeader(new MeituanHeader(getActivity()));
//        springView.setFooter(new MeituanFooter(getActivity()));

            //机械齿轮
//        springView.setHeader(new RotationHeader(getActivity()));
//        springView.setFooter(new RotationFooter(getActivity()));
        }


        loadingLayout = (LoadingLayout) findViewById(R.id.loading_layout);
        if (loadingLayout != null) {
            loadingLayout.setOnRetryClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingLayout.showLoading();
                    requestServer();
                }
            });
        }
    }

    protected void initContentView(int layoutResID) {
        titleView = (TitleView) super.findViewById(R.id.title_view);
//        super.findViewById(R.id.headview).setPadding(0, (int) CommonUtil.getStatusBarHeight(this), 0, 0);

        layout_body = (FrameLayout) super.findViewById(R.id.layout_body);

        layout_current = getLayoutInflater().inflate(layoutResID, layout_body);

        //layout_body.addView(layout_current);
    }

    /**
     * 无数据或出错时点击重新加载时调用
     */
    public void requestServer() {

    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void updateView();

    protected void myToast(String content) {
//        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
        /*Toast toast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();*/
        ToastUtils.show(content);
    }

    protected <T extends View> T findViewByID_My(int id) {
        return (T) super.findViewById(id);
    }

    /**
     * 在fragment中请求服务器或本地判断，成功后在activity中做进一步操作
     */
    public void onResultSuccess() {

    }


    /**
     * @param flag 是否加载更多
     */
    @Override
    public void setSpringViewMore(boolean flag) {
        //有些页面不需要加载更多，只设置头部风格，如需加载更多在需要的页面自行设置
        if (springView != null) {
            springView.setEnableFooter(flag);
        }
    }

    @Override
    public void showProgress(boolean flag, String message) {
        if (pd == null) {
            pd = new ProgressDialog(this);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(flag);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage(message);
            pd.show();
        } else {
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(flag);
            pd.setCanceledOnTouchOutside(false);
            pd.setMessage(message);
            pd.show();
        }
    }

    @Override
    public void hideProgress() {
        try {
            if (springView != null) {
                springView.onFinishFreshAndLoad();
            }

            if (pd == null) {
                return;
            } else {
                if (pd.isShowing()) {
                    if (!isFinishing()) {
                        pd.dismiss();
                    }
                }
            }
        } catch (Exception e) {

        }
        /*if (dialog != null) {
            if (dialog.isShowing() == true) {
                dialog.dismiss();
            }
        }*/
    }

    @Override
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    /*@Override
    public void showToast(String msg) {
        if (!isFinishing()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }*/
    @Override
    public void showToast(String msg) {
        //        if (!getActivity().isFinishing()) {
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//        }

        /*if (!isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(msg);
            builder.setTitle(getString(R.string.app_prompt));
            builder.setPositiveButton(getString(R.string.app_confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.create().show();
        }*/
        if (!isFinishing()) {
            if (dialog.isShowing() == false) {
                dialog.contentView(R.layout.dialog_base1)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();

                TextView textView2 = dialog.findViewById(R.id.textView2);
                textView2.setText(msg);
                dialog.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        }
    }

    public void showToast(String msg, View.OnClickListener listener) {
        //        if (!isFinishing()) {
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//        }
        /*if (!isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(msg);
            builder.setTitle(getString(R.string.app_prompt));
            builder.setPositiveButton(getString(R.string.app_confirm), listener);
            builder.create().show();
        }*/
        if (!isFinishing()) {
            if (dialog.isShowing() == false) {
                dialog.contentView(R.layout.dialog_base1)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView textView2 = dialog.findViewById(R.id.textView2);
                textView2.setText(msg);
                dialog.findViewById(R.id.textView3).setOnClickListener(listener);
                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        }
    }

    public void showToast(String msg, String btntxt1, String btntxt2, View.OnClickListener listener1, View.OnClickListener listener2) {
        //        if (!isFinishing()) {
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//        }
        /*if (!isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(msg);
            builder.setTitle(getString(R.string.app_prompt));
            builder.setPositiveButton(getString(R.string.app_confirm), listener);
            builder.create().show();
        }*/
        if (!isFinishing()) {
            if (dialog.isShowing() == false) {
                dialog.contentView(R.layout.dialog_base2)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView textView2 = dialog.findViewById(R.id.textView2);
                textView2.setText(msg);
                TextView textView3 = dialog.findViewById(R.id.textView3);
                TextView textView4 = dialog.findViewById(R.id.textView4);
                textView3.setText(btntxt1);
                textView4.setText(btntxt2);
                textView3.setOnClickListener(listener1);
                textView4.setOnClickListener(listener2);

                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        }

    }

    @Override
    public String getStringbyid(int resId) {
        return getString(resId);
    }

    @Override
    public void showLoadingPage() {
        if (loadingLayout != null) {
            loadingLayout.showLoading();
        }
    }

    @Override
    public void showErrorPage() {
        if (loadingLayout != null) {
            loadingLayout.showError();
        }
    }

    @Override
    public void showEmptyPage() {
        if (loadingLayout != null) {
            loadingLayout.showEmpty();
        }
    }

    @Override
    public void showContentPage() {
        if (loadingLayout != null) {
            loadingLayout.showContent();
        }
    }

    @Override
    public void onClick(View v) {

    }

    // 触摸其他区域，输入法界面消失
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
