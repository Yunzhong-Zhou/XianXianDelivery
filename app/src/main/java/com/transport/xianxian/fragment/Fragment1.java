package com.transport.xianxian.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cy.cyflowlayoutlibrary.FlowLayout;
import com.cy.cyflowlayoutlibrary.FlowLayoutAdapter;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.transport.xianxian.R;
import com.transport.xianxian.activity.CapitalStatisticsActivity;
import com.transport.xianxian.activity.MainActivity;
import com.transport.xianxian.activity.NoticeDetailActivity;
import com.transport.xianxian.activity.ScoreDetailActivity;
import com.transport.xianxian.base.BaseFragment;
import com.transport.xianxian.model.Fragment1Model;
import com.transport.xianxian.net.OkHttpClientManager;
import com.transport.xianxian.net.URLs;
import com.transport.xianxian.utils.CommonUtil;
import com.transport.xianxian.utils.MyLogger;
import com.transport.xianxian.zxing.CaptureActivity;
import com.transport.xianxian.zxing.Constant;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by fafukeji01 on 2016/1/6.
 * 首页
 */

public class Fragment1 extends BaseFragment {
    private RecyclerView recyclerView;
    List<Fragment1Model> list = new ArrayList<>();
    CommonAdapter<Fragment1Model> mAdapter;

    ImageView btn_right;
    LinearLayout ll_xiaoxi, ll_pingfen;
    TextView txt_zijin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.item == 0) {
            requestServer();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (MainActivity.item == 0) {
            requestServer();
        }
    }

    @Override
    protected void initView(View view) {
        setSpringViewMore(false);//需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                String string = "?token=" + localUserInfo.getToken();
                Request(string);
            }

            @Override
            public void onLoadmore() {

            }
        });
        recyclerView = findViewByID_My(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);

        btn_right = findViewByID_My(R.id.btn_right);
        btn_right.setOnClickListener(this);
        ll_xiaoxi = findViewByID_My(R.id.ll_xiaoxi);
        ll_xiaoxi.setOnClickListener(this);
        txt_zijin = findViewByID_My(R.id.txt_zijin);
        txt_zijin.setOnClickListener(this);
        ll_pingfen = findViewByID_My(R.id.ll_pingfen);
        ll_pingfen.setOnClickListener(this);

    }

    @Override
    protected void initData() {
//        requestServer();

    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(getActivity(), URLs.Fragment1 + string, new OkHttpClientManager.ResultCallback<Fragment1Model>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(Fragment1Model response) {
                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>首页" + response);

                mAdapter = new CommonAdapter<Fragment1Model>
                        (getActivity(), R.layout.item_fragment1, list) {
                    @Override
                    protected void convert(ViewHolder holder, Fragment1Model model, int position) {
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

                        //标签
                        FlowLayoutAdapter<String> flowLayoutAdapter;
                        List<String> list = new ArrayList<>();
                        list.add("专车");
                        list.add("专车");
                        list.add("专车");
                        flowLayoutAdapter = new FlowLayoutAdapter<String>(list) {
                            @Override
                            public void bindDataToView(ViewHolder holder, int position, String bean) {
//                                holder.setText(R.id.tv,bean);
                                TextView tv = holder.getView(R.id.tv);
                                tv.setText(bean);
                                if (position == 0){
                                    tv.setBackgroundResource(R.drawable.yuanjiao_3_lanse);
                                }else {
                                    tv.setBackgroundResource(R.drawable.yuanjiao_3_huise);
                                }
                            }

                            @Override
                            public void onItemClick(int position, String bean) {

                                showToast("点击" + position);
                            }

                            @Override
                            public int getItemLayoutID(int position, String bean) {
                                return R.layout.item_flowlayout;
                            }
                        };
                        ((FlowLayout) holder.getView(R.id.flowLayout)).setAdapter(flowLayoutAdapter);
                    }
                };
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                //扫一扫
                startQrCode();
                break;
            case R.id.ll_xiaoxi:
                //公告详情
                CommonUtil.gotoActivity(getActivity(), NoticeDetailActivity.class);
                break;
            case R.id.txt_zijin:
                //资金统计
                CommonUtil.gotoActivity(getActivity(), CapitalStatisticsActivity.class);
                break;
            case R.id.ll_pingfen:
                //评分详情
                CommonUtil.gotoActivity(getActivity(), ScoreDetailActivity.class);
                break;

        }
    }

    // 开始扫码
    private void startQrCode() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
            return;
        }
        // 二维码扫码
        /*Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);*/

        Intent intent1 = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent1, Constant.REQ_QR_CODE);
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();
       /* showProgress(true, getString(R.string.app_loading));
        String string = "?token=" + localUserInfo.getToken();
        Request(string);*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        /*if (requestCode == 10086) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("number", result);
                    CommonUtil.gotoActivityWithData(getActivity(), EquipmentDetailActivity.class, bundle1, false);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }*/
        //扫描结果回调
        if (requestCode == Constant.REQ_QR_CODE) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
                MyLogger.i(">>>扫码返回>>>>" + scanResult);
                if (scanResult != null && !scanResult.equals("")) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("id", scanResult);
//                    CommonUtil.gotoActivityWithData(QRCodeActivity.this, ScavengingPaymentActivity.class, bundle1, true);
                }
            }

        }
    }
}
