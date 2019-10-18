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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.cy.cyflowlayoutlibrary.FlowLayout;
import com.cy.cyflowlayoutlibrary.FlowLayoutAdapter;
import com.cy.dialog.BaseDialog;
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
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.superrtc.ContextUtils.getApplicationContext;


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
    TextView tv_zijintongji, tv_type, tv_distance, tv_temperature, tv_timestart, tv_timestop, tv_kaishijiedan;
    int i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0;

    Boolean isStartJieDan = false;//是否开始接单

    //定位
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        //设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。AMapLocationMode.Battery_Saving，低功耗模式。AMapLocationMode.Device_Sensors，仅设备模式。
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：默认为false。
        option.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        option.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        option.setInterval(5 * 1000);
        //设置是否返回地址信息（默认返回地址信息）
        option.setNeedAddress(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        option.setMockEnable(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        option.setHttpTimeOut(30000);
        //是否开启定位缓存机制
        option.setLocationCacheEnable(false);
        mLocationClient.setLocationOption(option);

        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        /*//可在其中解析amapLocation获取相应内容。
                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        aMapLocation.getLatitude();//获取纬度
                        aMapLocation.getLongitude();//获取经度
                        aMapLocation.getAccuracy();//获取精度信息
                        aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                        aMapLocation.getCountry();//国家信息
                        aMapLocation.getProvince();//省信息
                        aMapLocation.getCity();//城市信息
                        aMapLocation.getDistrict();//城区信息
                        aMapLocation.getStreet();//街道信息
                        aMapLocation.getStreetNum();//街道门牌号信息
                        aMapLocation.getCityCode();//城市编码
                        aMapLocation.getAdCode();//地区编码
                        aMapLocation.getAoiName();//获取当前定位点的AOI信息
                        aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                        aMapLocation.getFloor();//获取当前室内定位的楼层
                        aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                        //获取定位时间
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(amapLocation.getTime());
                        df.format(date);*/
                        MyLogger.i(">>>>>>>>>>定位信息：\n纬度：" + aMapLocation.getLatitude()
                                + "\n经度:" + aMapLocation.getAccuracy()
                                + "\n地址:" + aMapLocation.getAddress()
                        );
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        MyLogger.e("定位失败：", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                        myToast(""+aMapLocation.getErrorInfo());
                    }
                }
            }
        });

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
    public void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null)
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
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
        tv_zijintongji = findViewByID_My(R.id.tv_zijintongji);
        tv_zijintongji.setOnClickListener(this);
        ll_pingfen = findViewByID_My(R.id.ll_pingfen);
        ll_pingfen.setOnClickListener(this);

        tv_type = findViewByID_My(R.id.tv_type);
        tv_type.setOnClickListener(this);
        tv_distance = findViewByID_My(R.id.tv_distance);
        tv_distance.setOnClickListener(this);
        tv_temperature = findViewByID_My(R.id.tv_temperature);
        tv_temperature.setOnClickListener(this);
        tv_timestart = findViewByID_My(R.id.tv_timestart);
        tv_timestart.setOnClickListener(this);
        tv_timestop = findViewByID_My(R.id.tv_timestop);
        tv_timestop.setOnClickListener(this);
        tv_kaishijiedan = findViewByID_My(R.id.tv_kaishijiedan);
        tv_kaishijiedan.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        requestServer();
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

                /*for (int i = 0; i < 10; i++) {
                    list.add(new Fragment1Model());
                }*/
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
                        list.add("6吨");
                        list.add("15-20℃恒温");
                        flowLayoutAdapter = new FlowLayoutAdapter<String>(list) {
                            @Override
                            public void bindDataToView(FlowLayoutAdapter.ViewHolder holder, int position, String bean) {
//                                holder.setText(R.id.tv,bean);
                                TextView tv = holder.getView(R.id.tv);
                                tv.setText(bean);
                                if (position == 0) {
                                    tv.setTextColor(getResources().getColor(R.color.white));
                                    tv.setBackgroundResource(R.drawable.yuanjiao_3_lanse);
                                } else {
                                    tv.setTextColor(getResources().getColor(R.color.black1));
                                    tv.setBackgroundResource(R.drawable.yuanjiao_3_huise);
                                }
                            }

                            @Override
                            public void onItemClick(int position, String bean) {
//                        showToast("点击" + position);
                            }

                            @Override
                            public int getItemLayoutID(int position, String bean) {
                                return R.layout.item_flowlayout;
                            }
                        };
                        ((FlowLayout) holder.getView(R.id.flowLayout)).setAdapter(flowLayoutAdapter);
                    }
                };
                recyclerView.setAdapter(mAdapter);
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
            case R.id.tv_zijintongji:
                //资金统计
                CommonUtil.gotoActivity(getActivity(), CapitalStatisticsActivity.class);
                break;
            case R.id.ll_pingfen:
                //评分详情
                CommonUtil.gotoActivity(getActivity(), ScoreDetailActivity.class);
                break;
            case R.id.tv_kaishijiedan:
                //开始接单
                isStartJieDan = !isStartJieDan;
                if (isStartJieDan) {
                    //点击开始接单
                    tv_kaishijiedan.setBackgroundResource(R.drawable.btn_huise);
                    tv_kaishijiedan.setText("关闭接单");
                    if (mLocationClient != null) {
                        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
                        mLocationClient.stopLocation();
                        mLocationClient.startLocation();
                    }
//                    startAlarm();
                } else {
                    //点击关闭接单
                    tv_kaishijiedan.setBackgroundResource(R.drawable.btn_lanse);
                    tv_kaishijiedan.setText("开始接单");
                    if (mLocationClient != null)
                        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
//                    stopAlarm();
                }
                break;
            case R.id.tv_type:
                //订单类型
                BaseDialog dialog1 = new BaseDialog(getActivity());
                dialog1.contentView(R.layout.dialog_list)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView title = dialog1.findViewById(R.id.textView1);
                title.setText("订单类型");
                RecyclerView rv = dialog1.findViewById(R.id.recyclerView);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
                rv.setLayoutManager(mLinearLayoutManager);

                List<String> mStringList = new ArrayList<>();
                mStringList.add("不限");
                mStringList.add("专车");
                mStringList.add("顺风车");

                CommonAdapter<String> adapter = new CommonAdapter<String>
                        (getActivity(), R.layout.item_dialog_list, mStringList) {
                    @Override
                    protected void convert(ViewHolder holder, String model, int position) {
                        TextView tv = holder.getView(R.id.textView);
                        ImageView iv = holder.getView(R.id.imageView);
                        tv.setText(model);
                        if (position == i1) {
                            tv.setTextColor(getResources().getColor(R.color.blue));
                            iv.setImageResource(R.mipmap.ic_xuanzhong);
                        } else {
                            tv.setTextColor(getResources().getColor(R.color.black1));
                            iv.setImageResource(R.mipmap.ic_weixuan);
                        }
                    }
                };
                adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        i1 = i;
                        adapter.notifyDataSetChanged();
                        tv_type.setText(mStringList.get(i));
                        dialog1.dismiss();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        return false;
                    }
                });
                rv.setAdapter(adapter);
                dialog1.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });
                break;
            case R.id.tv_distance:
                //距离筛选
                BaseDialog dialog2 = new BaseDialog(getActivity());
                dialog2.contentView(R.layout.dialog_list)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView title2 = dialog2.findViewById(R.id.textView1);
                title2.setText("距离筛选");
                RecyclerView rv2 = dialog2.findViewById(R.id.recyclerView);
                LinearLayoutManager mLinearLayoutManager2 = new LinearLayoutManager(getActivity());
                rv2.setLayoutManager(mLinearLayoutManager2);

                List<String> mStringList2 = new ArrayList<>();
                mStringList2.add("不限");
                mStringList2.add("市内");
                mStringList2.add("长途");

                CommonAdapter<String> adapter2 = new CommonAdapter<String>
                        (getActivity(), R.layout.item_dialog_list, mStringList2) {
                    @Override
                    protected void convert(ViewHolder holder, String model, int position) {
                        TextView tv = holder.getView(R.id.textView);
                        ImageView iv = holder.getView(R.id.imageView);
                        tv.setText(model);
                        if (position == i2) {
                            tv.setTextColor(getResources().getColor(R.color.blue));
                            iv.setImageResource(R.mipmap.ic_xuanzhong);
                        } else {
                            tv.setTextColor(getResources().getColor(R.color.black1));
                            iv.setImageResource(R.mipmap.ic_weixuan);
                        }
                    }
                };
                adapter2.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        i2 = i;
                        adapter2.notifyDataSetChanged();
                        tv_distance.setText(mStringList2.get(i));
                        dialog2.dismiss();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        return false;
                    }
                });
                rv2.setAdapter(adapter2);
                dialog2.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });
                break;
            case R.id.tv_temperature:
                //温层选择
                BaseDialog dialog3 = new BaseDialog(getActivity());
                dialog3.contentView(R.layout.dialog_list)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView title3 = dialog3.findViewById(R.id.textView1);
                title3.setText("温层选择");
                RecyclerView rv3 = dialog3.findViewById(R.id.recyclerView);
                LinearLayoutManager mLinearLayoutManager3 = new LinearLayoutManager(getActivity());
                rv3.setLayoutManager(mLinearLayoutManager3);

                List<String> mStringList3 = new ArrayList<>();
                mStringList3.add("不限");
                mStringList3.add("-18℃~-2℃");
                mStringList3.add("+2℃~+10℃");
                mStringList3.add("+15℃~+20℃");
                CommonAdapter<String> adapter3 = new CommonAdapter<String>
                        (getActivity(), R.layout.item_dialog_list, mStringList3) {
                    @Override
                    protected void convert(ViewHolder holder, String model, int position) {
                        TextView tv = holder.getView(R.id.textView);
                        ImageView iv = holder.getView(R.id.imageView);
                        tv.setText(model);
                        if (position == i3) {
                            tv.setTextColor(getResources().getColor(R.color.blue));
                            iv.setImageResource(R.mipmap.ic_xuanzhong);
                        } else {
                            tv.setTextColor(getResources().getColor(R.color.black1));
                            iv.setImageResource(R.mipmap.ic_weixuan);
                        }
                    }
                };
                adapter3.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        i3 = i;
                        adapter3.notifyDataSetChanged();
                        tv_temperature.setText(mStringList3.get(i));
                        dialog3.dismiss();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        return false;
                    }
                });
                rv3.setAdapter(adapter3);
                dialog3.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog3.dismiss();
                    }
                });
                break;
            case R.id.tv_timestart:
                //接单时间起
                BaseDialog dialog4 = new BaseDialog(getActivity());
                dialog4.contentView(R.layout.dialog_list)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView title4 = dialog4.findViewById(R.id.textView1);
                title4.setText("接单时间起");
                RecyclerView rv4 = dialog4.findViewById(R.id.recyclerView);
                LinearLayoutManager mLinearLayoutManager4 = new LinearLayoutManager(getActivity());
                rv4.setLayoutManager(mLinearLayoutManager4);

                List<String> mStringList4 = new ArrayList<>();
                mStringList4.add("现在");
                mStringList4.add("11:00");
                mStringList4.add("12:00");
                mStringList4.add("13:00");
                mStringList4.add("14:00");
                mStringList4.add("15:00");
                mStringList4.add("16:00");

                CommonAdapter<String> adapter4 = new CommonAdapter<String>
                        (getActivity(), R.layout.item_dialog_list, mStringList4) {
                    @Override
                    protected void convert(ViewHolder holder, String model, int position) {
                        TextView tv = holder.getView(R.id.textView);
                        ImageView iv = holder.getView(R.id.imageView);
                        tv.setText(model);
                        if (position == i4) {
                            tv.setTextColor(getResources().getColor(R.color.blue));
                            iv.setImageResource(R.mipmap.ic_xuanzhong);
                        } else {
                            tv.setTextColor(getResources().getColor(R.color.black1));
                            iv.setImageResource(R.mipmap.ic_weixuan);
                        }
                    }
                };
                adapter4.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        i4 = i;
                        adapter4.notifyDataSetChanged();
                        tv_timestart.setText(mStringList4.get(i));
                        dialog4.dismiss();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        return false;
                    }
                });
                rv4.setAdapter(adapter4);
                dialog4.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog4.dismiss();
                    }
                });
                break;
            case R.id.tv_timestop:
                //接单时间止
                BaseDialog dialog5 = new BaseDialog(getActivity());
                dialog5.contentView(R.layout.dialog_list)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView title5 = dialog5.findViewById(R.id.textView1);
                title5.setText("接单时间止");
                RecyclerView rv5 = dialog5.findViewById(R.id.recyclerView);
                LinearLayoutManager mLinearLayoutManager5 = new LinearLayoutManager(getActivity());
                rv5.setLayoutManager(mLinearLayoutManager5);

                List<String> mStringList5 = new ArrayList<>();
                mStringList5.add("现在");
                mStringList5.add("21:00");
                mStringList5.add("22:00");
                mStringList5.add("23:00");
                mStringList5.add("24:00");
                mStringList5.add("次日1:00");
                mStringList5.add("次日2:00");

                CommonAdapter<String> adapter5 = new CommonAdapter<String>
                        (getActivity(), R.layout.item_dialog_list, mStringList5) {
                    @Override
                    protected void convert(ViewHolder holder, String model, int position) {
                        TextView tv = holder.getView(R.id.textView);
                        ImageView iv = holder.getView(R.id.imageView);
                        tv.setText(model);
                        if (position == i5) {
                            tv.setTextColor(getResources().getColor(R.color.blue));
                            iv.setImageResource(R.mipmap.ic_xuanzhong);
                        } else {
                            tv.setTextColor(getResources().getColor(R.color.black1));
                            iv.setImageResource(R.mipmap.ic_weixuan);
                        }
                    }
                };
                adapter5.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        i5 = i;
                        adapter5.notifyDataSetChanged();
                        tv_timestop.setText(mStringList5.get(i));
                        dialog5.dismiss();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        return false;
                    }
                });
                rv5.setAdapter(adapter5);
                dialog5.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog5.dismiss();
                    }
                });
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
        showProgress(true, getString(R.string.app_loading));
        String string = "?token=" + localUserInfo.getToken();
        Request(string);
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
