package com.delivery.xianxian.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.cy.dialog.BaseDialog;
import com.delivery.xianxian.R;
import com.delivery.xianxian.activity.Auth_ShenFenZhengActivity;
import com.delivery.xianxian.activity.ConfirmOrderActivity;
import com.delivery.xianxian.activity.MainActivity;
import com.delivery.xianxian.activity.SelectAddressActivity;
import com.delivery.xianxian.base.BaseFragment;
import com.delivery.xianxian.model.AddFeeModel;
import com.delivery.xianxian.model.Fragment1Model;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.CommonUtil;
import com.delivery.xianxian.utils.MyLogger;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.delivery.xianxian.net.OkHttpClientManager.IMGHOST;


/**
 * Created by fafukeji01 on 2016/1/6.
 * 首页
 */

public class Fragment1 extends BaseFragment {
    //左上角设置城市
    LinearLayout btn_left;
    TextView tv_addr;
    List<HotCity> hotCities = new ArrayList<>();
    String province = "", city = "", cityCode = "";
    //定位
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;

    //类型
    int use_type = 1;
    TextView tv_type1, tv_type2, tv_type3;

    //车型
    int item = 0;
    RecyclerView recyclerView;
    List<Fragment1Model.CarTypeBean> carTypeList = new ArrayList<>();
    CommonAdapter<Fragment1Model.CarTypeBean> mStringAdapter;
    LayoutInflater inflater;
    ViewPager viewPager;
    private ArrayList<View> pageViews = new ArrayList<>();
    GuidePageAdapter mPageAdapter;
    ImageView iv_left, iv_right;

    //1、有预约时间、2、无预约时间
    String is_plan = "2";
    LinearLayout ll_time1;
    TextView tv_now, tv_next;

    //地址
    String addr_ids = "";
    TextView tv_qidian, tv_zhongdian, tv_tujingdian, tv_time;
    LinearLayout ll_add, ll_time2;
    String startAddr_id = "", endAddr_id = "", plan_time = "";
    TimePickerView pvTime1;

    //下一步
    AddFeeModel model;
    TextView tv_detail, tv_nextStep;

    //用车时间-顺风车
    LinearLayout ll_time3;
    TextView tv_time3;

    //快递信息
    LinearLayout ll_kuaidi, ll_fahuo, ll_shouhuo;
    TextView tv_kuaidi_type1, tv_kuaidi_type2, tv_kuaidi_type3, tv_kuaidi_type4,
            tv_fahuo_name, tv_fahuo_mobile, tv_shouhuo_name, tv_shouhuo_mobile;
    ImageView iv_kuaidi_kaiguan;
    String goods_send_home = "1";//1是2否
    String goods_name = "", goods_quantity = "", goods_weight = "", goods_bulk = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        this.inflater = inflater;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (localUserInfo.getIsordertrue().equals("1")) {
            //是否下单成功//0未成功，1成功
            tv_qidian.setText("");
            startAddr_id = "";
            tv_fahuo_name.setText("发货人：");
            tv_fahuo_mobile.setText("电话号码：");

            tv_zhongdian.setText("");
            endAddr_id = "";
            tv_shouhuo_name.setText("收货人：");
            tv_shouhuo_mobile.setText("电话号码：");

            ll_add.removeAllViews();
            addr_ids = "";

            tv_time.setText("");
            tv_time3.setText("");
            plan_time = "";

            tv_kuaidi_type1.setText("");
            tv_kuaidi_type2.setText("");
            tv_kuaidi_type3.setText("");
            tv_kuaidi_type4.setText("");
            goods_name = "";
            goods_quantity = "";
            goods_weight = "";
            goods_bulk = "";

            localUserInfo.setIsordertrue("0");//是否下单成功//0未成功，1成功
        }
        if (MainActivity.item == 0) {
            requestServer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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
        //左上角设置城市
        btn_left = findViewByID_My(R.id.btn_left);
        btn_left.setOnClickListener(this);
        tv_addr = findViewByID_My(R.id.tv_addr);

        //类型
        tv_type1 = findViewByID_My(R.id.tv_type1);
        tv_type2 = findViewByID_My(R.id.tv_type2);
        tv_type3 = findViewByID_My(R.id.tv_type3);
        tv_type1.setOnClickListener(this);
        tv_type2.setOnClickListener(this);
        tv_type3.setOnClickListener(this);

        //车型
        recyclerView = findViewByID_My(R.id.recyclerView);
        viewPager = findViewByID_My(R.id.viewPager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        iv_left = findViewByID_My(R.id.iv_left);
        iv_left.setOnClickListener(this);
        iv_right = findViewByID_My(R.id.iv_right);
        iv_right.setOnClickListener(this);

        //现在、预约
        ll_time1 = findViewByID_My(R.id.ll_time1);
        tv_now = findViewByID_My(R.id.tv_now);
        tv_now.setOnClickListener(this);
        tv_next = findViewByID_My(R.id.tv_next);
        tv_next.setOnClickListener(this);

        //地址
        tv_qidian = findViewByID_My(R.id.tv_qidian);
        tv_qidian.setOnClickListener(this);
        tv_zhongdian = findViewByID_My(R.id.tv_zhongdian);
        tv_zhongdian.setOnClickListener(this);
        tv_tujingdian = findViewByID_My(R.id.tv_tujingdian);
        tv_tujingdian.setOnClickListener(this);
        ll_add = findViewByID_My(R.id.ll_add);
        tv_time = findViewByID_My(R.id.tv_time);
        ll_time2 = findViewByID_My(R.id.ll_time2);
        ll_time2.setOnClickListener(this);
        //下一步
        tv_detail = findViewByID_My(R.id.tv_detail);
        tv_detail.setOnClickListener(this);
        tv_nextStep = findViewByID_My(R.id.tv_nextStep);
        tv_nextStep.setOnClickListener(this);

        //用车时间-顺风车
        ll_time3 = findViewByID_My(R.id.ll_time3);
        tv_time3 = findViewByID_My(R.id.tv_time3);
        ll_time3.setOnClickListener(this);
        //快递信息
        ll_kuaidi = findViewByID_My(R.id.ll_kuaidi);
        tv_kuaidi_type1 = findViewByID_My(R.id.tv_kuaidi_type1);
        tv_kuaidi_type2 = findViewByID_My(R.id.tv_kuaidi_type2);
        tv_kuaidi_type3 = findViewByID_My(R.id.tv_kuaidi_type3);
        tv_kuaidi_type4 = findViewByID_My(R.id.tv_kuaidi_type4);
        iv_kuaidi_kaiguan = findViewByID_My(R.id.iv_kuaidi_kaiguan);
        tv_kuaidi_type1.setOnClickListener(this);
        tv_kuaidi_type2.setOnClickListener(this);
        tv_kuaidi_type3.setOnClickListener(this);
        tv_kuaidi_type4.setOnClickListener(this);
        iv_kuaidi_kaiguan.setOnClickListener(this);
        ll_fahuo = findViewByID_My(R.id.ll_fahuo);
        ll_shouhuo = findViewByID_My(R.id.ll_shouhuo);
        tv_fahuo_name = findViewByID_My(R.id.tv_fahuo_name);
        tv_fahuo_mobile = findViewByID_My(R.id.tv_fahuo_mobile);
        tv_shouhuo_name = findViewByID_My(R.id.tv_shouhuo_name);
        tv_shouhuo_mobile = findViewByID_My(R.id.tv_shouhuo_mobile);
    }

    @Override
    protected void initData() {
//        requestServer();
        //热门城市
        hotCities.add(new HotCity("北京", "北京", "101010100")); //code为城市代码
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity());
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
                                + "\n经度:" + aMapLocation.getLongitude()
                                + "\n地址:" + aMapLocation.getAddress());

                        tv_addr.setText(aMapLocation.getCity() + "");

                        province = aMapLocation.getProvince();//省信息
                        city = aMapLocation.getCity();//城市信息

                        cityCode = aMapLocation.getCityCode();//城市编码

                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        MyLogger.e("定位失败：", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                        myToast("" + aMapLocation.getErrorInfo());
                    }
                }
            }
        });

        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
//        mLocationClient.stopLocation();
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        mLocationClient.startLocation();
    }

    //获取车型
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
                //保存是否认证
                localUserInfo.setIsVerified(response.getIs_certification()+"");//1 认证 2 未认证

                carTypeList = response.getCar_type();
                //车型描述
                pageViews = new ArrayList<View>();
                for (int i = 0; i < carTypeList.size(); i++) {
                    /*if (response.getCar_type().equals(carTypeList.get(i).getId())) {
                        item = i;
                    }*/
                    pageViews.add(inflater.inflate(R.layout.item_auth_clzp_vp_page, null));
                }
                for (int i = 0; i < pageViews.size(); i++) {
                    ImageView iv = (ImageView) pageViews.get(i).findViewById(R.id.iv);
                    TextView tv1 = pageViews.get(i).findViewById(R.id.tv1);
                    TextView tv2 = pageViews.get(i).findViewById(R.id.tv2);
                    TextView tv3 = pageViews.get(i).findViewById(R.id.tv3);
                    tv1.setText("长宽高：" + carTypeList.get(i).getSize());
                    tv2.setText("载重：" + carTypeList.get(i).getWeight() + "吨");
                    tv3.setText("承载体积：" + carTypeList.get(i).getBulk() + "方");
                    if (!carTypeList.get(i).getImage().equals(""))
                        Glide.with(getActivity())
                                .load(IMGHOST + carTypeList.get(i).getImage())
                                .centerCrop()
//                                    .placeholder(R.mipmap.headimg)//加载站位图
//                                    .error(R.mipmap.headimg)//加载失败
                                .into(iv);//加载图片
                }
                mPageAdapter = new GuidePageAdapter();
                viewPager.setAdapter(mPageAdapter);
                viewPager.setOnPageChangeListener(new GuidePageChangeListener());
                //车型
                mStringAdapter = new CommonAdapter<Fragment1Model.CarTypeBean>
                        (getActivity(), R.layout.item_auth_cheliangzhaopian_tv, carTypeList) {
                    @Override
                    protected void convert(ViewHolder holder, Fragment1Model.CarTypeBean model, int position) {
                        TextView tv1 = holder.getView(R.id.tv1);
                        TextView tv2 = holder.getView(R.id.tv2);
                        LinearLayout ll = holder.getView(R.id.ll);
                        tv1.setText(model.getName());
                        tv2.setText(model.getName());

                        if (item == position) {
                            ll.setVisibility(View.VISIBLE);
                            tv1.setVisibility(View.GONE);
                        } else {
                            ll.setVisibility(View.GONE);
                            tv1.setVisibility(View.VISIBLE);
                        }

                    }
                };
                mStringAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        item = i;
                        changeCarType();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        return false;
                    }
                });
                recyclerView.setAdapter(mStringAdapter);
                changeCarType();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                //选择地址
                CityPicker.from(getActivity()) //activity或者fragment
                        .enableAnimation(true)    //启用动画效果，默认无
//                        .setAnimationStyle(anim)	//自定义动画
//                        .setLocatedCity(new LocatedCity("杭州", "浙江", "101210101"))  //APP自身已定位的城市，传null会自动定位（默认）
                        .setHotCities(hotCities)    //指定热门城市
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                //选择的城市
                                tv_addr.setText(data.getName() + "");
                                city = data.getName();
                            }

                            @Override
                            public void onCancel() {
                                //取消
                                /*Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();*/
                            }

                            @Override
                            public void onLocate() {
                                //定位接口，需要APP自身实现，这里模拟一下定位
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //定位完成之后更新数据到城市选择器
                                        CityPicker.from(getActivity()).locateComplete(new LocatedCity(province,
                                                city, cityCode), LocateState.SUCCESS);
                                    }
                                }, 3000);
                            }
                        })
                        .show();
                break;

            case R.id.tv_type1:
                //专车
                use_type = 1;
                changeUI();
                break;
            case R.id.tv_type2:
                //顺风车
                use_type = 2;
                changeUI();
                break;
            case R.id.tv_type3:
                //快递
                showToast("零担功能正在维护中...");
                /*use_type = 3;
                changeUI();*/
                break;

            case R.id.iv_left:
                //车型选择-左
                if (item > 0) {
                    item--;
                    changeCarType();
                }
                break;
            case R.id.iv_right:
                //车型选择-右
                if (item < carTypeList.size() - 1) {
                    item++;
                    changeCarType();
                }
                break;

            case R.id.tv_now:
                //现在用车
                is_plan = "2";
                plan_time = "";
                tv_now.setBackgroundResource(R.drawable.yuanjiao_10_lanse_left);
                tv_next.setBackgroundResource(R.drawable.yuanjiao_10_huise_right);
                tv_now.setTextColor(getResources().getColor(R.color.white));
                tv_next.setTextColor(getResources().getColor(R.color.black2));

                ll_time2.setVisibility(View.GONE);
                break;
            case R.id.tv_next:
                //预约用车
                is_plan = "1";
                tv_now.setBackgroundResource(R.drawable.yuanjiao_10_huise_left);
                tv_next.setBackgroundResource(R.drawable.yuanjiao_10_lanse_right);
                tv_now.setTextColor(getResources().getColor(R.color.black2));
                tv_next.setTextColor(getResources().getColor(R.color.white));

                ll_time2.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_qidian:
                //起点
                Intent intent1 = new Intent(getActivity(), SelectAddressActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", 10001);
                bundle1.putString("city", tv_addr.getText().toString());
               /* if (!startAddr_id.equals("")){

                }else {
                    bundle1.putString("city", "");
                }*/
                intent1.putExtras(bundle1);
                startActivityForResult(intent1, 10001, bundle1);
                break;
            case R.id.tv_zhongdian:
                //终点
                Intent intent2 = new Intent(getActivity(), SelectAddressActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("type", 10002);
                bundle2.putString("city", tv_addr.getText().toString());
                /*if (!startAddr_id.equals("")){

                }else {
                    bundle2.putString("city", "");
                }*/
                intent2.putExtras(bundle2);
                startActivityForResult(intent2, 10002, bundle2);
                break;
            case R.id.tv_tujingdian:
                //途经点
                Intent intent3 = new Intent(getActivity(), SelectAddressActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putInt("type", 10003);
                bundle3.putString("city", tv_addr.getText().toString());
                /*if (!startAddr_id.equals("")){

                }else {
                    bundle3.putString("city", "");
                }*/
                intent3.putExtras(bundle3);
                startActivityForResult(intent3, 10003, bundle3);
                break;
            case R.id.ll_time2:
                //预约时间
                is_plan = "1";
                setDate("选择预约时间", tv_time);
                break;

            /*case R.id.tv_detail:
                //详细
                Bundle bundle4 = new Bundle();
                bundle4.putString("city", city);
                bundle4.putString("car_type_id", carTypeList.get(item).getId() + "");
                bundle4.putString("use_type", use_type + "");
                CommonUtil.gotoActivityWithData(getActivity(), FeeDetailActivity.class, bundle4, false);
                break;*/
            case R.id.tv_nextStep:
                //下一步
                if (localUserInfo.getIsVerified().equals("2")) {
                    showToast("您暂未完成认证，确定前往认证？", "去认证", "取消",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    CommonUtil.gotoActivity(getActivity(), Auth_ShenFenZhengActivity.class, false);
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                }else {
                    if (match()) {
                        //先计算费用-返回路程等信息
                        Map<String, String> params = new HashMap<>();
                        params.put("token", localUserInfo.getToken());
                        params.put("city", city);
                        params.put("car_type_id", carTypeList.get(item).getId() + "");
                        params.put("use_type", use_type + "");
                        params.put("addr_ids", addr_ids);
                        RequestAdd(params);
                    }
                }

                break;
            case R.id.ll_time3:
                //用车时间-顺风车
                is_plan = "1";
                setDate("选择用车时间", tv_time3);
                break;

            case R.id.tv_kuaidi_type1:
                //类型
                dialog = new BaseDialog(getActivity());
                dialog.contentView(R.layout.dialog_edit)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView textView1 = dialog.findViewById(R.id.textView1);
                textView1.setText("类型");
                TextView textView2 = dialog.findViewById(R.id.textView2);
                textView2.setText("");
                final EditText editText1 = dialog.findViewById(R.id.editText1);
                editText1.setHint("请输入类型");
                editText1.setInputType(InputType.TYPE_CLASS_TEXT);
                dialog.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText1.getText().toString().trim().equals("") && editText1.getText().toString().trim().length() <= 8) {
                            CommonUtil.hideSoftKeyboard_fragment(v, getActivity());
                            dialog.dismiss();
                            tv_kuaidi_type1.setText(editText1.getText().toString().trim());
                            goods_name = editText1.getText().toString().trim();
                        } else {
                            myToast("请输入类型或类型个数不能大于8");
                        }
                    }
                });
                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.tv_kuaidi_type2:
                //总件数
                dialog = new BaseDialog(getActivity());
                dialog.contentView(R.layout.dialog_edit)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView textView1_2 = dialog.findViewById(R.id.textView1);
                textView1_2.setText("总件数");
                TextView textView2_2 = dialog.findViewById(R.id.textView2);
                textView2_2.setText("件");
                final EditText editText1_2 = dialog.findViewById(R.id.editText1);
                editText1_2.setHint("请输入总件数");
                editText1_2.setInputType(InputType.TYPE_CLASS_NUMBER);
                dialog.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText1_2.getText().toString().trim().equals("") && Integer.valueOf(editText1_2.getText().toString().trim()) > 0) {
                            CommonUtil.hideSoftKeyboard_fragment(v, getActivity());
                            dialog.dismiss();
                            tv_kuaidi_type2.setText(editText1_2.getText().toString().trim() + "件");
                            goods_quantity = editText1_2.getText().toString().trim();
                        } else {
                            myToast("请输入总件数");
                        }
                    }
                });
                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.tv_kuaidi_type3:
                //总重量
                dialog = new BaseDialog(getActivity());
                dialog.contentView(R.layout.dialog_edit)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView textView1_3 = dialog.findViewById(R.id.textView1);
                textView1_3.setText("总重量");
                TextView textView2_3 = dialog.findViewById(R.id.textView2);
                textView2_3.setText("kg");
                final EditText editText1_3 = dialog.findViewById(R.id.editText1);
                editText1_3.setHint("请输入总重量");
                editText1_3.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                dialog.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText1_3.getText().toString().trim().equals("") && Integer.valueOf(editText1_3.getText().toString().trim()) > 0) {
                            CommonUtil.hideSoftKeyboard_fragment(v, getActivity());
                            dialog.dismiss();
                            tv_kuaidi_type3.setText(editText1_3.getText().toString().trim() + "kg");
                            goods_weight = editText1_3.getText().toString().trim();
                        } else {
                            myToast("请输入总重量");
                        }
                    }
                });
                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.tv_kuaidi_type4:
                //总体积
                dialog = new BaseDialog(getActivity());
                dialog.contentView(R.layout.dialog_edit)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView textView1_4 = dialog.findViewById(R.id.textView1);
                textView1_4.setText("总体积");
                TextView textView2_4 = dialog.findViewById(R.id.textView2);
                textView2_4.setText("m³");
                final EditText editText1_4 = dialog.findViewById(R.id.editText1);
                editText1_4.setHint("请输入总体积");
                editText1_4.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                dialog.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText1_4.getText().toString().trim().equals("") && Integer.valueOf(editText1_4.getText().toString().trim()) > 0) {
                            CommonUtil.hideSoftKeyboard_fragment(v, getActivity());
                            dialog.dismiss();
                            tv_kuaidi_type4.setText(editText1_4.getText().toString().trim() + "m³");
                            goods_bulk = editText1_4.getText().toString().trim();
                        } else {
                            myToast("请输入总体积");
                        }
                    }
                });
                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.iv_kuaidi_kaiguan:
                //是否需要送货上门
                if (goods_send_home.equals("2")) {
                    goods_send_home = "1";
                    iv_kuaidi_kaiguan.setImageResource(R.mipmap.ic_kai);
                } else {
                    goods_send_home = "2";
                    iv_kuaidi_kaiguan.setImageResource(R.mipmap.ic_guan);
                }
                break;
        }
    }

    //改变类型
    private void changeUI() {
        switch (use_type) {
            case 1:
                tv_type1.setBackgroundResource(R.drawable.yuanjiao_10_baise_top);
                tv_type2.setBackgroundResource(R.drawable.yuanjiao_10_huiise_top);
                tv_type3.setBackgroundResource(R.drawable.yuanjiao_10_huiise_top);
                tv_type1.setTextColor(getResources().getColor(R.color.black1));
                tv_type2.setTextColor(getResources().getColor(R.color.black2));
                tv_type3.setTextColor(getResources().getColor(R.color.black2));

                tv_tujingdian.setVisibility(View.VISIBLE);
                ll_time1.setVisibility(View.VISIBLE);
                if (is_plan.equals("2")) {
                    ll_time2.setVisibility(View.GONE);

                    plan_time = "";

                    tv_now.setBackgroundResource(R.drawable.yuanjiao_10_lanse_left);
                    tv_next.setBackgroundResource(R.drawable.yuanjiao_10_huise_right);
                    tv_now.setTextColor(getResources().getColor(R.color.white));
                    tv_next.setTextColor(getResources().getColor(R.color.black2));
                } else {
                    ll_time2.setVisibility(View.VISIBLE);

                    tv_now.setBackgroundResource(R.drawable.yuanjiao_10_huise_left);
                    tv_next.setBackgroundResource(R.drawable.yuanjiao_10_lanse_right);
                    tv_now.setTextColor(getResources().getColor(R.color.black2));
                    tv_next.setTextColor(getResources().getColor(R.color.white));
                }
                ll_time3.setVisibility(View.GONE);
                ll_kuaidi.setVisibility(View.GONE);

//                ll_fahuo.setVisibility(View.GONE);
//                ll_shouhuo.setVisibility(View.GONE);

                break;
            case 2:
                tv_type1.setBackgroundResource(R.drawable.yuanjiao_10_huiise_top);
                tv_type2.setBackgroundResource(R.drawable.yuanjiao_10_baise_top);
                tv_type3.setBackgroundResource(R.drawable.yuanjiao_10_huiise_top);
                tv_type1.setTextColor(getResources().getColor(R.color.black2));
                tv_type2.setTextColor(getResources().getColor(R.color.black1));
                tv_type3.setTextColor(getResources().getColor(R.color.black2));

                tv_tujingdian.setVisibility(View.VISIBLE);
                ll_time1.setVisibility(View.GONE);
                ll_time2.setVisibility(View.GONE);
                ll_time3.setVisibility(View.VISIBLE);
                ll_kuaidi.setVisibility(View.GONE);
//                ll_fahuo.setVisibility(View.GONE);
//                ll_shouhuo.setVisibility(View.GONE);
                break;
            case 3:
                tv_type1.setBackgroundResource(R.drawable.yuanjiao_10_huiise_top);
                tv_type2.setBackgroundResource(R.drawable.yuanjiao_10_huiise_top);
                tv_type3.setBackgroundResource(R.drawable.yuanjiao_10_baise_top);
                tv_type1.setTextColor(getResources().getColor(R.color.black2));
                tv_type2.setTextColor(getResources().getColor(R.color.black2));
                tv_type3.setTextColor(getResources().getColor(R.color.black1));

                tv_tujingdian.setVisibility(View.GONE);
                ll_time1.setVisibility(View.GONE);
                ll_time2.setVisibility(View.GONE);
                ll_time3.setVisibility(View.GONE);
                ll_kuaidi.setVisibility(View.VISIBLE);
//                ll_fahuo.setVisibility(View.VISIBLE);
//                ll_shouhuo.setVisibility(View.VISIBLE);
                break;
        }
    }

    //改变车型
    private void changeCarType() {
        //recyclerView切换
        recyclerView.scrollToPosition(item);
        mStringAdapter.notifyDataSetChanged();
        //设置viewPager切换
        viewPager.setCurrentItem(item);
        mPageAdapter.notifyDataSetChanged();

    }

    //预约时间
    private void setDate(String string, TextView textView) {
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH);
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //分钟
        int minute = calendar.get(Calendar.MINUTE);
        //秒
        int second = calendar.get(Calendar.SECOND);


        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        //正确设置方式 原因：注意事项有说明
        startDate.set(year, month, day, hour, minute);

        //当前时间加3天
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        endDate.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));

        pvTime1 = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                textView.setText(CommonUtil.getTime1(date));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(15)//滚轮文字大小
                .setTitleSize(16)//标题文字大小
                .setTitleText(string)//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(getResources().getColor(R.color.black2))//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.blue))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.blue))//取消按钮文字颜色
                .setTitleBgColor(getResources().getColor(R.color.black5))//标题背景颜色 Night mode
                .setBgColor(getResources().getColor(R.color.white))//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();

        Dialog mDialog = pvTime1.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime1.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }

        pvTime1.show();
    }

    private boolean match() {
        city = tv_addr.getText().toString();
        if (city.equals("")) {
            myToast("请选择城市");
            return false;
        }
        switch (use_type) {
            case 1:
                if (is_plan.equals("1")) {
                    plan_time = tv_time.getText().toString().trim();
                    if (plan_time.equals("")) {
                        myToast("请选择预约时间");
                        return false;
                    }
                    if (Long.valueOf(CommonUtil.dataOne(plan_time)) * 1000 <= System.currentTimeMillis()) {
                        myToast("预约时间不能低于当前时间");
                        return false;
                    }
                } else {
                    plan_time = "";
                }

                break;
            case 2:
                plan_time = tv_time3.getText().toString().trim();
                if (plan_time.equals("")) {
                    myToast("请选择用车时间");
                    return false;
                }
                if (Long.valueOf(CommonUtil.dataOne(plan_time)) * 1000 <= System.currentTimeMillis()) {
                    myToast("用车时间不能低于当前时间");
                    return false;
                }
                break;
            case 3:
                plan_time = "";
                if (goods_name.equals("")) {
                    myToast("请输入类型");
                    return false;
                }
                if (goods_quantity.equals("")) {
                    myToast("请输入总件数");
                    return false;
                }
                if (goods_weight.equals("")) {
                    myToast("请输入总重量");
                    return false;
                }
                if (goods_bulk.equals("")) {
                    myToast("请输入总体积");
                    return false;
                }
                break;
        }

        if (startAddr_id.equals("")) {
            myToast("请选择发货地址");
            return false;
        }
        if (endAddr_id.equals("")) {
            myToast("请选择收货地址");
            return false;
        }
        if (startAddr_id.equals(endAddr_id)) {
            myToast("收货地址和发货地址不能一样");
            return false;
        }

        if (ll_add.getChildCount() > 4) {
            myToast("途经地不能大于4个");
            return false;
        }

        addr_ids = startAddr_id;
        for (int i = 0; i < ll_add.getChildCount(); i++) {
            View childAt = ll_add.getChildAt(i);
            TextView tv_id = (TextView) childAt.findViewById(R.id.tv_id);

            if (!TextUtils.isEmpty(tv_id.getText().toString())) {
                addr_ids = addr_ids + "," + tv_id.getText().toString();
            } else {
                showToast("请选择途经地");
            }
        }
        addr_ids = addr_ids + "," + endAddr_id;
        MyLogger.i(">>>>>>>>" + addr_ids);
        return true;
    }

    private void RequestAdd(Map<String, String> params) {
        OkHttpClientManager.postAsyn(getActivity(), URLs.OrderAdd, params, new OkHttpClientManager.ResultCallback<AddFeeModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    if (info.contains("认证")) {
                        showToast("您暂未完成认证，确定前往认证？",
                                "去认证", "取消",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        CommonUtil.gotoActivity(getActivity(), Auth_ShenFenZhengActivity.class, false);
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                    } else {
                        showToast(info);
                    }
                }
            }

            @Override
            public void onResponse(AddFeeModel response) {
                MyLogger.i(">>>>>>>>>计算费用" + response);
                hideProgress();
                model = response;
                localUserInfo.setIsordertrue("0");//是否下单成功//0未成功，1成功

                Bundle bundle5 = new Bundle();
                bundle5.putString("city", tv_addr.getText().toString());
                bundle5.putString("car_type_id", carTypeList.get(item).getId() + "");//车型id
                bundle5.putString("use_type", use_type + "");//用车类型1专车2顺风车3快递
                bundle5.putString("is_plan", is_plan + "");//用车时间类型1预约2现在
                bundle5.putString("plan_time", plan_time);//预约时间

//                bundle5.putString("mileage", response.getMillage() + "");//里程km
//                bundle5.putString("pre_time", response.getDuration() + "");//预计耗时s
//                bundle5.putString("price", response.getPrice() + "");//价格
                bundle5.putString("addr_ids", addr_ids + "");//需要得位置起始地顺序id,逗号隔开(1,2,3)
                bundle5.putString("goods_name", goods_name);//类型
                bundle5.putString("goods_quantity", goods_quantity);//总件数
                bundle5.putString("goods_weight", goods_weight);//总重量
                bundle5.putString("goods_bulk", goods_bulk);//总体积
                bundle5.putString("goods_send_home", goods_send_home);//是否送货
                bundle5.putSerializable("AddFeeModel", response);
                CommonUtil.gotoActivityWithData(getActivity(), ConfirmOrderActivity.class, bundle5, false);
            }
        });
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
        MyLogger.i(">>>>>requestCode:" + requestCode +
                "\n>>>>>resultCode:" + resultCode +
                "\n>>>>>data:" + data);
        switch (requestCode) {
            case 10001:
                //起点
                if (data != null) {
                    Bundle bundle1 = data.getExtras();
                    String addr1 = bundle1.getString("addr");
                    startAddr_id = bundle1.getString("addr_id");
                    MyLogger.i(">>>地址>>>>" + addr1);
                    tv_qidian.setText(addr1);

                    tv_fahuo_name.setText("发货人：" + bundle1.getString("name"));
                    tv_fahuo_mobile.setText("电话号码：" + bundle1.getString("mobile"));

                    tv_addr.setText(bundle1.getString("city"));
                }
                break;
            case 10002:
                //终点
                if (data != null) {
                    Bundle bundle2 = data.getExtras();
                    String addr2 = bundle2.getString("addr");
                    endAddr_id = bundle2.getString("addr_id");
                    MyLogger.i(">>>地址>>>>" + addr2);
                    tv_zhongdian.setText(addr2);

                    tv_shouhuo_name.setText("收货人：" + bundle2.getString("name"));
                    tv_shouhuo_mobile.setText("电话号码：" + bundle2.getString("mobile"));
                }
                break;
            case 10003:
                //途经点
                if (data != null) {
                    Bundle bundle3 = data.getExtras();
                    /*String addr1 = bundle3.getString("addr");
                    MyLogger.i(">>>地址>>>>" + addr1);*/
                    addView(bundle3.getString("addr"), bundle3.getString("addr_id"),
                            bundle3.getString("name"), bundle3.getString("mobile"));
                }
                break;
        }

    }

    //添加布局
    private void addView(String addr, String addr_id, String name, String mobile) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.item_addaddress, null, false);
        view.setLayoutParams(lp);
        //实例化子页面的控件
        TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
        TextView tv_dizhi = (TextView) view.findViewById(R.id.tv_dizhi);
        TextView tv_delete = (TextView) view.findViewById(R.id.tv_delete);
        TextView tv_tujingdian_name = (TextView) view.findViewById(R.id.tv_tujingdian_name);
        TextView tv_tujingdian_mobile = (TextView) view.findViewById(R.id.tv_tujingdian_mobile);

        tv_id.setText(addr_id);
        tv_dizhi.setText(addr);
        tv_tujingdian_name.setText("收货人：" + name);
        tv_tujingdian_mobile.setText("电话号码：" + mobile);

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_id.setText("");
                tv_dizhi.setText("");
                ll_add.removeView(view);
            }
        });
        ll_add.addView(view);
    }

    /**
     * *****************************************GuidePageAdapter********************************************
     */
    class GuidePageAdapter extends PagerAdapter {

        //销毁position位置的界面
        @Override
        public void destroyItem(View v, int position, Object arg2) {
            // TODO Auto-generated method stub
            ((ViewPager) v).removeView(pageViews.get(position));

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        //获取当前窗体界面数
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return pageViews.size();
        }

        //初始化position位置的界面
        @Override
        public Object instantiateItem(View v, int position) {
            // TODO Auto-generated method stub
            ((ViewPager) v).addView(pageViews.get(position));
            return pageViews.get(position);
        }

        // 判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View v, Object arg1) {
            // TODO Auto-generated method stub
            return v == arg1;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {
        private int maxPos = pageViews.size() - 1;//最后一页
        private int currentPageScrollStatus;

        @Override
        public void onPageScrollStateChanged(int state) {
            // TODO Auto-generated method stub
            //记录page滑动状态，如果滑动了state就是1
            currentPageScrollStatus = state;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // TODO Auto-generated method stub

            //recyclerView切换
            recyclerView.scrollToPosition(item);
            mStringAdapter.notifyDataSetChanged();

            if (item == 0) {
                //如果offsetPixels是0页面也被滑动了，代表在第一页还要往左划
                if (positionOffsetPixels == 0 && currentPageScrollStatus == 1) {

                }
            } else if (item == maxPos) {
                //已经在最后一页还想往右划
                if (positionOffsetPixels == 0 && currentPageScrollStatus == 1) {


                }
            }
        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub

            setCurrentPos(position);

        }

        public void setMaxPage(int position) {
            //设置最后一页的position值
            maxPos = position;
        }

        public void setCurrentPos(int position) {
            //设置当前页的position值
            item = position;
        }
    }
}
