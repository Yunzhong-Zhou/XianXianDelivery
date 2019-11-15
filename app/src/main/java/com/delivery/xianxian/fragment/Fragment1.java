package com.delivery.xianxian.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
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
import com.bumptech.glide.Glide;
import com.delivery.xianxian.R;
import com.delivery.xianxian.activity.MainActivity;
import com.delivery.xianxian.activity.SelectAddressActivity;
import com.delivery.xianxian.base.BaseFragment;
import com.delivery.xianxian.model.Fragment1Model;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
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
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.delivery.xianxian.net.OkHttpClientManager.IMGHOST;
import static com.superrtc.ContextUtils.getApplicationContext;


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
    ImageView iv_left,iv_right;

    //现在、预约
    LinearLayout ll_time1;
    TextView tv_now,tv_next;

    //地址
    TextView tv_qidian,tv_zhongdian,tv_tujingdian;
    LinearLayout ll_add;



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
        mLocationClient.stopLocation();
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
                    tv1.setText("长宽高：" + carTypeList.get(i).getWeight());
                    tv2.setText("载重：" + carTypeList.get(i).getSize());
                    tv3.setText("承载体积：" + carTypeList.get(i).getBulk());
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
                use_type = 3;
                changeUI();
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
                tv_now.setBackgroundResource(R.drawable.yuanjiao_10_lanse_left);
                tv_next.setBackgroundResource(R.drawable.yuanjiao_10_huise_right);
                tv_now.setTextColor(getResources().getColor(R.color.white));
                tv_next.setTextColor(getResources().getColor(R.color.black2));
                break;
            case R.id.tv_next:
                //预约用车
                tv_now.setBackgroundResource(R.drawable.yuanjiao_10_huise_left);
                tv_next.setBackgroundResource(R.drawable.yuanjiao_10_lanse_right);
                tv_now.setTextColor(getResources().getColor(R.color.black2));
                tv_next.setTextColor(getResources().getColor(R.color.white));
                break;

            case R.id.tv_qidian:
                //起点
                Intent intent1 = new Intent(getActivity(), SelectAddressActivity.class);
                startActivityForResult(intent1, 10001);
                break;
            case R.id.tv_zhongdian:
                //终点
                Intent intent2 = new Intent(getActivity(), SelectAddressActivity.class);
                startActivityForResult(intent2, 10002);
                break;
            case R.id.tv_tujingdian:
                //途经点
                Intent intent3 = new Intent(getActivity(), SelectAddressActivity.class);
                startActivityForResult(intent3, 10003);
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

                ll_time1.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_type1.setBackgroundResource(R.drawable.yuanjiao_10_huiise_top);
                tv_type2.setBackgroundResource(R.drawable.yuanjiao_10_baise_top);
                tv_type3.setBackgroundResource(R.drawable.yuanjiao_10_huiise_top);
                tv_type1.setTextColor(getResources().getColor(R.color.black2));
                tv_type2.setTextColor(getResources().getColor(R.color.black1));
                tv_type3.setTextColor(getResources().getColor(R.color.black2));

                ll_time1.setVisibility(View.GONE);

                break;
            case 3:
                tv_type1.setBackgroundResource(R.drawable.yuanjiao_10_huiise_top);
                tv_type2.setBackgroundResource(R.drawable.yuanjiao_10_huiise_top);
                tv_type3.setBackgroundResource(R.drawable.yuanjiao_10_baise_top);
                tv_type1.setTextColor(getResources().getColor(R.color.black2));
                tv_type2.setTextColor(getResources().getColor(R.color.black2));
                tv_type3.setTextColor(getResources().getColor(R.color.black1));

                ll_time1.setVisibility(View.GONE);

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
        MyLogger.i(">>>>>requestCode:"+requestCode+
                "\n>>>>>resultCode:"+resultCode+
                "\n>>>>>data:"+data);
        switch (requestCode){
            case 10001:
                //起点
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    String scanResult = bundle.getString("addr");
                    MyLogger.i(">>>地址>>>>" + scanResult);

                }
                break;
            case 10002:
                //终点
                break;
            case 10003:
                //途经点
                break;
        }

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
