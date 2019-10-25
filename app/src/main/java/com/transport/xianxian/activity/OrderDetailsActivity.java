package com.transport.xianxian.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.TruckPath;
import com.amap.api.services.route.TruckRouteRestult;
import com.bumptech.glide.Glide;
import com.cy.cyflowlayoutlibrary.FlowLayout;
import com.cy.cyflowlayoutlibrary.FlowLayoutAdapter;
import com.hyphenate.easeui.EaseConstant;
import com.squareup.okhttp.Request;
import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;
import com.transport.xianxian.model.OrderDetailsModel;
import com.transport.xianxian.net.OkHttpClientManager;
import com.transport.xianxian.net.URLs;
import com.transport.xianxian.utils.CommonUtil;
import com.transport.xianxian.utils.MyLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import overlay.AMapUtil;
import overlay.TruckRouteColorfulOverLay;

import static com.transport.xianxian.net.OkHttpClientManager.IMGHOST;

/**
 * Created by zyz on 2019-10-23.
 * 订单详情
 */
public class OrderDetailsActivity extends BaseActivity implements RouteSearch.OnTruckRouteSearchListener, AMap.OnMyLocationChangeListener {
    String id = "";
    OrderDetailsModel model;
    private AMap aMap;
    private MapView mapView;
    private RouteSearch mRouteSearch;
    private TruckRouteRestult truckRouteResult;
    private LatLonPoint mStartPoint = null;//起点
    private LatLonPoint mEndPoint = null;//终点，39.995576,116.481288
    List<LatLonPoint> pointList = new ArrayList<>();//途经点
    private ProgressDialog progDialog = null;// 搜索时进度条

    LinearLayout linearLayout1, ll_hint1, ll_hint2;
    ImageView imageView1, iv_xinxi, iv_dianhua, iv_xiangqing;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11,
            tv_addr1, tv_title1, tv_juli1, tv_addr2, tv_title2, tv_juli2, tv_shouqi, tv_fanhui, tv_queren;

    double lat = 0, lng = 0, juli = 0;
    private DPoint mStartDPoint = null;
    private DPoint mEndDPoint = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        //初始化地图
        mapView = (MapView) findViewById(R.id.route_map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
    }

    @Override
    protected void initView() {
        linearLayout1 = findViewByID_My(R.id.linearLayout1);
        ll_hint1 = findViewByID_My(R.id.ll_hint1);
        ll_hint2 = findViewByID_My(R.id.ll_hint2);

        imageView1 = findViewByID_My(R.id.imageView1);
        iv_xinxi = findViewByID_My(R.id.iv_xinxi);
        iv_dianhua = findViewByID_My(R.id.iv_dianhua);
        iv_xiangqing = findViewByID_My(R.id.iv_xiangqing);

        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);
        textView4 = findViewByID_My(R.id.textView4);
        textView5 = findViewByID_My(R.id.textView5);
        textView6 = findViewByID_My(R.id.textView6);
        textView7 = findViewByID_My(R.id.textView7);
        textView8 = findViewByID_My(R.id.textView8);
        textView9 = findViewByID_My(R.id.textView9);
        textView10 = findViewByID_My(R.id.textView10);
        textView11 = findViewByID_My(R.id.textView11);

        tv_addr1 = findViewByID_My(R.id.tv_addr1);
        tv_title1 = findViewByID_My(R.id.tv_title1);
        tv_juli1 = findViewByID_My(R.id.tv_juli1);
        tv_addr2 = findViewByID_My(R.id.tv_addr2);
        tv_title2 = findViewByID_My(R.id.tv_title2);
        tv_juli2 = findViewByID_My(R.id.tv_juli2);

        tv_shouqi = findViewByID_My(R.id.tv_shouqi);
        tv_fanhui = findViewByID_My(R.id.tv_fanhui);
        tv_queren = findViewByID_My(R.id.tv_queren);

        //收起布局
        ll_hint1.setVisibility(View.GONE);
        ll_hint2.setVisibility(View.GONE);
        iv_xiangqing.setVisibility(View.VISIBLE);
        tv_shouqi.setVisibility(View.GONE);
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
                + "&id=" + id;
        Request(string);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(OrderDetailsActivity.this, URLs.OrderDetails + string, new OkHttpClientManager.ResultCallback<OrderDetailsModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(OrderDetailsModel response) {
//                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>订单详情" + response);
                model = response;
                for (int i = 0; i < response.getTindent().getAddr_list().size(); i++) {
                    pointList.add(new LatLonPoint(Double.valueOf(response.getTindent().getAddr_list().get(i).getLat())
                            , Double.valueOf(response.getTindent().getAddr_list().get(i).getLng())));//添加标注点
                    if (i == 0) {
                        mStartPoint = new LatLonPoint(Double.valueOf(response.getTindent().getAddr_list().get(i).getLat())
                                , Double.valueOf(response.getTindent().getAddr_list().get(i).getLng()));//起点

                        tv_addr1.setText(response.getTindent().getAddr_list().get(i).getAddr());
                        tv_title1.setText(response.getTindent().getAddr_list().get(i).getAddr_detail());

                        /*if (lat != 0 && lng != 0) {
                            mStartDPoint = new DPoint(lat, lng);//起点
                            mEndDPoint = new DPoint(Double.valueOf(response.getTindent().getAddr_list().get(i).getLat()),
                                    Double.valueOf(response.getTindent().getAddr_list().get(i).getLng()));//终点，39.995576,116.481288
                            juli = CoordinateConverter.calculateLineDistance(mStartDPoint, mEndDPoint);
                            tv_juli1.setText("距您" + CommonUtil.distanceFormat(juli) + "m");
                        }*/
                    } else if (i == (model.getTindent().getAddr_list().size() - 1)) {
                        mEndPoint = new LatLonPoint(Double.valueOf(response.getTindent().getAddr_list().get(i).getLat()),
                                Double.valueOf(response.getTindent().getAddr_list().get(i).getLng()));//终点

                        tv_addr2.setText(response.getTindent().getAddr_list().get(i).getAddr());
                        tv_title2.setText(response.getTindent().getAddr_list().get(i).getAddr_detail());
                        /*if (lat != 0 && lng != 0) {
                            mStartDPoint = new DPoint(lat, lng);//起点
                            mEndDPoint = new DPoint(Double.valueOf(response.getTindent().getAddr_list().get(i).getLat()),
                                    Double.valueOf(response.getTindent().getAddr_list().get(i).getLng()));//终点，39.995576,116.481288
                            juli = CoordinateConverter.calculateLineDistance(mStartDPoint, mEndDPoint);
                            tv_juli2.setText("距您" + CommonUtil.distanceFormat(juli) + "m");
                        }*/
                    } else {
                        //途经点

                    }
                }
                setfromandtoMarker();//显示标注物
                searchRouteResult(RouteSearch.DRIVING_MULTI_STRATEGY_FASTEST_SHORTEST_AVOID_CONGESTION);//默认避免拥堵、设置车辆信息

                if (!response.getTindent().getSend_head().equals(""))
                    Glide.with(OrderDetailsActivity.this)
                            .load(IMGHOST + response.getTindent().getSend_head())
                            .centerCrop()
//                    .placeholder(R.mipmap.headimg)//加载站位图
//                    .error(R.mipmap.headimg)//加载失败
                            .into(imageView1);//加载图片
                textView1.setText(response.getTindent().getSend_name());//昵称
                textView2.setText(response.getTindent().getIndustry());//行业
                textView3.setText("货源单号" + response.getTindent().getSn());//货源单号
                textView4.setText(response.getTindent().getCreated_at() + " 发布");// 发布
                textView5.setText(response.getTindent().getNow_state() + " " + response.getTindent().getNow_state_action());// 卸货
                textView6.setText(response.getTindent().getSend_time());//离装货时间还有0小时
                textView7.setText(response.getTindent().getRemark());//备注
                textView8.setText("¥ " + response.getTindent().getPrice());//订单金额
                textView9.setText(response.getTindent().getPrice_detail().getStart() + "元");//起步价
                textView10.setText(response.getTindent().getPrice_detail().getMilleage() + "元");//离装货时间还有0小时
//                textView11.setText(response.getTindent().getSend_time());//描述

                //标签
                FlowLayoutAdapter<String> flowLayoutAdapter;
                List<String> stringList = new ArrayList<>();
                for (int i = 0; i < response.getTindent().getTag().size(); i++) {
                    stringList.add(response.getTindent().getTag().get(i).getVal());
                }
                flowLayoutAdapter = new FlowLayoutAdapter<String>(stringList) {
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
                ((FlowLayout) findViewByID_My(R.id.flowLayout)).setAdapter(flowLayoutAdapter);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_xinxi:
                //聊天
                Bundle bundle1 = new Bundle();
                bundle1.putString(EaseConstant.EXTRA_USER_ID, model.getTindent().getHx_username());
                CommonUtil.gotoActivityWithData(this, ChatActivity.class, bundle1, false);
                break;
            case R.id.iv_dianhua:
                //打电话
                showToast("确认拨打 " + model.getTindent().getSend_mobile() + " 吗？", "确认", "取消",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                //创建打电话的意图
                                Intent intent = new Intent();
                                //设置拨打电话的动作
                                intent.setAction(Intent.ACTION_CALL);//直接拨出电话
//                                                                        intent.setAction(Intent.ACTION_DIAL);//只调用拨号界面，不拨出电话
                                //设置拨打电话的号码
                                intent.setData(Uri.parse("tel:" + model.getTindent().getSend_mobile()));
                                //开启打电话的意图
                                startActivity(intent);
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                break;
            case R.id.linearLayout1:
            case R.id.iv_xiangqing:
                //查看详情
                ll_hint1.setVisibility(View.VISIBLE);
                ll_hint2.setVisibility(View.VISIBLE);
                iv_xiangqing.setVisibility(View.GONE);
                tv_shouqi.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_shouqi:
                //收起
                ll_hint1.setVisibility(View.GONE);
                ll_hint2.setVisibility(View.GONE);
                iv_xiangqing.setVisibility(View.VISIBLE);
                tv_shouqi.setVisibility(View.GONE);
                break;

            case R.id.tv_fanhui:
                //返回列表
                finish();
                break;
            case R.id.tv_queren:
                //确认接单
                showToast("确认接单吗？", "确认", "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Map<String, String> params = new HashMap<>();
                        params.put("token", localUserInfo.getToken());
                        params.put("id", model.getTindent().getId());
                        params.put("action", "2");//接单
                        RequestJieDan(params);

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    private void RequestJieDan(Map<String, String> params) {
        OkHttpClientManager.postAsyn(OrderDetailsActivity.this, URLs.OrderDetails_JieDan, params, new OkHttpClientManager.ResultCallback<String>() {
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
                MyLogger.i(">>>>>>>>>是否接单" + response);
                myToast("接单成功");
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                CommonUtil.gotoActivityWithData(OrderDetailsActivity.this, MapNavigationActivity.class, bundle, false);

                /*JSONObject jObj;
                try {
                    jObj = new JSONObject(response);
                    *//*JSONArray jsonArray = jObj.getJSONArray("data");
                    list = JSON.parseArray(jsonArray.toString(), Fragment1ListModel.class);
                    MyLogger.i(">>>>>>>" + list.size());*//*
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
        titleView.setTitle("订单详情");
    }


    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(60 * 1000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        //以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(this);

        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setOnTruckRouteSearchListener(this);
    }

    /**
     * 添加覆盖物(起点和终点)
     */
    private void setfromandtoMarker() {
        //批量添加marker到地图上
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < model.getTindent().getAddr_list().size(); i++) {
                    if (i == 0) {
                        addMarker(1, model.getTindent().getAddr_list().get(i).getAddr_detail(),
                                model.getTindent().getAddr_list().get(i).getAddr(), pointList.get(i));//起点
                    } else if (i == (model.getTindent().getAddr_list().size() - 1)) {
                        addMarker(2, model.getTindent().getAddr_list().get(i).getAddr_detail(),
                                model.getTindent().getAddr_list().get(i).getAddr(), pointList.get(i));//终点
                    } else {
                        //途经点
                        addMarker(3, model.getTindent().getAddr_list().get(i).getAddr_detail(),
                                model.getTindent().getAddr_list().get(i).getAddr(), pointList.get(i));
                    }

                }
            }
        });
        //显示多个infowindow
        /*ArrayList<MarkerOptions> optionsList = new ArrayList<>();
        MarkerOptions options1 = new MarkerOptions();
        options1.position(AMapUtil.convertToLatLng(mStartPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.start))
                .title("起点")
                .snippet("起点地址描述")
                .setFlat(true);
        MarkerOptions options2 = new MarkerOptions();
        options2.position(AMapUtil.convertToLatLng(mEndPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.end))
                .title("终点")
                .snippet("终点地址描述")
                .setFlat(true);
        optionsList.add(options1);
        optionsList.add(options2);

        aMap.addMarkers(optionsList,true);*/

        /*//只显示一个infowindow
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mStartPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.start))
                .title("起点")
                .snippet("起点地址描述")
        ).showInfoWindow();

        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mEndPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.end))
                .title("终点")
                .snippet("终点地址描述")
        ).showInfoWindow();*/
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int mode) {
        if (mStartPoint == null) {
            myToast("定位中，稍后再试...");
            return;
        }
        if (mEndPoint == null) {
            myToast("终点未设置");
        }
        showProgressDialog();
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);

        //设置车辆信息
        fromAndTo.setPlateNumber(model.getTindent().getCar_type_info().getCar_number());//车牌号，如A6BN05
//        fromAndTo.setPlateProvince("京");

        // 第一个参数表示路径规划的起点和终点，第二个参数表示计算路径的模式，第三个参数表示途经点，第四个参数货车大小 必填
        RouteSearch.TruckRouteQuery query = new RouteSearch.TruckRouteQuery(fromAndTo, mode, pointList, RouteSearch.TRUCK_SIZE_HEAVY);

        query.setTruckAxis(Float.valueOf(model.getTindent().getCar_type_info().getVehicle_axis()));//轴数
        query.setTruckHeight(Float.valueOf(model.getTindent().getCar_type_info().getVehicle_height()));//高 单位：米
        query.setTruckWidth(Float.valueOf(model.getTindent().getCar_type_info().getVehicle_weight()));//宽度，单位：米
        query.setTruckLoad(Float.valueOf(model.getTindent().getCar_type_info().getVehicle_load()));//最大载重，单位：吨
        query.setTruckWeight(Float.valueOf(model.getTindent().getCar_type_info().getVehicle_weight()));//载重

        //异步查询
        mRouteSearch.calculateTruckRouteAsyn(query);
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null) {
            progDialog = new ProgressDialog(this);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setIndeterminate(false);
            progDialog.setCancelable(true);
            progDialog.setMessage("正在搜索...");
            progDialog.show();
        }
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    /**
     * 返回监听
     *
     * @param result
     * @param rCode
     */
    @Override
    public void onTruckRouteSearched(TruckRouteRestult result, int rCode) {
        dissmissProgressDialog();
        //建议通过TruckPath中getRestriction() 判断路线上是否存在限行
        /**
         * 限行结果
         * 0，未知（未输入完整/正确车牌号信息时候显示）
         * 1，已规避限行
         * 2，起点限行
         * 3，途径点在限行区域内（设置途径点才出现此报错）
         * 4，途径限行区域
         * 5，终点限行
         */
        if (rCode == 1000) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                truckRouteResult = result;
                TruckPath path = truckRouteResult.getPaths().get(0);
                if (path == null) {
                    return;
                }
//                aMap.clear();// 清理地图上的所有覆盖物

                TruckRouteColorfulOverLay drivingRouteOverlay = new TruckRouteColorfulOverLay(
                        this, aMap, path, truckRouteResult.getStartPos(),
                        truckRouteResult.getTargetPos(), null);

                drivingRouteOverlay.removeFromMap();
                drivingRouteOverlay.setIsColorfulline(true);
                drivingRouteOverlay.addToMap();
                drivingRouteOverlay.zoomToSpan();
            } else {
                myToast("对不起，没有搜索到相关数据！");
            }
        } else if (rCode == 1901) {
            myToast("参数无效");

        } else if (rCode == 1904) {
            myToast("搜索失败,请检查网络连接！");
        } else if (rCode == 1002) {
            myToast("key验证无效！");
        } else {
            myToast("结果：" + rCode);
        }
    }

    /**
     * by moos on 2017/11/15
     * func:添加marker到地图上显示
     */
    BitmapDescriptor bitmapDescriptor;

    private void addMarker(int type, String s1, String s2, LatLonPoint point) {
        MarkerOptions options1 = new MarkerOptions();
        options1.position(AMapUtil.convertToLatLng(point));
        customizeMarkerIcon(type, s1, s2, new OnMarkerIconLoadListener() {
            @Override
            public void markerIconLoadingFinished(View view) {
//                bitmapDescriptor = BitmapDescriptorFactory.fromView(view);
                options1.icon(bitmapDescriptor);
                aMap.addMarker(options1);
            }
        });
       /* MarkerOptions options2 = new MarkerOptions();
        options1.position(AMapUtil.convertToLatLng(mEndPoint));
        customizeMarkerIcon(2,"终点","终点地址描述","卸货点",new OnMarkerIconLoadListener() {
            @Override
            public void markerIconLoadingFinished(View view) {
//                bitmapDescriptor = BitmapDescriptorFactory.fromView(view);
                options2.icon(bitmapDescriptor);
                aMap.addMarker(options2);
            }
        });*/

    }

    /**
     * by moos on 2017/11/13
     * func:定制化marker的图标
     *
     * @return
     */
    private void customizeMarkerIcon(int type, String s1, String s2, OnMarkerIconLoadListener listener) {
        final View markerView = LayoutInflater.from(this).inflate(R.layout.marker_bg, null);
        TextView tv1 = markerView.findViewById(R.id.tv1);
        TextView tv2 = markerView.findViewById(R.id.tv2);
        TextView tv3 = markerView.findViewById(R.id.tv3);
        ImageView iv = markerView.findViewById(R.id.iv);

        tv1.setText(s1);
        tv2.setText(s2);
        switch (type) {
            case 1:
                //装货地
                tv3.setText("装货地");
                tv3.setTextColor(getResources().getColor(R.color.blue));
                iv.setImageResource(R.mipmap.start);
                break;
            case 2:
                //卸货地
                tv3.setText("卸货地");
                tv3.setTextColor(getResources().getColor(R.color.red));
                iv.setImageResource(R.mipmap.end);
                break;
            default:
                //途经点
                tv3.setText("途经点");
                tv3.setTextColor(getResources().getColor(R.color.black1));
                iv.setImageResource(R.mipmap.end);
                break;
        }
        bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(markerView));
        listener.markerIconLoadingFinished(markerView);

    }

    @Override
    public void onMyLocationChange(Location location) {
        MyLogger.i(">>>>>>>>我的位置：" + location.getLatitude());
        lat = location.getLatitude();
        lng = location.getLongitude();
        if (model != null) {
            for (int i = 0; i < model.getTindent().getAddr_list().size(); i++) {
                if (i == 0) {
                    if (lat != 0 && lng != 0) {
                        mStartDPoint = new DPoint(lat, lng);//起点
                        mEndDPoint = new DPoint(Double.valueOf(model.getTindent().getAddr_list().get(i).getLat()),
                                Double.valueOf(model.getTindent().getAddr_list().get(i).getLng()));//终点，39.995576,116.481288
                        juli = CoordinateConverter.calculateLineDistance(mStartDPoint, mEndDPoint);
                        tv_juli1.setText("距您" + CommonUtil.distanceFormat(juli) + "m");
                    }
                } else if (i == (model.getTindent().getAddr_list().size() - 1)) {
                    if (lat != 0 && lng != 0) {
                        mStartDPoint = new DPoint(lat, lng);//起点
                        mEndDPoint = new DPoint(Double.valueOf(model.getTindent().getAddr_list().get(i).getLat()),
                                Double.valueOf(model.getTindent().getAddr_list().get(i).getLng()));//终点，39.995576,116.481288
                        juli = CoordinateConverter.calculateLineDistance(mStartDPoint, mEndDPoint);
                        tv_juli2.setText("距您" + CommonUtil.distanceFormat(juli) + "m");
                    }
                }
            }
        }

    }

    /**
     * by moos on 2017/11/15
     * func:自定义监听接口,用来marker的icon加载完毕后回调添加marker属性
     */
    public interface OnMarkerIconLoadListener {
        void markerIconLoadingFinished(View view);
    }

    /**
     * by mos on 2017.11.13
     * func:view转bitmap
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();

        return bitmap;

    }
}
